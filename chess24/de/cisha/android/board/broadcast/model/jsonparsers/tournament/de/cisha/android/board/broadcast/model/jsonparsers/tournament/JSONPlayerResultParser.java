/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.PlayerStanding;
import de.cisha.android.board.broadcast.model.TournamentsStandings;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONResultParser;
import org.json.JSONObject;

public class JSONPlayerResultParser
extends JSONResultParser<PlayerStanding> {
    public JSONPlayerResultParser(String string) {
        super(string);
    }

    @Override
    protected PlayerStanding createStandingsObject(int n, int n2, int n3, JSONObject jSONObject) {
        return new PlayerStanding((float)jSONObject.optDouble("p", 0.0), n, n2, n3);
    }
}
