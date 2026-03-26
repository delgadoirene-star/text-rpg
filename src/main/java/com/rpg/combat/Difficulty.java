package com.rpg.combat;

/**
 * Difficulty modes that affect combat mechanics
 */
public enum Difficulty {
    STORY("Story Mode", 
          "Focus on narrative, easier combat", 
          1.5,    // Focus charge rate multiplier
          false,  // No adaptive AI
          false), // No damage caps
    
    NORMAL("Normal", 
           "Balanced experience", 
           1.0,    // Standard focus charge
           true,   // Light adaptive AI
           false), // No damage caps
    
    HARD("Hard Mode", 
         "For challenge seekers", 
         1.0,    // Standard focus charge
         true,   // Full adaptive AI
         true);  // Soft damage caps
    
    private final String displayName;
    private final String description;
    private final double focusChargeMultiplier;
    private final boolean adaptiveAI;
    private final boolean hasDamageCaps;
    
    Difficulty(String displayName, String description, double focusChargeMultiplier, 
               boolean adaptiveAI, boolean hasDamageCaps) {
        this.displayName = displayName;
        this.description = description;
        this.focusChargeMultiplier = focusChargeMultiplier;
        this.adaptiveAI = adaptiveAI;
        this.hasDamageCaps = hasDamageCaps;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public double getFocusChargeMultiplier() { return focusChargeMultiplier; }
    public boolean hasAdaptiveAI() { return adaptiveAI; }
    public boolean hasDamageCaps() { return hasDamageCaps; }
}
