/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;

public static class JSONVideoSeriesInfoListParser.VideoSeriesInfoList {
    private int _seriesCount;
    private List<VideoSeriesInformation> _viedeoSeriesList;

    public JSONVideoSeriesInfoListParser.VideoSeriesInfoList(List<VideoSeriesInformation> list, int n) {
        this._viedeoSeriesList = list;
        this._seriesCount = n;
    }

    public List<VideoSeriesInformation> getList() {
        return this._viedeoSeriesList;
    }

    public int getSeriesCount() {
        return this._seriesCount;
    }
}
