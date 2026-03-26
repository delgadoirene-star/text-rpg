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
        // Create locations
        Location town = new Location.Builder("town_square", "Town Square",
            "The bustling center of the capital city. A large fountain sits in the middle.")
            .connect("north", "castle_gate")
            .connect("south", "market_district")
            .connect("west", "residential_area")
            .connect("east", "guild_hall")
            .build();
        
        Location castleGate = new Location.Builder("castle_gate", "Castle Gate",
            "Two enormous guards stand before the majestic gate of the royal castle.")
            .connect("south", "town_square")
            .requireFlag("royal_invitation") // Can't enter without this flag
            .build();
            
        Location market = new Location.Builder("market_district", "Market District",
            "Merchants hawk their wares, from fresh food to shiny trinkets.")
            .connect("north", "town_square")
            .addCompanion("companion_merchant") // A potential companion is here
            .addQuest("quest_lost_goods")
            .build();
            
        Location guildHall = new Location.Builder("guild_hall", "Adventurer's Guild",
            "A large hall filled with adventurers seeking fame and fortune.")
            .connect("west", "town_square")
            .addQuest("quest_goblin_slaying")
            .build();
            
        Location residential = new Location.Builder("residential_area", "Residential Area",
            "Quiet streets lined with houses.")
            .connect("east", "town_square")
            .build();
            
        // Add locations to map
        addLocation(town);
        addLocation(castleGate);
        addLocation(market);
        addLocation(guildHall);
        addLocation(residential);
        
        // Set starting location
        setStartingLocation("town_square");
        
        // Mark starting location as discovered
        getStartingLocation().setDiscovered(true);
    }
}
