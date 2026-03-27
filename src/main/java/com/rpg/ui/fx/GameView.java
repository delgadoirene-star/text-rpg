package com.rpg.ui.fx;

import com.rpg.models.*;
import com.rpg.ui.fx.components.TooltipFactory;
import com.rpg.ui.fx.views.*;
import com.rpg.world.RegionType;
import com.rpg.world.ReputationSystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Main view for the JavaFX game interface.
 * Implements RPG Classic layout: text/dialogue top, party status bottom, actions right.
 * Dark Fantasy theme with gold/red accents.
 */
public class GameView {
    
    // Constants
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 800;
    
    // Core components
    private final Stage stage;
    private final GameController controller;
    private Scene scene;
    
    // Layout regions
    private BorderPane rootPane;
    private StackPane contentArea;      // Center - swappable content
    private VBox partyStatusBar;        // Bottom - party HP/MP bars
    private VBox actionPanel;           // Right - context actions
    private HBox topBar;                // Top - location, day, gold info
    private TextArea messageArea;       // Bottom-center - activity log
    
    // Sub-views
    private TitleView titleView;
    private CharacterCreationView characterCreationView;
    private ExplorationView explorationView;
    private CombatView combatView;
    private PartyMenuView partyMenuView;
    
    public GameView(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        controller.setGameView(this);
        
        initializeLayout();
        initializeSubViews();
        createScene();
    }
    
    private void initializeLayout() {
        rootPane = new BorderPane();
        rootPane.getStyleClass().add("root-pane");
        
        // Top bar - game info
        topBar = createTopBar();
        rootPane.setTop(topBar);
        
        // Center - main content area (swappable)
        contentArea = new StackPane();
        contentArea.getStyleClass().add("content-area");
        
        // Message display area - compact activity log
        messageArea = new TextArea();
        messageArea.getStyleClass().add("message-display");
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setPrefHeight(80);
        messageArea.setMinHeight(80);
        messageArea.setMaxHeight(80);
        messageArea.setStyle("-fx-control-inner-background: #0d0d0d; -fx-text-fill: #a89878; " +
                            "-fx-font-family: monospace; -fx-font-size: 11px; " +
                            "-fx-border-color: #333333; -fx-border-width: 1; " +
                            "-fx-background-color: #0d0d0d;");
        
        // Bind message log to display
        controller.messageLogProperty().addListener((obs, oldVal, newVal) -> {
            messageArea.setText(newVal);
            messageArea.positionCaret(newVal != null ? newVal.length() : 0);
        });
        
        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(messageArea, contentArea);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        VBox.setVgrow(messageArea, Priority.NEVER);
        rootPane.setCenter(centerBox);
        
        // Right - action panel
        actionPanel = createActionPanel();
        rootPane.setRight(actionPanel);
        
        // Bottom - party status
        partyStatusBar = createPartyStatusBar();
        rootPane.setBottom(partyStatusBar);
    }
    
    private HBox createTopBar() {
        HBox bar = new HBox(20);
        bar.getStyleClass().add("top-bar");
        bar.setPadding(new Insets(10, 20, 10, 20));
        bar.setAlignment(Pos.CENTER_LEFT);
        
        // Location label
        Label locationLabel = new Label("Location: ---");
        locationLabel.setId("location-label");
        locationLabel.getStyleClass().add("info-label");
        
        // Region reputation label
        Label repLabel = new Label("Rep: 0 [NEUTRAL]");
        repLabel.setId("rep-label");
        repLabel.getStyleClass().add("info-label");
        
        // Day counter
        Label dayLabel = new Label("Day: 1");
        dayLabel.setId("day-label");
        dayLabel.getStyleClass().add("info-label");
        
        // Gold counter
        Label goldLabel = new Label("Gold: 0");
        goldLabel.setId("gold-label");
        goldLabel.getStyleClass().add("info-label");
        
        // Reputation button
        Button repBtn = new Button("Rep");
        repBtn.setId("rep-btn");
        repBtn.getStyleClass().add("menu-button");
        repBtn.setOnAction(e -> showReputationDialog());
        Tooltip repTooltip = new Tooltip("View regional reputation");
        repTooltip.getStyleClass().add("game-tooltip");
        repBtn.setTooltip(repTooltip);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Menu button
        Button menuBtn = new Button("Menu");
        menuBtn.getStyleClass().add("menu-button");
        menuBtn.setOnAction(e -> showGameMenu());
        
        bar.getChildren().addAll(locationLabel, repLabel, dayLabel, goldLabel, repBtn, spacer, menuBtn);
        
        return bar;
    }
    
