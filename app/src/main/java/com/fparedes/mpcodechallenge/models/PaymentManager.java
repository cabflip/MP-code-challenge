package com.fparedes.mpcodechallenge.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentManager {

    private static final PaymentManager mInstance = new PaymentManager();

    public static PaymentManager getInstance() {
        return mInstance;
    }

    public double paymentAmount = 0.0;
    public PaymentMethod selectedPaymentMethod = null;
    public List<CardIssuer> cardIssuers = new ArrayList<>();
    @Nullable
    public CardIssuer selectedCardIssuer = null;
    public Installment selectedInstallment = null;

    private PaymentManager() {
    }

    /**
     * Some credit cards are their own Card Issuer (e.g. Cordobesa). In those cases
     *  there's no need to send that parameter (retrofit interprets null as a signal
     *  that you don't want to send the parameter).
     *
     * @return the selected card issuer ID or null if there's no Card Issuer object
     */
    public Integer getSelectedCardIssuerId() {
        if (selectedCardIssuer != null)
            return selectedCardIssuer.getId();
        else
            return null;
    }
}
