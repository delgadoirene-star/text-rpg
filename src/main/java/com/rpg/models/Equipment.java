package com.rpg.models;

/**
 * Equipment item that can be equipped by characters
 */
public class Equipment {
    private String id;
    private String name;
    private String description;
    private EquipSlot slot;
    private Rarity rarity;
    
    // Requirements
    private int requiredLevel;
    private String requiredClass; // null = any class can equip
    
    // Stat bonuses
    private Stats bonusStats;
    
    // Special effects (passive abilities, procs, etc.)
    private EquipmentEffect specialEffect;
    
    // Value
    private int goldValue;
    
    public Equipment(String id, String name, EquipSlot slot, Rarity rarity) {
        this.id = id;
        this.name = name;
        this.slot = slot;
        this.rarity = rarity;
        this.description = "";
        this.requiredLevel = 1;
        this.requiredClass = null;
        this.bonusStats = new Stats(0, 0, 0, 0, 0, 0);
        this.specialEffect = null;
        this.goldValue = 0;
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private Equipment equipment;
        
        public Builder(String id, String name, EquipSlot slot, Rarity rarity) {
            equipment = new Equipment(id, name, slot, rarity);
        }
        
        public Builder description(String description) {
            equipment.description = description;
            return this;
        }
        
        public Builder requiredLevel(int level) {
            equipment.requiredLevel = level;
            return this;
        }
        
        public Builder requiredClass(String className) {
            equipment.requiredClass = className;
            return this;
        }
        
        public Builder bonusStats(Stats stats) {
            equipment.bonusStats = stats;
            return this;
        }
        
        public Builder bonusStats(int str, int dex, int vit, int intel, int wis, int luk) {
            equipment.bonusStats = new Stats(str, dex, vit, intel, wis, luk);
            return this;
        }
        
        public Builder specialEffect(EquipmentEffect effect) {
            equipment.specialEffect = effect;
            return this;
        }
        
        public Builder goldValue(int value) {
            equipment.goldValue = value;
            return this;
        }
        
        public Equipment build() {
            return equipment;
        }
    }
    
    // ==================== Getters ====================
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public EquipSlot getSlot() { return slot; }
    public Rarity getRarity() { return rarity; }
    public int getRequiredLevel() { return requiredLevel; }
    public String getRequiredClass() { return requiredClass; }
    public Stats getBonusStats() { return bonusStats; }
    public EquipmentEffect getSpecialEffect() { return specialEffect; }
    public int getGoldValue() { return goldValue; }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Name with rarity color
        sb.append(String.format("[%s] %s", rarity.getDisplayName(), name));
        
        // Slot
        sb.append(String.format(" (%s)", slot.getDisplayName()));
        
        // Requirements
        if (requiredLevel > 1) {
            sb.append(String.format(" [Lv.%d+]", requiredLevel));
        }
        if (requiredClass != null) {
            sb.append(String.format(" [%s only]", requiredClass));
        }
        
        return sb.toString();
    }
    
    /**
     * Get detailed equipment info
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        sb.append("  ").append(description).append("\n");
        
        // Show stat bonuses
        if (bonusStats != null && !bonusStats.isZero()) {
            sb.append("  Stats: ");
            if (bonusStats.getStrength() > 0) sb.append(String.format("STR+%d ", bonusStats.getStrength()));
            if (bonusStats.getDexterity() > 0) sb.append(String.format("DEX+%d ", bonusStats.getDexterity()));
            if (bonusStats.getVitality() > 0) sb.append(String.format("VIT+%d ", bonusStats.getVitality()));
            if (bonusStats.getIntelligence() > 0) sb.append(String.format("INT+%d ", bonusStats.getIntelligence()));
            if (bonusStats.getWisdom() > 0) sb.append(String.format("WIS+%d ", bonusStats.getWisdom()));
            if (bonusStats.getLuck() > 0) sb.append(String.format("LUK+%d ", bonusStats.getLuck()));
            sb.append("\n");
        }
        
        // Show special effect
        if (specialEffect != null) {
            sb.append("  Effect: ").append(specialEffect.getDescription()).append("\n");
        }
        
        // Show value
        sb.append(String.format("  Value: %d gold", goldValue));
        
        return sb.toString();
    }
    
    // ==================== Equipment Effect Interface ====================
    
    /**
     * Interface for special equipment effects
     */
    public interface EquipmentEffect {
        void apply(Character character);
        void remove(Character character);
        String getDescription();
    }
}
