/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAddressParser;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONNodeServerAdressRequestParser
extends JSONAPIResultParser<NodeServerAddress> {
    private static final String SERVER_KEY = "server";

    @Override
    public NodeServerAddress parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        try {
            Object object = jSONObject.getJSONObject(SERVER_KEY);
            object = new JSONNodeServerAddressParser().parseResult((JSONObject)object);
            return object;
        }
        catch (JSONException jSONException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("key server not found in result object: ");
            stringBuilder.append((Object)jSONObject);
            throw new InvalidJsonForObjectException(stringBuilder.toString(), (Throwable)jSONException);
        }
    }
}
