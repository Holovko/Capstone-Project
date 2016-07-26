package com.holovko.kyivmommap;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;

import com.holovko.kyivmommap.model.Rubric;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Local constants
 * Created by Andrey Holovko on 7/18/16.
 */
public class Constant {
    public static final int RUBRIC_PARKS = 1;
    public static final int RUBRIC_PLAYGROUNDS = 2;
    public static final int RUBRIC_RESTAURANTS = 3;
    public static final int RUBRIC_MUSEUMS = 4;
    public static final int RUBRIC_INDOOR_PLAY_AREA = 5;

    public static List<Rubric> getListRubric() {
        ArrayList<Rubric> list = new ArrayList<>();
        list.add(new Rubric(RUBRIC_PARKS));
        list.add(new Rubric(RUBRIC_PLAYGROUNDS));
        list.add(new Rubric(RUBRIC_RESTAURANTS));
        list.add(new Rubric(RUBRIC_MUSEUMS));
        list.add(new Rubric(RUBRIC_INDOOR_PLAY_AREA));
        return list;
    }

    @StringRes
    public static int getNameByType(@RubricType int type) {
        switch (type) {
            case RUBRIC_INDOOR_PLAY_AREA:
                return R.string.title_indoor_play_areas;
            case RUBRIC_MUSEUMS:
                return R.string.title_museums;
            case RUBRIC_PARKS:
                return R.string.title_parks;
            case RUBRIC_PLAYGROUNDS:
                return R.string.title_playgrounds;
            case RUBRIC_RESTAURANTS:
                return R.string.title_restaurants;
        }
        throw new IllegalArgumentException();
    }

    public static String getNodeByType(@RubricType int type) {
        switch (type) {
            case RUBRIC_INDOOR_PLAY_AREA:
                return "play_area";
            case RUBRIC_MUSEUMS:
                return "museums";
            case RUBRIC_PARKS:
                return "parks";
            case RUBRIC_PLAYGROUNDS:
                return "play_grounds";
            case RUBRIC_RESTAURANTS:
                return "restaurants";
        }
        throw new IllegalArgumentException();
    }

    @DrawableRes
    public static int getBackGroundByRubric(@RubricType int type) {
        switch (type) {
            case RUBRIC_INDOOR_PLAY_AREA:
                return R.drawable.park;
            case RUBRIC_MUSEUMS:
                return R.drawable.park;
            case RUBRIC_PARKS:
                return R.drawable.park;
            case RUBRIC_PLAYGROUNDS:
                return R.drawable.park;
            case RUBRIC_RESTAURANTS:
                return R.drawable.park;
        }
        throw new IllegalArgumentException();
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RUBRIC_PARKS, RUBRIC_PLAYGROUNDS, RUBRIC_RESTAURANTS,
            RUBRIC_MUSEUMS, RUBRIC_INDOOR_PLAY_AREA})
    public @interface RubricType {
    }
}
