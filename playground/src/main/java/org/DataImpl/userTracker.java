package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.Binary;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userTracker {
    @BsonProperty("permissions")
    private Binary permissions;

    @BsonProperty("username")
    private String username;

    @BsonProperty("active")
    private Boolean active;
}
