package db.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.util.Objects;

import db.objects.Tuple;
import db.tools.TableTools;
import rules.notes.Constraint;
import rules.notes.Constraints;
import systemx.exceptions.InterfaceErrors;
import systemx.utils.CsvTools;
import systemx.utils.FileManager;

import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;


import static environment.WorkspaceMaker.assignTableFile;

/**
 * Represents a table in a database, with columns and rows.
 */
public class Table implements InterfaceErrors {
    /** The name of the table */
    private final String tableName;
    /** The columns of the table */
    private Constraints columnConstrains;
    /** The file of the table */
    private final File TABLE_FILE;
    /** The index of the column names */
    private final int COLUMN_NAMES_INDEX = 0;

    /**
     * Creates a table with the given name and columns
     * @param tableName The name of the table
     * @param columns The columns of the table
     */
    public Table(String tableName, Constraints columns) throws
            NotUniqueException, DoNotExistsException, FailedToCreateException {
        this.tableName = tableName;
        this.columnConstrains = columnConstrainsSetup(columns);
        TABLE_FILE = assignTableFile(this);
        //WorkspaceMaker.createTableFile(TABLE_FILE);
        String[] columnNames = getColumnNames();
        if (!TABLE_FILE.exists()) {

            TableTools.columnNamesSetup(TABLE_FILE, columnNames);
        } else{
            this.columnConstrains = createColumnsConstraints();
            System.out.println(InterfaceErrors.ERROR_MESSAGE +
                    tableName +
                    " is already present, use overrideColumns() to override the columns" +
                    InterfaceErrors.RESET
            );
        }
    }

    private Constraints createColumnsConstraints() throws NotUniqueException, DoNotExistsException {
        Constraints columns = new Constraints();
        for (String columnName : CsvTools.getRow(TABLE_FILE, COLUMN_NAMES_INDEX)) {
            columns.addConstraint(new Constraint(columnName, String.class));
        }
        return columns;
    }

    /**
     * Set up the columns of the table
     * @param columns The columns to set up
     * @return The columns setup
     */
    private Constraints columnConstrainsSetup(Constraints columns) throws NotUniqueException {
        final Constraints columnsCopy = new Constraints();
        for (Constraint column : columns.getConstraints()) {
            columnsCopy.addConstraint(new Constraint(column.getName(), column.getDataType()));
        }
        return columnsCopy;
    }

