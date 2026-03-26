package com.rpg.combat;

/**
 * Target types for abilities
 */
public enum AbilityTarget {
    SINGLE_ENEMY("Single Enemy"),
    ALL_ENEMIES("All Enemies"),
    SELF("Self"),
    SINGLE_ALLY("Single Ally"),
    ALL_ALLIES("All Allies"),
    ALL("Everyone");
    
    private final String displayName;
    
    AbilityTarget(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() { return displayName; }
}
