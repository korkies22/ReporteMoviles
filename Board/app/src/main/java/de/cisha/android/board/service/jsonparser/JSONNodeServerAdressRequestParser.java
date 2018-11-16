// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.service.NodeServerAddress;

public class JSONNodeServerAdressRequestParser extends JSONAPIResultParser<NodeServerAddress>
{
    private static final String SERVER_KEY = "server";
    
    @Override
    public NodeServerAddress parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new JSONNodeServerAddressParser().parseResult(jsonObject.getJSONObject("server"));
        }
        catch (JSONException ex) {
            final StringBuilder sb = new StringBuilder();
            sb.append("key server not found in result object: ");
            sb.append(jsonObject);
            throw new InvalidJsonForObjectException(sb.toString(), (Throwable)ex);
        }
    }
}
