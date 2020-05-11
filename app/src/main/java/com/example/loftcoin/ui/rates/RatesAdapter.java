package com.example.loftcoin.ui.rates;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.LiRateBinding;
import java.util.Objects;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private int colorNegative = Color.RED;
    private int colorPositive = Color.GREEN;

    public RatesAdapter() {
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
        holder.binding.symbol.setText(getItem(position).symbol());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LiRateBinding binding;

        ViewHolder(@NonNull LiRateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
