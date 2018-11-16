// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.View;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PlusButtonViewGroup extends LinearLayout
{
    private ImageView _image;
    
    public PlusButtonViewGroup(final Context context) {
        super(context);
        this.setGravity(17);
        (this._image = new ImageView(context)).setBackgroundResource(2131230726);
        this.setPadding(15, 0, 0, 0);
        this.addView((View)this._image, -2, -2);
    }
    
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        final ImageView image = this._image;
        int imageResource;
        if (selected) {
            imageResource = 2131231595;
        }
        else {
            imageResource = 2131230726;
        }
        image.setImageResource(imageResource);
        float n;
        if (selected) {
            n = 0.0f;
        }
        else {
            n = 45.0f;
        }
        float n2;
        if (selected) {
            n2 = 45.0f;
        }
        else {
            n2 = 0.0f;
        }
        final RotateAnimation rotateAnimation = new RotateAnimation(n, n2, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(500L);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setFillAfter(true);
        this._image.startAnimation((Animation)rotateAnimation);
    }
}
