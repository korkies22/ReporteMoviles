/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(value=26)
static class TextViewCompat.TextViewCompatApi26Impl
extends TextViewCompat.TextViewCompatApi23Impl {
    TextViewCompat.TextViewCompatApi26Impl() {
    }

    @Override
    public void setCustomSelectionActionModeCallback(final TextView textView, final ActionMode.Callback callback) {
        if (Build.VERSION.SDK_INT != 26 && Build.VERSION.SDK_INT != 27) {
            super.setCustomSelectionActionModeCallback(textView, callback);
            return;
        }
        textView.setCustomSelectionActionModeCallback(new ActionMode.Callback(){
            private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
            private boolean mCanUseMenuBuilderReferences;
            private boolean mInitializedMenuBuilderReferences = false;
            private Class mMenuBuilderClass;
            private Method mMenuBuilderRemoveItemAtMethod;

            private Intent createProcessTextIntent() {
                return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
            }

            private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo, TextView textView2) {
                return this.createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", this.isEditable(textView2) ^ true).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            }

            private List<ResolveInfo> getSupportedActivities(Context context, PackageManager object) {
                ArrayList<ResolveInfo> arrayList = new ArrayList<ResolveInfo>();
                if (!(context instanceof Activity)) {
                    return arrayList;
                }
                for (ResolveInfo resolveInfo : object.queryIntentActivities(this.createProcessTextIntent(), 0)) {
                    if (!this.isSupportedActivity(resolveInfo, context)) continue;
                    arrayList.add(resolveInfo);
                }
                return arrayList;
            }

            private boolean isEditable(TextView textView2) {
                if (textView2 instanceof Editable && textView2.onCheckIsTextEditor() && textView2.isEnabled()) {
                    return true;
                }
                return false;
            }

            private boolean isSupportedActivity(ResolveInfo resolveInfo, Context context) {
                boolean bl = context.getPackageName().equals(resolveInfo.activityInfo.packageName);
                boolean bl2 = true;
                if (bl) {
                    return true;
                }
                if (!resolveInfo.activityInfo.exported) {
                    return false;
                }
                if (resolveInfo.activityInfo.permission != null) {
                    if (context.checkSelfPermission(resolveInfo.activityInfo.permission) == 0) {
                        return true;
                    }
                    bl2 = false;
                }
                return bl2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void recomputeProcessTextMenuItems(Menu menu) {
                int n;
                Context context;
                Object object;
                PackageManager packageManager;
                block8 : {
                    context = textView.getContext();
                    packageManager = context.getPackageManager();
                    if (!this.mInitializedMenuBuilderReferences) {
                        this.mInitializedMenuBuilderReferences = true;
                        try {
                            this.mMenuBuilderClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
                            this.mMenuBuilderRemoveItemAtMethod = this.mMenuBuilderClass.getDeclaredMethod("removeItemAt", Integer.TYPE);
                            this.mCanUseMenuBuilderReferences = true;
                            break block8;
                        }
                        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {}
                        this.mMenuBuilderClass = null;
                        this.mMenuBuilderRemoveItemAtMethod = null;
                        this.mCanUseMenuBuilderReferences = false;
                    }
                }
                try {
                    object = this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance((Object)menu) ? this.mMenuBuilderRemoveItemAtMethod : menu.getClass().getDeclaredMethod("removeItemAt", Integer.TYPE);
                    for (n = menu.size() - 1; n >= 0; --n) {
                        MenuItem menuItem = menu.getItem(n);
                        if (menuItem.getIntent() == null || !"android.intent.action.PROCESS_TEXT".equals(menuItem.getIntent().getAction())) continue;
                        object.invoke((Object)menu, n);
                    }
                    object = this.getSupportedActivities(context, packageManager);
                    n = 0;
                }
                catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                    return;
                }
                do {
                    if (n >= object.size()) {
                        return;
                    }
                    context = (ResolveInfo)object.get(n);
                    menu.add(0, 0, 100 + n, context.loadLabel(packageManager)).setIntent(this.createProcessTextIntentForResolveInfo((ResolveInfo)context, textView)).setShowAsAction(1);
                    ++n;
                } while (true);
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return callback.onActionItemClicked(actionMode, menuItem);
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return callback.onCreateActionMode(actionMode, menu);
            }

            public void onDestroyActionMode(ActionMode actionMode) {
                callback.onDestroyActionMode(actionMode);
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                this.recomputeProcessTextMenuItems(menu);
                return callback.onPrepareActionMode(actionMode, menu);
            }
        });
    }

}
