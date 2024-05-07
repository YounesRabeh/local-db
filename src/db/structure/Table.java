package db.structure;

import java.util.List;
import java.io.File;

import db.objects.Tuple;
import db.tools.TableTools;
import environment.WorkspaceMaker;
import rules.notes.Constraint;
import rules.notes.Constraints;
import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;

import static environment.WorkspaceMaker.assignTableFile;

/**
 * Represents a table in a database, with columns and rows.
 */
public class Table {
    /** The name of the table */
    private final String tableName;
    /** The columns of the table */
    private Constraints columnConstrains;
    /** The original ref columns of the table */
    private final Constraints originalColumnsConstrainsRef;
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
        this.originalColumnsConstrainsRef = columns;
        //FIX@ME: the table file is created even if the table is not added to the database
        TABLE_FILE = assignTableFile(this);
        //WorkspaceMaker.createTableFile(TABLE_FILE);
        if (!TABLE_FILE.exists()) {
            TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
        } else System.out.println( tableName + " is already present, use overrideColumns() to override the columns");
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
     * Uses the original columns reference, discarding the current columns and
     * using the original columns reference (which can be modified independently of the table)
     */
    public void useOriginalColumnsRef() throws DoNotExistsException {
        this.columnConstrains = originalColumnsConstrainsRef;
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
    public void deleteColumn(Constraint... columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
        TableTools.columnNamesSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Deletes a list of columns from the table
     * @param columns The list of columns to delete
     */
    public void deleteColumn(List<Constraint> columns) throws NotUniqueException, DoNotExistsException {
        deleteColumn(columns.toArray(new Constraint[0]));
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
     * Adds a tuple to the table
     * @param tuple The tuple to add
     * @throws DoNotExistsException If the tuple is null or the table does not exist
     */
    public void addTuple(Tuple tuple) throws DoNotExistsException {
        if (tuple == null) throw new DoNotExistsException("Tuple is null");
        if (tuple.values().length != columnConstrains.getConstraints().size()) {
            //
            //TODO: add the possibility to add a tuple with less/more values than the columns
            //

            throw new DoNotExistsException("Tuple values do not match the columns of the table");
        } //FIXME: check if the values are cast-able to the column data types
        TableTools.addRow(TABLE_FILE, tuple.values());
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

    //TODO: See if the getTuples method should return a list of tuples or an array of tuples
    /**
     * Get all the tuples of the table
     * @param indices The indices of the tuples to get
     * @return The tuples of the table
     * @throws DoNotExistsException If the table does not exist
     */
    public Tuple[] getTuples(List<Integer> indices) throws DoNotExistsException {
        Tuple[] tuples = new Tuple[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            tuples[i] = getTuple(indices.get(i));
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