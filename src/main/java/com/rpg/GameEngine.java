package com.rpg;

import com.rpg.combat.*;
import com.rpg.models.*;
import com.rpg.systems.AlignmentSystem;
import com.rpg.systems.FlagManager;
import com.rpg.systems.QuestManager;
import com.rpg.ui.ConsoleUI;
import com.rpg.world.*;

import java.util.*;

/**
 * Main game engine - handles the game loop, menus, and state transitions.
 * Alpha demo covers:
 *   Title → Create Character → Explore → Combat → Companions → Quests → End
 */
public class GameEngine {
    
    private final Scanner scanner;
    private final ConsoleUI ui;
    private GameState state;
    
    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI();
    }
    
    // ==================== Main Entry ====================
    
    public void run() {
        ui.displayWelcome();
        
        while (true) {
            System.out.println("\n  1. New Game");
            System.out.println("  2. Quit");
            System.out.print("\n> ");
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("2")) {
                ui.displayMessage("Goodbye!");
                ui.cleanup();
                return;
            }
            if (choice.equals("1")) {
                break;
            }
        }
        
        newGame();
        gameLoop();
    }
    
    // ==================== New Game ====================
    
    private void newGame() {
        state = new GameState();
        
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  CHARACTER CREATION");
        ui.displaySeparator();
        
        // Choose name
        System.out.print("\n  Enter your character's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Hero";
        
        // Choose element
        System.out.println("\n  Choose your elemental affinity:");
        System.out.println("  1. Fire   - Strong vs Wind, weak vs Water");
        System.out.println("  2. Water  - Strong vs Fire, weak vs Earth");
        System.out.println("  3. Earth  - Strong vs Water, weak vs Wind");
        System.out.println("  4. Wind   - Strong vs Earth, weak vs Fire");
        System.out.println("  5. Light  - Strong vs Dark");
        System.out.println("  6. Dark   - Strong vs Light");
        Element element = chooseElement();
        
        // Choose class
        System.out.println("\n  Choose your starting class:");
        System.out.println("  1. Warrior  - High STR/VIT, physical fighter");
        System.out.println("  2. Scholar  - High INT/WIS, healer and debuffer");
        System.out.println("  3. Rogue    - High DEX/LUK, fast striker");
        System.out.println("  4. Paladin  - Balanced STR/VIT/WIS, holy warrior");
        GameClass startingClass = chooseClass();
        
        // Set background
        Background background = Background.SOLDIER;
        
        // Create player with balanced stats
        Stats stats = new Stats(12, 12, 12, 12, 12, 12);
        Player player = new Player(name, background, startingClass, stats, element);
        
        // Add starting abilities from class
        for (Ability ability : startingClass.getClassAbilities()) {
            player.addAbility(ability);
        }
        
        state.setPlayer(player);
        
        // Initialize world and quests
        WorldMap worldMap = new WorldMap();
        worldMap.initializeWorld();
        state.setWorldMap(worldMap);
        state.setCurrentLocation(worldMap.getStartingLocation());
        
        state.getQuestManager().initializeQuests();
        
        // Register all companions (not recruited yet)
        registerCompanions();
        
        // Show intro
        showIntro(name, element, startingClass);
    }
    
    private Element chooseElement() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return Element.FIRE;
                case "2": return Element.WATER;
                case "3": return Element.EARTH;
                case "4": return Element.WIND;
                case "5": return Element.LIGHT;
                case "6": return Element.DARK;
            }
            System.out.println("  Invalid choice. Try again.");
        }
    }
    
    private GameClass chooseClass() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return GameClassRegistry.createWarriorClass();
                case "2": return GameClassRegistry.createScholarClass();
                case "3": return GameClassRegistry.createRogueClass();
                case "4": return GameClassRegistry.createPaladinClass();
            }
            System.out.println("  Invalid choice. Try again.");
        }
    }
    
    private void registerCompanions() {
        // Pre-create and register all 11 companions
        state.registerCompanion(CompanionFactory.createIsolde(GameClassRegistry.createPaladinClass()));
        state.registerCompanion(CompanionFactory.createBram(GameClassRegistry.createRogueClass()));
        state.registerCompanion(CompanionFactory.createSybilla(GameClassRegistry.createScholarClass()));
        state.registerCompanion(CompanionFactory.createGarrick(GameClassRegistry.createWarriorClass()));
        state.registerCompanion(CompanionFactory.createOona(GameClassRegistry.createGuardianClass()));
        state.registerCompanion(CompanionFactory.createTheron(GameClassRegistry.createMonkClass()));
        state.registerCompanion(CompanionFactory.createSash(GameClassRegistry.createSpyClass()));
        state.registerCompanion(CompanionFactory.createGhor(GameClassRegistry.createBrawlerClass()));
        state.registerCompanion(CompanionFactory.createVek(GameClassRegistry.createArcanistClass()));
        state.registerCompanion(CompanionFactory.createMaeva(GameClassRegistry.createShamanClass()));
        state.registerCompanion(CompanionFactory.createSilas(GameClassRegistry.createPriestClass()));
    }
    
    private void showIntro(String name, Element element, GameClass startingClass) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  THE SHATTERED RECORDS");
        ui.displaySeparator();
        ui.displayMessage("");
        ui.displayMessage("  You are " + name + ", a " + startingClass.getName() + " of " + element + " affinity.");
        ui.displayMessage("");
        ui.displayMessage("  The world is fractured into four regions:");
        ui.displayMessage("  - The Archivist Core: Rigid law, sun-scorched archives");
        ui.displayMessage("  - The Sump: Desperate slums, fierce community");
        ui.displayMessage("  - The Choke: Living forest, symbiotic horror");
        ui.displayMessage("  - The Zenith: Extreme purity, monastic isolation");
        ui.displayMessage("");
        ui.displayMessage("  A corruption spreads. Companions will join you -");
        ui.displayMessage("  or abandon you - based on your choices.");
        ui.displayMessage("");
        ui.displayMessage("  Press Enter to begin...");
        scanner.nextLine();
    }
    
    // ==================== Main Game Loop ====================
    
    private void gameLoop() {
        while (!state.isGameOver() && !state.isGameWon()) {
            Location loc = state.getCurrentLocation();
            displayLocation(loc);
            String action = getLocationAction(loc);
            processLocationAction(action, loc);
        }
        
        showEnding();
    }
    
    private void displayLocation(Location loc) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  " + loc.getName());
        if (loc.getRegionType() != null) {
            ui.displayMessage("  Region: " + loc.getRegionType().getDisplayName());
        }
        ui.displaySeparator();
        ui.displayMessage("");
        ui.displayMessage("  " + loc.getDescription());
        ui.displayMessage("");
        
        // Show companions present
        List<String> compIds = loc.getCompanionsPresent();
        if (!compIds.isEmpty()) {
            for (String compId : compIds) {
                Companion c = state.getCompanion(compId);
                if (c != null && !c.isRecruited()) {
                    ui.displayMessage("  [!] " + c.getName() + " is here.");
                }
            }
        }
        
        // Show connections
        ui.displayMessage("");
        ui.displayMessage("  Paths:");
        for (Map.Entry<String, String> conn : loc.getConnectedLocations().entrySet()) {
            Location dest = state.getWorldMap().getLocation(conn.getValue());
            String status = dest.isDiscovered() ? dest.getName() : "???";
            ui.displayMessage("    " + conn.getKey() + " -> " + status);
        }
    }
    
    private String getLocationAction(Location loc) {
        ui.displayMessage("");
        ui.displayMessage("  Actions:");
        ui.displayMessage("  [1] Explore (look for enemies/items)");
        ui.displayMessage("  [2] Travel");
        ui.displayMessage("  [3] Talk to people");
        ui.displayMessage("  [4] Check party");
        ui.displayMessage("  [5] Quest log");
        ui.displayMessage("  [6] Rest (heal party)");
        System.out.print("\n> ");
        return scanner.nextLine().trim();
    }
    
    private void processLocationAction(String action, Location loc) {
        switch (action) {
            case "1" -> exploreLocation(loc);
            case "2" -> travel(loc);
            case "3" -> talkToPeople(loc);
            case "4" -> checkParty();
            case "5" -> showQuestLog();
            case "6" -> rest();
            default -> {}
        }
    }
    
    // ==================== Explore ====================
    
    private void exploreLocation(Location loc) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  EXPLORING " + loc.getName().toUpperCase());
        ui.displaySeparator();
        
        // Check for enemies
        List<String> enemyIds = loc.getEnemiesPresent();
        if (!enemyIds.isEmpty() && Math.random() < 0.6) {
            startCombat(loc);
            return;
        }
        
        // Check for quests
        List<String> questIds = loc.getAvailableQuests();
        for (String questId : questIds) {
            if (!state.getQuestManager().isQuestCompleted(questId)) {
                state.getQuestManager().startQuest(questId);
                ui.displayMessage("");
                ui.displayMessage("  Press Enter to continue...");
                scanner.nextLine();
                return;
            }
        }
        
        // Random flavor text
        String[] flavorTexts = {
            "You search the area but find nothing of note.",
            "The wind whispers through the area.",
            "You find an old crumbled wall covered in moss.",
            "Faint footprints lead in different directions.",
            "A distant sound echoes through the area."
        };
        ui.displayMessage("\n  " + flavorTexts[new Random().nextInt(flavorTexts.length)]);
        ui.displayMessage("\n  Press Enter to continue...");
        scanner.nextLine();
    }
    
    // ==================== Combat ====================
    
    private void startCombat(Location loc) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  COMBAT ENCOUNTER!");
        ui.displaySeparator();
        
        // Create enemies based on location
        List<Enemy> enemies = createEnemiesForLocation(loc);
        
        ui.displayMessage("\n  Enemies:");
        for (Enemy e : enemies) {
            ui.displayMessage("  - " + e.getName() + " (Lv." + e.getLevel() + ")");
        }
        
        ui.displayMessage("\n  Press Enter to start battle...");
        scanner.nextLine();
        
        // Run combat
        CombatEngine combat = new CombatEngine(Difficulty.NORMAL);
        combat.initializeBattle(state.getCombatParty(), enemies);
        
        int turnCount = 0;
        while (!combat.isBattleEnded() && turnCount < 20) {
            ui.clearScreen();
            ui.displaySeparator();
            ui.displayMessage("  TURN " + (turnCount + 1));
            ui.displaySeparator();
            
            List<CombatEngine.TurnEvent> events = combat.processTurn();
            for (CombatEngine.TurnEvent event : events) {
                ui.displayMessage("  " + event.getMessage());
            }
            
            turnCount++;
            
            if (!combat.isBattleEnded()) {
                // Show party status
                ui.displayMessage("");
                ui.displayMessage("  Party Status:");
                for (com.rpg.models.Character c : state.getCombatParty()) {
                    String status = c.isAlive() ? 
                        "HP: " + c.getCurrentHP() + "/" + c.getMaxHP() : "DEFEATED";
                    ui.displayMessage("    " + c.getName() + " - " + status);
                }
                ui.displayMessage("");
                ui.displayMessage("  Press Enter for next turn...");
                scanner.nextLine();
            }
        }
        
        // Battle result
        CombatEngine.BattleResult result = combat.getResult();
        ui.displayMessage("");
        ui.displayMessage("  " + (result != null ? result.getMessage() : "Battle ended."));
        
        if (result != null && result.isVictory()) {
            // Give rewards
            int expGain = 50 * loc.getEnemiesPresent().size();
            state.getPlayer().gainExperience(expGain);
            ui.displayMessage("  Gained " + expGain + " EXP!");
            
            // Update quest kill objectives
            for (Enemy e : enemies) {
                state.getQuestManager().updateQuestsOnEvent(
                    com.rpg.systems.QuestObjective.ObjectiveType.KILL_ENEMY, e.getId());
            }
            
            // Subversion: combat choices affect alignment
            state.getAlignmentSystem().makeChoice(0, 0, "Won battle");
        }
        
        if (result != null && !result.isVictory()) {
            ui.displayMessage("  Your party has been defeated...");
            ui.displayMessage("  Returning to last location...");
            state.healParty();
        }
        
        ui.displayMessage("\n  Press Enter to continue...");
        scanner.nextLine();
    }
    
    private List<Enemy> createEnemiesForLocation(Location loc) {
        List<Enemy> enemies = new ArrayList<>();
        int playerLevel = state.getPlayer().getLevel();
        RegionType region = loc.getRegionType();
        
        // Create 1-2 enemies appropriate to the region
        int count = 1 + new Random().nextInt(2);
        
        for (int i = 0; i < count; i++) {
            Enemy enemy;
            if (region == RegionType.ARCHIVIST_CORE) {
                Stats stats = new Stats(10 + playerLevel, 8 + playerLevel, 10 + playerLevel, 
                                         6 + playerLevel, 6 + playerLevel, 8 + playerLevel);
                enemy = new Enemy("archivist_guard", "Archivist Guard", playerLevel,
                    stats, Element.EARTH, Enemy.AIPattern.TACTICAL);
            } else if (region == RegionType.SUMP) {
                Stats stats = new Stats(8 + playerLevel, 12 + playerLevel, 8 + playerLevel,
                                         6 + playerLevel, 6 + playerLevel, 12 + playerLevel);
                enemy = new Enemy("sump_thug", "Sump Thug", playerLevel,
                    stats, Element.WIND, Enemy.AIPattern.AGGRESSIVE);
            } else if (region == RegionType.CHOKE) {
                Stats stats = new Stats(12 + playerLevel, 6 + playerLevel, 14 + playerLevel,
                                         8 + playerLevel, 10 + playerLevel, 4 + playerLevel);
                enemy = new Enemy("choke_vinebeast", "Vine Beast", playerLevel,
                    stats, Element.EARTH, Enemy.AIPattern.AGGRESSIVE);
            } else {
                Stats stats = new Stats(10 + playerLevel, 10 + playerLevel, 10 + playerLevel,
                                         10 + playerLevel, 10 + playerLevel, 10 + playerLevel);
                enemy = new Enemy("zenith_pilgrim", "Zealous Pilgrim", playerLevel,
                    stats, Element.LIGHT, Enemy.AIPattern.DEFENSIVE);
            }
            
            // Add a basic ability
            Ability attack = new Ability.Builder("strike", "Strike", 1)
                .description("A basic attack.")
                .basePower(1.5)
                .element(enemy.getElementAffinity())
                .build();
            enemy.addAbility(attack);
            
            enemies.add(enemy);
        }
        
        return enemies;
    }
    
    // ==================== Travel ====================
    
    private void travel(Location current) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  WHERE TO?");
        ui.displaySeparator();
        ui.displayMessage("");
        
        Map<String, String> connections = current.getConnectedLocations();
        List<String> directions = new ArrayList<>(connections.keySet());
        
        for (int i = 0; i < directions.size(); i++) {
            String dir = directions.get(i);
            Location dest = state.getWorldMap().getLocation(connections.get(dir));
            String name = dest.isDiscovered() ? dest.getName() : "Unknown Area";
            ui.displayMessage("  [" + (i + 1) + "] " + dir + " -> " + name);
        }
        ui.displayMessage("  [0] Stay here");
        
        System.out.print("\n> ");
        String input = scanner.nextLine().trim();
        
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < directions.size()) {
                String dir = directions.get(idx);
                Location dest = state.getWorldMap().travel(current, dir);
                if (dest != null) {
                    // Check flag requirements
                    if (dest.canEnter(state.getFlagManager().getAllFlags())) {
                        state.setCurrentLocation(dest);
                        state.advanceDay();
                    } else {
                        ui.displayMessage("\n  You can't enter this area yet. Something is blocking the way.");
                        ui.displayMessage("  Press Enter to continue...");
                        scanner.nextLine();
                    }
                }
            }
        } catch (NumberFormatException e) {
            // Stay here
        }
    }
    
    // ==================== Talk to People ====================
    
    private void talkToPeople(Location loc) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  PEOPLE AT " + loc.getName().toUpperCase());
        ui.displaySeparator();
        
        List<String> compIds = loc.getCompanionsPresent();
        if (compIds.isEmpty()) {
            ui.displayMessage("\n  There's no one to talk to here.");
            ui.displayMessage("  Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // List available companions
        List<Companion> available = new ArrayList<>();
        for (String compId : compIds) {
            Companion c = state.getCompanion(compId);
            if (c != null && !c.isRecruited()) {
                available.add(c);
            }
        }
        
        if (available.isEmpty()) {
            ui.displayMessage("\n  There's no one new to talk to here.");
            ui.displayMessage("  Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        ui.displayMessage("");
        for (int i = 0; i < available.size(); i++) {
            Companion c = available.get(i);
            ui.displayMessage("  [" + (i + 1) + "] " + c.getName() + " - " + c.getPersonalityType());
        }
        ui.displayMessage("  [0] Leave");
        
        System.out.print("\n> ");
        String input = scanner.nextLine().trim();
        
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < available.size()) {
                interactWithCompanion(available.get(idx));
            }
        } catch (NumberFormatException e) {
            // Leave
        }
    }
    
    private void interactWithCompanion(Companion companion) {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  " + companion.getName());
        ui.displaySeparator();
        ui.displayMessage("");
        ui.displayMessage("  " + companion.getBackstory());
        ui.displayMessage("");
        ui.displayMessage("  Class: " + companion.getCurrentClass().getName());
        ui.displayMessage("  Personality: " + companion.getPersonalityType());
        ui.displayMessage("  Combat Role: " + companion.getCombatRole());
        ui.displayMessage("");
        ui.displayMessage("  [1] Invite to join your party");
        ui.displayMessage("  [2] Ask about the region");
        ui.displayMessage("  [0] Walk away");
        
        System.out.print("\n> ");
        String input = scanner.nextLine().trim();
        
        if (input.equals("1")) {
            if (state.addToParty(companion)) {
                ui.displayMessage("");
                ui.displayMessage("  " + companion.getDialogue("recruitment"));
                if (ui.displayConfirm("  Accept " + companion.getName() + " into your party?")) {
                    ui.displaySuccess("  " + companion.getName() + " has joined your party!");
                }
            }
        } else if (input.equals("2")) {
            ui.displayMessage("");
            String dialogue = companion.getDialogue("region_info");
            if (dialogue.equals("...")) {
                ui.displayMessage("  " + companion.getName() + ": \"This region is... complicated.\"");
            } else {
                ui.displayMessage("  " + companion.getName() + ": \"" + dialogue + "\"");
            }
        }
        
        ui.displayMessage("\n  Press Enter to continue...");
        scanner.nextLine();
    }
    
    // ==================== Party Menu ====================
    
    private void checkParty() {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  PARTY STATUS");
        ui.displaySeparator();
        
        // Show player
        ui.displayMessage("");
        ui.displayMessage("  " + state.getPlayer().toString());
        ui.displayMessage("  Alignment: " + state.getAlignmentSystem().getHonorTier().getName() + 
                         " / " + state.getAlignmentSystem().getCompassionTier().getName());
        
        // Show companions
        List<Companion> party = state.getParty();
        if (party.isEmpty()) {
            ui.displayMessage("");
            ui.displayMessage("  No companions in party.");
        } else {
            ui.displayMessage("");
            ui.displayMessage("  Companions:");
            for (Companion c : party) {
                ui.displayMessage("  - " + c.toString());
            }
        }
        
        ui.displayMessage("");
        ui.displayMessage("  Day: " + state.getDayCount());
        
        ui.displayMessage("\n  Press Enter to continue...");
        scanner.nextLine();
    }
    
    // ==================== Quest Log ====================
    
    private void showQuestLog() {
        ui.clearScreen();
        ui.displayMessage(state.getQuestManager().getQuestLog());
        ui.displayMessage("  Press Enter to continue...");
        scanner.nextLine();
    }
    
    // ==================== Rest ====================
    
    private void rest() {
        ui.clearScreen();
        ui.displaySeparator();
        ui.displayMessage("  RESTING");
        ui.displaySeparator();
        state.healParty();
        state.advanceDay();
        ui.displayMessage("\n  Press Enter to continue...");
        scanner.nextLine();
    }
    
    // ==================== Ending ====================
    
    private void showEnding() {
        ui.clearScreen();
        ui.displaySeparator();
        if (state.isGameWon()) {
            ui.displayMessage("  VICTORY!");
        } else {
            ui.displayMessage("  GAME OVER");
        }
        ui.displaySeparator();
        ui.displayMessage("");
        ui.displayMessage("  Days survived: " + state.getDayCount());
        ui.displayMessage("  Companions recruited: " + state.getAllCompanions().size());
        ui.displayMessage("  Quests completed: " + state.getQuestManager().getCompletedQuests().size());
        ui.displayMessage("");
        ui.displayMessage("  Thank you for playing the alpha demo!");
        ui.displayMessage("");
        ui.cleanup();
    }
}
