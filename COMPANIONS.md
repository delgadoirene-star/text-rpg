# Companion Design - The Shattered Records

## Overview

The game features 11 unique companions distributed across 4 world regions. Each companion has:
- **Alignment Anchors**: Where they naturally sit on the Dual-Axis
- **Breaking Points**: What player actions cause them to lose loyalty, leave, or turn hostile
- **Quest Subversion**: A climactic personal quest choice that challenges their worldview
- **Transformation Classes**: Good/Evil class paths based on player influence

---

## The Rework Framework

Every companion is designed through a three-step filter:

1. **The Alignment Anchor**: Where does this companion naturally sit on the Dual-Axis (Honor <-> Deceit, Compassion <-> Cruelty)?

2. **The Breaking Point**: What specific player alignment or action (Flag) will cause them to lose loyalty, leave the party, or even turn hostile?

3. **The Quest Subversion**: How can their personal quest challenge their alignment anchor, forcing the player to either push them toward an "Ultimate/Secret" mindset or ruin their purity?

---

## Companion Summary Table

| ID | Name | Region | Alignment Anchor | Combat Role | Starting Class | Recruitment |
|----|------|--------|-----------------|-------------|----------------|-------------|
| isolde | Isolde vex-Torvath | Core | H+70/C-40 (Paragon/Harsh) | Off-tank DPS | Vanguard | Early (Act 1) |
| bram | Bram "The Vulture" | Sump | H-60/C+50 (Cunning/Kind) | Burst DPS | Prowler | Early (Act 1) |
| sybilla | Dr. Sybilla ves-Vael | Core | H+40/C+60 (Honorable/Saint) | Ranged Magic + Healing | Chronicler | Early (Act 1) |
| garrick | Garrick nul-Kael | Exiled | H+10/C+70 (Pragmatic/Saint) | Control DPS | Tactician | Mid (Act 2) |
| oona | Oona Pale-Sap | Choke | H0/C+50 (Pragmatic/Kind) | Control Support | Wildspeaker | Mid (Act 2) |
| theron | Balance-Ninth | Zenith | H+80/C+10 (Paragon/Neutral) | Balanced Hybrid | Monk | Late (Act 3) |
| sash | Sash "Velvet" | Sump | H-80/C-30 (Serpent/Harsh) | Debuff/Control | Grifter | Mid (Act 2) |
| ghor | Ghor Iron-Root | Choke | H+50/C+80 (Honorable/Saint) | Pure Tank | Guardian | Early (Act 1) |
| vek | Vek "Glass" | Sump | H-40/C-70 (Cunning/Tyrant) | Glass Cannon | Evoker | Mid (Act 2) |
| maeva | Maeva Still-Water | Choke | H+30/C+40 (Honorable/Kind) | AoE Caster | Stormcaller | Mid (Act 2) |
| silas | Void-Exile | Zenith | H-10/C-20 (Pragmatic/Harsh) | Healer/Dark DPS | Cleric | Late (Act 3) |

**Role Distribution:**
- Tanks: 1 (Ghor)
- DPS: 3 (Bram, Vek, Maeva)
- Off-tank/Hybrid: 2 (Isolde, Theron)
- Control/Debuff: 3 (Garrick, Sash, Oona)
- Support/Healing: 2 (Sybilla, Silas)

**Alignment Distribution:**

| Axis | Positive | Neutral | Negative |
|------|----------|---------|----------|
| Honor | 6 (Isolde, Sybilla, Garrick, Theron, Ghor, Maeva) | 1 (Oona) | 4 (Bram, Sash, Vek, Silas) |
| Compassion | 6 (Bram, Sybilla, Garrick, Oona, Ghor, Maeva) | 2 (Theron, Silas) | 3 (Isolde, Sash, Vek) |

**Design Note:** The Compassion axis intentionally skews toward +Compassion companions. This reflects the game's narrative theme: cruelty is isolating. Tyrant players will find fewer allies, but those allies (Isolde, Sash, Vek) are extremely powerful and synergize well together. The difficulty of maintaining a Tyrant party is part of the intended experience.

---

## 1. Isolde vex-Torvath - The Law Enforcer

- **Region:** Archivist Core
- **Starting Class:** Vanguard (Off-tank DPS)
- **Alignment Anchor:** Honor +70 / Compassion -40 (Paragon/Harsh)
- **Opposing Alignment:** Serpent/Kind
- **Rival Companion:** Bram (opposing alignment: law vs freedom)
- **Personality:** Righteous, stubborn, zealous. Believes in absolute justice without compromise. Views the law as sacred.

### Backstory
Daughter of a disgraced Duke who was executed for "treason" - he actually stood his ground to protect vulnerable people from zealot mobs. Isolde internalized the opposite lesson: that compassion is weakness and the law must be enforced without mercy. She seeks to restore her family's honor by becoming the ultimate enforcer.

### Recruitment
- **Location:** Castle Gate (`castle_gate`)
- **Timing:** Early (Act 1) - Available after the prologue
- **Requirement:** Player must demonstrate an Honorable act early in the game.
- **Missable:** No - she will seek you out if you meet the requirement

### Quest Subversion: "The Sunken Blade"
She must confront the truth about her father's death. He wasn't a traitor - he died protecting the innocent.

