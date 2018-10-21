#!/usr/bin/python3

import sys
import json
from math import radians, cos, sin, asin, sqrt

radius = 900
bad_filename = sys.argv[1]
good_filenames = ""
good_table = ""
bad_table = []

for i, x in enumerate(sys.argv):
    if i > 1:
        good_filenames += x + ","
good_filenames = good_filenames[:-1]

bad_table = json.loads(str(open(bad_filename, "r").read()))


with open("merged_file.json", "w") as outfile:
    outfile.write('[{}]'.format(','.join([open(f, "r").read() for f in good_filenames.split(",")])))
good_table = json.loads(str(open("merged_file.json", "r").read()))

def haversine(lon1, lat1, lon2, lat2):
    """
    Calculate the great circle distance between two points 
    on the earth (specified in decimal degrees)
    """
    # convert decimal degrees to radians 
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
    # haversine formula 
    dlon = lon2 - lon1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a)) 
    # Radius of earth in kilometers is 6371
    km = 6371* c
    return km * 1000
json_res = "["
intensities = []
for i, x in enumerate(good_table):
    for j, y in enumerate(x):
        intensity = 100
        coords_good = y["geoData"]["coordinates"]
        for k, z in enumerate(bad_table):
            coords_bad = z["geoData"]["coordinates"]
            # get distance in metres:
            dist = haversine(coords_bad[0], coords_bad[1], coords_good[0], coords_good[1])
            if dist <= 2 * radius and intensity >= 20:
                intensity -= 20
        json_res += "{"
        json_res += "\"coordinates\":[{0}, {1}],".format(coords_good[1], coords_good[0])
        json_res += "\"intensity\":{0}".format(intensity)
        json_res += "},"
json_res = json_res[:-1]
json_res += "]"
f = open("intensity_cloks.json", "w")
f.write(json_res)  
