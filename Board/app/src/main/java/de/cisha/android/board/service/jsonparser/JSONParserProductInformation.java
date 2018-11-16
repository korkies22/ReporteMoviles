// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONArray;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.account.model.ProductInformation;
import java.util.List;

public class JSONParserProductInformation extends JSONAPIResultParser<List<ProductInformation>>
{
    public static final String IDENTIFIERS = "identifiers";
    public static final String IDENTIFIER_NAME = "name";
    public static final String IDENTIFIER_SECONDS = "duration";
    
    @Override
    public List<ProductInformation> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final LinkedList<ProductInformation> list = new LinkedList<ProductInformation>();
        final JSONArray optJSONArray = jsonObject.optJSONArray("identifiers");
        for (int length = optJSONArray.length(), i = 0; i < length; ++i) {
            final JSONObject jsonObject2 = optJSONArray.getJSONObject(i);
            final String optString = jsonObject2.optString("name", "");
            final int optInt = jsonObject2.optInt("duration", 0);
            final int optInt2 = jsonObject2.optInt("type", 0);
            final boolean optBoolean = this.optBoolean(jsonObject2, "is_recurring", false);
            final int round = Math.round(optInt / 2635200.0f);
            boolean b = true;
            if (optInt2 != 1) {
                b = false;
            }
            list.add(new ProductInformation(optString, round, optBoolean, b));
        }
        return list;
    }
}
