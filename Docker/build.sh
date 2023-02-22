#/bin/sh

mkdir -p contents
rm contents/*
cp ../build/libs/RosNetworkTables-1.0-SNAPSHOT.jar contents/RosNetworkTables.jar
cp ../src/main/resources/config.carrot contents/config.carrot
cp ../src/main/lib/wpiutil-cpp-2023.2.1-linuxarm64.zip contents/
unzip contents/wpiutil-cpp-2023.2.1-linuxarm64.zip

docker build --platform linux/arm64 -t nt2ros .

docker save nt2ros -o nt2ros.tar
