@echo off
:: set variable MY_DIR equal to the current directory with a / at the end
set MY_DIR=%~dp0
:: create the directory contents
mkdir contents
:: remove any files in contents and dont ask the user to confirm "/Q"
del /Q contents\*
:: copy dependencies into contents folder
copy %MY_DIR%..\build\libs\RosNetworkTables-1.0-SNAPSHOT.jar %MY_DIR%contents\RosNetworkTables.jar
copy %MY_DIR%..\src\main\resources\config.carrot %MY_DIR%contents\config.carrot
:: unzip wpiutil package
powershell expand-archive %MY_DIR%..\src\main\lib\wpiutil-cpp-2023.2.1-linuxarm64.zip -DestinationPath %MY_DIR%contents\wpilib
:: build docker image
docker build --platform linux/arm64 -t nt2ros .
:: save docker image to a .tar file instead of uploading the image. We do this because some devices that will need the image will not always have an internet connection
docker save nt2ros -o nt2ros.tar
