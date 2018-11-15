/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatViewInflater;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

private static class AppCompatViewInflater.DeclaredOnClickListener
implements View.OnClickListener {
    private final View mHostView;
    private final String mMethodName;
    private Context mResolvedContext;
    private Method mResolvedMethod;

    public AppCompatViewInflater.DeclaredOnClickListener(@NonNull View view, @NonNull String string) {
        this.mHostView = view;
        this.mMethodName = string;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @NonNull
    private void resolveMethod(@Nullable Context object, @NonNull String object2) {
        do {
            if (object != null) {
                if (!object.isRestricted() && (object2 = object.getClass().getMethod(this.mMethodName, View.class)) != null) {
                    this.mResolvedMethod = object2;
                    this.mResolvedContext = object;
                    return;
                }
            } else {
                int n = this.mHostView.getId();
                if (n == -1) {
                    object = "";
                } else {
                    object = new StringBuilder();
                    object.append(" with id '");
                    object.append(this.mHostView.getContext().getResources().getResourceEntryName(n));
                    object.append("'");
                    object = object.toString();
                }
                object2 = new StringBuilder();
                object2.append("Could not find method ");
                object2.append(this.mMethodName);
                object2.append("(View) in a parent or ancestor Context for android:onClick ");
                object2.append("attribute defined on view ");
                object2.append(this.mHostView.getClass());
                object2.append((String)object);
                throw new IllegalStateException(object2.toString());
                catch (NoSuchMethodException noSuchMethodException) {}
            }
            if (object instanceof ContextWrapper) {
                object = ((ContextWrapper)object).getBaseContext();
                continue;
            }
            object = null;
        } while (true);
    }

    public void onClick(@NonNull View view) {
        if (this.mResolvedMethod == null) {
            this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
        }
        try {
            this.mResolvedMethod.invoke((Object)this.mResolvedContext, new Object[]{view});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
        }
    }
}
