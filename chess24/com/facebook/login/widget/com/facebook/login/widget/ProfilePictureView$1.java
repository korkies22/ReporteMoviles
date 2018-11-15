/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login.widget;

import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;

class ProfilePictureView
implements ImageRequest.Callback {
    ProfilePictureView() {
    }

    @Override
    public void onCompleted(ImageResponse imageResponse) {
        ProfilePictureView.this.processResponse(imageResponse);
    }
}
