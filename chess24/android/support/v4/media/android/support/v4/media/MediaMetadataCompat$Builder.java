/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.util.ArrayMap;
import java.util.Set;

public static final class MediaMetadataCompat.Builder {
    private final Bundle mBundle;

    public MediaMetadataCompat.Builder() {
        this.mBundle = new Bundle();
    }

    public MediaMetadataCompat.Builder(MediaMetadataCompat mediaMetadataCompat) {
        this.mBundle = new Bundle(mediaMetadataCompat.mBundle);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public MediaMetadataCompat.Builder(MediaMetadataCompat object, int n) {
        this((MediaMetadataCompat)object);
        for (String string : this.mBundle.keySet()) {
            Object object2 = this.mBundle.get(string);
            if (!(object2 instanceof Bitmap) || (object2 = (Bitmap)object2).getHeight() <= n && object2.getWidth() <= n) continue;
            this.putBitmap(string, this.scaleBitmap((Bitmap)object2, n));
        }
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int n) {
        float f = n;
        f = Math.min(f / (float)bitmap.getWidth(), f / (float)bitmap.getHeight());
        n = (int)((float)bitmap.getHeight() * f);
        return Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)((float)bitmap.getWidth() * f)), (int)n, (boolean)true);
    }

    public MediaMetadataCompat build() {
        return new MediaMetadataCompat(this.mBundle);
    }

    public MediaMetadataCompat.Builder putBitmap(String string, Bitmap object) {
        if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(string) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(string) != 2) {
            object = new StringBuilder();
            object.append("The ");
            object.append(string);
            object.append(" key cannot be used to put a Bitmap");
            throw new IllegalArgumentException(object.toString());
        }
        this.mBundle.putParcelable(string, (Parcelable)object);
        return this;
    }

    public MediaMetadataCompat.Builder putLong(String string, long l) {
        if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(string) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(string) != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The ");
            stringBuilder.append(string);
            stringBuilder.append(" key cannot be used to put a long");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mBundle.putLong(string, l);
        return this;
    }

    public MediaMetadataCompat.Builder putRating(String string, RatingCompat object) {
        if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(string) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(string) != 3) {
            object = new StringBuilder();
            object.append("The ");
            object.append(string);
            object.append(" key cannot be used to put a Rating");
            throw new IllegalArgumentException(object.toString());
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.mBundle.putParcelable(string, (Parcelable)object.getRating());
            return this;
        }
        this.mBundle.putParcelable(string, (Parcelable)object);
        return this;
    }

    public MediaMetadataCompat.Builder putString(String string, String charSequence) {
        if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(string) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(string) != 1) {
            charSequence = new StringBuilder();
            charSequence.append("The ");
            charSequence.append(string);
            charSequence.append(" key cannot be used to put a String");
            throw new IllegalArgumentException(charSequence.toString());
        }
        this.mBundle.putCharSequence(string, charSequence);
        return this;
    }

    public MediaMetadataCompat.Builder putText(String string, CharSequence charSequence) {
        if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(string) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(string) != 1) {
            charSequence = new StringBuilder();
            charSequence.append("The ");
            charSequence.append(string);
            charSequence.append(" key cannot be used to put a CharSequence");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        this.mBundle.putCharSequence(string, charSequence);
        return this;
    }
}
