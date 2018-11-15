/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.ArrayList;
import java.util.List;

public static abstract class ShareMedia.Builder<M extends ShareMedia, B extends ShareMedia.Builder>
implements ShareModelBuilder<M, B> {
    private Bundle params = new Bundle();

    static /* synthetic */ Bundle access$000(ShareMedia.Builder builder) {
        return builder.params;
    }

    static List<ShareMedia> readListFrom(Parcel arrparcelable) {
        arrparcelable = arrparcelable.readParcelableArray(ShareMedia.class.getClassLoader());
        ArrayList<ShareMedia> arrayList = new ArrayList<ShareMedia>(arrparcelable.length);
        int n = arrparcelable.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add((ShareMedia)arrparcelable[i]);
        }
        return arrayList;
    }

    static void writeListTo(Parcel parcel, int n, List<ShareMedia> list) {
        parcel.writeParcelableArray((Parcelable[])((ShareMedia[])list.toArray()), n);
    }

    @Override
    public B readFrom(M m) {
        if (m == null) {
            return (B)this;
        }
        return this.setParameters(m.getParameters());
    }

    @Deprecated
    public B setParameter(String string, String string2) {
        this.params.putString(string, string2);
        return (B)this;
    }

    @Deprecated
    public B setParameters(Bundle bundle) {
        this.params.putAll(bundle);
        return (B)this;
    }
}
