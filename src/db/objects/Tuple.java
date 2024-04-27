package db.objects;

import rules.constraints.Constraint;
import rules.constraints.Constraints;
import systemx.exceptions.DoNotExistsException;

import java.util.List;

/**
 * A tuple of values, used to represent a row in a database. The values are stored as strings.
 * @param values The values of the tuple
 */
public record Tuple(String[] columnNames, String... values) {

    /**
     * Gets the value at the given index
     * @param index The index of the value
     * @return The value at the given index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public String getValue(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= values.length) {
            throw new IndexOutOfBoundsException();
        }
        return values[index];
    }

    /**
     * Gets the value of the given column name
     * @param columnName The name of the column
     * @return The value of the column
     * @throws DoNotExistsException If the column does not exist
     */
    public String getValue(String columnName) throws DoNotExistsException {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equalsIgnoreCase(columnName)) {
                return values[i];
            }
        }
        //TODO: Add custom exception
        throw new DoNotExistsException("The column " + columnName + " does not exist in the tuple");
    }


    @Override
    public String toString() {
        StringBuilder tupleString = new StringBuilder();
        tupleString.append("(");
        for (String value : values) {
            tupleString.append(value).append(", ");
        }
        tupleString.delete(tupleString.length() - 2, tupleString.length());
        tupleString.append(")");
        return tupleString.toString();
    }
}
