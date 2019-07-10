package com.fparedes.mpcodechallenge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fparedes.mpcodechallenge.adapters.PaymentMethodsAdapter;
import com.fparedes.mpcodechallenge.api.ApiClient;
import com.fparedes.mpcodechallenge.api.response.ApiResponseListener;
import com.fparedes.mpcodechallenge.models.CardIssuers;
import com.fparedes.mpcodechallenge.models.PaymentMethod;
import com.fparedes.mpcodechallenge.models.PaymentType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentMethodsActivity extends AppCompatActivity
        implements PaymentMethodsAdapter.OnItemSelectedListener {

    private CompositeSubscription compSub = new CompositeSubscription();

    @BindView(R.id.payments_recycler_view)
    RecyclerView paymentsList;

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
                adapter.setPaymentMethods(paymentMethods);
            }

            @Override
            public void onFailed(Throwable throwable) {
                somethingWentWrong(throwable);
            }
        });
    }

    @Override
    public void onItemSelected(PaymentMethod paymentMethod) {
        ApiClient.getCardIssuers(compSub, paymentMethod.getId(), new ApiResponseListener<List<CardIssuers>>() {

            @Override
            public void onSuccess(@NonNull List<CardIssuers> cardIssuers) {
                cardIssuers.size();
            }

            @Override
            public void onFailed(Throwable throwable) {
                somethingWentWrong(throwable);
            }
        });
    }

    private void somethingWentWrong(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(PaymentMethodsActivity.this, "Algo salio mal!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compSub.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compSub.unsubscribe();
    }
}
