package com.example.loftcoin.ui.currency;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.DialogCurrencyBinding;
import com.example.loftcoin.widget.OnItemClick;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import javax.inject.Inject;

public class CurrencyDialog extends AppCompatDialogFragment {

    private final CurrencyComponent component;
    private DialogCurrencyBinding binding;
    private CurrencyViewModel currencyVM;
    private CurrencyAdapter adapter;
    private OnItemClick onItemClick;

    @Inject
    CurrencyDialog(BaseComponent baseComponent) {
        component = DaggerCurrencyComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyVM = new ViewModelProvider(this, component.vmFactory())
                .get(CurrencyViewModel.class);
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
        currencyVM.allCurrencies().observe(this, adapter::submitList);
        onItemClick = new OnItemClick((v) -> {
            final RecyclerView.ViewHolder vh = binding.recyclerCurrency.findContainingViewHolder(v);
            if (vh != null) {
                currencyVM.updateCurrency(adapter.getItem(vh.getAdapterPosition()));
            }
            dismissAllowingStateLoss();
        });
        binding.recyclerCurrency.addOnItemTouchListener(onItemClick);
    }

    @Override
    public void onDestroy() {
        binding.recyclerCurrency.removeOnItemTouchListener(onItemClick);
        binding.recyclerCurrency.setAdapter(null);
        super.onDestroy();
    }
}
