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

static final class RssItem
implements Parcelable.Creator<nl.matshofman.saxrssreader.RssItem> {
    RssItem() {
    }

    public nl.matshofman.saxrssreader.RssItem createFromParcel(Parcel parcel) {
        return new nl.matshofman.saxrssreader.RssItem(parcel);
    }

    public nl.matshofman.saxrssreader.RssItem[] newArray(int n) {
        return new nl.matshofman.saxrssreader.RssItem[n];
    }
}
