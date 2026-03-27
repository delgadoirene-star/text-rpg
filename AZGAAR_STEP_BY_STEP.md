# Azgaar Fantasy Map Generator - Complete Step-by-Step Guide

## Prerequisites
- Open: https://azgaar.github.io/Fantasy-Map-Generator
- Wait for the default map to load

---

## STEP 1: Generate Base Terrain (5 minutes)

### 1.1 Clear Default Map
1. Click **Tools** (top menu)
2. Click **Heightmap**
3. Click **Erase** button
4. Click and drag across the entire map to erase it
5. The map should now be mostly blank/water level

### 1.2 Create Elevation (The Key Part!)
Our elevation goes: SUMP (bottom/wet) → CORE (mid plateau) → CHOKE (hills) → ZENITH (mountain peaks)

1. Click **Tools** → **Heightmap**
2. Select **Pencil** tool
3. Set brush size to Large (slider at bottom)

**Paint the elevation:**

```
LOW (Sump) ──────────────────────── HIGH (Zenith)
   ↓                                ↑
 Water/marsh                 Mountain peaks
 (dark blue)                 (white)
```

**Paint from LEFT to RIGHT with increasing elevation:**

| Area | Brush | Action |
|------|-------|--------|
| Left 30% | **Fill** with dark blue/teal | This is the Sump (sea level, marshy) |
| Middle 40% | **Brush** with medium gray | Core plateau (stable ground) |
| Right 30% | **Brush** with lighter gray → white | Rising into mountains (Choke base → Zenith peaks) |

**Pro tip:** The highest points should be on the RIGHT side where Zenith goes.

### 1.3 Add Islands/Bays (Optional)
1. Click **Tools** → **Heightmap** → **Islands**
2. Adjust sliders if you want island chains

---

## STEP 2: Generate Map Features (3 minutes)

### 2.1 Auto-Generate
1. Click **Generate** button (top left, green arrow)
2. Wait for generation to complete
3. You should now see:
   - Ocean/sea on left (Sump elevation)
   - Land in middle (Core)
   - Mountains on right (Choke/Zenith)

### 2.2 Fine-tune Landmass
1. Click **Tools** → **Heightmap** → **Brush**
2. Smooth out any weird gaps
3. Add rivers: **Tools** → **Heightmap** → **Rivers**

---

## STEP 3: Create the 4 States (5 minutes)

### 3.1 Delete Random States
1. Click **Tools** → **States**
2. Look at the list on the left panel
3. Delete all existing states (click X or Delete button)
4. Start fresh

