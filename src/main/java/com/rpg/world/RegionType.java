package com.rpg.world;

/**
 * The Four Regions of the world, each with unique culture and naming conventions
 */
public enum RegionType {
    /**
     * The Archivist Core - Lawful/Bureaucratic
     * A sun-baked city obsessed with history, permanence, and rigid law
     * (+Honor, -Compassion)
     * Names: Persian/Sanskrit rhythms mixed with metallic/architectural concepts
     * Example: Isolde vex-Torvath (where "vex" is a caste prefix)
     */
    ARCHIVIST_CORE(
        "The Archivist Core",
        "A sun-scorched metropolis of towering archives and rigid castes. " +
        "Every deed is recorded, every law enforced. Honor is currency, mercy is weakness.",
        new String[]{"vex", "kes", "dal", "ves", "nul"},  // Caste prefixes
        new String[]{"Torvath", "Kaelir", "Vael", "Zephyr", "Raxis"},  // Surnames
        1, -1  // +Honor, -Compassion
    ),
    
    /**
     * The Sump / The Drowned Wards - Pragmatic/Community
     * A vertical, waterlogged slum where survival is everything
     * (-Honor, +Compassion)
     * Language: Clipped and fast, no surnames, cheeky metaphorical street monikers
     * Example: Bram "The Vulture", Sash "Velvet"
     */
    SUMP(
        "The Sump",
        "Vertical slums carved into canyon walls, perpetually damp and desperate. " +
        "No one keeps promises here, but everyone watches each other's backs.",
        new String[]{"Bram", "Sash", "Kell", "Vex", "Thorn", "Ash", "Quinn"},  // First names
        new String[]{"The Vulture", "Velvet", "The Rust", "Knives", "Grease", "Echo", "Smoke"},  // Street monikers
        -1, 1  // -Honor, +Compassion
    ),
    
    /**
     * The Choke / The Symbiote Wilds - Wild/Unsettling
     * A hyper-evolved, parasitic forest ecosystem
     * (Neutral Honor, varies Compassion)
     * Culture romanticizes their horrific biological symbiosis with nature
     * Names: Poetic but veil a darker reality
     * Example: Oona Pale-Sap, Ghor Iron-Root
     */
    CHOKE(
        "The Choke",
        "An impossibly dense forest where trees breathe and roots hunger. " +
        "The people here are not quite human anymore, their bodies shared with the wood.",
        new String[]{"Oona", "Ghor", "Maeva", "Thrace", "Sylva", "Rowan"},  // First names
        new String[]{"Pale-Sap", "Iron-Root", "Still-Water", "Deep-Thorn", "Blood-Bark", "Soft-Moss"},  // Nature epithets
        0, 0  // Neutral on both axes (varies by individual)
    ),
    
    /**
     * The Zenith / The Deep Cloisters - Extreme Purity
     * Monasteries built in elemental hazard zones
     * (+Honor, +Compassion trending to True Neutral)
     * They abandon birth names for concept titles and numerical ranks
     * Example: Balance-Ninth, Void-Exile
     */
    ZENITH(
        "The Zenith",
        "Monasteries perched in impossible places: eye of the storm, heart of the volcano, " +
        "depth of the glacier. Those who survive transcend... or break entirely.",
        new String[]{"Balance", "Void", "Clarity", "Silence", "Mercy", "Truth", "Iron"},  // Concept names
        new String[]{"First", "Second", "Third", "Fourth", "Fifth", "Ninth", "Exile"},  // Ranks or status
        1, 1  // +Honor, +Compassion (but seekers of True Neutral)
    );
    
    private final String displayName;
    private final String description;
    private final String[] namePool1;  // First names or prefixes
    private final String[] namePool2;  // Surnames or epithets
    private final int honorTendency;    // -1, 0, or 1
    private final int compassionTendency;  // -1, 0, or 1
    
    RegionType(String displayName, String description, 
               String[] namePool1, String[] namePool2,
               int honorTendency, int compassionTendency) {
        this.displayName = displayName;
        this.description = description;
        this.namePool1 = namePool1;
        this.namePool2 = namePool2;
        this.honorTendency = honorTendency;
        this.compassionTendency = compassionTendency;
    }
    
    /**
     * Generate a culturally appropriate name for this region
     */
    public String generateName() {
        int idx1 = (int) (Math.random() * namePool1.length);
        int idx2 = (int) (Math.random() * namePool2.length);
        
        return switch (this) {
            case ARCHIVIST_CORE -> namePool1[idx1] + "-" + namePool2[idx2];
            case SUMP -> namePool1[idx1] + " \"" + namePool2[idx2] + "\"";
            case CHOKE -> namePool1[idx1] + " " + namePool2[idx2];
            case ZENITH -> namePool1[idx1] + "-" + namePool2[idx2];
        };
    }
    
    /**
     * Get the alignment tendency of this region
     */
    public int getHonorTendency() {
        return honorTendency;
    }
    
    public int getCompassionTendency() {
        return compassionTendency;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Get a short cultural summary
     */
    public String getCulturalSummary() {
        String honorDesc = switch (honorTendency) {
            case 1 -> "values honor and truth";
            case -1 -> "embraces pragmatism and deceit";
            default -> "neither honors nor condemns deception";
        };
        
        String compassionDesc = switch (compassionTendency) {
            case 1 -> "shows mercy to the weak";
            case -1 -> "crushes those beneath them";
            default -> "treats others with cold practicality";
        };
        
        return String.format("%s %s and %s.", displayName, honorDesc, compassionDesc);
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
