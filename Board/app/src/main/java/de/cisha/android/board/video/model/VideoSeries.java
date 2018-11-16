// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import java.util.Collections;
import java.util.List;
import de.cisha.chess.model.CishaUUID;

public class VideoSeries
{
    private String _author;
    private String _authorTitleCode;
    private String _description;
    private long _durationMillis;
    private EloRangeRepresentation _eloRange;
    private String _goals;
    private boolean _isAccessible;
    private boolean _isPurchased;
    private VideoLanguage _language;
    private int _priceCategory;
    private CishaUUID _teaserCouchId;
    private String _title;
    private List<VideoInformation> _videoInformationList;
    
    public VideoSeries(final String title, final VideoLanguage language, final String description, final String goals, final int priceCategory, final long durationMillis, final EloRangeRepresentation eloRange, final String author, final String authorTitleCode, final CishaUUID teaserCouchId, final boolean isPurchased, final boolean isAccessible, final List<VideoInformation> videoInformationList) {
        this._title = title;
        this._description = description;
        this._goals = goals;
        this._priceCategory = priceCategory;
        this._durationMillis = durationMillis;
        this._eloRange = eloRange;
        this._author = author;
        this._authorTitleCode = authorTitleCode;
        this._teaserCouchId = teaserCouchId;
        this._isPurchased = isPurchased;
        this._isAccessible = isAccessible;
        this._videoInformationList = videoInformationList;
        this._language = language;
    }
    
    public String getAuthor() {
        return this._author;
    }
    
    public String getAuthorTitleCode() {
        return this._authorTitleCode;
    }
    
    public String getAuthorWithTitle() {
        final StringBuilder sb = new StringBuilder();
        String string;
        if (this.getAuthorTitleCode() != null) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(this.getAuthorTitleCode());
            sb2.append(" ");
            string = sb2.toString();
        }
        else {
            string = "";
        }
        sb.append(string);
        sb.append(this.getAuthor());
        return sb.toString();
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public long getDurationMillis() {
        return this._durationMillis;
    }
    
    public EloRangeRepresentation getEloRange() {
        return this._eloRange;
    }
    
    public String getGoals() {
        return this._goals;
    }
    
    public VideoLanguage getLanguage() {
        return this._language;
    }
    
    public int getPriceCategoryId() {
        return this._priceCategory;
    }
    
    public CishaUUID getTeaserCouchId() {
        return this._teaserCouchId;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public List<VideoInformation> getVideoInformationList() {
        return Collections.unmodifiableList((List<? extends VideoInformation>)this._videoInformationList);
    }
    
    public boolean isAccessible() {
        return this._isAccessible;
    }
    
    public boolean isPurchased() {
        return this._isPurchased;
    }
}
