package com.rpg.systems;

import java.util.ArrayList;
import java.util.List;

/**
 * Dual-axis alignment system tracking Honor and Compassion
 * 
 * Honor Axis: Paragon (+100) ↔ Serpent (-100)
 *   - How you act: honesty, fairness, keeping promises
 * 
 * Compassion Axis: Saint (+100) ↔ Tyrant (-100)
 *   - How you treat others: mercy, kindness, cruelty
 */
public class AlignmentSystem {
    
    // Current alignment values (-100 to +100)
    private int honor;      // + = Paragon, - = Serpent
    private int compassion; // + = Saint, - = Tyrant
    
    // Purity tracking (have they EVER strayed from the extreme path?)
    private boolean honorPurityBroken;      // True if ever made a choice against Paragon path
    private boolean serpentPurityBroken;    // True if ever made a choice against Serpent path
    private boolean saintPurityBroken;      // True if ever made a choice against Saint path
    private boolean tyrantPurityBroken;     // True if ever made a choice against Tyrant path
    
    // Track peak values for buff thresholds
    private int peakHonor;
    private int peakSerpent; // Stored as positive (abs value of negative honor)
    private int peakSaint;
    private int peakTyrant;  // Stored as positive (abs value of negative compassion)
    
    // History of alignment-affecting choices
    private List<AlignmentChoice> choiceHistory;
    
    // ==================== Alignment Tiers ====================
    
    public enum HonorTier {
        PARAGON("Paragon", 100, 100),
        HONORABLE("Honorable", 50, 99),
        PRAGMATIC("Pragmatic", -49, 49),
        DECEITFUL("Deceitful", -99, -50),
        SERPENT("Serpent", -100, -100);
        
        private final String name;
        private final int min;
        private final int max;
        
        HonorTier(String name, int min, int max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }
        
        public String getName() { return name; }
        
        public static HonorTier fromValue(int value) {
            for (HonorTier tier : values()) {
                if (value >= tier.min && value <= tier.max) {
                    return tier;
                }
            }
            return PRAGMATIC;
        }
    }
    
    public enum CompassionTier {
        SAINT("Saint", 100, 100),
        MERCIFUL("Merciful", 50, 99),
        BALANCED("Balanced", -49, 49),
        HARSH("Harsh", -99, -50),
        TYRANT("Tyrant", -100, -100);
        
        private final String name;
        private final int min;
        private final int max;
        
        CompassionTier(String name, int min, int max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }
        
        public String getName() { return name; }
        
        public static CompassionTier fromValue(int value) {
            for (CompassionTier tier : values()) {
                if (value >= tier.min && value <= tier.max) {
                    return tier;
                }
            }
            return BALANCED;
        }
    }
    
    // ==================== Constructor ====================
    
    public AlignmentSystem() {
        this.honor = 0;
        this.compassion = 0;
        
        this.honorPurityBroken = false;
        this.serpentPurityBroken = false;
        this.saintPurityBroken = false;
        this.tyrantPurityBroken = false;
        
        this.peakHonor = 0;
        this.peakSerpent = 0;
        this.peakSaint = 0;
        this.peakTyrant = 0;
        
        this.choiceHistory = new ArrayList<>();
    }
    
    // ==================== Alignment Modification ====================
    
    /**
     * Record an alignment-affecting choice
     * @param honorChange Change to honor axis (+ = honorable, - = deceitful)
     * @param compassionChange Change to compassion axis (+ = merciful, - = cruel)
     * @param description Description of the choice
     */
    public void makeChoice(int honorChange, int compassionChange, String description) {
        // Check purity breaks BEFORE applying changes
        checkPurityBreaks(honorChange, compassionChange);
        
        // Apply changes
        honor = clamp(honor + honorChange, -100, 100);
        compassion = clamp(compassion + compassionChange, -100, 100);
        
        // Update peak values
        if (honor > 0 && honor > peakHonor) peakHonor = honor;
        if (honor < 0 && Math.abs(honor) > peakSerpent) peakSerpent = Math.abs(honor);
        if (compassion > 0 && compassion > peakSaint) peakSaint = compassion;
        if (compassion < 0 && Math.abs(compassion) > peakTyrant) peakTyrant = Math.abs(compassion);
        
        // Record choice
        choiceHistory.add(new AlignmentChoice(honorChange, compassionChange, description));
        
        // Notify player of significant changes
        notifyAlignmentChange(honorChange, compassionChange);
    }
    
    /**
     * Check if this choice breaks any purity paths
     */
    private void checkPurityBreaks(int honorChange, int compassionChange) {
        // If you've been building toward Paragon and make a dishonorable choice
        if (honor > 0 && honorChange < 0) {
            if (!honorPurityBroken) {
                honorPurityBroken = true;
                System.out.println(">>> Your honor wavers... the path to Paragon is forever closed.");
            }
        }
        
        // If you've been building toward Serpent and make an honorable choice
        if (honor < 0 && honorChange > 0) {
            if (!serpentPurityBroken) {
                serpentPurityBroken = true;
                System.out.println(">>> A moment of truth... the path to Serpent is forever closed.");
            }
        }
        
        // If you've been building toward Saint and make a cruel choice
        if (compassion > 0 && compassionChange < 0) {
            if (!saintPurityBroken) {
                saintPurityBroken = true;
                System.out.println(">>> Mercy abandoned... the path to Sainthood is forever closed.");
            }
        }
        
        // If you've been building toward Tyrant and show mercy
        if (compassion < 0 && compassionChange > 0) {
            if (!tyrantPurityBroken) {
                tyrantPurityBroken = true;
                System.out.println(">>> A moment of weakness... the path to Tyranny is forever closed.");
            }
        }
    }
    
