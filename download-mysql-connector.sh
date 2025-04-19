#!/bin/bash

# Color codes for better output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Downloading MySQL Connector/J...${NC}"

# Create lib directory if it doesn't exist
if [ ! -d "lib" ]; then
    mkdir -p lib
    echo "Created lib directory."
fi

# Remove any existing (possibly corrupt) connector files
rm -f lib/mysql-connector*.jar

# Download the MySQL Connector/J JAR file
MYSQL_CONNECTOR_VERSION="8.0.33"
DOWNLOAD_URL="https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/${MYSQL_CONNECTOR_VERSION}/mysql-connector-j-${MYSQL_CONNECTOR_VERSION}.jar"
CONNECTOR_JAR="lib/mysql-connector-j-${MYSQL_CONNECTOR_VERSION}.jar"

echo "Downloading from $DOWNLOAD_URL..."

# Try downloading with curl
if command -v curl &> /dev/null; then
    echo "Using curl to download..."
    curl -L -o "$CONNECTOR_JAR" "$DOWNLOAD_URL"
    if [ $? -eq 0 ] && [ -f "$CONNECTOR_JAR" ]; then
        echo -e "${GREEN}Successfully downloaded MySQL Connector JAR to $CONNECTOR_JAR${NC}"
        exit 0
    else
        echo -e "${RED}Failed to download with curl.${NC}"
    fi
fi

# Try downloading with wget
if command -v wget &> /dev/null; then
    echo "Using wget to download..."
    wget -O "$CONNECTOR_JAR" "$DOWNLOAD_URL"
    if [ $? -eq 0 ] && [ -f "$CONNECTOR_JAR" ]; then
        echo -e "${GREEN}Successfully downloaded MySQL Connector JAR to $CONNECTOR_JAR${NC}"
        exit 0
    else
        echo -e "${RED}Failed to download with wget.${NC}"
    fi
fi

# If both methods failed, show manual instructions
echo -e "${RED}Failed to download MySQL Connector automatically.${NC}"
echo "Please manually download the MySQL Connector JAR from:"
echo "https://dev.mysql.com/downloads/connector/j/"
echo "and save it to: lib/mysql-connector-j.jar"
exit 1 