/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.transition.CircularPropagation;
import android.support.transition.R;
import android.support.transition.TransitionPropagation;
import android.support.transition.TransitionValues;
import android.support.transition.TranslationAnimationCreator;
import android.support.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Map;

public class Explode
extends Visibility {
    private static final String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
    private static final TimeInterpolator sAccelerate;
    private static final TimeInterpolator sDecelerate;
    private int[] mTempLoc = new int[2];

    static {
        sDecelerate = new DecelerateInterpolator();
        sAccelerate = new AccelerateInterpolator();
    }

    public Explode() {
        this.setPropagation(new CircularPropagation());
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setPropagation(new CircularPropagation());
    }

    private static float calculateDistance(float f, float f2) {
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    private static float calculateMaxDistance(View view, int n, int n2) {
        n = Math.max(n, view.getWidth() - n);
        n2 = Math.max(n2, view.getHeight() - n2);
        return Explode.calculateDistance(n, n2);
    }

    private void calculateOut(View view, Rect rect, int[] arrn) {
        int n;
        int n2;
        view.getLocationOnScreen(this.mTempLoc);
        int n3 = this.mTempLoc[0];
        int n4 = this.mTempLoc[1];
        Rect rect2 = this.getEpicenter();
        if (rect2 == null) {
            n = view.getWidth() / 2 + n3 + Math.round(view.getTranslationX());
            n2 = view.getHeight() / 2 + n4 + Math.round(view.getTranslationY());
        } else {
            n = rect2.centerX();
            n2 = rect2.centerY();
        }
        int n5 = rect.centerX();
        int n6 = rect.centerY();
        float f = n5 - n;
        float f2 = n6 - n2;
        float f3 = f;
        float f4 = f2;
        if (f == 0.0f) {
            f3 = f;
            f4 = f2;
            if (f2 == 0.0f) {
                f3 = (float)(Math.random() * 2.0) - 1.0f;
                f4 = (float)(Math.random() * 2.0) - 1.0f;
            }
        }
        f = Explode.calculateDistance(f3, f4);
        f = Explode.calculateMaxDistance(view, n - n3, n2 - n4);
        arrn[0] = Math.round((f3 /= f) * f);
        arrn[1] = Math.round(f * (f4 /= f));
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(this.mTempLoc);
        int n = this.mTempLoc[0];
        int n2 = this.mTempLoc[1];
        int n3 = view.getWidth();
        int n4 = view.getHeight();
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, (Object)new Rect(n, n2, n3 + n, n4 + n2));
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        transitionValues = (Rect)transitionValues2.values.get(PROPNAME_SCREEN_BOUNDS);
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        this.calculateOut((View)viewGroup, (Rect)transitionValues, this.mTempLoc);
        float f3 = this.mTempLoc[0];
        float f4 = this.mTempLoc[1];
        return TranslationAnimationCreator.createAnimation(view, transitionValues2, transitionValues.left, transitionValues.top, f + f3, f2 + f4, f, f2, sDecelerate);
    }

    @Override
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        transitionValues2 = (Rect)transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        int n = transitionValues2.left;
        int n2 = transitionValues2.top;
        float f3 = view.getTranslationX();
        float f4 = view.getTranslationY();
        int[] arrn = (int[])transitionValues.view.getTag(R.id.transition_position);
        if (arrn != null) {
            f = (float)(arrn[0] - transitionValues2.left) + f3;
            f2 = (float)(arrn[1] - transitionValues2.top) + f4;
            transitionValues2.offsetTo(arrn[0], arrn[1]);
        } else {
            f = f3;
            f2 = f4;
        }
        this.calculateOut((View)viewGroup, (Rect)transitionValues2, this.mTempLoc);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, n, n2, f3, f4, f + (float)this.mTempLoc[0], f2 + (float)this.mTempLoc[1], sAccelerate);
    }
}
