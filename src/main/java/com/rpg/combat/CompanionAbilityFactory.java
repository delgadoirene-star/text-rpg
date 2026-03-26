package com.rpg.combat;

import com.rpg.models.Element;
import com.rpg.models.StatusType;

/**
 * Factory for creating companion signature abilities.
 * Each companion has 3 unique abilities that define their combat identity.
 */
public class CompanionAbilityFactory {
    
    // ==================== Isolde (Vanguard) ====================
    
    public static Ability createRighteousCharge() {
        return new Ability.Builder("isolde_charge", "Righteous Charge", 1)
            .description("Rush to an enemy, dealing damage and taunting them for 2 turns.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.LIGHT)
            .basePower(1.8)
            .addStatusEffect(StatusType.TAUNT, 2, 1.0, 100)
            .cooldown(2)
            .build();
    }
    
    public static Ability createJudgeVerdict() {
        return new Ability.Builder("isolde_verdict", "Judge's Verdict", 2)
            .description("Massive single-target strike. +50% damage against enemies at <50% HP.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.LIGHT)
            .basePower(2.5)
            .addStatusEffect(StatusType.DEF_DOWN, 2, 0.3, 75)
            .cooldown(3)
            .build();
    }
    
    public static Ability createUnyieldingLaw() {
        return new Ability.Builder("isolde_unyielding", "Unyielding Law", 1)
            .description("Gain +15% damage resistance when at full HP. Self-buff.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.NEUTRAL)
            .basePower(0.5)
            .addStatusEffect(StatusType.DEF_UP, 3, 1.5, 100)
            .cooldown(4)
            .build();
    }
    
    // ==================== Bram (Prowler) ====================
    
    public static Ability createBackstab() {
        return new Ability.Builder("bram_backstab", "Backstab", 1)
            .description("Attack from the shadows. +100% crit chance if enemy is unaware.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.DARK)
            .basePower(2.2)
            .addStatusEffect(StatusType.BLEED, 2, 1.5, 80)
            .cooldown(2)
            .build();
    }
    
    public static Ability createVenomBlade() {
        return new Ability.Builder("bram_venom", "Venom Blade", 1)
            .description("Apply a stacking poison that deals damage over 3 turns.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.NEUTRAL)
            .basePower(1.2)
            .addStatusEffect(StatusType.POISON, 3, 2.0, 100)
            .cooldown(2)
            .build();
    }
    
    public static Ability createVanish() {
        return new Ability.Builder("bram_vanish", "Vanish", 1)
            .description("Enter stealth. Next attack gains Backstab bonus and ignores armor.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.DARK)
            .basePower(0.8)
            .addStatusEffect(StatusType.STEALTH, 2, 1.0, 100)
            .addStatusEffect(StatusType.CRIT_UP, 2, 2.0, 100)
            .cooldown(3)
            .build();
    }
    
    // ==================== Sybilla (Chronicler) ====================
    
    public static Ability createAnalyticalStrike() {
        return new Ability.Builder("sybilla_analyze", "Analytical Strike", 1)
            .description("Ranged attack that reveals enemy weaknesses for 3 turns.")
            .damageType(DamageType.MAGICAL)
            .element(Element.NEUTRAL)
            .basePower(1.5)
            .addStatusEffect(StatusType.DEF_DOWN, 3, 0.4, 100)
            .cooldown(1)
            .build();
    }
    
    public static Ability createHealingLight() {
        return new Ability.Builder("sybilla_heal", "Healing Light", 1)
            .description("Restore HP to one ally. More effective on wounded targets.")
            .damageType(DamageType.MAGICAL)
            .element(Element.LIGHT)
            .basePower(2.0)
            .targetType(AbilityTarget.SINGLE_ALLY)
            .addStatusEffect(StatusType.REGEN, 3, 1.0, 100)
            .cooldown(2)
            .build();
    }
    
    public static Ability createArcaneBarrier() {
        return new Ability.Builder("sybilla_barrier", "Arcane Barrier", 2)
            .description("Grant a shield to all allies that absorbs damage for 2 turns.")
            .damageType(DamageType.MAGICAL)
            .element(Element.NEUTRAL)
            .basePower(1.0)
            .targetType(AbilityTarget.ALL_ALLIES)
            .addStatusEffect(StatusType.DEF_UP, 2, 2.0, 100)
            .cooldown(4)
            .build();
    }
    
    // ==================== Garrick (Tactician) ====================
    
    public static Ability createMarkTarget() {
        return new Ability.Builder("garrick_mark", "Mark Target", 1)
            .description("Mark an enemy. All allies deal +20% damage to marked target for 3 turns.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.NEUTRAL)
            .basePower(0.8)
            .addStatusEffect(StatusType.VULNERABLE, 3, 1.2, 100)
            .cooldown(2)
            .build();
    }
    
    public static Ability createExecute() {
        return new Ability.Builder("garrick_execute", "Execute", 2)
            .description("Massive damage to enemies below 30% HP. Instant kill below 15%.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.FIRE)
            .basePower(3.5)
            .addStatusEffect(StatusType.FEAR, 1, 1.0, 50)
            .cooldown(4)
            .build();
    }
    
    public static Ability createRally() {
        return new Ability.Builder("garrick_rally", "Rally", 1)
            .description("Remove debuffs from all allies and grant +10% damage for 2 turns.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.NEUTRAL)
            .basePower(0.5)
            .targetType(AbilityTarget.ALL_ALLIES)
            .addStatusEffect(StatusType.ATK_UP, 2, 1.1, 100)
            .cooldown(3)
            .build();
    }
    
    // ==================== Oona (Wildspeaker) ====================
    
    public static Ability createEntanglingRoots() {
        return new Ability.Builder("oona_roots", "Entangling Roots", 1)
            .description("Root an enemy in place for 2 turns. They cannot move or dodge.")
            .damageType(DamageType.MAGICAL)
            .element(Element.EARTH)
            .basePower(1.0)
            .addStatusEffect(StatusType.SLOW, 2, 1.5, 100)
            .addStatusEffect(StatusType.ROOT, 2, 1.0, 80)
            .cooldown(2)
            .build();
    }
    
    public static Ability createNaturesBlessing() {
        return new Ability.Builder("oona_blessing", "Nature's Blessing", 1)
            .description("Apply regeneration to all allies, healing over 4 turns.")
            .damageType(DamageType.MAGICAL)
            .element(Element.EARTH)
            .basePower(1.2)
            .targetType(AbilityTarget.ALL_ALLIES)
            .addStatusEffect(StatusType.REGEN, 4, 1.0, 100)
            .cooldown(3)
            .build();
    }
    
    public static Ability createSporeGuardian() {
        return new Ability.Builder("oona_spore", "Summon Spore Guardian", 2)
            .description("Summon a creature that taunts enemies and explodes on death.")
            .damageType(DamageType.MAGICAL)
            .element(Element.EARTH)
            .basePower(1.5)
            .addStatusEffect(StatusType.TAUNT, 2, 1.0, 100)
            .addStatusEffect(StatusType.POISON, 3, 1.0, 75)
            .cooldown(4)
            .build();
    }
    
    // ==================== Theron (Monk) ====================
    
    public static Ability createFlowingStrike() {
        return new Ability.Builder("theron_flow", "Flowing Strike", 1)
            .description("Attack that adapts - deals more damage if enemy is strong, heals self if weak.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.WIND)
            .basePower(1.8)
            .addStatusEffect(StatusType.ATK_DOWN, 2, 0.8, 60)
            .cooldown(1)
            .build();
    }
    
    public static Ability createInnerBalance() {
        return new Ability.Builder("theron_balance", "Inner Balance", 1)
            .description("Cleanse all debuffs from self and gain +20% to all stats for 2 turns.")
            .damageType(DamageType.MAGICAL)
            .element(Element.WIND)
            .basePower(0.5)
            .targetType(AbilityTarget.SELF)
            .addStatusEffect(StatusType.ATK_UP, 2, 1.2, 100)
            .addStatusEffect(StatusType.DEF_UP, 2, 1.2, 100)
            .cooldown(3)
            .build();
    }
    
    public static Ability createPressurePoint() {
        return new Ability.Builder("theron_pressure", "Pressure Point", 2)
            .description("Strike that stuns the enemy for 1 turn and reduces their damage by 25%.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.WIND)
            .basePower(1.5)
            .addStatusEffect(StatusType.STUN, 1, 1.0, 100)
            .addStatusEffect(StatusType.ATK_DOWN, 3, 0.75, 100)
            .cooldown(3)
            .build();
    }
    
    // ==================== Sash (Grifter) ====================
    
    public static Ability createWhisperedLies() {
        return new Ability.Builder("sash_lies", "Whispered Lies", 1)
            .description("Confuse an enemy for 2 turns. Confused enemies may attack allies.")
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .basePower(1.0)
            .addStatusEffect(StatusType.CONFUSION, 2, 1.0, 85)
            .cooldown(2)
            .build();
    }
    
    public static Ability createStealSecrets() {
        return new Ability.Builder("sash_steal", "Steal Secrets", 1)
            .description("Remove all buffs from an enemy and apply them to yourself.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.NEUTRAL)
            .basePower(1.2)
            .addStatusEffect(StatusType.DEF_DOWN, 2, 0.5, 100)
            .addStatusEffect(StatusType.ATK_UP, 2, 1.3, 100)
            .cooldown(3)
            .build();
    }
    
    public static Ability createVelvetTrap() {
        return new Ability.Builder("sash_trap", "Velvet Trap", 2)
            .description("Charm an enemy to fight for you for 2 turns. Bosses take damage instead.")
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .basePower(2.0)
            .addStatusEffect(StatusType.CHARM, 2, 1.0, 70)
            .cooldown(4)
            .build();
    }
    
    // ==================== Ghor (Guardian) ====================
    
    public static Ability createIronBark() {
        return new Ability.Builder("ghor_bark", "Iron Bark", 1)
            .description("Gain +50% damage resistance for 2 turns. Taunt all enemies.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.EARTH)
            .basePower(0.3)
            .addStatusEffect(StatusType.DEF_UP, 2, 2.5, 100)
            .addStatusEffect(StatusType.TAUNT, 2, 1.5, 100)
            .cooldown(3)
            .build();
    }
    
    public static Ability createLivingShield() {
        return new Ability.Builder("ghor_shield", "Living Shield", 1)
            .description("Intercept all attacks on a chosen ally for 2 turns.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.EARTH)
            .basePower(0.2)
            .addStatusEffect(StatusType.TAUNT, 2, 2.0, 100)
            .addStatusEffect(StatusType.DEF_UP, 2, 1.5, 100)
            .cooldown(2)
            .build();
    }
    
    public static Ability createRootedSlam() {
        return new Ability.Builder("ghor_slam", "Rooted Slam", 1)
            .description("AoE attack that damages and slows all nearby enemies.")
            .damageType(DamageType.PHYSICAL)
            .element(Element.EARTH)
            .basePower(1.2)
            .targetType(AbilityTarget.ALL_ENEMIES)
            .addStatusEffect(StatusType.SLOW, 2, 1.0, 80)
            .cooldown(2)
            .build();
    }
    
    // ==================== Vek (Evoker) ====================
    
    public static Ability createArcaneBarrage() {
        return new Ability.Builder("vek_barrage", "Arcane Barrage", 2)
            .description("Massive single-target magic damage. Crits deal +100% damage.")
            .damageType(DamageType.MAGICAL)
            .element(Element.NEUTRAL)
            .basePower(3.0)
            .addStatusEffect(StatusType.MAG_DEF_DOWN, 2, 1.0, 50)
            .cooldown(2)
            .build();
    }
    
    public static Ability createElementalStorm() {
        return new Ability.Builder("vek_storm", "Elemental Storm", 2)
            .description("AoE attack hitting all enemies. Element matches battlefield conditions.")
            .damageType(DamageType.MAGICAL)
            .element(Element.FIRE)
            .basePower(2.2)
            .targetType(AbilityTarget.ALL_ENEMIES)
            .addStatusEffect(StatusType.BURN, 2, 1.5, 75)
            .cooldown(3)
            .build();
    }
    
    public static Ability createGlassCannon() {
        return new Ability.Builder("vek_glass", "Glass Cannon", 1)
            .description("+30% magic damage, but -20% HP. Passive self-buff.")
            .damageType(DamageType.MAGICAL)
            .element(Element.NEUTRAL)
            .basePower(0.5)
            .addStatusEffect(StatusType.MAG_ATK_UP, 99, 1.3, 100)
            .addStatusEffect(StatusType.DEF_DOWN, 99, 0.8, 100)
            .cooldown(0)
            .build();
    }
    
    // ==================== Maeva (Stormcaller) ====================
    
    public static Ability createLightningCall() {
        return new Ability.Builder("maeva_lightning", "Lightning Call", 1)
            .description("AoE attack that chains between enemies. More enemies = more damage.")
            .damageType(DamageType.MAGICAL)
            .element(Element.WIND)
            .basePower(1.8)
            .targetType(AbilityTarget.ALL_ENEMIES)
            .addStatusEffect(StatusType.STUN, 1, 1.0, 30)
            .cooldown(2)
            .build();
    }
    
    public static Ability createSoothingRain() {
        return new Ability.Builder("maeva_rain", "Soothing Rain", 1)
            .description("Heal all allies and cleanse one debuff from each.")
            .damageType(DamageType.MAGICAL)
            .element(Element.WATER)
            .basePower(1.5)
            .targetType(AbilityTarget.ALL_ALLIES)
            .addStatusEffect(StatusType.REGEN, 3, 1.0, 100)
            .cooldown(3)
            .build();
    }
    
    public static Ability createCrystalWard() {
        return new Ability.Builder("maeva_ward", "Crystal Ward", 2)
            .description("Shield all allies. The shield reflects a portion of damage taken.")
            .damageType(DamageType.MAGICAL)
            .element(Element.WATER)
            .basePower(1.0)
            .targetType(AbilityTarget.ALL_ALLIES)
            .addStatusEffect(StatusType.DEF_UP, 3, 1.5, 100)
            .addStatusEffect(StatusType.REFLECT, 3, 0.3, 100)
            .cooldown(4)
            .build();
    }
    
    // ==================== Silas (Cleric) ====================
    
    public static Ability createTwilightMend() {
        return new Ability.Builder("silas_mend", "Twilight Mend", 1)
            .description("Heal an ally. Effectiveness scales inversely with Tyrant alignment.")
            .damageType(DamageType.MAGICAL)
            .element(Element.LIGHT)
            .basePower(2.0)
            .targetType(AbilityTarget.SINGLE_ALLY)
            .addStatusEffect(StatusType.REGEN, 2, 0.8, 100)
            .cooldown(1)
            .build();
    }
    
    public static Ability createShadowBolt() {
        return new Ability.Builder("silas_bolt", "Shadow Bolt", 1)
            .description("Dark damage attack. Deals bonus damage if Silas is below 50% HP.")
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .basePower(2.0)
            .addStatusEffect(StatusType.MAG_DEF_DOWN, 2, 1.0, 40)
            .cooldown(1)
            .build();
    }
    
    public static Ability createLifeDrain() {
        return new Ability.Builder("silas_drain", "Life Drain", 1)
            .description("Steal HP from an enemy. The darker the player's path, the more damage.")
            .damageType(DamageType.MAGICAL)
            .element(Element.DARK)
            .basePower(1.8)
            .addStatusEffect(StatusType.DRAIN, 1, 1.0, 100)
            .addStatusEffect(StatusType.ATK_DOWN, 2, 0.9, 50)
            .cooldown(2)
            .build();
    }
}
