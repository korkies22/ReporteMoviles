/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Parcelable
 */
package com.facebook.messenger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import bolts.AppLinks;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookSignatureValidator;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.ShareToMessengerParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerUtils {
    public static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
    public static final String EXTRA_EXTERNAL_URI = "com.facebook.orca.extra.EXTERNAL_URI";
    public static final String EXTRA_IS_COMPOSE = "com.facebook.orca.extra.IS_COMPOSE";
    public static final String EXTRA_IS_REPLY = "com.facebook.orca.extra.IS_REPLY";
    public static final String EXTRA_METADATA = "com.facebook.orca.extra.METADATA";
    public static final String EXTRA_PARTICIPANTS = "com.facebook.orca.extra.PARTICIPANTS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
    public static final String EXTRA_REPLY_TOKEN_KEY = "com.facebook.orca.extra.REPLY_TOKEN";
    public static final String EXTRA_THREAD_TOKEN_KEY = "com.facebook.orca.extra.THREAD_TOKEN";
    public static final String ORCA_THREAD_CATEGORY_20150314 = "com.facebook.orca.category.PLATFORM_THREAD_20150314";
    public static final String PACKAGE_NAME = "com.facebook.orca";
    public static final int PROTOCOL_VERSION_20150314 = 20150314;
    private static final String TAG = "MessengerUtils";

    public static void finishShareToMessenger(Activity activity, ShareToMessengerParams shareToMessengerParams) {
        Intent intent = activity.getIntent();
        Set set = intent.getCategories();
        if (set == null) {
            activity.setResult(0, null);
            activity.finish();
            return;
        }
        if (set.contains(ORCA_THREAD_CATEGORY_20150314)) {
            intent = AppLinks.getAppLinkExtras(intent);
            Intent intent2 = new Intent();
            if (set.contains(ORCA_THREAD_CATEGORY_20150314)) {
                intent2.putExtra(EXTRA_PROTOCOL_VERSION, 20150314);
                intent2.putExtra(EXTRA_THREAD_TOKEN_KEY, intent.getString(EXTRA_THREAD_TOKEN_KEY));
                intent2.setDataAndType(shareToMessengerParams.uri, shareToMessengerParams.mimeType);
                intent2.setFlags(1);
                intent2.putExtra(EXTRA_APP_ID, FacebookSdk.getApplicationId());
                intent2.putExtra(EXTRA_METADATA, shareToMessengerParams.metaData);
                intent2.putExtra(EXTRA_EXTERNAL_URI, (Parcelable)shareToMessengerParams.externalUri);
                activity.setResult(-1, intent2);
                activity.finish();
                return;
            }
            throw new RuntimeException();
        }
        activity.setResult(0, null);
        activity.finish();
    }

    private static Set<Integer> getAllAvailableProtocolVersions(Context object) {
        ContentResolver contentResolver = object.getContentResolver();
        object = new HashSet();
        if ((contentResolver = contentResolver.query(Uri.parse((String)"content://com.facebook.orca.provider.MessengerPlatformProvider/versions"), new String[]{"version"}, null, null, null)) != null) {
            try {
                int n = contentResolver.getColumnIndex("version");
                while (contentResolver.moveToNext()) {
                    object.add(contentResolver.getInt(n));
                }
                return object;
            }
            finally {
                contentResolver.close();
            }
        }
        return object;
    }

    public static MessengerThreadParams getMessengerThreadParamsForIntent(Intent object) {
        Object object2 = object.getCategories();
        if (object2 == null) {
            return null;
        }
        if (object2.contains(ORCA_THREAD_CATEGORY_20150314)) {
            object = AppLinks.getAppLinkExtras(object);
            object2 = object.getString(EXTRA_THREAD_TOKEN_KEY);
            String string = object.getString(EXTRA_METADATA);
            String string2 = object.getString(EXTRA_PARTICIPANTS);
            boolean bl = object.getBoolean(EXTRA_IS_REPLY);
            boolean bl2 = object.getBoolean(EXTRA_IS_COMPOSE);
            object = MessengerThreadParams.Origin.UNKNOWN;
            if (bl) {
                object = MessengerThreadParams.Origin.REPLY_FLOW;
            } else if (bl2) {
                object = MessengerThreadParams.Origin.COMPOSE_FLOW;
            }
            return new MessengerThreadParams((MessengerThreadParams.Origin)((Object)object), (String)object2, string, MessengerUtils.parseParticipants(string2));
        }
        return null;
    }

    public static boolean hasMessengerInstalled(Context context) {
        return FacebookSignatureValidator.validateSignature(context, PACKAGE_NAME);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void openMessengerInPlayStore(Context context) {
        try {
            MessengerUtils.startViewUri(context, "market://details?id=com.facebook.orca");
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {}
        MessengerUtils.startViewUri(context, "http://play.google.com/store/apps/details?id=com.facebook.orca");
    }

    private static List<String> parseParticipants(String arrstring) {
        if (arrstring != null && arrstring.length() != 0) {
            arrstring = arrstring.split(",");
            ArrayList<String> arrayList = new ArrayList<String>();
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                arrayList.add(arrstring[i].trim());
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    public static void shareToMessenger(Activity activity, int n, ShareToMessengerParams shareToMessengerParams) {
        if (!MessengerUtils.hasMessengerInstalled((Context)activity)) {
            MessengerUtils.openMessengerInPlayStore((Context)activity);
            return;
        }
        if (MessengerUtils.getAllAvailableProtocolVersions((Context)activity).contains(20150314)) {
            MessengerUtils.shareToMessenger20150314(activity, n, shareToMessengerParams);
            return;
        }
        MessengerUtils.openMessengerInPlayStore((Context)activity);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void shareToMessenger20150314(Activity activity, int n, ShareToMessengerParams shareToMessengerParams) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(1);
            intent.setPackage(PACKAGE_NAME);
            intent.putExtra("android.intent.extra.STREAM", (Parcelable)shareToMessengerParams.uri);
            intent.setType(shareToMessengerParams.mimeType);
            String string = FacebookSdk.getApplicationId();
            if (string != null) {
                intent.putExtra(EXTRA_PROTOCOL_VERSION, 20150314);
                intent.putExtra(EXTRA_APP_ID, string);
                intent.putExtra(EXTRA_METADATA, shareToMessengerParams.metaData);
                intent.putExtra(EXTRA_EXTERNAL_URI, (Parcelable)shareToMessengerParams.externalUri);
            }
            activity.startActivityForResult(intent, n);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {}
        activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME));
    }

    private static void startViewUri(Context context, String string) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)string)));
    }
}
