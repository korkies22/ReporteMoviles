/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package nl.matshofman.saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import nl.matshofman.saxrssreader.RssItem;

public class RssFeed
implements Parcelable {
    public static final Parcelable.Creator<RssFeed> CREATOR = new Parcelable.Creator<RssFeed>(){

        public RssFeed createFromParcel(Parcel parcel) {
            return new RssFeed(parcel);
        }

        public RssFeed[] newArray(int n) {
            return new RssFeed[n];
        }
    };
    private String description;
    private String language;
    private String link;
    private ArrayList<RssItem> rssItems;
    private String title;

    public RssFeed() {
        this.rssItems = new ArrayList();
    }

    public RssFeed(Parcel parcel) {
        parcel = parcel.readBundle();
        this.title = parcel.getString("title");
        this.link = parcel.getString("link");
        this.description = parcel.getString("description");
        this.language = parcel.getString("language");
        this.rssItems = parcel.getParcelableArrayList("rssItems");
    }

    void addRssItem(RssItem rssItem) {
        this.rssItems.add(rssItem);
    }

    public int describeContents() {
        return 0;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLink() {
        return this.link;
    }

    public ArrayList<RssItem> getRssItems() {
        return this.rssItems;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public void setLanguage(String string) {
        this.language = string;
    }

    public void setLink(String string) {
        this.link = string;
    }

    public void setRssItems(ArrayList<RssItem> arrayList) {
        this.rssItems = arrayList;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public void writeToParcel(Parcel parcel, int n) {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("link", this.link);
        bundle.putString("description", this.description);
        bundle.putString("language", this.language);
        bundle.putParcelableArrayList("rssItems", this.rssItems);
        parcel.writeBundle(bundle);
    }

}
