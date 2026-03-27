package com.rpg.world;

import java.util.Map;
import java.util.HashMap;

/**
 * Manages all game locations and the world map
 */
public class WorldMap {
    private Map<String, Location> locations;
    private String startingLocationId;
    
    public WorldMap() {
        this.locations = new HashMap<>();
        this.travelCosts = new HashMap<>();
        this.currentDay = 1;
    }
    
    /**
     * Add a location to the world
     */
    public void addLocation(Location location) {
        if (location != null) {
            locations.put(location.getId(), location);
        }
    }
    
    /**
     * Get a location by its ID
     */
    public Location getLocation(String id) {
        return locations.get(id);
    }
    
    /**
     * Get all locations
     */
    public Map<String, Location> getAllLocations() {
        return new HashMap<>(locations);
    }
    
    /**
     * Set the starting location for the player
     */
    public void setStartingLocation(String id) {
        if (locations.containsKey(id)) {
            this.startingLocationId = id;
        } else {
            throw new IllegalArgumentException("Starting location ID does not exist in map");
        }
    }
    
    /**
     * Get the starting location
     */
    public Location getStartingLocation() {
        if (startingLocationId == null) {
            throw new IllegalStateException("Starting location not set");
        }
        return getLocation(startingLocationId);
    }
    
    /**
     * Move from one location to another
     * @param current The current location
     * @param direction The direction to travel
     * @return The new location, or null if path doesn't exist
     */
    public Location travel(Location current, String direction) {
        if (current == null) return null;
        
        String destinationId = current.getConnectedLocations().get(direction.toLowerCase());
        if (destinationId == null) {
            return null; // No path in that direction
        }
        
        return getLocation(destinationId);
    }
    
    // ==================== World Initialization ====================
    
    /**
     * Create and connect all game locations
     * (This would typically be loaded from data files)
     */
    public void initializeWorld() {
        // Create locations - all in ARCHIVIST_CORE region
        Location town = new Location.Builder("town_square", "Town Square",
            "The bustling center of the capital city. A large fountain sits in the middle.")
            .region(RegionType.ARCHIVIST_CORE)
            .connect("north", "castle_gate")
            .connect("south", "market_district")
            .connect("west", "residential_area")
            .connect("east", "guild_hall")
            .build();
        
        Location castleGate = new Location.Builder("castle_gate", "Castle Gate",
            "Two enormous guards stand before the majestic gate of the royal castle.")
            .region(RegionType.ARCHIVIST_CORE)
            .connect("south", "town_square")
            .requireFlag("royal_invitation")
            .build();
            
        Location market = new Location.Builder("market_district", "Market District",
            "Merchants hawk their wares, from fresh food to shiny trinkets.")
            .region(RegionType.ARCHIVIST_CORE)
            .connect("north", "town_square")
            .addCompanion("companion_merchant")
            .addQuest("quest_lost_goods")
            .build();
            
        Location guildHall = new Location.Builder("guild_hall", "Adventurer's Guild",
            "A large hall filled with adventurers seeking fame and fortune.")
            .region(RegionType.ARCHIVIST_CORE)
            .connect("west", "town_square")
            .addQuest("quest_goblin_slaying")
            .build();
            
        Location residential = new Location.Builder("residential_area", "Residential Area",
            "Quiet streets lined with houses.")
            .region(RegionType.ARCHIVIST_CORE)
            .connect("east", "town_square")
            .build();
            
        Location sumpEntrance = new Location.Builder("sump_entrance", "The Sump Gate",
            "A grim entrance to the waterlogged slums below. The smell of desperation hangs heavy.")
            .region(RegionType.SUMP)
            .connect("north", "town_square")
            .connect("south", "sump_depths")
            .build();
            
        Location sumpDepths = new Location.Builder("sump_depths", "The Drowned Wards",
            "Vertical slums carved into canyon walls. Water drips from every surface.")
            .region(RegionType.SUMP)
            .connect("north", "sump_entrance")
            .build();
            
        // Add locations to map
        addLocation(town);
        addLocation(castleGate);
        addLocation(market);
        addLocation(guildHall);
        addLocation(residential);
        addLocation(sumpEntrance);
        addLocation(sumpDepths);
        
        // Set travel costs
        setTravelCost("town_square", "market_district", 1);
        setTravelCost("town_square", "guild_hall", 1);
        setTravelCost("town_square", "residential_area", 1);
        setTravelCost("town_square", "sump_entrance", 1);
        setTravelCost("sump_entrance", "sump_depths", 2);
        
        // Set starting location
        setStartingLocation("town_square");
        
        // Set current location for travel calculations
        this.currentLocation = getStartingLocation();
        
        // Mark starting location as discovered
        getStartingLocation().setDiscovered(true);
    }
    
