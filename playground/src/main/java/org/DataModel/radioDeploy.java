package org.DataModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@NoArgsConstructor
@BsonDiscriminator(key = "_cls_radio",value="base_impl")
public abstract class radioDeploy {
    void printSupportedVariants()
    {
        System.out.println("supported variants are : ess , endc , sa , nsa , su-mimo and mu-mimo");
    }
    static void doNothing()
    {
        System.out.println("do nothing impl");
    }


    public abstract void configure(String mode, int max_bw, int min_bw, double threshold);



    public abstract void printMode();
}
