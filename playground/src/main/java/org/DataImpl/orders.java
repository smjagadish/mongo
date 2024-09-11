package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class orders {
    @BsonProperty("_id")
    private int id;

    @BsonProperty("order_number")
    private String ord_num;

    @BsonProperty("customer_id")
    private String cust_id;
}
