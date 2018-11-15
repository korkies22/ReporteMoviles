/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.facebook.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.UUID;

public class DialogPresenter {
    public static boolean canPresentNativeDialogWithFeature(DialogFeature dialogFeature) {
        if (DialogPresenter.getProtocolVersionForNativeDialog(dialogFeature).getProtocolVersion() != -1) {
            return true;
        }
        return false;
    }

    public static boolean canPresentWebFallbackDialogWithFeature(DialogFeature dialogFeature) {
        if (DialogPresenter.getDialogWebFallbackUri(dialogFeature) != null) {
            return true;
        }
        return false;
    }

    private static Uri getDialogWebFallbackUri(DialogFeature object) {
        String string = object.name();
        object = object.getAction();
        object = FetchedAppSettings.getDialogFeatureConfig(FacebookSdk.getApplicationId(), (String)object, string);
        if (object != null) {
            return object.getFallbackUrl();
        }
        return null;
    }

    public static NativeProtocol.ProtocolVersionQueryResult getProtocolVersionForNativeDialog(DialogFeature dialogFeature) {
        String string = FacebookSdk.getApplicationId();
        String string2 = dialogFeature.getAction();
        return NativeProtocol.getLatestAvailableProtocolVersionForAction(string2, DialogPresenter.getVersionSpecForFeature(string, string2, dialogFeature));
    }

    private static int[] getVersionSpecForFeature(String object, String string, DialogFeature dialogFeature) {
        if ((object = FetchedAppSettings.getDialogFeatureConfig((String)object, string, dialogFeature.name())) != null) {
            return object.getVersionSpec();
        }
        return new int[]{dialogFeature.getMinVersion()};
    }

    public static void logDialogActivity(Context object, String string, String string2) {
        object = AppEventsLogger.newLogger((Context)object);
        Bundle bundle = new Bundle();
        bundle.putString("fb_dialog_outcome", string2);
        object.logSdkEvent(string, null, bundle);
    }

    public static void present(AppCall appCall, Activity activity) {
        activity.startActivityForResult(appCall.getRequestIntent(), appCall.getRequestCode());
        appCall.setPending();
    }

    public static void present(AppCall appCall, FragmentWrapper fragmentWrapper) {
        fragmentWrapper.startActivityForResult(appCall.getRequestIntent(), appCall.getRequestCode());
        appCall.setPending();
    }

    public static void setupAppCallForCannotShowError(AppCall appCall) {
        DialogPresenter.setupAppCallForValidationError(appCall, new FacebookException("Unable to show the provided content via the web or the installed version of the Facebook app. Some dialogs are only supported starting API 14."));
    }

    public static void setupAppCallForErrorResult(AppCall appCall, FacebookException facebookException) {
        if (facebookException == null) {
            return;
        }
        Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(FacebookActivity.PASS_THROUGH_CANCEL_ACTION);
        NativeProtocol.setupProtocolRequestIntent(intent, appCall.getCallId().toString(), null, NativeProtocol.getLatestKnownVersion(), NativeProtocol.createBundleForException(facebookException));
        appCall.setRequestIntent(intent);
    }

    public static void setupAppCallForNativeDialog(AppCall appCall, ParameterProvider parameterProvider, DialogFeature object) {
        Context context = FacebookSdk.getApplicationContext();
        String string = object.getAction();
        NativeProtocol.ProtocolVersionQueryResult protocolVersionQueryResult = DialogPresenter.getProtocolVersionForNativeDialog((DialogFeature)object);
        int n = protocolVersionQueryResult.getProtocolVersion();
        if (n == -1) {
            throw new FacebookException("Cannot present this dialog. This likely means that the Facebook app is not installed.");
        }
        parameterProvider = NativeProtocol.isVersionCompatibleWithBucketedIntent(n) ? parameterProvider.getParameters() : parameterProvider.getLegacyParameters();
        object = parameterProvider;
        if (parameterProvider == null) {
            object = new Bundle();
        }
        if ((parameterProvider = NativeProtocol.createPlatformActivityIntent(context, appCall.getCallId().toString(), string, protocolVersionQueryResult, (Bundle)object)) == null) {
            throw new FacebookException("Unable to create Intent; this likely means theFacebook app is not installed.");
        }
        appCall.setRequestIntent((Intent)parameterProvider);
    }

    public static void setupAppCallForValidationError(AppCall appCall, FacebookException facebookException) {
        DialogPresenter.setupAppCallForErrorResult(appCall, facebookException);
    }

    public static void setupAppCallForWebDialog(AppCall appCall, String string, Bundle bundle) {
        Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
        Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", string);
        bundle2.putBundle("params", bundle);
        bundle = new Intent();
        NativeProtocol.setupProtocolRequestIntent((Intent)bundle, appCall.getCallId().toString(), string, NativeProtocol.getLatestKnownVersion(), bundle2);
        bundle.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        bundle.setAction("FacebookDialogFragment");
        appCall.setRequestIntent((Intent)bundle);
    }

    public static void setupAppCallForWebFallbackDialog(AppCall object, Bundle bundle, DialogFeature dialogFeature) {
        Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
        Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
        String string = dialogFeature.name();
        Uri uri = DialogPresenter.getDialogWebFallbackUri(dialogFeature);
        if (uri == null) {
            object = new StringBuilder();
            object.append("Unable to fetch the Url for the DialogFeature : '");
            object.append(string);
            object.append("'");
            throw new FacebookException(object.toString());
        }
        int n = NativeProtocol.getLatestKnownVersion();
        bundle = ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback(object.getCallId().toString(), n, bundle);
        if (bundle == null) {
            throw new FacebookException("Unable to fetch the app's key-hash");
        }
        bundle = uri.isRelative() ? Utility.buildUri(ServerProtocol.getDialogAuthority(), uri.toString(), bundle) : Utility.buildUri(uri.getAuthority(), uri.getPath(), bundle);
        string = new Bundle();
        string.putString("url", bundle.toString());
        string.putBoolean("is_fallback", true);
        bundle = new Intent();
        NativeProtocol.setupProtocolRequestIntent((Intent)bundle, object.getCallId().toString(), dialogFeature.getAction(), NativeProtocol.getLatestKnownVersion(), (Bundle)string);
        bundle.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        bundle.setAction("FacebookDialogFragment");
        object.setRequestIntent((Intent)bundle);
    }

    public static interface ParameterProvider {
        public Bundle getLegacyParameters();

        public Bundle getParameters();
    }

}
