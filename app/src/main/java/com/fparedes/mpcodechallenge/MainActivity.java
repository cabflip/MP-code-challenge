package com.fparedes.mpcodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fparedes.mpcodechallenge.api.Constants;
import com.fparedes.mpcodechallenge.models.PaymentManager;
import com.fparedes.mpcodechallenge.models.PaymentSucceedDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_currency_label)
    TextView priceCurrencyText;
    @BindView(R.id.main_price)
    TextView priceText;
    @BindView(R.id.main_price_decimals)
    TextView priceDecimalsText;

    @BindView(R.id.main_continue)
    Button continueBtn;

    // Doesn't contain comma
    private String price = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.title_main_activity));
        setSupportActionBar(toolbar);

        checkForSuccessfulPayments();
    }

    private void addNumber(String number) {
        price = price.concat(number);
        updatePrice();
    }

    private void backspace() {
        if (price != null && price.length() > 0) {
            price = price.substring(0, price.length() - 1);
            updatePrice();
        }
    }

    private void updatePrice() {
        // Zero-padding with a length of 3
        price = format(Locale.getDefault(), "%03d", Long.valueOf(price.trim()));

        // Get the round price value
        String priceValue = price.substring(0, price.length() - 2);
        // Remove leading zeros but leave one if necessary
        priceValue = priceValue.replaceFirst("^0+(?!$)", "");
        priceText.setText(priceValue);

        // Get the decimals
        String priceDecimals = price.substring(price.length() - 2);
        priceDecimalsText.setText(priceDecimals);

        refreshContinueBtnStatus();
    }

    private void refreshContinueBtnStatus() {
        Long priceLong = Long.valueOf(price);

        // Disable button if the price is lower than 100 ($1.00)
        boolean enabled = priceLong > 99;

        continueBtn.setEnabled(enabled);
        continueBtn.setClickable(enabled);

        priceCurrencyText.setEnabled(enabled);
        priceText.setEnabled(enabled);
        priceDecimalsText.setEnabled(enabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_keyb_1:
                addNumber(getString(R.string.keyboard_1));
                break;
            case R.id.main_keyb_2:
                addNumber(getString(R.string.keyboard_2));
                break;
            case R.id.main_keyb_3:
                addNumber(getString(R.string.keyboard_3));
                break;
            case R.id.main_keyb_4:
                addNumber(getString(R.string.keyboard_4));
                break;
            case R.id.main_keyb_5:
                addNumber(getString(R.string.keyboard_5));
                break;
            case R.id.main_keyb_6:
                addNumber(getString(R.string.keyboard_6));
                break;
            case R.id.main_keyb_7:
                addNumber(getString(R.string.keyboard_7));
                break;
            case R.id.main_keyb_8:
                addNumber(getString(R.string.keyboard_8));
                break;
            case R.id.main_keyb_9:
                addNumber(getString(R.string.keyboard_9));
                break;
            case R.id.main_keyb_0:
                addNumber(getString(R.string.keyboard_0));
                break;
            case R.id.main_keyb_00:
                addNumber(getString(R.string.keyboard_00));
                break;

            case R.id.main_keyb_backspace:
                backspace();
                break;

            case R.id.main_continue:
                PaymentManager.getInstance().paymentAmount = getPaymentAmount();
                startActivity(new Intent(MainActivity.this, PaymentMethodsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    private double getPaymentAmount() {
        return Double.parseDouble(
                priceText.getText().toString() + "." + priceDecimalsText.getText().toString());
    }

    private void checkForSuccessfulPayments() {
        if (getIntent() == null)
            return;

        boolean paymentSucceeded = getIntent().getBooleanExtra(
                Constants.PAYMENT_SUCCEEDED, false);
        if (!paymentSucceeded)
            return;

        new Handler().postDelayed(() -> new PaymentSucceedDialog().show(this), 500);
    }
}
