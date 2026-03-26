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

| ID | Name | Region | Alignment Anchor | Combat Role | Starting Class |
|----|------|--------|-----------------|-------------|----------------|
| isolde | Isolde vex-Torvath | Core | H+70/C-40 (Paragon/Harsh) | Off-tank DPS | Vanguard |
| bram | Bram "The Vulture" | Sump | H-60/C+50 (Cunning/Kind) | Burst DPS | Prowler |
| sybilla | Dr. Sybilla ves-Vael | Core | H+40/C+60 (Honorable/Saint) | Ranged Magic + Healing | Scholar |
| garrick | Garrick nul-Kael | Exiled | H+10/C+70 (Pragmatic/Saint) | Control DPS | Tactician |
| oona | Oona Pale-Sap | Choke | H0/C+50 (Pragmatic/Kind) | Control Support | Wildspeaker |
| theron | Balance-Ninth | Zenith | H+80/C+10 (Paragon/Neutral) | Balanced Hybrid | Monk |
| sash | Sash "Velvet" | Sump | H-80/C-30 (Serpent/Harsh) | Debuff/Control | Grifter |
| ghor | Ghor Iron-Root | Choke | H+50/C+80 (Honorable/Saint) | Pure Tank | Guardian |
| vek | Vek "Glass" | Sump | H-40/C-70 (Cunning/Tyrant) | Glass Cannon | Evoker |
| maeva | Maeva Still-Water | Choke | H+30/C+40 (Honorable/Kind) | AoE Caster | Stormcaller |
| silas | Void-Exile | Zenith | H-10/C-20 (Pragmatic/Harsh) | Healer/Dark DPS | Cleric |

**Role Distribution:**
- Tanks: 1 (Ghor)
- DPS: 3 (Bram, Vek, Maeva)
- Off-tank/Hybrid: 2 (Isolde, Theron)
- Control/Debuff: 3 (Garrick, Sash, Oona)
- Support/Healing: 2 (Sybilla, Silas)

---

## 1. Isolde vex-Torvath - The Law Enforcer

- **Region:** Archivist Core
- **Starting Class:** Vanguard (Off-tank DPS)
- **Alignment Anchor:** Honor +70 / Compassion -40 (Paragon/Harsh)
- **Opposing Alignment:** Serpent/Kind
- **Enemy Companion:** Kael (Garrick)
- **Personality:** Righteous, stubborn, zealous. Believes in absolute justice without compromise. Views the law as sacred.

### Backstory
Daughter of a disgraced Duke who was executed for "treason" - he actually stood his ground to protect vulnerable people from zealot mobs. Isolde internalized the opposite lesson: that compassion is weakness and the law must be enforced without mercy. She seeks to restore her family's honor by becoming the ultimate enforcer.

### Recruitment
- **Location:** Castle Gate (`castle_gate`)
- **Requirement:** Player must demonstrate an Honorable act early in the game.

### Quest Subversion: "The Sunken Blade"
She must confront the truth about her father's death. He wasn't a traitor - he died protecting the innocent.

- **Good Path:** Let go of the sword and the oppression it represents. Like her father, choose compassion over rigid law. She unlocks the **Redeemer** class.
- **Bad Path:** Double down, condemn her father, and follow the path of the zealots who killed him. She becomes the **Iron Judge**, an executioner without mercy.

### Combat Role
Off-tank with high damage. Fights on the front line but focuses on punishing enemies rather than soaking damage. Strong against single targets, weak against groups.

### Breaking Points
- Player takes extreme Serpent path actions (deception, betrayal)
- Player shows mercy to "criminals" she considers guilty

---

## 2. Bram "The Vulture" - The Street Rogue

- **Region:** The Sump
- **Starting Class:** Prowler (Burst DPS / Evasion)
- **Alignment Anchor:** Honor -60 / Compassion +50 (Cunning/Kind)
- **Opposing Alignment:** Honorable/Harsh
- **Enemy Companion:** Sera (Isolde)
- **Personality:** Cynical, sarcastic, guarded. Fiercely loyal to those who earn his trust. Would die for his found family.

### Backstory
Grew up in the Sump's slums, part of a thieves' guild that became his only family. He is being extorted by a corrupt Guard Captain who threatens to destroy the guild. He wants to buy or steal his guild's freedom.

