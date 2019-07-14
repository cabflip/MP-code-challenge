package com.fparedes.mpcodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fparedes.mpcodechallenge.adapters.PaymentMethodsAdapter;
import com.fparedes.mpcodechallenge.models.CardIssuer;
import com.fparedes.mpcodechallenge.models.PaymentManager;
import com.fparedes.mpcodechallenge.models.PaymentMethodBase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fparedes on 10/7/2019
 */
public class CardIssuersActivity extends AppCompatActivity
        implements PaymentMethodsAdapter.OnItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.card_issuers_recycler_view)
    RecyclerView cardIssuersList;

    private PaymentManager paymentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_issuers);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        paymentManager = PaymentManager.getInstance();

        PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(this, this);
        adapter.setPaymentMethods(paymentManager.cardIssuers);

        cardIssuersList.setAdapter(adapter);
        cardIssuersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(PaymentMethodBase paymentMethodBase) {
        paymentManager.selectedCardIssuer = (CardIssuer) paymentMethodBase;
        startActivity(new Intent(CardIssuersActivity.this, InstallmentsActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }
}
