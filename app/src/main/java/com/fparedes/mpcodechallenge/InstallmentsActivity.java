package com.fparedes.mpcodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fparedes.mpcodechallenge.adapters.InstallmentsAdapter;
import com.fparedes.mpcodechallenge.api.ApiClient;
import com.fparedes.mpcodechallenge.api.Constants;
import com.fparedes.mpcodechallenge.api.response.ApiResponseListener;
import com.fparedes.mpcodechallenge.api.response.InstallmentsResponse;
import com.fparedes.mpcodechallenge.models.Installment;
import com.fparedes.mpcodechallenge.models.PaymentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fparedes on 10/7/2019
 */
public class InstallmentsActivity extends BaseActivity
        implements InstallmentsAdapter.OnInstallmentSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.installments_recycler_view)
    RecyclerView installmentsList;
    @BindView(R.id.loading_view)
    View loadingView;

    private PaymentManager paymentManager;
    private InstallmentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installments);
        ButterKnife.bind(this);

        setToolbar();

        setupRecyclerView();

        paymentManager = PaymentManager.getInstance();
        getInstallments();
    }

    private void setupRecyclerView() {
        adapter = new InstallmentsAdapter(this);
        installmentsList.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        installmentsList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(installmentsList.getContext(),
                linearLayoutManager.getOrientation());
        installmentsList.addItemDecoration(dividerItemDecoration);
    }

    private void getInstallments() {
        loading(true);
        ApiClient.getInstallments(compSub,
                paymentManager.selectedPaymentMethod.getId(),
                paymentManager.paymentAmount,
                paymentManager.getSelectedCardIssuerId(),
                new ApiResponseListener<InstallmentsResponse>() {

                    @Override
                    public void onSuccess(@NonNull InstallmentsResponse installments) {
                        loading(false);
                        adapter.setInstallments(installments.getInstallments());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        loading(false);
                        somethingWentWrong(throwable);
                    }
                });
    }

    private void somethingWentWrong(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(InstallmentsActivity.this, "Algo salio mal!", Toast.LENGTH_SHORT).show();
    }

    private void loading(boolean loading) {
        loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onInstallmentSelected(Installment selectedInstallment) {
        paymentManager.selectedInstallment = selectedInstallment;
        String confirmationMessage = String.format(this.getString(
                R.string.payment_confirmation_message), selectedInstallment.getRecommendedMessage());

        new AlertDialog.Builder(this)
                .setMessage(confirmationMessage)
                .setPositiveButton(R.string.positive_button, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    returnToMainActivity();
                }).setNegativeButton(R.string.negative_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra(Constants.PAYMENT_SUCCEEDED, true);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
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

    private void setToolbar() {
        toolbar.setTitle(getString(R.string.title_installments_activity));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
