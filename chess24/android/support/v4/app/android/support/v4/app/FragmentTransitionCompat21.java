/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.transition.Transition
 *  android.transition.Transition$EpicenterCallback
 *  android.transition.Transition$TransitionListener
 *  android.transition.TransitionManager
 *  android.transition.TransitionSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.app;

import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransitionImpl;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiresApi(value=21)
class FragmentTransitionCompat21
extends FragmentTransitionImpl {
    FragmentTransitionCompat21() {
    }

    private static boolean hasSimpleTarget(Transition transition) {
        if (FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetIds()) && FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetNames()) && FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetTypes())) {
            return false;
        }
        return true;
    }

    @Override
    public void addTarget(Object object, View view) {
        if (object != null) {
            ((Transition)object).addTarget(view);
        }
    }

    @Override
    public void addTargets(Object object, ArrayList<View> arrayList) {
        block4 : {
            int n;
            block3 : {
                if ((object = (Transition)object) == null) {
                    return;
                }
                boolean bl = object instanceof TransitionSet;
                int n2 = 0;
                if (!bl) break block3;
                object = (TransitionSet)object;
                n2 = object.getTransitionCount();
                for (n = 0; n < n2; ++n) {
                    this.addTargets((Object)object.getTransitionAt(n), arrayList);
                }
                break block4;
            }
            if (FragmentTransitionCompat21.hasSimpleTarget((Transition)object) || !FragmentTransitionCompat21.isNullOrEmpty(object.getTargets())) break block4;
            int n3 = arrayList.size();
            for (n = n2; n < n3; ++n) {
                object.addTarget(arrayList.get(n));
            }
        }
    }

    @Override
    public void beginDelayedTransition(ViewGroup viewGroup, Object object) {
        TransitionManager.beginDelayedTransition((ViewGroup)viewGroup, (Transition)((Transition)object));
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof Transition;
    }

    @Override
    public Object cloneTransition(Object object) {
        if (object != null) {
            return ((Transition)object).clone();
        }
        return null;
    }

    @Override
    public Object mergeTransitionsInSequence(Object object, Object object2, Object object3) {
        object = (Transition)object;
        object2 = (Transition)object2;
        object3 = (Transition)object3;
        if (object != null && object2 != null) {
            object = new TransitionSet().addTransition((Transition)object).addTransition((Transition)object2).setOrdering(1);
        } else if (object == null) {
            object = object2 != null ? object2 : null;
        }
        if (object3 != null) {
            object2 = new TransitionSet();
            if (object != null) {
                object2.addTransition((Transition)object);
            }
            object2.addTransition((Transition)object3);
            return object2;
        }
        return object;
    }

    @Override
    public Object mergeTransitionsTogether(Object object, Object object2, Object object3) {
        TransitionSet transitionSet = new TransitionSet();
        if (object != null) {
            transitionSet.addTransition((Transition)object);
        }
        if (object2 != null) {
            transitionSet.addTransition((Transition)object2);
        }
        if (object3 != null) {
            transitionSet.addTransition((Transition)object3);
        }
        return transitionSet;
    }

    @Override
    public void removeTarget(Object object, View view) {
        if (object != null) {
            ((Transition)object).removeTarget(view);
        }
    }

    @Override
    public void replaceTargets(Object object, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        block4 : {
            int n;
            int n2;
            List list;
            block3 : {
                object = (Transition)object;
                boolean bl = object instanceof TransitionSet;
                if (!bl) break block3;
                object = (TransitionSet)object;
                n2 = object.getTransitionCount();
                for (n = 0; n < n2; ++n) {
                    this.replaceTargets((Object)object.getTransitionAt(n), arrayList, arrayList2);
                }
                break block4;
            }
            if (FragmentTransitionCompat21.hasSimpleTarget((Transition)object) || (list = object.getTargets()) == null || list.size() != arrayList.size() || !list.containsAll(arrayList)) break block4;
            n = arrayList2 == null ? 0 : arrayList2.size();
            for (n2 = 0; n2 < n; ++n2) {
                object.addTarget(arrayList2.get(n2));
            }
            for (n = arrayList.size() - 1; n >= 0; --n) {
                object.removeTarget(arrayList.get(n));
            }
        }
    }

    @Override
    public void scheduleHideFragmentView(Object object, final View view, final ArrayList<View> arrayList) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener((Transition.TransitionListener)this);
                view.setVisibility(8);
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((View)arrayList.get(i)).setVisibility(0);
                }
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override
    public void scheduleRemoveTargets(Object object, final Object object2, final ArrayList<View> arrayList, final Object object3, final ArrayList<View> arrayList2, final Object object4, final ArrayList<View> arrayList3) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
                if (object2 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(object2, arrayList, null);
                }
                if (object3 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(object3, arrayList2, null);
                }
                if (object4 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(object4, arrayList3, null);
                }
            }
        });
    }

    @Override
    public void setEpicenter(Object object, final Rect rect) {
        if (object != null) {
            ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(){

                public Rect onGetEpicenter(Transition transition) {
                    if (rect != null && !rect.isEmpty()) {
                        return rect;
                    }
                    return null;
                }
            });
        }
    }

    @Override
    public void setEpicenter(Object object, View view) {
        if (view != null) {
            object = (Transition)object;
            final Rect rect = new Rect();
            this.getBoundsOnScreen(view, rect);
            object.setEpicenterCallback(new Transition.EpicenterCallback(){

                public Rect onGetEpicenter(Transition transition) {
                    return rect;
                }
            });
        }
    }

    @Override
    public void setSharedElementTargets(Object object, View view, ArrayList<View> arrayList) {
        object = (TransitionSet)object;
        List list = object.getTargets();
        list.clear();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            FragmentTransitionCompat21.bfsAddViewChildren(list, arrayList.get(i));
        }
        list.add(view);
        arrayList.add(view);
        this.addTargets(object, arrayList);
    }

    @Override
    public void swapSharedElementTargets(Object object, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if ((object = (TransitionSet)object) != null) {
            object.getTargets().clear();
            object.getTargets().addAll(arrayList2);
            this.replaceTargets(object, arrayList, arrayList2);
        }
    }

    @Override
    public Object wrapTransitionInSet(Object object) {
        if (object == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition)object);
        return transitionSet;
    }

}
