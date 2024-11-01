package org.DatabaseEncryptionProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class masterKeyProvider {
    private MongoClient client;
    public masterKeyProvider(MongoClient client)
    {
        this.client = client;
    }
    private byte[] generateMasterKey()
    {
        byte[] mkey = new byte[96];
        SecureRandom random = new SecureRandom();
        random.nextBytes(mkey);
        return mkey;

    }
    public Map<String,Map<String,Object>> configureKeyVault()
    {
        MongoDatabase kvdb = client.getDatabase("encryption");
        MongoCollection<Document> doc = kvdb.getCollection("__keyVault");
        Map<String,Object> localprov = new HashMap<>();
        localprov.put("key",generateMasterKey());
        Map<String,Map<String,Object>> keyprov = new HashMap<>();
        keyprov.put("local",localprov);
        return keyprov;

    }
}
