package com.rpg.world;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks player fame and reputation in each region (-100 to +100).
 * 
 * CRITICAL: This is NOT the same as Alignment (Honor/Compassion).
 * - Alignment: Tracks the player's soul, affects class/companion loyalty
 * - Reputation: Tracks regional fame, affects shop prices, safe zones, enemy encounters
 */
public class ReputationSystem {
    private Map<RegionType, Integer> regionalReputation;
    private Map<String, Rumor> rumorQueue;
    private WorldMap worldMap;

    public ReputationSystem() {
        this.regionalReputation = new HashMap<>();
        this.rumorQueue = new HashMap<>();
        
        for (RegionType region : RegionType.values()) {
            regionalReputation.put(region, 0);
        }
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Get reputation for a specific region (-100 to +100)
     */
    public int getReputation(RegionType region) {
        return regionalReputation.getOrDefault(region, 0);
    }

    /**
     * Check if player is "Sullied" in a region (Reputation <= -50).
     * Sullied players face hostile events, locked shops, and aggressive encounters.
     */
    public boolean isSullied(RegionType region) {
        return getReputation(region) <= -50;
    }

    /**
     * Check if player is "Beloved" in a region (Reputation >= +75).
     */
    public boolean isBeloved(RegionType region) {
        return getReputation(region) >= 75;
    }

    /**
     * Modify reputation in a region.
     * If diplomacy matrix applies, creates Rumor objects for cascading effects.
     * 
     * @param region Target region
     * @param change Amount to change (-100 to +100)
     * @param sourceDescription Why the reputation changed (for logging)
     */
    public void modifyReputation(RegionType region, int change, String sourceDescription) {
        int current = getReputation(region);
        int newRep = Math.max(-100, Math.min(100, current + change));
        regionalReputation.put(region, newRep);
        
        System.out.printf("[REPUTATION] %s: %+d (%s) - Now %d in %s%n", 
            sourceDescription, change, region.getDisplayName(), newRep, region.getDisplayName());
        
        if (newRep <= -50 && current > -50) {
            System.out.printf("[WARNING] You are now SULLIED in %s!%n", region.getDisplayName());
        } else if (newRep >= 75 && current < 75) {
            System.out.printf("[SUCCESS] You are now BELOVED in %s!%n", region.getDisplayName());
        }
        
        applyDiplomacyMatrix(region, change);
    }

    /**
     * Apply the Diplomacy Matrix - cascading reputation effects between regions.
     * 
     * Diplomacy Modifiers:
     * - ARCHIVIST_CORE vs. SUMP: -0.5 (Enemies)
     * - ARCHIVIST_CORE vs. ZENITH: +0.2 (Similar lawful values)
     * - ARCHIVIST_CORE vs. CHOKE: -0.3 (Industry vs. Wild)
     * - SUMP vs. CHOKE: +0.3 (Fellow outcasts)
     * - ZENITH vs. SUMP: -0.2 (Purity vs. Chaos)
     */
    private void applyDiplomacyMatrix(RegionType source, int primaryChange) {
        Map<RegionType, Double> modifiers = new HashMap<>();
        
        switch (source) {
            case ARCHIVIST_CORE:
                modifiers.put(RegionType.SUMP, -0.5);
                modifiers.put(RegionType.ZENITH, +0.2);
                modifiers.put(RegionType.CHOKE, -0.3);
                break;
            case SUMP:
                modifiers.put(RegionType.ARCHIVIST_CORE, -0.5);
                modifiers.put(RegionType.ZENITH, -0.2);
                modifiers.put(RegionType.CHOKE, +0.3);
                break;
            case CHOKE:
                modifiers.put(RegionType.ARCHIVIST_CORE, -0.3);
                modifiers.put(RegionType.SUMP, +0.3);
                modifiers.put(RegionType.ZENITH, 0.0);
                break;
            case ZENITH:
                modifiers.put(RegionType.ARCHIVIST_CORE, +0.2);
                modifiers.put(RegionType.SUMP, -0.2);
                modifiers.put(RegionType.CHOKE, 0.0);
                break;
        }
        
        for (Map.Entry<RegionType, Double> entry : modifiers.entrySet()) {
            RegionType targetRegion = entry.getKey();
            double modifier = entry.getValue();
            
            int cascadingChange = (int) (primaryChange * modifier);
            
            if (cascadingChange != 0) {
                createRumor(targetRegion, cascadingChange);
            }
        }
    }

    /**
     * Create a Rumor object for cascading reputation changes.
     * The rumor is queued with travel time based on world map distances.
     */
    private void createRumor(RegionType targetRegion, int reputationChange) {
        if (worldMap == null) {
            int current = getReputation(targetRegion);
            int newRep = Math.max(-100, Math.min(100, current + reputationChange));
            regionalReputation.put(targetRegion, newRep);
            System.out.printf("[RUMOR] News reached %s: %+d (instant, no map)%n", 
                targetRegion.getDisplayName(), reputationChange);
            return;
        }
        
        int daysUntilArrival = worldMap.calculateTravelDays(targetRegion);
        Rumor rumor = new Rumor(targetRegion, reputationChange, daysUntilArrival);
        rumorQueue.put(targetRegion.name() + "_" + System.currentTimeMillis(), rumor);
        
        System.out.printf("[RUMOR] News will reach %s in %d days%n", 
            targetRegion.getDisplayName(), daysUntilArrival);
    }

    /**
     * Process rumors - advance time and apply reputation changes.
     * Call this when the player travels or time passes.
     * 
     * @param daysPassed Number of days to advance
     */
    public void processRumors(int daysPassed) {
        if (rumorQueue.isEmpty()) {
            return;
        }
        
        System.out.println("\n=== PROCESSING RUMORS ===");
        
        Map<String, Rumor> toRemove = new HashMap<>();
        
        for (Map.Entry<String, Rumor> entry : rumorQueue.entrySet()) {
            Rumor rumor = entry.getValue();
            rumor.advanceDays(daysPassed);
            
            if (rumor.hasArrived()) {
                RegionType region = rumor.getTargetRegion();
                int change = rumor.getReputationChange();
                int current = getReputation(region);
                int newRep = Math.max(-100, Math.min(100, current + change));
                regionalReputation.put(region, newRep);
                
                System.out.printf("[RUMOR] News of your actions has reached %s: %+d reputation (Now: %d)%n", 
                    region.getDisplayName(), change, newRep);
                
                if (newRep <= -50) {
                    System.out.printf("[ALERT] You are now SULLIED in %s!%n", region.getDisplayName());
                }
                
                toRemove.put(entry.getKey(), rumor);
            } else {
                System.out.printf("[RUMOR] News traveling to %s: %d days remaining%n", 
                    rumor.getTargetRegion().getDisplayName(), rumor.getDaysUntilArrival());
            }
        }
        
        for (String key : toRemove.keySet()) {
            rumorQueue.remove(key);
        }
        
        if (rumorQueue.isEmpty()) {
            System.out.println("No rumors currently traveling.");
        }
    }

    /**
     * Get all current rumors in the queue
     */
    public Map<String, Rumor> getRumors() {
        return new HashMap<>(rumorQueue);
    }

    /**
     * Get reputation summary for all regions
     */
    public String getReputationSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REGIONAL REPUTATION ===\n");
        
        for (RegionType region : RegionType.values()) {
            int rep = getReputation(region);
            String status = "";
            
            if (rep >= 75) {
                status = "[BELOVED]";
            } else if (rep > 0) {
                status = "[FRIENDLY]";
            } else if (rep == 0) {
                status = "[NEUTRAL]";
            } else if (rep > -50) {
                status = "[UNFRIENDLY]";
            } else {
                status = "[SULLIED]";
            }
            
            sb.append(String.format("  %-20s %+4d %s%n", region.getDisplayName(), rep, status));
        }
        
        return sb.toString();
    }

    /**
     * Get the region where player has highest reputation
     */
    public RegionType getMostFavorableRegion() {
        RegionType best = RegionType.ARCHIVIST_CORE;
        int highest = -100;
        
        for (Map.Entry<RegionType, Integer> entry : regionalReputation.entrySet()) {
            if (entry.getValue() > highest) {
                highest = entry.getValue();
                best = entry.getKey();
            }
        }
        
        return best;
    }

    /**
     * Get the region where player has lowest reputation
     */
    public RegionType getLeastFavorableRegion() {
        RegionType worst = RegionType.ARCHIVIST_CORE;
        int lowest = 100;
        
        for (Map.Entry<RegionType, Integer> entry : regionalReputation.entrySet()) {
            if (entry.getValue() < lowest) {
                lowest = entry.getValue();
                worst = entry.getKey();
            }
        }
        
        return worst;
    }
}
