package com.rpg.ui.fx.views;

import com.rpg.models.*;
import com.rpg.ui.fx.GameController;
import com.rpg.ui.fx.components.TooltipFactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Party menu view showing detailed character information and management options.
 */
public class PartyMenuView {
    
    private final GameController controller;
    private final VBox root;
    
    // UI Components
    private VBox playerDetailPanel;
    private VBox companionListPanel;
    private VBox selectedCompanionPanel;
    
    public PartyMenuView(GameController controller) {
        this.controller = controller;
        this.root = new VBox(15);
        initializeView();
    }
    
    private void initializeView() {
        root.setPadding(new Insets(15));
        
        // Title
        Label title = new Label("Party Management");
        title.getStyleClass().add("creation-title");
        
        // Main content - horizontal split
        HBox content = new HBox(20);
        content.setPadding(new Insets(10, 0, 0, 0));
        
        // Left side - Player details
        playerDetailPanel = new VBox(10);
        playerDetailPanel.getStyleClass().add("location-card");
        playerDetailPanel.setPadding(new Insets(15));
        playerDetailPanel.setPrefWidth(350);
        
        // Center - Companion list
        companionListPanel = new VBox(10);
        companionListPanel.getStyleClass().add("location-card");
        companionListPanel.setPadding(new Insets(15));
        companionListPanel.setPrefWidth(250);
        
        // Right - Selected companion details
        selectedCompanionPanel = new VBox(10);
        selectedCompanionPanel.getStyleClass().add("location-card");
        selectedCompanionPanel.setPadding(new Insets(15));
        selectedCompanionPanel.setPrefWidth(350);
        
        content.getChildren().addAll(playerDetailPanel, companionListPanel, selectedCompanionPanel);
        HBox.setHgrow(playerDetailPanel, Priority.ALWAYS);
        HBox.setHgrow(selectedCompanionPanel, Priority.ALWAYS);
        
        // Alignment display
        HBox alignmentBar = createAlignmentDisplay();
        
        root.getChildren().addAll(title, alignmentBar, content);
        VBox.setVgrow(content, Priority.ALWAYS);
    }
    
    public void refresh() {
        refreshPlayerPanel();
        refreshCompanionList();
        selectedCompanionPanel.getChildren().clear();
        
        Label placeholder = new Label("Select a companion to view details");
        placeholder.setStyle("-fx-text-fill: #666666;");
        selectedCompanionPanel.getChildren().add(placeholder);
    }
    
