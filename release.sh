rm "Latest Release/Randomize.zip" > /dev/null 2>&1
rm "Latest Release/Randomize.tar.gz" > /dev/null 2>&1

cd _project
zip -qr "../Latest Release/Randomize.zip" .
tar zcf "../Latest Release/Randomize.tar.gz" .
cd ..

cd "Latest Release"
rm -rf ./_unzip/*
rm -rf ./_untar/*
unzip -q "./Randomize.zip" -d ./_unzip
tar -xzf "./Randomize.tar.gz" -C ./_untar