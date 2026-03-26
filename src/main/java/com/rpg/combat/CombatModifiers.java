package com.rpg.combat;

import com.rpg.models.Character;
import com.rpg.systems.AlignmentSystem;

/**
 * Interface for applying combat modifiers from various sources
 * (alignment buffs, class abilities, equipment effects, status effects)
 */
public class CombatModifiers {
    
    /**
     * All combat modifier bonuses consolidated
     */
    public static class ModifierResult {
        public double damageMultiplier = 1.0;
        public double healingMultiplier = 1.0;
        public double damageTakenMultiplier = 1.0;
        public double accuracyBonus = 0.0;
        public double critRateBonus = 0.0;
        public boolean canExecute = false;        // Instant kill below threshold
        public int executeThreshold = 0;          // HP% for execute
        public boolean immuneToCharm = false;
        public boolean immuneToDark = false;
        public double allyDamageReduction = 0.0;  // Reduction for nearby allies
        public double enemyStatReduction = 0.0;   // Reduction to enemy stats
        public double allyStatBonus = 0.0;        // Bonus to ally stats
        public double woundedDamageBonus = 0.0;   // Bonus damage vs wounded enemies
        
        public void combine(ModifierResult other) {
            this.damageMultiplier *= other.damageMultiplier;
            this.healingMultiplier *= other.healingMultiplier;
            this.damageTakenMultiplier *= other.damageTakenMultiplier;
            this.accuracyBonus += other.accuracyBonus;
            this.critRateBonus += other.critRateBonus;
            this.canExecute = this.canExecute || other.canExecute;
            this.executeThreshold = Math.max(this.executeThreshold, other.executeThreshold);
            this.immuneToCharm = this.immuneToCharm || other.immuneToCharm;
            this.immuneToDark = this.immuneToDark || other.immuneToDark;
            this.allyDamageReduction += other.allyDamageReduction;
            this.enemyStatReduction += other.enemyStatReduction;
            this.allyStatBonus += other.allyStatBonus;
            this.woundedDamageBonus += other.woundedDamageBonus;
        }
    }
    
    /**
     * Calculate alignment-based combat modifiers
     */
    public static ModifierResult getAlignmentModifiers(AlignmentSystem alignment) {
        ModifierResult result = new ModifierResult();
        
        if (alignment == null) return result;
        
        int honor = alignment.getHonor();
        int compassion = alignment.getCompassion();
        
        // ==================== HONOR AXIS BUFFS ====================
        
        if (honor > 0) {
            // PARAGON PATH (Positive Honor)
            int tier = alignment.getHonorBuffTier();
            
            // +75: Incorruptible - Immune to charm/mind control
            if (tier >= 3) {
                result.immuneToCharm = true;
            }
            
            // +90: Living Legend - Allies gain +15% all stats
            if (tier >= 4) {
                result.allyStatBonus += 0.15;
            }
            
        } else if (honor < 0) {
            // SERPENT PATH (Negative Honor)
            int tier = alignment.getHonorBuffTier();
            
            // -50: Web of Lies - Enemies have -10% accuracy (confusion)
            if (tier >= 2) {
                result.enemyStatReduction += 0.10;
            }
            
            // -90: Puppeteer - Would need special handling for enemy confusion
            // (Handled separately in AI system)
        }
        
        // ==================== COMPASSION AXIS BUFFS ====================
        
        if (compassion > 0) {
            // SAINT PATH (Positive Compassion)
            int tier = alignment.getCompassionBuffTier();
            
            // +25: Gentle Soul - Healing +10%
            if (tier >= 1) {
                result.healingMultiplier *= 1.10;
            }
            
            // +50: Protector - Allies take -15% damage near you
            if (tier >= 2) {
                result.allyDamageReduction += 0.15;
            }
            
            // +75: Beacon - Undead/demons deal -25% damage to you
            // (Would need enemy type checking - simplified here)
            if (tier >= 3) {
                result.immuneToDark = true; // Simplified
            }
            
        } else if (compassion < 0) {
            // TYRANT PATH (Negative Compassion)
            int tier = alignment.getCompassionBuffTier();
            
            // -50: No Mercy - +20% damage to wounded enemies (<50% HP)
            if (tier >= 2) {
                result.woundedDamageBonus += 0.20;
            }
            
            // -75: Dread Presence - Enemies have -15% all stats
            if (tier >= 3) {
                result.enemyStatReduction += 0.15;
            }
            
            // -90: Executioner - Instant kill enemies below 10% HP
            if (tier >= 4) {
                result.canExecute = true;
                result.executeThreshold = 10;
            }
        }
        
        return result;
    }
    
    /**
     * Check if target should be executed (instant kill)
     */
    public static boolean shouldExecute(Character target, ModifierResult modifiers) {
        if (!modifiers.canExecute) return false;
        
        double hpPercent = (double) target.getCurrentHP() / target.getMaxHP() * 100;
        return hpPercent <= modifiers.executeThreshold;
    }
    
    /**
     * Apply wounded damage bonus
     */
    public static double getWoundedBonus(Character target, ModifierResult modifiers) {
        if (modifiers.woundedDamageBonus <= 0) return 1.0;
        
        double hpPercent = (double) target.getCurrentHP() / target.getMaxHP() * 100;
        if (hpPercent < 50) {
            return 1.0 + modifiers.woundedDamageBonus;
        }
        return 1.0;
    }
}
