package org.IndexManager;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
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
        IndexOptions options = new IndexOptions();
        options.collation(Collation.builder().locale("en").build());
        options.name("NAME_IDX");
        this.database.getCollection("userTracker")
                .createIndex(idx , options);
    }
    public void addTextIndex()
    {
        try {
            // doesn't support collations
            System.out.println("adding a text index to description in stores");
            Bson idx = Indexes.text("description");
            this.database.getCollection("stores")
                    .createIndex(idx);
        }
        catch (Exception e)
        {
            // do nothing
        }
    }
    public void addMultiKeyIndex()
    {
        System.out.println("adding a multikey index to precincts array in parks collection");
        Bson idx = Indexes.ascending("precincts");
        this.database.getCollection("parks")
                .createIndex(idx);
    }
    public void addCompoundIndex()
    {
        System.out.println("adding a compound index on radiocconf.mode and site in legacy_sites collection");
        Bson idx = Indexes.compoundIndex(Indexes.ascending("radioconf.max_bw"),Indexes.ascending("site"));
        this.database.getCollection("legacy_sites")
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
