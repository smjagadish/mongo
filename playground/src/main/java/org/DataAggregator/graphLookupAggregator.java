package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.GraphLookupOptions;
import org.bson.Document;

import java.util.Arrays;

public class graphLookupAggregator {
    private MongoClient client;
    private MongoDatabase database;

    public graphLookupAggregator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void doGraphLookup()
    {
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("employees").aggregate(
                    Arrays.asList(Aggregates.match(Filters.eq("name","Charlie")),
                            Aggregates.graphLookup("employees","$_id","_id","managerId","subordinates",new GraphLookupOptions().maxDepth(2)))).cursor();
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
