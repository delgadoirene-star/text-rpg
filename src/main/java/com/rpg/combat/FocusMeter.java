package com.rpg.combat;

/**
 * Manages the Focus meter used for special abilities
 */
public class FocusMeter {
    private double currentFocus;
    private final double maxFocus;
    private double chargeRateModifier;
    
    // Tier costs
    public static final int TIER_1_COST = 25;
    public static final int TIER_2_COST = 50;
    public static final int TIER_3_COST = 100;
    
    public FocusMeter() {
        this.maxFocus = 100.0;
        this.currentFocus = 0.0;
        this.chargeRateModifier = 1.0;
    }
    
    /**
     * Add focus to the meter
     * @param amount Base amount to add
     * @param difficultyMultiplier Difficulty-based multiplier
     */
    public void addFocus(double amount, double difficultyMultiplier) {
        double totalCharge = amount * chargeRateModifier * difficultyMultiplier;
        currentFocus = Math.min(maxFocus, currentFocus + totalCharge);
    }
    
    /**
     * Spend focus for an ability
     * @param cost Focus cost
     * @return true if focus was spent successfully
     */
    public boolean spendFocus(int cost) {
        if (currentFocus >= cost) {
            currentFocus -= cost;
            return true;
        }
        return false;
    }
    
    /**
     * Check if can use ability of given tier
     */
    public boolean canUseTier(int tier) {
        return switch (tier) {
            case 1 -> currentFocus >= TIER_1_COST;
            case 2 -> currentFocus >= TIER_2_COST;
            case 3 -> currentFocus >= TIER_3_COST;
            default -> false;
        };
    }
    
    /**
     * Get focus cost for tier
     */
    public static int getCostForTier(int tier) {
        return switch (tier) {
            case 1 -> TIER_1_COST;
            case 2 -> TIER_2_COST;
            case 3 -> TIER_3_COST;
            default -> 0;
        };
    }
    
    /**
     * Reset focus to 0 (for start of battle)
     */
    public void reset() {
        currentFocus = 0.0;
    }
    
    /**
     * Set focus to specific value (for testing)
     */
    public void setFocus(double focus) {
        this.currentFocus = Math.max(0, Math.min(maxFocus, focus));
    }
    
    // Getters and setters
    public double getCurrentFocus() { return currentFocus; }
    public double getMaxFocus() { return maxFocus; }
    public double getChargeRateModifier() { return chargeRateModifier; }
    public void setChargeRateModifier(double modifier) { this.chargeRateModifier = modifier; }
    
    /**
     * Get focus percentage
     */
    public double getFocusPercentage() {
        return (currentFocus / maxFocus) * 100.0;
    }
    
    @Override
    public String toString() {
        return String.format("Focus: %.0f/%.0f (%.0f%%)", currentFocus, maxFocus, getFocusPercentage());
    }
}
