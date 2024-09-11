package org.DataRetriever;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.DataImpl.locations;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.near;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class geoProximity {
    private MongoClient client;
    private MongoDatabase database;
    public geoProximity(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void searchStore(double farthest , double nearest)
    {
        System.out.println("searching nearby stores");
        MongoCursor<locations> cursor = null;
        try
        {
            Point loc = new Point(new Position(-73.9667,40.78));
            Bson query = near("coord",loc,farthest,nearest);
            cursor = this.database.getCollection("store_location", locations.class)
                    .find(query)
                    .projection(Projections.fields(Projections.excludeId(),Projections.include("city")))
                    .cursor();
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().getCity_proper());
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
