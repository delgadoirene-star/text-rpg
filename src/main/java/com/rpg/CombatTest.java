package com.rpg;

import com.rpg.combat.*;
import com.rpg.models.Stats;
import com.rpg.models.Element;
import com.rpg.models.Character;

/**
 * Test combat mechanics
 */
public class CombatTest {
    
    public static void main(String[] args) {
        System.out.println("=== COMBAT SYSTEM TEST ===\n");
        
        // Test 1: Focus Meter
        testFocusMeter();
        
        // Test 2: Damage Calculation
        testDamageCalculation();
        
        // Test 3: Element Advantage in Combat
        testElementalCombat();
        
        // Test 4: Difficulty Modes
        testDifficultyModes();
        
        System.out.println("\n=== ALL COMBAT TESTS COMPLETED ===");
    }
    
    private static void testFocusMeter() {
        System.out.println("\n--- Test 1: Focus Meter System ---");
        
        FocusMeter meter = new FocusMeter();
        System.out.println("Initial: " + meter.toString());
        
        // Simulate gaining focus from actions
        System.out.println("\nGaining focus from combat actions:");
        meter.addFocus(15, 1.0); // Normal attack
        System.out.println("After attack: " + meter.toString());
        
        meter.addFocus(10, 1.0); // Taking damage
        System.out.println("After taking damage: " + meter.toString());
        
        meter.addFocus(20, 1.0); // Another attack
        System.out.println("After another attack: " + meter.toString());
        
        // Check what tiers are available
        System.out.println("\nAvailable tiers:");
        System.out.println("  Tier 1 (25 cost): " + (meter.canUseTier(1) ? "YES" : "NO"));
        System.out.println("  Tier 2 (50 cost): " + (meter.canUseTier(2) ? "NO" : "YES"));
        System.out.println("  Tier 3 (100 cost): " + (meter.canUseTier(3) ? "YES" : "NO"));
        
        // Use a Tier 1 ability
        System.out.println("\nUsing Tier 1 ability (25 focus)...");
        meter.spendFocus(FocusMeter.TIER_1_COST);
        System.out.println("After using ability: " + meter.toString());
        
        // Fill to 100%
        meter.setFocus(100);
        System.out.println("\nSet to maximum: " + meter.toString());
        System.out.println("  Tier 3 available: " + (meter.canUseTier(3) ? "YES" : "NO"));
        
        // Test Story mode bonus
        System.out.println("\nTesting Story Mode (1.5x charge rate):");
        meter.reset();
        meter.addFocus(20, 1.5); // Story mode multiplier
        System.out.println("After one attack in Story Mode: " + meter.toString());
        
        System.out.println("===============================================");
    }
    
    private static void testDamageCalculation() {
        System.out.println("\n--- Test 2: Damage Calculation ---");
        
        // Create a warrior and enemy
        Stats warriorStats = new Stats(20, 15, 18, 8, 10, 15);
        Stats enemyStats = new Stats(12, 10, 15, 8, 10, 10);
        
        TestCharacter warrior = new TestCharacter("warrior", "Warrior", 5, warriorStats, Element.FIRE);
        TestCharacter enemy = new TestCharacter("goblin", "Goblin", 3, enemyStats, Element.NEUTRAL);
        
        System.out.println("Attacker: " + warrior.toString());
        System.out.println("  Physical ATK: " + String.format("%.1f", warrior.getPhysicalAttack()));
        System.out.println("  Crit Rate: " + String.format("%.1f%%", warrior.getTotalStats().getCritRate()));
        
        System.out.println("\nDefender: " + enemy.toString());
        System.out.println("  Physical DEF: " + String.format("%.1f", enemy.getPhysicalDefense()));
        System.out.println("  Evasion: " + String.format("%.1f%%", enemy.getTotalStats().getEvasion()));
        
        // Test multiple attacks to see variance
        DamageCalculator calculator = new DamageCalculator(Difficulty.NORMAL);
        
        System.out.println("\nSimulating 10 attacks (base power 1.5):");
        int totalDamage = 0;
        int hits = 0;
        int crits = 0;
        
        for (int i = 1; i <= 10; i++) {
            DamageCalculator.DamageResult result = calculator.calculateDamage(
                warrior, enemy, 1.5, DamageType.PHYSICAL, Element.NEUTRAL
            );
            
            System.out.println("Attack " + i + ": " + result.getMessage());
            
            if (!result.isMiss()) {
                totalDamage += result.getDamage();
                hits++;
                if (result.isCritical()) crits++;
            }
        }
        
        System.out.println("\nResults:");
        System.out.println("  Total Hits: " + hits + "/10");
        System.out.println("  Critical Hits: " + crits);
        System.out.println("  Average Damage: " + (hits > 0 ? totalDamage / hits : 0));
        System.out.println("  Total Damage: " + totalDamage);
        
        System.out.println("===============================================");
    }
    
