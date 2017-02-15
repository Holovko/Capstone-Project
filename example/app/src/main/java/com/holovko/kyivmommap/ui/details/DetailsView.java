package com.holovko.kyivmommap.ui.details;

import java.util.List;

/**
 * Created by Andrey Holovko on 9/3/16.
 */

public interface DetailsView {
    void initView(String pic, String title, String description, float rank, boolean isInFavouriteList);
    void showPhotos(List<String> urls);
    void showSuccessAddedToFavourite();
    void showSuccessDeletedFromFavourite();
    void setFabWasAddedToFavourite(boolean isAddedToFavourite);
    void setPresenter(DetailsPresenter presenter);
}