- **Good Path:** Let go of the sword and the oppression it represents. Like her father, choose compassion over rigid law. She unlocks the **Redeemer** class.
- **Bad Path:** Double down, condemn her father, and follow the path of the zealots who killed him. She becomes the **Zealot Inquisitor**, an executioner without mercy.

### Combat Role
Off-tank with high damage. Fights on the front line but focuses on punishing enemies rather than soaking damage. Strong against single targets, weak against groups.

### Signature Abilities
- **Righteous Charge** (25 Focus): Rush to an enemy, dealing damage and taunting them for 2 turns.
- **Judge's Verdict** (40 Focus): Massive single-target strike. +50% damage against enemies at <50% HP.
- **Unyielding Law** (Passive): Gain +15% damage resistance when at full HP.

### Breaking Points
- Player takes extreme Serpent path actions (deception, betrayal)
- Player shows mercy to "criminals" she considers guilty

**Consequences:**
- **Loyalty 20-30:** Warning confrontation - she threatens to leave
- **Loyalty 10-20:** Final ultimatum - one more transgression and she's gone
- **Loyalty 0-10:** Departure - she leaves the party permanently
- **Breaking Point + Betrayal:** If she's actively betrayed (e.g., you frame her, side with criminals against her), she becomes a late-game boss fight

---

## 2. Bram "The Vulture" - The Street Rogue

- **Region:** The Sump
- **Starting Class:** Prowler (Burst DPS / Evasion)
- **Alignment Anchor:** Honor -60 / Compassion +50 (Cunning/Kind)
- **Opposing Alignment:** Honorable/Harsh
- **Rival Companion:** Isolde (opposing alignment: freedom vs law)
- **Personality:** Cynical, sarcastic, guarded. Fiercely loyal to those who earn his trust. Would die for his found family.

### Backstory
Grew up in the Sump's slums, part of a thieves' guild that became his only family. He is being extorted by a corrupt Guard Captain who threatens to destroy the guild. He wants to buy or steal his guild's freedom.

### Recruitment
- **Location:** Residential Area (`residential_area`) at night
- **Timing:** Early (Act 1) - Available in the Sump introduction
- **Requirement:** Help him evade city guards in a side quest.
- **Missable:** Yes - if you turn him in to the guards, he becomes unavailable

### Quest Subversion: "The Gilded Cage"
Help him save his guild and truly improve the livelihood of the Sump's underbelly.

- **Good Path:** The crime lord offers a deal: "I'll let the guild go, but you take their place." Bram chooses his freedom over his found family's... no wait. He stays. He finds a real solution that helps everyone, not a temporary fix. He unlocks the **Spymaster** class.
- **Bad Path:** The player forsakes him and his guild. Bram tries to kill you in retaliation, his rage a kamikaze attack born from losing his only solace. He becomes the cold **Contractor**, doing anything for gold because loyalty is dead.

### Combat Role
Burst melee DPS with high evasion. Excels at backstabbing and applying bleed/poison. Fast, fragile, devastating from stealth.

### Signature Abilities
- **Backstab** (20 Focus): Attack from the shadows. +100% crit chance if enemy is unaware or flanked.
- **Venom Blade** (30 Focus): Apply a stacking poison that deals damage over 3 turns.
- **Vanish** (35 Focus): Enter stealth. Next attack gains Backstab bonus and ignores armor.

### Breaking Points
- Extreme Paragon actions that endanger his guild (refusing to bend rules when lives are at stake)
- Betraying his trust

**Consequences:**
- **Loyalty 20-30:** Cold shoulder - he stops sharing personal information
- **Loyalty 10-20:** He starts planning an exit, visible distrust
- **Loyalty 0-10:** He disappears one night, taking some gold with him
- **Breaking Point + Guild Betrayal:** If you directly harm his guild (turn them in, raid their hideout), he attempts to assassinate you - a difficult mid-combat ambush

---

## 3. Dr. Sybilla ves-Vael - The Clinical Healer

- **Region:** Archivist Core
- **Starting Class:** Chronicler (Ranged Magic + Healing)
- **Alignment Anchor:** Honor +40 / Compassion +60 (Honorable/Saint)
- **Opposing Alignment:** Deceitful/Tyrant
- **Rival Companion:** Vek (opposing alignment: ethics vs obsession)
- **Personality:** Inquisitive, kind, clinical. Fascinated by ancient history and magic. Approaches healing like a scientist.

### Backstory
A brilliant researcher from the Royal Academy. She is investigating a magical plague that is slowly draining the world's mana. Her methods are ethical but sometimes agonizingly slow.

### Recruitment
- **Location:** Ancient Library
- **Timing:** Early (Act 1) - Available after reaching the Archivist Core
- **Requirement:** Help her solve a magical puzzle to access a forbidden section.
- **Missable:** No - the library remains accessible

### Quest Subversion: "Echoes of the Arcane"
Find the cure for the plague.

- **Good Path:** The harmless cure exists but has a massive failure rate and takes decades. People will keep dying in the short term. She chooses this anyway because her Honor demands she not commit a foul act. She unlocks the **True Healer** class - mastery of pure restoration magic.
- **Bad Path:** The instant, guaranteed cure requires sacrificing a holy being or draining its blood. Her Saint-level Compassion screams to save everyone NOW. She takes the bloody route. She unlocks the **Blood Alchemist** class - healing through forbidden sacrifice.

### Combat Role
Ranged magic DPS with healing support. Can deal elemental damage from the back line while keeping the party alive. Weak in melee.

