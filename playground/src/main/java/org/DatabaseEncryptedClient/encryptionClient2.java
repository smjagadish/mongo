package org.DatabaseEncryptedClient;

import com.mongodb.client.MongoClient;

// double locking pattern
public class encryptionClient2 {
    private static volatile MongoClient client = null;
    private static final Object lock = new Object();
    private encryptionClient2()
    {
      client= null; //change
    }
    public MongoClient getClient()
    {
        return this.getClient();
    }
    public static MongoClient getEncryptionClient2()
    {
        if(client==null)
        {
            synchronized (lock) {
                if (client == null) {
                    encryptionClient2 cl2 = new encryptionClient2();
                }
            }
        }

        return client;
    }

}
