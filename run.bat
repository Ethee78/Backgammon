@echo off
set JAVAFX_PATH=C:\javafx-sdk-25.0.1\lib

javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics Backgammon.java

if %errorlevel% equ 0 (
    @java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics Backgammon
) else (
    echo.
    echo Compilation failed!
    pause
)