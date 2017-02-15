package com.holovko.kyivmommap.ui.add_new;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddPlaceActivityFragment extends Fragment implements AddView, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.ll_thanks)
    LinearLayout mLlThanks;
    private AddPresenter mPresent;
    private GoogleMap mGoogleMap;

    public AddPlaceActivityFragment() {
    }

    public static AddPlaceActivityFragment newInstance() {
        return new AddPlaceActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        ButterKnife.bind(this, view);
        mEtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mPresent != null) {
                    mPresent.userChangedTitle(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mPresent != null) {
                    mPresent.userChangedDescription(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constant.KYIV_LATITUDE, Constant.KYIV_LONGTITUDE), 14.0f));
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().
                position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mPresent.userSelectedCoords(latLng);
    }

    @Override
    public void setPresenter(AddPresenter presenter) {
        mPresent = presenter;
    }

    @Override
    public void showErrorMessage(int errorType) {
        @StringRes int msg = 0;
        switch (errorType){
            case AddPresenter.ERROR_NO_COORDS:
                msg = R.string.msg_choose_coords;
                break;
            case AddPresenter.ERROR_NO_TITLE:
                msg = R.string.msg_add_title;
                break;
            case AddPresenter.ERROR_NO_DESCRIPTION:
                msg = R.string.msg_add_description;
                break;
        }
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("HardwareIds")
    @Override
    public String getDeviceID() {
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void showThanks() {
        mLlThanks.setVisibility(View.VISIBLE);
        ((AddPlaceActivity)getActivity()).setFabVisibility(false);
    }
}
