package com.rpg.models;

/**
 * Two-tier stat system
 * Tier 1: Core stats (base attributes)
 * Tier 2: Derived stats (calculated from core stats)
 */
public class Stats {
    // Tier 1: Core Stats
    private int strength;     // Physical power
    private int dexterity;    // Precision and speed
    private int vitality;     // Health and resilience
    private int intelligence; // Magic power
    private int wisdom;       // Magic defense and focus
    private int luck;         // Crit chance and special procs
    
    public Stats(int str, int dex, int vit, int intel, int wis, int luk) {
        this.strength = str;
        this.dexterity = dex;
        this.vitality = vit;
        this.intelligence = intel;
        this.wisdom = wis;
        this.luck = luk;
    }
    
    // Default constructor (all stats = 10)
    public Stats() {
        this(10, 10, 10, 10, 10, 10);
    }
    
    // Tier 2: Derived Stats (calculated)
    
    public int getMaxHP() {
        return vitality * 10 + 50; // Base 50 HP + 10 per VIT
    }
    
    public double getPhysicalAttack() {
        return strength * 2.0 + dexterity * 0.5;
    }
    
    public double getPhysicalDefense() {
        return vitality * 1.5 + strength * 0.3;
    }
    
    public double getMagicAttack() {
        return intelligence * 2.0 + wisdom * 0.5;
    }
    
    public double getMagicDefense() {
        return wisdom * 1.5 + intelligence * 0.3;
    }
    
    public double getSpeed() {
        return dexterity * 2.0 + luck * 0.2;
    }
    
    public double getCritRate() {
        return (luck * 0.5 + dexterity * 0.2); // Returns percentage (5.0 = 5%)
    }
    
    public double getCritDamage() {
        return 150.0 + (luck * 0.3); // Base 150%, increases with luck
    }
    
    public double getEvasion() {
        return (dexterity * 0.3 + luck * 0.1); // Returns percentage
    }
    
    public double getAccuracy() {
        return 95.0 + (dexterity * 0.2); // Base 95% accuracy
    }
    
    // Core stat getters
    public int getStrength() { return strength; }
    public int getDexterity() { return dexterity; }
    public int getVitality() { return vitality; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getLuck() { return luck; }
    
    // Core stat setters
    public void setStrength(int strength) { this.strength = strength; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }
    public void setVitality(int vitality) { this.vitality = vitality; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }
    public void setLuck(int luck) { this.luck = luck; }
    
    // Modifier methods
    public void addStrength(int amount) { this.strength += amount; }
    public void addDexterity(int amount) { this.dexterity += amount; }
    public void addVitality(int amount) { this.vitality += amount; }
    public void addIntelligence(int amount) { this.intelligence += amount; }
    public void addWisdom(int amount) { this.wisdom += amount; }
    public void addLuck(int amount) { this.luck += amount; }
    
    /**
     * Add another Stats object to this one (for equipment bonuses)
     */
    public void add(Stats other) {
        this.strength += other.strength;
        this.dexterity += other.dexterity;
        this.vitality += other.vitality;
        this.intelligence += other.intelligence;
        this.wisdom += other.wisdom;
        this.luck += other.luck;
    }
    
    /**
     * Create a copy of these stats
     */
    public Stats copy() {
        return new Stats(strength, dexterity, vitality, intelligence, wisdom, luck);
    }
    
    /**
     * Check if all stats are zero
     */
    public boolean isZero() {
        return strength == 0 && dexterity == 0 && vitality == 0 && 
               intelligence == 0 && wisdom == 0 && luck == 0;
    }
    
    @Override
    public String toString() {
        return String.format(
            "STR: %d | DEX: %d | VIT: %d | INT: %d | WIS: %d | LUK: %d",
            strength, dexterity, vitality, intelligence, wisdom, luck
        );
    }
    
    public String toDetailedString() {
        return String.format("""
            === Core Stats ===
            STR: %d  DEX: %d  VIT: %d
            INT: %d  WIS: %d  LUK: %d
            
            === Derived Stats ===
            HP: %d
            Physical ATK: %.1f  Physical DEF: %.1f
            Magic ATK: %.1f     Magic DEF: %.1f
            Speed: %.1f
            Crit Rate: %.1f%%   Crit DMG: %.1f%%
            Evasion: %.1f%%     Accuracy: %.1f%%
            """,
            strength, dexterity, vitality,
            intelligence, wisdom, luck,
            getMaxHP(),
            getPhysicalAttack(), getPhysicalDefense(),
            getMagicAttack(), getMagicDefense(),
            getSpeed(),
            getCritRate(), getCritDamage(),
            getEvasion(), getAccuracy()
        );
    }
}
