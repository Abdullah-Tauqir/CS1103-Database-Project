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
 * Manages all student-related database operations
 */
public class StudentManager {
    private Connection connection;
    
    /**
     * Constructor
     * @param connection the database connection to use
     */
    public StudentManager(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Display menu for student management and handle user choices
     * @param scanner Scanner object for user input
     */
    public void manageStudents(Scanner scanner) {
        System.out.println("\n=== Student Management ===");
        System.out.println("1. Insert new student");
        System.out.println("2. Delete student");
        System.out.println("3. View all students");
        System.out.println("4. Change student's department");
        System.out.println("5. Back to main menu");
        System.out.print("Enter your choice (1-5): ");
        
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                insertStudent(scanner);
                break;
            case "2":
                deleteStudent(scanner);
                break;
            case "3":
                viewAllStudents();
                break;
            case "4":
                changeStudentDepartment(scanner);
                break;
            case "5":
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    
    /**
     * Insert a new student into the database
     * @param scanner Scanner object for user input
     */
    public void insertStudent(Scanner scanner) {
        System.out.println("\n=== Insert New Student ===");
        
        // Get student details from user
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter total credits: ");
        String totCred = scanner.nextLine().trim();
        
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
        String sql = "INSERT INTO student VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, name);
            
            if (deptName == null) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, deptName);
            }
            
            stmt.setInt(4, Integer.parseInt(totCred));
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student inserted successfully!");
            } else {
                System.out.println("Failed to insert student.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * View all students in the database
     */
    public void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        
        String sql = "SELECT s.ID, s.name, s.tot_cred, s.dept_name, d.building " + 
                  "FROM student s LEFT JOIN department d ON s.dept_name = d.dept_name " + 
                  "ORDER BY s.ID";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-10s %-25s %-15s %-15s %-15s\n", 
                    "ID", "Name", "Total Credits", "Department", "Building");
            System.out.println("----------------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s %-25s %-15d %-15s %-15s\n", 
                        rs.getString("ID"), 
                        rs.getString("name"), 
                        rs.getInt("tot_cred"), 
                        rs.getString("dept_name") != null ? rs.getString("dept_name") : "N/A", 
                        rs.getString("building") != null ? rs.getString("building") : "N/A");
            }
            
            if (!found) {
                System.out.println("No students found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Delete a student from the database
     * @param scanner Scanner object for user input
     */
    public void deleteStudent(Scanner scanner) {
        System.out.println("\n=== Delete Student ===");
        
        // First display all students
        viewAllStudents();
        
        System.out.print("\nEnter the ID of the student to delete: ");
        String studentId = scanner.nextLine().trim();
        
        // Confirm deletion
        System.out.print("Are you sure you want to delete this student? This will also delete all related records (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }
        
        String sql = "DELETE FROM student WHERE ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Change a student's department
     * @param scanner Scanner object for user input
     */
    public void changeStudentDepartment(Scanner scanner) {
        System.out.println("\n=== Change Student Department ===");
        
        // First display all students
        viewAllStudents();
        
        System.out.print("\nEnter the ID of the student to update: ");
        String studentId = scanner.nextLine().trim();
        
        // Check if student exists
        try (PreparedStatement checkStmt = connection.prepareStatement("SELECT * FROM student WHERE ID = ?")) {
            checkStmt.setString(1, studentId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No student found with ID: " + studentId);
                return;
            }
            
            String currentDept = rs.getString("dept_name");
            System.out.println("Current department: " + (currentDept != null ? currentDept : "None"));
        } catch (SQLException e) {
            System.out.println("Error checking student: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
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
        } catch (SQLException e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        System.out.print("\nEnter new department name from the list above (or 'null' to remove department): ");
        String newDept = scanner.nextLine().trim();
        
        if (newDept.equalsIgnoreCase("null")) {
            newDept = null;
        }
        
        // Update student's department
        String sql = "UPDATE student SET dept_name = ? WHERE ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (newDept == null) {
                stmt.setNull(1, Types.VARCHAR);
            } else {
                stmt.setString(1, newDept);
            }
            stmt.setString(2, studentId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student department updated successfully!");
                
                // Show the updated student record
                try (PreparedStatement selectStmt = connection.prepareStatement(
                        "SELECT s.ID, s.name, s.tot_cred, s.dept_name, d.building " + 
                        "FROM student s LEFT JOIN department d ON s.dept_name = d.dept_name " + 
                        "WHERE s.ID = ?")) {
                    selectStmt.setString(1, studentId);
                    ResultSet resultSet = selectStmt.executeQuery();
                    
                    if (resultSet.next()) {
                        System.out.println("\nUpdated student record:");
                        System.out.printf("%-10s %-25s %-15s %-15s %-15s\n", 
                                "ID", "Name", "Total Credits", "Department", "Building");
                        System.out.println("----------------------------------------------------------------------------");
                        System.out.printf("%-10s %-25s %-15d %-15s %-15s\n", 
                                resultSet.getString("ID"), 
                                resultSet.getString("name"), 
                                resultSet.getInt("tot_cred"), 
                                resultSet.getString("dept_name") != null ? resultSet.getString("dept_name") : "N/A", 
                                resultSet.getString("building") != null ? resultSet.getString("building") : "N/A");
                    }
                }
            } else {
                System.out.println("Failed to update student department.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student department: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
