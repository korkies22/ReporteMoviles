/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ClassesInfoCache {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static ClassesInfoCache sInstance = new ClassesInfoCache();
    private final Map<Class, CallbackInfo> mCallbackMap = new HashMap<Class, CallbackInfo>();
    private final Map<Class, Boolean> mHasLifecycleMethods = new HashMap<Class, Boolean>();

    ClassesInfoCache() {
    }

    private CallbackInfo createInfo(Class class_, @Nullable Method[] arrmethod) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private Method[] getDeclaredMethods(Class arrmethod) {
        try {
            arrmethod = arrmethod.getDeclaredMethods();
            return arrmethod;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", noClassDefFoundError);
        }
    }

    private void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> object, MethodReference object2, Lifecycle.Event event, Class class_) {
        Lifecycle.Event event2 = object.get(object2);
        if (event2 != null && event != event2) {
            object = object2.mMethod;
            object2 = new StringBuilder();
            object2.append("Method ");
            object2.append(object.getName());
            object2.append(" in ");
            object2.append(class_.getName());
            object2.append(" already declared with different @OnLifecycleEvent value: previous");
            object2.append(" value ");
            object2.append((Object)event2);
            object2.append(", new value ");
            object2.append((Object)event);
            throw new IllegalArgumentException(object2.toString());
        }
        if (event2 == null) {
            object.put((MethodReference)object2, (Lifecycle.Event)event);
        }
    }

    CallbackInfo getInfo(Class class_) {
        CallbackInfo callbackInfo = this.mCallbackMap.get(class_);
        if (callbackInfo != null) {
            return callbackInfo;
        }
        return this.createInfo(class_, null);
    }

    boolean hasLifecycleMethods(Class class_) {
        if (this.mHasLifecycleMethods.containsKey(class_)) {
            return this.mHasLifecycleMethods.get(class_);
        }
        Method[] arrmethod = this.getDeclaredMethods(class_);
        int n = arrmethod.length;
        for (int i = 0; i < n; ++i) {
            if (arrmethod[i].getAnnotation(OnLifecycleEvent.class) == null) continue;
            this.createInfo(class_, arrmethod);
            return true;
        }
        this.mHasLifecycleMethods.put(class_, false);
        return false;
    }

    static class CallbackInfo {
        final Map<Lifecycle.Event, List<MethodReference>> mEventToHandlers;
        final Map<MethodReference, Lifecycle.Event> mHandlerToEvent;

        CallbackInfo(Map<MethodReference, Lifecycle.Event> arrayList) {
            this.mHandlerToEvent = arrayList;
            this.mEventToHandlers = new HashMap<Lifecycle.Event, List<MethodReference>>();
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : arrayList.entrySet()) {
                Lifecycle.Event event = entry.getValue();
                List<MethodReference> list = this.mEventToHandlers.get((Object)event);
                arrayList = list;
                if (list == null) {
                    arrayList = new ArrayList<MethodReference>();
                    this.mEventToHandlers.put(event, arrayList);
                }
                arrayList.add(entry.getKey());
            }
        }

        private static void invokeMethodsForEvent(List<MethodReference> list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
            if (list != null) {
                for (int i = list.size() - 1; i >= 0; --i) {
                    list.get(i).invokeCallback(lifecycleOwner, event, object);
                }
            }
        }

        void invokeCallbacks(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
            CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)event), lifecycleOwner, event, object);
            CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)Lifecycle.Event.ON_ANY), lifecycleOwner, event, object);
        }
    }

    static class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int n, Method method) {
            this.mCallType = n;
            this.mMethod = method;
            this.mMethod.setAccessible(true);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null) {
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                object = (MethodReference)object;
                if (this.mCallType == object.mCallType && this.mMethod.getName().equals(object.mMethod.getName())) {
                    return true;
                }
                return false;
            }
            return false;
        }

        public int hashCode() {
            return 31 * this.mCallType + this.mMethod.getName().hashCode();
        }

        /*
         * Exception decompiling
         */
        void invokeCallback(LifecycleOwner var1_1, Lifecycle.Event var2_4, Object var3_5) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:767)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:864)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
            // org.benf.cfr.reader.Main.doClass(Main.java:54)
            // org.benf.cfr.reader.Main.main(Main.java:247)
            throw new IllegalStateException("Decompilation failed");
        }
    }

}
