package com.example.ashwanigupta.moh2go;

/**
 * Created by ashwani gupta on 26-08-2017.
 */

public class TutorialItem {

    String category;
    String name;
    String link;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TutorialItem(String category, String name, String link) {
        this.category = category;
        this.name = name;
        this.link = link;
    }

    public String getLink() {

        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
