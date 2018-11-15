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

public class JSONGetNodeServerParser
extends JSONAPIResultParser<NodeServerAddress> {
    private static final String SERVER_OBJECT = "server";

    @Override
    public NodeServerAddress parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        try {
            jSONObject = jSONObject.getJSONObject(SERVER_OBJECT);
        }
        catch (JSONException jSONException) {
            throw new InvalidJsonForObjectException((Throwable)jSONException);
        }
        return new JSONNodeServerAddressParser().parseResult(jSONObject);
    }
}
