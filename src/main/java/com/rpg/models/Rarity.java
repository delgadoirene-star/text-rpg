package com.rpg.models;

/**
 * Item rarity levels
 */
public enum Rarity {
    COMMON("Common", 1.0),
    UNCOMMON("Uncommon", 1.2),
    RARE("Rare", 1.5),
    EPIC("Epic", 2.0),
    LEGENDARY("Legendary", 3.0),
    MYTHIC("Mythic", 5.0);
    
    private final String displayName;
    private final double statMultiplier;
    
    Rarity(String displayName, double statMultiplier) {
        this.displayName = displayName;
        this.statMultiplier = statMultiplier;
    }
    
    public String getDisplayName() { return displayName; }
    public double getStatMultiplier() { return statMultiplier; }
}
