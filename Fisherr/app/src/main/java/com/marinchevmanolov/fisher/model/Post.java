package com.marinchevmanolov.fisher.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String title;
    private String description;
    private String date;
    private double[] coordinates;
    private List<String> images;


    public Post(){
        this.coordinates = new double[2];
        this.images = new ArrayList<String>();
    }

    public Post(String title, String description, String date, double[] coordinates, List<String> images) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.coordinates = coordinates;
        this.images = images;
    }
}
