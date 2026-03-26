# Companion Design - The Shattered Records

## Overview

The game features 11 unique companions distributed across 4 world regions. Each companion has:
- **Alignment Anchors**: Alignment values that bond them to you
- **Breaking Points**: Alignment extremes that cause them to leave
- **Transformation Classes**: Good/Evil class paths based on player influence
- **Personal Quests**: Multi-part questlines tied to their backstory

---

## Companion Summary Table

| ID | Name | Region | Alignment Tendency | Combat Role |
|----|------|--------|-------------------|-------------|
| isolde | Isolde vex-Torvath | Archivist Core | +Honor/-Compassion | Tank |
| bram | Bram "The Vulture" | Sump | -Honor/+Compassion | DPS |
| sybilla | Dr. Sybilla ves-Vael | Archivist Core | +Honor/+Compassion | Support |
| garrick | Garrick nul-Kael | Exiled | +Honor/+Compassion | Tank |
| oona | Oona Pale-Sap | Choke | Neutral/+Compassion | Tank |
| theron | Balance-Ninth | Zenith | Neutral/Neutral | Balanced |
| sash | Sash "Velvet" | Sump | -Honor/-Compassion | DPS |
| ghor | Ghor Iron-Root | Choke | +Honor/+Compassion | Tank |
| vek | Vek "Glass" | Sump | -Honor/-Compassion | DPS |
| maeva | Maeva Still-Water | Choke | +Honor/+Compassion | Support |
| silas | Void-Exile | Zenith | -Honor/-Compassion | Support |

---

## 1. Isolde vex-Torvath

- **Background:** Noble Born
- **Starting Class:** Paladin
- **Region:** Archivist Core
- **Personality:** Honorable, righteous, stubborn, but deeply compassionate. Believes in justice above all else.
- **Backstory:** Daughter of a disgraced Duke, she seeks to restore her family's honor by becoming a paragon of virtue. She is on a quest to find a legendary holy sword.
- **Combat Role:** Tank / Support
- **Recruitment:**
  - **Location:** Castle Gate (`castle_gate`)
  - **Requirement:** Player must have the `honorable_path` flag set after completing an early-game moral choice.
- **Alignment Anchors:** +Honor, -Compassion (strict justice)
- **Breaking Points:** Extreme Serpent path actions (betrayal, deception)
- **Transformation Classes:**
  - **Good Path:** Redeemer - Purification, turn enemies to allies
  - **Evil Path:** Iron Judge - Absolute justice without mercy, execute guilty
- **Personal Quest:** "The Sunken Blade" - A multi-part quest to recover a holy sword from a fallen underwater temple, which requires Isolde to confront her family's past.

---

## 2. Bram "The Vulture"

- **Background:** Street Urchin
- **Starting Class:** Rogue
- **Region:** Sump
- **Personality:** Cynical, sarcastic, and guarded, but fiercely loyal to those who earn his trust.
- **Backstory:** Grew up in the capital's slums, part of a thieves' guild. He wants to buy his guild's freedom from a ruthless crime lord.
- **Combat Role:** Melee DPS (High Crit/Status Effects)
- **Recruitment:**
  - **Location:** Residential Area (`residential_area`) at night.
  - **Requirement:** Player must complete a side quest to help him evade city guards.
- **Alignment Anchors:** -Honor (pragmatic), +Compassion (protects the weak)
- **Breaking Points:** Extreme Paragon actions that endanger his guild
- **Transformation Classes:**
  - **Good Path:** Spymaster - Information warfare, protect from shadows
  - **Evil Path:** Contractor - Pure mercenary, no loyalty
- **Personal Quest:** "The Gilded Cage" - Dismantle the crime lord's operations and free the thieves' guild.

---

## 3. Dr. Sybilla ves-Vael

