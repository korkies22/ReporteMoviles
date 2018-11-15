/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParserProductInformation
extends JSONAPIResultParser<List<ProductInformation>> {
    public static final String IDENTIFIERS = "identifiers";
    public static final String IDENTIFIER_NAME = "name";
    public static final String IDENTIFIER_SECONDS = "duration";

    @Override
    public List<ProductInformation> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        LinkedList<ProductInformation> linkedList = new LinkedList<ProductInformation>();
        jSONObject = jSONObject.optJSONArray(IDENTIFIERS);
        int n = jSONObject.length();
        for (int i = 0; i < n; ++i) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(i);
            String string = jSONObject2.optString(IDENTIFIER_NAME, "");
            int n2 = jSONObject2.optInt(IDENTIFIER_SECONDS, 0);
            int n3 = jSONObject2.optInt("type", 0);
            boolean bl = this.optBoolean(jSONObject2, "is_recurring", false);
            n2 = Math.round((float)n2 / 2635200.0f);
            boolean bl2 = true;
            if (n3 != 1) {
                bl2 = false;
            }
            linkedList.add(new ProductInformation(string, n2, bl, bl2));
        }
        return linkedList;
    }
}
