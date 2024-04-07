import rules.ClientType;
import template.base.Client;
import template.records.ClientRecord;

import java.io.IOException;
import java.util.NoSuchElementException;

import static explorer.CsvTools.deleteRow;
import static explorer.CsvTools.getRow;
import static explorer.FileManager.getNumberOfLines;

final class DbTools implements DbInfo {
    private DbTools(){}

    //TODO: add all the tools that DBMS needs [Object conversion]

    static String getClientDbDestination(ClientRecord record){
        int destinationIndex = 0;
        //FIXME: make it more efficient
        for (String destination: CLIENTS_DATABASES){
            int startIndex = destination.indexOf(CLIENTS_DB_DIR + "/") + CLIENTS_DB_DIR.length();
            String dbLocalDestination = destination.substring(startIndex);
            String dbLocalDestinationType = dbLocalDestination.substring(1, dbLocalDestination.indexOf(DB_FILE_SEPARATOR));
            String clientType = record.client().getClientType().toString();
            if (dbLocalDestinationType.contains(clientType)){
                return CLIENTS_DATABASES.get(destinationIndex);
            }
            destinationIndex += 1;
        }
        throw new NoSuchElementException("No database for " + record.getClass().getSimpleName() + " in " + CLIENTS_DB_DIR);
    }


    //TODO: Client -> type , Client -> Db dirs

    static int getTupleNumber(Client client) throws IOException {
        String clientType = client.getClientType().toString().toUpperCase();
        return getNumberOfLines(CLIENTS_DB_DIR +
                "/" + clientType +
                DB_FILE_SEPARATOR +
                DB_FILE_CLIENTS_ENDING
        );
    }


    //TODO: create a row & column objects
    static String[] removeTuple(Client client) throws IOException {
        int index = client.getID(); //TODO: exception if assignId fails
        String targetDb = getTargetDb(client.getClientType());
        String[] tuple = getRow(targetDb, index);
        deleteRow(targetDb, index);
        return tuple;
    }

    static String getTargetDb(Object obj){
        int dbIndex = 0;
        for (String db: DATABASES_DIR){
            if (db.substring(db.lastIndexOf("/") + 1).contains(obj.getClass().getSimpleName())){
                return DATABASES_DIR.get(dbIndex);
            }
            dbIndex++;
        }
        throw new NoSuchElementException("No target db in " + CLIENTS_DB_DIR + " for " + obj.getClass().getSimpleName());
    }

    private static String getTargetDb(ClientType clientType){
        int dbIndex = 0;
        for (String db: CLIENTS_DATABASES){
            if (db.contains(clientType.toString())){
                return CLIENTS_DATABASES.get(dbIndex);
            }
            dbIndex++;
        }
        throw new NoSuchElementException("Target db doesn't exist in " + CLIENTS_DB_DIR);
    }

    private String getPathBranchFromLeaf(String path, int height){
        return path.substring(path.in("/") + 1)
    }






}
