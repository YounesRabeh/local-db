package tests;

import systemx.exceptions.DoNotExistsException;
import systemx.utils.CsvTools;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class test2 {
    public static void main(String[] args) throws DoNotExistsException {
        CsvTools.deleteRow(new File("out/artifacts/local_db_jar/localDB_Tests/table2.csv"),
                1);
        CsvTools.insertRow(new File("out/artifacts/local_db_jar/localDB_Tests/table2.csv"),
                1, new String[]{"1", "2", "3", "4", "9"});

    }

}