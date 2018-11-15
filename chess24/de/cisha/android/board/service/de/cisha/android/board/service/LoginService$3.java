/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import org.json.JSONException;
import org.json.JSONObject;

class LoginService
extends JSONAPIResultParser<Boolean> {
    LoginService() {
    }

    @Override
    public Boolean parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        int n = jSONObject.optInt("valid", -1);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }
}
