package com.rpg.ui.fx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX Application entry point for The Shattered Records
 * Dark Fantasy theme with RPG Classic layout
 */
public class GameApplication extends Application {
    
    private static final String TITLE = "The Shattered Records";
    
    private GameController controller;
    private GameView gameView;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize the game controller
        controller = new GameController();
        
        // Create main view (it creates its own Scene internally)
        gameView = new GameView(primaryStage, controller);
        
        // Configure stage - GameView already set up the scene and stage
        primaryStage.setTitle(TITLE);
        
        // Handle close request
        primaryStage.setOnCloseRequest(event -> {
            controller.shutdown();
        });
        
        primaryStage.show();
        
        // Show title screen
        controller.showTitleScreen();
    }
    
    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }
    
    /**
     * Launch the JavaFX application
     */
    public static void launchApp(String[] args) {
        launch(args);
    }
}
