# INTERNAL / SPOILERS — Companion Mechanics

**Audience:** Developers, designers, QA.

This document contains **mechanics** for the companion system (including recruitment requirements, loyalty/friendship systems, party dynamics rules, and all signature abilities).

For spoiler-heavy narrative content (backstories, quest subversions, transformation story outcomes), see `COMPANION_DESIGN_INTERNAL.md`.

---

# System-Level Mechanics

## Party Size Rules
- **Active Combat:** Player + 3 Companions maximum
- **Reserve:** Unlimited companions available
- **Swap:** Only at safe zones or camps
- **Dismiss:** Can dismiss companions to reserve at any time

## Loyalty System (0-100)

### Loyalty Effects
| Loyalty | Effect |
|---------|--------|
| 0-19 | May refuse orders, eventually leaves |
| 20-39 | Unreliable, won't take risks |
| 40-59 | Standard behavior |
| 60-79 | Willing to take moderate risks |
| 80-89 | Loyal, takes significant risks |
| 90-100 | Devoted, won't abandon player |

### Loyalty Changes
- **Alignment Match:** +1-3 per session
- **Alignment Mismatch:** -1-2 per session
- **Completing Quests:** +5-15
- **Betraying Values:** -5-20
- **Death (resurrection):** -10

## Friendship System (0-10)

### Friendship Bonuses
| Level | Bonus |
|-------|-------|
| 1 | Companion dialogue unlocked |
| 2 | Basic combat synergy |
| 3 | +5% damage when adjacent |
| 4 | Shared ability cooldown |
| 5 | Combo attack available |
| 6 | +10% all stats |
| 7 | Dual ultimate ability |
| 8 | Lore unlocked |
| 9 | Transformation path available |
| 10 | Ultimate synergy unlocked |

### Gaining Friendship
- Positive dialogue choices: +1
- Completing personal quests: +2-3
- Time spent together: +1 per 3 sessions
- Protective actions: +1

## Party Dynamics

### Personality Compatibility Matrix
| Personality | Synergizes With | Conflicts With |
|------------|-----------------|----------------|
| Noble | Noble, Idealist | Cynic, Pragmatist |
| Cynic | Pragmatist, Survivor | Idealist, Noble |
| Idealist | Noble, Dreamer | Cynic, Realist |
| Pragmatist | Cynic, Survivor | Idealist, Dreamer |
| Dreamer | Idealist, Wild | Pragmatist, Cynic |
| Survivor | Pragmatist, Wild | Noble, Dreamer |
| Wild | Dreamer, Survivor | Noble, Cynic |

### Party Synergy Bonuses
- **2 Synergistic Companions:** +10% damage
- **3 Synergistic Companions:** +15% damage, shared resistances
- **Conflicting Pair:** -5% damage, increased companion tension

---

# Companion Kits (Signature Abilities + Recruitment Requirements)

## 1) Isolde vex-Torvath

### Recruitment (Mechanics)
- **Location:** Castle Gate (`castle_gate`)
- **Timing:** Early (Act 1)
- **Requirement:** Player must demonstrate an Honorable act early in the game.
- **Missable:** No

### Combat Role
- Off-tank DPS (frontline, strong single-target, weak vs groups).

### Signature Abilities
- **Righteous Charge** (25 Focus): Rush to an enemy, dealing damage and taunting them for 2 turns.
- **Judge's Verdict** (40 Focus): Massive single-target strike. +50% damage against enemies at <50% HP.
- **Unyielding Law** (Passive): Gain +15% damage resistance when at full HP.

### Alignment Modifier
- Prefers Honor +50+
- Accepts Compassion -50 to +50
- Rejects: Serpent (-80+), Tyrant (-80+)

---

## 2) Bram "The Vulture"

### Recruitment (Mechanics)
- **Location:** Residential Area (`residential_area`) at night
- **Timing:** Early (Act 1)
- **Requirement:** Help him evade city guards in a side quest.
- **Missable:** Yes (turning him in makes him unavailable)

### Combat Role
- Burst melee DPS with high evasion; stealth/backstab play.

### Signature Abilities
- **Backstab** (20 Focus): Attack from the shadows. +100% crit chance if enemy is unaware or flanked.
- **Venom Blade** (30 Focus): Apply a stacking poison that deals damage over 3 turns.
- **Vanish** (35 Focus): Enter stealth. Next attack gains Backstab bonus and ignores armor.

