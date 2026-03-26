package com.rpg.ui.fx.views;

import com.rpg.models.Element;
import com.rpg.models.Enemy;
import com.rpg.models.EnemyFactory;
import com.rpg.models.Stats;
import com.rpg.ui.fx.GameController;
import com.rpg.ui.fx.components.TooltipFactory;
import com.rpg.world.Location;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

/**
 * Exploration view showing current location and available actions.
 */
public class ExplorationView {
    
    private final GameController controller;
    private final VBox root;
    
    // UI Components
    private Label locationName;
    private Label locationType;
    private TextArea locationDescription;
    private VBox eventArea;
    
    private final Random random = new Random();
    
    public ExplorationView(GameController controller) {
        this.controller = controller;
        this.root = new VBox(15);
        initializeView();
    }
    
    private void initializeView() {
        root.getStyleClass().add("exploration-area");
        root.setPadding(new Insets(15));
        
        // Location card
        VBox locationCard = new VBox(10);
        locationCard.getStyleClass().add("location-card");
        locationCard.setPadding(new Insets(20));
        
        locationName = new Label("Unknown Location");
        locationName.getStyleClass().add("location-name");
        
        locationType = new Label("Region");
        locationType.getStyleClass().add("location-type");
        
        locationDescription = new TextArea();
        locationDescription.getStyleClass().add("creation-input");
        locationDescription.setEditable(false);
        locationDescription.setWrapText(true);
        locationDescription.setPrefHeight(80);
        locationDescription.setStyle("-fx-background-color: transparent; -fx-text-fill: #a89878;");
        
        locationCard.getChildren().addAll(locationName, locationType, locationDescription);
        
        // Event area (for random encounters, discoveries, etc.)
        eventArea = new VBox(10);
        eventArea.getStyleClass().add("location-card");
        eventArea.setPadding(new Insets(15));
        
        Label eventTitle = new Label("Events");
        eventTitle.getStyleClass().add("panel-title");
        eventArea.getChildren().add(eventTitle);
        
        // Quick action buttons
        HBox quickActions = new HBox(15);
        quickActions.setAlignment(Pos.CENTER_LEFT);
        quickActions.setPadding(new Insets(10, 0, 0, 0));
        
        Button exploreBtn = new Button("Explore Area");
        exploreBtn.getStyleClass().add("action-button");
        exploreBtn.setOnAction(e -> exploreArea());
        
        Button searchBtn = new Button("Search for Treasure");
        searchBtn.getStyleClass().add("action-button");
        searchBtn.setOnAction(e -> searchTreasure());
        
        Button encounterBtn = new Button("Seek Battle");
        encounterBtn.getStyleClass().add("action-button");
        encounterBtn.setOnAction(e -> seekBattle());
        
        quickActions.getChildren().addAll(exploreBtn, searchBtn, encounterBtn);
        
        root.getChildren().addAll(locationCard, eventArea, quickActions);
        VBox.setVgrow(eventArea, Priority.ALWAYS);
    }
    
    public void refresh() {
        Location location = controller.getCurrentLocation();
        if (location == null) return;
        
        locationName.setText(location.getName());
        String regionName = location.getRegionType() != null ? location.getRegionType().name() : "Unknown Region";
        locationType.setText(regionName);
        locationDescription.setText(location.getDescription());
        
        // Clear old events
        eventArea.getChildren().clear();
        Label eventTitle = new Label("What would you like to do?");
        eventTitle.getStyleClass().add("panel-title");
        eventArea.getChildren().add(eventTitle);
        
        // Add location-specific options
        addLocationOptions(location);
    }
    
    private void addLocationOptions(Location location) {
        String locId = location.getId();
        
        // Show NPCs at this location with specific dialogue
        if (!location.getCompanionsPresent().isEmpty()) {
            for (String npcId : location.getCompanionsPresent()) {
                String npcName = getNPCDisplayName(npcId);
                Button talkBtn = new Button("Talk to " + npcName);
                talkBtn.getStyleClass().add("action-button");
                talkBtn.setOnAction(e -> showNPCDialogue(npcId, npcName, locId));
                eventArea.getChildren().add(talkBtn);
            }
        }
        
        // Show available quests
        if (!location.getAvailableQuests().isEmpty()) {
            var questManager = controller.getState().getQuestManager();
            for (String questId : location.getAvailableQuests()) {
                var quest = questManager.getQuest(questId);
                if (quest == null || quest.getStatus() != com.rpg.systems.Quest.QuestStatus.NOT_STARTED) continue;
                
                Button questBtn = new Button("[Quest] " + quest.getName());
                questBtn.getStyleClass().add("action-button");
                questBtn.setStyle("-fx-border-color: #c9a227;");
                questBtn.setOnAction(e -> showQuestDialog(quest));
                eventArea.getChildren().add(questBtn);
            }
            
            // Show active quests for this location
            for (String questId : location.getAvailableQuests()) {
                var quest = questManager.getQuest(questId);
                if (quest != null && quest.getStatus() == com.rpg.systems.Quest.QuestStatus.IN_PROGRESS) {
                    Text activeText = new Text("[Active] " + quest.getName() + " - " + 
                        (quest.getCurrentObjective() != null ? quest.getCurrentObjective().getDescription() : "No objective"));
                    activeText.setStyle("-fx-fill: #228b22; -fx-padding: 5 0;");
                    eventArea.getChildren().add(activeText);
                }
                if (quest != null && quest.getStatus() == com.rpg.systems.Quest.QuestStatus.COMPLETED) {
                    Text completeText = new Text("[Completed] " + quest.getName());
                    completeText.setStyle("-fx-fill: #888888; -fx-padding: 5 0;");
                    eventArea.getChildren().add(completeText);
                }
            }
        }
        
        // Show available connections
        var connections = location.getConnectedLocations();
        if (!connections.isEmpty()) {
            Label travelLabel = new Label("Nearby Areas:");
            travelLabel.getStyleClass().add("creation-label");
            travelLabel.setPadding(new Insets(10, 0, 5, 0));
            eventArea.getChildren().add(travelLabel);
            
            FlowPane connectionButtons = new FlowPane(10, 10);
            for (var entry : connections.entrySet()) {
                Button destBtn = new Button(entry.getKey() + ": " + entry.getValue());
                destBtn.getStyleClass().add("action-button");
                String direction = entry.getKey();
                destBtn.setOnAction(e -> controller.travel(direction));
                
                Tooltip tooltip = new Tooltip("Travel " + entry.getKey());
                tooltip.getStyleClass().add("game-tooltip");
                destBtn.setTooltip(tooltip);
                
                connectionButtons.getChildren().add(destBtn);
            }
            eventArea.getChildren().add(connectionButtons);
        }
    }
    
