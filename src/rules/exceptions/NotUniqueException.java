package rules.exceptions;

/**
 * An exception thrown when an element is not unique in the parent object.
 */
public class NotUniqueException extends Exception implements ConstraintsErrors {

    //TODO: see {@info.TODO}
    /**
     * Creates a new NotUniqueException.
     */
    public NotUniqueException() {
        super();
    }

    /**
     * Creates a new NotUniqueException with the given message.
     * @param message The message of the exception
     */
    public NotUniqueException(String message) {
        super(NOT_UNIQUE_CONSTRAINT + message);
    }

    /**
     * Creates a new NotUniqueException with the given object and parent.
     * @param object The object that is not unique
     * @param parent The parent object
     */
    public NotUniqueException(String object, String parent){
        super(NOT_UNIQUE_CONSTRAINT + object + " in " + parent);
    }

}
