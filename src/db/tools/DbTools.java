package db.tools;

import db.structure.DataBase;
import db.structure.Table;
import environment.WorkspaceInit;
import rules.exceptions.NotUniqueException;
import rules.notes.Constraint;
import rules.notes.Constraints;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import systemx.utils.CsvTools;
import systemx.utils.FileManager;
import systemx.utils.PathResolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DbTools {
    private DbTools() {}

    /**
     * Assigns a database location
     * @param db The database to assign the location
     * @return The location of the database
     */
    public static File assignDbLocation(DataBase db){
        final File WORKSPACE_PATH = WorkspaceInit.getInstance().getWorkspaceLocation();
        String dbPath = WORKSPACE_PATH.getAbsolutePath() + File.separator + db.getName();
        PathResolver.createDirectory(dbPath);
        return new File(dbPath);
    }

    /**
     * Creates a table file
     * @param tableFile The file to create
     * @throws FailedToCreateException If the file could not be created
     */
    public static void createTableFile(File tableFile) throws FailedToCreateException {
        try {
            if (tableFile.createNewFile());
        } catch (Exception e) {
            throw new FailedToCreateException(tableFile.getAbsolutePath());
        }
    }

    /**
     * Gets the tables in a database
     * @param dbLocation The location of the database
     * @return The tables in the database
     * @throws DoNotExistsException If the database does not exist
     * @throws FailedToCreateException If the tables could not be created
     * @throws NotUniqueException If the tables are not unique
     */
    public static List<Table> getTablesInDataBase(File dbLocation)
            throws DoNotExistsException, FailedToCreateException, NotUniqueException {
        File[] files = PathResolver.getFilesInDirectory(dbLocation);
        List<Table> tables = new ArrayList<>();
        for (File file : files) {
            if (file.toPath().endsWith(".csv")) {
                tables.add(
                        new Table(
                                file.getName(),
                                new Constraints((Constraint) List.of(CsvTools.getColumnsTitles(file)))
                        )
                );
            }
        }

        return tables;
    }
}