    private void refreshPlayerPanel() {
        playerDetailPanel.getChildren().clear();
        
        Player player = controller.getPlayer();
        if (player == null) return;
        
        // Header
        Label nameLabel = new Label(player.getName());
        nameLabel.getStyleClass().add("location-name");
        
        Label classLabel = new Label("Level " + player.getLevel() + " " + player.getCurrentClass().getName());
        classLabel.getStyleClass().add("location-type");
        
        // Experience bar
        HBox expBar = createProgressBar("EXP", player.getExperience(), player.getExperienceToNextLevel(), "#c9a227");
        
        // HP bar (uses Focus system, not MP)
        HBox hpBar = createProgressBar("HP", player.getCurrentHP(), player.getMaxHP(), "#dc143c");
        HBox focusBar = createProgressBar("Focus", (int) player.getFocusMeter().getCurrentFocus(), 100, "#4a90d9");
        
        // Stats grid with allocation buttons
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(15);
        statsGrid.setVgap(5);
        statsGrid.setPadding(new Insets(10, 0, 10, 0));
        
        Stats stats = player.getBaseStats();
        int available = player.getAvailableStatPoints();
        
        String[][] statInfo = {
            {"STR", "Strength", String.valueOf(stats.getStrength())},
            {"DEX", "Dexterity", String.valueOf(stats.getDexterity())},
            {"VIT", "Vitality", String.valueOf(stats.getVitality())},
            {"INT", "Intelligence", String.valueOf(stats.getIntelligence())},
            {"WIS", "Wisdom", String.valueOf(stats.getWisdom())},
            {"LUK", "Luck", String.valueOf(stats.getLuck())}
        };
        
        for (int i = 0; i < statInfo.length; i++) {
            String shortName = statInfo[i][0];
            String fullName = statInfo[i][1];
            String value = statInfo[i][2];
            
            Label statNameLabel = new Label(fullName + ":");
            statNameLabel.setStyle("-fx-text-fill: #888888; -fx-min-width: 80;");
            
            Label valueLabel = new Label(value);
            valueLabel.setStyle("-fx-text-fill: #c9a227; -fx-font-weight: bold;");
            
            statsGrid.add(statNameLabel, 0, i);
            statsGrid.add(valueLabel, 1, i);
            
            if (available > 0) {
                Button plusBtn = new Button("+");
                plusBtn.setStyle("-fx-background-color: #2a4a2a; -fx-text-fill: #228b22; " +
                                "-fx-border-color: #228b22; -fx-border-width: 1; " +
                                "-fx-min-width: 28; -fx-min-height: 22; -fx-cursor: hand;");
                plusBtn.setOnAction(e -> {
                    player.allocateStatPoint(shortName);
                    refresh();
                });
                statsGrid.add(plusBtn, 2, i);
            }
        }
        
        // Show available stat points
        if (available > 0) {
            Label pointsLabel = new Label("Available Points: " + available);
            pointsLabel.setStyle("-fx-text-fill: #228b22; -fx-font-weight: bold; -fx-padding: 5 0 0 0;");
            playerDetailPanel.getChildren().add(pointsLabel);
        }
        
        // Element
        Label elementLabel = new Label("Element: " + player.getElementAffinity().name());
        elementLabel.getStyleClass().add("element-" + player.getElementAffinity().name().toLowerCase());
        
        // Background info
        Label backgroundLabel = new Label("Background: " + player.getBackground().getDisplayName());
        backgroundLabel.setStyle("-fx-text-fill: #a89878;");
        
        playerDetailPanel.getChildren().addAll(
            nameLabel, classLabel, expBar, hpBar, focusBar, 
            statsGrid, elementLabel, backgroundLabel
        );
    }
    
    private void refreshCompanionList() {
        companionListPanel.getChildren().clear();
        
        Label title = new Label("Companions");
        title.getStyleClass().add("panel-title");
        companionListPanel.getChildren().add(title);
        
        var state = controller.getState();
        if (state == null) return;
        
        // Active companions
        Label activeLabel = new Label("Active Party:");
        activeLabel.setStyle("-fx-text-fill: #228b22; -fx-padding: 10 0 5 0;");
        companionListPanel.getChildren().add(activeLabel);
        
        for (Companion companion : state.getActiveCompanions()) {
            Button compBtn = createCompanionButton(companion, true);
            companionListPanel.getChildren().add(compBtn);
        }
        
        // Available companions
        Label availableLabel = new Label("Available:");
        availableLabel.setStyle("-fx-text-fill: #c9a227; -fx-padding: 10 0 5 0;");
        companionListPanel.getChildren().add(availableLabel);
        
        for (Companion companion : state.getAvailableCompanions()) {
            if (!state.getActiveCompanions().contains(companion)) {
                Button compBtn = createCompanionButton(companion, false);
                companionListPanel.getChildren().add(compBtn);
            }
        }
    }
    
    private Button createCompanionButton(Companion companion, boolean isActive) {
        Button btn = new Button(companion.getName());
        btn.getStyleClass().add("action-button");
        btn.setPrefWidth(200);
        
        if (isActive) {
            btn.setStyle("-fx-border-color: #228b22;");
        }
        
        // Loyalty indicator
        int loyalty = companion.getLoyaltyLevel();
        String loyaltyColor = loyalty >= 60 ? "#228b22" : loyalty >= 30 ? "#c9a227" : "#dc143c";
        
        // Tooltip with basic info
        Tooltip tooltip = TooltipFactory.createCompanionTooltip(companion);
        btn.setTooltip(tooltip);
        
        btn.setOnAction(e -> showCompanionDetails(companion));
        
        return btn;
    }
    