### 3.2 Create THE ARCHIVIST CORE
1. Still in **Tools** → **States**
2. Click **Add** or **New State**
3. Fill in:
   - **Name:** `The Archivist Core`
   - **Color:** Click color picker → Select **Gold** (#C9A227) or **Steel Blue** (#4682B4)
4. Click **Add**
5. This state is now selected (highlighted)

### 3.3 Create THE SUMP
1. Click **Add** or **New State**
2. Fill in:
   - **Name:** `The Sump`
   - **Color:** Click color picker → Select **Murky Green** (#4A5D23) or **Swamp Brown** (#5D4E37)
3. Click **Add**

### 3.4 Create THE CHOKE
1. Click **Add** or **New State**
2. Fill in:
   - **Name:** `The Choke`
   - **Color:** Click color picker → Select **Sickly Violet** (#6B4E71) or **Overgrown Green** (#2E5D3A)
3. Click **Add**

### 3.5 Create THE ZENITH
1. Click **Add** or **New State**
2. Fill in:
   - **Name:** `The Zenith`
   - **Color:** Click color picker → Select **White** (#E8E8E8) or **Pale Gray** (#D3D3D3)
3. Click **Add**

**You should now see 4 states in the left panel.**

---

## STEP 4: Paint State Territories (5 minutes)

### 4.1 Select THE SUMP
1. In the States panel, click on **The Sump** to select it
2. The state should be highlighted/selected

### 4.2 Paint THE SUMP Territory
1. Click **Tools** → **States**
2. Select **Brush** tool
3. Paint the **LEFT side** of the map (low elevation areas)
4. Click and drag to paint cells
5. Paint all the low/marshy areas as "The Sump"

### 4.3 Select THE ARCHIVIST CORE
1. Click on **The Archivist Core** in the states list

### 4.4 Paint THE CORE Territory
1. Paint the **MIDDLE section** of the map
2. This should be the flat, stable plateau areas
3. Leave some buffer between Core and Sump

### 4.5 Select THE CHOKE
1. Click on **The Choke** in the states list

### 4.6 Paint THE CHOKE Territory
1. Paint the **LOWER RIGHT** area (foothills)
2. These are areas where the land starts rising
3. Border the Zenith area

### 4.7 Select THE ZENITH
1. Click on **The Zenith** in the states list

### 4.8 Paint THE ZENITH Territory
1. Paint the **HIGHEST areas** (mountain peaks)
2. The white-capped mountain areas
3. This should be the smallest territory

**Result should look like:**
```
LEFT          MIDDLE           RIGHT
[SUMP]      [CORE]      [CHOKE]→[ZENITH]
Low water   Plateaus    Hills    Peaks
```

---

## STEP 5: Set Capitals (2 minutes)

### 5.1 Set Sump Capital
1. Click **Tools** → **Burgs** (settlements)
2. Click on a cell in the Sump territory
3. A burg (settlement) marker should appear
4. In the popup, name it: `The Drowned Market`
5. Check the **Capital** checkbox

### 5.2 Set Core Capital
1. Click on a cell in the Core territory
2. Name it: `The Obsidian Keep`
3. Check **Capital**

### 5.3 Set Choke Capital
1. Click on a cell in the Choke territory
2. Name it: `The Corrupted Heart`
3. Check **Capital**

### 5.4 Set Zenith Capital
1. Click on a cell in the Zenith territory (highest point)
2. Name it: `The Silent Cloister`
3. Check **Capital**

---

## STEP 6: Add More Settlements (Burgs) (5 minutes)

Add settlements to match our WorldMap locations:

### Core Settlements:
1. Click **Tools** → **Burgs**
2. Click in Core territory to add:
   - `The Grand Foyer` (near Core entrance)
   - `The Grand Bazaar` (market area)
   - `The Royal Academy`
   - `The Noble Quarter`
   - `The Ironhold` (prison)

### Sump Settlements:
1. Click in Sump territory to add:
   - `The Descent` (elevator down)
   - `The Ratways`
   - `The Great Pumps`
   - `The Blood Pit`
   - `The Underbelly`

### Choke Settlements:
1. Click in Choke territory to add:
   - `The Blight Line` (border)
   - `The Watching Trees`
   - `The Bone Pass`
   - `The Slow Mire`
   - `The Forgotten City`

### Zenith Settlements:
1. Click in Zenith territory to add:
   - `The Thousand Steps`
   - `The Fallen Peak`
   - `The Apex`

---

## STEP 7: Set Diplomacy (2 minutes)

1. Click **Tools** → **States**
2. Click the **Diplomacy** tab (or button)
3. Set relationships:

| State 1 | State 2 | Relationship |
|---------|---------|--------------|
| Core | Sump | **Enemy** |
| Core | Zenith | **Friendly** |
| Core | Choke | **Neutral** |
| Sump | Choke | **Friendly** |
| Sump | Zenith | **Neutral** |
| Choke | Zenith | **Neutral** |

---

## STEP 8: Configure Biomes (3 minutes)

1. Click **Tools** → **Biomes**

### Set Biome Colors:
| Region | Biome | Color |
|--------|-------|-------|
| Sump | **Swamp** or **Marsh** | Brown/Green |
| Core | **Temperate Forest** or **Plains** | Green |
| Choke | **Tropical Forest** or **Swamp** | Dark Green/Violet |
| Zenith | **Glacier** or **Alpine** | White/Light Blue |

### Paint Biomes:
1. Select the biome from the palette
2. Use brush to paint regions
3. Make Sump wet/muddy, Zenith snowy

---

## STEP 9: Draw Routes (5 minutes)

### 9.1 Delete Random Routes
1. Click **Tools** → **Routes**
2. Select and delete any auto-generated roads

### 9.2 Add Roads (Core - Good Paths)
1. Select **Road** tool
2. Click and drag to draw paths:
   - Core Gate → Grand Bazaar
   - Grand Bazaar → Royal Academy
   - Royal Academy → Obsidian Keep
   - Grand Bazaar → Noble Quarter

### 9.3 Add Trails (Sump/Choke - Rough Paths)
1. Select **Trail** tool
2. Draw:
   - Core → Descent (down into Sump)
   - Ratways → Blight Line (Sump to Choke)
   - Bone Pass → Thousand Steps (Choke to Zenith)

---

## STEP 10: Configure Cultures (Optional - 2 minutes)

1. Click **Tools** → **Cultures**

### Set Namebases:
| State | Culture Namebase |
|-------|------------------|
| Core | Persian/Semitic |
| Sump | Short clipped names |
| Choke | Hyphenated nature |
| Zenith | Concept titles |

---

## STEP 11: Export JSON (1 minute)

1. Click **Map** (top menu)
2. Click **Export**
3. Select **JSON** format
4. Click **Export**
5. Save the file as `map.json`

---

## STEP 12: Generate Java Code

1. Open terminal/command prompt
2. Navigate to where you saved `map.json`
3. Run:
   ```bash
   python azgaar_to_worldmap.py map.json
   ```
4. Copy the output
5. Paste into `WorldMap.java` `initializeWorld()` method

---

## Troubleshooting

### States won't paint?
- Make sure you're in **Edit Mode** (click state in list first)
- Check you're using the Brush tool

### Can't place burgs?
- You need to click on cells within a state's territory
- Make sure the cell height is land (not water)

### Elevation looks wrong?
- Go back to **Tools → Heightmap**
- Use **Brush** to adjust (lighter = higher)

### Map too small/large?
- Use the **Zoom** slider at bottom
- Or regenerate with different settings

---

## Final Checklist

Before exporting, verify:
- [ ] 4 states created with correct names and colors
- [ ] Each state has a capital
- [ ] States painted in correct positions (Sump=left, Core=center, Zenith=top/right)
- [ ] Settlements placed at your locations
- [ ] Routes drawn between major locations
- [ ] Diplomacy set (Core↔Sump as Enemy)
