// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.util.Iterator;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class SetUserDataResponseParser extends JSONAPIResultParser<Map<String, String>>
{
    @Override
    public Map<String, String> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        final Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            final String s = keys.next();
            treeMap.put(s, jsonObject.optString(s));
        }
        return treeMap;
    }
}
