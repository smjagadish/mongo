package org.IndexManager;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class removeIndices {
    private MongoClient client;
    private MongoDatabase database;
    public removeIndices(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void cleanupIndices()
    {
        System.out.println("cleaning up indices created ");
        try {
            this.database.getCollection("stores")
                    .dropIndexes();
            this.database.getCollection("parks")
                    .dropIndexes();
            this.database.getCollection("userTracker")
                    .dropIndexes();
            this.database.getCollection("legacy_sites")
                    .dropIndexes();
        }
        catch(Exception e)
        {
            // do nothing;
        }
    }
}
