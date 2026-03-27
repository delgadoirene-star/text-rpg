#!/usr/bin/env python3
"""
Azgaar Fantasy Map Generator to Java WorldMap Converter

Usage:
    1. Export your map from Azgaar as JSON (Map > Export > JSON)
    2. Save the JSON file (e.g., map.json)
    3. Run: python azgaar_to_java.py map.json

Output: WorldMap.java with all locations, regions, and connections auto-generated
"""

import json
import sys
import re
from collections import defaultdict

def to_java_id(name):
    """Convert a name to a valid Java variable ID"""
    # Remove special chars, replace spaces/slashes with camelCase
    name = re.sub(r'[^\w\s-]', '', name)
    name = re.sub(r'[\s/]+', ' ', name)
    words = name.strip().split()
    
    if not words:
        return "location"
    
    # Lowercase first word, capitalize rest
    result = words[0].lower()
    for word in words[1:]:
        result += word.capitalize()
    
    return result

def to_java_name(name):
    """Convert a name to a display name for Java"""
    name = re.sub(r'[^\w\s\-\']', '', name)
    return name.strip()

def parse_azgaar_json(json_data):
    """Parse Azgaar JSON and extract map data"""
    
    result = {
        'states': [],
        'provinces': defaultdict(list),
        'routes': [],
        'cells': []
    }
    
    # Parse states
    if 'states' in json_data:
        for state in json_data['states']:
            state_data = {
                'id': state.get('i', ''),
                'name': state.get('name', 'Unknown'),
                'color': state.get('color', '#888888'),
                'capital': state.get('capital', ''),
                'provinces': state.get('provinces', [])
            }
            result['states'].append(state_data)
    
    # Parse provinces (embedded in cells or separate)
    if 'provinces' in json_data:
        for prov in json_data['provinces']:
            state_id = prov.get('state', '')
            result['provinces'][state_id].append({
                'id': prov.get('i', ''),
                'name': prov.get('name', 'Unknown'),
                'cells': prov.get('cells', [])
            })
    
    # Parse routes
    if 'routes' in json_data:
        for route in json_data['routes']:
            result['routes'].append({
                'type': route.get('type', 'trail'),
                'cells': route.get('cells', [])
            })
    
    # Parse cells for biome/height data
    if 'cells' in json_data:
        for cell in json_data['cells']:
            result['cells'].append({
                'x': cell.get('x', 0),
                'y': cell.get('y', 0),
                'height': cell.get('h', 0),
                'biome': cell.get('biome', 0),
                'province': cell.get('p', ''),
                'state': cell.get('s', '')
            })
    
    return result