### Signature Abilities
- **Analytical Strike** (20 Focus): Ranged attack that reveals enemy weaknesses for 3 turns (+15% damage from all sources).
- **Healing Light** (25 Focus): Restore HP to one ally. More effective on wounded targets.
- **Arcane Barrier** (35 Focus): Grant a shield to all allies that absorbs damage for 2 turns.

### Breaking Points
- Player uses dark magic openly
- Player harms innocents for research shortcuts

**Consequences:**
- **Loyalty 20-30:** Heated arguments about ethics
- **Loyalty 10-20:** She refuses to use her strongest abilities
- **Loyalty 0-10:** She leaves to "continue research elsewhere"
- **Breaking Point + Innocent Harm:** If you sacrifice innocents for power while she's present, she becomes a witness who spreads word of your crimes, causing reputation loss in all regions

---

## 4. Garrick nul-Kael - The Survivor

- **Region:** Exiled (travels between regions)
- **Starting Class:** Tactician (Control / Executioner DPS)
- **Alignment Anchor:** Honor +10 / Compassion +70 (Pragmatic/Saint)
- **Opposing Alignment:** Pragmatic/Tyrant
- **Rival Companion:** Vek (opposing alignment: protection vs cruelty)
- **Personality:** Gruff, stoic, haunted. Carries deep survivor's guilt. Will do anything to protect civilians because he failed once.

### Backstory
Former captain of the Archivist Core's army. He mutinied against his commander who ordered the slaughter of a civilian village. He was discharged and exiled, branded a coward. The truth is more complicated: his defiance may have meant nothing - the village was killed anyway, and he just wasn't there to see it.

### Recruitment
- **Location:** Adventurer's Guild (`guild_hall`)
- **Timing:** Mid (Act 2) - Available after completing first major quest
- **Requirement:** Hire him for a quest. If loyalty is high enough, he joins permanently.
- **Missable:** Yes - if you mistreat him during his contract, he refuses to join

### Quest Subversion: "The Price of Duty"
Confront his former commander and uncover the truth.

- **Good Path:** Face his commander. Discover that his refusal to bend to genocide DID matter - it showed others who he was, and some soldiers followed his example. He finds closure and purpose. He unlocks the **Shield-Captain** class.
- **Bad Path:** Uncover the terrible truth that his defiance changed nothing. Those he thought he stood for were killed anyway, but for nothing. Horrified, he refuses to face it. He becomes more callous and jaded, numbing himself. He unlocks the **Reaver** class, embracing brutality because "nothing matters anyway."

### Combat Role
Control DPS. Excels at exploiting enemy weaknesses, commanding allies, and executing wounded targets. Not a tank - a battlefield tactician who finishes fights.

### Signature Abilities
- **Mark Target** (15 Focus): Mark an enemy. All allies deal +20% damage to marked target for 3 turns.
- **Execute** (35 Focus): Massive damage to enemies below 30% HP. Instant kill below 15%.
- **Rally** (30 Focus): Remove debuffs from all allies and grant +10% damage for 2 turns.

### Breaking Points
- Orders to harm innocents
- Extreme Tyrant actions
- Player shows cruelty to surrendering enemies

**Consequences:**
- **Loyalty 20-30:** He becomes insubordinate, questioning orders openly
- **Loyalty 10-20:** He refuses direct orders that conflict with his values
- **Loyalty 0-10:** He abandons the party, calling you "no better than his old commander"
- **Breaking Point + Massacre:** If you order a civilian massacre, he turns on you immediately - a difficult boss fight where his tactical mastery works against you

---

## 5. Oona Pale-Sap - The Nature Controller

- **Region:** The Choke
- **Starting Class:** Wildspeaker (Control / Support)
- **Alignment Anchor:** Honor 0 / Compassion +50 (Pragmatic/Kind)
- **Opposing Alignment:** Pragmatic/Harsh
- **Rival Companion:** Silas (opposing alignment: nature vs corruption)
- **Personality:** Serene, mysterious, deeply connected to nature. Speaks in riddles and metaphors. Holds prejudices about "non-natural" sentient beings.

### Backstory
Orphan raised by the symbiotic organisms of the Choke. She acts as a guardian of the wild, believing the forest is pure and all else is corrupting. But the truth is far more disturbing.

### Recruitment
- **Location:** Whispering Woods
- **Timing:** Mid (Act 2) - Available when the Choke region opens
- **Requirement:** Prove respect for nature through a trial.
- **Missable:** Yes - destroying natural sites before meeting her locks her out

### Quest Subversion: "Heart of the Forest"
The quest to heal a corrupted ancient tree reveals a devastating truth.

- **Good Path:** Through trial and honest love for nature, the tree is healed and nature rebalanced. She also faces her prejudices - accepting that other sentient beings are part of nature too, not parasites. She unlocks the **Guardian of Growth** class.
- **Bad Path:** The virus isn't the tree. The forest itself, her clan, and all the creatures are the liches/fungi of this tree, choking it of life. A reality-shattering realization. She turns her ire against her own, becoming **Blight-Touched**.

### Combat Role
Control/Support. Summons nature creatures, roots enemies, applies crowd control, and provides healing over time. Not a tank - she controls the battlefield from mid-range.

### Signature Abilities
- **Entangling Roots** (25 Focus): Root an enemy in place for 2 turns. They cannot move or dodge.
- **Nature's Blessing** (30 Focus): Apply regeneration to all allies, healing over 4 turns.
- **Summon Spore Guardian** (40 Focus): Summon a creature that taunts enemies and explodes on death, poisoning nearby foes.

