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
public class employees {
    @BsonProperty("name")
    private String name;
    @BsonProperty("managerId")
    private int managerId;
}
