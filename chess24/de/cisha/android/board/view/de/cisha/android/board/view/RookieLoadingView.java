/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RookieLoadingView
extends LinearLayout {
    private AnimationDrawable _loadingAanimationDrawable;

    public RookieLoadingView(Context context) {
        super(context);
        this.init();
    }

    public RookieLoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setGravity(17);
        RookieLoadingView.inflate((Context)this.getContext(), (int)2131427529, (ViewGroup)this);
        this._loadingAanimationDrawable = (AnimationDrawable)((ImageView)this.findViewById(2131296575)).getDrawable();
    }

    public void enableLoadingAnimation(boolean bl) {
        if (bl) {
            this._loadingAanimationDrawable.start();
            return;
        }
        this._loadingAanimationDrawable.stop();
    }
}
