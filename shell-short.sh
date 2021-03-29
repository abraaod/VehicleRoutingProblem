#! /bin/bash


while read p; do
  echo "$p"
  java -jar cvrp.jar $p
  echo " "
done <instances-short.txt
