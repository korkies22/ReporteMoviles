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
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.AppGroupCreationContent;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class CreateAppGroupDialog
extends FacebookDialogBase<AppGroupCreationContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupCreate.toRequestCode();
    private static final String GAME_GROUP_CREATION_DIALOG = "game_group_create";

    @Deprecated
    public CreateAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public CreateAppGroupDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public CreateAppGroupDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private CreateAppGroupDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public static boolean canShow() {
        return true;
    }

    @Deprecated
    public static void show(Activity activity, AppGroupCreationContent appGroupCreationContent) {
        new CreateAppGroupDialog(activity).show(appGroupCreationContent);
    }

    @Deprecated
    public static void show(Fragment fragment, AppGroupCreationContent appGroupCreationContent) {
        CreateAppGroupDialog.show(new FragmentWrapper(fragment), appGroupCreationContent);
    }

    @Deprecated
    public static void show(android.support.v4.app.Fragment fragment, AppGroupCreationContent appGroupCreationContent) {
        CreateAppGroupDialog.show(new FragmentWrapper(fragment), appGroupCreationContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, AppGroupCreationContent appGroupCreationContent) {
        new CreateAppGroupDialog(fragmentWrapper).show(appGroupCreationContent);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<AppGroupCreationContent, Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<AppGroupCreationContent, Result>> arrayList = new ArrayList<FacebookDialogBase<AppGroupCreationContent, Result>>();
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
                this.val$callback.onSuccess(new Result(bundle.getString("id")));
            }
        };
        object = new CallbackManagerImpl.Callback((ResultProcessor)object){
            final /* synthetic */ ResultProcessor val$resultProcessor;
            {
                this.val$resultProcessor = resultProcessor;
            }

            @Override
            public boolean onActivityResult(int n, Intent intent) {
                return ShareInternalUtility.handleActivityResult(CreateAppGroupDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
            }
        };
        callbackManagerImpl.registerCallback(this.getRequestCode(), (CallbackManagerImpl.Callback)object);
    }

    @Deprecated
    public static final class Result {
        private final String id;

        private Result(String string) {
            this.id = string;
        }

        public String getId() {
            return this.id;
        }
    }

    private class WebHandler
    extends FacebookDialogBase<AppGroupCreationContent, Result> {
        private WebHandler() {
            super(CreateAppGroupDialog.this);
        }

        public boolean canShow(AppGroupCreationContent appGroupCreationContent, boolean bl) {
            return true;
        }

        public AppCall createAppCall(AppGroupCreationContent appGroupCreationContent) {
            AppCall appCall = CreateAppGroupDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog(appCall, CreateAppGroupDialog.GAME_GROUP_CREATION_DIALOG, WebDialogParameters.create(appGroupCreationContent));
            return appCall;
        }
    }

}
