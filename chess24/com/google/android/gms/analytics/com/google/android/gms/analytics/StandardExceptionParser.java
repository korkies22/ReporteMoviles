/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.ExceptionParser;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class StandardExceptionParser
implements ExceptionParser {
    private final TreeSet<String> zzabq = new TreeSet();

    public StandardExceptionParser(Context context, Collection<String> collection) {
        this.setIncludedPackages(context, collection);
    }

    protected StackTraceElement getBestStackTraceElement(Throwable arrstackTraceElement) {
        if ((arrstackTraceElement = arrstackTraceElement.getStackTrace()) != null && arrstackTraceElement.length != 0) {
            int n = arrstackTraceElement.length;
            for (int i = 0; i < n; ++i) {
                StackTraceElement stackTraceElement = arrstackTraceElement[i];
                String string = stackTraceElement.getClassName();
                Iterator<String> iterator = this.zzabq.iterator();
                while (iterator.hasNext()) {
                    if (!string.startsWith(iterator.next())) continue;
                    return stackTraceElement;
                }
            }
            return arrstackTraceElement[0];
        }
        return null;
    }

    protected Throwable getCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    @Override
    public String getDescription(String string, Throwable throwable) {
        return this.getDescription(this.getCause(throwable), this.getBestStackTraceElement(this.getCause(throwable)), string);
    }

    protected String getDescription(Throwable object, StackTraceElement stackTraceElement, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object.getClass().getSimpleName());
        if (stackTraceElement != null) {
            String[] arrstring = stackTraceElement.getClassName().split("\\.");
            String string2 = "unknown";
            object = string2;
            if (arrstring != null) {
                object = string2;
                if (arrstring.length > 0) {
                    object = arrstring[arrstring.length - 1];
                }
            }
            stringBuilder.append(String.format(" (@%s:%s:%s)", object, stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
        }
        if (string != null) {
            stringBuilder.append(String.format(" {%s}", string));
        }
        return stringBuilder.toString();
    }

    public void setIncludedPackages(Context object, Collection<String> object2) {
        this.zzabq.clear();
        HashSet<String> hashSet = new HashSet<String>();
        if (object2 != null) {
            hashSet.addAll((Collection<String>)object2);
        }
        if (object != null) {
            hashSet.add(object.getApplicationContext().getPackageName());
        }
        object = hashSet.iterator();
        while (object.hasNext()) {
            object2 = (String)object.next();
            boolean bl = true;
            for (String string : this.zzabq) {
                if (!object2.startsWith(string)) {
                    if (!string.startsWith((String)object2)) break;
                    this.zzabq.remove(string);
                    break;
                }
                bl = false;
            }
            if (!bl) continue;
            this.zzabq.add((String)object2);
        }
    }
}
