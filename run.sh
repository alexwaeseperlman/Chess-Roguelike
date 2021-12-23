FILE="${1:-Main.java}"
CLASS="${2:-Main}"
echo $FILE $CLASS
javac -classpath .:/run_dir/junit-4.12.jar:target/dependency/* -d . $FILE -d build && node inputForward.mjs $CLASS
