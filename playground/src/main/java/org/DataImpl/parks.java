package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class parks {
   /* @BsonRepresentation(BsonType.OBJECT_ID)
    @BsonIgnore
    private String _id;
  */
    @BsonProperty("city")
    private String city;

    @BsonProperty("name")
    private String name;

    @BsonProperty("precincts")
    private List<Integer> locations;
}
