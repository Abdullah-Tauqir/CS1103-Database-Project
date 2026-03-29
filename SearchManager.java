package University;

// No package declaration - allows more portability

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages search operations for the university database
 */
public class SearchManager {
    private Connection connection;
    private boolean compactOutput = false; // Default to standard output
    
    // Store the last search results as a list of lists (rows and columns)
    private List<List<String>> lastSearchResults = null;
    private List<String> lastSearchColumnNames = null;
    
    /**
     * Constructor
     * @param connection the database connection to use
     */
    public SearchManager(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Set whether to use compact output formatting
     * @param compact true for compact output, false for standard output
     */
    public void setCompactOutput(boolean compact) {
        this.compactOutput = compact;
    }
    
    /**
     * Check if compact output mode is enabled
     * @return true if compact output is enabled, false otherwise
     */
    public boolean isCompactOutputEnabled() {
        return compactOutput;
    }
    
    /**
     * Export the results of a search query to a file in compact format
     * @param resultSet The results to export
     * @param filePath The path where to save the output file
     */
    // Previous exportSearchResults method was removed as it's now handled by exportLastSearchResults
    
    /**
     * Export the last search results to a file
     * @param filePath Path where to save the file
     */
    public void exportLastSearchResults(String filePath) {
        if (lastSearchResults == null || lastSearchResults.isEmpty()) {
            System.out.println("No search results available to export. Please perform a search first.");
            return;
        }
        
        // Make sure we use a relative path so export works on other machines
        // Don't allow path separators to prevent directory traversal
        if (filePath.contains("/") || filePath.contains("\\")) {
            // Strip path information for security - only keep filename
            filePath = new java.io.File(filePath).getName();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Calculate column widths based on data and headers
            int[] columnWidths = new int[lastSearchColumnNames.size()];
            
            // Initialize with column header lengths
            for (int i = 0; i < lastSearchColumnNames.size(); i++) {
                columnWidths[i] = lastSearchColumnNames.get(i).length();
            }
            
            // Find maximum width needed for each column
            for (List<String> row : lastSearchResults) {
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    if (value != null) {
                        columnWidths[i] = Math.max(columnWidths[i], value.length());
                    }
                }
            }
            
            // Write header
            StringBuilder header = new StringBuilder();
            StringBuilder separator = new StringBuilder();
            
            for (int i = 0; i < lastSearchColumnNames.size(); i++) {
                header.append(String.format("%-" + columnWidths[i] + "s", lastSearchColumnNames.get(i)));
                header.append(" | ");
                
                // Create separator line
                separator.append("-".repeat(columnWidths[i]));
                separator.append("-+-");
            }
            
            writer.println(header.toString());
            writer.println(separator.toString());
            
            // Write data rows
            for (List<String> row : lastSearchResults) {
                StringBuilder rowStr = new StringBuilder();
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    if (value == null) value = "NULL";
                    rowStr.append(String.format("%-" + columnWidths[i] + "s", value));
                    rowStr.append(" | ");
                }
                writer.println(rowStr.toString());
            }
            
            System.out.println("Results exported to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting results: " + e.getMessage());
        }
    }
    
    /**
     * Display the search menu
     * @param scanner Scanner for user input
     */
    public void showSearchMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nSearch Menu:");
            System.out.println("1. Search students by semester");
            System.out.println("2. Search students by course");
            System.out.println("3. Search students by department");
            System.out.println("4. Search students by grade range");
            System.out.println("5. Search instructors by department");
            System.out.println("6. Search courses by department");
            System.out.println("7. Back to main menu");
            System.out.print("Enter your choice (1-7): ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    searchStudentsBySemester(scanner);
                    break;
                case "2":
                    searchStudentsByCourse(scanner);
                    break;
                case "3":
                    searchStudentsByDepartment(scanner);
                    break;
                case "4":
                    searchStudentsByGradeRange(scanner);
                    break;
                case "5":
                    searchInstructorsByDepartment(scanner);
                    break;
                case "6":
                    searchCoursesByDepartment(scanner);
                    break;
                case "7":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    /**
     * Search and display students by semester
     * @param scanner Scanner for user input
     */
    private void searchStudentsBySemester(Scanner scanner) {
        try {
            System.out.print("Enter semester (e.g., Fall, Spring, Summer): ");
            String semester = scanner.nextLine().trim();
            
            System.out.print("Enter year (e.g., 2024): ");
            String year = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT s.ID, s.name, s.dept_name, t.course_id, t.sec_id, t.semester, t.year, t.grade "
                + "FROM student s "
                + "JOIN takes t ON s.ID = t.student_id "
                + "WHERE t.semester = ? AND t.year = ? "
                + "ORDER BY s.ID";
                
            PreparedStatement stmt = connection.prepareStatement(query, 
                                              ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                              ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, semester);
            stmt.setString(2, year);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Student Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    /**
     * Search and display students by course
     * @param scanner Scanner for user input
     */
    private void searchStudentsByCourse(Scanner scanner) {
        try {
            System.out.print("Enter course ID (e.g., CS-101): ");
            String courseId = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT s.ID, s.name, s.dept_name, t.course_id, t.sec_id, t.semester, t.year, t.grade "
                + "FROM student s "
                + "JOIN takes t ON s.ID = t.student_id "
                + "WHERE t.course_id = ? "
                + "ORDER BY s.ID, t.year, t.semester";
                
            PreparedStatement stmt = connection.prepareStatement(query, 
                                              ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                              ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, courseId);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Student Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    /**
     * Search and display students by department
     * @param scanner Scanner for user input
     */
    private void searchStudentsByDepartment(Scanner scanner) {
        try {
            System.out.print("Enter department name (e.g., Computer Science): ");
            String deptName = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT s.ID, s.name, s.dept_name, t.course_id, t.sec_id, t.semester, t.year, t.grade "
                + "FROM student s "
                + "LEFT JOIN takes t ON s.ID = t.student_id "
                + "WHERE s.dept_name = ? "
                + "ORDER BY s.ID, t.year, t.semester";
                
            PreparedStatement stmt = connection.prepareStatement(query, 
                                              ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                              ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, deptName);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Student Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    /**
     * Search and display students by grade range
     * @param scanner Scanner for user input
     */
    private void searchStudentsByGradeRange(Scanner scanner) {
        try {
            System.out.print("Enter minimum grade (e.g., B): ");
            String minGrade = scanner.nextLine().trim();
            
            System.out.print("Enter maximum grade (e.g., A+): ");
            String maxGrade = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT s.ID, s.name, s.dept_name, t.course_id, t.sec_id, t.semester, t.year, t.grade "
                + "FROM student s "
                + "JOIN takes t ON s.ID = t.student_id "
                + "WHERE t.grade >= ? AND t.grade <= ? "
                + "ORDER BY s.ID, t.grade";
                
            PreparedStatement stmt = connection.prepareStatement(query, 
                                              ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                              ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, minGrade);
            stmt.setString(2, maxGrade);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Student Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    /**
     * Search and display instructors by department
     * @param scanner Scanner for user input
     */
    private void searchInstructorsByDepartment(Scanner scanner) {
        try {
            System.out.print("Enter department name (e.g., Computer Science): ");
            String deptName = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT i.ID, i.name, i.dept_name, i.salary "
                + "FROM instructor i "
                + "WHERE i.dept_name = ? "
                + "ORDER BY i.name";
                
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, deptName);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Instructor Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching instructors: " + e.getMessage());
        }
    }
    
    /**
     * Search and display courses by department
     * @param scanner Scanner for user input
     */
    private void searchCoursesByDepartment(Scanner scanner) {
        try {
            System.out.print("Enter department name (e.g., Computer Science): ");
            String deptName = scanner.nextLine().trim();
            
            String query = ""
                + "SELECT course_id, title, dept_name, credits "
                + "FROM course "
                + "WHERE dept_name = ? "
                + "ORDER BY course_id";
                
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, deptName);
            
            ResultSet rs = stmt.executeQuery();
            // Store the search results as data
            storeSearchResults(rs);
            displaySearchResults(rs, "Course Search");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching courses: " + e.getMessage());
        }
    }
    
    /**
     * Helper method to store search results in memory for later export
     * @param resultSet The ResultSet to store
     * @throws SQLException if a database error occurs
     */
    private void storeSearchResults(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            lastSearchResults = null;
            lastSearchColumnNames = null;
            return;
        }
        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        // Store column names
        lastSearchColumnNames = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            lastSearchColumnNames.add(metaData.getColumnLabel(i));
        }
        
        // Store data rows
        lastSearchResults = new ArrayList<>();
        while (resultSet.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getString(i));
            }
            lastSearchResults.add(row);
        }
        
        // Reset result set cursor position for display
        resultSet.beforeFirst();
    }

    /**
     * Helper method to display search results
     * @param resultSet The results to display
     * @param searchType The type of search (e.g., Student Search, Instructor Search)
     * @throws SQLException if a database error occurs
     */
    private void displaySearchResults(ResultSet resultSet, String searchType) throws SQLException {
        if (resultSet == null) {
            System.out.println("No results found for the search.");
            return;
        }

        if (compactOutput) {
            // Use the compact formatter for screenshots - but now with our stored data
            try {
                // First make sure we have data stored
                if (lastSearchResults == null || lastSearchResults.isEmpty()) {
                    System.out.println("No results to display in compact format.");
                    return;
                }
                
                System.out.println("\n" + searchType + " Results:\n");
                
                // Calculate column widths
                int[] columnWidths = new int[lastSearchColumnNames.size()];
                
                // Start with column name lengths
                for (int i = 0; i < lastSearchColumnNames.size(); i++) {
                    columnWidths[i] = Math.max(lastSearchColumnNames.get(i).length(), 5);
                }
                
                // Consider data widths
                for (List<String> row : lastSearchResults) {
                    for (int i = 0; i < row.size(); i++) {
                        String value = row.get(i);
                        if (value != null) {
                            columnWidths[i] = Math.max(columnWidths[i], value.length());
                        }
                    }
                }
                
                // Cap column widths for compact display
                for (int i = 0; i < columnWidths.length; i++) {
                    columnWidths[i] = Math.min(columnWidths[i], 15);
                }
                
                // Print header
                StringBuilder header = new StringBuilder();
                StringBuilder separator = new StringBuilder();
                
                for (int i = 0; i < lastSearchColumnNames.size(); i++) {
                    header.append(String.format("%-" + columnWidths[i] + "s", lastSearchColumnNames.get(i)));
                    header.append("|");
                    
                    separator.append("-".repeat(columnWidths[i]));
                    separator.append("+");
                }
                
                System.out.println(header.toString());
                System.out.println(separator.toString());
                
                // Print data rows
                for (List<String> row : lastSearchResults) {
                    StringBuilder rowStr = new StringBuilder();
                    for (int i = 0; i < row.size(); i++) {
                        String value = row.get(i);
                        if (value == null) value = "NULL";
                        if (value.length() > columnWidths[i]) {
                            value = value.substring(0, columnWidths[i] - 3) + "...";
                        }
                        rowStr.append(String.format("%-" + columnWidths[i] + "s", value));
                        rowStr.append("|");
                    }
                    System.out.println(rowStr.toString());
                }
            } catch (Exception e) {
                System.out.println("Error formatting results: " + e.getMessage());
            }
            return;
        }
        
        // Standard output formatting using the stored data
        if (lastSearchResults == null || lastSearchColumnNames == null) {
            System.out.println("No results available to display.");
            return;
        }
        
        int columnCount = lastSearchColumnNames.size();
        
        // Print header
        System.out.println("\n" + searchType + " Results:\n");
        for (int i = 0; i < columnCount; i++) {
            System.out.printf("%-15s", lastSearchColumnNames.get(i));
        }
        System.out.println();
        
        // Print separator line
        for (int i = 0; i < columnCount; i++) {
            System.out.print("---------------");
        }
        System.out.println();
        
        // Print data
        if (lastSearchResults.isEmpty()) {
            System.out.println("No results found for the search.");
        } else {
            for (List<String> row : lastSearchResults) {
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    if (value == null) value = "NULL";
                    System.out.printf("%-15s", value);
                }
                System.out.println();
            }
        }
    }
}
