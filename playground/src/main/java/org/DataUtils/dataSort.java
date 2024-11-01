package org.DataUtils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import org.DataImpl.stores;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class dataSort {
    public MongoClient client;
    public MongoDatabase database;
    public dataSort(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void textSearch(String term) {

        System.out.println("performing a text search");
        Bson doc = Filters.text(term);
        MongoCursor<stores> cursor = null;
        try {
            cursor = this.database.getCollection("stores", stores.class)
                    .find(doc)
                    .projection(Projections.excludeId())
                    .cursor();
            while (cursor.hasNext()) {
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
    public void limitResults(int limit)
    {
        System.out.println("triggering sort and limit with limit of: "+limit);
        MongoCursor<stores> cursor = null;
        try {
           cursor = this.database.getCollection("stores", stores.class)
                   .find(Filters.empty())
                   .limit(limit)
                   .cursor();
           while (cursor.hasNext())
           {
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
    public void skipResults(int skip_count)
    {
        System.out.println("skip operation to exclude n record from the beginning");
        MongoCursor<stores> cursor = null;
        try {
           cursor = this.database.getCollection("stores", stores.class)
                   .find(Filters.empty())
                   .skip(skip_count)
                   .cursor();
           while (cursor.hasNext())
           {
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
    public void addStore(String name , String desc , int s_id)
    {
        System.out.println("creating a new store");
        try
        {
            stores store = stores.builder()
                    .s_name(name)
                    .s_desc(desc)
                    .s_id(s_id)
                    .build();
            InsertOneResult res= this.database.getCollection("stores", stores.class)
                    .insertOne(store);
            System.out.println("inserted with id : "+res.getInsertedId());
        }
        catch(Exception e)
        {
            // do nothing
        }
    }
    public void addDynamicDataToQuery()
    {
        System.out.println("will add 100 to the id field and project as a new field");
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("stores")
                    .find()
                    .projection(Projections.fields(Projections.excludeId(),Projections.include("name","description"),Projections.computed("new_field_value",
                            new Document("$add", Arrays.asList("$_id",100)))))
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
