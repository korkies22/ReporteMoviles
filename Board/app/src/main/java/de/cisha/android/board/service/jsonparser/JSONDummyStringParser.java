// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONDummyStringParser extends JSONAPIResultParser<String>
{
    @Override
    public String parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("data object was null");
        }
        return jsonObject.toString();
    }
}
