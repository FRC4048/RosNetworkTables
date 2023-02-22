#/bin/sh

mkdir -p contents
rm -r contents/*

cp ../build/libs/RosNetworkTables-1.0-SNAPSHOT.jar contents/RosNetworkTables.jar
cp ../src/main/resources/config.carrot contents/config.carrot
unzip ../src/main/lib/wpiutil-cpp-2023.2.1-linuxarm64.zip -d contents/wpilib

docker build -t nt2ros .

docker save nt2ros -o nt2ros.tar
