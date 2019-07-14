package com.fparedes.mpcodechallenge.api;

import android.support.annotation.NonNull;

import com.fparedes.mpcodechallenge.api.response.ApiResponseListener;
import com.fparedes.mpcodechallenge.models.CardIssuer;
import com.fparedes.mpcodechallenge.api.response.InstallmentsResponse;
import com.fparedes.mpcodechallenge.models.PaymentMethod;
import com.fparedes.mpcodechallenge.models.PaymentType;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    public static void getPaymentMethods(CompositeSubscription compSub, @NonNull PaymentType paymentTypeFilter,
                                         @NonNull ApiResponseListener<List<PaymentMethod>> listener) {

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
                    // Sort alphabetically
                    Collections.sort(paymentMethods, (paymentMethod1, paymentMethod2) ->
                            paymentMethod1.getId().compareTo(paymentMethod2.getId()));

                    listener.onSuccess(paymentMethods);
                }, listener::onFailed));
    }

    public static void getCardIssuers(CompositeSubscription compSub, String paymentMethodId,
                                      @NonNull ApiResponseListener<List<CardIssuer>> listener) {

        if (paymentMethodId == null || paymentMethodId.isEmpty()) {
            listener.onFailed(new Throwable("PaymentMethod id cannot is null or empty"));
            return;
        }

        compSub.add(apiService.getCardIssuers(Constants.MP_API_KEY, paymentMethodId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardIssuers -> {
                    // Sort card issuers alphabetically
                    Collections.sort(cardIssuers, (cardIssuer1, cardIssuer2) ->
                            cardIssuer1.getName().compareTo(cardIssuer2.getName()));
                    listener.onSuccess(cardIssuers);
                }, listener::onFailed));
    }

    public static void getInstallments(CompositeSubscription compSub, String paymentMethodId,
                                       double paymentAmount, int issuerId,
                                       @NonNull ApiResponseListener<InstallmentsResponse> listener) {

        if (paymentMethodId == null || paymentMethodId.isEmpty()) {
            listener.onFailed(new Throwable("PaymentMethodId is null or empty"));
            return;
        }

        compSub.add(apiService.getInstallments(Constants.MP_API_KEY, paymentMethodId, paymentAmount, issuerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<InstallmentsResponse>>() {
                    @Override
                    public void call(List<InstallmentsResponse> installmentsResponse) {
                        listener.onSuccess(installmentsResponse.get(0));
                    }
                }, Throwable::printStackTrace));
    }
}
