package org.challenge.database;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.*;

@Data
@Accessors(chain = true)
public class PictureDBO {

    @BsonProperty(value = "file_name")
    private String fileName;

    @BsonProperty(value = "url")
    private String url;

    @BsonProperty(value = "_id")
    private String _id;

    @BsonProperty(value = "labels")
    private Set<String> labels;


    public void addLabel(String label){
        initLabels();
        this.labels.add(label);
    }

    public void addLabels(Collection<String> labels){
        initLabels();
        for(String label: labels){
            addLabel(label);
        }
    }

    private void initLabels(){
        if(this.labels == null){
            this.labels = new HashSet<>();
        }
    }
}
