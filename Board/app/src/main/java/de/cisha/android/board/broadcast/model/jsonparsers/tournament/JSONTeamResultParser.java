// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TournamentsStandings;
import org.json.JSONObject;
import de.cisha.android.board.broadcast.model.TeamStanding;

public class JSONTeamResultParser extends JSONResultParser<TeamStanding>
{
    public static final String RESULT_BOARD_POINTS = "bp";
    public static final String RESULT_TEAM_POINTS = "mp";
    
    public JSONTeamResultParser(final String s) {
        super(s);
    }
    
    @Override
    protected TeamStanding createStandingsObject(final int n, final int n2, final int n3, final JSONObject jsonObject) {
        return new TeamStanding((float)jsonObject.optDouble("mp", 0.0), (float)jsonObject.optDouble("bp", 0.0), n, n2, n3);
    }
}