### Breaking Points
- Destruction of natural sites
- Extreme industrialization
- Showing Harsh cruelty to creatures

**Consequences:**
- **Loyalty 20-30:** She becomes distant, speaking only in cryptic warnings
- **Loyalty 10-20:** Her summons become aggressive toward the party
- **Loyalty 0-10:** She vanishes into the forest, becoming unreachable
- **Breaking Point + Nature Destruction:** If you burn/destroy a sacred grove, she summons the forest against you - environmental hazards for the rest of your time in the Choke

---

## 6. Balance-Ninth (Theron) - The Enlightened

- **Region:** The Zenith
- **Starting Class:** Monk (Balanced Hybrid)
- **Alignment Anchor:** Honor +80 / Compassion +10 (Paragon/Neutral)
- **Opposing Alignment:** Serpent/Neutral
- **Rival Companion:** Sash (opposing alignment: truth vs deception)
- **Personality:** Calm, disciplined, philosophical. Seeks enlightenment through balance. Hides deep existential doubt.

### Backstory
Member of a secluded monastic order that believes true power comes from perfect balance. His temple was destroyed by a rival sect. Now he seeks a new purpose, wrestling with whether enlightenment is even possible.

### Recruitment
- **Location:** Mountaintop Monastery
- **Timing:** Late (Act 3) - Available after proving yourself worthy
- **Requirement:** Defeat him in a one-on-one duel.
- **Missable:** No - you can retry the duel until you win

### Quest Subversion: "The Unbroken Circle"
Find true enlightenment.

- **Good Path:** Find true enlightenment in True Neutrality - the balance of all. Choosing to share this with his students like a Bodhisattva. He unlocks the **Bodhisattva** class.
- **Bad Path:** Fall to the despair of nihility. Let go of all as it has no meaning. A twisted Nirvana, an incomplete journey. He unlocks the **Void Walker** class, seeking to erase the world because nothing has meaning.

### Combat Role
Balanced hybrid. Can deal damage, tank hits, and support allies equally. Jack of all trades, master of none. Excels in sustained fights where his balance gives him versatility.

### Signature Abilities
- **Flowing Strike** (20 Focus): Attack that adapts - deals more damage if enemy is strong, heals self if enemy is weak.
- **Inner Balance** (25 Focus): Cleanse all debuffs from self and gain +20% to all stats for 2 turns.
- **Pressure Point** (35 Focus): Strike that stuns the enemy for 1 turn and reduces their damage by 25% for 3 turns.

### Breaking Points
- Extreme actions in any direction (breaks his concept of balance)
- Player makes him choose sides in a clear moral dilemma

**Consequences:**
- **Loyalty 20-30:** Meditation sessions where he questions following you
- **Loyalty 10-20:** He enters a spiritual crisis, becoming unreliable in combat
- **Loyalty 0-10:** He leaves to "find a new path," disappointed but not hostile
- **Breaking Point + Forced Extremism:** If you consistently push him to extreme actions (pure Saint or pure Tyrant), he has a mental break - either becoming a pacifist who won't fight or a nihilist who attacks randomly

---

## 7. Sash "Velvet" - The Information Broker

- **Region:** The Sump
- **Starting Class:** Grifter (Debuff / Control)
- **Alignment Anchor:** Honor -80 / Compassion -30 (Serpent/Harsh)
- **Opposing Alignment:** Paragon/Kind
- **Rival Companion:** Theron (opposing alignment: deception vs truth)
- **Personality:** Charismatic, manipulative, unpredictable. Sees life as performance. Deep down, she's exhausted by the act.

### Backstory
A master spy and information broker. She operates a vast spy network and has never trusted anyone enough to lower her guard. She survives through deception and self-interest.

### Recruitment
- **Location:** Market District (`market_district`)
- **Timing:** Mid (Act 2) - Available after building a reputation in the Sump
- **Requirement:** Outsmart her in a game of wits.
- **Missable:** No - she respects persistence and will offer rematches

### Quest Subversion: "A Dance of Daggers"
She faces her selfishness, a product of constant survival mode.

- **Good Path:** At the last moment, she chooses in favor of others, not self-gain. She must permanently burn her spy network to save the party - giving up all leverage, wealth, and power. She becomes a "nobody" but gains true friends. She unlocks the **Selfless Spy** class.
- **Bad Path:** Fully continues her self-destructing ways. She burns the last bridges she built. She goes with her "benefits" in her pocket but with an empty heart. She unlocks the **Velvet Assassin** class.

### Combat Role
Debuff/Control specialist. Applies confusion, charm, accuracy reduction, and steals enemy buffs. Low direct damage but makes enemies fight each other.

### Signature Abilities
- **Whispered Lies** (25 Focus): Confuse an enemy for 2 turns. Confused enemies may attack allies.
- **Steal Secrets** (30 Focus): Remove all buffs from an enemy and apply them to yourself.
- **Velvet Trap** (40 Focus): Charm an enemy to fight for you for 2 turns. Bosses are immune but take damage instead.

### Breaking Points
- Naive honesty that exposes her operations
- Extreme Saint actions that make her feel judged
- Player refuses to play "the game" with her