    // ==================== Travel & Reputation System ====================
    
    private Location currentLocation;
    private ReputationSystem reputationSystem;
    private int currentDay;
    private Map<String, Integer> travelCosts;
    
    public void setReputationSystem(ReputationSystem system) {
        this.reputationSystem = system;
        if (system != null) {
            system.setWorldMap(this);
        }
    }
    
    public Location getCurrentLocation() {
        return currentLocation;
    }
    
    public int getCurrentDay() {
        return currentDay;
    }
    
    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }
    
    public void advanceDays(int days) {
        this.currentDay += days;
        System.out.printf("\n[DAY] Time passes... It is now Day %d%n", currentDay);
        
        if (reputationSystem != null) {
            reputationSystem.processRumors(days);
        }
    }
    
    /**
     * Set travel cost between two locations (in days)
     */
    public void setTravelCost(String fromId, String toId, int days) {
        String key = fromId + "_" + toId;
        travelCosts.put(key, days);
        String reverseKey = toId + "_" + fromId;
        travelCosts.put(reverseKey, days);
    }
    
    /**
     * Get travel cost between two locations
     */
    public int getTravelCost(String fromId, String toId) {
        String key = fromId + "_" + toId;
        return travelCosts.getOrDefault(key, 1);
    }
    
    /**
     * Get all discovered locations
     */
    public Map<String, Location> getDiscoveredLocations() {
        Map<String, Location> discovered = new HashMap<>();
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            if (entry.getValue().isDiscovered()) {
                discovered.put(entry.getKey(), entry.getValue());
            }
        }
        return discovered;
    }
    
    /**
     * Travel from current location to a connected destination
     * 
     * @param destination The location to travel to
     * @return true if travel was successful
     */
    public boolean travelTo(Location destination) {
        if (currentLocation == null || destination == null) {
            System.out.println("[ERROR] Invalid travel parameters.");
            return false;
        }

        String destId = destination.getId();
        String currentId = currentLocation.getId();
        
        if (!currentLocation.getConnectedLocations().containsKey(destId)) {
            System.out.printf("[ERROR] %s is not connected to %s!%n", 
                currentLocation.getName(), destination.getName());
            return false;
        }

        int travelCost = getTravelCost(currentId, destId);
        
        System.out.printf("%n=== TRAVELING ===%n");
        System.out.printf("From: %s [%s]%n", currentLocation.getName(), 
            currentLocation.getRegionType() != null ? currentLocation.getRegionType().getDisplayName() : "Unknown");
        System.out.printf("To:   %s [%s]%n", destination.getName(),
            destination.getRegionType() != null ? destination.getRegionType().getDisplayName() : "Unknown");
        System.out.printf("Travel time: %d day(s)%n", travelCost);

        if (travelCost > 1) {
            System.out.println("You travel through wilderness and encounter areas...");
        }

        advanceDays(travelCost);

        currentLocation = destination;
        destination.setDiscovered(true);
        destination.onEnter(reputationSystem);

        return true;
    }
    
    /**
     * Calculate travel days to reach a region (simplified: uses first location in region)
     */
    public int calculateTravelDays(RegionType targetRegion) {
        if (currentLocation == null || currentLocation.getRegionType() == targetRegion) {
            return 0;
        }

        for (Location loc : locations.values()) {
            if (loc.getRegionType() == targetRegion) {
                return getTravelCost(currentLocation.getId(), loc.getId());
            }
        }
        
        return 5;
    }
    
    /**
     * Display world status
     */
    public void displayWorldStatus() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      WORLD MAP STATUS                        ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Current Day: %-5d | Location: %-20s ║%n", currentDay, 
            currentLocation != null ? currentLocation.getName() : "None");
        System.out.printf("║  Region: %-20s | Discovered: %-3d/%-3d locations ║%n",
            currentLocation != null && currentLocation.getRegionType() != null ? 
                currentLocation.getRegionType().getDisplayName() : "None",
            getDiscoveredLocations().size(), locations.size());
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        
        System.out.println("║  REGIONAL PRESENCE:                                          ║");
        if (reputationSystem != null) {
            for (RegionType region : RegionType.values()) {
                int rep = reputationSystem.getReputation(region);
                String status = rep >= 75 ? "★" : rep >= 50 ? "♦" : rep >= 0 ? "○" : rep > -50 ? "□" : "✗";
                System.out.printf("║    %-20s %+4d %s                         ║%n",
                    region.getDisplayName(), rep, status);
            }
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
