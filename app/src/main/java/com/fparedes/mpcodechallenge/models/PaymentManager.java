package com.fparedes.mpcodechallenge.models;

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
    public CardIssuer selectedCardIssuer = null;

    private PaymentManager() {
    }

}
