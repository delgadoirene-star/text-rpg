package com.rpg.ui;

import java.util.Scanner;

/**
 * Handles all console output and formatting.
 * ANSI colors are optional - works fine in plain terminals too.
 */
public class ConsoleUI {
    
    private final Scanner scanner;
    private final boolean ansiEnabled;
    
    // ANSI escape codes (no library needed)
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.ansiEnabled = detectAnsi();
    }
    
    private boolean detectAnsi() {
        // Check if terminal supports ANSI
        String term = System.getenv("TERM");
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) {
            // Windows CMD/PowerShell - ANSI support varies
            return System.getenv("WT_SESSION") != null || // Windows Terminal
                   System.getenv("TERM_PROGRAM") != null; // VS Code terminal, etc.
        }
        // Unix/macOS - almost always supports ANSI
        return term != null && !term.equals("dumb");
    }
    
    // ==================== ANSI helpers ====================
    
    private String color(String code, String text) {
        if (ansiEnabled) return code + text + RESET;
        return text;
    }
    
    // ==================== Display Methods ====================
    
    public void displayWelcome() {
        clearScreen();
        
        String title = """
            ╔═══════════════════════════════════════════════════════════════╗
            ║                                                               ║
            ║                  THE SHATTERED RECORDS                        ║
            ║                                                               ║
            ║              A Story-Driven Alpha Demo                        ║
            ║                                                               ║
            ╚═══════════════════════════════════════════════════════════════╝
            """;
        System.out.println(color(CYAN, title));
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public void displayColored(String message, String colorCode) {
        System.out.println(color(colorCode, message));
    }
    
    /**
     * Compatibility method for old Jansi-based callers
     */
    public void displayMessage(String message, Object color) {
        System.out.println(message);
    }
    
    public void displayError(String error) {
        System.out.println(color(RED, "ERROR: " + error));
    }
    
    public void displaySuccess(String success) {
        System.out.println(color(GREEN, success));
    }
    
    public void displayWarning(String warning) {
        System.out.println(color(YELLOW, "WARNING: " + warning));
    }
    
    public void clearScreen() {
        // Try ANSI clear, fall back to blank lines
        if (ansiEnabled) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            for (int i = 0; i < 3; i++) System.out.println();
        }
    }
    
    public void displaySeparator() {
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
    
    // ==================== Input Methods ====================
    
    /**
     * Prompt for yes/no confirmation
     */
    public boolean displayConfirm(String prompt) {
        System.out.println(prompt + " [y/n]");
        System.out.print("> ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
    
    /**
     * Prompt for a line of input
     */
    public String promptInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }
    
    public void cleanup() {
        // Nothing to clean up without Jansi
    }
}
