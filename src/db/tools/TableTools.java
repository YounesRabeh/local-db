package db.tools;

import java.util.Arrays;
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
        final String[] actualColumnNames = CsvTools.getRow(file, 0);
        final int newColumnState = actualColumnNames.length - columnNames.length;

        if (newColumnState != 0){
            //FIXME: the refactor doesn't work, append row works , override row also works;
            refactorTable(file, columnNames, getIndicesToDelete(actualColumnNames, columnNames));
        }
    }

    private static int[] getIndicesToDelete(String[] actualColumnNames, String[] newColumnNames){
        List<Integer> indicesToDelete = new ArrayList<>();
        for (int i = 0; i < actualColumnNames.length; i++) {
            if (!Arrays.asList(newColumnNames).contains(actualColumnNames[i])){
                indicesToDelete.add(i);
            }
        }
        return indicesToDelete.stream().mapToInt(i -> i).toArray();
    }

    //FIXME: the refactor doesn't work, add the override file in CSV tools
    /**
     * Refactors the table to have a new number of columns
     * @param file The file of the table
     * @param columnNames The new number of columns
     * @throws DoNotExistsException If the file does not exist
     */
    public static void refactorTable(
            File file,
            String[] columnNames,
            int... indices
    ) throws DoNotExistsException {
        List<String[]> refactoredContent = new ArrayList<>();
        int newColumnNumber = columnNames.length;
        refactoredContent.add(columnNames);

        for (int i = 1; i < FileManager.countLines(file); i++) {
            String row = FileManager.getFileLine(file, i);
            String[] columns = row.split(",");
            String[] newRow;

            //TODO: get the indices to delete or add
            final int columnNumber = columns.length;
            final int columnState  = columnNumber - newColumnNumber;

            //CROP:
            if (columnState > 0){
                //FIXME: deleting certain indices doesn't work
                List<Integer> indicesToDelete = Arrays.stream(indices).boxed().toList();
                List<String> modifiedRow = new ArrayList<>();
                for (int j = 0; j < columnNumber; j++) {
                    if (!indicesToDelete.contains(j)) {
                        modifiedRow.add(columns[j]);
                    }
                }
                newRow = modifiedRow.toArray(new String[0]);
                //newRow = Arrays.copyOf(columns, newColumnNumber); //work if not delete a column in the middle
            }
            //EXPAND:
            else if (columnState < 0){
                String[] expandedRow = new String[newColumnNumber];
                System.arraycopy(columns, 0, expandedRow, 0, columnNumber);
                Arrays.fill(expandedRow, columnNumber, newColumnNumber, "null");
                newRow = expandedRow;
            }
            else break;
            refactoredContent.add(newRow);
        }
        CsvTools.overrideFile(file, refactoredContent);
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

    public static String[] getRow(File tableFile, int index) throws DoNotExistsException {
        return CsvTools.getRow(tableFile, index);
    }

}
