package com.rpg.models;

import com.rpg.combat.Ability;
import java.util.ArrayList;
import java.util.List;

/**
 * Character class (Warrior, Mage, etc.) with stat growth and abilities
 */
public class GameClass {
    private String id;
    private String name;
    private String description;
    
    // Class hierarchy
    private ClassTier tier; // BASE, ADVANCED, SECRET
    private String prerequisiteClass; // null for base classes
    
    // Requirements
    private int requiredLevel;
    
    // Stat growth per level
    private Stats statGrowth;
    
    // Class-specific abilities
    private List<Ability> classAbilities;
    
    // Stat preferences (for equipment recommendations)
    private List<String> primaryStats; // e.g., ["STR", "VIT"]
    
    public GameClass(String id, String name, ClassTier tier) {
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.description = "";
        this.prerequisiteClass = null;
        this.requiredLevel = 1;
        this.statGrowth = new Stats(1, 1, 1, 1, 1, 1); // Default growth
        this.classAbilities = new ArrayList<>();
        this.primaryStats = new ArrayList<>();
    }
    
    // ==================== Class Tier Enum ====================
    
    public enum ClassTier {
        BASE(1, "Base Class"),
        ADVANCED(2, "Advanced Class"),
        SECRET(3, "Secret Class");
        
        private final int level;
        private final String displayName;
        
        ClassTier(int level, String displayName) {
            this.level = level;
            this.displayName = displayName;
        }
        
        public int getLevel() { return level; }
        public String getDisplayName() { return displayName; }
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private GameClass gameClass;
        
        public Builder(String id, String name, ClassTier tier) {
            gameClass = new GameClass(id, name, tier);
        }
        
        public Builder description(String description) {
            gameClass.description = description;
            return this;
        }
        
        public Builder prerequisite(String classId) {
            gameClass.prerequisiteClass = classId;
            return this;
        }
        
        public Builder requiredLevel(int level) {
            gameClass.requiredLevel = level;
            return this;
        }
        
        public Builder statGrowth(Stats growth) {
            gameClass.statGrowth = growth;
            return this;
        }
        
        public Builder statGrowth(int str, int dex, int vit, int intel, int wis, int luk) {
            gameClass.statGrowth = new Stats(str, dex, vit, intel, wis, luk);
            return this;
        }
        
        public Builder addAbility(Ability ability) {
            gameClass.classAbilities.add(ability);
            return this;
        }
        
        public Builder primaryStats(String... stats) {
            for (String stat : stats) {
                gameClass.primaryStats.add(stat);
            }
            return this;
        }
        
        public GameClass build() {
            return gameClass;
        }
    }
    
    // ==================== Getters ====================
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ClassTier getTier() { return tier; }
    public String getPrerequisiteClass() { return prerequisiteClass; }
    public int getRequiredLevel() { return requiredLevel; }
    public Stats getStatGrowth() { return statGrowth; }
    public List<Ability> getClassAbilities() { return new ArrayList<>(classAbilities); }
    public List<String> getPrimaryStats() { return new ArrayList<>(primaryStats); }
    
    // ==================== Utility ====================
    
    /**
     * Check if this class can use equipment/abilities from another class
     * (For equipment restrictions - advanced classes can use base class equipment)
     */
    public boolean canUse(String classId) {
        if (this.id.equals(classId)) return true;
        if (prerequisiteClass != null && prerequisiteClass.equals(classId)) return true;
        return false;
    }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s)", name, tier.getDisplayName()));
        
        if (requiredLevel > 1) {
            sb.append(String.format(" [Lv.%d+]", requiredLevel));
        }
        
        if (prerequisiteClass != null) {
            sb.append(String.format(" [Requires: %s]", prerequisiteClass));
        }
        
        return sb.toString();
    }
    
    /**
     * Get detailed class info
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║  %s (%s)\n", name, tier.getDisplayName()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  %s\n", description));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        
        // Requirements
        if (requiredLevel > 1 || prerequisiteClass != null) {
            sb.append("║  REQUIREMENTS:\n");
            if (requiredLevel > 1) {
                sb.append(String.format("║    Level %d+\n", requiredLevel));
            }
            if (prerequisiteClass != null) {
                sb.append(String.format("║    Must be: %s\n", prerequisiteClass));
            }
            sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        }
        
        // Stat growth
        sb.append("║  STAT GROWTH PER LEVEL:\n");
        sb.append(String.format("║    STR +%d | DEX +%d | VIT +%d\n",
            statGrowth.getStrength(), statGrowth.getDexterity(), statGrowth.getVitality()));
        sb.append(String.format("║    INT +%d | WIS +%d | LUK +%d\n",
            statGrowth.getIntelligence(), statGrowth.getWisdom(), statGrowth.getLuck()));
        
        // Primary stats
        if (!primaryStats.isEmpty()) {
            sb.append("║  PRIMARY STATS: ");
            sb.append(String.join(", ", primaryStats)).append("\n");
        }
        
        // Abilities
        if (!classAbilities.isEmpty()) {
            sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
            sb.append("║  CLASS ABILITIES:\n");
            for (Ability ability : classAbilities) {
                sb.append(String.format("║    • %s (T%d)\n", ability.getName(), ability.getTier()));
                sb.append(String.format("║      %s\n", ability.getDescription()));
            }
        }
        
        sb.append("╚═══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
}
