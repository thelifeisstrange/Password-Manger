@echo off
echo Setting up SecureVault Password Manager...

REM Check if MySQL is installed
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo MySQL is not installed. Please install MySQL first.
    echo You can download MySQL from: https://dev.mysql.com/downloads/installer/
    exit /b 1
)

echo MySQL is installed.

REM Check if MySQL service is running
mysqladmin ping -h localhost --silent >nul 2>&1
if %errorlevel% neq 0 (
    echo MySQL service is not running. Please start it first.
    echo You can start it from Services or using the command:
    echo   net start mysql
    exit /b 1
)

echo MySQL is running.

REM Ask for MySQL root password
echo MySQL root password is required to create database.
set /p MYSQL_PASSWORD=Enter MySQL root password (leave empty if none): 

REM Create database if it doesn't exist
echo Creating passwordmanager database...

if "%MYSQL_PASSWORD%"=="" (
    REM No password provided
    mysql -u root --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;" >nul 2>&1
    if %errorlevel% equ 0 (
        echo Database created successfully.
        set "MYSQL_PWD_OPTION="
    ) else (
        echo Failed to connect without password. Please enter password when prompted...
        mysql -u root -p --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;"
        if %errorlevel% equ 0 (
            echo Database created successfully.
            set "MYSQL_PWD_OPTION=-p"
        ) else (
            echo Failed to create database. Please check your MySQL credentials.
            exit /b 1
        )
    )
) else (
    REM Password provided
    set MYSQL_PWD=%MYSQL_PASSWORD%
    mysql -u root --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;" >nul 2>&1
    if %errorlevel% equ 0 (
        echo Database created successfully.
        set "MYSQL_PWD_OPTION=-p%MYSQL_PASSWORD%"
    ) else (
        echo Failed to create database with provided password.
        exit /b 1
    )
)

REM Also update DatabaseUtil.java with correct password
echo Updating database configuration...
if not "%MYSQL_PASSWORD%"=="" (
    powershell -Command "(Get-Content src\com\passwordmanager\util\DatabaseUtil.java) -replace 'private static final String DB_PASSWORD = \"\";', 'private static final String DB_PASSWORD = \"%MYSQL_PASSWORD%\";' | Set-Content src\com\passwordmanager\util\DatabaseUtil.java"
    if %errorlevel% equ 0 (
        echo Database configuration updated successfully.
    ) else (
        echo Failed to update database configuration.
        echo Please manually update the password in src\com\passwordmanager\util\DatabaseUtil.java
    )
)

REM Check if lib directory exists and MySQL connector is valid
if not exist lib\ (
    mkdir lib
    echo Created lib directory for MySQL JDBC driver.
)

REM Find MySQL connector
for /f "tokens=*" %%a in ('dir /b lib\mysql-connector*.jar 2^>nul') do (
    set CONNECTOR_JAR=lib\%%a
)

if not defined CONNECTOR_JAR (
    echo MySQL Connector JAR not found in lib directory.
    echo Please download MySQL Connector/J using the provided script:
    echo download-mysql-connector.bat
    exit /b 1
)

REM Verify the JAR file is valid
echo Verifying MySQL connector integrity...
jar tf %CONNECTOR_JAR% >nul 2>&1
if %errorlevel% neq 0 (
    echo MySQL Connector JAR appears to be corrupt.
    echo Please download it again using:
    echo download-mysql-connector.bat
    exit /b 1
)

echo MySQL Connector JAR found and validated.

REM Create output directory if it doesn't exist
if not exist out\ (
    mkdir out
)

REM Compile the application
echo Compiling the application...
javac -cp "lib\*;." -d out src\com\passwordmanager\*.java src\com\passwordmanager\*\*.java
if %errorlevel% neq 0 (
    echo Failed to compile the application.
    exit /b 1
)

echo Application compiled successfully.

REM Run the application
echo Starting the application...
java -cp "out;lib\*" com.passwordmanager.PasswordManagerApp

exit /b 0 