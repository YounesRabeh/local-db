package environment;

import db.structure.Table;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import systemx.utils.PathResolver;

import java.io.File;

public final class WorkspaceMaker{

    public static File assignTableFile(Table table) {
        File path = WorkspaceInit.getInstance().getWorkspaceLocation();
        return new File( path +
                        File.separator + table.getName() + ".csv"
        );
    }
}
