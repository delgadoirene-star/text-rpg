package com.rpg.models;

import com.rpg.combat.FocusMeter;
import com.rpg.combat.Ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Companion character that can join the player's party
 * Similar to Player but pre-configured with personality, story, and loyalty system
 */
public class Companion extends Character {
    private String personalityType; // e.g., "Brave", "Cautious", "Ruthless", "Kind"
    private String backstory;
    
    // Class and progression
    private Background background;
    private GameClass currentClass;
    private FocusMeter focusMeter;
    
    // Equipment
    private Map<EquipSlot, Equipment> equipment;
    
    // Relationship system
    private int loyaltyLevel; // 0-100
    private int friendshipLevel; // 0-10 (affects combat bonuses)
    private Map<String, Boolean> personalQuests; // Quest ID → Completed
    
    // Recruitment
    private boolean recruited;
    private String recruitmentLocation;
    private String recruitmentFlag; // Flag required to recruit (optional)
    
    // Combat preferences (AI hints when not controlled)
    private String combatRole; // "Tank", "DPS", "Support", "Balanced"
    private String preferredTarget; // "Weakest", "Strongest", "RandomElement", etc.
    
    // Dialogue and interactions
    private Map<String, String> dialogueResponses; // Situation → Response
    
    // Abilities
    private List<Ability> abilities;
    
    public Companion(String id, String name, int level, Stats baseStats, 
                     Element elementAffinity, Background background, GameClass startingClass) {
        super(id, name, level, baseStats, elementAffinity);
        this.background = background;
        this.currentClass = startingClass;
        this.focusMeter = new FocusMeter();
        
        // Initialize equipment
        this.equipment = new HashMap<>();
        for (EquipSlot slot : EquipSlot.values()) {
            equipment.put(slot, null);
        }
        
        // Initialize relationship
        this.loyaltyLevel = 50; // Neutral start
        this.friendshipLevel = 0;
        this.personalQuests = new HashMap<>();
        
        // Initialize recruitment
        this.recruited = false;
        this.recruitmentLocation = "";
        this.recruitmentFlag = null;
        
        // Initialize personality
        this.personalityType = "Balanced";
        this.backstory = "";
        this.combatRole = "Balanced";
        this.preferredTarget = "Random";
        
        // Initialize dialogue
        this.dialogueResponses = new HashMap<>();
        
        // Initialize abilities
        this.abilities = new ArrayList<>();
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private Companion companion;
        
        public Builder(String id, String name, Background background, GameClass startingClass) {
            Stats defaultStats = new Stats(10, 10, 10, 10, 10, 10);
            companion = new Companion(id, name, 1, defaultStats, Element.NONE, background, startingClass);
        }
        
        public Builder level(int level) {
            companion.setLevel(level);
            return this;
        }
        
        public Builder stats(Stats stats) {
            // Apply stat growth for levels 2+
            for (int i = 2; i <= companion.getLevel(); i++) {
                Stats growth = companion.currentClass.getStatGrowth();
                stats.add(growth);
            }
            companion.setBaseStats(stats);
            return this;
        }
        
        public Builder element(Element element) {
            companion.setElementAffinity(element);
            return this;
        }
        
        public Builder personality(String type) {
            companion.personalityType = type;
            return this;
        }
        
        public Builder backstory(String story) {
            companion.backstory = story;
            return this;
        }
        
        public Builder combatRole(String role) {
            companion.combatRole = role;
            return this;
        }
        
        public Builder preferredTarget(String target) {
            companion.preferredTarget = target;
            return this;
        }
        
        public Builder recruitmentLocation(String location) {
            companion.recruitmentLocation = location;
            return this;
        }
        
        public Builder recruitmentFlag(String flag) {
            companion.recruitmentFlag = flag;
            return this;
        }
        
        public Builder startingLoyalty(int loyalty) {
            companion.loyaltyLevel = Math.max(0, Math.min(100, loyalty));
            return this;
        }
        
        public Builder addAbility(Ability ability) {
            companion.addAbility(ability);
            return this;
        }
        
        public Builder addDialogue(String situation, String response) {
            companion.dialogueResponses.put(situation, response);
            return this;
        }
        
        public Builder addPersonalQuest(String questId) {
            companion.personalQuests.put(questId, false);
            return this;
        }
        
        public Companion build() {
            companion.recalculateStats();
            return companion;
        }
    }
    
    // ==================== Equipment Management ====================
    
