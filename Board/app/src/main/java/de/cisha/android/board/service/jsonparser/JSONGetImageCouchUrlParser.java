// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import java.net.URL;
import org.json.JSONObject;
import de.cisha.android.board.CouchUrl;

public class JSONGetImageCouchUrlParser extends JSONAPIResultParser<CouchUrl>
{
    @Override
    public CouchUrl parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new CouchUrl(new URL(jsonObject.getString("url")), jsonObject.getString("rev"));
        }
        catch (MalformedURLException ex) {
            Logger.getInstance().debug(JSONGetImageCouchUrlParser.class.getName(), MalformedURLException.class.getName(), ex);
            throw new InvalidJsonForObjectException("url format seems to be wrong", ex);
        }
        catch (JSONException ex2) {
            Logger.getInstance().error(JSONGetImageCouchUrlParser.class.getName(), JSONException.class.getName(), (Throwable)ex2);
            throw new InvalidJsonForObjectException("attribute not set in json", (Throwable)ex2);
        }
    }
}
