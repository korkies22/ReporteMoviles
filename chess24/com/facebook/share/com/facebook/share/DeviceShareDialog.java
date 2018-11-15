/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 */
package com.facebook.share;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import java.util.List;

public class DeviceShareDialog
extends FacebookDialogBase<ShareContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.DeviceShare.toRequestCode();

    public DeviceShareDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public DeviceShareDialog(Fragment fragment) {
        super(new FragmentWrapper(fragment), DEFAULT_REQUEST_CODE);
    }

    public DeviceShareDialog(android.support.v4.app.Fragment fragment) {
        super(new FragmentWrapper(fragment), DEFAULT_REQUEST_CODE);
    }

    @Override
    protected boolean canShowImpl(ShareContent shareContent, Object object) {
        if (!(shareContent instanceof ShareLinkContent) && !(shareContent instanceof ShareOpenGraphContent)) {
            return false;
        }
        return true;
    }

    @Override
    protected AppCall createBaseAppCall() {
        return null;
    }

    @Override
    protected List<FacebookDialogBase<ShareContent, Result>> getOrderedModeHandlers() {
        return null;
    }

    @Override
    protected void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, final FacebookCallback<Result> facebookCallback) {
        callbackManagerImpl.registerCallback(this.getRequestCode(), new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(int n, Intent object) {
                if (object.hasExtra("error")) {
                    object = (FacebookRequestError)object.getParcelableExtra("error");
                    facebookCallback.onError(object.getException());
                    return true;
                }
                facebookCallback.onSuccess(new Result());
                return true;
            }
        });
    }

    @Override
    protected void showImpl(ShareContent object, Object object2) {
        if (object == null) {
            throw new FacebookException("Must provide non-null content to share");
        }
        if (!(object instanceof ShareLinkContent) && !(object instanceof ShareOpenGraphContent)) {
            object = new StringBuilder();
            object.append(this.getClass().getSimpleName());
            object.append(" only supports ShareLinkContent or ShareOpenGraphContent");
            throw new FacebookException(object.toString());
        }
        object2 = new Intent();
        object2.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        object2.setAction("DeviceShareDialogFragment");
        object2.putExtra("content", (Parcelable)object);
        this.startActivityForResult((Intent)object2, this.getRequestCode());
    }

    public static class Result {
    }

}
