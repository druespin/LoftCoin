package com.example.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;

public interface ImageLoader {

    @NonNull
    ImageRequest load(String id);

    interface ImageRequest {
        void into(@NonNull ImageView image);
    }
}
