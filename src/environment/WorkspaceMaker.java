package environment;

import db.structure.Table;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import systemx.utils.PathResolver;

import java.io.File;

public final class WorkspaceMaker{

    public static File assignTableFile(Table table) throws FailedToCreateException {
        File path = WorkspaceInit.getInstance().getWorkspaceLocation();
        if (path == null) throw new FailedToCreateException("Table file path is null");
        return new File( path +
                        File.separator + table.getName() + ".csv"
        );
    }
}
