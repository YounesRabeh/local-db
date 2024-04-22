package db.objects;


public interface DbEntry {
    /**
     * Converts the tuple to a record
     * @return A record of the tuple
     */
    Tuple getTuple();

}
