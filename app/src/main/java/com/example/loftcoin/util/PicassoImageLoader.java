package com.example.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class PicassoImageLoader implements ImageLoader {

    private Picasso picasso;

    public PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ImageRequest load(String id) {
        return new PicassoImageRequest(picasso.load(id));
    }


    class PicassoImageRequest implements ImageRequest {
        private final RequestCreator request;

        PicassoImageRequest(RequestCreator request) {
            this.request = request;
        }

        @Override
        public void into(@NonNull ImageView image) {
            request.into(image);
        }
    }
}
