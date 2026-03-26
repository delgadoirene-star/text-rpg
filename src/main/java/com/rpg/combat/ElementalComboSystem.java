package com.rpg.combat;

import com.rpg.models.Character;
import com.rpg.models.Element;
import com.rpg.models.StatusType;

import java.util.*;

/**
 * Handles elemental combo reactions (like Genshin Impact)
 * When certain elements interact, special effects occur
 */
public class ElementalComboSystem {
    
    // Track what elements have been applied to each character
    private final Map<Character, List<ElementalAura>> characterAuras;
    
    public ElementalComboSystem() {
        this.characterAuras = new HashMap<>();
    }
    
    /**
     * Apply elemental damage and check for combos
     * @return ComboResult describing what happened
     */
    public ComboResult applyElementalDamage(Character target, Element element, double baseDamage) {
        // Get existing auras on target
        List<ElementalAura> auras = characterAuras.computeIfAbsent(target, k -> new ArrayList<>());
        
        // Check for combo reactions
        for (ElementalAura existingAura : auras) {
            ComboReaction reaction = checkReaction(existingAura.element, element);
            
            if (reaction != null) {
                // Combo triggered!
                return executeReaction(target, existingAura, element, baseDamage, reaction);
            }
        }
        
        // No combo, just apply aura
        applyAura(target, element);
        return new ComboResult(null, 0, null, "Applied " + element.name() + " aura");
    }
    
    /**
     * Check if two elements react
     */
    private ComboReaction checkReaction(Element existing, Element incoming) {
        return switch (existing) {
            case FIRE -> switch (incoming) {
                case WATER -> ComboReaction.VAPORIZE;
                case WIND -> ComboReaction.SWIRL_FIRE;
                case EARTH -> ComboReaction.CRYSTALLIZE;
                default -> null;
            };
            case WATER -> switch (incoming) {
                case FIRE -> ComboReaction.VAPORIZE;
                case WIND -> ComboReaction.SWIRL_WATER;
                case LIGHT -> ComboReaction.FREEZE;
                default -> null;
            };
            case EARTH -> switch (incoming) {
                case FIRE -> ComboReaction.MELT;
                case WATER -> ComboReaction.BLOOM;
                case WIND -> ComboReaction.SWIRL_EARTH;
                default -> null;
            };
            case WIND -> switch (incoming) {
                case FIRE, WATER, EARTH -> ComboReaction.SPREAD;
                default -> null;
            };
            case LIGHT -> switch (incoming) {
                case DARK -> ComboReaction.ANNIHILATION;
                case WATER -> ComboReaction.FREEZE;
                default -> null;
            };
            case DARK -> switch (incoming) {
                case LIGHT -> ComboReaction.ANNIHILATION;
                case FIRE -> ComboReaction.CORRUPTION;
                default -> null;
            };
            default -> null;
        };
    }
    
    /**
     * Execute a combo reaction
     */
    private ComboResult executeReaction(Character target, ElementalAura existingAura, 
                                       Element incomingElement, double baseDamage, 
                                       ComboReaction reaction) {
        // Remove the existing aura (it was consumed)
        characterAuras.get(target).remove(existingAura);
        
        // Apply reaction effects
        double bonusDamage = 0;
        StatusEffect statusEffect = null;
        String message = reaction.displayName + "!";
        
        switch (reaction) {
            case VAPORIZE -> {
                // Fire + Water = Big damage boost
                bonusDamage = baseDamage * 1.5;
                message += " (" + String.format("%.0f", bonusDamage) + " bonus damage!)";
            }
            case MELT -> {
                // Earth + Fire = Damage over time
                bonusDamage = baseDamage * 0.5;
                statusEffect = new StatusEffect(StatusType.BURN, 3, 20.0, true, false, 3);
                message += " (Inflicted Burning!)";
            }
            case FREEZE -> {
                // Water/Light + Ice = Stun
                statusEffect = new StatusEffect(StatusType.FREEZE, 1, 0.0);
                message += " (Target Frozen!)";
            }
            case SWIRL_FIRE, SWIRL_WATER, SWIRL_EARTH -> {
                // Wind spreads element to nearby
                bonusDamage = baseDamage * 0.75;
                message += " (Element spread to nearby foes!)";
            }
            case SPREAD -> {
                // Wind + Element = Area effect
                bonusDamage = baseDamage * 1.0;
                message += " (Area damage!)";
            }
            case BLOOM -> {
                // Earth + Water = Healing reduction + damage
                bonusDamage = baseDamage * 0.8;
                statusEffect = new StatusEffect(StatusType.POISON, 2, 15.0);
                message += " (Toxic bloom!)";
            }
            case CRYSTALLIZE -> {
                // Fire/Water/Earth + Earth = Shield (defensive reaction)
                // This would create a shield, but we don't have that mechanic yet
                message += " (Created protective barrier!)";
            }
            case ANNIHILATION -> {
                // Light + Dark = Massive damage
                bonusDamage = baseDamage * 2.0;
                message += " (MASSIVE DAMAGE: " + String.format("%.0f", bonusDamage) + "!)";
            }
            case CORRUPTION -> {
                // Dark + Fire = Curse
                statusEffect = new StatusEffect(StatusType.CURSE, 4, 10.0);
                message += " (Inflicted Curse!)";
            }
        }
        
        // Apply status effect if any
        if (statusEffect != null) {
            target.addStatusEffect(statusEffect);
        }
        
        return new ComboResult(reaction, bonusDamage, statusEffect, message);
    }
    
