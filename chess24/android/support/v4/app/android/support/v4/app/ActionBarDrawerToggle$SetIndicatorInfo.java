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
package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import java.lang.reflect.Method;

private static class ActionBarDrawerToggle.SetIndicatorInfo {
    Method mSetHomeActionContentDescription;
    Method mSetHomeAsUpIndicator;
    ImageView mUpIndicatorView;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ActionBarDrawerToggle.SetIndicatorInfo(Activity activity) {
        try {
            this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
            this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
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
            this.mUpIndicatorView = (ImageView)activity;
        }
    }
}
