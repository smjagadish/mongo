package org.DataModifier;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.DataImpl.parks;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

public class parksUpdater {
    private MongoClient client;
    private MongoDatabase database;
    public parksUpdater(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void updateLoc(String name , int... loc)
    {
        Bson filter= Filters.eq("city",name);
        System.out.println("adding more precincts to:"+name);
        try{
            Bson updated_doc = Updates.pushEach("precincts", Arrays.stream(loc).boxed().collect(Collectors.toList()));
            UpdateResult res = this.database.getCollection("parks")
                    .updateOne(filter,updated_doc);
            System.out.println(res.wasAcknowledged());
        }
        catch(Exception e)
        {

        }
    }

    public void addLoc(String name , int loc)
    {
        Bson filter= Filters.eq("city",name);
        System.out.println("adding a precinct to:"+name);
        try{
            Bson updated_doc = Updates.push("precincts",loc);
            UpdateResult res = this.database.getCollection("parks")
                    .updateOne(filter,updated_doc);
            System.out.println(res.wasAcknowledged());
        }
        catch(Exception e)
        {

        }
    }
    public void setSingleLoc(String name , int loc)
    {
        Bson filter = Filters.eq("city",name);
        System.out.println("setting allowed precincts to only one");
        try
        {
            Bson updated_doc = Updates.set("precincts",Collections.singletonList(loc));
            UpdateResult res = this.database.getCollection("parks")
                    .updateOne(filter,updated_doc);
            System.out.println(res.wasAcknowledged());
        }
        catch(Exception e)
        {
            // do nothing
        }
    }

    public void getMulticities(int... values)
    {
        Bson filter = Filters.elemMatch("precincts",Filters.in("$in",values));
        System.out.println("fetching cities with specific precincts");
        MongoCursor<parks> cursor = null;
        try
        {
              MongoCursor< Document> cs = this.database.getCollection("parks").find(filter)
                     // .projection(Projections.fields(Projections.excludeId(),Projections.include("city")))
                      .cursor();
              while(cs.hasNext())
              {
                  System.out.println(cs.next().toJson());
              }
              cs.close();
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
    public void printParks()
    {
        MongoCursor<parks> cursor = null;
        try
        {
            System.out.println("printing parks");
            cursor = this.database.getCollection("parks", parks.class).find().projection(Projections.excludeId()).cursor();
            while (cursor.hasNext())
            {
                System.out.println(cursor.next().toString());
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }
}
