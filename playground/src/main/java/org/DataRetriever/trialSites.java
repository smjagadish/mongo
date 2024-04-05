package org.DataRetriever;

import com.mongodb.client.*;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.DataImpl.newRadio;
import org.bson.Document;
import org.bson.conversions.Bson;

public class trialSites {
    MongoClient client;

    public trialSites(MongoClient client) {
        this.client = client;
    }

    public void fetchFirstDocAsPojo() {
        newRadio first_obj = client.getDatabase("local").getCollection("trial_sites", newRadio.class).find().first();
        System.out.println("first doc only as pojo");
        System.out.println(first_obj.toString());
    }

    public void fetchFirstDoc() {
        Document doc = client.getDatabase("local").getCollection("trial_sites").find().first();
        System.out.println("fetch as doc type:");
        System.out.println(doc.toJson());
    }

    public void fetchInfoAsPojo() {
        MongoCursor<newRadio> cursor = null;
        try {
            Bson projectionFields = Projections.fields(
                    Projections.excludeId());
            MongoDatabase db = client.getDatabase("local");
            FindIterable<newRadio> iter = db.getCollection("trial_sites").find(newRadio.class)
                    .projection(projectionFields);
             cursor= iter.iterator();
            System.out.println("fetch as pojo:");
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }

        } catch (Exception e) {

        }
        finally {
            if(cursor!=null)
            cursor.close();
        }
    }
    public void fetchWithCondition(String site)
    {
        MongoCursor<newRadio> cursor = null;
        try {
            Bson doc = Filters.eq("radio_sap_site",site);
            cursor = client.getDatabase("local").getCollection("trial_sites", newRadio.class)
                    .find(doc).iterator();
            System.out.println("filtered data as pojo");
            while(cursor.hasNext())
            {
                System.out.println(cursor.next().toString());
            }
        }
        catch(Exception e)
        {

        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }
    public void getDocumentCount()
    {
        try {
            CountOptions opts = new CountOptions().hintString("_id_");
           long count = client.getDatabase("local")
                   .getCollection("trial_sites")
                   .countDocuments(new Document(),opts);
           System.out.println("count of docs without filter is:"+ count);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void getSiteDocumentCount(String site)
    {
        try {
            Bson doc = Filters.eq("radio_sap_site",site);
            long f_count = client.getDatabase("local")
                    .getCollection("trial_sites")
                    .countDocuments(doc);
            System.out.println("count of site specific docs is:"+f_count);
        }
        catch(Exception e)
        {
            // do nothing
        }
    }
}
