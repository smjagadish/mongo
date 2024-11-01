package org.DataRetriever;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.DataImpl.userTracker;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userTrackerGet {
    private MongoClient client;
    private MongoDatabase database;
    public userTrackerGet(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void getRecords()
    {
        System.out.println("dumping records from userTracker");
        MongoCursor<userTracker> cursor = null;
        try {
            cursor = this.database.getCollection("userTracker", userTracker.class)
                    .find(Filters.bitsAnySet("permissions",8))
                    .projection(Projections.fields(Projections.excludeId())).cursor();
            while (cursor.hasNext())
            {
              //int val =  cursor.next().getPermissions().getData()[0] ;
                // val = val & 0xFF;
                System.out.println(cursor.next().toString());
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
