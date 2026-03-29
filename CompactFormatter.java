package University;

// No package declaration - allows more portability

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CompactFormatter {
    
    /**
     * Exports a ResultSet to a compact tabular file format
     * 
     * @param resultSet The SQL query results to format
     * @param filePath The path where the output file should be saved
     * @throws SQLException If there is an error processing the ResultSet
     * @throws IOException If there is an error writing to the file
     */
    public static void exportToCompactFile(ResultSet resultSet, String filePath) throws SQLException, IOException {
        if (resultSet == null) {
            System.out.println("No results to export.");
            return;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Get column names and determine their display width
            String[] columnNames = new String[columnCount];
            int[] columnWidths = new int[columnCount];
            
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i-1] = metaData.getColumnLabel(i);
                // Start with column name length or minimum 5, whichever is larger
                columnWidths[i-1] = Math.max(columnNames[i-1].length(), 5);
            }
            
            // First pass: determine column widths based on data
            resultSet.beforeFirst(); // Reset cursor to before first row
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    if (value == null) value = "NULL";
                    columnWidths[i-1] = Math.max(columnWidths[i-1], value.length());
                }
            }
            
            // Write header
            StringBuilder header = new StringBuilder();
            StringBuilder separator = new StringBuilder();
            
            for (int i = 0; i < columnCount; i++) {
                header.append(String.format("%-" + columnWidths[i] + "s", columnNames[i]));
                header.append(" | ");
                
                // Create separator line
                separator.append("-".repeat(columnWidths[i]));
                separator.append("-+-");
            }
            
            writer.println(header.toString());
            writer.println(separator.toString());
            
            // Write data rows
            resultSet.beforeFirst(); // Reset cursor again
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    if (value == null) value = "NULL";
                    row.append(String.format("%-" + columnWidths[i-1] + "s", value));
                    row.append(" | ");
                }
                writer.println(row.toString());
            }
            
            System.out.println("Results exported to: " + filePath);
        }
    }
    
    /**
     * Returns a compact formatted string from a ResultSet
     * (useful for displaying in the console)
     * 
     * @param resultSet The SQL query results to format
     * @return A string with the formatted results
     * @throws SQLException If there is an error processing the ResultSet
     */
    public static String getCompactFormat(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return "No results available.";
        }
        
        StringBuilder output = new StringBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        // Get column names and determine their display width
        String[] columnNames = new String[columnCount];
        int[] columnWidths = new int[columnCount];
        
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i-1] = metaData.getColumnLabel(i);
            // Start with column name length or minimum 5, whichever is larger
            columnWidths[i-1] = Math.max(columnNames[i-1].length(), 5);
        }
        
        // First pass: determine column widths based on data
        resultSet.beforeFirst(); // Reset cursor to before first row
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                if (value == null) value = "NULL";
                columnWidths[i-1] = Math.max(columnWidths[i-1], value.length());
            }
        }
        
        // Compact format: reduce spacing between columns
        for (int i = 0; i < columnCount; i++) {
            // Make columns a bit more compact
            columnWidths[i] = Math.min(columnWidths[i], 15); // Cap width at 15 chars
        }
        
        // Write header
        StringBuilder header = new StringBuilder();
        StringBuilder separator = new StringBuilder();
        
        for (int i = 0; i < columnCount; i++) {
            header.append(String.format("%-" + columnWidths[i] + "s", columnNames[i]));
            header.append("|");
            
            // Create separator line
            separator.append("-".repeat(columnWidths[i]));
            separator.append("+");
        }
        
        output.append(header.toString()).append("\n");
        output.append(separator.toString()).append("\n");
        
        // Write data rows
        resultSet.beforeFirst(); // Reset cursor again
        while (resultSet.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                if (value == null) value = "NULL";
                if (value.length() > columnWidths[i-1]) {
                    // Truncate and add ellipsis if too long
                    value = value.substring(0, columnWidths[i-1] - 3) + "...";
                }
                row.append(String.format("%-" + columnWidths[i-1] + "s", value));
                row.append("|");
            }
            output.append(row.toString()).append("\n");
        }
        
        return output.toString();
    }
}
