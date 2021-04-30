package org.challenge.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class Database {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<PictureDBO> mainCollection;
    private static final CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));


    public static void init(){
        mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(codecRegistry).build());
        database = mongoClient.getDatabase("pictures");
        database = database.withCodecRegistry(codecRegistry);
        mainCollection = database.getCollection("picture_collection", PictureDBO.class);

    }

    public static void addPictureLabels(String picture, Set<String> labels){
        PictureDBO dbObject = new PictureDBO();
        dbObject.setFileName(picture);
        dbObject.setLabels(labels);
        dbObject.set_id(UUID.randomUUID().toString());
        mainCollection.insertOne(dbObject);
    }

    public static void insertOne(PictureDBO toInsert){
        mainCollection.insertOne(toInsert);
    }

    public static MongoCollection<PictureDBO> getCollection(){
        return mainCollection;
    }

    public static List<String> getAllWithLabel(String label){
        MongoCursor<PictureDBO> cursor = Database.getCollection().find(Filters.all("labels", label)).cursor();
        List<String> toReturn = new ArrayList<>();
        while (cursor.hasNext()){
            toReturn.add(cursor.next().getFileName());
        }
        return toReturn;

    }

}
