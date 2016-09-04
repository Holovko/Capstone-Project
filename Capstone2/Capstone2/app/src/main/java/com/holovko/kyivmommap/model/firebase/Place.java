package com.holovko.kyivmommap.model.firebase;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

/**
 * Avtovalue class for places
 * Created by Andrey Holovko on 8/2/16.
 */

@AutoValue public abstract class Place implements Parcelable {
    @JsonProperty("approved") public abstract boolean approved();
    @JsonProperty("title") public abstract String title();
    @JsonProperty("description") public abstract String description();
    @JsonProperty("pic") public abstract String pic();
    @JsonProperty("latitude") public abstract double latitude();
    @JsonProperty("longitude") public abstract double longitude();
    @JsonProperty("rank") public abstract float rank();

    @JsonCreator
    public static Place create(
            @JsonProperty("approved") boolean approved,
            @JsonProperty("title")  String title,
            @JsonProperty("description") String description,
            @JsonProperty("pic")  String pic,
            @JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude,
            @JsonProperty("rank") float rank){
        return new AutoValue_Place(approved, title, description, pic, latitude, longitude, rank);
    }
}
