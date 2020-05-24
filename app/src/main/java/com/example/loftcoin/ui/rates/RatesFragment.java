package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.PicassoImageLoader;
import com.example.loftcoin.util.PriceFormatter;
import com.squareup.picasso.Picasso;

import java.util.Timer;

import javax.inject.Inject;

import timber.log.Timber;


public class RatesFragment extends Fragment {

    private RatesComponent component;
    private FragmentRatesBinding binding;
    private RatesAdapter adapter;
    private RatesViewModel ratesVM;

    @Inject
    public RatesFragment(BaseComponent baseComponent) {
        component = DaggerRatesComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ratesVM = new ViewModelProvider(this, component.vmFactory())
                .get(RatesViewModel.class);
        adapter = component.ratesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentRatesBinding.bind(view);
        binding.recyclerRates.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerRates.setAdapter(adapter);
        binding.recyclerRates.setHasFixedSize(true);
        ratesVM.coins().observe(getViewLifecycleOwner(), adapter::submitList);
        ratesVM.isRefreshing().observe(getViewLifecycleOwner(), binding.refresher::setRefreshing);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rates_sorting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.currency_dialog) {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.currency_dialog);
            return true;
        }
        else if (item.getItemId() == R.id.sorting) {
            ratesVM.switchSortingOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.recyclerRates.swapAdapter(null, false);
        super.onDestroyView();
    }
}
