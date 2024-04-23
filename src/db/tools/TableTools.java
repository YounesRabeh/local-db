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
}
