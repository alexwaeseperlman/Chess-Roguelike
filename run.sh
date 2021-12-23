#!/bin/sh

# Usage:
# The first parameter is the file you want to compile for
# Second parameter is the class+package name.
# e.g. ./run.sh Main.java Main will run the full game
# ./run.sh tests/UserInterface.java tests.UserInterface will run the user interface test

FILE="${1:-Main.java}"
CLASS="${2:-Main}"
echo $FILE $CLASS
javac -classpath .:/run_dir/junit-4.12.jar:target/dependency/* -d . $FILE -d build && node inputForward.mjs $CLASS
