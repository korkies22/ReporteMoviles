// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import de.cisha.chess.model.CishaUUID;

public class VideoInformation
{
    private String _description;
    private long _durationInMillisec;
    private VideoId _id;
    private boolean _isAccessible;
    private boolean _isFree;
    private boolean _isIntro;
    private boolean _isPurchased;
    private CishaUUID _teaserCouchId;
    private String _title;
    
    public VideoInformation(final VideoId id, final String title, final long durationInMillisec, final String description, final CishaUUID teaserCouchId, final boolean isFree, final boolean isIntro, final boolean isAccessible) {
        this._id = id;
        this._title = title;
        this._durationInMillisec = durationInMillisec;
        this._description = description;
        this._teaserCouchId = teaserCouchId;
        this._isFree = isFree;
        this._isIntro = isIntro;
        this._isAccessible = isAccessible;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public long getDuration() {
        return this._durationInMillisec;
    }
    
    public VideoId getId() {
        return this._id;
    }
    
    public CishaUUID getTeaserCouchId() {
        return this._teaserCouchId;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public boolean isAccessible() {
        return this._isAccessible;
    }
    
    public boolean isFree() {
        return this._isFree;
    }
    
    public boolean isInProgress() {
        return false;
    }
    
    public boolean isIntro() {
        return this._isIntro;
    }
    
    public boolean isPurchased() {
        return this._isPurchased;
    }
}
