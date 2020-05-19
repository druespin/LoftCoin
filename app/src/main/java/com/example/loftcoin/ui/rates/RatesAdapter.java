package com.example.loftcoin.ui.rates;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

import java.util.Objects;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private int colorNegative = Color.BLACK;
    private int colorPositive = Color.WHITE;
    private Formatter<Double> priceFormatter;
    private ImageLoader imageLoader;

    RatesAdapter(Formatter<Double> priceFormatter, @NonNull ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
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
        holder.binding.change.setText(priceFormatter.format(coin.change24()));
        if (coin.change24() >= 0) {
            holder.binding.change.setTextColor(colorPositive);
        } else {
            holder.binding.change.setTextColor(colorNegative);
        }
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);
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
