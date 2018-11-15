/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.account.model;

import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAfterPurchaseInformationParser
extends JSONAPIResultParser<AfterPurchaseInformation> {
    public static final String EXPIRATION_SECONDS_KEY = "expires";
    public static final String EXTENSION_SECONDS_KEY = "variation";

    @Override
    public AfterPurchaseInformation parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        if (jSONObject == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        long l = jSONObject.optLong(EXPIRATION_SECONDS_KEY);
        return new AfterPurchaseInformation(jSONObject.optLong(EXTENSION_SECONDS_KEY) * 1000L, new Date(new Date().getTime() + l * 1000L));
    }
}
