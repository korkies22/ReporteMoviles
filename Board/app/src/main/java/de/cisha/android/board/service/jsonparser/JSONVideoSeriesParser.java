// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONArray;
import java.util.List;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.video.model.VideoInformation;
import java.util.LinkedList;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.video.model.VideoLanguage;
import org.json.JSONObject;
import de.cisha.android.board.video.model.VideoSeries;

public class JSONVideoSeriesParser extends JSONAPIResultParser<VideoSeries>
{
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_AUTHOR_FIDE_TITLE_CODE = "author_fide_title_code";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_IS_ACCESSIBLE = "is_accessible";
    private static final String KEY_IS_PURCHASED = "is_purchased";
    private static final String KEY_LANGAUGE = "language";
    private static final String KEY_METADATA = "metadata";
    private static final String KEY_PRICE_CATEGORY = "price_category";
    private static final String KEY_TEASER_IMAGE_ID = "teaser_image_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VIDEOS = "videos";
    private static final String KEY_VIDEO_DESCRIPTION = "description";
    private static final String KEY_VIDEO_ID = "id";
    private static final String KEY_VIDEO_IS_FREE = "is_free";
    private static final String KEY_VIDEO_IS_INTRO = "is_intro";
    private static final String KEY_VIDEO_SERIES_DESCRIPTION = "introduction";
    
    @Override
    public VideoSeries parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final JSONObject jsonObject2 = jsonObject.getJSONObject("metadata");
        final String optString = jsonObject2.optString("title");
        final String optString2 = jsonObject2.optString("introduction");
        final String optString3 = jsonObject2.optString("goals");
        final long optLong = jsonObject2.optLong("duration");
        final String optString4 = jsonObject2.optString("author");
        final int optInt = jsonObject2.optInt("price_category", -1);
        final String optString5 = jsonObject2.optString("author_fide_title_code");
        final String optString6 = jsonObject2.optString("teaser_image_id");
        final String optString7 = jsonObject2.optString("language", VideoLanguage.ENGLISH.getIso2Code());
        CishaUUID cishaUUID;
        if (!optString6.isEmpty()) {
            cishaUUID = new CishaUUID(optString6, true);
        }
        else {
            cishaUUID = null;
        }
        final boolean optBoolean = this.optBoolean(jsonObject2, "is_purchased", false);
        final boolean optBoolean2 = this.optBoolean(jsonObject2, "is_accessible", false);
        final LinkedList<VideoInformation> list = new LinkedList<VideoInformation>();
        final JSONArray optJSONArray = jsonObject.optJSONArray("videos");
        String s = optString4;
        String s2 = optString5;
        CishaUUID cishaUUID2 = cishaUUID;
        if (optJSONArray != null) {
            int n = 0;
            final CishaUUID cishaUUID3 = cishaUUID;
            final String s3 = optString5;
            final String s4 = optString4;
            final JSONArray jsonArray = optJSONArray;
            while (true) {
                s = s4;
                s2 = s3;
                cishaUUID2 = cishaUUID3;
                if (n >= jsonArray.length()) {
                    break;
                }
                final JSONObject jsonObject3 = jsonArray.getJSONObject(n);
                final String optString8 = jsonObject3.optString("id");
                if (optString8.isEmpty()) {
                    final Logger instance = Logger.getInstance();
                    final String name = JSONVideoSeriesParser.class.getName();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("video_id not set in json for series ");
                    sb.append(optString);
                    instance.error(name, sb.toString());
                }
                else {
                    final VideoId videoId = new VideoId(optString8);
                    final String optString9 = jsonObject3.optString("title");
                    final String optString10 = jsonObject3.optString("description");
                    final long optLong2 = jsonObject3.optLong("duration");
                    final String optString11 = jsonObject3.optString("teaser_image_id");
                    final boolean optBoolean3 = this.optBoolean(jsonObject3, "is_accessible", false);
                    CishaUUID cishaUUID4;
                    if (!optString11.isEmpty()) {
                        cishaUUID4 = new CishaUUID(optString11, true);
                    }
                    else {
                        cishaUUID4 = null;
                    }
                    list.add(new VideoInformation(videoId, optString9, optLong2 * 1000L, optString10, cishaUUID4, this.optBoolean(jsonObject3, "is_free", false), this.optBoolean(jsonObject3, "is_intro", false), optBoolean3));
                }
                ++n;
            }
        }
        return new VideoSeries(optString, VideoLanguage.from(optString7), optString2, optString3, optInt, optLong * 1000L, new JSONEloRangeParser().parseResult(jsonObject2), s, s2, cishaUUID2, optBoolean, optBoolean2, list);
    }
}
