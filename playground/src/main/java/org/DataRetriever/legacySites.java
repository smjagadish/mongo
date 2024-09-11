package org.DataRetriever;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.DataImpl.legacyRadio;

public class legacySites {
    private MongoClient client;
    private MongoDatabase db;
    public legacySites(MongoClient client)
    {
        this.client = client;
        this.db = client.getDatabase("local");
    }
    public void fetchAllLegacySites()
    {
        MongoCursor<legacyRadio> cursor = null;
        try {
            System.out.println("dumping legacy site info");
            cursor = db.getCollection("legacy_sites", legacyRadio.class)
                    .find()
                    .cursor();
            while (cursor.hasNext())
            {
                System.out.println(cursor.next().toString());
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally {
           if(cursor!=null)
               cursor.close();
        }
    }

}