**Consequences:**
- **Loyalty 20-30:** She starts keeping secrets from you
- **Loyalty 10-20:** She feeds you false information "for your own good"
- **Loyalty 0-10:** She sells your secrets to enemies before disappearing
- **Breaking Point + Public Exposure:** If you publicly reveal her spy network or identity, she becomes a recurring enemy agent who sabotages your quests from the shadows

---

## 8. Ghor Iron-Root - The Wall

- **Region:** The Choke
- **Starting Class:** Guardian (Pure Tank)
- **Alignment Anchor:** Honor +50 / Compassion +80 (Honorable/Saint)
- **Opposing Alignment:** Cunning/Tyrant
- **Rival Companion:** Vek (opposing alignment: gentle giant vs arrogant intellect)
- **Personality:** Simple, honest, fiercely protective. Not the sharpest, but his heart is pure gold. Learns by watching the player.

### Backstory
An ogre bound to a symbiotic tree that gives him bark-like armor. Ostracized for his size, he found a home in the army but was used as a disposable weapon. He now seeks a place where he can belong and protect those he loves.

### Recruitment
- **Location:** Mountain Pass
- **Timing:** Early (Act 1) - Available when traveling through the Choke
- **Requirement:** Defend him from monster hunters.
- **Missable:** Yes - if you side with the hunters, he dies or flees permanently

### Quest Subversion: "A Giant's Heart"
The player's combat choices throughout the game influence his outcome.

- **Good Path:** He becomes chieftain and uses his worldly knowledge to help his tribe thrive and protect itself. He unlocks the **Chieftain** class.
- **Bad Path:** If the player frequently uses "Cruel" or "Deceitful" dialogue options while Ghor is in the party, he learns that "might makes right." He becomes a **Warlord** who leads his tribe into raids of pillaging and killing.

### Combat Role
Pure Tank. The immovable wall. Highest HP and defense in the party. Draws aggro, shields allies, and soaks damage. Low damage output.

### Signature Abilities
- **Iron Bark** (20 Focus): Gain +50% damage resistance for 2 turns. Taunt all enemies.
- **Living Shield** (30 Focus): Intercept all attacks on a chosen ally for 2 turns.
- **Rooted Slam** (25 Focus): AoE attack that damages and slows all nearby enemies. Low damage but high control.

### Breaking Points
- Betrayal of trust
- Cruelty to innocents
- Player demonstrates that strength is the only thing that matters

**Consequences:**
- **Loyalty 20-30:** He becomes confused, questioning his understanding of you
- **Loyalty 10-20:** He starts mimicking your cruelty in disturbing ways
- **Loyalty 0-10:** He leaves, heartbroken, returning to his tribe
- **Breaking Point + Cruelty Lesson:** If you consistently teach him that "might makes right," he doesn't leave - he transforms into the Warlord path even without completing his personal quest, returning later as a raid boss leading his tribe against you

---

## 9. Vek "Glass" - The Arrogant Mage

- **Region:** The Sump (exiled from Core)
- **Starting Class:** Evoker (Glass Cannon Magic DPS)
- **Alignment Anchor:** Honor -40 / Compassion -70 (Cunning/Tyrant)
- **Opposing Alignment:** Paragon/Saint
- **Rival Companions:** Sybilla (ethics), Ghor (intelligence), Garrick (cruelty)
- **Personality:** Arrogant, brilliant, obsessive. Believes knowledge is the only true currency. Views most people as beneath him.

### Backstory
Former Archmage of the Royal Academy, exiled to the Sump for practicing forbidden magic. His fragile, dangerous magic earned him the street name "Glass." He seeks to unlock the ultimate spell regardless of the cost.

### Recruitment
- **Location:** Ruined Tower
- **Timing:** Mid (Act 2) - Available after rumors of forbidden magic spread
- **Requirement:** High INT stat or Sybilla in the party. Agree to help his dangerous research.
- **Missable:** Yes - refusing his research offer or having low INT with no Sybilla locks him out

### Quest Subversion: "The God-Fragment"
Assemble an artifact of immense power.

- **Good Path:** He lets go of the pursuit. He comes to understand he was not satisfying his desire for knowledge (noble purposes) but his own ego - he wanted to be "the first." He uses his vast intellect to permanently seal/destroy the artifact, sacrificing his chance at godhood to protect people he thought were beneath him. He unlocks the **Selfless Scholar** class.
- **Bad Path:** He gives in to his pride. The artifact consumes him. Its knowledge, so far beyond his mortal mind (like an eldritch being), shatters it. He becomes vegetative. The **Fractured God** class unlocks - a shell of immense power with no mind behind it.

### Combat Role
Glass Cannon Magic DPS. Highest damage output in the party but extremely fragile. Devastating AoE elemental spells. Must be protected.

### Signature Abilities
- **Arcane Barrage** (30 Focus): Massive single-target magic damage. Crits deal +100% damage.
- **Elemental Storm** (45 Focus): AoE attack hitting all enemies. Element matches current battlefield conditions.
- **Glass Cannon** (Passive): +30% magic damage, but -20% HP. High risk, high reward.

### Breaking Points
- Anti-intellectualism (destroying books/knowledge)
- Player destroys artifacts or historical sites
- Extreme Saint naivety that he sees as stupidity

**Consequences:**
- **Loyalty 20-30:** He mocks you openly, undermining your authority
- **Loyalty 10-20:** He conducts dangerous experiments without telling you
- **Loyalty 0-10:** He abandons you for "more worthy patrons"
- **Breaking Point + Knowledge Destruction:** If you destroy a major artifact or library, he attacks you on the spot in a rage - a dangerous fight given his magical power