### Recruitment
- **Location:** Residential Area (`residential_area`) at night
- **Requirement:** Help him evade city guards in a side quest.

### Quest Subversion: "The Gilded Cage"
Help him save his guild and truly improve the livelihood of the Sump's underbelly.

- **Good Path:** The crime lord offers a deal: "I'll let the guild go, but you take their place." Bram chooses his freedom over his found family's... no wait. He stays. He finds a real solution that helps everyone, not a temporary fix. He unlocks the **Spymaster** class.
- **Bad Path:** The player forsakes him and his guild. Bram tries to kill you in retaliation, his rage a kamikaze attack born from losing his only solace. He becomes the cold **Contractor**, doing anything for gold because loyalty is dead.

### Combat Role
Burst melee DPS with high evasion. Excels at backstabbing and applying bleed/poison. Fast, fragile, devastating from stealth.

### Breaking Points
- Extreme Paragon actions that endanger his guild (refusing to bend rules when lives are at stake)
- Betraying his trust

---

## 3. Dr. Sybilla ves-Vael - The Clinical Healer

- **Region:** Archivist Core
- **Starting Class:** Scholar (Ranged Magic + Healing)
- **Alignment Anchor:** Honor +40 / Compassion +60 (Honorable/Saint)
- **Opposing Alignment:** Deceitful/Tyrant
- **Enemy Companion:** Valerius or Zara
- **Personality:** Inquisitive, kind, clinical. Fascinated by ancient history and magic. Approaches healing like a scientist.

### Backstory
A brilliant researcher from the Royal Academy. She is investigating a magical plague that is slowly draining the world's mana. Her methods are ethical but sometimes agonizingly slow.

### Recruitment
- **Location:** Ancient Library
- **Requirement:** Help her solve a magical puzzle to access a forbidden section.

### Quest Subversion: "Echoes of the Arcane"
Find the cure for the plague.

- **Good Path:** The harmless cure exists but has a massive failure rate and takes decades. People will keep dying in the short term. She chooses this anyway because her Honor demands she not commit a foul act.
- **Bad Path:** The instant, guaranteed cure requires sacrificing a holy being or draining its blood. Her Saint-level Compassion screams to save everyone NOW. She takes the bloody route.

### Combat Role
Ranged magic DPS with healing support. Can deal elemental damage from the back line while keeping the party alive. Weak in melee.

### Breaking Points
- Player uses dark magic openly
- Player harms innocents for research shortcuts

---

## 4. Garrick nul-Kael - The Survivor

- **Region:** Exiled (travels between regions)
- **Starting Class:** Tactician (Control / Executioner DPS)
- **Alignment Anchor:** Honor +10 / Compassion +70 (Pragmatic/Saint)
- **Opposing Alignment:** Pragmatic/Tyrant
- **Enemy Companion:** Valerius or Zara or Silas
- **Personality:** Gruff, stoic, haunted. Carries deep survivor's guilt. Will do anything to protect civilians because he failed once.

### Backstory
Former captain of the Archivist Core's army. He mutinied against his commander who ordered the slaughter of a civilian village. He was discharged and exiled, branded a coward. The truth is more complicated: his defiance may have meant nothing - the village was killed anyway, and he just wasn't there to see it.

### Recruitment
- **Location:** Adventurer's Guild (`guild_hall`)
- **Requirement:** Hire him for a quest. If loyalty is high enough, he joins permanently.

### Quest Subversion: "The Price of Duty"
Confront his former commander and uncover the truth.

- **Good Path:** Face his commander. Discover that his refusal to bend to genocide DID matter - it showed others who he was, and some soldiers followed his example. He finds closure and purpose. He unlocks the **Shield-Captain** class.
- **Bad Path:** Uncover the terrible truth that his defiance changed nothing. Those he thought he stood for were killed anyway, but for nothing. Horrified, he refuses to face it. He becomes more callous and jaded, numbing himself. He unlocks the **Reaver** class, embracing brutality because "nothing matters anyway."

### Combat Role
Control DPS. Excels at exploiting enemy weaknesses, commanding allies, and executing wounded targets. Not a tank - a battlefield tactician who finishes fights.

### Breaking Points
- Orders to harm innocents
- Extreme Tyrant actions
- Player shows cruelty to surrendering enemies

---

## 5. Oona Pale-Sap - The Nature Controller

