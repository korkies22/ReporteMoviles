/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.widget.ImageView
 */
package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.widget.ImageViewCompat;
import android.widget.ImageView;

static interface ImageViewCompat.ImageViewCompatImpl {
    public ColorStateList getImageTintList(ImageView var1);

    public PorterDuff.Mode getImageTintMode(ImageView var1);

    public void setImageTintList(ImageView var1, ColorStateList var2);

    public void setImageTintMode(ImageView var1, PorterDuff.Mode var2);
}
