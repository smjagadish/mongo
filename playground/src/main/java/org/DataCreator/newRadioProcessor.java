package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.DataImpl.newRadio;
import org.DataImpl.nrCategory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class newRadioProcessor {
    private MongoClient client;
    private MongoDatabase db;
    public newRadioProcessor(MongoClient client)
    {
        this.client = client;
        this.db = client.getDatabase("local");
    }
    public void createCollection()
    {
        CreateCollectionOptions copts = new CreateCollectionOptions();
        // copts can be used to pass additional options for the collection. some eg: include capped collections , field encryption etc.
        // the ref is not used currently , but will be adapted as the app evolves
        db.createCollection("trial_sites");
    }
    public void addRadio(String sap, int max_bw , int min_bw , nrCategory category)
    {
        newRadio radio = newRadio.builder()
                .radio_sap_site(sap)
                .radio_max_bw(max_bw)
                .radio_min_bw(min_bw)
                .radio_category(category)
                .build();
        Document doc = new Document("_id",new ObjectId())
                .append("radio_sap_site",radio.getRadio_sap_site())
                .append("radio_max_bw",radio.getRadio_max_bw())
                .append("radio_min_bw",radio.getRadio_min_bw())
                .append("radio_category",radio.getRadio_category().name())
                .append("sector_count",radio.getRadio_category().getSector());
        db.getCollection("trial_sites").insertOne(doc);
    }
    public void addRadioPojo(String sap, int max_bw, int min_bw , nrCategory category)
    {
        newRadio radio = new newRadio(sap,max_bw,min_bw,category);
        db.getCollection("trial_sites", newRadio.class).insertOne(radio);
    }
    public void addMultipleRadios(List<newRadio> radioList)
    {
        try{
            System.out.println("multiple radio addition");
            db.getCollection("trial_sites", newRadio.class)
                    .insertMany(radioList);
        }
        catch (Exception e)
        {
            // do nothing
        }
    }
    public void doBulkWrite()
    {
        try {
            Bson doc = Filters.eq("radio_sap_site","DesMoines");
            Bson update = Updates.set("radio_min_bw",12);
            BulkWriteOptions options = new BulkWriteOptions().ordered(false);
            db.getCollection("trial_sites", newRadio.class)
                    .bulkWrite(Arrays.asList(
                            new InsertOneModel<>(new newRadio("DesMoines",50,30,nrCategory.lowband))
                    ),options);
        }
        catch(Exception e)
        {

        }
    }


}
