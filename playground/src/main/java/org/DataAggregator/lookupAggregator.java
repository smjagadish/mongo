package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.bson.types.Binary;

import java.util.Arrays;

@Data
public class lookupAggregator {
    private MongoClient client;
    private MongoDatabase database;
    public lookupAggregator(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void doLookup(int id)
    {
        MongoCursor<Document> cursor = null;
        try {

            cursor = this.getDatabase().getCollection("orders").
                    aggregate(Arrays.asList
                            (Aggregates.match(Filters.eq("_id",id)),
                                    Aggregates.lookup("customers","customer_id","_id","combined")
                                    ))
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
