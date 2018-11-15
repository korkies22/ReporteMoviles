/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.LinearLayout
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static class ActivityChooserView.InnerLayout
extends LinearLayout {
    private static final int[] TINT_ATTRS = new int[]{16842964};

    public ActivityChooserView.InnerLayout(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, TINT_ATTRS);
        this.setBackgroundDrawable(object.getDrawable(0));
        object.recycle();
    }
}
