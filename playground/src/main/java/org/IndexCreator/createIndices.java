package org.IndexCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.DataImpl.userTracker;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Indexes.ascending;

public class createIndices {
    private MongoClient client;
    private MongoDatabase database;
    public createIndices(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void addIndex()
    {
        System.out.println("adding asc.index to username in userTracker");
        Bson idx = ascending("username");
        this.database.getCollection("userTracker")
                .createIndex(idx , new IndexOptions().name("NAME_IDX"));
    }
    public void addTextIndex()
    {
        System.out.println("adding a text index to description in stores");
        Bson idx = Indexes.text("description");
        this.database.getCollection("stores")
                .createIndex(idx);
    }
    public void showIndex()
    {
       System.out.println("querying indices in userTracker");
        MongoCursor< Document> cursor = this.database.getCollection("userTracker")
               .listIndexes().cursor();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next().toJson());
        }
        if(cursor!=null)
            cursor.close();
    }
}
