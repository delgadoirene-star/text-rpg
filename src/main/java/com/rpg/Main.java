package com.rpg;

import com.rpg.ui.fx.GameApplication;

/**
 * Main entry point for The Shattered Records
 * 
 * Version 1.2 - Dual Mode (Console/JavaFX GUI)
 * 
 * Usage:
 *   java -jar text-rpg.jar          # Launches JavaFX GUI (default)
 *   java -jar text-rpg.jar --console # Launches Console mode
 *   java -jar text-rpg.jar --help    # Shows help
 * 
 * Maven:
 *   mvn javafx:run                   # Run with JavaFX
 *   mvn exec:java -Dexec.args="--console"  # Run in console mode
 */
public class Main {
    
    public static final String VERSION = "1.2-SNAPSHOT";
    public static final String TITLE = "The Shattered Records";
    
    public static void main(String[] args) {
        LaunchMode mode = parseArgs(args);
        
        switch (mode) {
            case GUI -> launchGui(args);
            case CONSOLE -> launchConsole();
            case HELP -> showHelp();
        }
    }
    
    private enum LaunchMode {
        GUI, CONSOLE, HELP
    }
    
    private static LaunchMode parseArgs(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "--console", "-c" -> { return LaunchMode.CONSOLE; }
                case "--help", "-h", "-?" -> { return LaunchMode.HELP; }
            }
        }
        return LaunchMode.GUI; // Default to GUI
    }
    
    private static void launchGui(String[] args) {
        System.out.println("Launching " + TITLE + " v" + VERSION + " (JavaFX GUI)...");
        try {
            GameApplication.launch(GameApplication.class, args);
        } catch (Exception e) {
            System.err.println("Failed to launch JavaFX GUI: " + e.getMessage());
            System.err.println("Falling back to console mode...");
            launchConsole();
        }
    }
    
    private static void launchConsole() {
        System.out.println("Launching " + TITLE + " v" + VERSION + " (Console Mode)...");
        GameEngine engine = new GameEngine();
        engine.run();
    }
    
    private static void showHelp() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  THE SHATTERED RECORDS                        ║");
        System.out.println("║                     Version " + VERSION + "                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("A story-driven RPG with deep combat mechanics.");
        System.out.println();
        System.out.println("USAGE:");
        System.out.println("  java -jar text-rpg.jar [OPTIONS]");
        System.out.println();
        System.out.println("OPTIONS:");
        System.out.println("  (no args)     Launch with JavaFX GUI (default)");
        System.out.println("  --console, -c Launch in console/text mode");
        System.out.println("  --help, -h    Show this help message");
        System.out.println();
        System.out.println("MAVEN:");
        System.out.println("  mvn javafx:run                      # Run with JavaFX GUI");
        System.out.println("  mvn exec:java -Dexec.args=\"--console\" # Run in console mode");
        System.out.println();
        System.out.println("FEATURES:");
        System.out.println("  - Dual-axis alignment system (Honor/Compassion)");
        System.out.println("  - 11 unique companions with personal quests");
        System.out.println("  - 94+ character classes with transformation paths");
        System.out.println("  - Deep turn-based combat with elemental combos");
        System.out.println("  - Hover tooltips for status effects (GUI mode)");
        System.out.println();
    }
}