---

## 10. Maeva Still-Water - The Grounded Healer

- **Region:** The Choke
- **Starting Class:** Stormcaller (AoE Caster / Support)
- **Alignment Anchor:** Honor +30 / Compassion +40 (Honorable/Kind)
- **Opposing Alignment:** Deceitful/Harsh
- **Rival Companion:** Sash (opposing alignment: selfless vs selfish)
- **Personality:** Grounded, patient, self-sacrificing to a fault. The anchor of her community. Puts herself second, always.

### Backstory
Leader of a mountain tribe. Her people are threatened by a strange crystal that is corrupting their land. She has been shielding the corruption with her own body, slowly being poisoned.

### Recruitment
- **Location:** Mountain Village
- **Timing:** Mid (Act 2) - Available when exploring deeper into the Choke
- **Requirement:** Help her tribe defend against corrupted creatures.
- **Missable:** No - the village remains accessible and she will accept help

### Quest Subversion: "The Crystal's Song"
Journey to the heart of the mountain.

- **Good Path:** She faces herself - her putting herself always second. She manages to surpass the crystal's manipulation (which deals in corruption of extremes). She safely purifies the crystal and gains an artifact of the purest mana that helps her clan for ages to come. She unlocks the **Crystal Sage** class.
- **Bad Path:** The crystal weaponizes her self-sacrificing tendencies. With the threat of horrors upon the world and her clan, it fools her into not purifying it. When confronted, she flees with the corrupted crystal where no one can find them. A Frodo/Gollum situation. She becomes a tragic late-game boss with the **Corrupted Crystal** class.

### Combat Role
AoE Caster / Support. Deals elemental area damage while providing party-wide buffs and off-healing. Not as fragile as Vek, not as durable as a tank.

### Signature Abilities
- **Lightning Call** (25 Focus): AoE attack that chains between enemies. More enemies = more damage.
- **Soothing Rain** (30 Focus): Heal all allies and cleanse one debuff from each.
- **Crystal Ward** (35 Focus): Shield all allies. The shield reflects a portion of damage taken back to attackers.

### Breaking Points
- Abandoning those in need
- Extreme selfishness
- Player pushes her too hard to "sacrifice more"

**Consequences:**
- **Loyalty 20-30:** She overextends herself, taking damage to help others
- **Loyalty 10-20:** She becomes passive-aggressive, healing you less
- **Loyalty 0-10:** She returns to her village, unable to watch you abandon people
- **Breaking Point + Exploitation:** If you manipulate her self-sacrificing nature (forcing her to sacrifice her health/power for your gain repeatedly), she breaks down and the crystal corruption accelerates - she becomes the Corrupted Crystal boss early

---

## 11. Void-Exile (Silas) - The Fallen Priest

- **Region:** The Zenith
- **Starting Class:** Cleric (Healing / Dark Magic DPS)
- **Alignment Anchor:** Honor -10 / Compassion -20 (Pragmatic/Harsh)
- **Opposing Alignment:** Pragmatic/Kind
- **Rival Companion:** Oona (opposing alignment: darkness vs nature)
- **Personality:** Brooding, melancholic, bitter. Struggles with growing darkness within him. Seeks meaning in a world that disappointed him.

### Backstory
A former high priest excommunicated after turning to dark magic to save a loved one, only to fail. He now wanders, using dark powers for what he believes is the greater good. But the darkness is growing, and his disappointment in reality leaves him vulnerable.

### Recruitment
- **Location:** Graveyard (night)
- **Timing:** Late (Act 3) - Available after dark events shake the Zenith
- **Requirement:** Help him perform a ritual. Requires high WIS or Theron in the party.
- **Missable:** Yes - disrupting his ritual or having low WIS with no Theron locks him out

### Quest Subversion: "Shadow and Light"
Confront the source of his dark power.

- **Good Path:** Through reflection, he rein himself in. The bad emotions were inching him closer to becoming a husk for the darkness possessing him. He finds the source and vanquishes it. He unlocks the **Purified** class.
- **Bad Path:** His disappointment in reality, the injustices and biases proven, leaves him an easy prey. He is eaten from the inside out. The party must now face the being wearing his skin. He unlocks the **Shadow-Worn** class, becoming a permanent unsettling party member.

### Combat Role
Healer / Dark Magic DPS hybrid. Can heal allies and deal dark damage to enemies. His healing becomes less effective the more "dark" choices the player makes, reflecting his internal corruption.

### Signature Abilities
- **Twilight Mend** (25 Focus): Heal an ally. Effectiveness scales inversely with player's Tyrant alignment.
- **Shadow Bolt** (20 Focus): Dark damage attack. Deals bonus damage if Silas is below 50% HP.
- **Life Drain** (35 Focus): Steal HP from an enemy. The darker the player's path, the more damage (but less healing).

### Breaking Points
- Extreme pure light path (he rejects it as hypocritical)
- Player shows naive kindness in a world that hurt him
- Player forces him to confront his darkness too early

**Consequences:**
- **Loyalty 20-30:** His healing becomes unreliable, sometimes hurting instead
- **Loyalty 10-20:** Dark whispers become audible to the party
- **Loyalty 0-10:** He loses control to the darkness temporarily, causing party damage before fleeing
- **Breaking Point + Forced Purity:** If you force him into extreme Saint actions while he's conflicted, the darkness within rebels - he transforms into Shadow-Worn mid-quest and must be fought or talked down

