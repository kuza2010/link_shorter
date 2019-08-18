package ru.adanil.shorter.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Link")
public class Link {

    @Id
    private String id;

    @Field(value = "long_link")
    private String longLink;

    @Field(value = "short_link")
    private String shortLink;

    public Link() {
    }

    public Link(String id, String longLink, String shortLink) {
        this.id = id;
        this.longLink = longLink;
        this.shortLink = shortLink;
    }

    public Link(String longLink, String shortLink) {
        this.longLink = longLink;
        this.shortLink = shortLink;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    @Override
    public String toString() {
        return "LinkModel{" +
                "id='" + id + '\'' +
                ", longLink='" + longLink + '\'' +
                '}';
    }
}