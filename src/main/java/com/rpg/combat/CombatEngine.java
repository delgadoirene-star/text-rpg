package com.rpg.combat;

import com.rpg.models.Character;
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
     * Execute player turn (for now, just basic attack on random enemy)
     */
    private List<TurnEvent> executePlayerTurn(Character player) {
        List<TurnEvent> events = new ArrayList<>();
        
        // Select random alive enemy
        List<Enemy> aliveEnemies = getAliveEnemies();
        if (aliveEnemies.isEmpty()) {
            return events;
        }
        
        Enemy target = aliveEnemies.get(new Random().nextInt(aliveEnemies.size()));
        
        events.add(new TurnEvent(TurnEvent.Type.ACTION, player, target,
            player.getName() + " attacks " + target.getName() + "!"));
        
        events.addAll(executeBasicAttack(player, target));
        
        return events;
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
}
