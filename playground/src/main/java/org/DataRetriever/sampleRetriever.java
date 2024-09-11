package org.DataRetriever;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class sampleRetriever {
    private MongoClient client;
    private MongoDatabase database;
    public sampleRetriever(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void listRecords()
    {
        MongoCursor<Document> cursor = null;
        try {
            cursor = this.database.getCollection("sampleCollection")
                    .find(Filters.empty())
                    .cursor();
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().toJson());
            }
        }
        catch(Exception e)
        {
            // do nothing
        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }
}
