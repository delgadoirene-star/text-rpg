#!/usr/bin/env python3
"""
Azgaar Fantasy Map Generator - Complete WorldMap Generator

This script takes Azgaar's JSON export and generates a complete,
ready-to-use initializeWorld() method for WorldMap.java

Usage:
    1. Export from Azgaar: Map > Export > JSON (save as map.json)
    2. Edit this script to set your state name mappings below
    3. Run: python azgaar_to_worldmap.py map.json

Output: Copy the generated method directly into WorldMap.java
"""

import json
import sys
import re
from collections import defaultdict

# ============================================================
# CONFIGURATION: Map your Azgaar state names to RegionType
# ============================================================
STATE_TO_REGION = {
    # Lowercase names as keys
    'archivist core': 'ARCHIVIST_CORE',
    'the archivist core': 'ARCHIVIST_CORE', 
    'core': 'ARCHIVIST_CORE',
    
    'sump': 'SUMP',
    'the sump': 'SUMP',
    'drowned wards': 'SUMP',
    
    'choke': 'CHOKE',
    'the choke': 'CHOKE',
    'symbiote wilds': 'CHOKE',
    
    'zenith': 'ZENITH',
    'the zenith': 'ZENITH',
    'deep cloisters': 'ZENITH',
    
    # Add more mappings as needed
    # 'my custom state': 'ARCHIVIST_CORE',
}

# State colors (for reference/logging)
STATE_COLORS = {
    'ARCHIVIST_CORE': '#C9A227',  # Gold
    'SUMP': '#4A5D23',            # Murky green
    'CHOKE': '#6B4E71',           # Sickly violet
    'ZENITH': '#E8E8E8',          # Pale white
}

# ============================================================

def sanitize_name(name):
    """Clean name for use in code"""
    name = re.sub(r'[^\w\s\-\']', '', name)
    return name.strip()

def to_java_id(name):
    """Convert to camelCase Java ID"""
    name = sanitize_name(name)
    words = name.replace('-', ' ').replace('_', ' ').split()
    if not words:
        return 'location'
    result = words[0].lower()
    for word in words[1:]:
        result += word.capitalize()
    return result

def get_region_type(state_name, state_mapping):
    """Map state name to RegionType enum"""
    state_lower = state_name.lower().strip()
    return state_mapping.get(state_lower, 'ARCHIVIST_CORE')

class MapGenerator:
    def __init__(self):
        self.locations = []
        self.connections = []
        self.state_mapping = {}
        
    def parse_json(self, json_data):
        """Parse Azgaar JSON into internal structure"""
        
        # Handle different JSON structures
        states_data = []
        provinces_data = defaultdict(list)
        
        # States are usually at root level
        if 'states' in json_data:
            for state in json_data['states']:
                state_info = {
                    'id': state.get('i', state.get('id', '')),
                    'name': state.get('name', 'Unknown'),
                    'color': state.get('color', '#888888'),
                    'capital': state.get('capital', ''),
                    'burgs': state.get('burgs', []),
                    'cells': state.get('cells', [])
                }
                states_data.append(state_info)
        
        # Parse provinces if available
        if 'provinces' in json_data:
            for prov in json_data['provinces']:
                state_id = prov.get('state', prov.get('s', ''))
                provinces_data[state_id].append({
                    'id': prov.get('i', prov.get('id', '')),
                    'name': prov.get('name', 'Unknown'),
                    'cells': prov.get('cells', [])
                })
        
        # Parse cells for additional data
        cells_data = []
        if 'cells' in json_data:
            cells_data = json_data['cells']
        
        return {
            'states': states_data,
            'provinces': provinces_data,
            'cells': cells_data
        }
    
    def process_burgs(self, map_data):
        """Extract locations from 'burgs' (settlements)"""
        
        for state in map_data['states']:
            state_name = state['name']
            region_type = get_region_type(state_name, self.state_mapping)
            
            # Process burgs (settlements/cities)
            for burg in state.get('burgs', []):
                if burg.get('capital'):
                    # Capital - always safe, usually has shop
                    self.add_location(
                        name=burg['name'],
                        region=region_type,
                        is_capital=True,
                        is_safe=True,
                        has_shop=True
                    )
                elif burg.get('town', False) or burg.get('city', False):
                    # Towns and cities
                    self.add_location(
                        name=burg['name'],
                        region=region_type,
                        is_capital=False,
                        is_safe=True,
                        has_shop=bool(burg.get('city', False))
                    )
                elif burg.get('village', False):
                    # Villages
                    self.add_location(
                        name=burg['name'],
                        region=region_type,
                        is_capital=False,
                        is_safe=False,
                        has_shop=False
                    )
    
    def process_cells_by_height(self, map_data):
        """Generate additional locations based on cell heights"""
        
        heights_by_state = defaultdict(list)
        
        for state in map_data['states']:
            state_id = state['id']
            region_type = get_region_type(state['name'], self.state_mapping)
            
            for cell in state.get('cells', []):
                height = cell.get('h', cell.get('height', 0.5))
                heights_by_state[state_id].append(height)
        
        # Add locations based on average height per state
        for state in map_data['states']:
            state_id = state['id']
            state_name = state['name']
            region_type = get_region_type(state_name, self.state_mapping)
            
            heights = heights_by_state.get(state_id, [])
            if heights:
                avg_height = sum(heights) / len(heights)
                
                # Add a "low" location for low-lying areas (Sump-like)
                if avg_height < 0.3 and region_type == 'SUMP':
                    self.add_location(
                        name=f"Lower {state_name}",
                        region=region_type,
                        is_capital=False,
                        is_safe=False,
                        has_shop=False
                    )
                
                # Add a "high" location for elevated areas
                if avg_height > 0.7 and region_type == 'ZENITH':
                    self.add_location(
                        name=f"Peaks of {state_name}",
                        region=region_type,
                        is_capital=False,
                        is_safe=True,
                        has_shop=False
                    )
    
    def add_location(self, name, region, is_capital=False, is_safe=False, has_shop=False):
        """Add a location to the map"""
        java_id = to_java_id(name)
        
        # Avoid duplicates
        if any(loc['java_id'] == java_id for loc in self.locations):
            return
            
        self.locations.append({
            'name': sanitize_name(name),
            'java_id': java_id,
            'region': region,
            'is_capital': is_capital,
            'is_safe': is_safe,
            'has_shop': has_shop
        })
    
    def generate_connections(self):
        """Auto-generate connections between locations in same region"""
        
        # Group by region
        by_region = defaultdict(list)
        for loc in self.locations:
            by_region[loc['region']].append(loc)
        
        # Connect locations within same region linearly
        for region, locs in by_region.items():
            for i in range(len(locs) - 1):
                self.connections.append({
                    'from': locs[i]['java_id'],
                    'to': locs[i + 1]['java_id'],
                    'cost': 10
                })
    
    def generate_java_code(self, class_name='WorldMap'):
        """Generate complete Java code for initializeWorld() method"""
        
        lines = []
        lines.append("    // ==================== AUTO-GENERATED FROM AZGAAR ====================")
        lines.append("    // Generated by azgaar_to_worldmap.py")
        lines.append("    // States mapped: " + ", ".join(set(loc['region'] for loc in self.locations)))
        lines.append("")
        
        # Location declarations
        for loc in self.locations:
            safe = ".safeZone(true)" if loc['is_safe'] else ""
            shop = ".shop(true)" if loc['has_shop'] else ""
            capital_note = " // CAPITAL" if loc['is_capital'] else ""
            
            lines.append(f"    Location {loc['java_id']} = new Location.Builder(")
            lines.append(f"            \"{loc['java_id']}\", \"{loc['name']}\", \"{loc['name']} - Auto-generated location.\")")
            lines.append(f"            .region(RegionType.{loc['region']}){safe}{shop}")
            lines.append(f"            .build();{capital_note}")
            lines.append("")
        
        # Add locations to map
        lines.append("    // Add all locations to the map")
        for loc in self.locations:
            lines.append(f"    addLocation({loc['java_id']});")
        lines.append("")
        
        # Connections
        lines.append("    // Location connections (bi-directional)")
        for conn in self.connections:
            lines.append(f"    {conn['from']}.addConnection({conn['to']}, {conn['cost']});")
        lines.append("")
        
        # Set starting location
        for loc in self.locations:
            if loc['is_capital'] and loc['region'] == 'ARCHIVIST_CORE':
                lines.append("    // Set starting location")
                lines.append(f"    setStartingLocation(\"{loc['java_id']}\");")
                lines.append(f"    this.currentLocation = getStartingLocation();")
                lines.append(f"    getStartingLocation().setDiscovered(true);")
                break
        else:
            # Fallback to first location
            if self.locations:
                lines.append("    // Set starting location")
                lines.append(f"    setStartingLocation(\"{self.locations[0]['java_id']}\");")
                lines.append(f"    this.currentLocation = getStartingLocation();")
                lines.append(f"    getStartingLocation().setDiscovered(true);")
        
        return "\n".join(lines)

