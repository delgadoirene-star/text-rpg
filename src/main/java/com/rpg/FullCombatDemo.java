package com.rpg;

import com.rpg.combat.*;
import com.rpg.models.Stats;
import com.rpg.models.Element;
import com.rpg.models.Character;
import com.rpg.models.Enemy;
import com.rpg.models.StatusType;

import java.util.ArrayList;
import java.util.List;

/**
 * Full combat demo - complete battle simulation
 */
public class FullCombatDemo {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          TEXT RPG - FULL COMBAT DEMONSTRATION                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        // Run battles with different difficulties
        runBattle("STORY MODE", Difficulty.STORY);
        
        System.out.println("\n\n");
        
        runBattle("NORMAL MODE", Difficulty.NORMAL);
        
        System.out.println("\n\n");
        
        runBattle("HARD MODE", Difficulty.HARD);
    }
    
    private static void runBattle(String difficultyName, Difficulty difficulty) {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  BATTLE: " + difficultyName);
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Create player party
        List<Character> playerParty = createPlayerParty();
        
        System.out.println("PLAYER PARTY:");
        for (Character character : playerParty) {
            System.out.println("  " + character.toString());
            System.out.println("    Element: " + character.getElementAffinity());
            System.out.println("    ATK: " + String.format("%.1f", character.getPhysicalAttack()) + 
                             " | DEF: " + String.format("%.1f", character.getPhysicalDefense()) +
                             " | SPD: " + String.format("%.1f", character.getSpeed()));
        }
        
        // Create enemies
        List<Enemy> enemies = createEnemies();
        
        System.out.println("\nENEMIES:");
        for (Enemy enemy : enemies) {
            System.out.println("  " + enemy.toString());
            System.out.println("    Element: " + enemy.getElementAffinity() + 
                             " | AI: " + enemy.getAIPattern().getDisplayName());
            System.out.println("    ATK: " + String.format("%.1f", enemy.getPhysicalAttack()) + 
                             " | DEF: " + String.format("%.1f", enemy.getPhysicalDefense()) +
                             " | SPD: " + String.format("%.1f", enemy.getSpeed()));
        }
        
        // Initialize combat
        CombatEngine combat = new CombatEngine(difficulty);
        combat.initializeBattle(playerParty, enemies);
        
        System.out.println("\n" + "=".repeat(63));
        System.out.println("BATTLE START!");
        System.out.println("=".repeat(63) + "\n");
        
        // Battle loop
        int maxTurns = 15; // Prevent infinite loops
        int turnCount = 0;
        
        while (!combat.isBattleEnded() && turnCount < maxTurns) {
            List<CombatEngine.TurnEvent> events = combat.processTurn();
            
            for (CombatEngine.TurnEvent event : events) {
                System.out.println(event.getMessage());
            }
            
            System.out.println();
            turnCount++;
            
            // Small delay between turns for readability
            if (!combat.isBattleEnded()) {
                System.out.println("-".repeat(63) + "\n");
            }
        }
        
        // Battle result
        if (combat.isBattleEnded()) {
            CombatEngine.BattleResult result = combat.getResult();
            System.out.println("=".repeat(63));
            System.out.println(result.getMessage());
            System.out.println("=".repeat(63));
            
            // Show final party status
            System.out.println("\nFINAL STATUS:");
            System.out.println("Player Party:");
            for (Character character : combat.getPlayerParty()) {
                String status = character.isAlive() ? 
                    "HP: " + character.getCurrentHP() + "/" + character.getMaxHP() : 
                    "DEFEATED";
                System.out.println("  " + character.getName() + " - " + status);
            }
            
            System.out.println("\nEnemies:");
            for (Enemy enemy : combat.getEnemies()) {
                String status = enemy.isAlive() ? 
                    "HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() : 
                    "DEFEATED";
                System.out.println("  " + enemy.getName() + " - " + status);
            }
        } else {
            System.out.println("Battle reached maximum turns (" + maxTurns + ")");
        }
    }
    
    /**
     * Create a balanced player party
     */
    private static List<Character> createPlayerParty() {
        List<Character> party = new ArrayList<>();
        
        // Warrior - Fire element, high STR/VIT
        Stats warriorStats = new Stats(20, 14, 18, 8, 10, 12);
        Character warrior = new TestCharacter("warrior", "Aria the Warrior", 5, warriorStats, Element.FIRE);
        party.add(warrior);
        
        // Mage - Water element, high INT/WIS
        Stats mageStats = new Stats(8, 12, 12, 22, 18, 10);
        Character mage = new TestCharacter("mage", "Zephyr the Mage", 5, mageStats, Element.WATER);
        party.add(mage);
        
        // Rogue - Wind element, high DEX/LUK
        Stats rogueStats = new Stats(14, 20, 14, 10, 10, 18);
        Character rogue = new TestCharacter("rogue", "Silva the Rogue", 5, rogueStats, Element.WIND);
        party.add(rogue);
        
        return party;
    }
    
    /**
     * Create enemy party
     */
    private static List<Enemy> createEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        
        // Goblin Warrior - Earth element, aggressive (BUFFED STATS)
        Stats goblinStats = new Stats(18, 14, 22, 10, 10, 12);
        Enemy goblin = new Enemy("goblin_warrior", "Goblin Warrior", 5, 
                                goblinStats, Element.EARTH, Enemy.AIPattern.AGGRESSIVE);
        
        // Add abilities
        Ability slash = new Ability.Builder("slash", "Slash", 1)
            .description("Basic slash attack")
            .basePower(1.5)
            .element(Element.EARTH)
            .build();
        
        Ability powerSlash = new Ability.Builder("power_slash", "Power Slash", 2)
            .description("Powerful overhead strike")
            .basePower(3.0)
            .element(Element.EARTH)
            .cooldown(3)
            .addStatusEffect(StatusType.DEF_DOWN, 2, 20.0, 50)
            .build();
        
        goblin.addAbility(slash);
        goblin.addAbility(powerSlash);
        
        // Add HP trigger
        goblin.addHPTrigger(50.0, 
            () -> System.out.println("  >>> Goblin Warrior becomes enraged!"),
            "Enrage");
        
        enemies.add(goblin);
        
        // Dark Shaman - Dark element, tactical (BUFFED STATS)
        Stats shamanStats = new Stats(12, 12, 18, 22, 20, 14);
        Enemy shaman = new Enemy("dark_shaman", "Dark Shaman", 6, 
                                shamanStats, Element.DARK, Enemy.AIPattern.TACTICAL);
        
        Ability darkBolt = new Ability.Builder("dark_bolt", "Dark Bolt", 1)
            .description("Bolt of dark energy")
            .basePower(2.2)
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .build();
        
        Ability curse = new Ability.Builder("curse", "Curse", 2)
            .description("Inflict curse on target")
            .basePower(1.5)
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .cooldown(4)
            .addStatusEffect(StatusType.CURSE, 4, 20.0, 90)
            .addStatusEffect(StatusType.ATK_DOWN, 3, 30.0, 100)
            .build();
        
        shaman.addAbility(darkBolt);
        shaman.addAbility(curse);
        
        // Add HP trigger
        shaman.addHPTrigger(30.0, 
            () -> System.out.println("  >>> Dark Shaman casts protective barrier!"),
            "Protective Barrier");
        
        enemies.add(shaman);
        
        // Goblin Archer - Wind element, tactical (focuses on squishy targets)
        Stats archerStats = new Stats(14, 22, 14, 10, 10, 18);
        Enemy archer = new Enemy("goblin_archer", "Goblin Archer", 4, 
                                archerStats, Element.WIND, Enemy.AIPattern.TACTICAL);
        
        Ability quickShot = new Ability.Builder("quick_shot", "Quick Shot", 1)
            .description("Fast arrow attack")
            .basePower(1.8)
            .element(Element.WIND)
            .build();
        
        Ability piercingArrow = new Ability.Builder("piercing_arrow", "Piercing Arrow", 2)
            .description("Arrow that ignores some defense")
            .basePower(2.8)
            .element(Element.WIND)
            .cooldown(2)
            .addStatusEffect(StatusType.BLEED, 3, 15.0, 70)
            .build();
        
        archer.addAbility(quickShot);
        archer.addAbility(piercingArrow);
        
        // Add HP trigger
        archer.addHPTrigger(40.0, 
            () -> System.out.println("  >>> Goblin Archer retreats to safe distance!"),
            "Tactical Retreat");
        
        enemies.add(archer);
        
        return enemies;
    }
    
    static class TestCharacter extends Character {
        public TestCharacter(String id, String name, int level, Stats baseStats, Element element) {
            super(id, name, level, baseStats, element);
        }
    }
}
