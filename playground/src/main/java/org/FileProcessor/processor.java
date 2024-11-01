package org.FileProcessor;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

public class processor {
    private MongoClient client;
    private MongoDatabase database;
    public processor(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void renameFile(String oldName, String newName)
    {
        System.out.println("renaming file to:"+newName);
        GridFSBucket bucket = GridFSBuckets.create(database, "lorem_bucket");
        try {
            String oid = bucket.find(Filters.eq("filename", oldName))
                    .cursor()
                    .next()
                    .getId()
                    .asObjectId()
                    .getValue()
                    .toHexString();
            bucket.rename(new ObjectId(oid), newName);
        }
        catch(Exception e)
        {
            //
        }
        checkRename(bucket,newName);
    }
    private void checkRename(GridFSBucket bucket , String newName)
    {
        String name = bucket.find(Filters.eq("filename",newName))
                .cursor()
                .next()
                .getFilename();
        if (name.equals(newName))
            System.out.println("file renamed");

    }
}
