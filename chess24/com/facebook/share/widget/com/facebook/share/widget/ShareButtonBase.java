/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;

public abstract class ShareButtonBase
extends FacebookButtonBase {
    private boolean enabledExplicitlySet = false;
    private int requestCode = 0;
    private ShareContent shareContent;

    protected ShareButtonBase(Context context, AttributeSet attributeSet, int n, String string, String string2) {
        super(context, attributeSet, n, 0, string, string2);
        n = this.isInEditMode() ? 0 : this.getDefaultRequestCode();
        this.requestCode = n;
        this.internalSetEnabled(false);
    }

    private void internalSetEnabled(boolean bl) {
        this.setEnabled(bl);
        this.enabledExplicitlySet = false;
    }

    protected boolean canShare() {
        return this.getDialog().canShow(this.getShareContent());
    }

    @Override
    protected void configureButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super.configureButton(context, attributeSet, n, n2);
        this.setInternalOnClickListener(this.getShareOnClickListener());
    }

    protected abstract FacebookDialogBase<ShareContent, Sharer.Result> getDialog();

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
                ShareButtonBase.this.callExternalOnClickListener(view);
                ShareButtonBase.this.getDialog().show(ShareButtonBase.this.getShareContent());
            }
        };
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.registerSharerCallback(this.getRequestCode(), callbackManager, facebookCallback);
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> facebookCallback, int n) {
        this.setRequestCode(n);
        this.registerCallback(callbackManager, facebookCallback);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.enabledExplicitlySet = true;
    }

    protected void setRequestCode(int n) {
        if (FacebookSdk.isFacebookRequestCode(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Request code ");
            stringBuilder.append(n);
            stringBuilder.append(" cannot be within the range reserved by the Facebook SDK.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.requestCode = n;
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
        if (!this.enabledExplicitlySet) {
            this.internalSetEnabled(this.canShare());
        }
    }

}
