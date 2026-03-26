package com.rpg.ui.fx.components;

import com.rpg.combat.Ability;
import com.rpg.combat.StatusEffect;
import com.rpg.models.*;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * Factory for creating rich tooltips with detailed information.
 * Main feature for JavaFX version - hover tooltips for stats, abilities, status effects.
 */
public class TooltipFactory {
    
    // Tooltip display settings
    private static final Duration SHOW_DELAY = Duration.millis(200);
    private static final Duration HIDE_DELAY = Duration.millis(100);
    
    /**
     * Create a detailed tooltip for a character (player or companion)
     */
    public static Tooltip createCharacterTooltip(com.rpg.models.Character character) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        // Name - need to get class differently for Player vs Companion
        TextFlow header = new TextFlow();
        Text nameText = new Text(character.getName());
        nameText.getStyleClass().add("tooltip-title");
        
        String className = "Unknown";
        if (character instanceof Player player) {
            className = player.getCurrentClass().getName();
        } else if (character instanceof Companion companion) {
            className = companion.getCurrentClass().getName();
        }
        
        Text classText = new Text("\nLevel " + character.getLevel() + " " + className);
        classText.getStyleClass().add("tooltip-stat");
        header.getChildren().addAll(nameText, classText);
        
        // Stats
        Stats stats = character.getBaseStats();
        TextFlow statsFlow = new TextFlow();
        statsFlow.getChildren().addAll(
            createStatLine("STR", stats.getStrength()),
            createStatLine("DEX", stats.getDexterity()),
            createStatLine("VIT", stats.getVitality()),
            createStatLine("INT", stats.getIntelligence()),
            createStatLine("WIS", stats.getWisdom()),
            createStatLine("LUK", stats.getLuck())
        );
        
        // HP/MP
        TextFlow resourceFlow = new TextFlow();
        Text hpText = new Text("HP: " + character.getCurrentHP() + "/" + character.getMaxHP());
        hpText.setStyle("-fx-fill: #dc143c;");
        resourceFlow.getChildren().add(hpText);
        
        // Element
        Text elementText = new Text("\nElement: " + character.getElementAffinity().name());
        elementText.getStyleClass().add("element-" + character.getElementAffinity().name().toLowerCase());
        
        content.getChildren().addAll(header, new Text(""), resourceFlow, statsFlow, elementText);
        
        // Status effects
        if (!character.getStatusEffects().isEmpty()) {
            Text statusTitle = new Text("\nStatus Effects:");
            statusTitle.getStyleClass().add("tooltip-title");
            content.getChildren().add(statusTitle);
            
            for (StatusEffect effect : character.getStatusEffects()) {
                Text effectText = new Text("  " + effect.getType().getDisplayName() + " (" + effect.getDuration() + " turns)");
                if (effect.getType().isBuff()) {
                    effectText.getStyleClass().add("status-buff");
                } else {
                    effectText.getStyleClass().add("status-debuff");
                }
                content.getChildren().add(effectText);
            }
        }
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a detailed tooltip for a companion with relationship info
     */
    public static Tooltip createCompanionTooltip(Companion companion) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        // Name and class
        TextFlow header = new TextFlow();
        Text nameText = new Text(companion.getName());
        nameText.getStyleClass().add("tooltip-title");
        Text classText = new Text("\nLevel " + companion.getLevel() + " " + companion.getCurrentClass().getName());
        classText.getStyleClass().add("tooltip-stat");
        header.getChildren().addAll(nameText, classText);
        
        // Loyalty (relationship)
        int loyalty = companion.getLoyaltyLevel();
        String loyaltyStatus = companion.getLoyaltyDescription();
        Text relText = new Text("\nLoyalty: " + loyalty + "/100 (" + loyaltyStatus + ")");
        relText.setStyle(getRelationshipColor(loyalty));
        
        // Personality type
        Text personalityText = new Text("\nPersonality: " + companion.getPersonalityType());
        personalityText.getStyleClass().add("tooltip-description");
        
