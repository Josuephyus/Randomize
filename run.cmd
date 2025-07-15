@echo off

IF EXIST "./_project/Main_8.jar" (
    echo [Jyph] Running...
    echo --------------------
    java.exe -jar ./_project/Main_8.jar
)
