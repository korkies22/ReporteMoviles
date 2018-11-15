/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.PropertyValuesHolder
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.AnimationUtils
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.ScaleAnimation
 *  android.view.animation.Transformation
 */
package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.BackStackState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentManagerState;
import android.support.v4.app.FragmentState;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransition;
import android.support.v4.app.OneShotPreDrawListener;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl
extends FragmentManager
implements LayoutInflater.Factory2 {
    static final Interpolator ACCELERATE_CUBIC;
    static final Interpolator ACCELERATE_QUINT;
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC;
    static final Interpolator DECELERATE_QUINT;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField;
    SparseArray<Fragment> mActive;
    final ArrayList<Fragment> mAdded = new ArrayList();
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable(){

        @Override
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    private final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList();
    boolean mNeedMenuInvalidate;
    int mNextFragmentIndex = 0;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    FragmentManagerNonConfig mSavedNonConfig;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    boolean mStopped;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    static {
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
        ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
        ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    }

    FragmentManagerImpl() {
    }

    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        if (this.mCurState < 1) {
            return;
        }
        int n = Math.min(this.mCurState, 4);
        int n2 = this.mAdded.size();
        for (int i = 0; i < n2; ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment.mState >= n) continue;
            this.moveToState(fragment, n, fragment.getNextAnim(), fragment.getNextTransition(), false);
            if (fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded) continue;
            arraySet.add(fragment);
        }
    }

    private void animateRemoveFragment(final @NonNull Fragment fragment, @NonNull AnimationOrAnimator animationOrAnimator, int n) {
        final View view = fragment.mView;
        final ViewGroup viewGroup = fragment.mContainer;
        viewGroup.startViewTransition(view);
        fragment.setStateAfterAnimating(n);
        if (animationOrAnimator.animation != null) {
            EndViewTransitionAnimator endViewTransitionAnimator = new EndViewTransitionAnimator(animationOrAnimator.animation, viewGroup, view);
            fragment.setAnimatingAway(fragment.mView);
            endViewTransitionAnimator.setAnimationListener((Animation.AnimationListener)new AnimationListenerWrapper(FragmentManagerImpl.getAnimationListener((Animation)endViewTransitionAnimator)){

                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    viewGroup.post(new Runnable(){

                        @Override
                        public void run() {
                            if (fragment.getAnimatingAway() != null) {
                                fragment.setAnimatingAway(null);
                                FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                            }
                        }
                    });
                }

            });
            FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(view, animationOrAnimator);
            fragment.mView.startAnimation((Animation)endViewTransitionAnimator);
            return;
        }
        Animator animator = animationOrAnimator.animator;
        fragment.setAnimator(animationOrAnimator.animator);
        animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                viewGroup.endViewTransition(view);
                animator = fragment.getAnimator();
                fragment.setAnimator(null);
                if (animator != null && viewGroup.indexOfChild(view) < 0) {
                    FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                }
            }
        });
        animator.setTarget((Object)fragment.mView);
        FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
        animator.start();
    }

    private void burpActive() {
        if (this.mActive != null) {
            for (int i = this.mActive.size() - 1; i >= 0; --i) {
                if (this.mActive.valueAt(i) != null) continue;
                this.mActive.delete(this.mActive.keyAt(i));
            }
        }
    }

    private void checkStateLoss() {
        if (this.isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can not perform this action inside of ");
            stringBuilder.append(this.mNoTransactionsBecause);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void completeExecute(BackStackRecord backStackRecord, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            backStackRecord.executePopOps(bl3);
        } else {
            backStackRecord.executeOps();
        }
        Object object = new ArrayList<BackStackRecord>(1);
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>(1);
        object.add(backStackRecord);
        arrayList.add(bl);
        if (bl2) {
            FragmentTransition.startTransitions(this, object, arrayList, 0, 1, true);
        }
        if (bl3) {
            this.moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int n = this.mActive.size();
            for (int i = 0; i < n; ++i) {
                object = (Fragment)this.mActive.valueAt(i);
                if (object == null || object.mView == null || !object.mIsNewlyAdded || !backStackRecord.interactsWith(object.mContainerId)) continue;
                if (object.mPostponedAlpha > 0.0f) {
                    object.mView.setAlpha(object.mPostponedAlpha);
                }
                if (bl3) {
                    object.mPostponedAlpha = 0.0f;
                    continue;
                }
                object.mPostponedAlpha = -1.0f;
                object.mIsNewlyAdded = false;
            }
        }
    }

    private void dispatchStateChange(int n) {
        try {
            this.mExecutingActions = true;
            this.moveToState(n, false);
            this.execPendingActions();
            return;
        }
        finally {
            this.mExecutingActions = false;
        }
    }

    private void endAnimatingAwayFragments() {
        Object object = this.mActive;
        int n = object == null ? 0 : this.mActive.size();
        for (int i = 0; i < n; ++i) {
            object = (Fragment)this.mActive.valueAt(i);
            if (object == null) continue;
            if (object.getAnimatingAway() != null) {
                int n2 = object.getStateAfterAnimating();
                View view = object.getAnimatingAway();
                Animation animation = view.getAnimation();
                if (animation != null) {
                    animation.cancel();
                    view.clearAnimation();
                }
                object.setAnimatingAway(null);
                this.moveToState((Fragment)object, n2, 0, 0, false);
                continue;
            }
            if (object.getAnimator() == null) continue;
            object.getAnimator().end();
        }
    }

    private void ensureExecReady(boolean bl) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!bl) {
            this.checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try {
            this.executePostponedTransaction(null, null);
            return;
        }
        finally {
            this.mExecutingActions = false;
        }
    }

    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        while (n < n2) {
            BackStackRecord backStackRecord = arrayList.get(n);
            boolean bl = arrayList2.get(n);
            boolean bl2 = true;
            if (bl) {
                backStackRecord.bumpBackStackNesting(-1);
                if (n != n2 - 1) {
                    bl2 = false;
                }
                backStackRecord.executePopOps(bl2);
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            ++n;
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        int n3;
        int n4 = n;
        boolean bl = arrayList.get((int)n4).mReorderingAllowed;
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            this.mTmpAddedFragments.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        Object object = this.getPrimaryNavigationFragment();
        boolean bl2 = false;
        for (n3 = n4; n3 < n2; ++n3) {
            BackStackRecord backStackRecord = arrayList.get(n3);
            object = arrayList2.get(n3) == false ? backStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)object) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)object);
            if (!bl2 && !backStackRecord.mAddToBackStack) {
                bl2 = false;
                continue;
            }
            bl2 = true;
        }
        this.mTmpAddedFragments.clear();
        if (!bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n4, n2, false);
        }
        FragmentManagerImpl.executeOps(arrayList, arrayList2, n, n2);
        if (bl) {
            object = new ArraySet();
            this.addAddedFragments((ArraySet<Fragment>)object);
            n = this.postponePostponableTransactions(arrayList, arrayList2, n4, n2, (ArraySet<Fragment>)object);
            this.makeRemovedFragmentsInvisible((ArraySet<Fragment>)object);
        } else {
            n = n2;
        }
        n3 = n4;
        if (n != n4) {
            n3 = n4;
            if (bl) {
                FragmentTransition.startTransitions(this, arrayList, arrayList2, n4, n, true);
                this.moveToState(this.mCurState, true);
                n3 = n4;
            }
        }
        while (n3 < n2) {
            object = arrayList.get(n3);
            if (arrayList2.get(n3).booleanValue() && object.mIndex >= 0) {
                this.freeBackStackIndex(object.mIndex);
                object.mIndex = -1;
            }
            object.runOnCommitRunnables();
            ++n3;
        }
        if (bl2) {
            this.reportBackStackChanged();
        }
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int n = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int n2 = 0;
        int n3 = n;
        n = n2;
        while (n < n3) {
            int n4;
            block7 : {
                StartEnterTransitionListener startEnterTransitionListener;
                block8 : {
                    block6 : {
                        startEnterTransitionListener = this.mPostponedTransactions.get(n);
                        if (arrayList == null || startEnterTransitionListener.mIsBack || (n2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) == -1 || !arrayList2.get(n2).booleanValue()) break block6;
                        startEnterTransitionListener.cancelTransaction();
                        n4 = n;
                        n2 = n3;
                        break block7;
                    }
                    if (startEnterTransitionListener.isReady()) break block8;
                    n4 = n;
                    n2 = n3;
                    if (arrayList == null) break block7;
                    n4 = n;
                    n2 = n3;
                    if (!startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size())) break block7;
                }
                this.mPostponedTransactions.remove(n);
                n4 = n - 1;
                n2 = n3 - 1;
                if (arrayList != null && !startEnterTransitionListener.mIsBack && (n = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(n).booleanValue()) {
                    startEnterTransitionListener.cancelTransaction();
                } else {
                    startEnterTransitionListener.completeTransaction();
                }
            }
            n = n4 + 1;
            n3 = n2;
        }
    }

    private Fragment findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup != null) {
            if (view == null) {
                return null;
            }
            for (int i = this.mAdded.indexOf((Object)fragment) - 1; i >= 0; --i) {
                fragment = this.mAdded.get(i);
                if (fragment.mContainer != viewGroup || fragment.mView == null) continue;
                return fragment;
            }
            return null;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        synchronized (this) {
            ArrayList<OpGenerator> arrayList3 = this.mPendingActions;
            int n = 0;
            if (arrayList3 != null && this.mPendingActions.size() != 0) {
                int n2 = this.mPendingActions.size();
                boolean bl = false;
                do {
                    if (n >= n2) {
                        this.mPendingActions.clear();
                        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                        return bl;
                    }
                    bl |= this.mPendingActions.get(n).generateOps(arrayList, arrayList2);
                    ++n;
                } while (true);
            }
            return false;
        }
    }

    private static Animation.AnimationListener getAnimationListener(Animation animation) {
        try {
            if (sAnimationListenerField == null) {
                sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                sAnimationListenerField.setAccessible(true);
            }
            animation = (Animation.AnimationListener)sAnimationListenerField.get((Object)animation);
            return animation;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Cannot access Animation's mListener field", (Throwable)illegalAccessException);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"No field with the name mListener is found in Animation class", (Throwable)noSuchFieldException);
        }
        return null;
    }

    static AnimationOrAnimator makeFadeAnimation(Context context, float f, float f2) {
        context = new AlphaAnimation(f, f2);
        context.setInterpolator(DECELERATE_CUBIC);
        context.setDuration(220L);
        return new AnimationOrAnimator((Animation)context);
    }

    static AnimationOrAnimator makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        context = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        scaleAnimation = new AlphaAnimation(f3, f4);
        scaleAnimation.setInterpolator(DECELERATE_CUBIC);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        return new AnimationOrAnimator((Animation)context);
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int n = arraySet.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = arraySet.valueAt(i);
            if (fragment.mAdded) continue;
            View view = fragment.getView();
            fragment.mPostponedAlpha = view.getAlpha();
            view.setAlpha(0.0f);
        }
    }

    static boolean modifiesAlpha(Animator object) {
        block4 : {
            block3 : {
                if (object == null) {
                    return false;
                }
                if (!(object instanceof ValueAnimator)) break block3;
                object = ((ValueAnimator)object).getValues();
                for (int i = 0; i < ((PropertyValuesHolder[])object).length; ++i) {
                    if (!"alpha".equals(object[i].getPropertyName())) continue;
                    return true;
                }
                break block4;
            }
            if (!(object instanceof AnimatorSet)) break block4;
            object = ((AnimatorSet)object).getChildAnimations();
            for (int i = 0; i < object.size(); ++i) {
                if (!FragmentManagerImpl.modifiesAlpha((Animator)object.get(i))) continue;
                return true;
            }
        }
        return false;
    }

    static boolean modifiesAlpha(AnimationOrAnimator object) {
        if (object.animation instanceof AlphaAnimation) {
            return true;
        }
        if (object.animation instanceof AnimationSet) {
            object = ((AnimationSet)object.animation).getAnimations();
            for (int i = 0; i < object.size(); ++i) {
                if (!(object.get(i) instanceof AlphaAnimation)) continue;
                return true;
            }
            return false;
        }
        return FragmentManagerImpl.modifiesAlpha(object.animator);
    }

    private boolean popBackStackImmediate(String string, int n, int n2) {
        boolean bl;
        FragmentManager fragmentManager;
        this.execPendingActions();
        this.ensureExecReady(true);
        if (this.mPrimaryNav != null && n < 0 && string == null && (fragmentManager = this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManager.popBackStackImmediate()) {
            return true;
        }
        bl = this.popBackStackState(this.mTmpRecords, this.mTmpIsPop, string, n, n2);
        if (bl) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, ArraySet<Fragment> arraySet) {
        int n3 = n2;
        for (int i = n2 - 1; i >= n; --i) {
            BackStackRecord backStackRecord = arrayList.get(i);
            boolean bl = arrayList2.get(i);
            boolean bl2 = backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i + 1, n2);
            int n4 = n3;
            if (bl2) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bl);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (bl) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                n4 = n3 - 1;
                if (i != n4) {
                    arrayList.remove(i);
                    arrayList.add(n4, backStackRecord);
                }
                this.addAddedFragments(arraySet);
            }
            n3 = n4;
        }
        return n3;
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList != null) {
            if (arrayList.isEmpty()) {
                return;
            }
            if (arrayList2 != null && arrayList.size() == arrayList2.size()) {
                this.executePostponedTransaction(arrayList, arrayList2);
                int n = arrayList.size();
                int n2 = 0;
                int n3 = 0;
                while (n2 < n) {
                    int n4 = n2;
                    int n5 = n3;
                    if (!arrayList.get((int)n2).mReorderingAllowed) {
                        if (n3 != n2) {
                            this.executeOpsTogether(arrayList, arrayList2, n3, n2);
                        }
                        n5 = n3 = n2 + 1;
                        if (arrayList2.get(n2).booleanValue()) {
                            do {
                                n5 = n3;
                                if (n3 >= n) break;
                                n5 = n3;
                                if (!arrayList2.get(n3).booleanValue()) break;
                                n5 = n3++;
                            } while (!arrayList.get((int)n3).mReorderingAllowed);
                        }
                        this.executeOpsTogether(arrayList, arrayList2, n2, n5);
                        n4 = n5 - 1;
                    }
                    n2 = n4 + 1;
                    n3 = n5;
                }
                if (n3 != n) {
                    this.executeOpsTogether(arrayList, arrayList2, n3, n);
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    public static int reverseTransit(int n) {
        int n2 = 8194;
        if (n != 4097) {
            if (n != 4099) {
                if (n != 8194) {
                    return 0;
                }
                return 4097;
            }
            n2 = 4099;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void scheduleCommit() {
        synchronized (this) {
            ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
            boolean bl = false;
            boolean bl2 = arrayList != null && !this.mPostponedTransactions.isEmpty();
            boolean bl3 = bl;
            if (this.mPendingActions != null) {
                bl3 = bl;
                if (this.mPendingActions.size() == 1) {
                    bl3 = true;
                }
            }
            if (bl2 || bl3) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
            return;
        }
    }

    private static void setHWLayerAnimListenerIfAlpha(View view, AnimationOrAnimator animationOrAnimator) {
        if (view != null) {
            if (animationOrAnimator == null) {
                return;
            }
            if (FragmentManagerImpl.shouldRunOnHWLayer(view, animationOrAnimator)) {
                if (animationOrAnimator.animator != null) {
                    animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorOnHWLayerIfNeededListener(view));
                    return;
                }
                Animation.AnimationListener animationListener = FragmentManagerImpl.getAnimationListener(animationOrAnimator.animation);
                view.setLayerType(2, null);
                animationOrAnimator.animation.setAnimationListener((Animation.AnimationListener)new AnimateOnHWLayerIfNeededListener(view, animationListener));
            }
            return;
        }
    }

    private static void setRetaining(FragmentManagerNonConfig iterator) {
        if (iterator == null) {
            return;
        }
        List<Fragment> list = iterator.getFragments();
        if (list != null) {
            list = list.iterator();
            while (list.hasNext()) {
                ((Fragment)list.next()).mRetaining = true;
            }
        }
        if ((iterator = iterator.getChildNonConfigs()) != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                FragmentManagerImpl.setRetaining((FragmentManagerNonConfig)iterator.next());
            }
        }
    }

    static boolean shouldRunOnHWLayer(View view, AnimationOrAnimator animationOrAnimator) {
        boolean bl = false;
        if (view != null) {
            if (animationOrAnimator == null) {
                return false;
            }
            boolean bl2 = bl;
            if (Build.VERSION.SDK_INT >= 19) {
                bl2 = bl;
                if (view.getLayerType() == 0) {
                    bl2 = bl;
                    if (ViewCompat.hasOverlappingRendering(view)) {
                        bl2 = bl;
                        if (FragmentManagerImpl.modifiesAlpha(animationOrAnimator)) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e((String)TAG, (String)runtimeException.getMessage());
        Log.e((String)TAG, (String)"Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
            }
        } else {
            try {
                this.dump("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
            }
        }
        throw runtimeException;
    }

    public static int transitToStyleIndex(int n, boolean bl) {
        if (n != 4097) {
            if (n != 4099) {
                if (n != 8194) {
                    return -1;
                }
                if (bl) {
                    return 3;
                }
                return 4;
            }
            if (bl) {
                return 5;
            }
            return 6;
        }
        if (bl) {
            return 1;
        }
        return 2;
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(backStackRecord);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addFragment(Fragment fragment, boolean bl) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            serializable.append("add: ");
            serializable.append(fragment);
            Log.v((String)TAG, (String)serializable.toString());
        }
        this.makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                serializable = new StringBuilder();
                serializable.append("Fragment already added: ");
                serializable.append(fragment);
                throw new IllegalStateException(serializable.toString());
            }
            serializable = this.mAdded;
            synchronized (serializable) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (bl) {
                this.moveToState(fragment);
                return;
            }
        }
    }

    @Override
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int n = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1);
                if (DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Adding back stack index ");
                    stringBuilder.append(n);
                    stringBuilder.append(" with ");
                    stringBuilder.append(backStackRecord);
                    Log.v((String)TAG, (String)stringBuilder.toString());
                }
                this.mBackStackIndices.set(n, backStackRecord);
                return n;
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            int n = this.mBackStackIndices.size();
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Setting back stack index ");
                stringBuilder.append(n);
                stringBuilder.append(" to ");
                stringBuilder.append(backStackRecord);
                Log.v((String)TAG, (String)stringBuilder.toString());
            }
            this.mBackStackIndices.add(backStackRecord);
            return n;
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            serializable.append("attach: ");
            serializable.append(fragment);
            Log.v((String)TAG, (String)serializable.toString());
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded.contains(fragment)) {
                    serializable = new StringBuilder();
                    serializable.append("Fragment already added: ");
                    serializable.append(fragment);
                    throw new IllegalStateException(serializable.toString());
                }
                if (DEBUG) {
                    serializable = new StringBuilder();
                    serializable.append("add from attach: ");
                    serializable.append(fragment);
                    Log.v((String)TAG, (String)serializable.toString());
                }
                serializable = this.mAdded;
                synchronized (serializable) {
                    this.mAdded.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                    return;
                }
            }
        }
    }

    @Override
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    void completeShowHideFragment(final Fragment fragment) {
        if (fragment.mView != null) {
            AnimationOrAnimator animationOrAnimator = this.loadAnimation(fragment, fragment.getNextTransition(), fragment.mHidden ^ true, fragment.getNextTransitionStyle());
            if (animationOrAnimator != null && animationOrAnimator.animator != null) {
                animationOrAnimator.animator.setTarget((Object)fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final ViewGroup viewGroup = fragment.mContainer;
                        final View view = fragment.mView;
                        viewGroup.startViewTransition(view);
                        animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                            public void onAnimationEnd(Animator animator) {
                                viewGroup.endViewTransition(view);
                                animator.removeListener((Animator.AnimatorListener)this);
                                if (fragment.mView != null) {
                                    fragment.mView.setVisibility(8);
                                }
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
                animationOrAnimator.animator.start();
            } else {
                if (animationOrAnimator != null) {
                    FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
                    fragment.mView.startAnimation(animationOrAnimator.animation);
                    animationOrAnimator.animation.start();
                }
                int n = fragment.mHidden && !fragment.isHideReplaced() ? 8 : 0;
                fragment.mView.setVisibility(n);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void detachFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            serializable.append("detach: ");
            serializable.append(fragment);
            Log.v((String)TAG, (String)serializable.toString());
        }
        if (fragment.mDetached) return;
        fragment.mDetached = true;
        if (!fragment.mAdded) return;
        if (DEBUG) {
            serializable = new StringBuilder();
            serializable.append("remove from detach: ");
            serializable.append(fragment);
            Log.v((String)TAG, (String)serializable.toString());
        }
        serializable = this.mAdded;
        // MONITORENTER : serializable
        this.mAdded.remove(fragment);
        // MONITOREXIT : serializable
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(2);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performConfigurationChanged(configuration);
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performContextItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(1);
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        this.execPendingActions();
        this.dispatchStateChange(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        this.dispatchStateChange(1);
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performLowMemory();
        }
    }

    public void dispatchMultiWindowModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performMultiWindowModeChanged(bl);
        }
    }

    void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentActivityCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentActivityCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentAttached(fragment, context, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentDestroyed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentDestroyed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDestroyed(this, fragment);
        }
    }

    void dispatchOnFragmentDetached(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentDetached(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDetached(this, fragment);
        }
    }

    void dispatchOnFragmentPaused(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPaused(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPaused(this, fragment);
        }
    }

    void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPreAttached(fragment, context, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentPreCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPreCreated(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentResumed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentResumed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentResumed(this, fragment);
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentSaveInstanceState(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentStarted(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStarted(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStarted(this, fragment);
        }
    }

    void dispatchOnFragmentStopped(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStopped(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStopped(this, fragment);
        }
    }

    void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentViewCreated(fragment, view, bundle, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewCreated(this, fragment, view, bundle);
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean bl) {
        Object object;
        if (this.mParent != null && (object = this.mParent.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentViewDestroyed(fragment, true);
        }
        for (Pair pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewDestroyed(this, fragment);
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performOptionsItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performOptionsMenuClosed(menu);
        }
    }

    public void dispatchPause() {
        this.dispatchStateChange(4);
    }

    public void dispatchPictureInPictureModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performPictureInPictureModeChanged(bl);
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        int n = this.mCurState;
        if (n < 1) {
            return false;
        }
        boolean bl = false;
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            boolean bl2 = bl;
            if (fragment != null) {
                bl2 = bl;
                if (fragment.performPrepareOptionsMenu(menu)) {
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    public void dispatchReallyStop() {
        this.dispatchStateChange(2);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(5);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(4);
    }

    public void dispatchStop() {
        this.mStopped = true;
        this.dispatchStateChange(3);
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            this.startPendingDeferredFragments();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void dump(String string, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        int n2;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        Object object2 = this.mActive;
        int n3 = 0;
        if (object2 != null && (n = this.mActive.size()) > 0) {
            printWriter.print(string);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (n2 = 0; n2 < n; ++n2) {
                object2 = (Fragment)this.mActive.valueAt(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object2);
                if (object2 == null) continue;
                object2.dump((String)charSequence, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        if ((n = this.mAdded.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Added Fragments:");
            for (n2 = 0; n2 < n; ++n2) {
                object2 = this.mAdded.get(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object2.toString());
            }
        }
        if (this.mCreatedMenus != null && (n = this.mCreatedMenus.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Fragments Created Menus:");
            for (n2 = 0; n2 < n; ++n2) {
                object2 = this.mCreatedMenus.get(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object2.toString());
            }
        }
        if (this.mBackStack != null && (n = this.mBackStack.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Back Stack:");
            for (n2 = 0; n2 < n; ++n2) {
                object2 = this.mBackStack.get(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object2.toString());
                object2.dump((String)charSequence, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        // MONITORENTER : this
        if (this.mBackStackIndices != null && (n = this.mBackStackIndices.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Back Stack Indices:");
            for (n2 = 0; n2 < n; ++n2) {
                object = this.mBackStackIndices.get(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
            printWriter.print(string);
            printWriter.print("mAvailBackStackIndices: ");
            printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
        }
        // MONITOREXIT : this
        if (this.mPendingActions != null && (n = this.mPendingActions.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Pending Actions:");
            for (n2 = n3; n2 < n; ++n2) {
                object = this.mPendingActions.get(n2);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        printWriter.print(string);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(string);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(string);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(string);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(string);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(string);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause == null) return;
        printWriter.print(string);
        printWriter.print("  mNoTransactionsBecause=");
        printWriter.println(this.mNoTransactionsBecause);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enqueueAction(OpGenerator opGenerator, boolean bl) {
        if (!bl) {
            this.checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    this.mPendingActions = new ArrayList();
                }
                this.mPendingActions.add(opGenerator);
                this.scheduleCommit();
                return;
            }
            if (bl) {
                return;
            }
            throw new IllegalStateException("Activity has been destroyed");
        }
    }

    void ensureInflatedFragmentView(Fragment fragment) {
        if (fragment.mFromLayout && !fragment.mPerformedCreateView) {
            fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            if (fragment.mView != null) {
                fragment.mInnerView = fragment.mView;
                fragment.mView.setSaveFromParentEnabled(false);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                this.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
                return;
            }
            fragment.mInnerView = null;
        }
    }

    public boolean execPendingActions() {
        this.ensureExecReady(true);
        boolean bl = false;
        while (this.generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                bl = true;
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
    }

    public void execSingleAction(OpGenerator opGenerator, boolean bl) {
        if (bl && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        this.ensureExecReady(bl);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
    }

    @Override
    public boolean executePendingTransactions() {
        boolean bl = this.execPendingActions();
        this.forcePostponedTransactions();
        return bl;
    }

    @Override
    public Fragment findFragmentById(int n) {
        int n2;
        Fragment fragment;
        for (n2 = this.mAdded.size() - 1; n2 >= 0; --n2) {
            fragment = this.mAdded.get(n2);
            if (fragment == null || fragment.mFragmentId != n) continue;
            return fragment;
        }
        if (this.mActive != null) {
            for (n2 = this.mActive.size() - 1; n2 >= 0; --n2) {
                fragment = (Fragment)this.mActive.valueAt(n2);
                if (fragment == null || fragment.mFragmentId != n) continue;
                return fragment;
            }
        }
        return null;
    }

    @Override
    public Fragment findFragmentByTag(String string) {
        int n;
        Fragment fragment;
        if (string != null) {
            for (n = this.mAdded.size() - 1; n >= 0; --n) {
                fragment = this.mAdded.get(n);
                if (fragment == null || !string.equals(fragment.mTag)) continue;
                return fragment;
            }
        }
        if (this.mActive != null && string != null) {
            for (n = this.mActive.size() - 1; n >= 0; --n) {
                fragment = (Fragment)this.mActive.valueAt(n);
                if (fragment == null || !string.equals(fragment.mTag)) continue;
                return fragment;
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String string) {
        if (this.mActive != null && string != null) {
            for (int i = this.mActive.size() - 1; i >= 0; --i) {
                Fragment fragment = (Fragment)this.mActive.valueAt(i);
                if (fragment == null || (fragment = fragment.findFragmentByWho(string)) == null) continue;
                return fragment;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void freeBackStackIndex(int n) {
        synchronized (this) {
            this.mBackStackIndices.set(n, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Freeing back stack index ");
                stringBuilder.append(n);
                Log.v((String)TAG, (String)stringBuilder.toString());
            }
            this.mAvailBackStackIndices.add(n);
            return;
        }
    }

    int getActiveFragmentCount() {
        if (this.mActive == null) {
            return 0;
        }
        return this.mActive.size();
    }

    List<Fragment> getActiveFragments() {
        if (this.mActive == null) {
            return null;
        }
        int n = this.mActive.size();
        ArrayList<Fragment> arrayList = new ArrayList<Fragment>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add((Fragment)this.mActive.valueAt(i));
        }
        return arrayList;
    }

    @Override
    public FragmentManager.BackStackEntry getBackStackEntryAt(int n) {
        return this.mBackStack.get(n);
    }

    @Override
    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    @Override
    public Fragment getFragment(Bundle object, String string) {
        int n = object.getInt(string, -1);
        if (n == -1) {
            return null;
        }
        object = (Fragment)this.mActive.get(n);
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment no longer exists for key ");
            stringBuilder.append(string);
            stringBuilder.append(": index ");
            stringBuilder.append(n);
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Fragment> getFragments() {
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            return (List)this.mAdded.clone();
        }
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    @Override
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    public void hideFragment(Fragment fragment) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hide: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isStateAtLeast(int n) {
        if (this.mCurState >= n) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isStateSaved() {
        if (!this.mStateSaved && !this.mStopped) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    AnimationOrAnimator loadAnimation(Fragment object, int n, boolean bl, int n2) {
        block23 : {
            int n3 = object.getNextAnim();
            Animation animation = object.onCreateAnimation(n, bl, n3);
            if (animation != null) {
                return new AnimationOrAnimator(animation);
            }
            if ((object = object.onCreateAnimator(n, bl, n3)) != null) {
                return new AnimationOrAnimator((Animator)object);
            }
            if (n3 != 0) {
                boolean bl2;
                boolean bl3 = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(n3));
                boolean bl4 = bl2 = false;
                if (bl3) {
                    try {
                        object = AnimationUtils.loadAnimation((Context)this.mHost.getContext(), (int)n3);
                        if (object != null) {
                            return new AnimationOrAnimator((Animation)object);
                        }
                        bl4 = true;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        throw notFoundException;
                    }
                    catch (RuntimeException runtimeException) {
                        bl4 = bl2;
                    }
                }
                if (!bl4) {
                    try {
                        object = AnimatorInflater.loadAnimator((Context)this.mHost.getContext(), (int)n3);
                        if (object != null) {
                            return new AnimationOrAnimator((Animator)object);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        if (bl3) {
                            throw runtimeException;
                        }
                        Animation animation2 = AnimationUtils.loadAnimation((Context)this.mHost.getContext(), (int)n3);
                        if (animation2 == null) break block23;
                        return new AnimationOrAnimator(animation2);
                    }
                }
            }
        }
        if (n == 0) {
            return null;
        }
        if ((n = FragmentManagerImpl.transitToStyleIndex(n, bl)) < 0) {
            return null;
        }
        switch (n) {
            default: {
                n = n2;
                if (n2 != 0) break;
                n = n2;
                if (!this.mHost.onHasWindowAnimations()) break;
                n = this.mHost.onGetWindowAnimations();
                break;
            }
            case 6: {
                return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            }
            case 5: {
                return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            }
            case 4: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            }
            case 3: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            }
            case 2: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            }
            case 1: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            }
        }
        if (n != 0) return null;
        return null;
    }

    void makeActive(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            return;
        }
        int n = this.mNextFragmentIndex;
        this.mNextFragmentIndex = n + 1;
        fragment.setIndex(n, this.mParent);
        if (this.mActive == null) {
            this.mActive = new SparseArray();
        }
        this.mActive.put(fragment.mIndex, (Object)fragment);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Allocated fragment index ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
    }

    void makeInactive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Freeing fragment index ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        this.mActive.put(fragment.mIndex, null);
        fragment.initState();
    }

    void moveFragmentToExpectedState(Fragment fragment) {
        int n;
        if (fragment == null) {
            return;
        }
        int n2 = n = this.mCurState;
        if (fragment.mRemoving) {
            n2 = fragment.isInBackStack() ? Math.min(n, 1) : Math.min(n, 0);
        }
        this.moveToState(fragment, n2, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
        if (fragment.mView != null) {
            Object object = this.findFragmentUnder(fragment);
            if (object != null) {
                object = object.mView;
                ViewGroup viewGroup = fragment.mContainer;
                n2 = viewGroup.indexOfChild((View)object);
                n = viewGroup.indexOfChild(fragment.mView);
                if (n < n2) {
                    viewGroup.removeViewAt(n);
                    viewGroup.addView(fragment.mView, n2);
                }
            }
            if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha);
                }
                fragment.mPostponedAlpha = 0.0f;
                fragment.mIsNewlyAdded = false;
                object = this.loadAnimation(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                if (object != null) {
                    FragmentManagerImpl.setHWLayerAnimListenerIfAlpha(fragment.mView, (AnimationOrAnimator)object);
                    if (object.animation != null) {
                        fragment.mView.startAnimation(object.animation);
                    } else {
                        object.animator.setTarget((Object)fragment.mView);
                        object.animator.start();
                    }
                }
            }
        }
        if (fragment.mHiddenChanged) {
            this.completeShowHideFragment(fragment);
        }
    }

    void moveToState(int n, boolean bl) {
        if (this.mHost == null && n != 0) {
            throw new IllegalStateException("No activity");
        }
        if (!bl && n == this.mCurState) {
            return;
        }
        this.mCurState = n;
        if (this.mActive != null) {
            int n2 = this.mAdded.size();
            for (n = 0; n < n2; ++n) {
                this.moveFragmentToExpectedState(this.mAdded.get(n));
            }
            n2 = this.mActive.size();
            for (n = 0; n < n2; ++n) {
                Fragment fragment = (Fragment)this.mActive.valueAt(n);
                if (fragment == null || !fragment.mRemoving && !fragment.mDetached || fragment.mIsNewlyAdded) continue;
                this.moveFragmentToExpectedState(fragment);
            }
            this.startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                this.mHost.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    void moveToState(Fragment fragment) {
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /*
     * Exception decompiling
     */
    void moveToState(Fragment var1_1, int var2_2, int var3_3, int var4_4, boolean var5_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:148)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:386)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        this.mStopped = false;
        int n = this.mAdded.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.noteStateNotSaved();
        }
    }

    public View onCreateView(View object, String object2, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(object2)) {
            return null;
        }
        object2 = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
        int n = 0;
        String string = object2;
        if (object2 == null) {
            string = typedArray.getString(0);
        }
        int n2 = typedArray.getResourceId(1, -1);
        String string2 = typedArray.getString(2);
        typedArray.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), string)) {
            return null;
        }
        if (object != null) {
            n = object.getId();
        }
        if (n == -1 && n2 == -1 && string2 == null) {
            object = new StringBuilder();
            object.append(attributeSet.getPositionDescription());
            object.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
            object.append(string);
            throw new IllegalArgumentException(object.toString());
        }
        object2 = n2 != -1 ? this.findFragmentById(n2) : null;
        object = object2;
        if (object2 == null) {
            object = object2;
            if (string2 != null) {
                object = this.findFragmentByTag(string2);
            }
        }
        object2 = object;
        if (object == null) {
            object2 = object;
            if (n != -1) {
                object2 = this.findFragmentById(n);
            }
        }
        if (DEBUG) {
            object = new StringBuilder();
            object.append("onCreateView: id=0x");
            object.append(Integer.toHexString(n2));
            object.append(" fname=");
            object.append(string);
            object.append(" existing=");
            object.append(object2);
            Log.v((String)TAG, (String)object.toString());
        }
        if (object2 == null) {
            object = this.mContainer.instantiate(context, string, null);
            object.mFromLayout = true;
            int n3 = n2 != 0 ? n2 : n;
            object.mFragmentId = n3;
            object.mContainerId = n;
            object.mTag = string2;
            object.mInLayout = true;
            object.mFragmentManager = this;
            object.mHost = this.mHost;
            object.onInflate(this.mHost.getContext(), attributeSet, object.mSavedFragmentState);
            this.addFragment((Fragment)object, true);
        } else {
            if (object2.mInLayout) {
                object = new StringBuilder();
                object.append(attributeSet.getPositionDescription());
                object.append(": Duplicate id 0x");
                object.append(Integer.toHexString(n2));
                object.append(", tag ");
                object.append(string2);
                object.append(", or parent id 0x");
                object.append(Integer.toHexString(n));
                object.append(" with another fragment for ");
                object.append(string);
                throw new IllegalArgumentException(object.toString());
            }
            object2.mInLayout = true;
            object2.mHost = this.mHost;
            if (!object2.mRetaining) {
                object2.onInflate(this.mHost.getContext(), attributeSet, object2.mSavedFragmentState);
            }
            object = object2;
        }
        if (this.mCurState < 1 && object.mFromLayout) {
            this.moveToState((Fragment)object, 1, 0, 0, false);
        } else {
            this.moveToState((Fragment)object);
        }
        if (object.mView == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(string);
            object.append(" did not create a view.");
            throw new IllegalStateException(object.toString());
        }
        if (n2 != 0) {
            object.mView.setId(n2);
        }
        if (object.mView.getTag() == null) {
            object.mView.setTag((Object)string2);
        }
        return object.mView;
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string, context, attributeSet);
    }

    public void performPendingDeferredStart(Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            fragment.mDeferStart = false;
            this.moveToState(fragment, this.mCurState, 0, 0, false);
        }
    }

    @Override
    public void popBackStack() {
        this.enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    @Override
    public void popBackStack(int n, int n2) {
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad id: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.enqueueAction(new PopBackStackState(null, n, n2), false);
    }

    @Override
    public void popBackStack(String string, int n) {
        this.enqueueAction(new PopBackStackState(string, -1, n), false);
    }

    @Override
    public boolean popBackStackImmediate() {
        this.checkStateLoss();
        return this.popBackStackImmediate(null, -1, 0);
    }

    @Override
    public boolean popBackStackImmediate(int n, int n2) {
        this.checkStateLoss();
        this.execPendingActions();
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad id: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return this.popBackStackImmediate(null, n, n2);
    }

    @Override
    public boolean popBackStackImmediate(String string, int n) {
        this.checkStateLoss();
        return this.popBackStackImmediate(string, -1, n);
    }

    boolean popBackStackState(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String string, int n, int n2) {
        int n3;
        if (this.mBackStack == null) {
            return false;
        }
        if (string == null && n < 0 && (n2 & 1) == 0) {
            n = this.mBackStack.size() - 1;
            if (n < 0) {
                return false;
            }
            arrayList.add(this.mBackStack.remove(n));
            arrayList2.add(true);
            return true;
        }
        if (string == null && n < 0) {
            n3 = -1;
        } else {
            int n4;
            BackStackRecord backStackRecord;
            for (n4 = this.mBackStack.size() - 1; n4 >= 0; --n4) {
                backStackRecord = this.mBackStack.get(n4);
                if (string != null && string.equals(backStackRecord.getName()) || n >= 0 && n == backStackRecord.mIndex) break;
            }
            if (n4 < 0) {
                return false;
            }
            n3 = n4;
            if ((n2 & 1) != 0) {
                n2 = n4 - 1;
                do {
                    n3 = n2;
                    if (n2 < 0) break;
                    backStackRecord = this.mBackStack.get(n2);
                    if (string == null || !string.equals(backStackRecord.getName())) {
                        n3 = n2;
                        if (n < 0) break;
                        n3 = n2;
                        if (n != backStackRecord.mIndex) break;
                    }
                    --n2;
                } while (true);
            }
        }
        if (n3 == this.mBackStack.size() - 1) {
            return false;
        }
        for (n = this.mBackStack.size() - 1; n > n3; --n) {
            arrayList.add(this.mBackStack.remove(n));
            arrayList2.add(true);
        }
        return true;
    }

    @Override
    public void putFragment(Bundle bundle, String string, Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        bundle.putInt(string, fragment.mIndex);
    }

    @Override
    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
        this.mLifecycleCallbacks.add(new Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>(fragmentLifecycleCallbacks, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            serializable.append("remove: ");
            serializable.append(fragment);
            serializable.append(" nesting=");
            serializable.append(fragment.mBackStackNesting);
            Log.v((String)TAG, (String)serializable.toString());
        }
        boolean bl = fragment.isInBackStack();
        if (fragment.mDetached) {
            if (!(bl ^ true)) return;
        }
        serializable = this.mAdded;
        // MONITORENTER : serializable
        this.mAdded.remove(fragment);
        // MONITOREXIT : serializable
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
        fragment.mRemoving = true;
    }

    @Override
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); ++i) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void restoreAllState(Parcelable object, FragmentManagerNonConfig object2) {
        Object object3;
        int n;
        Object object4;
        Object object5;
        int n2;
        Object object6;
        if (object == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState)object;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        if (object2 == null) {
            object = object5 = null;
        } else {
            object6 = object2.getFragments();
            object3 = object2.getChildNonConfigs();
            object4 = object2.getViewModelStores();
            n2 = object6 != null ? object6.size() : 0;
            n = 0;
            do {
                int n3;
                object5 = object3;
                object = object4;
                if (n >= n2) break;
                object = object6.get(n);
                if (DEBUG) {
                    object5 = new StringBuilder();
                    object5.append("restoreAllState: re-attaching retained ");
                    object5.append(object);
                    Log.v((String)TAG, (String)object5.toString());
                }
                for (n3 = 0; n3 < fragmentManagerState.mActive.length && fragmentManagerState.mActive[n3].mIndex != object.mIndex; ++n3) {
                }
                if (n3 == fragmentManagerState.mActive.length) {
                    object5 = new StringBuilder();
                    object5.append("Could not find active fragment with index ");
                    object5.append(object.mIndex);
                    this.throwException(new IllegalStateException(object5.toString()));
                }
                object5 = fragmentManagerState.mActive[n3];
                object5.mInstance = object;
                object.mSavedViewState = null;
                object.mBackStackNesting = 0;
                object.mInLayout = false;
                object.mAdded = false;
                object.mTarget = null;
                if (object5.mSavedFragmentState != null) {
                    object5.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                    object.mSavedViewState = object5.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    object.mSavedFragmentState = object5.mSavedFragmentState;
                }
                ++n;
            } while (true);
        }
        this.mActive = new SparseArray(fragmentManagerState.mActive.length);
        for (n2 = 0; n2 < fragmentManagerState.mActive.length; ++n2) {
            object6 = fragmentManagerState.mActive[n2];
            if (object6 == null) continue;
            object3 = object5 != null && n2 < object5.size() ? (FragmentManagerNonConfig)object5.get(n2) : null;
            object4 = object != null && n2 < object.size() ? (ViewModelStore)object.get(n2) : null;
            object3 = object6.instantiate(this.mHost, this.mContainer, this.mParent, (FragmentManagerNonConfig)object3, (ViewModelStore)object4);
            if (DEBUG) {
                object4 = new StringBuilder();
                object4.append("restoreAllState: active #");
                object4.append(n2);
                object4.append(": ");
                object4.append(object3);
                Log.v((String)TAG, (String)object4.toString());
            }
            this.mActive.put(object3.mIndex, object3);
            object6.mInstance = null;
        }
        if (object2 != null) {
            object = object2.getFragments();
            n2 = object != null ? object.size() : 0;
            for (n = 0; n < n2; ++n) {
                object2 = (Fragment)object.get(n);
                if (object2.mTargetIndex < 0) continue;
                object2.mTarget = (Fragment)this.mActive.get(object2.mTargetIndex);
                if (object2.mTarget != null) continue;
                object5 = new StringBuilder();
                object5.append("Re-attaching retained fragment ");
                object5.append(object2);
                object5.append(" target no longer exists: ");
                object5.append(object2.mTargetIndex);
                Log.w((String)TAG, (String)object5.toString());
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            for (n2 = 0; n2 < fragmentManagerState.mAdded.length; ++n2) {
                object = (Fragment)this.mActive.get(fragmentManagerState.mAdded[n2]);
                if (object == null) {
                    object2 = new StringBuilder();
                    object2.append("No instantiated fragment for index #");
                    object2.append(fragmentManagerState.mAdded[n2]);
                    this.throwException(new IllegalStateException(object2.toString()));
                }
                object.mAdded = true;
                if (DEBUG) {
                    object2 = new StringBuilder();
                    object2.append("restoreAllState: added #");
                    object2.append(n2);
                    object2.append(": ");
                    object2.append(object);
                    Log.v((String)TAG, (String)object2.toString());
                }
                if (this.mAdded.contains(object)) {
                    throw new IllegalStateException("Already added!");
                }
                object2 = this.mAdded;
                synchronized (object2) {
                    this.mAdded.add((Fragment)object);
                    continue;
                }
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            for (n2 = 0; n2 < fragmentManagerState.mBackStack.length; ++n2) {
                object = fragmentManagerState.mBackStack[n2].instantiate(this);
                if (DEBUG) {
                    object2 = new StringBuilder();
                    object2.append("restoreAllState: back stack #");
                    object2.append(n2);
                    object2.append(" (index ");
                    object2.append(object.mIndex);
                    object2.append("): ");
                    object2.append(object);
                    Log.v((String)TAG, (String)object2.toString());
                    object2 = new PrintWriter(new LogWriter(TAG));
                    object.dump("  ", (PrintWriter)object2, false);
                    object2.close();
                }
                this.mBackStack.add((BackStackRecord)object);
                if (object.mIndex < 0) continue;
                this.setBackStackIndex(object.mIndex, (BackStackRecord)object);
            }
        } else {
            this.mBackStack = null;
        }
        if (fragmentManagerState.mPrimaryNavActiveIndex >= 0) {
            this.mPrimaryNav = (Fragment)this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    FragmentManagerNonConfig retainNonConfig() {
        FragmentManagerImpl.setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    Parcelable saveAllState() {
        this.forcePostponedTransactions();
        this.endAnimatingAwayFragments();
        this.execPendingActions();
        this.mStateSaved = true;
        BackStackState[] arrbackStackState = null;
        this.mSavedNonConfig = null;
        if (this.mActive != null) {
            Object object;
            Object object2;
            int n;
            if (this.mActive.size() <= 0) {
                return null;
            }
            int n2 = this.mActive.size();
            FragmentState[] arrfragmentState = new FragmentState[n2];
            int n3 = 0;
            int n4 = n = 0;
            while (n < n2) {
                object = (int[])this.mActive.valueAt(n);
                if (object != null) {
                    StringBuilder stringBuilder;
                    if (object.mIndex < 0) {
                        object2 = new StringBuilder();
                        object2.append("Failure saving state: active ");
                        object2.append(object);
                        object2.append(" has cleared index: ");
                        object2.append(object.mIndex);
                        this.throwException(new IllegalStateException(object2.toString()));
                    }
                    arrfragmentState[n] = object2 = new FragmentState((Fragment)object);
                    if (object.mState > 0 && object2.mSavedFragmentState == null) {
                        object2.mSavedFragmentState = this.saveFragmentBasicState((Fragment)object);
                        if (object.mTarget != null) {
                            if (object.mTarget.mIndex < 0) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Failure saving state: ");
                                stringBuilder.append(object);
                                stringBuilder.append(" has target not in fragment manager: ");
                                stringBuilder.append(object.mTarget);
                                this.throwException(new IllegalStateException(stringBuilder.toString()));
                            }
                            if (object2.mSavedFragmentState == null) {
                                object2.mSavedFragmentState = new Bundle();
                            }
                            this.putFragment(object2.mSavedFragmentState, TARGET_STATE_TAG, object.mTarget);
                            if (object.mTargetRequestCode != 0) {
                                object2.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, object.mTargetRequestCode);
                            }
                        }
                    } else {
                        object2.mSavedFragmentState = object.mSavedFragmentState;
                    }
                    if (DEBUG) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Saved state of ");
                        stringBuilder.append(object);
                        stringBuilder.append(": ");
                        stringBuilder.append((Object)object2.mSavedFragmentState);
                        Log.v((String)TAG, (String)stringBuilder.toString());
                    }
                    n4 = 1;
                }
                ++n;
            }
            if (n4 == 0) {
                if (DEBUG) {
                    Log.v((String)TAG, (String)"saveAllState: no fragments!");
                }
                return null;
            }
            n4 = this.mAdded.size();
            if (n4 > 0) {
                object2 = new int[n4];
                n = 0;
                do {
                    object = object2;
                    if (n < n4) {
                        object2[n] = this.mAdded.get((int)n).mIndex;
                        if (object2[n] < 0) {
                            object = new StringBuilder();
                            object.append("Failure saving state: active ");
                            object.append(this.mAdded.get(n));
                            object.append(" has cleared index: ");
                            object.append((int)object2[n]);
                            this.throwException(new IllegalStateException(object.toString()));
                        }
                        if (DEBUG) {
                            object = new StringBuilder();
                            object.append("saveAllState: adding fragment #");
                            object.append(n);
                            object.append(": ");
                            object.append(this.mAdded.get(n));
                            Log.v((String)TAG, (String)object.toString());
                        }
                        ++n;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                object = null;
            }
            object2 = arrbackStackState;
            if (this.mBackStack != null) {
                n4 = this.mBackStack.size();
                object2 = arrbackStackState;
                if (n4 > 0) {
                    arrbackStackState = new BackStackState[n4];
                    n = n3;
                    do {
                        object2 = arrbackStackState;
                        if (n >= n4) break;
                        arrbackStackState[n] = new BackStackState(this.mBackStack.get(n));
                        if (DEBUG) {
                            object2 = new StringBuilder();
                            object2.append("saveAllState: adding back stack #");
                            object2.append(n);
                            object2.append(": ");
                            object2.append(this.mBackStack.get(n));
                            Log.v((String)TAG, (String)object2.toString());
                        }
                        ++n;
                    } while (true);
                }
            }
            arrbackStackState = new BackStackState[]();
            arrbackStackState.mActive = arrfragmentState;
            arrbackStackState.mAdded = object;
            arrbackStackState.mBackStack = object2;
            if (this.mPrimaryNav != null) {
                arrbackStackState.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex;
            }
            arrbackStackState.mNextFragmentIndex = this.mNextFragmentIndex;
            this.saveNonConfig();
            return arrbackStackState;
        }
        return null;
    }

    Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        this.dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        } else {
            bundle = null;
        }
        if (fragment.mView != null) {
            this.saveFragmentViewState(fragment);
        }
        Bundle bundle2 = bundle;
        if (fragment.mSavedViewState != null) {
            bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        bundle = bundle2;
        if (!fragment.mUserVisibleHint) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment object) {
        StringBuilder stringBuilder;
        if (object.mIndex < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(object);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        int n = object.mState;
        stringBuilder = null;
        if (n > 0) {
            Bundle bundle = this.saveFragmentBasicState((Fragment)object);
            object = stringBuilder;
            if (bundle != null) {
                object = new Fragment.SavedState(bundle);
            }
            return object;
        }
        return null;
    }

    void saveFragmentViewState(Fragment fragment) {
        if (fragment.mInnerView == null) {
            return;
        }
        if (this.mStateArray == null) {
            this.mStateArray = new SparseArray();
        } else {
            this.mStateArray.clear();
        }
        fragment.mInnerView.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            fragment.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    void saveNonConfig() {
        Object object;
        Object object2;
        Object object3;
        if (this.mActive != null) {
            Object object4;
            Object object5;
            int n = 0;
            Object object6 = object4 = (object5 = null);
            do {
                object = object5;
                object3 = object4;
                object2 = object6;
                if (n < this.mActive.size()) {
                    Fragment fragment = (Fragment)this.mActive.valueAt(n);
                    object3 = object5;
                    object = object4;
                    Object object7 = object6;
                    if (fragment != null) {
                        int n2;
                        object2 = object5;
                        if (fragment.mRetainInstance) {
                            object3 = object5;
                            if (object5 == null) {
                                object3 = new ArrayList();
                            }
                            object3.add(fragment);
                            n2 = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                            fragment.mTargetIndex = n2;
                            object2 = object3;
                            if (DEBUG) {
                                object5 = new StringBuilder();
                                object5.append("retainNonConfig: keeping retained ");
                                object5.append(fragment);
                                Log.v((String)TAG, (String)object5.toString());
                                object2 = object3;
                            }
                        }
                        if (fragment.mChildFragmentManager != null) {
                            fragment.mChildFragmentManager.saveNonConfig();
                            object3 = fragment.mChildFragmentManager.mSavedNonConfig;
                        } else {
                            object3 = fragment.mChildNonConfig;
                        }
                        object5 = object4;
                        if (object4 == null) {
                            object5 = object4;
                            if (object3 != null) {
                                object4 = new ArrayList(this.mActive.size());
                                n2 = 0;
                                do {
                                    object5 = object4;
                                    if (n2 >= n) break;
                                    object4.add(null);
                                    ++n2;
                                } while (true);
                            }
                        }
                        if (object5 != null) {
                            object5.add(object3);
                        }
                        object4 = object6;
                        if (object6 == null) {
                            object4 = object6;
                            if (fragment.mViewModelStore != null) {
                                object6 = new ArrayList(this.mActive.size());
                                n2 = 0;
                                do {
                                    object4 = object6;
                                    if (n2 >= n) break;
                                    object6.add(null);
                                    ++n2;
                                } while (true);
                            }
                        }
                        object3 = object2;
                        object = object5;
                        object7 = object4;
                        if (object4 != null) {
                            object4.add(fragment.mViewModelStore);
                            object7 = object4;
                            object = object5;
                            object3 = object2;
                        }
                    }
                    ++n;
                    object5 = object3;
                    object4 = object;
                    object6 = object7;
                    continue;
                }
                break;
            } while (true);
        } else {
            object = null;
            FragmentManagerNonConfig fragmentManagerNonConfig = object;
            object2 = fragmentManagerNonConfig;
            object3 = fragmentManagerNonConfig;
        }
        if (object == null && object3 == null && object2 == null) {
            this.mSavedNonConfig = null;
            return;
        }
        this.mSavedNonConfig = new FragmentManagerNonConfig((List<Fragment>)object, (List<FragmentManagerNonConfig>)object3, (List<ViewModelStore>)object2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBackStackIndex(int n, BackStackRecord backStackRecord) {
        synchronized (this) {
            int n2;
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            if (n < n2) {
                if (DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Setting back stack index ");
                    stringBuilder.append(n);
                    stringBuilder.append(" to ");
                    stringBuilder.append(backStackRecord);
                    Log.v((String)TAG, (String)stringBuilder.toString());
                }
                this.mBackStackIndices.set(n, backStackRecord);
            } else {
                StringBuilder stringBuilder;
                for (int i = n2 = this.mBackStackIndices.size(); i < n; ++i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList();
                    }
                    if (DEBUG) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Adding available back stack index ");
                        stringBuilder.append(i);
                        Log.v((String)TAG, (String)stringBuilder.toString());
                    }
                    this.mAvailBackStackIndices.add(i);
                }
                if (DEBUG) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Adding back stack index ");
                    stringBuilder.append(n);
                    stringBuilder.append(" with ");
                    stringBuilder.append(backStackRecord);
                    Log.v((String)TAG, (String)stringBuilder.toString());
                }
                this.mBackStackIndices.add(backStackRecord);
            }
            return;
        }
    }

    public void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null && (this.mActive.get(fragment.mIndex) != fragment || fragment.mHost != null && fragment.getFragmentManager() != this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not an active fragment of FragmentManager ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mPrimaryNav = fragment;
    }

    public void showFragment(Fragment fragment) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("show: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged ^= true;
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); ++i) {
            Fragment fragment = (Fragment)this.mActive.valueAt(i);
            if (fragment == null) continue;
            this.performPendingDeferredStart(fragment);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, stringBuilder);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        synchronized (copyOnWriteArrayList) {
            int n = 0;
            int n2 = this.mLifecycleCallbacks.size();
            do {
                block6 : {
                    block5 : {
                        if (n >= n2) break block5;
                        if (this.mLifecycleCallbacks.get((int)n).first != fragmentLifecycleCallbacks) break block6;
                        this.mLifecycleCallbacks.remove(n);
                    }
                    return;
                }
                ++n;
            } while (true);
        }
    }

    private static class AnimateOnHWLayerIfNeededListener
    extends AnimationListenerWrapper {
        View mView;

        AnimateOnHWLayerIfNeededListener(View view, Animation.AnimationListener animationListener) {
            super(animationListener);
            this.mView = view;
        }

        @CallSuper
        @Override
        public void onAnimationEnd(Animation animation) {
            if (!ViewCompat.isAttachedToWindow(this.mView) && Build.VERSION.SDK_INT < 24) {
                this.mView.setLayerType(0, null);
            } else {
                this.mView.post(new Runnable(){

                    @Override
                    public void run() {
                        AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
                    }
                });
            }
            super.onAnimationEnd(animation);
        }

    }

    private static class AnimationListenerWrapper
    implements Animation.AnimationListener {
        private final Animation.AnimationListener mWrapped;

        private AnimationListenerWrapper(Animation.AnimationListener animationListener) {
            this.mWrapped = animationListener;
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationEnd(animation);
            }
        }

        @CallSuper
        public void onAnimationRepeat(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationRepeat(animation);
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationStart(animation);
            }
        }
    }

    private static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        private AnimationOrAnimator(Animator animator) {
            this.animation = null;
            this.animator = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }

        private AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }
    }

    private static class AnimatorOnHWLayerIfNeededListener
    extends AnimatorListenerAdapter {
        View mView;

        AnimatorOnHWLayerIfNeededListener(View view) {
            this.mView = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.mView.setLayerType(0, null);
            animator.removeListener((Animator.AnimatorListener)this);
        }

        public void onAnimationStart(Animator animator) {
            this.mView.setLayerType(2, null);
        }
    }

    private static class EndViewTransitionAnimator
    extends AnimationSet
    implements Runnable {
        private final View mChild;
        private boolean mEnded;
        private final ViewGroup mParent;
        private boolean mTransitionEnded;

        EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
            super(false);
            this.mParent = viewGroup;
            this.mChild = view;
            this.addAnimation(animation);
        }

        public boolean getTransformation(long l, Transformation transformation) {
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (!super.getTransformation(l, transformation)) {
                this.mEnded = true;
                OneShotPreDrawListener.add((View)this.mParent, this);
            }
            return true;
        }

        public boolean getTransformation(long l, Transformation transformation, float f) {
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (!super.getTransformation(l, transformation, f)) {
                this.mEnded = true;
                OneShotPreDrawListener.add((View)this.mParent, this);
            }
            return true;
        }

        @Override
        public void run() {
            this.mParent.endViewTransition(this.mChild);
            this.mTransitionEnded = true;
        }
    }

    static class FragmentTag {
        public static final int[] Fragment = new int[]{16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    static interface OpGenerator {
        public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2);
    }

    private class PopBackStackState
    implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String string, int n, int n2) {
            this.mName = string;
            this.mId = n;
            this.mFlags = n2;
        }

        @Override
        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            FragmentManager fragmentManager;
            if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (fragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManager.popBackStackImmediate()) {
                return false;
            }
            return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
        }
    }

    static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean bl) {
            this.mIsBack = bl;
            this.mRecord = backStackRecord;
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }

        public void completeTransaction() {
            int n = this.mNumPostponed;
            n = n > 0 ? 1 : 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int n2 = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < n2; ++i) {
                Fragment fragment = fragmentManagerImpl.mAdded.get(i);
                fragment.setOnStartEnterTransitionListener(null);
                if (n == 0 || !fragment.isPostponed()) continue;
                fragment.startPostponedEnterTransition();
            }
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, (boolean)(n ^ 1), true);
        }

        public boolean isReady() {
            if (this.mNumPostponed == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void onStartEnterTransition() {
            --this.mNumPostponed;
            if (this.mNumPostponed != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override
        public void startListening() {
            ++this.mNumPostponed;
        }
    }

}