### Alignment Modifier
- Prefers Pragmatic/Deceitful alignment
- Accepts Honor -50 to +50
- Rejects: Paragon (90+), Saint (90+)

---

## 3) Dr. Sybilla ves-Vael

### Recruitment (Mechanics)
- **Location:** Ancient Library
- **Timing:** Early (Act 1)
- **Requirement:** Help her solve a magical puzzle to access a forbidden section.
- **Missable:** No

### Combat Role
- Ranged magic DPS + healing support.

### Signature Abilities
- **Analytical Strike** (20 Focus): Ranged attack that reveals enemy weaknesses for 3 turns (+15% damage from all sources).
- **Healing Light** (25 Focus): Restore HP to one ally. More effective on wounded targets.
- **Arcane Barrier** (35 Focus): Grant a shield to all allies that absorbs damage for 2 turns.

### Alignment Modifier
- Prefers High Compassion (+50+)
- Accepts any alignment except extreme Tyrant (-90+)

---

## 4) Garrick nul-Kael

### Recruitment (Mechanics)
- **Location:** Adventurer's Guild (`guild_hall`)
- **Timing:** Mid (Act 2)
- **Requirement:** Hire him for a quest. If loyalty is high enough, he joins permanently.
- **Missable:** Yes (mistreating him during his contract prevents permanent recruitment)

### Combat Role
- Control DPS / execution; marks, executes, rallies.

### Signature Abilities
- **Mark Target** (15 Focus): Mark an enemy. All allies deal +20% damage to marked target for 3 turns.
- **Execute** (35 Focus): Massive damage to enemies below 30% HP. Instant kill below 15%.
- **Rally** (30 Focus): Remove debuffs from all allies and grant +10% damage for 2 turns.

### Alignment Modifier
- Prefers Balanced to Good alignment
- Accepts Honor -25 to +75, Compassion -25 to +100
- Rejects: Serpent (-80+), Tyrant (-90+)

---

## 5) Oona Pale-Sap

### Recruitment (Mechanics)
- **Location:** Whispering Woods
- **Timing:** Mid (Act 2)
- **Requirement:** Prove respect for nature through a trial.
- **Missable:** Yes (destroying natural sites before meeting her locks her out)

### Combat Role
- Control / support; summons, roots, regen.

### Signature Abilities
- **Entangling Roots** (25 Focus): Root an enemy in place for 2 turns. They cannot move or dodge.
- **Nature's Blessing** (30 Focus): Apply regeneration to all allies, healing over 4 turns.
- **Summon Spore Guardian** (40 Focus): Summon a creature that taunts enemies and explodes on death, poisoning nearby foes.

### Alignment Modifier
- Prefers Nature-respecting alignment
- Accepts Compassion +30+, any Honor
- Rejects: Extreme destruction of nature

---

## 6) Balance-Ninth (Theron)

### Recruitment (Mechanics)
- **Location:** Mountaintop Monastery
- **Timing:** Late (Act 3)
- **Requirement:** Defeat him in a one-on-one duel.
- **Missable:** No (retry until you win)

### Combat Role
- Balanced hybrid; adaptable sustained fighter.

### Signature Abilities
- **Flowing Strike** (20 Focus): Attack that adapts - deals more damage if enemy is strong, heals self if enemy is weak.
- **Inner Balance** (25 Focus): Cleanse all debuffs from self and gain +20% to all stats for 2 turns.
- **Pressure Point** (35 Focus): Strike that stuns the enemy for 1 turn and reduces their damage by 25% for 3 turns.

### Alignment Modifier
- Prefers Balanced to High Honor (+70+)
- Accepts any alignment with at least one axis above +50
- Rejects: Extreme on any axis

---

## 7) Sash "Velvet"

### Recruitment (Mechanics)
- **Location:** Market District (`market_district`)
- **Timing:** Mid (Act 2)
- **Requirement:** Outsmart her in a game of wits.
- **Missable:** No

### Combat Role
- Debuff/control; confusion/charm/buff theft.

### Signature Abilities
- **Whispered Lies** (25 Focus): Confuse an enemy for 2 turns. Confused enemies may attack allies.
- **Steal Secrets** (30 Focus): Remove all buffs from an enemy and apply them to yourself.
- **Velvet Trap** (40 Focus): Charm an enemy to fight for you for 2 turns. Bosses are immune but take damage instead.

