package com.rpg.ui.fx.views;

import com.rpg.combat.Ability;
import com.rpg.models.*;
import com.rpg.ui.fx.GameController;
import com.rpg.ui.fx.components.TooltipFactory;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Combat view showing enemies, party status, and combat options.
 */
public class CombatView {
    
    private final GameController controller;
    private final VBox root;
    
    // UI Components
    private HBox enemyArea;
    private ListView<String> combatLogView;
    private VBox actionPanel;
    private Label turnIndicator;
    private Label selectedTargetLabel;
    
    private Enemy selectedTarget = null;
    
    public CombatView(GameController controller) {
        this.controller = controller;
        this.root = new VBox(10);
        root.getStyleClass().add("combat-area");
        root.setPadding(new Insets(15));
        buildUI();
    }
    
    private void buildUI() {
        // Turn indicator
        turnIndicator = new Label("Battle!");
        turnIndicator.getStyleClass().add("panel-title");
        turnIndicator.setStyle("-fx-font-size: 18px; -fx-text-fill: #dc143c; -fx-font-weight: bold;");
        
        // Selected target display
        selectedTargetLabel = new Label("No target selected - click an enemy to target it");
        selectedTargetLabel.getStyleClass().add("tooltip-stat");
        selectedTargetLabel.setStyle("-fx-text-fill: #c9a227;");
        
        // Enemy display area
        enemyArea = new HBox(10);
        enemyArea.setAlignment(Pos.CENTER);
        enemyArea.setPadding(new Insets(15));
        enemyArea.setStyle("-fx-background-color: linear-gradient(to bottom, #1a0a0a, #0d0505); " +
                          "-fx-border-color: #8b0000; -fx-border-width: 1; -fx-border-radius: 5;");
        enemyArea.setMinHeight(120);
        
        // Combat log
        combatLogView = new ListView<>();
        combatLogView.getStyleClass().add("combat-log");
        combatLogView.setPrefHeight(180);
        combatLogView.setItems(controller.getCombatLog());
        controller.getCombatLog().addListener((ListChangeListener<String>) change -> {
            combatLogView.scrollTo(controller.getCombatLog().size() - 1);
        });
        
        // Action buttons
        actionPanel = new VBox(8);
        actionPanel.setPadding(new Insets(10, 0, 0, 0));
        actionPanel.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(turnIndicator, selectedTargetLabel, enemyArea, combatLogView, actionPanel);
        VBox.setVgrow(combatLogView, Priority.ALWAYS);
    }
    
    public void refresh() {
        enemyArea.getChildren().clear();
        actionPanel.getChildren().clear();
        selectedTarget = null;
        selectedTargetLabel.setText("No target selected - click an enemy to target it");
        
        if (controller.isInCombat()) {
            turnIndicator.setText("Battle in Progress!");
            
            // Build enemy cards
            List<Enemy> enemies = controller.getCurrentEnemies();
            if (enemies != null) {
                for (Enemy enemy : enemies) {
                    enemyArea.getChildren().add(createEnemyCard(enemy));
                }
            }
            
            // Build action buttons
            buildActionButtons();
        } else {
            // Show battle summary
            showBattleSummary();
        }
    }
    
