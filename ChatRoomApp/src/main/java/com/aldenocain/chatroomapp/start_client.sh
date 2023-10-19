#!/bin/bash

# Number of times to run the command in parallel
num_runs=6
javac AppClient.java -Xlint:deprecation
java AppClient

# # Loop to run the command in the background
# for i in $(seq 1 $num_runs); do
#   java AppClient localhost 8000 &
# done

# # Wait for all background jobs to finish (optional)
# wait
