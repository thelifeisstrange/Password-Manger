# Running the Password Manager Application

Follow these step-by-step instructions to set up and run the application:

## 1. Database Setup

First, you need to set up the MySQL database:

1. Make sure MySQL is installed and running on your system
2. Open a terminal and run the SQL script:
   ```
   mysql -u root -p < database/schema.sql
   ```
3. When prompted, enter your MySQL root password

## 2. Configure Database Connection

Edit the `config.properties` file to match your MySQL configuration:

```
db.url=jdbc:mysql://localhost:3306/password_manager
db.user=root
db.password=YOUR_MYSQL_PASSWORD
```

Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password.

## 3. Fix Project Structure

Run the structure fix script to organize the project correctly:

```
./fix-structure.sh
```

This will reorganize the files into the correct package structure.

## 4. Run the Application

There are two ways to run the application:

### Option 1: Using the build script (easiest)

1. Open a terminal in the project directory
2. Run the build script:
   ```
   ./build.sh
   ```
   This will automatically download the MySQL connector (if needed), compile the code, and run the application.

### Option 2: Manual compilation and execution

1. Compile the application:
   ```
   mkdir -p out
   javac -cp "lib/*" -d out src/com/passwordmanager/*.java src/com/passwordmanager/*/*.java
   ```

2. Run the application:
   ```
   java -cp "out:lib/*" com.passwordmanager.PasswordManagerApp
   ```

## 5. First-time Usage

1. When the application starts, you'll see the welcome screen
2. Click "Sign Up" to create a new account
3. Fill in your name, email, phone, and password (at least 8 characters)
4. Click "Sign Up" to create your account
5. Log in with your newly created account
6. Use the tabs to manage your passwords and notes

## Troubleshooting

1. **Database Connection Issues:**
   - Make sure MySQL is running
   - Verify your database credentials in config.properties
   - Check if the password_manager database exists

2. **Compilation Errors:**
   - Ensure you have Java JDK installed (Java 8 or higher)
   - Make sure the MySQL connector jar is in the lib directory
   - If you see package-related errors, run the fix-structure.sh script again

3. **Runtime Errors:**
   - Check the console output for error messages
   - Verify that all tables were created correctly in the database 