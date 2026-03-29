package University;

// No package declaration - allows more portability

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

/**
 * Manages basic record operations for university database entities
 */
public class RecordManager {
    private Connection connection;
    
    /**
     * Constructor
     * @param connection the database connection to use
     */
    public RecordManager(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Display menu for inserting records and handle user choices
     * @param scanner Scanner object for user input
     */
    public void insertRecordsMenu(Scanner scanner) {
        System.out.println("\n=== Insert Records Menu ===");
        System.out.println("1. Insert Department");
        System.out.println("2. Insert Course");
        System.out.println("3. Insert Section");
        System.out.println("4. Back to Previous Menu");
        System.out.print("Enter your choice (1-4): ");
        
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                insertDepartment(scanner);
                break;
            case "2":
                insertCourse(scanner);
                break;
            case "3":
                insertSection(scanner);
                break;
            case "4":
                System.out.println("Returning to previous menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    
    /**
     * Insert a new department into the database
     * @param scanner Scanner object for user input
     */
    public void insertDepartment(Scanner scanner) {
        System.out.println("\n=== Insert New Department ===");
        
        System.out.print("Enter department name: ");
        String deptName = scanner.nextLine().trim();
        System.out.print("Enter building: ");
        String building = scanner.nextLine().trim();
        System.out.print("Enter budget: ");
        String budget = scanner.nextLine().trim();
        
        String sql = "INSERT INTO department VALUES (?, ?, ?)";  
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deptName);
            stmt.setString(2, building);
            stmt.setDouble(3, Double.parseDouble(budget));
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Department record inserted successfully!");
            } else {
                System.out.println("Failed to insert department record.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting department: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Insert a new course into the database
     * @param scanner Scanner object for user input
     */
    public void insertCourse(Scanner scanner) {
        System.out.println("\n=== Insert New Course ===");
        
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine().trim();
        System.out.print("Enter course title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter credits: ");
        String credits = scanner.nextLine().trim();
        
        // Show available departments
        System.out.println("\nAvailable departments:");
        String deptQuery = "SELECT dept_name FROM department ORDER BY dept_name";
        try (Statement deptStmt = connection.createStatement();
             ResultSet deptRs = deptStmt.executeQuery(deptQuery)) {
            
            while (deptRs.next()) {
                System.out.println(deptRs.getString("dept_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        System.out.print("Enter department name from the list above: ");
        String deptName = scanner.nextLine().trim();
        
        String sql = "INSERT INTO course VALUES (?, ?, ?, ?)";  
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, title);
            stmt.setString(3, deptName);
            stmt.setInt(4, Integer.parseInt(credits));
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course record inserted successfully!");
            } else {
                System.out.println("Failed to insert course record.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting course: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Insert a new section into the database
     * @param scanner Scanner object for user input
     */
    public void insertSection(Scanner scanner) {
        System.out.println("\n=== Insert New Section ===");
        
        // Show available courses
        System.out.println("\nAvailable courses:");
        String courseQuery = "SELECT course_id, title FROM course ORDER BY course_id";
        try (Statement courseStmt = connection.createStatement();
             ResultSet courseRs = courseStmt.executeQuery(courseQuery)) {
            
            System.out.printf("%-10s %-30s\n", "Course ID", "Title");
            System.out.println("----------------------------------------");
            while (courseRs.next()) {
                System.out.printf("%-10s %-30s\n", 
                        courseRs.getString("course_id"),
                        courseRs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        System.out.print("\nEnter course ID from the list above: ");
        String courseId = scanner.nextLine().trim();
        System.out.print("Enter section ID: ");
        String secId = scanner.nextLine().trim();
        System.out.print("Enter semester (Fall/Spring/Summer): ");
        String semester = scanner.nextLine().trim();
        System.out.print("Enter year: ");
        String year = scanner.nextLine().trim();
        
        String sql = "INSERT INTO section (course_id, sec_id, semester, year) VALUES (?, ?, ?, ?)";  
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, secId);
            stmt.setString(3, semester);
            stmt.setInt(4, Integer.parseInt(year));
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Section record inserted successfully!");
            } else {
                System.out.println("Failed to insert section record.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting section: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
