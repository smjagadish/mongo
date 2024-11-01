package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UnwindOptions;
import org.bson.Document;

import java.util.Arrays;

public class unwindAggregator {
    private MongoClient client;
    private MongoDatabase database;
    public unwindAggregator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void doUnwind()
    {
        System.out.println("unwinding precincts array for parks");
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("parks").aggregate(Arrays.asList(Aggregates.match(Filters.empty()),
                    Aggregates.unwind("$precincts",new UnwindOptions().preserveNullAndEmptyArrays(true).includeArrayIndex("idx")))).cursor();
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
