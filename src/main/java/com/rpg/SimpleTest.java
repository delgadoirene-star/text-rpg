package com.rpg;

import com.rpg.combat.StatusEffect;
import com.rpg.models.Stats;
import com.rpg.models.Element;
import com.rpg.models.StatusType;
import com.rpg.models.Character;

/**
 * Simple test without external dependencies
 */
public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("=== TEXT RPG - SYSTEM TEST ===\n");
        
        // Test 1: Stats System
        testStatsSystem();
        
        // Test 2: Element System
        testElementSystem();
        
        // Test 3: Status Effects
        testStatusEffects();
        
        // Test 4: Character with Status Effects
        testCharacterWithEffects();
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
    
    private static void testStatsSystem() {
        System.out.println("\n--- Test 1: Two-Tier Stats System ---");
        
        Stats warriorStats = new Stats(20, 12, 18, 8, 10, 14);
        System.out.println("\nWarrior Stats:");
        System.out.println(warriorStats.toDetailedString());
        
        Stats mageStats = new Stats(8, 10, 12, 22, 18, 12);
        System.out.println("\nMage Stats:");
        System.out.println(mageStats.toDetailedString());
        
        System.out.println("===============================================");
    }
    
    private static void testElementSystem() {
        System.out.println("\n--- Test 2: Element Advantage System ---");
        System.out.println("\nElement Advantage Wheel:");
        System.out.println("FIRE > WIND > EARTH > WATER > FIRE");
        System.out.println("LIGHT <-> DARK\n");
        
        testElementMatchup(Element.FIRE, Element.WIND);
        testElementMatchup(Element.WATER, Element.FIRE);
        testElementMatchup(Element.LIGHT, Element.DARK);
        testElementMatchup(Element.FIRE, Element.EARTH);
        
        System.out.println("===============================================");
    }
    
    private static void testElementMatchup(Element attacker, Element defender) {
        double multiplier = attacker.getDamageMultiplier(defender);
        String result;
        
        if (multiplier > 1.0) {
            result = "ADVANTAGE! (2x damage)";
        } else if (multiplier < 1.0) {
            result = "Disadvantage (0.5x damage)";
        } else {
            result = "Neutral (1x damage)";
        }
        
        System.out.println(String.format("%s vs %s: %s", 
            attacker.name(), defender.name(), result));
    }
    
    private static void testStatusEffects() {
        System.out.println("\n--- Test 3: Status Effect System ---");
        
        StatusEffect atkBuff = new StatusEffect(StatusType.ATK_UP, 3, 20.0);
        StatusEffect poison = new StatusEffect(StatusType.POISON, 5, 15.0, true, false, 3);
        StatusEffect stun = new StatusEffect(StatusType.STUN, 1, 0.0);
        
        System.out.println("\nCreated Status Effects:");
        System.out.println("1. " + atkBuff.toString());
        System.out.println("2. " + poison.toString());
        System.out.println("3. " + stun.toString());
        
        System.out.println("\nTesting Poison Stacking:");
        System.out.println("Initial: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1);
        System.out.println("After +1 stack: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1);
        System.out.println("After +1 stack: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1);
        System.out.println("After +1 stack (max): " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        
        System.out.println("\nTesting Duration:");
        System.out.println("ATK Buff duration: " + atkBuff.getDuration() + " turns");
        atkBuff.decrementDuration();
        System.out.println("After 1 turn: " + atkBuff.getDuration() + " turns");
        atkBuff.decrementDuration();
        System.out.println("After 2 turns: " + atkBuff.getDuration() + " turns");
        
        System.out.println("===============================================");
    }
    
    private static void testCharacterWithEffects() {
        System.out.println("\n--- Test 4: Character with Status Effects ---");
        
        Stats testStats = new Stats(15, 15, 15, 15, 15, 15);
        TestCharacter hero = new TestCharacter("hero_001", "Brave Hero", 5, testStats, Element.FIRE);
        
        System.out.println("\n" + hero.toString());
        System.out.println("Physical ATK: " + String.format("%.1f", hero.getPhysicalAttack()));
        System.out.println("Physical DEF: " + String.format("%.1f", hero.getPhysicalDefense()));
        System.out.println("Speed: " + String.format("%.1f", hero.getSpeed()));
        
        System.out.println("\nApplying +30% ATK buff and +20% SPD buff...");
        hero.addStatusEffect(new StatusEffect(StatusType.ATK_UP, 3, 30.0));
        hero.addStatusEffect(new StatusEffect(StatusType.SPD_UP, 2, 20.0));
        
        System.out.println("\nAfter buffs:");
        System.out.println("Physical ATK: " + String.format("%.1f", hero.getPhysicalAttack()) + " (was 30.0)");
        System.out.println("Speed: " + String.format("%.1f", hero.getSpeed()) + " (was 30.3)");
        
        System.out.println("\nApplying Poison (15 damage/turn, 3 turns)...");
        StatusEffect poison = new StatusEffect(StatusType.POISON, 3, 15.0, true, false, 3);
        hero.addStatusEffect(poison);
        
        System.out.println("\nStatus Effects: ");
        for (StatusEffect effect : hero.getStatusEffects()) {
            System.out.println("  - " + effect.toString());
        }
        
        System.out.println("\nSimulating 3 turns of combat:");
        for (int turn = 1; turn <= 3; turn++) {
            System.out.println("\n--- Turn " + turn + " ---");
            System.out.println("HP before: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
            
            double tickDamage = hero.processStatusEffects();
            if (tickDamage > 0) {
                System.out.println("Tick damage: " + String.format("%.1f", tickDamage));
            }
            
            System.out.println("HP after: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
            System.out.println("Can act: " + (hero.canAct() ? "Yes" : "No"));
            
            System.out.println("Active effects: ");
            for (StatusEffect effect : hero.getStatusEffects()) {
                System.out.println("  - " + effect.toString());
            }
        }
        
        System.out.println("\n\nTesting Damage:");
        System.out.println("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        int damage = hero.takeDamage(50);
        System.out.println("Took " + damage + " damage!");
        System.out.println("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        
        System.out.println("\nTesting Healing:");
        int healed = hero.heal(30);
        System.out.println("Healed " + healed + " HP!");
        System.out.println("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        
        System.out.println("===============================================");
    }
    
    static class TestCharacter extends Character {
        public TestCharacter(String id, String name, int level, Stats baseStats, Element element) {
            super(id, name, level, baseStats, element);
        }
    }
}
