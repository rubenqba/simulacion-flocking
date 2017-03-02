#!/bin/bash

simulations=$1

mkdir -p data;

for i in "$simulations"/*; do
    [ ! -d "$i" ] && continue;

    name=data/$(basename "$i").csv
    echo "generando $name..."
    ./trans.sh "$i" | ./reduce.r > $name
done;

cat data/*.csv | sort -r | uniq > final.csv