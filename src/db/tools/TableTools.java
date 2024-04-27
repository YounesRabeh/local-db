package db.tools;

import systemx.exceptions.DoNotExistsException;
import systemx.utils.CsvTools;

import java.io.File;

public final class TableTools {
    private TableTools() {}

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
     * Changes the fist row of the table (the column names).
     * First it deletes any present data the first row and then inserts a new one.
     * @param file The file of the table
     * @param columnNames The column names of the table
     * @throws DoNotExistsException If the file does not exist
     */
    public static void columnsSetup(File file, String[] columnNames) throws DoNotExistsException {
        CsvTools.deleteRow(file, 0);
        CsvTools.insertRow(file, 0,columnNames);
    }
}
