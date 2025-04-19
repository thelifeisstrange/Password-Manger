#!/bin/bash

# Color codes for better output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Setting up SecureVault Password Manager...${NC}"

# Check if MySQL is installed
if ! command -v mysql &> /dev/null; then
    echo -e "${RED}MySQL is not installed. Please install MySQL first.${NC}"
    echo "You can install it with the following commands:"
    echo "  For Ubuntu/Debian: sudo apt-get install mysql-server"
    echo "  For CentOS/RHEL: sudo yum install mysql-server"
    echo "  For MacOS: brew install mysql"
    exit 1
fi

# Check if MySQL service is running
if ! mysqladmin ping -h localhost --silent; then
    echo -e "${RED}MySQL service is not running. Please start it first.${NC}"
    echo "You can start it with the following commands:"
    echo "  For systemd (most Linux): sudo systemctl start mysql"
    echo "  For MacOS: brew services start mysql"
    exit 1
fi

echo -e "${GREEN}MySQL is installed and running.${NC}"

# Ask for MySQL root password
echo -e "${YELLOW}MySQL root password is required to create database.${NC}"
echo -n "Enter MySQL root password (leave empty if none): "
read -s MYSQL_PASSWORD
echo

# Create database if it doesn't exist
echo -e "${YELLOW}Creating passwordmanager database...${NC}"

if [ -z "$MYSQL_PASSWORD" ]; then
    # No password provided
    if mysql -u root --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;"; then
        echo -e "${GREEN}Database created successfully.${NC}"
        MYSQL_PWD_OPTION=""
    else
        echo -e "${RED}Failed to connect without password. Trying with password prompt...${NC}"
        if mysql -u root -p --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;"; then
            echo -e "${GREEN}Database created successfully.${NC}"
            MYSQL_PWD_OPTION="-p"
        else
            echo -e "${RED}Failed to create database. Please check your MySQL credentials.${NC}"
            exit 1
        fi
    fi
else
    # Password provided
    if MYSQL_PWD="$MYSQL_PASSWORD" mysql -u root --execute="CREATE DATABASE IF NOT EXISTS passwordmanager;"; then
        echo -e "${GREEN}Database created successfully.${NC}"
        MYSQL_PWD_OPTION="-p$MYSQL_PASSWORD"
    else
        echo -e "${RED}Failed to create database with provided password.${NC}"
        exit 1
    fi
fi

# Also update DatabaseUtil.java with correct password
echo -e "${YELLOW}Updating database configuration...${NC}"
if [ -n "$MYSQL_PASSWORD" ]; then
    sed -i.bak "s/private static final String DB_PASSWORD = \"\";/private static final String DB_PASSWORD = \"$MYSQL_PASSWORD\";/" src/com/passwordmanager/util/DatabaseUtil.java
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}Database configuration updated successfully.${NC}"
        rm -f src/com/passwordmanager/util/DatabaseUtil.java.bak
    else
        echo -e "${RED}Failed to update database configuration.${NC}"
        echo "Please manually update the password in src/com/passwordmanager/util/DatabaseUtil.java"
    fi
fi

# Check if MySQL connector is in the lib directory and valid
if [ ! -d "lib" ]; then
    mkdir -p lib
    echo -e "${YELLOW}Created lib directory for MySQL JDBC driver.${NC}"
fi

CONNECTOR_JAR=$(ls lib/mysql-connector*.jar 2>/dev/null | head -1)
if [ -z "$CONNECTOR_JAR" ]; then
    echo -e "${RED}MySQL Connector JAR not found in lib directory.${NC}"
    echo "Please download MySQL Connector/J using the provided script:"
    echo "./download-mysql-connector.sh"
    exit 1
fi

# Verify the JAR file is valid
echo -e "${YELLOW}Verifying MySQL connector integrity...${NC}"
if ! jar tf "$CONNECTOR_JAR" > /dev/null 2>&1; then
    echo -e "${RED}MySQL Connector JAR appears to be corrupt.${NC}"
    echo "Please download it again using:"
    echo "./download-mysql-connector.sh"
    exit 1
fi

echo -e "${GREEN}MySQL Connector JAR found and validated.${NC}"

# Create output directory if it doesn't exist
if [ ! -d "out" ]; then
    mkdir -p out
fi

# Compile the application
echo -e "${YELLOW}Compiling the application...${NC}"
if javac -cp "lib/*:." -d out src/com/passwordmanager/*.java src/com/passwordmanager/*/*.java; then
    echo -e "${GREEN}Application compiled successfully.${NC}"
else
    echo -e "${RED}Failed to compile the application.${NC}"
    exit 1
fi

# Run the application
echo -e "${YELLOW}Starting the application...${NC}"
java -cp "out:lib/*" com.passwordmanager.PasswordManagerApp

exit 0 