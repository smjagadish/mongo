package org.DataStreamer;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.DataImpl.newRadio;
import org.bson.Document;

public class streamWatcher {
    private MongoClient mc;
    private MongoDatabase db;
    public streamWatcher(MongoClient mc)
    {
        this.mc = mc;
        if(mc!=null)
            db = mc.getDatabase("local");
    }
    public void openStream()
    {
        MongoCollection<Document> mcoll = db.getCollection("trial_sites");
        ChangeStreamIterable<Document> changeStream = mcoll.watch();
        changeStream.forEach(event ->
                System.out.println("Received a change: " + event));
    }
}
