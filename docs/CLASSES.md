# Class System Design v2

## The Companion Rework Framework

To rework a companion, put them through this three-step design filter:

1. **The Alignment Anchor**: Where does this companion naturally sit on the Dual-Axis (Honor <-> Deceit, Compassion <-> Cruelty)?

2. **The Breaking Point**: What specific player alignment or action (Flag) will cause them to lose loyalty, leave the party, or even turn hostile?

3. **The Quest Subversion**: How can their personal quest challenge their alignment anchor, forcing the player to either push them toward an "Ultimate/Secret" mindset or ruin their purity?

---

## Alignment System

### Dual-Axis Morality
Two independent axes track the player's moral choices:

**HONOR AXIS** (How you act)
- **Paragon** (+100): Never lies, always keeps promises, fights fairly
- **Honorable** (+50 to +99): Generally honest and fair
- **Pragmatic** (-49 to +49): Does what's necessary
- **Deceitful** (-50 to -99): Regularly manipulates and tricks
- **Serpent** (-100): Master manipulator, never tells the truth when a lie serves better

**COMPASSION AXIS** (How you treat others)
- **Saint** (+100): Never harms innocents, always shows mercy
- **Merciful** (+50 to +99): Generally kind and forgiving
- **Balanced** (-49 to +49): Judges each situation
- **Harsh** (-50 to -99): Punishes severely, rarely shows mercy
- **Tyrant** (-100): Cruelty is a tool, suffering is power

### Alignment Purity Rewards

**THRESHOLD BUFFS** (Incremental bonuses for staying on path):
- **|25|**: +5% effectiveness to alignment-related abilities
- **|50|**: Unique dialogue options unlock
- **|75|**: +10% effectiveness, minor passive ability
- **|90|**: +15% effectiveness, alignment-specific ability unlocks
- **|100| PURE**: Ultimate alignment class unlocks

**PURITY LOCK**: One choice against your axis resets progress toward that extreme. At 100%, a single betrayal of your principles permanently locks you out of that alignment's ultimate class.

---

## Worldbuilding: The Four Regions

### 1. The Archivist Core (Formerly The Hegemony)

- **Cultural Vibe**: Obsessed with permanence, history, and rigid law. They are not "evil military guys" - they record everything. Their magic is geometric and bound by strict laws.
- **Alignment Tendency**: +Honor (Lawful), -Compassion (Bureaucratic/Cold)
- **Phonetics**: Persian/Sanskrit rhythms with architectural/metallic concepts. Polysyllabic, rolling, ending in sharp resonant sounds (-ath, -el, -za).
- **Naming Convention**: [Given Name] + [Caste-Prefix] + [Patron/Guild]
  - `vex` = military caste
  - `ves` = academic/licensed mage caste
  - `nul` = caste-less or exiled
- **Examples**: Isolde vex-Torvath, Dr. Sybilla ves-Vael, Garrick nul-Kael

### 2. The Sump / The Drowned Wards

- **Cultural Vibe**: A vertical, waterlogged slum built into ancient ruins. Everyone is hustling to survive. Time is money, people speak fast and clip words.
- **Alignment Tendency**: -Honor (Deceitful/Pragmatic), +Compassion (Community-focused)
- **Phonetics**: Creole/Pidgin concepts. Short vowels, glottal stops, dropped syllables. Names are often clipped monikers.
- **Naming Convention**: Surnames don't exist. [One-Syllable Name] + [Earned Moniker/Trade/Flaw]
- **Examples**: Bram "The Vulture", Sash "Velvet", Vek "Glass"

### 3. The Choke / The Symbiote Wilds

- **Cultural Vibe**: A hyper-evolved, aggressive ecosystem. People survive by merging with nature through biological symbiosis. They romanticize their horrific bond with the wild.
- **Alignment Tendency**: 0 Honor (Wild), Variable Compassion
- **Phonetics**: Clicking consonants (Q, K, X) mixed with deep guttural vowels (U, O). Sounds like things snapping and growing.
- **Naming Convention**: [Given Name] + [Biological Bond]. Their name reflects what organism they are bound to.
- **Examples**: Oona Pale-Sap, Ghor Iron-Root, Maeva Still-Water

