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
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import nl.matshofman.saxrssreader.RssFeed;

public class RssItem
implements Comparable<RssItem>,
Parcelable {
    public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>(){

        public RssItem createFromParcel(Parcel parcel) {
            return new RssItem(parcel);
        }

        public RssItem[] newArray(int n) {
            return new RssItem[n];
        }
    };
    private String author;
    private String category;
    private String content;
    private String description;
    private RssFeed feed;
    private String link;
    private Date pubDate;
    private URL teaserImageURL;
    private String title;

    public RssItem() {
    }

    public RssItem(Parcel parcel) {
        parcel = parcel.readBundle();
        this.title = parcel.getString("title");
        this.link = parcel.getString("link");
        this.pubDate = (Date)parcel.getSerializable("pubDate");
        this.description = parcel.getString("description");
        this.content = parcel.getString("content");
        this.feed = (RssFeed)parcel.getParcelable("feed");
        this.teaserImageURL = this.getURLFromString(parcel.getString("teaserImage"));
    }

    @Override
    public int compareTo(RssItem rssItem) {
        if (this.getPubDate() != null && rssItem.getPubDate() != null) {
            return this.getPubDate().compareTo(rssItem.getPubDate());
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCategory() {
        return this.category;
    }

    public String getContent() {
        return this.content;
    }

    public String getDescription() {
        return this.description;
    }

    public RssFeed getFeed() {
        return this.feed;
    }

    public String getLink() {
        return this.link;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public URL getTeaserImageURL() {
        return this.teaserImageURL;
    }

    public String getTitle() {
        return this.title;
    }

    public URL getURLFromString(String object) {
        try {
            object = new URL((String)object);
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
            return null;
        }
    }

    public void setAuthor(String string) {
        this.author = string;
    }

    public void setCategory(String string) {
        this.category = string;
    }

    public void setContent(String string) {
        this.content = string;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public void setFeed(RssFeed rssFeed) {
        this.feed = rssFeed;
    }

    public void setLink(String string) {
        this.link = string;
    }

    public void setPubDate(String string) {
        try {
            this.pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(string);
            return;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
            return;
        }
    }

    public void setPubDate(Date date) {
        this.pubDate = date;
    }

    public void setTeaserImage(String string) {
        this.teaserImageURL = this.getURLFromString(string);
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public void writeToParcel(Parcel parcel, int n) {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("link", this.link);
        bundle.putSerializable("pubDate", (Serializable)this.pubDate);
        bundle.putString("description", this.description);
        bundle.putString("content", this.content);
        bundle.putParcelable("feed", (Parcelable)this.feed);
        String string = this.teaserImageURL != null ? this.teaserImageURL.toExternalForm() : "";
        bundle.putString("teaserImage", string);
        parcel.writeBundle(bundle);
    }

}
