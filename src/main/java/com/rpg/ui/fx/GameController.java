package com.rpg.ui.fx;

import com.rpg.GameState;
import com.rpg.combat.Ability;
import com.rpg.combat.DamageCalculator;
import com.rpg.combat.DamageType;
import com.rpg.combat.Difficulty;
import com.rpg.combat.ElementalComboSystem;
import com.rpg.combat.StatusEffect;
import com.rpg.models.Companion;
import com.rpg.models.CompanionFactory;
import com.rpg.models.Element;
import com.rpg.models.Enemy;
import com.rpg.models.GameClass;
import com.rpg.models.GameClassRegistry;
import com.rpg.models.Player;
import com.rpg.models.Stats;
import com.rpg.models.Background;
import com.rpg.systems.AlignmentSystem;
import com.rpg.world.Location;
import com.rpg.world.WorldMap;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Central controller for the JavaFX game interface.
 * Manages game state and coordinates between views.
 */
public class GameController {
    
    // Game state
    private GameState state;
    private Difficulty gameDifficulty = Difficulty.NORMAL;
    
    // Combat state
    private List<Enemy> currentEnemies;
    private DamageCalculator damageCalculator;
    private ElementalComboSystem comboSystem;
    private int combatTurnCount;
    
    // Player action state
    public enum PlayerAction { ATTACK, DEFEND, ABILITY, FLEE, NONE }
    private PlayerAction currentAction = PlayerAction.NONE;
    private Ability selectedAbility;
    private Enemy selectedTarget;
    
    // Battle result
    private BattleResult lastBattleResult;
    
    public static class BattleResult {
        public final boolean victory;
        public final int expGained;
        public final int goldLooted;
        public final boolean leveledUp;
        public final String summary;
        
        public BattleResult(boolean victory, int expGained, int goldLooted, boolean leveledUp, String summary) {
            this.victory = victory;
            this.expGained = expGained;
            this.goldLooted = goldLooted;
            this.leveledUp = leveledUp;
            this.summary = summary;
        }
    }
    
    public BattleResult getLastBattleResult() { return lastBattleResult; }
    
    // Observable properties for UI binding
    private final StringProperty currentScreen = new SimpleStringProperty("title");
    private final StringProperty messageLog = new SimpleStringProperty("");
    private final BooleanProperty inCombat = new SimpleBooleanProperty(false);
    private final ObservableList<String> combatLog = FXCollections.observableArrayList();
    
    // View reference
    private GameView gameView;
    
    public GameController() {
        // State will be initialized on new game
    }
    
    public void setGameView(GameView view) {
        this.gameView = view;
    }
    
    // ==================== Screen Navigation ====================
    
    public void showTitleScreen() {
        currentScreen.set("title");
        if (gameView != null) {
            gameView.showTitleScreen();
        }
    }
    
    public void showCharacterCreation() {
        currentScreen.set("character_creation");
        if (gameView != null) {
            gameView.showCharacterCreation();
        }
    }
    
    public void showExploration() {
        currentScreen.set("exploration");
        if (gameView != null) {
            gameView.showExploration();
        }
    }
    
    public void showCombat() {
        currentScreen.set("combat");
        inCombat.set(true);
        if (gameView != null) {
            gameView.showCombat();
        }
    }
    
    public void showPartyMenu() {
        currentScreen.set("party");
        if (gameView != null) {
            gameView.showPartyMenu();
        }
    }
    
    // ==================== Game Actions ====================
    
    public void setDifficulty(Difficulty difficulty) {
        this.gameDifficulty = difficulty;
    }
    
    public Difficulty getDifficulty() {
        return gameDifficulty;
    }
    
    public void startNewGame(String name, Element element, GameClass gameClass) {
        state = new GameState();
        
        Stats stats = new Stats(12, 12, 12, 12, 12, 12);
        Player player = new Player(name, Background.SOLDIER, gameClass, stats, element);
        
        for (com.rpg.combat.Ability ability : gameClass.getClassAbilities()) {
            player.addAbility(ability);
        }
        
        state.setPlayer(player);
        
        WorldMap worldMap = new WorldMap();
        worldMap.initializeWorld();
        state.setWorldMap(worldMap);
        state.setCurrentLocation(worldMap.getStartingLocation());
        
        state.getQuestManager().initializeQuests();
        registerCompanions();
        
        addMessage("Welcome, " + name + ". Your journey begins...");
        showExploration();
    }
    
