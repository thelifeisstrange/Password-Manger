@echo off
echo Downloading MySQL Connector/J...

REM Create lib directory if it doesn't exist
if not exist lib\ (
    mkdir lib
    echo Created lib directory.
)

REM Remove any existing (possibly corrupt) connector files
del /F /Q lib\mysql-connector*.jar 2>nul

REM Download the MySQL Connector/J JAR file
set MYSQL_CONNECTOR_VERSION=8.0.33
set DOWNLOAD_URL=https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/%MYSQL_CONNECTOR_VERSION%/mysql-connector-j-%MYSQL_CONNECTOR_VERSION%.jar
set CONNECTOR_JAR=lib\mysql-connector-j-%MYSQL_CONNECTOR_VERSION%.jar

echo Downloading from %DOWNLOAD_URL%...

REM Try to download using PowerShell
echo Using PowerShell to download...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%CONNECTOR_JAR%'}" 2>nul

REM Check if download was successful
if exist %CONNECTOR_JAR% (
    echo Successfully downloaded MySQL Connector JAR to %CONNECTOR_JAR%
    exit /b 0
) else (
    echo Failed to download MySQL Connector JAR using PowerShell.
    echo Please manually download the MySQL Connector JAR from:
    echo %DOWNLOAD_URL%
    echo and save it to:
    echo %CONNECTOR_JAR%
    
    echo.
    echo Press any key to open the MySQL Connector download page in your browser...
    pause >nul
    start https://dev.mysql.com/downloads/connector/j/
    exit /b 1
) 