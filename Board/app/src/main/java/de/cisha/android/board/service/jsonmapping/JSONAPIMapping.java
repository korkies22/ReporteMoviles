// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonmapping;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONAPIMapping<T>
{
    JSONObject mapToJSON(final T p0) throws JSONException;
}
