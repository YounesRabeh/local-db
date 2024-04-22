package rules.constrains;

/**
 * A constraint in a database table (a column).
 * A constraint has a name and a data type.
 */
public class Constraint {
    private final String name;
    private final String dataTypeString;
    private final Class<?> dataType;

    /**
     * Creates a new constraint with the given name and data type.
     * @param name The name of the constraint <b>not type sensitive, must be unique</b>
     * @param dataType The data type of the constraint <b> -your object-.class</b>
     */
    public Constraint(String name, Class<?> dataType) {
        this.name = name.toUpperCase();
        this.dataTypeString = dataType.getSimpleName();
        this.dataType = dataType;
    }

    /**
     * Get the name of the constraint.
     * @return The name of the constraint
     */
    public String getName() {
        return name;
    }

    /**
     * Get the data type of the constraint as a string.
     * @return The data type of the constraint as a string
     */
    public String getDataTypeString() {
        return dataTypeString;
    }

    /**
     * Get the data type of the constraint.
     * @return The data type of the constraint
     */
    public Class<?> getDataType() {
        return dataType;
    }

}