    private void exploreArea() {
        controller.addMessage("You explore the surrounding area...");
        
        // Random discovery chance
        int roll = random.nextInt(100);
        if (roll < 20) {
            controller.addMessage("You discovered a hidden cache! (+50 gold)");
            // TODO: Actually add gold
        } else if (roll < 40) {
            controller.addMessage("You found some useful herbs. (+1 Healing Potion)");
        } else if (roll < 60) {
            controller.addMessage("You encounter hostile creatures!");
            seekBattle();
        } else {
            controller.addMessage("The area seems quiet. Nothing of interest found.");
        }
    }
    
    private void searchTreasure() {
        controller.addMessage("You search carefully for hidden treasures...");
        
        int roll = random.nextInt(100);
        if (roll < 15) {
            controller.addMessage("You found a treasure chest! (+100 gold)");
        } else if (roll < 30) {
            controller.addMessage("You found a small pouch of coins. (+25 gold)");
        } else {
            controller.addMessage("Your search turned up nothing valuable.");
        }
    }
    
    private void seekBattle() {
        controller.addMessage("You venture forth seeking battle...");
        
        // Create random enemies based on location
        Location location = controller.getCurrentLocation();
        List<Enemy> enemies = generateEnemies(location);
        
        if (enemies != null && !enemies.isEmpty()) {
            controller.startCombat(enemies);
        } else {
            controller.addMessage("No enemies found in this area.");
        }
    }
    
    private List<Enemy> generateEnemies(Location location) {
        // Generate 1-2 enemies based on location
        int numEnemies = 1 + random.nextInt(2); // 1 or 2 enemies
        
        // Get player level for scaling (if available)
        int playerLevel = controller.getState() != null && controller.getState().getPlayer() != null 
            ? controller.getState().getPlayer().getLevel() 
            : 1;
        
        // Generate enemies using the factory
        return EnemyFactory.generateEnemies(location.getId().toLowerCase().replace(" ", "_"), playerLevel, numEnemies);
    }
    
    private String getNPCDisplayName(String npcId) {
        return switch (npcId) {
            case "companion_merchant" -> "Merchant";
            default -> npcId.replace("_", " ").replace("companion ", "");
        };
    }
    
    private void showNPCDialogue(String npcId, String npcName, String locationId) {
        String dialogue = getDialogueForNPC(npcId, locationId);
        String[] labels = getResponsesForNPC(npcId, locationId);
        
        Runnable[] actions = new Runnable[labels.length];
        for (int i = 0; i < labels.length; i++) {
            final String label = labels[i];
            actions[i] = () -> controller.addMessage(npcName + ": " + label);
        }
        
        controller.showDialogue(npcName, dialogue, labels, actions);
    }
    
    private String getDialogueForNPC(String npcId, String locationId) {
        return switch (npcId) {
            case "companion_merchant" -> 
                "Ah, a traveler! Welcome to the Market District. " +
                "I've got the finest wares in all the kingdom. " +
                "Heard some goblins have been causing trouble near the Guild Hall. " +
                "If you clear them out, I'll give you a discount on potions!";
            default -> "Greetings, traveler. What brings you to this place?";
        };
    }
    
    private String[] getResponsesForNPC(String npcId, String locationId) {
        return switch (npcId) {
            case "companion_merchant" -> new String[]{
                "Tell me about the goblins",
                "What do you sell?",
                "Goodbye"
            };
            default -> new String[]{"Goodbye"};
        };
    }
    
    private void showQuestDialog(com.rpg.systems.Quest quest) {
        String description = quest.getDescription();
        
        String[] labels = {"Accept Quest", "Decline"};
        Runnable[] actions = {
            () -> {
                var qm = controller.getState().getQuestManager();
                qm.startQuest(quest.getId());
                controller.addMessage("Quest accepted: " + quest.getName());
                refresh();
            },
            () -> {
                controller.addMessage("You declined the quest.");
            }
        };
        
        controller.showDialogue(quest.getName(), 
            description + "\n\nReward: " + quest.getRewardExperience() + " EXP, " + quest.getRewardGold() + " Gold",
            labels, actions
        );
    }
    
    public VBox getRoot() {
        return root;
    }
}
