package db.tools;

import systemx.exceptions.DoNotExistsException;
import systemx.utils.CsvTools;

import java.io.File;

public final class DbTools {
    private DbTools() {}

    public static String[] getRow(File file, int index) throws DoNotExistsException {
        return CsvTools.getRow(file, index);
    }
}
