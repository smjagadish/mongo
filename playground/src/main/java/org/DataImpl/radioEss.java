package org.DataImpl;

import lombok.*;
import org.DataModel.radioDeploy;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@BsonDiscriminator(value="radioEss",key="_cls_radio")
public class radioEss extends radioDeploy {
    private String mode;
    private int max_bw;
    private int min_bw;
    private double threshold;
    @Override
    public void configure(String mode, int max_bw, int min_bw, double threshold) {
      this.mode = mode;
      this.max_bw = max_bw;
      this.min_bw = min_bw;
      this.threshold = threshold;
    }

    @Override
    public void printMode() {
        System.out.println("the radio mode is ess");
    }
}
