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
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;

public class SetUserDataResponseParser
extends JSONAPIResultParser<Map<String, String>> {
    @Override
    public Map<String, String> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            treeMap.put(string, jSONObject.optString(string));
        }
        return treeMap;
    }
}
