package com.rpg;

import com.rpg.ui.ConsoleUI;

/**
 * Main entry point for the Text-Based RPG
 */
public class Main {
    
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.displayWelcome();
        
        // TODO: Initialize game engine
        // TODO: Load or create new game
        // TODO: Start game loop
        
        ui.displayMessage("\nGame initialization coming soon...");
        ui.displayMessage("Project structure created successfully!");
    }
}
