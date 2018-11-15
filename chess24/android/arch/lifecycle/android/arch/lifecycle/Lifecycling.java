/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ClassesInfoCache;
import android.arch.lifecycle.CompositeGeneratedAdaptersObserver;
import android.arch.lifecycle.FullLifecycleObserver;
import android.arch.lifecycle.FullLifecycleObserverAdapter;
import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ReflectiveGenericLifecycleObserver;
import android.arch.lifecycle.SingleGeneratedAdapterObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class Lifecycling {
    private static final int GENERATED_CALLBACK = 2;
    private static final int REFLECTIVE_CALLBACK = 1;
    private static Map<Class, Integer> sCallbackCache = new HashMap<Class, Integer>();
    private static Map<Class, List<Constructor<? extends GeneratedAdapter>>> sClassToAdapters = new HashMap<Class, List<Constructor<? extends GeneratedAdapter>>>();

    private static GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> object, Object object2) {
        try {
            object = object.newInstance(object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException(instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private static Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> genericDeclaration) {
        try {
            Object object = genericDeclaration.getPackage();
            String string = genericDeclaration.getCanonicalName();
            object = object != null ? object.getName() : "";
            if (!object.isEmpty()) {
                string = string.substring(object.length() + 1);
            }
            string = Lifecycling.getAdapterName(string);
            if (object.isEmpty()) {
                object = string;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(".");
                stringBuilder.append(string);
                object = stringBuilder.toString();
            }
            genericDeclaration = Class.forName((String)object).getDeclaredConstructor(new Class[]{genericDeclaration});
            if (genericDeclaration.isAccessible()) return genericDeclaration;
            genericDeclaration.setAccessible(true);
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static String getAdapterName(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.replace(".", "_"));
        stringBuilder.append("_LifecycleAdapter");
        return stringBuilder.toString();
    }

    @NonNull
    static GenericLifecycleObserver getCallback(Object object) {
        if (object instanceof FullLifecycleObserver) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver)object);
        }
        if (object instanceof GenericLifecycleObserver) {
            return (GenericLifecycleObserver)object;
        }
        Class<?> class_ = object.getClass();
        if (Lifecycling.getObserverConstructorType(class_) == 2) {
            class_ = sClassToAdapters.get(class_);
            int n = class_.size();
            if (n == 1) {
                return new SingleGeneratedAdapterObserver(Lifecycling.createGeneratedAdapter((Constructor)class_.get(0), object));
            }
            GeneratedAdapter[] arrgeneratedAdapter = new GeneratedAdapter[class_.size()];
            for (int i = 0; i < class_.size(); ++i) {
                arrgeneratedAdapter[i] = Lifecycling.createGeneratedAdapter((Constructor)class_.get(i), object);
            }
            return new CompositeGeneratedAdaptersObserver(arrgeneratedAdapter);
        }
        return new ReflectiveGenericLifecycleObserver(object);
    }

    private static int getObserverConstructorType(Class<?> class_) {
        if (sCallbackCache.containsKey(class_)) {
            return sCallbackCache.get(class_);
        }
        int n = Lifecycling.resolveObserverCallbackType(class_);
        sCallbackCache.put(class_, n);
        return n;
    }

    private static boolean isLifecycleParent(Class<?> class_) {
        if (class_ != null && LifecycleObserver.class.isAssignableFrom(class_)) {
            return true;
        }
        return false;
    }

    private static int resolveObserverCallbackType(Class<?> class_) {
        if (class_.getCanonicalName() == null) {
            return 1;
        }
        Object object = Lifecycling.generatedConstructor(class_);
        if (object != null) {
            sClassToAdapters.put(class_, Collections.singletonList(object));
            return 2;
        }
        if (ClassesInfoCache.sInstance.hasLifecycleMethods(class_)) {
            return 1;
        }
        ArrayList arrayList = class_.getSuperclass();
        object = null;
        if (Lifecycling.isLifecycleParent(arrayList)) {
            if (Lifecycling.getObserverConstructorType(arrayList) == 1) {
                return 1;
            }
            object = new ArrayList(sClassToAdapters.get(arrayList));
        }
        for (Class<?> class_2 : class_.getInterfaces()) {
            if (!Lifecycling.isLifecycleParent(class_2)) continue;
            if (Lifecycling.getObserverConstructorType(class_2) == 1) {
                return 1;
            }
            arrayList = object;
            if (object == null) {
                arrayList = new ArrayList();
            }
            arrayList.addAll(sClassToAdapters.get(class_2));
            object = arrayList;
        }
        if (object != null) {
            sClassToAdapters.put(class_, (List<Constructor<? extends GeneratedAdapter>>)object);
            return 2;
        }
        return 1;
    }
}
