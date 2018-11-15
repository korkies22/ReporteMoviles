/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.widget.CompoundButton
 */
package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.CompoundButtonCompat;
import android.widget.CompoundButton;

@RequiresApi(value=23)
static class CompoundButtonCompat.CompoundButtonCompatApi23Impl
extends CompoundButtonCompat.CompoundButtonCompatApi21Impl {
    CompoundButtonCompat.CompoundButtonCompatApi23Impl() {
    }

    @Override
    public Drawable getButtonDrawable(CompoundButton compoundButton) {
        return compoundButton.getButtonDrawable();
    }
}
