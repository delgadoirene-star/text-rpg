# World Map

## The Four Regions

```
NORTH (High Elevation)
    ↑
[ZENITH] ───────────────────→ [CHOKE]
Peaks/Monasteries           Hills/Forest
    ↓                           ↓
[ZENITH] ←─── [CHOKE] ←────────┘
                    ↓
              [CORE/ZENITH Border]
                    ↓
              [ARCHIVIST CORE]
                  Plateaus
                    ↓
              [SUMP/CORE Border]
                    ↓
              [THE SUMP] ────→ [CHOKE]
           Low/Waterlogged      ↓
                              [CHOKE]
                              
SOUTH (Low Elevation)
```

## Region Overview

| Region | Elevation | Culture | Colors |
|--------|-----------|---------|--------|
| **The Archivist Core** | Mid (Plateaus) | Lawful, bureaucratic, historical | Gold/Steel Blue |
| **The Sump** | Low (Waterlogged) | Pragmatic, community-focused | Murky Green/Brown |
| **The Choke** | Mid-High (Hills) | Wild, symbiotic | Violet/Overgrown Green |
| **The Zenith** | Highest (Mountain Peaks) | Extreme purity seekers | White/Pale Gray |

## Travel Routes

### Core Region (5 locations)
```
coreGate ←→ coreMarket ←→ coreAcademy
    ↓              ↓
 corePrison    coreEstates
                   ↓
              coreAcademy
```

### Sump Region (6 locations)
```
sumpElevator ←→ sumpRatways ←→ sumpMarket
                    ↓              ↓
               sumpArena      sumpPumps
                                   ↓
                              sumpDepths
```

### Choke Region (6 locations)
```
chokeBorder ←→ chokeWoods ←→ chokePass
     ↓              ↓           ↓
sumpRatways    chokeRuins   chokeHeart
                              ↓
                           chokeBog
```

### Zenith Region (4 locations)
```
zenithSteps ←→ zenithMonastery ←→ zenithPinnacle
                            ↓
                      zenithGraveyard
```

## Region Transitions

| From | To | Cost | Description |
|------|----|------|-------------|
| coreGate | sumpElevator | 10 | Descend into slums |
| coreGate | chokeBorder | 20 | Enter the wild |
| sumpRatways | chokeBorder | 15 | Cross the border |
| chokePass | zenithSteps | 30 | Climb to monastery |

## Location Properties

### Safe Zones (Can rest without encounters)
- coreGate
- coreMarket
- coreAcademy
- coreEstates
- chokeHeart
- zenithMonastery

### Shop Locations
- coreMarket (General goods)
- sumpMarket (Black market)

## Biome Types

| Region | Primary Biome | Atmosphere |
|--------|--------------|------------|
| Core | Temperate/Plains | Stable, civilized |
| Sump | Swamp/Marsh | Wet, desperate |
| Choke | Tropical Forest | Oppressive, alive |
| Zenith | Glacier/Alpine | Hostile, transcendent |
