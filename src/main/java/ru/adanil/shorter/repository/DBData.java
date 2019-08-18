package ru.adanil.shorter.repository;

public class DBData {

    public static class Link {
        public static final String name = "LinkModel";

        enum Columns {
            LONG_LINK("long_link"), SHORT_LINK("short_link");

            public String name;

            Columns(String link) {
                this.name = link;
            }
        }

    }

    public static class LinkSequence {
        public static final String name = "LinkSequence";

        public enum Columns {
            SEQ("seq"), ID("_id");

            public String name;

            Columns(String link) {
                this.name = link;
            }
        }

        public enum Value {
            VALUE("link_id");

            public String name;

            Value(String link) {
                this.name = link;
            }
        }
    }
}
