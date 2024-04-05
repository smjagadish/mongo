package org.DataModifier;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.DataImpl.newRadio;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class radioUpdater {
    private MongoClient client;
    private MongoDatabase db;
    public radioUpdater(MongoClient client)
    {
        this.client = client;
        this.db = client.getDatabase("local");
    }
    public void change_MaxBw_Single(String oid , int max_bw) {
        try {
            System.out.println("updating max_bw");
            Document doc = new Document("_id", new ObjectId(oid));
            Bson updates = Updates.combine(Updates.set("radio_max_bw", max_bw));
            db.getCollection("trial_sites", newRadio.class)
                    .updateOne(doc, updates);
        } catch (Exception e) {
             // do nothing
        }
    }
    public void change_MinBw_Multi(int min_bw)
    {
        try {
              Bson query = Filters.gte("radio_max_bw",100);
              Bson updates = Updates.set("radio_min_bw",min_bw);
            UpdateOptions options = new UpdateOptions().upsert(true);
              db.getCollection("trial_sites", newRadio.class)
                      .updateMany(query,updates,options);
        }
        catch(Exception e)
        {
            // do nothing
        }

    }
}
