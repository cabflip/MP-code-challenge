package com.fparedes.mpcodechallenge.api;

import com.fparedes.mpcodechallenge.models.CardIssuer;
import com.fparedes.mpcodechallenge.api.response.InstallmentsResponse;
import com.fparedes.mpcodechallenge.models.PaymentMethod;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fparedes on 9/7/2019
 */
public interface MPApiService {

    String GET_PAYMENT_METHODS = "v1/payment_methods";
    String GET_CARD_ISSUERS = GET_PAYMENT_METHODS + "/card_issuers";
    String GET_INSTALLMENTS = GET_PAYMENT_METHODS + "/installments";

    @GET(GET_PAYMENT_METHODS)
    Observable<List<PaymentMethod>> getPaymentMethods(
            @Query("public_key") String publicKey);

    @GET(GET_CARD_ISSUERS)
    Observable<List<CardIssuer>> getCardIssuers(
            @Query("public_key") String publicKey,
            @Query("payment_method_id") String paymentMethodId);

    @GET(GET_INSTALLMENTS)
    Observable<List<InstallmentsResponse>> getInstallments(
            @Query("public_key") String publicKey,
            @Query("payment_method_id") String paymentMethodId,
            @Query("amount") double paymentAmount,
            @Query("issuer.id") Integer issuerId);
}
