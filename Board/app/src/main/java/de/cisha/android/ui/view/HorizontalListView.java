// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.view;

import android.view.animation.Transformation;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Adapter;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.util.AttributeSet;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import java.util.LinkedList;
import android.content.Context;
import android.widget.Scroller;
import android.view.View;
import java.util.Queue;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.database.DataSetObserver;
import android.widget.ListAdapter;
import android.widget.AdapterView;

public class HorizontalListView extends AdapterView<ListAdapter>
{
    protected ListAdapter mAdapter;
    private AdjustPositionAnimation mAdjustAnimation;
    public boolean mAlwaysOverrideTouch;
    private boolean mCircleScrolling;
    protected int mCurrentX;
    private boolean mDataChanged;
    private DataSetObserver mDataObserver;
    private int mDisplayOffset;
    private boolean mFingerDown;
    private boolean mFlinging;
    private GestureDetector mGesture;
    private int mLeftViewIndex;
    private int mMaxX;
    private int mMinX;
    protected int mNextX;
    private float mOldScrollSubPixelRemain;
    private float mOldX;
    private GestureDetector.OnGestureListener mOnGesture;
    private AdapterView.OnItemClickListener mOnItemClicked;
    private AdapterView.OnItemLongClickListener mOnItemLongClicked;
    private AdapterView.OnItemSelectedListener mOnItemSelected;
    private Queue<View> mRemovedViewQueue;
    private int mRightViewIndex;
    protected Scroller mScroller;
    private int mSelectedIndex;
    private boolean mSnappingToCenter;
    