    private void showCompanionDetails(Companion companion) {
        selectedCompanionPanel.getChildren().clear();
        
        // Header
        Label nameLabel = new Label(companion.getName());
        nameLabel.getStyleClass().add("location-name");
        
        Label classLabel = new Label("Level " + companion.getLevel() + " " + companion.getCurrentClass().getName());
        classLabel.getStyleClass().add("location-type");
        
        // Loyalty bar
        HBox loyaltyBar = createProgressBar("Loyalty", companion.getLoyaltyLevel(), 100, getRelationshipColor(companion.getLoyaltyLevel()));
        
        // Personality type
        Label personalityLabel = new Label("Personality: " + companion.getPersonalityType());
        personalityLabel.setStyle("-fx-text-fill: #a89878; -fx-padding: 5 0;");
        
        // HP bar
        HBox hpBar = createProgressBar("HP", companion.getCurrentHP(), companion.getMaxHP(), "#dc143c");
        
        // Stats
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(5);
        statsGrid.setPadding(new Insets(10, 0, 10, 0));
        
        Stats stats = companion.getBaseStats();
        addStatRow(statsGrid, 0, "Strength", stats.getStrength());
        addStatRow(statsGrid, 1, "Dexterity", stats.getDexterity());
        addStatRow(statsGrid, 2, "Vitality", stats.getVitality());
        addStatRow(statsGrid, 3, "Intelligence", stats.getIntelligence());
        addStatRow(statsGrid, 4, "Wisdom", stats.getWisdom());
        addStatRow(statsGrid, 5, "Luck", stats.getLuck());
        
        // Equipment slots
        Label equipTitle = new Label("Equipment");
        equipTitle.getStyleClass().add("panel-title");
        equipTitle.setStyle("-fx-padding: 10 0 5 0;");
        
        GridPane equipGrid = new GridPane();
        equipGrid.setHgap(10);
        equipGrid.setVgap(5);
        equipGrid.setPadding(new Insets(5, 0, 10, 0));
        
        var allEquipment = companion.getAllEquipment();
        int row = 0;
        for (EquipSlot slot : EquipSlot.values()) {
            Label slotLabel = new Label(slot.getDisplayName() + ":");
            slotLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
            slotLabel.setMinWidth(70);
            
            Equipment item = allEquipment.get(slot);
            Label itemLabel;
            if (item != null) {
                itemLabel = new Label(item.getName());
                itemLabel.setStyle("-fx-text-fill: " + getRarityColor(item.getRarity()) + "; -fx-font-size: 11px;");
                
                Tooltip tip = new Tooltip(item.getDetailedInfo());
                tip.getStyleClass().add("game-tooltip");
                itemLabel.setTooltip(tip);
            } else {
                itemLabel = new Label("[Empty]");
                itemLabel.setStyle("-fx-text-fill: #555555; -fx-font-style: italic; -fx-font-size: 11px;");
            }
            
            equipGrid.add(slotLabel, 0, row);
            equipGrid.add(itemLabel, 1, row);
            row++;
        }
        
        // Action buttons
        HBox actions = new HBox(10);
        actions.setPadding(new Insets(10, 0, 0, 0));
        
        var state = controller.getState();
        boolean isActive = state != null && state.getActiveCompanions().contains(companion);
        
        if (isActive) {
            Button removeBtn = new Button("Remove from Party");
            removeBtn.getStyleClass().add("action-button");
            removeBtn.setOnAction(e -> {
                state.removeActiveCompanion(companion);
                refresh();
            });
            actions.getChildren().add(removeBtn);
        } else {
            Button addBtn = new Button("Add to Party");
            addBtn.getStyleClass().add("action-button");
            addBtn.setOnAction(e -> {
                if (state.getActiveCompanions().size() < 3) {
                    state.addActiveCompanion(companion);
                    refresh();
                } else {
                    controller.addMessage("Party is full! (Max 3 companions)");
                }
            });
            actions.getChildren().add(addBtn);
        }
        
        selectedCompanionPanel.getChildren().addAll(
            nameLabel, classLabel, loyaltyBar, personalityLabel, hpBar, statsGrid, equipTitle, equipGrid, actions
        );
    }
    
