// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import java.net.URL;
import de.cisha.chess.model.CishaUUID;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONUploadProfileImageResponseParser extends JSONAPIResultParser<IProfileDataService.SetProfileImageResponse>
{
    private static String IMAGE_ID = "reference_id";
    private static String IMAGE_REVISION = "rev";
    private static String IMAGE_URL = "reference_url";
    
    @Override
    public IProfileDataService.SetProfileImageResponse parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException {
        if (jsonObject != null && jsonObject.has(JSONUploadProfileImageResponseParser.IMAGE_ID)) {
            final CishaUUID cishaUUID = new CishaUUID(jsonObject.optString(JSONUploadProfileImageResponseParser.IMAGE_ID), true);
            final String optString = jsonObject.optString(JSONUploadProfileImageResponseParser.IMAGE_URL, "");
            final String optString2 = jsonObject.optString(JSONUploadProfileImageResponseParser.IMAGE_REVISION, "");
            jsonObject = null;
            try {
                jsonObject = (JSONObject)new URL(optString);
            }
            catch (MalformedURLException ex) {
                Logger.getInstance().debug(JSONUploadProfileImageResponseParser.class.getName(), MalformedURLException.class.getName(), ex);
            }
            return new IProfileDataService.SetProfileImageResponse(cishaUUID, (URL)jsonObject, optString2);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("No new Image UUID found for key");
        sb.append(JSONUploadProfileImageResponseParser.IMAGE_ID);
        sb.append(" in ");
        sb.append(jsonObject);
        throw new InvalidJsonForObjectException(sb.toString());
    }
}
