/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.share.internal;

import android.net.Uri;
import com.facebook.internal.Utility;
import com.facebook.share.model.SharePhoto;

static final class WebDialogParameters
implements Utility.Mapper<SharePhoto, String> {
    WebDialogParameters() {
    }

    @Override
    public String apply(SharePhoto sharePhoto) {
        return sharePhoto.getImageUrl().toString();
    }
}
