package com.example.loftcoin.ui.rates;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BuildConfig;
import com.example.loftcoin.R;
import com.example.loftcoin.data.CmcCoin;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.LiRateBinding;
import com.example.loftcoin.util.Formatter;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.OutlineCircle;
import com.example.loftcoin.util.PercentFormatter;
import com.example.loftcoin.util.PriceFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private int colorNegative = Color.BLACK;
    private int colorPositive = Color.WHITE;
    private Formatter<Double> priceFormatter;
    private Formatter<Double> percentFormatter;
    private ImageLoader imageLoader;

    @Inject
    RatesAdapter(PriceFormatter priceFormatter,
                 PercentFormatter percentFormatter,
                 ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return newItem;
            }
        });

        this.priceFormatter = priceFormatter;
        this.percentFormatter = percentFormatter;
        this.imageLoader = imageLoader;
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).id();
    }

    @NonNull
    @Override
    public RatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRateBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Coin coin = getItem(position);
        holder.binding.symbol.setText(coin.symbol());
        holder.binding.price.setText(priceFormatter.format(coin.price()));
        holder.binding.change.setText(percentFormatter.format(coin.change24()));
        if (coin.change24() >= 0) {
            holder.binding.change.setTextColor(colorPositive);
        } else {
            holder.binding.change.setTextColor(colorNegative);
        }
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final Coin coin = (Coin) payloads.get(0);
            holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
            holder.binding.price.setText(percentFormatter.format(coin.change24()));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Context context = recyclerView.getContext();
        inflater = LayoutInflater.from(context);
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.textPositive, tv, true);
        colorPositive = tv.data;
        context.getTheme().resolveAttribute(R.attr.textNegative, tv, true);
        colorNegative = tv.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LiRateBinding binding;

        ViewHolder(@NonNull LiRateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.logo);
        }
    }
}
