/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.CouchUrl;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGetImageCouchUrlParser
extends JSONAPIResultParser<CouchUrl> {
    @Override
    public CouchUrl parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            String string = object.getString("url");
            object = object.getString("rev");
            object = new CouchUrl(new URL(string), (String)object);
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            Logger.getInstance().debug(JSONGetImageCouchUrlParser.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            throw new InvalidJsonForObjectException("url format seems to be wrong", malformedURLException);
        }
        catch (JSONException jSONException) {
            Logger.getInstance().error(JSONGetImageCouchUrlParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("attribute not set in json", (Throwable)jSONException);
        }
    }
}
