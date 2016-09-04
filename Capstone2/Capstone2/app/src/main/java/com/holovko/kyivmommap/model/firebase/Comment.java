package com.holovko.kyivmommap.model.firebase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

/**
 * Autovalue for comments
 * Created by Andrey Holovko on 8/9/16.
 */

@AutoValue
public abstract class Comment {
    @JsonProperty("approved") public abstract boolean approved();
    @JsonProperty("name") public abstract String name();
    @JsonProperty("comment") public abstract String comment();
    @JsonProperty("rank") public abstract float rank();

    @JsonCreator
    public static Comment create(
            @JsonProperty("approved") boolean approved,
            @JsonProperty("name")  String name,
            @JsonProperty("comment") String comment,
            @JsonProperty("rank") float rank) {
        return new AutoValue_Comment(approved,name,comment,rank);
    }
}