- **Region:** The Choke
- **Starting Class:** Wildspeaker (Control / Support)
- **Alignment Anchor:** Honor 0 / Compassion +50 (Pragmatic/Kind)
- **Opposing Alignment:** Pragmatic/Harsh
- **Enemy Companion:** Silas
- **Personality:** Serene, mysterious, deeply connected to nature. Speaks in riddles and metaphors. Holds prejudices about "non-natural" sentient beings.

### Backstory
Orphan raised by the symbiotic organisms of the Choke. She acts as a guardian of the wild, believing the forest is pure and all else is corrupting. But the truth is far more disturbing.

### Recruitment
- **Location:** Whispering Woods
- **Requirement:** Prove respect for nature through a trial.

### Quest Subversion: "Heart of the Forest"
The quest to heal a corrupted ancient tree reveals a devastating truth.

- **Good Path:** Through trial and honest love for nature, the tree is healed and nature rebalanced. She also faces her prejudices - accepting that other sentient beings are part of nature too, not parasites. She unlocks the **Guardian of Growth** class.
- **Bad Path:** The virus isn't the tree. The forest itself, her clan, and all the creatures are the liches/fungi of this tree, choking it of life. A reality-shattering realization. She turns her ire against her own, becoming **Blight-Touched**.

### Combat Role
Control/Support. Summons nature creatures, roots enemies, applies crowd control, and provides healing over time. Not a tank - she controls the battlefield from mid-range.

### Breaking Points
- Destruction of natural sites
- Extreme industrialization
- Showing Harsh cruelty to creatures

---

## 6. Balance-Ninth (Theron) - The Enlightened

- **Region:** The Zenith
- **Starting Class:** Monk (Balanced Hybrid)
- **Alignment Anchor:** Honor +80 / Compassion +10 (Paragon/Neutral)
- **Opposing Alignment:** Serpent/Neutral
- **Enemy Companion:** Zara or Kael
- **Personality:** Calm, disciplined, philosophical. Seeks enlightenment through balance. Hides deep existential doubt.

### Backstory
Member of a secluded monastic order that believes true power comes from perfect balance. His temple was destroyed by a rival sect. Now he seeks a new purpose, wrestling with whether enlightenment is even possible.

### Recruitment
- **Location:** Mountaintop Monastery
- **Requirement:** Defeat him in a one-on-one duel.

### Quest Subversion: "The Unbroken Circle"
Find true enlightenment.

- **Good Path:** Find true enlightenment in True Neutrality - the balance of all. Choosing to share this with his students like a Bodhisattva. He unlocks the **Bodhisattva** class.
- **Bad Path:** Fall to the despair of nihility. Let go of all as it has no meaning. A twisted Nirvana, an incomplete journey. He unlocks the **Void Walker** class, seeking to erase the world because nothing has meaning.

### Combat Role
Balanced hybrid. Can deal damage, tank hits, and support allies equally. Jack of all trades, master of none. Excels in sustained fights where his balance gives him versatility.

### Breaking Points
- Extreme actions in any direction (breaks his concept of balance)
- Player makes him choose sides in a clear moral dilemma

---

## 7. Sash "Velvet" - The Information Broker

- **Region:** The Sump
- **Starting Class:** Grifter (Debuff / Control)
- **Alignment Anchor:** Honor -80 / Compassion -30 (Serpent/Harsh)
- **Opposing Alignment:** Paragon/Kind
- **Enemy Companion:** Borin (Garrick) or Elara (Sybilla)
- **Personality:** Charismatic, manipulative, unpredictable. Sees life as performance. Deep down, she's exhausted by the act.

### Backstory
A master spy and information broker. She operates a vast spy network and has never trusted anyone enough to lower her guard. She survives through deception and self-interest.

### Recruitment
- **Location:** Market District (`market_district`)
- **Requirement:** Outsmart her in a game of wits.

### Quest Subversion: "A Dance of Daggers"
She faces her selfishness, a product of constant survival mode.

- **Good Path:** At the last moment, she chooses in favor of others, not self-gain. She must permanently burn her spy network to save the party - giving up all leverage, wealth, and power. She becomes a "nobody" but gains true friends. She unlocks the **Selfless Spy** class.
- **Bad Path:** Fully continues her self-destructing ways. She burns the last bridges she built. She goes with her "benefits" in her pocket but with an empty heart. She unlocks the **Velvet Assassin** class.

