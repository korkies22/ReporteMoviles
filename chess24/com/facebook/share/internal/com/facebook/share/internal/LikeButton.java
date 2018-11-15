/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 */
package com.facebook.share.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.facebook.FacebookButtonBase;
import com.facebook.common.R;

@Deprecated
public class LikeButton
extends FacebookButtonBase {
    @Deprecated
    public LikeButton(Context context, boolean bl) {
        super(context, null, 0, 0, "fb_like_button_create", "fb_like_button_did_tap");
        this.setSelected(bl);
    }

    private void updateForLikeStatus() {
        if (this.isSelected()) {
            this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
            this.setText((CharSequence)this.getResources().getString(R.string.com_facebook_like_button_liked));
            return;
        }
        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_icon, 0, 0, 0);
        this.setText((CharSequence)this.getResources().getString(R.string.com_facebook_like_button_not_liked));
    }

    @Override
    protected void configureButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super.configureButton(context, attributeSet, n, n2);
        this.updateForLikeStatus();
    }

    @Override
    protected int getDefaultRequestCode() {
        return 0;
    }

    @Override
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_button_like;
    }

    @Deprecated
    public void setSelected(boolean bl) {
        super.setSelected(bl);
        this.updateForLikeStatus();
    }
}