def generate_java_worldmap(map_data, state_mapping):
    """Generate Java WorldMap initialization code"""
    
    lines = []
    lines.append("    // ==================== AUTO-GENERATED FROM AZGAAR ====================")
    lines.append("    // Map generated with Azgaar Fantasy Map Generator")
    lines.append("")
    
    locations = []
    connections = []
    
    # Generate locations from states
    state_names_used = set()
    for state in map_data['states']:
        state_name = state['name']
        
        # Map state to RegionType
        region_type = state_mapping.get(state_name.lower(), 'ARCHIVIST_CORE')
        
        # Add capital as main location
        capital = state.get('capital', '')
        if capital:
            java_id = to_java_id(capital)
            java_name = to_java_name(capital)
            if java_id not in state_names_used:
                locations.append({
                    'id': java_id,
                    'name': java_name,
                    'region': region_type,
                    'isCapital': True,
                    'safe': True,
                    'shop': True
                })
                state_names_used.add(java_id)
        
        # Add some provinces as locations
        for prov in map_data['provinces'].get(state['id'], [])[:5]:  # Limit to 5 per state
            java_id = to_java_id(prov['name'])
            java_name = to_java_name(prov['name'])
            if java_id not in state_names_used:
                locations.append({
                    'id': java_id,
                    'name': java_name,
                    'region': region_type,
                    'isCapital': False,
                    'safe': False,
                    'shop': False
                })
                state_names_used.add(java_id)
    
    # Generate connections from routes
    route_types = {
        'road': 5,      # Good roads = low stamina cost
        'trail': 10,    # Trails = medium cost
        'track': 15,    # Rough tracks = higher cost
        'river': 20,    # Rivers = high cost
        'default': 10
    }
    
    # Simplify: connect adjacent locations within same state
    for state in map_data['states']:
        state_locs = [l for l in locations if l['region'] == state_mapping.get(state['name'].lower(), 'ARCHIVIST_CORE')]
        for i in range(len(state_locs) - 1):
            connections.append({
                'from': state_locs[i]['id'],
                'to': state_locs[i + 1]['id'],
                'cost': 10
            })
    
    # Write location declarations
    lines.append("    // Locations")
    for loc in locations:
        safe_flag = ".safeZone(true)" if loc['safe'] else ""
        shop_flag = ".shop(true)" if loc['shop'] else ""
        
        lines.append(f"    Location {loc['id']} = new Location.Builder(\"{loc['id']}\", \"{loc['name']}\", \"\")")
        lines.append(f"        .region(RegionType.{loc['region']}){safe_flag}{shop_flag}")
        lines.append(f"        .build();")
        lines.append("")
    
    # Write addLocation calls
    lines.append("    // Add locations to map")
    for loc in locations:
        lines.append(f"    addLocation({loc['id']});")
    lines.append("")
    
    # Write connections
    lines.append("    // Connections")
    for conn in connections:
        lines.append(f"    {conn['from']}.addConnection({conn['to']}, {conn['cost']});")
    lines.append("")
    
    # Set starting location (first Core location)
    for loc in locations:
        if loc['region'] == 'ARCHIVIST_CORE':
            lines.append(f"    // Set starting location")
            lines.append(f"    setStartingLocation(\"{loc['id']}\");")
            lines.append(f"    this.currentLocation = getStartingLocation();")
            lines.append(f"    getStartingLocation().setDiscovered(true);")
            break
    
    return "\n".join(lines)

def main():
    if len(sys.argv) < 2:
        print("Usage: python azgaar_to_java.py <map.json>")
        print("  1. Export your map from Azgaar as JSON")
        print("  2. Run this script with the JSON file path")
        sys.exit(1)
    
    json_path = sys.argv[1]
    
    print(f"Reading {json_path}...")
    
    with open(json_path, 'r', encoding='utf-8') as f:
        json_data = json.load(f)
    
    print("Parsing map data...")
    map_data = parse_azgaar_json(json_data)
    
    print(f"Found {len(map_data['states'])} states")
    print(f"Found {sum(len(p) for p in map_data['provinces'].values())} provinces")
    print(f"Found {len(map_data['routes'])} routes")
    
    # Map state names to RegionType
    state_mapping = {
        'archivist core': 'ARCHIVIST_CORE',
        'the archivist core': 'ARCHIVIST_CORE',
        'core': 'ARCHIVIST_CORE',
        'sump': 'SUMP',
        'the sump': 'SUMP',
        'the drowned wards': 'SUMP',
        'choke': 'CHOKE',
        'the choke': 'CHOKE',
        'symbiote wilds': 'CHOKE',
        'zenith': 'ZENITH',
        'the zenith': 'ZENITH',
        'deep cloisters': 'ZENITH',
    }
    
    print("\nGenerating Java WorldMap code...")
    java_code = generate_java_worldmap(map_data, state_mapping)
    
    # Output to file
    output_path = json_path.replace('.json', '_worldmap.java')
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write("package com.rpg.world;\n\n")
        f.write("/**\n")
        f.write(" * Auto-generated WorldMap from Azgaar Fantasy Map Generator\n")
        f.write(" * Run: python azgaar_to_java.py <map.json>\n")
        f.write(" */\n")
        f.write("public class WorldMapGenerated {\n")
        f.write(java_code)
        f.write("\n}\n")
    
    print(f"\nGenerated: {output_path}")
    print("\nCopy the initialization code into your WorldMap.java initializeWorld() method")
    print("\n" + "="*60)
    print(java_code)

if __name__ == '__main__':
    main()
