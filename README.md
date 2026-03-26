# Text-Based RPG

A complex story-driven text RPG with deep combat mechanics inspired by GranBlue Fantasy.

## Features

### Story & Narrative
- **Flag-based branching system**: Every choice matters and is tracked
- **Complex NPC interactions**: Relationships, reputation, and consequences
- **Rich dialogue system**: Multiple conversation paths and outcomes
- **Quest system**: Main story, side quests, and hidden content

### Combat System
- **Tiered Ultimate System**: Three tiers of special abilities
  - Tier 1 (25%): Tactical skills for setup
  - Tier 2 (50%): Powerful abilities for impact
  - Tier 3 (100%): Ultimate transformations or finishers
- **Focus Meter**: Builds through combat, spend strategically
- **Resonance System**: Coordinate with party members for bonus effects
- **30+ Status Effects**: Buffs, debuffs, DoTs, crowd control with stacking mechanics
- **Element System**: Fire, Water, Earth, Wind, Light, Dark with advantage wheel
- **Break/Overdrive**: Enemy state system for burst damage windows
- **Class System**: Multiple classes with unique abilities and synergies
- **Weapon Grid**: Equipment provides passive bonuses

### World & Exploration
- **ASCII Map System**: Visual representation of your location
- **Fog of War**: Discover new areas as you explore
- **Dynamic world**: Events and changes based on your actions

### Difficulty Modes
- **Normal**: Natural scaling, faster Focus meter, experimentation friendly
- **Hard**: Soft damage caps, optimization required, tactical combat

## Tech Stack
- Java 17
- Maven
- Gson (JSON processing)
- JLine (Enhanced console I/O)
- Jansi (ANSI colors)

## Project Structure
```
src/main/java/com/rpg/
├── Main.java                 # Entry point
├── models/                   # Data models (Player, Enemy, Item, etc.)
├── combat/                   # Combat engine and mechanics
├── systems/                  # Game systems (Flags, Quests, Dialog)
├── world/                    # World map and locations
├── ui/                       # Console UI and rendering
├── data/                     # Data loaders and save/load
└── utils/                    # Helper utilities
```

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

- [x] Project structure setup
- [ ] Core models (Player, Enemy, Item, StatusEffect)
- [ ] Combat system framework
- [ ] Flag management system
- [ ] Dialog system
- [ ] Quest system
- [ ] Map renderer
- [ ] Save/Load system
- [ ] Content creation (story, items, enemies)

## License
MIT