    private void registerCompanions() {
        state.registerCompanion(CompanionFactory.createIsolde(GameClassRegistry.createPaladinClass()));
        state.registerCompanion(CompanionFactory.createBram(GameClassRegistry.createRogueClass()));
        state.registerCompanion(CompanionFactory.createSybilla(GameClassRegistry.createScholarClass()));
        state.registerCompanion(CompanionFactory.createGarrick(GameClassRegistry.createWarriorClass()));
        state.registerCompanion(CompanionFactory.createOona(GameClassRegistry.createGuardianClass()));
        state.registerCompanion(CompanionFactory.createTheron(GameClassRegistry.createMonkClass()));
        state.registerCompanion(CompanionFactory.createSash(GameClassRegistry.createSpyClass()));
        state.registerCompanion(CompanionFactory.createGhor(GameClassRegistry.createBrawlerClass()));
        state.registerCompanion(CompanionFactory.createVek(GameClassRegistry.createArcanistClass()));
        state.registerCompanion(CompanionFactory.createMaeva(GameClassRegistry.createShamanClass()));
        state.registerCompanion(CompanionFactory.createSilas(GameClassRegistry.createPriestClass()));
    }
    
    public void travel(String direction) {
        if (state == null) return;
        
        Location current = state.getCurrentLocation();
        Location destination = state.getWorldMap().travel(current, direction);
        
        if (destination != null) {
            if (destination.canEnter(state.getFlagManager().getAllFlags())) {
                state.setCurrentLocation(destination);
                state.advanceDay();
                addMessage("Traveled to " + destination.getName());
                showExploration();
            } else {
                addMessage("You cannot enter this area yet.");
            }
        }
    }
    
    public void rest() {
        if (state == null) return;
        state.healParty();
        state.advanceDay();
        addMessage("Your party rests and recovers.");
    }
    
    // ==================== Combat System ====================
    
    public void startCombat(List<Enemy> enemies) {
        if (state == null) return;
        
        currentEnemies = new ArrayList<>(enemies);
        damageCalculator = new DamageCalculator(gameDifficulty);
        comboSystem = new ElementalComboSystem();
        combatTurnCount = 0;
        currentAction = PlayerAction.NONE;
        selectedAbility = null;
        selectedTarget = null;
        
        combatLog.clear();
        combatLog.add("=== Combat begins! ===");
        for (Enemy e : currentEnemies) {
            combatLog.add("- " + e.getName() + " (Lv." + e.getLevel() + ")");
        }
        combatLog.add("Select your action...");
        
        inCombat.set(true);
        showCombat();
    }
    
    // Player action buttons
    public void playerAttack(Enemy target) {
        if (!inCombat.get()) return;
        currentAction = PlayerAction.ATTACK;
        selectedAbility = null;
        selectedTarget = target;
        combatLog.add("You target " + target.getName() + " with a basic attack.");
        resolvePlayerAndEnemyTurn();
    }
    
    public void playerUseAbility(Ability ability, Enemy target) {
        if (!inCombat.get()) return;
        currentAction = PlayerAction.ABILITY;
        selectedAbility = ability;
        selectedTarget = target;
        combatLog.add("You prepare to use " + ability.getName() + " on " + target.getName() + ".");
        resolvePlayerAndEnemyTurn();
    }
    
    public void playerDefend() {
        if (!inCombat.get()) return;
        currentAction = PlayerAction.DEFEND;
        selectedAbility = null;
        selectedTarget = null;
        combatLog.add("You take a defensive stance.");
        resolvePlayerAndEnemyTurn();
    }
    
    public void playerFlee() {
        if (!inCombat.get()) return;
        
        int fleeChance = 50 + (state.getPlayer().getBaseStats().getDexterity() * 3);
        if (new Random().nextInt(100) < fleeChance) {
            combatLog.add("You successfully flee from battle!");
            endCombat(false);
        } else {
            combatLog.add("You failed to escape!");
            resolvePlayerAndEnemyTurn();
        }
    }
    
