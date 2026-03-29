package University;

// No package declaration - allows more portability

// Package declaration for University project

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Handles database connection and basic initialization operations
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "university";
    private static final String USER = "root";
    private static final String PASSWORD = "test";
    
    private Connection connection = null;
    
    /**
     * Establishes a connection to the database
     * @return true if connection is successful, false otherwise
     * @throws SQLException if connection fails
     */
    public boolean connect() throws SQLException {
        try {
            // Load the JDBC driver explicitly - try multiple approaches
            try {
                // First approach - standard driver loading
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("MySQL JDBC Driver loaded successfully using Class.forName");
            } catch (ClassNotFoundException e) {
                try {
                    // Second approach - legacy name
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("MySQL JDBC Driver loaded successfully using legacy name");
                } catch (ClassNotFoundException e2) {
                    System.err.println("Failed to load MySQL JDBC driver: " + e.getMessage());
                    System.err.println("Make sure mysql-connector-j-9.2.0.jar is in the classpath");
                    throw new SQLException("Cannot find the database driver", e);
                }
            }
            
            // Try to connect to the database if it exists
            System.out.println("Attempting to connect to: " + URL + DATABASE_NAME);
            connection = DriverManager.getConnection(
                URL + DATABASE_NAME,
                USER,
                PASSWORD
            );
            return true;
        } catch (SQLException e) {
            // If database doesn't exist, try to create it
            if (e.getMessage().contains("Unknown database")) {
                System.out.println("Database does not exist. Creating database...");
                Connection tempConn = DriverManager.getConnection(URL, USER, PASSWORD);
                tempConn.createStatement().executeUpdate("CREATE DATABASE " + DATABASE_NAME);
                tempConn.close();
                
                // Connect to the newly created database
                connection = DriverManager.getConnection(
                    URL + DATABASE_NAME,
                    USER,
                    PASSWORD
                );
                return true;
            } else {
                throw e; // Re-throw other SQL exceptions
            }
        }
    }
    
    /**
     * Closes the database connection
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Gets the current database connection
     * @return the connection object
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Create the database schema using SQL file
     * @return true if schema creation is successful, false otherwise
     */
    public boolean createSchema() {
        try {
            File schemaFile = findFile("schema.sql");
            if (schemaFile != null) {
                executeScriptFile(schemaFile.getAbsolutePath());
                return true;
            } else {
                System.out.println("Schema file not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error creating schema: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Populate the database with sample data
     * @return true if data population is successful, false otherwise
     */
    public boolean populateData() {
        try {
            File dataFile = findFile("sample_data.sql");
            if (dataFile != null) {
                executeScriptFile(dataFile.getAbsolutePath());
                return true;
            } else {
                System.out.println("Sample data file not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error populating data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Execute SQL statements from a file
     * @param filename the file containing SQL statements
     * @throws SQLException if execution fails
     */
    private void executeScriptFile(String filename) throws SQLException {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            StringBuilder statement = new StringBuilder();
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                // Skip comments and empty lines
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                
                statement.append(line);
                
                // Execute the statement when it ends with a semicolon
                if (line.endsWith(";")) {
                    connection.createStatement().executeUpdate(statement.toString());
                    statement.setLength(0); // Clear the StringBuilder
                } else {
                    statement.append(" ");
                }
            }
            
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            throw new SQLException("Could not find SQL file: " + e.getMessage());
        }
    }
    
    /**
     * Find a file in the current directory or parent directories
     * @param filename the name of the file to find
     * @return the File object if found, null otherwise
     */
    /**
     * Main method to test database connection
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Database Connection Test");
        System.out.println("========================");
        System.out.println("Working directory: " + new File(".").getAbsolutePath());
        
        // Print classpath to help with debugging
        System.out.println("\nClasspath:\n----------");
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
        for (String path : classpathEntries) {
            System.out.println(path);
        }
        
        // Check if MySQL connector exists
        File libDir = new File("lib");
        if (libDir.exists() && libDir.isDirectory()) {
            System.out.println("\nFound lib directory: " + libDir.getAbsolutePath());
            File[] files = libDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println("File in lib: " + file.getName());
                }
            }
        } else {
            System.out.println("lib directory not found in: " + new File(".").getAbsolutePath());
        }
        
        // Try connecting
        try {
            DatabaseConnection db = new DatabaseConnection();
            if (db.connect()) {
                System.out.println("\nDatabase connection SUCCESSFUL!");
                db.disconnect();
            } else {
                System.out.println("\nDatabase connection FAILED!");
            }
        } catch (Exception e) {
            System.out.println("\nException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private File findFile(String filename) {
        try {
            System.out.println("Looking for file: " + filename);
            System.out.println("Current working directory: " + new File(".").getAbsolutePath());
            
            // Check in current directory
            File file = new File(filename);
            if (file.exists()) {
                System.out.println("Found at: " + file.getAbsolutePath());
                return file;
            }
            
            // Check in University directory specifically (common case)
            file = new File("University" + File.separator + filename);
            if (file.exists()) {
                System.out.println("Found at: " + file.getAbsolutePath());
                return file;
            }
            
            // Check in parent directory
            file = new File(".." + File.separator + filename);
            if (file.exists()) {
                System.out.println("Found at: " + file.getAbsolutePath());
                return file;
            }
            
            // Try with absolute path if we know we're in the University folder
            String currentDir = new File(".").getAbsolutePath();
            if (currentDir.contains("University")) {
                int uniIndex = currentDir.lastIndexOf("University");
                String basePath = currentDir.substring(0, uniIndex + 10); // +10 to include "University"
                file = new File(basePath + File.separator + filename);
                if (file.exists()) {
                    System.out.println("Found at: " + file.getAbsolutePath());
                    return file;
                }
            }
            
            System.out.println("File not found after checking multiple locations: " + filename);
            return null;
        } catch (Exception e) {
            System.err.println("Error while searching for file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
