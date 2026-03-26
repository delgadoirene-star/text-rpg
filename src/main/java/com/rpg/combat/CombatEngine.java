package com.rpg.combat;

import com.rpg.models.Character;
import com.rpg.models.Companion;
import com.rpg.models.Element;
import com.rpg.models.Enemy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main combat engine - handles turn-based combat flow
 */
public class CombatEngine {
    private final Difficulty difficulty;
    private final DamageCalculator damageCalculator;
    private final ElementalComboSystem comboSystem;
    
    private List<Character> playerParty;
    private List<Enemy> enemies;
    private List<CombatantTurn> turnOrder;
    private int currentTurn;
    private boolean battleEnded;
    private BattleResult result;
    
    public CombatEngine(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.damageCalculator = new DamageCalculator(difficulty);
        this.comboSystem = new ElementalComboSystem();
        this.currentTurn = 0;
        this.battleEnded = false;
    }
    
    /**
     * Initialize a new battle
     */
    public void initializeBattle(List<Character> playerParty, List<Enemy> enemies) {
        this.playerParty = new ArrayList<>(playerParty);
        this.enemies = new ArrayList<>(enemies);
        this.currentTurn = 0;
        this.battleEnded = false;
        this.result = null;
        
        // Reset all combatants
        for (Character character : playerParty) {
            resetCombatant(character);
        }
        for (Enemy enemy : enemies) {
            resetCombatant(enemy);
        }
        
        // Calculate turn order
        calculateTurnOrder();
        
        comboSystem.clear();
    }
    
    /**
     * Reset combatant for battle start
     */
    private void resetCombatant(Character character) {
        // Reset cooldowns for abilities if they have any
        // Focus meters would be reset here if characters had them
    }
    
    /**
     * Calculate turn order based on speed stats
     */
    private void calculateTurnOrder() {
        turnOrder = new ArrayList<>();
        
        // Add all alive combatants
        for (Character character : playerParty) {
            if (character.isAlive()) {
                turnOrder.add(new CombatantTurn(character, false));
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                turnOrder.add(new CombatantTurn(enemy, true));
            }
        }
        
        // Sort by speed (highest first)
        turnOrder.sort((a, b) -> Double.compare(b.combatant.getSpeed(), a.combatant.getSpeed()));
    }
    
    /**
     * Process one full turn (all combatants act once)
     */
    public List<TurnEvent> processTurn() {
        if (battleEnded) {
            return Collections.emptyList();
        }
        
        currentTurn++;
        List<TurnEvent> events = new ArrayList<>();
        
        events.add(new TurnEvent(TurnEvent.Type.TURN_START, null, null, 
            "=== Turn " + currentTurn + " ==="));
        
        // Process each combatant in turn order
        for (CombatantTurn turn : turnOrder) {
            if (!turn.combatant.isAlive()) {
                continue;
            }
            
            // Process status effects at start of turn
            events.addAll(processStatusEffects(turn.combatant));
            
            // Check if still alive after status effects
            if (!turn.combatant.isAlive()) {
                events.add(new TurnEvent(TurnEvent.Type.DEATH, turn.combatant, null,
                    turn.combatant.getName() + " has been defeated!"));
                continue;
            }
            
            // Check if can act (not stunned, etc.)
            if (!turn.combatant.canAct()) {
                events.add(new TurnEvent(TurnEvent.Type.SKIP_TURN, turn.combatant, null,
                    turn.combatant.getName() + " cannot act!"));
                continue;
            }
            
            // Execute action
            if (turn.isEnemy) {
                events.addAll(executeEnemyTurn((Enemy) turn.combatant));
            } else {
                // For now, player characters will use basic attack
                // In full game, this would prompt player for input
                events.addAll(executePlayerTurn(turn.combatant));
            }
            
            // Check for battle end
            if (checkBattleEnd()) {
                events.add(new TurnEvent(TurnEvent.Type.BATTLE_END, null, null,
                    result.message));
                battleEnded = true;
                break;
            }
        }
        
        // Process elemental combo system end of turn
        comboSystem.processTurn();
        
        return events;
    }
    
