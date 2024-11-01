package org.DatabaseEncryptedClient;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.DataImpl.*;
import org.DataModel.radioDeploy;
import org.DatabaseListeners.dbCommandListener;
import org.DatabaseListeners.dbConnectionPoolListener;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

//client for encryption support
public class encryptionClient {
    public MongoClient client;
    private static CodecProvider pojoCodecProvider;
    private static CodecRegistry pojoCodecRegistry;
    public static MongoClientSettings getSettings()
    {
        String connectionString = "mongodb://127.0.0.1:27017/";
        // needed for working with pojo's as opposed to generic document class
        /*
        ClassModel<legacyRadio> lr = ClassModel.builder(legacyRadio.class).enableDiscriminator(true).build();
        ClassModel<radioDeploy> rd = ClassModel.builder(radioDeploy.class).enableDiscriminator(true).build();
        ClassModel<radioEss> ress = ClassModel.builder(radioEss.class).enableDiscriminator(true).build();
        ClassModel<radioSuMimo> rsumimo = ClassModel.builder(radioSuMimo.class).enableDiscriminator(true).build();
        ClassModel<stores> stores = ClassModel.builder(org.DataImpl.stores.class).build();
        ClassModel<parks> parks = ClassModel.builder(org.DataImpl.parks.class).build();
        ClassModel<orders> orders = ClassModel.builder(org.DataImpl.orders.class).build();
        ClassModel<customers> customers = ClassModel.builder(org.DataImpl.customers.class).build();
        ClassModel<employees> employees = ClassModel.builder(org.DataImpl.employees.class).build();
        ClassModel<userTracker> userTracker = ClassModel.builder(org.DataImpl.userTracker.class).build();
        ClassModel<sampleColl> sampleCollection = ClassModel.builder(sampleColl.class).build();
        ClassModel<arrayOpsColl> arrayOpsCollection = ClassModel.builder(arrayOpsColl.class).build();
        ClassModel<arrayOpsColl.arrayOpsElement> arrayOpsElementClassModel = ClassModel.builder(arrayOpsColl.arrayOpsElement.class).build();
        */

        pojoCodecProvider = PojoCodecProvider.builder().automatic(true)
                .build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings settings = MongoClientSettings.builder()
                // .addCommandListener(new dbCommandListener()) // cmd listener for update commands
                .applyConnectionString(new ConnectionString(connectionString))
                // . applyToConnectionPoolSettings(builder -> builder.addConnectionPoolListener(new dbConnectionPoolListener()))
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .codecRegistry(pojoCodecRegistry)
                .build();
        return settings;
    }
    private encryptionClient()
    {

        // this client will have an internal connection pool with default max size of 100 . no need to create a separate connection pool and/or manage it
        // closing the client will 'release' idle and in-use sockets(connections). do this when you no longer need to work with db
        // multiple options exposed by the driver to control various settings if required
        client = MongoClients.create(getSettings());
    }
    public MongoClient getClient()
    {
        return this.client;
    }
    public static class encryptionClientHelper {
        private static final encryptionClient client = new encryptionClient();
        public static MongoClient getEncryptionClient()
        {
            return client.getClient();
        }
    }
}
