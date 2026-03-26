package com.rpg.combat;

/**
 * Types of damage that can be dealt
 */
public enum DamageType {
    PHYSICAL("Physical", false),
    MAGICAL("Magical", false),
    TRUE("True", true),      // Ignores defense
    HEALING("Healing", false); // Negative damage (heals)
    
    private final String displayName;
    private final boolean ignoresDefense;
    
    DamageType(String displayName, boolean ignoresDefense) {
        this.displayName = displayName;
        this.ignoresDefense = ignoresDefense;
    }
    
    public String getDisplayName() { return displayName; }
    public boolean ignoresDefense() { return ignoresDefense; }
}
