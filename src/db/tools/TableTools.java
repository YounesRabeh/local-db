package db.tools;

import systemx.utils.CsvTools;
import java.io.File;

import systemx.exceptions.DoNotExistsException;

public final class TableTools {
    private TableTools() {}

    /**
     * Changes the fist row of the table (the column names).
     * First it deletes any present data the first row and then inserts a new one.
     * @param file The file of the table
     * @param columnNames The column names of the table
     * @throws DoNotExistsException If the file does not exist
     */
    public static void columnNamesSetup(File file, String[] columnNames) throws DoNotExistsException {
        CsvTools.deleteRow(file, 0);
        CsvTools.insertRow(file, 0, columnNames);
    }

    /**
     * Gets the row of the table
     * @param file The file of the table
     * @param index The index of the row
     * @return The row of the table
     */
    public static String[] getRow(File file, int index) throws DoNotExistsException {
        return CsvTools.getRow(file, index);
    }

    /**
     * Adds a row to the table
     * @param tableFile The file of the table
     * @param values The values of the row
     * @throws DoNotExistsException If the file does not exist
     */
    public static void addRow(File tableFile, String[] values) throws DoNotExistsException {
        CsvTools.appendRow(tableFile, values);
    }

    /**
     * Deletes a row from the table
     * @param tableFile The file of the table
     * @param index The index of the row
     * @throws DoNotExistsException If the file does not exist
     */
    public static void deleteRow(File tableFile, int index) throws DoNotExistsException {
        CsvTools.deleteRow(tableFile, index);
    }

}
