package com.rpg.ui;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Handles all console output and formatting
 */
public class ConsoleUI {
    
    public ConsoleUI() {
        AnsiConsole.systemInstall();
    }
    
    public void displayWelcome() {
        clearScreen();
        
        System.out.println(ansi().fg(Ansi.Color.CYAN).a(
            """
            ╔═══════════════════════════════════════════════════════════════╗
            ║                                                               ║
            ║                    TEXT-BASED RPG                             ║
            ║                                                               ║
            ║              A Story-Driven Adventure                         ║
            ║                                                               ║
            ╚═══════════════════════════════════════════════════════════════╝
            """
        ).reset());
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public void displayMessage(String message, Ansi.Color color) {
        System.out.println(ansi().fg(color).a(message).reset());
    }
    
    public void displayError(String error) {
        System.out.println(ansi().fg(Ansi.Color.RED).a("ERROR: " + error).reset());
    }
    
    public void displaySuccess(String success) {
        System.out.println(ansi().fg(Ansi.Color.GREEN).a(success).reset());
    }
    
    public void displayWarning(String warning) {
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("WARNING: " + warning).reset());
    }
    
    public void clearScreen() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
    }
    
    public void displaySeparator() {
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
    
    public void cleanup() {
        AnsiConsole.systemUninstall();
    }
}
