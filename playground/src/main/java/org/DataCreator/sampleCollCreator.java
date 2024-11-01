package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.DataImpl.sampleColl;

public class sampleCollCreator {
    private MongoClient client;
    private MongoDatabase database;
    public sampleCollCreator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void addRecord(sampleColl record)
    {
        System.out.println("adding record");
        InsertOneResult res = this.database.getCollection("sampleCollection", sampleColl.class)
                .insertOne(record);
        System.out.println("insert result:" +res.wasAcknowledged());
    }
}
