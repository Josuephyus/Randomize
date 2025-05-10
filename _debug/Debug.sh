cd _src
javac RandomChampion.java -d ../_build
cd ../_build
jar cfve RandomChampion.jar RandomChampion *.class
cd ..
mv _build/RandomChampion.jar RANDOM_CHAMPION/RandomChampion.jar

zip -q -r RandomChampion.zip RANDOM_CHAMPION/*
tar qzcvf RandomChampion.tar.gz RANDOM_CHAMPION/*

unzip -q RandomChampion.zip -d _debug/_unzip

cd RANDOM_CHAMPION
java -jar RandomChampion.jar