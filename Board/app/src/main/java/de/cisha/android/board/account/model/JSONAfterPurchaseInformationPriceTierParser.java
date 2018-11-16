// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.model;

import org.json.JSONException;
import java.util.Date;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONAfterPurchaseInformationPriceTierParser extends JSONAPIResultParser<AfterPurchaseInformation>
{
    @Override
    public AfterPurchaseInformation parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        return new AfterPurchaseInformation(0L, null);
    }
}