        // Breaking point warning
        if (loyalty < 30) {
            Text warningText = new Text("\n[!] Loyalty critical - may leave party!");
            warningText.setStyle("-fx-fill: #dc143c; -fx-font-weight: bold;");
            content.getChildren().addAll(header, relText, personalityText, warningText);
        } else {
            content.getChildren().addAll(header, relText, personalityText);
        }
        
        // Stats
        Stats stats = companion.getBaseStats();
        TextFlow statsFlow = new TextFlow();
        statsFlow.getChildren().addAll(
            createStatLine("STR", stats.getStrength()),
            createStatLine("DEX", stats.getDexterity()),
            createStatLine("VIT", stats.getVitality()),
            createStatLine("INT", stats.getIntelligence()),
            createStatLine("WIS", stats.getWisdom()),
            createStatLine("LUK", stats.getLuck())
        );
        content.getChildren().add(statsFlow);
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a tooltip for an ability
     */
    public static Tooltip createAbilityTooltip(Ability ability) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        // Name
        Text nameText = new Text(ability.getName());
        nameText.getStyleClass().add("tooltip-title");
        
        // Type and cost
        Text typeText = new Text("\nType: " + ability.getDamageType().getDisplayName());
        typeText.getStyleClass().add("tooltip-stat");
        
        Text costText = new Text("\nFocus Cost: " + ability.getFocusCost());
        costText.setStyle("-fx-fill: #4a90d9;");
        
        // Damage/Power
        Text powerText = new Text("\nPower: " + ability.getBasePower());
        powerText.getStyleClass().add("tooltip-stat");
        
        // Element
        if (ability.getElement() != null && ability.getElement() != Element.NONE) {
            Text elementText = new Text("\nElement: " + ability.getElement().name());
            elementText.getStyleClass().add("element-" + ability.getElement().name().toLowerCase());
            content.getChildren().addAll(nameText, typeText, costText, powerText, elementText);
        } else {
            content.getChildren().addAll(nameText, typeText, costText, powerText);
        }
        
        // Description
        if (ability.getDescription() != null && !ability.getDescription().isEmpty()) {
            Text descText = new Text("\n\n" + ability.getDescription());
            descText.getStyleClass().add("tooltip-description");
            descText.setWrappingWidth(250);
            content.getChildren().add(descText);
        }
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a tooltip for a status effect
     */
    public static Tooltip createStatusEffectTooltip(StatusEffect effect) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        // Name
        Text nameText = new Text(effect.getType().getDisplayName());
        nameText.getStyleClass().add("tooltip-title");
        if (effect.getType().isBuff()) {
            nameText.getStyleClass().add("status-buff");
        } else {
            nameText.getStyleClass().add("status-debuff");
        }
        
        // Duration
        Text durationText = new Text("\nDuration: " + effect.getDuration() + " turns remaining");
        durationText.getStyleClass().add("tooltip-stat");
        
        // Stacks info if stackable
        if (effect.isStackable() && effect.getStacks() > 1) {
            Text stacksText = new Text("\nStacks: " + effect.getStacks() + "/" + effect.getMaxStacks());
            stacksText.getStyleClass().add("tooltip-stat");
            content.getChildren().addAll(nameText, durationText, stacksText);
        } else {
            content.getChildren().addAll(nameText, durationText);
        }
        
        // Potency info
        Text potencyText = new Text("\nPotency: " + String.format("%.1f", effect.getPotency()));
        potencyText.getStyleClass().add("tooltip-description");
        content.getChildren().add(potencyText);
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a tooltip for an enemy
     */
    public static Tooltip createEnemyTooltip(Enemy enemy) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        // Name and level
        Text nameText = new Text(enemy.getName());
        nameText.getStyleClass().add("tooltip-title");
        nameText.setStyle("-fx-fill: #dc143c;");
        
        Text levelText = new Text("\nLevel " + enemy.getLevel());
        levelText.getStyleClass().add("tooltip-stat");
        
