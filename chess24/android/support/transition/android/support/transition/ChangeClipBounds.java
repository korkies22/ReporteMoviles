/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.transition.RectEvaluator;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.support.transition.ViewUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeClipBounds
extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = new String[]{"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        Rect rect = ViewCompat.getClipBounds(view);
        transitionValues.values.put(PROPNAME_CLIP, (Object)rect);
        if (rect == null) {
            view = new Rect(0, 0, view.getWidth(), view.getHeight());
            transitionValues.values.put(PROPNAME_BOUNDS, (Object)view);
        }
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(@NonNull ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null && transitionValues.values.containsKey(PROPNAME_CLIP)) {
            if (!transitionValues2.values.containsKey(PROPNAME_CLIP)) {
                return null;
            }
            Object object = (Rect)transitionValues.values.get(PROPNAME_CLIP);
            Rect rect = (Rect)transitionValues2.values.get(PROPNAME_CLIP);
            boolean bl = rect == null;
            if (object == null && rect == null) {
                return null;
            }
            if (object == null) {
                viewGroup = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
                transitionValues = rect;
            } else {
                viewGroup = object;
                transitionValues = rect;
                if (rect == null) {
                    transitionValues = (Rect)transitionValues2.values.get(PROPNAME_BOUNDS);
                    viewGroup = object;
                }
            }
            if (viewGroup.equals((Object)transitionValues)) {
                return null;
            }
            ViewCompat.setClipBounds(transitionValues2.view, (Rect)viewGroup);
            object = new RectEvaluator(new Rect());
            viewGroup = ObjectAnimator.ofObject((Object)transitionValues2.view, ViewUtils.CLIP_BOUNDS, (TypeEvaluator)object, (Object[])new Rect[]{viewGroup, transitionValues});
            if (bl) {
                viewGroup.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(transitionValues2.view){
                    final /* synthetic */ View val$endView;
                    {
                        this.val$endView = view;
                    }

                    public void onAnimationEnd(Animator animator) {
                        ViewCompat.setClipBounds(this.val$endView, null);
                    }
                });
            }
            return viewGroup;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}
