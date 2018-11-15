/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 *  android.widget.ImageView
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public class MenuBarItem.GlowAnimation
extends Animation {
    private ImageView _blinkingImage;

    public MenuBarItem.GlowAnimation(ImageView imageView) {
        this._blinkingImage = imageView;
        this.setRepeatMode(2);
        this.setRepeatCount(-1);
        this.setDuration(1000L);
    }

    protected void applyTransformation(float f, Transformation transformation) {
        int n = (int)(200.0f * f);
        this._blinkingImage.setAlpha(n + 55);
    }
}
