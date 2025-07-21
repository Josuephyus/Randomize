rm "Latest Release/Randomized.zip" > /dev/null 2>&1
rm "Latest Release/Randomized.tar.gz" > /dev/null 2>&1

cd _project
zip -qr "../Latest Release/Randomized.zip" .
tar zcf "../Latest Release/Randomized.tar.gz" .
cd ..

rm -rf ./debug/_unzip/*
rm -rf ./debug/_untar/*
unzip -q "Latest Release/Randomized.zip" -d debug/_unzip
tar -xzf "Latest Release/Randomized.tar.gz" -C debug/_untar