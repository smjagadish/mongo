package org.DataRepo;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.DataImpl.legacyRadio;
import org.DataImpl.radioEss;
import org.DataImpl.radioSuMimo;
import org.DataModel.radioDeploy;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Client {
    private String connectionString;
    private MongoClient mc;
    private static CodecProvider pojoCodecProvider;
    private static CodecRegistry pojoCodecRegistry;
    private Client()
    {
        connectionString = "mongodb://127.0.0.1:27017/";
        // needed for working with pojo's as opposed to generic document class
        ClassModel<legacyRadio> lr = ClassModel.builder(legacyRadio.class).enableDiscriminator(true).build();
        ClassModel<radioDeploy> rd = ClassModel.builder(radioDeploy.class).enableDiscriminator(true).build();
        ClassModel<radioEss> ress = ClassModel.builder(radioEss.class).enableDiscriminator(true).build();
        ClassModel<radioSuMimo> rsumimo = ClassModel.builder(radioSuMimo.class).enableDiscriminator(true).build();

        pojoCodecProvider = PojoCodecProvider.builder().automatic(true)
                        .register(lr,rd,ress,rsumimo)
                .build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
                .codecRegistry(pojoCodecRegistry)
            .build();
        // this client will have an internal connection pool with default max size of 100 . no need to create a separate connection pool and/or manage it
        // closing the client will 'release' idle and in-use sockets(connections). do this when you no longer need to work with db
        // multiple options exposed by the driver to control various settings if required
        mc = MongoClients.create(settings);
    }
    public MongoClient getClient()
    {
        return mc;
    }
    private static class ClientHelper {
        private static final Client client = new Client();
    }
        public static Client getClientHelper() {
            return ClientHelper.client;
            }
    public static CodecRegistry getPojoCodecRegistry()
    {
        return pojoCodecRegistry;
    }

}
