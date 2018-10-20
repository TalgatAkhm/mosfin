import sys
import json
import codecs
import pandas as pd
import csv
from pandas import DataFrame

# inp = str(input("Input file name: "))
# "peluceria.json"
print(sys.argv[1])
file = codecs.open(str(sys.argv[1]),"r", encoding='cp1251') 
u = str(file.read())
out = sys.argv[1][:-4] + "csv"
# print(u)

output = open(out, "w")

df = pd.read_json(u, typ='series')
df.to_csv(output, index=False, sep='\r')
# writer = csv.writer(output)
# for row in json.loads(u):
#    writer.writerow(row)
