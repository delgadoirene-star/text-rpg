package com.rpg.world;

/**
 * Represents a piece of news that travels through the world over time.
 * News doesn't travel instantly - rumors spread based on travel distances.
 * 
 * When reputation changes cascade through the Diplomacy Matrix, the
 * delayed effects are packaged as Rumor objects.
 */
public class Rumor {
    private final RegionType targetRegion;
    private final int reputationChange;
    private int daysUntilArrival;
    private final int originalDays;
    private final long createdAt;

    public Rumor(RegionType targetRegion, int reputationChange, int daysUntilArrival) {
        this.targetRegion = targetRegion;
        this.reputationChange = reputationChange;
        this.daysUntilArrival = daysUntilArrival;
        this.originalDays = daysUntilArrival;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Advance time for this rumor
     * @param days Number of days to advance
     */
    public void advanceDays(int days) {
        daysUntilArrival = Math.max(0, daysUntilArrival - days);
    }

    /**
     * Check if the rumor has arrived at its destination
     */
    public boolean hasArrived() {
        return daysUntilArrival <= 0;
    }

    /**
     * Get how many days until this rumor arrives
     */
    public int getDaysUntilArrival() {
        return daysUntilArrival;
    }

    /**
     * Get the target region for this rumor
     */
    public RegionType getTargetRegion() {
        return targetRegion;
    }

    /**
     * Get the reputation change this rumor will apply
     */
    public int getReputationChange() {
        return reputationChange;
    }

    /**
     * Get progress as a percentage (0-100)
     */
    public int getProgressPercent() {
        if (originalDays == 0) return 100;
        return (int) ((1.0 - (double) daysUntilArrival / originalDays) * 100);
    }

    @Override
    public String toString() {
        return String.format("Rumor to %s: %+d rep in %d days (progress: %d%%)",
            targetRegion.getDisplayName(), reputationChange, daysUntilArrival, getProgressPercent());
    }
}
