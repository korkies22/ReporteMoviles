/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import de.cisha.android.board.view.ToastView;

public class ProfileSavedToastView
extends ToastView {
    public ProfileSavedToastView(Context context) {
        super(context);
        this.init();
    }

    public ProfileSavedToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        ProfileSavedToastView.inflate((Context)this.getContext(), (int)2131427521, (ViewGroup)this);
    }
}
