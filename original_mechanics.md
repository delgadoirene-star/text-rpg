# INTERNAL / SPOILERS — Companion Mechanics

**Audience:** Developers, designers, QA.

This document contains **mechanics** for the companion system (including recruitment requirements, loyalty/friendship systems, party dynamics rules, and all signature abilities).

For spoiler-heavy narrative content (backstories, quest subversions, transformation story outcomes), see `COMPANION_DESIGN_INTERNAL.md`.

---

# Source of Truth

As of **2026-03-26**, this file is being rebuilt from the companion design source (`COMPANIONS.md` at commit `c77dcf97aa7120ad6c9fae654a25e819323ff4b7`).

If you update companion mechanics in the future, update this document and keep `COMPANIONS.md` spoiler-light.

---

# System-Level Mechanics (TODO)

> TODO: Move the following systems sections from the old combined doc into this file:
> - Party size / reserve rules / swapping
> - Loyalty (0–100) breakpoints + effects
> - Friendship (0–10) bonuses
> - Recruitment mechanical gates (stats / party composition)
> - Party dynamics tables (positive/negative) and combo attack rules
> - Any formulas (alignment distance modifiers, loyalty decay, etc.)

---

# Companion Kits (Signature Abilities + Recruitment Requirements)

Below are the **mechanics-facing** portions for each companion.

## 1) Isolde vex-Torvath

### Recruitment (Mechanics)
- **Location:** Castle Gate (`castle_gate`)
- **Timing:** Early (Act 1)
- **Requirement:** Player must demonstrate an Honorable act early in the game.
- **Missable:** No

### Combat Role (Mechanics)
- Off-tank DPS (frontline, strong single-target, weak vs groups).

### Signature Abilities
- **Righteous Charge** (25 Focus): Rush to an enemy, dealing damage and taunting them for 2 turns.
- **Judge's Verdict** (40 Focus): Massive single-target strike. +50% damage against enemies at <50% HP.
- **Unyielding Law** (Passive): Gain +15% damage resistance when at full HP.

---

## 2) Bram "The Vulture"

### Recruitment (Mechanics)
- **Location:** Residential Area (`residential_area`) at night
- **Timing:** Early (Act 1)
- **Requirement:** Help him evade city guards in a side quest.
- **Missable:** Yes (turning him in makes him unavailable)

### Combat Role (Mechanics)
- Burst melee DPS with high evasion; stealth/backstab play.

### Signature Abilities
- **Backstab** (20 Focus): Attack from the shadows. +100% crit chance if enemy is unaware or flanked.
- **Venom Blade** (30 Focus): Apply a stacking poison that deals damage over 3 turns.
- **Vanish** (35 Focus): Enter stealth. Next attack gains Backstab bonus and ignores armor.

---

## 3) Dr. Sybilla ves-Vael

### Recruitment (Mechanics)
- **Location:** Ancient Library
- **Timing:** Early (Act 1)
- **Requirement:** Help her solve a magical puzzle to access a forbidden section.
- **Missable:** No

### Combat Role (Mechanics)
- Ranged magic DPS + healing support.

### Signature Abilities
- **Analytical Strike** (20 Focus): Ranged attack that reveals enemy weaknesses for 3 turns (+15% damage from all sources).
- **Healing Light** (25 Focus): Restore HP to one ally. More effective on wounded targets.
- **Arcane Barrier** (35 Focus): Grant a shield to all allies that absorbs damage for 2 turns.

---

## 4) Garrick nul-Kael

### Recruitment (Mechanics)
- **Location:** Adventurer's Guild (`guild_hall`)
- **Timing:** Mid (Act 2)
- **Requirement:** Hire him for a quest. If loyalty is high enough, he joins permanently.
- **Missable:** Yes (mistreating him during his contract prevents permanent recruitment)

### Combat Role (Mechanics)
- Control DPS / execution; marks, executes, rallies.

