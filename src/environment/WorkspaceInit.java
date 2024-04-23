package environment;

import systemx.utils.PathResolver;

import java.io.File;

public class WorkspaceInit implements DatabaseLocations {
    private static WorkspaceInit instance = null;
    private final File WORKSPACE;

    //TODO: return here when SystemX works fine (Path resolver bug)

    // Private constructor to prevent instantiation from outside
    private WorkspaceInit() {
        try {
            //TODO: in the future detect the jar entry main function and create the dir in the same dir
            this.WORKSPACE = new File("out/artifacts/local_db_jar" + "/localDB/");
            PathResolver.createDirectory(this.WORKSPACE.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Static method to get the singleton instance
    public static synchronized WorkspaceInit getInstance() {
        if (instance == null) {
            instance = new WorkspaceInit();
        }
        return instance;
    }

    public File getWorkspaceLocation() {
        return this.WORKSPACE;
    }
}
