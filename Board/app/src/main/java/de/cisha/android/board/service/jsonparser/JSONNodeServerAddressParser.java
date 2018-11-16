// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.service.NodeServerAddress;

public class JSONNodeServerAddressParser extends JSONAPIResultParser<NodeServerAddress>
{
    private static final String HOST = "host";
    private static final String PORT = "port";
    
    @Override
    public NodeServerAddress parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("JSONNodeServerAddressParser - dataObject was null");
        }
        try {
            return new NodeServerAddress(jsonObject.getString("host"), jsonObject.getInt("port"));
        }
        catch (JSONException ex) {
            final StringBuilder sb = new StringBuilder();
            sb.append("invalid Json:");
            sb.append(jsonObject);
            throw new InvalidJsonForObjectException(sb.toString(), (Throwable)ex);
        }
    }
}
