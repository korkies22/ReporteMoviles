/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONDummyStringParser
extends JSONAPIResultParser<String> {
    @Override
    public String parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        if (jSONObject == null) {
            throw new InvalidJsonForObjectException("data object was null");
        }
        return jSONObject.toString();
    }
}
