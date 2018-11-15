/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonmapping;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONAPIMapping<T> {
    public JSONObject mapToJSON(T var1) throws JSONException;
}
