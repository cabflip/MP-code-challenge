package com.fparedes.mpcodechallenge.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fparedes.mpcodechallenge.R;
import com.fparedes.mpcodechallenge.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fparedes on 9/7/2019
 */
public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodViewHolder> {

    private List<PaymentMethod> mPaymentMethods = new ArrayList<>();
    private Context mContext;
    private OnItemSelectedListener mListener;

    public PaymentMethodsAdapter(Context context, @NonNull OnItemSelectedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        mPaymentMethods.clear();
        mPaymentMethods.addAll(paymentMethods);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.payment_method_item, viewGroup, false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder viewHolder, int i) {
        PaymentMethod paymentMethod = mPaymentMethods.get(i);

        Glide.with(mContext)
                .load(paymentMethod.getThumbnailUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .fitCenter()
                .into(viewHolder.thumbnailImg);

        viewHolder.name.setText(paymentMethod.getName());
    }

    @Override
    public int getItemCount() {
        return mPaymentMethods.size();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(PaymentMethod paymentMethod);
    }

    class PaymentMethodViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.payment_method_item_img)
        ImageView thumbnailImg;
        @BindView(R.id.payment_method_item_name)
        TextView name;

        PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                PaymentMethod paymentMethod = mPaymentMethods.get(getAdapterPosition());
                mListener.onItemSelected(paymentMethod);
            });
        }
    }
}
