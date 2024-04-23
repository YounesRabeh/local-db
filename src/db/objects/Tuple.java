package db.objects;

/**
 * A tuple of values, used to represent a row in a database. The values are stored as strings.
 * @param values The values of the tuple
 */
public record Tuple(String... values) {

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
