// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.model.CishaUUID;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONUserLoginDataParser extends JSONAPIResultParser<UserLoginData>
{
    @Override
    public UserLoginData parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            jsonObject = jsonObject.getJSONObject("user");
            return new UserLoginData(new CishaUUID(jsonObject.getString("authToken"), true), new CishaUUID(jsonObject.getString("uuid"), true));
        }
        catch (JSONException ex) {
            throw new InvalidJsonForObjectException("Invalid JSON form Login:", (Throwable)ex);
        }
    }
}
