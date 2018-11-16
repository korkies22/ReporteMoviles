// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONDashboardDataParser extends JSONAPIResultParser<DashboardData>
{
    private static final String GAME_HISTORY_WIDGET_KEY = "GameHistoryWidget";
    private static final String TACTICS_TRAINER_WIDGET_KEY = "TacticsTrainerWidget";
    
    @Override
    public DashboardData parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new DashboardData(new JSONTacticsWidgetDataParser().parseResult(jsonObject.getJSONObject("TacticsTrainerWidget")), new JSONPlayzoneWidgetDataParser().parseResult(jsonObject.getJSONObject("GameHistoryWidget")));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(JSONDashboardDataParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            final StringBuilder sb = new StringBuilder();
            sb.append("JSONDashboardDataParser: Error parsing dataObject: ");
            sb.append(jsonObject);
            throw new InvalidJsonForObjectException(sb.toString(), (Throwable)ex);
        }
    }
}