    public HorizontalListView(final Context context) {
        super(context);
        this.mAlwaysOverrideTouch = true;
        this.mLeftViewIndex = -1;
        this.mRightViewIndex = 0;
        this.mDisplayOffset = 0;
        this.mRemovedViewQueue = new LinkedList<View>();
        this.mDataChanged = false;
        this.mFingerDown = false;
        this.mSnappingToCenter = false;
        this.mCircleScrolling = false;
        this.mFlinging = false;
        this.mSelectedIndex = 0;
        this.mMaxX = Integer.MAX_VALUE;
        this.mMinX = 0;
        this.mDataObserver = new DataSetObserver() {
            public void onChanged() {
                synchronized (HorizontalListView.this) {
                    HorizontalListView.this.mDataChanged = true;
                    // monitorexit(this.this.0)
                    HorizontalListView.this.adjustSubviewPositions();
                }
            }
            
            public void onInvalidated() {
                HorizontalListView.this.reset();
                HorizontalListView.this.adjustSubviewPositions();
            }
        };
        this.mOnGesture = (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
            private boolean isEventWithinView(final MotionEvent motionEvent, final View view) {
                final Rect rect = new Rect();
                final int[] array = new int[2];
                view.getLocationOnScreen(array);
                final int n = array[0];
                final int width = view.getWidth();
                final int n2 = array[1];
                rect.set(n, n2, width + n, view.getHeight() + n2);
                return rect.contains((int)motionEvent.getRawX(), (int)motionEvent.getRawY());
            }
            
            public boolean onDown(final MotionEvent motionEvent) {
                return HorizontalListView.this.onDown(motionEvent);
            }
            
            public boolean onFling(final MotionEvent motionEvent, final MotionEvent motionEvent2, final float n, final float n2) {
                return HorizontalListView.this.onFling(motionEvent, motionEvent2, n, n2);
            }
            
            public void onLongPress(final MotionEvent motionEvent) {
                final int childCount = HorizontalListView.this.getChildCount();
                int i = 0;
                while (i < childCount) {
                    final View child = HorizontalListView.this.getChildAt(i);
                    if (this.isEventWithinView(motionEvent, child)) {
                        if (HorizontalListView.this.mOnItemLongClicked != null) {
                            HorizontalListView.this.mOnItemLongClicked.onItemLongClick((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                            return;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            
            public boolean onSingleTapConfirmed(final MotionEvent motionEvent) {
                int i = 0;
                while (i < HorizontalListView.this.getChildCount()) {
                    final View child = HorizontalListView.this.getChildAt(i);
                    if (this.isEventWithinView(motionEvent, child)) {
                        if (HorizontalListView.this.mOnItemClicked != null) {
                            HorizontalListView.this.mOnItemClicked.onItemClick((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                        }
                        if (HorizontalListView.this.mOnItemSelected != null) {
                            HorizontalListView.this.mOnItemSelected.onItemSelected((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                            return true;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                return true;
            }
        };
        this.initView();
    }
    
    public HorizontalListView(final Context context, final AttributeSet set) {
        super(context, set);
        this.mAlwaysOverrideTouch = true;
        this.mLeftViewIndex = -1;
        this.mRightViewIndex = 0;
        this.mDisplayOffset = 0;
        this.mRemovedViewQueue = new LinkedList<View>();
        this.mDataChanged = false;
        this.mFingerDown = false;
        this.mSnappingToCenter = false;
        this.mCircleScrolling = false;
        this.mFlinging = false;
        this.mSelectedIndex = 0;
        this.mMaxX = Integer.MAX_VALUE;
        this.mMinX = 0;
        this.mDataObserver = new DataSetObserver() {
            public void onChanged() {
                synchronized (HorizontalListView.this) {
                    HorizontalListView.this.mDataChanged = true;
                    // monitorexit(this.this.0)
                    HorizontalListView.this.adjustSubviewPositions();
                }
            }
            
            public void onInvalidated() {
                HorizontalListView.this.reset();
                HorizontalListView.this.adjustSubviewPositions();
            }
        };
        this.mOnGesture = (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
            private boolean isEventWithinView(final MotionEvent motionEvent, final View view) {
                final Rect rect = new Rect();
                final int[] array = new int[2];
                view.getLocationOnScreen(array);
                final int n = array[0];
                final int width = view.getWidth();
                final int n2 = array[1];
                rect.set(n, n2, width + n, view.getHeight() + n2);
                return rect.contains((int)motionEvent.getRawX(), (int)motionEvent.getRawY());
            }
            
            public boolean onDown(final MotionEvent motionEvent) {
                return HorizontalListView.this.onDown(motionEvent);
            }
            
            public boolean onFling(final MotionEvent motionEvent, final MotionEvent motionEvent2, final float n, final float n2) {
                return HorizontalListView.this.onFling(motionEvent, motionEvent2, n, n2);
            }
            
            public void onLongPress(final MotionEvent motionEvent) {
                final int childCount = HorizontalListView.this.getChildCount();
                int i = 0;
                while (i < childCount) {
                    final View child = HorizontalListView.this.getChildAt(i);
                    if (this.isEventWithinView(motionEvent, child)) {
                        if (HorizontalListView.this.mOnItemLongClicked != null) {
                            HorizontalListView.this.mOnItemLongClicked.onItemLongClick((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                            return;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            
            public boolean onSingleTapConfirmed(final MotionEvent motionEvent) {
                int i = 0;
                while (i < HorizontalListView.this.getChildCount()) {
                    final View child = HorizontalListView.this.getChildAt(i);
                    if (this.isEventWithinView(motionEvent, child)) {
                        if (HorizontalListView.this.mOnItemClicked != null) {
                            HorizontalListView.this.mOnItemClicked.onItemClick((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                        }
                        if (HorizontalListView.this.mOnItemSelected != null) {
                            HorizontalListView.this.mOnItemSelected.onItemSelected((AdapterView)HorizontalListView.this, child, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                            return true;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                return true;
            }
        };
        this.initView();
    }
    
    private void addAndMeasureChild(final View view, final int n) {
        ViewGroup.LayoutParams layoutParams;
        if ((layoutParams = view.getLayoutParams()) == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -1);
        }
        this.addViewInLayout(view, n, layoutParams, true);
        view.measure(View.MeasureSpec.makeMeasureSpec(this.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(this.getHeight(), Integer.MIN_VALUE));
    }
    
    private void adjustSubviewPositions() {
        if (this.mAdapter == null) {
            return;
        }
        if (this.mDataChanged) {
            final int mCurrentX = this.mCurrentX;
            this.initView();
            this.removeAllViewsInLayout();
            this.mNextX = mCurrentX;
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
        final int n = this.mCurrentX - this.mNextX;
        this.removeNonVisibleItems(n);
        this.fillList(n);
        this.positionItems(n);
        this.mCurrentX = this.mNextX;
        if (!this.mScroller.isFinished()) {
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    HorizontalListView.this.adjustSubviewPositions();
                }
            });
        }
        else {
            if (this.mFlinging) {
                this.scrollerFinished();
            }
            this.mFlinging = false;
        }
        this.invalidate();
    }
    
    private void fillList(final int n) {
        final View child = this.getChildAt(this.getChildCount() - 1);
        final boolean b = false;
        int right;
        if (child != null) {
            right = child.getRight();
        }
        else {
            right = 0;
        }
        this.fillListRight(right, n);
        final View child2 = this.getChildAt(0);
        int left = b ? 1 : 0;
        if (child2 != null) {
            left = child2.getLeft();
        }
        this.fillListLeft(left, n);
    }
    
    private void fillListLeft(int nextLeftItemNo, final int n) {
        final int nextLeftItemNo2 = this.getNextLeftItemNo();
        int n2;
        View view;
        for (n2 = nextLeftItemNo, nextLeftItemNo = nextLeftItemNo2; n2 + n > 0 && nextLeftItemNo >= 0; n2 -= view.getMeasuredWidth(), --this.mLeftViewIndex, nextLeftItemNo = this.getNextLeftItemNo(), this.mDisplayOffset -= view.getMeasuredWidth()) {
            view = this.mAdapter.getView(nextLeftItemNo, (View)this.mRemovedViewQueue.poll(), (ViewGroup)this);
            this.addAndMeasureChild(view, 0);
        }
        if (this.mLeftViewIndex == -1) {
            this.mMinX = this.mCurrentX + n2 - this.getWidth() / 2;
            return;
        }
        this.mMinX = Integer.MIN_VALUE;
    }
    
    private void fillListRight(int nextRightItemNo, final int n) {
        final int nextRightItemNo2 = this.getNextRightItemNo();
        int n2;
        View view;
        for (n2 = nextRightItemNo, nextRightItemNo = nextRightItemNo2; n2 + n < this.getWidth() && nextRightItemNo < this.mAdapter.getCount(); n2 += view.getMeasuredWidth(), ++this.mRightViewIndex, nextRightItemNo = this.getNextRightItemNo()) {
            view = this.mAdapter.getView(nextRightItemNo, (View)this.mRemovedViewQueue.poll(), (ViewGroup)this);
            this.addAndMeasureChild(view, -1);
        }
        if (this.mRightViewIndex == this.mAdapter.getCount()) {
            this.mMaxX = this.mCurrentX + n2 - this.getWidth() / 2;
            return;
        }
        this.mMaxX = Integer.MAX_VALUE;
    }
    
    private int getAdapterIndexNumber(int n) {
        if (this.mAdapter == null) {
            return -1;
        }
        if (this.mCircleScrolling) {
            final int n2 = n % this.mAdapter.getCount();
            if ((n = n2) < 0) {
                n = n2 + this.mAdapter.getCount();
            }
            return n;
        }
        return n;
    }
    
    private View getMiddleItem() {
        synchronized (this) {
            final int childCount = this.getChildCount();
            if (childCount != 0) {
                final int width = this.getWidth();
                int n = 0;
                View child;
                do {
                    child = this.getChildAt(n);
                    ++n;
                } while (n < childCount && child.getRight() < width / 2);
                return child;
            }
            return null;
        }
    }
    
    private int getMiddlePositon() {
        final int childCount = this.getChildCount();
        int n = 0;
        if (childCount != 0) {
            View child;
            int n2;
            do {
                child = this.getChildAt(n);
                n2 = n + 1;
                if (n2 >= childCount) {
                    break;
                }
                n = n2;
            } while (child.getRight() < this.getWidth() / 2);
            return this.getAdapterIndexNumber(this.mLeftViewIndex + n2);
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
        }
    }
    
    private void onUp() {
        synchronized (this) {
            if (!this.mFlinging) {
                this.readjustScrollToMiddleItem();
            }
        }
    }
    
    private void positionItems(int i) {
        if (this.getChildCount() > 0) {
            this.mDisplayOffset += i;
            int mDisplayOffset = this.mDisplayOffset;
            View child;
            int measuredWidth;
            for (i = 0; i < this.getChildCount(); ++i) {
                child = this.getChildAt(i);
                measuredWidth = child.getMeasuredWidth();
                child.layout(mDisplayOffset, 0, mDisplayOffset + measuredWidth, child.getMeasuredHeight());
                mDisplayOffset += measuredWidth + child.getPaddingRight();
            }
        }
    }
    
    private void readjustScrollToMiddleItem() {
        if (this.mSnappingToCenter) {
            final View middleItem = this.getMiddleItem();
            if (middleItem != null) {
                this.mSelectedIndex = this.getMiddlePositon();
                final int n = this.getWidth() / 2;
                final int measuredWidth = middleItem.getMeasuredWidth();
                final int left = middleItem.getLeft();
                final int n2 = measuredWidth / 2;
                if (this.mAdjustAnimation != null) {
                    this.mAdjustAnimation.stop();
                }
                this.setAnimation((Animation)(this.mAdjustAnimation = new AdjustPositionAnimation(left + n2 - n)));
                this.startAnimation((Animation)this.mAdjustAnimation);
            }
        }
    }
    
    private void removeNonVisibleItems(final int n) {
        for (View view = this.getChildAt(0); view != null && view.getRight() + n <= 0; view = this.getChildAt(0)) {
            this.mDisplayOffset += view.getMeasuredWidth();
            this.mRemovedViewQueue.offer(view);
            this.removeViewInLayout(view);
            ++this.mLeftViewIndex;
        }
        for (View view2 = this.getChildAt(this.getChildCount() - 1); view2 != null && view2.getLeft() + n >= this.getWidth(); view2 = this.getChildAt(this.getChildCount() - 1)) {
            this.mRemovedViewQueue.offer(view2);
            this.removeViewInLayout(view2);
            --this.mRightViewIndex;
        }
    }
    
    private void reset() {
        synchronized (this) {
            this.initView();
            this.removeAllViewsInLayout();
            this.adjustSubviewPositions();
        }
    }
    
    private void scrollerFinished() {
        if (!this.mFingerDown) {
            this.readjustScrollToMiddleItem();
        }
    }
    
    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        final boolean b = super.dispatchTouchEvent(motionEvent) | this.mGesture.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            default: {
                return b;
            }
            case 2: {
                synchronized (this) {
                    final float x = motionEvent.getX(0);
                    final float n = this.mOldX - x;
                    this.mOldX = x;
                    final int round = Math.round(this.mOldScrollSubPixelRemain + n);
                    this.mOldScrollSubPixelRemain = n + this.mOldScrollSubPixelRemain - round;
                    this.mNextX += round;
                    // monitorexit(this)
                    this.adjustSubviewPositions();
                    return true;
                }
            }
            case 1:
            case 3: {
                this.mFingerDown = false;
                this.onUp();
                return b;
            }
            case 0: {
                this.mFingerDown = true;
                this.mOldX = motionEvent.getX(0);
                return b;
            }
        }
    }
    
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }
    
    public long getSelectedItemId() {
        long n;
        if ((n = this.getMiddlePositon()) == -1L) {
            n = Long.MIN_VALUE;
        }
        return n;
    }
    
    public int getSelectedItemPosition() {
        int middlePositon;
        if ((middlePositon = this.getMiddlePositon()) == -1) {
            middlePositon = -1;
        }
        return middlePositon;
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
    
    protected boolean onDown(final MotionEvent motionEvent) {
        synchronized (this) {
            this.mOldScrollSubPixelRemain = 0.0f;
            this.mScroller.forceFinished(true);
            if (this.mAdjustAnimation != null) {
                this.mAdjustAnimation.stop();
            }
            return true;
        }
    }
    
    protected boolean onFling(final MotionEvent motionEvent, final MotionEvent motionEvent2, final float n, final float n2) {
    Label_0069_Outer:
        while (true) {
            while (true) {
            Label_0101:
                while (true) {
                    Label_0098: {
                        synchronized (this) {
                            this.mFlinging = true;
                            final Scroller mScroller = this.mScroller;
                            final int mNextX = this.mNextX;
                            final int n3 = (int)(-n);
                            if (this.mCircleScrolling) {
                                final int mMinX = Integer.MIN_VALUE;
                                break Label_0098;
                            }
                            final int mMinX = this.mMinX;
                            break Label_0098;
                            // iftrue(Label_0060:, !this.mCircleScrolling)
                            Block_4: {
                                break Block_4;
                                Label_0060: {
                                    final int mMaxX = this.mMaxX;
                                }
                                break Label_0101;
                            }
                            final int mMaxX = Integer.MAX_VALUE;
                            break Label_0101;
                            final Scroller scroller;
                            scroller.fling(mNextX, 0, n3, 0, mMinX, mMaxX, 0, 0);
                            // monitorexit(this)
                            this.adjustSubviewPositions();
                            return true;
                        }
                    }
                    continue Label_0069_Outer;
                }
                continue;
            }
        }
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        synchronized (this) {
            super.onLayout(b, n, n2, n3, n4);
        }
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.setSelection(this.mSelectedIndex);
        this.adjustSubviewPositions();
    }
    
    public void scrollTo(final int n) {
        synchronized (this) {
            this.mScroller.startScroll(this.mCurrentX, 0, n - this.mCurrentX, 0, 500);
            this.adjustSubviewPositions();
            this.invalidate();
        }
    }
    
    public void setAdapter(final ListAdapter mAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
        }
        (this.mAdapter = mAdapter).registerDataSetObserver(this.mDataObserver);
        this.reset();
    }
    
    public void setCircleScrolling(final boolean mCircleScrolling) {
        this.mCircleScrolling = mCircleScrolling;
        this.adjustSubviewPositions();
    }
    
    public void setOnItemClickListener(final AdapterView.OnItemClickListener mOnItemClicked) {
        this.mOnItemClicked = mOnItemClicked;
    }
    
    public void setOnItemLongClickListener(final AdapterView.OnItemLongClickListener mOnItemLongClicked) {
        this.mOnItemLongClicked = mOnItemLongClicked;
    }
    
    public void setOnItemSelectedListener(final AdapterView.OnItemSelectedListener mOnItemSelected) {
        this.mOnItemSelected = mOnItemSelected;
    }
    
    public void setSelection(final int mSelectedIndex) {
        // monitorenter(this)
        Label_0192: {
            if (mSelectedIndex < 0) {
                break Label_0192;
            }
            while (true) {
                try {
                    if (mSelectedIndex < this.mAdapter.getCount()) {
                        for (int i = this.getChildCount() - 1; i >= 0; --i) {
                            final View child = this.getChildAt(i);
                            this.mRemovedViewQueue.offer(child);
                            this.removeViewInLayout(child);
                        }
                        this.mLeftViewIndex = mSelectedIndex - 1;
                        this.mRightViewIndex = mSelectedIndex + 1;
                        final View view = this.mAdapter.getView(mSelectedIndex, (View)this.mRemovedViewQueue.poll(), (ViewGroup)this);
                        if (view != null) {
                            this.mSelectedIndex = mSelectedIndex;
                            this.addAndMeasureChild(view, 0);
                            final int measuredWidth = view.getMeasuredWidth();
                            final int n = this.getWidth() / 2;
                            final int mDisplayOffset = n - measuredWidth / 2;
                            final int n2 = measuredWidth / 2;
                            this.fillListLeft(this.mDisplayOffset = mDisplayOffset, 0);
                            this.fillListRight(n + n2, 0);
                            this.positionItems(0);
                            this.mCurrentX = measuredWidth * mSelectedIndex;
                            this.mNextX = this.mCurrentX;
                            this.adjustSubviewPositions();
                        }
                    }
                    // monitorexit(this)
                    return;
                    // monitorexit(this)
                    throw;
                }
                finally {
                    continue;
                }
                break;
            }
        }
    }
    
    public void setSnappingToCenter(final boolean mSnappingToCenter) {
        this.mSnappingToCenter = mSnappingToCenter;
        this.adjustSubviewPositions();
    }
    
    private class AdjustPositionAnimation extends Animation
    {
        private int mAlreadyScrolled;
        private boolean mCancelled;
        private int mPixelsToScrollLeft;
        
        public AdjustPositionAnimation(final int mPixelsToScrollLeft) {
            this.mCancelled = false;
            this.mAlreadyScrolled = 0;
            this.mPixelsToScrollLeft = mPixelsToScrollLeft;
            this.setInterpolator((Interpolator)new AccelerateDecelerateInterpolator());
            this.setDuration(200L);
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            if (!this.mCancelled) {
                final int n2 = (int)(n * this.mPixelsToScrollLeft) - this.mAlreadyScrolled;
                this.mAlreadyScrolled += n2;
                final HorizontalListView this.0 = HorizontalListView.this;
                this.0.mNextX += n2;
                HorizontalListView.this.adjustSubviewPositions();
            }
        }
        
        public void stop() {
            this.mCancelled = true;
        }
    }
}
