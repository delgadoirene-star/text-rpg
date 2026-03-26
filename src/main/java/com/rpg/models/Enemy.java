package com.rpg.models;

import com.rpg.combat.Ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enemy character with AI patterns and HP triggers
 */
public class Enemy extends Character {
    private final AIPattern aiPattern;
    private final List<HPTrigger> hpTriggers;
    private final Map<String, Integer> abilityUsageCount;
    private final List<Ability> abilities;
    private int turnCount;
    
    public Enemy(String id, String name, int level, Stats baseStats, Element element, AIPattern pattern) {
        super(id, name, level, baseStats, element);
        this.aiPattern = pattern;
        this.hpTriggers = new ArrayList<>();
        this.abilityUsageCount = new HashMap<>();
        this.abilities = new ArrayList<>();
        this.turnCount = 0;
    }
    
    /**
     * Add an HP trigger (executes when HP drops below threshold)
     */
    public void addHPTrigger(double hpPercentThreshold, Runnable action, String description) {
        hpTriggers.add(new HPTrigger(hpPercentThreshold, action, description));
    }
    
    /**
     * Add an ability this enemy can use
     */
    public void addAbility(Ability ability) {
        abilities.add(ability);
        abilityUsageCount.put(ability.getId(), 0);
    }
    
    /**
     * Check and execute HP triggers
     * @return List of triggered descriptions
     */
    public List<String> checkHPTriggers() {
        List<String> triggered = new ArrayList<>();
        double currentHPPercent = getHPPercentage();
        
        for (HPTrigger trigger : hpTriggers) {
            if (!trigger.isTriggered() && currentHPPercent <= trigger.threshold) {
                trigger.execute();
                triggered.add(trigger.description);
            }
        }
        
        return triggered;
    }
    
    /**
     * Get AI decision for this turn
     * @param playerParty List of player characters
     * @return AI decision
     */
    public AIDecision getAIDecision(List<Character> playerParty) {
        turnCount++;
        
        return switch (aiPattern) {
            case AGGRESSIVE -> makeAggressiveDecision(playerParty);
            case DEFENSIVE -> makeDefensiveDecision(playerParty);
            case TACTICAL -> makeTacticalDecision(playerParty);
            case RANDOM -> makeRandomDecision(playerParty);
        };
    }
    
    /**
     * Aggressive AI: Focus on dealing damage
     */
    private AIDecision makeAggressiveDecision(List<Character> playerParty) {
        // Prioritize highest damage abilities
        Ability bestAbility = null;
        double highestPower = 0;
        
        for (Ability ability : abilities) {
            if (ability.isReady() && ability.getBasePower() > highestPower) {
                bestAbility = ability;
                highestPower = ability.getBasePower();
            }
        }
        
        // Target: Lowest HP character (finish them off)
        Character target = playerParty.stream()
            .filter(Character::isAlive)
            .min((a, b) -> Integer.compare(a.getCurrentHP(), b.getCurrentHP()))
            .orElse(playerParty.get(0));
        
        return new AIDecision(bestAbility, target, "Aggressive: Targeting weakest enemy");
    }
    
    /**
     * Defensive AI: Focus on survival
     */
    private AIDecision makeDefensiveDecision(List<Character> playerParty) {
        // If low HP, try to use defensive/healing abilities
        if (getHPPercentage() < 40) {
            for (Ability ability : abilities) {
                if (ability.isReady() && 
                    (ability.getTargetType().name().contains("SELF") || 
                     ability.getDamageType().name().contains("HEAL"))) {
                    return new AIDecision(ability, this, "Defensive: Self-preservation");
                }
            }
        }
        
        // Otherwise, use available abilities cautiously
        Ability safeAbility = abilities.stream()
            .filter(Ability::isReady)
            .findFirst()
            .orElse(null);
        
        Character target = playerParty.stream()
            .filter(Character::isAlive)
            .findFirst()
            .orElse(playerParty.get(0));
        
        return new AIDecision(safeAbility, target, "Defensive: Cautious attack");
    }
    
