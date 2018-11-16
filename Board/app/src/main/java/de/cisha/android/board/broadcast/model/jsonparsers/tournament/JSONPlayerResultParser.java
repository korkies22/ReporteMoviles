// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TournamentsStandings;
import org.json.JSONObject;
import de.cisha.android.board.broadcast.model.PlayerStanding;

public class JSONPlayerResultParser extends JSONResultParser<PlayerStanding>
{
    public JSONPlayerResultParser(final String s) {
        super(s);
    }
    
    @Override
    protected PlayerStanding createStandingsObject(final int n, final int n2, final int n3, final JSONObject jsonObject) {
        return new PlayerStanding((float)jsonObject.optDouble("p", 0.0), n, n2, n3);
    }
}