    private void showBattleSummary() {
        turnIndicator.setText("Battle Ended");
        
        GameController.BattleResult result = controller.getLastBattleResult();
        if (result == null) return;
        
        // Summary panel
        VBox summaryPanel = new VBox(15);
        summaryPanel.setAlignment(Pos.CENTER);
        summaryPanel.setPadding(new Insets(30));
        summaryPanel.setMaxWidth(400);
        
        if (result.victory) {
            summaryPanel.setStyle("-fx-background-color: #1a2a1a; -fx-border-color: #228b22; " +
                                 "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
            
            Text victoryText = new Text("VICTORY!");
            victoryText.setStyle("-fx-fill: #228b22; -fx-font-size: 28px; -fx-font-weight: bold;");
            
            Text expText = new Text("EXP gained: +" + result.expGained);
            expText.setStyle("-fx-fill: #c9a227; -fx-font-size: 16px;");
            
            Text goldText = new Text("Gold looted: +" + result.goldLooted);
            goldText.setStyle("-fx-fill: #ffd700; -fx-font-size: 16px;");
            
            summaryPanel.getChildren().addAll(victoryText, expText, goldText);
            
            if (result.leveledUp) {
                Text levelUpText = new Text("*** LEVEL UP! ***");
                levelUpText.setStyle("-fx-fill: #ff8c00; -fx-font-size: 20px; -fx-font-weight: bold;");
                
                Text pointsText = new Text("You have " + controller.getPlayer().getAvailableStatPoints() + " stat points to allocate!");
                pointsText.setStyle("-fx-fill: #228b22; -fx-font-size: 14px;");
                
                summaryPanel.getChildren().addAll(levelUpText, pointsText);
            }
        } else {
            summaryPanel.setStyle("-fx-background-color: #2a1a1a; -fx-border-color: #8b0000; " +
                                 "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
            
            Text defeatText = new Text("DEFEAT");
            defeatText.setStyle("-fx-fill: #dc143c; -fx-font-size: 28px; -fx-font-weight: bold;");
            
            Text infoText = new Text(result.summary);
            infoText.setStyle("-fx-fill: #a89878; -fx-font-size: 14px;");
            
            summaryPanel.getChildren().addAll(defeatText, infoText);
        }
        
        // Show party status in summary
        Text partyTitle = new Text("Party Status");
        partyTitle.setStyle("-fx-fill: #a89878; -fx-font-size: 12px; -fx-font-weight: bold;");
        summaryPanel.getChildren().add(partyTitle);
        
        for (com.rpg.models.Character c : controller.getAliveParty()) {
            Text memberText = new Text(c.getName() + " - HP: " + c.getCurrentHP() + "/" + c.getMaxHP());
            memberText.setStyle("-fx-fill: #a89878; -fx-font-size: 11px;");
            summaryPanel.getChildren().add(memberText);
        }
        
        // Continue button
        Button continueBtn = new Button("Continue");
        continueBtn.getStyleClass().add("title-button");
        continueBtn.setPrefWidth(150);
        continueBtn.setOnAction(e -> controller.showExploration());
        summaryPanel.getChildren().add(continueBtn);
        
        // Replace content
        root.getChildren().clear();
        root.getChildren().add(summaryPanel);
    }
    
    private VBox createEnemyCard(Enemy enemy) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setMinWidth(130);
        card.setAlignment(Pos.CENTER);
        
        boolean alive = enemy.isAlive();
        
        if (alive) {
            card.setStyle("-fx-background-color: #2a1515; -fx-border-color: #8b0000; " +
                         "-fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5; " +
                         "-fx-cursor: hand;");
        } else {
            card.setStyle("-fx-background-color: #151515; -fx-border-color: #444444; " +
                         "-fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5; " +
                         "-fx-opacity: 0.5;");
        }
        
        Text name = new Text(enemy.getName());
        name.setStyle("-fx-fill: " + (alive ? "#dc143c" : "#666666") + "; -fx-font-weight: bold;");
        
        Text level = new Text("Lv." + enemy.getLevel());
        level.setStyle("-fx-fill: #a89878; -fx-font-size: 10px;");
        
        Text hp = new Text(alive ? "HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() : "DEFEATED");
        hp.setStyle("-fx-fill: " + (alive ? "#dc143c" : "#666666") + "; -fx-font-size: 11px;");
        
        // HP bar
        ProgressBar hpBar = new ProgressBar();
        hpBar.setProgress(alive ? (double) enemy.getCurrentHP() / enemy.getMaxHP() : 0);
        hpBar.setPrefWidth(100);
        hpBar.setPrefHeight(8);
        hpBar.setStyle("-fx-accent: #dc143c;");
        
        Text element = new Text(enemy.getElementAffinity().name());
        element.setStyle("-fx-fill: #888888; -fx-font-size: 9px;");
        
        card.getChildren().addAll(name, level, hpBar, hp, element);
        
        // Make alive enemies clickable
        if (alive) {
            card.setOnMouseClicked(e -> {
                selectedTarget = enemy;
                selectedTargetLabel.setText("Target: " + enemy.getName() + " (HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() + ")");
                highlightTarget();
            });
            
            card.setOnMouseEntered(e -> {
                card.setStyle("-fx-background-color: #3a1a1a; -fx-border-color: #ff4500; " +
                             "-fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5; " +
                             "-fx-cursor: hand;");
            });
            card.setOnMouseExited(e -> {
                if (selectedTarget != enemy) {
                    card.setStyle("-fx-background-color: #2a1515; -fx-border-color: #8b0000; " +
                                 "-fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5; " +
                                 "-fx-cursor: hand;");
                }
            });
        }
        
        return card;
    }
    
    private void highlightTarget() {
        enemyArea.getChildren().clear();
        List<Enemy> enemies = controller.getCurrentEnemies();
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                enemyArea.getChildren().add(createEnemyCard(enemy));
            }
        }
    }
    
