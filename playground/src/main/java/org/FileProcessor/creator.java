package org.FileProcessor;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.FileInputStream;
import java.io.InputStream;

public class creator {
    private MongoClient client;
    private MongoDatabase database;

    public creator (MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void createFile(int unique_id)
    {
        System.out.println("adding a file using gridFS");
        GridFSBucket bucket = GridFSBuckets.create(database,"lorem_bucket");
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(10)
                .metadata(new Document("key_id",unique_id));
        try {
            InputStream inputStream = new FileInputStream("C:\\Users\\esmjaga\\mongodb_poc\\mongo\\playground\\src\\main\\java\\org\\FileProcessor\\data.txt");
            ObjectId id = bucket.uploadFromStream("mystream",inputStream,options);
            System.out.println("added file with object id:"+ id.toHexString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void readFileMetadata()
    {
        System.out.println("reading  file metadata prior to fetch  using gridFS");
        GridFSBucket bucket = GridFSBuckets.create(database,"lorem_bucket");
        bucket.find(Filters.empty())
                .forEach(c-> System.out.println());

    }
}
