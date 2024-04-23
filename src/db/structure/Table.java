package db.structure;

import java.util.List;

import rules.constrains.Constraint;
import rules.constrains.Constraints;
import rules.exceptions.NotUniqueException;


public class Table {
    /** The name of the table */
    private final String tableName;
    /** The columns of the table */
    private Constraints columnConstrains;
    /** The original ref columns of the table */
    private Constraints originalColumnsConstrainsRef;

    /**
     * Creates a table with the given name and columns
     * @param tableName The name of the table
     * @param columns The columns of the table
     */
    public Table(String tableName, Constraints columns) throws NotUniqueException {
        this.tableName = tableName;
        this.columnConstrains = columnConstrainsSetup(columns);
        this.originalColumnsConstrainsRef = columns;
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
    public void addColumn(Constraint... columns) throws NotUniqueException {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
    }

    /**
     * Adds a list of columns to the table
     * @param columns The list of  columns to add
     */
    public void addColumn(List<Constraint> columns) throws NotUniqueException {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
    }

    /**
     * Uses the original columns reference, discarding the current columns and
     * using the original columns reference (which can be modified independently of the table)
     */
    public void useOriginalColumnsRef() {
        this.columnConstrains = originalColumnsConstrainsRef;
    }

    /**
     * Overrides the columns of the table with the given columns
     * @param columns The columns to override
     */
    public void overrideColumns(Constraints columns) {
        this.columnConstrains = columns;
    }

    /**
     * Deletes columns from the table
     * @param columns The columns to delete
     */
    public void deleteColumn(Constraint... columns) throws NotUniqueException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
    }

    /**
     * Deletes a list of columns from the table
     * @param columns The list of columns to delete
     */
    public void deleteColumn(List<Constraint> columns) throws NotUniqueException {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
    }

    /**
     * Returns the name of the table
     * @return The name of the table
     */
    public String getName() {
        return tableName;
    }

    /**
     * Returns the columns constraints of the table
     * @return The list of columns constraints of the table
     */
    public List<Constraint> getColumnsConstraints() {
        return columnConstrains.getConstraints();
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
