package com.fparedes.mpcodechallenge.models;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fparedes.mpcodechallenge.R;

import java.util.Objects;

/**
 * Created by fparedes on 18/7/2019
 */
public class PaymentSucceedDialog {

    public void show(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.successful_payment_popup);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView dialogBody = dialog.findViewById(R.id.payment_popup_body);
        dialogBody.setText(getBody());

        Button closeBtn = dialog.findViewById(R.id.payment_popup_close_btn);
        closeBtn.setOnClickListener(view ->  dialog.dismiss());

        dialog.show();
    }

    private String getBody() {
        PaymentManager pm = PaymentManager.getInstance();

        StringBuilder body = new StringBuilder();
        body.append("El pago fue realizado con ").append(pm.selectedPaymentMethod.toString());
        if (pm.selectedCardIssuer != null)
            body.append(" emitida por ").append(pm.selectedCardIssuer.getName());
        body.append(" en ") .append(pm.selectedInstallment.getRecommendedMessage()).append(".");

        return body.toString();
    }
}