    private void resolvePlayerAndEnemyTurn() {
        combatTurnCount++;
        combatLog.add("--- Turn " + combatTurnCount + " ---");
        
        // 1. Execute player action
        executePlayerAction();
        
        // 2. Check if battle ended
        if (checkBattleEnd()) return;
        
        // 3. Execute companion turns
        executeCompanionTurns();
        
        // 4. Check if battle ended
        if (checkBattleEnd()) return;
        
        // 5. Execute enemy turns
        executeEnemyTurns();
        
        // 6. Check if battle ended
        if (checkBattleEnd()) return;
        
        // 7. Refresh party status bar
        if (gameView != null) {
            gameView.updatePartyStatus();
        }
        
        // 8. Refresh combat view
        if (gameView != null) {
            gameView.refreshCombat();
        }
        
        // Reset action for next turn
        currentAction = PlayerAction.NONE;
        selectedAbility = null;
        selectedTarget = null;
    }
    
    private void executePlayerAction() {
        Player player = state.getPlayer();
        if (player == null || !player.isAlive()) return;
        
        switch (currentAction) {
            case ATTACK -> {
                if (selectedTarget != null && selectedTarget.isAlive()) {
                    executeBasicAttack(player, selectedTarget);
                } else {
                    Enemy target = getRandomAliveEnemy();
                    if (target != null) executeBasicAttack(player, target);
                }
            }
            case ABILITY -> {
                if (selectedAbility != null) {
                    // Spend focus for the player
                    player.spendFocus(selectedAbility);
                    if (selectedTarget != null && selectedTarget.isAlive()) {
                        executeAbility(player, selectedTarget, selectedAbility);
                    } else {
                        Enemy target = getRandomAliveEnemy();
                        if (target != null) executeAbility(player, target, selectedAbility);
                    }
                }
            }
            case DEFEND -> {
                combatLog.add("  " + player.getName() + " defends. (Damage reduced this turn)");
            }
            default -> {
                Enemy target = getRandomAliveEnemy();
                if (target != null) executeBasicAttack(player, target);
            }
        }
        
        // Process status effects on player
        double tickDmg = player.processStatusEffects();
        if (tickDmg > 0) {
            combatLog.add("  " + player.getName() + " takes " + String.format("%.0f", tickDmg) + " DoT damage.");
        }
    }
    
    private void executeCompanionTurns() {
        for (com.rpg.models.Character c : state.getCombatParty()) {
            if (c instanceof Companion companion && companion.isAlive() && c != state.getPlayer()) {
                Enemy target = getRandomAliveEnemy();
                if (target == null) break;
                executeBasicAttack(companion, target);
            }
        }
    }
    
    private void executeEnemyTurns() {
        for (Enemy enemy : currentEnemies) {
            if (!enemy.isAlive()) continue;
            
            // Process status effects
            double tickDmg = enemy.processStatusEffects();
            if (tickDmg > 0) {
                combatLog.add("  " + enemy.getName() + " takes " + String.format("%.0f", tickDmg) + " DoT damage.");
            }
            if (!enemy.isAlive()) {
                combatLog.add("  " + enemy.getName() + " has been defeated!");
                continue;
            }
            
            // Enemy acts
            List<com.rpg.models.Character> aliveParty = getAliveParty();
            if (aliveParty.isEmpty()) break;
            
            Enemy.AIDecision decision = enemy.getAIDecision(aliveParty);
            com.rpg.models.Character target = decision.getTarget();
            
            if (decision.getAbility() != null && decision.getAbility().isReady()) {
                executeAbilityEnemy(enemy, target, decision.getAbility());
            } else {
                executeBasicAttackEnemy(enemy, target);
            }
        }
    }
    