def main():
    if len(sys.argv) < 2:
        print("=" * 60)
        print("Azgaar Fantasy Map Generator -> Java WorldMap Converter")
        print("=" * 60)
        print("")
        print("Usage: python azgaar_to_worldmap.py <map.json>")
        print("")
        print("Steps:")
        print("  1. Create your map in Azgaar (https://azgaar.github.io)")
        print("  2. Configure 4 states: Core, Sump, Choke, Zenith")
        print("  3. Add settlements (burgs) - they become locations")
        print("  4. Export: Map > Export > JSON")
        print("  5. Run: python azgaar_to_worldmap.py your_map.json")
        print("  6. Copy the output into WorldMap.java initializeWorld()")
        print("")
        print("Edit STATE_TO_REGION in this script to match your state names")
        print("=" * 60)
        sys.exit(1)
    
    json_path = sys.argv[1]
    
    print(f"Reading: {json_path}")
    
    with open(json_path, 'r', encoding='utf-8') as f:
        json_data = json.load(f)
    
    print("Parsing map data...")
    generator = MapGenerator()
    generator.state_mapping = STATE_TO_REGION
    
    map_data = generator.parse_json(json_data)
    
    print(f"States found: {len(map_data['states'])}")
    for state in map_data['states']:
        region = get_region_type(state['name'], STATE_TO_REGION)
        burgs = len(state.get('burgs', []))
        print(f"  - {state['name']} -> {region} ({burgs} settlements)")
    
    print("\nProcessing settlements...")
    generator.process_burgs(map_data)
    
    print(f"Locations generated: {len(generator.locations)}")
    
    print("\nGenerating connections...")
    generator.generate_connections()
    print(f"Connections generated: {len(generator.connections)}")
    
    print("\n" + "=" * 60)
    print("GENERATED JAVA CODE (copy into WorldMap.java):")
    print("=" * 60)
    print()
    
    java_code = generator.generate_java_code()
    print(java_code)
    
    # Save to file
    output_path = json_path.replace('.json', '_generated.java')
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write("// Auto-generated WorldMap initialization\n")
        f.write("// From: " + json_path + "\n")
        f.write("// Run: python azgaar_to_worldmap.py <map.json>\n\n")
        f.write(java_code)
    
    print()
    print("=" * 60)
    print(f"Saved to: {output_path}")

if __name__ == '__main__':
    main()
