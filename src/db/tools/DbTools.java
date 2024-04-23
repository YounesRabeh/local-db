package db.tools;

import systemx.exceptions.FailedToCreateException;

import java.io.File;
import java.io.IOException;

public final class DbTools {
    private DbTools() {}

    /**
     * Creates a table file
     * @param tableFile The file to create
     * @throws FailedToCreateException If the file could not be created
     */
    public static void createTableFile(File tableFile) throws FailedToCreateException {
        try {
            tableFile.createNewFile();
        } catch (IOException e) {
            throw new FailedToCreateException(tableFile.getAbsolutePath());
        }
    }
}
