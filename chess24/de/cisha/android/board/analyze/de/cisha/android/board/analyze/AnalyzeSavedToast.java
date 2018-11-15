/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import de.cisha.android.board.view.ToastView;

public class AnalyzeSavedToast
extends ToastView {
    public AnalyzeSavedToast(Context context) {
        super(context);
        this.initView();
    }

    public AnalyzeSavedToast(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private void initView() {
        AnalyzeSavedToast.inflate((Context)this.getContext(), (int)2131427371, (ViewGroup)this);
    }
}
