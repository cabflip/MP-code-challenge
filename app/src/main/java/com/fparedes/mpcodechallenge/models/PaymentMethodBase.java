package com.fparedes.mpcodechallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fparedes on 13/7/2019
 */
public abstract class PaymentMethodBase {
    protected String name;

    @SerializedName("thumbnail")
    protected String thumbnailUrl;
    @SerializedName("secure_thumbnail")
    protected String thumbnailSecureUrl;

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getThumbnailSecureUrl() {
        return thumbnailSecureUrl;
    }
}
