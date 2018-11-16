// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONArray;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class JSONVideoGetPriceCategoriesParser extends JSONAPIResultParser<Map<Integer, String>>
{
    private static final String KEY_ID = "id";
    private static final String KEY_PRICE_CATEGORIES = "priceCategories";
    private static final String KEY_PRODUCT_KEY = "product_key";
    
    @Override
    public Map<Integer, String> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        final JSONArray jsonArray = jsonObject.getJSONArray("priceCategories");
        for (int i = 0; i < jsonArray.length(); ++i) {
            final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            final int optInt = jsonObject2.optInt("id", -1);
            final String optString = jsonObject2.optString("product_key");
            if (optInt >= 0 && !optString.isEmpty()) {
                treeMap.put(optInt, optString);
            }
        }
        return treeMap;
    }
}
