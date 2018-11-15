/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.Scroller
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId = -1;
    PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new Runnable(){

        @Override
        public void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    };
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private EdgeEffect mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffect mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            @Override
            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return f * f * f * f * (f -= 1.0f) + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public ViewPager(@NonNull Context context) {
        super(context);
        this.initViewPager();
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initViewPager();
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int n, ItemInfo itemInfo2) {
        int n2;
        int n3;
        float f;
        float f2;
        int n4 = this.mAdapter.getCount();
        int n5 = this.getClientWidth();
        float f3 = n5 > 0 ? (float)this.mPageMargin / (float)n5 : 0.0f;
        if (itemInfo2 != null) {
            n5 = itemInfo2.position;
            if (n5 < itemInfo.position) {
                f = itemInfo2.offset + itemInfo2.widthFactor + f3;
                ++n5;
                n3 = 0;
                while (n5 <= itemInfo.position && n3 < this.mItems.size()) {
                    itemInfo2 = this.mItems.get(n3);
                    do {
                        n2 = n5;
                        f2 = f;
                        if (n5 <= itemInfo2.position) break;
                        n2 = n5;
                        f2 = f;
                        if (n3 >= this.mItems.size() - 1) break;
                        itemInfo2 = this.mItems.get(++n3);
                    } while (true);
                    while (n2 < itemInfo2.position) {
                        f2 += this.mAdapter.getPageWidth(n2) + f3;
                        ++n2;
                    }
                    itemInfo2.offset = f2;
                    f = f2 + (itemInfo2.widthFactor + f3);
                    n5 = n2 + 1;
                }
            } else if (n5 > itemInfo.position) {
                n3 = this.mItems.size() - 1;
                f = itemInfo2.offset;
                --n5;
                while (n5 >= itemInfo.position && n3 >= 0) {
                    itemInfo2 = this.mItems.get(n3);
                    do {
                        n2 = n5;
                        f2 = f;
                        if (n5 >= itemInfo2.position) break;
                        n2 = n5;
                        f2 = f;
                        if (n3 <= 0) break;
                        itemInfo2 = this.mItems.get(--n3);
                    } while (true);
                    while (n2 > itemInfo2.position) {
                        f2 -= this.mAdapter.getPageWidth(n2) + f3;
                        --n2;
                    }
                    itemInfo2.offset = f = f2 - (itemInfo2.widthFactor + f3);
                    n5 = n2 - 1;
                }
            }
        }
        n2 = this.mItems.size();
        f2 = itemInfo.offset;
        n5 = itemInfo.position - 1;
        f = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f;
        n3 = itemInfo.position;
        f = n3 == --n4 ? itemInfo.offset + itemInfo.widthFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f;
        n3 = n - 1;
        f = f2;
        while (n3 >= 0) {
            itemInfo2 = this.mItems.get(n3);
            while (n5 > itemInfo2.position) {
                f -= this.mAdapter.getPageWidth(n5) + f3;
                --n5;
            }
            itemInfo2.offset = f -= itemInfo2.widthFactor + f3;
            if (itemInfo2.position == 0) {
                this.mFirstOffset = f;
            }
            --n3;
            --n5;
        }
        f = itemInfo.offset + itemInfo.widthFactor + f3;
        n3 = itemInfo.position + 1;
        n5 = n + 1;
        n = n3;
        while (n5 < n2) {
            itemInfo = this.mItems.get(n5);
            while (n < itemInfo.position) {
                f += this.mAdapter.getPageWidth(n) + f3;
                ++n;
            }
            if (itemInfo.position == n4) {
                this.mLastOffset = itemInfo.widthFactor + f - 1.0f;
            }
            itemInfo.offset = f;
            f += itemInfo.widthFactor + f3;
            ++n5;
            ++n;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    private void completeScroll(boolean bl) {
        int n;
        int n2;
        int n3 = this.mScrollState == 2 ? 1 : 0;
        if (n3 != 0) {
            this.setScrollingCacheEnabled(false);
            if (this.mScroller.isFinished() ^ true) {
                this.mScroller.abortAnimation();
                n = this.getScrollX();
                n2 = this.getScrollY();
                int n4 = this.mScroller.getCurrX();
                int n5 = this.mScroller.getCurrY();
                if (n != n4 || n2 != n5) {
                    this.scrollTo(n4, n5);
                    if (n4 != n) {
                        this.pageScrolled(n4);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        n2 = 0;
        n = n3;
        for (n3 = n2; n3 < this.mItems.size(); ++n3) {
            ItemInfo itemInfo = this.mItems.get(n3);
            if (!itemInfo.scrolling) continue;
            itemInfo.scrolling = false;
            n = 1;
        }
        if (n != 0) {
            if (bl) {
                ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
                return;
            }
            this.mEndScrollRunnable.run();
        }
    }

    private int determineTargetPage(int n, float f, int n2, int n3) {
        if (Math.abs(n3) > this.mFlingDistance && Math.abs(n2) > this.mMinimumVelocity) {
            if (n2 <= 0) {
                ++n;
            }
        } else {
            float f2 = n >= this.mCurItem ? 0.4f : 0.6f;
            n += (int)(f + f2);
        }
        n2 = n;
        if (this.mItems.size() > 0) {
            ItemInfo itemInfo = this.mItems.get(0);
            ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
            n2 = Math.max(itemInfo.position, Math.min(n, itemInfo2.position));
        }
        return n2;
    }

    private void dispatchOnPageScrolled(int n, float f, int n2) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(n, f, n2);
        }
        if (this.mOnPageChangeListeners != null) {
            int n3 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n3; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageScrolled(n, f, n2);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(n, f, n2);
        }
    }

    private void dispatchOnPageSelected(int n) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(n);
        }
        if (this.mOnPageChangeListeners != null) {
            int n2 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n2; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageSelected(n);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(n);
        }
    }

    private void dispatchOnScrollStateChanged(int n) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(n);
        }
        if (this.mOnPageChangeListeners != null) {
            int n2 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n2; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageScrollStateChanged(n);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged(n);
        }
    }

    private void enableLayers(boolean bl) {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            int n2 = bl ? this.mPageTransformerLayerType : 0;
            this.getChildAt(i).setLayerType(n2, null);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        for (rect = view.getParent(); rect instanceof ViewGroup && rect != this; rect = rect.getParent()) {
            rect = (ViewGroup)rect;
            rect2.left += rect.getLeft();
            rect2.right += rect.getRight();
            rect2.top += rect.getTop();
            rect2.bottom += rect.getBottom();
        }
        return rect2;
    }

    private int getClientWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    private ItemInfo infoForCurrentScrollPosition() {
        float f;
        int n = this.getClientWidth();
        float f2 = n > 0 ? (float)this.getScrollX() / (float)n : 0.0f;
        float f3 = n > 0 ? (float)this.mPageMargin / (float)n : 0.0f;
        float f4 = f = 0.0f;
        n = 0;
        int n2 = -1;
        ItemInfo itemInfo = null;
        boolean bl = true;
        while (n < this.mItems.size()) {
            ItemInfo itemInfo2 = this.mItems.get(n);
            int n3 = n;
            ItemInfo itemInfo3 = itemInfo2;
            if (!bl) {
                int n4 = itemInfo2.position;
                n3 = n;
                itemInfo3 = itemInfo2;
                if (n4 != ++n2) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f + f4 + f3;
                    itemInfo3.position = n2;
                    itemInfo3.widthFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                    n3 = n - 1;
                }
            }
            f = itemInfo3.offset;
            f4 = itemInfo3.widthFactor;
            if (!bl && f2 < f) {
                return itemInfo;
            }
            if (f2 >= f4 + f + f3) {
                if (n3 == this.mItems.size() - 1) {
                    return itemInfo3;
                }
                n2 = itemInfo3.position;
                f4 = itemInfo3.widthFactor;
                n = n3 + 1;
                bl = false;
                itemInfo = itemInfo3;
                continue;
            }
            return itemInfo3;
        }
        return itemInfo;
    }

    private static boolean isDecorView(@NonNull View view) {
        if (view.getClass().getAnnotation(DecorView.class) != null) {
            return true;
        }
        return false;
    }

    private boolean isGutterDrag(float f, float f2) {
        if (f < (float)this.mGutterSize && f2 > 0.0f || f > (float)(this.getWidth() - this.mGutterSize) && f2 < 0.0f) {
            return true;
        }
        return false;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(n);
            this.mActivePointerId = motionEvent.getPointerId(n);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int n) {
        if (this.mItems.size() == 0) {
            if (this.mFirstLayout) {
                return false;
            }
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0f, 0);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            return false;
        }
        ItemInfo itemInfo = this.infoForCurrentScrollPosition();
        int n2 = this.getClientWidth();
        int n3 = this.mPageMargin;
        float f = this.mPageMargin;
        float f2 = n2;
        int n4 = itemInfo.position;
        f2 = ((float)n / f2 - itemInfo.offset) / (itemInfo.widthFactor + (f /= f2));
        n = (int)((float)(n3 + n2) * f2);
        this.mCalledSuper = false;
        this.onPageScrolled(n4, f2, n);
        if (!this.mCalledSuper) {
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        return true;
    }

    private boolean performDrag(float f) {
        boolean bl;
        int n;
        float f2 = this.mLastMotionX;
        this.mLastMotionX = f;
        float f3 = (float)this.getScrollX() + (f2 - f);
        float f4 = this.getClientWidth();
        f = this.mFirstOffset * f4;
        f2 = this.mLastOffset * f4;
        Object object = this.mItems;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        object = object.get(0);
        ItemInfo itemInfo = this.mItems.get(this.mItems.size() - 1);
        if (object.position != 0) {
            f = object.offset * f4;
            n = 0;
        } else {
            n = 1;
        }
        if (itemInfo.position != this.mAdapter.getCount() - 1) {
            f2 = itemInfo.offset * f4;
            bl = false;
        } else {
            bl = true;
        }
        if (f3 < f) {
            if (n != 0) {
                this.mLeftEdge.onPull(Math.abs(f - f3) / f4);
                bl4 = true;
            }
        } else {
            bl4 = bl3;
            f = f3;
            if (f3 > f2) {
                bl4 = bl2;
                if (bl) {
                    this.mRightEdge.onPull(Math.abs(f3 - f2) / f4);
                    bl4 = true;
                }
                f = f2;
            }
        }
        f2 = this.mLastMotionX;
        n = (int)f;
        this.mLastMotionX = f2 + (f - (float)n);
        this.scrollTo(n, this.getScrollY());
        this.pageScrolled(n);
        return bl4;
    }

    private void recomputeScrollPosition(int n, int n2, int n3, int n4) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(this.getCurrentItem() * this.getClientWidth());
                return;
            }
            int n5 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = this.getPaddingLeft();
            int n8 = this.getPaddingRight();
            this.scrollTo((int)((float)this.getScrollX() / (float)(n2 - n7 - n8 + n4) * (float)(n - n5 - n6 + n3)), this.getScrollY());
            return;
        }
        ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
        float f = itemInfo != null ? Math.min(itemInfo.offset, this.mLastOffset) : 0.0f;
        if ((n = (int)(f * (float)(n - this.getPaddingLeft() - this.getPaddingRight()))) != this.getScrollX()) {
            this.completeScroll(false);
            this.scrollTo(n, this.getScrollY());
        }
    }

    private void removeNonDecorViews() {
        int n = 0;
        while (n < this.getChildCount()) {
            int n2 = n;
            if (!((LayoutParams)this.getChildAt((int)n).getLayoutParams()).isDecor) {
                this.removeViewAt(n);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        this.endDrag();
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        if (!this.mLeftEdge.isFinished() && !this.mRightEdge.isFinished()) {
            return false;
        }
        return true;
    }

    private void scrollToItem(int n, boolean bl, int n2, boolean bl2) {
        ItemInfo itemInfo = this.infoForPosition(n);
        int n3 = itemInfo != null ? (int)((float)this.getClientWidth() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset))) : 0;
        if (bl) {
            this.smoothScrollTo(n3, 0, n2);
            if (bl2) {
                this.dispatchOnPageSelected(n);
                return;
            }
        } else {
            if (bl2) {
                this.dispatchOnPageSelected(n);
            }
            this.completeScroll(false);
            this.scrollTo(n3, 0);
            this.pageScrolled(n3);
        }
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled != bl) {
            this.mScrollingCacheEnabled = bl;
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                this.mDrawingOrderedChildren.add(view);
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3 = arrayList.size();
        int n4 = this.getDescendantFocusability();
        if (n4 != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        if (n4 != 262144 || n3 == arrayList.size()) {
            if (!this.isFocusable()) {
                return;
            }
            if ((n2 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode()) {
                return;
            }
            if (arrayList != null) {
                arrayList.add((View)this);
            }
        }
    }

    ItemInfo addNewItem(int n, int n2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n;
        itemInfo.object = this.mAdapter.instantiateItem(this, n);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(n);
        if (n2 >= 0 && n2 < this.mItems.size()) {
            this.mItems.add(n2, itemInfo);
            return itemInfo;
        }
        this.mItems.add(itemInfo);
        return itemInfo;
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>();
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
            view.addTouchables(arrayList);
        }
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        layoutParams.isDecor |= ViewPager.isDecorView(view);
        if (this.mInLayout) {
            if (layoutParams != null && layoutParams.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams.needsMeasure = true;
            this.addViewInLayout(view, n, layoutParams2);
            return;
        }
        super.addView(view, n, layoutParams2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean arrowScroll(int var1_1) {
        block15 : {
            block13 : {
                block14 : {
                    var7_2 = this.findFocus();
                    var4_3 = false;
                    var6_4 = null;
                    if (var7_2 != this) break block14;
                    var5_5 = var6_4;
                    break block15;
                }
                if (var7_2 == null) ** GOTO lbl-1000
                var5_5 = var7_2.getParent();
                while (var5_5 instanceof ViewGroup) {
                    if (var5_5 == this) {
                        var2_6 = 1;
                        break block13;
                    }
                    var5_5 = var5_5.getParent();
                }
                var2_6 = 0;
            }
            if (var2_6 == 0) {
                var8_7 = new StringBuilder();
                var8_7.append(var7_2.getClass().getSimpleName());
                var5_5 = var7_2.getParent();
                while (var5_5 instanceof ViewGroup) {
                    var8_7.append(" => ");
                    var8_7.append(var5_5.getClass().getSimpleName());
                    var5_5 = var5_5.getParent();
                }
                var5_5 = new StringBuilder();
                var5_5.append("arrowScroll tried to find focus based on non-child current focused view ");
                var5_5.append(var8_7.toString());
                Log.e((String)"ViewPager", (String)var5_5.toString());
                var5_5 = var6_4;
            } else lbl-1000: // 2 sources:
            {
                var5_5 = var7_2;
            }
        }
        var6_4 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)var5_5, var1_1);
        if (var6_4 != null && var6_4 != var5_5) {
            if (var1_1 == 17) {
                var2_6 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_4).left;
                var3_8 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_5).left;
                var4_3 = var5_5 != null && var2_6 >= var3_8 ? this.pageLeft() : var6_4.requestFocus();
            } else if (var1_1 == 66) {
                var2_6 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_4).left;
                var3_9 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_5).left;
                var4_3 = var5_5 != null && var2_6 <= var3_9 ? this.pageRight() : var6_4.requestFocus();
            }
        } else if (var1_1 != 17 && var1_1 != 1) {
            if (var1_1 == 66 || var1_1 == 2) {
                var4_3 = this.pageRight();
            }
        } else {
            var4_3 = this.pageLeft();
        }
        if (var4_3 == false) return var4_3;
        this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1_1));
        return var4_3;
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        this.setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)0, (float)0.0f, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.recycle();
        this.mFakeDragBeginTime = l;
        return true;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n6;
                int n7 = n2 + n4;
                View view2 = viewGroup.getChildAt(i);
                if (n7 < view2.getLeft() || n7 >= view2.getRight() || (n6 = n3 + n5) < view2.getTop() || n6 >= view2.getBottom() || !this.canScroll(view2, true, n, n7 - view2.getLeft(), n6 - view2.getTop())) continue;
                return true;
            }
        }
        if (bl && view.canScrollHorizontally(- n)) {
            return true;
        }
        return false;
    }

    public boolean canScrollHorizontally(int n) {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean bl = false;
        boolean bl2 = false;
        if (pagerAdapter == null) {
            return false;
        }
        int n2 = this.getClientWidth();
        int n3 = this.getScrollX();
        if (n < 0) {
            if (n3 > (int)((float)n2 * this.mFirstOffset)) {
                bl2 = true;
            }
            return bl2;
        }
        if (n > 0) {
            bl2 = bl;
            if (n3 < (int)((float)n2 * this.mLastOffset)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams)) {
            return true;
        }
        return false;
    }

    public void clearOnPageChangeListeners() {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear();
        }
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
                if (!this.pageScrolled(n3)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(0, n4);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void dataSetChanged() {
        int n;
        int n2;
        this.mExpectedAdapterCount = n2 = this.mAdapter.getCount();
        int n3 = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < n2 ? 1 : 0;
        int n4 = this.mCurItem;
        int n5 = n3;
        n3 = n4;
        n4 = n = 0;
        while (n < this.mItems.size()) {
            int n6;
            int n7;
            int n8;
            block12 : {
                block14 : {
                    ItemInfo itemInfo;
                    int n9;
                    block13 : {
                        block11 : {
                            itemInfo = this.mItems.get(n);
                            n9 = this.mAdapter.getItemPosition(itemInfo.object);
                            if (n9 != -1) break block11;
                            n6 = n;
                            n8 = n4;
                            n7 = n3;
                            break block12;
                        }
                        if (n9 != -2) break block13;
                        this.mItems.remove(n);
                        n8 = n - 1;
                        n6 = n4;
                        if (n4 == 0) {
                            this.mAdapter.startUpdate(this);
                            n6 = 1;
                        }
                        this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                        n = n8;
                        n4 = n6;
                        if (this.mCurItem == itemInfo.position) {
                            n3 = Math.max(0, Math.min(this.mCurItem, n2 - 1));
                            n4 = n6;
                            n = n8;
                        }
                        break block14;
                    }
                    n6 = n;
                    n8 = n4;
                    n7 = n3;
                    if (itemInfo.position == n9) break block12;
                    if (itemInfo.position == this.mCurItem) {
                        n3 = n9;
                    }
                    itemInfo.position = n9;
                }
                n5 = 1;
                n6 = n;
                n8 = n4;
                n7 = n3;
            }
            n = n6 + 1;
            n4 = n8;
            n3 = n7;
        }
        if (n4 != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (n5 != 0) {
            n = this.getChildCount();
            for (n4 = 0; n4 < n; ++n4) {
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(n4).getLayoutParams();
                if (layoutParams.isDecor) continue;
                layoutParams.widthFactor = 0.0f;
            }
            this.setCurrentItemInternal(n3, false, true);
            this.requestLayout();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!super.dispatchKeyEvent(keyEvent) && !this.executeKeyEvent(keyEvent)) {
            return false;
        }
        return true;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            return true;
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n = this.getOverScrollMode();
        int n2 = 0;
        int n3 = 0;
        if (n != 0 && (n != 1 || this.mAdapter == null || this.mAdapter.getCount() <= 1)) {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        } else {
            if (!this.mLeftEdge.isFinished()) {
                n2 = canvas.save();
                n3 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                n = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float)(- n3 + this.getPaddingTop()), this.mFirstOffset * (float)n);
                this.mLeftEdge.setSize(n3, n);
                n3 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(n2);
            }
            n2 = n3;
            if (!this.mRightEdge.isFinished()) {
                n = canvas.save();
                n2 = this.getWidth();
                int n4 = this.getHeight();
                int n5 = this.getPaddingTop();
                int n6 = this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float)(- this.getPaddingTop()), (- this.mLastOffset + 1.0f) * (float)n2);
                this.mRightEdge.setSize(n4 - n5 - n6, n2);
                n2 = n3 | this.mRightEdge.draw(canvas);
                canvas.restoreToCount(n);
            }
        }
        if (n2 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(this.getDrawableState());
        }
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter != null) {
            Object object = this.mVelocityTracker;
            object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
            int n = (int)object.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int n2 = this.getClientWidth();
            int n3 = this.getScrollX();
            object = this.infoForCurrentScrollPosition();
            this.setCurrentItemInternal(this.determineTargetPage(object.position, ((float)n3 / (float)n2 - object.offset) / object.widthFactor, n, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, n);
        }
        this.endDrag();
        this.mFakeDragging = false;
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        block8 : {
            block9 : {
                if (keyEvent.getAction() != 0) break block8;
                int n = keyEvent.getKeyCode();
                if (n == 61) break block9;
                switch (n) {
                    default: {
                        break block8;
                    }
                    case 22: {
                        if (keyEvent.hasModifiers(2)) {
                            return this.pageRight();
                        }
                        return this.arrowScroll(66);
                    }
                    case 21: {
                        if (keyEvent.hasModifiers(2)) {
                            return this.pageLeft();
                        }
                        return this.arrowScroll(17);
                    }
                }
            }
            if (keyEvent.hasNoModifiers()) {
                return this.arrowScroll(2);
            }
            if (keyEvent.hasModifiers(1)) {
                return this.arrowScroll(1);
            }
        }
        return false;
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter == null) {
            return;
        }
        this.mLastMotionX += f;
        float f2 = (float)this.getScrollX() - f;
        float f3 = this.getClientWidth();
        f = this.mFirstOffset * f3;
        float f4 = this.mLastOffset * f3;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            f = itemInfo.offset * f3;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f4 = itemInfo2.offset * f3;
        }
        if (f2 >= f) {
            f = f2;
            if (f2 > f4) {
                f = f4;
            }
        }
        f4 = this.mLastMotionX;
        int n = (int)f;
        this.mLastMotionX = f4 + (f - (float)n);
        this.scrollTo(n, this.getScrollY());
        this.pageScrolled(n);
        long l = SystemClock.uptimeMillis();
        itemInfo = MotionEvent.obtain((long)this.mFakeDragBeginTime, (long)l, (int)2, (float)this.mLastMotionX, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement((MotionEvent)itemInfo);
        itemInfo.recycle();
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    @Nullable
    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    protected int getChildDrawingOrder(int n, int n2) {
        int n3 = n2;
        if (this.mDrawingOrder == 2) {
            n3 = n - 1 - n2;
        }
        return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n3).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent != null && viewParent instanceof View) {
                view = (View)viewParent;
                continue;
            }
            return null;
        }
        return this.infoForChild(view);
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    ItemInfo infoForPosition(int n) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.position != n) continue;
            return itemInfo;
        }
        return null;
    }

    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int)(400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int)(25.0f * f);
        this.mCloseEnough = (int)(2.0f * f);
        this.mDefaultGutterSize = (int)(16.0f * f);
        ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){
            private final Rect mTempRect = new Rect();

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                if ((object = ViewCompat.onApplyWindowInsets((View)object, windowInsetsCompat)).isConsumed()) {
                    return object;
                }
                windowInsetsCompat = this.mTempRect;
                windowInsetsCompat.left = object.getSystemWindowInsetLeft();
                windowInsetsCompat.top = object.getSystemWindowInsetTop();
                windowInsetsCompat.right = object.getSystemWindowInsetRight();
                windowInsetsCompat.bottom = object.getSystemWindowInsetBottom();
                int n = ViewPager.this.getChildCount();
                for (int i = 0; i < n; ++i) {
                    WindowInsetsCompat windowInsetsCompat2 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), (WindowInsetsCompat)object);
                    windowInsetsCompat.left = Math.min(windowInsetsCompat2.getSystemWindowInsetLeft(), windowInsetsCompat.left);
                    windowInsetsCompat.top = Math.min(windowInsetsCompat2.getSystemWindowInsetTop(), windowInsetsCompat.top);
                    windowInsetsCompat.right = Math.min(windowInsetsCompat2.getSystemWindowInsetRight(), windowInsetsCompat.right);
                    windowInsetsCompat.bottom = Math.min(windowInsetsCompat2.getSystemWindowInsetBottom(), windowInsetsCompat.bottom);
                }
                return object.replaceSystemWindowInsets(windowInsetsCompat.left, windowInsetsCompat.top, windowInsetsCompat.right, windowInsetsCompat.bottom);
            }
        });
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        if (this.mScroller != null && !this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int n = this.getScrollX();
            int n2 = this.getWidth();
            float f = this.mPageMargin;
            float f2 = n2;
            float f3 = f / f2;
            Object object = this.mItems;
            int n3 = 0;
            object = object.get(0);
            f = object.offset;
            int n4 = this.mItems.size();
            int n5 = this.mItems.get((int)(n4 - 1)).position;
            for (int i = object.position; i < n5; ++i) {
                float f4;
                float f5;
                while (i > object.position && n3 < n4) {
                    object = this.mItems;
                    object = object.get(++n3);
                }
                if (i == object.position) {
                    f5 = object.offset;
                    float f6 = object.widthFactor;
                    f = object.offset;
                    f4 = object.widthFactor;
                    f5 = (f5 + f6) * f2;
                    f = f + f4 + f3;
                } else {
                    f4 = this.mAdapter.getPageWidth(i);
                    f5 = (f + f4) * f2;
                    f += f4 + f3;
                }
                if ((float)this.mPageMargin + f5 > (float)n) {
                    this.mMarginDrawable.setBounds(Math.round(f5), this.mTopPageBounds, Math.round((float)this.mPageMargin + f5), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f5 <= (float)(n + n2)) continue;
                return;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction() & 255;
        if (n != 3 && n != 1) {
            if (n != 0) {
                if (this.mIsBeingDragged) {
                    return true;
                }
                if (this.mIsUnableToDrag) {
                    return false;
                }
            }
            if (n != 0) {
                if (n != 2) {
                    if (n == 6) {
                        this.onSecondaryPointerUp(motionEvent);
                    }
                } else {
                    n = this.mActivePointerId;
                    if (n != -1) {
                        n = motionEvent.findPointerIndex(n);
                        float f = motionEvent.getX(n);
                        float f2 = f - this.mLastMotionX;
                        float f3 = Math.abs(f2);
                        float f4 = motionEvent.getY(n);
                        float f5 = Math.abs(f4 - this.mInitialMotionY);
                        if (f2 != 0.0f && !this.isGutterDrag(this.mLastMotionX, f2) && this.canScroll((View)this, false, (int)f2, (int)f, (int)f4)) {
                            this.mLastMotionX = f;
                            this.mLastMotionY = f4;
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                        if (f3 > (float)this.mTouchSlop && f3 * 0.5f > f5) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            this.setScrollState(1);
                            f2 = f2 > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                            this.mLastMotionX = f2;
                            this.mLastMotionY = f4;
                            this.setScrollingCacheEnabled(true);
                        } else if (f5 > (float)this.mTouchSlop) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && this.performDrag(f)) {
                            ViewCompat.postInvalidateOnAnimation((View)this);
                        }
                    }
                }
            } else {
                float f;
                this.mInitialMotionX = f = motionEvent.getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f = motionEvent.getY();
                this.mLastMotionY = f;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mIsScrollStarted = true;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                } else {
                    this.completeScroll(false);
                    this.mIsBeingDragged = false;
                }
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            return this.mIsBeingDragged;
        }
        this.resetTouch();
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams;
        View view;
        int n5;
        int n6 = this.getChildCount();
        int n7 = n3 - n;
        int n8 = n4 - n2;
        n2 = this.getPaddingLeft();
        n = this.getPaddingTop();
        int n9 = this.getPaddingRight();
        n4 = this.getPaddingBottom();
        int n10 = this.getScrollX();
        int n11 = 0;
        for (int i = 0; i < n6; ++i) {
            view = this.getChildAt(i);
            n5 = n2;
            int n12 = n9;
            int n13 = n;
            int n14 = n4;
            n3 = n11;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = n2;
                n12 = n9;
                n13 = n;
                n14 = n4;
                n3 = n11;
                if (layoutParams.isDecor) {
                    n3 = layoutParams.gravity & 7;
                    n14 = layoutParams.gravity & 112;
                    if (n3 != 1) {
                        if (n3 != 3) {
                            if (n3 != 5) {
                                n3 = n2;
                                n5 = n2;
                            } else {
                                n3 = n7 - n9 - view.getMeasuredWidth();
                                n9 += view.getMeasuredWidth();
                                n5 = n2;
                            }
                        } else {
                            n5 = view.getMeasuredWidth();
                            n3 = n2;
                            n5 += n2;
                        }
                    } else {
                        n3 = Math.max((n7 - view.getMeasuredWidth()) / 2, n2);
                        n5 = n2;
                    }
                    if (n14 != 16) {
                        if (n14 != 48) {
                            if (n14 != 80) {
                                n2 = n;
                            } else {
                                n2 = n8 - n4 - view.getMeasuredHeight();
                                n4 += view.getMeasuredHeight();
                            }
                        } else {
                            n14 = view.getMeasuredHeight();
                            n2 = n;
                            n = n14 + n;
                        }
                    } else {
                        n2 = Math.max((n8 - view.getMeasuredHeight()) / 2, n);
                    }
                    view.layout(n3, n2, view.getMeasuredWidth() + (n3 += n10), n2 + view.getMeasuredHeight());
                    n3 = n11 + 1;
                    n14 = n4;
                    n13 = n;
                    n12 = n9;
                }
            }
            n2 = n5;
            n9 = n12;
            n = n13;
            n4 = n14;
            n11 = n3;
        }
        for (n3 = 0; n3 < n6; ++n3) {
            ItemInfo itemInfo;
            view = this.getChildAt(n3);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (itemInfo = this.infoForChild(view)) == null) continue;
            float f = n7 - n2 - n9;
            n5 = (int)(itemInfo.offset * f) + n2;
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)(f * layoutParams.widthFactor)), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n8 - n - n4), (int)1073741824));
            }
            view.layout(n5, n, view.getMeasuredWidth() + n5, view.getMeasuredHeight() + n);
        }
        this.mTopPageBounds = n;
        this.mBottomPageBounds = n8 - n4;
        this.mDecorChildCount = n11;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    protected void onMeasure(int n, int n2) {
        LayoutParams layoutParams;
        View view;
        this.setMeasuredDimension(ViewPager.getDefaultSize((int)0, (int)n), ViewPager.getDefaultSize((int)0, (int)n2));
        n = this.getMeasuredWidth();
        this.mGutterSize = Math.min(n / 10, this.mDefaultGutterSize);
        int n3 = this.getPaddingLeft();
        int n4 = this.getPaddingRight();
        n2 = this.getMeasuredHeight();
        int n5 = this.getPaddingTop();
        int n6 = this.getPaddingBottom();
        int n7 = this.getChildCount();
        n2 = n2 - n5 - n6;
        n = n - n3 - n4;
        n5 = 0;
        do {
            int n8 = 1;
            int n9 = 1073741824;
            if (n5 >= n7) break;
            view = this.getChildAt(n5);
            n3 = n;
            n4 = n2;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = n;
                n4 = n2;
                if (layoutParams != null) {
                    n3 = n;
                    n4 = n2;
                    if (layoutParams.isDecor) {
                        int n10;
                        n3 = layoutParams.gravity & 7;
                        n4 = layoutParams.gravity & 112;
                        boolean bl = n4 == 48 || n4 == 80;
                        n6 = n8;
                        if (n3 != 3) {
                            n6 = n3 == 5 ? n8 : 0;
                        }
                        n4 = Integer.MIN_VALUE;
                        if (bl) {
                            n3 = Integer.MIN_VALUE;
                            n4 = 1073741824;
                        } else {
                            n3 = n6 != 0 ? 1073741824 : Integer.MIN_VALUE;
                        }
                        if (layoutParams.width != -2) {
                            n4 = layoutParams.width != -1 ? layoutParams.width : n;
                            n8 = 1073741824;
                            n10 = n4;
                        } else {
                            n10 = n;
                            n8 = n4;
                        }
                        if (layoutParams.height != -2) {
                            n3 = layoutParams.height != -1 ? layoutParams.height : n2;
                        } else {
                            n4 = n2;
                            n9 = n3;
                            n3 = n4;
                        }
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n10, (int)n8), View.MeasureSpec.makeMeasureSpec((int)n3, (int)n9));
                        if (bl) {
                            n4 = n2 - view.getMeasuredHeight();
                            n3 = n;
                        } else {
                            n3 = n;
                            n4 = n2;
                            if (n6 != 0) {
                                n3 = n - view.getMeasuredWidth();
                                n4 = n2;
                            }
                        }
                    }
                }
            }
            ++n5;
            n = n3;
            n2 = n4;
        } while (true);
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            view = this.getChildAt(n2);
            if (view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) != null && layoutParams.isDecor) continue;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n * layoutParams.widthFactor)), (int)1073741824), this.mChildHeightMeasureSpec);
        }
    }

    @CallSuper
    protected void onPageScrolled(int n, float f, int n2) {
        View view;
        int n3 = this.mDecorChildCount;
        int n4 = 0;
        if (n3 > 0) {
            int n5 = this.getScrollX();
            n3 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = this.getWidth();
            int n8 = this.getChildCount();
            for (int i = 0; i < n8; ++i) {
                view = this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.isDecor) continue;
                int n9 = layoutParams.gravity & 7;
                if (n9 != 1) {
                    int n10;
                    if (n9 != 3) {
                        if (n9 != 5) {
                            n10 = n3;
                            n9 = n3;
                            n3 = n10;
                        } else {
                            n9 = n7 - n6 - view.getMeasuredWidth();
                            n6 += view.getMeasuredWidth();
                        }
                    } else {
                        n10 = view.getWidth() + n3;
                        n9 = n3;
                        n3 = n10;
                    }
                } else {
                    n9 = Math.max((n7 - view.getMeasuredWidth()) / 2, n3);
                }
                n9 = n9 + n5 - view.getLeft();
                if (n9 == 0) continue;
                view.offsetLeftAndRight(n9);
            }
        }
        this.dispatchOnPageScrolled(n, f, n2);
        if (this.mPageTransformer != null) {
            n2 = this.getScrollX();
            n3 = this.getChildCount();
            for (n = n4; n < n3; ++n) {
                view = this.getChildAt(n);
                if (((LayoutParams)view.getLayoutParams()).isDecor) continue;
                f = (float)(view.getLeft() - n2) / (float)this.getClientWidth();
                this.mPageTransformer.transformPage(view, f);
            }
        }
        this.mCalledSuper = true;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3 = this.getChildCount();
        int n4 = -1;
        if ((n & 2) != 0) {
            n4 = n3;
            n3 = 0;
            n2 = 1;
        } else {
            --n3;
            n2 = -1;
        }
        while (n3 != n4) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n, rect)) {
                return true;
            }
            n3 += n2;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(parcelable.adapterState, parcelable.loader);
            this.setCurrentItemInternal(parcelable.position, false, true);
            return;
        }
        this.mRestoredCurItem = parcelable.position;
        this.mRestoredAdapterState = parcelable.adapterState;
        this.mRestoredClassLoader = parcelable.loader;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            this.recomputeScrollPosition(n, n3, this.mPageMargin, this.mPageMargin);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mFakeDragging) {
            return true;
        }
        int n = motionEvent.getAction();
        boolean bl = false;
        if (n == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter != null) {
            if (this.mAdapter.getCount() == 0) {
                return false;
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            switch (motionEvent.getAction() & 255) {
                default: {
                    break;
                }
                case 6: {
                    this.onSecondaryPointerUp(motionEvent);
                    this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                    break;
                }
                case 5: {
                    n = motionEvent.getActionIndex();
                    this.mLastMotionX = motionEvent.getX(n);
                    this.mActivePointerId = motionEvent.getPointerId(n);
                    break;
                }
                case 3: {
                    if (!this.mIsBeingDragged) break;
                    this.scrollToItem(this.mCurItem, true, 0, false);
                    bl = this.resetTouch();
                    break;
                }
                case 2: {
                    if (!this.mIsBeingDragged) {
                        n = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (n == -1) {
                            bl = this.resetTouch();
                            break;
                        }
                        float f = motionEvent.getX(n);
                        float f2 = Math.abs(f - this.mLastMotionX);
                        float f3 = motionEvent.getY(n);
                        float f4 = Math.abs(f3 - this.mLastMotionY);
                        if (f2 > (float)this.mTouchSlop && f2 > f4) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            f = f - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                            this.mLastMotionX = f;
                            this.mLastMotionY = f3;
                            this.setScrollState(1);
                            this.setScrollingCacheEnabled(true);
                            ViewParent viewParent = this.getParent();
                            if (viewParent != null) {
                                viewParent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                    if (!this.mIsBeingDragged) break;
                    bl = false | this.performDrag(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)));
                    break;
                }
                case 1: {
                    if (!this.mIsBeingDragged) break;
                    Object object = this.mVelocityTracker;
                    object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    n = (int)object.getXVelocity(this.mActivePointerId);
                    this.mPopulatePending = true;
                    int n2 = this.getClientWidth();
                    int n3 = this.getScrollX();
                    object = this.infoForCurrentScrollPosition();
                    float f = this.mPageMargin;
                    float f5 = n2;
                    this.setCurrentItemInternal(this.determineTargetPage(object.position, ((float)n3 / f5 - object.offset) / (object.widthFactor + (f /= f5)), n, (int)(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, n);
                    bl = this.resetTouch();
                    break;
                }
                case 0: {
                    float f;
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mInitialMotionX = f = motionEvent.getX();
                    this.mLastMotionX = f;
                    this.mInitialMotionY = f = motionEvent.getY();
                    this.mLastMotionY = f;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                }
            }
            if (bl) {
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
            return true;
        }
        return false;
    }

    boolean pageLeft() {
        if (this.mCurItem > 0) {
            this.setCurrentItem(this.mCurItem - 1, true);
            return true;
        }
        return false;
    }

    boolean pageRight() {
        if (this.mAdapter != null && this.mCurItem < this.mAdapter.getCount() - 1) {
            this.setCurrentItem(this.mCurItem + 1, true);
            return true;
        }
        return false;
    }

    void populate() {
        this.populate(this.mCurItem);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void populate(int var1_1) {
        block29 : {
            block34 : {
                block28 : {
                    if (this.mCurItem != var1_1) {
                        var14_2 = this.infoForPosition(this.mCurItem);
                        this.mCurItem = var1_1;
                    } else {
                        var14_2 = null;
                    }
                    if (this.mAdapter == null) {
                        this.sortChildDrawingOrder();
                        return;
                    }
                    if (this.mPopulatePending) {
                        this.sortChildDrawingOrder();
                        return;
                    }
                    if (this.getWindowToken() == null) {
                        return;
                    }
                    this.mAdapter.startUpdate(this);
                    var1_1 = this.mOffscreenPageLimit;
                    var11_3 = Math.max(0, this.mCurItem - var1_1);
                    var9_4 = this.mAdapter.getCount();
                    var10_5 = Math.min(var9_4 - 1, this.mCurItem + var1_1);
                    if (var9_4 != this.mExpectedAdapterCount) {
                        block27 : {
                            try {
                                var13_6 = this.getResources().getResourceName(this.getId());
                                break block27;
                            }
                            catch (Resources.NotFoundException var13_7) {}
                            var13_8 = Integer.toHexString(this.getId());
                        }
                        var14_2 = new StringBuilder();
                        var14_2.append("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
                        var14_2.append(this.mExpectedAdapterCount);
                        var14_2.append(", found: ");
                        var14_2.append(var9_4);
                        var14_2.append(" Pager id: ");
                        var14_2.append((String)var13_9);
                        var14_2.append(" Pager class: ");
                        var14_2.append(this.getClass());
                        var14_2.append(" Problematic adapter: ");
                        var14_2.append(this.mAdapter.getClass());
                        throw new IllegalStateException(var14_2.toString());
                    }
                    for (var1_1 = 0; var1_1 < this.mItems.size(); ++var1_1) {
                        var13_10 = this.mItems.get(var1_1);
                        if (var13_10.position < this.mCurItem) continue;
                        if (var13_10.position != this.mCurItem) break;
                        break block28;
                    }
                    var13_11 = null;
                }
                var15_47 = var13_12;
                if (var13_12 == null) {
                    var15_47 = var13_12;
                    if (var9_4 > 0) {
                        var15_47 = this.addNewItem(this.mCurItem, var1_1);
                    }
                }
                if (var15_47 == null) break block29;
                var6_48 = var1_1 - 1;
                if (var6_48 >= 0) {
                    var13_13 = this.mItems.get(var6_48);
                } else {
                    var13_14 = null;
                }
                var12_49 = this.getClientWidth();
                var4_50 = var12_49 <= 0 ? 0.0f : 2.0f - var15_47.widthFactor + (float)this.getPaddingLeft() / (float)var12_49;
                var3_52 = 0.0f;
                var5_53 = var1_1;
                for (var8_51 = this.mCurItem - 1; var8_51 >= 0; --var8_51) {
                    block31 : {
                        block32 : {
                            block33 : {
                                block30 : {
                                    if (var3_52 < var4_50 || var8_51 >= var11_3) break block30;
                                    if (var13_16 == null) break;
                                    var2_54 = var3_52;
                                    var1_1 = var6_48;
                                    var16_57 = var13_16;
                                    var7_55 = var5_53;
                                    if (var8_51 != var13_16.position) break block31;
                                    var2_54 = var3_52;
                                    var1_1 = var6_48;
                                    var16_58 = var13_16;
                                    var7_55 = var5_53--;
                                    if (var13_16.scrolling) break block31;
                                    this.mItems.remove(var6_48);
                                    this.mAdapter.destroyItem(this, var8_51, var13_16.object);
                                    var2_54 = var3_52;
                                    var1_1 = --var6_48;
                                    var7_55 = var5_53;
                                    if (var6_48 < 0) ** GOTO lbl-1000
                                    var13_17 = this.mItems.get(var6_48);
                                    var2_54 = var3_52;
                                    var1_1 = var6_48;
                                    break block32;
                                }
                                if (var13_16 == null || var8_51 != var13_16.position) break block33;
                                var2_54 = var3_52 += var13_16.widthFactor;
                                var1_1 = --var6_48;
                                var7_55 = var5_53;
                                if (var6_48 < 0) ** GOTO lbl-1000
                                var13_18 = this.mItems.get(var6_48);
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                                break block32;
                            }
                            var2_54 = var3_52 += this.addNewItem((int)var8_51, (int)(var6_48 + 1)).widthFactor;
                            var1_1 = var6_48;
                            var7_55 = ++var5_53;
                            if (var6_48 >= 0) {
                                var13_19 = this.mItems.get(var6_48);
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                            } else lbl-1000: // 3 sources:
                            {
                                var13_20 = null;
                                var5_53 = var7_55;
                            }
                        }
                        var7_55 = var5_53;
                        var16_59 = var13_21;
                    }
                    var3_52 = var2_54;
                    var6_48 = var1_1;
                    var13_23 = var16_56;
                    var5_53 = var7_55;
                }
                var3_52 = var15_47.widthFactor;
                var6_48 = var5_53 + 1;
                if (var3_52 >= 2.0f) break block34;
                if (var6_48 < this.mItems.size()) {
                    var13_24 = this.mItems.get(var6_48);
                } else {
                    var13_25 = null;
                }
                var4_50 = var12_49 <= 0 ? 0.0f : (float)this.getPaddingRight() / (float)var12_49 + 2.0f;
                var1_1 = this.mCurItem;
                var16_60 = var13_26;
                while ((var7_55 = var1_1 + 1) < var9_4) {
                    block36 : {
                        block37 : {
                            block35 : {
                                if (var3_52 < var4_50 || var7_55 <= var10_5) break block35;
                                if (var16_61 == null) break;
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                                var13_28 = var16_61;
                                if (var7_55 != var16_61.position) break block36;
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                                var13_29 = var16_61;
                                if (var16_61.scrolling) break block36;
                                this.mItems.remove(var6_48);
                                this.mAdapter.destroyItem(this, var7_55, var16_61.object);
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                                if (var6_48 >= this.mItems.size()) ** GOTO lbl-1000
                                var13_30 = this.mItems.get(var6_48);
                                var2_54 = var3_52;
                                var1_1 = var6_48;
                                break block36;
                            }
                            if (var16_61 == null || var7_55 != var16_61.position) break block37;
                            var2_54 = var3_52 += var16_61.widthFactor;
                            var1_1 = ++var6_48;
                            if (var6_48 >= this.mItems.size()) ** GOTO lbl-1000
                            var13_33 = this.mItems.get(var6_48);
                            var2_54 = var3_52;
                            var1_1 = var6_48;
                            break block36;
                        }
                        var13_34 = this.addNewItem(var7_55, var6_48);
                        var2_54 = var3_52 += var13_34.widthFactor;
                        var1_1 = ++var6_48;
                        if (var6_48 < this.mItems.size()) {
                            var13_35 = this.mItems.get(var6_48);
                            var1_1 = var6_48;
                            var2_54 = var3_52;
                        } else lbl-1000: // 3 sources:
                        {
                            var13_32 = null;
                        }
                    }
                    var3_52 = var2_54;
                    var6_48 = var1_1;
                    var16_62 = var13_36;
                    var1_1 = var7_55;
                }
            }
            this.calculatePageOffsets(var15_47, var5_53, (ItemInfo)var14_2);
            this.mAdapter.setPrimaryItem(this, this.mCurItem, var15_47.object);
        }
        this.mAdapter.finishUpdate(this);
        var5_53 = this.getChildCount();
        for (var1_1 = 0; var1_1 < var5_53; ++var1_1) {
            var14_2 = this.getChildAt(var1_1);
            var13_40 = (LayoutParams)var14_2.getLayoutParams();
            var13_40.childIndex = var1_1;
            if (var13_40.isDecor || var13_40.widthFactor != 0.0f || (var14_2 = this.infoForChild((View)var14_2)) == null) continue;
            var13_40.widthFactor = var14_2.widthFactor;
            var13_40.position = var14_2.position;
        }
        this.sortChildDrawingOrder();
        if (this.hasFocus() == false) return;
        var13_41 = this.findFocus();
        if (var13_41 != null) {
            var13_42 = this.infoForAnyChild(var13_41);
        } else {
            var13_43 = null;
        }
        if (var13_44 != null) {
            if (var13_44.position == this.mCurItem) return;
        }
        var1_1 = 0;
        while (var1_1 < this.getChildCount()) {
            var13_46 = this.getChildAt(var1_1);
            var14_2 = this.infoForChild(var13_46);
            if (var14_2 != null && var14_2.position == this.mCurItem && var13_46.requestFocus(2)) {
                return;
            }
            ++var1_1;
        }
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners != null) {
            this.mAdapterChangeListeners.remove(onAdapterChangeListener);
        }
    }

    public void removeOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove(onPageChangeListener);
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    public void setAdapter(@Nullable PagerAdapter pagerAdapter) {
        int n;
        Object object = this.mAdapter;
        int n2 = 0;
        if (object != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate(this);
            for (n = 0; n < this.mItems.size(); ++n) {
                object = this.mItems.get(n);
                this.mAdapter.destroyItem(this, object.position, object.object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        object = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean bl = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if (this.mAdapterChangeListeners != null && !this.mAdapterChangeListeners.isEmpty()) {
            int n3 = this.mAdapterChangeListeners.size();
            for (n = n2; n < n3; ++n) {
                this.mAdapterChangeListeners.get(n).onAdapterChanged(this, (PagerAdapter)object, pagerAdapter);
            }
        }
    }

    public void setCurrentItem(int n) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, this.mFirstLayout ^ true, false);
    }

    public void setCurrentItem(int n, boolean bl) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, bl, false);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2) {
        this.setCurrentItemInternal(n, bl, bl2, 0);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2, int n2) {
        if (this.mAdapter != null && this.mAdapter.getCount() > 0) {
            int n3;
            if (!bl2 && this.mCurItem == n && this.mItems.size() != 0) {
                this.setScrollingCacheEnabled(false);
                return;
            }
            bl2 = true;
            if (n < 0) {
                n3 = 0;
            } else {
                n3 = n;
                if (n >= this.mAdapter.getCount()) {
                    n3 = this.mAdapter.getCount() - 1;
                }
            }
            n = this.mOffscreenPageLimit;
            if (n3 > this.mCurItem + n || n3 < this.mCurItem - n) {
                for (n = 0; n < this.mItems.size(); ++n) {
                    this.mItems.get((int)n).scrolling = true;
                }
            }
            if (this.mCurItem == n3) {
                bl2 = false;
            }
            if (this.mFirstLayout) {
                this.mCurItem = n3;
                if (bl2) {
                    this.dispatchOnPageSelected(n3);
                }
                this.requestLayout();
                return;
            }
            this.populate(n3);
            this.scrollToItem(n3, bl, n2, bl2);
            return;
        }
        this.setScrollingCacheEnabled(false);
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n) {
        int n2 = n;
        if (n < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested offscreen page limit ");
            stringBuilder.append(n);
            stringBuilder.append(" too small; defaulting to ");
            stringBuilder.append(1);
            Log.w((String)TAG, (String)stringBuilder.toString());
            n2 = 1;
        }
        if (n2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = n2;
            this.populate();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int n) {
        int n2 = this.mPageMargin;
        this.mPageMargin = n;
        int n3 = this.getWidth();
        this.recomputeScrollPosition(n3, n3, n, n2);
        this.requestLayout();
    }

    public void setPageMarginDrawable(@DrawableRes int n) {
        this.setPageMarginDrawable(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setPageMarginDrawable(@Nullable Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setPageTransformer(boolean bl, @Nullable PageTransformer pageTransformer) {
        this.setPageTransformer(bl, pageTransformer, 2);
    }

    public void setPageTransformer(boolean bl, @Nullable PageTransformer pageTransformer, int n) {
        int n2 = 1;
        boolean bl2 = pageTransformer != null;
        boolean bl3 = this.mPageTransformer != null;
        boolean bl4 = bl2 != bl3;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabled(bl2);
        if (bl2) {
            if (bl) {
                n2 = 2;
            }
            this.mDrawingOrder = n2;
            this.mPageTransformerLayerType = n;
        } else {
            this.mDrawingOrder = 0;
        }
        if (bl4) {
            this.populate();
        }
    }

    void setScrollState(int n) {
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        if (this.mPageTransformer != null) {
            boolean bl = n != 0;
            this.enableLayers(bl);
        }
        this.dispatchOnScrollStateChanged(n);
    }

    void smoothScrollTo(int n, int n2) {
        this.smoothScrollTo(n, n2, 0);
    }

    void smoothScrollTo(int n, int n2, int n3) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        int n4 = this.mScroller != null && !this.mScroller.isFinished() ? 1 : 0;
        if (n4 != 0) {
            n4 = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            this.setScrollingCacheEnabled(false);
        } else {
            n4 = this.getScrollX();
        }
        int n5 = this.getScrollY();
        int n6 = n - n4;
        if (n6 == 0 && (n2 -= n5) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n = this.getClientWidth();
        int n7 = n / 2;
        float f = Math.abs(n6);
        float f2 = n;
        float f3 = Math.min(1.0f, f * 1.0f / f2);
        f = n7;
        f3 = this.distanceInfluenceForSnapDuration(f3);
        n = Math.abs(n3);
        if (n > 0) {
            n = 4 * Math.round(1000.0f * Math.abs((f + f3 * f) / (float)n));
        } else {
            f = this.mAdapter.getPageWidth(this.mCurItem);
            n = (int)(((float)Math.abs(n6) / (f2 * f + (float)this.mPageMargin) + 1.0f) * 100.0f);
        }
        n = Math.min(n, 600);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(n4, n5, n6, n2, n);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (!super.verifyDrawable(drawable) && drawable != this.mMarginDrawable) {
            return false;
        }
        return true;
    }

    @Inherited
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DecorView {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = context.getInteger(0, 48);
            context.recycle();
        }
    }

    class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        private boolean canScroll() {
            if (ViewPager.this.mAdapter != null && ViewPager.this.mAdapter.getCount() > 1) {
                return true;
            }
            return false;
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            accessibilityEvent.setScrollable(this.canScroll());
            if (accessibilityEvent.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
                accessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
                accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
                accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
            }
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(this.canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            if (super.performAccessibilityAction(view, n, bundle)) {
                return true;
            }
            if (n != 4096) {
                if (n != 8192) {
                    return false;
                }
                if (ViewPager.this.canScrollHorizontally(-1)) {
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1);
                    return true;
                }
                return false;
            }
            if (ViewPager.this.canScrollHorizontally(1)) {
                ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1);
                return true;
            }
            return false;
        }
    }

    public static interface OnAdapterChangeListener {
        public void onAdapterChanged(@NonNull ViewPager var1, @Nullable PagerAdapter var2, @Nullable PagerAdapter var3);
    }

    public static interface OnPageChangeListener {
        public void onPageScrollStateChanged(int var1);

        public void onPageScrolled(int var1, float var2, int var3);

        public void onPageSelected(int var1);
    }

    public static interface PageTransformer {
        public void transformPage(@NonNull View var1, float var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        PagerObserver() {
        }

        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentPager.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" position=");
            stringBuilder.append(this.position);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }

    }

    public static class SimpleOnPageChangeListener
    implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override
        public int compare(View object, View object2) {
            object = (LayoutParams)object.getLayoutParams();
            object2 = (LayoutParams)object2.getLayoutParams();
            if (object.isDecor != object2.isDecor) {
                if (object.isDecor) {
                    return 1;
                }
                return -1;
            }
            return object.position - object2.position;
        }
    }

}
