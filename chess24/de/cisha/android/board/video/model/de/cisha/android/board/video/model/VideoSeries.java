/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.chess.model.CishaUUID;
import java.util.Collections;
import java.util.List;

public class VideoSeries {
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

    public VideoSeries(String string, VideoLanguage videoLanguage, String string2, String string3, int n, long l, EloRangeRepresentation eloRangeRepresentation, String string4, String string5, CishaUUID cishaUUID, boolean bl, boolean bl2, List<VideoInformation> list) {
        this._title = string;
        this._description = string2;
        this._goals = string3;
        this._priceCategory = n;
        this._durationMillis = l;
        this._eloRange = eloRangeRepresentation;
        this._author = string4;
        this._authorTitleCode = string5;
        this._teaserCouchId = cishaUUID;
        this._isPurchased = bl;
        this._isAccessible = bl2;
        this._videoInformationList = list;
        this._language = videoLanguage;
    }

    public String getAuthor() {
        return this._author;
    }

    public String getAuthorTitleCode() {
        return this._authorTitleCode;
    }

    public String getAuthorWithTitle() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getAuthorTitleCode() != null) {
            charSequence = new StringBuilder();
            charSequence.append(this.getAuthorTitleCode());
            charSequence.append(" ");
            charSequence = charSequence.toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(this.getAuthor());
        return stringBuilder.toString();
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
        return Collections.unmodifiableList(this._videoInformationList);
    }

    public boolean isAccessible() {
        return this._isAccessible;
    }

    public boolean isPurchased() {
        return this._isPurchased;
    }
}
