// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.model;

import org.json.JSONException;
import java.util.Date;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONAfterPurchaseInformationParser extends JSONAPIResultParser<AfterPurchaseInformation>
{
    public static final String EXPIRATION_SECONDS_KEY = "expires";
    public static final String EXTENSION_SECONDS_KEY = "variation";
    
    @Override
    public AfterPurchaseInformation parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        return new AfterPurchaseInformation(jsonObject.optLong("variation") * 1000L, new Date(new Date().getTime() + jsonObject.optLong("expires") * 1000L));
    }
}
