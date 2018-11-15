/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ClassesInfoCache;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

static class ClassesInfoCache.CallbackInfo {
    final Map<Lifecycle.Event, List<ClassesInfoCache.MethodReference>> mEventToHandlers;
    final Map<ClassesInfoCache.MethodReference, Lifecycle.Event> mHandlerToEvent;

    ClassesInfoCache.CallbackInfo(Map<ClassesInfoCache.MethodReference, Lifecycle.Event> arrayList) {
        this.mHandlerToEvent = arrayList;
        this.mEventToHandlers = new HashMap<Lifecycle.Event, List<ClassesInfoCache.MethodReference>>();
        for (Map.Entry<ClassesInfoCache.MethodReference, Lifecycle.Event> entry : arrayList.entrySet()) {
            Lifecycle.Event event = entry.getValue();
            List<ClassesInfoCache.MethodReference> list = this.mEventToHandlers.get((Object)event);
            arrayList = list;
            if (list == null) {
                arrayList = new ArrayList<ClassesInfoCache.MethodReference>();
                this.mEventToHandlers.put(event, arrayList);
            }
            arrayList.add(entry.getKey());
        }
    }

    private static void invokeMethodsForEvent(List<ClassesInfoCache.MethodReference> list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; --i) {
                list.get(i).invokeCallback(lifecycleOwner, event, object);
            }
        }
    }

    void invokeCallbacks(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)event), lifecycleOwner, event, object);
        ClassesInfoCache.CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)Lifecycle.Event.ON_ANY), lifecycleOwner, event, object);
    }
}
