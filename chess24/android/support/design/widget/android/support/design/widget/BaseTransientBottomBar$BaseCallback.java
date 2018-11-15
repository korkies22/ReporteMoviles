/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.annotation.RestrictTo;
import android.support.design.widget.BaseTransientBottomBar;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static abstract class BaseTransientBottomBar.BaseCallback<B> {
    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    public static final int DISMISS_EVENT_MANUAL = 3;
    public static final int DISMISS_EVENT_SWIPE = 0;
    public static final int DISMISS_EVENT_TIMEOUT = 2;

    public void onDismissed(B b, int n) {
    }

    public void onShown(B b) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface DismissEvent {
    }

}
