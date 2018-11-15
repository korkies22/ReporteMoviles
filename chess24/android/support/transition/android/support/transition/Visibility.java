/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.transition.AnimatorUtils;
import android.support.transition.AnimatorUtilsApi14;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public abstract class Visibility
extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties = new String[]{"android:visibility:visibility", "android:visibility:parent"};
    private int mMode = 3;

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int n = TypedArrayUtils.getNamedInt((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "transitionVisibilityMode", 0, 0);
        context.recycle();
        if (n != 0) {
            this.setMode(n);
        }
    }

    private void captureValues(TransitionValues transitionValues) {
        int n = transitionValues.view.getVisibility();
        transitionValues.values.put(PROPNAME_VISIBILITY, n);
        transitionValues.values.put(PROPNAME_PARENT, (Object)transitionValues.view.getParent());
        int[] arrn = new int[2];
        transitionValues.view.getLocationOnScreen(arrn);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, arrn);
    }

    private VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.mStartVisibility = (Integer)transitionValues.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.mStartParent = (ViewGroup)transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.mEndVisibility = (Integer)transitionValues2.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.mEndParent = (ViewGroup)transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.mStartVisibility == visibilityInfo.mEndVisibility && visibilityInfo.mStartParent == visibilityInfo.mEndParent) {
                return visibilityInfo;
            }
            if (visibilityInfo.mStartVisibility != visibilityInfo.mEndVisibility) {
                if (visibilityInfo.mStartVisibility == 0) {
                    visibilityInfo.mFadeIn = false;
                    visibilityInfo.mVisibilityChange = true;
                    return visibilityInfo;
                }
                if (visibilityInfo.mEndVisibility == 0) {
                    visibilityInfo.mFadeIn = true;
                    visibilityInfo.mVisibilityChange = true;
                    return visibilityInfo;
                }
            } else {
                if (visibilityInfo.mEndParent == null) {
                    visibilityInfo.mFadeIn = false;
                    visibilityInfo.mVisibilityChange = true;
                    return visibilityInfo;
                }
                if (visibilityInfo.mStartParent == null) {
                    visibilityInfo.mFadeIn = true;
                    visibilityInfo.mVisibilityChange = true;
                    return visibilityInfo;
                }
            }
        } else {
            if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
                visibilityInfo.mFadeIn = true;
                visibilityInfo.mVisibilityChange = true;
                return visibilityInfo;
            }
            if (transitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
                visibilityInfo.mFadeIn = false;
                visibilityInfo.mVisibilityChange = true;
            }
        }
        return visibilityInfo;
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = this.getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityInfo.mVisibilityChange && (visibilityInfo.mStartParent != null || visibilityInfo.mEndParent != null)) {
            if (visibilityInfo.mFadeIn) {
                return this.onAppear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
            }
            return this.onDisappear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
        }
        return null;
    }

    public int getMode() {
        return this.mMode;
    }

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public boolean isTransitionRequired(TransitionValues object, TransitionValues transitionValues) {
        boolean bl;
        block5 : {
            block6 : {
                boolean bl2 = false;
                if (object == null && transitionValues == null) {
                    return false;
                }
                if (object != null && transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY) != object.values.containsKey(PROPNAME_VISIBILITY)) {
                    return false;
                }
                object = this.getVisibilityChangeInfo((TransitionValues)object, transitionValues);
                bl = bl2;
                if (!object.mVisibilityChange) break block5;
                if (object.mStartVisibility == 0) break block6;
                bl = bl2;
                if (object.mEndVisibility != 0) break block5;
            }
            bl = true;
        }
        return bl;
    }

    public boolean isVisible(TransitionValues transitionValues) {
        boolean bl = false;
        if (transitionValues == null) {
            return false;
        }
        int n = (Integer)transitionValues.values.get(PROPNAME_VISIBILITY);
        transitionValues = (View)transitionValues.values.get(PROPNAME_PARENT);
        boolean bl2 = bl;
        if (n == 0) {
            bl2 = bl;
            if (transitionValues != null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int n, TransitionValues transitionValues2, int n2) {
        if ((this.mMode & 1) == 1) {
            if (transitionValues2 == null) {
                return null;
            }
            if (transitionValues == null) {
                View view = (View)transitionValues2.view.getParent();
                if (this.getVisibilityChangeInfo((TransitionValues)this.getMatchedTransitionValues((View)view, (boolean)false), (TransitionValues)this.getTransitionValues((View)view, (boolean)false)).mVisibilityChange) {
                    return null;
                }
            }
            return this.onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
        }
        return null;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues object, int n, TransitionValues transitionValues, int n2) {
        Object object2;
        View view;
        block14 : {
            block13 : {
                View view2;
                block11 : {
                    block12 : {
                        if ((this.mMode & 2) != 2) {
                            return null;
                        }
                        view2 = object != null ? object.view : null;
                        object2 = transitionValues != null ? transitionValues.view : null;
                        if (object2 == null || object2.getParent() == null) break block11;
                        if (n2 == 4) break block12;
                        view = view2;
                        if (view2 != object2) break block13;
                    }
                    view = null;
                    break block14;
                }
                if (object2 != null) {
                    view = object2;
                } else {
                    if (view2 == null) return null;
                    if (view2.getParent() == null) {
                        view = view2;
                    } else {
                        if (!(view2.getParent() instanceof View)) return null;
                        view = (View)view2.getParent();
                        if (!this.getVisibilityChangeInfo((TransitionValues)this.getTransitionValues((View)view, (boolean)true), (TransitionValues)this.getMatchedTransitionValues((View)view, (boolean)true)).mVisibilityChange) {
                            view = TransitionUtils.copyViewImage(viewGroup, view2, view);
                        } else {
                            if (view.getParent() != null) return null;
                            n = view.getId();
                            if (n == -1) return null;
                            if (viewGroup.findViewById(n) == null) return null;
                            if (!this.mCanRemoveViews) return null;
                            view = view2;
                        }
                    }
                }
            }
            object2 = null;
        }
        if (view != null && object != null) {
            object2 = (View)object.values.get(PROPNAME_SCREEN_LOCATION);
            n = object2[0];
            n2 = object2[1];
            object2 = new int[2];
            viewGroup.getLocationOnScreen((int[])object2);
            view.offsetLeftAndRight(n - object2[0] - view.getLeft());
            view.offsetTopAndBottom(n2 - object2[1] - view.getTop());
            object2 = ViewGroupUtils.getOverlay(viewGroup);
            object2.add(view);
            viewGroup = this.onDisappear(viewGroup, view, (TransitionValues)object, transitionValues);
            if (viewGroup == null) {
                object2.remove(view);
                return viewGroup;
            }
            viewGroup.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewGroupOverlayImpl)object2, view){
                final /* synthetic */ View val$finalOverlayView;
                final /* synthetic */ ViewGroupOverlayImpl val$overlay;
                {
                    this.val$overlay = viewGroupOverlayImpl;
                    this.val$finalOverlayView = view;
                }

                public void onAnimationEnd(Animator animator) {
                    this.val$overlay.remove(this.val$finalOverlayView);
                }
            });
            return viewGroup;
        }
        if (object2 == null) {
            return null;
        }
        n = object2.getVisibility();
        ViewUtils.setTransitionVisibility((View)object2, 0);
        viewGroup = this.onDisappear(viewGroup, (View)object2, (TransitionValues)object, transitionValues);
        if (viewGroup != null) {
            object = new DisappearListener((View)object2, n2, true);
            viewGroup.addListener((Animator.AnimatorListener)object);
            AnimatorUtils.addPauseListener((Animator)viewGroup, (AnimatorListenerAdapter)object);
            this.addListener((Transition.TransitionListener)object);
            return viewGroup;
        }
        ViewUtils.setTransitionVisibility((View)object2, n);
        return viewGroup;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public void setMode(int n) {
        if ((n & -4) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = n;
    }

    private static class DisappearListener
    extends AnimatorListenerAdapter
    implements Transition.TransitionListener,
    AnimatorUtilsApi14.AnimatorPauseListenerCompat {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        DisappearListener(View view, int n, boolean bl) {
            this.mView = view;
            this.mFinalVisibility = n;
            this.mParent = (ViewGroup)view.getParent();
            this.mSuppressLayout = bl;
            this.suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
            this.suppressLayout(false);
        }

        private void suppressLayout(boolean bl) {
            if (this.mSuppressLayout && this.mLayoutSuppressed != bl && this.mParent != null) {
                this.mLayoutSuppressed = bl;
                ViewGroupUtils.suppressLayout(this.mParent, bl);
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator) {
            this.hideViewWhenNotCanceled();
        }

        @Override
        public void onAnimationPause(Animator animator) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            }
        }

        public void onAnimationRepeat(Animator animator) {
        }

        @Override
        public void onAnimationResume(Animator animator) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
            }
        }

        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onTransitionCancel(@NonNull Transition transition) {
        }

        @Override
        public void onTransitionEnd(@NonNull Transition transition) {
            this.hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        @Override
        public void onTransitionPause(@NonNull Transition transition) {
            this.suppressLayout(false);
        }

        @Override
        public void onTransitionResume(@NonNull Transition transition) {
            this.suppressLayout(true);
        }

        @Override
        public void onTransitionStart(@NonNull Transition transition) {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface Mode {
    }

    private static class VisibilityInfo {
        ViewGroup mEndParent;
        int mEndVisibility;
        boolean mFadeIn;
        ViewGroup mStartParent;
        int mStartVisibility;
        boolean mVisibilityChange;

        private VisibilityInfo() {
        }
    }

}
