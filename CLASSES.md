# Class System Design

## Background → Base Classes → Advanced Classes → Secret Classes

### 1. NOBLE_BORN
**Philosophy:** Honor, leadership, martial excellence, tactical superiority

#### Base Classes:
- **PALADIN** (Holy Warrior)
  - Primary: STR, VIT, WIS
  - Growth: +3 STR, +2 VIT, +2 WIS, +1 DEX, +1 INT, +1 LUK
  - Playstyle: Tank/Support hybrid with healing and defensive buffs
  
- **KNIGHT** (Defensive Tank)
  - Primary: VIT, STR
  - Growth: +2 STR, +2 DEX, +4 VIT, +1 INT, +1 WIS, +1 LUK
  - Playstyle: Pure tank with shield abilities and aggro management
  
- **DUELIST** (Agile Fighter)
  - Primary: DEX, STR
  - Growth: +3 STR, +4 DEX, +2 VIT, +1 INT, +1 WIS, +2 LUK
  - Playstyle: High damage single-target fighter with ripostes
  
- **COMMANDER** (Tactical Leader)
  - Primary: INT, WIS
  - Growth: +2 STR, +2 DEX, +2 VIT, +3 INT, +3 WIS, +1 LUK
  - Playstyle: Support/Buffer with team-wide abilities

#### Advanced Classes (Level 10+):
- **PALADIN** →
  - **HOLY CRUSADER**: Offensive paladin (Light element, heavy damage + healing)
  - **DIVINE GUARDIAN**: Defensive paladin (Shields allies, resurrection abilities)
  
- **KNIGHT** →
  - **DRAGOON**: Lance specialist with aerial attacks
  - **FORTRESS**: Immovable wall (Taunt, counter-attacks, damage reflection)
  
- **DUELIST** →
  - **BLADE DANCER**: DEX-focused multi-hit specialist
  - **SWORD SAINT**: Master duelist with perfect counters and instant kills
  
- **COMMANDER** →
  - **TACTICIAN**: Manipulates turn order, applies team buffs/debuffs
  - **WARLORD**: Damage-focused leader with offensive commands

#### Secret Class:
- **ROYAL CHAMPION** (Requires special flag: "Reclaim_Throne" OR "Unite_Kingdoms")
  - Ultimate noble class combining best aspects of all four paths
  - Requires: Level 20+, All 4 base classes at Advanced level

---

### 2. STREET_URCHIN
**Philosophy:** Survival, cunning, speed, adaptability

#### Base Classes:
- **ROGUE** (Stealth Assassin)
  - Primary: DEX, LUK
  - Growth: +2 STR, +4 DEX, +2 VIT, +1 INT, +1 WIS, +3 LUK
  - Playstyle: High crit, backstab mechanics, status effects
  
- **BRAWLER** (Bare-Knuckle Fighter)
  - Primary: STR, DEX
  - Growth: +4 STR, +3 DEX, +3 VIT, +1 INT, +1 WIS, +1 LUK
  - Playstyle: Fast combo attacks, counterattacks, stuns
  
- **TRICKSTER** (Debuff Specialist)
  - Primary: LUK, INT
  - Growth: +2 STR, +3 DEX, +2 VIT, +2 INT, +2 WIS, +4 LUK
  - Playstyle: Status effects, traps, misdirection
  
- **SCOUT** (Ranged DPS)
  - Primary: DEX, LUK
  - Growth: +2 STR, +4 DEX, +2 VIT, +2 INT, +1 WIS, +3 LUK
  - Playstyle: Ranged attacks, evasion, marking targets

#### Advanced Classes (Level 10+):
- **ROGUE** →
  - **ASSASSIN**: Pure damage dealer (guaranteed crits on debuffed targets)
  - **SHADOW WALKER**: Stealth master (turn invisibility, ambush attacks)
  
- **BRAWLER** →
  - **PUGILIST**: Multi-hit combo specialist
  - **STREET KING**: Dirty fighting (ignores honor, extreme damage when below 50% HP)
  
- **TRICKSTER** →
  - **CHARLATAN**: Master of illusions and mind games
  - **SABOTEUR**: Trap and poison expert
  
- **SCOUT** →
  - **RANGER**: Bow specialist with long-range abilities
  - **MARKSMAN**: Precision shooter (guaranteed hits, weak point targeting)

#### Secret Class:
- **PHANTOM THIEF** (Requires flag: "Steal_Crown_Jewel" AND high Fame)
  - Master of all roguish arts
  - Can steal abilities from enemies temporarily

