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
import org.json.JSONException;
import org.json.JSONObject;

public class JSONNodeServerAddressParser
extends JSONAPIResultParser<NodeServerAddress> {
    private static final String HOST = "host";
    private static final String PORT = "port";

    @Override
    public NodeServerAddress parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        if (jSONObject == null) {
            throw new InvalidJsonForObjectException("JSONNodeServerAddressParser - dataObject was null");
        }
        try {
            NodeServerAddress nodeServerAddress = new NodeServerAddress(jSONObject.getString(HOST), jSONObject.getInt(PORT));
            return nodeServerAddress;
        }
        catch (JSONException jSONException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid Json:");
            stringBuilder.append((Object)jSONObject);
            throw new InvalidJsonForObjectException(stringBuilder.toString(), (Throwable)jSONException);
        }
    }
}
