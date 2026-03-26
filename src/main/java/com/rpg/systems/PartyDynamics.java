package com.rpg.systems;

import com.rpg.combat.Ability;
import com.rpg.combat.CompanionAbilityFactory;
import com.rpg.combat.DamageType;
import com.rpg.models.Companion;
import com.rpg.models.Element;

import java.util.*;

/**
 * Manages companion pair dynamics, combat bonuses, conflicts, and combo attacks.
 * Bonuses are calculated at combat start based on active party composition.
 */
public class PartyDynamics {
    
    /**
     * A dynamic between two companions
     */
    public static class Dynamic {
        public final String companion1Id;
        public final String companion2Id;
        public final String name;
        public final String description;
        public final boolean isConflict;
        public final int combatBonus; // Percentage bonus
        public final String combatBonusDesc;
        
        public Dynamic(String c1, String c2, String name, String description, 
                       boolean isConflict, int combatBonus, String combatBonusDesc) {
            this.companion1Id = c1;
            this.companion2Id = c2;
            this.name = name;
            this.description = description;
            this.isConflict = isConflict;
            this.combatBonus = combatBonus;
            this.combatBonusDesc = combatBonusDesc;
        }
    }
    
    /**
     * A combo attack unlocked by high friendship between two companions
     */
    public static class ComboAttack {
        public final String companion1Id;
        public final String companion2Id;
        public final String name;
        public final String description;
        public final int requiredFriendship;
        public final double basePower;
        public final DamageType damageType;
        public final Element element;
        
        public ComboAttack(String c1, String c2, String name, String description,
                           int requiredFriendship, double basePower, DamageType type, Element element) {
            this.companion1Id = c1;
            this.companion2Id = c2;
            this.name = name;
            this.description = description;
            this.requiredFriendship = requiredFriendship;
            this.basePower = basePower;
            this.damageType = type;
            this.element = element;
        }
    }
    
    // All defined dynamics
    private final List<Dynamic> allDynamics;
    // All combo attacks
    private final List<ComboAttack> allCombos;
    
    public PartyDynamics() {
        allDynamics = new ArrayList<>();
        allCombos = new ArrayList<>();
        initializeDynamics();
        initializeCombos();
    }
    
    private void initializeDynamics() {
        // Positive dynamics
        allDynamics.add(new Dynamic("bram", "sash", "Professional Respect",
            "Both from the Sump. They understand each other's language.", false,
            10, "+10% evasion for both"));
        
        allDynamics.add(new Dynamic("garrick", "ghor", "Brotherhood",
            "Fellow soldiers who respect each other's strength.", false,
            10, "+10% defense for both"));
        
        allDynamics.add(new Dynamic("oona", "maeva", "Nature Kinship",
            "Connected through their bond with the natural world.", false,
            15, "+15% healing effectiveness when paired"));
        
        allDynamics.add(new Dynamic("theron", "silas", "Philosophical Balance",
            "Their debates sharpen both minds.", false,
            5, "+5% all stats"));
        
        allDynamics.add(new Dynamic("isolde", "bram", "Law vs Freedom",
            "They clash constantly, but the friction makes them sharper.", false,
            15, "+15% damage when fighting together"));
        
        allDynamics.add(new Dynamic("sybilla", "vek", "Rivalry",
            "Ethical science vs forbidden knowledge. Sparks fly.", false,
            10, "+10% magic damage when paired"));
        
        // Negative dynamics (conflicts)
        allDynamics.add(new Dynamic("isolde", "sash", "Ideological Enemies",
            "Law enforcer vs master spy. Oil and water.", true,
            -10, "Cannot both be in party if either has <30 loyalty"));
        
        allDynamics.add(new Dynamic("ghor", "vek", "Cultural Clash",
            "Simple warrior vs arrogant mage. Mutual contempt.", true,
            -5, "-5% loyalty per quest together, but unique combo at friendship 7+"));
        
        allDynamics.add(new Dynamic("sybilla", "silas", "Light vs Dark",
            "Pure healer vs corrupted priest. Healing magic conflicts.", true,
            -10, "-10% healing when both present until one completes quest"));
    }
    
    private void initializeCombos() {
        allCombos.add(new ComboAttack("isolde", "garrick", "Pincer Strike",
            "Both attack the same target in perfect coordination.",
            5, 3.0, DamageType.PHYSICAL, Element.LIGHT));
        
        allCombos.add(new ComboAttack("bram", "sash", "Shadow Network",
            "AoE debuff that reveals all enemy weaknesses.",
            6, 2.0, DamageType.MAGICAL, Element.DARK));
        
        allCombos.add(new ComboAttack("ghor", "vek", "Arcane Bulwark",
            "Ghor shields Vek; Vek's next spell deals +100% damage.",
            7, 3.5, DamageType.MAGICAL, Element.EARTH));
        
        allCombos.add(new ComboAttack("oona", "maeva", "Storm of Growth",
            "Massive nature AoE that heals allies and damages enemies.",
            5, 2.5, DamageType.MAGICAL, Element.EARTH));
        
        allCombos.add(new ComboAttack("theron", "silas", "Twilight Balance",
            "Full party heal + cleanse + damage boost.",
            6, 1.5, DamageType.MAGICAL, Element.WIND));
    }
    
