package com.rpg.ui.fx.views;

import com.rpg.combat.Difficulty;
import com.rpg.models.*;
import com.rpg.ui.fx.GameController;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Character creation view for starting a new game.
 */
public class CharacterCreationView {
    
    private final GameController controller;
    private final StackPane root;
    
    // Form fields
    private TextField nameField;
    private ComboBox<GameClass> classCombo;
    private ComboBox<Difficulty> difficultyCombo;
    private TextArea classDescription;
    
    public CharacterCreationView(GameController controller) {
        this.controller = controller;
        this.root = new StackPane();
        initializeView();
    }
    
    private void initializeView() {
        root.getStyleClass().add("title-screen");
        
        VBox panel = new VBox(20);
        panel.getStyleClass().add("creation-panel");
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setMaxWidth(600);
        panel.setMaxHeight(700);
        
        // Title
        Text title = new Text("Create Your Character");
        title.getStyleClass().add("creation-title");
        
        // Name input
        VBox nameSection = createSection("Name");
        nameField = new TextField();
        nameField.getStyleClass().add("creation-input");
        nameField.setPromptText("Enter your character's name");
        nameField.setMaxWidth(300);
        nameSection.getChildren().add(nameField);
        
        // Element is fixed to FIRE (like GBF grid system)
        Text elementNote = new Text("Element Affinity: FIRE");
        elementNote.getStyleClass().addAll("creation-label", "element-fire");
        elementNote.setStyle("-fx-fill: #ff4500; -fx-font-size: 14px; -fx-font-weight: bold;");
        VBox elementNoteBox = new VBox(5);
        elementNoteBox.setAlignment(Pos.CENTER);
        elementNoteBox.setPadding(new Insets(0, 50, 0, 50));
        elementNoteBox.getChildren().add(elementNote);
        
        // Class selection
        VBox classSection = createSection("Class");
        classCombo = new ComboBox<>(FXCollections.observableArrayList(
            GameClassRegistry.createWarriorClass(),
            GameClassRegistry.createRogueClass(),
            GameClassRegistry.createScholarClass(),
            GameClassRegistry.createPaladinClass(),
            GameClassRegistry.createMonkClass(),
            GameClassRegistry.createArcanistClass(),
            GameClassRegistry.createGuardianClass(),
            GameClassRegistry.createBrawlerClass()
        ));
        classCombo.getStyleClass().add("creation-input");
        classCombo.setPromptText("Choose your class");
        classCombo.setMaxWidth(300);
        classCombo.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(GameClass item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        classCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GameClass item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        
        // Class description area
        classDescription = new TextArea();
        classDescription.getStyleClass().add("creation-input");
        classDescription.setEditable(false);
        classDescription.setWrapText(true);
        classDescription.setPrefHeight(100);
        classDescription.setMaxWidth(400);
        classDescription.setPromptText("Select a class to see its description");
        
        classCombo.setOnAction(e -> {
            GameClass selected = classCombo.getValue();
            if (selected != null) {
                classDescription.setText(getClassDescription(selected));
            }
        });
        
        classSection.getChildren().addAll(classCombo, classDescription);
        
        // Difficulty selection
        VBox difficultySection = createSection("Difficulty");
        difficultyCombo = new ComboBox<>(FXCollections.observableArrayList(
            Difficulty.STORY, Difficulty.NORMAL, Difficulty.HARD
        ));
        difficultyCombo.getStyleClass().add("creation-input");
        difficultyCombo.setPromptText("Choose difficulty");
        difficultyCombo.setMaxWidth(300);
        difficultyCombo.setValue(Difficulty.NORMAL);
        difficultyCombo.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Difficulty item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.name());
            }
        });
        difficultyCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Difficulty item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.name());
            }
        });
        difficultySection.getChildren().add(difficultyCombo);
        
        // Buttons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 0, 0));
        
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("action-button");
        backBtn.setOnAction(e -> controller.showTitleScreen());
        
        Button startBtn = new Button("Begin Adventure");
        startBtn.getStyleClass().add("title-button");
        startBtn.setOnAction(e -> startGame());
        
        buttons.getChildren().addAll(backBtn, startBtn);
        
        panel.getChildren().addAll(title, nameSection, elementNoteBox, classSection, difficultySection, buttons);
        
        root.getChildren().add(panel);
        StackPane.setAlignment(panel, Pos.CENTER);
    }
    
    private VBox createSection(String labelText) {
        VBox section = new VBox(8);
        section.setAlignment(Pos.CENTER_LEFT);
        section.setPadding(new Insets(0, 50, 0, 50));
        
        Label label = new Label(labelText);
        label.getStyleClass().add("creation-label");
        
        section.getChildren().add(label);
        return section;
    }
    
    private String getClassDescription(GameClass gameClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameClass.getName()).append("\n\n");
        
        // Role description
        sb.append("A ");
        String name = gameClass.getName().toLowerCase();
        switch (name) {
            case "warrior" -> sb.append("front-line combatant specializing in physical damage and durability.");
            case "rogue" -> sb.append("swift striker dealing critical hits and exploiting enemy weaknesses.");
            case "scholar" -> sb.append("master of arcane knowledge wielding powerful elemental magic.");
            case "paladin" -> sb.append("holy warrior combining martial prowess with divine protection.");
            case "monk" -> sb.append("disciplined fighter using ki energy for devastating combos.");
            case "arcanist" -> sb.append("versatile mage manipulating raw magical energy.");
            case "guardian" -> sb.append("stalwart defender protecting allies and controlling the battlefield.");
            case "brawler" -> sb.append("fierce melee fighter relying on raw strength and endurance.");
            default -> sb.append("versatile adventurer ready for any challenge.");
        }
        
        sb.append("\n\nStarting Abilities: ");
        var abilities = gameClass.getClassAbilities();
        if (!abilities.isEmpty()) {
            for (int i = 0; i < abilities.size(); i++) {
                sb.append(abilities.get(i).getName());
                if (i < abilities.size() - 1) sb.append(", ");
            }
        }
        
        return sb.toString();
    }
    
    private void startGame() {
        String name = nameField.getText().trim();
        Element element = Element.FIRE;  // Fixed to FIRE (like GBF grid system)
        GameClass gameClass = classCombo.getValue();
        Difficulty difficulty = difficultyCombo.getValue();
        
        // Validation
        if (name.isEmpty()) {
            showError("Please enter a name for your character.");
            return;
        }
        if (gameClass == null) {
            showError("Please select a class.");
            return;
        }
        if (difficulty == null) {
            difficulty = Difficulty.NORMAL;
        }
        
        // Set difficulty and start the game
        controller.setDifficulty(difficulty);
        controller.startNewGame(name, element, gameClass);
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Character Creation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public StackPane getRoot() {
        return root;
    }
}
