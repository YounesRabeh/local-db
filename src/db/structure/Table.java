package db.structure;

import java.io.File;
import java.util.List;

import db.objects.Tuple;
import db.tools.TableTools;
import rules.constrains.Constraint;
import rules.constrains.Constraints;
import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;

import static environment.WorkspaceMaker.assignTableFile;


public class Table {
    /** The name of the table */
    private final String tableName;
    /** The columns of the table */
    private Constraints columnConstrains;
    /** The original ref columns of the table */
    private Constraints originalColumnsConstrainsRef;

    private final File TABLE_FILE;

    /**
     * Creates a table with the given name and columns
     * @param tableName The name of the table
     * @param columns The columns of the table
     */
    public Table(String tableName, Constraints columns) throws
            NotUniqueException, FailedToCreateException, DoNotExistsException {
        this.tableName = tableName;
        this.columnConstrains = columnConstrainsSetup(columns);
        this.originalColumnsConstrainsRef = columns;
        //FIXME: doesn't create the file in the dir
        TABLE_FILE = assignTableFile(this);
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
     * Returns the columns constraints of the table
     * @return The list of columns constraints of the table
     */
    public List<Constraint> getColumnsConstraints() {
        return columnConstrains.getConstraints();
    }



    ///////////////////////////////////////////////////////////////////////////////////

    public Tuple getTuple(int index) throws DoNotExistsException {
        //TODO: don't cast to Object cast each element to the correct type
       return new Tuple((Object) TableTools.getRow(this.TABLE_FILE, index));
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
