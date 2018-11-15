/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.RotateAnimation
 *  android.view.animation.Transformation
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote;

import android.graphics.Color;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.TextView;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

private class PlayzoneRemoteFragment.PlayzoneWaitingScreen.WaitingAnimation
extends RotateAnimation {
    private TextView _textView;

    public PlayzoneRemoteFragment.PlayzoneWaitingScreen.WaitingAnimation(TextView textView) {
        super(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        this._textView = textView;
        this.setDuration(3000L);
        this.setRepeatCount(-1);
        this.setRepeatMode(1);
        this.setInterpolator((Interpolator)new LinearInterpolator());
    }

    protected void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        int n = Color.argb((int)244, (int)0, (int)0, (int)((int)(180.0 * ((Math.sin((double)f * 3.141592653589793 * 2.0) + 1.0) / 2.0)) + 20));
        this._textView.setTextColor(n);
    }
}
