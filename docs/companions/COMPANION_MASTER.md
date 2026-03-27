# INTERNAL / SPOILERS — Definitive Companion Design

**Audience:** Developers, writers, QA.
This document contains the fully integrated narrative and mechanical designs for all 11 companions. 

## The Framework
* **Alignment Anchor:** Where the character naturally sits on the Dual-Axis.
* **Breaking Point:** What specific player actions cause them to leave, betray, or sully your reputation.
* **Quest Subversion:** The climactic choice merging narrative flavor with mechanical Class Transformations.

---

## 1. Isolde vex-Torvath (The Law Enforcer)
* **Region:** The Archivist Core
* **Starting Class:** Vanguard (Off-tank DPS)
* **Alignment Anchor:** Honor +70 / Compassion -40 (Paragon/Harsh)
* **Breaking Point:** Player commits treason against the Core or executes surrendering enemies.
* **Quest Subversion: "The Torvath Record"** * *Setup:* She seeks the physical ledger of a massacre her father ordered, currently locked in the `core_oubliette` (Sunless Gaol). 
  * *The Twist:* Her father died protecting innocents from corrupt officials. 
  * *Good Path (Redeemer):* Embraces compassion over rigid law. 
  * *Bad Path (Zealot Inquisitor):* Doubles down on rigid law. Mechanically loses tanking abilities; passive becomes *Executioner's Right* (+15% damage dealt, +15% damage taken).

## 2. Bram "The Vulture" (The Street Rogue)
* **Region:** The Sump
* **Starting Class:** Prowler (Burst DPS)
* **Alignment Anchor:** Honor -60 / Compassion +50 (Cunning/Kind)
* **Breaking Point:** Player steals from innocents or refuses to help the vulnerable.
* **Quest Subversion: "The Gilded Cage"**
  * *Setup:* His "Thieves Guild" is actually a front for a massive Sump orphanage he funds. A corrupt Guard Captain threatens the children to force Bram into a heist.
  * *Good Path (Spymaster):* Protects the children and finds a real solution to free the Sump.
  * *Bad Path (Contractor):* You forsake the children. Bram survives, but his heart dies. He becomes a cold mercenary.

## 3. Dr. Sybilla ves-Vael (The Clinical Healer)
* **Region:** The Archivist Core
* **Starting Class:** Chronicler (Ranged Magic + Healing)
* **Alignment Anchor:** Honor +40 / Compassion +60 (Honorable/Saint)
* **Breaking Point:** Player destroys historical knowledge or harms innocents for research.
* **Quest Subversion: "Echoes of the Arcane"**
  * *Setup:* Finding the cure for the mana plague.
  * *Good Path (True Healer):* Takes the slow, harmless route. Rejects shortcuts.
  * *Bad Path (Blood Alchemist):* Takes the bloody shortcut. Her `Healing Light` ability is permanently replaced with `Transfusion` (must drain enemies/allies to heal).

## 4. Garrick nul-Kael (The Survivor)
* **Region:** Exiled (Travels)
* **Starting Class:** Tactician (Control DPS)
* **Alignment Anchor:** Honor +10 / Compassion +70 (Pragmatic/Saint)
* **Breaking Point:** Player orders attacks on civilians or abandons allies.
* **Quest Subversion: "The Last Command"**
  * *Setup:* Gathers a group of Core deserters to confront his old Commander.
  * *The Twist:* His past honorable mutiny actually changed nothing; the village he tried to save died anyway.
  * *Good Path (Shield-Captain):* Accepts his past failure but finds new purpose in leading his deserters as protectors.
  * *Bad Path (Reaver):* Succumbs to the realization that his defiance was meaningless. Embraces nihilism and brutality.

## 5. Oona Pale-Sap (The Nature Controller)
* **Region:** The Choke
* **Starting Class:** Wildspeaker (Control Support)
* **Alignment Anchor:** Honor 0 / Compassion +50 (Pragmatic/Kind)
* **Breaking Point:** Player destroys natural sites or treats the wild as a resource.
* **Quest Subversion: "Root and Reach"**
  * *Setup:* Journey to heal the "dying" forest.
  * *The Twist:* The forest isn't dying; it is a massive, horrific parasitic organism choking the earth. 
  * *Good Path (Guardian of Growth):* Purges the parasite. Her summons change from fungal spores to beasts/elementals.
  * *Bad Path (Blight-Touched):* Protects the parasite. Her healing spells become parasitic (drains player max HP during fights to heal the party).

