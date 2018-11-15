/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.DeviceShareDialog;
import com.facebook.share.R;
import com.facebook.share.model.ShareContent;

public final class DeviceShareButton
extends FacebookButtonBase {
    private DeviceShareDialog dialog = null;
    private boolean enabledExplicitlySet = false;
    private int requestCode = 0;
    private ShareContent shareContent;

    public DeviceShareButton(Context context) {
        this(context, null, 0);
    }

    public DeviceShareButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private DeviceShareButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n, 0, "fb_device_share_button_create", "fb_device_share_button_did_tap");
        n = this.isInEditMode() ? 0 : this.getDefaultRequestCode();
        this.requestCode = n;
        this.internalSetEnabled(false);
    }

    private boolean canShare() {
        return new DeviceShareDialog(this.getActivity()).canShow(this.getShareContent());
    }

    private DeviceShareDialog getDialog() {
        if (this.dialog != null) {
            return this.dialog;
        }
        this.dialog = this.getFragment() != null ? new DeviceShareDialog(this.getFragment()) : (this.getNativeFragment() != null ? new DeviceShareDialog(this.getNativeFragment()) : new DeviceShareDialog(this.getActivity()));
        return this.dialog;
    }

    private void internalSetEnabled(boolean bl) {
        this.setEnabled(bl);
        this.enabledExplicitlySet = false;
    }

    private void setRequestCode(int n) {
        if (FacebookSdk.isFacebookRequestCode(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Request code ");
            stringBuilder.append(n);
            stringBuilder.append(" cannot be within the range reserved by the Facebook SDK.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.requestCode = n;
    }

    @Override
    protected void configureButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super.configureButton(context, attributeSet, n, n2);
        this.setInternalOnClickListener(this.getShareOnClickListener());
    }

    @Override
    protected int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    }

    @Override
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_button_share;
    }

    @Override
    public int getRequestCode() {
        return this.requestCode;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    protected View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener(){

            public void onClick(View view) {
                DeviceShareButton.this.callExternalOnClickListener(view);
                DeviceShareButton.this.getDialog().show(DeviceShareButton.this.getShareContent());
            }
        };
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> facebookCallback) {
        this.getDialog().registerCallback(callbackManager, facebookCallback);
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> facebookCallback, int n) {
        this.setRequestCode(n);
        this.getDialog().registerCallback(callbackManager, facebookCallback, n);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.enabledExplicitlySet = true;
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
        if (!this.enabledExplicitlySet) {
            this.internalSetEnabled(this.canShare());
        }
    }

}
