package com.fparedes.mpcodechallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fparedes on 10/7/2019
 */
public class CardIssuer extends PaymentMethodBase {

    private int id;

    @SerializedName("processing_mode")
    private String processingMode;
    @SerializedName("merchant_account_id")
    private int merchantAccountId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailSecureUrl() {
        return thumbnailSecureUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
