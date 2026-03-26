# Text-Based RPG

A complex story-driven text RPG with deep combat mechanics inspired by Granblue Fantasy, Dragon Age Origins, and Octopath Traveler.

## Features

### Story & Narrative
- **Flag-based branching system**: Every choice matters and is tracked via FlagManager
- **Companion system**: Recruit 10+ unique companions with loyalty, friendship, and personal quests
- **Background-based narrative**: 6 backgrounds (Noble Born, Street Urchin, Scholar, Outlander, Soldier, Acolyte)
- **Branching class trees**: 24 base classes → 48 advanced specializations → 6 secret classes
- **Complex NPC interactions**: Relationships, reputation, and consequences
- **Quest system**: Main story, side quests, and companion personal quests

### Combat System (GBF-Inspired)
- **Tiered Focus System**: Three ability tiers based on Focus meter
  - Tier 1 (25 Focus): Tactical skills for setup and combos
  - Tier 2 (50 Focus): Powerful abilities with major impact
  - Tier 3 (100 Focus): Ultimate abilities and transformations
- **Focus Meter**: 0-100 meter that builds through combat actions
- **Elemental Combo System**: 11 combo reactions (Vaporize, Melt, Freeze, etc.)
- **30+ Status Effects**: Buffs, debuffs, DoTs, crowd control with stacking/refreshing
- **Element System**: Fire > Wind > Earth > Water > Fire wheel, plus Light <-> Dark
  - 2x advantage, 0.5x disadvantage
- **Enemy AI Patterns**: Aggressive, Defensive, Tactical, Random with HP triggers
- **Two-tier Stat System**: 6 core stats (STR, DEX, VIT, INT, WIS, LUK) → derived stats
- **Equipment System**: 8 equipment slots with stat bonuses and special effects
- **Class System**: Background-locked classes with stat growth and abilities

### Character Progression
- **6 Backgrounds**: Each unlocks 4 unique base classes
- **24 Base Classes**: Starting classes with distinct playstyles
- **48 Advanced Classes**: Level 10+ specializations (2 per base class)
- **6 Secret Classes**: Unlocked via special story flags
- **Experience & Leveling**: Exponential growth curve with class-based stat increases
- **Equipment System**: 8 slots (Weapon, Offhand, Head, Chest, Legs, Accessory×2, Charm)
- **Companion Loyalty**: 0-100 loyalty system with friendship levels (0-10)
  - +5% combat bonus per friendship level
  - Personal quests increase loyalty

### Difficulty Modes
- **Story Mode**: 1.5x Focus charge, simple AI, natural scaling
- **Normal Mode**: Standard Focus charge, light adaptive AI
- **Hard Mode**: 1.0x Focus charge, full adaptive AI, +50% enemy HP, +30% enemy damage

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
│   ├── Player.java               # Player character with equipment & progression
│   ├── Companion.java            # Recruitable party members
│   ├── Enemy.java                # Enemy with AI patterns & HP triggers
│   ├── Stats.java                # Two-tier stat system
│   ├── Equipment.java            # Equippable items
│   ├── GameClass.java            # Class definitions with stat growth
│   ├── Background.java           # Character backgrounds
│   ├── Element.java              # Elemental system
│   ├── StatusType.java           # Status effect types
│   ├── EquipSlot.java            # Equipment slots
│   └── Rarity.java               # Item rarity tiers
├── combat/                       # Combat engine and mechanics
│   ├── CombatEngine.java         # Turn-based combat system
│   ├── DamageCalculator.java    # Damage calculation with hit/crit/element
│   ├── Ability.java              # Ability framework with builder pattern
│   ├── FocusMeter.java           # Focus meter (0-100) with tiers
│   ├── ElementalComboSystem.java # 11 elemental combo reactions
│   ├── StatusEffect.java         # Status effects with stacking/duration
│   ├── DamageType.java           # Physical/Magical/True/Healing
│   ├── Difficulty.java           # Story/Normal/Hard settings
│   └── AbilityTarget.java        # Single/All/Self/Ally targeting
├── systems/                      # Game systems
│   └── FlagManager.java          # Flag-based choice tracking
├── world/                        # World map and locations (TODO)
├── ui/                           # Console UI
│   └── ConsoleUI.java            # ANSI colored console output
└── data/                         # Data loaders (TODO)
```

## Documentation
- **CLASSES.md**: Complete class tree design for all 6 backgrounds
  - 24 base classes with stat growth
  - 48 advanced specializations
  - 6 secret classes with unlock requirements

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
- [x] Complete class tree design (24 base, 48 advanced, 6 secret)
- [x] Companion system with loyalty & friendship
- [x] Background-based class restrictions
- [x] Flag management system for story choices

### 🔄 Phase 3: World & Narrative (IN PROGRESS)
- [ ] Location system with travel mechanics
- [ ] World map and area design
- [ ] Quest system (main/side/personal)
- [ ] Design 10+ unique companions
- [ ] Dialogue system
- [ ] Save/Load system

### 📋 Phase 4: Content Creation (TODO)
- [ ] Story content and branching paths
- [ ] All class abilities (24 base × ~4 abilities each)
- [ ] Equipment database
- [ ] Enemy database
- [ ] Location descriptions and events
- [ ] Companion dialogue and quests

### 📋 Phase 5: Polish & Balance (TODO)
- [ ] Combat balancing across all difficulty modes
- [ ] UI/UX improvements
- [ ] Testing and bug fixes
- [ ] Performance optimization

## License
MIT
