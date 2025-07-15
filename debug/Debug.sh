cd _src
javac RandomChampion.java -d ../_build
cd ../_build
jar cfe RandomChampion.jar RandomChampion *.class
cd ..
mv _build/RandomChampion.jar RANDOM_CHAMPION/RandomChampion.jar

zip -q -r RandomChampion.zip RANDOM_CHAMPION/*
tar zcf RandomChampion.tar.gz RANDOM_CHAMPION/*


rm -rf ./_debug/_unzip/*
unzip -q RandomChampion.zip -d _debug/_unzip
rm -rf ./_debug/_untar/*
tar -xzf RandomChampion.tar.gz -C _debug/_untar

cd RANDOM_CHAMPION
java -jar RandomChampion.jar