package com.rpg;

import com.rpg.models.*;
import com.rpg.systems.AlignmentSystem;
import com.rpg.systems.AlignmentSystem.CompanionAnchor;
import com.rpg.systems.FlagManager;
import com.rpg.systems.FlagManager.SubversionTracker;
import com.rpg.world.RegionType;

/**
 * Demonstration of the complete companion system integration:
 * - Companion creation with regional cultural backgrounds
 * - Alignment anchors and loyalty tracking
 * - Quest subversion for transformation paths
 * - Dynamic class transformations based on player choices
 * 
 * This demo shows how all companion systems work together to create
 * meaningful narrative consequences based on player decisions.
 */
public class CompanionIntegrationDemo {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       COMPANION SYSTEM INTEGRATION DEMONSTRATION              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        // Initialize core systems
        AlignmentSystem alignmentSystem = new AlignmentSystem();
        FlagManager flagManager = new FlagManager();
        
        // Demo 1: Regional cultural system
        demoRegionalCulture();
        
        // Demo 2: Companion creation and alignment anchors
        demoCompanionCreationAndAlignment(alignmentSystem);
        
        // Demo 3: Quest subversion and transformation paths
        demoQuestSubversionAndTransformation(alignmentSystem, flagManager);
        
        // Demo 4: Complete companion journey
        demoCompleteCompanionJourney(alignmentSystem, flagManager);
    }
    
    /**
     * DEMO 1: Regional Culture System
     * Shows how the 4 regions generate culturally appropriate names and alignment tendencies
     */
    private static void demoRegionalCulture() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DEMO 1: REGIONAL CULTURE SYSTEM");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        for (RegionType region : RegionType.values()) {
            System.out.println(region.getDisplayName() + ":");
            System.out.println("  Description: " + region.getDescription());
            System.out.println("  Alignment: Honor " + (region.getHonorTendency() > 0 ? "+" : "") + 
                             region.getHonorTendency() + " / Compassion " + 
                             (region.getCompassionTendency() > 0 ? "+" : "") + 
                             region.getCompassionTendency());
            System.out.println("  Example Names:");
            for (int i = 0; i < 3; i++) {
                System.out.println("    - " + region.generateName());
            }
            System.out.println();
        }
    }
    
    /**
     * DEMO 2: Companion Creation & Alignment Anchors
     * Shows how to create companions with alignment anchors and check compatibility
     */
    private static void demoCompanionCreationAndAlignment(AlignmentSystem alignmentSystem) {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DEMO 2: COMPANION CREATION & ALIGNMENT ANCHORS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Create base classes for companions
        GameClass paladinClass = GameClassRegistry.createPaladinClass();
        GameClass rogueClass = GameClassRegistry.createRogueClass();
        
        // Create Isolde (Lawful companion from Archivist Core)
        // Anchor: High honor (75), moderate-low compassion (30), tolerance 40
        Companion isolde = CompanionFactory.createIsolde(paladinClass);
        CompanionAnchor isoldeAnchor = new CompanionAnchor(
            isolde.getId(),
            75,  // High honor anchor - values law and order
            30,  // Moderate compassion - pragmatic about mercy
            40   // Tolerance before breaking point
        );
        
        System.out.println("Created: " + isolde.getName());
        System.out.println("  Class: " + isolde.getCurrentClass().getName());
        System.out.println("  Backstory: " + (isolde.getBackstory().length() > 100 ? 
                         isolde.getBackstory().substring(0, 100) + "..." : isolde.getBackstory()));
        System.out.println("  Anchor: Honor=" + isoldeAnchor.getHonorAnchor() + 
                         ", Compassion=" + isoldeAnchor.getCompassionAnchor());
        System.out.println("  Alignment: " + isoldeAnchor.getAlignmentDescription());
        System.out.println("  Tolerance: " + isoldeAnchor.getTolerance() + " points per axis");
        System.out.println();
        
        // Create Bram (Pragmatic companion from The Sump)
        // Anchor: Low honor (20), high compassion (70), tolerance 35
        Companion bram = CompanionFactory.createBram(rogueClass);
        CompanionAnchor bramAnchor = new CompanionAnchor(
            bram.getId(),
            20,  // Low honor - street pragmatist
            70,  // High compassion - protects the weak
            35   // Tolerance
        );
        
        System.out.println("Created: " + bram.getName());
        System.out.println("  Class: " + bram.getCurrentClass().getName());
        System.out.println("  Backstory: " + (bram.getBackstory().length() > 100 ? 
                         bram.getBackstory().substring(0, 100) + "..." : bram.getBackstory()));
        System.out.println("  Anchor: Honor=" + bramAnchor.getHonorAnchor() + 
                         ", Compassion=" + bramAnchor.getCompassionAnchor());
        System.out.println("  Alignment: " + bramAnchor.getAlignmentDescription());
        System.out.println("  Tolerance: " + bramAnchor.getTolerance() + " points per axis");
        System.out.println();
        
        // Test player compatibility with different alignments
        System.out.println("COMPATIBILITY TESTING:");
        System.out.println("-".repeat(63));
        
        // Start from neutral
        alignmentSystem = new AlignmentSystem();
        
        // Lawful Good player: Honor=70, Compassion=70
        alignmentSystem.makeChoice(70, 70, "Initial alignment - Lawful Good");
        testCompatibility(alignmentSystem, isoldeAnchor, bramAnchor, "Lawful Good Player");
        
        // Shift to Lawful Evil: Honor stays 70, Compassion drops to -70
        alignmentSystem.makeChoice(0, -140, "Shift to cruelty");
        testCompatibility(alignmentSystem, isoldeAnchor, bramAnchor, "Lawful Evil Player");
        
        // Reset and test Chaotic Good
        alignmentSystem = new AlignmentSystem();
        alignmentSystem.makeChoice(-70, 70, "Chaotic Good");
        testCompatibility(alignmentSystem, isoldeAnchor, bramAnchor, "Chaotic Good Player");
        
        // Reset and test Chaotic Evil
        alignmentSystem = new AlignmentSystem();
        alignmentSystem.makeChoice(-70, -70, "Chaotic Evil");
        testCompatibility(alignmentSystem, isoldeAnchor, bramAnchor, "Chaotic Evil Player");
    }
    
    private static void testCompatibility(AlignmentSystem alignmentSystem, 
                                         CompanionAnchor isolde, CompanionAnchor bram,
                                         String playerType) {
        System.out.println("\n" + playerType + " (H=" + alignmentSystem.getHonor() + 
                         ", C=" + alignmentSystem.getCompassion() + "):");
        
        // Check Isolde compatibility
        int isoldeCompat = isolde.getCompatibility(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        int isoldeLoyalty = isolde.getLoyaltyModifier(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        boolean isoldeBreaks = isolde.isBreakingPoint(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        
        System.out.println("  Isolde: " + isoldeCompat + "% compatible, " +
                         (isoldeLoyalty > 0 ? "+" : "") + isoldeLoyalty + " loyalty" +
                         (isoldeBreaks ? " [WILL LEAVE]" : ""));
        
        // Check Bram compatibility
        int bramCompat = bram.getCompatibility(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        int bramLoyalty = bram.getLoyaltyModifier(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        boolean bramBreaks = bram.isBreakingPoint(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        
        System.out.println("  Bram:   " + bramCompat + "% compatible, " +
                         (bramLoyalty > 0 ? "+" : "") + bramLoyalty + " loyalty" +
                         (bramBreaks ? " [WILL LEAVE]" : ""));
    }
    
    /**
     * DEMO 3: Quest Subversion & Transformation Paths
     * Shows how hidden morality tracking determines companion class transformations
     */
    private static void demoQuestSubversionAndTransformation(AlignmentSystem alignmentSystem,
                                                             FlagManager flagManager) {
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DEMO 3: QUEST SUBVERSION & TRANSFORMATION PATHS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Create Dr. Sybilla (Scholar companion with transformation path)
        GameClass scholarClass = GameClassRegistry.createScholarClass();
        Companion sybilla = CompanionFactory.createSybilla(scholarClass);
        
        System.out.println("Companion: " + sybilla.getName());
        System.out.println("Starting Class: " + sybilla.getCurrentClass().getName());
        System.out.println("Transformation Options:");
        System.out.println("  GOOD PATH: True Healer (Selfless healing, refuses to weaponize)");
        System.out.println("  BAD PATH:  Blood Alchemist (Weaponizes medicine for power)");
        System.out.println();
        
        // Initialize subversion tracker - threshold 3 means 3+ corruption points = bad path
        flagManager.initializeSubversionTracker(sybilla.getId(), 3);
        SubversionTracker tracker = flagManager.getSubversionTracker(sybilla.getId());
        
        // Simulate GOOD PATH quest choices
        System.out.println("QUEST SCENARIO: Research for the Cure (GOOD PATH)");
        System.out.println("-".repeat(63));
        
        // Choice 1: Ethical research methods - no subversion points
        System.out.println("\nChoice 1: A patient is dying. Experimental treatment might save them.");
        System.out.println("  [A] Wait for proper trials (ethical, slow)");
        System.out.println("  [B] Administer immediately (risky, fast)");
        System.out.println("Player chooses: [A] - Wait for proper trials");
        System.out.println("  No subversion points added (ethical choice)");
        
        // Choice 2: Respect protected nature - no subversion points
        System.out.println("\nChoice 2: Rare ingredient needed. Only source is protected grove.");
        System.out.println("  [A] Seek alternative ingredient (harder)");
        System.out.println("  [B] Harvest from protected grove (effective)");
        System.out.println("Player chooses: [A] - Seek alternative");
        System.out.println("  No subversion points added (respectful choice)");
        
        // Choice 3: Treat the poor - no subversion points
        System.out.println("\nChoice 3: Limited supply. Who gets treatment first?");
        System.out.println("  [A] Wealthy merchant (will fund more research)");
        System.out.println("  [B] Poor villagers (need it most)");
        System.out.println("Player chooses: [B] - Poor villagers");
        System.out.println("  No subversion points added (compassionate choice)");
        
        System.out.println();
        System.out.println("Path: " + tracker.getOutcomePath());
        System.out.println("Subversion Points: " + tracker.getPoints() + "/" + tracker.getThreshold());
        if (!tracker.hasReachedThreshold()) {
            System.out.println(">>> GOOD PATH CONFIRMED <<<");
            System.out.println("Dr. Sybilla becomes: TRUE HEALER");
            System.out.println("  \"I've learned that true healing means respecting life in all forms.\"");
            
            GameClass trueHealer = GameClassRegistry.createTrueHealerClass();
            sybilla.changeClass(trueHealer);
            System.out.println("  New Class: " + sybilla.getCurrentClass().getName());
        }
        
        // Show alternative BAD PATH
        System.out.println("\n" + "-".repeat(63));
        System.out.println("[ALTERNATE PATH - What if player made different choices?]");
        System.out.println("-".repeat(63));
        
        flagManager.initializeSubversionTracker("sybilla_alt", 3);
        flagManager.addSubversionPoints("sybilla_alt", 1, "Used patients as test subjects");
        flagManager.addSubversionPoints("sybilla_alt", 1, "Harvested from sacred grove");
        flagManager.addSubversionPoints("sybilla_alt", 1, "Sold cure to highest bidder");
        
        SubversionTracker altTracker = flagManager.getSubversionTracker("sybilla_alt");
        System.out.println("Path: " + altTracker.getOutcomePath());
        System.out.println("Subversion Points: " + altTracker.getPoints() + "/" + altTracker.getThreshold());
        
        if (altTracker.hasReachedThreshold()) {
            System.out.println(">>> BAD PATH CONFIRMED <<<");
            System.out.println("Dr. Sybilla becomes: BLOOD ALCHEMIST");
            System.out.println("  \"Power isn't evil. It's just... efficient.\"");
            
            GameClass bloodAlchemist = GameClassRegistry.createBloodAlchemistClass();
            sybilla.changeClass(bloodAlchemist);
            System.out.println("  New Class: " + sybilla.getCurrentClass().getName());
        }
    }
    
    /**
     * DEMO 4: Complete Companion Journey
     * Shows a full companion arc from recruitment to transformation
     */
    private static void demoCompleteCompanionJourney(AlignmentSystem alignmentSystem,
                                                     FlagManager flagManager) {
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DEMO 4: COMPLETE COMPANION JOURNEY");
        System.out.println("  Companion: Balance-Ninth / Theron");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Create Theron (monk from The Zenith)
        GameClass monkClass = GameClassRegistry.createMonkClass();
        Companion theron = CompanionFactory.createTheron(monkClass);
        
        // Register alignment anchor
        // Theron is from The Zenith: high honor, high compassion, narrow tolerance
        CompanionAnchor theronAnchor = new CompanionAnchor(
            theron.getId(),
            75,  // High honor (purity doctrine)
            75,  // High compassion (Zenith values)
            50   // Tolerance before breaking point
        );
        
        System.out.println("RECRUITMENT:");
        System.out.println("-".repeat(63));
        System.out.println("Location: The Zenith (Extreme Purity region)");
        System.out.println("You meet a monk who questions the Zenith's doctrine of purity.");
        System.out.println(theron.getDialogue("first_meeting"));
        theron.recruit();
        System.out.println(theron);
        System.out.println();
        
        // Initial compatibility
        alignmentSystem = new AlignmentSystem();
        alignmentSystem.makeChoice(70, 70, "Player is lawful and compassionate");
        System.out.println("INITIAL COMPATIBILITY:");
        System.out.println("  Player Alignment: H=" + alignmentSystem.getHonor() + 
                         ", C=" + alignmentSystem.getCompassion());
        int compat = theronAnchor.getCompatibility(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        System.out.println("  Compatibility: " + compat + "%");
        System.out.println("  Loyalty: " + theron.getLoyaltyLevel() + "/100");
        System.out.println();
        
        // Initialize subversion tracker for Theron's personal quest
        flagManager.initializeSubversionTracker(theron.getId(), 3);
        SubversionTracker theronTracker = flagManager.getSubversionTracker(theron.getId());
        
        // Quest progression - GOOD PATH
        System.out.println("PERSONAL QUEST: The Weight of Purity (GOOD PATH)");
        System.out.println("-".repeat(63));
        
        System.out.println("\nAct 1: Theron's village exiles an 'impure' child");
        System.out.println("Player choice: Shelter the child");
        theron.changeLoyalty(5);
        int newCompat = theronAnchor.getCompatibility(
            alignmentSystem.getHonor(), 
            alignmentSystem.getCompassion()
        );
        System.out.println("  Theron approves (+5 loyalty): \"Perhaps... compassion isn't weakness.\"");
        System.out.println("  Compatibility: " + newCompat + "%");
        System.out.println("  Loyalty: " + theron.getLoyaltyLevel() + "/100");
        
        System.out.println("\nAct 2: Zenith agents demand child's return");
        System.out.println("Player choice: Defend the child");
        theron.changeLoyalty(10);
        alignmentSystem.makeChoice(5, 10, "Honorable and compassionate stand");
        System.out.println("  Theron deeply approves (+10 loyalty): \"You risk everything for one life...\"");
        System.out.println("  Loyalty: " + theron.getLoyaltyLevel() + "/100");
        
        System.out.println("\nAct 3: Theron must choose - Zenith doctrine or new truth");
        theron.changeLoyalty(15);
        System.out.println("  Theron: \"I understand now. True purity isn't about isolation...\"");
        System.out.println("  Loyalty: " + theron.getLoyaltyLevel() + "/100");
        
        // Check transformation
        System.out.println();
        System.out.println("Path: " + theronTracker.getOutcomePath());
        if (!theronTracker.hasReachedThreshold()) {
            System.out.println(">>> TRANSFORMATION: BODHISATTVA <<<");
            System.out.println("Balance-Ninth casts off his title and becomes Theron again.");
            System.out.println("He rejects the Zenith's isolation and embraces compassionate action.");
            
            GameClass bodhisattva = GameClassRegistry.createBodhisattvaClass();
            theron.changeClass(bodhisattva);
            
            System.out.println("\nNew Class: " + theron.getCurrentClass().getName());
            System.out.println("Theron: \"I will be the balance between purity and compassion.\"");
        }
        
        // Show final status
        System.out.println("\n" + theron.getCompanionSheet());
        
        // Show alternate path
        System.out.println("\n\n[ALTERNATE PATH - Player embraces Zenith doctrine]");
        System.out.println("-".repeat(63));
        System.out.println("If player had made cruel choices to enforce 'purity':");
        System.out.println("  - 3+ subversion points triggers CORRUPTED path");
        System.out.println("  - Theron transforms into VOID WALKER");
        System.out.println("  - Becomes emotionless enforcer of purity");
        System.out.println("  - \"Balance-Tenth reporting. All weakness has been purged.\"");
        
        // Show companion summary
        System.out.println("\n\n" + "=".repeat(63));
        System.out.println("ALL 11 COMPANIONS:");
        System.out.println("=".repeat(63));
        System.out.println("  1. Isolde vex-Torvath   - Archivist Core - Paladin");
        System.out.println("  2. Bram \"The Vulture\"  - The Sump - Rogue");
        System.out.println("  3. Dr. Sybilla ves-Vael - Archivist Core - Scholar");
        System.out.println("  4. Garrick nul-Kael     - Archivist Core - Veteran");
        System.out.println("  5. Oona Pale-Sap        - The Choke - Guardian");
        System.out.println("  6. Theron (Balance-9th) - The Zenith - Monk");
        System.out.println("  7. Sash \"Velvet\"       - The Sump - Spy");
        System.out.println("  8. Ghor Iron-Root       - The Choke - Brawler");
        System.out.println("  9. Vek \"Glass\"         - The Sump - Arcanist");
        System.out.println(" 10. Maeva Still-Water    - The Choke - Shaman");
        System.out.println(" 11. Silas (Void-Exile)   - The Zenith - Priest");
        System.out.println("\nEach companion has:");
        System.out.println("  - Regional cultural background and naming");
        System.out.println("  - Alignment anchor with breaking point tolerance");
        System.out.println("  - Subversion tracker for transformation path");
        System.out.println("  - Good/Bad class transformation (22 total secret classes)");
    }
}
