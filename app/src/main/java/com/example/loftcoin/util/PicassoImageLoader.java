package com.example.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PicassoImageLoader implements ImageLoader {

    private Picasso picasso;

    @Inject
    public PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ImageRequest load(String url) {
        return new PicassoImageRequest(picasso.load(url));
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
