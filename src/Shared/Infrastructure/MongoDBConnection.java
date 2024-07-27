package Shared.Infrastructure;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private MongoDatabase database;

    public MongoDBConnection(String connection, String dbName) {
        database = MongoClients.create(connection).getDatabase(dbName);
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
