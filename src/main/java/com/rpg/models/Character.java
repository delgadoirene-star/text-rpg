package com.rpg.models;

import com.rpg.combat.StatusEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all characters (Player, Enemy, NPC, Companion)
 */
public abstract class Character {
    protected String id;
    protected String name;
    protected int level;
    protected int currentHP;
    protected Stats baseStats;          // Base stats from leveling
    protected Element elementAffinity;
    protected List<StatusEffect> statusEffects;
    
    public Character(String id, String name, int level, Stats baseStats, Element element) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.baseStats = baseStats;
        this.elementAffinity = element;
        this.statusEffects = new ArrayList<>();  // Initialize BEFORE calling getMaxHP()
        this.currentHP = getMaxHP();
    }
    
    /**
     * Get total stats including equipment and buffs
     * Override in subclasses to add equipment bonuses
     */
    public Stats getTotalStats() {
        // Base implementation just returns base stats
        // Player/Companion classes will override to include equipment
        return baseStats.copy();
    }
    
    /**
     * Calculate max HP including all modifiers
     */
    public int getMaxHP() {
        int baseHP = getTotalStats().getMaxHP();
        
        // Apply HP modifiers from status effects (only if initialized)
        if (statusEffects != null) {
            for (StatusEffect effect : statusEffects) {
                // Add HP modifier logic if needed
            }
        }
        
        return baseHP;
    }
    
    /**
     * Get physical attack including all modifiers
     */
    public double getPhysicalAttack() {
        double baseAtk = getTotalStats().getPhysicalAttack();
        return applyStatModifiers(baseAtk, StatusType.ATK_UP, StatusType.ATK_DOWN);
    }
    
    /**
     * Get physical defense including all modifiers
     */
    public double getPhysicalDefense() {
        double baseDef = getTotalStats().getPhysicalDefense();
        return applyStatModifiers(baseDef, StatusType.DEF_UP, StatusType.DEF_DOWN);
    }
    
    /**
     * Get magic attack including all modifiers
     */
    public double getMagicAttack() {
        double baseMagAtk = getTotalStats().getMagicAttack();
        return applyStatModifiers(baseMagAtk, StatusType.MAG_ATK_UP, StatusType.MAG_ATK_DOWN);
    }
    
    /**
     * Get magic defense including all modifiers
     */
    public double getMagicDefense() {
        double baseMagDef = getTotalStats().getMagicDefense();
        return applyStatModifiers(baseMagDef, StatusType.MAG_DEF_UP, StatusType.MAG_DEF_DOWN);
    }
    
    /**
     * Get speed including all modifiers
     */
    public double getSpeed() {
        double baseSpd = getTotalStats().getSpeed();
        return applyStatModifiers(baseSpd, StatusType.SPD_UP, StatusType.SPD_DOWN);
    }
    
    /**
     * Apply status effect modifiers to a stat
     */
    private double applyStatModifiers(double baseStat, StatusType buffType, StatusType debuffType) {
        double modifier = 0.0;
        
        for (StatusEffect effect : statusEffects) {
            if (effect.getType() == buffType) {
                modifier += effect.getStatModifier();
            } else if (effect.getType() == debuffType) {
                modifier -= effect.getStatModifier();
            }
        }
        
        // Apply modifier as percentage
        return baseStat * (1.0 + modifier / 100.0);
    }
    
    /**
     * Take damage
     * @return actual damage dealt
     */
    public int takeDamage(int damage) {
        int actualDamage = Math.max(0, damage);
        currentHP = Math.max(0, currentHP - actualDamage);
        return actualDamage;
    }
    
    /**
     * Heal HP
     * @return actual HP healed
     */
    public int heal(int amount) {
        int maxHP = getMaxHP();
        int oldHP = currentHP;
        currentHP = Math.min(maxHP, currentHP + amount);
        return currentHP - oldHP;
    }
    
    /**
     * Add a status effect
     */
    public void addStatusEffect(StatusEffect effect) {
        // Check if effect already exists
        for (StatusEffect existing : statusEffects) {
            if (existing.getType() == effect.getType()) {
                if (existing.isStackable()) {
                    existing.addStacks(1);
                } else if (existing.isRefreshable()) {
                    existing.refresh(effect.getDuration());
                }
                return;
            }
        }
        
        // Add new effect
        statusEffects.add(effect);
    }
    
    /**
     * Remove a specific status effect
     */
    public void removeStatusEffect(StatusType type) {
        statusEffects.removeIf(effect -> effect.getType() == type);
    }
    
    /**
     * Process status effects (tick damage, decrease duration)
     * @return Total tick damage taken
     */
    public double processStatusEffects() {
        double totalTickDamage = 0.0;
        
        // Process each effect
        List<StatusEffect> toRemove = new ArrayList<>();
        for (StatusEffect effect : statusEffects) {
            // Apply tick damage
            if (effect.getType().hasTickDamage()) {
                totalTickDamage += effect.getTickDamage();
            }
            
            // Decrease duration
            if (effect.decrementDuration()) {
                toRemove.add(effect);
            }
        }
        
        // Remove expired effects
        statusEffects.removeAll(toRemove);
        
        // Apply tick damage
        if (totalTickDamage > 0) {
            takeDamage((int) totalTickDamage);
        }
        
        return totalTickDamage;
    }
    
    /**
     * Check if character has a specific status effect
     */
    public boolean hasStatusEffect(StatusType type) {
        return statusEffects.stream().anyMatch(effect -> effect.getType() == type);
    }
    
    /**
     * Check if character can act (not stunned, asleep, etc.)
     */
    public boolean canAct() {
        return !hasStatusEffect(StatusType.STUN) &&
               !hasStatusEffect(StatusType.SLEEP) &&
               !hasStatusEffect(StatusType.FREEZE);
    }
    
    /**
     * Check if character is alive
     */
    public boolean isAlive() {
        return currentHP > 0;
    }
    
    /**
     * Get HP percentage
     */
    public double getHPPercentage() {
        return (double) currentHP / getMaxHP() * 100.0;
    }
    
    // Basic getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getCurrentHP() { return currentHP; }
    public Stats getBaseStats() { return baseStats; }
    public Element getElementAffinity() { return elementAffinity; }
    public List<StatusEffect> getStatusEffects() { return new ArrayList<>(statusEffects); }
    
    // Setters
    public void setLevel(int level) { this.level = level; }
    public void setCurrentHP(int currentHP) { this.currentHP = Math.max(0, Math.min(getMaxHP(), currentHP)); }
    public void setBaseStats(Stats baseStats) { this.baseStats = baseStats; }
    public void setElementAffinity(Element element) { this.elementAffinity = element; }
    
    /**
     * Recalculate derived stats after equipment or level changes
     */
    public void recalculateStats() {
        // Base implementation does nothing - override in subclasses
        // Player/Companion will override to recalculate equipment bonuses
    }
    
    /**
     * Add an ability to this character
     * Override in subclasses that have ability lists
     */
    public void addAbility(com.rpg.combat.Ability ability) {
        // Base implementation does nothing - override in subclasses
    }
    
    /**
     * Get magical attack (alias for getMagicAttack for compatibility)
     */
    public double getMagicalAttack() {
        return getMagicAttack();
    }
    
    /**
     * Get magical defense (alias for getMagicDefense for compatibility)
     */
    public double getMagicalDefense() {
        return getMagicDefense();
    }
    
    @Override
    public String toString() {
        return String.format("%s (Lv %d) - HP: %d/%d", name, level, currentHP, getMaxHP());
    }
}
