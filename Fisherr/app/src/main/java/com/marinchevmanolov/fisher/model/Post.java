package com.marinchevmanolov.fisher.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private String title;
    private String description;
    private Date date;
    private List<Double> coordinates;
    private List<String> images;


    public Post(){
        this.coordinates = new ArrayList<Double>();
        this.images = new ArrayList<String>();
    }

    public Post(String title, String description, Date date, List<Double> coordinates, List<String> images) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.coordinates = coordinates;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
