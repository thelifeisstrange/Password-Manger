# Password Manager Application

A secure password and notes manager application built with Java Swing and MySQL.

## Quick Start (First-time Setup)

1. **Install MySQL** from [MySQL Community Downloads](https://dev.mysql.com/downloads/) if not already installed
2. **Download MySQL Connector**:
   - Linux/macOS: `chmod +x download-mysql-connector.sh && ./download-mysql-connector.sh`
   - Windows: `download-mysql-connector.bat`
3. **Run the Application**:
   - Linux/macOS: `chmod +x run.sh && ./run.sh`
   - Windows: `run.bat`
   - *You will be prompted for your MySQL root password if required*

## Features

- Securely store and manage passwords and notes
- AES-256 encryption for all sensitive data
- User account management
- Password generator
- Responsive UI design

## Prerequisites

- Java 11 or higher
- MySQL 5.7 or higher

## Setup and Installation

### 1. MySQL Setup

Ensure MySQL is installed and running on your system. If not, you can download and install it from:
- [MySQL Community Downloads](https://dev.mysql.com/downloads/)

### 2. Database Configuration

By default, the application uses the following MySQL connection parameters:
- Host: localhost
- Port: 3306
- Database: passwordmanager
- Username: root
- Password: (empty)

Our setup scripts will now prompt you for your MySQL root password and automatically configure the application to use it. If your MySQL server uses a different password, simply enter it when prompted.

If you need to manually configure the database connection, modify the parameters in:
`src/com/passwordmanager/util/DatabaseUtil.java`

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/passwordmanager";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = ""; // Change this if needed
```

### 3. Adding MySQL JDBC Driver

Download the MySQL Connector/J JAR file from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/)
and place it in the `lib` directory of your project.

Alternatively, you can use our provided scripts to download it automatically:

#### For Linux/macOS:
```
chmod +x download-mysql-connector.sh
./download-mysql-connector.sh
```

#### For Windows:
```
download-mysql-connector.bat
```

### 4. Running the Application

We've provided simple scripts to set up the database, compile, and run the application:

#### For Linux/macOS:
```
chmod +x run.sh
./run.sh
```

#### For Windows:
```
run.bat
```

These scripts will:
- Check if MySQL is installed and running
- Create the database if it doesn't exist
- Verify the MySQL connector is in the lib directory
- Compile and run the application

#### Manual Compilation (if needed):

If you prefer to compile and run the application manually:

##### Linux/macOS:
```
mkdir -p out
javac -cp "lib/*:." -d out src/com/passwordmanager/*.java src/com/passwordmanager/*/*.java
java -cp "out:lib/*" com.passwordmanager.PasswordManagerApp
```

##### Windows:
```
mkdir out
javac -cp "lib\*;." -d out src\com\passwordmanager\*.java src\com\passwordmanager\*\*.java
java -cp "out;lib\*" com.passwordmanager.PasswordManagerApp
```

## Usage

1. After launching the application, you'll see the welcome screen.
2. Create a new account or log in with existing credentials.
3. Use the dashboard to manage your passwords and notes.
4. All data is automatically encrypted and stored securely in the MySQL database.

## Security Notes

- The application uses AES-256 encryption for sensitive data
- For demo purposes, we're using a fixed encryption key in the code
- In a production environment, you should implement a secure key management solution

## Troubleshooting

### MySQL Connector Issues

If you see the error `error reading lib/mysql-connector-j-8.0.33.jar; zip END header not found`, it means the MySQL connector JAR file is corrupted or didn't download correctly. To fix this:

1. Run the connector download script again:
   - Linux/macOS: `chmod +x download-mysql-connector.sh && ./download-mysql-connector.sh`
   - Windows: `download-mysql-connector.bat`

2. Alternatively, manually download the MySQL Connector/J from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/) and place it in the `lib` directory.

3. Verify the JAR file is valid with:
   ```
   jar tf lib/mysql-connector-j-8.0.33.jar
   ```
   This should list the contents of the JAR file. If it shows an error, the file is corrupt and needs to be downloaded again.

### Database Connection Issues

If you encounter database connection problems:

1. Verify MySQL is running:
   ```
   mysqladmin ping -h localhost
   ```

2. Ensure the database exists:
   ```
   mysql -u root -p -e "SHOW DATABASES LIKE 'passwordmanager';"
   ```

3. Check MySQL user permissions:
   ```
   mysql -u root -p -e "GRANT ALL PRIVILEGES ON passwordmanager.* TO 'root'@'localhost';"
   mysql -u root -p -e "FLUSH PRIVILEGES;"
   ```

4. Make sure the MySQL JDBC driver is in your classpath and correctly referenced

## License

This project is licensed under the MIT License. 