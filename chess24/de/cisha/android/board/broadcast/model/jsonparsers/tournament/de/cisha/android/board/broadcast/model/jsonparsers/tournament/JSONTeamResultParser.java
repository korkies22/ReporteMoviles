/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentsStandings;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONResultParser;
import org.json.JSONObject;

public class JSONTeamResultParser
extends JSONResultParser<TeamStanding> {
    public static final String RESULT_BOARD_POINTS = "bp";
    public static final String RESULT_TEAM_POINTS = "mp";

    public JSONTeamResultParser(String string) {
        super(string);
    }

    @Override
    protected TeamStanding createStandingsObject(int n, int n2, int n3, JSONObject jSONObject) {
        float f = (float)jSONObject.optDouble(RESULT_BOARD_POINTS, 0.0);
        return new TeamStanding((float)jSONObject.optDouble(RESULT_TEAM_POINTS, 0.0), f, n, n2, n3);
    }
}