    public boolean equip(Equipment item) {
        if (item == null) return false;
        if (!canEquip(item)) return false;
        
        EquipSlot slot = item.getSlot();
        Equipment oldItem = equipment.get(slot);
        
        if (oldItem != null) {
            unequip(slot);
        }
        
        equipment.put(slot, item);
        applyEquipmentStats(item, true);
        return true;
    }
    
    public Equipment unequip(EquipSlot slot) {
        Equipment item = equipment.get(slot);
        if (item == null) return null;
        
        equipment.put(slot, null);
        applyEquipmentStats(item, false);
        return item;
    }
    
    public boolean canEquip(Equipment item) {
        if (item == null) return false;
        if (getLevel() < item.getRequiredLevel()) return false;
        if (item.getRequiredClass() != null && 
            !currentClass.canUse(item.getRequiredClass())) {
            return false;
        }
        return true;
    }
    
    private void applyEquipmentStats(Equipment item, boolean equipping) {
        int multiplier = equipping ? 1 : -1;
        
        Stats bonusStats = item.getBonusStats();
        if (bonusStats != null) {
            Stats currentStats = getBaseStats();
            currentStats.setStrength(currentStats.getStrength() + (bonusStats.getStrength() * multiplier));
            currentStats.setDexterity(currentStats.getDexterity() + (bonusStats.getDexterity() * multiplier));
            currentStats.setVitality(currentStats.getVitality() + (bonusStats.getVitality() * multiplier));
            currentStats.setIntelligence(currentStats.getIntelligence() + (bonusStats.getIntelligence() * multiplier));
            currentStats.setWisdom(currentStats.getWisdom() + (bonusStats.getWisdom() * multiplier));
            currentStats.setLuck(currentStats.getLuck() + (bonusStats.getLuck() * multiplier));
            recalculateStats();
        }
        
        if (equipping && item.getSpecialEffect() != null) {
            item.getSpecialEffect().apply(this);
        } else if (!equipping && item.getSpecialEffect() != null) {
            item.getSpecialEffect().remove(this);
        }
    }
    
    public Equipment getEquipment(EquipSlot slot) {
        return equipment.get(slot);
    }
    
    public Map<EquipSlot, Equipment> getAllEquipment() {
        return new HashMap<>(equipment);
    }
    
    // ==================== Focus Meter ====================
    
    public FocusMeter getFocusMeter() {
        return focusMeter;
    }
    
    public void addFocus(int amount) {
        focusMeter.addFocus(amount, 1.0);
    }
    
    public boolean canUseAbility(Ability ability) {
        return focusMeter.canAfford(ability.getFocusCost());
    }
    
    public boolean spendFocus(Ability ability) {
        return focusMeter.spendFocus(ability.getFocusCost());
    }
    
    // ==================== Class System ====================
    
    public GameClass getCurrentClass() {
        return currentClass;
    }
    
    public boolean changeClass(GameClass newClass) {
        if (!canChangeToClass(newClass)) return false;
        
        this.currentClass = newClass;
        
        for (Ability ability : newClass.getClassAbilities()) {
            addAbility(ability);
        }
        
        return true;
    }
    
    /**
     * Add an ability to the companion
     */
    @Override
    public void addAbility(Ability ability) {
        if (ability != null) {
            abilities.add(ability);
        }
    }
    
    /**
     * Get all abilities this companion has
     */
    public List<Ability> getAbilities() {
        return new ArrayList<>(abilities);
    }
    
    /**
     * Recalculate derived stats after equipment or level changes
     */
    @Override
    public void recalculateStats() {
        // Update max HP if it changed
        int newMaxHP = getMaxHP();
        if (currentHP > newMaxHP) {
            currentHP = newMaxHP;
        }
    }
    
    public boolean canChangeToClass(GameClass newClass) {
        if (getLevel() < newClass.getRequiredLevel()) return false;
        if (!background.canAccess(newClass)) return false;
        if (newClass.getPrerequisiteClass() != null && 
            !currentClass.getId().equals(newClass.getPrerequisiteClass())) {
            return false;
        }
        return true;
    }
    
    public Background getBackground() {
        return background;
    }
    
    // ==================== Loyalty & Relationship ====================
    
    /**
     * Change loyalty level
     * @param amount Positive to increase, negative to decrease
     */
    public void changeLoyalty(int amount) {
        loyaltyLevel += amount;
        loyaltyLevel = Math.max(0, Math.min(100, loyaltyLevel));
        
        // Check for friendship level up
        int newFriendshipLevel = loyaltyLevel / 10;
        if (newFriendshipLevel > friendshipLevel) {
            friendshipLevel = newFriendshipLevel;
            onFriendshipLevelUp();
        }
    }
    
