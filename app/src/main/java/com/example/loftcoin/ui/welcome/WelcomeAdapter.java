package com.example.loftcoin.ui.welcome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.WelcomePageBinding;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private static final int[] IMAGES = {
            R.drawable.asset_1,
            R.drawable.asset_2,
            R.drawable.asset_3,
    };
    private static final int[] TITLES = {
            R.string.title_1,
            R.string.title_2,
            R.string.title_3,
    };
    private static final int[] SUBTITLES = {
            R.string.subtitle_1,
            R.string.subtitle_2,
            R.string.subtitle_3,
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                WelcomePageBinding.inflate(inflater, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.image.setImageResource(IMAGES[position]);
        holder.binding.title.setText(TITLES[position]);
        holder.binding.subtitle.setText(SUBTITLES[position]);
    }

    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final WelcomePageBinding binding;

        ViewHolder(WelcomePageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