        // HP
        Text hpText = new Text("\nHP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
        hpText.setStyle("-fx-fill: #dc143c;");
        
        // Element/Weakness
        Text elementText = new Text("\nElement: " + enemy.getElementAffinity().name());
        elementText.getStyleClass().add("element-" + enemy.getElementAffinity().name().toLowerCase());
        
        // Weaknesses hint
        Element weakness = getWeakness(enemy.getElementAffinity());
        if (weakness != Element.NONE) {
            Text weakText = new Text("\nWeak to: " + weakness.name());
            weakText.setStyle("-fx-fill: #4a90d9; -fx-font-style: italic;");
            content.getChildren().addAll(nameText, levelText, hpText, elementText, weakText);
        } else {
            content.getChildren().addAll(nameText, levelText, hpText, elementText);
        }
        
        // Status effects
        if (!enemy.getStatusEffects().isEmpty()) {
            Text statusTitle = new Text("\nStatus Effects:");
            statusTitle.getStyleClass().add("tooltip-stat");
            content.getChildren().add(statusTitle);
            
            for (StatusEffect effect : enemy.getStatusEffects()) {
                Text effectText = new Text("\n  " + effect.getType().getDisplayName() + " (" + effect.getDuration() + ")");
                effectText.getStyleClass().add(effect.getType().isBuff() ? "status-buff" : "status-debuff");
                content.getChildren().add(effectText);
            }
        }
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a tooltip for a stat value
     */
    public static Tooltip createStatTooltip(String statName, int value) {
        VBox content = new VBox(3);
        content.setPadding(new Insets(5));
        
        Text nameText = new Text(statName);
        nameText.getStyleClass().add("tooltip-title");
        
        int modifier = (value - 10) / 2;
        String modStr = modifier >= 0 ? "+" + modifier : String.valueOf(modifier);
        
        Text valueText = new Text("\nValue: " + value + " (" + modStr + ")");
        valueText.getStyleClass().add("tooltip-stat");
        
        Text descText = new Text("\n" + getStatDescription(statName));
        descText.getStyleClass().add("tooltip-description");
        descText.setWrappingWidth(220);
        
        Text effectText = new Text("\n" + getStatEffect(statName, value, modifier));
        effectText.setStyle("-fx-fill: #c9a227; -fx-font-size: 10px;");
        effectText.setWrappingWidth(220);
        
        content.getChildren().addAll(nameText, valueText, descText, effectText);
        
        return createStyledTooltip(content);
    }
    
    /**
     * Create a tooltip for alignment values
     */
    public static Tooltip createAlignmentTooltip(int honor, int compassion) {
        VBox content = new VBox(5);
        content.setPadding(new Insets(5));
        
        Text title = new Text("Alignment");
        title.getStyleClass().add("tooltip-title");
        
        // Honor axis
        String honorLabel = honor >= 0 ? "Honorable" : "Pragmatic";
        Text honorText = new Text("\n" + honorLabel + ": " + Math.abs(honor));
        honorText.setStyle(honor >= 0 ? "-fx-fill: #c9a227;" : "-fx-fill: #888888;");
        
        // Compassion axis
        String compLabel = compassion >= 0 ? "Compassionate" : "Ruthless";
        Text compText = new Text("\n" + compLabel + ": " + Math.abs(compassion));
        compText.setStyle(compassion >= 0 ? "-fx-fill: #4a90d9;" : "-fx-fill: #dc143c;");
        
        // Overall alignment title
        String alignmentTitle = getAlignmentTitle(honor, compassion);
        Text alignText = new Text("\n\nAlignment: " + alignmentTitle);
        alignText.getStyleClass().add("tooltip-description");
        
        content.getChildren().addAll(title, honorText, compText, alignText);
        
        return createStyledTooltip(content);
    }
    
    // ==================== Helper Methods ====================
    
    private static Tooltip createStyledTooltip(VBox content) {
        Tooltip tooltip = new Tooltip();
        tooltip.setGraphic(content);
        tooltip.getStyleClass().add("game-tooltip");
        tooltip.setShowDelay(SHOW_DELAY);
        tooltip.setHideDelay(HIDE_DELAY);
        tooltip.setShowDuration(Duration.INDEFINITE);
        return tooltip;
    }
    
    private static Text createStatLine(String stat, int value) {
        String modifier = getModifier(value);
        Text text = new Text("\n" + stat + ": " + value + " (" + modifier + ")");
        text.getStyleClass().add("tooltip-stat");
        return text;
    }
    
    private static String getModifier(int value) {
        int mod = (value - 10) / 2;
        return mod >= 0 ? "+" + mod : String.valueOf(mod);
    }
    
    private static String getRelationshipStatus(int value) {
        if (value >= 80) return "Devoted";
        if (value >= 60) return "Loyal";
        if (value >= 40) return "Friendly";
        if (value >= 20) return "Neutral";
        return "Strained";
    }
    
    private static String getRelationshipColor(int value) {
        if (value >= 80) return "-fx-fill: #4a90d9;";
        if (value >= 60) return "-fx-fill: #228b22;";
        if (value >= 40) return "-fx-fill: #c9a227;";
        if (value >= 20) return "-fx-fill: #ff8c00;";
        return "-fx-fill: #dc143c;";
    }
    
    private static Element getWeakness(Element element) {
        // Based on advantage wheel: Fire > Wind > Earth > Water > Fire, Light <-> Dark
        return switch (element) {
            case FIRE -> Element.WATER;
            case WATER -> Element.EARTH;
            case EARTH -> Element.WIND;
            case WIND -> Element.FIRE;
            case LIGHT -> Element.DARK;
            case DARK -> Element.LIGHT;
            default -> Element.NONE;
        };
    }
    
    private static String getStatDescription(String stat) {
        return switch (stat.toUpperCase()) {
            case "STR", "STRENGTH" -> "Physical power. Increases melee damage and carrying capacity.";
            case "DEX", "DEXTERITY" -> "Agility and reflexes. Affects accuracy, evasion, and initiative.";
            case "VIT", "VITALITY" -> "Endurance and toughness. Increases max HP and resistance to status effects.";
            case "INT", "INTELLIGENCE" -> "Mental acuity. Increases magic damage and Focus generation.";
            case "WIS", "WISDOM" -> "Perception and insight. Affects magic resistance, healing power, and companion loyalty gains.";
            case "LUK", "LUCK" -> "Fortune and fate. Affects critical hit chance, drop rates, and rare outcomes.";
            default -> "";
        };
    }
    
    private static String getStatEffect(String stat, int value, int modifier) {
        return switch (stat.toUpperCase()) {
            case "STR", "STRENGTH" -> "Melee damage: +" + modifier + " | Carry: " + (value * 5) + " lbs";
            case "DEX", "DEXTERITY" -> "Evasion: " + (modifier + 5) + "% | Flee chance: +" + modifier + "%";
            case "VIT", "VITALITY" -> "Max HP: +" + (modifier * 10) + " | DoT resist: +" + modifier + "%";
            case "INT", "INTELLIGENCE" -> "Magic power: +" + modifier + " | Focus/turn: +" + (modifier / 2);
            case "WIS", "WISDOM" -> "Magic resist: +" + modifier + "% | Healing: +" + modifier + "%";
            case "LUK", "LUCK" -> "Crit chance: " + (3 + modifier) + "% | Drop rate: +" + modifier + "%";
            default -> "";
        };
    }
    
    private static String getAlignmentTitle(int honor, int compassion) {
        boolean isHonorable = honor >= 20;
        boolean isPragmatic = honor <= -20;
        boolean isCompassionate = compassion >= 20;
        boolean isRuthless = compassion <= -20;
        
        if (isHonorable && isCompassionate) return "Paragon";
        if (isHonorable && isRuthless) return "Justicar";
        if (isPragmatic && isCompassionate) return "Liberator";
        if (isPragmatic && isRuthless) return "Tyrant";
        if (isHonorable) return "Knight";
        if (isPragmatic) return "Realist";
        if (isCompassionate) return "Healer";
        if (isRuthless) return "Enforcer";
        return "Neutral";
    }
}
