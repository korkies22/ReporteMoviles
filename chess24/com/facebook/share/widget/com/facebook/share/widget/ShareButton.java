/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.R;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareButtonBase;
import com.facebook.share.widget.ShareDialog;

public final class ShareButton
extends ShareButtonBase {
    public ShareButton(Context context) {
        super(context, null, 0, "fb_share_button_create", "fb_share_button_did_tap");
    }

    public ShareButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, "fb_share_button_create", "fb_share_button_did_tap");
    }

    public ShareButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n, "fb_share_button_create", "fb_share_button_did_tap");
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
    protected FacebookDialogBase<ShareContent, Sharer.Result> getDialog() {
        if (this.getFragment() != null) {
            return new ShareDialog(this.getFragment(), this.getRequestCode());
        }
        if (this.getNativeFragment() != null) {
            return new ShareDialog(this.getNativeFragment(), this.getRequestCode());
        }
        return new ShareDialog(this.getActivity(), this.getRequestCode());
    }
}
