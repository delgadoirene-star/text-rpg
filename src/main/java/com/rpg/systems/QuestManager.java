package com.rpg.systems;

import com.rpg.models.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all quests and the player's quest log
 */
public class QuestManager {
    private Map<String, Quest> allQuests; // All quests in the game
    private Map<String, Quest> activeQuests; // Player's current quests
    private List<String> completedQuests; // IDs of completed quests
    
    public QuestManager() {
        this.allQuests = new HashMap<>();
        this.activeQuests = new HashMap<>();
        this.completedQuests = new ArrayList<>();
    }
    
    /**
     * Load all quests into the game
     * (This would typically be loaded from data files)
     */
    public void initializeQuests() {
        // Example Quest 1: Goblin Slaying
        Quest goblinQuest = new Quest.Builder("quest_goblin_slaying", 
            "Goblin Troubles", "The Adventurer's Guild has posted a bounty for goblins.")
            .requiredLevel(2)
            .addObjective(new QuestObjective("Defeat 5 Goblins", 
                QuestObjective.ObjectiveType.KILL_ENEMY, "goblin_warrior", 5))
            .rewardExperience(200)
            .rewardGold(50)
            .rewardItem("item_health_potion")
            .build();
            
        // Example Quest 2: Lost Goods
        Quest lostGoods = new Quest.Builder("quest_lost_goods", 
            "Lost Merchant Goods", "A merchant in the market district lost a crate of goods.")
            .addObjective(new QuestObjective("Find the lost crate", 
                QuestObjective.ObjectiveType.REACH_LOCATION, "location_crate", 1))
            .addObjective(new QuestObjective("Return the crate to the merchant",
                QuestObjective.ObjectiveType.TALK_TO_NPC, "npc_merchant", 1))
            .rewardExperience(150)
            .rewardGold(100)
            .rewardFlag("helped_merchant")
            .build();
            
        allQuests.put(goblinQuest.getId(), goblinQuest);
        allQuests.put(lostGoods.getId(), lostGoods);
    }
    
    /**
     * Start a quest
     */
    public boolean startQuest(String questId) {
        Quest quest = allQuests.get(questId);
        
        if (quest == null || activeQuests.containsKey(questId) || 
            completedQuests.contains(questId)) {
            return false;
        }
        
        quest.start();
        activeQuests.put(questId, quest);
        
        return true;
    }
    
    /**
     * Update all active quests based on a game event
     * @param type The type of event that occurred
     * @param targetId The ID of the thing affected (e.g., enemy ID, item ID)
     */
    public void updateQuestsOnEvent(QuestObjective.ObjectiveType type, String targetId) {
        for (Quest quest : activeQuests.values()) {
            QuestObjective current = quest.getCurrentObjective();
            if (current != null && current.getType() == type && 
                current.getTargetId().equals(targetId)) {
                
                current.incrementProgress(1);
                
                // If objective is now complete, advance the quest
                if (current.isComplete()) {
                    quest.update(null); // Pass null flag manager, not ideal
                }
            }
        }
        
        // Check for newly completed quests
        checkCompletedQuests();
    }
    
    /**
     * Update quests based on FlagManager state
     */
    public void updateQuestsOnFlags(FlagManager flagManager) {
        for (Quest quest : activeQuests.values()) {
            quest.update(flagManager);
        }
        
        checkCompletedQuests();
    }
    
    /**
     * Check for completed quests and move them to the completed list
     */
    private void checkCompletedQuests() {
        List<String> toComplete = new ArrayList<>();
        for (Quest quest : activeQuests.values()) {
            if (quest.getStatus() == Quest.QuestStatus.COMPLETED) {
                toComplete.add(quest.getId());
            }
        }
        
        for (String questId : toComplete) {
            completeQuest(questId);
        }
    }
    
    /**
     * Complete a quest and give rewards
     */
    private void completeQuest(String questId) {
        Quest quest = activeQuests.remove(questId);
        if (quest == null) return;
        
        completedQuests.add(questId);
        
        // TODO: Give rewards to player
        // Player player = ...;
        // player.gainExperience(quest.getRewardExperience());
        // player.addGold(quest.getRewardGold());
        // for (String itemId : quest.getRewardItemIds()) { player.addItem(itemId); }
        // for (String flag : quest.getRewardFlags()) { flagManager.setFlag(flag); }
        
        System.out.println(">>> Rewards for " + quest.getName() + ":");
        System.out.println("  - " + quest.getRewardExperience() + " EXP");
        System.out.println("  - " + quest.getRewardGold() + " Gold");
    }
    
    /**
     * Get all active quests
     */
    public Map<String, Quest> getActiveQuests() {
        return new HashMap<>(activeQuests);
    }
    
    /**
     * Get all completed quest IDs
     */
    public List<String> getCompletedQuests() {
        return new ArrayList<>(completedQuests);
    }
    
    /**
     * Check if a quest is completed
     */
    public boolean isQuestCompleted(String questId) {
        return completedQuests.contains(questId);
    }
    
    /**
     * Get a specific quest by ID
     */
    public Quest getQuest(String questId) {
        return allQuests.get(questId);
    }
    
    /**
     * Get the player's quest log
     */
    public String getQuestLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append("║                           QUEST LOG                           ║\n");
        sb.append("╚═══════════════════════════════════════════════════════════════╝\n\n");
        
        if (activeQuests.isEmpty()) {
            sb.append("No active quests.\n");
        } else {
            sb.append("--- ACTIVE QUESTS ---\n");
            for (Quest quest : activeQuests.values()) {
                sb.append(quest.getQuestLogEntry()).append("\n");
            }
        }
        
        if (!completedQuests.isEmpty()) {
            sb.append("\n--- COMPLETED QUESTS ---\n");
            for (String questId : completedQuests) {
                sb.append("  - ").append(allQuests.get(questId).getName()).append("\n");
            }
        }
        
        return sb.toString();
    }
}
