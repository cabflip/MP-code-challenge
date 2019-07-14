package com.fparedes.mpcodechallenge.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fparedes on 13/7/2019
 */
public class Installment {

    private int installments;
    @SerializedName("installment_rate")
    private double installmentRate;
    private List<String> labels;
    @SerializedName("recommended_message")
    private String recommendedMessage;
    @SerializedName("installment_amount")
    private double installmentAmount;
    @SerializedName("total_amount")
    private double totalAmount;


    public int getInstallments() {
        return installments;
    }

    public double getInstallmentRate() {
        return installmentRate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }


}
