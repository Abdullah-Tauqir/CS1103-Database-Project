package University;

// No package declaration - allows more portability

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Generates database reports for the university database
 */
public class ReportGenerator {
    private Connection connection;
    
    /**
     * Constructor
     * @param connection the database connection to use
     */
    public ReportGenerator(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Find and display students with the highest grade for each semester
     */
    public void findHighestGradeStudents() {
        System.out.println("\n=== Students with Highest Grade per Semester ===");
        
        String sql = "SELECT t.semester, t.year, s.ID, s.name, t.course_id, c.title, t.grade " + 
                    "FROM student s JOIN takes t ON s.ID = t.student_id " + 
                    "JOIN course c ON t.course_id = c.course_id " + 
                    "JOIN (" + 
                    "    SELECT semester, year, MAX(grade) as max_grade " + 
                    "    FROM takes WHERE grade IS NOT NULL " + 
                    "    GROUP BY semester, year" + 
                    ") m ON t.semester = m.semester AND t.year = m.year AND t.grade = m.max_grade " + 
                    "ORDER BY t.year DESC, t.semester";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-10s %-6s %-10s %-25s %-15s %-25s %-10s\n", 
                    "Semester", "Year", "Student ID", "Student Name", 
                    "Course ID", "Course Title", "Grade");
            System.out.println("----------------------------------------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s %-6d %-10s %-25s %-15s %-25s %-10s\n", 
                        rs.getString("semester"), 
                        rs.getInt("year"), 
                        rs.getString("ID"), 
                        rs.getString("name"), 
                        rs.getString("course_id"), 
                        rs.getString("title"), 
                        rs.getString("grade"));
            }
            
            if (!found) {
                System.out.println("No grade records found in the database.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error finding highest grade students: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * View students by department
     */
    public void viewStudentsByDepartment() {
        System.out.println("\n=== Students by Department ===");
        
        String sql = "SELECT d.dept_name, COUNT(s.ID) as student_count " + 
                    "FROM department d LEFT JOIN student s ON d.dept_name = s.dept_name " + 
                    "GROUP BY d.dept_name " +
                    "ORDER BY student_count DESC, d.dept_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-20s %-15s\n", "Department", "Student Count");
            System.out.println("------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-20s %-15d\n", 
                        rs.getString("dept_name"), 
                        rs.getInt("student_count"));
            }
            
            if (!found) {
                System.out.println("No departments found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating department report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * View course enrollment statistics
     */
    public void viewCourseEnrollments() {
        System.out.println("\n=== Course Enrollment Statistics ===");
        
        String sql = "SELECT c.course_id, c.title, COUNT(t.student_id) as enrollment_count " + 
                    "FROM course c LEFT JOIN takes t ON c.course_id = t.course_id " + 
                    "GROUP BY c.course_id, c.title " +
                    "ORDER BY enrollment_count DESC, c.course_id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print header
            System.out.printf("%-10s %-30s %-15s\n", "Course ID", "Title", "Enrollment Count");
            System.out.println("------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s %-30s %-15d\n", 
                        rs.getString("course_id"), 
                        rs.getString("title"), 
                        rs.getInt("enrollment_count"));
            }
            
            if (!found) {
                System.out.println("No courses found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating course enrollment report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
