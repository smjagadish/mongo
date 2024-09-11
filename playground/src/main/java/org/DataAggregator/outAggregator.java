package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.DataImpl.stores;
import org.bson.Document;

import static com.mongodb.client.model.Filters.expr;
import static com.mongodb.client.model.mql.MqlValues.*;

import java.util.Arrays;

public class outAggregator {
    private MongoClient client;
    private MongoDatabase database;
    public outAggregator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void doAggregateToDiffCollection(String coll_name)
    {
        System.out.println("dumping data from stores collection to : "+ coll_name);
        try
        {
            this.database.getCollection("stores")
                    .aggregate(Arrays.asList(Aggregates.out("local",coll_name)));
            var condition = current().getString("name");
             this.database.getCollection("stores", stores.class).aggregate(Arrays.asList(Aggregates.match(expr(condition.eq(of("lfc_store"))))))
                     .cursor();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

}