### Signature Abilities
- **Mark Target** (15 Focus): Mark an enemy. All allies deal +20% damage to marked target for 3 turns.
- **Execute** (35 Focus): Massive damage to enemies below 30% HP. Instant kill below 15%.
- **Rally** (30 Focus): Remove debuffs from all allies and grant +10% damage for 2 turns.

---

## 5) Oona Pale-Sap

### Recruitment (Mechanics)
- **Location:** Whispering Woods
- **Timing:** Mid (Act 2)
- **Requirement:** Prove respect for nature through a trial.
- **Missable:** Yes (destroying natural sites before meeting her locks her out)

### Combat Role (Mechanics)
- Control / support; summons, roots, regen.

### Signature Abilities
- **Entangling Roots** (25 Focus): Root an enemy in place for 2 turns. They cannot move or dodge.
- **Nature's Blessing** (30 Focus): Apply regeneration to all allies, healing over 4 turns.
- **Summon Spore Guardian** (40 Focus): Summon a creature that taunts enemies and explodes on death, poisoning nearby foes.

---

## 6) Balance-Ninth (Theron)

### Recruitment (Mechanics)
- **Location:** Mountaintop Monastery
- **Timing:** Late (Act 3)
- **Requirement:** Defeat him in a one-on-one duel.
- **Missable:** No (retry until you win)

### Combat Role (Mechanics)
- Balanced hybrid; adaptable sustained fighter.

### Signature Abilities
- **Flowing Strike** (20 Focus): Attack that adapts - deals more damage if enemy is strong, heals self if enemy is weak.
- **Inner Balance** (25 Focus): Cleanse all debuffs from self and gain +20% to all stats for 2 turns.
- **Pressure Point** (35 Focus): Strike that stuns the enemy for 1 turn and reduces their damage by 25% for 3 turns.

---

## 7) Sash "Velvet"

### Recruitment (Mechanics)
- **Location:** Market District (`market_district`)
- **Timing:** Mid (Act 2)
- **Requirement:** Outsmart her in a game of wits.
- **Missable:** No

### Combat Role (Mechanics)
- Debuff/control; confusion/charm/buff theft.

### Signature Abilities
- **Whispered Lies** (25 Focus): Confuse an enemy for 2 turns. Confused enemies may attack allies.
- **Steal Secrets** (30 Focus): Remove all buffs from an enemy and apply them to yourself.
- **Velvet Trap** (40 Focus): Charm an enemy to fight for you for 2 turns. Bosses are immune but take damage instead.

---

## 8) Ghor Iron-Root

### Recruitment (Mechanics)
- **Location:** Mountain Pass
- **Timing:** Early (Act 1)
- **Requirement:** Defend him from monster hunters.
- **Missable:** Yes (side with hunters => dies/flees)

### Combat Role (Mechanics)
- Pure tank; highest HP/defense; taunts and intercepts.

### Signature Abilities
- **Iron Bark** (20 Focus): Gain +50% damage resistance for 2 turns. Taunt all enemies.
- **Living Shield** (30 Focus): Intercept all attacks on a chosen ally for 2 turns.
- **Rooted Slam** (25 Focus): AoE attack that damages and slows all nearby enemies. Low damage but high control.

---

## 9) Vek "Glass"

### Recruitment (Mechanics)
- **Location:** Ruined Tower
- **Timing:** Mid (Act 2)
- **Requirement:** High INT stat **or** Sybilla in the party. Agree to help his dangerous research.
- **Missable:** Yes (refuse research, or low INT with no Sybilla)

### Combat Role (Mechanics)
- Glass cannon magic DPS; huge damage, very fragile.

### Signature Abilities
> TODO: Copy full ability list from the old combined doc (truncated in current chat excerpt).

---

## 10) Maeva Still-Water

### Recruitment (Mechanics)
> TODO: Copy recruitment requirements from the old combined doc.

### Combat Role (Mechanics)
> TODO

### Signature Abilities
> TODO

---

## 11) Void-Exile (Silas)

### Recruitment (Mechanics)
> TODO: Copy recruitment requirements from the old combined doc.

### Combat Role (Mechanics)
> TODO

### Signature Abilities
> TODO
