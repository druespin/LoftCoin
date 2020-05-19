package com.example.loftcoin.ui.currency;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.data.Currency;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.CurrencyRepoImpl;
import com.example.loftcoin.databinding.DialogCurrencyBinding;
import com.example.loftcoin.util.OnItemClick;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CurrencyDialog extends AppCompatDialogFragment {

    private DialogCurrencyBinding binding;
    private CurrencyRepo currencyRepo;
    private CurrencyAdapter adapter;
    private OnItemClick onItemClick;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyRepo = new CurrencyRepoImpl(requireContext());
        adapter = new CurrencyAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogCurrencyBinding.inflate(requireActivity().getLayoutInflater());
        return new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.choose_currency)
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerCurrency.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerCurrency.setAdapter(adapter);
        currencyRepo.availableCurrencies().observe(this, adapter::submitList);
        onItemClick = new OnItemClick((v) -> {
            final RecyclerView.ViewHolder vh = binding.recyclerCurrency.findContainingViewHolder(v);
            if (vh != null) {
                final Currency item = adapter.getItem(vh.getAdapterPosition());
                currencyRepo.updateCurrency(item);
            }
            dismissAllowingStateLoss();
        });
        binding.recyclerCurrency.addOnItemTouchListener(onItemClick);
    }

    @Override
    public void onDestroy() {
        binding.recyclerCurrency.setAdapter(null);
        super.onDestroy();
    }
}
