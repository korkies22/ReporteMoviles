/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

class AppCompatPopupWindow
extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR;
    private boolean mOverlapAnchor;

    static {
        boolean bl = Build.VERSION.SDK_INT < 21;
        COMPAT_OVERLAP_ANCHOR = bl;
    }

    public AppCompatPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet, n, 0);
    }

    public AppCompatPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int n, @StyleRes int n2) {
        super(context, attributeSet, n, n2);
        this.init(context, attributeSet, n, n2);
    }

    private void init(Context object, AttributeSet attributeSet, int n, int n2) {
        if ((object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.PopupWindow, n, n2)).hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            this.setSupportOverlapAnchor(object.getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        this.setBackgroundDrawable(object.getDrawable(R.styleable.PopupWindow_android_popupBackground));
        object.recycle();
    }

    private void setSupportOverlapAnchor(boolean bl) {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = bl;
            return;
        }
        PopupWindowCompat.setOverlapAnchor(this, bl);
    }

    public void showAsDropDown(View view, int n, int n2) {
        int n3 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n3 = n2;
            if (this.mOverlapAnchor) {
                n3 = n2 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n, n3);
    }

    public void showAsDropDown(View view, int n, int n2, int n3) {
        int n4 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n4 = n2;
            if (this.mOverlapAnchor) {
                n4 = n2 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n, n4, n3);
    }

    public void update(View view, int n, int n2, int n3, int n4) {
        int n5 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n5 = n2;
            if (this.mOverlapAnchor) {
                n5 = n2 - view.getHeight();
            }
        }
        super.update(view, n, n5, n3, n4);
    }
}
