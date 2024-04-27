package db.structure;

import java.io.File;
import java.util.List;

import db.objects.Tuple;
import db.tools.TableTools;
import rules.constraints.Constraint;
import rules.constraints.Constraints;
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
    private Constraints originalColumnsConstrainsRef;
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
        TABLE_FILE = assignTableFile(this);
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
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
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Adds a list of columns to the table
     * @param columns The list of  columns to add
     */
    public void addColumn(List<Constraint> columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Uses the original columns reference, discarding the current columns and
     * using the original columns reference (which can be modified independently of the table)
     */
    public void useOriginalColumnsRef() throws DoNotExistsException {
        this.columnConstrains = originalColumnsConstrainsRef;
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Overrides the columns of the table with the given columns
     * @param columns The columns to override
     */
    public void overrideColumns(Constraints columns) throws DoNotExistsException {
        this.columnConstrains = columns;
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Deletes columns from the table
     * @param columns The columns to delete
     */
    public void deleteColumn(Constraint... columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Deletes a list of columns from the table
     * @param columns The list of columns to delete
     */
    public void deleteColumn(List<Constraint> columns) throws NotUniqueException, DoNotExistsException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
        TableTools.columnsSetup(TABLE_FILE, getColumnNames());
    }

    /**
     * Returns the columns constraints of the table
     * @return The list of columns constraints of the table
     */
    public List<Constraint> getColumnsConstraints() {
        return columnConstrains.getConstraints();
    }

    /**
     * Returns the names of the columns of the table
     * @return The names of the columns of the table
     */
    public String[] getColumnNames() {
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
     * Get the tuple at the given index. <b>(Index 0) is the column names</b> {@link #COLUMN_NAMES_INDEX}
     * @param index The row index of the tuple
     * @return The tuple at the given row index
     * @throws DoNotExistsException If the tuple does not exist
     *
     */
    public Tuple getTuple(int index) throws DoNotExistsException {
        return new Tuple(this.getColumnNames(), TableTools.getRow(this.TABLE_FILE, index));
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
     * Equals method for the table. Two tables are considered equal if their names are equal. Case-insensitive.
     * @param obj The object to compare
     * @return True if the object is a table and has the same name as this table
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Table && ((Table) obj).getName().equalsIgnoreCase(tableName);
    }


}
