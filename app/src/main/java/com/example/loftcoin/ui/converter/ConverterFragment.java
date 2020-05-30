package com.example.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.DialogCurrencyBinding;
import com.example.loftcoin.databinding.FragmentConverterBinding;
import com.example.loftcoin.util.VMFactory;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ConverterFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ConverterComponent component;

    private FragmentConverterBinding binding;

    private ConverterViewModel converterVM;

    @Inject
    public ConverterFragment(BaseComponent baseComponent) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        converterVM = new ViewModelProvider(requireParentFragment(), component.vmFactory())
                .get(ConverterViewModel.class);
        Timber.d("%s", converterVM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentConverterBinding.bind(view);

        final NavController navController = NavHostFragment.findNavController(this);
        disposable.add(converterVM.topCoins().subscribe());
        disposable.add(RxView.clicks(binding.fromCoin).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsDialog.KEY_MODE, CoinsDialog.MODE_FROM);
            navController.navigate(R.id.coins_dialog, args);
        }));

        disposable.add(RxView.clicks(binding.toCoin).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsDialog.KEY_MODE, CoinsDialog.MODE_TO);
            navController.navigate(R.id.coins_dialog, args);
        }));

        disposable.add(converterVM.fromCoin().subscribe(coin -> {
            binding.fromCoin.setText(coin.symbol());
        }));
        disposable.add(converterVM.toCoin().subscribe(coin -> {
            binding.toCoin.setText(coin.symbol());
        }));

        disposable.add(RxTextView.textChanges(binding.from).subscribe(converterVM::fromValue));
        disposable.add(RxTextView.textChanges(binding.to).subscribe(converterVM::toValue));

        disposable.add(converterVM.fromValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.from.setText(text);
                    binding.from.setSelection(text.length());
                }));
        disposable.add(converterVM.toValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.to.setText(text);
                    binding.to.setSelection(text.length());
                }));
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }
}

