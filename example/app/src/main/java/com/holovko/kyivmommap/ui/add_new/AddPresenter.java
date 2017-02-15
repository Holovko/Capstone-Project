package com.holovko.kyivmommap.ui.add_new;

import com.google.android.gms.maps.model.LatLng;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataRepository;

/**
 * Created by Andrey Holovko on 9/4/16.
 */

public class AddPresenter {
    public static final int ERROR_NO_COORDS = 0;
    public static final int ERROR_NO_TITLE = 1;
    public static final int ERROR_NO_DESCRIPTION = 2;
    private final DataRepository mDataRepositoy;
    private final AddView mView;
    private LatLng mCoords;
    private String mTitle = "";
    private String mDescription = "";

    public AddPresenter(DataRepository dataRepository, AddView view) {
        mView = view;
        mDataRepositoy = dataRepository;
        view.setPresenter(this);
    }

    public void userSelectedCoords(LatLng latLng) {
        mCoords = latLng;
    }


    //TODO in second release change type when add
    public void initCreatePlace()
    {
        String uid = mView.getDeviceID();

        if(mCoords ==null){
            mView.showErrorMessage(ERROR_NO_COORDS);
            return;
        }
        if(mTitle.trim().isEmpty()){
            mView.showErrorMessage(ERROR_NO_TITLE);
            return;
        }
        if(mDescription.trim().isEmpty()){
            mView.showErrorMessage(ERROR_NO_DESCRIPTION);
            return;
        }
        mDataRepositoy.addPlace(
                uid,
                Constant.RUBRIC_PARKS,
                false,
                mTitle,
                mDescription,
                "",
                mCoords.latitude,
                mCoords.longitude
        );

        mView.showThanks();
    }

    public void userChangedTitle(String title) {
        mTitle = title;
    }

    public void userChangedDescription(String description) {
        mDescription = description;
    }
}