    /**
     * Process status effects on a character
     */
    private List<TurnEvent> processStatusEffects(Character character) {
        List<TurnEvent> events = new ArrayList<>();
        
        double tickDamage = character.processStatusEffects();
        if (tickDamage > 0) {
            events.add(new TurnEvent(TurnEvent.Type.STATUS_DAMAGE, character, null,
                character.getName() + " takes " + String.format("%.0f", tickDamage) + 
                " damage from status effects!"));
        }
        
        return events;
    }
    
    /**
     * Execute enemy turn (AI controlled)
     */
    private List<TurnEvent> executeEnemyTurn(Enemy enemy) {
        List<TurnEvent> events = new ArrayList<>();
        
        // Get AI decision
        Enemy.AIDecision decision = enemy.getAIDecision(getAlivePlayerParty());
        
        events.add(new TurnEvent(TurnEvent.Type.ACTION, enemy, decision.getTarget(),
            enemy.getName() + "'s turn: " + decision.getReasoning()));
        
        // Execute action
        if (decision.getAbility() != null) {
            events.addAll(executeAbility(enemy, decision.getTarget(), decision.getAbility()));
            enemy.recordAbilityUsage(decision.getAbility().getId());
        } else {
            events.addAll(executeBasicAttack(enemy, decision.getTarget()));
        }
        
        // Check HP triggers
        List<String> triggers = enemy.checkHPTriggers();
        for (String trigger : triggers) {
            events.add(new TurnEvent(TurnEvent.Type.HP_TRIGGER, enemy, null,
                ">>> " + trigger + " <<<"));
        }
        
        return events;
    }
    
    /**
     * Execute player turn - uses companion AI for Companion characters
     */
    private List<TurnEvent> executePlayerTurn(Character player) {
        List<TurnEvent> events = new ArrayList<>();
        
        List<Enemy> aliveEnemies = getAliveEnemies();
        if (aliveEnemies.isEmpty()) {
            return events;
        }
        
        // Check if this character is a Companion with AI
        if (player instanceof Companion companion) {
            return executeCompanionTurn(companion, aliveEnemies);
        }
        
        // Default: basic attack on random enemy
        Enemy target = aliveEnemies.get(new Random().nextInt(aliveEnemies.size()));
        events.add(new TurnEvent(TurnEvent.Type.ACTION, player, target,
            player.getName() + " attacks " + target.getName() + "!"));
        events.addAll(executeBasicAttack(player, target));
        
        return events;
    }
    
    /**
     * Execute companion turn using AI based on combatRole and preferredTarget
     */
    private List<TurnEvent> executeCompanionTurn(Companion companion, List<Enemy> aliveEnemies) {
        List<TurnEvent> events = new ArrayList<>();
        
        CompanionAIDecision decision = makeCompanionDecision(companion, aliveEnemies);
        
        events.add(new TurnEvent(TurnEvent.Type.ACTION, companion, decision.target,
            companion.getName() + "'s turn: " + decision.reasoning));
        
        if (decision.ability != null && companion.canUseAbility(decision.ability)) {
            events.addAll(executeAbility(companion, decision.target, decision.ability));
            companion.spendFocus(decision.ability);
        } else {
            events.addAll(executeBasicAttack(companion, decision.target));
        }
        
        return events;
    }
    
    /**
     * Make an AI decision for a companion based on combatRole and preferredTarget
     */
    private CompanionAIDecision makeCompanionDecision(Companion companion, List<Enemy> enemies) {
        String role = companion.getCombatRole();
        String targetPref = companion.getPreferredTarget();
        
        // Select target based on preferredTarget
        Character target = selectTarget(companion, enemies, targetPref);
        
        // Select action based on combatRole
        Ability ability = selectAbility(companion, role, target, enemies);
        
        String reasoning;
        if (ability != null) {
            reasoning = String.format("[%s] Using %s on %s", role, ability.getName(), target.getName());
        } else {
            reasoning = String.format("[%s] Attacking %s", role, target.getName());
        }
        
        return new CompanionAIDecision(ability, target, reasoning);
    }
    
