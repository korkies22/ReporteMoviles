/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 */
package com.facebook.login.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.login.R;
import com.facebook.login.widget.ToolTipPopup;

private class ToolTipPopup.PopupContentView
extends FrameLayout {
    private View bodyFrame;
    private ImageView bottomArrow;
    private ImageView topArrow;
    private ImageView xOut;

    public ToolTipPopup.PopupContentView(Context context) {
        super(context);
        this.init();
    }

    static /* synthetic */ View access$300(ToolTipPopup.PopupContentView popupContentView) {
        return popupContentView.bodyFrame;
    }

    static /* synthetic */ ImageView access$400(ToolTipPopup.PopupContentView popupContentView) {
        return popupContentView.bottomArrow;
    }

    static /* synthetic */ ImageView access$500(ToolTipPopup.PopupContentView popupContentView) {
        return popupContentView.topArrow;
    }

    static /* synthetic */ ImageView access$600(ToolTipPopup.PopupContentView popupContentView) {
        return popupContentView.xOut;
    }

    private void init() {
        LayoutInflater.from((Context)this.getContext()).inflate(R.layout.com_facebook_tooltip_bubble, (ViewGroup)this);
        this.topArrow = (ImageView)this.findViewById(R.id.com_facebook_tooltip_bubble_view_top_pointer);
        this.bottomArrow = (ImageView)this.findViewById(R.id.com_facebook_tooltip_bubble_view_bottom_pointer);
        this.bodyFrame = this.findViewById(R.id.com_facebook_body_frame);
        this.xOut = (ImageView)this.findViewById(R.id.com_facebook_button_xout);
    }

    public void showBottomArrow() {
        this.topArrow.setVisibility(4);
        this.bottomArrow.setVisibility(0);
    }

    public void showTopArrow() {
        this.topArrow.setVisibility(0);
        this.bottomArrow.setVisibility(4);
    }
}
