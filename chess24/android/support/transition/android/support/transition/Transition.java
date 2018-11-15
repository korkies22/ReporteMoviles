/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.InflateException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.transition.AnimatorUtils;
import android.support.transition.PathMotion;
import android.support.transition.Styleable;
import android.support.transition.TransitionPropagation;
import android.support.transition.TransitionSet;
import android.support.transition.TransitionValues;
import android.support.transition.TransitionValuesMaps;
import android.support.transition.ViewUtils;
import android.support.transition.WindowIdImpl;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition
implements Cloneable {
    static final boolean DBG = false;
    private static final int[] DEFAULT_MATCH_ORDER = new int[]{2, 1, 3, 4};
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";
    private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion(){

        @Override
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal();
    private ArrayList<Animator> mAnimators = new ArrayList();
    boolean mCanRemoveViews = false;
    private ArrayList<Animator> mCurrentAnimators = new ArrayList();
    long mDuration = -1L;
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    private ArrayList<TransitionValues> mEndValuesList;
    private boolean mEnded = false;
    private EpicenterCallback mEpicenterCallback;
    private TimeInterpolator mInterpolator = null;
    private ArrayList<TransitionListener> mListeners = null;
    private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    private String mName = this.getClass().getName();
    private ArrayMap<String, String> mNameOverrides;
    private int mNumInstances = 0;
    TransitionSet mParent = null;
    private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;
    private boolean mPaused = false;
    TransitionPropagation mPropagation;
    private ViewGroup mSceneRoot = null;
    private long mStartDelay = -1L;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    private ArrayList<TransitionValues> mStartValuesList;
    private ArrayList<View> mTargetChildExcludes = null;
    private ArrayList<View> mTargetExcludes = null;
    private ArrayList<Integer> mTargetIdChildExcludes = null;
    private ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList();
    private ArrayList<String> mTargetNameExcludes = null;
    private ArrayList<String> mTargetNames = null;
    private ArrayList<Class> mTargetTypeChildExcludes = null;
    private ArrayList<Class> mTargetTypeExcludes = null;
    private ArrayList<Class> mTargetTypes = null;
    ArrayList<View> mTargets = new ArrayList();

    public Transition() {
    }

    public Transition(Context object, AttributeSet attributeSet) {
        int n;
        TypedArray typedArray = object.obtainStyledAttributes(attributeSet, Styleable.TRANSITION);
        attributeSet = (XmlResourceParser)attributeSet;
        long l = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)attributeSet, "duration", 1, -1);
        if (l >= 0L) {
            this.setDuration(l);
        }
        if ((l = (long)TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)attributeSet, "startDelay", 2, -1)) > 0L) {
            this.setStartDelay(l);
        }
        if ((n = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)attributeSet, "interpolator", 0, 0)) > 0) {
            this.setInterpolator((TimeInterpolator)AnimationUtils.loadInterpolator((Context)object, (int)n));
        }
        if ((object = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)attributeSet, "matchOrder", 3)) != null) {
            this.setMatchOrder(Transition.parseMatchOrder((String)object));
        }
        typedArray.recycle();
    }

    private void addUnmatched(ArrayMap<View, TransitionValues> object, ArrayMap<View, TransitionValues> arrayMap) {
        int n = 0;
        int n2 = 0;
        do {
            if (n2 >= object.size()) break;
            TransitionValues transitionValues = (TransitionValues)object.valueAt(n2);
            if (this.isValidTarget(transitionValues.view)) {
                this.mStartValuesList.add(transitionValues);
                this.mEndValuesList.add(null);
            }
            ++n2;
        } while (true);
        for (int i = n; i < arrayMap.size(); ++i) {
            object = (TransitionValues)arrayMap.valueAt(i);
            if (!this.isValidTarget(object.view)) continue;
            this.mEndValuesList.add((TransitionValues)object);
            this.mStartValuesList.add(null);
        }
    }

    private static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues object) {
        transitionValuesMaps.mViewValues.put(view, (TransitionValues)object);
        int n = view.getId();
        if (n >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(n) >= 0) {
                transitionValuesMaps.mIdValues.put(n, null);
            } else {
                transitionValuesMaps.mIdValues.put(n, (Object)view);
            }
        }
        if ((object = ViewCompat.getTransitionName(view)) != null) {
            if (transitionValuesMaps.mNameValues.containsKey(object)) {
                transitionValuesMaps.mNameValues.put((String)object, null);
            } else {
                transitionValuesMaps.mNameValues.put((String)object, view);
            }
        }
        if (view.getParent() instanceof ListView && (object = (ListView)view.getParent()).getAdapter().hasStableIds()) {
            long l = object.getItemIdAtPosition(object.getPositionForView(view));
            if (transitionValuesMaps.mItemIdValues.indexOfKey(l) >= 0) {
                view = transitionValuesMaps.mItemIdValues.get(l);
                if (view != null) {
                    ViewCompat.setHasTransientState(view, false);
                    transitionValuesMaps.mItemIdValues.put(l, null);
                    return;
                }
            } else {
                ViewCompat.setHasTransientState(view, true);
                transitionValuesMaps.mItemIdValues.put(l, view);
            }
        }
    }

    private static boolean alreadyContains(int[] arrn, int n) {
        int n2 = arrn[n];
        for (int i = 0; i < n; ++i) {
            if (arrn[i] != n2) continue;
            return true;
        }
        return false;
    }

    private void captureHierarchy(View view, boolean bl) {
        int n;
        if (view == null) {
            return;
        }
        int n2 = view.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(n2)) {
            return;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains((Object)view)) {
            return;
        }
        Object object = this.mTargetTypeExcludes;
        int n3 = 0;
        if (object != null) {
            int n4 = this.mTargetTypeExcludes.size();
            for (n = 0; n < n4; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance((Object)view)) continue;
                return;
            }
        }
        if (view.getParent() instanceof ViewGroup) {
            object = new TransitionValues();
            object.view = view;
            if (bl) {
                this.captureStartValues((TransitionValues)object);
            } else {
                this.captureEndValues((TransitionValues)object);
            }
            object.mTargetedTransitions.add(this);
            this.capturePropagationValues((TransitionValues)object);
            if (bl) {
                Transition.addViewValues(this.mStartValues, view, (TransitionValues)object);
            } else {
                Transition.addViewValues(this.mEndValues, view, (TransitionValues)object);
            }
        }
        if (view instanceof ViewGroup) {
            if (this.mTargetIdChildExcludes != null && this.mTargetIdChildExcludes.contains(n2)) {
                return;
            }
            if (this.mTargetChildExcludes != null && this.mTargetChildExcludes.contains((Object)view)) {
                return;
            }
            if (this.mTargetTypeChildExcludes != null) {
                n2 = this.mTargetTypeChildExcludes.size();
                for (n = 0; n < n2; ++n) {
                    if (!this.mTargetTypeChildExcludes.get(n).isInstance((Object)view)) continue;
                    return;
                }
            }
            view = (ViewGroup)view;
            for (n = n3; n < view.getChildCount(); ++n) {
                this.captureHierarchy(view.getChildAt(n), bl);
            }
        }
    }

    private ArrayList<Integer> excludeId(ArrayList<Integer> arrayList, int n, boolean bl) {
        ArrayList<Integer> arrayList2 = arrayList;
        if (n > 0) {
            if (bl) {
                return ArrayListManager.add(arrayList, n);
            }
            arrayList2 = ArrayListManager.remove(arrayList, n);
        }
        return arrayList2;
    }

    private static <T> ArrayList<T> excludeObject(ArrayList<T> arrayList, T t, boolean bl) {
        ArrayList<T> arrayList2 = arrayList;
        if (t != null) {
            if (bl) {
                return ArrayListManager.add(arrayList, t);
            }
            arrayList2 = ArrayListManager.remove(arrayList, t);
        }
        return arrayList2;
    }

    private ArrayList<Class> excludeType(ArrayList<Class> arrayList, Class class_, boolean bl) {
        ArrayList<Class> arrayList2 = arrayList;
        if (class_ != null) {
            if (bl) {
                return ArrayListManager.add(arrayList, class_);
            }
            arrayList2 = ArrayListManager.remove(arrayList, class_);
        }
        return arrayList2;
    }

    private ArrayList<View> excludeView(ArrayList<View> arrayList, View view, boolean bl) {
        ArrayList<View> arrayList2 = arrayList;
        if (view != null) {
            if (bl) {
                return ArrayListManager.add(arrayList, view);
            }
            arrayList2 = ArrayListManager.remove(arrayList, view);
        }
        return arrayList2;
    }

    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> arrayMap;
        ArrayMap<Object, AnimationInfo> arrayMap2 = arrayMap = sRunningAnimators.get();
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            sRunningAnimators.set(arrayMap2);
        }
        return arrayMap2;
    }

    private static boolean isValidMatch(int n) {
        if (n >= 1 && n <= 4) {
            return true;
        }
        return false;
    }

    private static boolean isValueChanged(TransitionValues object, TransitionValues object2, String string) {
        object = object.values.get(string);
        object2 = object2.values.get(string);
        boolean bl = true;
        if (object == null && object2 == null) {
            return false;
        }
        if (object != null) {
            if (object2 == null) {
                return true;
            }
            bl = true ^ object.equals(object2);
        }
        return bl;
    }

    private void matchIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        int n = sparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = (View)sparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = (View)sparseArray2.get(sparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    private void matchInstances(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            Object object = (View)arrayMap.keyAt(i);
            if (object == null || !this.isValidTarget((View)object) || (object = (TransitionValues)arrayMap2.remove(object)) == null || object.view == null || !this.isValidTarget(object.view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.removeAt(i);
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add((TransitionValues)object);
        }
    }

    private void matchItemIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        int n = longSparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = longSparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = longSparseArray2.get(longSparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    private void matchNames(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        int n = arrayMap3.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = (View)arrayMap3.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = (View)arrayMap4.get(arrayMap3.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
            TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove((Object)view2);
            arrayMap2.remove((Object)view);
        }
    }

    private void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<View, TransitionValues>(transitionValuesMaps.mViewValues);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<View, TransitionValues>(transitionValuesMaps2.mViewValues);
        block6 : for (int i = 0; i < this.mMatchOrder.length; ++i) {
            switch (this.mMatchOrder[i]) {
                default: {
                    continue block6;
                }
                case 4: {
                    this.matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.mItemIdValues, transitionValuesMaps2.mItemIdValues);
                    continue block6;
                }
                case 3: {
                    this.matchIds(arrayMap, arrayMap2, transitionValuesMaps.mIdValues, transitionValuesMaps2.mIdValues);
                    continue block6;
                }
                case 2: {
                    this.matchNames(arrayMap, arrayMap2, transitionValuesMaps.mNameValues, transitionValuesMaps2.mNameValues);
                    continue block6;
                }
                case 1: {
                    this.matchInstances(arrayMap, arrayMap2);
                }
            }
        }
        this.addUnmatched(arrayMap, arrayMap2);
    }

    private static int[] parseMatchOrder(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, ",");
        object = new int[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int[] arrn;
            block8 : {
                block4 : {
                    block7 : {
                        block6 : {
                            block5 : {
                                block3 : {
                                    arrn = stringTokenizer.nextToken().trim();
                                    if (!MATCH_ID_STR.equalsIgnoreCase((String)arrn)) break block3;
                                    object[n] = 3;
                                    break block4;
                                }
                                if (!MATCH_INSTANCE_STR.equalsIgnoreCase((String)arrn)) break block5;
                                object[n] = 1;
                                break block4;
                            }
                            if (!MATCH_NAME_STR.equalsIgnoreCase((String)arrn)) break block6;
                            object[n] = 2;
                            break block4;
                        }
                        if (!MATCH_ITEM_ID_STR.equalsIgnoreCase((String)arrn)) break block7;
                        object[n] = 4;
                        break block4;
                    }
                    if (!arrn.isEmpty()) break block8;
                    arrn = new int[((int[])object).length - 1];
                    System.arraycopy(object, 0, arrn, 0, n);
                    --n;
                    object = arrn;
                }
                ++n;
                continue;
            }
            object = new StringBuilder();
            object.append("Unknown match type in matchOrder: '");
            object.append((String)arrn);
            object.append("'");
            throw new InflateException(object.toString());
        }
        return object;
    }

    private void runAnimator(Animator animator, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator != null) {
            animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator) {
                    arrayMap.remove((Object)animator);
                    Transition.this.mCurrentAnimators.remove((Object)animator);
                }

                public void onAnimationStart(Animator animator) {
                    Transition.this.mCurrentAnimators.add(animator);
                }
            });
            this.animate(animator);
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int n) {
        if (n != 0) {
            this.mTargetIds.add(n);
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull View view) {
        this.mTargets.add(view);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class class_) {
        if (this.mTargetTypes == null) {
            this.mTargetTypes = new ArrayList();
        }
        this.mTargetTypes.add(class_);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String string) {
        if (this.mTargetNames == null) {
            this.mTargetNames = new ArrayList();
        }
        this.mTargetNames.add(string);
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void animate(Animator animator) {
        if (animator == null) {
            this.end();
            return;
        }
        if (this.getDuration() >= 0L) {
            animator.setDuration(this.getDuration());
        }
        if (this.getStartDelay() >= 0L) {
            animator.setStartDelay(this.getStartDelay());
        }
        if (this.getInterpolator() != null) {
            animator.setInterpolator(this.getInterpolator());
        }
        animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                Transition.this.end();
                animator.removeListener((Animator.AnimatorListener)this);
            }
        });
        animator.start();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void cancel() {
        int n;
        for (n = this.mCurrentAnimators.size() - 1; n >= 0; --n) {
            this.mCurrentAnimators.get(n).cancel();
        }
        if (this.mListeners != null && this.mListeners.size() > 0) {
            ArrayList arrayList = (ArrayList)this.mListeners.clone();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                ((TransitionListener)arrayList.get(n)).onTransitionCancel(this);
            }
        }
    }

    public abstract void captureEndValues(@NonNull TransitionValues var1);

    void capturePropagationValues(TransitionValues transitionValues) {
        if (this.mPropagation != null && !transitionValues.values.isEmpty()) {
            int n;
            block4 : {
                String[] arrstring = this.mPropagation.getPropagationProperties();
                if (arrstring == null) {
                    return;
                }
                int n2 = 0;
                for (n = 0; n < arrstring.length; ++n) {
                    if (transitionValues.values.containsKey(arrstring[n])) continue;
                    n = n2;
                    break block4;
                }
                n = 1;
            }
            if (n == 0) {
                this.mPropagation.captureValues(transitionValues);
            }
        }
    }

    public abstract void captureStartValues(@NonNull TransitionValues var1);

    void captureValues(ViewGroup object, boolean bl) {
        Object object2;
        Object object3;
        this.clearValues(bl);
        int n = this.mTargetIds.size();
        int n2 = 0;
        if (n <= 0 && this.mTargets.size() <= 0 || this.mTargetNames != null && !this.mTargetNames.isEmpty() || this.mTargetTypes != null && !this.mTargetTypes.isEmpty()) {
            this.captureHierarchy((View)object, bl);
        } else {
            for (n = 0; n < this.mTargetIds.size(); ++n) {
                object2 = object.findViewById(this.mTargetIds.get(n).intValue());
                if (object2 == null) continue;
                object3 = new TransitionValues();
                object3.view = object2;
                if (bl) {
                    this.captureStartValues((TransitionValues)object3);
                } else {
                    this.captureEndValues((TransitionValues)object3);
                }
                object3.mTargetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object3);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, object2, (TransitionValues)object3);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, object2, (TransitionValues)object3);
            }
            for (n = 0; n < this.mTargets.size(); ++n) {
                object = this.mTargets.get(n);
                object2 = new TransitionValues();
                object2.view = object;
                if (bl) {
                    this.captureStartValues((TransitionValues)object2);
                } else {
                    this.captureEndValues((TransitionValues)object2);
                }
                object2.mTargetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object2);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, (View)object, (TransitionValues)object2);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, (View)object, (TransitionValues)object2);
            }
        }
        if (!bl && this.mNameOverrides != null) {
            int n3 = this.mNameOverrides.size();
            object = new ArrayList<E>(n3);
            n = 0;
            do {
                if (n >= n3) break;
                object2 = (String)this.mNameOverrides.keyAt(n);
                object.add(this.mStartValues.mNameValues.remove(object2));
                ++n;
            } while (true);
            for (int i = n2; i < n3; ++i) {
                object2 = (View)object.get(i);
                if (object2 == null) continue;
                object3 = (String)this.mNameOverrides.valueAt(i);
                this.mStartValues.mNameValues.put((String)object3, (View)object2);
            }
        }
    }

    void clearValues(boolean bl) {
        if (bl) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
            return;
        }
        this.mEndValues.mViewValues.clear();
        this.mEndValues.mIdValues.clear();
        this.mEndValues.mItemIdValues.clear();
    }

    public Transition clone() {
        try {
            Transition transition = (Transition)super.clone();
            transition.mAnimators = new ArrayList<E>();
            transition.mStartValues = new TransitionValuesMaps();
            transition.mEndValues = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps object, TransitionValuesMaps transitionValuesMaps, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int n = arrayList.size();
        long l = Long.MAX_VALUE;
        int n2 = 0;
        do {
            long l2;
            int n3;
            block19 : {
                Object object2;
                TransitionValues transitionValues;
                Object object3;
                View view;
                block14 : {
                    int n4;
                    TransitionValues transitionValues2;
                    block20 : {
                        block15 : {
                            block17 : {
                                block18 : {
                                    block13 : {
                                        block16 : {
                                            if (n2 >= n) break block15;
                                            object3 = arrayList.get(n2);
                                            object = arrayList2.get(n2);
                                            transitionValues = object3;
                                            if (object3 != null) {
                                                transitionValues = object3;
                                                if (!object3.mTargetedTransitions.contains(this)) {
                                                    transitionValues = null;
                                                }
                                            }
                                            object2 = object;
                                            if (object != null) {
                                                object2 = object;
                                                if (!object.mTargetedTransitions.contains(this)) {
                                                    object2 = null;
                                                }
                                            }
                                            if (transitionValues == null && object2 == null) break block16;
                                            n3 = transitionValues != null && object2 != null && !this.isTransitionRequired(transitionValues, (TransitionValues)object2) ? 0 : 1;
                                            if (n3 == 0 || (object = this.createAnimator(viewGroup, transitionValues, (TransitionValues)object2)) == null) break block16;
                                            if (object2 == null) break block17;
                                            view = object2.view;
                                            String[] arrstring = this.getTransitionProperties();
                                            if (view == null || arrstring == null || arrstring.length <= 0) break block18;
                                            transitionValues2 = new TransitionValues();
                                            transitionValues2.view = view;
                                            object3 = (TransitionValues)transitionValuesMaps.mViewValues.get((Object)view);
                                            n3 = n2;
                                            if (object3 != null) {
                                                n4 = 0;
                                                do {
                                                    n3 = n2;
                                                    if (n4 < arrstring.length) {
                                                        transitionValues2.values.put(arrstring[n4], object3.values.get(arrstring[n4]));
                                                        ++n4;
                                                        continue;
                                                    }
                                                    break block13;
                                                    break;
                                                } while (true);
                                            }
                                            break block13;
                                        }
                                        l2 = l;
                                        n3 = n2;
                                        break block19;
                                    }
                                    n2 = n3;
                                    n4 = arrayMap.size();
                                    break block20;
                                }
                                object3 = null;
                                break block14;
                            }
                            view = transitionValues.view;
                            object3 = null;
                            break block14;
                        }
                        if (l != 0L) {
                            for (n2 = 0; n2 < sparseIntArray.size(); ++n2) {
                                n3 = sparseIntArray.keyAt(n2);
                                viewGroup = this.mAnimators.get(n3);
                                viewGroup.setStartDelay((long)sparseIntArray.valueAt(n2) - l + viewGroup.getStartDelay());
                            }
                        }
                        return;
                    }
                    for (n3 = 0; n3 < n4; ++n3) {
                        object3 = (AnimationInfo)arrayMap.get((Object)((Animator)arrayMap.keyAt(n3)));
                        if (object3.mValues == null || object3.mView != view || !object3.mName.equals(this.getName()) || !object3.mValues.equals(transitionValues2)) continue;
                        object = null;
                        object3 = transitionValues2;
                        break block14;
                    }
                    object3 = transitionValues2;
                }
                l2 = l;
                n3 = n2;
                if (object != null) {
                    l2 = l;
                    if (this.mPropagation != null) {
                        l2 = this.mPropagation.getStartDelay(viewGroup, this, transitionValues, (TransitionValues)object2);
                        sparseIntArray.put(this.mAnimators.size(), (int)l2);
                        l2 = Math.min(l2, l);
                    }
                    arrayMap.put((Animator)object, new AnimationInfo(view, this.getName(), this, ViewUtils.getWindowId((View)viewGroup), (TransitionValues)object3));
                    this.mAnimators.add((Animator)object);
                    n3 = n2;
                }
            }
            n2 = n3 + 1;
            l = l2;
        } while (true);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void end() {
        --this.mNumInstances;
        if (this.mNumInstances == 0) {
            ArrayList arrayList;
            int n;
            if (this.mListeners != null && this.mListeners.size() > 0) {
                arrayList = (ArrayList)this.mListeners.clone();
                int n2 = arrayList.size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)arrayList.get(n)).onTransitionEnd(this);
                }
            }
            for (n = 0; n < this.mStartValues.mItemIdValues.size(); ++n) {
                arrayList = this.mStartValues.mItemIdValues.valueAt(n);
                if (arrayList == null) continue;
                ViewCompat.setHasTransientState((View)arrayList, false);
            }
            for (n = 0; n < this.mEndValues.mItemIdValues.size(); ++n) {
                arrayList = this.mEndValues.mItemIdValues.valueAt(n);
                if (arrayList == null) continue;
                ViewCompat.setHasTransientState((View)arrayList, false);
            }
            this.mEnded = true;
        }
    }

    @NonNull
    public Transition excludeChildren(@IdRes int n, boolean bl) {
        this.mTargetIdChildExcludes = this.excludeId(this.mTargetIdChildExcludes, n, bl);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view, boolean bl) {
        this.mTargetChildExcludes = this.excludeView(this.mTargetChildExcludes, view, bl);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class class_, boolean bl) {
        this.mTargetTypeChildExcludes = this.excludeType(this.mTargetTypeChildExcludes, class_, bl);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int n, boolean bl) {
        this.mTargetIdExcludes = this.excludeId(this.mTargetIdExcludes, n, bl);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean bl) {
        this.mTargetExcludes = this.excludeView(this.mTargetExcludes, view, bl);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class class_, boolean bl) {
        this.mTargetTypeExcludes = this.excludeType(this.mTargetTypeExcludes, class_, bl);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String string, boolean bl) {
        this.mTargetNameExcludes = Transition.excludeObject(this.mTargetNameExcludes, string, bl);
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    void forceToEnd(ViewGroup object) {
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        if (object != null) {
            object = ViewUtils.getWindowId((View)object);
            --n;
            while (n >= 0) {
                AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                if (animationInfo.mView != null && object != null && object.equals(animationInfo.mWindowId)) {
                    ((Animator)arrayMap.keyAt(n)).end();
                }
                --n;
            }
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    @Nullable
    public Rect getEpicenter() {
        if (this.mEpicenterCallback == null) {
            return null;
        }
        return this.mEpicenterCallback.onGetEpicenter(this);
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    TransitionValues getMatchedTransitionValues(View object, boolean bl) {
        int n;
        if (this.mParent != null) {
            return this.mParent.getMatchedTransitionValues((View)object, bl);
        }
        ArrayList<TransitionValues> arrayList = bl ? this.mStartValuesList : this.mEndValuesList;
        Object var8_4 = null;
        if (arrayList == null) {
            return null;
        }
        int n2 = arrayList.size();
        int n3 = -1;
        int n4 = 0;
        do {
            n = n3;
            if (n4 >= n2) break;
            TransitionValues transitionValues = arrayList.get(n4);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == object) {
                n = n4;
                break;
            }
            ++n4;
        } while (true);
        object = var8_4;
        if (n >= 0) {
            object = bl ? this.mEndValuesList : this.mStartValuesList;
            object = (TransitionValues)object.get(n);
        }
        return object;
    }

    @NonNull
    public String getName() {
        return this.mName;
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.mPropagation;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.mTargetIds;
    }

    @Nullable
    public List<String> getTargetNames() {
        return this.mTargetNames;
    }

    @Nullable
    public List<Class> getTargetTypes() {
        return this.mTargetTypes;
    }

    @NonNull
    public List<View> getTargets() {
        return this.mTargets;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view, boolean bl) {
        if (this.mParent != null) {
            return this.mParent.getTransitionValues(view, bl);
        }
        TransitionValuesMaps transitionValuesMaps = bl ? this.mStartValues : this.mEndValues;
        return (TransitionValues)transitionValuesMaps.mViewValues.get((Object)view);
    }

    public boolean isTransitionRequired(@Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        boolean bl;
        block6 : {
            boolean bl2;
            bl = bl2 = false;
            if (transitionValues != null) {
                bl = bl2;
                if (transitionValues2 != null) {
                    block7 : {
                        Object object = this.getTransitionProperties();
                        if (object != null) {
                            int n = ((String[])object).length;
                            int n2 = 0;
                            do {
                                bl = bl2;
                                if (n2 >= n) break block6;
                                if (!Transition.isValueChanged(transitionValues, transitionValues2, (String)object[n2])) {
                                    ++n2;
                                    continue;
                                }
                                break block7;
                                break;
                            } while (true);
                        }
                        object = transitionValues.values.keySet().iterator();
                        do {
                            bl = bl2;
                            if (!object.hasNext()) break block6;
                        } while (!Transition.isValueChanged(transitionValues, transitionValues2, (String)object.next()));
                    }
                    bl = true;
                }
            }
        }
        return bl;
    }

    boolean isValidTarget(View view) {
        int n;
        int n2 = view.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(n2)) {
            return false;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains((Object)view)) {
            return false;
        }
        if (this.mTargetTypeExcludes != null) {
            int n3 = this.mTargetTypeExcludes.size();
            for (n = 0; n < n3; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance((Object)view)) continue;
                return false;
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(view) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && (this.mTargetTypes == null || this.mTargetTypes.isEmpty()) && (this.mTargetNames == null || this.mTargetNames.isEmpty())) {
            return true;
        }
        if (!this.mTargetIds.contains(n2)) {
            if (this.mTargets.contains((Object)view)) {
                return true;
            }
            if (this.mTargetNames != null && this.mTargetNames.contains(ViewCompat.getTransitionName(view))) {
                return true;
            }
            if (this.mTargetTypes != null) {
                for (n = 0; n < this.mTargetTypes.size(); ++n) {
                    if (!this.mTargetTypes.get(n).isInstance((Object)view)) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void pause(View object) {
        if (!this.mEnded) {
            ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
            int n = arrayMap.size();
            object = ViewUtils.getWindowId((View)object);
            --n;
            while (n >= 0) {
                AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                if (animationInfo.mView != null && object.equals(animationInfo.mWindowId)) {
                    AnimatorUtils.pause((Animator)arrayMap.keyAt(n));
                }
                --n;
            }
            if (this.mListeners != null && this.mListeners.size() > 0) {
                object = (ArrayList)this.mListeners.clone();
                int n2 = object.size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)object.get(n)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    void playTransition(ViewGroup viewGroup) {
        this.mStartValuesList = new ArrayList<E>();
        this.mEndValuesList = new ArrayList<E>();
        this.matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)viewGroup);
        --n;
        while (n >= 0) {
            AnimationInfo animationInfo;
            Animator animator = (Animator)arrayMap.keyAt(n);
            if (animator != null && (animationInfo = (AnimationInfo)arrayMap.get((Object)animator)) != null && animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId)) {
                TransitionValues transitionValues = animationInfo.mValues;
                Object object = animationInfo.mView;
                TransitionValues transitionValues2 = this.getTransitionValues((View)object, true);
                object = this.getMatchedTransitionValues((View)object, true);
                boolean bl = (transitionValues2 != null || object != null) && animationInfo.mTransition.isTransitionRequired(transitionValues, (TransitionValues)object);
                if (bl) {
                    if (!animator.isRunning() && !animator.isStarted()) {
                        arrayMap.remove((Object)animator);
                    } else {
                        animator.cancel();
                    }
                }
            }
            --n;
        }
        this.createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        this.runAnimators();
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        if (this.mListeners == null) {
            return this;
        }
        this.mListeners.remove(transitionListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int n) {
        if (n != 0) {
            this.mTargetIds.remove((Object)n);
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view) {
        this.mTargets.remove((Object)view);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class class_) {
        if (this.mTargetTypes != null) {
            this.mTargetTypes.remove(class_);
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String string) {
        if (this.mTargetNames != null) {
            this.mTargetNames.remove(string);
        }
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void resume(View object) {
        if (this.mPaused) {
            if (!this.mEnded) {
                ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
                int n = arrayMap.size();
                object = ViewUtils.getWindowId((View)object);
                --n;
                while (n >= 0) {
                    AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                    if (animationInfo.mView != null && object.equals(animationInfo.mWindowId)) {
                        AnimatorUtils.resume((Animator)arrayMap.keyAt(n));
                    }
                    --n;
                }
                if (this.mListeners != null && this.mListeners.size() > 0) {
                    object = (ArrayList)this.mListeners.clone();
                    int n2 = object.size();
                    for (n = 0; n < n2; ++n) {
                        ((TransitionListener)object.get(n)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void runAnimators() {
        this.start();
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        for (Animator animator : this.mAnimators) {
            if (!arrayMap.containsKey((Object)animator)) continue;
            this.start();
            this.runAnimator(animator, arrayMap);
        }
        this.mAnimators.clear();
        this.end();
    }

    void setCanRemoveViews(boolean bl) {
        this.mCanRemoveViews = bl;
    }

    @NonNull
    public Transition setDuration(long l) {
        this.mDuration = l;
        return this;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public /* varargs */ void setMatchOrder(int ... arrn) {
        if (arrn != null && arrn.length != 0) {
            for (int i = 0; i < arrn.length; ++i) {
                if (!Transition.isValidMatch(arrn[i])) {
                    throw new IllegalArgumentException("matches contains invalid value");
                }
                if (!Transition.alreadyContains(arrn, i)) continue;
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
            this.mMatchOrder = (int[])arrn.clone();
            return;
        }
        this.mMatchOrder = DEFAULT_MATCH_ORDER;
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
            return;
        }
        this.mPathMotion = pathMotion;
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    Transition setSceneRoot(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
        return this;
    }

    @NonNull
    public Transition setStartDelay(long l) {
        this.mStartDelay = l;
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected void start() {
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList arrayList = (ArrayList)this.mListeners.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((TransitionListener)arrayList.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        ++this.mNumInstances;
    }

    public String toString() {
        return this.toString("");
    }

    String toString(String charSequence) {
        CharSequence charSequence2;
        block13 : {
            block12 : {
                charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append(this.getClass().getSimpleName());
                charSequence2.append("@");
                charSequence2.append(Integer.toHexString(this.hashCode()));
                charSequence2.append(": ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
                charSequence = charSequence2;
                if (this.mDuration != -1L) {
                    charSequence = new StringBuilder();
                    charSequence.append((String)charSequence2);
                    charSequence.append("dur(");
                    charSequence.append(this.mDuration);
                    charSequence.append(") ");
                    charSequence = charSequence.toString();
                }
                charSequence2 = charSequence;
                if (this.mStartDelay != -1L) {
                    charSequence2 = new StringBuilder();
                    charSequence2.append((String)charSequence);
                    charSequence2.append("dly(");
                    charSequence2.append(this.mStartDelay);
                    charSequence2.append(") ");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = charSequence2;
                if (this.mInterpolator != null) {
                    charSequence = new StringBuilder();
                    charSequence.append((String)charSequence2);
                    charSequence.append("interp(");
                    charSequence.append((Object)this.mInterpolator);
                    charSequence.append(") ");
                    charSequence = charSequence.toString();
                }
                if (this.mTargetIds.size() > 0) break block12;
                charSequence2 = charSequence;
                if (this.mTargets.size() <= 0) break block13;
            }
            charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append("tgts(");
            charSequence2 = ((StringBuilder)charSequence2).toString();
            int n = this.mTargetIds.size();
            int n2 = 0;
            charSequence = charSequence2;
            if (n > 0) {
                charSequence = charSequence2;
                for (n = 0; n < this.mTargetIds.size(); ++n) {
                    charSequence2 = charSequence;
                    if (n > 0) {
                        charSequence2 = new StringBuilder();
                        charSequence2.append((String)charSequence);
                        charSequence2.append(", ");
                        charSequence2 = ((StringBuilder)charSequence2).toString();
                    }
                    charSequence = new StringBuilder();
                    charSequence.append((String)charSequence2);
                    charSequence.append(this.mTargetIds.get(n));
                    charSequence = charSequence.toString();
                }
            }
            charSequence2 = charSequence;
            if (this.mTargets.size() > 0) {
                n = n2;
                do {
                    charSequence2 = charSequence;
                    if (n >= this.mTargets.size()) break;
                    charSequence2 = charSequence;
                    if (n > 0) {
                        charSequence2 = new StringBuilder();
                        charSequence2.append((String)charSequence);
                        charSequence2.append(", ");
                        charSequence2 = ((StringBuilder)charSequence2).toString();
                    }
                    charSequence = new StringBuilder();
                    charSequence.append((String)charSequence2);
                    charSequence.append((Object)this.mTargets.get(n));
                    charSequence = charSequence.toString();
                    ++n;
                } while (true);
            }
            charSequence = new StringBuilder();
            charSequence.append((String)charSequence2);
            charSequence.append(")");
            charSequence2 = charSequence.toString();
        }
        return charSequence2;
    }

    private static class AnimationInfo {
        String mName;
        Transition mTransition;
        TransitionValues mValues;
        View mView;
        WindowIdImpl mWindowId;

        AnimationInfo(View view, String string, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.mView = view;
            this.mName = string;
            this.mValues = transitionValues;
            this.mWindowId = windowIdImpl;
            this.mTransition = transition;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
            ArrayList<Object> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList();
            }
            if (!arrayList2.contains(t)) {
                arrayList2.add(t);
            }
            return arrayList2;
        }

        static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
            ArrayList<T> arrayList2 = arrayList;
            if (arrayList != null) {
                arrayList.remove(t);
                arrayList2 = arrayList;
                if (arrayList.isEmpty()) {
                    arrayList2 = null;
                }
            }
            return arrayList2;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface MatchOrder {
    }

    public static interface TransitionListener {
        public void onTransitionCancel(@NonNull Transition var1);

        public void onTransitionEnd(@NonNull Transition var1);

        public void onTransitionPause(@NonNull Transition var1);

        public void onTransitionResume(@NonNull Transition var1);

        public void onTransitionStart(@NonNull Transition var1);
    }

}
