package org.DataImpl;

import com.mongodb.client.model.geojson.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class locations {
    @BsonProperty("city")
    private String city_proper;
    @BsonProperty("coord")
    private Point geo_coord;
    @BsonProperty("type")
    private String loc_type;
}
