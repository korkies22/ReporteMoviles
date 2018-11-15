/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.DataSetObserver
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.view.animation.Transformation
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ListAdapter
 *  android.widget.Scroller
 */
package de.cisha.android.ui.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalListView
extends AdapterView<ListAdapter> {
    protected ListAdapter mAdapter;
    private AdjustPositionAnimation mAdjustAnimation;
    public boolean mAlwaysOverrideTouch = true;
    private boolean mCircleScrolling = false;
    protected int mCurrentX;
    private boolean mDataChanged = false;
    private DataSetObserver mDataObserver = new DataSetObserver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onChanged() {
            HorizontalListView horizontalListView = HorizontalListView.this;
            synchronized (horizontalListView) {
                HorizontalListView.this.mDataChanged = true;
            }
            HorizontalListView.this.adjustSubviewPositions();
        }

        public void onInvalidated() {
            HorizontalListView.this.reset();
            HorizontalListView.this.adjustSubviewPositions();
        }
    };
    private int mDisplayOffset = 0;
    private boolean mFingerDown = false;
    private boolean mFlinging = false;
    private GestureDetector mGesture;
    private int mLeftViewIndex = -1;
    private int mMaxX = Integer.MAX_VALUE;
    private int mMinX = 0;
    protected int mNextX;
    private float mOldScrollSubPixelRemain;
    private float mOldX;
    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener(){

        private boolean isEventWithinView(MotionEvent motionEvent, View view) {
            Rect rect = new Rect();
            int[] arrn = new int[2];
            view.getLocationOnScreen(arrn);
            int n = arrn[0];
            int n2 = view.getWidth();
            int n3 = arrn[1];
            rect.set(n, n3, n2 + n, view.getHeight() + n3);
            return rect.contains((int)motionEvent.getRawX(), (int)motionEvent.getRawY());
        }

        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            int n = HorizontalListView.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = HorizontalListView.this.getChildAt(i);
                if (!this.isEventWithinView(motionEvent, view)) continue;
                if (HorizontalListView.this.mOnItemLongClicked == null) break;
                HorizontalListView.this.mOnItemLongClicked.onItemLongClick((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                return;
            }
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            for (int i = 0; i < HorizontalListView.this.getChildCount(); ++i) {
                View view = HorizontalListView.this.getChildAt(i);
                if (!this.isEventWithinView(motionEvent, view)) continue;
                if (HorizontalListView.this.mOnItemClicked != null) {
                    HorizontalListView.this.mOnItemClicked.onItemClick((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                }
                if (HorizontalListView.this.mOnItemSelected == null) break;
                HorizontalListView.this.mOnItemSelected.onItemSelected((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                return true;
            }
            return true;
        }
    };
    private AdapterView.OnItemClickListener mOnItemClicked;
    private AdapterView.OnItemLongClickListener mOnItemLongClicked;
    private AdapterView.OnItemSelectedListener mOnItemSelected;
    private Queue<View> mRemovedViewQueue = new LinkedList<View>();
    private int mRightViewIndex = 0;
    protected Scroller mScroller;
    private int mSelectedIndex = 0;
    private boolean mSnappingToCenter = false;

    public HorizontalListView(Context context) {
        super(context);
        this.initView();
    }

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private void addAndMeasureChild(View view, int n) {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new ViewGroup.LayoutParams(-1, -1);
        }
        this.addViewInLayout(view, n, layoutParams2, true);
        view.measure(View.MeasureSpec.makeMeasureSpec((int)this.getWidth(), (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)this.getHeight(), (int)Integer.MIN_VALUE));
    }

    private void adjustSubviewPositions() {
        int n;
        if (this.mAdapter == null) {
            return;
        }
        if (this.mDataChanged) {
            n = this.mCurrentX;
            this.initView();
            this.removeAllViewsInLayout();
            this.mNextX = n;
            this.mDataChanged = false;
        }
        if (this.mScroller.computeScrollOffset()) {
            this.mNextX = this.mScroller.getCurrX();
        }
        if (this.mNextX <= this.mMinX && !this.mCircleScrolling) {
            this.mNextX = this.mMinX;
            this.mScroller.forceFinished(true);
            if (this.mAdjustAnimation != null) {
                this.mAdjustAnimation.stop();
            }
        }
        if (this.mNextX >= this.mMaxX && !this.mCircleScrolling) {
            this.mNextX = this.mMaxX;
            this.mScroller.forceFinished(true);
            if (this.mAdjustAnimation != null) {
                this.mAdjustAnimation.stop();
            }
        }
        n = this.mCurrentX - this.mNextX;
        this.removeNonVisibleItems(n);
        this.fillList(n);
        this.positionItems(n);
        this.mCurrentX = this.mNextX;
        if (!this.mScroller.isFinished()) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    HorizontalListView.this.adjustSubviewPositions();
                }
            });
        } else {
            if (this.mFlinging) {
                this.scrollerFinished();
            }
            this.mFlinging = false;
        }
        this.invalidate();
    }

    private void fillList(int n) {
        View view = this.getChildAt(this.getChildCount() - 1);
        int n2 = 0;
        int n3 = view != null ? view.getRight() : 0;
        this.fillListRight(n3, n);
        view = this.getChildAt(0);
        n3 = n2;
        if (view != null) {
            n3 = view.getLeft();
        }
        this.fillListLeft(n3, n);
    }

    private void fillListLeft(int n, int n2) {
        int n3 = this.getNextLeftItemNo();
        int n4 = n;
        n = n3;
        while (n4 + n2 > 0 && n >= 0) {
            View view = this.mAdapter.getView(n, this.mRemovedViewQueue.poll(), (ViewGroup)this);
            this.addAndMeasureChild(view, 0);
            n4 -= view.getMeasuredWidth();
            --this.mLeftViewIndex;
            n = this.getNextLeftItemNo();
            this.mDisplayOffset -= view.getMeasuredWidth();
        }
        if (this.mLeftViewIndex == -1) {
            this.mMinX = this.mCurrentX + n4 - this.getWidth() / 2;
            return;
        }
        this.mMinX = Integer.MIN_VALUE;
    }

    private void fillListRight(int n, int n2) {
        int n3 = this.getNextRightItemNo();
        int n4 = n;
        n = n3;
        while (n4 + n2 < this.getWidth() && n < this.mAdapter.getCount()) {
            View view = this.mAdapter.getView(n, this.mRemovedViewQueue.poll(), (ViewGroup)this);
            this.addAndMeasureChild(view, -1);
            n4 += view.getMeasuredWidth();
            ++this.mRightViewIndex;
            n = this.getNextRightItemNo();
        }
        if (this.mRightViewIndex == this.mAdapter.getCount()) {
            this.mMaxX = this.mCurrentX + n4 - this.getWidth() / 2;
            return;
        }
        this.mMaxX = Integer.MAX_VALUE;
    }

    private int getAdapterIndexNumber(int n) {
        if (this.mAdapter != null) {
            if (this.mCircleScrolling) {
                int n2;
                n = n2 = n % this.mAdapter.getCount();
                if (n2 < 0) {
                    n = n2 + this.mAdapter.getCount();
                }
                return n;
            }
            return n;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private View getMiddleItem() {
        synchronized (this) {
            View view;
            int n = this.getChildCount();
            if (n == 0) {
                return null;
            }
            int n2 = this.getWidth();
            int n3 = 0;
            do {
                view = this.getChildAt(n3);
            } while (++n3 < n && view.getRight() < n2 / 2);
            return view;
        }
    }

    private int getMiddlePositon() {
        int n = this.getChildCount();
        int n2 = 0;
        if (n != 0) {
            int n3;
            View view;
            int n4 = this.getWidth();
            do {
                view = this.getChildAt(n2);
                n3 = n2 + 1;
                if (n3 >= n) break;
                n2 = n3;
            } while (view.getRight() < n4 / 2);
            return this.getAdapterIndexNumber(this.mLeftViewIndex + n3);
        }
        return 0;
    }

    private int getNextLeftItemNo() {
        return this.getAdapterIndexNumber(this.mLeftViewIndex);
    }

    private int getNextRightItemNo() {
        return this.getAdapterIndexNumber(this.mRightViewIndex);
    }

    private void initView() {
        synchronized (this) {
            this.mLeftViewIndex = -1;
            this.mRightViewIndex = 0;
            this.mDisplayOffset = 0;
            this.mCurrentX = 0;
            this.mNextX = 0;
            this.mMaxX = Integer.MAX_VALUE;
            this.mScroller = new Scroller(this.getContext());
            this.mGesture = new GestureDetector(this.getContext(), this.mOnGesture);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onUp() {
        synchronized (this) {
            if (!this.mFlinging) {
                this.readjustScrollToMiddleItem();
            }
            return;
        }
    }

    private void positionItems(int n) {
        if (this.getChildCount() > 0) {
            this.mDisplayOffset += n;
            int n2 = this.mDisplayOffset;
            for (n = 0; n < this.getChildCount(); ++n) {
                View view = this.getChildAt(n);
                int n3 = view.getMeasuredWidth();
                view.layout(n2, 0, n2 + n3, view.getMeasuredHeight());
                n2 += n3 + view.getPaddingRight();
            }
        }
    }

    private void readjustScrollToMiddleItem() {
        View view;
        if (this.mSnappingToCenter && (view = this.getMiddleItem()) != null) {
            this.mSelectedIndex = this.getMiddlePositon();
            int n = this.getWidth() / 2;
            int n2 = view.getMeasuredWidth();
            int n3 = view.getLeft();
            n2 /= 2;
            if (this.mAdjustAnimation != null) {
                this.mAdjustAnimation.stop();
            }
            this.mAdjustAnimation = new AdjustPositionAnimation(n3 + n2 - n);
            this.setAnimation((Animation)this.mAdjustAnimation);
            this.startAnimation((Animation)this.mAdjustAnimation);
        }
    }

    private void removeNonVisibleItems(int n) {
        View view = this.getChildAt(0);
        while (view != null && view.getRight() + n <= 0) {
            this.mDisplayOffset += view.getMeasuredWidth();
            this.mRemovedViewQueue.offer(view);
            this.removeViewInLayout(view);
            ++this.mLeftViewIndex;
            view = this.getChildAt(0);
        }
        view = this.getChildAt(this.getChildCount() - 1);
        while (view != null && view.getLeft() + n >= this.getWidth()) {
            this.mRemovedViewQueue.offer(view);
            this.removeViewInLayout(view);
            --this.mRightViewIndex;
            view = this.getChildAt(this.getChildCount() - 1);
        }
    }

    private void reset() {
        synchronized (this) {
            this.initView();
            this.removeAllViewsInLayout();
            this.adjustSubviewPositions();
            return;
        }
    }

    private void scrollerFinished() {
        if (!this.mFingerDown) {
            this.readjustScrollToMiddleItem();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean bl = super.dispatchTouchEvent(motionEvent) | this.mGesture.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            default: {
                return bl;
            }
            case 2: {
                synchronized (this) {
                    float f = motionEvent.getX(0);
                    float f2 = this.mOldX - f;
                    this.mOldX = f;
                    int n = Math.round(this.mOldScrollSubPixelRemain + f2);
                    this.mOldScrollSubPixelRemain = f2 + this.mOldScrollSubPixelRemain - (float)n;
                    this.mNextX += n;
                }
                this.adjustSubviewPositions();
                return true;
            }
            case 1: 
            case 3: {
                this.mFingerDown = false;
                this.onUp();
                return bl;
            }
            case 0: 
        }
        this.mFingerDown = true;
        this.mOldX = motionEvent.getX(0);
        return bl;
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public long getSelectedItemId() {
        long l;
        long l2 = l = (long)this.getMiddlePositon();
        if (l == -1L) {
            l2 = Long.MIN_VALUE;
        }
        return l2;
    }

    public int getSelectedItemPosition() {
        int n;
        int n2 = n = this.getMiddlePositon();
        if (n == -1) {
            n2 = -1;
        }
        return n2;
    }

    public View getSelectedView() {
        return this.getMiddleItem();
    }

    public boolean isCircleScrolling() {
        return this.mCircleScrolling;
    }

    public boolean isSnappingToCenter() {
        return this.mSnappingToCenter;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean onDown(MotionEvent motionEvent) {
        synchronized (this) {
            this.mOldScrollSubPixelRemain = 0.0f;
            this.mScroller.forceFinished(true);
            if (this.mAdjustAnimation != null) {
                this.mAdjustAnimation.stop();
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        synchronized (this) {
            this.mFlinging = true;
            motionEvent = this.mScroller;
            int n = this.mNextX;
            int n2 = (int)(- f);
            int n3 = this.mCircleScrolling ? Integer.MIN_VALUE : this.mMinX;
            int n4 = this.mCircleScrolling ? Integer.MAX_VALUE : this.mMaxX;
            motionEvent.fling(n, 0, n2, 0, n3, n4, 0, 0);
        }
        this.adjustSubviewPositions();
        return true;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        synchronized (this) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.setSelection(this.mSelectedIndex);
        this.adjustSubviewPositions();
    }

    public void scrollTo(int n) {
        synchronized (this) {
            this.mScroller.startScroll(this.mCurrentX, 0, n - this.mCurrentX, 0, 500);
            this.adjustSubviewPositions();
            this.invalidate();
            return;
        }
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = listAdapter;
        this.mAdapter.registerDataSetObserver(this.mDataObserver);
        this.reset();
    }

    public void setCircleScrolling(boolean bl) {
        this.mCircleScrolling = bl;
        this.adjustSubviewPositions();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClicked = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClicked = onItemLongClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelected = onItemSelectedListener;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setSelection(int var1_1) {
        // MONITORENTER : this
        if (var1_1 < 0) ** GOTO lbl26
        if (var1_1 < this.mAdapter.getCount()) {
            for (var2_2 = this.getChildCount() - 1; var2_2 >= 0; --var2_2) {
                var6_3 = this.getChildAt(var2_2);
                this.mRemovedViewQueue.offer(var6_3);
                this.removeViewInLayout(var6_3);
            }
            this.mLeftViewIndex = var1_1 - 1;
            this.mRightViewIndex = var1_1 + 1;
            var6_3 = this.mAdapter.getView(var1_1, this.mRemovedViewQueue.poll(), (ViewGroup)this);
            if (var6_3 != null) {
                this.mSelectedIndex = var1_1;
                this.addAndMeasureChild(var6_3, 0);
                var2_2 = var6_3.getMeasuredWidth();
                var3_5 = this.getWidth() / 2;
                var4_6 = var3_5 - var2_2 / 2;
                var5_7 = var2_2 / 2;
                this.mDisplayOffset = var4_6;
                this.fillListLeft(var4_6, 0);
                this.fillListRight(var3_5 + var5_7, 0);
                this.positionItems(0);
                this.mNextX = this.mCurrentX = var2_2 * var1_1;
                this.adjustSubviewPositions();
            }
        }
lbl26: // 6 sources:
        // MONITOREXIT : this
        return;
    }

    public void setSnappingToCenter(boolean bl) {
        this.mSnappingToCenter = bl;
        this.adjustSubviewPositions();
    }

    private class AdjustPositionAnimation
    extends Animation {
        private int mAlreadyScrolled = 0;
        private boolean mCancelled = false;
        private int mPixelsToScrollLeft;

        public AdjustPositionAnimation(int n) {
            this.mPixelsToScrollLeft = n;
            this.setInterpolator((Interpolator)new AccelerateDecelerateInterpolator());
            this.setDuration(200L);
        }

        protected void applyTransformation(float f, Transformation object) {
            if (!this.mCancelled) {
                int n = (int)(f * (float)this.mPixelsToScrollLeft) - this.mAlreadyScrolled;
                this.mAlreadyScrolled += n;
                object = HorizontalListView.this;
                object.mNextX += n;
                HorizontalListView.this.adjustSubviewPositions();
            }
        }

        public void stop() {
            this.mCancelled = true;
        }
    }

}
