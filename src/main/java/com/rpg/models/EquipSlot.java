package com.rpg.models;

/**
 * Equipment slot types
 */
public enum EquipSlot {
    WEAPON("Weapon", "Main hand weapon"),
    OFF_HAND("Off-Hand", "Shield or secondary weapon"),
    HEAD("Head", "Helmet or hat"),
    CHEST("Chest", "Armor or clothing"),
    LEGS("Legs", "Leg armor or pants"),
    FEET("Feet", "Boots or shoes"),
    ACCESSORY_1("Accessory", "Ring, necklace, or charm"),
    ACCESSORY_2("Accessory", "Ring, necklace, or charm");
    
    private final String displayName;
    private final String description;
    
    EquipSlot(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
