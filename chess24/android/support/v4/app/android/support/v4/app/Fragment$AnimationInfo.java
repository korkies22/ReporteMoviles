/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.view.View
 */
package android.support.v4.app;

import android.animation.Animator;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.view.View;

static class Fragment.AnimationInfo {
    private Boolean mAllowEnterTransitionOverlap;
    private Boolean mAllowReturnTransitionOverlap;
    View mAnimatingAway;
    Animator mAnimator;
    private Object mEnterTransition = null;
    SharedElementCallback mEnterTransitionCallback = null;
    boolean mEnterTransitionPostponed;
    private Object mExitTransition = null;
    SharedElementCallback mExitTransitionCallback = null;
    boolean mIsHideReplaced;
    int mNextAnim;
    int mNextTransition;
    int mNextTransitionStyle;
    private Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
    private Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    private Object mSharedElementEnterTransition = null;
    private Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
    int mStateAfterAnimating;

    Fragment.AnimationInfo() {
    }

    static /* synthetic */ Object access$000(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mEnterTransition;
    }

    static /* synthetic */ Object access$002(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mEnterTransition = object;
        return object;
    }

    static /* synthetic */ Object access$100(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mReturnTransition;
    }

    static /* synthetic */ Object access$102(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mReturnTransition = object;
        return object;
    }

    static /* synthetic */ Object access$200(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mExitTransition;
    }

    static /* synthetic */ Object access$202(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mExitTransition = object;
        return object;
    }

    static /* synthetic */ Object access$300(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mReenterTransition;
    }

    static /* synthetic */ Object access$302(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mReenterTransition = object;
        return object;
    }

    static /* synthetic */ Object access$400(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mSharedElementEnterTransition;
    }

    static /* synthetic */ Object access$402(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mSharedElementEnterTransition = object;
        return object;
    }

    static /* synthetic */ Object access$500(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mSharedElementReturnTransition;
    }

    static /* synthetic */ Object access$502(Fragment.AnimationInfo animationInfo, Object object) {
        animationInfo.mSharedElementReturnTransition = object;
        return object;
    }

    static /* synthetic */ Boolean access$600(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mAllowEnterTransitionOverlap;
    }

    static /* synthetic */ Boolean access$602(Fragment.AnimationInfo animationInfo, Boolean bl) {
        animationInfo.mAllowEnterTransitionOverlap = bl;
        return bl;
    }

    static /* synthetic */ Boolean access$700(Fragment.AnimationInfo animationInfo) {
        return animationInfo.mAllowReturnTransitionOverlap;
    }

    static /* synthetic */ Boolean access$702(Fragment.AnimationInfo animationInfo, Boolean bl) {
        animationInfo.mAllowReturnTransitionOverlap = bl;
        return bl;
    }
}
