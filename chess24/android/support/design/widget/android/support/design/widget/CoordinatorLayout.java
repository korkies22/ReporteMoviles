/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.AbsSavedState
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.coreui.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Pools;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.DirectedAcyclicGraph;
import android.support.v4.widget.ViewGroupUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
extends ViewGroup
implements NestedScrollingParent2 {
    static final Class<?>[] CONSTRUCTOR_PARAMS;
    static final int EVENT_NESTED_SCROLL = 1;
    static final int EVENT_PRE_DRAW = 0;
    static final int EVENT_VIEW_REMOVED = 2;
    static final String TAG = "CoordinatorLayout";
    static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    private static final int TYPE_ON_INTERCEPT = 0;
    private static final int TYPE_ON_TOUCH = 1;
    static final String WIDGET_PACKAGE_NAME;
    static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
    private static final Pools.Pool<Rect> sRectPool;
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private View mBehaviorTouchView;
    private final DirectedAcyclicGraph<View> mChildDag = new DirectedAcyclicGraph();
    private final List<View> mDependencySortedChildren = new ArrayList<View>();
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    private boolean mNeedsPreDrawListener;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    private View mNestedScrollingTarget;
    ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList = new ArrayList<View>();
    private final int[] mTempIntPair = new int[2];
    private final List<View> mTempList1 = new ArrayList<View>();

    static {
        Object object = CoordinatorLayout.class.getPackage();
        object = object != null ? object.getName() : null;
        WIDGET_PACKAGE_NAME = object;
        TOP_SORTED_CHILDREN_COMPARATOR = Build.VERSION.SDK_INT >= 21 ? new ViewElevationComparator() : null;
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal();
        sRectPool = new Pools.SynchronizedPool<Rect>(12);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        int n2 = 0;
        attributeSet = n == 0 ? context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout) : context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, n, 0);
        n = attributeSet.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
        if (n != 0) {
            context = context.getResources();
            this.mKeylines = context.getIntArray(n);
            float f = context.getDisplayMetrics().density;
            int n3 = this.mKeylines.length;
            for (n = n2; n < n3; ++n) {
                this.mKeylines[n] = (int)((float)this.mKeylines[n] * f);
            }
        }
        this.mStatusBarBackground = attributeSet.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
        attributeSet.recycle();
        this.setupForInsets();
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)new HierarchyChangeListener());
    }

    @NonNull
    private static Rect acquireTempRect() {
        Rect rect;
        Rect rect2 = rect = sRectPool.acquire();
        if (rect == null) {
            rect2 = new Rect();
        }
        return rect2;
    }

    private void constrainChildRect(LayoutParams layoutParams, Rect rect, int n, int n2) {
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        n3 = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, n3 - this.getPaddingRight() - n - layoutParams.rightMargin));
        n4 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, n4 - this.getPaddingBottom() - n2 - layoutParams.bottomMargin));
        rect.set(n3, n4, n + n3, n2 + n4);
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int n = this.getChildCount();
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            windowInsetsCompat = windowInsetsCompat2;
            if (ViewCompat.getFitsSystemWindows(view)) {
                Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
                windowInsetsCompat = windowInsetsCompat2;
                if (behavior != null) {
                    windowInsetsCompat = windowInsetsCompat2 = behavior.onApplyWindowInsets(this, view, windowInsetsCompat2);
                    if (windowInsetsCompat2.isConsumed()) {
                        return windowInsetsCompat2;
                    }
                }
            }
            windowInsetsCompat2 = windowInsetsCompat;
        }
        return windowInsetsCompat2;
    }

    private void getDesiredAnchoredChildRectWithoutConstraints(View view, int n, Rect rect, Rect rect2, LayoutParams layoutParams, int n2, int n3) {
        int n4 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveAnchoredChildGravity(layoutParams.gravity), n);
        n = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveGravity(layoutParams.anchorGravity), n);
        int n5 = n4 & 7;
        int n6 = n4 & 112;
        int n7 = n & 7;
        n4 = n & 112;
        n = n7 != 1 ? (n7 != 5 ? rect.left : rect.right) : rect.left + rect.width() / 2;
        n4 = n4 != 16 ? (n4 != 80 ? rect.top : rect.bottom) : rect.top + rect.height() / 2;
        if (n5 != 1) {
            n7 = n;
            if (n5 != 5) {
                n7 = n - n2;
            }
        } else {
            n7 = n - n2 / 2;
        }
        if (n6 != 16) {
            n = n4;
            if (n6 != 80) {
                n = n4 - n3;
            }
        } else {
            n = n4 - n3 / 2;
        }
        rect2.set(n7, n, n2 + n7, n3 + n);
    }

    private int getKeyline(int n) {
        if (this.mKeylines == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No keylines defined for ");
            stringBuilder.append(this);
            stringBuilder.append(" - attempted index lookup ");
            stringBuilder.append(n);
            Log.e((String)TAG, (String)stringBuilder.toString());
            return 0;
        }
        if (n >= 0 && n < this.mKeylines.length) {
            return this.mKeylines[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Keyline index ");
        stringBuilder.append(n);
        stringBuilder.append(" out of range for ");
        stringBuilder.append(this);
        Log.e((String)TAG, (String)stringBuilder.toString());
        return 0;
    }

    private void getTopSortedChildren(List<View> list) {
        list.clear();
        boolean bl = this.isChildrenDrawingOrderEnabled();
        int n = this.getChildCount();
        for (int i = n - 1; i >= 0; --i) {
            int n2 = bl ? this.getChildDrawingOrder(n, i) : i;
            list.add(this.getChildAt(n2));
        }
        if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
            Collections.sort(list, TOP_SORTED_CHILDREN_COMPARATOR);
        }
    }

    private boolean hasDependencies(View view) {
        return this.mChildDag.hasOutgoingEdges(view);
    }

    private void layoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = CoordinatorLayout.acquireTempRect();
        rect.set(this.getPaddingLeft() + layoutParams.leftMargin, this.getPaddingTop() + layoutParams.topMargin, this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin, this.getHeight() - this.getPaddingBottom() - layoutParams.bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += this.mLastInsets.getSystemWindowInsetLeft();
            rect.top += this.mLastInsets.getSystemWindowInsetTop();
            rect.right -= this.mLastInsets.getSystemWindowInsetRight();
            rect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        GravityCompat.apply(CoordinatorLayout.resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, n);
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
        CoordinatorLayout.releaseTempRect(rect);
        CoordinatorLayout.releaseTempRect(rect2);
    }

    private void layoutChildWithAnchor(View view, View view2, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        layoutParams = CoordinatorLayout.acquireTempRect();
        Rect rect = CoordinatorLayout.acquireTempRect();
        try {
            this.getDescendantRect(view2, (Rect)layoutParams);
            this.getDesiredAnchoredChildRect(view, n, (Rect)layoutParams, rect);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
            return;
        }
        finally {
            CoordinatorLayout.releaseTempRect((Rect)layoutParams);
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    private void layoutChildWithKeyline(View view, int n, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n2);
        int n4 = n3 & 7;
        int n5 = n3 & 112;
        int n6 = this.getWidth();
        int n7 = this.getHeight();
        int n8 = view.getMeasuredWidth();
        int n9 = view.getMeasuredHeight();
        n3 = n;
        if (n2 == 1) {
            n3 = n6 - n;
        }
        n = this.getKeyline(n3) - n8;
        n2 = 0;
        if (n4 != 1) {
            if (n4 == 5) {
                n += n8;
            }
        } else {
            n += n8 / 2;
        }
        if (n5 != 16) {
            if (n5 == 80) {
                n2 = 0 + n9;
            }
        } else {
            n2 = 0 + n9 / 2;
        }
        n = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(n, n6 - this.getPaddingRight() - n8 - layoutParams.rightMargin));
        n2 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(n2, n7 - this.getPaddingBottom() - n9 - layoutParams.bottomMargin));
        view.layout(n, n2, n8 + n, n9 + n2);
    }

    private void offsetChildByInset(View object, Rect rect, int n) {
        if (!ViewCompat.isLaidOut((View)object)) {
            return;
        }
        if (object.getWidth() > 0) {
            if (object.getHeight() <= 0) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
            Behavior behavior = layoutParams.getBehavior();
            Rect rect2 = CoordinatorLayout.acquireTempRect();
            Rect rect3 = CoordinatorLayout.acquireTempRect();
            rect3.set(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
            if (behavior != null && behavior.getInsetDodgeRect(this, object, rect2)) {
                if (!rect3.contains(rect2)) {
                    object = new StringBuilder();
                    object.append("Rect should be within the child's bounds. Rect:");
                    object.append(rect2.toShortString());
                    object.append(" | Bounds:");
                    object.append(rect3.toShortString());
                    throw new IllegalArgumentException(object.toString());
                }
            } else {
                rect2.set(rect3);
            }
            CoordinatorLayout.releaseTempRect(rect3);
            if (rect2.isEmpty()) {
                CoordinatorLayout.releaseTempRect(rect2);
                return;
            }
            int n2 = GravityCompat.getAbsoluteGravity(layoutParams.dodgeInsetEdges, n);
            if ((n2 & 48) == 48 && (n = rect2.top - layoutParams.topMargin - layoutParams.mInsetOffsetY) < rect.top) {
                this.setInsetOffsetY((View)object, rect.top - n);
                n = 1;
            } else {
                n = 0;
            }
            int n3 = n;
            if ((n2 & 80) == 80) {
                int n4 = this.getHeight() - rect2.bottom - layoutParams.bottomMargin + layoutParams.mInsetOffsetY;
                n3 = n;
                if (n4 < rect.bottom) {
                    this.setInsetOffsetY((View)object, n4 - rect.bottom);
                    n3 = 1;
                }
            }
            if (n3 == 0) {
                this.setInsetOffsetY((View)object, 0);
            }
            if ((n2 & 3) == 3 && (n = rect2.left - layoutParams.leftMargin - layoutParams.mInsetOffsetX) < rect.left) {
                this.setInsetOffsetX((View)object, rect.left - n);
                n = 1;
            } else {
                n = 0;
            }
            n3 = n;
            if ((n2 & 5) == 5) {
                n2 = this.getWidth() - rect2.right - layoutParams.rightMargin + layoutParams.mInsetOffsetX;
                n3 = n;
                if (n2 < rect.right) {
                    this.setInsetOffsetX((View)object, n2 - rect.right);
                    n3 = 1;
                }
            }
            if (n3 == 0) {
                this.setInsetOffsetX((View)object, 0);
            }
            CoordinatorLayout.releaseTempRect(rect2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Behavior parseBehavior(Context object, AttributeSet object2, String hashMap) {
        Map<String, Constructor<Behavior>> map;
        StringBuilder stringBuilder;
        CharSequence charSequence;
        if (TextUtils.isEmpty((CharSequence)((Object)map))) {
            return null;
        }
        if (map.startsWith(".")) {
            charSequence = new StringBuilder();
            charSequence.append(object.getPackageName());
            charSequence.append((String)((Object)map));
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (map.indexOf(46) >= 0) {
            charSequence = map;
        } else {
            charSequence = map;
            if (!TextUtils.isEmpty((CharSequence)WIDGET_PACKAGE_NAME)) {
                charSequence = new StringBuilder();
                charSequence.append(WIDGET_PACKAGE_NAME);
                charSequence.append('.');
                charSequence.append((String)((Object)map));
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        try {
            Constructor<?> constructor = sConstructors.get();
            map = constructor;
            if (constructor == null) {
                map = new HashMap<String, Constructor<Behavior>>();
                sConstructors.set(map);
            }
            Constructor<Behavior> constructor2 = map.get(charSequence);
            constructor = constructor2;
            if (constructor2 == null) {
                constructor = object.getClassLoader().loadClass((String)charSequence).getConstructor(CONSTRUCTOR_PARAMS);
                constructor.setAccessible(true);
                map.put((String)charSequence, constructor);
            }
            return (Behavior)constructor.newInstance(object, stringBuilder);
        }
        catch (Exception exception) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not inflate Behavior subclass ");
            stringBuilder.append((String)charSequence);
            throw new RuntimeException(stringBuilder.toString(), exception);
        }
    }

    private boolean performIntercept(MotionEvent motionEvent, int n) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:296)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        this.mChildDag.clear();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            LayoutParams layoutParams = this.getResolvedLayoutParams(view);
            layoutParams.findAnchorView(this, view);
            this.mChildDag.addNode(view);
            for (int j = 0; j < n; ++j) {
                View view2;
                if (j == i || !layoutParams.dependsOn(this, view, view2 = this.getChildAt(j))) continue;
                if (!this.mChildDag.contains(view2)) {
                    this.mChildDag.addNode(view2);
                }
                this.mChildDag.addEdge(view2, view);
            }
        }
        this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
        Collections.reverse(this.mDependencySortedChildren);
    }

    private static void releaseTempRect(@NonNull Rect rect) {
        rect.setEmpty();
        sRectPool.release(rect);
    }

    private void resetTouchBehaviors(boolean bl) {
        int n;
        int n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (behavior == null) continue;
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
            if (bl) {
                behavior.onInterceptTouchEvent(this, view, motionEvent);
            } else {
                behavior.onTouchEvent(this, view, motionEvent);
            }
            motionEvent.recycle();
        }
        for (n = 0; n < n2; ++n) {
            ((LayoutParams)this.getChildAt(n).getLayoutParams()).resetTouchBehaviorTracking();
        }
        this.mBehaviorTouchView = null;
        this.mDisallowInterceptReset = false;
    }

    private static int resolveAnchoredChildGravity(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 17;
        }
        return n2;
    }

    private static int resolveGravity(int n) {
        int n2 = n;
        if ((n & 7) == 0) {
            n2 = n | 8388611;
        }
        n = n2;
        if ((n2 & 112) == 0) {
            n = n2 | 48;
        }
        return n;
    }

    private static int resolveKeylineGravity(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 8388661;
        }
        return n2;
    }

    private void setInsetOffsetX(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetX != n) {
            ViewCompat.offsetLeftAndRight(view, n - layoutParams.mInsetOffsetX);
            layoutParams.mInsetOffsetX = n;
        }
    }

    private void setInsetOffsetY(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetY != n) {
            ViewCompat.offsetTopAndBottom(view, n - layoutParams.mInsetOffsetY);
            layoutParams.mInsetOffsetY = n;
        }
    }

    private void setupForInsets() {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (ViewCompat.getFitsSystemWindows((View)this)) {
            if (this.mApplyWindowInsetsListener == null) {
                this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener(){

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
                    }
                };
            }
            ViewCompat.setOnApplyWindowInsetsListener((View)this, this.mApplyWindowInsetsListener);
            this.setSystemUiVisibility(1280);
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, null);
    }

    void addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams)) {
            return true;
        }
        return false;
    }

    public void dispatchDependentViewsChanged(View view) {
        List list = this.mChildDag.getIncomingEdges(view);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                View view2 = (View)list.get(i);
                Behavior behavior = ((LayoutParams)view2.getLayoutParams()).getBehavior();
                if (behavior == null) continue;
                behavior.onDependentViewChanged(this, view2, view);
            }
        }
    }

    public boolean doViewsOverlap(View view, View view2) {
        int n = view.getVisibility();
        boolean bl = false;
        if (n == 0 && view2.getVisibility() == 0) {
            boolean bl2;
            Rect rect;
            block5 : {
                rect = CoordinatorLayout.acquireTempRect();
                bl2 = view.getParent() != this;
                this.getChildRect(view, bl2, rect);
                view = CoordinatorLayout.acquireTempRect();
                bl2 = view2.getParent() != this;
                this.getChildRect(view2, bl2, (Rect)view);
                bl2 = bl;
                try {
                    if (rect.left > view.right) break block5;
                    bl2 = bl;
                }
                catch (Throwable throwable) {
                    CoordinatorLayout.releaseTempRect(rect);
                    CoordinatorLayout.releaseTempRect((Rect)view);
                    throw throwable;
                }
                if (rect.top > view.bottom) break block5;
                bl2 = bl;
                if (rect.right < view.left) break block5;
                n = rect.bottom;
                int n2 = view.top;
                bl2 = bl;
                if (n < n2) break block5;
                bl2 = true;
            }
            CoordinatorLayout.releaseTempRect(rect);
            CoordinatorLayout.releaseTempRect((Rect)view);
            return bl2;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        float f;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mBehavior != null && (f = layoutParams.mBehavior.getScrimOpacity(this, view)) > 0.0f) {
            if (this.mScrimPaint == null) {
                this.mScrimPaint = new Paint();
            }
            this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view));
            this.mScrimPaint.setAlpha(MathUtils.clamp(Math.round(255.0f * f), 0, 255));
            int n = canvas.save();
            if (view.isOpaque()) {
                canvas.clipRect((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom(), Region.Op.DIFFERENCE);
            }
            canvas.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.mScrimPaint);
            canvas.restoreToCount(n);
        }
        return super.drawChild(canvas, view, l);
    }

    protected void drawableStateChanged() {
        boolean bl;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        Drawable drawable = this.mStatusBarBackground;
        boolean bl2 = bl = false;
        if (drawable != null) {
            bl2 = bl;
            if (drawable.isStateful()) {
                bl2 = false | drawable.setState(arrn);
            }
        }
        if (bl2) {
            this.invalidate();
        }
    }

    void ensurePreDrawListener() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (this.hasDependencies(this.getChildAt(n2))) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        if (bl != this.mNeedsPreDrawListener) {
            if (bl) {
                this.addPreDrawListener();
                return;
            }
            this.removePreDrawListener();
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    void getChildRect(View view, boolean bl, Rect rect) {
        if (!view.isLayoutRequested() && view.getVisibility() != 8) {
            if (bl) {
                this.getDescendantRect(view, rect);
                return;
            }
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            return;
        }
        rect.setEmpty();
    }

    @NonNull
    public List<View> getDependencies(@NonNull View object) {
        object = this.mChildDag.getOutgoingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object != null) {
            this.mTempDependenciesList.addAll((Collection<View>)object);
        }
        return this.mTempDependenciesList;
    }

    @VisibleForTesting
    final List<View> getDependencySortedChildren() {
        this.prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    @NonNull
    public List<View> getDependents(@NonNull View object) {
        object = this.mChildDag.getIncomingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object != null) {
            this.mTempDependenciesList.addAll((Collection<View>)object);
        }
        return this.mTempDependenciesList;
    }

    void getDescendantRect(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect);
    }

    void getDesiredAnchoredChildRect(View view, int n, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredWidth();
        int n3 = view.getMeasuredHeight();
        this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect, rect2, layoutParams, n2, n3);
        this.constrainChildRect(layoutParams, rect2, n2, n3);
    }

    void getLastChildRect(View view, Rect rect) {
        rect.set(((LayoutParams)view.getLayoutParams()).getLastChildRect());
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets;
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    LayoutParams getResolvedLayoutParams(View object) {
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            Object object2;
            if (object instanceof AttachedBehavior) {
                if ((object = ((AttachedBehavior)object).getBehavior()) == null) {
                    Log.e((String)TAG, (String)"Attached behavior class is null");
                }
                layoutParams.setBehavior((Behavior)object);
                layoutParams.mBehaviorResolved = true;
                return layoutParams;
            }
            Class<?> class_ = object.getClass();
            object = null;
            while (class_ != null) {
                object = object2 = class_.getAnnotation(DefaultBehavior.class);
                if (object2 != null) break;
                class_ = class_.getSuperclass();
                object = object2;
            }
            if (object != null) {
                try {
                    layoutParams.setBehavior(object.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
                catch (Exception exception) {
                    object2 = new StringBuilder();
                    object2.append("Default behavior class ");
                    object2.append(object.value().getName());
                    object2.append(" could not be instantiated. Did you forget");
                    object2.append(" a default constructor?");
                    Log.e((String)TAG, (String)object2.toString(), (Throwable)exception);
                }
            }
            layoutParams.mBehaviorResolved = true;
        }
        return layoutParams;
    }

    @Nullable
    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
    }

    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
    }

    public boolean isPointInChildBounds(View view, int n, int n2) {
        Rect rect = CoordinatorLayout.acquireTempRect();
        this.getDescendantRect(view, rect);
        try {
            boolean bl = rect.contains(n, n2);
            return bl;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    void offsetChildToAnchor(View view, int n) {
        block6 : {
            int n2;
            Rect rect;
            int n3;
            int n4;
            Behavior behavior;
            Rect rect2;
            Rect rect3;
            LayoutParams layoutParams;
            block8 : {
                block7 : {
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (layoutParams.mAnchorView == null) break block6;
                    rect2 = CoordinatorLayout.acquireTempRect();
                    rect3 = CoordinatorLayout.acquireTempRect();
                    rect = CoordinatorLayout.acquireTempRect();
                    this.getDescendantRect(layoutParams.mAnchorView, rect2);
                    n4 = 0;
                    this.getChildRect(view, false, rect3);
                    n2 = view.getMeasuredWidth();
                    n3 = view.getMeasuredHeight();
                    this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect2, rect, layoutParams, n2, n3);
                    if (rect.left != rect3.left) break block7;
                    n = n4;
                    if (rect.top == rect3.top) break block8;
                }
                n = 1;
            }
            this.constrainChildRect(layoutParams, rect, n2, n3);
            n4 = rect.left - rect3.left;
            n2 = rect.top - rect3.top;
            if (n4 != 0) {
                ViewCompat.offsetLeftAndRight(view, n4);
            }
            if (n2 != 0) {
                ViewCompat.offsetTopAndBottom(view, n2);
            }
            if (n != 0 && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onDependentViewChanged(this, view, layoutParams.mAnchorView);
            }
            CoordinatorLayout.releaseTempRect(rect2);
            CoordinatorLayout.releaseTempRect(rect3);
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.resetTouchBehaviors(false);
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows((View)this)) {
            ViewCompat.requestApplyInsets((View)this);
        }
        this.mIsAttachedToWindow = true;
    }

    final void onChildViewsChanged(int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = this.mDependencySortedChildren.size();
        Rect rect = CoordinatorLayout.acquireTempRect();
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        Rect rect3 = CoordinatorLayout.acquireTempRect();
        for (int i = 0; i < n3; ++i) {
            Object object;
            int n4;
            View view = this.mDependencySortedChildren.get(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (n == 0 && view.getVisibility() == 8) continue;
            for (n4 = 0; n4 < i; ++n4) {
                object = this.mDependencySortedChildren.get(n4);
                if (layoutParams.mAnchorDirectChild != object) continue;
                this.offsetChildToAnchor(view, n2);
            }
            this.getChildRect(view, true, rect2);
            if (layoutParams.insetEdge != 0 && !rect2.isEmpty()) {
                n4 = GravityCompat.getAbsoluteGravity(layoutParams.insetEdge, n2);
                int n5 = n4 & 112;
                if (n5 != 48) {
                    if (n5 == 80) {
                        rect.bottom = Math.max(rect.bottom, this.getHeight() - rect2.top);
                    }
                } else {
                    rect.top = Math.max(rect.top, rect2.bottom);
                }
                if ((n4 &= 7) != 3) {
                    if (n4 == 5) {
                        rect.right = Math.max(rect.right, this.getWidth() - rect2.left);
                    }
                } else {
                    rect.left = Math.max(rect.left, rect2.right);
                }
            }
            if (layoutParams.dodgeInsetEdges != 0 && view.getVisibility() == 0) {
                this.offsetChildByInset(view, rect, n2);
            }
            if (n != 2) {
                this.getLastChildRect(view, rect3);
                if (rect3.equals((Object)rect2)) continue;
                this.recordLastChildRect(view, rect2);
            }
            for (n4 = i + 1; n4 < n3; ++n4) {
                boolean bl;
                layoutParams = this.mDependencySortedChildren.get(n4);
                object = (LayoutParams)layoutParams.getLayoutParams();
                Behavior behavior = object.getBehavior();
                if (behavior == null || !behavior.layoutDependsOn(this, layoutParams, view)) continue;
                if (n == 0 && object.getChangedAfterNestedScroll()) {
                    object.resetChangedAfterNestedScroll();
                    continue;
                }
                if (n != 2) {
                    bl = behavior.onDependentViewChanged(this, layoutParams, view);
                } else {
                    behavior.onDependentViewRemoved(this, layoutParams, view);
                    bl = true;
                }
                if (n != 1) continue;
                object.setChangedAfterNestedScroll(bl);
            }
        }
        CoordinatorLayout.releaseTempRect(rect);
        CoordinatorLayout.releaseTempRect(rect2);
        CoordinatorLayout.releaseTempRect(rect3);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.resetTouchBehaviors(false);
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mNestedScrollingTarget != null) {
            this.onStopNestedScroll(this.mNestedScrollingTarget);
        }
        this.mIsAttachedToWindow = false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int n = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0;
            if (n > 0) {
                this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), n);
                this.mStatusBarBackground.draw(canvas);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.resetTouchBehaviors(true);
        }
        boolean bl = this.performIntercept(motionEvent, 0);
        if (n == 1 || n == 3) {
            this.resetTouchBehaviors(true);
        }
        return bl;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = ViewCompat.getLayoutDirection((View)this);
        n3 = this.mDependencySortedChildren.size();
        for (n = 0; n < n3; ++n) {
            Behavior behavior;
            View view = this.mDependencySortedChildren.get(n);
            if (view.getVisibility() == 8 || (behavior = ((LayoutParams)view.getLayoutParams()).getBehavior()) != null && behavior.onLayoutChild(this, view, n2)) continue;
            this.onLayoutChild(view, n2);
        }
    }

    public void onLayoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.checkAnchorChanged()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        if (layoutParams.mAnchorView != null) {
            this.layoutChildWithAnchor(view, layoutParams.mAnchorView, n);
            return;
        }
        if (layoutParams.keyline >= 0) {
            this.layoutChildWithKeyline(view, layoutParams.keyline, n);
            return;
        }
        this.layoutChild(view, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onMeasure(int var1_1, int var2_2) {
        this.prepareChildren();
        this.ensurePreDrawListener();
        var13_3 = this.getPaddingLeft();
        var14_4 = this.getPaddingTop();
        var15_5 = this.getPaddingRight();
        var16_6 = this.getPaddingBottom();
        var17_7 = ViewCompat.getLayoutDirection((View)this);
        var6_8 = var17_7 == 1;
        var18_9 = View.MeasureSpec.getMode((int)var1_1);
        var19_10 = View.MeasureSpec.getSize((int)var1_1);
        var20_11 = View.MeasureSpec.getMode((int)var2_2);
        var21_12 = View.MeasureSpec.getSize((int)var2_2);
        var10_13 = this.getSuggestedMinimumWidth();
        var4_14 = this.getSuggestedMinimumHeight();
        var7_15 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) != false;
        var8_16 = this.mDependencySortedChildren.size();
        var5_17 = 0;
        var9_18 = 0;
        do {
            block9 : {
                if (var9_18 >= var8_16) {
                    this.setMeasuredDimension(View.resolveSizeAndState((int)var10_13, (int)var1_1, (int)(-16777216 & var5_17)), View.resolveSizeAndState((int)var4_14, (int)var2_2, (int)(var5_17 << 16)));
                    return;
                }
                var24_24 = this.mDependencySortedChildren.get(var9_18);
                if (var24_24.getVisibility() == 8) break block9;
                var25_25 = (LayoutParams)var24_24.getLayoutParams();
                if (var25_25.keyline < 0 || var18_9 == 0) ** GOTO lbl-1000
                var3_19 = this.getKeyline(var25_25.keyline);
                var11_20 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(var25_25.gravity), var17_7) & 7;
                if (var11_20 == 3 && !var6_8 || var11_20 == 5 && var6_8) {
                    var3_19 = Math.max(0, var19_10 - var15_5 - var3_19);
                } else if (var11_20 == 5 && !var6_8 || var11_20 == 3 && var6_8) {
                    var3_19 = Math.max(0, var3_19 - var13_3);
                } else lbl-1000: // 2 sources:
                {
                    var3_19 = 0;
                }
                var11_20 = var5_17;
                var12_21 = var4_14;
                if (var7_15 && !ViewCompat.getFitsSystemWindows(var24_24)) {
                    var4_14 = this.mLastInsets.getSystemWindowInsetLeft();
                    var23_23 = this.mLastInsets.getSystemWindowInsetRight();
                    var5_17 = this.mLastInsets.getSystemWindowInsetTop();
                    var22_22 = this.mLastInsets.getSystemWindowInsetBottom();
                    var4_14 = View.MeasureSpec.makeMeasureSpec((int)(var19_10 - (var4_14 + var23_23)), (int)var18_9);
                    var5_17 = View.MeasureSpec.makeMeasureSpec((int)(var21_12 - (var5_17 + var22_22)), (int)var20_11);
                } else {
                    var4_14 = var1_1;
                    var5_17 = var2_2;
                }
                var26_26 = var25_25.getBehavior();
                if (var26_26 == null || !var26_26.onMeasureChild(this, var24_24, var4_14, var3_19, var5_17, 0)) {
                    this.onMeasureChild(var24_24, var4_14, var3_19, var5_17, 0);
                }
                var10_13 = Math.max(var10_13, var13_3 + var15_5 + var24_24.getMeasuredWidth() + var25_25.leftMargin + var25_25.rightMargin);
                var4_14 = Math.max(var12_21, var14_4 + var16_6 + var24_24.getMeasuredHeight() + var25_25.topMargin + var25_25.bottomMargin);
                var5_17 = View.combineMeasuredStates((int)var11_20, (int)var24_24.getMeasuredState());
            }
            ++var9_18;
        } while (true);
    }

    public void onMeasureChild(View view, int n, int n2, int n3, int n4) {
        this.measureChildWithMargins(view, n, n2, n3, n4);
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.onNestedPreScroll(view, n, n2, arrn, 0);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn, int n3) {
        int n4;
        int n5;
        int n6;
        int n7 = this.getChildCount();
        int n8 = n4 = (n6 = (n5 = 0));
        int n9 = n5;
        for (int i = n6; i < n7; ++i) {
            View view2 = this.getChildAt(i);
            if (view2.getVisibility() == 8) {
                n5 = n4;
                n6 = n8;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                if (!object.isNestedScrollAccepted(n3)) {
                    n5 = n4;
                    n6 = n8;
                } else {
                    object = object.getBehavior();
                    n5 = n4;
                    n6 = n8;
                    if (object != null) {
                        int[] arrn2 = this.mTempIntPair;
                        this.mTempIntPair[1] = 0;
                        arrn2[0] = 0;
                        object.onNestedPreScroll(this, view2, view, n, n2, this.mTempIntPair, n3);
                        n6 = n > 0 ? Math.max(n4, this.mTempIntPair[0]) : Math.min(n4, this.mTempIntPair[0]);
                        n4 = n2 > 0 ? Math.max(n8, this.mTempIntPair[1]) : Math.min(n8, this.mTempIntPair[1]);
                        n5 = n6;
                        n6 = n4;
                        n9 = 1;
                    }
                }
            }
            n4 = n5;
            n8 = n6;
        }
        arrn[0] = n4;
        arrn[1] = n8;
        if (n9 != 0) {
            this.onChildViewsChanged(1);
        }
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.onNestedScroll(view, n, n2, n3, n4, 0);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
        int n6 = this.getChildCount();
        boolean bl = false;
        for (int i = 0; i < n6; ++i) {
            Object object;
            View view2 = this.getChildAt(i);
            if (view2.getVisibility() == 8 || !(object = (LayoutParams)view2.getLayoutParams()).isNestedScrollAccepted(n5) || (object = object.getBehavior()) == null) continue;
            object.onNestedScroll(this, view2, view, n, n2, n3, n4, n5);
            bl = true;
        }
        if (bl) {
            this.onChildViewsChanged(1);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.onNestedScrollAccepted(view, view2, n, 0);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n, n2);
        this.mNestedScrollingTarget = view2;
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            View view3 = this.getChildAt(i);
            Object object = (LayoutParams)view3.getLayoutParams();
            if (!object.isNestedScrollAccepted(n2) || (object = object.getBehavior()) == null) continue;
            object.onNestedScrollAccepted(this, view3, view, view2, n, n2);
        }
    }

    protected void onRestoreInstanceState(Parcelable sparseArray) {
        if (!(sparseArray instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)sparseArray);
            return;
        }
        sparseArray = (SavedState)sparseArray;
        super.onRestoreInstanceState(sparseArray.getSuperState());
        sparseArray = sparseArray.behaviorStates;
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            Parcelable parcelable;
            View view = this.getChildAt(i);
            int n2 = view.getId();
            Behavior behavior = this.getResolvedLayoutParams(view).getBehavior();
            if (n2 == -1 || behavior == null || (parcelable = (Parcelable)sparseArray.get(n2)) == null) continue;
            behavior.onRestoreInstanceState(this, view, parcelable);
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            int n2 = view.getId();
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (n2 == -1 || behavior == null || (view = behavior.onSaveInstanceState(this, view)) == null) continue;
            sparseArray.append(n2, (Object)view);
        }
        savedState.behaviorStates = sparseArray;
        return savedState;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return this.onStartNestedScroll(view, view2, n, 0);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.onStopNestedScroll(view, 0);
    }

    @Override
    public void onStopNestedScroll(View view, int n) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, n);
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view2 = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (!layoutParams.isNestedScrollAccepted(n)) continue;
            Behavior behavior = layoutParams.getBehavior();
            if (behavior != null) {
                behavior.onStopNestedScroll(this, view2, view, n);
            }
            layoutParams.resetNestedScroll(n);
            layoutParams.resetChangedAfterNestedScroll();
        }
        this.mNestedScrollingTarget = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block8 : {
            block7 : {
                var2_6 = var1_1 /* !! */ .getActionMasked();
                if (this.mBehaviorTouchView != null) break block7;
                var4_8 = var3_7 = this.performIntercept(var1_1 /* !! */ , 1);
                if (!var3_7) ** GOTO lbl-1000
                break block8;
            }
            var3_7 = false;
        }
        var8_9 = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
        var4_8 = var3_7;
        if (var8_9 != null) {
            var4_8 = var8_9.onTouchEvent(this, this.mBehaviorTouchView, var1_1 /* !! */ );
        } else lbl-1000: // 2 sources:
        {
            var5_10 = false;
            var3_7 = var4_8;
            var4_8 = var5_10;
        }
        var9_11 = this.mBehaviorTouchView;
        var8_9 = null;
        if (var9_11 == null) {
            var5_10 = var4_8 | super.onTouchEvent(var1_1 /* !! */ );
            var1_2 = var8_9;
        } else {
            var5_10 = var4_8;
            var1_3 = var8_9;
            if (var3_7) {
                var6_12 = SystemClock.uptimeMillis();
                var1_4 = MotionEvent.obtain((long)var6_12, (long)var6_12, (int)3, (float)0.0f, (float)0.0f, (int)0);
                super.onTouchEvent(var1_4);
                var5_10 = var4_8;
            }
        }
        if (var1_5 != null) {
            var1_5.recycle();
        }
        if (var2_6 != 1) {
            if (var2_6 != 3) return var5_10;
        }
        this.resetTouchBehaviors(false);
        return var5_10;
    }

    void recordLastChildRect(View view, Rect rect) {
        ((LayoutParams)view.getLayoutParams()).setLastChildRect(rect);
    }

    void removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
        if (behavior != null && behavior.onRequestChildRectangleOnScreen(this, view, rect, bl)) {
            return true;
        }
        return super.requestChildRectangleOnScreen(view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        if (bl && !this.mDisallowInterceptReset) {
            this.resetTouchBehaviors(false);
            this.mDisallowInterceptReset = true;
        }
    }

    public void setFitsSystemWindows(boolean bl) {
        super.setFitsSystemWindows(bl);
        this.setupForInsets();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        if (this.mStatusBarBackground != drawable) {
            Drawable drawable2 = this.mStatusBarBackground;
            Drawable drawable3 = null;
            if (drawable2 != null) {
                this.mStatusBarBackground.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.mStatusBarBackground = drawable3;
            if (this.mStatusBarBackground != null) {
                if (this.mStatusBarBackground.isStateful()) {
                    this.mStatusBarBackground.setState(this.getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection((View)this));
                drawable = this.mStatusBarBackground;
                boolean bl = this.getVisibility() == 0;
                drawable.setVisible(bl, false);
                this.mStatusBarBackground.setCallback((Drawable.Callback)this);
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void setStatusBarBackgroundColor(@ColorInt int n) {
        this.setStatusBarBackground((Drawable)new ColorDrawable(n));
    }

    public void setStatusBarBackgroundResource(@DrawableRes int n) {
        Drawable drawable = n != 0 ? ContextCompat.getDrawable(this.getContext(), n) : null;
        this.setStatusBarBackground(drawable);
    }

    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        if (this.mStatusBarBackground != null && this.mStatusBarBackground.isVisible() != bl) {
            this.mStatusBarBackground.setVisible(bl, false);
        }
    }

    final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
        if (!ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) {
            this.mLastInsets = windowInsetsCompat;
            boolean bl = false;
            boolean bl2 = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
            this.mDrawStatusBarBackground = bl2;
            bl2 = bl;
            if (!this.mDrawStatusBarBackground) {
                bl2 = bl;
                if (this.getBackground() == null) {
                    bl2 = true;
                }
            }
            this.setWillNotDraw(bl2);
            windowInsetsCompat2 = this.dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat);
            this.requestLayout();
        }
        return windowInsetsCompat2;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (!super.verifyDrawable(drawable) && drawable != this.mStatusBarBackground) {
            return false;
        }
        return true;
    }

    public static interface AttachedBehavior {
        @NonNull
        public Behavior getBehavior();
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public static Object getTag(View view) {
            return ((LayoutParams)view.getLayoutParams()).mBehaviorTag;
        }

        public static void setTag(View view, Object object) {
            ((LayoutParams)view.getLayoutParams()).mBehaviorTag = object;
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
            if (this.getScrimOpacity(coordinatorLayout, v) > 0.0f) {
                return true;
            }
            return false;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect) {
            return false;
        }

        @ColorInt
        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
            return -16777216;
        }

        @FloatRange(from=0.0, to=1.0)
        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
            return 0.0f;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        @NonNull
        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3, int n4) {
            return false;
        }

        public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2, boolean bl) {
            return false;
        }

        public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
            return false;
        }

        @Deprecated
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, @NonNull int[] arrn) {
        }

        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, @NonNull int[] arrn, int n3) {
            if (n3 == 0) {
                this.onNestedPreScroll(coordinatorLayout, v, view, n, n2, arrn);
            }
        }

        @Deprecated
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, int n3, int n4) {
        }

        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, int n3, int n4, int n5) {
            if (n5 == 0) {
                this.onNestedScroll(coordinatorLayout, v, view, n, n2, n3, n4);
            }
        }

        @Deprecated
        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n) {
        }

        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n, int n2) {
            if (n2 == 0) {
                this.onNestedScrollAccepted(coordinatorLayout, v, view, view2, n);
            }
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean bl) {
            return false;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n) {
            return false;
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n, int n2) {
            if (n2 == 0) {
                return this.onStartNestedScroll(coordinatorLayout, v, view, view2, n);
            }
            return false;
        }

        @Deprecated
        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
        }

        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n) {
            if (n == 0) {
                this.onStopNestedScroll(coordinatorLayout, v, view);
            }
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }
    }

    @Deprecated
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface DefaultBehavior {
        public Class<? extends Behavior> value();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface DispatchChangeEvent {
    }

    private class HierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int anchorGravity = 0;
        public int dodgeInsetEdges = 0;
        public int gravity = 0;
        public int insetEdge = 0;
        public int keyline = -1;
        View mAnchorDirectChild;
        int mAnchorId = -1;
        View mAnchorView;
        Behavior mBehavior;
        boolean mBehaviorResolved = false;
        Object mBehaviorTag;
        private boolean mDidAcceptNestedScrollNonTouch;
        private boolean mDidAcceptNestedScrollTouch;
        private boolean mDidBlockInteraction;
        private boolean mDidChangeAfterNestedScroll;
        int mInsetOffsetX;
        int mInsetOffsetY;
        final Rect mLastChildRect = new Rect();

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
            this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.insetEdge = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.dodgeInsetEdges = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.mBehaviorResolved = typedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
            if (this.mBehaviorResolved) {
                this.mBehavior = CoordinatorLayout.parseBehavior(context, attributeSet, typedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            typedArray.recycle();
            if (this.mBehavior != null) {
                this.mBehavior.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            this.mAnchorView = coordinatorLayout.findViewById(this.mAnchorId);
            if (this.mAnchorView != null) {
                if (this.mAnchorView == coordinatorLayout) {
                    if (coordinatorLayout.isInEditMode()) {
                        this.mAnchorDirectChild = null;
                        this.mAnchorView = null;
                        return;
                    }
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
                View view2 = this.mAnchorView;
                for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                    if (viewParent == view) {
                        if (coordinatorLayout.isInEditMode()) {
                            this.mAnchorDirectChild = null;
                            this.mAnchorView = null;
                            return;
                        }
                        throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                    }
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                }
                this.mAnchorDirectChild = view2;
                return;
            }
            if (coordinatorLayout.isInEditMode()) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find CoordinatorLayout descendant view with id ");
            stringBuilder.append(coordinatorLayout.getResources().getResourceName(this.mAnchorId));
            stringBuilder.append(" to anchor view ");
            stringBuilder.append((Object)view);
            throw new IllegalStateException(stringBuilder.toString());
        }

        private boolean shouldDodge(View view, int n) {
            int n2 = GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).insetEdge, n);
            if (n2 != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, n) & n2) == n2) {
                return true;
            }
            return false;
        }

        private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false;
            }
            View view2 = this.mAnchorView;
            for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout; viewParent = viewParent.getParent()) {
                if (viewParent != null && viewParent != view) {
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                    continue;
                }
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return false;
            }
            this.mAnchorDirectChild = view2;
            return true;
        }

        boolean checkAnchorChanged() {
            if (this.mAnchorView == null && this.mAnchorId != -1) {
                return true;
            }
            return false;
        }

        boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (!(view2 == this.mAnchorDirectChild || this.shouldDodge(view2, ViewCompat.getLayoutDirection((View)coordinatorLayout)) || this.mBehavior != null && this.mBehavior.layoutDependsOn(coordinatorLayout, view, view2))) {
                return false;
            }
            return true;
        }

        boolean didBlockInteraction() {
            if (this.mBehavior == null) {
                this.mDidBlockInteraction = false;
            }
            return this.mDidBlockInteraction;
        }

        View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mAnchorId == -1) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return null;
            }
            if (this.mAnchorView == null || !this.verifyAnchorView(view, coordinatorLayout)) {
                this.resolveAnchorView(view, coordinatorLayout);
            }
            return this.mAnchorView;
        }

        @IdRes
        public int getAnchorId() {
            return this.mAnchorId;
        }

        @Nullable
        public Behavior getBehavior() {
            return this.mBehavior;
        }

        boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll;
        }

        Rect getLastChildRect() {
            return this.mLastChildRect;
        }

        void invalidateAnchor() {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
        }

        boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mDidBlockInteraction) {
                return true;
            }
            boolean bl = this.mDidBlockInteraction;
            boolean bl2 = this.mBehavior != null ? this.mBehavior.blocksInteractionBelow(coordinatorLayout, view) : false;
            this.mDidBlockInteraction = bl2 |= bl;
            return bl2;
        }

        boolean isNestedScrollAccepted(int n) {
            switch (n) {
                default: {
                    return false;
                }
                case 1: {
                    return this.mDidAcceptNestedScrollNonTouch;
                }
                case 0: 
            }
            return this.mDidAcceptNestedScrollTouch;
        }

        void resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false;
        }

        void resetNestedScroll(int n) {
            this.setNestedScrollAccepted(n, false);
        }

        void resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false;
        }

        public void setAnchorId(@IdRes int n) {
            this.invalidateAnchor();
            this.mAnchorId = n;
        }

        public void setBehavior(@Nullable Behavior behavior) {
            if (this.mBehavior != behavior) {
                if (this.mBehavior != null) {
                    this.mBehavior.onDetachedFromLayoutParams();
                }
                this.mBehavior = behavior;
                this.mBehaviorTag = null;
                this.mBehaviorResolved = true;
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this);
                }
            }
        }

        void setChangedAfterNestedScroll(boolean bl) {
            this.mDidChangeAfterNestedScroll = bl;
        }

        void setLastChildRect(Rect rect) {
            this.mLastChildRect.set(rect);
        }

        void setNestedScrollAccepted(int n, boolean bl) {
            switch (n) {
                default: {
                    return;
                }
                case 1: {
                    this.mDidAcceptNestedScrollNonTouch = bl;
                    return;
                }
                case 0: 
            }
            this.mDidAcceptNestedScrollTouch = bl;
        }
    }

    class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
        }
    }

    protected static class SavedState
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
        SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel arrparcelable, ClassLoader classLoader) {
            super((Parcel)arrparcelable, classLoader);
            int n = arrparcelable.readInt();
            int[] arrn = new int[n];
            arrparcelable.readIntArray(arrn);
            arrparcelable = arrparcelable.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray(n);
            for (int i = 0; i < n; ++i) {
                this.behaviorStates.append(arrn[i], (Object)arrparcelable[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            int[] arrn = this.behaviorStates;
            int n2 = arrn != null ? this.behaviorStates.size() : 0;
            parcel.writeInt(n2);
            arrn = new int[n2];
            Parcelable[] arrparcelable = new Parcelable[n2];
            for (int i = 0; i < n2; ++i) {
                arrn[i] = this.behaviorStates.keyAt(i);
                arrparcelable[i] = (Parcelable)this.behaviorStates.valueAt(i);
            }
            parcel.writeIntArray(arrn);
            parcel.writeParcelableArray(arrparcelable, n);
        }

    }

    static class ViewElevationComparator
    implements Comparator<View> {
        ViewElevationComparator() {
        }

        @Override
        public int compare(View view, View view2) {
            float f;
            float f2 = ViewCompat.getZ(view);
            if (f2 > (f = ViewCompat.getZ(view2))) {
                return -1;
            }
            if (f2 < f) {
                return 1;
            }
            return 0;
        }
    }

}
