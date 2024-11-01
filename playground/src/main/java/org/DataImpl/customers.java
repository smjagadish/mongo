package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class customers {
    @BsonProperty("_id")
    private String id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("email")
    private String email;
}
