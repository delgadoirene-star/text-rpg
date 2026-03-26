package com.rpg.models;

/**
 * Element types with advantage wheel mechanics
 * Fire > Wind > Earth > Water > Fire
 * Light <-> Dark
 */
public enum Element {
    FIRE,
    WATER,
    EARTH,
    WIND,
    LIGHT,
    DARK,
    NEUTRAL;
    
    /**
     * Check if this element has advantage over another
     * @param other The element to check against
     * @return true if this element is strong against other
     */
    public boolean hasAdvantageOver(Element other) {
        return switch (this) {
            case FIRE -> other == WIND;
            case WIND -> other == EARTH;
            case EARTH -> other == WATER;
            case WATER -> other == FIRE;
            case LIGHT -> other == DARK;
            case DARK -> other == LIGHT;
            case NEUTRAL -> false;
        };
    }
    
    /**
     * Get damage multiplier against another element
     * @param other The defending element
     * @return Damage multiplier (2.0 for advantage, 0.5 for disadvantage, 1.0 neutral)
     */
    public double getDamageMultiplier(Element other) {
        if (this == NEUTRAL || other == NEUTRAL) return 1.0;
        if (hasAdvantageOver(other)) return 2.0;
        if (other.hasAdvantageOver(this)) return 0.5;
        return 1.0;
    }
}