    private HBox createAlignmentDisplay() {
        HBox container = new HBox(30);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #1a1a1a; -fx-border-color: #3d3d3d; -fx-border-width: 1;");
        
        var alignment = controller.getAlignment();
        if (alignment == null) return container;
        
        int honor = alignment.getHonor();
        int compassion = alignment.getCompassion();
        
        // Honor meter
        VBox honorBox = new VBox(3);
        honorBox.setAlignment(Pos.CENTER);
        Label honorLabel = new Label(honor >= 0 ? "Honor: " + honor : "Pragmatic: " + Math.abs(honor));
        honorLabel.setStyle(honor >= 0 ? "-fx-text-fill: #c9a227;" : "-fx-text-fill: #888888;");
        ProgressBar honorBar = new ProgressBar((honor + 100) / 200.0);
        honorBar.setPrefWidth(150);
        honorBar.setStyle("-fx-accent: " + (honor >= 0 ? "#c9a227" : "#888888") + ";");
        honorBox.getChildren().addAll(honorLabel, honorBar);
        
        // Compassion meter  
        VBox compBox = new VBox(3);
        compBox.setAlignment(Pos.CENTER);
        Label compLabel = new Label(compassion >= 0 ? "Compassion: " + compassion : "Ruthless: " + Math.abs(compassion));
        compLabel.setStyle(compassion >= 0 ? "-fx-text-fill: #4a90d9;" : "-fx-text-fill: #dc143c;");
        ProgressBar compBar = new ProgressBar((compassion + 100) / 200.0);
        compBar.setPrefWidth(150);
        compBar.setStyle("-fx-accent: " + (compassion >= 0 ? "#4a90d9" : "#dc143c") + ";");
        compBox.getChildren().addAll(compLabel, compBar);
        
        // Add tooltip
        Tooltip alignTooltip = TooltipFactory.createAlignmentTooltip(honor, compassion);
        Tooltip.install(container, alignTooltip);
        
        container.getChildren().addAll(honorBox, compBox);
        
        return container;
    }
    
    private HBox createProgressBar(String label, int current, int max, String color) {
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER_LEFT);
        
        Label nameLabel = new Label(label + ":");
        nameLabel.setStyle("-fx-text-fill: #888888; -fx-min-width: 80;");
        
        ProgressBar bar = new ProgressBar(max > 0 ? (double) current / max : 0);
        bar.setPrefWidth(150);
        bar.setPrefHeight(15);
        bar.setStyle("-fx-accent: " + color + ";");
        
        Label valueLabel = new Label(current + "/" + max);
        valueLabel.setStyle("-fx-text-fill: #d4c4a8;");
        
        container.getChildren().addAll(nameLabel, bar, valueLabel);
        
        return container;
    }
    
    private void addStatRow(GridPane grid, int row, String statName, int value) {
        Label nameLabel = new Label(statName + ":");
        nameLabel.setStyle("-fx-text-fill: #888888;");
        
        int modifier = (value - 10) / 2;
        String modStr = modifier >= 0 ? "+" + modifier : String.valueOf(modifier);
        
        Label valueLabel = new Label(value + " (" + modStr + ")");
        valueLabel.setStyle("-fx-text-fill: #d4c4a8;");
        
        // Add tooltip
        Tooltip tooltip = TooltipFactory.createStatTooltip(statName, value);
        Tooltip.install(nameLabel, tooltip);
        Tooltip.install(valueLabel, tooltip);
        
        grid.add(nameLabel, 0, row);
        grid.add(valueLabel, 1, row);
    }
    
    private String getRelationshipColor(int value) {
        if (value >= 80) return "#4a90d9";
        if (value >= 60) return "#228b22";
        if (value >= 40) return "#c9a227";
        if (value >= 20) return "#ff8c00";
        return "#dc143c";
    }
    
    private String getRarityColor(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> "#a89878";
            case UNCOMMON -> "#228b22";
            case RARE -> "#4169e1";
            case EPIC -> "#9932cc";
            case LEGENDARY -> "#ff8c00";
            case MYTHIC -> "#dc143c";
        };
    }
    
    public VBox getRoot() {
        return root;
    }
}
