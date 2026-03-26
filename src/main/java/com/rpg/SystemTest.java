package com.rpg;

import com.rpg.combat.StatusEffect;
import com.rpg.models.Stats;
import com.rpg.models.Element;
import com.rpg.models.StatusType;
import com.rpg.models.Character;
import com.rpg.ui.ConsoleUI;
import org.fusesource.jansi.Ansi;

/**
 * Test class to demonstrate core systems
 */
public class SystemTest {
    
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.displayWelcome();
        
        System.out.println("\n=== TESTING CORE SYSTEMS ===\n");
        
        // Test 1: Stats System
        testStatsSystem(ui);
        
        // Test 2: Element System
        testElementSystem(ui);
        
        // Test 3: Status Effects
        testStatusEffects(ui);
        
        // Test 4: Character with Status Effects
        testCharacterWithEffects(ui);
        
        ui.displaySuccess("\n=== ALL TESTS COMPLETED ===");
        ui.cleanup();
    }
    
    private static void testStatsSystem(ConsoleUI ui) {
        ui.displayMessage("\n--- Test 1: Two-Tier Stats System ---", Ansi.Color.CYAN);
        
        // Create a character with moderate stats
        Stats warriorStats = new Stats(20, 12, 18, 8, 10, 14);
        
        ui.displayMessage("\nWarrior Stats:");
        System.out.println(warriorStats.toDetailedString());
        
        // Create a mage with different stats
        Stats mageStats = new Stats(8, 10, 12, 22, 18, 12);
        
        ui.displayMessage("\nMage Stats:");
        System.out.println(mageStats.toDetailedString());
        
        ui.displaySeparator();
    }
    
    private static void testElementSystem(ConsoleUI ui) {
        ui.displayMessage("\n--- Test 2: Element Advantage System ---", Ansi.Color.CYAN);
        
        Element[] elements = {Element.FIRE, Element.WATER, Element.EARTH, 
                             Element.WIND, Element.LIGHT, Element.DARK};
        
        ui.displayMessage("\nElement Advantage Wheel:");
        ui.displayMessage("FIRE > WIND > EARTH > WATER > FIRE");
        ui.displayMessage("LIGHT <-> DARK\n");
        
        // Test some matchups
        testElementMatchup(ui, Element.FIRE, Element.WIND);
        testElementMatchup(ui, Element.WATER, Element.FIRE);
        testElementMatchup(ui, Element.LIGHT, Element.DARK);
        testElementMatchup(ui, Element.FIRE, Element.EARTH);
        
        ui.displaySeparator();
    }
    
    private static void testElementMatchup(ConsoleUI ui, Element attacker, Element defender) {
        double multiplier = attacker.getDamageMultiplier(defender);
        String result;
        Ansi.Color color;
        
        if (multiplier > 1.0) {
            result = "ADVANTAGE! (2x damage)";
            color = Ansi.Color.GREEN;
        } else if (multiplier < 1.0) {
            result = "Disadvantage (0.5x damage)";
            color = Ansi.Color.RED;
        } else {
            result = "Neutral (1x damage)";
            color = Ansi.Color.YELLOW;
        }
        
        ui.displayMessage(String.format("%s vs %s: %s", 
            attacker.name(), defender.name(), result), color);
    }
    
    private static void testStatusEffects(ConsoleUI ui) {
        ui.displayMessage("\n--- Test 3: Status Effect System ---", Ansi.Color.CYAN);
        
        // Create some status effects
        StatusEffect atkBuff = new StatusEffect(StatusType.ATK_UP, 3, 20.0); // 20% ATK up for 3 turns
        StatusEffect poison = new StatusEffect(StatusType.POISON, 5, 15.0, true, false, 3); // Stackable poison
        StatusEffect stun = new StatusEffect(StatusType.STUN, 1, 0.0); // 1 turn stun
        
        ui.displayMessage("\nCreated Status Effects:");
        ui.displayMessage("1. " + atkBuff.toString());
        ui.displayMessage("2. " + poison.toString());
        ui.displayMessage("3. " + stun.toString());
        
        // Test stacking
        ui.displayMessage("\nTesting Poison Stacking:");
        ui.displayMessage("Initial: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1);
        ui.displayMessage("After +1 stack: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1);
        ui.displayMessage("After +1 stack: " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        poison.addStacks(1); // Should cap at 3
        ui.displayMessage("After +1 stack (max): " + poison.toString() + " (Damage per turn: " + poison.getTickDamage() + ")");
        
        // Test duration
        ui.displayMessage("\nTesting Duration:");
        ui.displayMessage("ATK Buff duration: " + atkBuff.getDuration() + " turns");
        atkBuff.decrementDuration();
        ui.displayMessage("After 1 turn: " + atkBuff.getDuration() + " turns");
        atkBuff.decrementDuration();
        ui.displayMessage("After 2 turns: " + atkBuff.getDuration() + " turns");
        
        ui.displaySeparator();
    }
    
    private static void testCharacterWithEffects(ConsoleUI ui) {
        ui.displayMessage("\n--- Test 4: Character with Status Effects ---", Ansi.Color.CYAN);
        
        // Create a test character
        Stats testStats = new Stats(15, 15, 15, 15, 15, 15);
        TestCharacter hero = new TestCharacter("hero_001", "Brave Hero", 5, testStats, Element.FIRE);
        
        ui.displayMessage("\n" + hero.toString());
        ui.displayMessage("Physical ATK: " + String.format("%.1f", hero.getPhysicalAttack()));
        ui.displayMessage("Physical DEF: " + String.format("%.1f", hero.getPhysicalDefense()));
        ui.displayMessage("Speed: " + String.format("%.1f", hero.getSpeed()));
        
        // Apply buffs
        ui.displayMessage("\nApplying +30% ATK buff and +20% SPD buff...");
        hero.addStatusEffect(new StatusEffect(StatusType.ATK_UP, 3, 30.0));
        hero.addStatusEffect(new StatusEffect(StatusType.SPD_UP, 2, 20.0));
        
        ui.displayMessage("\nAfter buffs:");
        ui.displayMessage("Physical ATK: " + String.format("%.1f", hero.getPhysicalAttack()) + " (was 30.0)");
        ui.displayMessage("Speed: " + String.format("%.1f", hero.getSpeed()) + " (was 30.3)");
        
        // Apply poison
        ui.displayMessage("\nApplying Poison (15 damage/turn, 3 turns)...");
        StatusEffect poison = new StatusEffect(StatusType.POISON, 3, 15.0, true, false, 3);
        hero.addStatusEffect(poison);
        
        ui.displayMessage("\nStatus Effects: ");
        for (StatusEffect effect : hero.getStatusEffects()) {
            ui.displayMessage("  - " + effect.toString());
        }
        
        // Simulate turns
        ui.displayMessage("\nSimulating 3 turns of combat:");
        for (int turn = 1; turn <= 3; turn++) {
            ui.displayMessage("\n--- Turn " + turn + " ---");
            ui.displayMessage("HP before: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
            
            double tickDamage = hero.processStatusEffects();
            if (tickDamage > 0) {
                ui.displayMessage("Tick damage: " + String.format("%.1f", tickDamage), Ansi.Color.RED);
            }
            
            ui.displayMessage("HP after: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
            ui.displayMessage("Can act: " + (hero.canAct() ? "Yes" : "No"));
            
            ui.displayMessage("Active effects: ");
            for (StatusEffect effect : hero.getStatusEffects()) {
                ui.displayMessage("  - " + effect.toString());
            }
        }
        
        // Test taking damage
        ui.displayMessage("\n\nTesting Damage:");
        ui.displayMessage("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        int damage = hero.takeDamage(50);
        ui.displayMessage("Took " + damage + " damage!", Ansi.Color.RED);
        ui.displayMessage("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        
        // Test healing
        ui.displayMessage("\nTesting Healing:");
        int healed = hero.heal(30);
        ui.displayMessage("Healed " + healed + " HP!", Ansi.Color.GREEN);
        ui.displayMessage("HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP());
        
        ui.displaySeparator();
    }
    
    /**
     * Simple test character class
     */
    static class TestCharacter extends Character {
        public TestCharacter(String id, String name, int level, Stats baseStats, Element element) {
            super(id, name, level, baseStats, element);
        }
    }
}
