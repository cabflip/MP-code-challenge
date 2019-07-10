package com.fparedes.mpcodechallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentMethod {

    private String id;
    private String name;
    @SerializedName("payment_type_id")
    private String paymentTypeId;
    private String status;
    @SerializedName("secure_thumbnail")
    private String thumbnailSecureUrl;
    @SerializedName("thumbnail")
    private String thumbnailUrl;

//    @SerializedName("deferred_capture")
//    private String deferredCapture;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnailSecureUrl() {
        return thumbnailSecureUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