    private static void testElementalCombat() {
        System.out.println("\n--- Test 3: Elemental Combat ---");
        
        Stats attackerStats = new Stats(18, 12, 15, 12, 10, 12);
        
        // Fire attacker
        TestCharacter fireWarrior = new TestCharacter("fire_warrior", "Fire Warrior", 5, attackerStats, Element.FIRE);
        
        // Create enemies with different elements
        Stats enemyStats = new Stats(10, 10, 12, 10, 10, 10);
        TestCharacter windEnemy = new TestCharacter("wind_enemy", "Wind Slime", 3, enemyStats, Element.WIND);
        TestCharacter waterEnemy = new TestCharacter("water_enemy", "Water Slime", 3, enemyStats, Element.WATER);
        TestCharacter fireEnemy = new TestCharacter("fire_enemy", "Fire Slime", 3, enemyStats, Element.FIRE);
        
        DamageCalculator calculator = new DamageCalculator(Difficulty.NORMAL);
        
        System.out.println("Fire Warrior attacking different elements:");
        System.out.println("(Base power: 2.0 for all attacks)\n");
        
        // Attack Wind (advantage)
        System.out.println("vs WIND (Advantage):");
        for (int i = 0; i < 3; i++) {
            DamageCalculator.DamageResult result = calculator.calculateDamage(
                fireWarrior, windEnemy, 2.0, DamageType.PHYSICAL, Element.FIRE
            );
            System.out.println("  " + result.getMessage());
        }
        
        // Attack Water (disadvantage)
        System.out.println("\nvs WATER (Disadvantage):");
        for (int i = 0; i < 3; i++) {
            DamageCalculator.DamageResult result = calculator.calculateDamage(
                fireWarrior, waterEnemy, 2.0, DamageType.PHYSICAL, Element.FIRE
            );
            System.out.println("  " + result.getMessage());
        }
        
        // Attack Fire (neutral)
        System.out.println("\nvs FIRE (Neutral):");
        for (int i = 0; i < 3; i++) {
            DamageCalculator.DamageResult result = calculator.calculateDamage(
                fireWarrior, fireEnemy, 2.0, DamageType.PHYSICAL, Element.FIRE
            );
            System.out.println("  " + result.getMessage());
        }
        
        System.out.println("===============================================");
    }
    
    private static void testDifficultyModes() {
        System.out.println("\n--- Test 4: Difficulty Modes ---");
        
        Stats attackerStats = new Stats(25, 15, 20, 15, 15, 20); // Strong character
        Stats defenderStats = new Stats(10, 10, 12, 10, 10, 10);
        
        TestCharacter strongWarrior = new TestCharacter("strong", "Strong Warrior", 10, attackerStats, Element.NEUTRAL);
        TestCharacter weakEnemy = new TestCharacter("weak", "Weak Enemy", 3, defenderStats, Element.NEUTRAL);
        
        System.out.println("Testing same attack on different difficulties:");
        System.out.println("(High power attack: 5.0)\n");
        
        // Test each difficulty
        for (Difficulty diff : Difficulty.values()) {
            System.out.println(diff.getDisplayName() + ":");
            System.out.println("  " + diff.getDescription());
            System.out.println("  Focus charge: " + (diff.getFocusChargeMultiplier() * 100) + "%");
            System.out.println("  Damage caps: " + (diff.hasDamageCaps() ? "Yes" : "No"));
            
            DamageCalculator calculator = new DamageCalculator(diff);
            
            // Show 3 sample attacks
            System.out.println("  Sample attacks:");
            for (int i = 0; i < 3; i++) {
                DamageCalculator.DamageResult result = calculator.calculateDamage(
                    strongWarrior, weakEnemy, 5.0, DamageType.PHYSICAL, Element.NEUTRAL
                );
                System.out.println("    " + result.getMessage());
            }
            System.out.println();
        }
        
        // Test focus meter with difficulty
        System.out.println("Focus Meter Comparison (gaining 20 focus):");
        for (Difficulty diff : Difficulty.values()) {
            FocusMeter meter = new FocusMeter();
            meter.addFocus(20, diff.getFocusChargeMultiplier());
            System.out.println("  " + diff.getDisplayName() + ": " + meter.toString());
        }
        
        System.out.println("===============================================");
    }
    
    static class TestCharacter extends Character {
        public TestCharacter(String id, String name, int level, Stats baseStats, Element element) {
            super(id, name, level, baseStats, element);
        }
    }
}
