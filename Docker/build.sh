#/bin/sh

mkdir -p contents
rm contents/*
cp ../build/libs/RosNetworkTables-1.0-SNAPSHOT.jar contents/RosNetworkTables.jar
cp ../src/main/resources/config.carrot contents/config.carrot

docker build -t nt2ros .

docker save nt2ros -o nt2ros.tar
