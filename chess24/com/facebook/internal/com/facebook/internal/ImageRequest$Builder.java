/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Validate;

public static class ImageRequest.Builder {
    private boolean allowCachedRedirects;
    private ImageRequest.Callback callback;
    private Object callerTag;
    private Context context;
    private Uri imageUrl;

    public ImageRequest.Builder(Context context, Uri uri) {
        Validate.notNull((Object)uri, "imageUri");
        this.context = context;
        this.imageUrl = uri;
    }

    static /* synthetic */ Context access$000(ImageRequest.Builder builder) {
        return builder.context;
    }

    static /* synthetic */ Uri access$100(ImageRequest.Builder builder) {
        return builder.imageUrl;
    }

    static /* synthetic */ ImageRequest.Callback access$200(ImageRequest.Builder builder) {
        return builder.callback;
    }

    static /* synthetic */ boolean access$300(ImageRequest.Builder builder) {
        return builder.allowCachedRedirects;
    }

    static /* synthetic */ Object access$400(ImageRequest.Builder builder) {
        return builder.callerTag;
    }

    public ImageRequest build() {
        return new ImageRequest(this, null);
    }

    public ImageRequest.Builder setAllowCachedRedirects(boolean bl) {
        this.allowCachedRedirects = bl;
        return this;
    }

    public ImageRequest.Builder setCallback(ImageRequest.Callback callback) {
        this.callback = callback;
        return this;
    }

    public ImageRequest.Builder setCallerTag(Object object) {
        this.callerTag = object;
        return this;
    }
}
