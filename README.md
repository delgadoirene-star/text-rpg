# Text-Based RPG

A complex story-driven text RPG with deep combat mechanics inspired by Granblue Fantasy, Dragon Age Origins, and Octopath Traveler.

## Features

### Story & Narrative
- **Flag-based branching system**: Every choice matters and is tracked via FlagManager
- **Dual-axis alignment system**: Honor ↔ Deceit + Compassion ↔ Cruelty
  - 100% purity requirement for ultimate classes (one wrong choice locks you out forever)
  - Progressive buffs at thresholds (+25, +50, +75, +90, +100)
- **Companion system**: Recruit 11 unique companions with loyalty, friendship, and personal quests
- **Background-based narrative**: 6 backgrounds (Noble Born, Street Urchin, Scholar, Outlander, Soldier, Acolyte)
- **Quest system**: Main story, side quests, and companion personal quests

### Class System (94 Total Classes)
- **24 Base Classes**: 4 unique classes per background with distinct playstyles
- **48 Advanced Classes**: Level 15+ specializations (2 per base class)
- **16 Event-Based Secret Classes**: Unlocked through specific story choices
  - Dark Path: Necromancer, Blood Mage, Oathbreaker, Soul Reaver, Heretic, Tyrant's Hand
  - Grey Path: Witch Hunter, Spymaster, Reaver, Contractor
  - Light Path: Redeemer, Martyr, Voice of the People, Saint's Vessel
  - Discovery: Void Walker, Dragon Heir, Time Sage, Primordial
- **6 Alignment Ultimate Classes**: Require 100% purity (never strayed from path)
  - Paragon → Exemplar | Serpent → Shadowlord
  - Saint → Divine Avatar | Tyrant → Dread Sovereign
  - Combined: Celestial Champion, Iron Judge, Velvet Hand, Dark Messiah

### Combat System (GBF-Inspired)
- **Tiered Focus System**: Three ability tiers based on Focus meter
  - Tier 1 (25 Focus): Tactical skills for setup and combos
  - Tier 2 (50 Focus): Powerful abilities with major impact
  - Tier 3 (100 Focus): Ultimate abilities and transformations
- **Alignment Combat Modifiers**: Your moral choices affect combat
  - Saint path: +10% healing, ally damage reduction, revive allies
  - Tyrant path: +20% vs wounded, enemy stat reduction, execute low HP enemies
  - Paragon path: Immune to charm, +15% ally stats
  - Serpent path: Enemy confusion, -10% enemy accuracy
- **Elemental Combo System**: 11 combo reactions (Vaporize, Melt, Freeze, etc.)
- **30+ Status Effects**: Buffs, debuffs, DoTs, crowd control with stacking/refreshing
- **Element System**: Fire > Wind > Earth > Water > Fire wheel, plus Light <-> Dark
  - 2x advantage, 0.5x disadvantage
- **Enemy AI Patterns**: Aggressive, Defensive, Tactical, Random with HP triggers
- **Two-tier Stat System**: 6 core stats (STR, DEX, VIT, INT, WIS, LUK) → derived stats

### Character Progression
- **Equipment System**: 8 slots (Weapon, Offhand, Head, Chest, Legs, Accessory×2, Charm)
- **Experience & Leveling**: Exponential growth curve with class-based stat increases
- **Companion Loyalty**: 0-100 loyalty system with friendship levels (0-10)
  - +5% combat bonus per friendship level
  - Personal quests increase loyalty

### World & Exploration
- **Location System**: Connected areas with travel mechanics
- **Game Events**: Scripted events triggered by location, flags, or quests
- **Dynamic World**: Choices affect available locations and events

### Difficulty Modes
- **Story Mode**: 1.5x Focus charge, simple AI, natural scaling
- **Normal Mode**: Standard Focus charge, light adaptive AI
- **Hard Mode**: 1.0x Focus charge, full adaptive AI, +50% enemy HP, +30% enemy damage, damage caps

## Tech Stack
- Java 17
- Maven
- Gson (JSON processing)
- JLine (Enhanced console I/O)
- Jansi (ANSI colors)

