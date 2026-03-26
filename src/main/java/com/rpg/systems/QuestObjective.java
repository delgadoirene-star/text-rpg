package com.rpg.systems;

/**
 * A single objective within a quest
 */
public class QuestObjective {
    private String description;
    
    // Objective type and details
    private ObjectiveType type;
    private String targetId; // e.g., enemy ID, item ID, location ID, flag ID
    private int requiredCount;
    
    // State
    private int currentCount;
    private boolean completed;
    
    public enum ObjectiveType {
        KILL_ENEMY,
        GATHER_ITEM,
        REACH_LOCATION,
        TALK_TO_NPC,
        USE_ABILITY,
        CHECK_FLAG
    }
    
    public QuestObjective(String description, ObjectiveType type, String targetId, int count) {
        this.description = description;
        this.type = type;
        this.targetId = targetId;
        this.requiredCount = count;
        
        this.currentCount = 0;
        this.completed = false;
    }
    
    // ==================== Getters ====================
    
    public String getDescription() { return description; }
    public ObjectiveType getType() { return type; }
    public String getTargetId() { return targetId; }
    public int getRequiredCount() { return requiredCount; }
    public int getCurrentCount() { return currentCount; }
    public boolean isComplete() { return completed; }
    
    // ==================== Progression ====================
    
    /**
     * Start the objective
     */
    public void start() {
        System.out.println("  >>> New Objective: " + description);
    }
    
    /**
     * Increment the objective's progress
     */
    public void incrementProgress(int amount) {
        if (completed) return;
        
        currentCount += amount;
        if (currentCount >= requiredCount) {
            currentCount = requiredCount;
            completed = true;
        }
    }
    
    /**
     * Update the objective's status based on game state
     * (Especially for flag-based objectives)
     */
    public void update(FlagManager flagManager) {
        if (completed) return;
        
        if (type == ObjectiveType.CHECK_FLAG) {
            if (flagManager.hasFlag(targetId)) {
                incrementProgress(requiredCount);
            }
        }
    }
    
    /**
     * Get a string representing the objective's progress
     */
    public String getProgressString() {
        if (type == ObjectiveType.CHECK_FLAG || type == ObjectiveType.REACH_LOCATION || 
            type == ObjectiveType.TALK_TO_NPC) {
            return completed ? "Done" : "Pending";
        }
        
        return String.format("%d / %d", currentCount, requiredCount);
    }
}
