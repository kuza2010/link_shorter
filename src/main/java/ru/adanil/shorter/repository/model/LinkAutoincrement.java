package ru.adanil.shorter.repository.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "LinkSequence")
public class LinkAutoincrement {
    public static final String COLLECTION_NAME = "LinkSequence";

    @Id
    private String id;

    private int seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


    @Override
    public String toString() {
        return "LinkAutoincrement{" +
                "id='" + id + '\'' +
                ", seq=" + seq +
                '}';
    }
}
