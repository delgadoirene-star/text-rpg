package com.rpg.combat;

import com.rpg.models.StatusType;

/**
 * Represents a status effect applied to a character
 */
public class StatusEffect {
    private final StatusType type;
    private int duration;        // Turns remaining
    private int stacks;          // Number of stacks (for stackable effects)
    private final double potency;      // Effect strength
    private final boolean isStackable;
    private final boolean isRefreshable;
    private final int maxStacks;
    
    public StatusEffect(StatusType type, int duration, double potency, 
                       boolean isStackable, boolean isRefreshable, int maxStacks) {
        this.type = type;
        this.duration = duration;
        this.stacks = 1;
        this.potency = potency;
        this.isStackable = isStackable;
        this.isRefreshable = isRefreshable;
        this.maxStacks = maxStacks;
    }
    
    /**
     * Constructor for non-stackable effects
     */
    public StatusEffect(StatusType type, int duration, double potency) {
        this(type, duration, potency, false, true, 1);
    }
    
    /**
     * Decrease duration by one turn
     * @return true if effect expired
     */
    public boolean decrementDuration() {
        duration--;
        return duration <= 0;
    }
    
    /**
     * Add stacks if stackable
     * @param amount Number of stacks to add
     * @return true if stacks were added
     */
    public boolean addStacks(int amount) {
        if (!isStackable) return false;
        stacks = Math.min(stacks + amount, maxStacks);
        return true;
    }
    
    /**
     * Refresh duration (for refreshable effects)
     * @param newDuration New duration value
     * @return true if duration was refreshed
     */
    public boolean refresh(int newDuration) {
        if (!isRefreshable) return false;
        this.duration = Math.max(this.duration, newDuration);
        return true;
    }
    
    /**
     * Calculate tick damage (for DoT effects)
     */
    public double getTickDamage() {
        if (!type.hasTickDamage()) return 0.0;
        return potency * stacks;
    }
    
    /**
     * Get stat modifier (for buff/debuff effects)
     */
    public double getStatModifier() {
        return potency * stacks;
    }
    
    // Getters
    public StatusType getType() { return type; }
    public int getDuration() { return duration; }
    public int getStacks() { return stacks; }
    public double getPotency() { return potency; }
    public boolean isStackable() { return isStackable; }
    public boolean isRefreshable() { return isRefreshable; }
    public int getMaxStacks() { return maxStacks; }
    public boolean isExpired() { return duration <= 0; }
    
    @Override
    public String toString() {
        String stackInfo = isStackable && stacks > 1 ? " x" + stacks : "";
        return String.format("%s%s (%d turns)", type.getDisplayName(), stackInfo, duration);
    }
}
