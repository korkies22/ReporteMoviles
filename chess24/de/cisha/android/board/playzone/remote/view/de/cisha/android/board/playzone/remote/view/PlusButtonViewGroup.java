/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.RotateAnimation
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PlusButtonViewGroup
extends LinearLayout {
    private ImageView _image;

    public PlusButtonViewGroup(Context context) {
        super(context);
        this.setGravity(17);
        this._image = new ImageView(context);
        this._image.setBackgroundResource(2131230726);
        this.setPadding(15, 0, 0, 0);
        this.addView((View)this._image, -2, -2);
    }

    public void setSelected(boolean bl) {
        super.setSelected(bl);
        ImageView imageView = this._image;
        int n = bl ? 2131231595 : 2131230726;
        imageView.setImageResource(n);
        float f = bl ? 0.0f : 45.0f;
        float f2 = bl ? 45.0f : 0.0f;
        imageView = new RotateAnimation(f, f2, 1, 0.5f, 1, 0.5f);
        imageView.setDuration(500L);
        imageView.setFillEnabled(true);
        imageView.setFillAfter(true);
        this._image.startAnimation((Animation)imageView);
    }
}