    /**
     * Select target based on companion's preferredTarget setting
     */
    private Character selectTarget(Companion companion, List<Enemy> enemies, String preferredTarget) {
        return switch (preferredTarget != null ? preferredTarget : "Random") {
            case "Weakest" -> enemies.stream()
                .min(Comparator.comparingInt(Character::getCurrentHP))
                .orElse(enemies.get(0));
            case "Strongest" -> enemies.stream()
                .max(Comparator.comparingInt(Character::getCurrentHP))
                .orElse(enemies.get(0));
            case "LowestHP%" -> enemies.stream()
                .min(Comparator.comparingDouble(Character::getHPPercentage))
                .orElse(enemies.get(0));
            case "ElementWeak" -> {
                // Find enemy weak against companion's element
                Element compElement = companion.getElementAffinity();
                Character weakTarget = enemies.stream()
                    .filter(e -> compElement.hasAdvantageOver(e.getElementAffinity()))
                    .findFirst()
                    .orElse(null);
                yield weakTarget != null ? weakTarget : enemies.get(0);
            }
            default -> // "Random" or unknown
                enemies.get(new Random().nextInt(enemies.size()));
        };
    }
    
    /**
     * Select ability based on combat role
     */
    private Ability selectAbility(Companion companion, String role, Character target, List<Enemy> enemies) {
        List<Ability> readyAbilities = companion.getAbilities() != null ?
            companion.getAbilities().stream()
                .filter(a -> a.isReady() && companion.canUseAbility(a))
                .collect(Collectors.toList()) :
            Collections.emptyList();
        
        if (readyAbilities.isEmpty()) return null;
        
        return switch (role != null ? role : "Balanced") {
            case "Tank" -> selectTankAbility(readyAbilities, companion);
            case "DPS" -> selectDPSAbility(readyAbilities, target);
            case "Support" -> selectSupportAbility(readyAbilities, companion, getAlivePlayerParty());
            default -> selectBalancedAbility(readyAbilities, companion, target);
        };
    }
    
    /**
     * Tank role: Prefer defensive/self-buff abilities
     */
    private Ability selectTankAbility(List<Ability> abilities, Companion self) {
        // Prioritize defensive abilities (self-buffs, barriers)
        for (Ability ability : abilities) {
            if (ability.getTargetType() == AbilityTarget.SELF && !ability.getStatusEffects().isEmpty()) {
                return ability;
            }
            if (ability.getTargetType() == AbilityTarget.ALL_ALLIES && !ability.getStatusEffects().isEmpty()) {
                return ability;
            }
        }
        // Fall back to highest power attack
        return abilities.stream()
            .max(Comparator.comparingDouble(Ability::getBasePower))
            .orElse(null);
    }
    
    /**
     * DPS role: Prefer highest damage abilities
     */
    private Ability selectDPSAbility(List<Ability> abilities, Character target) {
        return abilities.stream()
            .filter(a -> a.getDamageType() != DamageType.HEALING)
            .max(Comparator.comparingDouble(Ability::getBasePower))
            .orElse(abilities.get(0));
    }
    
    /**
     * Support role: Prefer healing/buffing abilities
     */
    private Ability selectSupportAbility(List<Ability> abilities, Companion self, List<Character> allies) {
        // Check if any ally is below 50% HP - heal them
        boolean allyNeedsHealing = allies.stream()
            .anyMatch(a -> a.getHPPercentage() < 50);
        
        if (allyNeedsHealing) {
            Ability heal = abilities.stream()
                .filter(a -> a.getDamageType() == DamageType.HEALING)
                .findFirst()
                .orElse(null);
            if (heal != null) return heal;
        }
        
        // Otherwise, use a buff ability
        Ability buff = abilities.stream()
            .filter(a -> a.getTargetType() == AbilityTarget.ALL_ALLIES)
            .findFirst()
            .orElse(null);
        if (buff != null) return buff;
        
        // Fall back to any available ability
        return abilities.get(0);
    }
    
