/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLoggerUtility {
    private static final Map<GraphAPIActivityType, String> API_ACTIVITY_TYPE_TO_STRING = new HashMap<GraphAPIActivityType, String>(){
        {
            this.put(GraphAPIActivityType.MOBILE_INSTALL_EVENT, "MOBILE_APP_INSTALL");
            this.put(GraphAPIActivityType.CUSTOM_APP_EVENTS, "CUSTOM_APP_EVENTS");
        }
    };

    public static JSONObject getJSONObjectForGraphAPICall(GraphAPIActivityType object, AttributionIdentifiers attributionIdentifiers, String string, boolean bl, Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("event", (Object)API_ACTIVITY_TYPE_TO_STRING.get(object));
        object = AppEventsLogger.getUserID();
        if (object != null) {
            jSONObject.put("app_user_id", object);
        }
        Utility.setAppEventAttributionParameters(jSONObject, attributionIdentifiers, string, bl);
        try {
            Utility.setAppEventExtendedDeviceInfoParameters(jSONObject, context);
        }
        catch (Exception exception) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Fetching extended device info parameters failed: '%s'", exception.toString());
        }
        jSONObject.put("application_package_name", (Object)context.getPackageName());
        return jSONObject;
    }

    public static enum GraphAPIActivityType {
        MOBILE_INSTALL_EVENT,
        CUSTOM_APP_EVENTS;
        

        private GraphAPIActivityType() {
        }
    }

}