- **Background:** Scholar
- **Starting Class:** Mage (Specializes in Water/Light)
- **Region:** Archivist Core
- **Personality:** Inquisitive, kind, and a bit naive. Fascinated by ancient history and magic.
- **Backstory:** A brilliant student from the Royal Academy of Magic. She is researching the cause of a magical plague that is slowly draining the world of its mana.
- **Combat Role:** Healer / Magical DPS
- **Recruitment:**
  - **Location:** Ancient Library (a new location to be added)
  - **Requirement:** Help her solve a magical puzzle to access a forbidden section of the library.
- **Alignment Anchors:** +Honor (honest researcher), +Compassion (healer at heart)
- **Breaking Points:** Dark magic usage, harming innocents for research
- **Transformation Classes:**
  - **Good Path:** True Healer - Ultimate restoration, cure any ailment
  - **Evil Path:** Blood Alchemist - Sacrifice HP for power
- **Personal Quest:** "Echoes of the Arcane" - Uncover the source of the magical plague and find a way to reverse it.

---

## 4. Garrick nul-Kael

- **Background:** Soldier
- **Starting Class:** Warrior
- **Region:** Exiled (travels between regions)
- **Personality:** Gruff, stoic, and pragmatic. A veteran of many wars, he is weary of conflict but will fight to protect the innocent.
- **Backstory:** A former captain of the Royal Army, he was discharged after disobeying a direct order to sacrifice a village of civilians. He now works as a mercenary.
- **Combat Role:** Balanced DPS / Off-Tank
- **Recruitment:**
  - **Location:** Adventurer's Guild (`guild_hall`)
  - **Requirement:** Hire him for a quest. If his loyalty is high enough by the end, he will offer to join permanently.
