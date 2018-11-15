/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransitionCompat21;
import android.support.v4.app.FragmentTransitionImpl;
import android.support.v4.app.OneShotPreDrawListener;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FragmentTransition {
    private static final int[] INVERSE_OPS = new int[]{0, 3, 0, 1, 5, 4, 7, 6, 9, 8};
    private static final FragmentTransitionImpl PLATFORM_IMPL;
    private static final FragmentTransitionImpl SUPPORT_IMPL;

    static {
        FragmentTransitionCompat21 fragmentTransitionCompat21 = Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null;
        PLATFORM_IMPL = fragmentTransitionCompat21;
        SUPPORT_IMPL = FragmentTransition.resolveSupportImpl();
    }

    FragmentTransition() {
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            View view = (View)arrayMap.valueAt(i);
            if (!collection.contains(ViewCompat.getTransitionName(view))) continue;
            arrayList.add(view);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void addToFirstInLastOut(BackStackRecord var0, BackStackRecord.Op var1_1, SparseArray<FragmentContainerTransition> var2_2, boolean var3_3, boolean var4_4) {
        block24 : {
            block25 : {
                block22 : {
                    block23 : {
                        block21 : {
                            var11_5 = var1_1.fragment;
                            if (var11_5 == null) {
                                return;
                            }
                            var8_6 = var11_5.mContainerId;
                            if (var8_6 == 0) {
                                return;
                            }
                            var5_7 = var3_3 != false ? FragmentTransition.INVERSE_OPS[var1_1.cmd] : var1_1.cmd;
                            var9_8 = false;
                            if (var5_7 == 1) break block21;
                            switch (var5_7) {
                                default: {
                                    var7_10 = var6_9 = (var5_7 = 0);
                                    break block22;
                                }
                                case 5: {
                                    if (!var4_4) ** GOTO lbl18
                                    if (!var11_5.mHiddenChanged || var11_5.mHidden || !var11_5.mAdded) ** GOTO lbl-1000
                                    ** GOTO lbl-1000
lbl18: // 1 sources:
                                    var9_8 = var11_5.mHidden;
                                    break block23;
                                }
                                case 4: {
                                    if (!(var4_4 != false ? var11_5.mHiddenChanged != false && var11_5.mAdded != false && var11_5.mHidden != false : var11_5.mAdded != false && var11_5.mHidden == false)) ** GOTO lbl-1000
                                    ** GOTO lbl-1000
                                }
                                case 3: 
                                case 6: {
                                    if (var4_4 != false ? var11_5.mAdded == false && var11_5.mView != null && var11_5.mView.getVisibility() == 0 && var11_5.mPostponedAlpha >= 0.0f : var11_5.mAdded != false && var11_5.mHidden == false) lbl-1000: // 2 sources:
                                    {
                                        var5_7 = 1;
                                    } else lbl-1000: // 2 sources:
                                    {
                                        var5_7 = 0;
                                    }
                                    var7_10 = var5_7;
                                    var5_7 = 0;
                                    var6_9 = 1;
                                    break block22;
                                }
                                case 7: 
                            }
                        }
                        if (var4_4) {
                            var9_8 = var11_5.mIsNewlyAdded;
                        } else if (!var11_5.mAdded && !var11_5.mHidden) lbl-1000: // 2 sources:
                        {
                            var9_8 = true;
                        } else lbl-1000: // 2 sources:
                        {
                            var9_8 = false;
                        }
                    }
                    var7_10 = var6_9 = 0;
                    var5_7 = 1;
                }
                var1_1 = var10_11 = (FragmentContainerTransition)var2_2.get(var8_6);
                if (var9_8) {
                    var1_1 = FragmentTransition.ensureContainer((FragmentContainerTransition)var10_11, var2_2, var8_6);
                    var1_1.lastIn = var11_5;
                    var1_1.lastInIsPop = var3_3;
                    var1_1.lastInTransaction = var0;
                }
                if (!var4_4 && var5_7 != 0) {
                    if (var1_1 != null && var1_1.firstOut == var11_5) {
                        var1_1.firstOut = null;
                    }
                    var10_11 = var0.mManager;
                    if (var11_5.mState < 1 && var10_11.mCurState >= 1 && !var0.mReorderingAllowed) {
                        var10_11.makeActive(var11_5);
                        var10_11.moveToState(var11_5, 1, 0, 0, false);
                    }
                }
                var10_11 = var1_1;
                if (var7_10 == 0) break block24;
                if (var1_1 == null) break block25;
                var10_11 = var1_1;
                if (var1_1.firstOut != null) break block24;
            }
            var10_11 = FragmentTransition.ensureContainer((FragmentContainerTransition)var1_1, var2_2, var8_6);
            var10_11.firstOut = var11_5;
            var10_11.firstOutIsPop = var3_3;
            var10_11.firstOutTransaction = var0;
        }
        if (var4_4 != false) return;
        if (var6_9 == 0) return;
        if (var10_11 == null) return;
        if (var10_11.lastIn != var11_5) return;
        var10_11.lastIn = null;
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        int n = backStackRecord.mOps.size();
        for (int i = 0; i < n; ++i) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, false, bl);
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int n, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n2, int n3) {
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
        --n3;
        while (n3 >= n2) {
            Object object = arrayList.get(n3);
            if (object.interactsWith(n)) {
                boolean bl = arrayList2.get(n3);
                if (object.mSharedElementSourceNames != null) {
                    ArrayList<String> arrayList3;
                    ArrayList<String> arrayList4;
                    int n4 = object.mSharedElementSourceNames.size();
                    if (bl) {
                        arrayList3 = object.mSharedElementSourceNames;
                        arrayList4 = object.mSharedElementTargetNames;
                    } else {
                        arrayList4 = object.mSharedElementSourceNames;
                        arrayList3 = object.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < n4; ++i) {
                        object = arrayList4.get(i);
                        String string = arrayList3.get(i);
                        String string2 = (String)arrayMap.remove(string);
                        if (string2 != null) {
                            arrayMap.put((String)object, string2);
                            continue;
                        }
                        arrayMap.put((String)object, string);
                    }
                }
            }
            --n3;
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        if (!backStackRecord.mManager.mContainer.onHasView()) {
            return;
        }
        for (int i = backStackRecord.mOps.size() - 1; i >= 0; --i) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, true, bl);
        }
    }

    private static void callSharedElementStartEnd(Fragment object, Fragment object2, boolean bl, ArrayMap<String, View> arrayMap, boolean bl2) {
        object = bl ? object2.getEnterTransitionCallback() : object.getEnterTransitionCallback();
        if (object != null) {
            object2 = new ArrayList();
            ArrayList<String> arrayList = new ArrayList<String>();
            int n = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < n; ++i) {
                arrayList.add((String)arrayMap.keyAt(i));
                object2.add(arrayMap.valueAt(i));
            }
            if (bl2) {
                object.onSharedElementStart(arrayList, (List<View>)object2, null);
                return;
            }
            object.onSharedElementEnd(arrayList, (List<View>)object2, null);
        }
    }

    private static boolean canHandleAll(FragmentTransitionImpl fragmentTransitionImpl, List<Object> list) {
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            if (fragmentTransitionImpl.canHandle(list.get(i))) continue;
            return false;
        }
        return true;
    }

    private static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl arrayList, ArrayMap<String, String> arrayMap, Object object, FragmentContainerTransition object2) {
        Fragment fragment = object2.lastIn;
        View view = fragment.getView();
        if (!arrayMap.isEmpty() && object != null && view != null) {
            ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
            arrayList.findNamedViews(arrayMap2, view);
            arrayList = object2.lastInTransaction;
            if (object2.lastInIsPop) {
                object = fragment.getExitTransitionCallback();
                arrayList = arrayList.mSharedElementSourceNames;
            } else {
                object = fragment.getEnterTransitionCallback();
                arrayList = arrayList.mSharedElementTargetNames;
            }
            if (arrayList != null) {
                arrayMap2.retainAll(arrayList);
                arrayMap2.retainAll(arrayMap.values());
            }
            if (object != null) {
                object.onMapSharedElements(arrayList, arrayMap2);
                for (int i = arrayList.size() - 1; i >= 0; --i) {
                    object2 = arrayList.get(i);
                    object = (View)arrayMap2.get(object2);
                    if (object == null) {
                        object = FragmentTransition.findKeyForValue(arrayMap, (String)object2);
                        if (object == null) continue;
                        arrayMap.remove(object);
                        continue;
                    }
                    if (object2.equals(ViewCompat.getTransitionName((View)object)) || (object2 = FragmentTransition.findKeyForValue(arrayMap, (String)object2)) == null) continue;
                    arrayMap.put((String)object2, ViewCompat.getTransitionName((View)object));
                }
            } else {
                FragmentTransition.retainValues(arrayMap, arrayMap2);
            }
            return arrayMap2;
        }
        arrayMap.clear();
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl arrayList, ArrayMap<String, String> arrayMap, Object object, FragmentContainerTransition object2) {
        if (!arrayMap.isEmpty() && object != null) {
            object = object2.firstOut;
            ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
            arrayList.findNamedViews(arrayMap2, object.getView());
            arrayList = object2.firstOutTransaction;
            if (object2.firstOutIsPop) {
                object = object.getEnterTransitionCallback();
                arrayList = arrayList.mSharedElementTargetNames;
            } else {
                object = object.getExitTransitionCallback();
                arrayList = arrayList.mSharedElementSourceNames;
            }
            arrayMap2.retainAll(arrayList);
            if (object != null) {
                object.onMapSharedElements(arrayList, arrayMap2);
                for (int i = arrayList.size() - 1; i >= 0; --i) {
                    object2 = arrayList.get(i);
                    object = (View)arrayMap2.get(object2);
                    if (object == null) {
                        arrayMap.remove(object2);
                        continue;
                    }
                    if (object2.equals(ViewCompat.getTransitionName((View)object))) continue;
                    object2 = (String)arrayMap.remove(object2);
                    arrayMap.put(ViewCompat.getTransitionName((View)object), (String)object2);
                }
            } else {
                arrayMap.retainAll(arrayMap2.keySet());
            }
            return arrayMap2;
        }
        arrayMap.clear();
        return null;
    }

    private static FragmentTransitionImpl chooseImpl(Fragment object, Fragment fragment) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (object != null) {
            Object object2 = object.getExitTransition();
            if (object2 != null) {
                arrayList.add(object2);
            }
            if ((object2 = object.getReturnTransition()) != null) {
                arrayList.add(object2);
            }
            if ((object = object.getSharedElementReturnTransition()) != null) {
                arrayList.add(object);
            }
        }
        if (fragment != null) {
            object = fragment.getEnterTransition();
            if (object != null) {
                arrayList.add(object);
            }
            if ((object = fragment.getReenterTransition()) != null) {
                arrayList.add(object);
            }
            if ((object = fragment.getSharedElementEnterTransition()) != null) {
                arrayList.add(object);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (PLATFORM_IMPL != null && FragmentTransition.canHandleAll(PLATFORM_IMPL, arrayList)) {
            return PLATFORM_IMPL;
        }
        if (SUPPORT_IMPL != null && FragmentTransition.canHandleAll(SUPPORT_IMPL, arrayList)) {
            return SUPPORT_IMPL;
        }
        if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl fragmentTransitionImpl, Object object, Fragment object2, ArrayList<View> arrayList, View view) {
        if (object != null) {
            ArrayList<View> arrayList2 = new ArrayList<View>();
            if ((object2 = object2.getView()) != null) {
                fragmentTransitionImpl.captureTransitioningViews(arrayList2, (View)object2);
            }
            if (arrayList != null) {
                arrayList2.removeAll(arrayList);
            }
            object2 = arrayList2;
            if (!arrayList2.isEmpty()) {
                arrayList2.add(view);
                fragmentTransitionImpl.addTargets(object, arrayList2);
                return arrayList2;
            }
        } else {
            object2 = null;
        }
        return object2;
    }

    private static Object configureSharedElementsOrdered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final View view, final ArrayMap<String, String> object, final FragmentContainerTransition fragmentContainerTransition, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object object2, Object object3) {
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            if (fragment2 == null) {
                return null;
            }
            final boolean bl = fragmentContainerTransition.lastInIsPop;
            Object object4 = object.isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, bl);
            final ArrayMap<String, String> arrayMap = object;
            ArrayMap<String, View> arrayMap2 = FragmentTransition.captureOutSharedElements(fragmentTransitionImpl, arrayMap, object4, fragmentContainerTransition);
            if (object.isEmpty()) {
                object = null;
            } else {
                arrayList.addAll(arrayMap2.values());
                object = object4;
            }
            if (object2 == null && object3 == null && object == null) {
                return null;
            }
            FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, true);
            if (object != null) {
                object4 = new Rect();
                fragmentTransitionImpl.setSharedElementTargets(object, view, arrayList);
                FragmentTransition.setOutEpicenter(fragmentTransitionImpl, object, object3, arrayMap2, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                object3 = object4;
                if (object2 != null) {
                    fragmentTransitionImpl.setEpicenter(object2, (Rect)object4);
                    object3 = object4;
                }
            } else {
                object3 = null;
            }
            OneShotPreDrawListener.add((View)viewGroup, new Runnable((Rect)object3){
                final /* synthetic */ Rect val$inEpicenter;
                {
                    this.val$inEpicenter = rect;
                }

                @Override
                public void run() {
                    ArrayMap arrayMap2 = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, arrayMap, object, fragmentContainerTransition);
                    if (arrayMap2 != null) {
                        arrayList2.addAll(arrayMap2.values());
                        arrayList2.add(view);
                    }
                    FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, false);
                    if (object != null) {
                        fragmentTransitionImpl.swapSharedElementTargets(object, arrayList, arrayList2);
                        arrayMap2 = FragmentTransition.getInEpicenterView(arrayMap2, fragmentContainerTransition, object2, bl);
                        if (arrayMap2 != null) {
                            fragmentTransitionImpl.getBoundsOnScreen((View)arrayMap2, this.val$inEpicenter);
                        }
                    }
                }
            });
            return object;
        }
        return null;
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap<String, String> object, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> view2, ArrayList<View> arrayList, Object object2, Object object3) {
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment != null) {
            if (fragment2 == null) {
                return null;
            }
            final boolean bl = fragmentContainerTransition.lastInIsPop;
            Object object4 = object.isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, bl);
            ArrayMap<String, View> arrayMap = FragmentTransition.captureOutSharedElements(fragmentTransitionImpl, object, object4, fragmentContainerTransition);
            final ArrayMap<String, View> arrayMap2 = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, object, object4, fragmentContainerTransition);
            if (object.isEmpty()) {
                if (arrayMap != null) {
                    arrayMap.clear();
                }
                if (arrayMap2 != null) {
                    arrayMap2.clear();
                }
                object = null;
            } else {
                FragmentTransition.addSharedElementsWithMatchingNames(view2, arrayMap, object.keySet());
                FragmentTransition.addSharedElementsWithMatchingNames(arrayList, arrayMap2, object.values());
                object = object4;
            }
            if (object2 == null && object3 == null && object == null) {
                return null;
            }
            FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap, true);
            if (object != null) {
                arrayList.add(view);
                fragmentTransitionImpl.setSharedElementTargets(object, view, (ArrayList<View>)view2);
                FragmentTransition.setOutEpicenter(fragmentTransitionImpl, object, object3, arrayMap, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                view = new Rect();
                fragmentContainerTransition = FragmentTransition.getInEpicenterView(arrayMap2, fragmentContainerTransition, object2, bl);
                if (fragmentContainerTransition != null) {
                    fragmentTransitionImpl.setEpicenter(object2, (Rect)view);
                }
                view2 = view;
            } else {
                view2 = view = null;
                fragmentContainerTransition = view;
            }
            OneShotPreDrawListener.add((View)viewGroup, new Runnable((View)fragmentContainerTransition, fragmentTransitionImpl, (Rect)view2){
                final /* synthetic */ Rect val$epicenter;
                final /* synthetic */ View val$epicenterView;
                final /* synthetic */ FragmentTransitionImpl val$impl;
                {
                    this.val$epicenterView = view;
                    this.val$impl = fragmentTransitionImpl;
                    this.val$epicenter = rect;
                }

                @Override
                public void run() {
                    FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, false);
                    if (this.val$epicenterView != null) {
                        this.val$impl.getBoundsOnScreen(this.val$epicenterView, this.val$epicenter);
                    }
                }
            });
            return object;
        }
        return null;
    }

    private static void configureTransitionsOrdered(FragmentManagerImpl fragmentManagerImpl, int n, FragmentContainerTransition object, View view, ArrayMap<String, String> arrayMap) {
        fragmentManagerImpl = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup)fragmentManagerImpl.mContainer.onFindViewById(n) : null;
        if (fragmentManagerImpl == null) {
            return;
        }
        Object object2 = object.firstOut;
        Fragment fragment = object.lastIn;
        FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.chooseImpl((Fragment)object2, fragment);
        if (fragmentTransitionImpl == null) {
            return;
        }
        boolean bl = object.lastInIsPop;
        boolean bl2 = object.firstOutIsPop;
        Object object3 = FragmentTransition.getEnterTransition(fragmentTransitionImpl, fragment, bl);
        Object object4 = FragmentTransition.getExitTransition(fragmentTransitionImpl, (Fragment)object2, bl2);
        ArrayList<Object> arrayList = new ArrayList<View>();
        ArrayList<View> arrayList2 = new ArrayList<View>();
        Object object5 = FragmentTransition.configureSharedElementsOrdered(fragmentTransitionImpl, (ViewGroup)fragmentManagerImpl, view, arrayMap, (FragmentContainerTransition)object, arrayList, arrayList2, object3, object4);
        if (object3 == null && object5 == null && object4 == null) {
            return;
        }
        if ((object2 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object4, (Fragment)object2, arrayList, view)) == null || object2.isEmpty()) {
            object4 = null;
        }
        fragmentTransitionImpl.addTarget(object3, view);
        object = FragmentTransition.mergeTransitions(fragmentTransitionImpl, object3, object4, object5, fragment, object.lastInIsPop);
        if (object != null) {
            arrayList = new ArrayList();
            fragmentTransitionImpl.scheduleRemoveTargets(object, object3, arrayList, object4, (ArrayList<View>)object2, object5, arrayList2);
            FragmentTransition.scheduleTargetChange(fragmentTransitionImpl, (ViewGroup)fragmentManagerImpl, fragment, view, arrayList2, object3, arrayList, object4, (ArrayList<View>)object2);
            fragmentTransitionImpl.setNameOverridesOrdered((View)fragmentManagerImpl, arrayList2, arrayMap);
            fragmentTransitionImpl.beginDelayedTransition((ViewGroup)fragmentManagerImpl, object);
            fragmentTransitionImpl.scheduleNameReset((ViewGroup)fragmentManagerImpl, arrayList2, arrayMap);
        }
    }

    private static void configureTransitionsReordered(FragmentManagerImpl fragmentManagerImpl, int n, FragmentContainerTransition object, View object2, ArrayMap<String, String> arrayMap) {
        fragmentManagerImpl = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup)fragmentManagerImpl.mContainer.onFindViewById(n) : null;
        if (fragmentManagerImpl == null) {
            return;
        }
        Object object3 = object.firstOut;
        Object object4 = object.lastIn;
        FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.chooseImpl((Fragment)object3, (Fragment)object4);
        if (fragmentTransitionImpl == null) {
            return;
        }
        boolean bl = object.lastInIsPop;
        boolean bl2 = object.firstOutIsPop;
        ArrayList<View> arrayList = new ArrayList<View>();
        ArrayList<View> arrayList2 = new ArrayList<View>();
        Object object5 = FragmentTransition.getEnterTransition(fragmentTransitionImpl, (Fragment)object4, bl);
        ArrayList<View> arrayList3 = FragmentTransition.getExitTransition(fragmentTransitionImpl, (Fragment)object3, bl2);
        Object object6 = FragmentTransition.configureSharedElementsReordered(fragmentTransitionImpl, (ViewGroup)fragmentManagerImpl, object2, arrayMap, (FragmentContainerTransition)object, arrayList2, arrayList, object5, arrayList3);
        if (object5 == null && object6 == null && arrayList3 == null) {
            return;
        }
        object = arrayList3;
        arrayList3 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object, (Fragment)object3, arrayList2, object2);
        object2 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object5, (Fragment)object4, arrayList, object2);
        FragmentTransition.setViewVisibility((ArrayList<View>)object2, 4);
        object4 = FragmentTransition.mergeTransitions(fragmentTransitionImpl, object5, object, object6, (Fragment)object4, bl);
        if (object4 != null) {
            FragmentTransition.replaceHide(fragmentTransitionImpl, object, (Fragment)object3, arrayList3);
            object3 = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList);
            fragmentTransitionImpl.scheduleRemoveTargets(object4, object5, (ArrayList<View>)object2, object, arrayList3, object6, arrayList);
            fragmentTransitionImpl.beginDelayedTransition((ViewGroup)fragmentManagerImpl, object4);
            fragmentTransitionImpl.setNameOverridesReordered((View)fragmentManagerImpl, arrayList2, arrayList, (ArrayList<String>)object3, arrayMap);
            FragmentTransition.setViewVisibility((ArrayList<View>)object2, 0);
            fragmentTransitionImpl.swapSharedElementTargets(object6, arrayList2, arrayList);
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int n) {
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        if (fragmentContainerTransition == null) {
            fragmentContainerTransition2 = new FragmentContainerTransition();
            sparseArray.put(n, (Object)fragmentContainerTransition2);
        }
        return fragmentContainerTransition2;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String string) {
        int n = arrayMap.size();
        for (int i = 0; i < n; ++i) {
            if (!string.equals(arrayMap.valueAt(i))) continue;
            return (String)arrayMap.keyAt(i);
        }
        return null;
    }

    private static Object getEnterTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        object = bl ? object.getReenterTransition() : object.getEnterTransition();
        return fragmentTransitionImpl.cloneTransition(object);
    }

    private static Object getExitTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        object = bl ? object.getReturnTransition() : object.getExitTransition();
        return fragmentTransitionImpl.cloneTransition(object);
    }

    private static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition object, Object object2, boolean bl) {
        object = object.lastInTransaction;
        if (object2 != null && arrayMap != null && object.mSharedElementSourceNames != null && !object.mSharedElementSourceNames.isEmpty()) {
            object = bl ? object.mSharedElementSourceNames.get(0) : object.mSharedElementTargetNames.get(0);
            return (View)arrayMap.get(object);
        }
        return null;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, Fragment fragment, boolean bl) {
        if (object != null && fragment != null) {
            object = bl ? fragment.getSharedElementReturnTransition() : object.getSharedElementEnterTransition();
            return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(object));
        }
        return null;
    }

    private static Object mergeTransitions(FragmentTransitionImpl fragmentTransitionImpl, Object object, Object object2, Object object3, Fragment fragment, boolean bl) {
        bl = object != null && object2 != null && fragment != null ? (bl ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap()) : true;
        if (bl) {
            return fragmentTransitionImpl.mergeTransitionsTogether(object2, object, object3);
        }
        return fragmentTransitionImpl.mergeTransitionsInSequence(object2, object, object3);
    }

    private static void replaceHide(FragmentTransitionImpl fragmentTransitionImpl, Object object, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && object != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            fragmentTransitionImpl.scheduleHideFragmentView(object, fragment.getView(), arrayList);
            OneShotPreDrawListener.add((View)fragment.mContainer, new Runnable(){

                @Override
                public void run() {
                    FragmentTransition.setViewVisibility(arrayList, 4);
                }
            });
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl)Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            if (arrayMap2.containsKey((String)arrayMap.valueAt(i))) continue;
            arrayMap.removeAt(i);
        }
    }

    private static void scheduleTargetChange(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Object object, final ArrayList<View> arrayList2, final Object object2, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add((View)viewGroup, new Runnable(){

            @Override
            public void run() {
                ArrayList arrayList4;
                if (object != null) {
                    fragmentTransitionImpl.removeTarget(object, view);
                    arrayList4 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object, fragment, arrayList, view);
                    arrayList2.addAll(arrayList4);
                }
                if (arrayList3 != null) {
                    if (object2 != null) {
                        arrayList4 = new ArrayList();
                        arrayList4.add(view);
                        fragmentTransitionImpl.replaceTargets(object2, arrayList3, arrayList4);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static void setOutEpicenter(FragmentTransitionImpl fragmentTransitionImpl, Object object, Object object2, ArrayMap<String, View> view, boolean bl, BackStackRecord object3) {
        if (object3.mSharedElementSourceNames != null && !object3.mSharedElementSourceNames.isEmpty()) {
            object3 = bl ? object3.mSharedElementTargetNames.get(0) : object3.mSharedElementSourceNames.get(0);
            view = (View)view.get(object3);
            fragmentTransitionImpl.setEpicenter(object, view);
            if (object2 != null) {
                fragmentTransitionImpl.setEpicenter(object2, view);
            }
        }
    }

    private static void setViewVisibility(ArrayList<View> arrayList, int n) {
        if (arrayList == null) {
            return;
        }
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            arrayList.get(i).setVisibility(n);
        }
    }

    static void startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, boolean bl) {
        BackStackRecord backStackRecord;
        int n3;
        if (fragmentManagerImpl.mCurState < 1) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        for (n3 = n; n3 < n2; ++n3) {
            backStackRecord = arrayList.get(n3);
            if (arrayList2.get(n3).booleanValue()) {
                FragmentTransition.calculatePopFragments(backStackRecord, (SparseArray<FragmentContainerTransition>)sparseArray, bl);
                continue;
            }
            FragmentTransition.calculateFragments(backStackRecord, (SparseArray<FragmentContainerTransition>)sparseArray, bl);
        }
        if (sparseArray.size() != 0) {
            backStackRecord = new View(fragmentManagerImpl.mHost.getContext());
            int n4 = sparseArray.size();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = sparseArray.keyAt(n3);
                ArrayMap<String, String> arrayMap = FragmentTransition.calculateNameOverrides(n5, arrayList, arrayList2, n, n2);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition)sparseArray.valueAt(n3);
                if (bl) {
                    FragmentTransition.configureTransitionsReordered(fragmentManagerImpl, n5, fragmentContainerTransition, (View)backStackRecord, arrayMap);
                    continue;
                }
                FragmentTransition.configureTransitionsOrdered(fragmentManagerImpl, n5, fragmentContainerTransition, (View)backStackRecord, arrayMap);
            }
        }
    }

    static boolean supportsTransition() {
        if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
            return false;
        }
        return true;
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

}
