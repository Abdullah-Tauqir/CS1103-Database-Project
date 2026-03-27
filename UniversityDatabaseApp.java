package University;

// No package declaration - allows more portability

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main application class for the University Database System
 */
public class UniversityDatabaseApp {
    private DatabaseConnection dbConnection;
    private StudentManager studentManager;
    private InstructorManager instructorManager;
    private ReportGenerator reportGenerator;
    private RecordManager recordManager;
    private SearchManager searchManager;
    
    /**
     * Constructor initializes database connection and managers
     */
    public UniversityDatabaseApp() {
        dbConnection = new DatabaseConnection();
    }
    
    /**
     * Initialize all the managers with the database connection
     * @param connection the active database connection
     */
    private void initializeManagers(Connection connection) {
        studentManager = new StudentManager(connection);
        instructorManager = new InstructorManager(connection);
        reportGenerator = new ReportGenerator(connection);
        recordManager = new RecordManager(connection);
        searchManager = new SearchManager(connection);
    }
    
    /**
     * Run the application
     */
    public void run() {
        System.out.println("University Database Application");
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Connect to database
            if (dbConnection.connect()) {
                System.out.println("Successfully connected to MySQL!");
                initializeManagers(dbConnection.getConnection());
                
                // Create schema if needed
                System.out.print("Do you want to create the database schema? (y/n): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                
                if (answer.equals("y")) {
                    if (dbConnection.createSchema()) {
                        System.out.println("Schema created successfully!");
                        
                        // Load sample data if needed
                        System.out.print("Do you want to load sample data? (y/n): ");
                        answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("y")) {
                            if (dbConnection.populateData()) {
                                System.out.println("Sample data loaded successfully!");
                            } else {
                                System.out.println("Failed to load sample data.");
                            }
                        }
                    } else {
                        System.out.println("Failed to create schema.");
                    }
                }
                
                // Main application loop
                boolean exit = false;
                while (!exit) {
                    displayMainMenu();
                    String choice = scanner.nextLine().trim();
                    
                    switch (choice) {
                        case "1":
                            runModerateRequirements(scanner);
                            break;
                        case "2":
                            runChallengeRequirements(scanner);
                            break;
                        case "3":
                            searchManager.showSearchMenu(scanner);
                            break;
                        case "4":
                            presentationSettingsMenu(scanner);
                            break;
                        case "5":
                            System.out.println("Exiting application...");
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
                
                // Close resources
                scanner.close();
                dbConnection.disconnect();
                System.out.println("\nApplication finished.");
                
            } else {
                System.out.println("Failed to connect to database. Exiting application.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Display the main application menu
     */
    private void displayMainMenu() {
        System.out.println("\n===== University Database System =====");
        System.out.println("1. Moderate Requirements");
        System.out.println("2. Challenge Requirements");
        System.out.println("3. Search Functions");
        System.out.println("4. Presentation Settings");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }
    
    /**
     * Run the moderate requirements menu
     * @param scanner Scanner for user input
     */
    private void runModerateRequirements(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nModerate Requirements Menu:");
            System.out.println("1. Insert records to tables");
            System.out.println("2. Calculate instructor income tax (10%)");
            System.out.println("3. Find students with highest grade per semester");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice (1-4): ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    recordManager.insertRecordsMenu(scanner);
                    break;
                case "2":
                    instructorManager.calculateInstructorTax();
                    break;
                case "3":
                    reportGenerator.findHighestGradeStudents();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    /**
     * Run the challenge requirements menu
     * @param scanner Scanner for user input
     */
    private void runChallengeRequirements(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nChallenge Requirements Menu:");
            System.out.println("1. Manage Students (Insert/Delete)");
            System.out.println("2. Manage Instructors (Insert/Delete)");
            System.out.println("3. Change Student's Department");
            System.out.println("4. View Statistics Reports");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice (1-5): ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    studentManager.manageStudents(scanner);
                    break;
                case "2":
                    instructorManager.manageInstructors(scanner);
                    break;
                case "3":
                    studentManager.changeStudentDepartment(scanner);
                    break;
                case "4":
                    runReportsMenu(scanner);
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    /**
     * Run the reports menu
     * @param scanner Scanner for user input
     */
    private void runReportsMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nStatistics Reports Menu:");
            System.out.println("1. Department Statistics");
            System.out.println("2. Student Grade Distribution");
            System.out.println("3. Course Enrollment Report");
            System.out.println("4. Back to Challenge Menu");
            System.out.print("Enter your choice (1-4): ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    reportGenerator.viewStudentsByDepartment();
                    break;
                case "2":
                    reportGenerator.findHighestGradeStudents();
                    break;
                case "3":
                    reportGenerator.viewCourseEnrollments();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    /**
     * Display and handle the presentation settings menu
     * for configuring output format for screenshots
     * @param scanner Scanner for user input
     */
    private void presentationSettingsMenu(Scanner scanner) {
        boolean inSettingsMenu = true;
        
        do {
            System.out.println("\n===== Presentation Settings =====");
            System.out.println("1. Toggle Compact Output Mode (for Screenshots)");
            System.out.println("2. Export Recent Search Results to File");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            String input = scanner.nextLine().trim();
            int choice;
            
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    boolean currentMode = searchManager.isCompactOutputEnabled();
                    searchManager.setCompactOutput(!currentMode);
                    System.out.println("Compact output mode is now: " + 
                                     (searchManager.isCompactOutputEnabled() ? "ENABLED" : "DISABLED"));
                    System.out.println("(Perfect for taking compact screenshots for presentations)");
                    break;
                    
                case 2:
                    System.out.println("\nThis feature allows you to export search results to a file.");
                    System.out.println("First perform a search, then come back here to export the results.");
                    System.out.print("Enter filename to export to (e.g., search_results.txt): ");
                    String filename = scanner.nextLine().trim();
                    
                    if (filename.isEmpty()) {
                        filename = "search_results.txt";
                    }
                    
                    searchManager.exportLastSearchResults(filename);
                    break;
                    
                case 3:
                    inSettingsMenu = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (inSettingsMenu);
    }
    
    /**
     * Main entry point of the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        UniversityDatabaseApp app = new UniversityDatabaseApp();
        app.run();
    }
}