## Project Structure
```
src/main/java/com/rpg/
├── Main.java                     # Entry point
├── models/                       # Data models
│   ├── Character.java            # Base character class
│   ├── Player.java               # Player with equipment, alignment & progression
│   ├── Companion.java            # Recruitable party members
│   ├── Enemy.java                # Enemy with AI patterns & HP triggers
│   ├── Stats.java                # Two-tier stat system
│   ├── Equipment.java            # Equippable items with effects
│   ├── GameClass.java            # Class definitions (Base/Advanced/Secret)
│   ├── Background.java           # 6 backgrounds with class restrictions
│   ├── Element.java              # 6 elements with advantage wheel
│   ├── StatusType.java           # 30+ status effect types
│   ├── EquipSlot.java            # 8 equipment slots
│   └── Rarity.java               # Item rarity tiers
├── combat/                       # Combat engine and mechanics
│   ├── CombatEngine.java         # Turn-based combat system
│   ├── DamageCalculator.java     # Damage calc with alignment modifiers
│   ├── CombatModifiers.java      # Alignment/class combat bonuses
│   ├── Ability.java              # Ability framework with builder pattern
│   ├── FocusMeter.java           # Focus meter (0-100) with tiers
│   ├── ElementalComboSystem.java # 11 elemental combo reactions
│   ├── StatusEffect.java         # Status effects with stacking/duration
│   ├── DamageType.java           # Physical/Magical/True/Healing
│   ├── Difficulty.java           # Story/Normal/Hard settings
│   └── AbilityTarget.java        # Single/All/Self/Ally targeting
├── systems/                      # Game systems
│   ├── FlagManager.java          # Flag-based choice tracking
│   ├── AlignmentSystem.java      # Dual-axis morality (Honor + Compassion)
│   ├── Quest.java                # Quest with objectives and rewards
│   ├── QuestObjective.java       # Individual quest tasks
│   └── QuestManager.java         # Quest tracking and completion
├── world/                        # World and exploration
│   ├── Location.java             # Game locations with connections
│   ├── WorldMap.java             # World management and travel
│   └── GameEvent.java            # Scripted location events
├── ui/                           # Console UI
│   └── ConsoleUI.java            # ANSI colored console output
└── data/                         # Data loaders (TODO)
```

## Documentation
- **CLASSES.md**: Complete class system design
  - 24 base classes (4 per background, no overlaps)
  - 48 advanced specializations
  - 16 event-based secret classes (Dark/Grey/Light/Discovery paths)
  - 6 alignment ultimate classes
  - Progressive alignment buffs
  - Alignment choice examples (obvious evil, subtle corruption, dilemmas)
- **COMPANIONS.md**: 11 unique companion designs
  - Backgrounds, classes, personalities, backstories
  - Combat roles and recruitment requirements
  - Personal quest hooks

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build & Run
```bash
# Build the project
mvn clean package

# Run the game
java -jar target/text-rpg-1.0-SNAPSHOT.jar
```

### Development
```bash
# Compile
mvn compile

# Run tests
mvn test

# Run directly (during development)
mvn exec:java -Dexec.mainClass="com.rpg.Main"
```

## Development Roadmap

### ✅ Phase 1: Combat System (COMPLETED)
- [x] Project structure setup
- [x] Two-tier stat system (core + derived)
- [x] Element system with advantage wheel
- [x] Status effects (30+ types with stacking)
- [x] Damage calculator (hit/crit/element/variance)
- [x] Focus meter with tiered abilities
- [x] Enemy AI patterns (4 types) with HP triggers
- [x] Elemental combo system (11 reactions)
- [x] Full turn-based combat engine
- [x] Difficulty modes (Story/Normal/Hard)
- [x] All combat tests passing

### ✅ Phase 2: Character Systems (COMPLETED)
- [x] Player class with equipment & progression
- [x] Equipment system (8 slots, stat bonuses, special effects)
- [x] GameClass system (Base/Advanced/Secret tiers)
- [x] Companion system with loyalty & friendship
- [x] Background-based class restrictions (updated for 24 new classes)
- [x] Flag management system for story choices

### ✅ Phase 3: World & Narrative (COMPLETED)
- [x] Location system with travel mechanics
- [x] World map and area design
- [x] Quest system (main/side/personal)
- [x] Game events (scripted location triggers)
- [x] Design 11 unique companions
- [x] Dual-axis alignment system (Honor + Compassion)
- [x] Alignment combat integration
- [x] Complete class tree design (94 classes total)
  - 24 base, 48 advanced, 16 event-based, 6 alignment ultimate

### 📋 Phase 4: Content Creation (TODO)
- [ ] Story content and branching paths
- [ ] All class abilities (24 base × ~4 abilities each)
- [ ] Equipment database
- [ ] Enemy database
- [ ] Location descriptions and events
- [ ] Companion dialogue and quests
- [ ] Dialogue system
- [ ] Save/Load system

### 📋 Phase 5: Polish & Balance (TODO)
- [ ] Combat balancing across all difficulty modes
- [ ] UI/UX improvements
- [ ] Testing and bug fixes
- [ ] Performance optimization

## License
MIT
