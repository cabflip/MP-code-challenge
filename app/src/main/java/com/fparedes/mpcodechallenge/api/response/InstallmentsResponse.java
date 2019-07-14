package com.fparedes.mpcodechallenge.api.response;

import com.fparedes.mpcodechallenge.models.CardIssuer;
import com.fparedes.mpcodechallenge.models.Installment;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fparedes on 13/7/2019
 */
public class InstallmentsResponse {

    @SerializedName("payment_method_id")
    private String paymentMethodId;
    @SerializedName("payment_type_id")
    private String paymentTypeId;
    private CardIssuer issuer;
    @SerializedName("processing_mode")
    private String processingMode;
    @SerializedName("merchant_account_id")
    private String merchantAccountId;
    @SerializedName("payer_costs")
    private List<Installment> installments = new ArrayList<>();


    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public CardIssuer getIssuer() {
        return issuer;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public List<Installment> getInstallments() {
        return installments;
    }
}
