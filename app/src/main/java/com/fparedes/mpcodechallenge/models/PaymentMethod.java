package com.fparedes.mpcodechallenge.models;

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

    public String getStatus() {
        return status;
    }
}