    /**
     * Called when friendship levels up
     */
    private void onFriendshipLevelUp() {
        System.out.println(String.format(
            ">>> %s's friendship level increased to %d! New combat bonuses unlocked!",
            getName(), friendshipLevel
        ));
    }
    
    public int getLoyaltyLevel() {
        return loyaltyLevel;
    }
    
    public int getFriendshipLevel() {
        return friendshipLevel;
    }
    
    /**
     * Get loyalty description
     */
    public String getLoyaltyDescription() {
        if (loyaltyLevel >= 90) return "Devoted";
        if (loyaltyLevel >= 75) return "Loyal";
        if (loyaltyLevel >= 60) return "Friendly";
        if (loyaltyLevel >= 40) return "Neutral";
        if (loyaltyLevel >= 25) return "Wary";
        if (loyaltyLevel >= 10) return "Distrustful";
        return "Hostile";
    }
    
    /**
     * Get combat bonus based on friendship level
     */
    public double getFriendshipBonus() {
        return 1.0 + (friendshipLevel * 0.05); // 5% per friendship level
    }
    
    // ==================== Personal Quests ====================
    
    public void completePersonalQuest(String questId) {
        if (personalQuests.containsKey(questId)) {
            personalQuests.put(questId, true);
            changeLoyalty(20); // Completing personal quest greatly increases loyalty
        }
    }
    
    public boolean hasCompletedQuest(String questId) {
        return personalQuests.getOrDefault(questId, false);
    }
    
    public boolean hasUnfinishedPersonalQuests() {
        return personalQuests.values().stream().anyMatch(completed -> !completed);
    }
    
    public Map<String, Boolean> getPersonalQuests() {
        return new HashMap<>(personalQuests);
    }
    
    // ==================== Recruitment ====================
    
    public void recruit() {
        recruited = true;
        System.out.println(String.format(">>> %s has joined your party!", getName()));
    }
    
    public boolean isRecruited() {
        return recruited;
    }
    
    public String getRecruitmentLocation() {
        return recruitmentLocation;
    }
    
    public String getRecruitmentFlag() {
        return recruitmentFlag;
    }
    
    public boolean canRecruit(Map<String, Boolean> flags) {
        if (recruited) return false;
        if (recruitmentFlag == null) return true; // No flag requirement
        return flags.getOrDefault(recruitmentFlag, false);
    }
    
    // ==================== AI / Combat Behavior ====================
    
    public String getCombatRole() {
        return combatRole;
    }
    
    public String getPreferredTarget() {
        return preferredTarget;
    }
    
    // ==================== Dialogue ====================
    
    public String getDialogue(String situation) {
        return dialogueResponses.getOrDefault(situation, 
            "..." // Default silent response
        );
    }
    
    public void addDialogue(String situation, String response) {
        dialogueResponses.put(situation, response);
    }
    
    // ==================== Getters ====================
    
    public String getPersonalityType() {
        return personalityType;
    }
    
    public String getBackstory() {
        return backstory;
    }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        return String.format("%s (Lv.%d %s %s) - HP: %d/%d | %s",
            getName(), getLevel(), background.getDisplayName(), 
            currentClass.getName(), getCurrentHP(), getMaxHP(),
            getLoyaltyDescription());
    }
    
    /**
     * Get detailed companion sheet
     */
    public String getCompanionSheet() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║  %s - Level %d %s %s\n", 
            getName(), getLevel(), background.getDisplayName(), currentClass.getName()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  Personality: %s | Combat Role: %s\n",
            personalityType, combatRole));
        sb.append(String.format("║  Loyalty: %s (%d/100) | Friendship: Lv.%d (%.0f%% bonus)\n",
            getLoyaltyDescription(), loyaltyLevel, friendshipLevel, (getFriendshipBonus() - 1.0) * 100));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  HP: %d/%d | Focus: %d/100 | Element: %s\n",
            getCurrentHP(), getMaxHP(), focusMeter.getCurrentFocus(), getElementAffinity()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  BACKSTORY:\n");
        sb.append(String.format("║  %s\n", backstory));
        
        // Personal quests
        if (!personalQuests.isEmpty()) {
            sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
            sb.append("║  PERSONAL QUESTS:\n");
            for (Map.Entry<String, Boolean> quest : personalQuests.entrySet()) {
                String status = quest.getValue() ? "✓" : "○";
                sb.append(String.format("║  %s %s\n", status, quest.getKey()));
            }
        }
        
        sb.append("╚═══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
}