    /**
     * Get all dynamics for a given pair of companion IDs
     */
    public List<Dynamic> getDynamics(String c1, String c2) {
        List<Dynamic> result = new ArrayList<>();
        for (Dynamic d : allDynamics) {
            if ((d.companion1Id.equals(c1) && d.companion2Id.equals(c2)) ||
                (d.companion1Id.equals(c2) && d.companion2Id.equals(c1))) {
                result.add(d);
            }
        }
        return result;
    }
    
    /**
     * Get all combo attacks available for a given pair based on friendship levels
     */
    public List<ComboAttack> getAvailableCombos(String c1, String c2, 
                                                  int friendship1, int friendship2) {
        List<ComboAttack> result = new ArrayList<>();
        for (ComboAttack combo : allCombos) {
            if ((combo.companion1Id.equals(c1) && combo.companion2Id.equals(c2)) ||
                (combo.companion1Id.equals(c2) && combo.companion2Id.equals(c1))) {
                int requiredFriendship = combo.requiredFriendship;
                if (friendship1 >= requiredFriendship && friendship2 >= requiredFriendship) {
                    result.add(combo);
                }
            }
        }
        return result;
    }
    
    /**
     * Calculate total combat bonus for a party composition
     * @param activeCompanions List of active companion IDs
     * @param companionLoyalties Map of companion ID -> loyalty level
     * @return Map of bonus type -> value (percentage)
     */
    public Map<String, Integer> calculatePartyBonuses(List<String> activeCompanions, 
                                                       Map<String, Integer> companionLoyalties) {
        Map<String, Integer> bonuses = new HashMap<>();
        bonuses.put("damage", 0);
        bonuses.put("defense", 0);
        bonuses.put("evasion", 0);
        bonuses.put("healing", 0);
        bonuses.put("magic", 0);
        bonuses.put("allStats", 0);
        
        // Check all pairs in active party
        for (int i = 0; i < activeCompanions.size(); i++) {
            for (int j = i + 1; j < activeCompanions.size(); j++) {
                String c1 = activeCompanions.get(i);
                String c2 = activeCompanions.get(j);
                
                List<Dynamic> dynamics = getDynamics(c1, c2);
                for (Dynamic d : dynamics) {
                    int loyalty1 = companionLoyalties.getOrDefault(c1, 50);
                    int loyalty2 = companionLoyalties.getOrDefault(c2, 50);
                    
                    // Check if conflict should be suppressed by high loyalty
                    if (d.isConflict) {
                        if (loyalty1 >= 70 && loyalty2 >= 70) {
                            // Conflict suppressed - halved penalty
                            int halvedBonus = d.combatBonus / 2;
                            applyBonus(bonuses, d.combatBonusDesc, halvedBonus);
                        } else if (loyalty1 >= 90 && loyalty2 >= 90) {
                            // Conflict ignored entirely
                            continue;
                        }
                        // Full conflict applies
                        applyBonus(bonuses, d.combatBonusDesc, d.combatBonus);
                    } else {
                        // Positive dynamic always applies
                        applyBonus(bonuses, d.combatBonusDesc, d.combatBonus);
                    }
                }
            }
        }
        
        return bonuses;
    }
    
    private void applyBonus(Map<String, Integer> bonuses, String desc, int value) {
        String lower = desc.toLowerCase();
        if (lower.contains("damage")) {
            bonuses.put("damage", bonuses.get("damage") + value);
        }
        if (lower.contains("defense")) {
            bonuses.put("defense", bonuses.get("defense") + value);
        }
        if (lower.contains("evasion")) {
            bonuses.put("evasion", bonuses.get("evasion") + value);
        }
        if (lower.contains("healing")) {
            bonuses.put("healing", bonuses.get("healing") + value);
        }
        if (lower.contains("magic")) {
            bonuses.put("magic", bonuses.get("magic") + value);
        }
        if (lower.contains("all stats") || lower.contains("all stat")) {
            bonuses.put("allStats", bonuses.get("allStats") + value);
        }
    }
    
    /**
     * Get a human-readable summary of all active dynamics in a party
     */
    public String getPartyDynamicSummary(List<String> activeCompanions) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < activeCompanions.size(); i++) {
            for (int j = i + 1; j < activeCompanions.size(); j++) {
                String c1 = activeCompanions.get(i);
                String c2 = activeCompanions.get(j);
                
                List<Dynamic> dynamics = getDynamics(c1, c2);
                for (Dynamic d : dynamics) {
                    sb.append(d.name).append(": ").append(d.combatBonusDesc).append("\n");
                }
            }
        }
        
        return sb.toString();
    }
}
