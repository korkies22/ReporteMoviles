// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.view.WindowManager.LayoutParams;
import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.View;

public abstract class AbstractDialogFragment extends BaseDialogFragment
{
    private static final int ANIMATION_TIME_IN = 300;
    private static final int ANIMATION_TIME_OUT = 400;
    private boolean _animate;
    private boolean _dismiss;
    private OnDialogCloseListener _listener;
    private boolean _showing;
    
    public AbstractDialogFragment() {
        this._animate = true;
        this.setStyle(2, 0);
        this.setCancelable(false);
    }
    
    private void performScaleAnimation(final boolean b) {
        if (this.getView() != null) {
            this.setHardwareLayerType(this.getView(), true);
            float n;
            if (b) {
                n = 0.0f;
            }
            else {
                n = 1.0f;
            }
            float n2;
            if (b) {
                n2 = 1.0f;
            }
            else {
                n2 = 0.0f;
            }
            float n3;
            if (b) {
                n3 = 0.0f;
            }
            else {
                n3 = 1.0f;
            }
            float n4;
            if (b) {
                n4 = 1.0f;
            }
            else {
                n4 = 0.0f;
            }
            final ScaleAnimation scaleAnimation = new ScaleAnimation(n, n2, n3, n4, 1, 0.5f, 1, 0.5f);
            Object interpolator;
            if (b) {
                interpolator = new OvershootInterpolator(0.9f);
            }
            else {
                interpolator = new AnticipateInterpolator(0.9f);
            }
            scaleAnimation.setInterpolator((Interpolator)interpolator);
            long duration;
            if (b) {
                duration = 400L;
            }
            else {
                duration = 300L;
            }
            scaleAnimation.setDuration(duration);
            scaleAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    AbstractDialogFragment.this.setHardwareLayerType(AbstractDialogFragment.this.getView(), false);
                    if (!b) {
                        if (AbstractDialogFragment.this._showing) {
                            AbstractDialogFragment.this.dismiss();
                            return;
                        }
                        AbstractDialogFragment.this._dismiss = true;
                    }
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                }
            });
            this.getView().startAnimation((Animation)scaleAnimation);
        }
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType(final View view, final boolean b) {
        if (Build.VERSION.SDK_INT >= 16 && view != null) {
            int n;
            if (b) {
                n = 2;
            }
            else {
                n = 0;
            }
            view.setLayerType(n, (Paint)null);
        }
    }
    
    @Override
    public void dismiss() {
        if (this._animate) {
            this.performScaleAnimation(false);
            if (this._listener != null) {
                this._listener.onDialogClosed();
            }
        }
        else {
            super.dismiss();
        }
    }
    
    @Override
    public void onDestroyView() {
        if (this.getDialog() != null && this.getRetainInstance()) {
            this.getDialog().setDismissMessage((Message)null);
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
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.getDialog().getWindow().addFlags(2);
        final WindowManager.LayoutParams attributes = this.getDialog().getWindow().getAttributes();
        attributes.dimAmount = 0.5f;
        this.getDialog().getWindow().setAttributes(attributes);
        if (this._animate) {
            this.performScaleAnimation(true);
        }
    }
    
    public void setAnimationEnabled(final boolean animate) {
        this._animate = animate;
    }
    
    public void setOnDialogCloseListener(final OnDialogCloseListener listener) {
        this._listener = listener;
    }
    
    public interface OnDialogCloseListener
    {
        void onDialogClosed();
    }
}
