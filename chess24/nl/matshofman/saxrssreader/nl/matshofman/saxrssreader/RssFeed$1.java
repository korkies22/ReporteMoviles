/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package nl.matshofman.saxrssreader;

import android.os.Parcel;
import android.os.Parcelable;

static final class RssFeed
implements Parcelable.Creator<nl.matshofman.saxrssreader.RssFeed> {
    RssFeed() {
    }

    public nl.matshofman.saxrssreader.RssFeed createFromParcel(Parcel parcel) {
        return new nl.matshofman.saxrssreader.RssFeed(parcel);
    }

    public nl.matshofman.saxrssreader.RssFeed[] newArray(int n) {
        return new nl.matshofman.saxrssreader.RssFeed[n];
    }
}
