package com.rpg.world;

import com.rpg.systems.FlagManager;
import com.rpg.models.Player;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a scripted event that occurs at a location
 */
public class GameEvent {
    private String id;
    private String description;
    
    // Trigger conditions
    private EventTrigger trigger;
    private String triggerFlag; // Optional flag to trigger event
    
    // Event outcome
    private Consumer<Player> action; // The action to perform (e.g., give item, start combat)
    
    // State
    private boolean hasFired;
    private boolean repeatable;
    
    // ==================== Event Trigger Enum ====================
    
    public enum EventTrigger {
        ON_ENTER,      // Fires when player enters location
        ON_INTERACT,   // Fires when player interacts with object in location
        ON_FLAG_SET,   // Fires when a specific flag is set
        ON_QUEST_START,// Fires when a quest is started
        ON_QUEST_END   // Fires when a quest is completed
    }
    
    // ==================== Constructor ====================
    
    public GameEvent(String id, String description, EventTrigger trigger, Consumer<Player> action) {
        this.id = id;
        this.description = description;
        this.trigger = trigger;
        this.action = action;
        
        this.hasFired = false;
        this.repeatable = false;
        this.triggerFlag = null;
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private GameEvent event;
        
        public Builder(String id, String description, EventTrigger trigger, Consumer<Player> action) {
            event = new GameEvent(id, description, trigger, action);
        }
        
        public Builder repeatable() {
            event.repeatable = true;
            return this;
        }
        
        public Builder withTriggerFlag(String flag) {
            event.triggerFlag = flag;
            return this;
        }
        
        public GameEvent build() {
            return event;
        }
    }
    
    // ==================== Getters ====================
    
    public String getId() { return id; }
    public String getDescription() { return description; }
    public EventTrigger getTrigger() { return trigger; }
    public String getTriggerFlag() { return triggerFlag; }
    public boolean hasFired() { return hasFired; }
    public boolean isRepeatable() { return repeatable; }
    
    // ==================== Event Execution ====================
    
    /**
     * Check if this event should fire
     * @param currentTrigger The trigger that just occurred (e.g., ON_ENTER)
     * @param flagManager The game's flag manager
     * @return True if the event should fire, false otherwise
     */
    public boolean shouldFire(EventTrigger currentTrigger, FlagManager flagManager) {
        // Don't fire if it's a one-time event that already fired
        if (hasFired && !repeatable) {
            return false;
        }
        
        // Check if trigger type matches
        if (trigger != currentTrigger) {
            return false;
        }
        
        // Check for specific flag requirement
        if (triggerFlag != null && !flagManager.hasFlag(triggerFlag)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Execute the event's action
     * @param player The player character
     */
    public void fire(Player player) {
        if (action != null) {
            System.out.println(">>> EVENT: " + description);
            action.accept(player);
            hasFired = true;
        }
    }
}
