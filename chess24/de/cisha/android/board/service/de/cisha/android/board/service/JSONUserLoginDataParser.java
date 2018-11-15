/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.CishaUUID;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUserLoginDataParser
extends JSONAPIResultParser<UserLoginData> {
    @Override
    public UserLoginData parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            object = object.getJSONObject("user");
            String string = object.getString("uuid");
            object = new UserLoginData(new CishaUUID(object.getString("authToken"), true), new CishaUUID(string, true));
            return object;
        }
        catch (JSONException jSONException) {
            throw new InvalidJsonForObjectException("Invalid JSON form Login:", (Throwable)jSONException);
        }
    }
}
