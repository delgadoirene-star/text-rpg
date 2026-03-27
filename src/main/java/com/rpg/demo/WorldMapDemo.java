package com.rpg.demo;

import com.rpg.world.Location;
import com.rpg.world.RegionType;
import com.rpg.world.ReputationSystem;
import com.rpg.world.Rumor;
import com.rpg.world.WorldMap;

/**
 * Demo to test the World Map, Regional Reputation, and Rumor system
 */
public class WorldMapDemo {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       WORLD MAP & REPUTATION SYSTEM DEMO                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        // Create reputation system
        ReputationSystem reputation = new ReputationSystem();
        
        // Create world map
        WorldMap worldMap = new WorldMap();
        worldMap.initializeWorld();
        worldMap.setReputationSystem(reputation);
        
        System.out.println("\n=== TESTING REGIONAL REPUTATION ===");
        
        // Test reputation changes with diplomacy matrix
        System.out.println("\nInitial reputation:");
        System.out.println(reputation.getReputationSummary());
        
        System.out.println("\n--- Action: Player gains +20 reputation in ARCHIVIST_CORE ---");
        reputation.modifyReputation(RegionType.ARCHIVIST_CORE, 20, "Quest completion");
        
        System.out.println("\n--- Action: Player loses -60 reputation in SUMP ---");
        reputation.modifyReputation(RegionType.SUMP, -60, "Betrayed local survivors");
        
        System.out.println("\nCurrent reputation:");
        System.out.println(reputation.getReputationSummary());
        
        System.out.println("\n--- Checking SULLIED status ---");
        System.out.println("ARCHIVIST_CORE sullied? " + reputation.isSullied(RegionType.ARCHIVIST_CORE));
        System.out.println("SUMP sullied? " + reputation.isSullied(RegionType.SUMP));
        System.out.println("CHOKE sullied? " + reputation.isSullied(RegionType.CHOKE));
        System.out.println("ZENITH sullied? " + reputation.isSullied(RegionType.ZENITH));
        
        System.out.println("\n=== TESTING RUMOR QUEUE ===");
        
        // Process rumors after some days pass
        System.out.println("\nAdvancing 3 days...");
        worldMap.advanceDays(3);
        
        System.out.println("\nCurrent rumors in queue:");
        for (Rumor rumor : reputation.getRumors().values()) {
            System.out.println("  " + rumor);
        }
        
        System.out.println("\n=== TESTING WORLD MAP ===");
        
        worldMap.displayWorldStatus();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       WORLD MAP DEMO COMPLETED SUCCESSFULLY!                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
