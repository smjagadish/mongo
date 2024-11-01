package org.DataModifier;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.DataImpl.locations;
import org.bson.conversions.Bson;

public class locationUpdator {
    private MongoClient client;
    private MongoDatabase database;
    public locationUpdator(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void findAndUpdate(String city , String type)
    {
        System.out.println("doing atomic update for record in:"+city);
        Bson filter = Filters.and(Filters.eq("city",city),Filters.eq("type",type));
        Bson updated_doc = Updates.combine(Updates.set("type","closed for service"));
        locations loc = this.database.getCollection("store_location", locations.class)
                .findOneAndUpdate(filter,updated_doc, new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        System.out.println(loc.getLoc_type());
    }
}
