/*
 * ================================================================
 * MANUAL WORLDMAP TEMPLATE
 * ================================================================
 * 
 * If the automated script doesn't work, use this template and
 * manually fill in your locations from Azgaar.
 * 
 * To use:
 * 1. Open Azgaar: https://azgaar.github.io/Fantasy-Map-Generator/
 * 2. Create your 4 states (Core, Sump, Choke, Zenith)
 * 3. Place settlements (burgs) at key locations
 * 4. Export JSON or just use the map as reference
 * 5. Fill in the template below
 * 
 * ================================================================
 */

public void initializeWorld() {
    // ==================== CORE REGION (Archivist Core) ====================
    // Colors: Gold/Steel Blue
    // Capital: The Obsidian Keep
    // Biomes: Temperate, stable plateaus
    
    Location coreGate = new Location.Builder("coreGate", "The Grand Foyer", 
        "The main entrance to the Core. Guards check all who enter.")
        .region(RegionType.ARCHIVIST_CORE)
        .safeZone(true)
        .build();
    
    Location coreMarket = new Location.Builder("coreMarket", "The Grand Bazaar", 
        "A bustling marketplace with merchants from across all regions.")
        .region(RegionType.ARCHIVIST_CORE)
        .safeZone(true)
        .shop(true)
        .build();
    
    Location coreAcademy = new Location.Builder("coreAcademy", "The Royal Academy", 
        "The great library-fortress where scholars study ancient records.")
        .region(RegionType.ARCHIVIST_CORE)
        .safeZone(true)
        .build();
    
    Location coreKeep = new Location.Builder("coreKeep", "The Obsidian Keep", 
        "The fortified heart of the Core. Only the worthy may enter.")
        .region(RegionType.ARCHIVIST_CORE)
        .build();
    
    Location coreEstates = new Location.Builder("coreEstates", "The Noble Quarter", 
        "Luxurious mansions of the Core's elite nobility.")
        .region(RegionType.ARCHIVIST_CORE)
        .safeZone(true)
        .build();
    
    Location corePrison = new Location.Builder("corePrison", "The Ironhold", 
        "A grim fortress where the Core's enemies are held.")
        .region(RegionType.ARCHIVIST_CORE)
        .build();

    // ==================== SUMP REGION (The Drowned Wards) ====================
    // Colors: Murky green/brown
    // Capital: The Drowned Market
    // Biomes: Swamp, waterlogged
    
    Location sumpElevator = new Location.Builder("sumpElevator", "The Descent", 
        "A rickety elevator descending into the vertical slums.")
        .region(RegionType.SUMP)
        .build();
    
    Location sumpRatways = new Location.Builder("sumpRatways", "The Ratways", 
        "Narrow passages teeming with desperate folk and worse.")
        .region(RegionType.SUMP)
        .build();
    
    Location sumpMarket = new Location.Builder("sumpMarket", "The Drowned Market", 
        "A black market where anything can be bought for the right price.")
        .region(RegionType.SUMP)
        .shop(true)
        .build();
    
    Location sumpPumps = new Location.Builder("sumpPumps", "The Great Pumps", 
        "Massive pumps keep the water at bay. They groan constantly.")
        .region(RegionType.SUMP)
        .build();
    
    Location sumpArena = new Location.Builder("sumpArena", "The Blood Pit", 
        "An illegal fighting pit where blood sport reigns.")
        .region(RegionType.SUMP)
        .build();
    
    Location sumpDepths = new Location.Builder("sumpDepths", "The Underbelly", 
        "The lowest levels of the Sump. Few return from here.")
        .region(RegionType.SUMP)
        .build();

    // ==================== CHOKE REGION (The Symbiote Wilds) ====================
    // Colors: Deep violet/overgrown green
    // Capital: The Corrupted Heart
    // Biomes: Swamp, Tropical Forest, oppressive
    
    Location chokeBorder = new Location.Builder("chokeBorder", "The Blight Line", 
        "Where civilization ends and the parasitic forest begins.")
        .region(RegionType.CHOKE)
        .build();
    
    Location chokeWoods = new Location.Builder("chokeWoods", "The Watching Trees", 
        "Trees with too many eyes watch your every move.")
        .region(RegionType.CHOKE)
        .build();
    
    Location chokePass = new Location.Builder("chokePass", "The Bone Pass", 
        "A narrow path through the breathing trees.")
        .region(RegionType.CHOKE)
        .build();
    
    Location chokeHeart = new Location.Builder("chokeHeart", "The Corrupted Heart", 
        "The living heart of the forest. It pulses with ancient power.")
        .region(RegionType.CHOKE)
        .safeZone(true)
        .build();
    
    Location chokeBog = new Location.Builder("chokeBog", "The Slow Mire", 
        "Quicksand and worse lurks in this murky swamp.")
        .region(RegionType.CHOKE)
        .build();
    
    Location chokeRuins = new Location.Builder("chokeRuins", "The Forgotten City", 
        "Crumbling structures from before the forest claimed this land.")
        .region(RegionType.CHOKE)
        .build();

    // ==================== ZENITH REGION (The Deep Cloisters) ====================
    // Colors: Pure white/pale gray
    // Capital: The Silent Cloister
    // Biomes: Glacier, Alpine Tundra, hostile
    
    Location zenithSteps = new Location.Builder("zenithSteps", "The Thousand Steps", 
        "Countless stairs leading up to the monasteries above.")
        .region(RegionType.ZENITH)
        .build();
    
    Location zenithMonastery = new Location.Builder("zenithMonastery", "The Silent Cloister", 
        "The main monastery. Monks seek enlightenment here.")
        .region(RegionType.ZENITH)
        .safeZone(true)
        .build();
    
    Location zenithGraveyard = new Location.Builder("zenithGraveyard", "The Fallen Peak", 
        "Where those who failed their trials are buried.")
        .region(RegionType.ZENITH)
        .build();
    
    Location zenithPinnacle = new Location.Builder("zenithPinnacle", "The Apex", 
        "The highest point. Those who reach it are never the same.")
        .region(RegionType.ZENITH)
        .build();

    // ==================== ADD ALL LOCATIONS ====================
    addLocation(coreGate);
    addLocation(coreMarket);
    addLocation(coreAcademy);
    addLocation(coreKeep);
    addLocation(coreEstates);
    addLocation(corePrison);
    
    addLocation(sumpElevator);
    addLocation(sumpRatways);
    addLocation(sumpMarket);
    addLocation(sumpPumps);
    addLocation(sumpArena);
    addLocation(sumpDepths);
    
    addLocation(chokeBorder);
    addLocation(chokeWoods);
    addLocation(chokePass);
    addLocation(chokeHeart);
    addLocation(chokeBog);
    addLocation(chokeRuins);
    
    addLocation(zenithSteps);
    addLocation(zenithMonastery);
    addLocation(zenithGraveyard);
    addLocation(zenithPinnacle);

    // ==================== CORE CONNECTIONS ====================
    coreGate.addConnection(coreMarket, 5);
    coreMarket.addConnection(coreAcademy, 5);
    coreAcademy.addConnection(coreKeep, 10);
    coreMarket.addConnection(coreEstates, 10);
    coreGate.addConnection(corePrison, 15);

    // ==================== REGION TRANSITIONS ====================
    // Core to Sump (downward)
    coreGate.addConnection(sumpElevator, 10);
    
    // Core to Choke (outward)
    coreGate.addConnection(chokeBorder, 20);

    // ==================== SUMP CONNECTIONS ====================
    sumpElevator.addConnection(sumpRatways, 10);
    sumpRatways.addConnection(sumpMarket, 5);
    sumpRatways.addConnection(sumpArena, 10);
    sumpMarket.addConnection(sumpPumps, 15);
    sumpPumps.addConnection(sumpDepths, 20);
    
    // Sump to Choke
    sumpRatways.addConnection(chokeBorder, 15);

    // ==================== CHOKE CONNECTIONS ====================
    chokeBorder.addConnection(chokeWoods, 20);
    chokeWoods.addConnection(chokePass, 25);
    chokeWoods.addConnection(chokeRuins, 15);
    chokePass.addConnection(chokeHeart, 20);
    chokePass.addConnection(chokeBog, 20);
    
    // Choke to Zenith (upward)
    chokePass.addConnection(zenithSteps, 30);

    // ==================== ZENITH CONNECTIONS ====================
    zenithSteps.addConnection(zenithMonastery, 40);
    zenithMonastery.addConnection(zenithGraveyard, 20);
    zenithMonastery.addConnection(zenithPinnacle, 50);

    // ==================== DIRECTIONAL CONNECTIONS (for UI) ====================
    addDirectionalConnection(coreGate, coreMarket, "east");
    addDirectionalConnection(coreMarket, coreGate, "west");
    addDirectionalConnection(coreMarket, coreAcademy, "north");
    addDirectionalConnection(coreAcademy, coreMarket, "south");
    addDirectionalConnection(coreAcademy, coreKeep, "up");
    addDirectionalConnection(coreKeep, coreAcademy, "down");
    addDirectionalConnection(coreMarket, coreEstates, "west");
    addDirectionalConnection(coreEstates, coreMarket, "east");
    addDirectionalConnection(coreGate, corePrison, "south");
    addDirectionalConnection(corePrison, coreGate, "north");
    
    // Region transitions
    addDirectionalConnection(coreGate, sumpElevator, "down");
    addDirectionalConnection(sumpElevator, coreGate, "up");
    addDirectionalConnection(coreGate, chokeBorder, "east");
    addDirectionalConnection(chokeBorder, coreGate, "west");
    
    // Sump
    addDirectionalConnection(sumpElevator, sumpRatways, "down");
    addDirectionalConnection(sumpRatways, sumpElevator, "up");
    addDirectionalConnection(sumpRatways, sumpMarket, "east");
    addDirectionalConnection(sumpMarket, sumpRatways, "west");
    addDirectionalConnection(sumpRatways, sumpArena, "south");
    addDirectionalConnection(sumpArena, sumpRatways, "north");
    addDirectionalConnection(sumpMarket, sumpPumps, "down");
    addDirectionalConnection(sumpPumps, sumpMarket, "up");
    addDirectionalConnection(sumpPumps, sumpDepths, "down");
    addDirectionalConnection(sumpDepths, sumpPumps, "up");
    addDirectionalConnection(sumpRatways, chokeBorder, "north");
    addDirectionalConnection(chokeBorder, sumpRatways, "south");
    
    // Choke
    addDirectionalConnection(chokeBorder, chokeWoods, "north");
    addDirectionalConnection(chokeWoods, chokeBorder, "south");
    addDirectionalConnection(chokeWoods, chokePass, "north");
    addDirectionalConnection(chokePass, chokeWoods, "south");
    addDirectionalConnection(chokeWoods, chokeRuins, "east");
    addDirectionalConnection(chokeRuins, chokeWoods, "west");
    addDirectionalConnection(chokePass, chokeHeart, "west");
    addDirectionalConnection(chokeHeart, chokePass, "east");
    addDirectionalConnection(chokePass, chokeBog, "east");
    addDirectionalConnection(chokeBog, chokePass, "west");
    addDirectionalConnection(chokePass, zenithSteps, "north");
    addDirectionalConnection(zenithSteps, chokePass, "south");
    
    // Zenith
    addDirectionalConnection(zenithSteps, zenithMonastery, "up");
    addDirectionalConnection(zenithMonastery, zenithSteps, "down");
    addDirectionalConnection(zenithMonastery, zenithGraveyard, "west");
    addDirectionalConnection(zenithGraveyard, zenithMonastery, "east");
    addDirectionalConnection(zenithMonastery, zenithPinnacle, "up");
    addDirectionalConnection(zenithPinnacle, zenithMonastery, "down");

    // ==================== SET STARTING LOCATION ====================
    setStartingLocation("coreGate");
    this.currentLocation = getStartingLocation();
    getStartingLocation().setDiscovered(true);
}
