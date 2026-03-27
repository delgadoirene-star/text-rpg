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
- **6 Alignment Ultimate Classes**: Peak moral/immoral paths

## Prerequisites
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
##### Development
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

### Public Docs
- **[docs/README.md](docs/README.md)** - Documentation index
- **[CLASSES.md](CLASSES.md)** - Complete class system design (116+ classes)
- **[COMPANIONS.md](COMPANIONS.md)** - 11 unique companion designs (spoiler-light)
- **[docs/world/WORLD_MAP.md](docs/world/WORLD_MAP.md)** - World map and regions
- **[docs/systems/COMBAT_SYSTEM.md](docs/systems/COMBAT_SYSTEM.md)** - Combat mechanics
- **[docs/systems/ALIGNMENT_SYSTEM.md](docs/systems/ALIGNMENT_SYSTEM.md)** - Dual-axis morality

### Internal Docs (Spoilers)
- **[docs/companions/COMPANION_MASTER.md](docs/companions/COMPANION_MASTER.md)** - Single source of truth for Companion lore, mechanics, and quest subversions
- **[docs/world/LOCATION_DETAILS_INTERNAL.md](docs/world/LOCATION_DETAILS_INTERNAL.md)** - Detailed locations
- **[docs/quests/QUEST_SPOILERS_INTERNAL.md](docs/quests/QUEST_SPOILERS_INTERNAL.md)** - Quest walkthroughs
