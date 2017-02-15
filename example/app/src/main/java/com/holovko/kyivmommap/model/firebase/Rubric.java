package com.holovko.kyivmommap.model.firebase;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.Constant.*;

/**
 * Class helper for CardView
 * Created by Andrey Holovko on 7/19/16.
 */
public class Rubric {
    private final int type;
    @DrawableRes
    private int background;
    @StringRes
    private int name;
    public Rubric(@RubricType int type) {
        this.type = type;
        this.background = Constant.getBackGroundByRubric(type);
        this.name = Constant.getNameByType(type);
    }

    public int getType() {
        return type;
    }

    public int getBackground() {
        return background;
    }

    public int getName() {
        return name;
    }
}