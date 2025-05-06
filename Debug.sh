cd src
javac RandomChampion.java -d ../build
cd ../build
jar cfve RandomChampion.jar RandomChampion *.class
cd ..
rm RandomChampion.jar
mv build/RandomChampion.jar RandomChampion.jar
java -jar RandomChampion.jar