### 4. The Zenith / The Deep Cloisters

- **Cultural Vibe**: Monasteries built in extreme elemental hazard zones (active volcanoes, anti-gravity rocks). They believe mortal attachments weigh the soul down.
- **Alignment Tendency**: Extreme Purity (+100 or -100)
- **Phonetics**: Breathless, resonant sounds. Monosyllabic. Lots of M, N, L, and soft vowels. Like meditation chants.
- **Naming Convention**: Upon joining, they surrender their birth name and take a [Concept Title] + [Numerical Rank]
- **Examples**: Balance-Ninth, Void-Exile

---

## Background -> Base Classes (Revised - No Overlaps)

### 1. NOBLE BORN
*Philosophy: Authority, martial prowess, leadership*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Cavalier** | Mounted Tank | STR, VIT | Mount abilities, charge attacks |
| **Courtier** | Support/Buffer | INT, WIS | Inspire allies, diplomatic skills |
| **Duelist** | Single-target DPS | DEX, LUK | Riposte, perfect counters |
| **Vanguard** | Off-tank/DPS | STR, DEX | First strike, formation bonuses |

**Advanced Classes (Level 15+):**
- Cavalier -> **Dragon Knight** (flying mount) OR **Iron Bulwark** (unmovable tank)
- Courtier -> **War Marshal** (army-scale buffs) OR **Silver Tongue** (enemy manipulation)
- Duelist -> **Blade Saint** (instant kill on crit) OR **Phantom Fencer** (afterimages)
- Vanguard -> **Champion** (challenge enemies) OR **Linebreaker** (AoE charge)

---

### 2. STREET URCHIN
*Philosophy: Survival, adaptability, cunning*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Cutthroat** | Burst DPS | DEX, LUK | Backstab, assassination |
| **Grifter** | Debuffer | INT, LUK | Con, misdirect, steal buffs |
| **Brawler** | Combo DPS | STR, DEX | Combo chains, counterattacks |
| **Prowler** | Evasion Tank | DEX, VIT | Dodge-tank, smoke bombs |

**Advanced Classes (Level 15+):**
- Cutthroat -> **Deathblade** (guaranteed crits from stealth) OR **Shade** (invisibility master)
- Grifter -> **Mastermind** (control enemy actions) OR **Fence** (item manipulation)
- Brawler -> **Pit Fighter** (stronger when wounded) OR **Street Legend** (intimidation)
- Prowler -> **Phantom** (phase through attacks) OR **Trapmaster** (battlefield control)

---

### 3. SCHOLAR
*Philosophy: Knowledge, magical mastery, discovery*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Evoker** | Elemental DPS | INT, WIS | Element mastery, combo spells |
| **Enchanter** | Buffer/Debuffer | WIS, INT | Weapon/armor enchantments |
| **Naturalist** | Summoner | INT, VIT | Construct/golem creation |
| **Chronicler** | Utility | WIS, LUK | Identify weaknesses, lore bonuses |

**Advanced Classes (Level 15+):**
- Evoker -> **Archmage** (dual-cast spells) OR **Elementalist** (fuse elements)
- Enchanter -> **Runesmith** (permanent enchants) OR **Hexer** (curse specialist)
- Naturalist -> **Golemancer** (giant constructs) OR **Artificer** (magical items)
- Chronicler -> **Sage** (predict enemy moves) OR **Seeker** (find hidden things)

---

### 4. OUTLANDER
*Philosophy: Nature, primal power, survival*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Warden** | Nature Tank | VIT, WIS | Nature armor, regeneration |
| **Predator** | Melee DPS | STR, DEX | Tracking, ambush, bleed |
| **Wildspeaker** | Summoner | WIS, INT | Beast companions |
| **Stormcaller** | AoE Caster | INT, WIS | Weather manipulation |

