package org.DataCreator;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.DataImpl.customers;
import org.DataImpl.orders;

import java.util.List;

@Data
@Builder
public class lookupAggCreator {
    private MongoClient client;

    public lookupAggCreator(MongoClient client)
    {
        this.client = client;

    }
    public void createOrders(List<orders> ordersList)
    {
        try {
            MongoDatabase database = this.client.getDatabase("local");
            database.createCollection("orders");
            ordersList.forEach(e-> database.getCollection("orders", orders.class)
                    .insertOne(e));
        }
        catch(Exception e)
        {
            // do nothing
        }
    }
    public void createCustomers(List<customers> customersList)
    {
        try {
            MongoDatabase database = this.client.getDatabase("local");
            database.createCollection("customers");
            customersList.forEach(e->database.getCollection("customers", customers.class)
                    .insertOne(e));

        }
        catch(Exception e)
        {
            // do nothing
        }
    }
}
