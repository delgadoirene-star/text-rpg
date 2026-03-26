package com.rpg;

import com.rpg.combat.*;
import com.rpg.models.Stats;
import com.rpg.models.Element;
import com.rpg.models.Character;
import com.rpg.models.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Test Enemy AI and Elemental Combo systems
 */
public class AIAndComboTest {
    
    public static void main(String[] args) {
        System.out.println("=== AI & ELEMENTAL COMBO TEST ===\n");
        
        // Test 1: Enemy AI Patterns
        testEnemyAI();
        
        // Test 2: Elemental Combos
        testElementalCombos();
        
        // Test 3: HP Triggers
        testHPTriggers();
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
    
    private static void testEnemyAI() {
        System.out.println("\n--- Test 1: Enemy AI Patterns ---");
        
        // Create player party
        List<Character> playerParty = new ArrayList<>();
        Stats warriorStats = new Stats(18, 12, 20, 8, 10, 12);
        Stats mageStats = new Stats(8, 10, 12, 20, 16, 10);
        
        Character warrior = new TestCharacter("warrior", "Warrior", 5, warriorStats, Element.FIRE);
        Character mage = new TestCharacter("mage", "Mage", 5, mageStats, Element.WATER);
        warrior.setCurrentHP(80); // Damage the warrior
        
        playerParty.add(warrior);
        playerParty.add(mage);
        
        System.out.println("Player Party:");
        System.out.println("  " + warrior.toString());
        System.out.println("  " + mage.toString());
        
        // Create enemies with different AI patterns
        Enemy.AIPattern[] patterns = Enemy.AIPattern.values();
        
        for (Enemy.AIPattern pattern : patterns) {
            System.out.println("\n" + pattern.getDisplayName() + " AI:");
            System.out.println("  " + pattern.getDescription());
            
            Stats enemyStats = new Stats(15, 12, 15, 12, 12, 10);
            Enemy enemy = new Enemy("enemy_" + pattern.name().toLowerCase(), 
                                   pattern.getDisplayName() + " Goblin", 
                                   4, enemyStats, Element.EARTH, pattern);
            
            // Add some abilities
            Ability attack = new Ability.Builder("slash", "Slash", 1)
                .description("Basic attack")
                .basePower(1.5)
                .element(Element.EARTH)
                .build();
            
            Ability powerAttack = new Ability.Builder("power_slash", "Power Slash", 2)
                .description("Powerful attack")
                .basePower(2.5)
                .element(Element.EARTH)
                .cooldown(2)
                .build();
            
            enemy.addAbility(attack);
            enemy.addAbility(powerAttack);
            
            // Get AI decision
            Enemy.AIDecision decision = enemy.getAIDecision(playerParty);
            System.out.println("  Decision: " + decision.getReasoning());
            System.out.println("  Ability: " + decision.getAbility().getName());
            System.out.println("  Target: " + decision.getTarget().getName());
        }
        
        System.out.println("===============================================");
    }
    
    private static void testElementalCombos() {
        System.out.println("\n--- Test 2: Elemental Combo System ---");
        
        ElementalComboSystem comboSystem = new ElementalComboSystem();
        
        // Create a target
        Stats targetStats = new Stats(12, 10, 15, 10, 10, 10);
        TestCharacter target = new TestCharacter("target", "Training Dummy", 3, targetStats, Element.NEUTRAL);
        
        System.out.println("Target: " + target.toString());
        System.out.println("\nTesting various elemental combos:\n");
        
        // Test 1: Fire + Water = Vaporize
        System.out.println("1. Applying FIRE...");
        ElementalComboSystem.ComboResult result1 = comboSystem.applyElementalDamage(target, Element.FIRE, 50);
        System.out.println("   " + result1.getMessage());
        System.out.println("   Active auras: " + comboSystem.getAuras(target));
        
        System.out.println("\n   Then applying WATER...");
        ElementalComboSystem.ComboResult result2 = comboSystem.applyElementalDamage(target, Element.WATER, 50);
        System.out.println("   " + result2.getMessage());
        if (result2.isCombo()) {
            System.out.println("   Bonus Damage: " + String.format("%.0f", result2.getBonusDamage()));
        }
        System.out.println("   Active auras: " + comboSystem.getAuras(target));
        
        // Test 2: Earth + Fire = Melt
        System.out.println("\n2. Applying EARTH...");
        ElementalComboSystem.ComboResult result3 = comboSystem.applyElementalDamage(target, Element.EARTH, 50);
        System.out.println("   " + result3.getMessage());
        
        System.out.println("\n   Then applying FIRE...");
        ElementalComboSystem.ComboResult result4 = comboSystem.applyElementalDamage(target, Element.FIRE, 50);
        System.out.println("   " + result4.getMessage());
        if (result4.isCombo()) {
            System.out.println("   Bonus Damage: " + String.format("%.0f", result4.getBonusDamage()));
            if (result4.getAppliedStatus() != null) {
                System.out.println("   Status Applied: " + result4.getAppliedStatus().toString());
            }
        }
        
        // Test 3: Light + Dark = Annihilation
        comboSystem.clear();
        System.out.println("\n3. Applying LIGHT...");
        ElementalComboSystem.ComboResult result5 = comboSystem.applyElementalDamage(target, Element.LIGHT, 50);
        System.out.println("   " + result5.getMessage());
        
        System.out.println("\n   Then applying DARK...");
        ElementalComboSystem.ComboResult result6 = comboSystem.applyElementalDamage(target, Element.DARK, 50);
        System.out.println("   " + result6.getMessage());
        if (result6.isCombo()) {
            System.out.println("   Bonus Damage: " + String.format("%.0f", result6.getBonusDamage()));
        }
        
        // Test 4: Water + Light = Freeze
        comboSystem.clear();
        System.out.println("\n4. Applying WATER...");
        ElementalComboSystem.ComboResult result7 = comboSystem.applyElementalDamage(target, Element.WATER, 50);
        System.out.println("   " + result7.getMessage());
        
        System.out.println("\n   Then applying LIGHT...");
        ElementalComboSystem.ComboResult result8 = comboSystem.applyElementalDamage(target, Element.LIGHT, 50);
        System.out.println("   " + result8.getMessage());
        if (result8.isCombo() && result8.getAppliedStatus() != null) {
            System.out.println("   Status Applied: " + result8.getAppliedStatus().toString());
            System.out.println("   Target can act: " + target.canAct());
        }
        
        // Test 5: Wind Swirl reactions
        comboSystem.clear();
        System.out.println("\n5. Applying FIRE...");
        comboSystem.applyElementalDamage(target, Element.FIRE, 50);
        
        System.out.println("\n   Then applying WIND...");
        ElementalComboSystem.ComboResult result9 = comboSystem.applyElementalDamage(target, Element.WIND, 50);
        System.out.println("   " + result9.getMessage());
        if (result9.isCombo()) {
            System.out.println("   Bonus Damage: " + String.format("%.0f", result9.getBonusDamage()));
        }
        
        System.out.println("===============================================");
    }
    
    private static void testHPTriggers() {
        System.out.println("\n--- Test 3: HP Trigger System ---");
        
        Stats bossStats = new Stats(20, 15, 25, 15, 15, 12);
        Enemy boss = new Enemy("boss", "Test Boss", 10, bossStats, Element.DARK, Enemy.AIPattern.TACTICAL);
        
        System.out.println("Boss: " + boss.toString());
        System.out.println("Max HP: " + boss.getMaxHP());
        
        // Add HP triggers
        boss.addHPTrigger(75.0, 
            () -> System.out.println("   [TRIGGER] Boss enrages! (75% HP)"),
            "Boss Enrage Phase");
        
        boss.addHPTrigger(50.0, 
            () -> System.out.println("   [TRIGGER] Boss summons minions! (50% HP)"),
            "Summon Minions");
        
        boss.addHPTrigger(25.0, 
            () -> System.out.println("   [TRIGGER] Boss enters desperate state! (25% HP)"),
            "Desperate Mode");
        
        // Simulate damage
        System.out.println("\nSimulating battle damage:\n");
        
        int[] damageAmounts = {30, 40, 50, 60, 70, 50};
        
        for (int damage : damageAmounts) {
            int actualDamage = boss.takeDamage(damage);
            System.out.println("Dealt " + actualDamage + " damage");
            System.out.println("Boss HP: " + boss.getCurrentHP() + "/" + boss.getMaxHP() + 
                             " (" + String.format("%.1f%%", boss.getHPPercentage()) + ")");
            
            // Check for triggers
            List<String> triggers = boss.checkHPTriggers();
            if (!triggers.isEmpty()) {
                for (String trigger : triggers) {
                    System.out.println(">>> " + trigger + " activated!");
                }
            }
            System.out.println();
        }
        
        System.out.println("Boss Status: " + (boss.isAlive() ? "Still fighting!" : "Defeated!"));
        
        System.out.println("===============================================");
    }
    
    static class TestCharacter extends Character {
        public TestCharacter(String id, String name, int level, Stats baseStats, Element element) {
            super(id, name, level, baseStats, element);
        }
    }
}
