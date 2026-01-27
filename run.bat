@echo off
set JAVAFX_PATH=D:\javafx-sdk-21.0.10\lib

javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics Backgammon.java

if %errorlevel% equ 0 (
    @java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics Backgammon
) else (
    echo.
    echo Compilation failed!
    pause
)