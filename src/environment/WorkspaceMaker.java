package environment;

import db.structure.Table;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import systemx.utils.PathResolver;

import java.io.File;

public final class WorkspaceMaker{

    public static File assignTableFile(Table table) throws FailedToCreateException, DoNotExistsException {
        return PathResolver.createFileInDirectory(table.getName() + ".csv", WorkspaceInit.getInstance().getWorkspaceLocation());
    }
}
