// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.news.model;

import java.util.Date;
import java.net.URL;

public class NewsItem
{
    private String _author;
    private String _category;
    private String _description;
    private URL _linkUrl;
    private Date _pubDate;
    private URL _teaserImageUrl;
    private String _title;
    
    public NewsItem(final String title, final String description, final URL teaserImageUrl, final URL linkUrl, final String category, final Date pubDate, final String author) {
        this._title = title;
        this._description = description;
        this._teaserImageUrl = teaserImageUrl;
        this._linkUrl = linkUrl;
        this._category = category;
        this._pubDate = pubDate;
        this._author = author;
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
