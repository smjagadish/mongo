package org.DataModel;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "_cls_radio")
public interface radioDeploy {
    default void printSupportedVariants()
    {
        System.out.println("supported variants are : ess , endc , sa , nsa , su-mimo and mu-mimo");
    }
    static void doNothing()
    {
        System.out.println("do nothing impl");
    }
    void configure(String mode, int max_bw, int min_bw , double threshold);
    void printMode();
}
