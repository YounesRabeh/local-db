package db.structure;

import java.util.List;

import rules.constrains.Constraint;
import rules.constrains.Constraints;


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
    public Table(String tableName, Constraints columns) {
        this.tableName = tableName;
        final Constraints columnsCopy = new Constraints();
        for (Constraint column : columns.getConstraints()) {
            columnsCopy.addConstraint(new Constraint(column.getName(), column.getDataType()));
        }
        this.columnConstrains = columnsCopy;
        this.originalColumnsConstrainsRef = columns;
    }

    /**
     * Adds columns to the table
     * @param columns The columns to add
     */
    public void addColumn(Constraint... columns) {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
    }

    /**
     * Adds a list of columns to the table
     * @param columns The list of  columns to add
     */
    public void addColumn(List<Constraint> columns) {
        for (Constraint column : columns) {
            this.columnConstrains.addConstraint(column);
        }
    }

    /**
     * Uses the original columns reference
     */
    public void useOriginalColumnsRef() {
        this.columnConstrains = originalColumnsConstrainsRef;
    }

    public void overrideColumns(Constraints columns) {
        this.columnConstrains = columns;
    }

    /**
     * Deletes columns from the table
     * @param columns The columns to delete
     */
    public void deleteColumn(Constraint... columns) {
        for (Constraint column : columns) {
           this.columnConstrains.deleteConstraint(column);
        }
    }

    /**
     * Deletes a list of columns from the table
     * @param columns The list of columns to delete
     */
    public void deleteColumn(List<Constraint> columns) {
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
