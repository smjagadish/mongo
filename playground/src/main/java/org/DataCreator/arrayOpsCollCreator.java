package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.DataImpl.arrayOpsColl;

public class arrayOpsCollCreator {
    private MongoClient client;
    private MongoDatabase database;
    public arrayOpsCollCreator(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void createCollectionElement(arrayOpsColl element)
    {
        System.out.println("creating new doc to collection");
        InsertOneResult tresult = this.database.getCollection("myCollection", arrayOpsColl.class)
                .insertOne(element);
        System.out.println("created with id:"+tresult.getInsertedId());
    }
}
