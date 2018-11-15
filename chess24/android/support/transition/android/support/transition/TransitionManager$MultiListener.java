/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionManager;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

private static class TransitionManager.MultiListener
implements ViewTreeObserver.OnPreDrawListener,
View.OnAttachStateChangeListener {
    ViewGroup mSceneRoot;
    Transition mTransition;

    TransitionManager.MultiListener(Transition transition, ViewGroup viewGroup) {
        this.mTransition = transition;
        this.mSceneRoot = viewGroup;
    }

    private void removeListeners() {
        this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
        this.mSceneRoot.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
    }

    public boolean onPreDraw() {
        Object object;
        this.removeListeners();
        if (!sPendingTransitions.remove((Object)this.mSceneRoot)) {
            return true;
        }
        final ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap = TransitionManager.getRunningTransitions();
        ArrayList<Transition> arrayList = (ArrayList<Transition>)arrayMap.get((Object)this.mSceneRoot);
        ArrayList arrayList2 = null;
        if (arrayList == null) {
            object = new ArrayList<Transition>();
            arrayMap.put(this.mSceneRoot, (ArrayList<Transition>)object);
        } else {
            object = arrayList;
            if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
                object = arrayList;
            }
        }
        object.add(this.mTransition);
        this.mTransition.addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                ((ArrayList)arrayMap.get((Object)MultiListener.this.mSceneRoot)).remove(transition);
            }
        });
        this.mTransition.captureValues(this.mSceneRoot, false);
        if (arrayList2 != null) {
            object = arrayList2.iterator();
            while (object.hasNext()) {
                ((Transition)object.next()).resume((View)this.mSceneRoot);
            }
        }
        this.mTransition.playTransition(this.mSceneRoot);
        return true;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View object) {
        this.removeListeners();
        sPendingTransitions.remove((Object)this.mSceneRoot);
        object = (ArrayList)TransitionManager.getRunningTransitions().get((Object)this.mSceneRoot);
        if (object != null && object.size() > 0) {
            object = object.iterator();
            while (object.hasNext()) {
                ((Transition)object.next()).resume((View)this.mSceneRoot);
            }
        }
        this.mTransition.clearValues(true);
    }

}