**Advanced Classes (Level 15+):**
- Warden -> **Ancient Guardian** (become treant) OR **Earthshaper** (terrain control)
- Predator -> **Alpha** (pack tactics) OR **Skinwalker** (partial beast forms)
- Wildspeaker -> **Beastlord** (legendary beasts) OR **Hivemind** (swarm control)
- Stormcaller -> **Tempest** (continuous storm) OR **Worldsinger** (earthquake/tsunami)

---

### 5. SOLDIER
*Philosophy: Discipline, strength, warfare*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Legionnaire** | Balanced Tank/DPS | STR, VIT | Shield wall, formation |
| **Berserker** | Risk/Reward DPS | STR, VIT | Rage meter, pain = power |
| **Guardian** | Pure Tank | VIT, STR | Taunt, damage mitigation, shields |
| **Tactician** | Support/Control | INT, STR | Command allies, exploit weakness |

**Advanced Classes (Level 15+):**
- Legionnaire -> **Centurion** (ally damage reduction) OR **Praetorian** (bodyguard)
- Berserker -> **Ravager** (AoE destruction) OR **Bloodrager** (lifesteal frenzy)
- Guardian -> **Bulwark** (party-wide shields) OR **Warlord** (offensive tanking)
- Tactician -> **General** (battlefield control) OR **Veteran** (counter everything)

---

### 6. ACOLYTE
*Philosophy: Faith, divine power, spiritual balance*

| Base Class | Role | Primary Stats | Unique Mechanic |
|------------|------|---------------|-----------------|
| **Templar** | Holy Tank | STR, WIS | Holy damage, undead slayer |
| **Cleric** | Healer | WIS, VIT | Restoration, cleansing |
| **Oracle** | Utility/Support | WIS, LUK | Prophecy, foresight |
| **Monk** | Balanced Hybrid | DEX, WIS | Ki abilities, inner balance |

**Advanced Classes (Level 15+):**
- Templar -> **Crusader** (holy devastation) OR **Justicar** (execute/spare)
- Cleric -> **High Priest** (mass healing) OR **Exorcist** (banish/purify)
- Oracle -> **Seer** (see all futures) OR **Fateweaver** (alter probability)
- Monk -> **Enlightened** (transcend limits) OR **Void Walker** (embrace emptiness)

---

## Implemented Base Classes

The following classes are currently implemented in `GameClassRegistry.java`:

| Class | Type | Role | Description |
|-------|------|------|-------------|
| **Warrior** | Base | Tank/DPS | Balanced martial fighter |
| **Paladin** | Base | Holy Tank | Divine warrior with healing |
| **Rogue** | Base | DPS | Stealth and critical strikes |
| **Scholar** | Base | Utility | Knowledge-based abilities |
| **Guardian** | Base | Tank | Pure defensive specialist |
| **Monk** | Base | DPS/Support | Martial arts and ki |
| **Spy** | Base | DPS/Control | Information warfare |
| **Brawler** | Base | Combo DPS | Chain attacks |
| **Arcanist** | Base | Magic DPS | Raw magical power |
| **Shaman** | Base | Support | Elemental and nature magic |
| **Priest** | Base | Healer | Divine healing and buffs |

---

## Event-Based Secret Classes

These classes are unlocked through specific story choices. They represent transformative moments.

### DARK PATH CLASSES

| Class | Unlock Event | Description |
|-------|-------------|-------------|
| **Necromancer** | Perform forbidden ritual to raise the dead (sacrifice required) | Commands undead, drains life |
| **Blood Mage** | Drink from cursed artifact / murder for power | HP costs abilities, massive damage |
| **Oathbreaker** | Betray a sacred vow or ally at critical moment | Corrupted paladin abilities, fear aura |
| **Soul Reaver** | Consume the soul of a defeated boss enemy | Absorb enemy abilities permanently |
| **Heretic** | Defile a sacred temple or kill a high priest | Anti-divine powers, nullify holy |
| **Tyrant's Hand** | Serve the dark lord willingly, crush rebellion | Domination magic, break wills |

