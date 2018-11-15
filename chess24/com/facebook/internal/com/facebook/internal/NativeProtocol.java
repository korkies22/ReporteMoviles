/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookSignatureValidator;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NativeProtocol {
    public static final String ACTION_APPINVITE_DIALOG = "com.facebook.platform.action.request.APPINVITES_DIALOG";
    public static final String ACTION_CAMERA_EFFECT = "com.facebook.platform.action.request.CAMERA_EFFECT";
    public static final String ACTION_FEED_DIALOG = "com.facebook.platform.action.request.FEED_DIALOG";
    public static final String ACTION_LIKE_DIALOG = "com.facebook.platform.action.request.LIKE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG = "com.facebook.platform.action.request.MESSAGE_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG = "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG = "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    public static final String AUDIENCE_EVERYONE = "everyone";
    public static final String AUDIENCE_FRIENDS = "friends";
    public static final String AUDIENCE_ME = "only_me";
    public static final String BRIDGE_ARG_ACTION_ID_STRING = "action_id";
    public static final String BRIDGE_ARG_APP_NAME_STRING = "app_name";
    public static final String BRIDGE_ARG_ERROR_BUNDLE = "error";
    public static final String BRIDGE_ARG_ERROR_CODE = "error_code";
    public static final String BRIDGE_ARG_ERROR_DESCRIPTION = "error_description";
    public static final String BRIDGE_ARG_ERROR_JSON = "error_json";
    public static final String BRIDGE_ARG_ERROR_SUBCODE = "error_subcode";
    public static final String BRIDGE_ARG_ERROR_TYPE = "error_type";
    private static final String CONTENT_SCHEME = "content://";
    public static final String ERROR_APPLICATION_ERROR = "ApplicationError";
    public static final String ERROR_NETWORK_ERROR = "NetworkError";
    public static final String ERROR_PERMISSION_DENIED = "PermissionDenied";
    public static final String ERROR_PROTOCOL_ERROR = "ProtocolError";
    public static final String ERROR_SERVICE_DISABLED = "ServiceDisabled";
    public static final String ERROR_UNKNOWN_ERROR = "UnknownError";
    public static final String ERROR_USER_CANCELED = "UserCanceled";
    public static final String EXTRA_ACCESS_TOKEN = "com.facebook.platform.extra.ACCESS_TOKEN";
    public static final String EXTRA_APPLICATION_ID = "com.facebook.platform.extra.APPLICATION_ID";
    public static final String EXTRA_APPLICATION_NAME = "com.facebook.platform.extra.APPLICATION_NAME";
    public static final String EXTRA_ARGS_PROFILE = "com.facebook.platform.extra.PROFILE";
    public static final String EXTRA_ARGS_PROFILE_FIRST_NAME = "com.facebook.platform.extra.PROFILE_FIRST_NAME";
    public static final String EXTRA_ARGS_PROFILE_LAST_NAME = "com.facebook.platform.extra.PROFILE_LAST_NAME";
    public static final String EXTRA_ARGS_PROFILE_LINK = "com.facebook.platform.extra.PROFILE_LINK";
    public static final String EXTRA_ARGS_PROFILE_MIDDLE_NAME = "com.facebook.platform.extra.PROFILE_MIDDLE_NAME";
    public static final String EXTRA_ARGS_PROFILE_NAME = "com.facebook.platform.extra.PROFILE_NAME";
    public static final String EXTRA_ARGS_PROFILE_USER_ID = "com.facebook.platform.extra.PROFILE_USER_ID";
    public static final String EXTRA_DIALOG_COMPLETE_KEY = "com.facebook.platform.extra.DID_COMPLETE";
    public static final String EXTRA_DIALOG_COMPLETION_GESTURE_KEY = "com.facebook.platform.extra.COMPLETION_GESTURE";
    public static final String EXTRA_EXPIRES_SECONDS_SINCE_EPOCH = "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH";
    public static final String EXTRA_GET_INSTALL_DATA_PACKAGE = "com.facebook.platform.extra.INSTALLDATA_PACKAGE";
    public static final String EXTRA_GRAPH_API_VERSION = "com.facebook.platform.extra.GRAPH_API_VERSION";
    public static final String EXTRA_LOGGER_REF = "com.facebook.platform.extra.LOGGER_REF";
    public static final String EXTRA_PERMISSIONS = "com.facebook.platform.extra.PERMISSIONS";
    public static final String EXTRA_PROTOCOL_ACTION = "com.facebook.platform.protocol.PROTOCOL_ACTION";
    public static final String EXTRA_PROTOCOL_BRIDGE_ARGS = "com.facebook.platform.protocol.BRIDGE_ARGS";
    public static final String EXTRA_PROTOCOL_CALL_ID = "com.facebook.platform.protocol.CALL_ID";
    public static final String EXTRA_PROTOCOL_METHOD_ARGS = "com.facebook.platform.protocol.METHOD_ARGS";
    public static final String EXTRA_PROTOCOL_METHOD_RESULTS = "com.facebook.platform.protocol.RESULT_ARGS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.platform.protocol.PROTOCOL_VERSION";
    static final String EXTRA_PROTOCOL_VERSIONS = "com.facebook.platform.extra.PROTOCOL_VERSIONS";
    public static final String EXTRA_TOAST_DURATION_MS = "com.facebook.platform.extra.EXTRA_TOAST_DURATION_MS";
    public static final String EXTRA_USER_ID = "com.facebook.platform.extra.USER_ID";
    private static final String FACEBOOK_PROXY_AUTH_ACTIVITY = "com.facebook.katana.ProxyAuth";
    public static final String FACEBOOK_PROXY_AUTH_APP_ID_KEY = "client_id";
    public static final String FACEBOOK_PROXY_AUTH_E2E_KEY = "e2e";
    public static final String FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY = "scope";
    public static final String FACEBOOK_SDK_VERSION_KEY = "facebook_sdk_version";
    private static final String FACEBOOK_TOKEN_REFRESH_ACTIVITY = "com.facebook.katana.platform.TokenRefreshService";
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_USER_GENERATED_KEY = "user_generated";
    static final String INTENT_ACTION_PLATFORM_ACTIVITY = "com.facebook.platform.PLATFORM_ACTIVITY";
    static final String INTENT_ACTION_PLATFORM_SERVICE = "com.facebook.platform.PLATFORM_SERVICE";
    private static final List<Integer> KNOWN_PROTOCOL_VERSIONS;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REPLY = 65537;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST = 65536;
    public static final int MESSAGE_GET_AK_SEAMLESS_TOKEN_REPLY = 65545;
    public static final int MESSAGE_GET_AK_SEAMLESS_TOKEN_REQUEST = 65544;
    public static final int MESSAGE_GET_INSTALL_DATA_REPLY = 65541;
    public static final int MESSAGE_GET_INSTALL_DATA_REQUEST = 65540;
    public static final int MESSAGE_GET_LIKE_STATUS_REPLY = 65543;
    public static final int MESSAGE_GET_LIKE_STATUS_REQUEST = 65542;
    public static final int MESSAGE_GET_LOGIN_STATUS_REPLY = 65547;
    public static final int MESSAGE_GET_LOGIN_STATUS_REQUEST = 65546;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REPLY = 65539;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REQUEST = 65538;
    public static final int NO_PROTOCOL_AVAILABLE = -1;
    public static final String OPEN_GRAPH_CREATE_OBJECT_KEY = "fbsdk:create_object";
    private static final String PLATFORM_PROVIDER = ".provider.PlatformProvider";
    private static final String PLATFORM_PROVIDER_VERSIONS = ".provider.PlatformProvider/versions";
    private static final String PLATFORM_PROVIDER_VERSION_COLUMN = "version";
    public static final int PROTOCOL_VERSION_20121101 = 20121101;
    public static final int PROTOCOL_VERSION_20130502 = 20130502;
    public static final int PROTOCOL_VERSION_20130618 = 20130618;
    public static final int PROTOCOL_VERSION_20131107 = 20131107;
    public static final int PROTOCOL_VERSION_20140204 = 20140204;
    public static final int PROTOCOL_VERSION_20140324 = 20140324;
    public static final int PROTOCOL_VERSION_20140701 = 20140701;
    public static final int PROTOCOL_VERSION_20141001 = 20141001;
    public static final int PROTOCOL_VERSION_20141028 = 20141028;
    public static final int PROTOCOL_VERSION_20141107 = 20141107;
    public static final int PROTOCOL_VERSION_20141218 = 20141218;
    public static final int PROTOCOL_VERSION_20160327 = 20160327;
    public static final int PROTOCOL_VERSION_20170213 = 20170213;
    public static final int PROTOCOL_VERSION_20170411 = 20170411;
    public static final int PROTOCOL_VERSION_20170417 = 20170417;
    public static final int PROTOCOL_VERSION_20171115 = 20171115;
    public static final String RESULT_ARGS_ACCESS_TOKEN = "access_token";
    public static final String RESULT_ARGS_DIALOG_COMPLETE_KEY = "didComplete";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY = "completionGesture";
    public static final String RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH = "expires_seconds_since_epoch";
    public static final String RESULT_ARGS_PERMISSIONS = "permissions";
    public static final String RESULT_ARGS_SIGNED_REQUEST = "signed request";
    public static final String STATUS_ERROR_CODE = "com.facebook.platform.status.ERROR_CODE";
    public static final String STATUS_ERROR_DESCRIPTION = "com.facebook.platform.status.ERROR_DESCRIPTION";
    public static final String STATUS_ERROR_JSON = "com.facebook.platform.status.ERROR_JSON";
    public static final String STATUS_ERROR_SUBCODE = "com.facebook.platform.status.ERROR_SUBCODE";
    public static final String STATUS_ERROR_TYPE = "com.facebook.platform.status.ERROR_TYPE";
    private static final String TAG = "com.facebook.internal.NativeProtocol";
    public static final String WEB_DIALOG_ACTION = "action";
    public static final String WEB_DIALOG_IS_FALLBACK = "is_fallback";
    public static final String WEB_DIALOG_PARAMS = "params";
    public static final String WEB_DIALOG_URL = "url";
    private static final Map<String, List<NativeAppInfo>> actionToAppInfoMap;
    private static final List<NativeAppInfo> effectCameraAppInfoList;
    private static final List<NativeAppInfo> facebookAppInfoList;
    private static final AtomicBoolean protocolVersionsAsyncUpdating;

    static {
        facebookAppInfoList = NativeProtocol.buildFacebookAppList();
        effectCameraAppInfoList = NativeProtocol.buildEffectCameraAppInfoList();
        actionToAppInfoMap = NativeProtocol.buildActionToAppInfoMap();
        protocolVersionsAsyncUpdating = new AtomicBoolean(false);
        KNOWN_PROTOCOL_VERSIONS = Arrays.asList(20170417, 20160327, 20141218, 20141107, 20141028, 20141001, 20140701, 20140324, 20140204, 20131107, 20130618, 20130502, 20121101);
    }

    static /* synthetic */ TreeSet access$000(NativeAppInfo nativeAppInfo) {
        return NativeProtocol.fetchAllAvailableProtocolVersionsForAppInfo(nativeAppInfo);
    }

    private static Map<String, List<NativeAppInfo>> buildActionToAppInfoMap() {
        HashMap<String, List<NativeAppInfo>> hashMap = new HashMap<String, List<NativeAppInfo>>();
        ArrayList<MessengerAppInfo> arrayList = new ArrayList<MessengerAppInfo>();
        arrayList.add(new MessengerAppInfo());
        hashMap.put(ACTION_OGACTIONPUBLISH_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_FEED_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_LIKE_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_APPINVITE_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_MESSAGE_DIALOG, arrayList);
        hashMap.put(ACTION_OGMESSAGEPUBLISH_DIALOG, arrayList);
        hashMap.put(ACTION_CAMERA_EFFECT, effectCameraAppInfoList);
        return hashMap;
    }

    private static List<NativeAppInfo> buildEffectCameraAppInfoList() {
        ArrayList<NativeAppInfo> arrayList = new ArrayList<NativeAppInfo>(NativeProtocol.buildFacebookAppList());
        arrayList.add(0, new EffectTestAppInfo());
        return arrayList;
    }

    private static List<NativeAppInfo> buildFacebookAppList() {
        ArrayList<NativeAppInfo> arrayList = new ArrayList<NativeAppInfo>();
        arrayList.add(new KatanaAppInfo());
        arrayList.add(new WakizashiAppInfo());
        return arrayList;
    }

    private static Uri buildPlatformProviderVersionURI(NativeAppInfo nativeAppInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_SCHEME);
        stringBuilder.append(nativeAppInfo.getPackage());
        stringBuilder.append(PLATFORM_PROVIDER_VERSIONS);
        return Uri.parse((String)stringBuilder.toString());
    }

    public static int computeLatestAvailableVersionFromVersionSpec(TreeSet<Integer> object, int n, int[] arrn) {
        int n2 = -1;
        int n3 = arrn.length;
        object = object.descendingIterator();
        --n3;
        int n4 = -1;
        while (object.hasNext()) {
            int n5;
            int n6 = (Integer)object.next();
            int n7 = Math.max(n4, n6);
            for (n5 = n3; n5 >= 0 && arrn[n5] > n6; --n5) {
            }
            if (n5 < 0) {
                return -1;
            }
            n4 = n7;
            n3 = n5;
            if (arrn[n5] != n6) continue;
            n3 = n2;
            if (n5 % 2 == 0) {
                n3 = Math.min(n7, n);
            }
            return n3;
        }
        return -1;
    }

    public static Bundle createBundleForException(FacebookException facebookException) {
        if (facebookException == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(BRIDGE_ARG_ERROR_DESCRIPTION, facebookException.toString());
        if (facebookException instanceof FacebookOperationCanceledException) {
            bundle.putString(BRIDGE_ARG_ERROR_TYPE, ERROR_USER_CANCELED);
        }
        return bundle;
    }

    public static Intent createFacebookLiteIntent(Context context, String string, Collection<String> collection, String string2, boolean bl, boolean bl2, DefaultAudience defaultAudience, String string3) {
        FBLiteAppInfo fBLiteAppInfo = new FBLiteAppInfo();
        return NativeProtocol.validateActivityIntent(context, NativeProtocol.createNativeAppIntent(fBLiteAppInfo, string, collection, string2, bl, bl2, defaultAudience, string3), fBLiteAppInfo);
    }

    private static Intent createNativeAppIntent(NativeAppInfo nativeAppInfo, String string, Collection<String> collection, String string2, boolean bl, boolean bl2, DefaultAudience defaultAudience, String string3) {
        String string4 = nativeAppInfo.getLoginActivity();
        if (string4 == null) {
            return null;
        }
        nativeAppInfo = new Intent().setClassName(nativeAppInfo.getPackage(), string4).putExtra(FACEBOOK_PROXY_AUTH_APP_ID_KEY, string);
        nativeAppInfo.putExtra(FACEBOOK_SDK_VERSION_KEY, FacebookSdk.getSdkVersion());
        if (!Utility.isNullOrEmpty(collection)) {
            nativeAppInfo.putExtra(FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY, TextUtils.join((CharSequence)",", collection));
        }
        if (!Utility.isNullOrEmpty(string2)) {
            nativeAppInfo.putExtra(FACEBOOK_PROXY_AUTH_E2E_KEY, string2);
        }
        nativeAppInfo.putExtra("state", string3);
        nativeAppInfo.putExtra("response_type", "token,signed_request");
        nativeAppInfo.putExtra("return_scopes", "true");
        if (bl2) {
            nativeAppInfo.putExtra("default_audience", defaultAudience.getNativeProtocolAudience());
        }
        nativeAppInfo.putExtra("legacy_override", FacebookSdk.getGraphApiVersion());
        nativeAppInfo.putExtra("auth_type", "rerequest");
        return nativeAppInfo;
    }

    public static Intent createPlatformActivityIntent(Context context, String string, String string2, ProtocolVersionQueryResult protocolVersionQueryResult, Bundle bundle) {
        if (protocolVersionQueryResult == null) {
            return null;
        }
        NativeAppInfo nativeAppInfo = protocolVersionQueryResult.nativeAppInfo;
        if (nativeAppInfo == null) {
            return null;
        }
        if ((context = NativeProtocol.validateActivityIntent(context, new Intent().setAction(INTENT_ACTION_PLATFORM_ACTIVITY).setPackage(nativeAppInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), nativeAppInfo)) == null) {
            return null;
        }
        NativeProtocol.setupProtocolRequestIntent((Intent)context, string, string2, protocolVersionQueryResult.protocolVersion, bundle);
        return context;
    }

    public static Intent createPlatformServiceIntent(Context context) {
        for (NativeAppInfo nativeAppInfo : facebookAppInfoList) {
            if ((nativeAppInfo = NativeProtocol.validateServiceIntent(context, new Intent(INTENT_ACTION_PLATFORM_SERVICE).setPackage(nativeAppInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), nativeAppInfo)) == null) continue;
            return nativeAppInfo;
        }
        return null;
    }

    public static Intent createProtocolResultIntent(Intent intent, Bundle bundle, FacebookException facebookException) {
        UUID uUID = NativeProtocol.getCallIdFromIntent(intent);
        if (uUID == null) {
            return null;
        }
        Intent intent2 = new Intent();
        intent2.putExtra(EXTRA_PROTOCOL_VERSION, NativeProtocol.getProtocolVersionFromIntent(intent));
        intent = new Bundle();
        intent.putString(BRIDGE_ARG_ACTION_ID_STRING, uUID.toString());
        if (facebookException != null) {
            intent.putBundle(BRIDGE_ARG_ERROR_BUNDLE, NativeProtocol.createBundleForException(facebookException));
        }
        intent2.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, (Bundle)intent);
        if (bundle != null) {
            intent2.putExtra(EXTRA_PROTOCOL_METHOD_RESULTS, bundle);
        }
        return intent2;
    }

    public static Intent createProxyAuthIntent(Context context, String string, Collection<String> collection, String string2, boolean bl, boolean bl2, DefaultAudience defaultAudience, String string3) {
        for (NativeAppInfo nativeAppInfo : facebookAppInfoList) {
            if ((nativeAppInfo = NativeProtocol.validateActivityIntent(context, NativeProtocol.createNativeAppIntent(nativeAppInfo, string, collection, string2, bl, bl2, defaultAudience, string3), nativeAppInfo)) == null) continue;
            return nativeAppInfo;
        }
        return null;
    }

    public static Intent createTokenRefreshIntent(Context context) {
        for (NativeAppInfo nativeAppInfo : facebookAppInfoList) {
            if ((nativeAppInfo = NativeProtocol.validateServiceIntent(context, new Intent().setClassName(nativeAppInfo.getPackage(), FACEBOOK_TOKEN_REFRESH_ACTIVITY), nativeAppInfo)) == null) continue;
            return nativeAppInfo;
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static TreeSet<Integer> fetchAllAvailableProtocolVersionsForAppInfo(NativeAppInfo object) {
        TreeSet<Integer> treeSet;
        Object object2;
        block8 : {
            Object object3;
            block9 : {
                treeSet = new TreeSet<Integer>();
                ContentResolver contentResolver = FacebookSdk.getApplicationContext().getContentResolver();
                Uri uri = NativeProtocol.buildPlatformProviderVersionURI((NativeAppInfo)object);
                Object object4 = null;
                Object var4_8 = null;
                object2 = null;
                object3 = object4;
                try {
                    PackageManager packageManager = FacebookSdk.getApplicationContext().getPackageManager();
                    object3 = object4;
                    StringBuilder stringBuilder = new StringBuilder();
                    object3 = object4;
                    stringBuilder.append(object.getPackage());
                    object3 = object4;
                    stringBuilder.append(PLATFORM_PROVIDER);
                    object3 = object4;
                    object = stringBuilder.toString();
                    object3 = object4;
                    try {
                        object = packageManager.resolveContentProvider((String)object, 0);
                    }
                    catch (RuntimeException runtimeException) {
                        object3 = object4;
                        Log.e((String)TAG, (String)"Failed to query content resolver.", (Throwable)runtimeException);
                        object = null;
                    }
                    if (object == null) break block8;
                    object3 = object4;
                    object = contentResolver.query(uri, new String[]{PLATFORM_PROVIDER_VERSION_COLUMN}, null, null, null);
                    break block9;
                }
                catch (Throwable throwable) {
                    if (object3 == null) throw throwable;
                    object3.close();
                    throw throwable;
                }
                catch (NullPointerException | SecurityException runtimeException) {}
                object3 = object4;
                Log.e((String)TAG, (String)"Failed to query content resolver.");
                object = var4_8;
            }
            object2 = object;
            if (object != null) {
                do {
                    object2 = object;
                    object3 = object;
                    if (!object.moveToNext()) break;
                    object3 = object;
                    treeSet.add(object.getInt(object.getColumnIndex(PLATFORM_PROVIDER_VERSION_COLUMN)));
                } while (true);
            }
        }
        if (object2 == null) return treeSet;
        object2.close();
        return treeSet;
    }

    public static Bundle getBridgeArgumentsFromIntent(Intent intent) {
        if (!NativeProtocol.isVersionCompatibleWithBucketedIntent(NativeProtocol.getProtocolVersionFromIntent(intent))) {
            return null;
        }
        return intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static UUID getCallIdFromIntent(Intent object) {
        void var0_5;
        if (object == null) {
            return null;
        }
        if (NativeProtocol.isVersionCompatibleWithBucketedIntent(NativeProtocol.getProtocolVersionFromIntent(object))) {
            Bundle bundle = object.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
            if (bundle != null) {
                String string = bundle.getString(BRIDGE_ARG_ACTION_ID_STRING);
            } else {
                Object var0_3 = null;
            }
        } else {
            String string = object.getStringExtra(EXTRA_PROTOCOL_CALL_ID);
        }
        if (var0_5 == null) return null;
        try {
            return UUID.fromString((String)var0_5);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public static Bundle getErrorDataFromResultIntent(Intent intent) {
        if (!NativeProtocol.isErrorResult(intent)) {
            return null;
        }
        Bundle bundle = NativeProtocol.getBridgeArgumentsFromIntent(intent);
        if (bundle != null) {
            return bundle.getBundle(BRIDGE_ARG_ERROR_BUNDLE);
        }
        return intent.getExtras();
    }

    public static FacebookException getExceptionFromErrorData(Bundle bundle) {
        String string;
        String string2;
        if (bundle == null) {
            return null;
        }
        String string3 = string = bundle.getString(BRIDGE_ARG_ERROR_TYPE);
        if (string == null) {
            string3 = bundle.getString(STATUS_ERROR_TYPE);
        }
        string = string2 = bundle.getString(BRIDGE_ARG_ERROR_DESCRIPTION);
        if (string2 == null) {
            string = bundle.getString(STATUS_ERROR_DESCRIPTION);
        }
        if (string3 != null && string3.equalsIgnoreCase(ERROR_USER_CANCELED)) {
            return new FacebookOperationCanceledException(string);
        }
        return new FacebookException(string);
    }

    public static ProtocolVersionQueryResult getLatestAvailableProtocolVersionForAction(String string, int[] arrn) {
        return NativeProtocol.getLatestAvailableProtocolVersionForAppInfoList(actionToAppInfoMap.get(string), arrn);
    }

    private static ProtocolVersionQueryResult getLatestAvailableProtocolVersionForAppInfoList(List<NativeAppInfo> object, int[] arrn) {
        NativeProtocol.updateAllAvailableProtocolVersionsAsync();
        if (object == null) {
            return ProtocolVersionQueryResult.createEmpty();
        }
        object = object.iterator();
        while (object.hasNext()) {
            NativeAppInfo nativeAppInfo = (NativeAppInfo)object.next();
            int n = NativeProtocol.computeLatestAvailableVersionFromVersionSpec(nativeAppInfo.getAvailableVersions(), NativeProtocol.getLatestKnownVersion(), arrn);
            if (n == -1) continue;
            return ProtocolVersionQueryResult.create(nativeAppInfo, n);
        }
        return ProtocolVersionQueryResult.createEmpty();
    }

    public static int getLatestAvailableProtocolVersionForService(int n) {
        return NativeProtocol.getLatestAvailableProtocolVersionForAppInfoList(facebookAppInfoList, new int[]{n}).getProtocolVersion();
    }

    public static final int getLatestKnownVersion() {
        return KNOWN_PROTOCOL_VERSIONS.get(0);
    }

    public static Bundle getMethodArgumentsFromIntent(Intent intent) {
        if (!NativeProtocol.isVersionCompatibleWithBucketedIntent(NativeProtocol.getProtocolVersionFromIntent(intent))) {
            return intent.getExtras();
        }
        return intent.getBundleExtra(EXTRA_PROTOCOL_METHOD_ARGS);
    }

    public static int getProtocolVersionFromIntent(Intent intent) {
        return intent.getIntExtra(EXTRA_PROTOCOL_VERSION, 0);
    }

    public static Bundle getSuccessResultsFromIntent(Intent intent) {
        int n = NativeProtocol.getProtocolVersionFromIntent(intent);
        intent = intent.getExtras();
        if (NativeProtocol.isVersionCompatibleWithBucketedIntent(n)) {
            if (intent == null) {
                return intent;
            }
            return intent.getBundle(EXTRA_PROTOCOL_METHOD_RESULTS);
        }
        return intent;
    }

    public static boolean isErrorResult(Intent intent) {
        Bundle bundle = NativeProtocol.getBridgeArgumentsFromIntent(intent);
        if (bundle != null) {
            return bundle.containsKey(BRIDGE_ARG_ERROR_BUNDLE);
        }
        return intent.hasExtra(STATUS_ERROR_TYPE);
    }

    public static boolean isVersionCompatibleWithBucketedIntent(int n) {
        if (KNOWN_PROTOCOL_VERSIONS.contains(n) && n >= 20140701) {
            return true;
        }
        return false;
    }

    public static void setupProtocolRequestIntent(Intent intent, String string, String string2, int n, Bundle bundle) {
        String string3 = FacebookSdk.getApplicationId();
        String string4 = FacebookSdk.getApplicationName();
        intent.putExtra(EXTRA_PROTOCOL_VERSION, n).putExtra(EXTRA_PROTOCOL_ACTION, string2).putExtra(EXTRA_APPLICATION_ID, string3);
        if (NativeProtocol.isVersionCompatibleWithBucketedIntent(n)) {
            string2 = new Bundle();
            string2.putString(BRIDGE_ARG_ACTION_ID_STRING, string);
            Utility.putNonEmptyString((Bundle)string2, BRIDGE_ARG_APP_NAME_STRING, string4);
            intent.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, (Bundle)string2);
            string = bundle;
            if (bundle == null) {
                string = new Bundle();
            }
            intent.putExtra(EXTRA_PROTOCOL_METHOD_ARGS, (Bundle)string);
            return;
        }
        intent.putExtra(EXTRA_PROTOCOL_CALL_ID, string);
        if (!Utility.isNullOrEmpty(string4)) {
            intent.putExtra(EXTRA_APPLICATION_NAME, string4);
        }
        intent.putExtras(bundle);
    }

    public static void updateAllAvailableProtocolVersionsAsync() {
        if (!protocolVersionsAsyncUpdating.compareAndSet(false, true)) {
            return;
        }
        FacebookSdk.getExecutor().execute(new Runnable(){

            @Override
            public void run() {
                try {
                    Iterator iterator = facebookAppInfoList.iterator();
                    while (iterator.hasNext()) {
                        ((NativeAppInfo)iterator.next()).fetchAvailableVersions(true);
                    }
                    return;
                }
                finally {
                    protocolVersionsAsyncUpdating.set(false);
                }
            }
        });
    }

    static Intent validateActivityIntent(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        if (intent == null) {
            return null;
        }
        nativeAppInfo = context.getPackageManager().resolveActivity(intent, 0);
        if (nativeAppInfo == null) {
            return null;
        }
        if (!FacebookSignatureValidator.validateSignature(context, nativeAppInfo.activityInfo.packageName)) {
            return null;
        }
        return intent;
    }

    static Intent validateServiceIntent(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        if (intent == null) {
            return null;
        }
        nativeAppInfo = context.getPackageManager().resolveService(intent, 0);
        if (nativeAppInfo == null) {
            return null;
        }
        if (!FacebookSignatureValidator.validateSignature(context, nativeAppInfo.serviceInfo.packageName)) {
            return null;
        }
        return intent;
    }

    private static class EffectTestAppInfo
    extends NativeAppInfo {
        static final String EFFECT_TEST_APP_PACKAGE = "com.facebook.arstudio.player";

        private EffectTestAppInfo() {
            super();
        }

        @Override
        protected String getLoginActivity() {
            return null;
        }

        @Override
        protected String getPackage() {
            return EFFECT_TEST_APP_PACKAGE;
        }
    }

    private static class FBLiteAppInfo
    extends NativeAppInfo {
        static final String FACEBOOK_LITE_ACTIVITY = "com.facebook.lite.platform.LoginGDPDialogActivity";
        static final String FBLITE_PACKAGE = "com.facebook.lite";

        private FBLiteAppInfo() {
            super();
        }

        @Override
        protected String getLoginActivity() {
            return FACEBOOK_LITE_ACTIVITY;
        }

        @Override
        protected String getPackage() {
            return FBLITE_PACKAGE;
        }
    }

    private static class KatanaAppInfo
    extends NativeAppInfo {
        static final String KATANA_PACKAGE = "com.facebook.katana";

        private KatanaAppInfo() {
            super();
        }

        @Override
        protected String getLoginActivity() {
            return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
        }

        @Override
        protected String getPackage() {
            return KATANA_PACKAGE;
        }
    }

    private static class MessengerAppInfo
    extends NativeAppInfo {
        static final String MESSENGER_PACKAGE = "com.facebook.orca";

        private MessengerAppInfo() {
            super();
        }

        @Override
        protected String getLoginActivity() {
            return null;
        }

        @Override
        protected String getPackage() {
            return MESSENGER_PACKAGE;
        }
    }

    private static abstract class NativeAppInfo {
        private TreeSet<Integer> availableVersions;

        private NativeAppInfo() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void fetchAvailableVersions(boolean var1_1) {
            block5 : {
                // MONITORENTER : this
                if (var1_1) ** GOTO lbl5
                if (this.availableVersions != null) break block5;
lbl5: // 2 sources:
                this.availableVersions = NativeProtocol.access$000(this);
            }
            // MONITOREXIT : this
            return;
        }

        public TreeSet<Integer> getAvailableVersions() {
            if (this.availableVersions == null) {
                this.fetchAvailableVersions(false);
            }
            return this.availableVersions;
        }

        protected abstract String getLoginActivity();

        protected abstract String getPackage();
    }

    public static class ProtocolVersionQueryResult {
        private NativeAppInfo nativeAppInfo;
        private int protocolVersion;

        private ProtocolVersionQueryResult() {
        }

        public static ProtocolVersionQueryResult create(NativeAppInfo nativeAppInfo, int n) {
            ProtocolVersionQueryResult protocolVersionQueryResult = new ProtocolVersionQueryResult();
            protocolVersionQueryResult.nativeAppInfo = nativeAppInfo;
            protocolVersionQueryResult.protocolVersion = n;
            return protocolVersionQueryResult;
        }

        public static ProtocolVersionQueryResult createEmpty() {
            ProtocolVersionQueryResult protocolVersionQueryResult = new ProtocolVersionQueryResult();
            protocolVersionQueryResult.protocolVersion = -1;
            return protocolVersionQueryResult;
        }

        @Nullable
        public NativeAppInfo getAppInfo() {
            return this.nativeAppInfo;
        }

        public int getProtocolVersion() {
            return this.protocolVersion;
        }
    }

    private static class WakizashiAppInfo
    extends NativeAppInfo {
        static final String WAKIZASHI_PACKAGE = "com.facebook.wakizashi";

        private WakizashiAppInfo() {
        }

        @Override
        protected String getLoginActivity() {
            return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
        }

        @Override
        protected String getPackage() {
            return WAKIZASHI_PACKAGE;
        }
    }

}
