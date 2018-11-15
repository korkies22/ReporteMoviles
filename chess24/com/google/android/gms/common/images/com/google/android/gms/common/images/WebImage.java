/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.zzb;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage
extends zza {
    public static final Parcelable.Creator<WebImage> CREATOR = new zzb();
    final int mVersionCode;
    private final Uri zzarW;
    private final int zzrG;
    private final int zzrH;

    WebImage(int n, Uri uri, int n2, int n3) {
        this.mVersionCode = n;
        this.zzarW = uri;
        this.zzrG = n2;
        this.zzrH = n3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int n, int n2) throws IllegalArgumentException {
        this(1, uri, n, n2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (n >= 0 && n2 >= 0) {
            return;
        }
        throw new IllegalArgumentException("width and height must not be negative");
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(WebImage.zzs(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Uri zzs(JSONObject jSONObject) {
        if (!jSONObject.has("url")) return null;
        try {
            return Uri.parse((String)jSONObject.getString("url"));
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null) {
            if (!(object instanceof WebImage)) {
                return false;
            }
            object = (WebImage)object;
            if (zzaa.equal((Object)this.zzarW, (Object)object.zzarW) && this.zzrG == object.zzrG && this.zzrH == object.zzrH) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int getHeight() {
        return this.zzrH;
    }

    public Uri getUrl() {
        return this.zzarW;
    }

    public int getWidth() {
        return this.zzrG;
    }

    public int hashCode() {
        return zzaa.hashCode(new Object[]{this.zzarW, this.zzrG, this.zzrH});
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)this.zzarW.toString());
            jSONObject.put("width", this.zzrG);
            jSONObject.put("height", this.zzrH);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            return jSONObject;
        }
    }

    public String toString() {
        return String.format(Locale.US, "Image %dx%d %s", this.zzrG, this.zzrH, this.zzarW.toString());
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzb.zza(this, parcel, n);
    }
}
