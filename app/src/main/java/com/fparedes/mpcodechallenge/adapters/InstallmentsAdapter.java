package com.fparedes.mpcodechallenge.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fparedes.mpcodechallenge.R;
import com.fparedes.mpcodechallenge.models.Installment;
import com.fparedes.mpcodechallenge.models.PaymentMethodBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fparedes on 13/7/2019
 */
public class InstallmentsAdapter extends RecyclerView.Adapter<InstallmentsAdapter.InstallmentViewHolder> {

    private OnInstallmentSelectedListener mListener;
    private List<Installment> mInstallments = new ArrayList<>();

    public InstallmentsAdapter(@NonNull OnInstallmentSelectedListener listener) {
        mListener = listener;
    }

    public void setInstallments(List<Installment> installments) {
        mInstallments.clear();
        mInstallments.addAll(installments);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InstallmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.installment_item, viewGroup, false);
        return new InstallmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentViewHolder viewHolder, int i) {
        Installment installment = mInstallments.get(i);

        viewHolder.message.setText(installment.getRecommendedMessage());
    }

    @Override
    public int getItemCount() {
        return mInstallments.size();
    }

    public interface OnInstallmentSelectedListener {
        void onInstallmentSelected(Installment selectedInstallment);
    }

    class InstallmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.installment_msg)
        TextView message;

        InstallmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                Installment installment = mInstallments.get(getAdapterPosition());
                mListener.onInstallmentSelected(installment);
            });
        }
    }
}
