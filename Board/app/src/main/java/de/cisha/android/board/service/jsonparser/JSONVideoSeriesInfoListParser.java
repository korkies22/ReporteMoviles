// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import org.json.JSONArray;
import java.util.List;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.LinkedList;
import org.json.JSONObject;

public class JSONVideoSeriesInfoListParser extends JSONAPIResultParser<VideoSeriesInfoList>
{
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
    public VideoSeriesInfoList parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("series");
        final LinkedList<VideoSeriesInformation> list = new LinkedList<VideoSeriesInformation>();
        final JSONEloRangeParser jsonEloRangeParser = new JSONEloRangeParser();
        for (int i = 0; i < jsonArray.length(); ++i) {
            final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            final int int1 = jsonObject2.getInt("id");
            final boolean optBoolean = this.optBoolean(jsonObject2, "is_purchased", false);
            final boolean optBoolean2 = this.optBoolean(jsonObject2, "is_accessible", false);
            final int optInt = jsonObject2.optInt("price_category", -1);
            final String optString = jsonObject2.optString("author", "");
            final String optString2 = jsonObject2.optString("author_fide_title_code", "");
            final String optString3 = jsonObject2.optString("title", "no title");
            final String optString4 = jsonObject2.optString("introduction");
            final String optString5 = jsonObject2.optString("goals");
            final String optString6 = jsonObject2.optString("teaser_image_id");
            final String optString7 = jsonObject2.optString("language");
            final long optLong = jsonObject2.optLong("duration", 0L);
            final int optInt2 = jsonObject2.optInt("video_count");
            final EloRangeRepresentation result = jsonEloRangeParser.parseResult(jsonObject2);
            final StringBuilder sb = new StringBuilder();
            sb.append(int1);
            sb.append("");
            list.add(new VideoSeriesInformation(new VideoSeriesId(sb.toString()), optString3, optInt, optString4, optString5, VideoLanguage.from(optString7), optString, optString2, optInt2, optLong * 1000L, result, new CishaUUID(optString6, true), optBoolean, optBoolean2));
        }
        return new VideoSeriesInfoList(list, jsonObject.optInt("seriesCount", 0));
    }
    
    public static class VideoSeriesInfoList
    {
        private int _seriesCount;
        private List<VideoSeriesInformation> _viedeoSeriesList;
        
        public VideoSeriesInfoList(final List<VideoSeriesInformation> viedeoSeriesList, final int seriesCount) {
            this._viedeoSeriesList = viedeoSeriesList;
            this._seriesCount = seriesCount;
        }
        
        public List<VideoSeriesInformation> getList() {
            return this._viedeoSeriesList;
        }
        
        public int getSeriesCount() {
            return this._seriesCount;
        }
    }
}
