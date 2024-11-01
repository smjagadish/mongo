package org.FileProcessor;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSDownloadOptions;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class reader {
    private MongoClient client;
    private MongoDatabase database;
    public reader(MongoClient client)
    {
        this.client = client;
        this.database = this.client.getDatabase("local");
    }
    public void downloadFile(String filename)
    {
        System.out.println("downloading file :"+filename);
        GridFSBucket bucket = GridFSBuckets.create(database,"lorem_bucket");
        GridFSDownloadOptions options = new GridFSDownloadOptions().revision(0);
        try
        {
            FileOutputStream stream = new FileOutputStream("C:\\Users\\esmjaga\\mongodb_poc\\mongo\\playground\\src\\main\\java\\org\\FileProcessor\\data_dwnld.txt");
            bucket.downloadToStream(filename,stream);
            stream.flush();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    public void streamFile(String filename)
    {
        System.out.println("streaming file:"+filename);
        GridFSBucket bucket = GridFSBuckets.create(database,"lorem_bucket");
        try
        {
            GridFSDownloadStream stream = bucket.openDownloadStream(filename);
            int len = (int) stream.getGridFSFile().getLength();
            byte[] b_arr;
            b_arr = stream.readAllBytes();
            System.out.println(new String(b_arr, StandardCharsets.UTF_8));

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
