package db.objects;

import rules.notes.Constraint;
import rules.notes.Constraints;
import systemx.exceptions.DoNotExistsException;


/**
 * A tuple of values, used to represent a row in a database. The values are stored as strings.
 * @param values The values of the tuple
 */
public record Tuple(Constraints constraints, String... values) {
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
        int num = 0;
        for (Constraint column : constraints.getConstraints()) {
            if (column.getName().equalsIgnoreCase(columnName)) {
                return values[num];
            }
            num++;
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
