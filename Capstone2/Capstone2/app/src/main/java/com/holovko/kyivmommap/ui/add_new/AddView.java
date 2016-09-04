package com.holovko.kyivmommap.ui.add_new;

/**
 * Created by Andrey Holovko on 9/4/16.
 */

public interface AddView {
    void setPresenter(AddPresenter presenter);
    void showErrorMessage(int errorType);
    String getDeviceID();
    void showThanks();
}
