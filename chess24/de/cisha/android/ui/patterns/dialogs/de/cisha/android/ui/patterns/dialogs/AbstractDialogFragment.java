/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Dialog
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Message
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 *  android.view.animation.ScaleAnimation
 */
package de.cisha.android.ui.patterns.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import de.cisha.android.ui.patterns.dialogs.BaseDialogFragment;

public abstract class AbstractDialogFragment
extends BaseDialogFragment {
    private static final int ANIMATION_TIME_IN = 300;
    private static final int ANIMATION_TIME_OUT = 400;
    private boolean _animate = true;
    private boolean _dismiss;
    private OnDialogCloseListener _listener;
    private boolean _showing;

    public AbstractDialogFragment() {
        this.setStyle(2, 0);
        this.setCancelable(false);
    }

    private void performScaleAnimation(final boolean bl) {
        if (this.getView() != null) {
            this.setHardwareLayerType(this.getView(), true);
            float f = bl ? 0.0f : 1.0f;
            float f2 = bl ? 1.0f : 0.0f;
            float f3 = bl ? 0.0f : 1.0f;
            float f4 = bl ? 1.0f : 0.0f;
            ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f3, f4, 1, 0.5f, 1, 0.5f);
            Object object = bl ? new OvershootInterpolator(0.9f) : new AnticipateInterpolator(0.9f);
            scaleAnimation.setInterpolator((Interpolator)object);
            long l = bl ? 400L : 300L;
            scaleAnimation.setDuration(l);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    AbstractDialogFragment.this.setHardwareLayerType(AbstractDialogFragment.this.getView(), false);
                    if (!bl) {
                        if (AbstractDialogFragment.this._showing) {
                            AbstractDialogFragment.super.dismiss();
                            return;
                        }
                        AbstractDialogFragment.this._dismiss = true;
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            this.getView().startAnimation((Animation)scaleAnimation);
        }
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType(View view, boolean bl) {
        if (Build.VERSION.SDK_INT >= 16 && view != null) {
            int n = bl ? 2 : 0;
            view.setLayerType(n, null);
        }
    }

    @Override
    public void dismiss() {
        if (this._animate) {
            this.performScaleAnimation(false);
            if (this._listener != null) {
                this._listener.onDialogClosed();
                return;
            }
        } else {
            super.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        if (this.getDialog() != null && this.getRetainInstance()) {
            this.getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        this._showing = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        this._showing = true;
        super.onResume();
        if (this._dismiss) {
            super.dismiss();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.getDialog().getWindow().addFlags(2);
        view = this.getDialog().getWindow().getAttributes();
        view.dimAmount = 0.5f;
        this.getDialog().getWindow().setAttributes((WindowManager.LayoutParams)view);
        if (this._animate) {
            this.performScaleAnimation(true);
        }
    }

    public void setAnimationEnabled(boolean bl) {
        this._animate = bl;
    }

    public void setOnDialogCloseListener(OnDialogCloseListener onDialogCloseListener) {
        this._listener = onDialogCloseListener;
    }

    public static interface OnDialogCloseListener {
        public void onDialogClosed();
    }

}
