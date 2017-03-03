#!/bin/bash

directory=$1
# egrep '^(C1|C2|C3|Cant\.|Avg)|Cantidad\ de\ Agentes' $FILE | awk -F: '/:/{gsub(/ /, "", $2); print $2;}' | tr ',' '.' | rs -c' ' -C' ' -T| awk 'BEGIN{OFS=","}{print $4,$1,$2,$3,4,15,1,610,180,1,3,25,20,$6,$7,$8,$9,$10,$11,$12}'

dir=$(dirname $0)

#pushd "$directory" 2>&1 >/dev/null;
for i in "${directory}"/*.txt; do
    #echo "Particulas,Valientes,Cobardes,C1Min,C1Max,C2,C3,Vmax,ZonaVirt,Obstac.,Emax,Pmax,Objetivos,RAg,RInter.,RObstac,AvgExt,AvgPol,Col,FactCol,ConsExt,ConsPol,Calidad";
    awk 'BEGIN{
            r=0; OFS=","; C1Min=0; C1Max=0; C2=0; C3=0; Agentes=0; V=0; C=0;
            AvgExt=0;AvgPol=0;Col=0;FactCol=0;ConsExt=0;ConsPol=0;Calidad=0;
            print "Particulas,Valientes,Cobardes,C1Min,C1Max,C2,C3,Vmax,ZonaVirt,Obstac.,Emax,Pmax,Objetivos,RAg,RInter.,RObstac,AvgExt,AvgPol,Col,FactCol,ConsExt,ConsPol,Calidad";
        }
        /^C1Min/{ C1Min=$3}
        /^C1/{ C1Max=$3}
        /^C2/{ C2=$3}
        /^C3/{ C3=$3}
        /^Cantidad\ de\ Agentes/{ Agentes=$5; }
        /^Cantidad\ de\ Valientes/{ V=$4; }
        /^Cantidad\ de\ Cobardes/{ C=$5; }
        /^Iteracion/{ r=1; next; }
        /^==/ || /NaN/ { next; }
        {
            if(r>0) {
                print Agentes,V,C,C1Min,C1Max,C2,C3,4,15,1,610,180,1,3,25,20,$2,$3,$4,$5,$6,$7,$8;            
            }            
        }        
        ' "${i}" | ./reduce_test.r        
done;
#popd;