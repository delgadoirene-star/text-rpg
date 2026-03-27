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
     * Returns the starting location (coreGate)
     */
    public void initializeWorld() {
        // ==================== CORE REGION ====================
        Location coreGate = new Location.Builder("coreGate", "Core Gate", 
            "The main entrance to the Archivist Core. Guards check all who enter.")
            .region(RegionType.ARCHIVIST_CORE)
            .safeZone(true)
            .build();
        
        Location coreMarket = new Location.Builder("coreMarket", "Core Market", 
            "A bustling marketplace with merchants from across all regions.")
            .region(RegionType.ARCHIVIST_CORE)
            .safeZone(true)
            .shop(true)
            .build();
        
        Location coreAcademy = new Location.Builder("coreAcademy", "Core Academy", 
            "The great library-fortress where scholars study ancient records.")
            .region(RegionType.ARCHIVIST_CORE)
            .safeZone(true)
            .build();
        
        Location coreKeep = new Location.Builder("coreKeep", "Core Keep", 
            "The fortified heart of the Core. Only the worthy may enter.")
            .region(RegionType.ARCHIVIST_CORE)
            .build();
        
        Location coreEstates = new Location.Builder("coreEstates", "Core Estates", 
            "Luxurious mansions of the Core's elite nobility.")
            .region(RegionType.ARCHIVIST_CORE)
            .safeZone(true)
            .build();
        
        Location corePrison = new Location.Builder("corePrison", "Core Prison", 
            "A grim fortress where the Core's enemies are held.")
            .region(RegionType.ARCHIVIST_CORE)
            .build();
        
        // ==================== SUMP REGION ====================
        Location sumpElevator = new Location.Builder("sumpElevator", "Sump Elevator", 
            "A rickety elevator descending into the vertical slums.")
            .region(RegionType.SUMP)
            .build();
        
        Location sumpRatways = new Location.Builder("sumpRatways", "Sump Ratways", 
            "Narrow passages teeming with desperate folk and worse.")
            .region(RegionType.SUMP)
            .build();
        
        Location sumpMarket = new Location.Builder("sumpMarket", "Sump Market", 
            "A black market where anything can be bought for the right price.")
            .region(RegionType.SUMP)
            .shop(true)
            .build();
        
        Location sumpPumps = new Location.Builder("sumpPumps", "Sump Pumps", 
            "Massive pumps keep the water at bay. They groan constantly.")
            .region(RegionType.SUMP)
            .build();
        
        Location sumpArena = new Location.Builder("sumpArena", "Sump Arena", 
            "An illegal fighting pit where blood sport reigns.")
            .region(RegionType.SUMP)
            .build();
        
        Location sumpDepths = new Location.Builder("sumpDepths", "Sump Depths", 
            "The lowest levels of the Sump. Few return from here.")
            .region(RegionType.SUMP)
            .build();
        
        // ==================== CHOKE REGION ====================
        Location chokeBorder = new Location.Builder("chokeBorder", "Choke Border", 
            "Where civilization ends and the parasitic forest begins.")
            .region(RegionType.CHOKE)
            .build();
        
        Location chokeWoods = new Location.Builder("chokeWoods", "Choke Woods", 
            "Trees with too many eyes watch your every move.")
            .region(RegionType.CHOKE)
            .build();
        
        Location chokePass = new Location.Builder("chokePass", "Choke Pass", 
            "A narrow path through the breathing trees.")
            .region(RegionType.CHOKE)
            .build();
        
        Location chokeHeart = new Location.Builder("chokeHeart", "Choke Heart", 
            "The living heart of the forest. It pulses with ancient power.")
            .region(RegionType.CHOKE)
            .safeZone(true)
            .build();
        
        Location chokeBog = new Location.Builder("chokeBog", "Choke Bog", 
            "Quicksand and worse lurks in this murky swamp.")
            .region(RegionType.CHOKE)
            .build();
        
        Location chokeRuins = new Location.Builder("chokeRuins", "Choke Ruins", 
            "Crumbling structures from before the forest claimed this land.")
            .region(RegionType.CHOKE)
            .build();
        
        // ==================== ZENITH REGION ====================
        Location zenithSteps = new Location.Builder("zenithSteps", "Zenith Steps", 
            "Countless stairs leading up to the monasteries above.")
            .region(RegionType.ZENITH)
            .build();
        
        Location zenithMonastery = new Location.Builder("zenithMonastery", "Zenith Monastery", 
            "The main monastery. Monks seek enlightenment here.")
            .region(RegionType.ZENITH)
            .safeZone(true)
            .build();
        
        Location zenithGraveyard = new Location.Builder("zenithGraveyard", "Zenith Graveyard", 
            "Where those who failed their trials are buried.")
            .region(RegionType.ZENITH)
            .build();
        
        Location zenithPinnacle = new Location.Builder("zenithPinnacle", "Zenith Pinnacle", 
            "The highest point. Those who reach it are never the same.")
            .region(RegionType.ZENITH)
            .build();
        
        // ==================== Add All Locations ====================
        addLocation(coreGate);
        addLocation(coreMarket);
        addLocation(coreAcademy);
        addLocation(coreKeep);
        addLocation(coreEstates);
        addLocation(corePrison);
        addLocation(sumpElevator);
        addLocation(sumpRatways);
        addLocation(sumpMarket);
        addLocation(sumpPumps);
        addLocation(sumpArena);
        addLocation(sumpDepths);
        addLocation(chokeBorder);
        addLocation(chokeWoods);
        addLocation(chokePass);
        addLocation(chokeHeart);
        addLocation(chokeBog);
        addLocation(chokeRuins);
        addLocation(zenithSteps);
        addLocation(zenithMonastery);
        addLocation(zenithGraveyard);
        addLocation(zenithPinnacle);
        
        // ==================== Wire Connections ====================
        // CORE connections
        coreGate.addConnection(coreMarket, 5);
        coreMarket.addConnection(coreAcademy, 5);
        coreAcademy.addConnection(coreKeep, 10);
        coreMarket.addConnection(coreEstates, 10);
        coreGate.addConnection(corePrison, 15);
        
        // CORE to other regions
        coreGate.addConnection(sumpElevator, 10);
        coreGate.addConnection(chokeBorder, 20);
        
        // SUMP connections
        sumpElevator.addConnection(sumpRatways, 10);
        sumpRatways.addConnection(sumpMarket, 5);
        sumpRatways.addConnection(sumpArena, 10);
        sumpMarket.addConnection(sumpPumps, 15);
        sumpPumps.addConnection(sumpDepths, 20);
        sumpRatways.addConnection(chokeBorder, 15);
        
        // CHOKE connections
        chokeBorder.addConnection(chokeWoods, 20);
        chokeWoods.addConnection(chokePass, 25);
        chokeWoods.addConnection(chokeRuins, 15);
        chokePass.addConnection(chokeHeart, 20);
        chokePass.addConnection(chokeBog, 20);
        chokePass.addConnection(zenithSteps, 30);
        
        // ZENITH connections
        zenithSteps.addConnection(zenithMonastery, 40);
        zenithMonastery.addConnection(zenithGraveyard, 20);
        zenithMonastery.addConnection(zenithPinnacle, 50);
        
        // ==================== Directional Connections (for UI) ====================
        // CORE
        addDirectionalConnection(coreGate, coreMarket, "east");
        addDirectionalConnection(coreMarket, coreGate, "west");
        addDirectionalConnection(coreMarket, coreAcademy, "north");
        addDirectionalConnection(coreAcademy, coreMarket, "south");
        addDirectionalConnection(coreAcademy, coreKeep, "up");
        addDirectionalConnection(coreKeep, coreAcademy, "down");
        addDirectionalConnection(coreMarket, coreEstates, "west");
        addDirectionalConnection(coreEstates, coreMarket, "east");
        addDirectionalConnection(coreGate, corePrison, "south");
        addDirectionalConnection(corePrison, coreGate, "north");
        
        // Region transitions
        addDirectionalConnection(coreGate, sumpElevator, "down");
        addDirectionalConnection(sumpElevator, coreGate, "up");
        addDirectionalConnection(coreGate, chokeBorder, "east");
        addDirectionalConnection(chokeBorder, coreGate, "west");
        
        // SUMP
        addDirectionalConnection(sumpElevator, sumpRatways, "down");
        addDirectionalConnection(sumpRatways, sumpElevator, "up");
        addDirectionalConnection(sumpRatways, sumpMarket, "east");
        addDirectionalConnection(sumpMarket, sumpRatways, "west");
        addDirectionalConnection(sumpRatways, sumpArena, "south");
        addDirectionalConnection(sumpArena, sumpRatways, "north");
        addDirectionalConnection(sumpMarket, sumpPumps, "down");
        addDirectionalConnection(sumpPumps, sumpMarket, "up");
        addDirectionalConnection(sumpPumps, sumpDepths, "down");
        addDirectionalConnection(sumpDepths, sumpPumps, "up");
        addDirectionalConnection(sumpRatways, chokeBorder, "north");
        addDirectionalConnection(chokeBorder, sumpRatways, "south");
        
        // CHOKE
        addDirectionalConnection(chokeBorder, chokeWoods, "north");
        addDirectionalConnection(chokeWoods, chokeBorder, "south");
        addDirectionalConnection(chokeWoods, chokePass, "north");
        addDirectionalConnection(chokePass, chokeWoods, "south");
        addDirectionalConnection(chokeWoods, chokeRuins, "east");
        addDirectionalConnection(chokeRuins, chokeWoods, "west");
        addDirectionalConnection(chokePass, chokeHeart, "west");
        addDirectionalConnection(chokeHeart, chokePass, "east");
        addDirectionalConnection(chokePass, chokeBog, "east");
        addDirectionalConnection(chokeBog, chokePass, "west");
        addDirectionalConnection(chokePass, zenithSteps, "north");
        addDirectionalConnection(zenithSteps, chokePass, "south");
        
        // ZENITH
        addDirectionalConnection(zenithSteps, zenithMonastery, "up");
        addDirectionalConnection(zenithMonastery, zenithSteps, "down");
        addDirectionalConnection(zenithMonastery, zenithGraveyard, "west");
        addDirectionalConnection(zenithGraveyard, zenithMonastery, "east");
        addDirectionalConnection(zenithMonastery, zenithPinnacle, "up");
        addDirectionalConnection(zenithPinnacle, zenithMonastery, "down");
        
        // Set starting location
        setStartingLocation("coreGate");
        
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
     * Get stamina cost between two connected locations
     */
    public int getStaminaCost(Location from, Location to) {
        if (from == null || to == null) return Integer.MAX_VALUE;
        Integer cost = from.getConnectedNodes().get(to);
        return cost != null ? cost : Integer.MAX_VALUE;
    }
    
    /**
     * Add a directional connection for UI purposes (north, south, etc.)
     */
    public void addDirectionalConnection(Location from, Location to, String direction) {
        if (from != null && to != null && direction != null) {
            from.getConnectedLocations().put(direction.toLowerCase(), to.getId());
        }
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

        int travelCost = getStaminaCost(currentLocation, destination);
        if (travelCost == Integer.MAX_VALUE) {
            System.out.printf("[ERROR] %s is not connected to %s!%n", 
                currentLocation.getName(), destination.getName());
            return false;
        }
        
        System.out.printf("%n=== TRAVELING ===%n");
        System.out.printf("From: %s [%s]%n", currentLocation.getName(), 
            currentLocation.getRegionType() != null ? currentLocation.getRegionType().getDisplayName() : "Unknown");
        System.out.printf("To:   %s [%s]%n", destination.getName(),
            destination.getRegionType() != null ? destination.getRegionType().getDisplayName() : "Unknown");
        System.out.printf("Travel cost: %d stamina%n", travelCost);

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
                int cost = getStaminaCost(currentLocation, loc);
                return cost != Integer.MAX_VALUE ? cost : 5;
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
