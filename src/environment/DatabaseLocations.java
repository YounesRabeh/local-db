package environment;


import java.io.File;


public interface DatabaseLocations {
    WorkspaceInit WORKSPACE_INIT = WorkspaceInit.getInstance();

    File EXE_LOCATION = WORKSPACE_INIT.getWorkspaceLocation();

    String DATABASE = null;
}