### GREY PATH CLASSES

| Class | Unlock Event | Description |
|-------|-------------|-------------|
| **Witch Hunter** | Execute someone who may be innocent "for the greater good" | Anti-mage specialist, ruthless efficiency |
| **Spymaster** | Build and maintain a spy network through morally ambiguous means | Information warfare, assassination |
| **Reaver** | Take trophies from fallen foes, embrace battlefield brutality | Fear/intimidation combat style |
| **Contractor** | Complete morally questionable jobs for gold | No loyalty, pure mercenary efficiency |

### LIGHT PATH CLASSES

| Class | Unlock Event | Description |
|-------|-------------|-------------|
| **Redeemer** | Forgive and rehabilitate a major villain | Purification, turn enemies to allies |
| **Martyr** | Sacrifice yourself to save others (and be resurrected) | Deathless, inspire allies |
| **Voice of the People** | Lead successful peaceful revolution | Mass inspiration, cannot be silenced |
| **Saint's Vessel** | Divine entity chooses to inhabit you | Channel divine power directly |

### DISCOVERY CLASSES

| Class | Unlock Event | Description |
|-------|-------------|-------------|
| **Void Walker** | Survive exposure to the void between worlds | Reality manipulation, teleportation |
| **Dragon Heir** | Bond with or absorb a dying dragon | Draconic powers, elemental breath |
| **Time Sage** | Find and master the Chrono Codex | Manipulate turn order, rewind damage |
| **Primordial** | Touch the world's creation stone | Raw elemental chaos, transformation |

---

## Companion Transformation Classes

Each companion has two potential transformation classes based on the player's influence, triggered by their personal quest subversion:

| Companion | Good Path Class | Evil Path Class |
|-----------|-----------------|-----------------|
| **Isolde** | Redeemer | Zealot Inquisitor |
| **Bram** | Spymaster | Contractor |
| **Sybilla** | True Healer | Blood Alchemist |
| **Garrick** | Shield-Captain | Reaver |
| **Oona** | Guardian of Growth | Blight-Touched |
| **Theron** | Bodhisattva | Void Walker |
| **Sash** | Selfless Spy | Velvet Assassin |
| **Ghor** | Chieftain | Warlord |
| **Vek** | Selfless Scholar | Fractured God |
| **Maeva** | Crystal Sage | Corrupted Crystal |
| **Silas** | Purified | Shadow-Worn |

