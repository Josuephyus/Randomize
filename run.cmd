@echo off

cd _project
IF EXIST "Main_8.jar" (
    echo [Jyph] Running...
    echo --------------------
    java.exe -jar Main_8.jar
)