    private void executeBasicAttack(com.rpg.models.Character attacker, com.rpg.models.Character target) {
        ElementalComboSystem.ComboResult combo = comboSystem.applyElementalDamage(
            target, attacker.getElementAffinity(), 50);
        
        DamageCalculator.DamageResult dr = damageCalculator.calculateDamage(
            attacker, target, 1.5, DamageType.PHYSICAL, attacker.getElementAffinity());
        
        if (dr.isMiss()) {
            combatLog.add("  " + attacker.getName() + " attacks " + target.getName() + " but misses!");
            return;
        }
        
        int totalDmg = dr.getDamage() + (int) combo.getBonusDamage();
        int actual = target.takeDamage(totalDmg);
        
        String msg = "  " + attacker.getName() + " attacks " + target.getName() + " for " + actual + " damage!";
        if (combo.isCombo()) msg += " [COMBO!] ";
        combatLog.add(msg);
        combatLog.add("    " + target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());
        
        if (!target.isAlive()) {
            combatLog.add("    " + target.getName() + " has been defeated!");
        }
    }
    
    private void executeAbility(com.rpg.models.Character attacker, com.rpg.models.Character target, Ability ability) {
        combatLog.add("  " + attacker.getName() + " uses " + ability.getName() + "!");
        
        ElementalComboSystem.ComboResult combo = comboSystem.applyElementalDamage(
            target, ability.getElement(), ability.getBasePower() * 50);
        
        DamageCalculator.DamageResult dr = damageCalculator.calculateDamage(
            attacker, target, ability.getBasePower(), ability.getDamageType(), ability.getElement());
        
        if (dr.isMiss()) {
            combatLog.add("    Miss!");
            ability.use();
            return;
        }
        
        int totalDmg = dr.getDamage() + (int) combo.getBonusDamage();
        target.takeDamage(totalDmg);
        
        String msg = "    Deals " + totalDmg + " damage!";
        if (combo.isCombo()) msg += " [COMBO!]";
        combatLog.add(msg);
        combatLog.add("    " + target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());
        
        // Apply status effects
        for (Ability.StatusApplication sa : ability.getStatusEffects()) {
            if (new Random().nextInt(100) < sa.getChance()) {
                StatusEffect se = new StatusEffect(sa.getType(), sa.getDuration(), sa.getPotency());
                target.addStatusEffect(se);
                combatLog.add("    Applied " + sa.getType().getDisplayName() + "!");
            }
        }
        
        if (!target.isAlive()) {
            combatLog.add("    " + target.getName() + " has been defeated!");
        }
        
        ability.use();
    }
    
    private void executeBasicAttackEnemy(Enemy attacker, com.rpg.models.Character target) {
        ElementalComboSystem.ComboResult combo = comboSystem.applyElementalDamage(
            target, attacker.getElementAffinity(), 50);
        
        DamageCalculator.DamageResult dr = damageCalculator.calculateDamage(
            attacker, target, 1.5, DamageType.PHYSICAL, attacker.getElementAffinity());
        
        if (dr.isMiss()) {
            combatLog.add("  " + attacker.getName() + " attacks " + target.getName() + " but misses!");
            return;
        }
        
        int totalDmg = dr.getDamage() + (int) combo.getBonusDamage();
        int actual = target.takeDamage(totalDmg);
        
        combatLog.add("  " + attacker.getName() + " attacks " + target.getName() + " for " + actual + " damage!");
        combatLog.add("    " + target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());
        
        if (!target.isAlive()) {
            combatLog.add("    " + target.getName() + " has been defeated!");
        }
    }
    
    private void executeAbilityEnemy(Enemy attacker, com.rpg.models.Character target, Ability ability) {
        combatLog.add("  " + attacker.getName() + " uses " + ability.getName() + "!");
        
        DamageCalculator.DamageResult dr = damageCalculator.calculateDamage(
            attacker, target, ability.getBasePower(), ability.getDamageType(), ability.getElement());
        
        if (dr.isMiss()) {
            combatLog.add("    Miss!");
            ability.use();
            return;
        }
        
        int totalDmg = dr.getDamage();
        target.takeDamage(totalDmg);
        
        combatLog.add("    Deals " + totalDmg + " damage!");
        combatLog.add("    " + target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());
        
        for (Ability.StatusApplication sa : ability.getStatusEffects()) {
            if (new Random().nextInt(100) < sa.getChance()) {
                StatusEffect se = new StatusEffect(sa.getType(), sa.getDuration(), sa.getPotency());
                target.addStatusEffect(se);
                combatLog.add("    Applied " + sa.getType().getDisplayName() + "!");
            }
        }
        
        if (!target.isAlive()) {
            combatLog.add("    " + target.getName() + " has been defeated!");
        }
        
        ability.use();
    }
    
