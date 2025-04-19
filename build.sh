#!/bin/bash

# Create necessary directories
mkdir -p out

# Check if MySQL Connector is present
if [ ! -f "lib/mysql-connector-j-8.0.32.jar" ]; then
  echo "MySQL Connector not found. Downloading..."
  curl -L -o lib/mysql-connector-j-8.0.32.jar https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.32/mysql-connector-j-8.0.32.jar
fi

# Copy resources to output directory
echo "Copying resources..."
mkdir -p out/resources
if [ -d "resources" ]; then
  cp -r resources/* out/resources/
fi

# Create icons directory if it doesn't exist
mkdir -p resources/icons

# Compile the code
echo "Compiling..."
javac -cp "lib/*" -d out src/com/passwordmanager/*.java src/com/passwordmanager/*/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
  echo "Compilation successful!"
  echo "Running the application..."
  java -cp "out:lib/*" com.passwordmanager.PasswordManagerApp
else
  echo "Compilation failed."
fi 