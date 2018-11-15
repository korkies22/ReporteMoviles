/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.VideoId;
import de.cisha.chess.model.CishaUUID;

public class VideoInformation {
    private String _description;
    private long _durationInMillisec;
    private VideoId _id;
    private boolean _isAccessible;
    private boolean _isFree;
    private boolean _isIntro;
    private boolean _isPurchased;
    private CishaUUID _teaserCouchId;
    private String _title;

    public VideoInformation(VideoId videoId, String string, long l, String string2, CishaUUID cishaUUID, boolean bl, boolean bl2, boolean bl3) {
        this._id = videoId;
        this._title = string;
        this._durationInMillisec = l;
        this._description = string2;
        this._teaserCouchId = cishaUUID;
        this._isFree = bl;
        this._isIntro = bl2;
        this._isAccessible = bl3;
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
