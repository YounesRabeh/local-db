package environment;

import systemx.utils.PathResolver;

import java.io.File;

public class WorkspaceInit implements DatabaseLocations {
    /** The singleton instance of the workspace */
    private static WorkspaceInit instance = null;
    /** The location of the workspace */
    private static File WORKSPACE = null;
    /** The name of the workspace */
    private static String WORKSPACE_NAME = "localDB";

    // Private constructor to prevent instantiation from outside
    private WorkspaceInit(File workspace){
        try {
            //TODO: in the future detect the jar entry main function and create the dir in the same dir
            WORKSPACE = new File("out/artifacts/local_db_jar" + "/localDB_new/" + WORKSPACE_NAME);
            PathResolver.createDirectory(WORKSPACE.getAbsolutePath());
            //TODO: create the tables dir
            //TODO: create the workspace directories
            //TODO: if the workspace already exists, do not create it, just set the path

        } catch (Exception e) {
            throw new RuntimeException("Failed to create " + WORKSPACE_NAME);
        }
    }

    /**
     * Sets the workspace location
     * @param workspaceName The location of the workspace
     * @throws NullPointerException If the workspace is null
     */
    public static void setWorkspace(String workspaceName) {
        if (workspaceName == null) throw new NullPointerException("Workspace is null");
        WORKSPACE_NAME = workspaceName;
        WorkspaceInit.getInstance();
    }

    // Static method to get the singleton instance
    /**
     * Gets the singleton instance of the workspace
     * @return The workspace instance
     */
    public static synchronized WorkspaceInit getInstance() {
        if (instance == null) {
            instance = new WorkspaceInit(WORKSPACE);
        }
        return instance;
    }

    /**
     * Gets the location of the workspace
     * @return The location of the workspace
     */
    public File getWorkspaceLocation() {
        return WORKSPACE;
    }
}
