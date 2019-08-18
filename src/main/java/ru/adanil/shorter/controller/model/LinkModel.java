package ru.adanil.shorter.controller.model;

public class LinkModel {

    private String longLink;
    private String shortLink;

    public String getShort() {
        return shortLink;
    }

    public void setShort(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getLong() {
        return longLink;
    }

    public void setLong(String link) {
        this.longLink = link;
    }


}
