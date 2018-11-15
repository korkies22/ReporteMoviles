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

public class JSONAfterPurchaseInformationPriceTierParser
extends JSONAPIResultParser<AfterPurchaseInformation> {
    @Override
    public AfterPurchaseInformation parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        if (jSONObject == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        return new AfterPurchaseInformation(0L, null);
    }
}
