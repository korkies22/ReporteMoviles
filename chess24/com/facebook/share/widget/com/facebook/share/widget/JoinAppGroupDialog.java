/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class JoinAppGroupDialog
extends FacebookDialogBase<String, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupJoin.toRequestCode();
    private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";

    @Deprecated
    public JoinAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public JoinAppGroupDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public JoinAppGroupDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private JoinAppGroupDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public static boolean canShow() {
        return true;
    }

    @Deprecated
    public static void show(Activity activity, String string) {
        new JoinAppGroupDialog(activity).show(string);
    }

    @Deprecated
    public static void show(Fragment fragment, String string) {
        JoinAppGroupDialog.show(new FragmentWrapper(fragment), string);
    }

    @Deprecated
    public static void show(android.support.v4.app.Fragment fragment, String string) {
        JoinAppGroupDialog.show(new FragmentWrapper(fragment), string);
    }

    private static void show(FragmentWrapper fragmentWrapper, String string) {
        new JoinAppGroupDialog(fragmentWrapper).show(string);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<String, Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<String, Result>> arrayList = new ArrayList<FacebookDialogBase<String, Result>>();
        arrayList.add(new WebHandler());
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
                return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
            }
        };
        callbackManagerImpl.registerCallback(this.getRequestCode(), (CallbackManagerImpl.Callback)object);
    }

    @Deprecated
    public static final class Result {
        private final Bundle data;

        private Result(Bundle bundle) {
            this.data = bundle;
        }

        public Bundle getData() {
            return this.data;
        }
    }

    private class WebHandler
    extends FacebookDialogBase<String, Result> {
        private WebHandler() {
            super(JoinAppGroupDialog.this);
        }

        public boolean canShow(String string, boolean bl) {
            return true;
        }

        public AppCall createAppCall(String string) {
            AppCall appCall = JoinAppGroupDialog.this.createBaseAppCall();
            Bundle bundle = new Bundle();
            bundle.putString("id", string);
            DialogPresenter.setupAppCallForWebDialog(appCall, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, bundle);
            return appCall;
        }
    }

}
