package org.DatabaseEncryptionProvider;

import com.mongodb.ClientEncryptionSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.client.vault.ClientEncryptions;
import org.DatabaseEncryptedClient.encryptionClient;
import org.bson.BsonBinary;

public class dataKeyProvider {
    private masterKeyProvider mkeyprov;
    private MongoClient client;
    private ClientEncryptionSettings settings;
    private ClientEncryption encr;
    private BsonBinary dkey;
    public dataKeyProvider(MongoClient client)
    {
        this.client = client;
        this.mkeyprov = new masterKeyProvider(client);
    }
    private void configureEncryptionSettings()
    {
         this.settings = ClientEncryptionSettings
                .builder()
                .keyVaultMongoClientSettings(encryptionClient.getSettings())
                .keyVaultNamespace("encryption.__keyVault")
                .kmsProviders(this.mkeyprov.configureKeyVault())
                .build();
    }
    private void createDataKey()
    {
        try {
            configureEncryptionSettings();
            this.encr = ClientEncryptions.create(settings);
            this.dkey = encr.createDataKey("local", new DataKeyOptions());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public BsonBinary getDataKey()
    {
        return this.dkey;
    }
    public ClientEncryption getEncryption()
    {
        createDataKey();
        return this.encr;
    }
}
