package com.rpg.models;

import com.rpg.combat.CompanionAbilityFactory;
import com.rpg.systems.AlignmentSystem;
import com.rpg.world.RegionType;

/**
 * Factory for creating all companion characters with their detailed lore,
 * alignment anchors, and subversion paths
 */
public class CompanionFactory {
    
    /**
     * Create Isolde vex-Torvath - The Paladin
     * From: Archivist Core
     * Anchor: +Honor / -Compassion
     * Breaking Point: Player shows too much mercy or becomes too deceitful
     * Subversion: Seeking Inquisitor's blade to prove she isn't "weak" like her father
     * Good Path: "Redeemer" - Embraces compassion
     * Bad Path: "Iron Judge" - Becomes cruelly righteous
     */
    public static Companion createIsolde(GameClass paladinClass) {
        return new Companion.Builder("isolde", "Isolde vex-Torvath", 
            Background.NOBLE, paladinClass)
            .level(3)
            .stats(new Stats(16, 10, 14, 8, 12, 10))
            .element(Element.LIGHT)
            .personality("Rigid")
            .backstory("Daughter of a disgraced Archivist who showed mercy to a heretic. " +
                      "Isolde seeks the legendary Inquisitor's Blade to prove that honor " +
                      "and strength need no room for weakness.")
            .combatRole("Tank")
            .preferredTarget("Strongest")
            .recruitmentLocation("archivist_core_tribunal")
            .recruitmentFlag("met_isolde")
            .startingLoyalty(40)
            .addAbility(CompanionAbilityFactory.createRighteousCharge())
            .addAbility(CompanionAbilityFactory.createJudgeVerdict())
            .addAbility(CompanionAbilityFactory.createUnyieldingLaw())
            .addPersonalQuest("isoldes_trial")
            .addDialogue("battle_won", "Justice is served. But was it mercy or weakness that spared them?")
            .addDialogue("alignment_conflict", "Your... compassion disturbs me. It clouds judgment.")
            .build();
    }
    
    /**
     * Get Isolde's alignment anchor
     */
    public static AlignmentSystem.CompanionAnchor getIsoldeAnchor() {
        return new AlignmentSystem.CompanionAnchor("isolde", 75, -50, 40);
        // High honor, low compassion, moderate tolerance
    }
    
