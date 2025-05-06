cd src
javac RandomChampion.java
jar cfve RandomChampion.jar RandomChampion *.class
cd ..
rm RandomChampion.jar
mv src/RandomChampion.jar RandomChampion.jar
java -jar RandomChampion.jar