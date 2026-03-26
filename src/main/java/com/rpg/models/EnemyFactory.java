package com.rpg.models;

import com.rpg.combat.Ability;
import com.rpg.combat.DamageType;
import com.rpg.models.Enemy.AIPattern;

import java.util.*;

/**
 * Factory for creating region-specific enemies with varied patterns and abilities.
 */
public class EnemyFactory {
    
    private static final Random random = new Random();
    
    /**
     * Enemy template for generating enemies
     */
    public static class EnemyTemplate {
        public final String name;
        public final Element element;
        public final Enemy.AIPattern pattern;
        public final int strMod, dexMod, vitMod, intMod, wisMod, lukMod;
        public final List<String> abilityIds;
        
        public EnemyTemplate(String name, Element element, Enemy.AIPattern pattern,
                            int str, int dex, int vit, int int_, int wis, int luk,
                            String... abilities) {
            this.name = name;
            this.element = element;
            this.pattern = pattern;
            this.strMod = str;
            this.dexMod = dex;
            this.vitMod = vit;
            this.intMod = int_;
            this.wisMod = wis;
            this.lukMod = luk;
            this.abilityIds = List.of(abilities);
        }
    }
    
    // Enemy templates by region
    private static final Map<String, List<EnemyTemplate>> REGION_ENEMIES = new HashMap<>();
    
    static {
        // Archivist Core enemies (organized, human-like)
        REGION_ENEMIES.put("archivist_core", List.of(
            new EnemyTemplate("Enforcer", Element.LIGHT, Enemy.AIPattern.TACTICAL,
                4, 0, 2, 0, 2, 0),
            new EnemyTemplate("Scribe Golem", Element.NEUTRAL, Enemy.AIPattern.DEFENSIVE,
                0, -2, 4, 2, 2, 0),
            new EnemyTemplate("Purifier", Element.LIGHT, Enemy.AIPattern.RANDOM,
                0, 0, 0, 4, 4, 0)
        ));
        
        // The Sump enemies (dark, sneaky)
        REGION_ENEMIES.put("the_sump", List.of(
            new EnemyTemplate("Rat Swarm", Element.DARK, Enemy.AIPattern.AGGRESSIVE,
                2, 4, -2, -2, 0, 2),
            new EnemyTemplate("Sump Lurker", Element.DARK, Enemy.AIPattern.DEFENSIVE,
                0, 2, 4, 0, 0, 2),
            new EnemyTemplate("Corrupted Alchemist", Element.NEUTRAL, Enemy.AIPattern.RANDOM,
                -2, 0, 0, 4, 2, 0)
        ));
        
        // The Choke enemies (nature, beasts)
        REGION_ENEMIES.put("the_choke", List.of(
            new EnemyTemplate("Thornbeast", Element.EARTH, Enemy.AIPattern.AGGRESSIVE,
                4, 0, 4, -2, -2, 0),
            new EnemyTemplate("Spore Walker", Element.EARTH, Enemy.AIPattern.RANDOM,
                0, 0, 2, 2, 4, 0),
            new EnemyTemplate("Root Horror", Element.EARTH, Enemy.AIPattern.TACTICAL,
                2, 0, 4, 0, 2, 0)
        ));
        
        // The Zenith enemies (spiritual, ethereal)
        REGION_ENEMIES.put("the_zenith", List.of(
            new EnemyTemplate("Void Wraith", Element.DARK, Enemy.AIPattern.AGGRESSIVE,
                0, 4, -2, 4, 0, 2),
            new EnemyTemplate("Spirit Monk", Element.WIND, Enemy.AIPattern.TACTICAL,
                2, 2, 2, 2, 2, 2),
            new EnemyTemplate("Enlightened Shadow", Element.DARK, Enemy.AIPattern.RANDOM,
                0, 0, 0, 4, 4, 0)
        ));
        
        // Generic enemies (available everywhere)
        REGION_ENEMIES.put("generic", List.of(
            new EnemyTemplate("Bandit", Element.NEUTRAL, Enemy.AIPattern.AGGRESSIVE,
                2, 2, 0, 0, 0, 2),
            new EnemyTemplate("Wolf", Element.WIND, Enemy.AIPattern.AGGRESSIVE,
                2, 3, 0, -2, 0, 2),
            new EnemyTemplate("Slime", Element.WATER, Enemy.AIPattern.DEFENSIVE,
                -2, 0, 4, 0, 0, 0),
            new EnemyTemplate("Skeleton", Element.DARK, Enemy.AIPattern.TACTICAL,
                2, 0, 2, 0, 0, 2)
        ));
    }
    
    /**
     * Generate enemies for a given location and player level
     */
    public static List<Enemy> generateEnemies(String regionId, int playerLevel, int numEnemies) {
        List<Enemy> enemies = new ArrayList<>();
        
        List<EnemyTemplate> templates = REGION_ENEMIES.getOrDefault(regionId, REGION_ENEMIES.get("generic"));
        
        for (int i = 0; i < numEnemies; i++) {
            EnemyTemplate template = templates.get(random.nextInt(templates.size()));
            int enemyLevel = Math.max(1, playerLevel + random.nextInt(3) - 1);
            
            int base = 8 + enemyLevel * 2;
            Stats stats = new Stats(
                base + template.strMod,
                base + template.dexMod,
                base + template.vitMod,
                base + template.intMod,
                base + template.wisMod,
                base + template.lukMod
            );
            
            String id = "enemy_" + template.name.toLowerCase().replace(" ", "_") + "_" + i + "_" + System.currentTimeMillis();
            String displayName = template.name;
            if (enemyLevel > playerLevel + 1) {
                displayName = "Elite " + template.name;
            } else if (enemyLevel < playerLevel - 1) {
                displayName = "Young " + template.name;
            }
            
            Enemy enemy = new Enemy(id, displayName, enemyLevel, stats, template.element, template.pattern);
            enemies.add(enemy);
        }
        
        return enemies;
    }
    
    /**
     * Generate a boss enemy
     */
    public static Enemy generateBoss(String name, int level, Element element, Enemy.AIPattern pattern) {
        int base = 10 + level * 3;
        Stats stats = new Stats(
            base + 6, // STR
            base + 2, // DEX
            base + 8, // VIT
            base + 4, // INT
            base + 4, // WIS
            base + 2  // LUK
        );
        
        return new Enemy("boss_" + name.toLowerCase().replace(" ", "_"), 
                        name + " (BOSS)", level, stats, element, pattern);
    }
}
