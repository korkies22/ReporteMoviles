/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.util.Log
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

public static abstract class RecyclerView.SmoothScroller {
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mPendingInitialRun;
    private RecyclerView mRecyclerView;
    private final Action mRecyclingAction = new Action(0, 0);
    private boolean mRunning;
    private int mTargetPosition = -1;
    private View mTargetView;

    static /* synthetic */ void access$600(RecyclerView.SmoothScroller smoothScroller, int n, int n2) {
        smoothScroller.onAnimation(n, n2);
    }

    private void onAnimation(int n, int n2) {
        RecyclerView recyclerView = this.mRecyclerView;
        if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
            this.stop();
        }
        this.mPendingInitialRun = false;
        if (this.mTargetView != null) {
            if (this.getChildPosition(this.mTargetView) == this.mTargetPosition) {
                this.onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                this.mRecyclingAction.runIfNecessary(recyclerView);
                this.stop();
            } else {
                Log.e((String)RecyclerView.TAG, (String)"Passed over target position while smooth scrolling.");
                this.mTargetView = null;
            }
        }
        if (this.mRunning) {
            this.onSeekTargetStep(n, n2, recyclerView.mState, this.mRecyclingAction);
            boolean bl = this.mRecyclingAction.hasJumpTarget();
            this.mRecyclingAction.runIfNecessary(recyclerView);
            if (bl) {
                if (this.mRunning) {
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                    return;
                }
                this.stop();
            }
        }
    }

    public View findViewByPosition(int n) {
        return this.mRecyclerView.mLayout.findViewByPosition(n);
    }

    public int getChildCount() {
        return this.mRecyclerView.mLayout.getChildCount();
    }

    public int getChildPosition(View view) {
        return this.mRecyclerView.getChildLayoutPosition(view);
    }

    @Nullable
    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mLayoutManager;
    }

    public int getTargetPosition() {
        return this.mTargetPosition;
    }

    @Deprecated
    public void instantScrollToPosition(int n) {
        this.mRecyclerView.scrollToPosition(n);
    }

    public boolean isPendingInitialRun() {
        return this.mPendingInitialRun;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    protected void normalize(PointF pointF) {
        float f = (float)Math.sqrt(pointF.x * pointF.x + pointF.y * pointF.y);
        pointF.x /= f;
        pointF.y /= f;
    }

    protected void onChildAttachedToWindow(View view) {
        if (this.getChildPosition(view) == this.getTargetPosition()) {
            this.mTargetView = view;
        }
    }

    protected abstract void onSeekTargetStep(int var1, int var2, RecyclerView.State var3, Action var4);

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void onTargetFound(View var1, RecyclerView.State var2, Action var3);

    public void setTargetPosition(int n) {
        this.mTargetPosition = n;
    }

    void start(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = layoutManager;
        if (this.mTargetPosition == -1) {
            throw new IllegalArgumentException("Invalid target position");
        }
        this.mRecyclerView.mState.mTargetPosition = this.mTargetPosition;
        this.mRunning = true;
        this.mPendingInitialRun = true;
        this.mTargetView = this.findViewByPosition(this.getTargetPosition());
        this.onStart();
        this.mRecyclerView.mViewFlinger.postOnAnimation();
    }

    protected final void stop() {
        if (!this.mRunning) {
            return;
        }
        this.mRunning = false;
        this.onStop();
        this.mRecyclerView.mState.mTargetPosition = -1;
        this.mTargetView = null;
        this.mTargetPosition = -1;
        this.mPendingInitialRun = false;
        this.mLayoutManager.onSmoothScrollerStopped(this);
        this.mLayoutManager = null;
        this.mRecyclerView = null;
    }

    public static class Action {
        public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
        private boolean mChanged = false;
        private int mConsecutiveUpdates = 0;
        private int mDuration;
        private int mDx;
        private int mDy;
        private Interpolator mInterpolator;
        private int mJumpToPosition = -1;

        public Action(int n, int n2) {
            this(n, n2, Integer.MIN_VALUE, null);
        }

        public Action(int n, int n2, int n3) {
            this(n, n2, n3, null);
        }

        public Action(int n, int n2, int n3, Interpolator interpolator) {
            this.mDx = n;
            this.mDy = n2;
            this.mDuration = n3;
            this.mInterpolator = interpolator;
        }

        private void validate() {
            if (this.mInterpolator != null && this.mDuration < 1) {
                throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
            }
            if (this.mDuration < 1) {
                throw new IllegalStateException("Scroll duration must be a positive number");
            }
        }

        public int getDuration() {
            return this.mDuration;
        }

        public int getDx() {
            return this.mDx;
        }

        public int getDy() {
            return this.mDy;
        }

        public Interpolator getInterpolator() {
            return this.mInterpolator;
        }

        boolean hasJumpTarget() {
            if (this.mJumpToPosition >= 0) {
                return true;
            }
            return false;
        }

        public void jumpTo(int n) {
            this.mJumpToPosition = n;
        }

        void runIfNecessary(RecyclerView recyclerView) {
            if (this.mJumpToPosition >= 0) {
                int n = this.mJumpToPosition;
                this.mJumpToPosition = -1;
                recyclerView.jumpToPositionForSmoothScroller(n);
                this.mChanged = false;
                return;
            }
            if (this.mChanged) {
                this.validate();
                if (this.mInterpolator == null) {
                    if (this.mDuration == Integer.MIN_VALUE) {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                    } else {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                    }
                } else {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                }
                ++this.mConsecutiveUpdates;
                if (this.mConsecutiveUpdates > 10) {
                    Log.e((String)RecyclerView.TAG, (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.mChanged = false;
                return;
            }
            this.mConsecutiveUpdates = 0;
        }

        public void setDuration(int n) {
            this.mChanged = true;
            this.mDuration = n;
        }

        public void setDx(int n) {
            this.mChanged = true;
            this.mDx = n;
        }

        public void setDy(int n) {
            this.mChanged = true;
            this.mDy = n;
        }

        public void setInterpolator(Interpolator interpolator) {
            this.mChanged = true;
            this.mInterpolator = interpolator;
        }

        public void update(int n, int n2, int n3, Interpolator interpolator) {
            this.mDx = n;
            this.mDy = n2;
            this.mDuration = n3;
            this.mInterpolator = interpolator;
            this.mChanged = true;
        }
    }

    public static interface ScrollVectorProvider {
        public PointF computeScrollVectorForPosition(int var1);
    }

}
