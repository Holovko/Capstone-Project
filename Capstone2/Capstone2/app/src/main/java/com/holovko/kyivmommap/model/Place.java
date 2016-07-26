package com.holovko.kyivmommap.model;

/**
 * Model for place
 * Created by Andrey Holovko on 7/26/16.
 */
public class Place {
    public Place(boolean approved, String title, String description, double latitude, double longitude, float rank) {
        this.approved = approved;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rank = rank;
    }

    public boolean approved;
    public String title;
    public String description;
    public double latitude;
    public double longitude;
    public float rank;
}