    private boolean checkBattleEnd() {
        boolean allDead = getAliveParty().isEmpty();
        boolean enemiesDead = getAliveEnemies().isEmpty();
        
        if (allDead) {
            combatLog.add("=== Defeat! Your party has fallen... ===");
            state.healParty();
            lastBattleResult = new BattleResult(false, 0, 0, false,
                "Your party has fallen! HP has been restored.");
            endCombat(false);
            return true;
        }
        if (enemiesDead) {
            int expGain = 100 * currentEnemies.size();
            int goldGain = 25 + new Random().nextInt(50) * currentEnemies.size();
            boolean leveledUp = state.getPlayer().gainExperience(expGain);
            state.addGold(goldGain);
            
            combatLog.add("=== Victory! ===");
            combatLog.add("EXP gained: " + expGain);
            combatLog.add("Gold looted: " + goldGain);
            if (leveledUp) {
                combatLog.add("*** LEVEL UP! You are now level " + state.getPlayer().getLevel() + "! ***");
                combatLog.add("You have " + state.getPlayer().getAvailableStatPoints() + " stat points to allocate.");
            }
            
            String summary = "Victory! +" + expGain + " EXP, +" + goldGain + " Gold";
            if (leveledUp) {
                summary += "\n*** LEVEL UP! ***\nYou have " + state.getPlayer().getAvailableStatPoints() + " stat points to allocate.";
            }
            lastBattleResult = new BattleResult(true, expGain, goldGain, leveledUp, summary);
            endCombat(false);
            return true;
        }
        return false;
    }
    
    private void endCombat(boolean flee) {
        inCombat.set(false);
        if (!flee && gameView != null) {
            gameView.refreshCombat();
        }
    }
    
    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        if (currentEnemies != null) {
            for (Enemy e : currentEnemies) {
                if (e.isAlive()) alive.add(e);
            }
        }
        return alive;
    }
    
    public Enemy getRandomAliveEnemy() {
        List<Enemy> alive = getAliveEnemies();
        if (alive.isEmpty()) return null;
        return alive.get(new Random().nextInt(alive.size()));
    }
    
    public List<com.rpg.models.Character> getAliveParty() {
        List<com.rpg.models.Character> alive = new ArrayList<>();
        if (state != null && state.getCombatParty() != null) {
            for (com.rpg.models.Character c : state.getCombatParty()) {
                if (c.isAlive()) alive.add(c);
            }
        }
        return alive;
    }
    
    public List<Enemy> getCurrentEnemies() { return currentEnemies; }
    
    // ==================== Message System ====================
    
    public void addMessage(String message) {
        String current = messageLog.get();
        if (current.isEmpty()) {
            messageLog.set(message);
        } else {
            messageLog.set(current + "\n" + message);
        }
    }
    
    public void clearMessages() {
        messageLog.set("");
    }
    
    public void showDialogue(String speakerName, String text, String[] labels, Runnable[] actions) {
        if (gameView != null) {
            gameView.showDialogue(speakerName, text, labels, actions);
        }
    }
    
    // ==================== Getters ====================
    
    public GameState getState() { return state; }
    public Player getPlayer() { return state != null ? state.getPlayer() : null; }
    public Location getCurrentLocation() { return state != null ? state.getCurrentLocation() : null; }
    public AlignmentSystem getAlignment() { return state != null ? state.getAlignmentSystem() : null; }
    
    public StringProperty currentScreenProperty() { return currentScreen; }
    public StringProperty messageLogProperty() { return messageLog; }
    public BooleanProperty inCombatProperty() { return inCombat; }
    public ObservableList<String> getCombatLog() { return combatLog; }
    
    public boolean isInCombat() { return inCombat.get(); }
    
    public void shutdown() {
    }
}
