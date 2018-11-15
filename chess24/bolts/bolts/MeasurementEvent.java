/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package bolts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class MeasurementEvent {
    public static final String APP_LINK_NAVIGATE_IN_EVENT_NAME = "al_nav_in";
    public static final String APP_LINK_NAVIGATE_OUT_EVENT_NAME = "al_nav_out";
    public static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    public static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    public static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private Context appContext;
    private Bundle args;
    private String name;

    private MeasurementEvent(Context context, String string, Bundle bundle) {
        this.appContext = context.getApplicationContext();
        this.name = string;
        this.args = bundle;
    }

    private static Bundle getApplinkLogData(Context object, String string2, Bundle bundle, Intent object2) {
        Bundle bundle2 = new Bundle();
        if ((object = object2.resolveActivity(object.getPackageManager())) != null) {
            bundle2.putString("class", object.getShortClassName());
        }
        if (APP_LINK_NAVIGATE_OUT_EVENT_NAME.equals(string2)) {
            if (object != null) {
                bundle2.putString("package", object.getPackageName());
            }
            if (object2.getData() != null) {
                bundle2.putString("outputURL", object2.getData().toString());
            }
            if (object2.getScheme() != null) {
                bundle2.putString("outputURLScheme", object2.getScheme());
            }
        } else if (APP_LINK_NAVIGATE_IN_EVENT_NAME.equals(string2)) {
            if (object2.getData() != null) {
                bundle2.putString("inputURL", object2.getData().toString());
            }
            if (object2.getScheme() != null) {
                bundle2.putString("inputURLScheme", object2.getScheme());
            }
        }
        for (String string2 : bundle.keySet()) {
            object2 = bundle.get(string2);
            if (object2 instanceof Bundle) {
                object2 = (Bundle)object2;
                for (String string3 : object2.keySet()) {
                    String string4 = MeasurementEvent.objectToJSONString(object2.get(string3));
                    if (string2.equals("referer_app_link")) {
                        if (string3.equalsIgnoreCase("url")) {
                            bundle2.putString("refererURL", string4);
                            continue;
                        }
                        if (string3.equalsIgnoreCase("app_name")) {
                            bundle2.putString("refererAppName", string4);
                            continue;
                        }
                        if (string3.equalsIgnoreCase("package")) {
                            bundle2.putString("sourceApplication", string4);
                            continue;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append("/");
                    stringBuilder.append(string3);
                    bundle2.putString(stringBuilder.toString(), string4);
                }
                continue;
            }
            object2 = MeasurementEvent.objectToJSONString(object2);
            if (string2.equals("target_url")) {
                string2 = Uri.parse((String)object2);
                bundle2.putString("targetURL", string2.toString());
                bundle2.putString("targetURLHost", string2.getHost());
                continue;
            }
            bundle2.putString(string2, (String)object2);
        }
        return bundle2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String objectToJSONString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof JSONArray) return object.toString();
        if (object instanceof JSONObject) return object.toString();
        try {
            if (object instanceof Collection) {
                return new JSONArray((Collection)object).toString();
            }
            if (!(object instanceof Map)) return object.toString();
            return new JSONObject((Map)object).toString();
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendBroadcast() {
        if (this.name == null) {
            Log.d((String)this.getClass().getName(), (String)"Event name is required");
        }
        try {
            GenericDeclaration genericDeclaration = Class.forName("android.support.v4.content.LocalBroadcastManager");
            Object object = genericDeclaration.getMethod("getInstance", Context.class);
            genericDeclaration = genericDeclaration.getMethod("sendBroadcast", Intent.class);
            object = object.invoke(null, new Object[]{this.appContext});
            Intent intent = new Intent(MEASUREMENT_EVENT_NOTIFICATION_NAME);
            intent.putExtra(MEASUREMENT_EVENT_NAME_KEY, this.name);
            intent.putExtra(MEASUREMENT_EVENT_ARGS_KEY, this.args);
            genericDeclaration.invoke(object, new Object[]{intent});
            return;
        }
        catch (Exception exception) {}
        Log.d((String)this.getClass().getName(), (String)"LocalBroadcastManager in android support library is required to raise bolts event.");
    }

    static void sendBroadcastEvent(Context context, String string, Intent object, Map<String, String> map) {
        Bundle object22;
        Object object2 = object22 = new Bundle();
        if (object != null) {
            object2 = AppLinks.getAppLinkData((Intent)object);
            if (object2 != null) {
                object2 = MeasurementEvent.getApplinkLogData(context, string, object2, (Intent)object);
            } else {
                object2 = object.getData();
                if (object2 != null) {
                    object22.putString("intentData", object2.toString());
                }
                object = object.getExtras();
                object2 = object22;
                if (object != null) {
                    Iterator iterator = object.keySet().iterator();
                    do {
                        object2 = object22;
                        if (!iterator.hasNext()) break;
                        object2 = (String)iterator.next();
                        object22.putString((String)object2, MeasurementEvent.objectToJSONString(object.get((String)object2)));
                    } while (true);
                }
            }
        }
        if (map != null) {
            for (String string2 : map.keySet()) {
                object2.putString(string2, map.get(string2));
            }
        }
        new MeasurementEvent(context, string, (Bundle)object2).sendBroadcast();
    }
}
