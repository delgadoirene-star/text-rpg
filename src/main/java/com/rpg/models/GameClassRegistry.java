package com.rpg.models;

import com.rpg.combat.Ability;
import com.rpg.combat.AbilityTarget;
import com.rpg.combat.DamageType;

/**
 * Registry of all game classes including companion transformation classes
 * Based on the Companion Design Framework with Good/Bad paths
 * 
 * Each class has 2 abilities:
 * - Tier 1: Basic attack (low focus cost)
 * - Tier 2: Special skill (higher cost, status effects or AoE)
 * 
 * Transformation classes get 2 powerful abilities that reflect their path
 */
public class GameClassRegistry {
    
    // ==================== Base Classes ====================
    
    public static GameClass createPaladinClass() {
        return new GameClass.Builder("paladin", "Paladin", GameClass.ClassTier.BASE)
            .description("A holy warrior sworn to uphold justice and protect the innocent.")
            .statGrowth(3, 1, 3, 1, 2, 1)
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("holy_strike", "Holy Strike", 1)
                .description("A righteous blow empowered by faith.")
                .basePower(1.8).element(Element.LIGHT).damageType(DamageType.PHYSICAL)
                .build())
            .addAbility(new Ability.Builder("divine_shield", "Divine Shield", 2)
                .description("Summons a barrier of divine energy to protect an ally.")
                .basePower(0).element(Element.LIGHT).targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.DEF_UP, 3, 30.0, 100)
                .addStatusEffect(StatusType.BARRIER, 3, 20.0, 100)
                .cooldown(4)
                .build())
            .build();
    }
    
    public static GameClass createRogueClass() {
        return new GameClass.Builder("rogue", "Rogue", GameClass.ClassTier.BASE)
            .description("A cunning survivor who relies on speed, wit, and shadows.")
            .statGrowth(1, 4, 1, 2, 1, 3)
            .primaryStats("DEX", "LUK")
            .addAbility(new Ability.Builder("backstab", "Backstab", 1)
                .description("A quick strike from an unexpected angle.")
                .basePower(2.0).element(Element.WIND)
                .addStatusEffect(StatusType.BLEED, 2, 15.0, 40)
                .build())
            .addAbility(new Ability.Builder("smoke_bomb", "Smoke Bomb", 2)
                .description("Hurls a smoke pellet to blind and disorient all enemies.")
                .basePower(0).element(Element.WIND).targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.BLIND, 2, 25.0, 80)
                .addStatusEffect(StatusType.ACCURACY_DOWN, 3, 20.0, 100)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * SASH "VELVET" PATHS
     * Base: Spy
     */
    
    /**
     * Good Path: Selfless Spy
     * Sacrifices herself to protect the party from betrayal
     */
    public static GameClass createSelflessSpyClass() {
        return new GameClass.Builder("selfless_spy", "Selfless Spy", GameClass.ClassTier.SECRET)
            .description("A spy who discovered that some assets are worth more than profit - " +
                        "like trust, loyalty, and the lives of those she once manipulated.")
            .prerequisite("SPY")
            .requiredLevel(10)
            .statGrowth(2, 4, 2, 3, 3, 4)  // High DEX, WIS, LUK
            .primaryStats("DEX", "WIS", "LUK")
            .addAbility(new Ability.Builder("double_agent", "Double Agent", 1)
                .description("Convinces an enemy you've switched sides, then strikes from within.")
                .basePower(2.5).element(Element.WIND).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.CHARM, 2, 100.0, 70)
                .addStatusEffect(StatusType.DEF_DOWN, 3, 40.0, 100)
                .build())
            .addAbility(new Ability.Builder("noble_sacrifice", "Noble Sacrifice", 2)
                .description("Takes a fatal blow meant for an ally.")
                .basePower(0).element(Element.LIGHT).targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.INVULNERABLE, 2, 0.0, 100)
                .addStatusEffect(StatusType.BARRIER, 4, 50.0, 100)
                .cooldown(6)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Velvet Assassin
     * Betrays the party for personal gain
     */
    public static GameClass createVelvetAssassinClass() {
        return new GameClass.Builder("velvet_assassin", "Velvet Assassin", GameClass.ClassTier.SECRET)
            .description("A spy who decided that the only person worth trusting is herself. " +
                        "Her smile hides a blade, and her friendship hides a contract.")
            .prerequisite("SPY")
            .requiredLevel(10)
            .statGrowth(2, 5, 1, 3, 1, 5)  // Very high DEX, LUK
            .primaryStats("DEX", "LUK")
            .addAbility(new Ability.Builder("velvet_kiss", "Velvet Kiss", 1)
                .description("A poisoned blade hidden in a caress.")
                .basePower(2.8).element(Element.DARK).damageType(DamageType.PHYSICAL)
                .addStatusEffect(StatusType.POISON, 4, 25.0, 100)
                .addStatusEffect(StatusType.SILENCE, 2, 100.0, 60)
                .build())
            .addAbility(new Ability.Builder("betrayal", "Betrayal", 2)
                .description("Switches sides mid-battle, attacking the party with full force.")
                .basePower(4.0).element(Element.DARK).damageType(DamageType.TRUE)
                .targetType(AbilityTarget.SINGLE_ENEMY)
                .addStatusEffect(StatusType.STUN, 2, 0.0, 80)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * MAEVA STILL-WATER PATHS
     * Base: Shaman
     */
    
    /**
     * Good Path: Crystal Sage
     * Purifies the corrupt crystal, becoming a beacon of ancestral wisdom
     */
    public static GameClass createCrystalSageClass() {
        return new GameClass.Builder("crystal_sage", "Crystal Sage", GameClass.ClassTier.SECRET)
            .description("A shaman who purified the corrupt crystal, unlocking the full wisdom " +
                        "of her ancestors. She is a bridge between worlds, life and death.")
            .prerequisite("SHAMAN")
            .requiredLevel(10)
            .statGrowth(1, 3, 3, 4, 5, 2)  // High INT, WIS
            .primaryStats("WIS", "INT")
            .addAbility(new Ability.Builder("crystal_blessing", "Crystal Blessing", 1)
                .description("Channels purified ancestral energy to heal and protect.")
                .basePower(3.0).element(Element.WATER).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.REGEN, 3, 20.0, 100)
                .addStatusEffect(StatusType.MAG_DEF_UP, 3, 25.0, 100)
                .cooldown(3)
                .build())
            .addAbility(new Ability.Builder("ancestral_fury", "Ancestral Fury", 2)
                .description("Calls upon purified ancestors to smite those who defile nature.")
                .basePower(3.5).element(Element.WATER).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.FREEZE, 2, 0.0, 40)
                .addStatusEffect(StatusType.SPD_DOWN, 3, 25.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Corrupted Crystal
     * Crystal weaponizes her sacrifice, becoming a tragic boss
     */
    public static GameClass createCorruptedCrystalClass() {
        return new GameClass.Builder("corrupted_crystal", "Corrupted Crystal", GameClass.ClassTier.SECRET)
            .description("A shaman consumed by the corrupt crystal she tried to save. " +
                        "Her body is now a vessel for something ancient and hungry.")
            .prerequisite("SHAMAN")
            .requiredLevel(10)
            .statGrowth(2, 2, 4, 5, 3, 2)  // High INT, VIT
            .primaryStats("INT", "VIT")
            .addAbility(new Ability.Builder("crystal_shatter", "Crystal Shatter", 1)
                .description("Sends shards of corrupt crystal flying at enemies.")
                .basePower(2.5).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.BLEED, 3, 20.0, 70)
                .build())
            .addAbility(new Ability.Builder("soul_harvest", "Soul Harvest", 2)
                .description("The crystal drains life force from all nearby, healing its host.")
                .basePower(3.0).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL)
                .addStatusEffect(StatusType.REGEN, 3, 35.0, 100) // Self heal
                .addStatusEffect(StatusType.CURSE, 3, 20.0, 80)
                .cooldown(5)
                .build())
            .build();
    }
    
    public static GameClass createScholarClass() {
        return new GameClass.Builder("scholar", "Scholar", GameClass.ClassTier.BASE)
            .description("A learned healer and researcher who seeks knowledge to help others.")
            .statGrowth(1, 1, 2, 3, 3, 1)
            .primaryStats("INT", "WIS")
            .addAbility(new Ability.Builder("mending_touch", "Mending Touch", 1)
                .description("Gentle healing energy mends wounds.")
                .basePower(2.0).element(Element.NEUTRAL).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.REGEN, 2, 10.0, 50)
                .build())
            .addAbility(new Ability.Builder("weaken", "Weaken", 2)
                .description("Analyzes and exploits an enemy's physical weakness.")
                .basePower(1.2).element(Element.NEUTRAL).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.ATK_DOWN, 3, 25.0, 100)
                .addStatusEffect(StatusType.DEF_DOWN, 3, 20.0, 60)
                .cooldown(3)
                .build())
            .build();
    }
    
    public static GameClass createWarriorClass() {
        return new GameClass.Builder("warrior", "Warrior", GameClass.ClassTier.BASE)
            .description("A disciplined fighter who meets every challenge head-on.")
            .statGrowth(4, 2, 3, 1, 1, 1)
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("power_slash", "Power Slash", 1)
                .description("A devastating overhead strike.")
                .basePower(2.2).element(Element.EARTH).damageType(DamageType.PHYSICAL)
                .addStatusEffect(StatusType.DEF_DOWN, 2, 15.0, 30)
                .build())
            .addAbility(new Ability.Builder("battle_cry", "Battle Cry", 2)
                .description("A roar that rallies allies and intimidates foes.")
                .basePower(0).element(Element.NEUTRAL).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.ATK_UP, 3, 25.0, 100)
                .addStatusEffect(StatusType.SPD_UP, 2, 15.0, 100)
                .cooldown(4)
                .build())
            .build();
    }
    
    public static GameClass createGuardianClass() {
        return new GameClass.Builder("guardian", "Guardian", GameClass.ClassTier.BASE)
            .description("A nature-touched protector who shields others with primal strength.")
            .statGrowth(3, 1, 4, 1, 2, 1)
            .primaryStats("STR", "VIT", "WIS")
            .addAbility(new Ability.Builder("root_slam", "Root Slam", 1)
                .description("Slams the ground, sending roots erupting beneath the target.")
                .basePower(1.8).element(Element.EARTH)
                .addStatusEffect(StatusType.SPD_DOWN, 2, 20.0, 50)
                .build())
            .addAbility(new Ability.Builder("barkskin", "Barkskin", 2)
                .description("Hardens skin to living bark, drastically increasing defense.")
                .basePower(0).element(Element.EARTH).targetType(AbilityTarget.SELF)
                .addStatusEffect(StatusType.DEF_UP, 4, 40.0, 100)
                .addStatusEffect(StatusType.MAG_DEF_UP, 3, 30.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    public static GameClass createMonkClass() {
        return new GameClass.Builder("monk", "Monk", GameClass.ClassTier.BASE)
            .description("A disciplined ascetic who channels inner balance into devastating strikes.")
            .statGrowth(2, 3, 2, 1, 3, 1)
            .primaryStats("DEX", "WIS")
            .addAbility(new Ability.Builder("palm_strike", "Palm Strike", 1)
                .description("A precise strike targeting pressure points.")
                .basePower(1.9).element(Element.NEUTRAL)
                .addStatusEffect(StatusType.STUN, 1, 20.0, 15)
                .build())
            .addAbility(new Ability.Builder("inner_peace", "Inner Peace", 2)
                .description("Meditates briefly to restore body and mind.")
                .basePower(2.5).element(Element.NEUTRAL).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.SELF)
                .addStatusEffect(StatusType.REGEN, 3, 15.0, 100)
                .cooldown(3)
                .build())
            .build();
    }
    
    public static GameClass createSpyClass() {
        return new GameClass.Builder("spy", "Spy", GameClass.ClassTier.BASE)
            .description("A master of deception and information who operates in the spaces between.")
            .statGrowth(1, 3, 1, 2, 2, 3)
            .primaryStats("DEX", "INT", "LUK")
            .addAbility(new Ability.Builder("silenced_stab", "Silenced Stab", 1)
                .description("A precise thrust that prevents the target from crying out.")
                .basePower(1.7).element(Element.WIND)
                .addStatusEffect(StatusType.SILENCE, 2, 100.0, 60)
                .build())
            .addAbility(new Ability.Builder("false_identity", "False Identity", 2)
                .description("Convincingly assumes the role of an ally, redirecting attacks.")
                .basePower(0).element(Element.NEUTRAL).targetType(AbilityTarget.SELF)
                .addStatusEffect(StatusType.EVASION_UP, 3, 35.0, 100)
                .addStatusEffect(StatusType.COUNTER, 2, 20.0, 100)
                .cooldown(4)
                .build())
            .build();
    }
    
    public static GameClass createBrawlerClass() {
        return new GameClass.Builder("brawler", "Brawler", GameClass.ClassTier.BASE)
            .description("A primal fighter who draws power from raw nature and fierce will.")
            .statGrowth(4, 2, 4, 1, 1, 1)
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("crush", "Crush", 1)
                .description("An overwhelming blow that shatters defenses.")
                .basePower(2.4).element(Element.EARTH).damageType(DamageType.PHYSICAL)
                .addStatusEffect(StatusType.DEF_DOWN, 2, 30.0, 70)
                .build())
            .addAbility(new Ability.Builder("entangle", "Entangle", 2)
                .description("Roots and vines erupt to ensnare all enemies.")
                .basePower(0.8).element(Element.EARTH).targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.SPD_DOWN, 3, 30.0, 80)
                .addStatusEffect(StatusType.STUN, 1, 0.0, 25)
                .cooldown(4)
                .build())
            .build();
    }
    
    public static GameClass createArcanistClass() {
        return new GameClass.Builder("arcanist", "Arcanist", GameClass.ClassTier.BASE)
            .description("A wielder of arcane forces who walks the line between genius and madness.")
            .statGrowth(1, 1, 1, 4, 3, 2)
            .primaryStats("INT", "WIS")
            .addAbility(new Ability.Builder("arcane_bolt", "Arcane Bolt", 1)
                .description("A lance of pure magical energy.")
                .basePower(2.0).element(Element.NEUTRAL).damageType(DamageType.MAGICAL)
                .build())
            .addAbility(new Ability.Builder("ward", "Arcane Ward", 2)
                .description("Creates a protective ward that absorbs incoming damage.")
                .basePower(0).element(Element.NEUTRAL).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.BARRIER, 3, 25.0, 100)
                .addStatusEffect(StatusType.MAG_DEF_UP, 3, 20.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    public static GameClass createShamanClass() {
        return new GameClass.Builder("shaman", "Shaman", GameClass.ClassTier.BASE)
            .description("A spirit-talker who channels the voices of nature and the dead.")
            .statGrowth(1, 2, 2, 3, 4, 1)
            .primaryStats("WIS", "INT")
            .addAbility(new Ability.Builder("spirit_blast", "Spirit Blast", 1)
                .description("Channels ancestral spirits into a blast of energy.")
                .basePower(1.9).element(Element.NEUTRAL).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.CURSE, 2, 10.0, 30)
                .build())
            .addAbility(new Ability.Builder("commune", "Commune with Spirits", 2)
                .description("Calls upon ancestral spirits to bless the party.")
                .basePower(0).element(Element.NEUTRAL).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.ATK_UP, 3, 20.0, 100)
                .addStatusEffect(StatusType.DEF_UP, 3, 20.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    public static GameClass createPriestClass() {
        return new GameClass.Builder("priest", "Priest", GameClass.ClassTier.BASE)
            .description("A fallen servant of light who struggles against encroaching darkness.")
            .statGrowth(1, 1, 2, 3, 4, 1)
            .primaryStats("WIS", "INT")
            .addAbility(new Ability.Builder("smite", "Smite", 1)
                .description("Channels divine light into a punishing strike.")
                .basePower(2.0).element(Element.LIGHT).damageType(DamageType.MAGICAL)
                .build())
            .addAbility(new Ability.Builder("shadow_grasp", "Shadow Grasp", 2)
                .description("Dark tendrils reach from below, entangling the target.")
                .basePower(1.5).element(Element.DARK).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.CURSE, 3, 15.0, 80)
                .addStatusEffect(StatusType.SPD_DOWN, 2, 20.0, 100)
                .cooldown(3)
                .build())
            .build();
    }
    
    // ==================== Companion Transformation Classes ====================
    
    /**
     * ISOLDE VEX-TORVATH PATHS
     * Base: Paladin
     */
    
    /**
     * Good Path: Redeemer
     * Isolde embraces compassion and finds that true strength comes from mercy
     */
    public static GameClass createRedeemerClass() {
        return new GameClass.Builder("redeemer", "Redeemer", GameClass.ClassTier.SECRET)
            .description("A paladin who discovered that mercy is not weakness, but the highest form of strength. " +
                        "Channels divine power through compassion.")
            .prerequisite("PALADIN")
            .requiredLevel(10)
            .statGrowth(3, 2, 4, 1, 3, 2)  // High STR, VIT, WIS
            .primaryStats("STR", "VIT", "WIS")
            .addAbility(new Ability.Builder("absolution", "Absolution", 1)
                .description("Channels mercy itself into a healing strike that also damages undead.")
                .basePower(2.2).element(Element.LIGHT).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.CURSE, 2, 20.0, 50) // Purges corruption
                .build())
            .addAbility(new Ability.Builder("martyrdom", "Martyrdom", 2)
                .description("Takes an ally's wounds upon yourself, healing them fully.")
                .basePower(4.0).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.REGEN, 3, 20.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Iron Judge
     * Isolde becomes cruelly righteous, viewing mercy as heresy
     */
    public static GameClass createIronJudgeClass() {
        return new GameClass.Builder("iron_judge", "Iron Judge", GameClass.ClassTier.SECRET)
            .description("A paladin consumed by rigid righteousness. Every law must be enforced, " +
                        "every weakness purged. Justice without mercy.")
            .prerequisite("PALADIN")
            .requiredLevel(10)
            .statGrowth(4, 1, 3, 1, 2, 2)  // Very high STR, lower WIS
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("judgement", "Judgement", 1)
                .description("Strikes with the full weight of absolute law.")
                .basePower(2.5).element(Element.LIGHT).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.FEAR, 2, 30.0, 60)
                .build())
            .addAbility(new Ability.Builder("iron_decree", "Iron Decree", 2)
                .description("Issues an unbreakable decree: no mercy, no quarter.")
                .basePower(0).element(Element.LIGHT).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.ATK_UP, 4, 35.0, 100)
                .addStatusEffect(StatusType.COUNTER, 3, 25.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * BRAM "THE VULTURE" PATHS
     * Base: Rogue
     */
    
    /**
     * Good Path: Spymaster
     * Burns the corrupt system down, becomes a revolutionary leader
     */
    public static GameClass createSpymasterClass() {
        return new GameClass.Builder("spymaster", "Spymaster", GameClass.ClassTier.SECRET)
            .description("A rogue who turned rebellion into art. Master of networks, " +
                        "breaker of chains, voice for the voiceless.")
            .prerequisite("ROGUE")
            .requiredLevel(10)
            .statGrowth(2, 5, 2, 4, 2, 3)  // High DEX, INT, LUK
            .primaryStats("DEX", "INT", "LUK")
            .addAbility(new Ability.Builder("network_collapse", "Network Collapse", 1)
                .description("Triggers a chain reaction across an enemy spy network.")
                .basePower(2.0).element(Element.WIND).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.ACCURACY_DOWN, 2, 30.0, 70)
                .build())
            .addAbility(new Ability.Builder("false_flag", "False Flag", 2)
                .description("Turns enemies against each other with planted evidence.")
                .basePower(1.5).element(Element.WIND)
                .addStatusEffect(StatusType.CHARM, 2, 100.0, 60)
                .addStatusEffect(StatusType.SILENCE, 3, 100.0, 80)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Contractor
     * Sells soul to save his family, becomes a cold professional killer
     */
    public static GameClass createContractorClass() {
        return new GameClass.Builder("contractor", "Contractor", GameClass.ClassTier.SECRET)
            .description("A rogue who made the ultimate sacrifice: his humanity. " +
                        "Now he's the blade in the dark, efficient and emotionless.")
            .prerequisite("ROGUE")
            .requiredLevel(10)
            .statGrowth(3, 5, 1, 2, 1, 4)  // Very high DEX, LUK
            .primaryStats("DEX", "LUK")
            .addAbility(new Ability.Builder("death_mark", "Death Mark", 1)
                .description("Marks a target for elimination. The next hit deals true damage.")
                .basePower(3.0).element(Element.DARK).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.BLEED, 3, 25.0, 100)
                .build())
            .addAbility(new Ability.Builder("clean_cut", "Clean Cut", 2)
                .description("A perfectly calculated strike that ignores all defenses.")
                .basePower(3.5).element(Element.DARK).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.STUN, 1, 0.0, 40)
                .cooldown(3)
                .build())
            .build();
    }
    
    /**
     * DR. SYBILLA VES-VAEL PATHS
     * Base: Scholar
     */
    
    /**
     * Good Path: True Healer
     * Takes the slow, harmless path to cure the plague
     */
    public static GameClass createTrueHealerClass() {
        return new GameClass.Builder("true_healer", "True Healer", GameClass.ClassTier.SECRET)
            .description("A scholar who proved that ethics and progress are not enemies. " +
                        "Her cure saved thousands without costing a single innocent life.")
            .prerequisite("SCHOLAR")
            .requiredLevel(10)
            .statGrowth(1, 2, 3, 4, 5, 2)  // High INT, WIS
            .primaryStats("INT", "WIS")
            .addAbility(new Ability.Builder("sanctuary", "Sanctuary", 1)
                .description("Creates a zone where no violence can occur. Enemies' attacks heal allies.")
                .basePower(3.5).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.BARRIER, 2, 30.0, 100)
                .cooldown(3)
                .build())
            .addAbility(new Ability.Builder("panacea", "Panacea", 2)
                .description("A cure that purifies all ailments and grants regeneration.")
                .basePower(5.0).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.REGEN, 5, 25.0, 100)
                .addStatusEffect(StatusType.INVULNERABLE, 1, 0.0, 100)
                .cooldown(6)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Blood Alchemist
     * Takes instant cure via horrific sacrifice
     */
    public static GameClass createBloodAlchemistClass() {
        return new GameClass.Builder("blood_alchemist", "Blood Alchemist", GameClass.ClassTier.SECRET)
            .description("A scholar who discovered that progress demands sacrifice - " +
                        "of others. The cure works. The cost is unspeakable.")
            .prerequisite("SCHOLAR")
            .requiredLevel(10)
            .statGrowth(1, 3, 2, 5, 2, 3)  // Very high INT
            .primaryStats("INT", "DEX")
            .addAbility(new Ability.Builder("blood_siphon", "Blood Siphon", 1)
                .description("Drains life from a target, healing yourself.")
                .basePower(2.5).element(Element.DARK).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.BLEED, 3, 20.0, 80)
                .addStatusEffect(StatusType.REGEN, 2, 20.0, 100) // Self heal on hit
                .build())
            .addAbility(new Ability.Builder("blight", "Blight", 2)
                .description("Unleashes a plague that corrodes flesh and metal alike.")
                .basePower(2.8).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.POISON, 4, 30.0, 100)
                .addStatusEffect(StatusType.DEF_DOWN, 3, 35.0, 80)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * Good Path: Shield-Captain
     * Finds closure and renewed purpose protecting others
     */
    public static GameClass createShieldCaptainClass() {
        return new GameClass.Builder("shield_captain", "Shield-Captain", GameClass.ClassTier.SECRET)
            .description("A veteran who finally made peace with his past. " +
                        "His shield protects not from orders, but from conviction.")
            .prerequisite("WARRIOR")
            .requiredLevel(10)
            .statGrowth(4, 2, 5, 1, 3, 1)  // High STR, VIT, WIS
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("iron_wall", "Iron Wall", 1)
                .description("Braces for impact, redirecting all damage from allies to self.")
                .basePower(0).element(Element.EARTH).targetType(AbilityTarget.SELF)
                .addStatusEffect(StatusType.DEF_UP, 3, 50.0, 100)
                .addStatusEffect(StatusType.BARRIER, 3, 40.0, 100)
                .addStatusEffect(StatusType.COUNTER, 2, 30.0, 100)
                .cooldown(4)
                .build())
            .addAbility(new Ability.Builder("rally", "Rally", 2)
                .description("A commanding shout that inspires unwavering loyalty.")
                .basePower(0).element(Element.NEUTRAL).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.ATK_UP, 3, 25.0, 100)
                .addStatusEffect(StatusType.DEF_UP, 3, 25.0, 100)
                .addStatusEffect(StatusType.SPD_UP, 2, 15.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Reaver
     * Becomes cynical mercenary, fighting only for coin
     */
    public static GameClass createReaverClass() {
        return new GameClass.Builder("reaver", "Reaver", GameClass.ClassTier.SECRET)
            .description("A veteran who learned that ideals are lies. " +
                        "Now he sells his sword to the highest bidder - nothing more.")
            .prerequisite("WARRIOR")
            .requiredLevel(10)
            .statGrowth(5, 2, 3, 1, 1, 3)  // Very high STR, low WIS
            .primaryStats("STR", "DEX")
            .addAbility(new Ability.Builder("carnage", "Carnage", 1)
                .description("A brutal flurry of strikes fueled by rage.")
                .basePower(2.8).element(Element.FIRE).damageType(DamageType.PHYSICAL)
                .addStatusEffect(StatusType.BLEED, 3, 25.0, 70)
                .addStatusEffect(StatusType.BERSERK, 3, 0.0, 100) // Self buff/debuff
                .build())
            .addAbility(new Ability.Builder("blood_price", "Blood Price", 2)
                .description("Sacrifices health for devastating damage.")
                .basePower(4.0).element(Element.FIRE).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.ATK_UP, 2, 40.0, 100)
                .addStatusEffect(StatusType.DEF_DOWN, 3, 30.0, 100) // Trade-off
                .cooldown(3)
                .build())
            .build();
    }
    
    /**
     * Good Path: Bodhisattva
     * Achieves true enlightenment
     */
    public static GameClass createBodhisattvaClass() {
        return new GameClass.Builder("bodhisattva", "Bodhisattva", GameClass.ClassTier.SECRET)
            .description("A monk who found the center of all things. " +
                        "Perfect balance, perfect peace, perfect understanding.")
            .prerequisite("MONK")
            .requiredLevel(10)
            .statGrowth(3, 4, 3, 3, 4, 3)  // Perfectly balanced growth
            .primaryStats("DEX", "WIS")
            .addAbility(new Ability.Builder("enlightened_strike", "Enlightened Strike", 1)
                .description("A strike that transcends physical form, striking at the soul.")
                .basePower(2.5).element(Element.LIGHT).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.SILENCE, 2, 100.0, 50)
                .build())
            .addAbility(new Ability.Builder("transcendent_aura", "Transcendent Aura", 2)
                .description("An aura of perfect balance that protects and heals all allies.")
                .basePower(3.0).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.REGEN, 4, 20.0, 100)
                .addStatusEffect(StatusType.MAG_DEF_UP, 3, 30.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Void Walker
     * Falls into total nihilism
     */
    public static GameClass createVoidWalkerClass() {
        return new GameClass.Builder("void_walker", "Void Walker", GameClass.ClassTier.SECRET)
            .description("A monk who stared into the void and found... nothing. " +
                        "No meaning, no purpose, only the cold certainty of emptiness.")
            .prerequisite("MONK")
            .requiredLevel(10)
            .statGrowth(2, 5, 2, 2, 2, 5)  // High DEX, LUK - erratic
            .primaryStats("DEX", "LUK")
            .addAbility(new Ability.Builder("void_strike", "Void Strike", 1)
                .description("A strike that erases part of reality itself.")
                .basePower(2.8).element(Element.DARK).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.FEAR, 2, 100.0, 40)
                .build())
            .addAbility(new Ability.Builder("entropic_decay", "Entropic Decay", 2)
                .description("Accelerates the inevitable death of all things.")
                .basePower(2.0).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.CURSE, 4, 25.0, 100)
                .addStatusEffect(StatusType.SPD_DOWN, 3, 30.0, 80)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * GHOR IRON-ROOT PATHS
     * Base: Brawler
     */
    
    /**
     * Good Path: Chieftain
     * Uses knowledge to help tribe prosper
     */
    public static GameClass createChieftainClass() {
        return new GameClass.Builder("chieftain", "Chieftain", GameClass.ClassTier.SECRET)
            .description("A warrior who brought wisdom back to his people. " +
                        "Under his guidance, the tribe thrives.")
            .prerequisite("BRAWLER")
            .requiredLevel(10)
            .statGrowth(4, 2, 5, 2, 3, 1)  // High STR, VIT, WIS
            .primaryStats("STR", "VIT", "WIS")
            .addAbility(new Ability.Builder("tribal_fury", "Tribal Fury", 1)
                .description("Channels the fury of generations into a devastating strike.")
                .basePower(2.8).element(Element.EARTH).damageType(DamageType.PHYSICAL)
                .addStatusEffect(StatusType.STUN, 2, 0.0, 30)
                .build())
            .addAbility(new Ability.Builder("ancestral_call", "Ancestral Call", 2)
                .description("Summons ancestral spirits to fight alongside the tribe.")
                .basePower(0).element(Element.EARTH).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.ATK_UP, 4, 30.0, 100)
                .addStatusEffect(StatusType.DEF_UP, 4, 30.0, 100)
                .addStatusEffect(StatusType.COUNTER, 2, 20.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Warlord
     * Learns cruelty from civilization, becomes conquerer
     */
    public static GameClass createWarlordClass() {
        return new GameClass.Builder("warlord", "Warlord", GameClass.ClassTier.SECRET)
            .description("A warrior who learned the wrong lessons. " +
                        "Civilization taught him that strength crushes weakness. " +
                        "Now he brings fire and blood.")
            .prerequisite("BRAWLER")
            .requiredLevel(10)
            .statGrowth(6, 1, 4, 1, 1, 2)  // Very high STR, VIT
            .primaryStats("STR", "VIT")
            .addAbility(new Ability.Builder("reign_of_terror", "Reign of Terror", 1)
                .description("An overwhelming assault that crushes all resistance.")
                .basePower(3.0).element(Element.FIRE).damageType(DamageType.PHYSICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.FEAR, 2, 30.0, 60)
                .build())
            .addAbility(new Ability.Builder("scorched_earth", "Scorched Earth", 2)
                .description("Burns everything, friend and foe alike, to claim victory.")
                .basePower(3.5).element(Element.FIRE).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL)
                .addStatusEffect(StatusType.BURN, 4, 30.0, 100)
                .addStatusEffect(StatusType.ATK_UP, 3, 40.0, 100) // Self buff
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Good Path: Selfless Scholar
     * Sacrifices intellect to seal eldritch artifact
     */
    public static GameClass createSelflessScholarClass() {
        return new GameClass.Builder("selfless_scholar", "Selfless Scholar", GameClass.ClassTier.SECRET)
            .description("A mage who gave up godhood to save the world. " +
                        "His mind is dimmed, but his soul burns bright.")
            .prerequisite("ARCANIST")
            .requiredLevel(10)
            .statGrowth(2, 2, 4, 3, 4, 2)  // Balanced, higher WIS than INT
            .primaryStats("VIT", "WIS")
            .addAbility(new Ability.Builder("soul_shield", "Soul Shield", 1)
                .description("Sacrifices personal power to create a protective barrier for all allies.")
                .basePower(0).element(Element.LIGHT).targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.BARRIER, 3, 35.0, 100)
                .addStatusEffect(StatusType.INVULNERABLE, 1, 0.0, 100)
                .cooldown(4)
                .build())
            .addAbility(new Ability.Builder("sacrifice", "Willing Sacrifice", 2)
                .description("Gives up your life force to fully heal and empower an ally.")
                .basePower(6.0).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.SINGLE_ALLY)
                .addStatusEffect(StatusType.REGEN, 5, 30.0, 100)
                .addStatusEffect(StatusType.ATK_UP, 4, 25.0, 100)
                .cooldown(6)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Fractured God
     * Merges with artifact, godhood at cost of sanity
     */
    public static GameClass createFracturedGodClass() {
        return new GameClass.Builder("fractured_god", "Fractured God", GameClass.ClassTier.SECRET)
            .description("A mage who achieved transcendence and lost everything. " +
                        "Infinite power, infinite madness.")
            .prerequisite("ARCANIST")
            .requiredLevel(10)
            .statGrowth(1, 2, 1, 7, 3, 3)  // Insanely high INT
            .primaryStats("INT", "WIS")
            .addAbility(new Ability.Builder("reality_fracture", "Reality Fracture", 1)
                .description("Tears a hole in reality itself, exposing enemies to raw chaos.")
                .basePower(3.5).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.FREEZE, 1, 0.0, 20)
                .addStatusEffect(StatusType.BURN, 3, 25.0, 40)
                .build())
            .addAbility(new Ability.Builder("divine_wrath", "Divine Wrath", 2)
                .description("Unleashes the full fury of a fractured god upon a single target.")
                .basePower(5.0).element(Element.DARK).damageType(DamageType.TRUE)
                .addStatusEffect(StatusType.CURSE, 5, 35.0, 100)
                .addStatusEffect(StatusType.SILENCE, 3, 100.0, 80)
                .cooldown(4)
                .build())
            .build();
    }
    
    /**
     * Good Path: Purified
     * Overcomes bitterness, vanquishes inner darkness
     */
    public static GameClass createPurifiedClass() {
        return new GameClass.Builder("purified", "Purified", GameClass.ClassTier.SECRET)
            .description("A fallen priest who found redemption. " +
                        "The shadow is gone, and light remains.")
            .prerequisite("PRIEST")
            .requiredLevel(10)
            .statGrowth(2, 2, 3, 3, 5, 2)  // High WIS
            .primaryStats("WIS", "INT")
            .addAbility(new Ability.Builder("radiance", "Radiance", 1)
                .description("Bursts of holy light that purify corruption.")
                .basePower(2.5).element(Element.LIGHT).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.CURSE, 1, 0.0, 80) // Removes curses
                .build())
            .addAbility(new Ability.Builder("redemption_wave", "Redemption Wave", 2)
                .description("A wave of purifying light that heals allies and smites enemies.")
                .basePower(3.0).element(Element.LIGHT).damageType(DamageType.HEALING)
                .targetType(AbilityTarget.ALL_ALLIES)
                .addStatusEffect(StatusType.REGEN, 4, 20.0, 100)
                .addStatusEffect(StatusType.MAG_ATK_UP, 3, 25.0, 100)
                .cooldown(5)
                .build())
            .build();
    }
    
    /**
     * Bad Path: Shadow-Worn
     * Darkness consumes him completely
     */
    public static GameClass createShadowWornClass() {
        return new GameClass.Builder("shadow_worn", "Shadow-Worn", GameClass.ClassTier.SECRET)
            .description("A fallen priest who let the darkness win. " +
                        "Now it wears his skin and speaks with his voice.")
            .prerequisite("PRIEST")
            .requiredLevel(10)
            .statGrowth(2, 3, 2, 4, 4, 2)  // High INT, WIS but corrupted
            .primaryStats("INT", "WIS")
            .addAbility(new Ability.Builder("soul_drain", "Soul Drain", 1)
                .description("Drains the essence from a target, feeding the darkness within.")
                .basePower(2.8).element(Element.DARK).damageType(DamageType.MAGICAL)
                .addStatusEffect(StatusType.POISON, 3, 20.0, 100)
                .addStatusEffect(StatusType.REGEN, 2, 15.0, 100) // Self heal
                .build())
            .addAbility(new Ability.Builder("umbral_dominion", "Umbral Dominion", 2)
                .description("Plunges the battlefield into darkness, weakening all enemies.")
                .basePower(2.0).element(Element.DARK).damageType(DamageType.MAGICAL)
                .targetType(AbilityTarget.ALL_ENEMIES)
                .addStatusEffect(StatusType.BLIND, 3, 40.0, 100)
                .addStatusEffect(StatusType.MAG_DEF_DOWN, 3, 30.0, 80)
                .addStatusEffect(StatusType.FEAR, 2, 100.0, 50)
                .cooldown(5)
                .build())
            .build();
    }
}
