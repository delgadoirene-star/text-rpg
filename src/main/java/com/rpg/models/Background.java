package com.rpg.models;

/**
 * Character backgrounds that determine available base classes
 * Updated to match CLASSES.md v2
 */
public enum Background {
    NOBLE_BORN("Noble Born", 
        "Raised in luxury, trained in martial and courtly arts",
        new String[]{"CAVALIER", "COURTIER", "DUELIST", "VANGUARD"},
        new String[]{"Authority", "martial prowess", "leadership"}),
    
    STREET_URCHIN("Street Urchin",
        "Survived on the streets through cunning and agility",
        new String[]{"CUTTHROAT", "GRIFTER", "BRAWLER", "PROWLER"},
        new String[]{"Survival", "adaptability", "cunning"}),
    
    SCHOLAR("Scholar",
        "Devoted years to studying arcane knowledge",
        new String[]{"EVOKER", "ENCHANTER", "NATURALIST", "CHRONICLER"},
        new String[]{"Knowledge", "magical mastery", "discovery"}),
    
    OUTLANDER("Outlander",
        "Lived beyond civilization, connected to nature",
        new String[]{"WARDEN", "PREDATOR", "WILDSPEAKER", "STORMCALLER"},
        new String[]{"Nature", "primal power", "survival"}),
    
    SOLDIER("Soldier",
        "Trained in martial discipline and warfare",
        new String[]{"LEGIONNAIRE", "BERSERKER", "WEAPONMASTER", "TACTICIAN"},
        new String[]{"Discipline", "strength", "warfare"}),
    
    ACOLYTE("Acolyte",
        "Raised in service to the divine",
        new String[]{"TEMPLAR", "CLERIC", "ORACLE", "MENDICANT"},
        new String[]{"Faith", "divine power", "spiritual balance"}),
    
    // Additional backgrounds for companions
    NOBLE("Noble",
        "Born to privilege and expectations",
        new String[]{"CAVALIER", "DUELIST", "COURTIER"},
        new String[]{"Honor", "tradition", "duty"}),
    
    STREET_RAT("Street Rat",
        "Raised in poverty and crime",
        new String[]{"CUTTHROAT", "GRIFTER", "PROWLER"},
        new String[]{"Survival", "pragmatism", "loyalty"}),
    
    TRIBAL("Tribal",
        "From the wild places beyond civilization",
        new String[]{"WARDEN", "PREDATOR", "WILDSPEAKER"},
        new String[]{"Nature", "community", "tradition"}),
    
    MONK("Monk",
        "Trained in monastic discipline",
        new String[]{"TEMPLAR", "MENDICANT", "TACTICIAN"},
        new String[]{"Balance", "self-mastery", "enlightenment"}),
    
    SPY("Spy",
        "Trained in espionage and subterfuge",
        new String[]{"PROWLER", "GRIFTER", "CHRONICLER"},
        new String[]{"Information", "leverage", "survival"}),
    
    SHAMAN("Shaman",
        "Spiritual guide and mystic",
        new String[]{"WILDSPEAKER", "ORACLE", "NATURALIST"},
        new String[]{"Spirits", "sacrifice", "balance"}),
    
    HERETIC("Heretic",
        "Cast out from sacred orders",
        new String[]{"TEMPLAR", "ENCHANTER", "MENDICANT"},
        new String[]{"Bitterness", "power", "vengeance"}),
    
    OUTCAST("Outcast",
        "Rejected by society",
        new String[]{"EVOKER", "ENCHANTER", "NATURALIST"},
        new String[]{"Knowledge", "transcendence", "revenge"});
    
    private final String displayName;
    private final String description;
    private final String[] baseClasses;
    private final String[] philosophies;
    
    Background(String displayName, String description, String[] baseClasses, String[] philosophies) {
        this.displayName = displayName;
        this.description = description;
        this.baseClasses = baseClasses;
        this.philosophies = philosophies;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public String[] getAvailableClasses() { return baseClasses; }
    public String[] getPhilosophies() { return philosophies; }
    
    /**
     * Check if this background can access a class by ID
     */
    public boolean canAccessClass(String classId) {
        // Check base classes
        for (String baseClass : baseClasses) {
            if (baseClass.equalsIgnoreCase(classId)) {
                return true;
            }
        }
        
        // Advanced classes are accessible if their prerequisite base class is accessible
        // This would need to be checked against a class registry in full implementation
        return false;
    }
    
    /**
     * Check if this background can access a GameClass
     */
    public boolean canAccess(GameClass gameClass) {
        if (gameClass == null) return false;
        
        // Base classes: check directly
        if (gameClass.getTier() == GameClass.ClassTier.BASE) {
            return canAccessClass(gameClass.getId());
        }
        
        // Advanced/Secret classes: check prerequisite chain
        String prereq = gameClass.getPrerequisiteClass();
        if (prereq != null) {
            return canAccessClass(prereq);
        }
        
        // Event-based secret classes may have different requirements
        // These are typically unlocked via FlagManager, not background
        return false;
    }
    
    /**
     * Get detailed background info
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║  %s\n", displayName));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  %s\n", description));
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  Philosophy: ");
        sb.append(String.join(", ", philosophies)).append("\n");
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  Available Classes:\n");
        for (String className : baseClasses) {
            sb.append(String.format("║    - %s\n", className));
        }
        sb.append("╚═══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
}