### Combat Role
Debuff/Control specialist. Applies confusion, charm, accuracy reduction, and steals enemy buffs. Low direct damage but makes enemies fight each other.

### Breaking Points
- Naive honesty that exposes her operations
- Extreme Saint actions that make her feel judged
- Player refuses to play "the game" with her

---

## 8. Ghor Iron-Root - The Wall

- **Region:** The Choke
- **Starting Class:** Guardian (Pure Tank)
- **Alignment Anchor:** Honor +50 / Compassion +80 (Honorable/Saint)
- **Opposing Alignment:** Cunning/Tyrant
- **Enemy Companion:** Valerius
- **Personality:** Simple, honest, fiercely protective. Not the sharpest, but his heart is pure gold. Learns by watching the player.

### Backstory
An ogre bound to a symbiotic tree that gives him bark-like armor. Ostracized for his size, he found a home in the army but was used as a disposable weapon. He now seeks a place where he can belong and protect those he loves.

### Recruitment
- **Location:** Mountain Pass
- **Requirement:** Defend him from monster hunters.

### Quest Subversion: "A Giant's Heart"
The player's combat choices throughout the game influence his outcome.

- **Good Path:** He becomes chieftain and uses his worldly knowledge to help his tribe thrive and protect itself. He unlocks the **Chieftain** class.
- **Bad Path:** If the player frequently uses "Cruel" or "Deceitful" dialogue options while Ghor is in the party, he learns that "might makes right." He becomes a **Warlord** who leads his tribe into raids of pillaging and killing.

### Combat Role
Pure Tank. The immovable wall. Highest HP and defense in the party. Draws aggro, shields allies, and soaks damage. Low damage output.

### Breaking Points
- Betrayal of trust
- Cruelty to innocents
- Player demonstrates that strength is the only thing that matters

---

## 9. Vek "Glass" - The Arrogant Mage

- **Region:** The Sump (exiled from Core)
- **Starting Class:** Evoker (Glass Cannon Magic DPS)
- **Alignment Anchor:** Honor -40 / Compassion -70 (Cunning/Tyrant)
- **Opposing Alignment:** Paragon/Saint
- **Enemy Companion:** Grak (Ghor) or Elara (Sybilla)
- **Personality:** Arrogant, brilliant, obsessive. Believes knowledge is the only true currency. Views most people as beneath him.

### Backstory
Former Archmage of the Royal Academy, exiled to the Sump for practicing forbidden magic. His fragile, dangerous magic earned him the street name "Glass." He seeks to unlock the ultimate spell regardless of the cost.

### Recruitment
- **Location:** Ruined Tower
- **Requirement:** High INT stat or Sybilla in the party. Agree to help his dangerous research.

### Quest Subversion: "The God-Fragment"
Assemble an artifact of immense power.

- **Good Path:** He lets go of the pursuit. He comes to understand he was not satisfying his desire for knowledge (noble purposes) but his own ego - he wanted to be "the first." He uses his vast intellect to permanently seal/destroy the artifact, sacrificing his chance at godhood to protect people he thought were beneath him. He unlocks the **Selfless Scholar** class.
- **Bad Path:** He gives in to his pride. The artifact consumes him. Its knowledge, so far beyond his mortal mind (like an eldritch being), shatters it. He becomes vegetative. The **Fractured God** class unlocks - a shell of immense power with no mind behind it.

### Combat Role
Glass Cannon Magic DPS. Highest damage output in the party but extremely fragile. Devastating AoE elemental spells. Must be protected.

### Breaking Points
- Anti-intellectualism (destroying books/knowledge)
- Player destroys artifacts or historical sites
- Extreme Saint naivety that he sees as stupidity

---

## 10. Maeva Still-Water - The Grounded Healer

- **Region:** The Choke
- **Starting Class:** Stormcaller (AoE Caster / Support)
- **Alignment Anchor:** Honor +30 / Compassion +40 (Honorable/Kind)
- **Opposing Alignment:** Deceitful/Harsh
- **Enemy Companion:** Valerius or Zara
- **Personality:** Grounded, patient, self-sacrificing to a fault. The anchor of her community. Puts herself second, always.

### Backstory
Leader of a mountain tribe. Her people are threatened by a strange crystal that is corrupting their land. She has been shielding the corruption with her own body, slowly being poisoned.