    /**
     * Notify player of alignment changes
     */
    private void notifyAlignmentChange(int honorChange, int compassionChange) {
        if (honorChange != 0) {
            String direction = honorChange > 0 ? "Honor" : "Deceit";
            System.out.println(String.format("  [%s %+d]", direction, honorChange));
        }
        if (compassionChange != 0) {
            String direction = compassionChange > 0 ? "Mercy" : "Cruelty";
            System.out.println(String.format("  [%s %+d]", direction, compassionChange));
        }
    }
    
    // ==================== Purity & Buff Checks ====================
    
    /**
     * Check if player can still achieve Paragon purity
     */
    public boolean canAchieveParagon() {
        return !honorPurityBroken && honor >= 0;
    }
    
    /**
     * Check if player can still achieve Serpent purity
     */
    public boolean canAchieveSerpent() {
        return !serpentPurityBroken && honor <= 0;
    }
    
    /**
     * Check if player can still achieve Saint purity
     */
    public boolean canAchieveSaint() {
        return !saintPurityBroken && compassion >= 0;
    }
    
    /**
     * Check if player can still achieve Tyrant purity
     */
    public boolean canAchieveTyrant() {
        return !tyrantPurityBroken && compassion <= 0;
    }
    
    /**
     * Check if player has achieved a pure alignment (100 with no breaks)
     */
    public boolean hasAchievedPureParagon() {
        return honor == 100 && !honorPurityBroken;
    }
    
    public boolean hasAchievedPureSerpent() {
        return honor == -100 && !serpentPurityBroken;
    }
    
    public boolean hasAchievedPureSaint() {
        return compassion == 100 && !saintPurityBroken;
    }
    
    public boolean hasAchievedPureTyrant() {
        return compassion == -100 && !tyrantPurityBroken;
    }
    
    /**
     * Get current buff tier for honor axis (0-5, where 5 = ultimate)
     */
    public int getHonorBuffTier() {
        int absValue = Math.abs(honor);
        if (absValue >= 100) return 5;
        if (absValue >= 90) return 4;
        if (absValue >= 75) return 3;
        if (absValue >= 50) return 2;
        if (absValue >= 25) return 1;
        return 0;
    }
    
    /**
     * Get current buff tier for compassion axis (0-5, where 5 = ultimate)
     */
    public int getCompassionBuffTier() {
        int absValue = Math.abs(compassion);
        if (absValue >= 100) return 5;
        if (absValue >= 90) return 4;
        if (absValue >= 75) return 3;
        if (absValue >= 50) return 2;
        if (absValue >= 25) return 1;
        return 0;
    }
    
    // ==================== Getters ====================
    
    public int getHonor() { return honor; }
    public int getCompassion() { return compassion; }
    
    public HonorTier getHonorTier() { return HonorTier.fromValue(honor); }
    public CompassionTier getCompassionTier() { return CompassionTier.fromValue(compassion); }
    
    public List<AlignmentChoice> getChoiceHistory() { return new ArrayList<>(choiceHistory); }
    
    // ==================== Display ====================
    
    /**
     * Get alignment summary
     */
    public String getAlignmentSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════════════════╗\n");
        sb.append("║                        ALIGNMENT                              ║\n");
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        
        // Honor axis
        sb.append(String.format("║  HONOR: %s (%+d)\n", getHonorTier().getName(), honor));
        sb.append("║  ");
        sb.append(renderAlignmentBar(honor, "Serpent", "Paragon"));
        sb.append("\n");
        
        // Compassion axis
        sb.append(String.format("║  COMPASSION: %s (%+d)\n", getCompassionTier().getName(), compassion));
        sb.append("║  ");
        sb.append(renderAlignmentBar(compassion, "Tyrant", "Saint"));
        sb.append("\n");
        
        // Purity status
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  PURITY PATHS:\n");
        sb.append(String.format("║    Paragon: %s  |  Serpent: %s\n",
            canAchieveParagon() ? "OPEN" : "CLOSED",
            canAchieveSerpent() ? "OPEN" : "CLOSED"));
        sb.append(String.format("║    Saint: %s    |  Tyrant: %s\n",
            canAchieveSaint() ? "OPEN" : "CLOSED",
            canAchieveTyrant() ? "OPEN" : "CLOSED"));
        
        sb.append("╚═══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
    
    /**
     * Render a visual bar for alignment
     */
    private String renderAlignmentBar(int value, String negLabel, String posLabel) {
        StringBuilder bar = new StringBuilder();
        bar.append(String.format("%-8s [", negLabel));
        
        // Create a 21-character bar (-100 to +100, with 0 in middle)
        int position = (value + 100) / 10; // 0-20
        for (int i = 0; i <= 20; i++) {
            if (i == 10) {
                bar.append("|"); // Center marker
            } else if (i == position) {
                bar.append("@"); // Current position
            } else {
                bar.append("-");
            }
        }
        
        bar.append(String.format("] %s", posLabel));
        return bar.toString();
    }
    
    // ==================== Utility ====================
    
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    // ==================== Inner Classes ====================
    
    /**
     * Record of an alignment-affecting choice
     */
    public static class AlignmentChoice {
        private int honorChange;
        private int compassionChange;
        private String description;
        private long timestamp;
        
        public AlignmentChoice(int honorChange, int compassionChange, String description) {
            this.honorChange = honorChange;
            this.compassionChange = compassionChange;
            this.description = description;
            this.timestamp = System.currentTimeMillis();
        }
        
        public int getHonorChange() { return honorChange; }
        public int getCompassionChange() { return compassionChange; }
        public String getDescription() { return description; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("[Honor %+d, Compassion %+d] %s", 
                honorChange, compassionChange, description);
        }
    }
}