    /**
     * Balanced role: Mix of offense and defense
     */
    private Ability selectBalancedAbility(List<Ability> abilities, Companion self, Character target) {
        // If self HP is low, prefer healing
        if (self.getHPPercentage() < 30) {
            Ability heal = abilities.stream()
                .filter(a -> a.getDamageType() == DamageType.HEALING)
                .findFirst()
                .orElse(null);
            if (heal != null) return heal;
        }
        
        // Otherwise use the highest power ability available
        return abilities.stream()
            .max(Comparator.comparingDouble(Ability::getBasePower))
            .orElse(null);
    }
    
    /**
     * Execute a basic attack
     */
    private List<TurnEvent> executeBasicAttack(Character attacker, Character target) {
        List<TurnEvent> events = new ArrayList<>();
        
        // Check for elemental combo
        ElementalComboSystem.ComboResult comboResult = comboSystem.applyElementalDamage(
            target, attacker.getElementAffinity(), 50);
        
        if (comboResult.isCombo()) {
            events.add(new TurnEvent(TurnEvent.Type.COMBO, attacker, target,
                "  " + comboResult.getMessage()));
        }
        
        // Calculate damage
        DamageCalculator.DamageResult damageResult = damageCalculator.calculateDamage(
            attacker, target, 1.5, DamageType.PHYSICAL, attacker.getElementAffinity());
        
        if (damageResult.isMiss()) {
            events.add(new TurnEvent(TurnEvent.Type.MISS, attacker, target,
                "  " + damageResult.getMessage()));
            return events;
        }
        
        // Apply damage (including combo bonus)
        int totalDamage = damageResult.getDamage() + (int) comboResult.getBonusDamage();
        int actualDamage = target.takeDamage(totalDamage);
        
        events.add(new TurnEvent(TurnEvent.Type.DAMAGE, attacker, target,
            "  " + damageResult.getMessage() + " -> " + target.getName() + 
            " HP: " + target.getCurrentHP() + "/" + target.getMaxHP()));
        
        // Check if target died
        if (!target.isAlive()) {
            events.add(new TurnEvent(TurnEvent.Type.DEATH, target, null,
                "  " + target.getName() + " has been defeated!"));
        }
        
        return events;
    }
    
    /**
     * Execute an ability
     */
    private List<TurnEvent> executeAbility(Character attacker, Character target, Ability ability) {
        List<TurnEvent> events = new ArrayList<>();
        
        events.add(new TurnEvent(TurnEvent.Type.ABILITY_USE, attacker, target,
            "  Uses " + ability.getName() + "!"));
        
        // Check for elemental combo
        ElementalComboSystem.ComboResult comboResult = comboSystem.applyElementalDamage(
            target, ability.getElement(), ability.getBasePower() * 50);
        
        if (comboResult.isCombo()) {
            events.add(new TurnEvent(TurnEvent.Type.COMBO, attacker, target,
                "    " + comboResult.getMessage()));
        }
        
        // Calculate damage
        DamageCalculator.DamageResult damageResult = damageCalculator.calculateDamage(
            attacker, target, ability.getBasePower(), ability.getDamageType(), ability.getElement());
        
        if (damageResult.isMiss()) {
            events.add(new TurnEvent(TurnEvent.Type.MISS, attacker, target,
                "    " + damageResult.getMessage()));
            ability.use();
            return events;
        }
        
        // Apply damage
        int totalDamage = damageResult.getDamage() + (int) comboResult.getBonusDamage();
        target.takeDamage(totalDamage);
        
        events.add(new TurnEvent(TurnEvent.Type.DAMAGE, attacker, target,
            "    " + damageResult.getMessage() + " -> " + target.getName() + 
            " HP: " + target.getCurrentHP() + "/" + target.getMaxHP()));
        
        // Apply status effects
        for (Ability.StatusApplication statusApp : ability.getStatusEffects()) {
            if (new Random().nextInt(100) < statusApp.getChance()) {
                StatusEffect status = new StatusEffect(
                    statusApp.getType(), 
                    statusApp.getDuration(), 
                    statusApp.getPotency()
                );
                target.addStatusEffect(status);
                
                events.add(new TurnEvent(TurnEvent.Type.STATUS_APPLY, attacker, target,
                    "    Applied " + statusApp.getType().getDisplayName() + "!"));
            }
        }
        
        // Check if target died
        if (!target.isAlive()) {
            events.add(new TurnEvent(TurnEvent.Type.DEATH, target, null,
                "    " + target.getName() + " has been defeated!"));
        }
        
        // Use ability (start cooldown)
        ability.use();
        
        return events;
    }
    
