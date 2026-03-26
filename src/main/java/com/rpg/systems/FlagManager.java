package com.rpg.systems;

import java.util.*;

/**
 * Flag-based choice tracking system
 * Every meaningful choice sets flags that affect future story, recruitment, classes, etc.
 */
public class FlagManager {
    // All story flags (flag_id → true/false)
    private Map<String, Boolean> flags;
    
    // Numeric counters for tracking quantities (e.g., "enemies_killed", "gold_spent")
    private Map<String, Integer> counters;
    
    // Timed flags that expire after certain conditions
    private Map<String, TimedFlag> timedFlags;
    
    // Flag history for debugging/save file inspection
    private List<FlagEvent> history;
    
    // Mutual exclusivity groups (setting one flag clears others in same group)
    private Map<String, Set<String>> exclusiveGroups;
    
    public FlagManager() {
        this.flags = new HashMap<>();
        this.counters = new HashMap<>();
        this.timedFlags = new HashMap<>();
        this.history = new ArrayList<>();
        this.exclusiveGroups = new HashMap<>();
        
        initializeExclusiveGroups();
    }
    
    /**
     * Initialize mutually exclusive flag groups
     * (e.g., can't both save and kill a character)
     */
    private void initializeExclusiveGroups() {
        // Example exclusive groups
        addExclusiveGroup("faction_choice", Arrays.asList(
            "joined_empire", "joined_rebellion", "stayed_neutral"
        ));
        
        addExclusiveGroup("moral_alignment", Arrays.asList(
            "honorable_path", "ruthless_path", "pragmatic_path"
        ));
    }
    
    // ==================== Flag Operations ====================
    
    /**
     * Set a flag to true
     */
    public void setFlag(String flagId) {
        setFlag(flagId, true, "Manual set");
    }
    
    /**
     * Set a flag with description
     */
    public void setFlag(String flagId, String description) {
        setFlag(flagId, true, description);
    }
    
    /**
     * Set a flag to a specific value
     */
    public void setFlag(String flagId, boolean value, String description) {
        // Check for exclusive groups
        checkExclusiveGroups(flagId);
        
        boolean oldValue = flags.getOrDefault(flagId, false);
        flags.put(flagId, value);
        
        // Record in history
        history.add(new FlagEvent(flagId, value, description));
        
        // Trigger any dependent logic
        onFlagChanged(flagId, oldValue, value);
    }
    
    /**
     * Clear/unset a flag
     */
    public void clearFlag(String flagId) {
        setFlag(flagId, false, "Cleared");
    }
    
    /**
     * Check if a flag is set
     */
    public boolean hasFlag(String flagId) {
        return flags.getOrDefault(flagId, false);
    }
    
    /**
     * Check if ALL flags in a list are set
     */
    public boolean hasAllFlags(String... flagIds) {
        for (String flagId : flagIds) {
            if (!hasFlag(flagId)) return false;
        }
        return true;
    }
    
    /**
     * Check if ANY flag in a list is set
     */
    public boolean hasAnyFlag(String... flagIds) {
        for (String flagId : flagIds) {
            if (hasFlag(flagId)) return true;
        }
        return false;
    }
    
    /**
     * Check if a flag is NOT set
     */
    public boolean lacksFlag(String flagId) {
        return !hasFlag(flagId);
    }
    
    /**
     * Toggle a flag
     */
    public void toggleFlag(String flagId) {
        setFlag(flagId, !hasFlag(flagId), "Toggled");
    }
    
    // ==================== Counter Operations ====================
    
    /**
     * Increment a counter
     */
    public void incrementCounter(String counterId) {
        incrementCounter(counterId, 1);
    }
    
    /**
     * Increment a counter by amount
     */
    public void incrementCounter(String counterId, int amount) {
        int current = counters.getOrDefault(counterId, 0);
        counters.put(counterId, current + amount);
    }
    
    /**
     * Decrement a counter
     */
    public void decrementCounter(String counterId) {
        incrementCounter(counterId, -1);
    }
    
    /**
     * Set a counter to a specific value
     */
    public void setCounter(String counterId, int value) {
        counters.put(counterId, value);
    }
    
    /**
     * Get counter value
     */
    public int getCounter(String counterId) {
        return counters.getOrDefault(counterId, 0);
    }
    
    /**
     * Check if counter meets threshold
     */
    public boolean counterAtLeast(String counterId, int threshold) {
        return getCounter(counterId) >= threshold;
    }
    
    /**
     * Check if counter is below threshold
     */
    public boolean counterBelow(String counterId, int threshold) {
        return getCounter(counterId) < threshold;
    }
    
    // ==================== Timed Flags ====================
    
    /**
     * Set a flag that expires after a certain number of turns/events
     */
    public void setTimedFlag(String flagId, int duration, String description) {
        flags.put(flagId, true);
        timedFlags.put(flagId, new TimedFlag(flagId, duration, description));
        history.add(new FlagEvent(flagId, true, description + " (timed: " + duration + ")"));
    }
    
    /**
     * Tick all timed flags (call this after each turn or time event)
     */
    public void tickTimedFlags() {
        List<String> expired = new ArrayList<>();
        
        for (Map.Entry<String, TimedFlag> entry : timedFlags.entrySet()) {
            TimedFlag timed = entry.getValue();
            timed.tick();
            
            if (timed.isExpired()) {
                expired.add(entry.getKey());
            }
        }
        
        // Remove expired flags
        for (String flagId : expired) {
            clearFlag(flagId);
            timedFlags.remove(flagId);
            System.out.println(">>> Timed flag expired: " + flagId);
        }
    }
    
