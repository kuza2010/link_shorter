package ru.adanil.shorter.controller.model;

public class Link {

    private String longLink;
    private String shortLink;

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String link) {
        this.longLink = link;
    }


}