    /**
     * Check if battle has ended
     */
    private boolean checkBattleEnd() {
        boolean allPlayersDead = playerParty.stream().noneMatch(Character::isAlive);
        boolean allEnemiesDead = enemies.stream().noneMatch(Character::isAlive);
        
        if (allPlayersDead) {
            result = new BattleResult(false, "Defeat! All party members have fallen...");
            return true;
        }
        
        if (allEnemiesDead) {
            result = new BattleResult(true, "Victory! All enemies defeated!");
            return true;
        }
        
        return false;
    }
    
    /**
     * Get alive player party members
     */
    private List<Character> getAlivePlayerParty() {
        return playerParty.stream()
            .filter(Character::isAlive)
            .collect(Collectors.toList());
    }
    
    /**
     * Get alive enemies
     */
    private List<Enemy> getAliveEnemies() {
        return enemies.stream()
            .filter(Character::isAlive)
            .collect(Collectors.toList());
    }
    
    // Getters
    public boolean isBattleEnded() { return battleEnded; }
    public BattleResult getResult() { return result; }
    public int getCurrentTurn() { return currentTurn; }
    public List<Character> getPlayerParty() { return new ArrayList<>(playerParty); }
    public List<Enemy> getEnemies() { return new ArrayList<>(enemies); }
    
    /**
     * Represents one combatant's turn
     */
    private static class CombatantTurn {
        Character combatant;
        boolean isEnemy;
        
        CombatantTurn(Character combatant, boolean isEnemy) {
            this.combatant = combatant;
            this.isEnemy = isEnemy;
        }
    }
    
    /**
     * Event that happens during combat
     */
    public static class TurnEvent {
        public enum Type {
            TURN_START, ACTION, DAMAGE, MISS, COMBO, STATUS_APPLY, 
            STATUS_DAMAGE, ABILITY_USE, HP_TRIGGER, DEATH, SKIP_TURN, BATTLE_END
        }
        
        private final Type type;
        private final Character actor;
        private final Character target;
        private final String message;
        
        public TurnEvent(Type type, Character actor, Character target, String message) {
            this.type = type;
            this.actor = actor;
            this.target = target;
            this.message = message;
        }
        
        public Type getType() { return type; }
        public Character getActor() { return actor; }
        public Character getTarget() { return target; }
        public String getMessage() { return message; }
    }
    
    /**
     * Result of a battle
     */
    public static class BattleResult {
        private final boolean victory;
        private final String message;
        
        public BattleResult(boolean victory, String message) {
            this.victory = victory;
            this.message = message;
        }
        
        public boolean isVictory() { return victory; }
        public String getMessage() { return message; }
    }
    
    /**
     * Decision made by companion AI
     */
    private static class CompanionAIDecision {
        final Ability ability;
        final Character target;
        final String reasoning;
        
        CompanionAIDecision(Ability ability, Character target, String reasoning) {
            this.ability = ability;
            this.target = target;
            this.reasoning = reasoning;
        }
    }
}
