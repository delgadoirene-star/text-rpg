package com.rpg;

import com.rpg.combat.CombatEngine;
import com.rpg.models.*;
import com.rpg.systems.AlignmentSystem;
import com.rpg.systems.FlagManager;
import com.rpg.systems.PartyDynamics;
import com.rpg.systems.QuestManager;
import com.rpg.world.Location;
import com.rpg.world.WorldMap;

import java.util.*;

/**
 * Central game state - holds all mutable game data.
 * Created once at game start and passed to all systems.
 */
public class GameState {
    
    // Player
    private Player player;
    private List<Companion> party; // Active combat party (max 3 including player)
    private List<Companion> allCompanions; // All recruited companions
    
    // World
    private WorldMap worldMap;
    private Location currentLocation;
    
    // Systems
    private FlagManager flagManager;
    private QuestManager questManager;
    private PartyDynamics partyDynamics;
    // Note: AlignmentSystem is owned by Player, not GameState
    // Use getAlignmentSystem() which delegates to player.getAlignment()
    
    // Game progress
    private int currentDay;
    private int gold;
    private boolean gameOver;
    private boolean gameWon;
    
    // Companion registry (id -> factory method reference)
    private Map<String, Companion> companionRegistry;
    
    public GameState() {
        this.party = new ArrayList<>();
        this.allCompanions = new ArrayList<>();
        this.flagManager = new FlagManager();
        this.questManager = new QuestManager();
        this.partyDynamics = new PartyDynamics();
        this.companionRegistry = new HashMap<>();
        this.currentDay = 1;
        this.gold = 100; // Starting gold
        this.gameOver = false;
        this.gameWon = false;
    }
    
    // ==================== Player ====================
    
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    
    // ==================== Party Management ====================
    
    /**
     * Add a companion to the active party (max 3 total including player)
     */
    public boolean addToParty(Companion companion) {
        if (party.size() >= 2) { // Player + 2 companions max
            System.out.println("Party is full! (Max 3 characters)");
            return false;
        }
        if (!companion.isRecruited()) {
            companion.recruit();
        }
        if (!party.contains(companion)) {
            party.add(companion);
        }
        if (!allCompanions.contains(companion)) {
            allCompanions.add(companion);
        }
        return true;
    }
    
    /**
     * Remove a companion from the active party
     */
    public void removeFromParty(Companion companion) {
        party.remove(companion);
    }
    
    /**
     * Add companion to active party (alias for JavaFX controller)
     */
    public boolean addActiveCompanion(Companion companion) {
        return addToParty(companion);
    }
    
    /**
     * Remove companion from active party (alias for JavaFX controller)
     */
    public void removeActiveCompanion(Companion companion) {
        removeFromParty(companion);
    }
    
    /**
     * Get active companions (alias for JavaFX controller)
     */
    public List<Companion> getActiveCompanions() {
        return getParty();
    }
    
    /**
     * Get available (recruited) companions (alias for JavaFX controller)
     */
    public List<Companion> getAvailableCompanions() {
        return getAllCompanions();
    }
    
    /**
     * Get the full combat party (player + companions)
     */
    public List<com.rpg.models.Character> getCombatParty() {
        List<com.rpg.models.Character> combatParty = new ArrayList<>();
        combatParty.add(player);
        for (Companion c : party) {
            if (c.isAlive()) {
                combatParty.add(c);
            }
        }
        return combatParty;
    }
    
    public List<Companion> getParty() { return Collections.unmodifiableList(party); }
    public List<Companion> getAllCompanions() { return Collections.unmodifiableList(allCompanions); }
    
    /**
     * Get a companion by ID from registry or all companions
     */
    public Companion getCompanion(String id) {
        for (Companion c : allCompanions) {
            if (c.getId().equals(id)) return c;
        }
        return companionRegistry.get(id);
    }
    
    /**
     * Register a companion template (not yet recruited)
     */
    public void registerCompanion(Companion companion) {
        companionRegistry.put(companion.getId(), companion);
    }
    
    // ==================== World ====================
    
    public WorldMap getWorldMap() { return worldMap; }
    public void setWorldMap(WorldMap worldMap) { this.worldMap = worldMap; }
    
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location location) { 
        this.currentLocation = location;
        if (location != null) {
            location.setDiscovered(true);
            location.onFirstVisit();
        }
    }
    
    // ==================== Systems ====================
    
    public FlagManager getFlagManager() { return flagManager; }
    public QuestManager getQuestManager() { return questManager; }
    public PartyDynamics getPartyDynamics() { return partyDynamics; }
    
    /**
     * Get the alignment system - delegates to player's alignment.
     * This ensures there's only ONE source of truth for alignment.
     */
    public AlignmentSystem getAlignmentSystem() { 
        return player != null ? player.getAlignment() : null; 
    }
    
    // ==================== Progress ====================
    
    public int getCurrentDay() { return currentDay; }
    public int getDayCount() { return currentDay; } // Alias for compatibility
    public void advanceDay() { currentDay++; }
    
    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
    public void addGold(int amount) { this.gold += amount; }
    public void removeGold(int amount) { this.gold = Math.max(0, this.gold - amount); }
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }
    
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public boolean isGameWon() { return gameWon; }
    public void setGameWon(boolean gameWon) { this.gameWon = gameWon; }
    
    // ==================== Heal Party ====================
    
    /**
     * Heal all party members to full (rest at inn, etc.)
     */
    public void healParty() {
        player.heal(player.getMaxHP());
        for (Companion c : party) {
            c.heal(c.getMaxHP());
        }
        System.out.println("All party members fully healed!");
    }
    
    /**
     * Update all companion loyalty based on current alignment.
     * Call this after major alignment choices.
     */
    public void updateCompanionLoyalty() {
        AlignmentSystem alignment = getAlignmentSystem();
        if (alignment == null) return;
        
        for (Companion companion : allCompanions) {
            if (!companion.isRecruited()) continue;
            
            String id = companion.getId();
            int modifier = alignment.getCompanionLoyaltyModifier(id);
            
            if (modifier != 0) {
                companion.changeLoyalty(modifier);
            }
            
            // Check breaking point
            if (alignment.isCompanionBreakingPoint(id)) {
                int currentLoyalty = companion.getLoyaltyLevel();
                // Breaking point causes rapid loyalty drain
                if (currentLoyalty > 10) {
                    companion.changeLoyalty(-5);
                }
            }
        }
    }
}
