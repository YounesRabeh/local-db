package db.objects;

/**
 * A tuple of values, used to represent a row in a database. The values are stored as strings.
 * @param values The values of the tuple
 */
public record Tuple(Object... values) {

}
