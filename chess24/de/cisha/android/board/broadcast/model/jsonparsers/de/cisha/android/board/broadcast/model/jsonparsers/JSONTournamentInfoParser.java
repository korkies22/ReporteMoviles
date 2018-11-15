/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers;

import de.cisha.android.board.broadcast.model.MainKnockoutRoundInfo;
import de.cisha.android.board.broadcast.model.MultiGameKnockOutTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.SimpleTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTournamentInfoParser
extends JSONAPIResultParser<TournamentInfo> {
    private String _language = "en";
    private long _serverTimeDiff;

    public JSONTournamentInfoParser(long l, String string) {
        if (string != null) {
            this._language = string;
        }
        this._serverTimeDiff = l;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public TournamentInfo parseResult(JSONObject object) throws InvalidJsonForObjectException {
        Object object2;
        TournamentID tournamentID;
        void var11_36;
        void var10_25;
        void var12_33;
        boolean bl;
        void var1_3;
        block20 : {
            void var10_19;
            block19 : {
                Object var12_30;
                try {
                    URL uRL;
                    void var10_10;
                    tournamentID = new TournamentID(object.getString("rkeyOfDetailedRecord"));
                    JSONObject jSONObject = object.optJSONObject("titles");
                    var12_30 = null;
                    if (jSONObject != null) {
                        String string = jSONObject.optString(this._language, null);
                    } else {
                        Object var10_28 = null;
                    }
                    void var11_34 = var10_10;
                    if (var10_10 == null) {
                        String string = object.optString("title");
                    }
                    object2 = object.optString("logo", "false");
                    bl = "false".equals(object2);
                    Object var10_11 = var12_30;
                    if (bl) break block19;
                    URL uRL2 = uRL = new URL((String)object2);
                    break block20;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().debug(JSONTournamentInfoParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                    throw new InvalidJsonForObjectException("Error parsing Tournament Info JSON:", (Throwable)jSONException);
                }
                catch (MalformedURLException malformedURLException) {}
                Object var10_14 = var12_30;
                if (object2 != null) {
                    Object var10_15 = var12_30;
                    try {
                        if (!object2.isEmpty()) {
                            Object var10_16 = var12_30;
                            if (!object2.startsWith("http")) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("https://chess24.com/");
                                stringBuilder.append((String)object2);
                                URL uRL = new URL(stringBuilder.toString());
                            }
                        }
                    }
                    catch (MalformedURLException malformedURLException) {
                        Object var10_27 = var12_30;
                    }
                }
            }
            void var12_32 = var10_19;
        }
        int n = object.optInt("numberOfRounds");
        bl = object.optBoolean("videoAvailable", false);
        int n2 = object.optInt("runningGames");
        int n3 = object.optInt("finishedGames");
        object2 = object.optString("status");
        long l = object.optLong("startOfNextGames");
        l = l == 0L ? System.currentTimeMillis() + 3600000L : (l -= this._serverTimeDiff);
        TournamentState tournamentState = TournamentState.ONGOING;
        if ("upcoming".equals(object2)) {
            TournamentState tournamentState2 = TournamentState.UPCOMING;
        } else if ("finished".equals(object2)) {
            TournamentState tournamentState3 = TournamentState.FINISHED;
        } else if ("paused".equals(object2)) {
            TournamentState tournamentState4 = TournamentState.PAUSED;
        }
        int n4 = object.optInt("currentRound");
        object2 = new SimpleTournamentRoundInfo(n4);
        BroadcastTournamentType broadcastTournamentType = BroadcastTournamentType.fromKey(object.optString("eventType", ""));
        if (BroadcastTournamentType.MULTIKNOCKOUT == broadcastTournamentType) {
            int n5 = object.optInt("currentGame");
            MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo = new MultiGameKnockOutTournamentRoundInfo(new MainKnockoutRoundInfo(n4), n5);
            return new TournamentInfo(tournamentID, (String)var11_36, n, (TournamentRoundInfo)var1_3, n2, n3, (URL)var12_33, bl, (TournamentState)var10_25, new Date(l));
        } else {
            Object object3 = object2;
        }
        return new TournamentInfo(tournamentID, (String)var11_36, n, (TournamentRoundInfo)var1_3, n2, n3, (URL)var12_33, bl, (TournamentState)var10_25, new Date(l));
    }
}
