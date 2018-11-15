/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public static final class SharePhoto.Builder
extends ShareMedia.Builder<SharePhoto, SharePhoto.Builder> {
    private Bitmap bitmap;
    private String caption;
    private Uri imageUrl;
    private boolean userGenerated;

    static /* synthetic */ Bitmap access$000(SharePhoto.Builder builder) {
        return builder.bitmap;
    }

    static /* synthetic */ Uri access$100(SharePhoto.Builder builder) {
        return builder.imageUrl;
    }

    static /* synthetic */ boolean access$200(SharePhoto.Builder builder) {
        return builder.userGenerated;
    }

    static /* synthetic */ String access$300(SharePhoto.Builder builder) {
        return builder.caption;
    }

    static List<SharePhoto> readPhotoListFrom(Parcel object) {
        Object object2 = SharePhoto.Builder.readListFrom((Parcel)object);
        object = new ArrayList();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ShareMedia shareMedia = (ShareMedia)object2.next();
            if (!(shareMedia instanceof SharePhoto)) continue;
            object.add((SharePhoto)shareMedia);
        }
        return object;
    }

    static void writePhotoListTo(Parcel parcel, int n, List<SharePhoto> list) {
        Parcelable[] arrparcelable = new ShareMedia[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arrparcelable[i] = list.get(i);
        }
        parcel.writeParcelableArray(arrparcelable, n);
    }

    @Override
    public SharePhoto build() {
        return new SharePhoto(this, null);
    }

    Bitmap getBitmap() {
        return this.bitmap;
    }

    Uri getImageUrl() {
        return this.imageUrl;
    }

    @Override
    SharePhoto.Builder readFrom(Parcel parcel) {
        return this.readFrom((SharePhoto)parcel.readParcelable(SharePhoto.class.getClassLoader()));
    }

    @Override
    public SharePhoto.Builder readFrom(SharePhoto sharePhoto) {
        if (sharePhoto == null) {
            return this;
        }
        return ((SharePhoto.Builder)super.readFrom(sharePhoto)).setBitmap(sharePhoto.getBitmap()).setImageUrl(sharePhoto.getImageUrl()).setUserGenerated(sharePhoto.getUserGenerated()).setCaption(sharePhoto.getCaption());
    }

    public SharePhoto.Builder setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public SharePhoto.Builder setCaption(@Nullable String string) {
        this.caption = string;
        return this;
    }

    public SharePhoto.Builder setImageUrl(@Nullable Uri uri) {
        this.imageUrl = uri;
        return this;
    }

    public SharePhoto.Builder setUserGenerated(boolean bl) {
        this.userGenerated = bl;
        return this;
    }
}
