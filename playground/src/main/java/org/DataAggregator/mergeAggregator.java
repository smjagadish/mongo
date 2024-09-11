package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.MergeOptions;
import org.bson.Document;

import java.util.Arrays;

public class mergeAggregator {
    private MongoClient client;
    private MongoDatabase database;
    public mergeAggregator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void doMerge(String coll_name)
    {
        System.out.println("merging the stores coll to:" + coll_name);
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("stores")
                    .aggregate(Arrays.asList(Aggregates.match(Filters.empty()),
                            Aggregates.merge(coll_name,new MergeOptions().uniqueIdentifier("_id").whenMatched(MergeOptions.WhenMatched.REPLACE).whenNotMatched(MergeOptions.WhenNotMatched.INSERT)))).cursor();
            while (cursor.hasNext())
            {
                System.out.println(cursor.next());
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