    /**
     * Tactical AI: Exploits weaknesses and uses strategy
     */
    private AIDecision makeTacticalDecision(List<Character> playerParty) {
        // Try to exploit element weakness
        for (Character player : playerParty) {
            if (!player.isAlive()) continue;
            
            for (Ability ability : abilities) {
                if (!ability.isReady()) continue;
                
                // Check if element has advantage
                double multiplier = ability.getElement().getDamageMultiplier(player.getElementAffinity());
                if (multiplier > 1.0) {
                    return new AIDecision(ability, player, 
                        "Tactical: Exploiting " + player.getElementAffinity() + " weakness");
                }
            }
        }
        
        // Focus on debuffing if no weaknesses
        for (Ability ability : abilities) {
            if (ability.isReady() && !ability.getStatusEffects().isEmpty()) {
                Character target = playerParty.stream()
                    .filter(Character::isAlive)
                    .findFirst()
                    .orElse(playerParty.get(0));
                return new AIDecision(ability, target, "Tactical: Applying debuffs");
            }
        }
        
        // Default to aggressive behavior
        return makeAggressiveDecision(playerParty);
    }
    
    /**
     * Random AI: Unpredictable
     */
    private AIDecision makeRandomDecision(List<Character> playerParty) {
        List<Ability> readyAbilities = abilities.stream()
            .filter(Ability::isReady)
            .toList();
        
        if (readyAbilities.isEmpty()) {
            readyAbilities = abilities;
        }
        
        Ability randomAbility = readyAbilities.get((int)(Math.random() * readyAbilities.size()));
        
        List<Character> aliveTargets = playerParty.stream()
            .filter(Character::isAlive)
            .toList();
        
        Character randomTarget = aliveTargets.get((int)(Math.random() * aliveTargets.size()));
        
        return new AIDecision(randomAbility, randomTarget, "Random: Unpredictable behavior");
    }
    
    /**
     * Record ability usage (for adaptive AI)
     */
    public void recordAbilityUsage(String abilityId) {
        abilityUsageCount.merge(abilityId, 1, Integer::sum);
    }
    
    /**
     * Get how many times an ability has been used
     */
    public int getAbilityUsageCount(String abilityId) {
        return abilityUsageCount.getOrDefault(abilityId, 0);
    }
    
    // Getters
    public AIPattern getAIPattern() { return aiPattern; }
    public List<Ability> getAbilities() { return new ArrayList<>(abilities); }
    public int getTurnCount() { return turnCount; }
    
    /**
     * HP Trigger - executes action when HP drops below threshold
     */
    private static class HPTrigger {
        final double threshold; // HP percentage
        final Runnable action;
        final String description;
        boolean triggered = false;
        
        HPTrigger(double threshold, Runnable action, String description) {
            this.threshold = threshold;
            this.action = action;
            this.description = description;
        }
        
        void execute() {
            if (!triggered) {
                action.run();
                triggered = true;
            }
        }
        
        boolean isTriggered() {
            return triggered;
        }
    }
    
    /**
     * AI Decision - what the enemy will do this turn
     */
    public static class AIDecision {
        private final Ability ability;
        private final Character target;
        private final String reasoning;
        
        public AIDecision(Ability ability, Character target, String reasoning) {
            this.ability = ability;
            this.target = target;
            this.reasoning = reasoning;
        }
        
        public Ability getAbility() { return ability; }
        public Character getTarget() { return target; }
        public String getReasoning() { return reasoning; }
    }
    
    /**
     * AI Patterns
     */
    public enum AIPattern {
        AGGRESSIVE("Aggressive", "Focuses on dealing maximum damage"),
        DEFENSIVE("Defensive", "Prioritizes survival and caution"),
        TACTICAL("Tactical", "Exploits weaknesses and uses strategy"),
        RANDOM("Random", "Unpredictable behavior");
        
        private final String displayName;
        private final String description;
        
        AIPattern(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
}