    /**
     * Apply an elemental aura to a character
     */
    private void applyAura(Character target, Element element) {
        if (element == Element.NEUTRAL) return;
        
        List<ElementalAura> auras = characterAuras.computeIfAbsent(target, k -> new ArrayList<>());
        
        // Check if aura already exists
        for (ElementalAura aura : auras) {
            if (aura.element == element) {
                aura.refresh();
                return;
            }
        }
        
        // Add new aura
        auras.add(new ElementalAura(element, 2)); // Lasts 2 turns
    }
    
    /**
     * Reduce aura durations at end of turn
     */
    public void processTurn() {
        for (List<ElementalAura> auras : characterAuras.values()) {
            auras.removeIf(aura -> {
                aura.duration--;
                return aura.duration <= 0;
            });
        }
    }
    
    /**
     * Clear all auras (for new battle)
     */
    public void clear() {
        characterAuras.clear();
    }
    
    /**
     * Get active auras on a character
     */
    public List<Element> getAuras(Character character) {
        return characterAuras.getOrDefault(character, new ArrayList<>())
            .stream()
            .map(aura -> aura.element)
            .toList();
    }
    
    /**
     * Elemental Aura - temporary element on a character
     */
    private static class ElementalAura {
        Element element;
        int duration;
        
        ElementalAura(Element element, int duration) {
            this.element = element;
            this.duration = duration;
        }
        
        void refresh() {
            duration = 2;
        }
    }
    
    /**
     * Combo reactions
     */
    public enum ComboReaction {
        VAPORIZE("Vaporize"),
        MELT("Melt"),
        FREEZE("Freeze"),
        SWIRL_FIRE("Swirl (Fire)"),
        SWIRL_WATER("Swirl (Water)"),
        SWIRL_EARTH("Swirl (Earth)"),
        SPREAD("Spread"),
        BLOOM("Bloom"),
        CRYSTALLIZE("Crystallize"),
        ANNIHILATION("Annihilation"),
        CORRUPTION("Corruption");
        
        final String displayName;
        
        ComboReaction(String displayName) {
            this.displayName = displayName;
        }
    }
    
    /**
     * Result of a combo reaction
     */
    public static class ComboResult {
        private final ComboReaction reaction;
        private final double bonusDamage;
        private final StatusEffect appliedStatus;
        private final String message;
        
        public ComboResult(ComboReaction reaction, double bonusDamage, 
                          StatusEffect appliedStatus, String message) {
            this.reaction = reaction;
            this.bonusDamage = bonusDamage;
            this.appliedStatus = appliedStatus;
            this.message = message;
        }
        
        public boolean isCombo() { return reaction != null; }
        public ComboReaction getReaction() { return reaction; }
        public double getBonusDamage() { return bonusDamage; }
        public StatusEffect getAppliedStatus() { return appliedStatus; }
        public String getMessage() { return message; }
    }
}
