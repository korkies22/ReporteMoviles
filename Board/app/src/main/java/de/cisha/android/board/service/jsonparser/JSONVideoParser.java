// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONArray;
import java.util.List;
import android.net.Uri;
import de.cisha.android.board.video.command.VideoCommand;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import java.util.Map;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.MoveContainer;
import java.util.HashMap;
import de.cisha.chess.model.Game;
import java.util.LinkedList;
import de.cisha.chess.model.CishaUUID;
import org.json.JSONObject;
import de.cisha.android.board.video.model.Video;

public class JSONVideoParser extends JSONAPIResultParser<Video>
{
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
    public Video parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final String s = "";
        final JSONArray jsonArray = jsonObject.getJSONArray("videoUrls");
        int n = 0;
        String s2;
        while (true) {
            s2 = s;
            if (n >= jsonArray.length()) {
                break;
            }
            final JSONObject jsonObject2 = jsonArray.getJSONObject(n);
            if (jsonObject2.has("mp4")) {
                final String s3 = s2 = jsonObject2.getString("mp4");
                if (s3.startsWith("//")) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("http:");
                    sb.append(s3);
                    s2 = sb.toString();
                    break;
                }
                break;
            }
            else {
                ++n;
            }
        }
        final JSONObject jsonObject3 = jsonObject.getJSONObject("info");
        final JSONObject optJSONObject = jsonObject3.optJSONObject("metadata");
        jsonObject = (JSONObject)"";
        String s4 = "";
        String s5 = "";
        String s6 = "";
        long n2 = 0L;
        CishaUUID cishaUUID = null;
        if (optJSONObject != null) {
            final Object optString = optJSONObject.optString("title", "");
            final String optString2 = optJSONObject.optString("author", "");
            final String optString3 = optJSONObject.optString("authorTitleCode", "");
            final String optString4 = optJSONObject.optString("description", "");
            final long n3 = (long)(optJSONObject.optDouble("duration", 0.0) * 1000.0);
            final String optString5 = optJSONObject.optString("teaser_image_id", (String)null);
            jsonObject = (JSONObject)optString;
            s4 = optString2;
            s5 = optString3;
            s6 = optString4;
            n2 = n3;
            cishaUUID = cishaUUID;
            if (optString5 != null) {
                cishaUUID = new CishaUUID(optString5, true);
                n2 = n3;
                s6 = optString4;
                s5 = optString3;
                s4 = optString2;
                jsonObject = (JSONObject)optString;
            }
        }
        final LinkedList<Game> list = new LinkedList<Game>();
        final JSONArray optJSONArray = jsonObject3.optJSONArray("games");
        final HashMap<MoveContainer, BoardMarks> hashMap = new HashMap<MoveContainer, BoardMarks>();
        if (optJSONArray != null) {
            final JSONVideoGameParser jsonVideoGameParser = new JSONVideoGameParser();
            for (int i = 0; i < optJSONArray.length(); ++i) {
                final JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                if (optJSONObject2 != null) {
                    try {
                        final GameAndHighlights result = jsonVideoGameParser.parseResult(optJSONObject2);
                        list.add(result.game);
                        if (result.highlights != null) {
                            hashMap.putAll((Map<?, ?>)result.highlights);
                        }
                    }
                    catch (JSONException ex) {
                        final Logger instance = Logger.getInstance();
                        final String name = this.getClass().getName();
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append("error parsing game:");
                        sb2.append(optJSONObject2);
                        instance.error(name, sb2.toString(), (Throwable)ex);
                    }
                    catch (InvalidJsonForObjectException ex2) {
                        final Logger instance2 = Logger.getInstance();
                        final String name2 = this.getClass().getName();
                        final StringBuilder sb3 = new StringBuilder();
                        sb3.append("error parsing game:");
                        sb3.append(optJSONObject2);
                        instance2.error(name2, sb3.toString(), ex2);
                    }
                }
                else {
                    final Logger instance3 = Logger.getInstance();
                    final String name3 = this.getClass().getName();
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append("error non game in json games array:");
                    sb4.append(optJSONArray);
                    instance3.error(name3, sb4.toString());
                }
            }
        }
        final LinkedList<VideoCommand> list2 = new LinkedList<VideoCommand>();
        final JSONArray optJSONArray2 = jsonObject3.optJSONArray("cuepoints");
        if (optJSONArray2 != null) {
            final JSONVideoCommandParser jsonVideoCommandParser = new JSONVideoCommandParser();
            for (int j = 0; j < optJSONArray2.length(); ++j) {
                final JSONObject optJSONObject3 = optJSONArray2.optJSONObject(j);
                if (optJSONObject3 != null) {
                    try {
                        list2.add(jsonVideoCommandParser.parseResult(optJSONObject3));
                    }
                    catch (JSONException ex3) {
                        final Logger instance4 = Logger.getInstance();
                        final String name4 = this.getClass().getName();
                        final StringBuilder sb5 = new StringBuilder();
                        sb5.append("error parsing command:");
                        sb5.append(optJSONObject3);
                        instance4.error(name4, sb5.toString(), (Throwable)ex3);
                    }
                    catch (InvalidJsonForObjectException ex4) {
                        final Logger instance5 = Logger.getInstance();
                        final String name5 = this.getClass().getName();
                        final StringBuilder sb6 = new StringBuilder();
                        sb6.append("error parsing command:");
                        sb6.append(optJSONObject3);
                        instance5.error(name5, sb6.toString(), ex4);
                    }
                }
                else {
                    final Logger instance6 = Logger.getInstance();
                    final String name6 = this.getClass().getName();
                    final StringBuilder sb7 = new StringBuilder();
                    sb7.append("error non cuepoint in json cuepoint array:");
                    sb7.append(optJSONArray2);
                    instance6.error(name6, sb7.toString());
                }
            }
        }
        return new Video((String)jsonObject, s4, s5, s6, n2, cishaUUID, Uri.parse(Uri.encode(s2, "@#&=*+-_.,:!?()/~'%")), list, list2, hashMap);
    }
}