### Alignment Modifier
- Prefers Deceitful/Harsh alignment
- Accepts any alignment
- Special: Will stay with any player but dialogue changes

---

## 8) Ghor Iron-Root

### Recruitment (Mechanics)
- **Location:** Mountain Pass
- **Timing:** Early (Act 1)
- **Requirement:** Defend him from monster hunters.
- **Missable:** Yes (side with hunters => dies/flees)

### Combat Role
- Pure tank; highest HP/defense; taunts and intercepts.

### Signature Abilities
- **Iron Bark** (20 Focus): Gain +50% damage resistance for 2 turns. Taunt all enemies.
- **Living Shield** (30 Focus): Intercept all attacks on a chosen ally for 2 turns.
- **Rooted Slam** (25 Focus): AoE attack that damages and slows all nearby enemies. Low damage but high control.

### Alignment Modifier
- Prefers High Compassion (+70+)
- Accepts Honor -50 to +100, Compassion +30 to +100
- Rejects: Tyrant (-80+)

---

## 9) Vek "Glass"

### Recruitment (Mechanics)
- **Location:** Ruined Tower
- **Timing:** Mid (Act 2)
- **Requirement:** High INT stat **or** Sybilla in the party. Agree to help his dangerous research.
- **Missable:** Yes (refuse research, or low INT with no Sybilla)

### Combat Role
- Glass cannon magic DPS; huge damage, very fragile.

### Signature Abilities
- **Arcane Barrage** (25 Focus): Fire a volley of magic missiles. +1 missile per 10 INT.
- **Elemental Storm** (50 Focus): AoE damage based on elemental weakness.
- **Glass Cannon** (Passive): +50% spell damage, but take +25% extra damage.

### Alignment Modifier
- Prefers Neutral to Pragmatic
- Accepts any alignment except Paragon/Saint extremes
- Special: Will join if player has high INT regardless of alignment

---

## 10) Maeva Still-Water

### Recruitment (Mechanics)
- **Location:** Mountain Village
- **Timing:** Mid (Act 2)
- **Requirement:** Help defend the village from a threat, OR complete Oona's questline first.
- **Missable:** Yes (abandon village)

### Combat Role
- AoE caster / support; lightning, rain, crystals.

### Signature Abilities
- **Lightning Call** (25 Focus): Chain lightning strikes 3 random enemies.
- **Soothing Rain** (30 Focus): Heal all allies and remove one debuff each.
- **Crystal Ward** (35 Focus): Create a crystal barrier that absorbs next hit.

### Alignment Modifier
- Prefers Compassionate alignment (+40+)
- Accepts Honor -25 to +75, Compassion +20 to +100
- Rejects: Serpent (-70+), Tyrant (-70+)

---

## 11) Void-Exile (Silas)

### Recruitment (Mechanics)
- **Location:** Graveyard (night only)
- **Timing:** Late (Act 3)
- **Requirement:** Find him at night, convince him you're not a threat.
- **Missable:** Yes (never visit graveyard at night)

### Combat Role
- Healer / dark DPS hybrid.

### Signature Abilities
- **Twilight Mend** (25 Focus): Heal an ally for moderate HP. Heals more if target is injured.
- **Shadow Bolt** (20 Focus): Ranged dark damage. +50% damage to undead.
- **Life Drain** (35 Focus): Damage an enemy, heal for 50% of damage dealt.

### Alignment Modifier
- Neutral on both axes
- Accepts any alignment
- Special: Personal quest determines final loyalty

---

# Transformation Classes

Each companion has two transformation paths based on player influence:

| Companion | Good Path | Evil Path |
|-----------|-----------|-----------|
| **Isolde** | Redeemer | Zealot Inquisitor |
| **Bram** | Spymaster | Contractor |
| **Sybilla** | True Healer | Blood Alchemist |
| **Garrick** | Shield-Captain | Reaver |
| **Oona** | Guardian of Growth | Blight-Touched |
| **Theron** | Bodhisattva | Void Walker |
| **Sash** | Selfless Spy | Velvet Assassin |
| **Ghor** | Chieftain | Warlord |
| **Vek** | Selfless Scholar | Fractured God |
| **Maeva** | Crystal Sage | Corrupted Crystal |
| **Silas** | Purified | Shadow-Worn |

## Transformation Requirements
- Friendship level 9+
- Complete personal quest subversion
- Alignment path determined by player's choices
