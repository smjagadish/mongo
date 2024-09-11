package org.DataAggregator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.Data;
import org.DataImpl.newRadio;
import org.bson.BsonString;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;


import static com.mongodb.client.model.Accumulators.*;
import static java.util.Arrays.asList;


@Data
public class groupAggregator {
    private MongoClient client;
    private MongoDatabase database;
    public groupAggregator(MongoClient client)
    {
        this.client = client;
        this.database = client.getDatabase("local");
    }
    public void simpleGrouping(String category)
    {
        System.out.println("simple grouping by radio_category with value:"+ category);
        MongoCursor<Document> cursor = null;
        try {
            cursor = this.database.getCollection("trial_sites")
                    .aggregate(Arrays.asList(Aggregates.match(Filters.eq("radio_category",category)),
                            Aggregates.group("$radio_sap_site",sum("count",1)),Aggregates.sort(descending("count")))).cursor();
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
    public void groupByBand(String category)
    {
        System.out.println("grouping by radio_category with value : "+category);
        MongoCursor<Document> cursor = null;
        try {
            cursor = this.database.getCollection("trial_sites")
                    .aggregate(asList(Aggregates.match(Filters.eq("radio_category",category))
                            ,Aggregates.group("$radio_sap_site",
                                    sum("total_bw","$radio_max_bw")
                                    ,max("max_bw","$radio_max_bw")),Aggregates.sort(ascending("total_bw"))))
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
    public void groupByMaxn(String site)
    {
        System.out.println("grouping docs by site with side_id: "+site);
        MongoCursor<Document> cr = null;
        try
        {
            cr = this.database.getCollection("trial_sites")
                    .aggregate(asList(Aggregates.match(Filters.eq("radio_sap_site",site)),Aggregates.group("$radio_category",firstN("first_fields","$radio_max_bw",5))))
                    .cursor();
            while(cr.hasNext())
            {
                System.out.println(cr.next().toJson());
            }
        }
        catch(Exception e)
        {
            // do nothing
        }
        finally {
            if(cr!=null)
                cr.close();
        }
    }
    public void groupByTopN(String site)
    {
        System.out.println("grouping docs by site with site_id:" + site);
        MongoCursor<Document> cursor = null;
        try
        {
            cursor = this.database.getCollection("trial_sites")
                    .aggregate(asList(Aggregates.match(Filters.eq("radio_sap_site",site)),
                            Aggregates.group("$radio_sap_site"
                                    ,topN("topN fields"
                                            ,descending("radio_max_bw")
                                            ,asList(new BsonString("$radio_category"),new BsonString("$radio_min_bw"))
                                            ,4)))).cursor();
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().toJson());
            }
        }
        catch(Exception e)
        {
            //do nothing
        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }
}
