package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import org.DataImpl.userTracker;

import java.util.Arrays;
import java.util.stream.Collectors;

public class userTrackerCreator {
    private MongoClient client;
    private MongoDatabase database;
    public userTrackerCreator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void insertDoc(userTracker[] userTrackerCreators)
    {
        try
        {
            System.out.println("inserting records to userTrackerGet");
            this.database.createCollection("userTrackerGet");
            InsertManyResult iResult = this.database.getCollection("userTrackerGet", userTracker.class)
                    .insertMany(Arrays.stream(userTrackerCreators).collect(Collectors.toList()));
            System.out.println("Result is:"+ iResult.wasAcknowledged());
        }
        catch(Exception e)
        {
            // do nothing
        }
    }
}