- **Alignment Anchors:** +Honor (soldier's code), +Compassion (protect civilians)
- **Breaking Points:** Orders to harm innocents, extreme Tyrant actions
- **Transformation Classes:**
  - **Good Path:** Shield-Captain - Protect all allies, inspire courage
  - **Evil Path:** Reaver - Embrace battlefield brutality
- **Personal Quest:** "The Price of Duty" - Confront his former commander and expose the war crimes that led to his discharge.

---

## 5. Oona Pale-Sap

- **Background:** Outlander
- **Starting Class:** Druid
- **Region:** Choke
- **Personality:** Serene, mysterious, and deeply connected to nature. Speaks in riddles and metaphors.
- **Backstory:** An orphan raised by ancient spirits in a forgotten forest. She acts as a guardian of the natural world and is on a mission to stop the encroachment of industrial "progress".
- **Combat Role:** Support / Control Caster (Healing, Crowd Control)
- **Recruitment:**
  - **Location:** Whispering Woods (new location)
  - **Requirement:** Prove your respect for nature by completing a trial set by the forest spirits.
- **Alignment Anchors:** Neutral Honor, +Compassion (all life is sacred)
- **Breaking Points:** Destruction of natural sites, extreme industrialization
- **Transformation Classes:**
  - **Good Path:** Guardian of Growth - Ultimate nature protection
  - **Evil Path:** Blight-Touched - Corrupted nature powers
- **Personal Quest:** "Heart of the Forest" - Heal a corrupted ancient tree that is the source of the forest's power.

---

## 6. Balance-Ninth (Theron)

- **Background:** Acolyte
- **Starting Class:** Monk
- **Region:** Zenith
- **Personality:** Calm, disciplined, and philosophical. Seeks enlightenment through martial and spiritual balance.
- **Backstory:** A member of a secluded monastic order. His temple was destroyed by a rival sect, and he is now on a pilgrimage to find a new purpose.
- **Combat Role:** Melee DPS (High Speed/Combos)
- **Recruitment:**
  - **Location:** Mountaintop Monastery (new location)
  - **Requirement:** Defeat him in a one-on-one duel to prove your worth.
- **Alignment Anchors:** True Neutral (seeks balance in all things)
- **Breaking Points:** Extreme actions in any direction (breaks balance)
- **Transformation Classes:**
  - **Good Path:** Bodhisattva - Transcendent enlightenment
  - **Evil Path:** Void Walker - Reality manipulation through imbalance
- **Personal Quest:** "The Unbroken Circle" - Rebuild his monastic order and find the true meaning of balance.

---

## 7. Sash "Velvet"

- **Background:** Street Urchin
- **Starting Class:** Trickster
- **Region:** Sump
- **Personality:** Charismatic, manipulative, and unpredictable. She sees life as a grand performance.
- **Backstory:** A master spy and information broker. She works for the highest bidder and is entangled in a web of political intrigue between warring nations.
- **Combat Role:** Debuff Specialist / Ranged DPS
- **Recruitment:**
  - **Location:** Market District (`market_district`)
  - **Requirement:** Outsmart her in a game of wits, which involves a series of choices that test the player's cunning.
- **Alignment Anchors:** -Honor (deception is art), -Compassion (survival first)
- **Breaking Points:** Naive honesty, extreme Saint actions
- **Transformation Classes:**
  - **Good Path:** Selfless Spy - Use deception for good
  - **Evil Path:** Velvet Assassin - Pure assassination, no morals
- **Personal Quest:** "A Dance of Daggers" - Unravel a conspiracy to assassinate a key political figure, forcing her to choose a side.

---

## 8. Ghor Iron-Root

- **Background:** Soldier
- **Starting Class:** Guardian
- **Region:** Choke
- **Personality:** Simple, honest, and fiercely protective. He may not be the sharpest, but his heart is pure gold.
- **Backstory:** An ogre who was ostracized for his size and strength. He found a home in the army but left after being used as a disposable weapon. He now seeks a place where he can belong.
- **Combat Role:** Pure Tank
- **Recruitment:**
  - **Location:** Mountain Pass (new location)
  - **Requirement:** Defend him from a group of monster hunters who are trying to capture him.
- **Alignment Anchors:** +Honor (honest), +Compassion (protective)
- **Breaking Points:** Betrayal, cruelty to innocents
- **Transformation Classes:**
  - **Good Path:** Chieftain - Lead and inspire others
  - **Evil Path:** Warlord - Embrace strength as dominance
- **Personal Quest:** "A Giant's Heart" - Find a lost tribe of peaceful ogres and help them protect their home from destruction.

---

## 9. Vek "Glass"

- **Background:** Scholar
- **Starting Class:** Arcanist
- **Region:** Sump
- **Personality:** Arrogant, brilliant, and obsessed with power. He believes that knowledge is the only true currency.
- **Backstory:** A former Archmage of the Royal Academy who was exiled for practicing forbidden magic. He seeks to unlock the ultimate spell, regardless of the cost.
- **Combat Role:** Glass Cannon Magical DPS
- **Recruitment:**
  - **Location:** Ruined Tower (new location)
  - **Requirement:** Player must have a high intelligence stat or have Sybilla in the party. Agree to help him with his dangerous research.
- **Alignment Anchors:** -Honor (ends justify means), -Compassion (knowledge over people)
- **Breaking Points:** Anti-intellectualism, destroying knowledge
- **Transformation Classes:**
  - **Good Path:** Selfless Scholar - Knowledge to help all
  - **Evil Path:** Fractured God - Ultimate power at any cost
- **Personal Quest:** "The God-Fragment" - A quest to assemble an artifact of immense power, which presents several moral dilemmas about the price of knowledge.

---

## 10. Maeva Still-Water

- **Background:** Outlander
- **Starting Class:** Shaman (Earth-focused)
- **Region:** Choke
- **Personality:** Grounded, patient, and wise. She is the anchor of her community.
- **Backstory:** The leader of a tribe of mountain folk. Her people are threatened by a strange crystal that is corrupting their land and spirits.
- **Combat Role:** AoE Caster / Support
- **Recruitment:**
  - **Location:** Mountain Village (new location)
  - **Requirement:** Help her tribe defend against a wave of corrupted creatures.
- **Alignment Anchors:** +Honor (keeps promises), +Compassion (community first)
- **Breaking Points:** Abandoning those in need, extreme selfishness
- **Transformation Classes:**
  - **Good Path:** Crystal Sage - Master the crystals for good
  - **Evil Path:** Corrupted Crystal - Let the corruption empower you
- **Personal Quest:** "The Crystal's Song" - Journey to the heart of the mountain to either destroy or purify the corrupting crystal.

---

## 11. Void-Exile (Silas)

- **Background:** Acolyte
- **Starting Class:** Priest (Dark-focused)
- **Region:** Zenith
- **Personality:** Brooding, melancholic, and seeking redemption. He struggles with the darkness within him.
- **Backstory:** A former high priest who was excommunicated after turning to dark magic to save a loved one, only to fail. He now wanders the land, using his dark powers for what he believes is the greater good.
- **Combat Role:** Healer / Dark Magic DPS
- **Recruitment:**
  - **Location:** Graveyard (new location) at night.
  - **Requirement:** Help him perform a ritual to appease a powerful spirit. This requires the player to have a high Wisdom stat or Theron in the party.
- **Alignment Anchors:** -Honor (broke his vows), -Compassion (the end justifies suffering)
- **Breaking Points:** Pure light path (rejects his darkness)
- **Transformation Classes:**
  - **Good Path:** Purified - Find redemption and cleanse darkness
  - **Evil Path:** Shadow-Worn - Fully embrace the void
- **Personal Quest:** "Shadow and Light" - A quest to confront the source of his dark power, where he must choose to either embrace it fully or find a path back to the light.

---

## Companion Mechanics

### Loyalty System (0-100)
- Starts at 50 for most companions
- Increases through: agreeing with them, helping their goals, personal quests
- Decreases through: opposing their values, harming their interests
- At 0: Companion leaves permanently
- At 100: Unlocks special abilities and dialogue

### Friendship Levels (0-10)
- Based on time spent together and positive interactions
- +5% combat bonus per friendship level
- Unlocks personal backstory revelations
- Affects romance options (where applicable)

### Alignment Anchors
Each companion has alignment values they resonate with:
- **Anchor Alignment**: Shared values strengthen bond
- **Opposite Alignment**: Conflicting values weaken bond
- **Breaking Point**: Extreme opposite actions cause permanent departure

### Transformation Events
- Triggered by personal quest completion
- Player's alignment influences which class they transform into
- High loyalty companions follow player's path
- Low loyalty companions may rebel against player's path

---

## Recruitment by Region

### Archivist Core
- **Isolde vex-Torvath** - Castle Gate
- **Dr. Sybilla ves-Vael** - Ancient Library

### Sump
- **Bram "The Vulture"** - Residential Area (night)
- **Sash "Velvet"** - Market District
- **Vek "Glass"** - Ruined Tower

### Choke
- **Oona Pale-Sap** - Whispering Woods
- **Ghor Iron-Root** - Mountain Pass
- **Maeva Still-Water** - Mountain Village

### Zenith
- **Balance-Ninth (Theron)** - Mountaintop Monastery
- **Void-Exile (Silas)** - Graveyard (night)

### Exiled
- **Garrick nul-Kael** - Adventurer's Guild (any region)

---

## Party Dynamics

Certain companion combinations create special interactions:

| Pair | Dynamic |
|------|---------|
| Isolde + Sash | Conflict - Honor vs Deception |
| Sybilla + Vek | Rivalry - Ethical vs Unethical research |
| Garrick + Ghor | Friendship - Fellow soldiers |
| Theron + Silas | Philosophical debates - Balance vs Extremes |
| Bram + Sash | Professional respect - Both from Sump |
| Oona + Maeva | Kinship - Both nature-connected |
