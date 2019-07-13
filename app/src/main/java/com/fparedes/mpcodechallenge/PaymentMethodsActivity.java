package com.fparedes.mpcodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fparedes.mpcodechallenge.adapters.PaymentMethodsAdapter;
import com.fparedes.mpcodechallenge.api.ApiClient;
import com.fparedes.mpcodechallenge.api.response.ApiResponseListener;
import com.fparedes.mpcodechallenge.models.CardIssuer;
import com.fparedes.mpcodechallenge.models.PaymentManager;
import com.fparedes.mpcodechallenge.models.PaymentMethod;
import com.fparedes.mpcodechallenge.models.PaymentMethodBase;
import com.fparedes.mpcodechallenge.models.PaymentType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentMethodsActivity extends BaseActivity
        implements PaymentMethodsAdapter.OnItemSelectedListener {

    @BindView(R.id.payments_recycler_view)
    RecyclerView paymentsList;
    @BindView(R.id.loading_view)
    View loadingView;

    private PaymentMethodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        ButterKnife.bind(this);

        adapter = new PaymentMethodsAdapter(this, this);
        paymentsList.setAdapter(adapter);
        paymentsList.setLayoutManager(new LinearLayoutManager(this));
        paymentsList.setHasFixedSize(true);

        getPaymentMethods();
    }

    private void getPaymentMethods() {
        ApiClient.getPaymentMethods(compSub, PaymentType.CREDIT_CARD, new ApiResponseListener<List<PaymentMethod>>() {

            @Override
            public void onSuccess(@NonNull List<PaymentMethod> paymentMethods) {
                loading(false);

                adapter.setPaymentMethods(paymentMethods);
            }

            @Override
            public void onFailed(Throwable throwable) {
                loading(false);
                somethingWentWrong(throwable);
            }
        });
    }

    @Override
    public void onItemSelected(PaymentMethodBase paymentMethodBase) {
        loading(true);
        PaymentMethod paymentMethod = (PaymentMethod) paymentMethodBase;
        PaymentManager.getInstance().selectedPaymentMethod = paymentMethod;
        ApiClient.getCardIssuers(compSub, paymentMethod.getId(), new ApiResponseListener<List<CardIssuer>>() {

            @Override
            public void onSuccess(@NonNull List<CardIssuer> cardIssuers) {
                goToNextActivity(cardIssuers);
            }

            @Override
            public void onFailed(Throwable throwable) {
                loading(false);
                somethingWentWrong(throwable);
            }
        });
    }

    private void goToNextActivity(List<CardIssuer> cardIssuers) {
        PaymentManager paymentManager = PaymentManager.getInstance();
        paymentManager.cardIssuers = cardIssuers;

        Class nextActivity;
        if (cardIssuers.isEmpty()) {
            nextActivity = InstallmentsActivity.class;
            paymentManager.selectedCardIssuer = null;
        } else
            nextActivity = CardIssuersActivity.class;

        startActivity(new Intent(PaymentMethodsActivity.this, nextActivity));
    }

    private void somethingWentWrong(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(PaymentMethodsActivity.this, "Algo salio mal!", Toast.LENGTH_SHORT).show();
    }

    private void loading(boolean loading) {
        loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loading(false);
    }
}
