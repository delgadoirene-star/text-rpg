package com.rpg.combat;

import com.rpg.models.Element;
import com.rpg.models.StatusType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ability/skill that can be used in combat
 */
public class Ability {
    private final String id;
    private final String name;
    private final String description;
    private final int tier;                    // 1, 2, or 3
    private final int focusCost;
    private final DamageType damageType;
    private final Element element;
    private final double basePower;
    private final AbilityTarget targetType;
    private final List<StatusApplication> statusEffects;
    private final int cooldown;
    private int currentCooldown;
    
    // Constructor
    private Ability(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.tier = builder.tier;
        this.focusCost = FocusMeter.getCostForTier(tier);
        this.damageType = builder.damageType;
        this.element = builder.element;
        this.basePower = builder.basePower;
        this.targetType = builder.targetType;
        this.statusEffects = builder.statusEffects;
        this.cooldown = builder.cooldown;
        this.currentCooldown = 0;
    }
    
    /**
     * Check if ability is ready to use (not on cooldown)
     */
    public boolean isReady() {
        return currentCooldown == 0;
    }
    
    /**
     * Use the ability (start cooldown)
     */
    public void use() {
        currentCooldown = cooldown;
    }
    
    /**
     * Reduce cooldown by 1 turn
     */
    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }
    
    /**
     * Reset cooldown (for start of battle)
     */
    public void resetCooldown() {
        currentCooldown = 0;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getTier() { return tier; }
    public int getFocusCost() { return focusCost; }
    public DamageType getDamageType() { return damageType; }
    public Element getElement() { return element; }
    public double getBasePower() { return basePower; }
    public AbilityTarget getTargetType() { return targetType; }
    public List<StatusApplication> getStatusEffects() { return statusEffects; }
    public int getCooldown() { return cooldown; }
    public int getCurrentCooldown() { return currentCooldown; }
    
    @Override
    public String toString() {
        String cooldownInfo = cooldown > 0 ? " [CD: " + cooldown + "]" : "";
        String focusInfo = " [" + focusCost + " Focus]";
        return name + focusInfo + cooldownInfo;
    }
    
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ").append(name).append(" ===\n");
        info.append("Tier: ").append(tier).append(" | Focus: ").append(focusCost).append("\n");
        info.append(description).append("\n\n");
        info.append("Type: ").append(damageType.getDisplayName());
        info.append(" | Element: ").append(element.name()).append("\n");
        info.append("Power: ").append(basePower);
        info.append(" | Target: ").append(targetType.getDisplayName()).append("\n");
        
        if (!statusEffects.isEmpty()) {
            info.append("\nStatus Effects:\n");
            for (StatusApplication status : statusEffects) {
                info.append("  - ").append(status.toString()).append("\n");
            }
        }
        
        if (cooldown > 0) {
            info.append("\nCooldown: ").append(cooldown).append(" turns\n");
        }
        
        return info.toString();
    }
    
    /**
     * Builder pattern for creating abilities
     */
    public static class Builder {
        // Required
        private final String id;
        private final String name;
        private final int tier;
        
        // Optional with defaults
        private String description = "";
        private DamageType damageType = DamageType.PHYSICAL;
        private Element element = Element.NEUTRAL;
        private double basePower = 1.0;
        private AbilityTarget targetType = AbilityTarget.SINGLE_ENEMY;
        private List<StatusApplication> statusEffects = new ArrayList<>();
        private int cooldown = 0;
        
        public Builder(String id, String name, int tier) {
            this.id = id;
            this.name = name;
            this.tier = tier;
        }
        
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        
        public Builder damageType(DamageType damageType) {
            this.damageType = damageType;
            return this;
        }
        
        public Builder element(Element element) {
            this.element = element;
            return this;
        }
        
        public Builder basePower(double basePower) {
            this.basePower = basePower;
            return this;
        }
        
        public Builder targetType(AbilityTarget targetType) {
            this.targetType = targetType;
            return this;
        }
        
        public Builder addStatusEffect(StatusType type, int duration, double potency, int chance) {
            this.statusEffects.add(new StatusApplication(type, duration, potency, chance));
            return this;
        }
        
        public Builder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }
        
        public Ability build() {
            return new Ability(this);
        }
    }
    
    /**
     * Represents a status effect that can be applied by an ability
     */
    public static class StatusApplication {
        private final StatusType type;
        private final int duration;
        private final double potency;
        private final int chance; // Percentage chance to apply (100 = always)
        
        public StatusApplication(StatusType type, int duration, double potency, int chance) {
            this.type = type;
            this.duration = duration;
            this.potency = potency;
            this.chance = chance;
        }
        
        public StatusType getType() { return type; }
        public int getDuration() { return duration; }
        public double getPotency() { return potency; }
        public int getChance() { return chance; }
        
        @Override
        public String toString() {
            String chanceStr = chance < 100 ? " (" + chance + "% chance)" : "";
            return String.format("%s (%.0f potency, %d turns)%s", 
                type.getDisplayName(), potency, duration, chanceStr);
        }
    }
}
