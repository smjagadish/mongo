package org.DataModifier;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.DataImpl.sampleColl;
import org.bson.BsonDateTime;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.util.logging.Filter;

public class sampleCollUpdater {
    private MongoClient client;
    private MongoDatabase database;
    public sampleCollUpdater(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void updateCreationDate(String occupation)
    {
        Bson match = Filters.eq("occupation",occupation);
        this.database.getCollection("sampleCollection", sampleColl.class)
                .findOneAndUpdate(match, Updates.set("createdAt",new BsonDateTime(Instant.now().toEpochMilli())));
    }
}
