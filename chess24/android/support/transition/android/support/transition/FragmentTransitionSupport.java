/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.FragmentTransitionImpl;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class FragmentTransitionSupport
extends FragmentTransitionImpl {
    private static boolean hasSimpleTarget(Transition transition) {
        if (FragmentTransitionSupport.isNullOrEmpty(transition.getTargetIds()) && FragmentTransitionSupport.isNullOrEmpty(transition.getTargetNames()) && FragmentTransitionSupport.isNullOrEmpty(transition.getTargetTypes())) {
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
                    this.addTargets(object.getTransitionAt(n), arrayList);
                }
                break block4;
            }
            if (FragmentTransitionSupport.hasSimpleTarget((Transition)object) || !FragmentTransitionSupport.isNullOrEmpty(object.getTargets())) break block4;
            int n3 = arrayList.size();
            for (n = n2; n < n3; ++n) {
                object.addTarget(arrayList.get(n));
            }
        }
    }

    @Override
    public void beginDelayedTransition(ViewGroup viewGroup, Object object) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition)object);
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
            List<View> list;
            int n2;
            block3 : {
                object = (Transition)object;
                boolean bl = object instanceof TransitionSet;
                if (!bl) break block3;
                object = (TransitionSet)object;
                n = object.getTransitionCount();
                for (n2 = 0; n2 < n; ++n2) {
                    this.replaceTargets(object.getTransitionAt(n2), arrayList, arrayList2);
                }
                break block4;
            }
            if (FragmentTransitionSupport.hasSimpleTarget((Transition)object) || (list = object.getTargets()).size() != arrayList.size() || !list.containsAll(arrayList)) break block4;
            n2 = arrayList2 == null ? 0 : arrayList2.size();
            for (n = 0; n < n2; ++n) {
                object.addTarget(arrayList2.get(n));
            }
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                object.removeTarget(arrayList.get(n2));
            }
        }
    }

    @Override
    public void scheduleHideFragmentView(Object object, final View view, final ArrayList<View> arrayList) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((View)arrayList.get(i)).setVisibility(0);
                }
            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionStart(@NonNull Transition transition) {
            }
        });
    }

    @Override
    public void scheduleRemoveTargets(Object object, final Object object2, final ArrayList<View> arrayList, final Object object3, final ArrayList<View> arrayList2, final Object object4, final ArrayList<View> arrayList3) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {
            }

            @Override
            public void onTransitionStart(@NonNull Transition transition) {
                if (object2 != null) {
                    FragmentTransitionSupport.this.replaceTargets(object2, arrayList, null);
                }
                if (object3 != null) {
                    FragmentTransitionSupport.this.replaceTargets(object3, arrayList2, null);
                }
                if (object4 != null) {
                    FragmentTransitionSupport.this.replaceTargets(object4, arrayList3, null);
                }
            }
        });
    }

    @Override
    public void setEpicenter(Object object, final Rect rect) {
        if (object != null) {
            ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(){

                @Override
                public Rect onGetEpicenter(@NonNull Transition transition) {
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

                @Override
                public Rect onGetEpicenter(@NonNull Transition transition) {
                    return rect;
                }
            });
        }
    }

    @Override
    public void setSharedElementTargets(Object object, View view, ArrayList<View> arrayList) {
        object = (TransitionSet)object;
        List<View> list = object.getTargets();
        list.clear();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            FragmentTransitionSupport.bfsAddViewChildren(list, arrayList.get(i));
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
