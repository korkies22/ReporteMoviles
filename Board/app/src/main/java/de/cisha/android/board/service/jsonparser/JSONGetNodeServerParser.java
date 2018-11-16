// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.service.NodeServerAddress;

public class JSONGetNodeServerParser extends JSONAPIResultParser<NodeServerAddress>
{
    private static final String SERVER_OBJECT = "server";
    
    @Override
    public NodeServerAddress parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            jsonObject = jsonObject.getJSONObject("server");
            return new JSONNodeServerAddressParser().parseResult(jsonObject);
        }
        catch (JSONException ex) {
            throw new InvalidJsonForObjectException((Throwable)ex);
        }
    }
}
