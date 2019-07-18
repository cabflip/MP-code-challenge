package com.fparedes.mpcodechallenge.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentMethod extends PaymentMethodBase {

    private String id;

    @SerializedName("payment_type_id")
    private String paymentTypeId;
    private String status;
    @SerializedName("deferred_capture")
    private String deferredCapture;

    public String getId() {
        return id;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public String getPaymentTypeName() {
        if (paymentTypeId.contains("credit"))
            return "Tarjeta de crédito";

        if (paymentTypeId.contains("debit"))
            return "Tarjeta de débito";

        return paymentTypeId;
    }

    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return getPaymentTypeName() + " " + name;
    }
}
