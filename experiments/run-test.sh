#! /bin/bash

tests="Mejorado Spline Bravery"

if [ $# -ne 0 ]; then
    tests=$@;
fi;

pushd ..;

for i in $tests; do
    mvn -q clean test -Dtest="${i}Test";
    pushd target; 
    7z a ../../simulacion-${i,,}-$(date +%Y%m%d).7z
popd;
