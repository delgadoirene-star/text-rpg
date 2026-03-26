package com.rpg.models;

import com.rpg.combat.FocusMeter;
import com.rpg.combat.Ability;
import com.rpg.combat.CombatModifiers;
import com.rpg.systems.AlignmentSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Player character with equipment, focus meter, and progression systems
 */
public class Player extends Character {
    private Background background;
    private GameClass currentClass;
    private FocusMeter focusMeter;
    private AlignmentSystem alignment;
    
    // Equipment slots
    private Map<EquipSlot, Equipment> equipment;
    
    // Progression
    private int experience;
    private int experienceToNextLevel;
    
    // Stats tracking
    private int totalDamageDealt;
    private int totalDamageTaken;
    private int totalHealing;
    private int enemiesDefeated;
    
    public Player(String name, Background background, GameClass startingClass, 
                  Stats baseStats, Element elementAffinity) {
        super("player", name, 1, baseStats, elementAffinity);
        this.background = background;
        this.currentClass = startingClass;
        this.focusMeter = new FocusMeter();
        this.alignment = new AlignmentSystem();
        this.equipment = new HashMap<>();
        this.experience = 0;
        this.experienceToNextLevel = calculateExpForNextLevel(1);
        
        // Initialize all equipment slots as empty
        for (EquipSlot slot : EquipSlot.values()) {
            equipment.put(slot, null);
        }
        
        // Initialize stats tracking
        this.totalDamageDealt = 0;
        this.totalDamageTaken = 0;
        this.totalHealing = 0;
        this.enemiesDefeated = 0;
    }
    
    // ==================== Equipment Management ====================
    
    /**
     * Equip an item to the appropriate slot
     */
    public boolean equip(Equipment item) {
        if (item == null) return false;
        
        // Check if player can use this equipment (class/level requirements)
        if (!canEquip(item)) {
            return false;
        }
        
        EquipSlot slot = item.getSlot();
        Equipment oldItem = equipment.get(slot);
        
        // Unequip old item first
        if (oldItem != null) {
            unequip(slot);
        }
        
        // Equip new item
        equipment.put(slot, item);
        applyEquipmentStats(item, true);
        
        return true;
    }
    
    /**
     * Unequip an item from a slot
     */
    public Equipment unequip(EquipSlot slot) {
        Equipment item = equipment.get(slot);
        if (item == null) return null;
        
        equipment.put(slot, null);
        applyEquipmentStats(item, false);
        
        return item;
    }
    
