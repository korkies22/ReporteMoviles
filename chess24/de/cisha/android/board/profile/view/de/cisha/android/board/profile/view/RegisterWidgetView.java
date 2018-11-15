/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RegisterWidgetView
extends LinearLayout {
    public RegisterWidgetView(Context context) {
        super(context);
        this.initView();
    }

    public RegisterWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private void initView() {
        RegisterWidgetView.inflate((Context)this.getContext(), (int)2131427520, (ViewGroup)this);
    }
}
