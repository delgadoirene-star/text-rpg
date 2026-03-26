package com.rpg.combat;

import com.rpg.models.Character;
import com.rpg.models.Element;
import com.rpg.models.Player;

import java.util.Random;

/**
 * Calculates damage with all modifiers including alignment bonuses
 */
public class DamageCalculator {
    private static final Random random = new Random();
    private final Difficulty difficulty;
    
    public DamageCalculator(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    /**
     * Calculate damage result including all modifiers
     */
    public DamageResult calculateDamage(Character attacker, Character defender, 
                                       double basePower, DamageType damageType, 
                                       Element element) {
        return calculateDamage(attacker, defender, basePower, damageType, element, null);
    }
    
    /**
     * Calculate damage with combat modifiers (alignment, class bonuses, etc.)
     */
    public DamageResult calculateDamage(Character attacker, Character defender, 
                                       double basePower, DamageType damageType, 
                                       Element element, CombatModifiers.ModifierResult modifiers) {
        
        // Get modifiers from attacker if they're a Player and no modifiers provided
        if (modifiers == null && attacker instanceof Player) {
            modifiers = ((Player) attacker).getAlignmentCombatModifiers();
        }
        
        // Step 0: Check for execute (instant kill for Tyrant path)
        if (modifiers != null && CombatModifiers.shouldExecute(defender, modifiers)) {
            defender.takeDamage(defender.getCurrentHP()); // Kill them
            return new DamageResult(defender.getMaxHP(), false, false, false, 
                "EXECUTED! (Tyrant's Judgment)");
        }
        
        // Step 1: Check if attack hits
        double accuracyBonus = modifiers != null ? modifiers.accuracyBonus : 0;
        if (!rollHit(attacker, defender, accuracyBonus)) {
            return new DamageResult(0, false, false, true, "MISS!");
        }
        
        // Step 2: Get base attack and defense stats
        double attack = getAttackStat(attacker, damageType);
        double defense = getDefenseStat(defender, damageType);
        
        // Step 3: Calculate base damage
        double damage = (attack * basePower) - (defense * 0.5);
        damage = Math.max(1, damage); // Minimum 1 damage
        
        // Step 4: Apply element multiplier
        double elementMultiplier = element.getDamageMultiplier(defender.getElementAffinity());
        damage *= elementMultiplier;
        
        // Step 5: Check for critical hit
        double critBonus = modifiers != null ? modifiers.critRateBonus : 0;
        boolean isCrit = rollCrit(attacker, critBonus);
        if (isCrit) {
            double critMultiplier = attacker.getTotalStats().getCritDamage() / 100.0;
            damage *= critMultiplier;
        }
        
        // Step 6: Apply alignment damage modifiers
        if (modifiers != null) {
            damage *= modifiers.damageMultiplier;
            
            // Wounded damage bonus (Tyrant's "No Mercy")
            damage *= CombatModifiers.getWoundedBonus(defender, modifiers);
        }
        
        // Step 7: Apply damage caps (Hard mode only)
        if (difficulty.hasDamageCaps()) {
            damage = applyDamageCap(damage);
        }
        
        // Step 8: Add variance (±10%)
        double variance = 0.9 + (random.nextDouble() * 0.2); // 0.9 to 1.1
        damage *= variance;
        
        // Step 9: Round to integer
        int finalDamage = (int) Math.round(damage);
        
        // Create result message
        String message = buildDamageMessage(finalDamage, isCrit, elementMultiplier);
        
        return new DamageResult(finalDamage, isCrit, elementMultiplier > 1.0, false, message);
    }
    
    /**
     * Roll to see if attack hits
     */
    private boolean rollHit(Character attacker, Character defender, double accuracyBonus) {
        double accuracy = attacker.getTotalStats().getAccuracy() + accuracyBonus;
        double evasion = defender.getTotalStats().getEvasion();
        
        double hitChance = accuracy - evasion;
        hitChance = Math.max(5, Math.min(100, hitChance)); // Clamp between 5% and 100%
        
        return random.nextDouble() * 100 < hitChance;
    }
    
    /**
     * Roll for critical hit
     */
    private boolean rollCrit(Character attacker, double critBonus) {
        double critRate = attacker.getTotalStats().getCritRate() + critBonus;
        return random.nextDouble() * 100 < critRate;
    }
    
    /**
     * Get appropriate attack stat based on damage type
     */
    private double getAttackStat(Character character, DamageType type) {
        return switch (type) {
            case PHYSICAL -> character.getPhysicalAttack();
            case MAGICAL -> character.getMagicAttack();
            case TRUE, HEALING -> 1.0; // True damage and healing don't use attack stat
        };
    }
    
    /**
     * Get appropriate defense stat based on damage type
     */
    private double getDefenseStat(Character character, DamageType type) {
        if (type.ignoresDefense()) {
            return 0.0;
        }
        
        return switch (type) {
            case PHYSICAL -> character.getPhysicalDefense();
            case MAGICAL -> character.getMagicDefense();
            default -> 0.0;
        };
    }
    
    /**
     * Apply diminishing returns on high damage (Hard mode)
     */
    private double applyDamageCap(double damage) {
        double softCap = 999.0;
        
        if (damage <= softCap) {
            return damage;
        }
        
        // Damage above soft cap has diminishing returns
        double excess = damage - softCap;
        double diminished = Math.sqrt(excess) * 10; // Square root scaling
        
        return softCap + diminished;
    }
    
    /**
     * Build damage message with modifiers
     */
    private String buildDamageMessage(int damage, boolean isCrit, double elementMultiplier) {
        StringBuilder msg = new StringBuilder();
        msg.append(damage).append(" damage");
        
        if (isCrit) {
            msg.append(" (CRITICAL!)");
        }
        
        if (elementMultiplier > 1.0) {
            msg.append(" (SUPER EFFECTIVE!)");
        } else if (elementMultiplier < 1.0) {
            msg.append(" (Not very effective...)");
        }
        
        return msg.toString();
    }
    
    /**
     * Calculate healing (simplified damage calculation)
     */
    public int calculateHealing(Character caster, double basePower) {
        return calculateHealing(caster, basePower, null);
    }
    
    /**
     * Calculate healing with modifiers (Saint path bonus)
     */
    public int calculateHealing(Character caster, double basePower, CombatModifiers.ModifierResult modifiers) {
        // Get modifiers from caster if they're a Player
        if (modifiers == null && caster instanceof Player) {
            modifiers = ((Player) caster).getAlignmentCombatModifiers();
        }
        
        double magicAtk = caster.getMagicAttack();
        double healing = magicAtk * basePower * 0.5;
        
        // Apply healing multiplier (Saint's "Gentle Soul")
        if (modifiers != null) {
            healing *= modifiers.healingMultiplier;
        }
        
        // Add variance
        double variance = 0.9 + (random.nextDouble() * 0.2);
        healing *= variance;
        
        return (int) Math.round(healing);
    }
    
    /**
     * Result of a damage calculation
     */
    public static class DamageResult {
        private final int damage;
        private final boolean isCritical;
        private final boolean isSuperEffective;
        private final boolean isMiss;
        private final String message;
        
        public DamageResult(int damage, boolean isCritical, boolean isSuperEffective, 
                           boolean isMiss, String message) {
            this.damage = damage;
            this.isCritical = isCritical;
            this.isSuperEffective = isSuperEffective;
            this.isMiss = isMiss;
            this.message = message;
        }
        
        public int getDamage() { return damage; }
        public boolean isCritical() { return isCritical; }
        public boolean isSuperEffective() { return isSuperEffective; }
        public boolean isMiss() { return isMiss; }
        public String getMessage() { return message; }
    }
}
