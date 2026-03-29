package University;

// No package declaration - allows more portability

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

/**
 * Manages all instructor-related database operations
 */
public class InstructorManager {
    private Connection connection;
    
    /**
     * Constructor
     * @param connection the database connection to use
     */
    public InstructorManager(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Display menu for instructor management and handle user choices
     * @param scanner Scanner object for user input
     */
    public void manageInstructors(Scanner scanner) {
        System.out.println("\n=== Instructor Management ===");
        System.out.println("1. Insert new instructor");
        System.out.println("2. Delete instructor");
        System.out.println("3. View all instructors");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice (1-4): ");
        
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                insertInstructor(scanner);
                break;
            case "2":
                deleteInstructor(scanner);
                break;
            case "3":
                viewAllInstructors();
                break;
            case "4":
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    
    /**
     * Insert a new instructor into the database
     * @param scanner Scanner object for user input
     */
    public void insertInstructor(Scanner scanner) {
        System.out.println("\n=== Insert New Instructor ===");
        
        // Get instructor details from user
        System.out.print("Enter instructor ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter instructor name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter salary: ");
        String salary = scanner.nextLine().trim();
        
        // Show available departments
        System.out.println("\nAvailable departments:");
        String deptQuery = "SELECT dept_name, building FROM department ORDER BY dept_name";
        try (Statement deptStmt = connection.createStatement();
             ResultSet deptRs = deptStmt.executeQuery(deptQuery)) {
            
            System.out.printf("%-15s %-20s\n", "Department", "Building");
            System.out.println("-----------------------------------");
            while (deptRs.next()) {
                System.out.printf("%-15s %-20s\n", 
                    deptRs.getString("dept_name"),
                    deptRs.getString("building"));
            }
            System.out.println("Enter 'null' for no department");
        } catch (SQLException e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        System.out.print("Enter department name (or 'null'): ");
        String deptName = scanner.nextLine().trim();
        if (deptName.equalsIgnoreCase("null")) {
            deptName = null;
        }
        
        // Execute the insert statement
        String sql = "INSERT INTO instructor VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, name);
            
            // Set salary as the 3rd parameter
            stmt.setDouble(3, Double.parseDouble(salary));
            
            // Set department name as the 4th parameter
            if (deptName == null) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, deptName);
            }
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Instructor inserted successfully!");
            } else {
                System.out.println("Failed to insert instructor.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting instructor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * View all instructors in the database
     */
    public void viewAllInstructors() {
        System.out.println("\n=== All Instructors ===");
        
        String sql = "SELECT i.ID, i.name, i.salary, i.dept_name, d.building " + 
                  "FROM instructor i LEFT JOIN department d ON i.dept_name = d.dept_name " + 
                  "ORDER BY i.ID";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-10s %-25s %-15s %-15s %-15s\n", 
                    "ID", "Name", "Salary", "Department", "Building");
            System.out.println("----------------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s %-25s $%-14.2f %-15s %-15s\n", 
                        rs.getString("ID"), 
                        rs.getString("name"), 
                        rs.getDouble("salary"), 
                        rs.getString("dept_name") != null ? rs.getString("dept_name") : "N/A", 
                        rs.getString("building") != null ? rs.getString("building") : "N/A");
            }
            
            if (!found) {
                System.out.println("No instructors found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing instructors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Delete an instructor from the database
     * @param scanner Scanner object for user input
     */
    public void deleteInstructor(Scanner scanner) {
        System.out.println("\n=== Delete Instructor ===");
        
        // First display all instructors
        viewAllInstructors();
        
        System.out.print("\nEnter the ID of the instructor to delete: ");
        String instructorId = scanner.nextLine().trim();
        
        // Confirm deletion
        System.out.print("Are you sure you want to delete this instructor? This will also delete all related records (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }
        
        String sql = "DELETE FROM instructor WHERE ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, instructorId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Instructor deleted successfully!");
            } else {
                System.out.println("No instructor found with ID: " + instructorId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting instructor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Calculate and display income tax (10%) for each instructor
     */
    public void calculateInstructorTax() {
        System.out.println("\n=== Instructor Income Tax (10%) ===");
        
        String sql = "SELECT ID, name, salary, salary * 0.1 AS tax_amount FROM instructor ORDER BY name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-10s %-25s %-15s %-15s\n", "ID", "Name", "Salary ($)", "Tax Amount ($)");
            System.out.println("------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s %-25s %-15.2f %-15.2f\n", 
                        rs.getString("ID"), 
                        rs.getString("name"), 
                        rs.getDouble("salary"), 
                        rs.getDouble("tax_amount"));
            }
            
            if (!found) {
                System.out.println("No instructors found.");
            }
            
            System.out.println("\nIncome tax calculation complete.");
            
        } catch (SQLException e) {
            System.out.println("Error calculating instructor tax: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
