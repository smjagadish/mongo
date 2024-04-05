package org.DataPurger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class radioPhaseOut {
    private MongoClient client;
    private MongoDatabase db;
    public radioPhaseOut(MongoClient client)
    {
        this.client = client;
        this.db = client.getDatabase("local");
    }
    public void decom_by_id(String id)
    {
        try {
            Document doc = new Document("_id",new ObjectId(id));
            DeleteResult dr = db.getCollection("trial_sites")
                    .deleteOne(doc);
            System.out.println("deleted documents count :"+ dr.getDeletedCount());
        }
        catch(Exception e)
        {

        }
    }
}
