package rules.notes;

/**
 * A constraint in a database table (a column).
 * A constraint has a name and a data type.
 * Only the name is used to compare constraints. Ones the constraint is created, it cannot be modified, only deleted.
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
        this.name = name;
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

    /**
     * Two constraints are equal if they have the same name (case-insensitive).
     * @param obj The object to compare
     * @return True if the constraints are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Constraint && name.equalsIgnoreCase(((Constraint) obj).name);
    }
}
