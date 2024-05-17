package environment;

import db.structure.Table;
import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import systemx.utils.PathResolver;

import java.io.File;

public final class WorkspaceMaker{

    /**
     * Assigns a table file to a table
     * @param table The table to assign the file
     * @return The file of the table
     * @throws FailedToCreateException If the file could not be created
     */
    public static File assignTableFile(Table table) throws FailedToCreateException {
        File path = WorkspaceInit.getInstance().getWorkspaceLocation();
        if (path == null) throw new FailedToCreateException("Table file path is null");
        return new File( path +
                        File.separator + table.getName() + ".csv"
        );
    }

    /**
     * Creates a table file
     * @param tableFile The file to create
     * @throws FailedToCreateException If the file could not be created
     */
    public static void createTableFile(File tableFile) throws FailedToCreateException {
        try {
           tableFile.createNewFile();
        } catch (Exception e) {
            throw new FailedToCreateException(tableFile.getAbsolutePath());
        }
    }
}
