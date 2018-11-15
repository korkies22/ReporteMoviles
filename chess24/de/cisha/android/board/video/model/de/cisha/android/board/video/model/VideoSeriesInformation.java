/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.chess.model.CishaUUID;

public class VideoSeriesInformation {
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

    public VideoSeriesInformation(VideoSeriesId videoSeriesId, String string, int n, String string2, String string3, VideoLanguage videoLanguage, String string4, String string5, int n2, long l, EloRangeRepresentation eloRangeRepresentation, CishaUUID cishaUUID, boolean bl, boolean bl2) {
        this._seriesId = videoSeriesId;
        this._name = string;
        this._priceCategory = n;
        this._language = videoLanguage;
        this._author = string4;
        this._videoCount = n2;
        this._eloRange = eloRangeRepresentation;
        this._thumbCouchId = cishaUUID;
        this._isPurchased = bl;
        this._duration = l;
        this._isAccessible = bl2;
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

    public void setIsAccessible(boolean bl) {
        this._isAccessible = bl;
    }

    public void setIsPurchased(boolean bl) {
        this._isPurchased = bl;
    }
}