    // ==================== Exclusive Groups ====================
    
    /**
     * Add a mutually exclusive group
     */
    public void addExclusiveGroup(String groupName, List<String> flagIds) {
        Set<String> group = new HashSet<>(flagIds);
        for (String flagId : flagIds) {
            exclusiveGroups.put(flagId, group);
        }
    }
    
    /**
     * Check and clear mutually exclusive flags
     */
    private void checkExclusiveGroups(String flagId) {
        Set<String> group = exclusiveGroups.get(flagId);
        if (group != null) {
            for (String otherFlag : group) {
                if (!otherFlag.equals(flagId)) {
                    flags.put(otherFlag, false);
                }
            }
        }
    }
    
    // ==================== Events & Callbacks ====================
    
    /**
     * Called when a flag changes (override for custom logic)
     */
    protected void onFlagChanged(String flagId, boolean oldValue, boolean newValue) {
        // Can be overridden or use listeners
        // Example: if ("saved_princess".equals(flagId) && newValue) { ... }
    }
    
    // ==================== Querying ====================
    
    /**
     * Get all currently set flags
     */
    public Set<String> getAllActiveFlags() {
        Set<String> active = new HashSet<>();
        for (Map.Entry<String, Boolean> entry : flags.entrySet()) {
            if (entry.getValue()) {
                active.add(entry.getKey());
            }
        }
        return active;
    }
    
    /**
     * Get all flags (including inactive)
     */
    public Map<String, Boolean> getAllFlags() {
        return new HashMap<>(flags);
    }
    
    /**
     * Get all counters
     */
    public Map<String, Integer> getAllCounters() {
        return new HashMap<>(counters);
    }
    
    /**
     * Get flag history
     */
    public List<FlagEvent> getHistory() {
        return new ArrayList<>(history);
    }
    
    /**
     * Search history for specific flag
     */
    public List<FlagEvent> getHistoryForFlag(String flagId) {
        List<FlagEvent> result = new ArrayList<>();
        for (FlagEvent event : history) {
            if (event.getFlagId().equals(flagId)) {
                result.add(event);
            }
        }
        return result;
    }
    
    // ==================== Save/Load Support ====================
    
    /**
     * Export flags to a saveable format
     */
    public Map<String, Object> exportData() {
        Map<String, Object> data = new HashMap<>();
        data.put("flags", new HashMap<>(flags));
        data.put("counters", new HashMap<>(counters));
        
        // Export timed flags with remaining duration
        Map<String, Integer> timedData = new HashMap<>();
        for (Map.Entry<String, TimedFlag> entry : timedFlags.entrySet()) {
            timedData.put(entry.getKey(), entry.getValue().getRemainingDuration());
        }
        data.put("timed_flags", timedData);
        
        return data;
    }
    
    /**
     * Import flags from saved data
     */
    @SuppressWarnings("unchecked")
    public void importData(Map<String, Object> data) {
        if (data.containsKey("flags")) {
            Map<String, Boolean> savedFlags = (Map<String, Boolean>) data.get("flags");
            flags.putAll(savedFlags);
        }
        
        if (data.containsKey("counters")) {
            Map<String, Integer> savedCounters = (Map<String, Integer>) data.get("counters");
            counters.putAll(savedCounters);
        }
        
        if (data.containsKey("timed_flags")) {
            Map<String, Integer> timedData = (Map<String, Integer>) data.get("timed_flags");
            for (Map.Entry<String, Integer> entry : timedData.entrySet()) {
                timedFlags.put(entry.getKey(), 
                    new TimedFlag(entry.getKey(), entry.getValue(), "Loaded from save"));
            }
        }
    }
    
    // ==================== Debug ====================
    
    /**
     * Print all active flags (for debugging)
     */
    public void debugPrintFlags() {
        System.out.println("=== ACTIVE FLAGS ===");
        for (String flag : getAllActiveFlags()) {
            System.out.println("  " + flag);
        }
        
        System.out.println("\n=== COUNTERS ===");
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        if (!timedFlags.isEmpty()) {
            System.out.println("\n=== TIMED FLAGS ===");
            for (Map.Entry<String, TimedFlag> entry : timedFlags.entrySet()) {
                TimedFlag timed = entry.getValue();
                System.out.println("  " + entry.getKey() + ": " + 
                    timed.getRemainingDuration() + " turns remaining");
            }
        }
    }
    
    // ==================== Inner Classes ====================
    
    /**
     * Timed flag that expires after duration
     */
    private static class TimedFlag {
        private String flagId;
        private int duration;
        private int remaining;
        private String description;
        
        public TimedFlag(String flagId, int duration, String description) {
            this.flagId = flagId;
            this.duration = duration;
            this.remaining = duration;
            this.description = description;
        }
        
        public void tick() {
            remaining--;
        }
        
        public boolean isExpired() {
            return remaining <= 0;
        }
        
        public int getRemainingDuration() {
            return remaining;
        }
    }
    
    /**
     * Event record for flag history
     */
    public static class FlagEvent {
        private String flagId;
        private boolean value;
        private String description;
        private long timestamp;
        
        public FlagEvent(String flagId, boolean value, String description) {
            this.flagId = flagId;
            this.value = value;
            this.description = description;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getFlagId() { return flagId; }
        public boolean getValue() { return value; }
        public String getDescription() { return description; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("[%d] %s = %b (%s)", 
                timestamp, flagId, value, description);
        }
    }
}
