/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 */
package de.cisha.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import de.cisha.android.view.RoundedImageDrawable;
import de.cisha.android.view.WebImageView;

public class RoundedWebImageView
extends WebImageView {
    private int _cornerRadius;

    public RoundedWebImageView(Context context) {
        super(context);
        this.init();
    }

    public RoundedWebImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public RoundedWebImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void init() {
        this._cornerRadius = (int)(this.getContext().getResources().getDisplayMetrics().density * 5.0f);
    }

    public int getCornerRadius() {
        return this._cornerRadius;
    }

    public void setCornerRadius(int n) {
        this._cornerRadius = n;
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.setImageDrawable((Drawable)new RoundedImageDrawable(bitmap, this._cornerRadius, 5));
        }
    }
}