---

### 3. SCHOLAR
**Philosophy:** Knowledge, magical mastery, experimentation

#### Base Classes:
- **MAGE** (Elemental Caster)
  - Primary: INT, WIS
  - Growth: +1 STR, +2 DEX, +2 VIT, +4 INT, +3 WIS, +2 LUK
  - Playstyle: High damage spells, elemental combos
  
- **SAGE** (Support Caster)
  - Primary: WIS, INT
  - Growth: +1 STR, +1 DEX, +3 VIT, +3 INT, +4 WIS, +2 LUK
  - Playstyle: Buffs, healing, utility magic
  
- **ALCHEMIST** (Item/Status Specialist)
  - Primary: INT, LUK
  - Growth: +1 STR, +2 DEX, +3 VIT, +4 INT, +2 WIS, +3 LUK
  - Playstyle: Potions, transmutation, status infliction
  
- **ARCANIST** (Raw Power Caster)
  - Primary: INT
  - Growth: +1 STR, +1 DEX, +2 VIT, +5 INT, +2 WIS, +2 LUK
  - Playstyle: Massive single-target damage, high focus costs

#### Advanced Classes (Level 10+):
- **MAGE** →
  - **ELEMENTALIST**: Master of all 6 elements
  - **BATTLEMAGE**: Melee + magic hybrid (weapon enchantments)
  
- **SAGE** →
  - **WHITE MAGE**: Ultimate healer and buffer
  - **TIME MAGE**: Manipulates speed, haste, slow, stop effects
  
- **ALCHEMIST** →
  - **APOTHECARY**: Potion master (double item effects)
  - **TRANSMUTER**: Matter manipulation (temporary stat changes)
  
- **ARCANIST** →
  - **SORCERER**: Forbidden magic user (HP costs for extreme damage)
  - **SPELL WEAVER**: Combines multiple spells into one cast

#### Secret Class:
- **ARCHMAGE** (Requires flag: "Master_All_Elements" OR "Discover_Lost_Spell")
  - Ultimate magical power
  - Can cast two spells per turn

---

### 4. OUTLANDER
**Philosophy:** Nature, survival, primal power, harmony

#### Base Classes:
- **RANGER** (Nature Warrior)
  - Primary: DEX, WIS
  - Growth: +2 STR, +4 DEX, +3 VIT, +1 INT, +3 WIS, +2 LUK
  - Playstyle: Ranged attacks, nature magic, tracking
  
- **DRUID** (Nature Caster)
  - Primary: WIS, VIT
  - Growth: +1 STR, +2 DEX, +4 VIT, +2 INT, +4 WIS, +2 LUK
  - Playstyle: Healing, nature spells, shapeshifting
  
- **BEASTMASTER** (Pet Controller)
  - Primary: WIS, DEX
  - Growth: +2 STR, +3 DEX, +3 VIT, +1 INT, +4 WIS, +2 LUK
  - Playstyle: Commands beasts, summons, animal buffs
  
- **SHAMAN** (Elemental Mystic)
  - Primary: WIS, INT
  - Growth: +1 STR, +2 DEX, +3 VIT, +3 INT, +4 WIS, +2 LUK
  - Playstyle: Elemental totems, spirit magic, AoE effects

#### Advanced Classes (Level 10+):
- **RANGER** →
  - **FOREST SENTINEL**: Master archer with nature abilities
  - **WILD HUNTER**: Predator-style fighter (tracking, ambush, traps)
  
- **DRUID** →
  - **SHAPESHIFTER**: Multiple animal forms (bear, wolf, eagle)
  - **NATURE'S WRATH**: Offensive druid (thorns, poison, entangle)
  
- **BEASTMASTER** →
  - **MONSTER TAMER**: Commands powerful monsters
  - **PACK LEADER**: Multiple beast summons
  
- **SHAMAN** →
  - **SPIRIT CALLER**: Summons ancestor spirits
  - **TOTEM MASTER**: Creates powerful totems with various effects

#### Secret Class:
- **WILDLORD** (Requires flag: "Bond_With_Ancient_Beast" OR "Balance_Nature")
  - Perfect harmony with nature
  - Can transform into mythical beasts

---

### 5. SOLDIER
**Philosophy:** Discipline, strength, endurance, warfare