### Recruitment
- **Location:** Mountain Village
- **Requirement:** Help her tribe defend against corrupted creatures.

### Quest Subversion: "The Crystal's Song"
Journey to the heart of the mountain.

- **Good Path:** She faces herself - her putting herself always second. She manages to surpass the crystal's manipulation (which deals in corruption of extremes). She safely purifies the crystal and gains an artifact of the purest mana that helps her clan for ages to come. She unlocks the **Crystal Sage** class.
- **Bad Path:** The crystal weaponizes her self-sacrificing tendencies. With the threat of horrors upon the world and her clan, it fools her into not purifying it. When confronted, she flees with the corrupted crystal where no one can find them. A Frodo/Gollum situation. She becomes a tragic late-game boss with the **Corrupted Crystal** class.

### Combat Role
AoE Caster / Support. Deals elemental area damage while providing party-wide buffs and off-healing. Not as fragile as Vek, not as durable as a tank.

### Breaking Points
- Abandoning those in need
- Extreme selfishness
- Player pushes her too hard to "sacrifice more"

---

## 11. Void-Exile (Silas) - The Fallen Priest

- **Region:** The Zenith
- **Starting Class:** Cleric (Healing / Dark Magic DPS)
- **Alignment Anchor:** Honor -10 / Compassion -20 (Pragmatic/Harsh)
- **Opposing Alignment:** Pragmatic/Kind
- **Enemy Companion:** Lyra (Oona)
- **Personality:** Brooding, melancholic, bitter. Struggles with growing darkness within him. Seeks meaning in a world that disappointed him.

### Backstory
A former high priest excommunicated after turning to dark magic to save a loved one, only to fail. He now wanders, using dark powers for what he believes is the greater good. But the darkness is growing, and his disappointment in reality leaves him vulnerable.

### Recruitment
- **Location:** Graveyard (night)
- **Requirement:** Help him perform a ritual. Requires high WIS or Theron in the party.

### Quest Subversion: "Shadow and Light"
Confront the source of his dark power.

- **Good Path:** Through reflection, he rein himself in. The bad emotions were inching him closer to becoming a husk for the darkness possessing him. He finds the source and vanquishes it. He unlocks the **Purified** class.
- **Bad Path:** His disappointment in reality, the injustices and biases proven, leaves him an easy prey. He is eaten from the inside out. The party must now face the being wearing his skin. He unlocks the **Shadow-Worn** class, becoming a permanent unsettling party member.

### Combat Role
Healer / Dark Magic DPS hybrid. Can heal allies and deal dark damage to enemies. His healing becomes less effective the more "dark" choices the player makes, reflecting his internal corruption.

### Breaking Points
- Extreme pure light path (he rejects it as hypocritical)
- Player shows naive kindness in a world that hurt him
- Player forces him to confront his darkness too early

---

## Recruitment by Region

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

### Loyalty System (0-100)
- Starts at 50 for most companions
- Increases through: agreeing with them, helping their goals, completing personal quest steps
- Decreases through: opposing their values, harming their interests
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

### Transformation Events
- Triggered by personal quest subversion resolution
- Player's alignment throughout the companion's journey influences which class they transform into
- High loyalty (70+): Companion follows player's recommended path
- Low loyalty (<30): Companion rebels and goes the opposite path regardless
- Mid loyalty (30-70): The quest subversion dialogue choice is decisive

---

## Party Dynamics

Certain companion combinations create special interactions and combat bonuses:

| Pair | Dynamic | Effect |
|------|---------|--------|
| Isolde + Bram | Conflict - Law vs Freedom | -10% loyalty loss for both, but +15% damage when fighting together |
| Sybilla + Vek | Rivalry - Ethical vs Unethical research | Unique dialogue, +10% magic damage when paired |
| Garrick + Ghor | Brotherhood - Fellow soldiers | +10% defense for both |
| Theron + Silas | Philosophical debates | Unlock unique dialogue about balance vs extremes |
| Bram + Sash | Professional respect - Both from Sump | +10% evasion for both |
| Oona + Maeva | Kinship - Both nature-connected | +15% healing effectiveness when paired |
| Isolde + Sash | Ideological enemies | Cannot both be in active party at loyalty <30 |
| Ghor + Vek | Cultural clash | -5% loyalty per quest together, but unique combo attack |

---

## Combat Role Summary

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
