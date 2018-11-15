/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile.model;

import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.profile.model.JSONPlayzoneWidgetDataParser;
import de.cisha.android.board.profile.model.JSONTacticsWidgetDataParser;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONDashboardDataParser
extends JSONAPIResultParser<DashboardData> {
    private static final String GAME_HISTORY_WIDGET_KEY = "GameHistoryWidget";
    private static final String TACTICS_TRAINER_WIDGET_KEY = "TacticsTrainerWidget";

    @Override
    public DashboardData parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        try {
            Object object = new JSONPlayzoneWidgetDataParser().parseResult(jSONObject.getJSONObject(GAME_HISTORY_WIDGET_KEY));
            object = new DashboardData(new JSONTacticsWidgetDataParser().parseResult(jSONObject.getJSONObject(TACTICS_TRAINER_WIDGET_KEY)), (PlayzoneStatisticData)object);
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(JSONDashboardDataParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JSONDashboardDataParser: Error parsing dataObject: ");
            stringBuilder.append((Object)jSONObject);
            throw new InvalidJsonForObjectException(stringBuilder.toString(), (Throwable)jSONException);
        }
    }
}
