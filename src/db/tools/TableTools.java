package db.tools;

import java.util.List;
import systemx.utils.CsvTools;
import java.io.File;
import java.util.ArrayList;

import systemx.exceptions.DoNotExistsException;
import systemx.utils.FileManager;

//TODO: add the override row in CSV tools

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
        if (file.length() == 0) {
            CsvTools.appendRow(file, columnNames);
            return;
        }
        final int newColumnState = CsvTools.getRow(file, 0).length - columnNames.length;
        CsvTools.overrideRow(file, 0, columnNames);

        if (newColumnState != 0){
            refactorTable(file, columnNames.length);
        }
    }

    //FIXME: the refarctor doesn't work, add the override file in CSV tools
    public static void refactorTable(File file, int newColumnNumber) throws DoNotExistsException {
        List<String[]> refactoredContent = new ArrayList<>();
        for (int i = 1; i < FileManager.countLines(file); i++) {
            String[] row = CsvTools.getRow(file, i);
            String[] newRow = new String[newColumnNumber];

            final int columnState = row.length - newColumnNumber;
            if (row.length == newColumnNumber) {
                break;
            }

            //CROP:
            if (columnState > 0){
                for (int j = 0; j < newColumnNumber; j++){
                    newRow[j] = row[j];
                }
            }
            if (columnState < 0){
                for (int j = 0; j < newColumnNumber; j++){
                    if (j < row.length) {
                        newRow[j] = row[j];
                        continue;
                    }
                    break;
                }
            }

            //override
            refactoredContent.add(newRow);
        }
        CsvTools.overrideFile(file, refactoredContent);
    }

    /**
     * Gets the row of the table
     * @param tableFile The file of the table
     * @param index The index of the row
     * @return The row of the table
     */
    public static String[] getRow(File tableFile, int index) throws DoNotExistsException {
        if (FileManager.countLines(tableFile) <= index) {
            throw new DoNotExistsException("Row " + index, tableFile.toString());
        }
        return CsvTools.getRow(tableFile, index);
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
        if (FileManager.countLines(tableFile) <= index){
            throw new DoNotExistsException("Row " + index, tableFile.toString());
        }
        CsvTools.deleteRow(tableFile, index);
    }

}
