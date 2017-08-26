package com.example.ashwanigupta.moh2go;

/**
 * Created by ashwani gupta on 25-08-2017.
 */

public class Post {

    String name;
    String category;
    String description;
    String image;
    String email;
    long phone;
    int rating;
    String password;

    public Post(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Post(String name, String category, String description, String image, String email, long phone, int rating, String password) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return image;
    }

    public void setImg(String img) {
        this.image = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