#### Base Classes:
- **WARRIOR** (Balanced Fighter)
  - Primary: STR, VIT
  - Growth: +4 STR, +2 DEX, +3 VIT, +1 INT, +1 WIS, +2 LUK
  - Playstyle: Strong all-around physical damage dealer
  
- **BERSERKER** (Rage Fighter)
  - Primary: STR
  - Growth: +5 STR, +2 DEX, +3 VIT, +1 INT, +1 WIS, +1 LUK
  - Playstyle: High risk/high reward, stronger when HP is low
  
- **GUARDIAN** (Defensive Fighter)
  - Primary: VIT, STR
  - Growth: +3 STR, +2 DEX, +4 VIT, +1 INT, +2 WIS, +1 LUK
  - Playstyle: Protects allies, shields, counters
  
- **TACTICIAN** (Strategic Fighter)
  - Primary: STR, INT
  - Growth: +3 STR, +3 DEX, +2 VIT, +3 INT, +2 WIS, +1 LUK
  - Playstyle: Analyzes enemies, exploits weaknesses

#### Advanced Classes (Level 10+):
- **WARRIOR** →
  - **WEAPON MASTER**: Can switch weapon types mid-battle
  - **GLADIATOR**: Arena fighter (stronger in 1v1, crowd pleaser)
  
- **BERSERKER** →
  - **BLOOD KNIGHT**: Sacrifices HP for damage
  - **SAVAGE**: Pure rage (uncontrollable power)
  
- **GUARDIAN** →
  - **SENTINEL**: Ultimate defender (near-immortal tank)
  - **SHIELD MASTER**: Shield-based attacks and defense
  
- **TACTICIAN** →
  - **BATTLE MASTER**: All-seeing strategist
  - **WAR VETERAN**: Experience-based bonuses

#### Secret Class:
- **WAR GOD** (Requires flag: "Win_Grand_Tournament" OR "Defeat_Legendary_Warrior")
  - Legendary fighter
  - Can use all weapon types and fighting styles

---

### 6. ACOLYTE
**Philosophy:** Faith, devotion, balance, judgment

#### Base Classes:
- **CLERIC** (Holy Support)
  - Primary: WIS, VIT
  - Growth: +1 STR, +2 DEX, +3 VIT, +2 INT, +4 WIS, +2 LUK
  - Playstyle: Healing, buffs, Light magic
  
- **MONK** (Holy Warrior)
  - Primary: STR, WIS
  - Growth: +3 STR, +3 DEX, +3 VIT, +1 INT, +3 WIS, +1 LUK
  - Playstyle: Unarmed combat, ki abilities, balance
  
- **PRIEST** (Pure Caster)
  - Primary: WIS, INT
  - Growth: +1 STR, +1 DEX, +2 VIT, +3 INT, +5 WIS, +2 LUK
  - Playstyle: Light/Dark magic, status removal, resurrection
  
- **INQUISITOR** (Holy Warrior)
  - Primary: STR, WIS
  - Growth: +4 STR, +2 DEX, +2 VIT, +2 INT, +3 WIS, +1 LUK
  - Playstyle: Hunts heretics, high damage to demons/undead

#### Advanced Classes (Level 10+):
- **CLERIC** →
  - **HIGH PRIEST**: Master healer
  - **ORACLE**: Sees future, can predict enemy moves
  
- **MONK** →
  - **GRAND MASTER**: Perfect martial arts
  - **ASCETIC**: Transcends physical limits
  
- **PRIEST** →
  - **LIGHT BRINGER**: Pure Light magic offensive caster
  - **DARK PRIEST**: Embraces Dark magic (fallen priest)
  
- **INQUISITOR** →
  - **ZEALOT**: Fanatical damage dealer
  - **JUDGE**: Judges enemies (executes low HP targets)

#### Secret Class:
- **DIVINE HERALD** (Requires flag: "Chosen_By_God" OR "Balance_Light_Dark")
  - Channel divine power directly
  - Can resurrect fallen allies mid-battle

---

## Secret Class Requirements Summary
1. **ROYAL CHAMPION**: Unite/reclaim kingdoms (Noble Born)
2. **PHANTOM THIEF**: Steal legendary item + high fame (Street Urchin)
3. **ARCHMAGE**: Master all elements or discover lost spell (Scholar)
4. **WILDLORD**: Bond with ancient beast or restore nature balance (Outlander)
5. **WAR GOD**: Win grand tournament or defeat legendary warrior (Soldier)
6. **DIVINE HERALD**: Be chosen by deity or balance Light/Dark (Acolyte)
