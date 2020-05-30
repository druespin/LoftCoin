package com.example.loftcoin.ui.wallets;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BuildConfig;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.databinding.LiWalletBinding;
import com.example.loftcoin.util.BalanceFormatter;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.PriceFormatter;
import com.example.loftcoin.widget.OutlineCircle;

import java.util.Objects;

import javax.inject.Inject;


public class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final PriceFormatter priceFormatter;
    private final BalanceFormatter balanceFormatter;
    private final ImageLoader imageLoader;

    @Inject
    WalletsAdapter(ImageLoader imageLoader,
                   PriceFormatter priceFormatter,
                   BalanceFormatter balanceFormatter) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }
            @Override
            public boolean areContentsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.balanceFormatter = balanceFormatter;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LiWalletBinding.inflate(inflater, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.symbol.setText(wallet.coin().symbol());
        holder.binding.balance1.setText(balanceFormatter.format(wallet));
        final double balance = wallet.balance() * wallet.coin().price();
        holder.binding.balance2.setText(priceFormatter.format(wallet.coin().currencyCode(), balance));
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + wallet.coin().id() + ".png")
                .into(holder.binding.logo);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LiWalletBinding binding;

        ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.logo);
            binding.getRoot().setClipToOutline(true);

        }
    }
}
