package com.rpg.models;

/**
 * Character backgrounds that determine available base classes
 */
public enum Background {
    NOBLE_BORN("Noble Born", 
        "Raised in luxury and trained in noble arts",
        new String[]{"PALADIN", "KNIGHT", "DUELIST", "COMMANDER"}),
    
    STREET_URCHIN("Street Urchin",
        "Survived on the streets through cunning and agility",
        new String[]{"ROGUE", "BRAWLER", "TRICKSTER", "SCOUT"}),
    
    SCHOLAR("Scholar",
        "Devoted years to studying arcane knowledge",
        new String[]{"MAGE", "SAGE", "ALCHEMIST", "ARCANIST"}),
    
    OUTLANDER("Outlander",
        "Lived beyond civilization, connected to nature",
        new String[]{"RANGER", "DRUID", "BEASTMASTER", "SHAMAN"}),
    
    SOLDIER("Soldier",
        "Trained in martial discipline and warfare",
        new String[]{"WARRIOR", "BERSERKER", "GUARDIAN", "TACTICIAN"}),
    
    ACOLYTE("Acolyte",
        "Raised in service to the divine",
        new String[]{"CLERIC", "MONK", "PRIEST", "INQUISITOR"});
    
    private final String displayName;
    private final String description;
    private final String[] availableClasses;
    
    Background(String displayName, String description, String[] availableClasses) {
        this.displayName = displayName;
        this.description = description;
        this.availableClasses = availableClasses;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public String[] getAvailableClasses() { return availableClasses; }
    
    public boolean canAccessClass(String classId) {
        for (String availableClass : availableClasses) {
            if (availableClass.equals(classId)) {
                return true;
            }
        }
        return false;
    }
}
