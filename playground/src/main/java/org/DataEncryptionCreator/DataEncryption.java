package org.DataEncryptionCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.vault.EncryptOptions;
import org.DatabaseEncryptionProvider.dataKeyProvider;
import org.bson.BsonBinary;
import org.bson.BsonString;
import org.bson.Document;

public class DataEncryption {
    private MongoClient client;
    private MongoDatabase database;
    private dataKeyProvider dkeyProv;
    public DataEncryption(MongoClient client, dataKeyProvider dkeyProv)
    {
        this.client = client;
        this.dkeyProv = dkeyProv;
        this.database = this.client.getDatabase("local");
    }
    public void doEncryption()
    {
        System.out.println("adding encrypted field");
        BsonBinary encField = dkeyProv.getEncryption()
                .encrypt(new BsonString("supersecret"),
                        new EncryptOptions("AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic")
                                .keyId(dkeyProv.getDataKey()));
        MongoCollection<Document> collection = this.database.getCollection("encr_coll");
        collection.insertOne(new Document()
                .append("encryptedFiled",encField));

    }
}
