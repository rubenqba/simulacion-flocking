#!/bin/bash

directory=$1
# egrep '^(C1|C2|C3|Cant\.|Avg)|Cantidad\ de\ Agentes' $FILE | awk -F: '/:/{gsub(/ /, "", $2); print $2;}' | tr ',' '.' | rs -c' ' -C' ' -T| awk 'BEGIN{OFS=","}{print $4,$1,$2,$3,4,15,1,610,180,1,3,25,20,$6,$7,$8,$9,$10,$11,$12}'


echo "Particulas,Valientes,Cobardes,C1Min,C1Max,C2,C3,Vmax,ZonaVirt,Obstac.,Emax,Pmax,Objetivos,RAg,RInter.,RObstac,AvgExt,AvgPol,Col,FactCol,ConsExt,ConsPol,Calidad";

pushd "$directory" 2>&1 >/dev/null;
for i in *.txt; do
    egrep '^(C1|C2|C3|Cant\.|Avg)|Cantidad\ de\ (Agentes|Valientes|Cobardes)' "${i}" |
        awk -F: '/:/{gsub(/ /, "", $2); print $2;}' |
        tr ',' '.' | 
        rs -c' ' -C' ' -T |
        awk 'BEGIN{OFS=","}{print $5,$6,$7,$2,$1,$4,$4,4,15,1,610,180,1,3,25,20,$9,$10,$11,$12,$13,$14,$15}';
done;