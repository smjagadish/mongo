package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class sampleColl {
    @BsonIgnore
    private BsonId _id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("age")
    private int age;

    @BsonProperty("occupation")
    private String occupation;

    @BsonProperty("createdAt")
    private BsonDateTime createdAt;

}
