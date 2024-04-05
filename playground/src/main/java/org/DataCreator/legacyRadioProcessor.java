package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.DataImpl.legacyRadio;
import org.DataModel.radioDeploy;
import org.bson.types.ObjectId;

public class legacyRadioProcessor {
    private MongoClient client;
    private MongoDatabase db;
    public legacyRadioProcessor(MongoClient client)
    {
        this.client = client;
        this.db = client.getDatabase("local");
    }
    public void createCollection()
    {
        this.db.createCollection("legacy_sites");
    }
    public void createSite(String id, String site, int gen , int bw , radioDeploy deploy)
    {
        legacyRadio lr = legacyRadio.builder()
                .serial_num(new ObjectId().toHexString())
                .bw_info(bw)
                .site_name_info(site)
                .gen(gen)
                .rdeploy(deploy)
                .build();
        try {
            db.getCollection("legacy_sites", legacyRadio.class)
                    .insertOne(lr);
        }
        catch(Exception e)
        {
           System.out.println(e);
        }

    }
}