---

## Recruitment by Region

### Recruitment Timing Overview

| Act | Companions Available | Notes |
|-----|---------------------|-------|
| **Act 1 (Early)** | Isolde, Bram, Sybilla, Ghor | Core party formation - covers Tank, DPS, Healer, Off-tank |
| **Act 2 (Mid)** | Garrick, Oona, Sash, Vek, Maeva | World opens up - specialized roles and dark options |
| **Act 3 (Late)** | Theron, Silas | Endgame specialists - balance and corruption themes |

### Archivist Core
- **Isolde vex-Torvath** - Castle Gate (Off-tank DPS)
- **Dr. Sybilla ves-Vael** - Ancient Library (Ranged Magic + Healing)

### The Sump
- **Bram "The Vulture"** - Residential Area, night (Burst DPS)
- **Sash "Velvet"** - Market District (Debuff/Control)
- **Vek "Glass"** - Ruined Tower (Glass Cannon Magic)

### The Choke
- **Oona Pale-Sap** - Whispering Woods (Control Support)
- **Ghor Iron-Root** - Mountain Pass (Pure Tank)
- **Maeva Still-Water** - Mountain Village (AoE Caster/Support)

### The Zenith
- **Balance-Ninth (Theron)** - Mountaintop Monastery (Balanced Hybrid)
- **Void-Exile (Silas)** - Graveyard, night (Healer/Dark DPS)

### Exiled
- **Garrick nul-Kael** - Adventurer's Guild, any region (Control DPS)

---

## Companion Mechanics

### Party Size and Management

**Active Party:** Player + 3 companions (4 total combatants)
**Reserve (Bench):** Unlimited - all recruited companions remain available

**Swapping:**
- Can swap party members at any camp or safe location
- Cannot swap during dungeons or combat
- Some story moments may force specific companions into the party

