/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.animation.Interpolator
 *  android.widget.OverScroller
 */
package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.ArrayList;

class RecyclerView.ViewFlinger
implements Runnable {
    private boolean mEatRunOnAnimationRequest = false;
    Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
    private int mLastFlingX;
    private int mLastFlingY;
    private boolean mReSchedulePostAnimationCallback = false;
    private OverScroller mScroller;

    RecyclerView.ViewFlinger() {
        this.mScroller = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
    }

    static /* synthetic */ OverScroller access$400(RecyclerView.ViewFlinger viewFlinger) {
        return viewFlinger.mScroller;
    }

    private int computeScrollDuration(int n, int n2, int n3, int n4) {
        int n5;
        int n6 = Math.abs(n);
        boolean bl = n6 > (n5 = Math.abs(n2));
        n3 = (int)Math.sqrt(n3 * n3 + n4 * n4);
        n2 = (int)Math.sqrt(n * n + n2 * n2);
        n = bl ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
        n4 = n / 2;
        float f = n2;
        float f2 = n;
        float f3 = Math.min(1.0f, f * 1.0f / f2);
        f = n4;
        f3 = this.distanceInfluenceForSnapDuration(f3);
        if (n3 > 0) {
            n = 4 * Math.round(1000.0f * Math.abs((f + f3 * f) / (float)n3));
        } else {
            n = bl ? n6 : n5;
            n = (int)(((float)n / f2 + 1.0f) * 300.0f);
        }
        return Math.min(n, 2000);
    }

    private void disableRunOnAnimationRequests() {
        this.mReSchedulePostAnimationCallback = false;
        this.mEatRunOnAnimationRequest = true;
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    private void enableRunOnAnimationRequests() {
        this.mEatRunOnAnimationRequest = false;
        if (this.mReSchedulePostAnimationCallback) {
            this.postOnAnimation();
        }
    }

    public void fling(int n, int n2) {
        RecyclerView.this.setScrollState(2);
        this.mLastFlingY = 0;
        this.mLastFlingX = 0;
        this.mScroller.fling(0, 0, n, n2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.postOnAnimation();
    }

    void postOnAnimation() {
        if (this.mEatRunOnAnimationRequest) {
            this.mReSchedulePostAnimationCallback = true;
            return;
        }
        RecyclerView.this.removeCallbacks((Runnable)this);
        ViewCompat.postOnAnimation((View)RecyclerView.this, this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        block37 : {
            block38 : {
                block41 : {
                    block39 : {
                        block40 : {
                            if (RecyclerView.this.mLayout == null) {
                                this.stop();
                                return;
                            }
                            this.disableRunOnAnimationRequests();
                            RecyclerView.this.consumePendingUpdateOperations();
                            var13_1 = this.mScroller;
                            var14_2 = RecyclerView.this.mLayout.mSmoothScroller;
                            if (!var13_1.computeScrollOffset()) break block37;
                            var15_3 = RecyclerView.access$500(RecyclerView.this);
                            var11_4 = var13_1.getCurrX();
                            var12_5 = var13_1.getCurrY();
                            var2_6 = var11_4 - this.mLastFlingX;
                            var1_7 = var12_5 - this.mLastFlingY;
                            this.mLastFlingX = var11_4;
                            this.mLastFlingY = var12_5;
                            var6_8 = var2_6;
                            var5_9 = var1_7;
                            if (RecyclerView.this.dispatchNestedPreScroll(var2_6, var1_7, var15_3, null, 1)) {
                                var6_8 = var2_6 - var15_3[0];
                                var5_9 = var1_7 - var15_3[1];
                            }
                            if (RecyclerView.this.mAdapter != null) {
                                RecyclerView.this.startInterceptRequestLayout();
                                RecyclerView.this.onEnterLayoutOrScroll();
                                TraceCompat.beginSection("RV Scroll");
                                RecyclerView.this.fillRemainingScrollValues(RecyclerView.this.mState);
                                if (var6_8 != 0) {
                                    var1_7 = RecyclerView.this.mLayout.scrollHorizontallyBy(var6_8, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                                    var2_6 = var6_8 - var1_7;
                                } else {
                                    var2_6 = var1_7 = 0;
                                }
                                if (var5_9 != 0) {
                                    var3_10 = RecyclerView.this.mLayout.scrollVerticallyBy(var5_9, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                                    var4_11 = var5_9 - var3_10;
                                } else {
                                    var4_11 = var3_10 = 0;
                                }
                                TraceCompat.endSection();
                                RecyclerView.this.repositionShadowingViews();
                                RecyclerView.this.onExitLayoutOrScroll();
                                RecyclerView.this.stopInterceptRequestLayout(false);
                                var7_12 = var1_7;
                                var10_13 = var2_6;
                                var9_14 = var3_10;
                                var8_15 = var4_11;
                                if (var14_2 != null) {
                                    var7_12 = var1_7;
                                    var10_13 = var2_6;
                                    var9_14 = var3_10;
                                    var8_15 = var4_11;
                                    if (!var14_2.isPendingInitialRun()) {
                                        var7_12 = var1_7;
                                        var10_13 = var2_6;
                                        var9_14 = var3_10;
                                        var8_15 = var4_11;
                                        if (var14_2.isRunning()) {
                                            var7_12 = RecyclerView.this.mState.getItemCount();
                                            if (var7_12 == 0) {
                                                var14_2.stop();
                                                var7_12 = var1_7;
                                                var10_13 = var2_6;
                                                var9_14 = var3_10;
                                                var8_15 = var4_11;
                                            } else if (var14_2.getTargetPosition() >= var7_12) {
                                                var14_2.setTargetPosition(var7_12 - 1);
                                                RecyclerView.SmoothScroller.access$600(var14_2, var6_8 - var2_6, var5_9 - var4_11);
                                                var7_12 = var1_7;
                                                var10_13 = var2_6;
                                                var9_14 = var3_10;
                                                var8_15 = var4_11;
                                            } else {
                                                RecyclerView.SmoothScroller.access$600(var14_2, var6_8 - var2_6, var5_9 - var4_11);
                                                var7_12 = var1_7;
                                                var10_13 = var2_6;
                                                var9_14 = var3_10;
                                                var8_15 = var4_11;
                                            }
                                        }
                                    }
                                }
                            } else {
                                var8_15 = var2_6 = (var1_7 = (var7_12 = 0));
                                var9_14 = var2_6;
                                var10_13 = var1_7;
                            }
                            if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                                RecyclerView.this.invalidate();
                            }
                            if (RecyclerView.this.getOverScrollMode() != 2) {
                                RecyclerView.this.considerReleasingGlowsOnScroll(var6_8, var5_9);
                            }
                            if (RecyclerView.this.dispatchNestedScroll(var7_12, var9_14, var10_13, var8_15, null, 1) || var10_13 == 0 && var8_15 == 0) break block38;
                            var2_6 = (int)var13_1.getCurrVelocity();
                            if (var10_13 == var11_4) ** GOTO lbl-1000
                            if (var10_13 < 0) {
                                var1_7 = - var2_6;
                            } else if (var10_13 > 0) {
                                var1_7 = var2_6;
                            } else lbl-1000: // 2 sources:
                            {
                                var1_7 = 0;
                            }
                            if (var8_15 == var12_5) break block39;
                            if (var8_15 >= 0) break block40;
                            var2_6 = - var2_6;
                            break block41;
                        }
                        if (var8_15 > 0) break block41;
                    }
                    var2_6 = 0;
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.absorbGlows(var1_7, var2_6);
                }
                if (!(var1_7 == 0 && var10_13 != var11_4 && var13_1.getFinalX() != 0 || var2_6 == 0 && var8_15 != var12_5 && var13_1.getFinalY() != 0)) {
                    var13_1.abortAnimation();
                }
            }
            if (var7_12 != 0 || var9_14 != 0) {
                RecyclerView.this.dispatchOnScrolled(var7_12, var9_14);
            }
            if (!RecyclerView.access$700(RecyclerView.this)) {
                RecyclerView.this.invalidate();
            }
            var1_7 = var5_9 != 0 && RecyclerView.this.mLayout.canScrollVertically() != false && var9_14 == var5_9 ? 1 : 0;
            var2_6 = var6_8 != 0 && RecyclerView.this.mLayout.canScrollHorizontally() != false && var7_12 == var6_8 ? 1 : 0;
            var1_7 = (var6_8 != 0 || var5_9 != 0) && var2_6 == 0 && var1_7 == 0 ? 0 : 1;
            if (!var13_1.isFinished() && (var1_7 != 0 || RecyclerView.this.hasNestedScrollingParent(1))) {
                this.postOnAnimation();
                if (RecyclerView.this.mGapWorker != null) {
                    RecyclerView.this.mGapWorker.postFromTraversal(RecyclerView.this, var6_8, var5_9);
                }
            } else {
                RecyclerView.this.setScrollState(0);
                if (RecyclerView.access$800()) {
                    RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                }
                RecyclerView.this.stopNestedScroll(1);
            }
        }
        if (var14_2 != null) {
            if (var14_2.isPendingInitialRun()) {
                RecyclerView.SmoothScroller.access$600(var14_2, 0, 0);
            }
            if (!this.mReSchedulePostAnimationCallback) {
                var14_2.stop();
            }
        }
        this.enableRunOnAnimationRequests();
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, 0, 0);
    }

    public void smoothScrollBy(int n, int n2, int n3) {
        this.smoothScrollBy(n, n2, n3, RecyclerView.sQuinticInterpolator);
    }

    public void smoothScrollBy(int n, int n2, int n3, int n4) {
        this.smoothScrollBy(n, n2, this.computeScrollDuration(n, n2, n3, n4));
    }

    public void smoothScrollBy(int n, int n2, int n3, Interpolator interpolator) {
        if (this.mInterpolator != interpolator) {
            this.mInterpolator = interpolator;
            this.mScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
        }
        RecyclerView.this.setScrollState(2);
        this.mLastFlingY = 0;
        this.mLastFlingX = 0;
        this.mScroller.startScroll(0, 0, n, n2, n3);
        if (Build.VERSION.SDK_INT < 23) {
            this.mScroller.computeScrollOffset();
        }
        this.postOnAnimation();
    }

    public void smoothScrollBy(int n, int n2, Interpolator interpolator) {
        int n3 = this.computeScrollDuration(n, n2, 0, 0);
        Interpolator interpolator2 = interpolator;
        if (interpolator == null) {
            interpolator2 = RecyclerView.sQuinticInterpolator;
        }
        this.smoothScrollBy(n, n2, n3, interpolator2);
    }

    public void stop() {
        RecyclerView.this.removeCallbacks((Runnable)this);
        this.mScroller.abortAnimation();
    }
}
