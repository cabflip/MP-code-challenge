package com.fparedes.mpcodechallenge.api.response;


import android.support.annotation.NonNull;

/**
 * Created by fparedes on 9/7/2019
 */
public interface ApiResponseListener<T> {
    void onSuccess(@NonNull T response);
    void onFailed(Throwable throwable);
}