    /**
     * Check if player can equip an item
     */
    public boolean canEquip(Equipment item) {
        if (item == null) return false;
        
        // Check level requirement
        if (getLevel() < item.getRequiredLevel()) {
            return false;
        }
        
        // Check class restriction
        if (item.getRequiredClass() != null && 
            !currentClass.canUse(item.getRequiredClass())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Apply or remove equipment stat bonuses
     */
    private void applyEquipmentStats(Equipment item, boolean equipping) {
        int multiplier = equipping ? 1 : -1;
        
        Stats bonusStats = item.getBonusStats();
        if (bonusStats != null) {
            // Apply stat bonuses
            Stats currentStats = getBaseStats();
            currentStats.setStrength(currentStats.getStrength() + (bonusStats.getStrength() * multiplier));
            currentStats.setDexterity(currentStats.getDexterity() + (bonusStats.getDexterity() * multiplier));
            currentStats.setVitality(currentStats.getVitality() + (bonusStats.getVitality() * multiplier));
            currentStats.setIntelligence(currentStats.getIntelligence() + (bonusStats.getIntelligence() * multiplier));
            currentStats.setWisdom(currentStats.getWisdom() + (bonusStats.getWisdom() * multiplier));
            currentStats.setLuck(currentStats.getLuck() + (bonusStats.getLuck() * multiplier));
            
            // Recalculate derived stats
            recalculateStats();
        }
        
        // Apply special effects
        if (equipping && item.getSpecialEffect() != null) {
            item.getSpecialEffect().apply(this);
        } else if (!equipping && item.getSpecialEffect() != null) {
            item.getSpecialEffect().remove(this);
        }
    }
    
    /**
     * Get equipped item in a slot
     */
    public Equipment getEquipment(EquipSlot slot) {
        return equipment.get(slot);
    }
    
    /**
     * Get all equipped items
     */
    public Map<EquipSlot, Equipment> getAllEquipment() {
        return new HashMap<>(equipment);
    }
    
    // ==================== Focus Meter ====================
    
    /**
     * Get the player's focus meter
     */
    public FocusMeter getFocusMeter() {
        return focusMeter;
    }
    
    /**
     * Add focus (after dealing damage, taking damage, etc.)
     */
    public void addFocus(int amount) {
        focusMeter.addFocus(amount);
    }
    
    /**
     * Check if player can use an ability (focus cost)
     */
    public boolean canUseAbility(Ability ability) {
        return focusMeter.canAfford(ability.getFocusCost());
    }
    
    /**
     * Spend focus to use an ability
     */
    public boolean spendFocus(Ability ability) {
        return focusMeter.spendFocus(ability.getFocusCost());
    }
    
    // ==================== Class System ====================
    
    /**
     * Get current class
     */
    public GameClass getCurrentClass() {
        return currentClass;
    }
    
    /**
     * Change class (for specialization/advancement)
     */
    public boolean changeClass(GameClass newClass) {
        // Check if player meets requirements
        if (!canChangeToClass(newClass)) {
            return false;
        }
        
        // Apply class change
        this.currentClass = newClass;
        
        // Learn new class abilities
        for (Ability ability : newClass.getClassAbilities()) {
            addAbility(ability);
        }
        
        return true;
    }
    
    /**
     * Check if player can change to a class
     */
    public boolean canChangeToClass(GameClass newClass) {
        // Check level requirement
        if (getLevel() < newClass.getRequiredLevel()) {
            return false;
        }
        
        // Check if background allows this class
        if (!background.canAccess(newClass)) {
            return false;
        }
        
        // Check prerequisite class
        if (newClass.getPrerequisiteClass() != null && 
            !currentClass.getId().equals(newClass.getPrerequisiteClass())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Get player background
     */
    public Background getBackground() {
        return background;
    }
    
    // ==================== Alignment System ====================
    
    /**
     * Get the player's alignment system
     */
    public AlignmentSystem getAlignment() {
        return alignment;
    }
    
    /**
     * Make an alignment-affecting choice
     */
    public void makeAlignmentChoice(int honorChange, int compassionChange, String description) {
        alignment.makeChoice(honorChange, compassionChange, description);
    }
    
    /**
     * Get combat modifiers based on alignment
     */
    public CombatModifiers.ModifierResult getAlignmentCombatModifiers() {
        return CombatModifiers.getAlignmentModifiers(alignment);
    }
    
    // ==================== Experience & Leveling ====================
    
    /**
     * Gain experience
     */
    public boolean gainExperience(int amount) {
        experience += amount;
        
        boolean leveledUp = false;
        while (experience >= experienceToNextLevel) {
            levelUp();
            leveledUp = true;
        }
        
        return leveledUp;
    }
    
    /**
     * Level up the player
     */
    private void levelUp() {
        int newLevel = getLevel() + 1;
        setLevel(newLevel);
        
        // Subtract experience for level
        experience -= experienceToNextLevel;
        experienceToNextLevel = calculateExpForNextLevel(newLevel);
        
        // Apply stat growth from class
        Stats growth = currentClass.getStatGrowth();
        Stats currentStats = getBaseStats();
        
        currentStats.setStrength(currentStats.getStrength() + growth.getStrength());
        currentStats.setDexterity(currentStats.getDexterity() + growth.getDexterity());
        currentStats.setVitality(currentStats.getVitality() + growth.getVitality());
        currentStats.setIntelligence(currentStats.getIntelligence() + growth.getIntelligence());
        currentStats.setWisdom(currentStats.getWisdom() + growth.getWisdom());
        currentStats.setLuck(currentStats.getLuck() + growth.getLuck());
        
        // Recalculate derived stats
        recalculateStats();
        
        // Heal to full on level up
        heal(getMaxHP());
    }
    
    /**
     * Calculate experience needed for next level
     */
    private int calculateExpForNextLevel(int currentLevel) {
        // Exponential growth: 100 * (level^1.5)
        return (int)(100 * Math.pow(currentLevel, 1.5));
    }
    
    /**
     * Get current experience
     */
    public int getExperience() {
        return experience;
    }
    
    /**
     * Get experience needed for next level
     */
    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }
    
    /**
     * Get experience progress percentage
     */
    public double getExpProgressPercent() {
        int totalExpForLevel = calculateExpForNextLevel(getLevel());
        int prevLevelExp = getLevel() > 1 ? calculateExpForNextLevel(getLevel() - 1) : 0;
        int expIntoLevel = experience;
        
        return (double)expIntoLevel / (double)totalExpForLevel * 100.0;
    }
    
    // ==================== Stats Tracking ====================
    
    /**
     * Record damage dealt
     */
    public void recordDamageDealt(int amount) {
        totalDamageDealt += amount;
    }
    
    /**
     * Record damage taken (override from Character to track stats)
     */
    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        totalDamageTaken += amount;
    }
    
    /**
     * Record healing (override from Character to track stats)
     */
    @Override
    public void heal(int amount) {
        super.heal(amount);
        totalHealing += amount;
    }
    
    /**
     * Record enemy defeated
     */
    public void recordEnemyDefeated() {
        enemiesDefeated++;
    }
    
    // Stats getters
    public int getTotalDamageDealt() { return totalDamageDealt; }
    public int getTotalDamageTaken() { return totalDamageTaken; }
    public int getTotalHealing() { return totalHealing; }
    public int getEnemiesDefeated() { return enemiesDefeated; }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        return String.format("%s (Lv.%d %s %s) - HP: %d/%d | Focus: %d/100",
            getName(), getLevel(), background.getDisplayName(), 
            currentClass.getName(), getCurrentHP(), getMaxHP(), 
            focusMeter.getCurrentFocus());
    }
    
    /**
     * Get detailed character sheet
     */
    public String getCharacterSheet() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║  %s - Level %d %s %s\n", 
            getName(), getLevel(), background.getDisplayName(), currentClass.getName()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  HP: %d/%d | Focus: %d/100 | Element: %s\n",
            getCurrentHP(), getMaxHP(), focusMeter.getCurrentFocus(), getElementAffinity()));
        sb.append(String.format("║  EXP: %d/%d (%.1f%%)\n", 
            experience, experienceToNextLevel, getExpProgressPercent()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  ALIGNMENT: %s / %s\n",
            alignment.getHonorTier().getName(), alignment.getCompassionTier().getName()));
        sb.append(String.format("║    Honor: %+d | Compassion: %+d\n",
            alignment.getHonor(), alignment.getCompassion()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  STATS:\n");
        sb.append(String.format("║    STR: %d | DEX: %d | VIT: %d\n",
            getBaseStats().getStrength(), getBaseStats().getDexterity(), getBaseStats().getVitality()));
        sb.append(String.format("║    INT: %d | WIS: %d | LUK: %d\n",
            getBaseStats().getIntelligence(), getBaseStats().getWisdom(), getBaseStats().getLuck()));
        sb.append(String.format("║    ATK: %.1f | DEF: %.1f | M.ATK: %.1f | M.DEF: %.1f\n",
            getPhysicalAttack(), getPhysicalDefense(), getMagicalAttack(), getMagicalDefense()));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  EQUIPMENT:\n");
        for (EquipSlot slot : EquipSlot.values()) {
            Equipment item = equipment.get(slot);
            String itemName = item != null ? item.getName() : "Empty";
            sb.append(String.format("║    %s: %s\n", slot.getDisplayName(), itemName));
        }
        sb.append("╚═══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
}
