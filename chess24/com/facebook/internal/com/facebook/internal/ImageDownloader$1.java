/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.facebook.internal;

import android.graphics.Bitmap;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;

static final class ImageDownloader
implements Runnable {
    final /* synthetic */ Bitmap val$bitmap;
    final /* synthetic */ ImageRequest.Callback val$callback;
    final /* synthetic */ Exception val$error;
    final /* synthetic */ boolean val$isCachedRedirect;
    final /* synthetic */ ImageRequest val$request;

    ImageDownloader(ImageRequest imageRequest, Exception exception, boolean bl, Bitmap bitmap, ImageRequest.Callback callback) {
        this.val$request = imageRequest;
        this.val$error = exception;
        this.val$isCachedRedirect = bl;
        this.val$bitmap = bitmap;
        this.val$callback = callback;
    }

    @Override
    public void run() {
        ImageResponse imageResponse = new ImageResponse(this.val$request, this.val$error, this.val$isCachedRedirect, this.val$bitmap);
        this.val$callback.onCompleted(imageResponse);
    }
}
