/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoGetPriceCategoriesParser
extends JSONAPIResultParser<Map<Integer, String>> {
    private static final String KEY_ID = "id";
    private static final String KEY_PRICE_CATEGORIES = "priceCategories";
    private static final String KEY_PRODUCT_KEY = "product_key";

    @Override
    public Map<Integer, String> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        jSONObject = jSONObject.getJSONArray(KEY_PRICE_CATEGORIES);
        for (int i = 0; i < jSONObject.length(); ++i) {
            Object object = jSONObject.getJSONObject(i);
            int n = object.optInt(KEY_ID, -1);
            object = object.optString(KEY_PRODUCT_KEY);
            if (n < 0 || object.isEmpty()) continue;
            treeMap.put(n, (String)object);
        }
        return treeMap;
    }
}
