package com.rpg;

/**
 * Simple debug logging system.
 * Set DEBUG_ENABLED to true to see detailed logs in the console.
 */
public class Debug {
    
    public static boolean DEBUG_ENABLED = true;
    
    public static void log(String tag, String message) {
        if (DEBUG_ENABLED) {
            System.out.println("[DEBUG][" + tag + "] " + message);
        }
    }
    
    public static void logUI(String component, String message) {
        if (DEBUG_ENABLED) {
            System.out.println("[UI][" + component + "] " + message);
        }
    }
    
    public static void logCombat(String message) {
        if (DEBUG_ENABLED) {
            System.out.println("[COMBAT] " + message);
        }
    }
    
    public static void logCompanion(String companionId, String message) {
        if (DEBUG_ENABLED) {
            System.out.println("[COMPANION][" + companionId + "] " + message);
        }
    }
    
    public static void logRep(String region, String message) {
        if (DEBUG_ENABLED) {
            System.out.println("[REP][" + region + "] " + message);
        }
    }
    
    public static void error(String tag, String message) {
        System.err.println("[ERROR][" + tag + "] " + message);
    }
    
    public static void error(String tag, String message, Exception e) {
        System.err.println("[ERROR][" + tag + "] " + message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
