cd _src
javac RandomChampion.java -d ../_build
cd ../_build
jar cfve RandomChampion.jar RandomChampion *.class
cd ..
mv _build/RandomChampion.jar RANDOM_CHAMPION/RandomChampion.jar
cd RANDOM_CHAMPION
java -jar RandomChampion.jar