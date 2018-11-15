/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.model.FEN;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONAPIResultParser<E> {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean optBoolean(JSONObject object, String string, boolean bl) {
        try {
            return object.getBoolean(string);
        }
        catch (JSONException jSONException) {}
        Object object2 = object.opt(string);
        int n = object.optInt(string, -1);
        if (object2 == JSONObject.NULL) return false;
        if (n == 0) {
            return false;
        }
        try {
            object = object.getString(string);
            if (object.equalsIgnoreCase("false")) return false;
            if (object.equals("0")) return false;
            boolean bl2 = object.equals("");
            if (!bl2) return true;
            return false;
        }
        catch (JSONException jSONException) {
            return bl;
        }
    }

    protected FEN optFEN(JSONObject object, String string, FEN fEN) {
        string = this.optStringNotNull((JSONObject)object, string, null);
        object = fEN;
        if (string != null) {
            object = fEN;
            if (!"".equals(string.trim())) {
                object = new FEN(string);
            }
        }
        return object;
    }

    protected String optStringNotNull(JSONObject object, String string, String string2) {
        Object object2 = object.opt(string);
        object = object.optString(string, string2);
        if (object2 == null || object2 == JSONObject.NULL) {
            object = string2;
        }
        return object;
    }

    public abstract E parseResult(JSONObject var1) throws InvalidJsonForObjectException, JSONException;
}
