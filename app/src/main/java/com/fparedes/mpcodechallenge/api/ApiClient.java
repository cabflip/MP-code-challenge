package com.fparedes.mpcodechallenge.api;

import com.fparedes.mpcodechallenge.api.response.ApiResponseListener;
import com.fparedes.mpcodechallenge.models.CardIssuers;
import com.fparedes.mpcodechallenge.models.PaymentMethod;
import com.fparedes.mpcodechallenge.models.PaymentType;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.fparedes.mpcodechallenge.api.Constants.MP_API_BASE_URL;

/**
 * Created by fparedes on 9/7/2019
 */
public final class ApiClient {

    private final static long OKHTTP_CONNECTION_TIMEOUT = 30 * 1000;
    private final static long OKHTTP_READ_TIMEOUT = 30 * 1000;

    private static MPApiService apiService;

    private ApiClient() {
    }

    public static void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(OKHTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new Gson());

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MP_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(MPApiService.class);
    }

    public static void getPaymentMethods(CompositeSubscription compSub, PaymentType paymentTypeFilter,
                                         ApiResponseListener<List<PaymentMethod>> listener) {

        compSub.add(apiService.getPaymentMethods(Constants.MP_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentMethodsResponse -> {

                    String paymentFilter = "";

                    switch (paymentTypeFilter) {
                        case CREDIT_CARD:
                            paymentFilter = "credit_card";
                            break;
                        case DEBIT_CARD:
                            paymentFilter = "debit_card";
                            break;
                        case ALL:
                            listener.onSuccess(paymentMethodsResponse);
                            return;
                    }

                    if (paymentFilter.isEmpty()) {
                        listener.onSuccess(paymentMethodsResponse);
                        return;
                    }

                    List<PaymentMethod> paymentMethods = new ArrayList<>();
                    for (PaymentMethod paymentMethod : paymentMethodsResponse) {
                        if (paymentMethod.getPaymentTypeId().equalsIgnoreCase(paymentFilter))
                            paymentMethods.add(paymentMethod);

                    }
                    listener.onSuccess(paymentMethods);
                }, listener::onFailed));
    }

    public static void getCardIssuers(CompositeSubscription compSub, String paymentMethodId,
                                      ApiResponseListener<List<CardIssuers>> listener) {

        compSub.add(apiService.getCardIssuers(Constants.MP_API_KEY, paymentMethodId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSuccess, listener::onFailed));
    }
}
