package com.example.loftcoin.ui.currency;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Currency;
import com.example.loftcoin.databinding.LiCurrencyBinding;

import java.util.Objects;

public class CurrencyAdapter extends ListAdapter<Currency, CurrencyAdapter.ViewHolder> {

    private LayoutInflater inflater;

    CurrencyAdapter() {
        super(new DiffUtil.ItemCallback<Currency>() {
            @Override
            public boolean areItemsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiCurrencyBinding.inflate(inflater, parent, false));
    }

    @Override
    public Currency getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        final Currency currency = getItem(position);
        holder.binding.name.setText(currency.name());
        holder.binding.symbol.setText(currency.symbol());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LiCurrencyBinding binding;

        ViewHolder(@NonNull LiCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