**Bench Effects:**
- Benched companions still gain 50% of combat EXP
- Benched companions do NOT gain loyalty from player choices (they weren't there to see it)
- Benched companions CAN lose loyalty from major world-altering decisions (news travels)
- Personal quest progress pauses for benched companions

### Loyalty System (0-100)

Starting loyalty varies by companion personality:

| Companion | Starting Loyalty | Reason |
|-----------|------------------|--------|
| Isolde | 55 | Respects you for showing Honor |
| Bram | 45 | Guarded - trust must be earned |
| Sybilla | 60 | Open and collaborative by nature |
| Garrick | 50 | Professional - starts neutral |
| Oona | 50 | Curious but wary of outsiders |
| Theron | 55 | Respects you for defeating him |
| Sash | 40 | Highly suspicious - trust is rare |
| Ghor | 60 | Grateful and loyal-hearted |
| Vek | 45 | Arrogant - you must prove your worth |
| Maeva | 55 | Appreciative of your help |
| Silas | 45 | Bitter - connection takes effort |

**Loyalty Changes:**
- Increases through: agreeing with their values, helping their goals, completing personal quest steps
- Decreases through: opposing their values, harming their interests, breaking promises
- At 0: Companion leaves permanently (or turns hostile if deeply betrayed)
- At 100: Unlocks special abilities and deepens personal quest options

### Friendship Levels (0-10)
- Based on time spent together and positive interactions
- +5% combat bonus per friendship level
- Unlocks personal backstory revelations
- Determines if companion follows player's path or rebels during transformation

### Alignment Anchors (The Breaking Point)
Each companion has alignment values they resonate with:
- **Anchor Alignment**: Shared values strengthen bond (+loyalty per aligned choice)
- **Opposite Alignment**: Conflicting values weaken bond (-loyalty per conflicting choice)
- **Breaking Point**: Extreme opposite actions cause permanent departure or hostility

**Loyalty Change Formula:**

| Player Action | Loyalty Change |
|---------------|----------------|
| Minor aligned action (dialogue choice) | +2 to +5 |
| Major aligned action (quest outcome) | +10 to +15 |
| Minor opposing action | -2 to -5 |
| Major opposing action | -10 to -15 |
| Breaking point trigger | -30 to -50 (may cause immediate departure) |

**Alignment Distance Modifier:**
The further the player's alignment is from the companion's anchor, the harder it is to gain loyalty:
- Within 30 points: Normal loyalty gains
- 30-60 points away: 75% loyalty gains, 125% loyalty losses
- 60+ points away: 50% loyalty gains, 150% loyalty losses

**Example:** Isolde's anchor is H+70/C-40. If the player is at H-50/C+60 (nearly opposite), every aligned action gives half loyalty and every opposing action hurts 50% more.

### Transformation Events
- Triggered by personal quest subversion resolution
- Player's alignment throughout the companion's journey influences which class they transform into
- High loyalty (70+): Companion follows player's recommended path
- Low loyalty (<30): Companion rebels and goes the opposite path regardless
- Mid loyalty (30-70): The quest subversion dialogue choice is decisive

---

## Party Dynamics

Certain companion combinations create special interactions and combat bonuses:

### Implementation Details

**How Dynamics Work:**
- Bonuses are calculated at the start of each combat
- Only active party members count (not benched)
- Bonuses stack if multiple dynamics apply
- Negative dynamics (conflicts) can be overcome through high friendship

**Loyalty Threshold for Conflict Resolution:**
- If BOTH companions have 70+ loyalty to the player, conflict penalties are halved
- If BOTH have 90+ loyalty, conflicts are ignored (they trust the player's judgment)

### Positive Dynamics

| Pair | Dynamic | Combat Effect | Exploration Effect |
|------|---------|---------------|-------------------|
| Isolde + Bram | Conflict - Law vs Freedom | +15% damage when fighting together | Unique banter, -10% loyalty loss when they disagree |
| Sybilla + Vek | Rivalry - Ethical vs Unethical | +10% magic damage when paired | Unique dialogue about research ethics |
| Garrick + Ghor | Brotherhood - Fellow soldiers | +10% defense for both | War stories unlock unique dialogue |
| Theron + Silas | Philosophical debates | +5% all stats | Deep conversations about meaning |
| Bram + Sash | Professional respect | +10% evasion for both | Joint infiltration missions available |
| Oona + Maeva | Nature kinship | +15% healing effectiveness | Can find hidden nature paths together |

### Negative Dynamics (Conflicts)

| Pair | Dynamic | Effect | Resolution |
|------|---------|--------|------------|
| Isolde + Sash | Ideological enemies | Cannot both be in party if either has <30 loyalty | High loyalty (70+) allows uneasy truce |
| Ghor + Vek | Cultural clash | -5% loyalty per quest together | Unique combo attack unlocks at friendship 7+ |
| Sybilla + Silas | Light vs Dark magic | Healing conflicts (-10% healing when both present) | Resolved after either completes personal quest |

### Unique Combo Attacks

Some dynamics unlock special dual-character abilities:

| Pair | Combo Attack | Effect | Unlock Condition |
|------|--------------|--------|------------------|
| Isolde + Garrick | **Pincer Strike** | Both attack same target, +50% damage, guaranteed hit | Friendship 5+ with both |
| Bram + Sash | **Shadow Network** | AoE debuff that reveals all enemy weaknesses | Friendship 6+ with both |
| Ghor + Vek | **Arcane Bulwark** | Ghor shields Vek; Vek's next spell +100% damage | Friendship 7+ despite conflict |
| Oona + Maeva | **Storm of Growth** | Massive nature AoE that heals allies and damages enemies | Both at 80+ loyalty |
| Theron + Silas | **Twilight Balance** | Full party heal + cleanse + damage boost | Both personal quests completed |

---

## Combat Role Summary

### Archetype Comparisons

**Healers: Sybilla vs Silas**
| Aspect | Sybilla (Chronicler) | Silas (Cleric) |
|--------|---------------------|----------------|
| Healing Style | Pure, consistent, party-wide | Corruption-dependent, self-sustaining |
| Damage | Low (utility focused) | High (dark magic) |
| Alignment Fit | Saint/Paragon players | Tyrant players (healing degrades on light path) |
| Unique Niche | Shields + weakness reveal | Life drain + damage when corrupted |
| Best For | Stable, reliable healing | Aggressive, risky healing |

**DPS Mages: Vek vs Maeva**
| Aspect | Vek (Evoker) | Maeva (Stormcaller) |
|--------|--------------|---------------------|
| Damage Type | Single-target burst | AoE sustained |
| Survivability | Extremely fragile (-20% HP) | Moderate (can off-heal) |
| Utility | Pure damage | Damage + party support |
| Alignment Fit | Tyrant players | Kind players |
| Best For | Boss fights, glass cannon | Mob fights, balanced teams |

**Melee DPS: Bram vs Isolde**
| Aspect | Bram (Prowler) | Isolde (Vanguard) |
|--------|----------------|-------------------|
| Damage Type | Burst (stealth) | Sustained (front line) |
| Survivability | Evasion-based | Armor-based |
| Utility | Poison/bleed, vanish | Taunt, judgment |
| Alignment Fit | Serpent players | Paragon players |
| Best For | Assassinating priority targets | Off-tanking + damage |

**Control: Sash vs Oona vs Garrick**
| Aspect | Sash (Grifter) | Oona (Wildspeaker) | Garrick (Tactician) |
|--------|----------------|--------------------|--------------------|
| Control Style | Debuffs, charm | Roots, summons | Marks, commands |
| Damage | Low | Low-Medium | Medium-High |
| Utility | Buff stealing | Healing over time | Ally buffs |
| Alignment Fit | Serpent players | Nature lovers | Pragmatic players |
| Best For | Turning enemies | Crowd control | Execution builds |

| Role | Companions | Purpose |
|------|-----------|---------|
| **Pure Tank** | Ghor | Absorb damage, draw aggro, shield allies |
| **Off-tank DPS** | Isolde | Front line damage dealer with survivability |
| **Burst DPS** | Bram | High damage from stealth, fragile |
| **Glass Cannon** | Vek | Highest magic damage, must be protected |
| **AoE Caster** | Maeva | Area damage + party buffs |
| **Control DPS** | Garrick | Exploit weaknesses, execute wounded targets |
| **Balanced Hybrid** | Theron | Versatile, can fill any role adequately |
| **Control/Support** | Oona | Crowd control, nature summons, healing |
| **Debuff/Control** | Sash | Debuff enemies, make them fight each other |
| **Ranged Magic + Healing** | Sybilla | Back line damage + party healing |
| **Healer/Dark DPS** | Silas | Healing that degrades with corruption |