## 6. Balance-Ninth / Theron (The Enlightened)
* **Region:** The Zenith
* **Starting Class:** Monk (Balanced Hybrid)
* **Alignment Anchor:** Honor +80 / Compassion +10 (Paragon/Neutral)
* **Breaking Point:** Player acts in pure extremes (Pure Saint or Pure Tyrant) shattering his view of balance.
* **Quest Subversion: "The Final Test"**
  * *Setup:* Theron was once a violent street criminal named "Keth." His old gang finds him.
  * *The Twist:* The gang leader is a fallen Zenith monk who failed the teachings.
  * *Good Path (Bodhisattva):* Achieves true enlightenment, reconciling his dark past with his peaceful present.
  * *Bad Path (Void Walker):* The nihilism wins. He merges monastic power with his old ruthless criminal nature to erase meaning entirely.

## 7. Sash "Velvet" (The Information Broker)
* **Region:** The Sump
* **Starting Class:** Grifter (Debuff/Control)
* **Alignment Anchor:** Honor -80 / Compassion -30 (Serpent/Harsh)
* **Breaking Point:** Player exposes her spy operations or naively trusts enemies.
* **Quest Subversion: "The Velvet Mask"**
  * *Setup:* The massive spy network hunting her finally corners the party.
  * *The Twist:* The leader of the network is her mother, who abandoned her.
  * *Good Path (Selfless Spy):* Burns her entire network and leverage to save the party, breaking the cycle of generational trauma.
  * *Bad Path (Velvet Assassin):* Kills her mother and takes over the network, becoming perfectly untouchable but entirely hollow.

## 8. Ghor Iron-Root (The Wall)
* **Region:** The Choke
* **Starting Class:** Guardian (Pure Tank)
* **Alignment Anchor:** Honor +50 / Compassion +80 (Honorable/Saint)
* **Breaking Point:** Mimics the player. If player is consistently cruel, he learns the wrong lessons.
* **Quest Subversion: "A Giant's Heart"**
  * *Good Path (Chieftain):* Uses worldly knowledge to protect his tribe.
  * *Bad Path (Warlord):* Learns "might makes right." Leaves the party and becomes a recurring Nemesis on the World Map, raiding settlements.

## 9. Vek "Glass" (The Arrogant Mage)
* **Region:** The Sump
* **Starting Class:** Evoker (Glass Cannon Magic)
* **Alignment Anchor:** Honor -40 / Compassion -70 (Cunning/Tyrant)
* **Breaking Point:** Player destroys magical artifacts or books.
* **Quest Subversion: "The God-Fragment"**
  * *Good Path (Selfless Scholar):* Sacrifices his chance at godhood to permanently seal an eldritch artifact, saving the world.
  * *Bad Path (Fractured God):* His arrogant mortal mind is shattered by eldritch knowledge. Becomes a vegetative shell of immense power with no personality.

## 10. Maeva Still-Water (The Grounded Healer)
* **Region:** The Choke
* **Starting Class:** Stormcaller (AoE Caster/Support)
* **Alignment Anchor:** Honor +30 / Compassion +40 (Honorable/Kind)
* **Breaking Point:** Player exploits her self-sacrificing nature.
* **Quest Subversion: "Crystal Tears"**
  * *Good Path (Crystal Sage):* Safely purifies the crystal, putting herself first for once.
  * *Bad Path (Corrupted Crystal):* The crystal weaponizes her self-sacrifice. She flees with it to protect the world, becoming a tragic Gollum-esque late-game boss.

## 11. Void-Exile / Silas (The Fallen Priest)
* **Region:** The Zenith
* **Starting Class:** Cleric (Healer/Dark DPS)
* **Alignment Anchor:** Honor -10 / Compassion -20 (Pragmatic/Harsh)
* **Breaking Point:** Player forces him to confront his darkness before he is ready.
* **Quest Subversion: "Shadow and Light"**
  * *Mechanic:* His healing spell (`Twilight Mend`) inversely scales with the Player's Alignment. The more "evil" the player, the less he heals and the more damage he does.
  * *Good Path (Purified):* Overcomes his bitterness, purges the dark entity.
  * *Bad Path (Shadow-Worn):* The disappointment in reality breaks him; the dark entity wears his skin.