package com.fparedes.mpcodechallenge;

import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by fparedes on 13/7/2019
 */
public class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription compSub = new CompositeSubscription();

    @Override
    protected void onStop() {
        super.onStop();
        compSub.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compSub.unsubscribe();
    }
}
