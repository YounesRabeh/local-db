package db.structure;

import systemx.exceptions.DoNotExistsException;

import java.util.*;

public class DataBase {
    /** The name of the database */
    private final String dbName;
    /** The tables of the database */
    private HashMap<String, Table> dbTables = new HashMap<>();
    /** The names of the tables in the database */
    private Set<String> tableNames = new HashSet<>();

    /**
     * Creates a database with the given name and tables
     * @param dbName The name of the database
     * @param tables The tables of the database
     */
    public DataBase(String dbName, Table... tables) {
        this.dbName = dbName;
        addElements(List.of(tables));
    }

    /**
     * Creates a database with the given name and a list of tables
     * @param dbName The name of the database
     * @param tableList The list of tables in the database
     */
    public DataBase(String dbName, List<Table> tableList) {
        this.dbName = dbName;
        addElements(tableList);
    }

    /**
     * Adds elements to the database
     * @param collection The collection of elements to add
     */
    private void addElements(Collection<Table> collection ) {
        for (Table table : collection) {
            final String tableName = table.getName();
            if (tableNames.add(tableName)) {
                this.dbTables.put(tableName, table);
            }
        }
    }

    /**
     * Deletes elements from the database
     * @param collection The collection of elements to delete
     */
    private void deleteElements(Collection<Table> collection) {
        for (Table table : collection) {
            final String tableName = table.getName();
            if (tableNames.remove(tableName)) {
                dbTables.remove(tableName);
            }
        }
    }

    /**
     * Add tables to the database. Two tables are considered equal if their names are equal
     * @param tables The tables to add
     * @see Table#equals
     */
    public void addTable(Table... tables) {
        addElements(List.of(tables));
    }

    /**
     * Adds a list of tables to the database. Two tables are considered equal if their names are equal
     * @param tableList The list of tables to add
     * @see Table#equals
     */
    public void addTable(List<Table> tableList) {
        addElements(tableList);
    }

    /**
     * Deletes tables from the database. Two tables are considered equal if their names are equal
     * @param tables The tables to delete
     * @see Table#equals
     */
    public void deleteTable(Table... tables) {
       deleteElements(List.of(tables));
    }

    /**
     * Deletes a list of tables from the database. Two tables are considered equal if their names are equal
     * @param tableList The list of tables to delete
     * @see Table#equals
     */
    public void deleteTable(List<Table> tableList) {
       deleteElements(tableList);
    }

    /**
     * Returns the name of the database
     * @return The name of the database
     */
    public String getName() {
        return dbName;
    }

    /**
     * Returns the tables of the database
     * @return The tables of the database
     */
    public List<Table> getDbTables() {
        return new ArrayList<>(dbTables.values());
    }

    /**
     * Returns the table with the given name
     * @param tableName The name of the table to return
     * @return The table with the given name
     * @throws DoNotExistsException If the table does not exist in the database
     */
    public Table getTable(String tableName) throws DoNotExistsException {
        Table table = dbTables.get(tableName);
        if (table == null) throw new DoNotExistsException(tableName);
        return table;
    }
}