    /**
     * Create Bram "The Vulture" - The Rogue
     * From: The Sump
     * Anchor: -Honor / +Compassion
     * Breaking Point: Player becomes too honorable/lawful or too cruel
     * Subversion: Extorted by corrupt Guard Captain
     * Good Path: "Spymaster" - Burns the system down
     * Bad Path: "Contractor" - Sells soul to save found-family
     */
    public static Companion createBram(GameClass rogueClass) {
        return new Companion.Builder("bram", "Bram \"The Vulture\"",
            Background.STREET_RAT, rogueClass)
            .level(4)
            .stats(new Stats(12, 18, 10, 14, 8, 12))
            .element(Element.DARK)
            .personality("Pragmatic")
            .backstory("A Sump pickpocket turned information broker. Bram looks after " +
                      "his own - a ragtag family of orphans and outcasts. Now a Guard " +
                      "Captain has him in a vice, demanding impossible favors.")
            .combatRole("DPS")
            .preferredTarget("Weakest")
            .recruitmentLocation("sump_lower_wards")
            .recruitmentFlag("helped_sump_orphans")
            .startingLoyalty(30)
            .addAbility(CompanionAbilityFactory.createBackstab())
            .addAbility(CompanionAbilityFactory.createVenomBlade())
            .addAbility(CompanionAbilityFactory.createVanish())
            .addPersonalQuest("brams_extortion")
            .addDialogue("battle_won", "Clean work. No one has to know.")
            .addDialogue("alignment_conflict", "You keep playing by their rules, you'll end up like me.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getBramAnchor() {
        return new AlignmentSystem.CompanionAnchor("bram", -60, 70, 35);
        // Low honor, high compassion, tight tolerance
    }
    
    /**
     * Create Dr. Sybilla ves-Vael - The Scholar
     * From: Archivist Core
     * Anchor: +Honor / +Compassion
     * Breaking Point: Player becomes too deceitful or cruel
     * Subversion: Cure requires horrific sacrifice
     * Good Path: Slow, harmless cure
     * Bad Path: Instant cure via blood ritual
     */
    public static Companion createSybilla(GameClass scholarClass) {
        return new Companion.Builder("sybilla", "Dr. Sybilla ves-Vael",
            Background.SCHOLAR, scholarClass)
            .level(5)
            .stats(new Stats(8, 12, 12, 18, 16, 10))
            .element(Element.NEUTRAL)
            .personality("Clinical")
            .backstory("A renowned physician from the Core, obsessed with curing the " +
                      "Weeping Plague. Her research is close to breakthrough - but the " +
                      "final formula requires an ingredient no ethical doctor could obtain.")
            .combatRole("Support")
            .preferredTarget("Weakest")
            .recruitmentLocation("archivist_core_academy")
            .recruitmentFlag("plague_outbreak")
            .startingLoyalty(60)
            .addAbility(CompanionAbilityFactory.createAnalyticalStrike())
            .addAbility(CompanionAbilityFactory.createHealingLight())
            .addAbility(CompanionAbilityFactory.createArcaneBarrier())
            .addPersonalQuest("sybillas_cure")
            .addDialogue("battle_won", "Fascinating. The pathology of combat stress...")
            .addDialogue("alignment_conflict", "I cannot condone such cruelty, even in the name of progress.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getSybillaAnchor() {
        return new AlignmentSystem.CompanionAnchor("sybilla", 80, 80, 30);
        // High honor, high compassion, low tolerance
    }
    
    /**
     * Create Garrick nul-Kael - The Veteran
     * From: Exiled from Archivist Core
     * Anchor: +Honor / +Compassion
     * Breaking Point: Player acts dishonorably
     * Subversion: His mutiny was meaningless
     * Good Path: Finds closure and purpose
     * Bad Path: "Reaver" - Becomes cynical mercenary
     */
    public static Companion createGarrick(GameClass warriorClass) {
        return new Companion.Builder("garrick", "Garrick nul-Kael",
            Background.SOLDIER, warriorClass)
            .level(6)
            .stats(new Stats(18, 12, 16, 8, 10, 10))
            .element(Element.FIRE)
            .personality("Stoic")
            .backstory("Former commander exiled from the Core for mutiny. Garrick refused " +
                      "to execute deserters - his own men. Now he wanders, haunted by the " +
                      "question: did his defiance save anyone, or just doom them slower?")
            .combatRole("Tank")
            .preferredTarget("Strongest")
            .recruitmentLocation("frontier_outpost")
            .startingLoyalty(50)
            .addAbility(CompanionAbilityFactory.createMarkTarget())
            .addAbility(CompanionAbilityFactory.createExecute())
            .addAbility(CompanionAbilityFactory.createRally())
            .addPersonalQuest("garricks_redemption")
            .addDialogue("battle_won", "Another fight survived. Another day to question why.")
            .addDialogue("alignment_conflict", "I didn't throw away my career to follow another tyrant.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getGarrickAnchor() {
        return new AlignmentSystem.CompanionAnchor("garrick", 85, 60, 25);
        // Very high honor, moderate compassion, very tight tolerance
    }
    
    /**
     * Create Oona Pale-Sap - The Guardian
     * From: The Choke
     * Anchor: 0 Honor / +Compassion
     * Breaking Point: Player threatens the Choke
     * Subversion: Her clan is actually a parasitic organism
     * Good Path: Burns her home to save the world
     * Bad Path: Turns against party to protect the parasite
     */
    public static Companion createOona(GameClass guardianClass) {
        return new Companion.Builder("oona", "Oona Pale-Sap",
            Background.TRIBAL, guardianClass)
            .level(5)
            .stats(new Stats(16, 10, 18, 8, 14, 8))
            .element(Element.EARTH)
            .personality("Protective")
            .backstory("A warrior from the Choke, where humans and forest share one flesh. " +
                      "Oona left to find medicine for her dying sister, unaware that the " +
                      "'medicine' she needs is the death of the forest itself.")
            .combatRole("Tank")
            .preferredTarget("Random")
            .recruitmentLocation("choke_border")
            .startingLoyalty(45)
            .addAbility(CompanionAbilityFactory.createEntanglingRoots())
            .addAbility(CompanionAbilityFactory.createNaturesBlessing())
            .addAbility(CompanionAbilityFactory.createSporeGuardian())
            .addPersonalQuest("oonas_sister")
            .addDialogue("battle_won", "The roots whisper approval.")
            .addDialogue("alignment_conflict", "You threaten my home. Choose your next words carefully.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getOonaAnchor() {
        return new AlignmentSystem.CompanionAnchor("oona", 0, 75, 50);
        // Neutral honor, high compassion, but will defend Choke at any cost
    }
    
    /**
     * Create Balance-Ninth (Theron) - The Monk
     * From: The Zenith
     * Anchor: +Honor / +Compassion (seeking True Neutral)
     * Breaking Point: Player leans too far into extremes
     * Subversion: True enlightenment vs nihilism
     * Good Path: Achieves Bodhisattva state
     * Bad Path: "Void Walker" - Total nihilism
     */
    public static Companion createTheron(GameClass monkClass) {
        return new Companion.Builder("theron", "Balance-Ninth",
            Background.MONK, monkClass)
            .level(7)
            .stats(new Stats(14, 16, 14, 12, 16, 12))
            .element(Element.WIND)
            .personality("Serene")
            .backstory("Ninth of his cohort to attempt the Trial of Balance. The others " +
                      "died or went mad. Theron descended from the Zenith seeking answers: " +
                      "Is true balance enlightenment... or emptiness?")
            .combatRole("Balanced")
            .preferredTarget("Random")
            .recruitmentLocation("zenith_pilgrim_path")
            .startingLoyalty(55)
            .addAbility(CompanionAbilityFactory.createFlowingStrike())
            .addAbility(CompanionAbilityFactory.createInnerBalance())
            .addAbility(CompanionAbilityFactory.createPressurePoint())
            .addPersonalQuest("therons_balance")
            .addDialogue("battle_won", "Violence. Necessary, yet regrettable.")
            .addDialogue("alignment_conflict", "You sway too far. The center holds all truths.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getTheronAnchor() {
        return new AlignmentSystem.CompanionAnchor("theron", 0, 0, 60);
        // True neutral, but high tolerance - only breaks at extremes
    }
    
    /**
     * Create Sash "Velvet" - The Spy
     * From: The Sump
     * Anchor: -Honor / -Compassion
     * Breaking Point: Player becomes too honorable/merciful
     * Subversion: Burn network to save party or betray them
     * Good Path: Sacrifices power for loyalty
     * Bad Path: Betrays party to keep power, stays hollow
     */
    public static Companion createSash(GameClass spyClass) {
        return new Companion.Builder("sash", "Sash \"Velvet\"",
            Background.SPY, spyClass)
            .level(6)
            .stats(new Stats(10, 18, 10, 16, 12, 14))
            .element(Element.DARK)
            .personality("Manipulative")
            .backstory("Sump-born spy with a network spanning three regions. Sash trades " +
                      "in secrets and leverage. She's never had friends, only assets. " +
                      "But the party is starting to feel... different.")
            .combatRole("DPS")
            .preferredTarget("Weakest")
            .recruitmentLocation("sump_silk_market")
            .recruitmentFlag("uncovered_conspiracy")
            .startingLoyalty(25)
            .addAbility(CompanionAbilityFactory.createWhisperedLies())
            .addAbility(CompanionAbilityFactory.createStealSecrets())
            .addAbility(CompanionAbilityFactory.createVelvetTrap())
            .addPersonalQuest("sashs_leverage")
            .addDialogue("battle_won", "Everyone has a weakness. Even them.")
            .addDialogue("alignment_conflict", "Your naivety will get us all killed.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getSashAnchor() {
        return new AlignmentSystem.CompanionAnchor("sash", -75, -60, 30);
        // Low honor, low compassion, low tolerance
    }
    
    /**
     * Create Ghor Iron-Root - The Gentle Giant
     * From: The Choke
     * Anchor: +Honor / +Compassion
     * Breaking Point: Player is too cruel
     * Subversion: Learns wrong lessons from player
     * Good Path: Uses knowledge to help tribe thrive
     * Bad Path: Becomes pillaging Warlord
     */
    public static Companion createGhor(GameClass brawlerClass) {
        return new Companion.Builder("ghor", "Ghor Iron-Root",
            Background.TRIBAL, brawlerClass)
            .level(4)
            .stats(new Stats(20, 8, 18, 6, 10, 8))
            .element(Element.EARTH)
            .personality("Kind")
            .backstory("A massive warrior from the Choke, gentle as a fawn. Ghor left to " +
                      "learn the ways of the 'civilized' world, hoping to bring prosperity " +
                      "back to his tribe. But civilization is teaching him darker lessons.")
            .combatRole("Tank")
            .preferredTarget("Strongest")
            .recruitmentLocation("choke_village")
            .startingLoyalty(70)
            .addAbility(CompanionAbilityFactory.createIronBark())
            .addAbility(CompanionAbilityFactory.createLivingShield())
            .addAbility(CompanionAbilityFactory.createRootedSlam())
            .addPersonalQuest("ghors_education")
            .addDialogue("battle_won", "Ghor sorry. They gave no choice.")
            .addDialogue("alignment_conflict", "Ghor... not understand. Why be cruel when can be kind?")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getGhorAnchor() {
        return new AlignmentSystem.CompanionAnchor("ghor", 70, 85, 35);
        // High honor, very high compassion, moderate tolerance
    }
    
    /**
     * Create Vek "Glass" - The Arcanist
     * From: Exiled to the Sump
     * Anchor: -Honor / -Compassion
     * Breaking Point: Player prevents his ascension
     * Subversion: Godhood vs humanity
     * Good Path: Sacrifices intellect to seal artifact
     * Bad Path: Merges with artifact, mind shattered
     */
    public static Companion createVek(GameClass arcanistClass) {
        return new Companion.Builder("vek", "Vek \"Glass\"",
            Background.OUTCAST, arcanistClass)
            .level(8)
            .stats(new Stats(6, 14, 8, 20, 18, 10))
            .element(Element.NEUTRAL)
            .personality("Obsessive")
            .backstory("Exiled mage living in the Sump's forgotten depths. Vek discovered " +
                      "an eldritch artifact that promises godhood - at the cost of his " +
                      "mortal mind. He calls it 'ascension.' Others call it madness.")
            .combatRole("DPS")
            .preferredTarget("Random")
            .recruitmentLocation("sump_deep_archives")
            .recruitmentFlag("artifact_discovered")
            .startingLoyalty(35)
            .addAbility(CompanionAbilityFactory.createArcaneBarrage())
            .addAbility(CompanionAbilityFactory.createElementalStorm())
            .addAbility(CompanionAbilityFactory.createGlassCannon())
            .addPersonalQuest("veks_ascension")
            .addDialogue("battle_won", "Their screams held such... fascinating resonance.")
            .addDialogue("alignment_conflict", "Morality is a cage. I will transcend it.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getVekAnchor() {
        return new AlignmentSystem.CompanionAnchor("vek", -70, -70, 40);
        // Low honor, low compassion, moderate tolerance
    }
    
    /**
     * Create Maeva Still-Water - The Shaman
     * From: The Choke
     * Anchor: +Honor / +Compassion
     * Breaking Point: Player corrupts nature
     * Subversion: Purify crystal or become corrupted by it
     * Good Path: Purifies the corrupt crystal
     * Bad Path: Crystal weaponizes her sacrifice, becomes tragic boss
     */
    public static Companion createMaeva(GameClass shamanClass) {
        return new Companion.Builder("maeva", "Maeva Still-Water",
            Background.SHAMAN, shamanClass)
            .level(5)
            .stats(new Stats(10, 12, 14, 14, 18, 12))
            .element(Element.WATER)
            .personality("Self-sacrificing")
            .backstory("Shaman of the Still-Water clan, bonded to a crystal that holds " +
                      "the spirits of her ancestors. But the crystal is corrupting, " +
                      "whispering dark promises. Maeva will do anything to save it - " +
                      "even if it costs her soul.")
            .combatRole("Support")
            .preferredTarget("Weakest")
            .recruitmentLocation("choke_spirit_grove")
            .startingLoyalty(55)
            .addAbility(CompanionAbilityFactory.createLightningCall())
            .addAbility(CompanionAbilityFactory.createSoothingRain())
            .addAbility(CompanionAbilityFactory.createCrystalWard())
            .addPersonalQuest("maevas_crystal")
            .addDialogue("battle_won", "The spirits are... restless.")
            .addDialogue("alignment_conflict", "You desecrate the sacred. The spirits will remember.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getMaevaAnchor() {
        return new AlignmentSystem.CompanionAnchor("maeva", 75, 80, 30);
        // High honor, high compassion, low tolerance
    }
    
    /**
     * Create Void-Exile (Silas) - The Fallen Priest
     * From: The Zenith
     * Anchor: -Honor / -Compassion
     * Breaking Point: Player shows weakness/mercy
     * Subversion: Overcome bitterness or let darkness consume him
     * Good Path: Vanquishes inner darkness
     * Bad Path: Darkness wears his skin
     */
    public static Companion createSilas(GameClass priestClass) {
        return new Companion.Builder("silas", "Void-Exile",
            Background.HERETIC, priestClass)
            .level(7)
            .stats(new Stats(12, 10, 14, 16, 18, 10))
            .element(Element.DARK)
            .personality("Bitter")
            .backstory("Former priest of the Zenith, exiled for refusing to let go of his " +
                      "anger. Silas expected enlightenment. Instead he found only suffering. " +
                      "Now a shadow follows him - literally. It offers power, and demands " +
                      "only that he stop fighting it.")
            .combatRole("Support")
            .preferredTarget("Strongest")
            .recruitmentLocation("zenith_exile_path")
            .startingLoyalty(30)
            .addAbility(CompanionAbilityFactory.createTwilightMend())
            .addAbility(CompanionAbilityFactory.createShadowBolt())
            .addAbility(CompanionAbilityFactory.createLifeDrain())
            .addPersonalQuest("silas_shadow")
            .addDialogue("battle_won", "The void takes another. Good.")
            .addDialogue("alignment_conflict", "Your weakness disgusts me.")
            .build();
    }
    
    public static AlignmentSystem.CompanionAnchor getSilasAnchor() {
        return new AlignmentSystem.CompanionAnchor("silas", -80, -75, 25);
        // Very low honor, very low compassion, very tight tolerance
    }
}
