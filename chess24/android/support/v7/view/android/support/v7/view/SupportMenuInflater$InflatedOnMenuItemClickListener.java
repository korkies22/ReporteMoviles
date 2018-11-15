/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.InflateException
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 */
package android.support.v7.view;

import android.support.v7.view.SupportMenuInflater;
import android.view.InflateException;
import android.view.MenuItem;
import java.lang.reflect.Method;

private static class SupportMenuInflater.InflatedOnMenuItemClickListener
implements MenuItem.OnMenuItemClickListener {
    private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
    private Method mMethod;
    private Object mRealOwner;

    public SupportMenuInflater.InflatedOnMenuItemClickListener(Object object, String string) {
        this.mRealOwner = object;
        Class<?> class_ = object.getClass();
        try {
            this.mMethod = class_.getMethod(string, PARAM_TYPES);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't resolve menu item onClick handler ");
            stringBuilder.append(string);
            stringBuilder.append(" in class ");
            stringBuilder.append(class_.getName());
            string = new InflateException(stringBuilder.toString());
            string.initCause((Throwable)exception);
            throw string;
        }
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        try {
            if (this.mMethod.getReturnType() == Boolean.TYPE) {
                return (Boolean)this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
            }
            this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
            return true;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
