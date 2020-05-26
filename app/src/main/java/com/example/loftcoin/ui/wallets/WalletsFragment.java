package com.example.loftcoin.ui.wallets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentWalletsBinding;
import com.example.loftcoin.databinding.LiWalletBinding;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class WalletsFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private SnapHelper snapHelper;

    private WalletsComponent component;
    private FragmentWalletsBinding binding;
    private WalletsAdapter adapter;
    private WalletsViewModel walletsVM;

    @Inject
    public WalletsFragment(BaseComponent baseComponent) {
        component = DaggerWalletsComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletsVM = new ViewModelProvider(this, component.vmFactory())
                .get(WalletsViewModel.class);
        adapter = component.walletsAdapter();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentWalletsBinding.bind(view);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerWallets);

        TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.walletCardWidth, value, true);
        final DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        final int padding = (int) (dm.widthPixels - value.getDimension(dm)) / 2;
        binding.recyclerWallets.setPadding(padding, 0, padding, 0);
        binding.recyclerWallets.setClipToPadding(false);

        binding.recyclerWallets.setLayoutManager(
                new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        binding.recyclerWallets.addOnScrollListener(new CarouselScroller());

        binding.recyclerWallets.setAdapter(adapter);

        disposable.add(walletsVM.wallets().subscribe(adapter::submitList));
        disposable.add(walletsVM.wallets().map(List::isEmpty).subscribe(isEmpty -> {
            binding.walletCard.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            binding.recyclerWallets.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }));
    }

    @Override
    public void onDestroyView() {
        snapHelper.attachToRecyclerView(null);
        binding.recyclerWallets.setAdapter(null);
        disposable.clear();
        super.onDestroyView();
    }

    private static class CarouselScroller extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            final int centerX = (recyclerView.getLeft() + recyclerView.getRight()) / 2;
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                final View child = recyclerView.getChildAt(i);
                final int childCenterX = (child.getLeft() + child.getRight()) / 2;
                float childOffset = Math.abs((childCenterX - centerX) / (float) centerX);
                float factor = (float) Math.pow(0.8, childOffset);
                child.setScaleX(factor);
                child.setScaleY(factor);
            }
        }
    }
}