    /**
     * Adds columns to the table
     * @param columns The columns to add
     */
    public void addColumn(Constraint... columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Adds a list of columns to the table
     * @param columns The list of  columns to add
     */
    public void addColumn(List<Constraint> columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Overrides the columns of the table with the given columns
     * @param columns The columns to override
     */
    public void overrideColumns(Constraints columns) throws DoNotExistsException {
        this.columnConstrains = columns;
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Deletes columns from the table
     * @param columns The columns to delete
     */
    public void deleteColumn(Constraint... columns) throws DoNotExistsException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Deletes columns from the table
     * @param columnNames The names of the columns to delete
     */
    public void deleteColumn(String... columnNames) throws DoNotExistsException {
        for (String column : columnNames) {
            this.columnConstrains.deleteConstraint(column);
        }
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }


    /**
     * Returns the columns constraints of the table
     * @return The columns constraints of the table
     */
    public Constraints getColumnsConstraints() {
        return columnConstrains;
    }

    /**
     * Returns the columns constraints of the table
     * @return The list of columns constraints of the table
     */
    public List<Constraint> getColumnsConstraintsList() {
        return columnConstrains.getConstraints();
    }

    /**
     * Returns the names of the columns of the table
     * @return The columns names array of the table
     */
    public String[] getColumnNames() throws DoNotExistsException {
        if (columnConstrains.getConstraintsNames().isEmpty()){
            return TableTools.getRow(this.TABLE_FILE, COLUMN_NAMES_INDEX);
        }
        List<Constraint> columns = columnConstrains.getConstraints();
        final int SIZE = columns.size();
        String[] columnNames = new String[SIZE];

        for (int column = 0; column < SIZE; column++) {
            columnNames[column] = columns.get(column).getName();
        }
        return columnNames;
    }

    /**
     * Returns the Simple Name of the column types of the table as an array
     * @return The types of the columns of the table
     */
    public String[] getColumnTypes(){
        List<Constraint> columns = columnConstrains.getConstraints();
        final int SIZE = columns.size();
        String[] columnTypes = new String[SIZE];

        for (int column = 0; column < SIZE; column++) {
            columnTypes[column] = columns.get(column).getDataType().getSimpleName();
        }
        return columnTypes;
    }

    /**
     * Returns the data type of the column
     * @param columnName The name of the column
     * @return The data type of the column
     */
    public Class<?> getColumnType(String columnName) throws DoNotExistsException {
        for (Constraint column : columnConstrains.getConstraints()) {
            if (column.getName().equalsIgnoreCase(columnName)) {
                return column.getDataType();
            }
        }
       throw new DoNotExistsException(columnName, tableName);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //TODO: Row related methods

    /**
     * Adds a tuple to the table, best effort to add the tuple to the table.
     * if the tuple is smaller than the columns constraints, it should add the missing ones as null,
     * and if it is bigger, it should crop the extra ones
     * @param tuple The tuple to add
     * @throws DoNotExistsException If the tuple is null or the table does not exist
     */
    public void addTuple(Tuple tuple) throws DoNotExistsException {
        if (tuple == null) throw new DoNotExistsException("Tuple is null");
        final int constraintsSize = columnConstrains.getConstraints().size();
        final int tupleLengthState = tuple.values().length - constraintsSize;
        final String[] tupleValues = tuple.values();
        String[] values = new String[constraintsSize];
        if (tupleLengthState < 0) {
            System.arraycopy(tupleValues, 0, values, 0, tupleValues.length);
            Arrays.fill(values, tupleValues.length, constraintsSize, "null");
        } else if (tupleLengthState > 0) {
            System.arraycopy(tupleValues, 0, values, 0, constraintsSize);
            System.out.println(InterfaceErrors.WARNING_MESSAGE +
                    "The tuple is bigger than the columns constraints, the extra values will be cropped" +
                    InterfaceErrors.RESET
            );
        } else {
            values = tupleValues;
        }
        //FIXME: check if the values are cast-able to the column data types
        TableTools.addRow(TABLE_FILE, values);
    }

    /**
     * Adds a collection of tuples to the table
     * @param tuples The collection of tuples to add
     * @throws DoNotExistsException If one of the tuples is null or the table does not exist
     */
    public void addTuples(List<Tuple> tuples) throws DoNotExistsException {
        for (Tuple tuple : tuples) {
            addTuple(tuple);
        }
    }

    /**
     * Deletes the tuple at the given index
     * @param index The index of the tuple to delete
     * @throws DoNotExistsException If the tuple does not exist
     */
    public void deleteTuple(int index) throws DoNotExistsException {
        TableTools.deleteRow(TABLE_FILE, index);
    }

    /**
     * Deletes a list of tuples from the table
     * @param indices The indices of the tuples to delete
     * @throws DoNotExistsException If one of the tuples does not exist
     */
    public void deleteTuples(List<Integer> indices) throws DoNotExistsException {
        for (int index : indices) {
            deleteTuple(index);
        }
    }

    //TODO: add the get-delete methods utilizing the primary key of the table





    /**
     * Get the tuple at the given index. <b>(Index 0) is the column names</b> {@link #COLUMN_NAMES_INDEX}.
     * @param index The row index of the tuple
     * @return The tuple at the given row index
     * @throws DoNotExistsException If the tuple does not exist
     *
     */
    public Tuple getTuple(int index) throws DoNotExistsException {
        return new Tuple(columnConstrains, TableTools.getRow(this.TABLE_FILE, index));
    }

    //TO@DO: See if the getTuples method should return a list of tuples or an array of tuples
    //choose List
    /**
     * Get all the tuples of the table
     * @param indices The indices of the tuples to get
     * @return The tuples of the table
     * @throws DoNotExistsException If the table does not exist
     */
    public List<Tuple> getTuples(List<Integer> indices) throws DoNotExistsException {
        List<Tuple> tuples = new java.util.ArrayList<>();
        for (int index : indices) {
            tuples.add(getTuple(index));
        }
        return tuples;
    }

    /**
     * Get all the tuples of the table
     * @return The tuples of the table
     * @throws DoNotExistsException If the table does not exist
     */
    public List<Tuple> getAllTuples() throws DoNotExistsException {
        List<Tuple> tuples = new java.util.ArrayList<>();
        for (int index = 1; index < FileManager.countLines(TABLE_FILE); index++) {
            tuples.add(getTuple(index));
        }
        return tuples;
    }

    /**
     * Returns the file of the table
     * @return The file of the table
     */
    public File getTableFile() {
        return TABLE_FILE;
    }

    /**
     * Returns the name of the table
     * @return The name of the table
     */
    public String getName() {
        return tableName;
    }

    /**
     * Equals method for the table. Two tables are considered equal if their names are equal. <b>Case-insensitive.</b>
     * @param obj The object to compare
     * @return True if the object is a table and has the same name as this table
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Table && ((Table) obj).getName().equalsIgnoreCase(tableName);
    }
}