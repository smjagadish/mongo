package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class stores {

    @BsonIgnore()
    @BsonProperty("_id") // this will supersede the bson ignore annotation
    public int s_id;

    @BsonProperty("name")
    public String s_name;

    @BsonProperty("description")
    public String s_desc;

    //@BsonIgnore()
    //public int rating;
}
