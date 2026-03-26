package com.rpg.systems;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quest with objectives and rewards
 */
public class Quest {
    private String id;
    private String name;
    private String description;
    
    // Quest structure
    private List<QuestObjective> objectives;
    private int currentObjectiveIndex;
    
    // Quest state
    private QuestStatus status;
    
    // Requirements & Rewards
    private String requiredQuest; // Quest that must be completed first
    private int requiredLevel;
    private List<String> requiredFlags;
    
    private int rewardExperience;
    private int rewardGold;
    private List<String> rewardItemIds;
    private List<String> rewardFlags; // Flags to set upon completion
    
    public enum QuestStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }
    
    public Quest(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        
        this.objectives = new ArrayList<>();
        this.currentObjectiveIndex = 0;
        this.status = QuestStatus.NOT_STARTED;
        
        this.requiredQuest = null;
        this.requiredLevel = 1;
        this.requiredFlags = new ArrayList<>();
        
        this.rewardExperience = 0;
        this.rewardGold = 0;
        this.rewardItemIds = new ArrayList<>();
        this.rewardFlags = new ArrayList<>();
    }
    
    // ==================== Builder Pattern ====================
    
    public static class Builder {
        private Quest quest;
        
        public Builder(String id, String name, String description) {
            quest = new Quest(id, name, description);
        }
        
        public Builder addObjective(QuestObjective objective) {
            quest.objectives.add(objective);
            return this;
        }
        
        public Builder requiredQuest(String questId) {
            quest.requiredQuest = questId;
            return this;
        }
        
        public Builder requiredLevel(int level) {
            quest.requiredLevel = level;
            return this;
        }
        
        public Builder requiredFlag(String flag) {
            quest.requiredFlags.add(flag);
            return this;
        }
        
        public Builder rewardExperience(int exp) {
            quest.rewardExperience = exp;
            return this;
        }
        
        public Builder rewardGold(int gold) {
            quest.rewardGold = gold;
            return this;
        }
        
        public Builder rewardItem(String itemId) {
            quest.rewardItemIds.add(itemId);
            return this;
        }
        
        public Builder rewardFlag(String flag) {
            quest.rewardFlags.add(flag);
            return this;
        }
        
        public Quest build() {
            return quest;
        }
    }
    
    // ==================== Getters ====================
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<QuestObjective> getObjectives() { return new ArrayList<>(objectives); }
    public QuestStatus getStatus() { return status; }
    
    // Requirement getters
    public String getRequiredQuest() { return requiredQuest; }
    public int getRequiredLevel() { return requiredLevel; }
    public List<String> getRequiredFlags() { return new ArrayList<>(requiredFlags); }
    
    // Reward getters
    public int getRewardExperience() { return rewardExperience; }
    public int getRewardGold() { return rewardGold; }
    public List<String> getRewardItemIds() { return new ArrayList<>(rewardItemIds); }
    public List<String> getRewardFlags() { return new ArrayList<>(rewardFlags); }
    
    // ==================== Quest Progression ====================
    
    /**
     * Start the quest
     */
    public void start() {
        if (status == QuestStatus.NOT_STARTED) {
            status = QuestStatus.IN_PROGRESS;
            currentObjectiveIndex = 0;
            if (!objectives.isEmpty()) {
                objectives.get(0).start();
            }
            System.out.println(">>> Quest Started: " + name);
        }
    }
    
    /**
     * Get the current active objective
     */
    public QuestObjective getCurrentObjective() {
        if (status != QuestStatus.IN_PROGRESS || objectives.isEmpty() ||
            currentObjectiveIndex >= objectives.size()) {
            return null;
        }
        return objectives.get(currentObjectiveIndex);
    }
    
    /**
     * Update the quest's progress based on game events
     */
    public void update(FlagManager flagManager) {
        QuestObjective current = getCurrentObjective();
        if (current == null) return;
        
        // Update the current objective
        current.update(flagManager);
        
        // If current objective is complete, move to the next one
        if (current.isComplete()) {
            advanceToNextObjective();
        }
    }
    
    /**
     * Advance to the next objective, or complete the quest if all are done
     */
    private void advanceToNextObjective() {
        System.out.println("  >>> Objective Complete: " + getCurrentObjective().getDescription());
        
        currentObjectiveIndex++;
        
        if (currentObjectiveIndex >= objectives.size()) {
            // All objectives finished, complete the quest
            complete();
        } else {
            // Start the next objective
            getCurrentObjective().start();
        }
    }
    
    /**
     * Complete the quest
     */
    public void complete() {
        if (status == QuestStatus.IN_PROGRESS) {
            status = QuestStatus.COMPLETED;
            System.out.println(">>> Quest Completed: " + name);
        }
    }
    
    /**
     * Fail the quest
     */
    public void fail() {
        if (status == QuestStatus.IN_PROGRESS) {
            status = QuestStatus.FAILED;
            System.out.println(">>> Quest Failed: " + name);
        }
    }
    
    // ==================== Display ====================
    
    @Override
    public String toString() {
        return String.format("[%s] %s", status, name);
    }
    
    /**
     * Get a detailed quest log entry
     */
    public String getQuestLogEntry() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- %s ---\n", name));
        sb.append(description).append("\n\n");
        sb.append("Status: ").append(status).append("\n");
        
        if (status == QuestStatus.IN_PROGRESS) {
            QuestObjective current = getCurrentObjective();
            if (current != null) {
                sb.append("\nCurrent Objective:\n");
                sb.append("  - ").append(current.getDescription()).append("\n");
                sb.append("    (").append(current.getProgressString()).append(")\n");
            }
        } else if (status == QuestStatus.COMPLETED) {
            sb.append("\nThis quest has been completed.\n");
        }
        
        return sb.toString();
    }
}
