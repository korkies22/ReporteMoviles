/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.ImageView
 */
package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarDrawerToggleHoneycomb;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import java.lang.reflect.Method;

static class ActionBarDrawerToggleHoneycomb.SetIndicatorInfo {
    public Method setHomeActionContentDescription;
    public Method setHomeAsUpIndicator;
    public ImageView upIndicatorView;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ActionBarDrawerToggleHoneycomb.SetIndicatorInfo(Activity activity) {
        try {
            this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
            this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        activity = activity.findViewById(16908332);
        if (activity == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup)activity.getParent();
        if (viewGroup.getChildCount() != 2) {
            return;
        }
        activity = viewGroup.getChildAt(0);
        viewGroup = viewGroup.getChildAt(1);
        if (activity.getId() == 16908332) {
            activity = viewGroup;
        }
        if (activity instanceof ImageView) {
            this.upIndicatorView = (ImageView)activity;
        }
    }
}
