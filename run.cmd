@echo off

cd _project
IF EXIST "Randomize.jar" (
    echo [Jyph] Running...
    echo --------------------
    java.exe -jar Randomize.jar
)
