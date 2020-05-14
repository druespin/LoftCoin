package com.example.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

public class ImageLoader {

    public static void loadImage(@NonNull String id, @NonNull ImageView image) {

        Picasso.get()
                .load(id)
                .into(image);
    }
}
