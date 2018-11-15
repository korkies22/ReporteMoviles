/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.AppInviteDialogFeature;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.AppInviteContent;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class AppInviteDialog
extends FacebookDialogBase<AppInviteContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppInvite.toRequestCode();
    private static final String TAG = "AppInviteDialog";

    @Deprecated
    public AppInviteDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public AppInviteDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public AppInviteDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private AppInviteDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public static boolean canShow() {
        return false;
    }

    private static boolean canShowNativeDialog() {
        return false;
    }

    private static boolean canShowWebFallback() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Bundle createParameters(AppInviteContent object) {
        Bundle bundle = new Bundle();
        bundle.putString("app_link_url", object.getApplinkUrl());
        bundle.putString("preview_image_url", object.getPreviewImageUrl());
        bundle.putString("destination", object.getDestination().toString());
        String string = object.getPromotionCode();
        if (string == null) {
            string = "";
        }
        object = object.getPromotionText();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("promo_code", (Object)string);
                jSONObject.put("promo_text", object);
                bundle.putString("deeplink_context", jSONObject.toString());
                bundle.putString("promo_code", string);
                bundle.putString("promo_text", (String)object);
                return bundle;
            }
            catch (JSONException jSONException) {}
            Log.e((String)TAG, (String)"Json Exception in creating deeplink context");
        }
        return bundle;
    }

    private static DialogFeature getFeature() {
        return AppInviteDialogFeature.APP_INVITES_DIALOG;
    }

    @Deprecated
    public static void show(Activity activity, AppInviteContent appInviteContent) {
        new AppInviteDialog(activity).show(appInviteContent);
    }

    @Deprecated
    public static void show(Fragment fragment, AppInviteContent appInviteContent) {
        AppInviteDialog.show(new FragmentWrapper(fragment), appInviteContent);
    }

    @Deprecated
    public static void show(android.support.v4.app.Fragment fragment, AppInviteContent appInviteContent) {
        AppInviteDialog.show(new FragmentWrapper(fragment), appInviteContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, AppInviteContent appInviteContent) {
        new AppInviteDialog(fragmentWrapper).show(appInviteContent);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<AppInviteContent, Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<AppInviteContent, Result>> arrayList = new ArrayList<FacebookDialogBase<AppInviteContent, Result>>();
        arrayList.add(new NativeHandler());
        arrayList.add(new WebFallbackHandler());
        return arrayList;
    }

    @Override
    protected void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<Result> object) {
        object = object == null ? null : new ResultProcessor((FacebookCallback)object, (FacebookCallback)object){
            final /* synthetic */ FacebookCallback val$callback;
            {
                this.val$callback = facebookCallback2;
                super(facebookCallback);
            }

            @Override
            public void onSuccess(AppCall appCall, Bundle bundle) {
                if ("cancel".equalsIgnoreCase(ShareInternalUtility.getNativeDialogCompletionGesture(bundle))) {
                    this.val$callback.onCancel();
                    return;
                }
                this.val$callback.onSuccess(new Result(bundle));
            }
        };
        object = new CallbackManagerImpl.Callback((ResultProcessor)object){
            final /* synthetic */ ResultProcessor val$resultProcessor;
            {
                this.val$resultProcessor = resultProcessor;
            }

            @Override
            public boolean onActivityResult(int n, Intent intent) {
                return ShareInternalUtility.handleActivityResult(AppInviteDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
            }
        };
        callbackManagerImpl.registerCallback(this.getRequestCode(), (CallbackManagerImpl.Callback)object);
    }

    @Deprecated
    @Override
    public void show(AppInviteContent appInviteContent) {
    }

    private class NativeHandler
    extends FacebookDialogBase<AppInviteContent, Result> {
        private NativeHandler() {
            super(AppInviteDialog.this);
        }

        public boolean canShow(AppInviteContent appInviteContent, boolean bl) {
            return false;
        }

        public AppCall createAppCall(final AppInviteContent appInviteContent) {
            AppCall appCall = AppInviteDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(){

                @Override
                public Bundle getLegacyParameters() {
                    Log.e((String)AppInviteDialog.TAG, (String)"Attempting to present the AppInviteDialog with an outdated Facebook app on the device");
                    return new Bundle();
                }

                @Override
                public Bundle getParameters() {
                    return AppInviteDialog.createParameters(appInviteContent);
                }
            }, AppInviteDialog.getFeature());
            return appCall;
        }

    }

    @Deprecated
    public static final class Result {
        private final Bundle bundle;

        public Result(Bundle bundle) {
            this.bundle = bundle;
        }

        public Bundle getData() {
            return this.bundle;
        }
    }

    private class WebFallbackHandler
    extends FacebookDialogBase<AppInviteContent, Result> {
        private WebFallbackHandler() {
            super(AppInviteDialog.this);
        }

        public boolean canShow(AppInviteContent appInviteContent, boolean bl) {
            return false;
        }

        public AppCall createAppCall(AppInviteContent appInviteContent) {
            AppCall appCall = AppInviteDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebFallbackDialog(appCall, AppInviteDialog.createParameters(appInviteContent), AppInviteDialog.getFeature());
            return appCall;
        }
    }

}
