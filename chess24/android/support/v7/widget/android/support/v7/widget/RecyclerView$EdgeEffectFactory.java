/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.EdgeEffect
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.EdgeEffect;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class RecyclerView.EdgeEffectFactory {
    public static final int DIRECTION_BOTTOM = 3;
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_TOP = 1;

    @NonNull
    protected EdgeEffect createEdgeEffect(RecyclerView recyclerView, int n) {
        return new EdgeEffect(recyclerView.getContext());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EdgeDirection {
    }

}
