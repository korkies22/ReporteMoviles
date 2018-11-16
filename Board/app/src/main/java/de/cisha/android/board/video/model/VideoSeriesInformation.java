// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import de.cisha.chess.model.CishaUUID;

public class VideoSeriesInformation
{
    private String _author;
    private long _duration;
    private EloRangeRepresentation _eloRange;
    private boolean _isAccessible;
    private boolean _isPurchased;
    private VideoLanguage _language;
    private int _levelMax;
    private int _levelMin;
    private String _name;
    private int _priceCategory;
    private VideoSeriesId _seriesId;
    private CishaUUID _thumbCouchId;
    private int _videoCount;
    
    public VideoSeriesInformation(final VideoSeriesId seriesId, final String name, final int priceCategory, final String s, final String s2, final VideoLanguage language, final String author, final String s3, final int videoCount, final long duration, final EloRangeRepresentation eloRange, final CishaUUID thumbCouchId, final boolean isPurchased, final boolean isAccessible) {
        this._seriesId = seriesId;
        this._name = name;
        this._priceCategory = priceCategory;
        this._language = language;
        this._author = author;
        this._videoCount = videoCount;
        this._eloRange = eloRange;
        this._thumbCouchId = thumbCouchId;
        this._isPurchased = isPurchased;
        this._duration = duration;
        this._isAccessible = isAccessible;
    }
    
    public String getAuthor() {
        return this._author;
    }
    
    public long getDuration() {
        return this._duration;
    }
    
    public EloRangeRepresentation getEloRange() {
        return this._eloRange;
    }
    
    public VideoLanguage getLanguage() {
        return this._language;
    }
    
    public int getLevelMax() {
        return this._levelMax;
    }
    
    public int getLevelMin() {
        return this._levelMin;
    }
    
    public String getName() {
        return this._name;
    }
    
    public int getPriceCategoryId() {
        return this._priceCategory;
    }
    
    public CishaUUID getThumbCouchId() {
        return this._thumbCouchId;
    }
    
    public int getVideoCount() {
        return this._videoCount;
    }
    
    public VideoSeriesId getVideoSeriesId() {
        return this._seriesId;
    }
    
    public boolean isAccessible() {
        return this._isAccessible;
    }
    
    public boolean isPurchased() {
        return this._isPurchased;
    }
    
    public void setIsAccessible(final boolean isAccessible) {
        this._isAccessible = isAccessible;
    }
    
    public void setIsPurchased(final boolean isPurchased) {
        this._isPurchased = isPurchased;
    }
}
