package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.DataImpl.legacyRadio;
import org.DataImpl.newRadio;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Sorts.descending;

public class simpleAggregator {
    MongoClient client;
    MongoDatabase database;
    public simpleAggregator(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void doAggregate()
    {
        MongoCursor<newRadio> cursor = null;
        try
        {
            System.out.println("a simple aggregation pipeline that dumps legacy site collection");
            cursor = this.database.getCollection("trial_sites", newRadio.class)
                    .aggregate(Arrays.asList(Aggregates.match(Filters.empty()),Aggregates.project(Projections.excludeId()))).cursor();
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().toString());
            }
        }
        catch (Exception e)
        {
          // do nothing
        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }
    public void filteredAggregate()
    {
        MongoCursor<newRadio> cursor = null;
        try
        {
            Bson filter = Filters.and(Filters.gte("radio_min_bw",20),Filters.lte("radio_max_bw",200));
            Bson projection = Projections.include("radio_sap_site","radio_category");
            cursor = this.database.getCollection("trial_sites", newRadio.class)
                    .aggregate(Arrays.asList(Aggregates.match(filter),Aggregates.project(projection))).cursor();
            System.out.println("now dumping the results of a aggregate with filters");
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
    public void sortedAggregate()
    {
        MongoCursor<newRadio> cursor = null;
        try
        {
            cursor = this.database.getCollection("trial_sites", newRadio.class)
                    .aggregate(Arrays.asList(Aggregates.match(Filters.empty()),
                            Aggregates.sort(descending("radio_max_bw")),
                            Aggregates.project(Projections.include("radio_sap_site","radio_category","radio_max_bw"))))
                    .cursor();
            System.out.println("aggregating over sorted data in desc order of max_bw");
            while(cursor.hasNext())
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
    public void aggregateWithComputedFields()
    {
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("trial_sites")
                    .aggregate(Arrays.asList(Aggregates.match(Filters.eq("radio_category","highband")),
                            Aggregates.project(Projections.fields(Projections.excludeId()
                            , Projections.include("radio_sap_site","radio_max_bw","radio_min_bw","radio_category","sector_count")
                                            , Projections.computed("dup_sector","$sector_count")))))
                    .cursor();
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().toJson());
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
