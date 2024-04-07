import java.io.IOException;

import static explorer.FileManager.createEmptyFileIfNotExists;
import static explorer.PathResolver.createDirectory;

abstract class Database<R extends Record> {

    private final String USERNAME;
    private final String PASSWORD;


    public Database(String username, String password) {
        this.USERNAME = username;
        this.PASSWORD = password;

        dbInit();
    }


    protected abstract void addRecord(R record);
    protected abstract void removeRecord(R record);

    public void createDatabase() {
        createTables();
    }

    // Abstract method to create tables and define constraints
    protected abstract void createTables();


    public void dbInit(){
    }


}
