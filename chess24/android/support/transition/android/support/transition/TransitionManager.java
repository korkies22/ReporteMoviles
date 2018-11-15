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
import android.support.annotation.Nullable;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TransitionManager {
    private static final String LOG_TAG = "TransitionManager";
    private static Transition sDefaultTransition = new AutoTransition();
    private static ArrayList<ViewGroup> sPendingTransitions;
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions;
    private ArrayMap<Scene, ArrayMap<Scene, Transition>> mScenePairTransitions = new ArrayMap();
    private ArrayMap<Scene, Transition> mSceneTransitions = new ArrayMap();

    static {
        sRunningTransitions = new ThreadLocal();
        sPendingTransitions = new ArrayList();
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        TransitionManager.beginDelayedTransition(viewGroup, null);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        if (!sPendingTransitions.contains((Object)viewGroup) && ViewCompat.isLaidOut((View)viewGroup)) {
            sPendingTransitions.add(viewGroup);
            Transition transition2 = transition;
            if (transition == null) {
                transition2 = sDefaultTransition;
            }
            transition = transition2.clone();
            TransitionManager.sceneChangeSetup(viewGroup, transition);
            Scene.setCurrentScene((View)viewGroup, null);
            TransitionManager.sceneChangeRunTransition(viewGroup, transition);
        }
    }

    private static void changeScene(Scene scene, Transition transition) {
        ViewGroup viewGroup = scene.getSceneRoot();
        if (!sPendingTransitions.contains((Object)viewGroup)) {
            if (transition == null) {
                scene.enter();
                return;
            }
            sPendingTransitions.add(viewGroup);
            transition = transition.clone();
            transition.setSceneRoot(viewGroup);
            Scene scene2 = Scene.getCurrentScene((View)viewGroup);
            if (scene2 != null && scene2.isCreatedFromLayoutResource()) {
                transition.setCanRemoveViews(true);
            }
            TransitionManager.sceneChangeSetup(viewGroup, transition);
            scene.enter();
            TransitionManager.sceneChangeRunTransition(viewGroup, transition);
        }
    }

    public static void endTransitions(ViewGroup viewGroup) {
        sPendingTransitions.remove((Object)viewGroup);
        ArrayList arrayList = (ArrayList)TransitionManager.getRunningTransitions().get((Object)viewGroup);
        if (arrayList != null && !arrayList.isEmpty()) {
            arrayList = new ArrayList(arrayList);
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                ((Transition)arrayList.get(i)).forceToEnd(viewGroup);
            }
        }
    }

    static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
        WeakReference<ArrayMap<Object, ArrayList<Transition>>> weakReference;
        block3 : {
            block2 : {
                WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference2 = sRunningTransitions.get();
                if (weakReference2 == null) break block2;
                weakReference = weakReference2;
                if (weakReference2.get() != null) break block3;
            }
            weakReference = new WeakReference(new ArrayMap());
            sRunningTransitions.set(weakReference);
        }
        return (ArrayMap)weakReference.get();
    }

    private Transition getTransition(Scene object) {
        ArrayMap arrayMap;
        Object object2 = object.getSceneRoot();
        if (object2 != null && (object2 = Scene.getCurrentScene((View)object2)) != null && (arrayMap = (ArrayMap)this.mScenePairTransitions.get(object)) != null && (object2 = (Transition)arrayMap.get(object2)) != null) {
            return object2;
        }
        if ((object = (Transition)this.mSceneTransitions.get(object)) != null) {
            return object;
        }
        return sDefaultTransition;
    }

    public static void go(@NonNull Scene scene) {
        TransitionManager.changeScene(scene, sDefaultTransition);
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        TransitionManager.changeScene(scene, transition);
    }

    private static void sceneChangeRunTransition(ViewGroup viewGroup, Transition object) {
        if (object != null && viewGroup != null) {
            object = new MultiListener((Transition)object, viewGroup);
            viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
            viewGroup.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        }
    }

    private static void sceneChangeSetup(ViewGroup object, Transition transition) {
        Object object2 = (ArrayList)TransitionManager.getRunningTransitions().get(object);
        if (object2 != null && object2.size() > 0) {
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ((Transition)object2.next()).pause((View)object);
            }
        }
        if (transition != null) {
            transition.captureValues((ViewGroup)object, true);
        }
        if ((object = Scene.getCurrentScene((View)object)) != null) {
            object.exit();
        }
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        ArrayMap<Scene, Transition> arrayMap;
        ArrayMap<Scene, Transition> arrayMap2 = arrayMap = (ArrayMap<Scene, Transition>)this.mScenePairTransitions.get(scene2);
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap<Scene, Transition>();
            this.mScenePairTransitions.put(scene2, arrayMap2);
        }
        arrayMap2.put(scene, transition);
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.mSceneTransitions.put(scene, transition);
    }

    public void transitionTo(@NonNull Scene scene) {
        TransitionManager.changeScene(scene, this.getTransition(scene));
    }

    private static class MultiListener
    implements ViewTreeObserver.OnPreDrawListener,
    View.OnAttachStateChangeListener {
        ViewGroup mSceneRoot;
        Transition mTransition;

        MultiListener(Transition transition, ViewGroup viewGroup) {
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

}
