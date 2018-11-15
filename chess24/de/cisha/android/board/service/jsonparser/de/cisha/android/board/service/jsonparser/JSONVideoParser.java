/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import android.net.Uri;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.service.jsonparser.GameAndHighlights;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONVideoCommandParser;
import de.cisha.android.board.service.jsonparser.JSONVideoGameParser;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.android.board.video.model.Video;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoParser
extends JSONAPIResultParser<Video> {
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private static final String AUTHOR_KEY = "author";
    private static final String CUEPOINTS_KEY = "cuepoints";
    private static final String DESCRIPTION_KEY = "description";
    private static final String DURATION_KEY = "duration";
    private static final String GAMES_KEY = "games";
    private static final String INFO_KEY = "info";
    private static final String METADATA_KEY = "metadata";
    private static final String MP4_VIDEO_KEY = "mp4";
    private static final String TITLE_KEY = "title";
    private static final String VIDEO_URLS_KEY = "videoUrls";

    @Override
    public Video parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        Object object2;
        Object object3;
        CharSequence charSequence;
        String string;
        StringBuilder stringBuilder;
        Object object4;
        Object object5;
        Object object6;
        Object object7 = "";
        Object object8 = object.getJSONArray(VIDEO_URLS_KEY);
        int n = 0;
        do {
            charSequence = object7;
            if (n >= object8.length()) break;
            charSequence = object8.getJSONObject(n);
            if (charSequence.has(MP4_VIDEO_KEY)) {
                object7 = charSequence.getString(MP4_VIDEO_KEY);
                charSequence = object7;
                if (!object7.startsWith("//")) break;
                charSequence = new StringBuilder();
                charSequence.append("http:");
                charSequence.append((String)object7);
                charSequence = charSequence.toString();
                break;
            }
            ++n;
        } while (true);
        Object object9 = object.getJSONObject(INFO_KEY);
        Object object10 = object9.optJSONObject(METADATA_KEY);
        object = "";
        object7 = "";
        object8 = "";
        Object object11 = "";
        long l = 0L;
        LinkedList<Game> linkedList = null;
        Object object12 = linkedList;
        if (object10 != null) {
            object6 = object10.optString(TITLE_KEY, "");
            object5 = object10.optString(AUTHOR_KEY, "");
            object3 = object10.optString("authorTitleCode", "");
            object2 = object10.optString(DESCRIPTION_KEY, "");
            long l2 = (long)(object10.optDouble(DURATION_KEY, 0.0) * 1000.0);
            object10 = object10.optString("teaser_image_id", null);
            object = object6;
            object7 = object5;
            object8 = object3;
            object11 = object2;
            l = l2;
            object12 = linkedList;
            if (object10 != null) {
                object12 = new CishaUUID((String)object10, true);
                l = l2;
                object11 = object2;
                object8 = object3;
                object7 = object5;
                object = object6;
            }
        }
        linkedList = new LinkedList<Game>();
        object5 = object9.optJSONArray(GAMES_KEY);
        object6 = new HashMap();
        if (object5 != null) {
            object3 = new JSONVideoGameParser();
            for (n = 0; n < object5.length(); ++n) {
                object2 = object5.optJSONObject(n);
                if (object2 != null) {
                    try {
                        object10 = object3.parseResult((JSONObject)object2);
                        linkedList.add(object10.game);
                        if (object10.highlights == null) continue;
                        object6.putAll(object10.highlights);
                    }
                    catch (JSONException jSONException) {
                        object4 = Logger.getInstance();
                        string = this.getClass().getName();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error parsing game:");
                        stringBuilder.append(object2);
                        object4.error(string, stringBuilder.toString(), (Throwable)jSONException);
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        object4 = Logger.getInstance();
                        string = this.getClass().getName();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error parsing game:");
                        stringBuilder.append(object2);
                        object4.error(string, stringBuilder.toString(), invalidJsonForObjectException);
                    }
                    continue;
                }
                object2 = Logger.getInstance();
                object10 = this.getClass().getName();
                object4 = new StringBuilder();
                object4.append("error non game in json games array:");
                object4.append(object5);
                object2.error((String)object10, object4.toString());
            }
        }
        object5 = new LinkedList();
        object3 = object9.optJSONArray(CUEPOINTS_KEY);
        if (object3 != null) {
            object2 = new JSONVideoCommandParser();
            for (n = 0; n < object3.length(); ++n) {
                object9 = object3.optJSONObject(n);
                if (object9 != null) {
                    try {
                        object5.add(object2.parseResult((JSONObject)object9));
                    }
                    catch (JSONException jSONException) {
                        object4 = Logger.getInstance();
                        string = this.getClass().getName();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error parsing command:");
                        stringBuilder.append(object9);
                        object4.error(string, stringBuilder.toString(), (Throwable)jSONException);
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        object4 = Logger.getInstance();
                        string = this.getClass().getName();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error parsing command:");
                        stringBuilder.append(object9);
                        object4.error(string, stringBuilder.toString(), invalidJsonForObjectException);
                    }
                    continue;
                }
                object9 = Logger.getInstance();
                object10 = this.getClass().getName();
                object4 = new StringBuilder();
                object4.append("error non cuepoint in json cuepoint array:");
                object4.append(object3);
                object9.error((String)object10, object4.toString());
            }
        }
        return new Video((String)object, (String)object7, (String)object8, (String)object11, l, (CishaUUID)object12, Uri.parse((String)Uri.encode((String)charSequence, (String)ALLOWED_URI_CHARS)), (List<Game>)linkedList, (List<VideoCommand>)object5, (Map<MoveContainer, BoardMarks>)object6);
    }
}
