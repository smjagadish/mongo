package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonDateTime;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class arrayOpsColl {
    @BsonIgnore
    private ObjectId _id;
    @BsonProperty("arrayField")
    private List<arrayOpsElement> elements;
    @Builder
    public static class arrayOpsElement{
        @BsonProperty("stringArray")
        private List<String> stringArray;
        @BsonProperty("integerField")
        private int integerField;
        @BsonProperty("dateTimeField")
        private BsonDateTime dateTimeField;
    }
}
