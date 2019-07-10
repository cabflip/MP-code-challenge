package com.fparedes.mpcodechallenge;

import android.app.Application;

import com.fparedes.mpcodechallenge.api.ApiClient;

/**
 * Created by fparedes on 9/7/2019
 */
public class MPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.init();
    }
}
