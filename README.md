# The Shattered Records - Text-Based RPG

A complex story-driven text RPG with deep combat mechanics inspired by Granblue Fantasy, Dragon Age Origins, and Octopath Traveler.

**Version 1.2** - JavaFX GUI with Dark Fantasy theme, hover tooltips, and interactive combat.

## Features

### JavaFX GUI (v1.2)
- **Dark Fantasy Theme**: Gold/red accents, medieval aesthetic
- **Hover Tooltips**: Detailed stats, abilities, status effects on mouseover
- **Interactive Combat**: Click enemies to target, use abilities with visual feedback
- **Dialogue System**: Center-screen dialogue boxes with real option text
- **RPG Classic Layout**: Text/dialogue top, party status bottom, actions right
- **Console Mode**: Use `--console` flag for the original text interface

### Story & Narrative
- **Flag-based branching system**: Every choice matters and is tracked via FlagManager
- **Dual-axis alignment system**: Honor <-> Deceit + Compassion <-> Cruelty
- **Companion system**: Recruit 11 unique companions with loyalty, friendship, and personal quests
- **Background-based narrative**: 6 backgrounds (Noble Born, Street Urchin, Scholar, Outlander, Soldier, Acolyte)
- **Quest system**: Main story, side quests, and companion personal quests

### Class System (94 Total Classes)
- **24 Base Classes**: 4 unique classes per background with distinct playstyles
- **48 Advanced Classes**: Level 15+ specializations (2 per base class)
- **16 Event-Based Secret Classes**: Unlocked through specific story choices
- **Companion Transformation Classes**: 24+ unique classes (good/evil paths per companion)
- **6 Alignment Ultimate Classes**: Require 100% purity

### Combat System (GBF-Inspired)
- **Tiered Focus System**: Three ability tiers based on Focus meter
- **Alignment Combat Modifiers**: Your moral choices affect combat
- **Elemental Combo System**: 11 combo reactions (Vaporize, Melt, Freeze, etc.)
- **30+ Status Effects**: Buffs, debuffs, DoTs, crowd control with stacking/refreshing
- **Element System**: Fire > Wind > Earth > Water > Fire wheel, plus Light <-> Dark
- **Enemy AI Patterns**: Aggressive, Defensive, Tactical, Random with HP triggers
- **Manual Stat Allocation**: 3 stat points per level to distribute as you choose

### Character Progression
- **Equipment System**: 8 slots (Weapon, Offhand, Head, Chest, Legs, Accessoryx2, Charm)
- **Manual Level Up**: Choose which stats to increase when you level up
- **Companion Loyalty**: 0-100 loyalty system with friendship levels (0-10)

### World & Exploration
- **5 Locations**: Town Square, Market District, Guild Hall, Castle Gate, Residential Area
- **Location System**: Connected areas with travel mechanics
- **NPC Dialogue**: Talk to NPCs with real dialogue options
- **Quest System**: Accept and track quests from NPCs

### Difficulty Modes
- **Story Mode**: 1.5x Focus charge, simple AI, natural scaling
- **Normal Mode**: Standard Focus charge, light adaptive AI
- **Hard Mode**: 1.0x Focus charge, full adaptive AI, damage caps

## Tech Stack
- Java 17
- JavaFX 21.0.1
- Maven
- Gson (JSON processing)
- JUnit Jupiter (Testing)

## Project Structure
```
src/main/java/com/rpg/
├── Main.java                           # Entry point (dual mode: GUI/Console)
├── GameEngine.java                     # Main game controller
├── GameState.java                      # Central game state
│
├── combat/                             # Combat engine and mechanics
│   ├── Ability.java                    # Ability framework with builder pattern
│   ├── CombatEngine.java               # Turn-based combat system
│   ├── DamageCalculator.java           # Damage calc with alignment modifiers
│   ├── ElementalComboSystem.java       # 11 elemental combo reactions
│   ├── FocusMeter.java                 # Focus meter (0-100) with tiers
│   └── StatusEffect.java               # Status effects with stacking/duration
│
├── models/                             # Data models
│   ├── Character.java                  # Base abstract character
│   ├── Player.java                     # Player with equipment & progression
│   ├── Companion.java                  # Recruitable party members
│   ├── Enemy.java                      # Enemy with AI patterns
│   ├── Equipment.java                  # Equippable items with effects
│   ├── GameClass.java                  # Class definitions
│   └── Stats.java                      # Two-tier stat system
│
├── systems/                            # Game systems
│   ├── AlignmentSystem.java            # Dual-axis morality system
│   ├── Quest.java                      # Quest with objectives and rewards
│   └── QuestManager.java               # Quest tracking and completion
│
├── world/                              # World and exploration
│   ├── Location.java                   # Game locations with connections
│   └── WorldMap.java                   # World management and travel
│
└── ui/
    ├── ConsoleUI.java                  # ANSI colored console output
    └── fx/                             # JavaFX GUI (v1.2)
        ├── GameApplication.java        # JavaFX entry point
        ├── GameController.java         # Central controller
        ├── GameView.java               # Main view with layout
        ├── components/
        │   └── TooltipFactory.java     # Hover tooltips
        └── views/
            ├── TitleView.java          # Title screen
            ├── CharacterCreationView.java  # Character creation
            ├── ExplorationView.java    # Exploration with NPC dialogue
            ├── CombatView.java         # Interactive combat
            └── PartyMenuView.java      # Party & equipment management
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build & Run
```bash
# Build the project
mvn clean package

# Run with JavaFX GUI (default)
java -jar target/text-rpg-1.2-SNAPSHOT.jar

# Run with console UI
java -jar target/text-rpg-1.2-SNAPSHOT.jar --console
```

### Development
```bash
# Compile
mvn compile

# Run tests
mvn test

# Run JavaFX GUI
mvn javafx:run

# Run console mode
mvn exec:java -Dexec.mainClass="com.rpg.Main" -Dexec.args="--console"
```

## Documentation
- **CLASSES.md**: Complete class system design
- **COMPANIONS.md**: 11 unique companion designs

## License
MIT
