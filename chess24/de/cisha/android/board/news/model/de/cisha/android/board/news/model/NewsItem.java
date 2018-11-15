/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.news.model;

import java.net.URL;
import java.util.Date;

public class NewsItem {
    private String _author;
    private String _category;
    private String _description;
    private URL _linkUrl;
    private Date _pubDate;
    private URL _teaserImageUrl;
    private String _title;

    public NewsItem(String string, String string2, URL uRL, URL uRL2, String string3, Date date, String string4) {
        this._title = string;
        this._description = string2;
        this._teaserImageUrl = uRL;
        this._linkUrl = uRL2;
        this._category = string3;
        this._pubDate = date;
        this._author = string4;
    }

    public String getAuthor() {
        return this._author;
    }

    public String getCategory() {
        return this._category;
    }

    public String getDescription() {
        return this._description;
    }

    public URL getLinkUrl() {
        return this._linkUrl;
    }

    public Date getPubDate() {
        return this._pubDate;
    }

    public URL getTeaserImageUrl() {
        return this._teaserImageUrl;
    }

    public String getTitle() {
        return this._title;
    }
}
