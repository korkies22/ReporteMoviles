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
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoSeriesParser
extends JSONAPIResultParser<VideoSeries> {
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
    public VideoSeries parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        JSONObject jSONObject = object.getJSONObject(KEY_METADATA);
        String string = jSONObject.optString(KEY_TITLE);
        String string2 = jSONObject.optString(KEY_VIDEO_SERIES_DESCRIPTION);
        String string3 = jSONObject.optString(KEY_GOALS);
        long l = jSONObject.optLong(KEY_DURATION);
        String string4 = jSONObject.optString(KEY_AUTHOR);
        int n = jSONObject.optInt(KEY_PRICE_CATEGORY, -1);
        Object object2 = jSONObject.optString(KEY_AUTHOR_FIDE_TITLE_CODE);
        Object object3 = jSONObject.optString(KEY_TEASER_IMAGE_ID);
        String string5 = jSONObject.optString(KEY_LANGAUGE, VideoLanguage.ENGLISH.getIso2Code());
        object3 = !object3.isEmpty() ? new CishaUUID((String)object3, true) : null;
        boolean bl = this.optBoolean(jSONObject, KEY_IS_PURCHASED, false);
        boolean bl2 = this.optBoolean(jSONObject, KEY_IS_ACCESSIBLE, false);
        LinkedList<VideoInformation> linkedList = new LinkedList<VideoInformation>();
        Object object4 = object.optJSONArray(KEY_VIDEOS);
        String string6 = string4;
        Object object5 = object2;
        Object object6 = object3;
        if (object4 != null) {
            int n2 = 0;
            Object object7 = object3;
            object3 = object2;
            object = string4;
            string4 = object4;
            do {
                string6 = object;
                object5 = object3;
                object6 = object7;
                if (n2 >= string4.length()) break;
                string6 = string4.getJSONObject(n2);
                object2 = string6.optString(KEY_VIDEO_ID);
                if (object2.isEmpty()) {
                    object2 = Logger.getInstance();
                    string6 = JSONVideoSeriesParser.class.getName();
                    object5 = new StringBuilder();
                    object5.append("video_id not set in json for series ");
                    object5.append(string);
                    object2.error(string6, object5.toString());
                } else {
                    object5 = new VideoId((String)object2);
                    object6 = string6.optString(KEY_TITLE);
                    object4 = string6.optString(KEY_VIDEO_DESCRIPTION);
                    long l2 = string6.optLong(KEY_DURATION);
                    object2 = string6.optString(KEY_TEASER_IMAGE_ID);
                    boolean bl3 = this.optBoolean((JSONObject)string6, KEY_IS_ACCESSIBLE, false);
                    object2 = !object2.isEmpty() ? new CishaUUID((String)object2, true) : null;
                    linkedList.add(new VideoInformation((VideoId)object5, (String)object6, l2 * 1000L, (String)object4, (CishaUUID)object2, this.optBoolean((JSONObject)string6, KEY_VIDEO_IS_FREE, false), this.optBoolean((JSONObject)string6, KEY_VIDEO_IS_INTRO, false), bl3));
                }
                ++n2;
            } while (true);
        }
        object = new JSONEloRangeParser().parseResult(jSONObject);
        return new VideoSeries(string, VideoLanguage.from(string5), string2, string3, n, l * 1000L, (EloRangeRepresentation)object, string6, (String)object5, (CishaUUID)object6, bl, bl2, linkedList);
    }
}
