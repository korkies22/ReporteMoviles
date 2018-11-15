/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.Observable
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.Display
 *  android.view.FocusFinder
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.OverScroller
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.Preconditions;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.FastScroller;
import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.support.v7.widget.ViewBoundsCheck;
import android.support.v7.widget.ViewInfoStore;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
extends ViewGroup
implements ScrollingView,
NestedScrollingChild2 {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    private static final boolean ALLOW_THREAD_GAP_WORK;
    private static final int[] CLIP_TO_PADDING_ATTR;
    static final boolean DEBUG = false;
    static final int DEFAULT_ORIENTATION = 1;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS;
    public static final long NO_ID = -1L;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    static final boolean VERBOSE_TRACING = false;
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator;
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout = false;
    boolean mDispatchItemsChangedEvent = false;
    private int mDispatchScrollCounter = 0;
    private int mEatenAccessibilityChangeFlags;
    @NonNull
    private EdgeEffectFactory mEdgeEffectFactory = new EdgeEffectFactory();
    boolean mEnableFastScroller;
    @VisibleForTesting
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth = 0;
    boolean mIsAttached;
    ItemAnimator mItemAnimator = new DefaultItemAnimator();
    private ItemAnimator$ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter = 0;
    boolean mLayoutWasDefered;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
    @VisibleForTesting
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final Recycler mRecycler = new Recycler();
    RecyclerListener mRecyclerListener;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;
    private float mScaledVerticalScrollFactor = Float.MIN_VALUE;
    private final int[] mScrollConsumed;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId = -1;
    private int mScrollState = 0;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect = new Rect();
    private final Rect mTempRect2 = new Rect();
    final RectF mTempRectF = new RectF();
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable = new Runnable(){

        @Override
        public void run() {
            if (RecyclerView.this.mFirstLayoutComplete) {
                if (RecyclerView.this.isLayoutRequested()) {
                    return;
                }
                if (!RecyclerView.this.mIsAttached) {
                    RecyclerView.this.requestLayout();
                    return;
                }
                if (RecyclerView.this.mLayoutFrozen) {
                    RecyclerView.this.mLayoutWasDefered = true;
                    return;
                }
                RecyclerView.this.consumePendingUpdateOperations();
                return;
            }
        }
    };
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore = new ViewInfoStore();

    static {
        NESTED_SCROLLING_ATTRS = new int[]{16843830};
        CLIP_TO_PADDING_ATTR = new int[]{16842987};
        boolean bl = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = bl;
        bl = Build.VERSION.SDK_INT >= 23;
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bl;
        bl = Build.VERSION.SDK_INT >= 16;
        POST_UPDATES_ON_ANIMATION = bl;
        bl = Build.VERSION.SDK_INT >= 21;
        ALLOW_THREAD_GAP_WORK = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        FORCE_ABS_FOCUS_SEARCH_DIRECTION = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        IGNORE_DETACHED_FOCUSED_CHILD = bl;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
        sQuinticInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return f * f * f * f * (f -= 1.0f) + 1.0f;
            }
        };
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        boolean bl = true;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new ViewFlinger();
        GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mPrefetchRegistry = layoutPrefetchRegistryImpl;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList<ViewHolder>();
        this.mItemAnimatorRunner = new Runnable(){

            @Override
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback(){

            @Override
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processDisappeared(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processPersistent(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        RecyclerView.this.postAnimationRunner();
                        return;
                    }
                } else if (RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    RecyclerView.this.postAnimationRunner();
                }
            }

            @Override
            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        if (attributeSet != null) {
            layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, CLIP_TO_PADDING_ATTR, n, 0);
            this.mClipToPadding = layoutPrefetchRegistryImpl.getBoolean(0, true);
            layoutPrefetchRegistryImpl.recycle();
        } else {
            this.mClipToPadding = true;
        }
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        layoutPrefetchRegistryImpl = ViewConfiguration.get((Context)context);
        this.mTouchSlop = layoutPrefetchRegistryImpl.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mMinFlingVelocity = layoutPrefetchRegistryImpl.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = layoutPrefetchRegistryImpl.getScaledMaximumFlingVelocity();
        boolean bl2 = this.getOverScrollMode() == 2;
        this.setWillNotDraw(bl2);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.initAdapterManager();
        this.initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        if (attributeSet != null) {
            layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, 0);
            String string = layoutPrefetchRegistryImpl.getString(R.styleable.RecyclerView_layoutManager);
            if (layoutPrefetchRegistryImpl.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
                this.setDescendantFocusability(262144);
            }
            this.mEnableFastScroller = layoutPrefetchRegistryImpl.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
            if (this.mEnableFastScroller) {
                this.initFastScroller((StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
            }
            layoutPrefetchRegistryImpl.recycle();
            this.createLayoutManager(context, string, attributeSet, n, 0);
            bl2 = bl;
            if (Build.VERSION.SDK_INT >= 21) {
                context = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, n, 0);
                bl2 = context.getBoolean(0, true);
                context.recycle();
            }
        } else {
            this.setDescendantFocusability(262144);
            bl2 = bl;
        }
        this.setNestedScrollingEnabled(bl2);
    }

    static /* synthetic */ int[] access$500(RecyclerView recyclerView) {
        return recyclerView.mScrollConsumed;
    }

    static /* synthetic */ boolean access$700(RecyclerView recyclerView) {
        return recyclerView.awakenScrollBars();
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean bl = view.getParent() == this;
        this.mRecycler.unscrapView(this.getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!bl) {
            this.mChildHelper.addView(view, true);
            return;
        }
        this.mChildHelper.hide(view);
    }

    private void animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean bl, boolean bl2) {
        viewHolder.setIsRecyclable(false);
        if (bl) {
            this.addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (bl2) {
                this.addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            this.addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private void cancelTouch() {
        this.resetTouch();
        this.setScrollState(0);
    }

    static void clearNestedRecyclerViewIfNotNested(@NonNull ViewHolder viewHolder) {
        if (viewHolder.mNestedRecyclerView != null) {
            View view = (View)viewHolder.mNestedRecyclerView.get();
            while (view != null) {
                if (view == viewHolder.itemView) {
                    return;
                }
                if ((view = view.getParent()) instanceof View) continue;
                view = null;
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private void createLayoutManager(Context arrobject, String object, AttributeSet attributeSet, int n, int n2) {
        block11 : {
            if (object == null || (object = object.trim()).isEmpty()) break block11;
            string = this.getFullClassName((Context)arrobject, (String)object);
            object = this.isInEditMode() != false ? this.getClass().getClassLoader() : arrobject.getClassLoader();
            class_ = object.loadClass(string).asSubclass(LayoutManager.class);
            var6_16 = null;
            object = class_.getConstructor(RecyclerView.LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
            arrobject = new Object[]{arrobject, attributeSet, n, n2};
            ** GOTO lbl23
            {
                catch (NoSuchMethodException noSuchMethodException222) {
                    try {
                        try {
                            object = class_.getConstructor(new Class[0]);
                            arrobject = var6_16;
                        }
                        catch (NoSuchMethodException noSuchMethodException3) {
                            noSuchMethodException3.initCause(noSuchMethodException222);
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(attributeSet.getPositionDescription());
                            stringBuilder.append(": Error creating LayoutManager ");
                            stringBuilder.append(string);
                            throw new IllegalStateException(stringBuilder.toString(), noSuchMethodException3);
                        }
lbl23: // 2 sources:
                        object.setAccessible(true);
                        this.setLayoutManager(object.newInstance(arrobject));
                        return;
                    }
                    catch (ClassCastException classCastException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Class is not a LayoutManager ");
                        object.append(string);
                        throw new IllegalStateException(object.toString(), classCastException);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Cannot access non-public constructor ");
                        object.append(string);
                        throw new IllegalStateException(object.toString(), illegalAccessException);
                    }
                    catch (InstantiationException instantiationException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Could not instantiate the LayoutManager: ");
                        object.append(string);
                        throw new IllegalStateException(object.toString(), instantiationException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Could not instantiate the LayoutManager: ");
                        object.append(string);
                        throw new IllegalStateException(object.toString(), invocationTargetException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Unable to find LayoutManager ");
                        object.append(string);
                        throw new IllegalStateException(object.toString(), classNotFoundException);
                    }
                }
            }
        }
    }

    private boolean didChildRangeChange(int n, int n2) {
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] arrn = this.mMinMaxLayoutPositions;
        boolean bl = false;
        if (arrn[0] != n || this.mMinMaxLayoutPositions[1] != n2) {
            bl = true;
        }
        return bl;
    }

    private void dispatchContentChangedIfNecessary() {
        int n = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (n != 0 && this.isAccessibilityEnabled()) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
            accessibilityEvent.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, n);
            this.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    private void dispatchLayoutStep1() {
        int n;
        int n2;
        ItemAnimator.ItemHolderInfo itemHolderInfo;
        Object object = this.mState;
        boolean bl = true;
        object.assertLayoutStep(1);
        this.fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        this.startInterceptRequestLayout();
        this.mViewInfoStore.clear();
        this.onEnterLayoutOrScroll();
        this.processAdapterUpdatesAndSetAnimationFlags();
        this.saveFocusInfo();
        object = this.mState;
        if (!this.mState.mRunSimpleAnimations || !this.mItemsChanged) {
            bl = false;
        }
        object.mTrackOldChangeHolders = bl;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            n2 = this.mChildHelper.getChildCount();
            for (n = 0; n < n2; ++n) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (object.shouldIgnore() || object.isInvalid() && !this.mAdapter.hasStableIds()) continue;
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object), object.getUnmodifiedPayloads());
                this.mViewInfoStore.addToPreLayout((ViewHolder)object, itemHolderInfo);
                if (!this.mState.mTrackOldChangeHolders || !object.isUpdated() || object.isRemoved() || object.shouldIgnore() || object.isInvalid()) continue;
                long l = this.getChangedHolderKey((ViewHolder)object);
                this.mViewInfoStore.addToOldChangeHolders(l, (ViewHolder)object);
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            this.saveOldPositions();
            bl = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = bl;
            for (n = 0; n < this.mChildHelper.getChildCount(); ++n) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (object.shouldIgnore() || this.mViewInfoStore.isInPreLayout((ViewHolder)object)) continue;
                int n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                bl = object.hasAnyOfTheFlags(8192);
                n2 = n3;
                if (!bl) {
                    n2 = n3 | 4096;
                }
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, n2, object.getUnmodifiedPayloads());
                if (bl) {
                    this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, itemHolderInfo);
                    continue;
                }
                this.mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)object, itemHolderInfo);
            }
            this.clearOldPositions();
        } else {
            this.clearOldPositions();
        }
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        this.startInterceptRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State state = this.mState;
        boolean bl = this.mState.mRunSimpleAnimations && this.mItemAnimator != null;
        state.mRunSimpleAnimations = bl;
        this.mState.mLayoutStep = 4;
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        this.startInterceptRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (viewHolder.shouldIgnore()) continue;
                long l = this.getChangedHolderKey(viewHolder);
                ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
                ViewHolder viewHolder2 = this.mViewInfoStore.getFromOldChangeHolders(l);
                if (viewHolder2 != null && !viewHolder2.shouldIgnore()) {
                    boolean bl = this.mViewInfoStore.isDisappearing(viewHolder2);
                    boolean bl2 = this.mViewInfoStore.isDisappearing(viewHolder);
                    if (bl && viewHolder2 == viewHolder) {
                        this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                        continue;
                    }
                    ItemAnimator.ItemHolderInfo itemHolderInfo2 = this.mViewInfoStore.popFromPreLayout(viewHolder2);
                    this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                    itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
                    if (itemHolderInfo2 == null) {
                        this.handleMissingPreInfoForChangeError(l, viewHolder, viewHolder2);
                        continue;
                    }
                    this.animateChange(viewHolder2, viewHolder, itemHolderInfo2, itemHolderInfo, bl, bl2);
                    continue;
                }
                this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        this.mState.mPreviousLayoutItemCount = this.mState.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
        this.mViewInfoStore.clear();
        if (this.didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            this.dispatchOnScrolled(0, 0);
        }
        this.recoverFocusFromState();
        this.resetFocusInfo();
    }

    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if (n == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                this.mActiveOnItemTouchListener.onTouchEvent(this, motionEvent);
                if (n == 3 || n == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
        }
        if (n != 0) {
            int n2 = this.mOnItemTouchListeners.size();
            for (n = 0; n < n2; ++n) {
                OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(n);
                if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) continue;
                this.mActiveOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n == 3 || n == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int n2 = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n2; ++i) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || n == 3) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    private void findMinMaxChildLayoutPositions(int[] arrn) {
        int n = this.mChildHelper.getChildCount();
        if (n == 0) {
            arrn[0] = -1;
            arrn[1] = -1;
            return;
        }
        int n2 = Integer.MIN_VALUE;
        int n3 = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            int n4;
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (viewHolder.shouldIgnore()) {
                n4 = n2;
            } else {
                int n5 = viewHolder.getLayoutPosition();
                int n6 = n3;
                if (n5 < n3) {
                    n6 = n5;
                }
                n3 = n6;
                n4 = n2;
                if (n5 > n2) {
                    n4 = n5;
                    n3 = n6;
                }
            }
            n2 = n4;
        }
        arrn[0] = n3;
        arrn[1] = n2;
    }

    @Nullable
    static RecyclerView findNestedRecyclerView(@NonNull View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView)view;
        }
        view = (ViewGroup)view;
        int n = view.getChildCount();
        for (int i = 0; i < n; ++i) {
            RecyclerView recyclerView = RecyclerView.findNestedRecyclerView(view.getChildAt(i));
            if (recyclerView == null) continue;
            return recyclerView;
        }
        return null;
    }

    @Nullable
    private View findNextViewToFocus() {
        ViewHolder viewHolder;
        int n = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int n2 = this.mState.getItemCount();
        for (int i = n; i < n2 && (viewHolder = this.findViewHolderForAdapterPosition(i)) != null; ++i) {
            if (!viewHolder.itemView.hasFocusable()) continue;
            return viewHolder.itemView;
        }
        for (n = Math.min((int)n2, (int)n) - 1; n >= 0; --n) {
            viewHolder = this.findViewHolderForAdapterPosition(n);
            if (viewHolder == null) {
                return null;
            }
            if (!viewHolder.itemView.hasFocusable()) continue;
            return viewHolder.itemView;
        }
        return null;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).mViewHolder;
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set(view.getLeft() - rect2.left - layoutParams.leftMargin, view.getTop() - rect2.top - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    private int getDeepestFocusedViewWithId(View view) {
        int n = view.getId();
        while (!view.isFocused() && view instanceof ViewGroup && view.hasFocus()) {
            View view2;
            view = view2 = ((ViewGroup)view).getFocusedChild();
            if (view2.getId() == -1) continue;
            n = view2.getId();
            view = view2;
        }
        return n;
    }

    private String getFullClassName(Context object, String string) {
        if (string.charAt(0) == '.') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getPackageName());
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        if (string.contains(".")) {
            return string;
        }
        object = new StringBuilder();
        object.append(RecyclerView.class.getPackage().getName());
        object.append('.');
        object.append(string);
        return object.toString();
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        }
        return this.mScrollingChildHelper;
    }

    private void handleMissingPreInfoForChangeError(long l, ViewHolder viewHolder, ViewHolder object) {
        Object object2;
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            object2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (object2 == viewHolder || this.getChangedHolderKey((ViewHolder)object2) != l) continue;
            if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
                object = new StringBuilder();
                object.append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                object.append(object2);
                object.append(" \n View Holder 2:");
                object.append(viewHolder);
                object.append(this.exceptionLabel());
                throw new IllegalStateException(object.toString());
            }
            object = new StringBuilder();
            object.append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
            object.append(object2);
            object.append(" \n View Holder 2:");
            object.append(viewHolder);
            object.append(this.exceptionLabel());
            throw new IllegalStateException(object.toString());
        }
        object2 = new StringBuilder();
        object2.append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
        object2.append(object);
        object2.append(" cannot be found but it is necessary for ");
        object2.append(viewHolder);
        object2.append(this.exceptionLabel());
        Log.e((String)TAG, (String)object2.toString());
    }

    private boolean hasUpdatedView() {
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore() || !viewHolder.isUpdated()) continue;
            return true;
        }
        return false;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback(){

            @Override
            public void addView(View view, int n) {
                RecyclerView.this.addView(view, n);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override
            public void attachViewToParent(View object, int n, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                if (viewHolder != null) {
                    if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        object = new StringBuilder();
                        object.append("Called attach on a child which is not detached: ");
                        object.append(viewHolder);
                        object.append(RecyclerView.this.exceptionLabel());
                        throw new IllegalArgumentException(object.toString());
                    }
                    viewHolder.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent((View)object, n, layoutParams);
            }

            @Override
            public void detachViewFromParent(int n) {
                Object object = this.getChildAt(n);
                if (object != null && (object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    if (object.isTmpDetached() && !object.shouldIgnore()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("called detach on an already detached child ");
                        stringBuilder.append(object);
                        stringBuilder.append(RecyclerView.this.exceptionLabel());
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    object.addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(n);
            }

            @Override
            public View getChildAt(int n) {
                return RecyclerView.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override
            public void onEnteredHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onEnteredHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void onLeftHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onLeftHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void removeAllViews() {
                int n = this.getChildCount();
                for (int i = 0; i < n; ++i) {
                    View view = this.getChildAt(i);
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            @Override
            public void removeViewAt(int n) {
                View view = RecyclerView.this.getChildAt(n);
                if (view != null) {
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                }
                RecyclerView.this.removeViewAt(n);
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isPreferredNextFocus(View object, View view, int n) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        if (view == null) return false;
        if (view == this) {
            return false;
        }
        if (this.findContainingItemView(view) == null) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.findContainingItemView((View)object) == null) {
            return true;
        }
        this.mTempRect.set(0, 0, object.getWidth(), object.getHeight());
        this.mTempRect2.set(0, 0, view.getWidth(), view.getHeight());
        this.offsetDescendantRectToMyCoords((View)object, this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect2);
        int n2 = this.mLayout.getLayoutDirection();
        int n3 = -1;
        int n4 = n2 == 1 ? -1 : 1;
        n2 = (this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right ? 1 : ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left ? -1 : 0);
        if ((this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom) {
            n3 = 1;
        } else if (this.mTempRect.bottom <= this.mTempRect2.bottom && this.mTempRect.top < this.mTempRect2.bottom || this.mTempRect.top <= this.mTempRect2.top) {
            n3 = 0;
        }
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        switch (n) {
                            default: {
                                object = new StringBuilder();
                                object.append("Invalid direction: ");
                                object.append(n);
                                object.append(this.exceptionLabel());
                                throw new IllegalArgumentException(object.toString());
                            }
                            case 2: {
                                if (n3 > 0) return true;
                                bl2 = bl6;
                                if (n3 != 0) return bl2;
                                bl2 = bl6;
                                if (n2 * n4 < 0) return bl2;
                                return true;
                            }
                            case 1: 
                        }
                        if (n3 < 0) return true;
                        bl2 = bl;
                        if (n3 != 0) return bl2;
                        bl2 = bl;
                        if (n2 * n4 > 0) return bl2;
                        return true;
                    }
                    if (n3 <= 0) return bl2;
                    return true;
                }
                bl2 = bl3;
                if (n2 <= 0) return bl2;
                return true;
            }
            bl2 = bl4;
            if (n3 >= 0) return bl2;
            return true;
        }
        bl2 = bl5;
        if (n2 >= 0) return bl2;
        return true;
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mScrollPointerId) {
            int n2;
            n = n == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(n);
            this.mLastTouchX = n2 = (int)(motionEvent.getX(n) + 0.5f);
            this.mInitialTouchX = n2;
            this.mLastTouchY = n = (int)(motionEvent.getY(n) + 0.5f);
            this.mInitialTouchY = n;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        if (this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations()) {
            return true;
        }
        return false;
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            if (this.mDispatchItemsChangedEvent) {
                this.mLayout.onItemsChanged(this);
            }
        }
        if (this.predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean bl = this.mItemsAddedOrRemoved;
        boolean bl2 = true;
        boolean bl3 = bl || this.mItemsChanged;
        State state = this.mState;
        bl = !(!this.mFirstLayoutComplete || this.mItemAnimator == null || !this.mDataSetHasChangedAfterLayout && !bl3 && !this.mLayout.mRequestedSimpleAnimations || this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds());
        state.mRunSimpleAnimations = bl;
        state = this.mState;
        bl = this.mState.mRunSimpleAnimations && bl3 && !this.mDataSetHasChangedAfterLayout && this.predictiveItemAnimationsEnabled() ? bl2 : false;
        state.mRunPredictiveAnimations = bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void pullGlows(float var1_1, float var2_2, float var3_3, float var4_4) {
        block6 : {
            var6_5 = true;
            if (var2_2 >= 0.0f) break block6;
            this.ensureLeftGlow();
            EdgeEffectCompat.onPull(this.mLeftGlow, (- var2_2) / (float)this.getWidth(), 1.0f - var3_3 / (float)this.getHeight());
            ** GOTO lbl10
        }
        if (var2_2 > 0.0f) {
            this.ensureRightGlow();
            EdgeEffectCompat.onPull(this.mRightGlow, var2_2 / (float)this.getWidth(), var3_3 / (float)this.getHeight());
lbl10: // 2 sources:
            var5_6 = true;
        } else {
            var5_6 = false;
        }
        if (var4_4 < 0.0f) {
            this.ensureTopGlow();
            EdgeEffectCompat.onPull(this.mTopGlow, (- var4_4) / (float)this.getHeight(), var1_1 / (float)this.getWidth());
            var5_6 = var6_5;
        } else if (var4_4 > 0.0f) {
            this.ensureBottomGlow();
            EdgeEffectCompat.onPull(this.mBottomGlow, var4_4 / (float)this.getHeight(), 1.0f - var1_1 / (float)this.getWidth());
            var5_6 = var6_5;
        }
        if (!var5_6 && var2_2 == 0.0f) {
            if (var4_4 == 0.0f) return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    private void recoverFocusFromState() {
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && this.hasFocus() && this.getDescendantFocusability() != 393216) {
            Object object;
            if (this.getDescendantFocusability() == 131072 && this.isFocused()) {
                return;
            }
            if (!this.isFocused()) {
                object = this.getFocusedChild();
                if (IGNORE_DETACHED_FOCUSED_CHILD && (object.getParent() == null || !object.hasFocus())) {
                    if (this.mChildHelper.getChildCount() == 0) {
                        this.requestFocus();
                        return;
                    }
                } else if (!this.mChildHelper.isHidden((View)object)) {
                    return;
                }
            }
            long l = this.mState.mFocusedItemId;
            Object object2 = null;
            object = l != -1L && this.mAdapter.hasStableIds() ? this.findViewHolderForItemId(this.mState.mFocusedItemId) : null;
            if (object != null && !this.mChildHelper.isHidden(object.itemView) && object.itemView.hasFocusable()) {
                object = object.itemView;
            } else {
                object = object2;
                if (this.mChildHelper.getChildCount() > 0) {
                    object = this.findNextViewToFocus();
                }
            }
            if (object != null) {
                object2 = object;
                if ((long)this.mState.mFocusedSubChildId != -1L) {
                    View view = object.findViewById(this.mState.mFocusedSubChildId);
                    object2 = object;
                    if (view != null) {
                        object2 = object;
                        if (view.isFocusable()) {
                            object2 = view;
                        }
                    }
                }
                object2.requestFocus();
            }
            return;
        }
    }

    private void releaseGlows() {
        boolean bl;
        if (this.mLeftGlow != null) {
            this.mLeftGlow.onRelease();
            bl = this.mLeftGlow.isFinished();
        } else {
            bl = false;
        }
        boolean bl2 = bl;
        if (this.mTopGlow != null) {
            this.mTopGlow.onRelease();
            bl2 = bl | this.mTopGlow.isFinished();
        }
        bl = bl2;
        if (this.mRightGlow != null) {
            this.mRightGlow.onRelease();
            bl = bl2 | this.mRightGlow.isFinished();
        }
        bl2 = bl;
        if (this.mBottomGlow != null) {
            this.mBottomGlow.onRelease();
            bl2 = bl | this.mBottomGlow.isFinished();
        }
        if (bl2) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    private void requestChildOnScreen(@NonNull View view, @Nullable View view2) {
        Rect rect;
        Object object = view2 != null ? view2 : view;
        this.mTempRect.set(0, 0, object.getWidth(), object.getHeight());
        object = object.getLayoutParams();
        if (object instanceof LayoutParams) {
            object = (LayoutParams)((Object)object);
            if (!object.mInsetsDirty) {
                object = object.mDecorInsets;
                rect = this.mTempRect;
                rect.left -= object.left;
                rect = this.mTempRect;
                rect.right += object.right;
                rect = this.mTempRect;
                rect.top -= object.top;
                rect = this.mTempRect;
                rect.bottom += object.bottom;
            }
        }
        if (view2 != null) {
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        object = this.mLayout;
        rect = this.mTempRect;
        boolean bl = this.mFirstLayoutComplete;
        boolean bl2 = view2 == null;
        object.requestChildRectangleOnScreen(this, view, rect, bl ^ true, bl2);
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1L;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        this.stopNestedScroll(0);
        this.releaseGlows();
    }

    private void saveFocusInfo() {
        boolean bl = this.mPreserveFocusAfterLayout;
        Object object = null;
        Object object2 = bl && this.hasFocus() && this.mAdapter != null ? this.getFocusedChild() : null;
        object2 = object2 == null ? object : this.findContainingViewHolder((View)object2);
        if (object2 == null) {
            this.resetFocusInfo();
            return;
        }
        object = this.mState;
        long l = this.mAdapter.hasStableIds() ? object2.getItemId() : -1L;
        object.mFocusedItemId = l;
        object = this.mState;
        int n = this.mDataSetHasChangedAfterLayout ? -1 : (object2.isRemoved() ? object2.mOldPosition : object2.getAdapterPosition());
        object.mFocusedItemPosition = n;
        this.mState.mFocusedSubChildId = this.getDeepestFocusedViewWithId(object2.itemView);
    }

    private void setAdapterInternal(Adapter adapter, boolean bl, boolean bl2) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!bl || bl2) {
            this.removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        Adapter adapter2 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(adapter2, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter2, this.mAdapter, bl);
        this.mState.mStructureChanged = true;
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    void absorbGlows(int n, int n2) {
        if (n < 0) {
            this.ensureLeftGlow();
            this.mLeftGlow.onAbsorb(- n);
        } else if (n > 0) {
            this.ensureRightGlow();
            this.mRightGlow.onAbsorb(n);
        }
        if (n2 < 0) {
            this.ensureTopGlow();
            this.mTopGlow.onAbsorb(- n2);
        } else if (n2 > 0) {
            this.ensureBottomGlow();
            this.mBottomGlow.onAbsorb(n2);
        }
        if (n != 0 || n2 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, arrayList, n, n2)) {
            super.addFocusables(arrayList, n, n2);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration, -1);
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int n) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(n, itemDecoration);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList<OnScrollListener>();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    void animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        this.addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void assertInLayoutOrScroll(String charSequence) {
        if (!this.isComputingLayout()) {
            if (charSequence == null) {
                charSequence = new StringBuilder();
                charSequence.append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
                charSequence.append(this.exceptionLabel());
                throw new IllegalStateException(charSequence.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(this.exceptionLabel());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    void assertNotInLayoutOrScroll(String charSequence) {
        if (this.isComputingLayout()) {
            if (charSequence == null) {
                charSequence = new StringBuilder();
                charSequence.append("Cannot call this method while RecyclerView is computing a layout or scrolling");
                charSequence.append(this.exceptionLabel());
                throw new IllegalStateException(charSequence.toString());
            }
            throw new IllegalStateException((String)charSequence);
        }
        if (this.mDispatchScrollCounter > 0) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(this.exceptionLabel());
            Log.w((String)TAG, (String)"Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", (Throwable)new IllegalStateException(charSequence.toString()));
        }
    }

    boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        if (this.mItemAnimator != null && !this.mItemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads())) {
            return false;
        }
        return true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams && this.mLayout.checkLayoutParams((LayoutParams)layoutParams)) {
            return true;
        }
        return false;
    }

    void clearOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.clearOldPosition();
        }
        this.mRecycler.clearOldPositions();
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void clearOnScrollListeners() {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    @Override
    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return n;
    }

    @Override
    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return n;
    }

    @Override
    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (this.mLayout.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return n;
    }

    void considerReleasingGlowsOnScroll(int n, int n2) {
        boolean bl;
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished() && n > 0) {
            this.mLeftGlow.onRelease();
            bl = this.mLeftGlow.isFinished();
        } else {
            bl = false;
        }
        boolean bl2 = bl;
        if (this.mRightGlow != null) {
            bl2 = bl;
            if (!this.mRightGlow.isFinished()) {
                bl2 = bl;
                if (n < 0) {
                    this.mRightGlow.onRelease();
                    bl2 = bl | this.mRightGlow.isFinished();
                }
            }
        }
        bl = bl2;
        if (this.mTopGlow != null) {
            bl = bl2;
            if (!this.mTopGlow.isFinished()) {
                bl = bl2;
                if (n2 > 0) {
                    this.mTopGlow.onRelease();
                    bl = bl2 | this.mTopGlow.isFinished();
                }
            }
        }
        bl2 = bl;
        if (this.mBottomGlow != null) {
            bl2 = bl;
            if (!this.mBottomGlow.isFinished()) {
                bl2 = bl;
                if (n2 < 0) {
                    this.mBottomGlow.onRelease();
                    bl2 = bl | this.mBottomGlow.isFinished();
                }
            }
        }
        if (bl2) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void consumePendingUpdateOperations() {
        if (this.mFirstLayoutComplete && !this.mDataSetHasChangedAfterLayout) {
            if (!this.mAdapterHelper.hasPendingUpdates()) {
                return;
            }
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                this.startInterceptRequestLayout();
                this.onEnterLayoutOrScroll();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutWasDefered) {
                    if (this.hasUpdatedView()) {
                        this.dispatchLayout();
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                    }
                }
                this.stopInterceptRequestLayout(true);
                this.onExitLayoutOrScroll();
                TraceCompat.endSection();
                return;
            }
            if (this.mAdapterHelper.hasPendingUpdates()) {
                TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                this.dispatchLayout();
                TraceCompat.endSection();
            }
            return;
        }
        TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
    }

    void defaultOnMeasure(int n, int n2) {
        this.setMeasuredDimension(LayoutManager.chooseSize(n, this.getPaddingLeft() + this.getPaddingRight(), ViewCompat.getMinimumWidth((View)this)), LayoutManager.chooseSize(n2, this.getPaddingTop() + this.getPaddingBottom(), ViewCompat.getMinimumHeight((View)this)));
    }

    void dispatchChildAttached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildAttachedToWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewAttachedToWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i = this.mOnChildAttachStateListeners.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewAttachedToWindow(view);
            }
        }
    }

    void dispatchChildDetached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildDetachedFromWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewDetachedFromWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i = this.mOnChildAttachStateListeners.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewDetachedFromWindow(view);
            }
        }
    }

    void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e((String)TAG, (String)"No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"No layout manager attached; skipping layout");
            return;
        }
        this.mState.mIsMeasuring = false;
        if (this.mState.mLayoutStep == 1) {
            this.dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == this.getWidth() && this.mLayout.getHeight() == this.getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        }
        this.dispatchLayoutStep3();
    }

    @Override
    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.getScrollingChildHelper().dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    void dispatchOnScrollStateChanged(int n) {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(n);
        }
        this.onScrollStateChanged(n);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, n);
        }
        if (this.mScrollListeners != null) {
            for (int i = this.mScrollListeners.size() - 1; i >= 0; --i) {
                this.mScrollListeners.get(i).onScrollStateChanged(this, n);
            }
        }
    }

    void dispatchOnScrolled(int n, int n2) {
        ++this.mDispatchScrollCounter;
        int n3 = this.getScrollX();
        int n4 = this.getScrollY();
        this.onScrollChanged(n3, n4, n3, n4);
        this.onScrolled(n, n2);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, n, n2);
        }
        if (this.mScrollListeners != null) {
            for (n3 = this.mScrollListeners.size() - 1; n3 >= 0; --n3) {
                this.mScrollListeners.get(n3).onScrolled(this, n, n2);
            }
        }
        --this.mDispatchScrollCounter;
    }

    void dispatchPendingImportantForAccessibilityChanges() {
        for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; --i) {
            int n;
            ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(i);
            if (viewHolder.itemView.getParent() != this || viewHolder.shouldIgnore() || (n = viewHolder.mPendingAccessibilityState) == -1) continue;
            ViewCompat.setImportantForAccessibility(viewHolder.itemView, n);
            viewHolder.mPendingAccessibilityState = -1;
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        int n;
        int n2;
        super.draw(canvas);
        int n3 = this.mItemDecorations.size();
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            this.mItemDecorations.get(n2).onDrawOver(canvas, this, this.mState);
        }
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished()) {
            n = canvas.save();
            n2 = this.mClipToPadding ? this.getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float)(- this.getHeight() + n2), 0.0f);
            n3 = this.mLeftGlow != null && this.mLeftGlow.draw(canvas) ? 1 : 0;
            canvas.restoreToCount(n);
        } else {
            n3 = 0;
        }
        n2 = n3;
        if (this.mTopGlow != null) {
            n2 = n3;
            if (!this.mTopGlow.isFinished()) {
                n = canvas.save();
                if (this.mClipToPadding) {
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                n2 = this.mTopGlow != null && this.mTopGlow.draw(canvas) ? 1 : 0;
                n2 = n3 | n2;
                canvas.restoreToCount(n);
            }
        }
        n3 = n2;
        if (this.mRightGlow != null) {
            n3 = n2;
            if (!this.mRightGlow.isFinished()) {
                n = canvas.save();
                int n5 = this.getWidth();
                n3 = this.mClipToPadding ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate((float)(- n3), (float)(- n5));
                n3 = this.mRightGlow != null && this.mRightGlow.draw(canvas) ? 1 : 0;
                n3 = n2 | n3;
                canvas.restoreToCount(n);
            }
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished()) {
            n = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate((float)(- this.getWidth() + this.getPaddingRight()), (float)(- this.getHeight() + this.getPaddingBottom()));
            } else {
                canvas.translate((float)(- this.getWidth()), (float)(- this.getHeight()));
            }
            n2 = n4;
            if (this.mBottomGlow != null) {
                n2 = n4;
                if (this.mBottomGlow.draw(canvas)) {
                    n2 = 1;
                }
            }
            n2 |= n3;
            canvas.restoreToCount(n);
        } else {
            n2 = n3;
        }
        n3 = n2;
        if (n2 == 0) {
            n3 = n2;
            if (this.mItemAnimator != null) {
                n3 = n2;
                if (this.mItemDecorations.size() > 0) {
                    n3 = n2;
                    if (this.mItemAnimator.isRunning()) {
                        n3 = 1;
                    }
                }
            }
        }
        if (n3 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long l) {
        return super.drawChild(canvas, view, l);
    }

    void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = this.mEdgeEffectFactory.createEdgeEffect(this, 3);
        if (this.mClipToPadding) {
            this.mBottomGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mBottomGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = this.mEdgeEffectFactory.createEdgeEffect(this, 0);
        if (this.mClipToPadding) {
            this.mLeftGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mLeftGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = this.mEdgeEffectFactory.createEdgeEffect(this, 2);
        if (this.mClipToPadding) {
            this.mRightGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mRightGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = this.mEdgeEffectFactory.createEdgeEffect(this, 1);
        if (this.mClipToPadding) {
            this.mTopGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mTopGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    String exceptionLabel() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        stringBuilder.append(", adapter:");
        stringBuilder.append(this.mAdapter);
        stringBuilder.append(", layout:");
        stringBuilder.append(this.mLayout);
        stringBuilder.append(", context:");
        stringBuilder.append((Object)this.getContext());
        return stringBuilder.toString();
    }

    final void fillRemainingScrollValues(State state) {
        if (this.getScrollState() == 2) {
            OverScroller overScroller = this.mViewFlinger.mScroller;
            state.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
            state.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        state.mRemainingScrollHorizontal = 0;
        state.mRemainingScrollVertical = 0;
    }

    public View findChildViewUnder(float f, float f2) {
        for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
            View view = this.mChildHelper.getChildAt(i);
            float f3 = view.getTranslationX();
            float f4 = view.getTranslationY();
            if (f < (float)view.getLeft() + f3 || f > (float)view.getRight() + f3 || f2 < (float)view.getTop() + f4 || f2 > (float)view.getBottom() + f4) continue;
            return view;
        }
        return null;
    }

    @Nullable
    public View findContainingItemView(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        view = viewParent;
        while (view != null && view != this && view instanceof View) {
            view2 = view;
            view = view2.getParent();
        }
        if (view == this) {
            return view2;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view) {
        if ((view = this.findContainingItemView(view)) == null) {
            return null;
        }
        return this.getChildViewHolder(view);
    }

    public ViewHolder findViewHolderForAdapterPosition(int n) {
        boolean bl = this.mDataSetHasChangedAfterLayout;
        ViewHolder viewHolder = null;
        if (bl) {
            return null;
        }
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n2; ++i) {
            ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder viewHolder3 = viewHolder;
            if (viewHolder2 != null) {
                viewHolder3 = viewHolder;
                if (!viewHolder2.isRemoved()) {
                    viewHolder3 = viewHolder;
                    if (this.getAdapterPositionFor(viewHolder2) == n) {
                        if (this.mChildHelper.isHidden(viewHolder2.itemView)) {
                            viewHolder3 = viewHolder2;
                        } else {
                            return viewHolder2;
                        }
                    }
                }
            }
            viewHolder = viewHolder3;
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long l) {
        Object object = this.mAdapter;
        Adapter adapter = null;
        if (object != null) {
            if (!this.mAdapter.hasStableIds()) {
                return null;
            }
            int n = this.mChildHelper.getUnfilteredChildCount();
            for (int i = 0; i < n; ++i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
                object = adapter;
                if (viewHolder != null) {
                    object = adapter;
                    if (!viewHolder.isRemoved()) {
                        object = adapter;
                        if (viewHolder.getItemId() == l) {
                            if (this.mChildHelper.isHidden(viewHolder.itemView)) {
                                object = viewHolder;
                            } else {
                                return viewHolder;
                            }
                        }
                    }
                }
                adapter = object;
            }
            return adapter;
        }
        return null;
    }

    public ViewHolder findViewHolderForLayoutPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    ViewHolder findViewHolderForPosition(int var1_1, boolean var2_2) {
        var4_3 = this.mChildHelper.getUnfilteredChildCount();
        var5_4 = null;
        var3_5 = 0;
        while (var3_5 < var4_3) {
            block3 : {
                block4 : {
                    var7_7 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(var3_5));
                    var6_6 = var5_4;
                    if (var7_7 == null) break block3;
                    var6_6 = var5_4;
                    if (var7_7.isRemoved()) break block3;
                    if (!var2_2) break block4;
                    if (var7_7.mPosition == var1_1) ** GOTO lbl-1000
                    var6_6 = var5_4;
                    break block3;
                }
                if (var7_7.getLayoutPosition() != var1_1) {
                    var6_6 = var5_4;
                } else lbl-1000: // 2 sources:
                {
                    if (this.mChildHelper.isHidden(var7_7.itemView) == false) return var7_7;
                    var6_6 = var7_7;
                }
            }
            ++var3_5;
            var5_4 = var6_6;
        }
        return var5_4;
    }

    public boolean fling(int n, int n2) {
        boolean bl;
        int n3;
        boolean bl2;
        int n4;
        int n5;
        block17 : {
            block16 : {
                block15 : {
                    block14 : {
                        LayoutManager layoutManager = this.mLayout;
                        n5 = 0;
                        if (layoutManager == null) {
                            Log.e((String)TAG, (String)"Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            return false;
                        }
                        if (this.mLayoutFrozen) {
                            return false;
                        }
                        bl = this.mLayout.canScrollHorizontally();
                        bl2 = this.mLayout.canScrollVertically();
                        if (!bl) break block14;
                        n4 = n;
                        if (Math.abs(n) >= this.mMinFlingVelocity) break block15;
                    }
                    n4 = 0;
                }
                if (!bl2) break block16;
                n3 = n2;
                if (Math.abs(n2) >= this.mMinFlingVelocity) break block17;
            }
            n3 = 0;
        }
        if (n4 == 0 && n3 == 0) {
            return false;
        }
        float f = n4;
        float f2 = n3;
        if (!this.dispatchNestedPreFling(f, f2)) {
            boolean bl3 = bl || bl2;
            this.dispatchNestedFling(f, f2, bl3);
            if (this.mOnFlingListener != null && this.mOnFlingListener.onFling(n4, n3)) {
                return true;
            }
            if (bl3) {
                n = n5;
                if (bl) {
                    n = 1;
                }
                n2 = n;
                if (bl2) {
                    n2 = n | 2;
                }
                this.startNestedScroll(n2, 1);
                n = Math.max(- this.mMaxFlingVelocity, Math.min(n4, this.mMaxFlingVelocity));
                n2 = Math.max(- this.mMaxFlingVelocity, Math.min(n3, this.mMaxFlingVelocity));
                this.mViewFlinger.fling(n, n2);
                return true;
            }
        }
        return false;
    }

    public View focusSearch(View view, int n) {
        View view2 = this.mLayout.onInterceptFocusSearch(view, n);
        if (view2 != null) {
            return view2;
        }
        int n2 = this.mAdapter != null && this.mLayout != null && !this.isComputingLayout() && !this.mLayoutFrozen ? 1 : 0;
        view2 = FocusFinder.getInstance();
        if (n2 != 0 && (n == 2 || n == 1)) {
            int n3;
            int n4;
            if (this.mLayout.canScrollVertically()) {
                n4 = n == 2 ? 130 : 33;
                n3 = view2.findNextFocus((ViewGroup)this, view, n4) == null ? 1 : 0;
                n2 = n3;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    n = n4;
                    n2 = n3;
                }
            } else {
                n2 = 0;
            }
            int n5 = n2;
            n3 = n;
            if (n2 == 0) {
                n5 = n2;
                n3 = n;
                if (this.mLayout.canScrollHorizontally()) {
                    n2 = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
                    n4 = n == 2 ? 1 : 0;
                    n2 = (n2 ^ n4) != 0 ? 66 : 17;
                    n4 = view2.findNextFocus((ViewGroup)this, view, n2) == null ? 1 : 0;
                    n5 = n4;
                    n3 = n;
                    if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                        n3 = n2;
                        n5 = n4;
                    }
                }
            }
            if (n5 != 0) {
                this.consumePendingUpdateOperations();
                if (this.findContainingItemView(view) == null) {
                    return null;
                }
                this.startInterceptRequestLayout();
                this.mLayout.onFocusSearchFailed(view, n3, this.mRecycler, this.mState);
                this.stopInterceptRequestLayout(false);
            }
            view2 = view2.findNextFocus((ViewGroup)this, view, n3);
            n = n3;
        } else if ((view2 = view2.findNextFocus((ViewGroup)this, view, n)) == null && n2 != 0) {
            this.consumePendingUpdateOperations();
            if (this.findContainingItemView(view) == null) {
                return null;
            }
            this.startInterceptRequestLayout();
            view2 = this.mLayout.onFocusSearchFailed(view, n, this.mRecycler, this.mState);
            this.stopInterceptRequestLayout(false);
        }
        if (view2 != null && !view2.hasFocusable()) {
            if (this.getFocusedChild() == null) {
                return super.focusSearch(view, n);
            }
            this.requestChildOnScreen(view2, null);
            return view;
        }
        if (this.isPreferredNextFocus(view, view2, n)) {
            return view2;
        }
        return super.focusSearch(view, n);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RecyclerView has no LayoutManager");
            stringBuilder.append(this.exceptionLabel());
            throw new IllegalStateException(stringBuilder.toString());
        }
        return this.mLayout.generateDefaultLayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet object) {
        if (this.mLayout == null) {
            object = new StringBuilder();
            object.append("RecyclerView has no LayoutManager");
            object.append(this.exceptionLabel());
            throw new IllegalStateException(object.toString());
        }
        return this.mLayout.generateLayoutParams(this.getContext(), (AttributeSet)object);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        if (this.mLayout == null) {
            object = new StringBuilder();
            object.append("RecyclerView has no LayoutManager");
            object.append(this.exceptionLabel());
            throw new IllegalStateException(object.toString());
        }
        return this.mLayout.generateLayoutParams((ViewGroup.LayoutParams)object);
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    int getAdapterPositionFor(ViewHolder viewHolder) {
        if (!viewHolder.hasAnyOfTheFlags(524) && viewHolder.isBound()) {
            return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
        }
        return -1;
    }

    public int getBaseline() {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    long getChangedHolderKey(ViewHolder viewHolder) {
        if (this.mAdapter.hasStableIds()) {
            return viewHolder.getItemId();
        }
        return viewHolder.mPosition;
    }

    public int getChildAdapterPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return object.getAdapterPosition();
        }
        return -1;
    }

    protected int getChildDrawingOrder(int n, int n2) {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(n, n2);
        }
        return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(n, n2);
    }

    public long getChildItemId(View object) {
        Adapter adapter = this.mAdapter;
        long l = -1L;
        if (adapter != null) {
            if (!this.mAdapter.hasStableIds()) {
                return -1L;
            }
            if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                l = object.getItemId();
            }
            return l;
        }
        return -1L;
    }

    public int getChildLayoutPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return object.getLayoutPosition();
        }
        return -1;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return this.getChildAdapterPosition(view);
    }

    public ViewHolder getChildViewHolder(View view) {
        Object object = view.getParent();
        if (object != null && object != this) {
            object = new StringBuilder();
            object.append("View ");
            object.append((Object)view);
            object.append(" is not a direct child of ");
            object.append(this);
            throw new IllegalArgumentException(object.toString());
        }
        return RecyclerView.getChildViewHolderInt(view);
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public ItemDecoration getItemDecorationAt(int n) {
        int n2 = this.getItemDecorationCount();
        if (n >= 0 && n < n2) {
            return this.mItemDecorations.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is an invalid index for size ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getItemDecorationCount() {
        return this.mItemDecorations.size();
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0L;
    }

    @Nullable
    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int n) {
        return this.getScrollingChildHelper().hasNestedScrollingParent(n);
    }

    public boolean hasPendingAdapterUpdates() {
        if (this.mFirstLayoutComplete && !this.mDataSetHasChangedAfterLayout && !this.mAdapterHelper.hasPendingUpdates()) {
            return false;
        }
        return true;
    }

    void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback(){

            void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                int n = updateOp.cmd;
                if (n != 4) {
                    if (n != 8) {
                        switch (n) {
                            default: {
                                return;
                            }
                            case 2: {
                                RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                                return;
                            }
                            case 1: 
                        }
                        RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
                    return;
                }
                RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            }

            @Override
            public ViewHolder findViewHolder(int n) {
                ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n, true);
                if (viewHolder == null) {
                    return null;
                }
                if (RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) {
                    return null;
                }
                return viewHolder;
            }

            @Override
            public void markViewHoldersUpdated(int n, int n2, Object object) {
                RecyclerView.this.viewRangeUpdate(n, n2, object);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override
            public void offsetPositionsForAdd(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForInsert(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForMove(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForMove(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                State state = RecyclerView.this.mState;
                state.mDeletedInvisibleItemCountSincePreviousLayout += n2;
            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }
        });
    }

    @VisibleForTesting
    void initFastScroller(StateListDrawable object, Drawable drawable, StateListDrawable stateListDrawable, Drawable drawable2) {
        if (object != null && drawable != null && stateListDrawable != null && drawable2 != null) {
            Resources resources = this.getContext().getResources();
            new FastScroller(this, (StateListDrawable)object, drawable, stateListDrawable, drawable2, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
            return;
        }
        object = new StringBuilder();
        object.append("Trying to set fast scroller without both required drawables.");
        object.append(this.exceptionLabel());
        throw new IllegalArgumentException(object.toString());
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    boolean isAccessibilityEnabled() {
        if (this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled()) {
            return true;
        }
        return false;
    }

    public boolean isAnimating() {
        if (this.mItemAnimator != null && this.mItemAnimator.isRunning()) {
            return true;
        }
        return false;
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isComputingLayout() {
        if (this.mLayoutOrScrollCounter > 0) {
            return true;
        }
        return false;
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.getScrollingChildHelper().isNestedScrollingEnabled();
    }

    void jumpToPositionForSmoothScroller(int n) {
        if (this.mLayout == null) {
            return;
        }
        this.mLayout.scrollToPosition(n);
        this.awakenScrollBars();
    }

    void markItemDecorInsetsDirty() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ((LayoutParams)this.mChildHelper.getUnfilteredChildAt((int)i).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    void markKnownViewsInvalid() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(6);
        }
        this.markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void offsetChildrenHorizontal(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetLeftAndRight(n);
        }
    }

    public void offsetChildrenVertical(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetTopAndBottom(n);
        }
    }

    void offsetPositionRecordsForInsert(int n, int n2) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n) continue;
            viewHolder.offsetPosition(n2, false);
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForInsert(n, n2);
        this.requestLayout();
    }

    void offsetPositionRecordsForMove(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = this.mChildHelper.getUnfilteredChildCount();
        if (n < n2) {
            n4 = n2;
            n3 = -1;
            n5 = n;
        } else {
            n4 = n;
            n5 = n2;
            n3 = 1;
        }
        for (int i = 0; i < n6; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.mPosition < n5 || viewHolder.mPosition > n4) continue;
            if (viewHolder.mPosition == n) {
                viewHolder.offsetPosition(n2 - n, false);
            } else {
                viewHolder.offsetPosition(n3, false);
            }
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForMove(n, n2);
        this.requestLayout();
    }

    void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            if (viewHolder.mPosition >= n + n2) {
                viewHolder.offsetPosition(- n2, bl);
                this.mState.mStructureChanged = true;
                continue;
            }
            if (viewHolder.mPosition < n) continue;
            viewHolder.flagRemovedAndOffsetPosition(n - 1, - n2, bl);
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForRemove(n, n2, bl);
        this.requestLayout();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean bl = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || this.isLayoutRequested()) {
            bl = false;
        }
        this.mFirstLayoutComplete = bl;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = GapWorker.sGapWorker.get();
            if (this.mGapWorker == null) {
                float f;
                this.mGapWorker = new GapWorker();
                Display display = ViewCompat.getDisplay((View)this);
                if (this.isInEditMode() || display == null || (f = display.getRefreshRate()) < 30.0f) {
                    f = 60.0f;
                }
                this.mGapWorker.mFrameIntervalNs = (long)(1.0E9f / f);
                GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        this.stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        this.removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK && this.mGapWorker != null) {
            this.mGapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mItemDecorations.get(i).onDraw(canvas, this, this.mState);
        }
    }

    void onEnterLayoutOrScroll() {
        ++this.mLayoutOrScrollCounter;
    }

    void onExitLayoutOrScroll() {
        this.onExitLayoutOrScroll(true);
    }

    void onExitLayoutOrScroll(boolean bl) {
        --this.mLayoutOrScrollCounter;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (bl) {
                this.dispatchContentChangedIfNecessary();
                this.dispatchPendingImportantForAccessibilityChanges();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onGenericMotionEvent(MotionEvent var1_1) {
        block8 : {
            block7 : {
                if (this.mLayout == null) {
                    return false;
                }
                if (this.mLayoutFrozen) {
                    return false;
                }
                if (var1_1.getAction() != 8) return false;
                if ((var1_1.getSource() & 2) == 0) break block7;
                var2_2 = this.mLayout.canScrollVertically() != false ? - var1_1.getAxisValue(9) : 0.0f;
                var3_3 = var2_2;
                if (!this.mLayout.canScrollHorizontally()) ** GOTO lbl17
                var3_3 = var1_1.getAxisValue(10);
                break block8;
            }
            if ((var1_1.getSource() & 4194304) == 0) ** GOTO lbl-1000
            var3_3 = var1_1.getAxisValue(26);
            if (this.mLayout.canScrollVertically()) {
                var3_3 = - var3_3;
lbl17: // 2 sources:
                var4_4 = 0.0f;
                var2_2 = var3_3;
                var3_3 = var4_4;
            } else if (this.mLayout.canScrollHorizontally()) {
                var2_2 = 0.0f;
            } else lbl-1000: // 2 sources:
            {
                var3_3 = var2_2 = 0.0f;
            }
        }
        if (var2_2 == 0.0f) {
            if (var3_3 == 0.0f) return false;
        }
        this.scrollByInternal((int)(var3_3 * this.mScaledHorizontalScrollFactor), (int)(var2_2 * this.mScaledVerticalScrollFactor), var1_1);
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent object) {
        boolean bl = this.mLayoutFrozen;
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.dispatchOnItemTouchIntercept((MotionEvent)object)) {
            this.cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        bl = this.mLayout.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)object);
        int n = object.getActionMasked();
        int n2 = object.getActionIndex();
        switch (n) {
            default: {
                break;
            }
            case 6: {
                this.onPointerUp((MotionEvent)object);
                break;
            }
            case 5: {
                this.mScrollPointerId = object.getPointerId(n2);
                this.mLastTouchX = n = (int)(object.getX(n2) + 0.5f);
                this.mInitialTouchX = n;
                this.mLastTouchY = n2 = (int)(object.getY(n2) + 0.5f);
                this.mInitialTouchY = n2;
                break;
            }
            case 3: {
                this.cancelTouch();
                break;
            }
            case 2: {
                n = object.findPointerIndex(this.mScrollPointerId);
                if (n < 0) {
                    object = new StringBuilder();
                    object.append("Error processing scroll; pointer index for id ");
                    object.append(this.mScrollPointerId);
                    object.append(" not found. Did any MotionEvents get skipped?");
                    Log.e((String)TAG, (String)object.toString());
                    return false;
                }
                n2 = (int)(object.getX(n) + 0.5f);
                int n3 = (int)(object.getY(n) + 0.5f);
                if (this.mScrollState == 1) break;
                n = this.mInitialTouchX;
                int n4 = this.mInitialTouchY;
                if (bl && Math.abs(n2 - n) > this.mTouchSlop) {
                    this.mLastTouchX = n2;
                    n2 = 1;
                } else {
                    n2 = 0;
                }
                n = n2;
                if (bl3) {
                    n = n2;
                    if (Math.abs(n3 - n4) > this.mTouchSlop) {
                        this.mLastTouchY = n3;
                        n = 1;
                    }
                }
                if (n == 0) break;
                this.setScrollState(1);
                break;
            }
            case 1: {
                this.mVelocityTracker.clear();
                this.stopNestedScroll(0);
                break;
            }
            case 0: {
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = object.getPointerId(0);
                this.mLastTouchX = n2 = (int)(object.getX() + 0.5f);
                this.mInitialTouchX = n2;
                this.mLastTouchY = n2 = (int)(object.getY() + 0.5f);
                this.mInitialTouchY = n2;
                if (this.mScrollState == 2) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                }
                object = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                object[0] = false;
                n2 = bl ? 1 : 0;
                n = n2;
                if (bl3) {
                    n = n2 | 2;
                }
                this.startNestedScroll(n, 0);
            }
        }
        if (this.mScrollState == 1) {
            bl2 = true;
        }
        return bl2;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void onMeasure(int n, int n2) {
        if (this.mLayout == null) {
            this.defaultOnMeasure(n, n2);
            return;
        }
        boolean bl = this.mLayout.isAutoMeasureEnabled();
        boolean bl2 = false;
        if (bl) {
            int n3 = View.MeasureSpec.getMode((int)n);
            int n4 = View.MeasureSpec.getMode((int)n2);
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            boolean bl3 = bl2;
            if (n3 == 1073741824) {
                bl3 = bl2;
                if (n4 == 1073741824) {
                    return;
                }
            }
            if (bl3) return;
            if (this.mAdapter == null) {
                return;
            }
            if (this.mState.mLayoutStep == 1) {
                this.dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(n, n2);
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            if (!this.mLayout.shouldMeasureTwice()) return;
            this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824));
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            return;
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            this.startInterceptRequestLayout();
            this.onEnterLayoutOrScroll();
            this.processAdapterUpdatesAndSetAnimationFlags();
            this.onExitLayoutOrScroll();
            if (this.mState.mRunPredictiveAnimations) {
                this.mState.mInPreLayout = true;
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                this.mState.mInPreLayout = false;
            }
            this.mAdapterUpdateDuringMeasure = false;
            this.stopInterceptRequestLayout(false);
        } else if (this.mState.mRunPredictiveAnimations) {
            this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());
            return;
        }
        this.mState.mItemCount = this.mAdapter != null ? this.mAdapter.getItemCount() : 0;
        this.startInterceptRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
        this.stopInterceptRequestLayout(false);
        this.mState.mInPreLayout = false;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        if (this.isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(n, rect);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.mPendingSavedState = (SavedState)parcelable;
        super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null) {
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            savedState.copyFrom(this.mPendingSavedState);
            return savedState;
        }
        if (this.mLayout != null) {
            savedState.mLayoutState = this.mLayout.onSaveInstanceState();
            return savedState;
        }
        savedState.mLayoutState = null;
        return savedState;
    }

    public void onScrollStateChanged(int n) {
    }

    public void onScrolled(int n, int n2) {
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3 || n2 != n4) {
            this.invalidateGlows();
        }
    }

    public boolean onTouchEvent(MotionEvent object) {
        boolean bl = this.mLayoutFrozen;
        int n = 0;
        if (!bl) {
            if (this.mIgnoreMotionEventTillDown) {
                return false;
            }
            if (this.dispatchOnItemTouch((MotionEvent)object)) {
                this.cancelTouch();
                return true;
            }
            if (this.mLayout == null) {
                return false;
            }
            bl = this.mLayout.canScrollHorizontally();
            boolean bl2 = this.mLayout.canScrollVertically();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            MotionEvent motionEvent = MotionEvent.obtain((MotionEvent)object);
            int n2 = object.getActionMasked();
            int n3 = object.getActionIndex();
            if (n2 == 0) {
                int[] arrn = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                arrn[0] = 0;
            }
            motionEvent.offsetLocation((float)this.mNestedOffsets[0], (float)this.mNestedOffsets[1]);
            switch (n2) {
                default: {
                    n3 = n;
                    break;
                }
                case 6: {
                    this.onPointerUp((MotionEvent)object);
                    n3 = n;
                    break;
                }
                case 5: {
                    this.mScrollPointerId = object.getPointerId(n3);
                    this.mLastTouchX = n2 = (int)(object.getX(n3) + 0.5f);
                    this.mInitialTouchX = n2;
                    this.mLastTouchY = n3 = (int)(object.getY(n3) + 0.5f);
                    this.mInitialTouchY = n3;
                    n3 = n;
                    break;
                }
                case 3: {
                    this.cancelTouch();
                    n3 = n;
                    break;
                }
                case 2: {
                    n3 = object.findPointerIndex(this.mScrollPointerId);
                    if (n3 < 0) {
                        object = new StringBuilder();
                        object.append("Error processing scroll; pointer index for id ");
                        object.append(this.mScrollPointerId);
                        object.append(" not found. Did any MotionEvents get skipped?");
                        Log.e((String)TAG, (String)object.toString());
                        return false;
                    }
                    int n4 = (int)(object.getX(n3) + 0.5f);
                    int n5 = (int)(object.getY(n3) + 0.5f);
                    int n6 = this.mLastTouchX - n4;
                    int n7 = this.mLastTouchY - n5;
                    n2 = n6;
                    n3 = n7;
                    if (this.dispatchNestedPreScroll(n6, n7, this.mScrollConsumed, this.mScrollOffset, 0)) {
                        n2 = n6 - this.mScrollConsumed[0];
                        n3 = n7 - this.mScrollConsumed[1];
                        motionEvent.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
                        object = this.mNestedOffsets;
                        object[0] = object[0] + this.mScrollOffset[0];
                        object = this.mNestedOffsets;
                        object[1] = object[1] + this.mScrollOffset[1];
                    }
                    n7 = n2;
                    n6 = n3;
                    if (this.mScrollState != 1) {
                        if (bl && Math.abs(n2) > this.mTouchSlop) {
                            n2 = n2 > 0 ? (n2 -= this.mTouchSlop) : (n2 += this.mTouchSlop);
                            n7 = 1;
                        } else {
                            n7 = 0;
                        }
                        int n8 = n7;
                        int n9 = n3;
                        if (bl2) {
                            n8 = n7;
                            n9 = n3;
                            if (Math.abs(n3) > this.mTouchSlop) {
                                n9 = n3 > 0 ? n3 - this.mTouchSlop : n3 + this.mTouchSlop;
                                n8 = 1;
                            }
                        }
                        n7 = n2;
                        n6 = n9;
                        if (n8 != 0) {
                            this.setScrollState(1);
                            n6 = n9;
                            n7 = n2;
                        }
                    }
                    n3 = n;
                    if (this.mScrollState != 1) break;
                    this.mLastTouchX = n4 - this.mScrollOffset[0];
                    this.mLastTouchY = n5 - this.mScrollOffset[1];
                    n3 = bl ? n7 : 0;
                    n2 = bl2 ? n6 : 0;
                    if (this.scrollByInternal(n3, n2, motionEvent)) {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    n3 = n;
                    if (this.mGapWorker == null) break;
                    if (n7 == 0) {
                        n3 = n;
                        if (n6 == 0) break;
                    }
                    this.mGapWorker.postFromTraversal(this, n7, n6);
                    n3 = n;
                    break;
                }
                case 1: {
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaxFlingVelocity);
                    float f = bl ? - this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                    float f2 = bl2 ? - this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                    if (f == 0.0f && f2 == 0.0f || !this.fling((int)f, (int)f2)) {
                        this.setScrollState(0);
                    }
                    this.resetTouch();
                    n3 = 1;
                    break;
                }
                case 0: {
                    this.mScrollPointerId = object.getPointerId(0);
                    this.mLastTouchX = n3 = (int)(object.getX() + 0.5f);
                    this.mInitialTouchX = n3;
                    this.mLastTouchY = n3 = (int)(object.getY() + 0.5f);
                    this.mInitialTouchY = n3;
                    n3 = bl ? 1 : 0;
                    n2 = n3;
                    if (bl2) {
                        n2 = n3 | 2;
                    }
                    this.startNestedScroll(n2, 0);
                    n3 = n;
                }
            }
            if (n3 == 0) {
                this.mVelocityTracker.addMovement(motionEvent);
            }
            motionEvent.recycle();
            return true;
        }
        return false;
    }

    void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation((View)this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    void processDataSetCompletelyChanged(boolean bl) {
        this.mDispatchItemsChangedEvent = bl | this.mDispatchItemsChangedEvent;
        this.mDataSetHasChangedAfterLayout = true;
        this.markKnownViewsInvalid();
    }

    void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            long l = this.getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l, viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    void removeAndRecycleViews() {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        if (this.mLayout != null) {
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    boolean removeAnimatingView(View object) {
        this.startInterceptRequestLayout();
        boolean bl = this.mChildHelper.removeViewIfHidden((View)object);
        if (bl) {
            object = RecyclerView.getChildViewHolderInt(object);
            this.mRecycler.unscrapView((ViewHolder)object);
            this.mRecycler.recycleViewHolderInternal((ViewHolder)object);
        }
        this.stopInterceptRequestLayout(bl ^ true);
        return bl;
    }

    protected void removeDetachedView(View object, boolean bl) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
        if (viewHolder != null) {
            if (viewHolder.isTmpDetached()) {
                viewHolder.clearTmpDetachFlag();
            } else if (!viewHolder.shouldIgnore()) {
                object = new StringBuilder();
                object.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                object.append(viewHolder);
                object.append(this.exceptionLabel());
                throw new IllegalArgumentException(object.toString());
            }
        }
        object.clearAnimation();
        this.dispatchChildDetached((View)object);
        super.removeDetachedView((View)object, bl);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            boolean bl = this.getOverScrollMode() == 2;
            this.setWillNotDraw(bl);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void removeItemDecorationAt(int n) {
        int n2 = this.getItemDecorationCount();
        if (n >= 0 && n < n2) {
            this.removeItemDecoration(this.getItemDecorationAt(n));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is an invalid index for size ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            return;
        }
        this.mOnChildAttachStateListeners.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove(onScrollListener);
        }
    }

    void repositionShadowingViews() {
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.mChildHelper.getChildAt(i);
            ViewHolder viewHolder = this.getChildViewHolder(view);
            if (viewHolder == null || viewHolder.mShadowingHolder == null) continue;
            viewHolder = viewHolder.mShadowingHolder.itemView;
            int n2 = view.getLeft();
            int n3 = view.getTop();
            if (n2 == viewHolder.getLeft() && n3 == viewHolder.getTop()) continue;
            viewHolder.layout(n2, n3, viewHolder.getWidth() + n2, viewHolder.getHeight() + n3);
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        int n = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n; ++i) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(bl);
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        if (this.mInterceptRequestLayoutDepth == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
            return;
        }
        this.mLayoutWasDefered = true;
    }

    void saveOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.saveOldPosition();
        }
    }

    public void scrollBy(int n, int n2) {
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        boolean bl = this.mLayout.canScrollHorizontally();
        boolean bl2 = this.mLayout.canScrollVertically();
        if (bl || bl2) {
            if (!bl) {
                n = 0;
            }
            if (!bl2) {
                n2 = 0;
            }
            this.scrollByInternal(n, n2, null);
        }
    }

    boolean scrollByInternal(int n, int n2, MotionEvent arrn) {
        int n3;
        int n4;
        int n5;
        int n6;
        this.consumePendingUpdateOperations();
        Adapter adapter = this.mAdapter;
        boolean bl = false;
        if (adapter != null) {
            int n7;
            this.startInterceptRequestLayout();
            this.onEnterLayoutOrScroll();
            TraceCompat.beginSection(TRACE_SCROLL_TAG);
            this.fillRemainingScrollValues(this.mState);
            if (n != 0) {
                n3 = this.mLayout.scrollHorizontallyBy(n, this.mRecycler, this.mState);
                n6 = n - n3;
            } else {
                n6 = n3 = 0;
            }
            if (n2 != 0) {
                n5 = this.mLayout.scrollVerticallyBy(n2, this.mRecycler, this.mState);
                n7 = n2 - n5;
            } else {
                n7 = n5 = 0;
            }
            TraceCompat.endSection();
            this.repositionShadowingViews();
            this.onExitLayoutOrScroll();
            this.stopInterceptRequestLayout(false);
            n4 = n7;
        } else {
            int n8;
            n4 = n3 = (n6 = (n8 = 0));
            n5 = n3;
            n3 = n8;
        }
        if (!this.mItemDecorations.isEmpty()) {
            this.invalidate();
        }
        if (this.dispatchNestedScroll(n3, n5, n6, n4, this.mScrollOffset, 0)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (arrn != null) {
                arrn.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
            }
            arrn = this.mNestedOffsets;
            arrn[0] = arrn[0] + this.mScrollOffset[0];
            arrn = this.mNestedOffsets;
            arrn[1] = arrn[1] + this.mScrollOffset[1];
        } else if (this.getOverScrollMode() != 2) {
            if (arrn != null && !MotionEventCompat.isFromSource((MotionEvent)arrn, 8194)) {
                this.pullGlows(arrn.getX(), n6, arrn.getY(), n4);
            }
            this.considerReleasingGlowsOnScroll(n, n2);
        }
        if (n3 != 0 || n5 != 0) {
            this.dispatchOnScrolled(n3, n5);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        if (n3 != 0 || n5 != 0) {
            bl = true;
        }
        return bl;
    }

    public void scrollTo(int n, int n2) {
        Log.w((String)TAG, (String)"RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        this.stopScroll();
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.scrollToPosition(n);
        this.awakenScrollBars();
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate((View)this, this.mAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, false, true);
        this.processDataSetCompletelyChanged(false);
        this.requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        boolean bl = this.mChildDrawingOrderCallback != null;
        this.setChildrenDrawingOrderEnabled(bl);
    }

    @VisibleForTesting
    boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int n) {
        if (this.isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = n;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, n);
        return true;
    }

    public void setClipToPadding(boolean bl) {
        if (bl != this.mClipToPadding) {
            this.invalidateGlows();
        }
        this.mClipToPadding = bl;
        super.setClipToPadding(bl);
        if (this.mFirstLayoutComplete) {
            this.requestLayout();
        }
    }

    public void setEdgeEffectFactory(@NonNull EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.mEdgeEffectFactory = edgeEffectFactory;
        this.invalidateGlows();
    }

    public void setHasFixedSize(boolean bl) {
        this.mHasFixedSize = bl;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int n) {
        this.mRecycler.setViewCacheSize(n);
    }

    public void setLayoutFrozen(boolean bl) {
        if (bl != this.mLayoutFrozen) {
            this.assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (!bl) {
                this.mLayoutFrozen = false;
                if (this.mLayoutWasDefered && this.mLayout != null && this.mAdapter != null) {
                    this.requestLayout();
                }
                this.mLayoutWasDefered = false;
                return;
            }
            long l = SystemClock.uptimeMillis();
            this.onTouchEvent(MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0));
            this.mLayoutFrozen = true;
            this.mIgnoreMotionEventTillDown = true;
            this.stopScroll();
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager == this.mLayout) {
            return;
        }
        this.stopScroll();
        if (this.mLayout != null) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            this.mRecycler.clear();
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            this.mRecycler.clear();
        }
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("LayoutManager ");
                stringBuilder.append(layoutManager);
                stringBuilder.append(" is already attached to a RecyclerView:");
                stringBuilder.append(layoutManager.mRecyclerView.exceptionLabel());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mLayout.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.dispatchAttachedToWindow(this);
            }
        }
        this.mRecycler.updateViewCacheSize();
        this.requestLayout();
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl) {
        this.getScrollingChildHelper().setNestedScrollingEnabled(bl);
    }

    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean bl) {
        this.mPreserveFocusAfterLayout = bl;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    void setScrollState(int n) {
        if (n == this.mScrollState) {
            return;
        }
        this.mScrollState = n;
        if (n != 2) {
            this.stopScrollersInternal();
        }
        this.dispatchOnScrollStateChanged(n);
    }

    public void setScrollingTouchSlop(int n) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
                stringBuilder.append(n);
                stringBuilder.append("; using default value");
                Log.w((String)TAG, (String)stringBuilder.toString());
                break;
            }
            case 1: {
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            case 0: 
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.isComputingLayout()) {
            int n = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
            int n2 = n;
            if (n == 0) {
                n2 = 0;
            }
            this.mEatenAccessibilityChangeFlags = n2 | this.mEatenAccessibilityChangeFlags;
            return true;
        }
        return false;
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, null);
    }

    public void smoothScrollBy(int n, int n2, Interpolator interpolator) {
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        if (!this.mLayout.canScrollHorizontally()) {
            n = 0;
        }
        if (!this.mLayout.canScrollVertically()) {
            n2 = 0;
        }
        if (n != 0 || n2 != 0) {
            this.mViewFlinger.smoothScrollBy(n, n2, interpolator);
        }
    }

    public void smoothScrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.smoothScrollToPosition(this, this.mState, n);
    }

    void startInterceptRequestLayout() {
        ++this.mInterceptRequestLayoutDepth;
        if (this.mInterceptRequestLayoutDepth == 1 && !this.mLayoutFrozen) {
            this.mLayoutWasDefered = false;
        }
    }

    @Override
    public boolean startNestedScroll(int n) {
        return this.getScrollingChildHelper().startNestedScroll(n);
    }

    @Override
    public boolean startNestedScroll(int n, int n2) {
        return this.getScrollingChildHelper().startNestedScroll(n, n2);
    }

    void stopInterceptRequestLayout(boolean bl) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!bl && !this.mLayoutFrozen) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (bl && this.mLayoutWasDefered && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                this.dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutWasDefered = false;
            }
        }
        --this.mInterceptRequestLayoutDepth;
    }

    @Override
    public void stopNestedScroll() {
        this.getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int n) {
        this.getScrollingChildHelper().stopNestedScroll(n);
    }

    public void stopScroll() {
        this.setScrollState(0);
        this.stopScrollersInternal();
    }

    public void swapAdapter(Adapter adapter, boolean bl) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, true, bl);
        this.processDataSetCompletelyChanged(true);
        this.requestLayout();
    }

    void viewRangeUpdate(int n, int n2, Object object) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            View view = this.mChildHelper.getUnfilteredChildAt(i);
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n || viewHolder.mPosition >= n + n2) continue;
            viewHolder.addFlags(2);
            viewHolder.addChangePayload(object);
            ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.viewRangeUpdate(n, n2);
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public final void bindViewHolder(@NonNull VH object, int n) {
            object.mPosition = n;
            if (this.hasStableIds()) {
                object.mItemId = this.getItemId(n);
            }
            object.setFlags(1, 519);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            this.onBindViewHolder(object, n, object.getUnmodifiedPayloads());
            object.clearPayload();
            object = object.itemView.getLayoutParams();
            if (object instanceof LayoutParams) {
                ((LayoutParams)object).mInsetsDirty = true;
            }
            TraceCompat.endSection();
        }

        public final VH createViewHolder(@NonNull ViewGroup object, int n) {
            try {
                TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
                object = this.onCreateViewHolder((ViewGroup)object, n);
                if (object.itemView.getParent() != null) {
                    throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                }
                object.mItemViewType = n;
                return (VH)object;
            }
            finally {
                TraceCompat.endSection();
            }
        }

        public abstract int getItemCount();

        public long getItemId(int n) {
            return -1L;
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int n) {
            this.mObservable.notifyItemRangeChanged(n, 1);
        }

        public final void notifyItemChanged(int n, @Nullable Object object) {
            this.mObservable.notifyItemRangeChanged(n, 1, object);
        }

        public final void notifyItemInserted(int n) {
            this.mObservable.notifyItemRangeInserted(n, 1);
        }

        public final void notifyItemMoved(int n, int n2) {
            this.mObservable.notifyItemMoved(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2) {
            this.mObservable.notifyItemRangeChanged(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2, @Nullable Object object) {
            this.mObservable.notifyItemRangeChanged(n, n2, object);
        }

        public final void notifyItemRangeInserted(int n, int n2) {
            this.mObservable.notifyItemRangeInserted(n, n2);
        }

        public final void notifyItemRangeRemoved(int n, int n2) {
            this.mObservable.notifyItemRangeRemoved(n, n2);
        }

        public final void notifyItemRemoved(int n) {
            this.mObservable.notifyItemRangeRemoved(n, 1);
        }

        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(@NonNull VH var1, int var2);

        public void onBindViewHolder(@NonNull VH VH, int n, @NonNull List<Object> list) {
            this.onBindViewHolder(VH, n);
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup var1, int var2);

        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(@NonNull VH VH) {
            return false;
        }

        public void onViewAttachedToWindow(@NonNull VH VH) {
        }

        public void onViewDetachedFromWindow(@NonNull VH VH) {
        }

        public void onViewRecycled(@NonNull VH VH) {
        }

        public void registerAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver((Object)adapterDataObserver);
        }

        public void setHasStableIds(boolean bl) {
            if (this.hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = bl;
        }

        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver((Object)adapterDataObserver);
        }
    }

    static class AdapterDataObservable
    extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return this.mObservers.isEmpty() ^ true;
        }

        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onChanged();
            }
        }

        public void notifyItemMoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(n, n2, 1);
            }
        }

        public void notifyItemRangeChanged(int n, int n2) {
            this.notifyItemRangeChanged(n, n2, null);
        }

        public void notifyItemRangeChanged(int n, int n2, @Nullable Object object) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(n, n2, object);
            }
        }

        public void notifyItemRangeInserted(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(n, n2);
            }
        }

        public void notifyItemRangeRemoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(n, n2);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int n, int n2) {
        }

        public void onItemRangeChanged(int n, int n2, @Nullable Object object) {
            this.onItemRangeChanged(n, n2);
        }

        public void onItemRangeInserted(int n, int n2) {
        }

        public void onItemRangeMoved(int n, int n2, int n3) {
        }

        public void onItemRangeRemoved(int n, int n2) {
        }
    }

    public static interface ChildDrawingOrderCallback {
        public int onGetChildDrawingOrder(int var1, int var2);
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        @NonNull
        protected EdgeEffect createEdgeEffect(RecyclerView recyclerView, int n) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EdgeEffectFactory$EdgeDirection {
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120L;
        private long mChangeDuration = 250L;
        private ArrayList<ItemAnimator$ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimator$ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250L;
        private long mRemoveDuration = 120L;

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int n = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            int n2 = n;
            if ((n & 4) == 0) {
                int n3 = viewHolder.getOldPosition();
                int n4 = viewHolder.getAdapterPosition();
                n2 = n;
                if (n3 != -1) {
                    n2 = n;
                    if (n4 != -1) {
                        n2 = n;
                        if (n3 != n4) {
                            n2 = n | 2048;
                        }
                    }
                }
            }
            return n2;
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder var1, @Nullable ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

        public abstract boolean animateChange(@NonNull ViewHolder var1, @NonNull ViewHolder var2, @NonNull ItemHolderInfo var3, @NonNull ItemHolderInfo var4);

        public abstract boolean animateDisappearance(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @Nullable ItemHolderInfo var3);

        public abstract boolean animatePersistence(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return this.canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            this.onAnimationFinished(viewHolder);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished(viewHolder);
            }
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            this.onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int n = this.mFinishedListeners.size();
            for (int i = 0; i < n; ++i) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimation(ViewHolder var1);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimator$ItemAnimatorFinishedListener itemAnimator$ItemAnimatorFinishedListener) {
            boolean bl = this.isRunning();
            if (itemAnimator$ItemAnimatorFinishedListener != null) {
                if (!bl) {
                    itemAnimator$ItemAnimatorFinishedListener.onAnimationsFinished();
                    return bl;
                }
                this.mFinishedListeners.add(itemAnimator$ItemAnimatorFinishedListener);
            }
            return bl;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int n, @NonNull List<Object> list) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long l) {
            this.mAddDuration = l;
        }

        public void setChangeDuration(long l) {
            this.mChangeDuration = l;
        }

        void setListener(ItemAnimator$ItemAnimatorListener itemAnimator$ItemAnimatorListener) {
            this.mListener = itemAnimator$ItemAnimatorListener;
        }

        public void setMoveDuration(long l) {
            this.mMoveDuration = l;
        }

        public void setRemoveDuration(long l) {
            this.mRemoveDuration = l;
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return this.setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int n) {
                viewHolder = viewHolder.itemView;
                this.left = viewHolder.getLeft();
                this.top = viewHolder.getTop();
                this.right = viewHolder.getRight();
                this.bottom = viewHolder.getBottom();
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ItemAnimator$AdapterChanges {
    }

    public static interface ItemAnimator$ItemAnimatorFinishedListener {
        public void onAnimationsFinished();
    }

    static interface ItemAnimator$ItemAnimatorListener {
        public void onAnimationFinished(ViewHolder var1);
    }

    private class ItemAnimatorRestoreListener
    implements ItemAnimator$ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int n, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            this.getItemOffsets(rect, ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
        private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return LayoutManager.this.getChildCount();
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedRight(view) + layoutParams.rightMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedLeft(view) - layoutParams.leftMargin;
            }

            @Override
            public View getParent() {
                return LayoutManager.this.mRecyclerView;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingLeft();
            }
        };
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        @Nullable
        SmoothScroller mSmoothScroller;
        ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
        private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return LayoutManager.this.getChildCount();
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedBottom(view) + layoutParams.bottomMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedTop(view) - layoutParams.topMargin;
            }

            @Override
            public View getParent() {
                return LayoutManager.this.mRecyclerView;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingTop();
            }
        };
        private int mWidth;
        private int mWidthMode;

        private void addViewInt(View view, int n, boolean bl) {
            Object object = RecyclerView.getChildViewHolderInt(view);
            if (!bl && !object.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout((ViewHolder)object);
            } else {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout((ViewHolder)object);
            }
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!object.wasReturnedFromScrap() && !object.isScrap()) {
                if (view.getParent() == this.mRecyclerView) {
                    int n2 = this.mChildHelper.indexOfChild(view);
                    int n3 = n;
                    if (n == -1) {
                        n3 = this.mChildHelper.getChildCount();
                    }
                    if (n2 == -1) {
                        object = new StringBuilder();
                        object.append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                        object.append(this.mRecyclerView.indexOfChild(view));
                        object.append(this.mRecyclerView.exceptionLabel());
                        throw new IllegalStateException(object.toString());
                    }
                    if (n2 != n3) {
                        this.mRecyclerView.mLayout.moveView(n2, n3);
                    }
                } else {
                    this.mChildHelper.addView(view, n, false);
                    layoutParams.mInsetsDirty = true;
                    if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                        this.mSmoothScroller.onChildAttachedToWindow(view);
                    }
                }
            } else {
                if (object.isScrap()) {
                    object.unScrap();
                } else {
                    object.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, n, view.getLayoutParams(), false);
            }
            if (layoutParams.mPendingInvalidate) {
                object.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public static int chooseSize(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode((int)n);
            n = View.MeasureSpec.getSize((int)n);
            if (n4 != Integer.MIN_VALUE) {
                if (n4 != 1073741824) {
                    return Math.max(n2, n3);
                }
                return n;
            }
            return Math.min(n, Math.max(n2, n3));
        }

        private void detachViewInternal(int n, View view) {
            this.mChildHelper.detachViewFromParent(n);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int getChildMeasureSpec(int n, int n2, int n3, int n4, boolean bl) {
            int n5;
            block10 : {
                int n6;
                block11 : {
                    block9 : {
                        block8 : {
                            n5 = 0;
                            n6 = Math.max(0, n - n3);
                            if (!bl) break block8;
                            if (n4 >= 0) break block9;
                            if (n4 == -1) {
                                n = n2 != Integer.MIN_VALUE && (n2 == 0 || n2 != 1073741824) ? (n2 = 0) : n6;
                                n3 = n;
                                n = n2;
                                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                            }
                            break block10;
                        }
                        if (n4 < 0) break block11;
                    }
                    n3 = n4;
                    n = 1073741824;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                if (n4 == -1) {
                    n = n2;
                    n3 = n6;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                if (n4 == -2) {
                    if (n2 != Integer.MIN_VALUE) {
                        n3 = n6;
                        n = n5;
                        if (n2 != 1073741824) return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                    }
                    n = Integer.MIN_VALUE;
                    n3 = n6;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
            }
            n3 = 0;
            n = n5;
            return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Deprecated
        public static int getChildMeasureSpec(int n, int n2, int n3, boolean bl) {
            block10 : {
                int n4;
                block5 : {
                    block9 : {
                        block8 : {
                            block7 : {
                                block6 : {
                                    block4 : {
                                        n4 = 0;
                                        n = Math.max(0, n - n2);
                                        if (!bl) break block4;
                                        if (n3 < 0) break block5;
                                        break block6;
                                    }
                                    if (n3 < 0) break block7;
                                }
                                n = n3;
                                break block8;
                            }
                            if (n3 != -1) break block9;
                        }
                        n2 = 1073741824;
                        return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
                    }
                    if (n3 == -2) break block10;
                }
                n = 0;
                n2 = n4;
                return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
            }
            n2 = Integer.MIN_VALUE;
            return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
        }

        private int[] getChildRectangleOnScreenScrollAmount(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingTop();
            int n3 = this.getWidth();
            int n4 = this.getPaddingRight();
            int n5 = this.getHeight();
            int n6 = this.getPaddingBottom();
            int n7 = view.getLeft() + rect.left - view.getScrollX();
            int n8 = view.getTop() + rect.top - view.getScrollY();
            int n9 = rect.width();
            int n10 = rect.height();
            int n11 = n7 - n;
            n = Math.min(0, n11);
            int n12 = n8 - n2;
            n2 = Math.min(0, n12);
            n4 = n9 + n7 - (n3 - n4);
            n3 = Math.max(0, n4);
            n5 = Math.max(0, n10 + n8 - (n5 - n6));
            if (this.getLayoutDirection() == 1) {
                n = n3 != 0 ? n3 : Math.max(n, n4);
            } else if (n == 0) {
                n = Math.min(n11, n3);
            }
            if (n2 == 0) {
                n2 = Math.min(n12, n5);
            }
            return new int[]{n, n2};
        }

        public static LayoutManager$Properties getProperties(Context context, AttributeSet attributeSet, int n, int n2) {
            LayoutManager$Properties layoutManager$Properties = new LayoutManager$Properties();
            context = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, n2);
            layoutManager$Properties.orientation = context.getInt(R.styleable.RecyclerView_android_orientation, 1);
            layoutManager$Properties.spanCount = context.getInt(R.styleable.RecyclerView_spanCount, 1);
            layoutManager$Properties.reverseLayout = context.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            layoutManager$Properties.stackFromEnd = context.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            context.recycle();
            return layoutManager$Properties;
        }

        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int n, int n2) {
            if ((recyclerView = recyclerView.getFocusedChild()) == null) {
                return false;
            }
            int n3 = this.getPaddingLeft();
            int n4 = this.getPaddingTop();
            int n5 = this.getWidth();
            int n6 = this.getPaddingRight();
            int n7 = this.getHeight();
            int n8 = this.getPaddingBottom();
            Rect rect = this.mRecyclerView.mTempRect;
            this.getDecoratedBoundsWithMargins((View)recyclerView, rect);
            if (rect.left - n < n5 - n6 && rect.right - n > n3 && rect.top - n2 < n7 - n8) {
                if (rect.bottom - n2 <= n4) {
                    return false;
                }
                return true;
            }
            return false;
        }

        private static boolean isMeasurementUpToDate(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode((int)n2);
            n2 = View.MeasureSpec.getSize((int)n2);
            boolean bl = false;
            boolean bl2 = false;
            if (n3 > 0 && n != n3) {
                return false;
            }
            if (n4 != Integer.MIN_VALUE) {
                if (n4 != 0) {
                    if (n4 != 1073741824) {
                        return false;
                    }
                    if (n2 == n) {
                        bl2 = true;
                    }
                    return bl2;
                }
                return true;
            }
            bl2 = bl;
            if (n2 >= n) {
                bl2 = true;
            }
            return bl2;
        }

        private void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int n, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) {
                return;
            }
            if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                this.removeViewAt(n);
                recycler.recycleViewHolderInternal(viewHolder);
                return;
            }
            this.detachViewAt(n);
            recycler.scrapView(view);
            this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
        }

        public void addDisappearingView(View view) {
            this.addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int n) {
            this.addViewInt(view, n, true);
        }

        public void addView(View view) {
            this.addView(view, -1);
        }

        public void addView(View view, int n) {
            this.addViewInt(view, n, false);
        }

        public void assertInLayoutOrScroll(String string) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll(string);
            }
        }

        public void assertNotInLayoutOrScroll(String string) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(string);
            }
        }

        public void attachView(View view) {
            this.attachView(view, -1);
        }

        public void attachView(View view, int n) {
            this.attachView(view, n, (LayoutParams)view.getLayoutParams());
        }

        public void attachView(View view, int n, LayoutParams layoutParams) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            this.mChildHelper.attachViewToParent(view, n, (ViewGroup.LayoutParams)layoutParams, viewHolder.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            if (this.mRecyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            if (layoutParams != null) {
                return true;
            }
            return false;
        }

        public void collectAdjacentPrefetchPositions(int n, int n2, State state, LayoutManager$LayoutPrefetchRegistry layoutManager$LayoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int n, LayoutManager$LayoutPrefetchRegistry layoutManager$LayoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                this.scrapOrRecycleView(recycler, i, this.getChildAt(i));
            }
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            this.scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
        }

        public void detachAndScrapViewAt(int n, Recycler recycler) {
            this.scrapOrRecycleView(recycler, n, this.getChildAt(n));
        }

        public void detachView(View view) {
            int n = this.mChildHelper.indexOfChild(view);
            if (n >= 0) {
                this.detachViewInternal(n, view);
            }
        }

        public void detachViewAt(int n) {
            this.detachViewInternal(n, this.getChildAt(n));
        }

        void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            this.onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            this.onDetachedFromWindow(recyclerView, recycler);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
            }
        }

        @Nullable
        public View findContainingItemView(View view) {
            if (this.mRecyclerView == null) {
                return null;
            }
            if ((view = this.mRecyclerView.findContainingItemView(view)) == null) {
                return null;
            }
            if (this.mChildHelper.isHidden(view)) {
                return null;
            }
            return view;
        }

        public View findViewByPosition(int n) {
            int n2 = this.getChildCount();
            for (int i = 0; i < n2; ++i) {
                View view = this.getChildAt(i);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder == null || viewHolder.getLayoutPosition() != n || viewHolder.shouldIgnore() || !this.mRecyclerView.mState.isPreLayout() && viewHolder.isRemoved()) continue;
                return view;
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
        }

        public View getChildAt(int n) {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildAt(n);
            }
            return null;
        }

        public int getChildCount() {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildCount();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            if (this.mRecyclerView != null && this.mRecyclerView.mClipToPadding) {
                return true;
            }
            return false;
        }

        public int getColumnCountForAccessibility(Recycler object, State state) {
            object = this.mRecyclerView;
            int n = 1;
            if (object != null) {
                if (this.mRecyclerView.mAdapter == null) {
                    return 1;
                }
                if (this.canScrollHorizontally()) {
                    n = this.mRecyclerView.mAdapter.getItemCount();
                }
                return n;
            }
            return 1;
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + this.getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - this.getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + this.getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - this.getTopDecorationHeight(view);
        }

        public View getFocusedChild() {
            if (this.mRecyclerView == null) {
                return null;
            }
            View view = this.mRecyclerView.getFocusedChild();
            if (view != null) {
                if (this.mChildHelper.isHidden(view)) {
                    return null;
                }
                return view;
            }
            return null;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getItemCount() {
            Adapter adapter = this.mRecyclerView != null ? this.mRecyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.left;
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
        }

        public int getPaddingBottom() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingEnd() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingEnd((View)this.mRecyclerView);
            }
            return 0;
        }

        public int getPaddingLeft() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingStart() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingStart((View)this.mRecyclerView);
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(View view) {
            return ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.right;
        }

        public int getRowCountForAccessibility(Recycler object, State state) {
            object = this.mRecyclerView;
            int n = 1;
            if (object != null) {
                if (this.mRecyclerView.mAdapter == null) {
                    return 1;
                }
                if (this.canScrollVertically()) {
                    n = this.mRecyclerView.mAdapter.getItemCount();
                }
                return n;
            }
            return 1;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.top;
        }

        public void getTransformedBoundingBox(View view, boolean bl, Rect rect) {
            Rect rect2;
            if (bl) {
                rect2 = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
                rect.set(- rect2.left, - rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.mRecyclerView != null && (rect2 = view.getMatrix()) != null && !rect2.isIdentity()) {
                RectF rectF = this.mRecyclerView.mTempRectF;
                rectF.set(rect);
                rect2.mapRect(rectF);
                rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewGroup.LayoutParams layoutParams = this.getChildAt(i).getLayoutParams();
                if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
                return true;
            }
            return false;
        }

        public boolean hasFocus() {
            if (this.mRecyclerView != null && this.mRecyclerView.hasFocus()) {
                return true;
            }
            return false;
        }

        public void ignoreView(View object) {
            if (object.getParent() == this.mRecyclerView && this.mRecyclerView.indexOfChild((View)object) != -1) {
                object = RecyclerView.getChildViewHolderInt((View)object);
                object.addFlags(128);
                this.mRecyclerView.mViewInfoStore.removeViewHolder((ViewHolder)object);
                return;
            }
            object = new StringBuilder();
            object.append("View should be fully attached to be ignored");
            object.append(this.mRecyclerView.exceptionLabel());
            throw new IllegalArgumentException(object.toString());
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean isFocused() {
            if (this.mRecyclerView != null && this.mRecyclerView.isFocused()) {
                return true;
            }
            return false;
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                return true;
            }
            return false;
        }

        public boolean isViewPartiallyVisible(@NonNull View view, boolean bl, boolean bl2) {
            bl2 = this.mHorizontalBoundCheck.isViewWithinBoundFlags(view, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(view, 24579);
            if (bl) {
                return bl2;
            }
            return bl2 ^ true;
        }

        public void layoutDecorated(View view, int n, int n2, int n3, int n4) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            view.layout(n + rect.left, n2 + rect.top, n3 - rect.right, n4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int n, int n2, int n3, int n4) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(n + rect.left + layoutParams.leftMargin, n2 + rect.top + layoutParams.topMargin, n3 - rect.right - layoutParams.rightMargin, n4 - rect.bottom - layoutParams.bottomMargin);
        }

        public void measureChild(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n, n2);
            }
        }

        public void measureChildWithMargins(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n, n2);
            }
        }

        public void moveView(int n, int n2) {
            Object object = this.getChildAt(n);
            if (object == null) {
                object = new StringBuilder();
                object.append("Cannot move a child from non-existing index:");
                object.append(n);
                object.append(this.mRecyclerView.toString());
                throw new IllegalArgumentException(object.toString());
            }
            this.detachViewAt(n);
            this.attachView((View)object, n2);
        }

        public void offsetChildrenHorizontal(int n) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(n);
            }
        }

        public void offsetChildrenVertical(int n) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(n);
            }
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n, int n2) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.onDetachedFromWindow(recyclerView);
        }

        @Nullable
        public View onFocusSearchFailed(View view, int n, Recycler recycler, State state) {
            return null;
        }

        public void onInitializeAccessibilityEvent(Recycler object, State state, AccessibilityEvent accessibilityEvent) {
            if (this.mRecyclerView != null) {
                boolean bl;
                if (accessibilityEvent == null) {
                    return;
                }
                object = this.mRecyclerView;
                boolean bl2 = bl = true;
                if (!object.canScrollVertically(1)) {
                    bl2 = bl;
                    if (!this.mRecyclerView.canScrollVertically(-1)) {
                        bl2 = bl;
                        if (!this.mRecyclerView.canScrollHorizontally(-1)) {
                            bl2 = this.mRecyclerView.canScrollHorizontally(1) ? bl : false;
                        }
                    }
                }
                accessibilityEvent.setScrollable(bl2);
                if (this.mRecyclerView.mAdapter != null) {
                    accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
                return;
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            boolean bl = this.canScrollVertically();
            int n = 0;
            int n2 = bl ? this.getPosition(view) : 0;
            if (this.canScrollHorizontally()) {
                n = this.getPosition(view);
            }
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, 1, n, 1, false, false));
        }

        void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView)) {
                this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
            }
        }

        public View onInterceptFocusSearch(View view, int n) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
            this.onItemsUpdated(recyclerView, n, n2);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e((String)"RecyclerView", (String)"You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(Recycler recycler, State state, int n, int n2) {
            this.mRecyclerView.defaultOnMeasure(n, n2);
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return this.onRequestChildFocus(recyclerView, view, view2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            if (!this.isSmoothScrolling() && !recyclerView.isComputingLayout()) {
                return false;
            }
            return true;
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int n) {
        }

        boolean performAccessibilityAction(int n, Bundle bundle) {
            return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n, bundle);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean performAccessibilityAction(Recycler var1_1, State var2_2, int var3_3, Bundle var4_4) {
            block6 : {
                block4 : {
                    block5 : {
                        if (this.mRecyclerView == null) {
                            return false;
                        }
                        if (var3_3 == 4096) break block4;
                        if (var3_3 == 8192) break block5;
                        var5_5 = var3_3 = 0;
                        break block6;
                    }
                    var3_3 = this.mRecyclerView.canScrollVertically(-1) != false ? - this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : 0;
                    var6_6 = var3_3;
                    if (!this.mRecyclerView.canScrollHorizontally(-1)) ** GOTO lbl-1000
                    var5_5 = - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                    break block6;
                }
                var3_3 = this.mRecyclerView.canScrollVertically(1) != false ? this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : 0;
                var6_6 = var3_3;
                if (this.mRecyclerView.canScrollHorizontally(1)) {
                    var5_5 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                } else lbl-1000: // 2 sources:
                {
                    var5_5 = 0;
                    var3_3 = var6_6;
                }
            }
            if (var3_3 == 0 && var5_5 == 0) {
                return false;
            }
            this.mRecyclerView.scrollBy(var5_5, var3_3);
            return true;
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int n, Bundle bundle) {
            return false;
        }

        boolean performAccessibilityActionForItem(View view, int n, Bundle bundle) {
            return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n, bundle);
        }

        public void postOnAnimation(Runnable runnable) {
            if (this.mRecyclerView != null) {
                ViewCompat.postOnAnimation((View)this.mRecyclerView, runnable);
            }
        }

        public void removeAllViews() {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                this.mChildHelper.removeViewAt(i);
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                if (RecyclerView.getChildViewHolderInt(this.getChildAt(i)).shouldIgnore()) continue;
                this.removeAndRecycleViewAt(i, recycler);
            }
        }

        void removeAndRecycleScrapInt(Recycler recycler) {
            int n = recycler.getScrapCount();
            for (int i = n - 1; i >= 0; --i) {
                View view = recycler.getScrapViewAt(i);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder.shouldIgnore()) continue;
                viewHolder.setIsRecyclable(false);
                if (viewHolder.isTmpDetached()) {
                    this.mRecyclerView.removeDetachedView(view, false);
                }
                if (this.mRecyclerView.mItemAnimator != null) {
                    this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
                }
                viewHolder.setIsRecyclable(true);
                recycler.quickRecycleScrapView(view);
            }
            recycler.clearScrap();
            if (n > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            this.removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int n, Recycler recycler) {
            View view = this.getChildAt(n);
            this.removeViewAt(n);
            recycler.recycleView(view);
        }

        public boolean removeCallbacks(Runnable runnable) {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int n) {
            if (this.getChildAt(n) != null) {
                this.mChildHelper.removeViewAt(n);
            }
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            return this.requestChildRectangleOnScreen(recyclerView, view, rect, bl, false);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View arrn, Rect rect, boolean bl, boolean bl2) {
            arrn = this.getChildRectangleOnScreenScrollAmount(recyclerView, (View)arrn, rect, bl);
            int n = arrn[0];
            int n2 = arrn[1];
            if (bl2 && !this.isFocusedChildVisibleAfterScrolling(recyclerView, n, n2) || n == 0 && n2 == 0) {
                return false;
            }
            if (bl) {
                recyclerView.scrollBy(n, n2);
                return true;
            }
            recyclerView.smoothScrollBy(n, n2);
            return true;
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int n) {
        }

        public int scrollVerticallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean bl) {
            this.mAutoMeasure = bl;
        }

        void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)1073741824));
        }

        public final void setItemPrefetchEnabled(boolean bl) {
            if (bl != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = bl;
                this.mPrefetchMaxCountObserved = 0;
                if (this.mRecyclerView != null) {
                    this.mRecyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        void setMeasureSpecs(int n, int n2) {
            this.mWidth = View.MeasureSpec.getSize((int)n);
            this.mWidthMode = View.MeasureSpec.getMode((int)n);
            if (this.mWidthMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize((int)n2);
            this.mHeightMode = View.MeasureSpec.getMode((int)n2);
            if (this.mHeightMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        public void setMeasuredDimension(int n, int n2) {
            this.mRecyclerView.setMeasuredDimension(n, n2);
        }

        public void setMeasuredDimension(Rect rect, int n, int n2) {
            int n3 = rect.width();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = rect.height();
            int n7 = this.getPaddingTop();
            int n8 = this.getPaddingBottom();
            this.setMeasuredDimension(LayoutManager.chooseSize(n, n3 + n4 + n5, this.getMinimumWidth()), LayoutManager.chooseSize(n2, n6 + n7 + n8, this.getMinimumHeight()));
        }

        void setMeasuredDimensionFromChildren(int n, int n2) {
            int n3;
            int n4 = this.getChildCount();
            if (n4 == 0) {
                this.mRecyclerView.defaultOnMeasure(n, n2);
                return;
            }
            int n5 = Integer.MAX_VALUE;
            int n6 = n3 = Integer.MIN_VALUE;
            int n7 = Integer.MAX_VALUE;
            for (int i = 0; i < n4; ++i) {
                View view = this.getChildAt(i);
                Rect rect = this.mRecyclerView.mTempRect;
                this.getDecoratedBoundsWithMargins(view, rect);
                int n8 = n5;
                if (rect.left < n5) {
                    n8 = rect.left;
                }
                int n9 = n3;
                if (rect.right > n3) {
                    n9 = rect.right;
                }
                n3 = n7;
                if (rect.top < n7) {
                    n3 = rect.top;
                }
                int n10 = n6;
                if (rect.bottom > n6) {
                    n10 = rect.bottom;
                }
                n7 = n3;
                n5 = n8;
                n3 = n9;
                n6 = n10;
            }
            this.mRecyclerView.mTempRect.set(n5, n7, n3, n6);
            this.setMeasuredDimension(this.mRecyclerView.mTempRect, n, n2);
        }

        public void setMeasurementCacheEnabled(boolean bl) {
            this.mMeasurementCacheEnabled = bl;
        }

        void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        boolean shouldMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            if (!view.isLayoutRequested() && this.mMeasurementCacheEnabled && LayoutManager.isMeasurementUpToDate(view.getWidth(), n, layoutParams.width) && LayoutManager.isMeasurementUpToDate(view.getHeight(), n2, layoutParams.height)) {
                return false;
            }
            return true;
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean shouldReMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            if (this.mMeasurementCacheEnabled && LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n, layoutParams.width) && LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n2, layoutParams.height)) {
                return false;
            }
            return true;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int n) {
            Log.e((String)"RecyclerView", (String)"You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller != null && smoothScroller != this.mSmoothScroller && this.mSmoothScroller.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public void stopIgnoringView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            object.stopIgnoring();
            object.resetInternal();
            object.addFlags(4);
        }

        void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

    }

    public static interface LayoutManager$LayoutPrefetchRegistry {
        public void addPosition(int var1, int var2);
    }

    public static class LayoutManager$Properties {
        public int orientation;
        public boolean reverseLayout;
        public int spanCount;
        public boolean stackFromEnd;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        @Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }
    }

    public static interface OnChildAttachStateChangeListener {
        public void onChildViewAttachedToWindow(View var1);

        public void onChildViewDetachedFromWindow(View var1);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int var1, int var2);
    }

    public static interface OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

        public void onRequestDisallowInterceptTouchEvent(boolean var1);

        public void onTouchEvent(RecyclerView var1, MotionEvent var2);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        }

        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface Orientation {
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        SparseArray<RecycledViewPool$ScrapData> mScrap = new SparseArray();

        private RecycledViewPool$ScrapData getScrapDataForType(int n) {
            RecycledViewPool$ScrapData recycledViewPool$ScrapData;
            RecycledViewPool$ScrapData recycledViewPool$ScrapData2 = recycledViewPool$ScrapData = (RecycledViewPool$ScrapData)this.mScrap.get(n);
            if (recycledViewPool$ScrapData == null) {
                recycledViewPool$ScrapData2 = new RecycledViewPool$ScrapData();
                this.mScrap.put(n, (Object)recycledViewPool$ScrapData2);
            }
            return recycledViewPool$ScrapData2;
        }

        void attach(Adapter adapter) {
            ++this.mAttachCount;
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); ++i) {
                ((RecycledViewPool$ScrapData)this.mScrap.valueAt((int)i)).mScrapHeap.clear();
            }
        }

        void detach() {
            --this.mAttachCount;
        }

        void factorInBindTime(int n, long l) {
            RecycledViewPool$ScrapData recycledViewPool$ScrapData = this.getScrapDataForType(n);
            recycledViewPool$ScrapData.mBindRunningAverageNs = this.runningAverage(recycledViewPool$ScrapData.mBindRunningAverageNs, l);
        }

        void factorInCreateTime(int n, long l) {
            RecycledViewPool$ScrapData recycledViewPool$ScrapData = this.getScrapDataForType(n);
            recycledViewPool$ScrapData.mCreateRunningAverageNs = this.runningAverage(recycledViewPool$ScrapData.mCreateRunningAverageNs, l);
        }

        @Nullable
        public ViewHolder getRecycledView(int n) {
            Object object = (RecycledViewPool$ScrapData)this.mScrap.get(n);
            if (object != null && !object.mScrapHeap.isEmpty()) {
                object = object.mScrapHeap;
                return (ViewHolder)object.remove(object.size() - 1);
            }
            return null;
        }

        public int getRecycledViewCount(int n) {
            return this.getScrapDataForType((int)n).mScrapHeap.size();
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            if (adapter != null) {
                this.detach();
            }
            if (!bl && this.mAttachCount == 0) {
                this.clear();
            }
            if (adapter2 != null) {
                this.attach(adapter2);
            }
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int n = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = this.getScrapDataForType((int)n).mScrapHeap;
            if (((RecycledViewPool$ScrapData)this.mScrap.get((int)n)).mMaxScrap <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        long runningAverage(long l, long l2) {
            if (l == 0L) {
                return l2;
            }
            return l / 4L * 3L + l2 / 4L;
        }

        public void setMaxRecycledViews(int n, int n2) {
            Object object = this.getScrapDataForType(n);
            object.mMaxScrap = n2;
            object = object.mScrapHeap;
            while (object.size() > n2) {
                object.remove(object.size() - 1);
            }
        }

        int size() {
            int n = 0;
            for (int i = 0; i < this.mScrap.size(); ++i) {
                ArrayList<ViewHolder> arrayList = ((RecycledViewPool$ScrapData)this.mScrap.valueAt((int)i)).mScrapHeap;
                int n2 = n;
                if (arrayList != null) {
                    n2 = n + arrayList.size();
                }
                n = n2;
            }
            return n;
        }

        boolean willBindInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mBindRunningAverageNs;
            if (l3 != 0L && l + l3 >= l2) {
                return false;
            }
            return true;
        }

        boolean willCreateInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mCreateRunningAverageNs;
            if (l3 != 0L && l + l3 >= l2) {
                return false;
            }
            return true;
        }
    }

    static class RecycledViewPool$ScrapData {
        long mBindRunningAverageNs = 0L;
        long mCreateRunningAverageNs = 0L;
        int mMaxScrap = 5;
        final ArrayList<ViewHolder> mScrapHeap = new ArrayList();

        RecycledViewPool$ScrapData() {
        }
    }

    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax = 2;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax = 2;

        private void attachAccessibilityDelegateOnBind(ViewHolder viewHolder) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                View view = viewHolder.itemView;
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view)) {
                    viewHolder.addFlags(16384);
                    ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl) {
            int n;
            for (n = viewGroup.getChildCount() - 1; n >= 0; --n) {
                View view = viewGroup.getChildAt(n);
                if (!(view instanceof ViewGroup)) continue;
                this.invalidateDisplayListInt((ViewGroup)view, true);
            }
            if (!bl) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            n = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(n);
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder viewHolder, int n, int n2, long l) {
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            int n3 = viewHolder.getItemViewType();
            long l2 = RecyclerView.this.getNanoTime();
            if (l != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(n3, l2, l)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n);
            l = RecyclerView.this.getNanoTime();
            this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), l - l2);
            this.attachAccessibilityDelegateOnBind(viewHolder);
            if (RecyclerView.this.mState.isPreLayout()) {
                viewHolder.mPreLayoutPosition = n2;
            }
            return true;
        }

        void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean bl) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            if (viewHolder.hasAnyOfTheFlags(16384)) {
                viewHolder.setFlags(0, 16384);
                ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            }
            if (bl) {
                this.dispatchViewRecycled(viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            this.getRecycledViewPool().putRecycledView(viewHolder);
        }

        public void bindViewToPosition(View object, int n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
            if (viewHolder == null) {
                object = new StringBuilder();
                object.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
                object.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(object.toString());
            }
            int n2 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
            if (n2 >= 0 && n2 < RecyclerView.this.mAdapter.getItemCount()) {
                this.tryBindViewHolderByDeadline(viewHolder, n2, n, Long.MAX_VALUE);
                object = viewHolder.itemView.getLayoutParams();
                if (object == null) {
                    object = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                    viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
                    object = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object);
                    viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else {
                    object = (LayoutParams)((Object)object);
                }
                boolean bl = true;
                object.mInsetsDirty = true;
                object.mViewHolder = viewHolder;
                if (viewHolder.itemView.getParent() != null) {
                    bl = false;
                }
                object.mPendingInvalidate = bl;
                return;
            }
            object = new StringBuilder();
            object.append("Inconsistency detected. Invalid item position ");
            object.append(n);
            object.append("(offset:");
            object.append(n2);
            object.append(").");
            object.append("state:");
            object.append(RecyclerView.this.mState.getItemCount());
            object.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(object.toString());
        }

        public void clear() {
            this.mAttachedScrap.clear();
            this.recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int n;
            int n2 = this.mCachedViews.size();
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                this.mCachedViews.get(n).clearOldPosition();
            }
            n2 = this.mAttachedScrap.size();
            for (n = 0; n < n2; ++n) {
                this.mAttachedScrap.get(n).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                n2 = this.mChangedScrap.size();
                for (n = n3; n < n2; ++n) {
                    this.mChangedScrap.get(n).clearOldPosition();
                }
            }
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        public int convertPreLayoutPositionToPostLayout(int n) {
            if (n >= 0 && n < RecyclerView.this.mState.getItemCount()) {
                if (!RecyclerView.this.mState.isPreLayout()) {
                    return n;
                }
                return RecyclerView.this.mAdapterHelper.findPositionOffset(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid position ");
            stringBuilder.append(n);
            stringBuilder.append(". State ");
            stringBuilder.append("item count is ");
            stringBuilder.append(RecyclerView.this.mState.getItemCount());
            stringBuilder.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void dispatchViewRecycled(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            }
        }

        ViewHolder getChangedScrapViewForPosition(int n) {
            if (this.mChangedScrap != null) {
                ViewHolder viewHolder;
                int n2 = this.mChangedScrap.size();
                if (n2 == 0) {
                    return null;
                }
                int n3 = 0;
                for (int i = 0; i < n2; ++i) {
                    viewHolder = this.mChangedScrap.get(i);
                    if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n) continue;
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
                if (RecyclerView.this.mAdapter.hasStableIds() && (n = RecyclerView.this.mAdapterHelper.findPositionOffset(n)) > 0 && n < RecyclerView.this.mAdapter.getItemCount()) {
                    long l = RecyclerView.this.mAdapter.getItemId(n);
                    for (n = n3; n < n2; ++n) {
                        viewHolder = this.mChangedScrap.get(n);
                        if (viewHolder.wasReturnedFromScrap() || viewHolder.getItemId() != l) continue;
                        viewHolder.addFlags(32);
                        return viewHolder;
                    }
                }
                return null;
            }
            return null;
        }

        RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        ViewHolder getScrapOrCachedViewForId(long l, int n, boolean bl) {
            int n2;
            ViewHolder viewHolder;
            for (n2 = this.mAttachedScrap.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.getItemId() != l || viewHolder.wasReturnedFromScrap()) continue;
                if (n == viewHolder.getItemViewType()) {
                    viewHolder.addFlags(32);
                    if (viewHolder.isRemoved() && !RecyclerView.this.mState.isPreLayout()) {
                        viewHolder.setFlags(2, 14);
                    }
                    return viewHolder;
                }
                if (bl) continue;
                this.mAttachedScrap.remove(n2);
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                this.quickRecycleScrapView(viewHolder.itemView);
            }
            for (n2 = this.mCachedViews.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.getItemId() != l) continue;
                if (n == viewHolder.getItemViewType()) {
                    if (!bl) {
                        this.mCachedViews.remove(n2);
                    }
                    return viewHolder;
                }
                if (bl) continue;
                this.recycleCachedViewAt(n2);
                return null;
            }
            return null;
        }

        ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int n, boolean bl) {
            Object object;
            int n2;
            ViewHolder viewHolder;
            int n3 = this.mAttachedScrap.size();
            int n4 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (!bl && (object = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n)) != null) {
                viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                RecyclerView.this.mChildHelper.unhide((View)object);
                n = RecyclerView.this.mChildHelper.indexOfChild((View)object);
                if (n == -1) {
                    object = new StringBuilder();
                    object.append("layout index should not be -1 after unhiding a view:");
                    object.append(viewHolder);
                    object.append(RecyclerView.this.exceptionLabel());
                    throw new IllegalStateException(object.toString());
                }
                RecyclerView.this.mChildHelper.detachViewFromParent(n);
                this.scrapView((View)object);
                viewHolder.addFlags(8224);
                return viewHolder;
            }
            n3 = this.mCachedViews.size();
            for (n2 = n4; n2 < n3; ++n2) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.isInvalid() || viewHolder.getLayoutPosition() != n) continue;
                if (!bl) {
                    this.mCachedViews.remove(n2);
                }
                return viewHolder;
            }
            return null;
        }

        View getScrapViewAt(int n) {
            return this.mAttachedScrap.get((int)n).itemView;
        }

        public View getViewForPosition(int n) {
            return this.getViewForPosition(n, false);
        }

        View getViewForPosition(int n, boolean bl) {
            return this.tryGetViewHolderForPositionByDeadline((int)n, (boolean)bl, (long)Long.MAX_VALUE).itemView;
        }

        void markItemDecorInsetsDirty() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                LayoutParams layoutParams = (LayoutParams)this.mCachedViews.get((int)i).itemView.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.mInsetsDirty = true;
            }
        }

        void markKnownViewsInvalid() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null) continue;
                viewHolder.addFlags(6);
                viewHolder.addChangePayload(null);
            }
            if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
                this.recycleAndClearCachedViews();
            }
        }

        void offsetPositionRecordsForInsert(int n, int n2) {
            int n3 = this.mCachedViews.size();
            for (int i = 0; i < n3; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || viewHolder.mPosition < n) continue;
                viewHolder.offsetPosition(n2, true);
            }
        }

        void offsetPositionRecordsForMove(int n, int n2) {
            int n3;
            int n4;
            int n5;
            if (n < n2) {
                n3 = n2;
                n4 = -1;
                n5 = n;
            } else {
                n3 = n;
                n4 = 1;
                n5 = n2;
            }
            int n6 = this.mCachedViews.size();
            for (int i = 0; i < n6; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || viewHolder.mPosition < n5 || viewHolder.mPosition > n3) continue;
                if (viewHolder.mPosition == n) {
                    viewHolder.offsetPosition(n2 - n, false);
                    continue;
                }
                viewHolder.offsetPosition(n4, false);
            }
        }

        void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null) continue;
                if (viewHolder.mPosition >= n + n2) {
                    viewHolder.offsetPosition(- n2, bl);
                    continue;
                }
                if (viewHolder.mPosition < n) continue;
                viewHolder.addFlags(8);
                this.recycleCachedViewAt(i);
            }
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            this.clear();
            this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl);
        }

        void quickRecycleScrapView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).mScrapContainer = null;
            ((ViewHolder)object).mInChangeScrap = false;
            object.clearReturnedFromScrapFlag();
            this.recycleViewHolderInternal((ViewHolder)object);
        }

        void recycleAndClearCachedViews() {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                this.recycleCachedViewAt(i);
            }
            this.mCachedViews.clear();
            if (ALLOW_THREAD_GAP_WORK) {
                RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        void recycleCachedViewAt(int n) {
            this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n), true);
            this.mCachedViews.remove(n);
        }

        public void recycleView(View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolder.isScrap()) {
                viewHolder.unScrap();
            } else if (viewHolder.wasReturnedFromScrap()) {
                viewHolder.clearReturnedFromScrapFlag();
            }
            this.recycleViewHolderInternal(viewHolder);
        }

        void recycleViewHolderInternal(ViewHolder object) {
            boolean bl = object.isScrap();
            boolean bl2 = false;
            int n = 0;
            if (!bl && object.itemView.getParent() == null) {
                int n2;
                if (object.isTmpDetached()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                    stringBuilder.append(object);
                    stringBuilder.append(RecyclerView.this.exceptionLabel());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                if (object.shouldIgnore()) {
                    object = new StringBuilder();
                    object.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                    object.append(RecyclerView.this.exceptionLabel());
                    throw new IllegalArgumentException(object.toString());
                }
                bl2 = ((ViewHolder)object).doesTransientStatePreventRecycling();
                int n3 = RecyclerView.this.mAdapter != null && bl2 && RecyclerView.this.mAdapter.onFailedToRecycleView(object) ? 1 : 0;
                if (n3 == 0 && !object.isRecyclable()) {
                    n3 = 0;
                    n2 = n;
                    n = n3;
                } else {
                    if (this.mViewCacheMax > 0 && !object.hasAnyOfTheFlags(526)) {
                        n3 = n2 = this.mCachedViews.size();
                        if (n2 >= this.mViewCacheMax) {
                            n3 = n2;
                            if (n2 > 0) {
                                this.recycleCachedViewAt(0);
                                n3 = n2 - 1;
                            }
                        }
                        n2 = n3;
                        if (ALLOW_THREAD_GAP_WORK) {
                            n2 = n3;
                            if (n3 > 0) {
                                n2 = n3--;
                                if (!RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(object.mPosition)) {
                                    while (n3 >= 0 && RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(n2 = this.mCachedViews.get((int)n3).mPosition)) {
                                        --n3;
                                    }
                                    n2 = n3 + 1;
                                }
                            }
                        }
                        this.mCachedViews.add(n2, (ViewHolder)object);
                        n3 = 1;
                    } else {
                        n3 = 0;
                    }
                    n2 = n;
                    n = n3;
                    if (n3 == 0) {
                        this.addViewHolderToRecycledViewPool((ViewHolder)object, true);
                        n2 = 1;
                        n = n3;
                    }
                }
                RecyclerView.this.mViewInfoStore.removeViewHolder((ViewHolder)object);
                if (n == 0 && n2 == 0 && bl2) {
                    object.mOwnerRecyclerView = null;
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
            stringBuilder.append(object.isScrap());
            stringBuilder.append(" isAttached:");
            if (object.itemView.getParent() != null) {
                bl2 = true;
            }
            stringBuilder.append(bl2);
            stringBuilder.append(RecyclerView.this.exceptionLabel());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        void recycleViewInternal(View view) {
            this.recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(view));
        }

        void scrapView(View object) {
            if (!(object = RecyclerView.getChildViewHolderInt((View)object)).hasAnyOfTheFlags(12) && object.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder((ViewHolder)object)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList();
                }
                object.setScrapContainer(this, true);
                this.mChangedScrap.add((ViewHolder)object);
                return;
            }
            if (object.isInvalid() && !object.isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
                object = new StringBuilder();
                object.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                object.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(object.toString());
            }
            object.setScrapContainer(this, false);
            this.mAttachedScrap.add((ViewHolder)object);
        }

        void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
            }
        }

        void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        public void setViewCacheSize(int n) {
            this.mRequestedCacheMax = n;
            this.updateViewCacheSize();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Nullable
        ViewHolder tryGetViewHolderForPositionByDeadline(int var1_1, boolean var2_2, long var3_3) {
            block28 : {
                block35 : {
                    block34 : {
                        block32 : {
                            block33 : {
                                block31 : {
                                    block30 : {
                                        block29 : {
                                            if (var1_1 < 0 || var1_1 >= RecyclerView.this.mState.getItemCount()) break block28;
                                            var10_4 = RecyclerView.this.mState.isPreLayout();
                                            var9_5 = true;
                                            if (!var10_4) break block29;
                                            var15_7 = var16_6 = this.getChangedScrapViewForPosition(var1_1);
                                            if (var16_6 == null) break block30;
                                            var6_9 = 1;
                                            break block31;
                                        }
                                        var15_7 = null;
                                    }
                                    var6_9 = 0;
                                    var16_6 = var15_7;
                                }
                                var15_7 = var16_6;
                                var5_10 = var6_9;
                                if (var16_6 == null) {
                                    var15_7 = var16_6 = this.getScrapOrHiddenOrCachedHolderForPosition(var1_1, var2_2);
                                    var5_10 = var6_9;
                                    if (var16_6 != null) {
                                        if (!this.validateViewHolderForOffsetPosition((ViewHolder)var16_6)) {
                                            if (!var2_2) {
                                                var16_6.addFlags(4);
                                                if (var16_6.isScrap()) {
                                                    RecyclerView.this.removeDetachedView(var16_6.itemView, false);
                                                    var16_6.unScrap();
                                                } else if (var16_6.wasReturnedFromScrap()) {
                                                    var16_6.clearReturnedFromScrapFlag();
                                                }
                                                this.recycleViewHolderInternal((ViewHolder)var16_6);
                                            }
                                            var15_7 = null;
                                            var5_10 = var6_9;
                                        } else {
                                            var5_10 = 1;
                                            var15_7 = var16_6;
                                        }
                                    }
                                }
                                var17_11 = var15_7;
                                var7_12 = var5_10;
                                if (var15_7 != null) break block32;
                                var7_12 = RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1);
                                if (var7_12 < 0 || var7_12 >= RecyclerView.this.mAdapter.getItemCount()) break block33;
                                var8_13 = RecyclerView.this.mAdapter.getItemViewType(var7_12);
                                var16_6 = var15_7;
                                var6_9 = var5_10;
                                if (RecyclerView.this.mAdapter.hasStableIds()) {
                                    var16_6 = var15_7 = this.getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(var7_12), var8_13, var2_2);
                                    var6_9 = var5_10;
                                    if (var15_7 != null) {
                                        var15_7.mPosition = var7_12;
                                        var6_9 = 1;
                                        var16_6 = var15_7;
                                    }
                                }
                                var15_7 = var16_6;
                                if (var16_6 == null) {
                                    var15_7 = var16_6;
                                    if (this.mViewCacheExtension != null) {
                                        var17_11 = this.mViewCacheExtension.getViewForPositionAndType(this, var1_1, var8_13);
                                        var15_7 = var16_6;
                                        if (var17_11 != null) {
                                            var16_6 = RecyclerView.this.getChildViewHolder((View)var17_11);
                                            if (var16_6 == null) {
                                                var15_7 = new StringBuilder();
                                                var15_7.append("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                                var15_7.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var15_7.toString());
                                            }
                                            var15_7 = var16_6;
                                            if (var16_6.shouldIgnore()) {
                                                var15_7 = new StringBuilder();
                                                var15_7.append("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                                var15_7.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var15_7.toString());
                                            }
                                        }
                                    }
                                }
                                var16_6 = var15_7;
                                if (var15_7 == null) {
                                    var16_6 = var15_7 = this.getRecycledViewPool().getRecycledView(var8_13);
                                    if (var15_7 != null) {
                                        var15_7.resetInternal();
                                        var16_6 = var15_7;
                                        if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                                            this.invalidateDisplayListInt((ViewHolder)var15_7);
                                            var16_6 = var15_7;
                                        }
                                    }
                                }
                                var17_11 = var16_6;
                                var7_12 = var6_9;
                                if (var16_6 != null) break block32;
                                var11_14 = RecyclerView.this.getNanoTime();
                                if (var3_3 != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(var8_13, var11_14, var3_3)) {
                                    return null;
                                }
                                var16_6 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, var8_13);
                                if (RecyclerView.access$800() && (var15_7 = RecyclerView.findNestedRecyclerView(var16_6.itemView)) != null) {
                                    var16_6.mNestedRecyclerView = new WeakReference<Object>(var15_7);
                                }
                                var13_15 = RecyclerView.this.getNanoTime();
                                this.mRecyclerPool.factorInCreateTime(var8_13, var13_15 - var11_14);
                                break block34;
                            }
                            var15_7 = new StringBuilder();
                            var15_7.append("Inconsistency detected. Invalid item position ");
                            var15_7.append(var1_1);
                            var15_7.append("(offset:");
                            var15_7.append(var7_12);
                            var15_7.append(").");
                            var15_7.append("state:");
                            var15_7.append(RecyclerView.this.mState.getItemCount());
                            var15_7.append(RecyclerView.this.exceptionLabel());
                            throw new IndexOutOfBoundsException(var15_7.toString());
                        }
                        var16_6 = var17_11;
                        var6_9 = var7_12;
                    }
                    if (var6_9 != 0 && !RecyclerView.this.mState.isPreLayout() && var16_6.hasAnyOfTheFlags(8192)) {
                        var16_6.setFlags(0, 8192);
                        if (RecyclerView.this.mState.mRunSimpleAnimations) {
                            var5_10 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)var16_6);
                            var15_7 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (ViewHolder)var16_6, var5_10 | 4096, var16_6.getUnmodifiedPayloads());
                            RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)var16_6, (ItemAnimator.ItemHolderInfo)var15_7);
                        }
                    }
                    if (!RecyclerView.this.mState.isPreLayout() || !var16_6.isBound()) break block35;
                    var16_6.mPreLayoutPosition = var1_1;
                    ** GOTO lbl-1000
                }
                if (var16_6.isBound() && !var16_6.needsUpdate() && !var16_6.isInvalid()) lbl-1000: // 2 sources:
                {
                    var2_2 = false;
                } else {
                    var2_2 = this.tryBindViewHolderByDeadline((ViewHolder)var16_6, RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1), var1_1, var3_3);
                }
                var15_7 = var16_6.itemView.getLayoutParams();
                if (var15_7 == null) {
                    var15_7 = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                    var16_6.itemView.setLayoutParams((ViewGroup.LayoutParams)var15_7);
                } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)var15_7)) {
                    var15_7 = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)var15_7);
                    var16_6.itemView.setLayoutParams((ViewGroup.LayoutParams)var15_7);
                } else {
                    var15_7 = (LayoutParams)var15_7;
                }
                var15_7.mViewHolder = var16_6;
                var2_2 = var6_9 != 0 && var2_2 != false ? var9_5 : false;
                var15_7.mPendingInvalidate = var2_2;
                return var16_6;
            }
            var15_8 = new StringBuilder();
            var15_8.append("Invalid item position ");
            var15_8.append(var1_1);
            var15_8.append("(");
            var15_8.append(var1_1);
            var15_8.append("). Item count:");
            var15_8.append(RecyclerView.this.mState.getItemCount());
            var15_8.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(var15_8.toString());
        }

        void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        void updateViewCacheSize() {
            int n = RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0;
            this.mViewCacheMax = this.mRequestedCacheMax + n;
            for (n = this.mCachedViews.size() - 1; n >= 0 && this.mCachedViews.size() > this.mViewCacheMax; --n) {
                this.recycleCachedViewAt(n);
            }
        }

        boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition >= 0 && viewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()) {
                boolean bl = RecyclerView.this.mState.isPreLayout();
                boolean bl2 = false;
                if (!bl && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                    return false;
                }
                if (RecyclerView.this.mAdapter.hasStableIds()) {
                    if (viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) {
                        bl2 = true;
                    }
                    return bl2;
                }
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
            stringBuilder.append(viewHolder);
            stringBuilder.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void viewRangeUpdate(int n, int n2) {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                int n3;
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || (n3 = viewHolder.mPosition) < n || n3 >= n2 + n) continue;
                viewHolder.addFlags(2);
                this.recycleCachedViewAt(i);
            }
        }
    }

    public static interface RecyclerListener {
        public void onViewRecycled(ViewHolder var1);
    }

    private class RecyclerViewDataObserver
    extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        @Override
        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            RecyclerView.this.mState.mStructureChanged = true;
            RecyclerView.this.processDataSetCompletelyChanged(true);
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override
        public void onItemRangeChanged(int n, int n2, Object object) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(n, n2, object)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeInserted(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeMoved(int n, int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(n, n2, n3)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeRemoved(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() {
            if (RecyclerView.POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                ViewCompat.postOnAnimation((View)RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
                return;
            }
            RecyclerView.this.mAdapterUpdateDuringMeasure = true;
            RecyclerView.this.requestLayout();
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
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
        Parcelable mLayoutState;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = LayoutManager.class.getClassLoader();
            }
            this.mLayoutState = parcel.readParcelable(classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        void copyFrom(SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

    }

    public static class SimpleOnItemTouchListener
    implements OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean bl) {
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final SmoothScroller$Action mRecyclingAction = new SmoothScroller$Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        static /* synthetic */ void access$600(SmoothScroller smoothScroller, int n, int n2) {
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
        public LayoutManager getLayoutManager() {
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

        protected abstract void onSeekTargetStep(int var1, int var2, State var3, SmoothScroller$Action var4);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(View var1, State var2, SmoothScroller$Action var3);

        public void setTargetPosition(int n) {
            this.mTargetPosition = n;
        }

        void start(RecyclerView recyclerView, LayoutManager layoutManager) {
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
    }

    public static class SmoothScroller$Action {
        public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
        private boolean mChanged = false;
        private int mConsecutiveUpdates = 0;
        private int mDuration;
        private int mDx;
        private int mDy;
        private Interpolator mInterpolator;
        private int mJumpToPosition = -1;

        public SmoothScroller$Action(int n, int n2) {
            this(n, n2, Integer.MIN_VALUE, null);
        }

        public SmoothScroller$Action(int n, int n2, int n3) {
            this(n, n2, n3, null);
        }

        public SmoothScroller$Action(int n, int n2, int n3, Interpolator interpolator) {
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

    public static interface SmoothScroller$ScrollVectorProvider {
        public PointF computeScrollVectorForPosition(int var1);
    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        int mRemainingScrollHorizontal;
        int mRemainingScrollVertical;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        void assertLayoutStep(int n) {
            if ((this.mLayoutStep & n) == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Layout state should be one of ");
                stringBuilder.append(Integer.toBinaryString(n));
                stringBuilder.append(" but it is ");
                stringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
                throw new IllegalStateException(stringBuilder.toString());
            }
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public <T> T get(int n) {
            if (this.mData == null) {
                return null;
            }
            return (T)this.mData.get(n);
        }

        public int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return this.mItemCount;
        }

        public int getRemainingScrollHorizontal() {
            return this.mRemainingScrollHorizontal;
        }

        public int getRemainingScrollVertical() {
            return this.mRemainingScrollVertical;
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            if (this.mTargetPosition != -1) {
                return true;
            }
            return false;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        void prepareForNestedPrefetch(Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public void put(int n, Object object) {
            if (this.mData == null) {
                this.mData = new SparseArray();
            }
            this.mData.put(n, object);
        }

        public void remove(int n) {
            if (this.mData == null) {
                return;
            }
            this.mData.remove(n);
        }

        State reset() {
            this.mTargetPosition = -1;
            if (this.mData != null) {
                this.mData.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("State{mTargetPosition=");
            stringBuilder.append(this.mTargetPosition);
            stringBuilder.append(", mData=");
            stringBuilder.append(this.mData);
            stringBuilder.append(", mItemCount=");
            stringBuilder.append(this.mItemCount);
            stringBuilder.append(", mIsMeasuring=");
            stringBuilder.append(this.mIsMeasuring);
            stringBuilder.append(", mPreviousLayoutItemCount=");
            stringBuilder.append(this.mPreviousLayoutItemCount);
            stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            stringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
            stringBuilder.append(", mStructureChanged=");
            stringBuilder.append(this.mStructureChanged);
            stringBuilder.append(", mInPreLayout=");
            stringBuilder.append(this.mInPreLayout);
            stringBuilder.append(", mRunSimpleAnimations=");
            stringBuilder.append(this.mRunSimpleAnimations);
            stringBuilder.append(", mRunPredictiveAnimations=");
            stringBuilder.append(this.mRunPredictiveAnimations);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface State$LayoutState {
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler var1, int var2, int var3);
    }

    class ViewFlinger
    implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private OverScroller mScroller;

        ViewFlinger() {
            this.mScroller = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
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
                                                    SmoothScroller.access$600(var14_2, var6_8 - var2_6, var5_9 - var4_11);
                                                    var7_12 = var1_7;
                                                    var10_13 = var2_6;
                                                    var9_14 = var3_10;
                                                    var8_15 = var4_11;
                                                } else {
                                                    SmoothScroller.access$600(var14_2, var6_8 - var2_6, var5_9 - var4_11);
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
                    SmoothScroller.access$600(var14_2, 0, 0);
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

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1L;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        @VisibleForTesting
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList<Object>();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        private boolean doesTransientStatePreventRecycling() {
            if ((this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView)) {
                return true;
            }
            return false;
        }

        private void onEnteredHiddenState(RecyclerView recyclerView) {
            this.mWasImportantForAccessibilityBeforeHidden = this.mPendingAccessibilityState != -1 ? this.mPendingAccessibilityState : ViewCompat.getImportantForAccessibility(this.itemView);
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        private void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private boolean shouldBeKeptAsChild() {
            if ((this.mFlags & 16) != 0) {
                return true;
            }
            return false;
        }

        void addChangePayload(Object object) {
            if (object == null) {
                this.addFlags(1024);
                return;
            }
            if ((1024 & this.mFlags) == 0) {
                this.createPayloadsIfNeeded();
                this.mPayloads.add(object);
            }
        }

        void addFlags(int n) {
            this.mFlags = n | this.mFlags;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        void flagRemovedAndOffsetPosition(int n, int n2, boolean bl) {
            this.addFlags(8);
            this.offsetPosition(n2, bl);
            this.mPosition = n;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView == null) {
                return -1;
            }
            return this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) == 0) {
                if (this.mPayloads != null && this.mPayloads.size() != 0) {
                    return this.mUnmodifiedPayloads;
                }
                return FULLUPDATE_PAYLOADS;
            }
            return FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int n) {
            if ((n & this.mFlags) != 0) {
                return true;
            }
            return false;
        }

        boolean isAdapterPositionUnknown() {
            if ((this.mFlags & 512) == 0 && !this.isInvalid()) {
                return false;
            }
            return true;
        }

        boolean isBound() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return false;
        }

        boolean isInvalid() {
            if ((this.mFlags & 4) != 0) {
                return true;
            }
            return false;
        }

        public final boolean isRecyclable() {
            if ((this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView)) {
                return true;
            }
            return false;
        }

        boolean isRemoved() {
            if ((this.mFlags & 8) != 0) {
                return true;
            }
            return false;
        }

        boolean isScrap() {
            if (this.mScrapContainer != null) {
                return true;
            }
            return false;
        }

        boolean isTmpDetached() {
            if ((this.mFlags & 256) != 0) {
                return true;
            }
            return false;
        }

        boolean isUpdated() {
            if ((this.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        boolean needsUpdate() {
            if ((this.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        void offsetPosition(int n, boolean bl) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (bl) {
                this.mPreLayoutPosition += n;
            }
            this.mPosition += n;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            this.clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int n, int n2) {
            this.mFlags = n & n2 | this.mFlags & ~ n2;
        }

        public final void setIsRecyclable(boolean bl) {
            int n = bl ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            this.mIsRecyclableCount = n;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                stringBuilder.append(this);
                Log.e((String)"View", (String)stringBuilder.toString());
                return;
            }
            if (!bl && this.mIsRecyclableCount == 1) {
                this.mFlags |= 16;
                return;
            }
            if (bl && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        void setScrapContainer(Recycler recycler, boolean bl) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = bl;
        }

        boolean shouldIgnore() {
            if ((this.mFlags & 128) != 0) {
                return true;
            }
            return false;
        }

        void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("ViewHolder{");
            charSequence.append(Integer.toHexString(this.hashCode()));
            charSequence.append(" position=");
            charSequence.append(this.mPosition);
            charSequence.append(" id=");
            charSequence.append(this.mItemId);
            charSequence.append(", oldPos=");
            charSequence.append(this.mOldPosition);
            charSequence.append(", pLpos:");
            charSequence.append(this.mPreLayoutPosition);
            StringBuilder stringBuilder = new StringBuilder(((StringBuilder)charSequence).toString());
            if (this.isScrap()) {
                stringBuilder.append(" scrap ");
                charSequence = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder.append((String)charSequence);
            }
            if (this.isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!this.isBound()) {
                stringBuilder.append(" unbound");
            }
            if (this.needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (this.isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (this.shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (this.isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.isRecyclable()) {
                charSequence = new StringBuilder();
                charSequence.append(" not recyclable(");
                charSequence.append(this.mIsRecyclableCount);
                charSequence.append(")");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            if (this.isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }
    }

}