    private VBox createActionPanel() {
        VBox panel = new VBox(10);
        panel.getStyleClass().add("action-panel");
        panel.setPadding(new Insets(15));
        panel.setPrefWidth(200);
        panel.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("Actions");
        title.getStyleClass().add("panel-title");
        
        panel.getChildren().add(title);
        
        return panel;
    }
    
    private VBox createPartyStatusBar() {
        VBox bar = new VBox(5);
        bar.getStyleClass().add("party-status-bar");
        bar.setPadding(new Insets(10, 20, 10, 20));
        
        Label title = new Label("Party Status");
        title.getStyleClass().add("panel-title");
        
        HBox partyCards = new HBox(15);
        partyCards.setId("party-cards");
        partyCards.setAlignment(Pos.CENTER_LEFT);
        
        bar.getChildren().addAll(title, partyCards);
        
        return bar;
    }
    
    private void initializeSubViews() {
        titleView = new TitleView(controller);
        characterCreationView = new CharacterCreationView(controller);
        explorationView = new ExplorationView(controller);
        combatView = new CombatView(controller);
        partyMenuView = new PartyMenuView(controller);
    }
    
    private void createScene() {
        scene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Load CSS
        String cssPath = getClass().getResource("/css/dark-fantasy.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        
        stage.setScene(scene);
        stage.setTitle("Dark Fantasy RPG");
        stage.setMinWidth(1024);
        stage.setMinHeight(600);
    }
    
    // ==================== View Switching ====================
    
    public void showTitleScreen() {
        hideGameUI();
        contentArea.getChildren().setAll(titleView.getRoot());
    }
    
    public void showCharacterCreation() {
        hideGameUI();
        contentArea.getChildren().setAll(characterCreationView.getRoot());
    }
    
    public void showExploration() {
        showGameUI();
        updateTopBar();
        updatePartyStatus();
        
        explorationView.refresh();
        contentArea.getChildren().setAll(explorationView.getRoot());
        
        // Update action panel
        updateExplorationActions();
    }
    
    public void showCombat() {
        showGameUI();
        updatePartyStatus();
        
        combatView.refresh();
        contentArea.getChildren().setAll(combatView.getRoot());
    }
    
    public void refreshCombat() {
        if (controller.isInCombat()) {
            combatView.refresh();
        } else {
            // Show the battle summary in combat view before returning to exploration
            combatView.refresh();
        }
    }
    
    public void showPartyMenu() {
        showGameUI();
        updatePartyStatus();
        
        partyMenuView.refresh();
        contentArea.getChildren().setAll(partyMenuView.getRoot());
        
        // Update action panel
        updatePartyMenuActions();
    }
    
    /**
     * Show a dialogue box in the center content area
     */
    public void showDialogue(String speakerName, String text, String[] labels, Runnable[] actions) {
        VBox dialogueBox = new VBox(15);
        dialogueBox.getStyleClass().add("location-card");
        dialogueBox.setPadding(new Insets(30));
        dialogueBox.setMaxWidth(600);
        dialogueBox.setAlignment(Pos.CENTER);
        
        // Speaker name
        Label speaker = new Label(speakerName);
        speaker.getStyleClass().add("location-name");
        speaker.setStyle("-fx-font-size: 20px; -fx-text-fill: #c9a227;");
        
        // Dialogue text
        TextArea dialogueText = new TextArea(text);
        dialogueText.setEditable(false);
        dialogueText.setWrapText(true);
        dialogueText.setPrefHeight(150);
        dialogueText.setStyle("-fx-background-color: transparent; -fx-text-fill: #a89878; -fx-font-size: 14px;");
        
        dialogueBox.getChildren().addAll(speaker, dialogueText);
        
        // Options/responses with actual labels
        if (labels != null && labels.length > 0) {
            VBox optionsBox = new VBox(10);
            optionsBox.setAlignment(Pos.CENTER);
            
            for (int i = 0; i < labels.length; i++) {
                final int index = i;
                Button optionBtn = new Button(labels[i]);
                optionBtn.getStyleClass().add("action-button");
                optionBtn.setPrefWidth(300);
                optionBtn.setOnAction(e -> {
                    if (actions != null && index < actions.length && actions[index] != null) {
                        actions[index].run();
                    }
                    showExploration();
                });
                optionsBox.getChildren().add(optionBtn);
            }
            
            dialogueBox.getChildren().add(optionsBox);
        } else {
            Button continueBtn = new Button("Continue");
            continueBtn.getStyleClass().add("action-button");
            continueBtn.setPrefWidth(150);
            continueBtn.setOnAction(e -> showExploration());
            dialogueBox.getChildren().add(continueBtn);
        }
        
        // Show in content area
        contentArea.getChildren().setAll(dialogueBox);
    }
    
    // ==================== UI Updates ====================
    
    private void hideGameUI() {
        topBar.setVisible(false);
        topBar.setManaged(false);
        actionPanel.setVisible(false);
        actionPanel.setManaged(false);
        partyStatusBar.setVisible(false);
        partyStatusBar.setManaged(false);
        messageArea.setVisible(false);
        messageArea.setManaged(false);
    }
    
    private void showGameUI() {
        topBar.setVisible(true);
        topBar.setManaged(true);
        actionPanel.setVisible(true);
        actionPanel.setManaged(true);
        partyStatusBar.setVisible(true);
        partyStatusBar.setManaged(true);
        messageArea.setVisible(true);
        messageArea.setManaged(true);
    }
    
    private void updateTopBar() {
        var location = controller.getCurrentLocation();
        var state = controller.getState();
        
        Label locationLabel = (Label) topBar.lookup("#location-label");
        Label repLabel = (Label) topBar.lookup("#rep-label");
        Label dayLabel = (Label) topBar.lookup("#day-label");
        Label goldLabel = (Label) topBar.lookup("#gold-label");
        
        if (location != null && locationLabel != null) {
            locationLabel.setText("Location: " + location.getName());
        }
        
        if (location != null && repLabel != null) {
            ReputationSystem repSystem = controller.getReputationSystem();
            if (repSystem != null && location.getRegionType() != null) {
                int rep = repSystem.getReputation(location.getRegionType());
                String status;
                String style;
                
                if (rep >= 75) {
                    status = "[BELOVED]";
                    style = "-fx-text-fill: #228b22;";
                } else if (rep > 0) {
                    status = "[FRIENDLY]";
                    style = "-fx-text-fill: #32cd32;";
                } else if (rep == 0) {
                    status = "[NEUTRAL]";
                    style = "-fx-text-fill: #a89878;";
                } else if (rep > -50) {
                    status = "[UNFRIENDLY]";
                    style = "-fx-text-fill: #ff8c00;";
                } else {
                    status = "[SULLIED]";
                    style = "-fx-text-fill: #dc143c;";
                }
                
                repLabel.setText("Rep: " + rep + " " + status);
                repLabel.setStyle(style);
            } else {
                repLabel.setText("Rep: 0 [NEUTRAL]");
                repLabel.setStyle("-fx-text-fill: #a89878;");
            }
        }
        
        if (state != null && dayLabel != null) {
            dayLabel.setText("Day: " + state.getCurrentDay());
        }
        if (state != null && goldLabel != null) {
            goldLabel.setText("Gold: " + state.getGold());
        }
    }
    
    public void updatePartyStatus() {
        var state = controller.getState();
        if (state == null) return;
        
        HBox partyCards = (HBox) partyStatusBar.lookup("#party-cards");
        if (partyCards == null) return;
        
        partyCards.getChildren().clear();
        
        // Add player card
        Player player = state.getPlayer();
        if (player != null) {
            partyCards.getChildren().add(createCharacterCard(player, true));
        }
        
        // Add active companion cards
        for (Companion companion : state.getActiveCompanions()) {
            partyCards.getChildren().add(createCharacterCard(companion, false));
        }
    }
    
    private VBox createCharacterCard(com.rpg.models.Character character, boolean isPlayer) {
        VBox card = new VBox(3);
        card.getStyleClass().add("character-card");
        if (isPlayer) {
            card.getStyleClass().add("player-card");
        }
        card.setPadding(new Insets(8));
        card.setPrefWidth(160);
        
        // Name with element icon
        String elementSymbol = getElementSymbol(character.getElementAffinity());
        Label nameLabel = new Label(elementSymbol + " " + character.getName());
        nameLabel.getStyleClass().add("character-name");
        
        // Class and level
        String className = "Unknown";
        if (character instanceof Player player) {
            className = player.getCurrentClass().getName();
        } else if (character instanceof Companion companion) {
            className = companion.getCurrentClass().getName();
        }
        Label levelLabel = new Label("Lv." + character.getLevel() + " " + className);
        levelLabel.getStyleClass().add("character-class");
        
        // HP Bar
        HBox hpBar = createStatBar("HP", character.getCurrentHP(), character.getMaxHP(), "hp-bar");
        
        card.getChildren().addAll(nameLabel, levelLabel, hpBar);
        
        // Focus bar for player
        if (character instanceof Player player) {
            int focus = (int) player.getFocusMeter().getCurrentFocus();
            HBox focusBar = createStatBar("FP", focus, 100, "focus-bar");
            card.getChildren().add(focusBar);
        }
        
        // Loyalty indicator for companions
        if (character instanceof Companion companion) {
            int loyalty = companion.getLoyaltyLevel();
            String loyaltyColor = loyalty >= 60 ? "#228b22" : loyalty >= 30 ? "#c9a227" : "#dc143c";
            Label loyaltyLabel = new Label(getLoyaltyHeart(loyalty) + " " + companion.getLoyaltyDescription());
            loyaltyLabel.setStyle("-fx-text-fill: " + loyaltyColor + "; -fx-font-size: 9px;");
            card.getChildren().add(loyaltyLabel);
        }
        
        // Add tooltip with detailed stats
        Tooltip tooltip = TooltipFactory.createCharacterTooltip(character);
        Tooltip.install(card, tooltip);
        
        return card;
    }
    
    private String getElementSymbol(Element element) {
        return switch (element) {
            case FIRE -> "F";
            case WATER -> "W";
            case EARTH -> "E";
            case WIND -> "N";
            case LIGHT -> "L";
            case DARK -> "D";
            case NEUTRAL, NONE -> "-";
        };
    }
    
    private String getLoyaltyHeart(int loyalty) {
        if (loyalty >= 80) return "\u2764";
        if (loyalty >= 60) return "\u2665";
        if (loyalty >= 40) return "\u2661";
        if (loyalty >= 20) return "\u2743";
        return "\u2620";
    }
    
    private HBox createStatBar(String label, int current, int max, String styleClass) {
        HBox container = new HBox(5);
        container.setAlignment(Pos.CENTER_LEFT);
        
        Label statLabel = new Label(label + ":");
        statLabel.getStyleClass().add("stat-label");
        statLabel.setMinWidth(25);
        
        ProgressBar bar = new ProgressBar();
        bar.getStyleClass().add(styleClass);
        bar.setProgress(max > 0 ? (double) current / max : 0);
        bar.setPrefWidth(80);
        bar.setPrefHeight(12);
        
        Label valueLabel = new Label(current + "/" + max);
        valueLabel.getStyleClass().add("stat-value");
        
        container.getChildren().addAll(statLabel, bar, valueLabel);
        
        return container;
    }
    
    // ==================== Action Panel Updates ====================
    
    private void updateExplorationActions() {
        actionPanel.getChildren().clear();
        
        Label title = new Label("Actions");
        title.getStyleClass().add("panel-title");
        actionPanel.getChildren().add(title);
        
        // Travel button
        Button travelBtn = createActionButton("Travel", "Explore nearby areas");
        travelBtn.setOnAction(e -> showTravelOptions());
        
        // Rest button
        Button restBtn = createActionButton("Rest", "Rest and recover HP/MP");
        restBtn.setOnAction(e -> controller.rest());
        
        // Party button
        Button partyBtn = createActionButton("Party", "View party status and equipment");
        partyBtn.setOnAction(e -> controller.showPartyMenu());
        
        // Quest log button
        Button questBtn = createActionButton("Quests", "View active quests");
        questBtn.setOnAction(e -> showQuestLog());
        
        actionPanel.getChildren().addAll(travelBtn, restBtn, partyBtn, questBtn);
    }
    
    private void updateCombatActions() {
        actionPanel.getChildren().clear();
        
        Label title = new Label("Combat");
        title.getStyleClass().add("panel-title");
        actionPanel.getChildren().add(title);
        
        // Attack button
        Button attackBtn = createActionButton("Attack", "Basic attack");
        
        // Ability button
        Button abilityBtn = createActionButton("Abilities", "Use special abilities");
        
        // Defend button
        Button defendBtn = createActionButton("Defend", "Reduce incoming damage");
        
        // Flee button
        Button fleeBtn = createActionButton("Flee", "Attempt to escape");
        
        actionPanel.getChildren().addAll(attackBtn, abilityBtn, defendBtn, fleeBtn);
    }
    
    private void updatePartyMenuActions() {
        actionPanel.getChildren().clear();
        
        Label title = new Label("Party Menu");
        title.getStyleClass().add("panel-title");
        actionPanel.getChildren().add(title);
        
        // Back button
        Button backBtn = createActionButton("Back", "Return to exploration");
        backBtn.setOnAction(e -> controller.showExploration());
        
        actionPanel.getChildren().add(backBtn);
    }
    
    private Button createActionButton(String text, String tooltipText) {
        Button btn = new Button(text);
        btn.getStyleClass().add("action-button");
        btn.setPrefWidth(160);
        btn.setPrefHeight(40);
        
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.getStyleClass().add("game-tooltip");
        btn.setTooltip(tooltip);
        
        return btn;
    }
    
    private void showTravelOptions() {
        var location = controller.getCurrentLocation();
        if (location == null) return;
        
        // Create travel dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Travel");
        dialog.setHeaderText("Choose your destination");
        dialog.getDialogPane().getStylesheets().add(
            getClass().getResource("/css/dark-fantasy.css").toExternalForm()
        );
        dialog.getDialogPane().getStyleClass().add("game-dialog");
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        var connections = location.getConnectedLocations();
        for (var entry : connections.entrySet()) {
            Button destBtn = new Button(entry.getKey() + ": " + entry.getValue());
            destBtn.getStyleClass().add("action-button");
            destBtn.setPrefWidth(250);
            String direction = entry.getKey();
            destBtn.setOnAction(e -> {
                controller.travel(direction);
                dialog.close();
            });
            content.getChildren().add(destBtn);
        }
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        dialog.showAndWait();
    }
    
    public void showReputationDialog() {
        ReputationSystem repSystem = controller.getReputationSystem();
        if (repSystem == null) return;
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Regional Reputation");
        dialog.setHeaderText("Your standing across the realm");
        dialog.getDialogPane().getStylesheets().add(
            getClass().getResource("/css/dark-fantasy.css").toExternalForm()
        );
        dialog.getDialogPane().getStyleClass().add("game-dialog");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(15));
        
        for (RegionType region : RegionType.values()) {
            int rep = repSystem.getReputation(region);
            String status;
            String style;
            
            if (rep >= 75) {
                status = "BELOVED";
                style = "-fx-text-fill: #228b22;";
            } else if (rep > 0) {
                status = "FRIENDLY";
                style = "-fx-text-fill: #32cd32;";
            } else if (rep == 0) {
                status = "NEUTRAL";
                style = "-fx-text-fill: #a89878;";
            } else if (rep > -50) {
                status = "UNFRIENDLY";
                style = "-fx-text-fill: #ff8c00;";
            } else {
                status = "SULLIED";
                style = "-fx-text-fill: #dc143c; -fx-font-weight: bold;";
            }
            
            HBox regionRow = new HBox(15);
            regionRow.setAlignment(Pos.CENTER_LEFT);
            
            Label regionLabel = new Label(region.getDisplayName());
            regionLabel.setMinWidth(150);
            regionLabel.setStyle("-fx-font-weight: bold;");
            
            Label repValue = new Label(String.format("%+4d", rep));
            repValue.setStyle(style);
            repValue.setMinWidth(50);
            
            Label statusLabel = new Label("[" + status + "]");
            statusLabel.setStyle(style);
            
            regionRow.getChildren().addAll(regionLabel, repValue, statusLabel);
            content.getChildren().add(regionRow);
        }
        
        Separator sep = new Separator();
        sep.setPadding(new Insets(10, 0, 10, 0));
        content.getChildren().add(sep);
        
        Label hintLabel = new Label("Reputation affects: Shop prices, Safe zones, Enemy aggression");
        hintLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
        content.getChildren().add(hintLabel);
        
        Label hintLabel2 = new Label("Sullied (Rep <= -50): Shops refuse service, enemies hunt you");
        hintLabel2.setStyle("-fx-text-fill: #dc143c; -fx-font-size: 11px;");
        content.getChildren().add(hintLabel2);
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        dialog.showAndWait();
    }
    
