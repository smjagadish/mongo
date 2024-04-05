package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.DataModel.radioDeploy;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// uncomment below key if you want to include type info to be added in the coll and used during fetch
// with same key but diff value, i can have a completely diff class to serialize into for instance
@BsonDiscriminator(value="RegisteredUser", key="_cls")
public class legacyRadio {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String serial_num;

    // serialize and deserialize with prop name as site
    @BsonProperty("site")
    private String site_name_info;

    // serialize and deserialize with prop name as generation
    @BsonProperty("generation")
    private int gen;

    // this property is neither persisted nor read during the db ops
    @BsonIgnore()
    private int bw_info;

    @BsonProperty("radioconf")
    private radioDeploy rdeploy;
}
