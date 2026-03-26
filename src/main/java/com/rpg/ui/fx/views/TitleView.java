package com.rpg.ui.fx.views;

import com.rpg.ui.fx.GameController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Title screen view with game logo and main menu options.
 */
public class TitleView {
    
    private final GameController controller;
    private final StackPane root;
    
    public TitleView(GameController controller) {
        this.controller = controller;
        this.root = new StackPane();
        initializeView();
    }
    
    private void initializeView() {
        root.getStyleClass().add("title-screen");
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));
        
        // Title
        Text title = new Text("DARK FANTASY");
        title.getStyleClass().add("title-text");
        
        Text subtitle = new Text("A Tale of Honor and Shadow");
        subtitle.getStyleClass().add("subtitle-text");
        
        // Spacer
        VBox spacer = new VBox();
        spacer.setMinHeight(60);
        
        // Menu buttons
        VBox menuButtons = new VBox(15);
        menuButtons.setAlignment(Pos.CENTER);
        
        Button newGameBtn = new Button("New Game");
        newGameBtn.getStyleClass().add("title-button");
        newGameBtn.setOnAction(e -> controller.showCharacterCreation());
        
        Button continueBtn = new Button("Continue");
        continueBtn.getStyleClass().add("title-button");
        continueBtn.setDisable(true); // TODO: Enable when save exists
        
        Button optionsBtn = new Button("Options");
        optionsBtn.getStyleClass().add("title-button");
        
        Button exitBtn = new Button("Exit");
        exitBtn.getStyleClass().add("title-button");
        exitBtn.setOnAction(e -> System.exit(0));
        
        menuButtons.getChildren().addAll(newGameBtn, continueBtn, optionsBtn, exitBtn);
        
        // Version info
        Text version = new Text("Version 1.2.0");
        version.setStyle("-fx-fill: #666666; -fx-font-size: 12px;");
        
        content.getChildren().addAll(title, subtitle, spacer, menuButtons, new VBox(30), version);
        
        root.getChildren().add(content);
    }
    
    public StackPane getRoot() {
        return root;
    }
}
