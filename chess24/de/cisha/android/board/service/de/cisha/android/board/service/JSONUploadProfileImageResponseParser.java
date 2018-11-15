/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUploadProfileImageResponseParser
extends JSONAPIResultParser<IProfileDataService.SetProfileImageResponse> {
    private static String IMAGE_ID = "reference_id";
    private static String IMAGE_REVISION = "rev";
    private static String IMAGE_URL = "reference_url";

    @Override
    public IProfileDataService.SetProfileImageResponse parseResult(JSONObject object) throws InvalidJsonForObjectException {
        if (object != null && object.has(IMAGE_ID)) {
            CishaUUID cishaUUID = new CishaUUID(object.optString(IMAGE_ID), true);
            Object object2 = object.optString(IMAGE_URL, "");
            String string = object.optString(IMAGE_REVISION, "");
            object = null;
            try {
                object2 = new URL((String)object2);
                object = object2;
            }
            catch (MalformedURLException malformedURLException) {
                Logger.getInstance().debug(JSONUploadProfileImageResponseParser.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            }
            return new IProfileDataService.SetProfileImageResponse(cishaUUID, (URL)object, string);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No new Image UUID found for key");
        stringBuilder.append(IMAGE_ID);
        stringBuilder.append(" in ");
        stringBuilder.append(object);
        throw new InvalidJsonForObjectException(stringBuilder.toString());
    }
}
