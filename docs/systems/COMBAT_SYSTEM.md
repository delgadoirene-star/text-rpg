# Combat System

## Turn-Based Combat

### Combat Flow
1. Player selects action
2. Player action executes
3. Companion actions execute (AI-controlled)
4. Enemy actions execute (AI-controlled)
5. Status effects tick
6. Repeat until victory/defeat

## Focus System

**Focus Meter (0-100):**
- Gains Focus from: attacking, taking damage, certain abilities
- Tiers:
  - **Tier 1 (0-33):** Basic abilities
  - **Tier 2 (34-66):** Intermediate abilities
  - **Tier 3 (67-100):** Ultimate abilities

### Focus Mechanics
- Focus persists between battles
- Resting fully restores Focus
- Some abilities generate Focus

## Elemental Combo System

### Element Wheel
```
FIRE → WIND → EARTH → WATER → FIRE
          ↑
         LIGHT ←→ DARK
```

### Combinations (11 Total)

| Combination | Elements | Effect |
|-------------|----------|--------|
| Vaporize | Fire + Water | Steam, burns |
| Melt | Fire + Earth | Molten damage |
| Freeze | Water + Wind | Ice锁敌 |
| Surge | Wind + Water | Lightning |
| Shatter | Earth + Wind | Stone shards |
| Combust | Fire + Wind | Inferno |
| Petrify | Earth + Water | Stone petrify |
| Storm | Wind + Wind | Heavy storm |
| Erode | Earth + Earth | Sandstorm |
| Tidal | Water + Water | Tsunami |
| Light | Light + Dark | Holy/Dark burst |

## Status Effects

### Buffs
- **Haste:** Speed +25%
- **Shield:** Absorbs damage
- **Regen:** Healing over time
- **Fury:** Damage +15%
- **Focus:** Focus generation +50%

### Debuffs
- **Poison:** Damage over time
- **Burn:** Fire DOT
- **Freeze:** Cannot act
- **Silence:** Cannot use abilities
- **Weaken:** Damage -25%
- **Slow:** Speed -25%

## Party Combat

### Party Size
- **Maximum:** 4 characters (1 Player + 3 Companions)
- **Reserve:** Unlimited, swap at safe zones

### Party Synergies
- Companions gain bonuses based on:
  - Loyalty level
  - Friendship level
  - Personality compatibility
  - Active quest progress

## Difficulty Modes

| Mode | Focus Charge | AI | Scaling |
|------|-------------|-----|---------|
| Story | 1.5x | Simple | None |
| Normal | 1.0x | Adaptive | Light |
| Hard | 0.75x | Adaptive | Full |
