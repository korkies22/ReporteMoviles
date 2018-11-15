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
import com.facebook.share.internal.GameRequestValidation;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.GameRequestContent;
import java.util.ArrayList;
import java.util.List;

public class GameRequestDialog
extends FacebookDialogBase<GameRequestContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.GameRequest.toRequestCode();
    private static final String GAME_REQUEST_DIALOG = "apprequests";

    public GameRequestDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public GameRequestDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    public GameRequestDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private GameRequestDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    public static boolean canShow() {
        return true;
    }

    public static void show(Activity activity, GameRequestContent gameRequestContent) {
        new GameRequestDialog(activity).show(gameRequestContent);
    }

    public static void show(Fragment fragment, GameRequestContent gameRequestContent) {
        GameRequestDialog.show(new FragmentWrapper(fragment), gameRequestContent);
    }

    public static void show(android.support.v4.app.Fragment fragment, GameRequestContent gameRequestContent) {
        GameRequestDialog.show(new FragmentWrapper(fragment), gameRequestContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, GameRequestContent gameRequestContent) {
        new GameRequestDialog(fragmentWrapper).show(gameRequestContent);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<GameRequestContent, Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<GameRequestContent, Result>> arrayList = new ArrayList<FacebookDialogBase<GameRequestContent, Result>>();
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
                if (bundle != null) {
                    this.val$callback.onSuccess(new Result(bundle));
                    return;
                }
                this.onCancel(appCall);
            }
        };
        callbackManagerImpl.registerCallback(this.getRequestCode(), new CallbackManagerImpl.Callback((ResultProcessor)object){
            final /* synthetic */ ResultProcessor val$resultProcessor;
            {
                this.val$resultProcessor = resultProcessor;
            }

            @Override
            public boolean onActivityResult(int n, Intent intent) {
                return ShareInternalUtility.handleActivityResult(GameRequestDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
            }
        });
    }

    public static final class Result {
        String requestId;
        List<String> to;

        private Result(Bundle bundle) {
            this.requestId = bundle.getString("request");
            this.to = new ArrayList<String>();
            while (bundle.containsKey(String.format("to[%d]", this.to.size()))) {
                this.to.add(bundle.getString(String.format("to[%d]", this.to.size())));
            }
        }

        public String getRequestId() {
            return this.requestId;
        }

        public List<String> getRequestRecipients() {
            return this.to;
        }
    }

    private class WebHandler
    extends FacebookDialogBase<GameRequestContent, Result> {
        private WebHandler() {
            super(GameRequestDialog.this);
        }

        public boolean canShow(GameRequestContent gameRequestContent, boolean bl) {
            return true;
        }

        public AppCall createAppCall(GameRequestContent gameRequestContent) {
            GameRequestValidation.validate(gameRequestContent);
            AppCall appCall = GameRequestDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog(appCall, GameRequestDialog.GAME_REQUEST_DIALOG, WebDialogParameters.create(gameRequestContent));
            return appCall;
        }
    }

}