    private void buildActionButtons() {
        if (!controller.isInCombat()) return;
        
        Player player = controller.getPlayer();
        if (player == null || !player.isAlive()) return;
        
        // Row 1: Attack + Defend
        HBox row1 = new HBox(10);
        row1.setAlignment(Pos.CENTER);
        
        Button attackBtn = createCombatButton("Attack", "#dc143c");
        attackBtn.setOnAction(e -> {
            Enemy target = selectedTarget != null && selectedTarget.isAlive() ? selectedTarget : controller.getRandomAliveEnemy();
            if (target != null) {
                controller.playerAttack(target);
            }
        });
        
        Button defendBtn = createCombatButton("Defend", "#4a90d9");
        defendBtn.setOnAction(e -> controller.playerDefend());
        
        Button fleeBtn = createCombatButton("Flee", "#888888");
        fleeBtn.setOnAction(e -> controller.playerFlee());
        
        row1.getChildren().addAll(attackBtn, defendBtn, fleeBtn);
        
        // Row 2: Abilities
        HBox row2 = new HBox(8);
        row2.setAlignment(Pos.CENTER);
        
        List<Ability> abilities = player.getCurrentClass().getClassAbilities();
        if (abilities != null && !abilities.isEmpty()) {
            for (Ability ability : abilities) {
                Button abBtn = createCombatButton(ability.getName(), getElementColor(ability.getElement()));
                
                boolean canUse = player.canUseAbility(ability) && ability.isReady();
                abBtn.setDisable(!canUse);
                
                String focusCost = "Cost: " + ability.getFocusCost() + " Focus";
                Tooltip tip = TooltipFactory.createAbilityTooltip(ability);
                abBtn.setTooltip(tip);
                
                abBtn.setOnAction(e -> {
                    Enemy target = selectedTarget != null && selectedTarget.isAlive() ? selectedTarget : controller.getRandomAliveEnemy();
                    if (target != null) {
                        controller.playerUseAbility(ability, target);
                    }
                });
                
                row2.getChildren().add(abBtn);
            }
        }
        
        actionPanel.getChildren().addAll(row1, row2);
    }
    
    private Button createCombatButton(String text, String color) {
        Button btn = new Button(text);
        btn.getStyleClass().add("action-button");
        btn.setPrefWidth(120);
        btn.setPrefHeight(35);
        btn.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2;");
        return btn;
    }
    
    private String getElementColor(Element element) {
        return switch (element) {
            case FIRE -> "#ff4500";
            case WATER -> "#4169e1";
            case EARTH -> "#8b4513";
            case WIND -> "#87ceeb";
            case LIGHT -> "#ffd700";
            case DARK -> "#4b0082";
            case NEUTRAL, NONE -> "#c9a227";
        };
    }
    
    public VBox getRoot() {
        return root;
    }
}
