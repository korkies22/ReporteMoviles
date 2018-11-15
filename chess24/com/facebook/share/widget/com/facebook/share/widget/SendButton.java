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
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButtonBase;

public final class SendButton
extends ShareButtonBase {
    public SendButton(Context context) {
        super(context, null, 0, "fb_send_button_create", "fb_send_button_did_tap");
    }

    public SendButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, "fb_send_button_create", "fb_send_button_did_tap");
    }

    public SendButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n, "fb_send_button_create", "fb_send_button_did_tap");
    }

    @Override
    protected int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
    }

    @Override
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_button_send;
    }

    @Override
    protected FacebookDialogBase<ShareContent, Sharer.Result> getDialog() {
        if (this.getFragment() != null) {
            return new MessageDialog(this.getFragment(), this.getRequestCode());
        }
        if (this.getNativeFragment() != null) {
            return new MessageDialog(this.getNativeFragment(), this.getRequestCode());
        }
        return new MessageDialog(this.getActivity(), this.getRequestCode());
    }
}