    private void showQuestLog() {
        // TODO: Implement quest log dialog
        controller.addMessage("Quest log not yet implemented.");
    }
    
    private void showGameMenu() {
        // Create game menu dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Game Menu");
        dialog.getDialogPane().getStylesheets().add(
            getClass().getResource("/css/dark-fantasy.css").toExternalForm()
        );
        dialog.getDialogPane().getStyleClass().add("game-dialog");
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        Button saveBtn = new Button("Save Game");
        saveBtn.getStyleClass().add("action-button");
        saveBtn.setPrefWidth(150);
        
        Button loadBtn = new Button("Load Game");
        loadBtn.getStyleClass().add("action-button");
        loadBtn.setPrefWidth(150);
        
        Button optionsBtn = new Button("Options");
        optionsBtn.getStyleClass().add("action-button");
        optionsBtn.setPrefWidth(150);
        
        Button quitBtn = new Button("Quit to Title");
        quitBtn.getStyleClass().add("action-button");
        quitBtn.setPrefWidth(150);
        quitBtn.setOnAction(e -> {
            controller.showTitleScreen();
            dialog.close();
        });
        
        content.getChildren().addAll(saveBtn, loadBtn, optionsBtn, quitBtn);
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        dialog.showAndWait();
    }
    
    // ==================== Getters ====================
    
    public Scene getScene() { return scene; }
    public Stage getStage() { return stage; }
}
