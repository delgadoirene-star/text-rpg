package com.rpg.models;

/**
 * Types of status effects
 */
public enum StatusType {
    // Buffs
    ATK_UP("Attack Up", true, false),
    DEF_UP("Defense Up", true, false),
    MAG_ATK_UP("Magic Attack Up", true, false),
    MAG_DEF_UP("Magic Defense Up", true, false),
    SPD_UP("Speed Up", true, false),
    CRIT_UP("Critical Up", true, false),
    EVASION_UP("Evasion Up", true, false),
    REGEN("Regeneration", true, true),
    BARRIER("Barrier", true, false),
    COUNTER("Counter", true, false),
    INVULNERABLE("Invulnerable", true, false),
    
    // Debuffs
    ATK_DOWN("Attack Down", false, false),
    DEF_DOWN("Defense Down", false, false),
    MAG_ATK_DOWN("Magic Attack Down", false, false),
    MAG_DEF_DOWN("Magic Defense Down", false, false),
    SPD_DOWN("Speed Down", false, false),
    ACCURACY_DOWN("Accuracy Down", false, false),
    
    // Damage over Time
    POISON("Poison", false, true),
    BURN("Burn", false, true),
    BLEED("Bleed", false, true),
    CURSE("Curse", false, true),
    
    // Crowd Control
    STUN("Stun", false, false),
    SLEEP("Sleep", false, false),
    FREEZE("Freeze", false, false),
    CHARM("Charm", false, false),
    FEAR("Fear", false, false),
    SILENCE("Silence", false, false),
    BLIND("Blind", false, false),
    
    // Special States
    BERSERK("Berserk", false, false),
    HASTE("Haste", true, false),
    SLOW("Slow", false, false),
    REFLECT("Reflect", true, false),
    ABSORB("Absorb", true, false);
    
    private final String displayName;
    private final boolean isBuff;
    private final boolean hasTickDamage;
    
    StatusType(String displayName, boolean isBuff, boolean hasTickDamage) {
        this.displayName = displayName;
        this.isBuff = isBuff;
        this.hasTickDamage = hasTickDamage;
    }
    
    public String getDisplayName() { return displayName; }
    public boolean isBuff() { return isBuff; }
    public boolean isDebuff() { return !isBuff; }
    public boolean hasTickDamage() { return hasTickDamage; }
}
