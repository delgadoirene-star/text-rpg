package com.rpg.world;

import com.rpg.models.Companion;
import com.rpg.models.Enemy;
import com.rpg.systems.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a specific location in the game world
 */
public class Location {
    private String id;
    private String name;
    private String description;
    private RegionType regionType;  // Cultural region this location belongs to
    
    // Connectivity
    private Map<String, String> connectedLocations; // Direction -> Location ID
    
    // Content
    private List<String> availableQuests; // Quest IDs
    private List<String> companionsPresent; // Companion IDs
    private List<String> enemiesPresent; // Enemy IDs for random encounters
    private List<GameEvent> events; // Scripted events
    
    // Requirements
    private List<String> requiredFlags; // Flags needed to enter
    
    // State
    private boolean discovered;
    private boolean visited;
    
    public Location(String id, String name, String description) {
        this(id, name, description, null);
    }
    
    public Location(String id, String name, String description, RegionType regionType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regionType = regionType;
        
        this.connectedLocations = new HashMap<>();
        this.availableQuests = new ArrayList<>();
        this.companionsPresent = new ArrayList<>();
        this.enemiesPresent = new ArrayList<>();
        this.events = new ArrayList<>();
        this.requiredFlags = new ArrayList<>();
        
        this.discovered = false;
        this.visited = false;
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private Location location;
        
        public Builder(String id, String name, String description) {
            location = new Location(id, name, description);
        }
        
        public Builder(String id, String name, String description, RegionType regionType) {
            location = new Location(id, name, description, regionType);
        }
        
        public Builder region(RegionType regionType) {
            location.regionType = regionType;
            return this;
        }
        
        public Builder connect(String direction, String locationId) {
            location.connectedLocations.put(direction.toLowerCase(), locationId);
            return this;
        }
        
        public Builder addQuest(String questId) {
            location.availableQuests.add(questId);
            return this;
        }
        
        public Builder addCompanion(String companionId) {
            location.companionsPresent.add(companionId);
            return this;
        }
        
        public Builder addEnemy(String enemyId) {
            location.enemiesPresent.add(enemyId);
            return this;
        }
        
        public Builder addEvent(GameEvent event) {
            location.events.add(event);
            return this;
        }
        
        public Builder requireFlag(String flag) {
            location.requiredFlags.add(flag);
            return this;
        }
        
        public Location build() {
            return location;
        }
    }
    
    // ==================== Getters ====================
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Map<String, String> getConnectedLocations() { return new HashMap<>(connectedLocations); }
    public List<String> getAvailableQuests() { return new ArrayList<>(availableQuests); }
    public List<String> getCompanionsPresent() { return new ArrayList<>(companionsPresent); }
    public List<String> getEnemiesPresent() { return new ArrayList<>(enemiesPresent); }
    public List<GameEvent> getEvents() { return new ArrayList<>(events); }
    public List<String> getRequiredFlags() { return new ArrayList<>(requiredFlags); }
    public RegionType getRegionType() { return regionType; }
    
    // ==================== State Management ====================
    
    public boolean isDiscovered() { return discovered; }
    public void setDiscovered(boolean discovered) { this.discovered = discovered; }
    
    public boolean hasVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }
    
    /**
     * Mark location as visited for the first time
     */
    public void onFirstVisit() {
        if (!visited) {
            setVisited(true);
            // Trigger any "first visit" events
        }
    }
    
    /**
     * Check if a player can enter this location
     * @param flags The player's current flags from FlagManager
     */
    public boolean canEnter(Map<String, Boolean> flags) {
        if (requiredFlags.isEmpty()) return true;
        
        for (String flag : requiredFlags) {
            if (!flags.getOrDefault(flag, false)) {
                return false; // Missing a required flag
            }
        }
        
        return true;
    }
    
    // ==================== Regional Reputation ====================
    
    private boolean shopAvailable = true;
    private boolean innAvailable = true;
    private boolean healingAvailable = true;
    
    /**
     * Called when player enters this location.
     * Checks if player is Sullied in this region, triggering hostile events.
     * 
     * @param reputation The reputation system to check
     */
    public void onEnter(ReputationSystem reputation) {
        visited = true;
        discovered = true;
        
        if (reputation != null && reputation.isSullied(regionType)) {
            handleSulliedEntry();
        } else {
            handleNormalEntry();
        }
    }
    
    /**
     * Handle entry when player is NOT sullied in this region
     */
    private void handleNormalEntry() {
        System.out.printf("%n=== ENTERING: %s [%s] ===%n", name, 
            regionType != null ? regionType.getDisplayName() : "Unknown Region");
        System.out.println(description);
        
        if (!shopAvailable) {
            System.out.println("[WARNING] The local shop is closed.");
        }
        if (!innAvailable) {
            System.out.println("[WARNING] No inn is available here.");
        }
        if (!healingAvailable) {
            System.out.println("[WARNING] Healing is not available.");
        }
    }
    
    /**
     * Handle entry when player IS sullied in this region
     * Triggers hostile events, potential combat, locked services
     */
    private void handleSulliedEntry() {
        System.out.printf("%n=== WARNING: ENTERING SULLIED REGION ===%n");
        System.out.printf("%s [%s]%n", name, 
            regionType != null ? regionType.getDisplayName() : "Unknown Region");
        System.out.println("Your reputation precedes you...");
        
        System.out.println("\n[HOSTILE] Locals eye you with suspicion and hostility!");
        
        if (shopAvailable) {
            shopAvailable = false;
            System.out.println("[LOCKED] The shopkeeper slams the door in your face!");
        }
        
        if (innAvailable) {
            innAvailable = false;
            System.out.println("[LOCKED] The inn refuses to shelter you!");
        }
        
        if (healingAvailable) {
            healingAvailable = false;
            System.out.println("[LOCKED] The healer turns away, citing 'too many enemies'.");
        }
        
        System.out.println("\n[ALERT] Random encounters are now more likely to be hostile!");
        System.out.println("[ALERT] Consider making amends or leaving this region.");
    }
    
    /**
     * Attempt to restore services after being sullied (e.g., after quest completion)
     */
    public void restoreServices() {
        shopAvailable = true;
        innAvailable = true;
        healingAvailable = true;
        System.out.println("[SUCCESS] Services have been restored in " + name + "!");
    }
    
    public boolean isShopAvailable() {
        return shopAvailable;
    }
    
    public boolean isInnAvailable() {
        return innAvailable;
    }
    
    public boolean isHealingAvailable() {
        return healingAvailable;
    }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        return String.format("%s (ID: %s)", name, id);
    }
    
    /**
     * Get a detailed description of the location
     */
    public String look() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- %s ---\n", name));
        sb.append(description).append("\n\n");
        
        // Show connections
        if (!connectedLocations.isEmpty()) {
            sb.append("Paths lead:\n");
            for (Map.Entry<String, String> entry : connectedLocations.entrySet()) {
                sb.append(String.format("  - To the %s\n", entry.getKey()));
            }
        }
        
        return sb.toString();
    }
}