**Transformation Mechanics:**
- Based on player alignment choices throughout the companion's personal quest
- Companion loyalty affects outcome (low loyalty = rebellion against player's path)
- Quest subversion resolution triggers transformation event
- Each class grants unique abilities and story implications

---

## Alignment-Based Ultimate Classes

### 100% PURITY CLASSES (Never strayed from path)

**HONOR AXIS:**

| Alignment | Ultimate Class | Description |
|-----------|---------------|-------------|
| **Paragon (+100)** | **Exemplar** | Perfect technique, all allies gain accuracy/crit when near you, enemies hesitate to attack |
| **Serpent (-100)** | **Shadowlord** | Master of all deception, create illusory clones, enemies attack each other |

**COMPASSION AXIS:**

| Alignment | Ultimate Class | Description |
|-----------|---------------|-------------|
| **Saint (+100)** | **Divine Avatar** | Healing harms undead, resurrect fallen allies, immune to dark damage |
| **Tyrant (-100)** | **Dread Sovereign** | Fear aura paralyzes weak enemies, execute wounded foes, pain empowers you |

### COMBINED PURITY (Both axes at 100%)

| Honor | Compassion | Ultimate Class | Description |
|-------|------------|---------------|-------------|
| Paragon | Saint | **Celestial Champion** | Literally blessed by gods, holy devastation + perfect healing |
| Paragon | Tyrant | **Iron Judge** | Absolute justice without mercy, execute guilty, smite |
| Serpent | Saint | **Velvet Hand** | Manipulation for good, enemies become allies, bloodless victory |
| Serpent | Tyrant | **Dark Messiah** | Ultimate villain class, fear + deception + cruelty combined |

---

## Progressive Alignment Buffs

### HONOR AXIS BUFFS

**Paragon Path (+):**
- +25: "Trustworthy" - Better prices, NPCs share info
- +50: "Oath Keeper" - Promises give +10% stats until fulfilled
- +75: "Incorruptible" - Immune to charm/mind control
- +90: "Living Legend" - Allies gain +15% all stats in your presence
- +100: Unlock **Exemplar** class

**Serpent Path (-):**
- -25: "Silver Tongue" - Unlock deception dialogue options
- -50: "Web of Lies" - Enemies have -10% accuracy (confusion)
- -75: "Master of Masks" - Can disguise as any humanoid
- -90: "Puppeteer" - 20% chance enemies attack each other
- -100: Unlock **Shadowlord** class

### COMPASSION AXIS BUFFS

**Saint Path (+):**
- +25: "Gentle Soul" - Healing +10%
- +50: "Protector" - Allies take -15% damage near you
- +75: "Beacon" - Undead/demons deal -25% damage to you
- +90: "Life Giver" - Once per battle, revive ally at 50% HP
- +100: Unlock **Divine Avatar** class

**Tyrant Path (-):**
- -25: "Intimidating" - Weaker enemies may flee
- -50: "No Mercy" - +20% damage to wounded enemies (<50% HP)
- -75: "Dread Presence" - Enemies have -15% all stats
- -90: "Executioner" - Instant kill enemies below 10% HP
- -100: Unlock **Dread Sovereign** class

---

## Alignment Choice Examples

### OBVIOUS EVIL (Tyrant/Serpent choices)
- Sacrifice innocent villagers to power a dark ritual
- Torture a prisoner for information
- Burn down an orphanage to kill one hidden enemy
- Kill surrendering enemies
- Betray an ally for personal gain

### SUBTLE CORRUPTION (Slippery slope)
- Accept "gifts" from suspicious benefactors
- Let someone else take the fall for your mistake
- Ignore suffering because "it's not your problem"
- Make a "small" compromise on your principles
- Tell a "white lie" that spirals into larger deception
- Use questionable methods to achieve good ends

### MERCY VS JUSTICE DILEMMAS
- Villain begs for mercy - spare or execute?
- Criminal stole to feed family - punish or forgive?
- Ally made fatal mistake - cover up or expose?
- Enemy offers valuable information for freedom - deal or refuse?

### HONOR VS PRAGMATISM DILEMMAS
- Poison the enemy's water supply to save your army?
- Break a promise to save a life?
- Cheat to win a duel against a dishonorable opponent?
- Lie to protect someone's feelings?

---

## Summary

**24 Base Classes** (4 per background, no overlaps)
- Noble Born: Cavalier, Courtier, Duelist, Vanguard
- Street Urchin: Cutthroat, Grifter, Brawler, Prowler
- Scholar: Evoker, Enchanter, Naturalist, Chronicler
- Outlander: Warden, Predator, Wildspeaker, Stormcaller
- Soldier: Legionnaire, Berserker, Guardian, Tactician
- Acolyte: Templar, Cleric, Oracle, Monk

**48 Advanced Classes** (2 per base class)
**16 Event-Based Secret Classes** (Dark, Grey, Light, Discovery paths)
**22 Companion Transformation Classes** (Good/Evil paths for 11 companions)
**6 Alignment Ultimate Classes** (4 single-axis, 2 dual-axis)
**20 Progressive Alignment Buffs** (5 per axis direction)

Total unique class options: **116+ classes**
