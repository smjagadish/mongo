package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptionDefaults;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.result.InsertOneResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.DataImpl.locations;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class locationCreator {
    private MongoClient client;
    private MongoDatabase database;
    public locationCreator(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void createCollection()
    {
        System.out.println("collection created");
        this.database.createCollection("store_location",new CreateCollectionOptions().maxDocuments(20));
        this.database.getCollection("store_location")
                .createIndex(Indexes.geo2dsphere("coord"));
    }
    public void populateCollection(String city , double longitude , double latitude , String store_type)
    {
        System.out.println("inserting doc");
        locations loc = locations.builder()
                .loc_type(store_type)
                .city_proper(city)
                .geo_coord(new Point(new Position(longitude,latitude)))
                .build();
        InsertOneResult res = this.database.getCollection("store_location", locations.class)
                .insertOne(loc);
        System.out.println("documented inserted with id :" +res.getInsertedId());
    }
}
