/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONEloRangeParser;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.chess.model.CishaUUID;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoSeriesInfoListParser
extends JSONAPIResultParser<VideoSeriesInfoList> {
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_AUTHOR_FIDE_TITLE_CODE = "author_fide_title_code";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_ID = "id";
    private static final String KEY_INTRODUCTION = "introduction";
    private static final String KEY_IS_ACCESSIBLE = "is_accessible";
    private static final String KEY_IS_PURCHASED = "is_purchased";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_PRICE_CATEGORY = "price_category";
    private static final String KEY_SERIES = "series";
    private static final String KEY_SERIES_COUNT = "seriesCount";
    private static final String KEY_TEASER_IMAGE_ID = "teaser_image_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VIDEO_COUNT = "video_count";

    @Override
    public VideoSeriesInfoList parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray(KEY_SERIES);
        LinkedList<VideoSeriesInformation> linkedList = new LinkedList<VideoSeriesInformation>();
        JSONEloRangeParser jSONEloRangeParser = new JSONEloRangeParser();
        for (int i = 0; i < jSONArray.length(); ++i) {
            Object object = jSONArray.getJSONObject(i);
            int n = object.getInt(KEY_ID);
            boolean bl = this.optBoolean((JSONObject)object, KEY_IS_PURCHASED, false);
            boolean bl2 = this.optBoolean((JSONObject)object, KEY_IS_ACCESSIBLE, false);
            int n2 = object.optInt(KEY_PRICE_CATEGORY, -1);
            String string = object.optString(KEY_AUTHOR, "");
            String string2 = object.optString(KEY_AUTHOR_FIDE_TITLE_CODE, "");
            String string3 = object.optString(KEY_TITLE, "no title");
            String string4 = object.optString(KEY_INTRODUCTION);
            String string5 = object.optString(KEY_GOALS);
            String string6 = object.optString(KEY_TEASER_IMAGE_ID);
            String string7 = object.optString(KEY_LANGUAGE);
            long l = object.optLong(KEY_DURATION, 0L);
            int n3 = object.optInt(KEY_VIDEO_COUNT);
            object = jSONEloRangeParser.parseResult((JSONObject)object);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            linkedList.add(new VideoSeriesInformation(new VideoSeriesId(stringBuilder.toString()), string3, n2, string4, string5, VideoLanguage.from(string7), string, string2, n3, l * 1000L, (EloRangeRepresentation)object, new CishaUUID(string6, true), bl, bl2));
        }
        return new VideoSeriesInfoList(linkedList, jSONObject.optInt(KEY_SERIES_COUNT, 0));
    }

    public static class VideoSeriesInfoList {
        private int _seriesCount;
        private List<VideoSeriesInformation> _viedeoSeriesList;

        public VideoSeriesInfoList(List<VideoSeriesInformation> list, int n) {
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

}
