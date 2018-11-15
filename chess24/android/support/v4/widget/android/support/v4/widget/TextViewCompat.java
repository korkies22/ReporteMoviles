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
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.util.Log
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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v4.os.BuildCompat;
import android.support.v4.widget.AutoSizeableTextView;
import android.text.Editable;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    static final TextViewCompatBaseImpl IMPL = BuildCompat.isAtLeastOMR1() ? new TextViewCompatApi27Impl() : (Build.VERSION.SDK_INT >= 26 ? new TextViewCompatApi26Impl() : (Build.VERSION.SDK_INT >= 23 ? new TextViewCompatApi23Impl() : (Build.VERSION.SDK_INT >= 18 ? new TextViewCompatApi18Impl() : (Build.VERSION.SDK_INT >= 17 ? new TextViewCompatApi17Impl() : (Build.VERSION.SDK_INT >= 16 ? new TextViewCompatApi16Impl() : new TextViewCompatBaseImpl())))));

    private TextViewCompat() {
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMaxTextSize(textView);
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMinTextSize(textView);
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        return IMPL.getAutoSizeStepGranularity(textView);
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextAvailableSizes(textView);
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextType(textView);
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return IMPL.getMaxLines(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return IMPL.getMinLines(textView);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int n, int n2, int n3, int n4) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithConfiguration(textView, n, n2, n3, n4);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] arrn, int n) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithPresetSizes(textView, arrn, n);
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int n) {
        IMPL.setAutoSizeTextTypeWithDefaults(textView, n);
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.setCompoundDrawablesRelative(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int n, @DrawableRes int n2, @DrawableRes int n3, @DrawableRes int n4) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, n, n2, n3, n4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull ActionMode.Callback callback) {
        IMPL.setCustomSelectionActionModeCallback(textView, callback);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int n) {
        IMPL.setTextAppearance(textView, n);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface AutoSizeTextType {
    }

    @RequiresApi(value=16)
    static class TextViewCompatApi16Impl
    extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        @Override
        public int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }

        @Override
        public int getMinLines(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(value=17)
    static class TextViewCompatApi17Impl
    extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        @Override
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView arrdrawable) {
            int n = arrdrawable.getLayoutDirection();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            arrdrawable = arrdrawable.getCompoundDrawables();
            if (bl) {
                Drawable drawable = arrdrawable[2];
                Drawable drawable2 = arrdrawable[0];
                arrdrawable[0] = drawable;
                arrdrawable[2] = drawable2;
            }
            return arrdrawable;
        }

        @Override
        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            int n = textView.getLayoutDirection();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            Drawable drawable5 = bl ? drawable3 : drawable;
            if (!bl) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        }

        @Override
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int n, @DrawableRes int n2, @DrawableRes int n3, @DrawableRes int n4) {
            int n5 = textView.getLayoutDirection();
            boolean bl = true;
            if (n5 != 1) {
                bl = false;
            }
            n5 = bl ? n3 : n;
            if (!bl) {
                n = n3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(n5, n2, n, n4);
        }

        @Override
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            int n = textView.getLayoutDirection();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            Drawable drawable5 = bl ? drawable3 : drawable;
            if (!bl) {
                drawable = drawable3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        }
    }

    @RequiresApi(value=18)
    static class TextViewCompatApi18Impl
    extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        @Override
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }

        @Override
        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        }

        @Override
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int n, @DrawableRes int n2, @DrawableRes int n3, @DrawableRes int n4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(n, n2, n3, n4);
        }

        @Override
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }

    @RequiresApi(value=23)
    static class TextViewCompatApi23Impl
    extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        @Override
        public void setTextAppearance(@NonNull TextView textView, @StyleRes int n) {
            textView.setTextAppearance(n);
        }
    }

    @RequiresApi(value=26)
    static class TextViewCompatApi26Impl
    extends TextViewCompatApi23Impl {
        TextViewCompatApi26Impl() {
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

    @RequiresApi(value=27)
    static class TextViewCompatApi27Impl
    extends TextViewCompatApi26Impl {
        TextViewCompatApi27Impl() {
        }

        @Override
        public int getAutoSizeMaxTextSize(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }

        @Override
        public int getAutoSizeMinTextSize(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        @Override
        public int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        @Override
        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }

        @Override
        public int getAutoSizeTextType(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        @Override
        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int n, int n2, int n3, int n4) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
        }

        @Override
        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] arrn, int n) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
        }

        @Override
        public void setAutoSizeTextTypeWithDefaults(TextView textView, int n) {
            textView.setAutoSizeTextTypeWithDefaults(n);
        }
    }

    static class TextViewCompatBaseImpl {
        private static final int LINES = 1;
        private static final String LOG_TAG = "TextViewCompatBase";
        private static Field sMaxModeField;
        private static boolean sMaxModeFieldFetched;
        private static Field sMaximumField;
        private static boolean sMaximumFieldFetched;
        private static Field sMinModeField;
        private static boolean sMinModeFieldFetched;
        private static Field sMinimumField;
        private static boolean sMinimumFieldFetched;

        TextViewCompatBaseImpl() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static Field retrieveField(String string) {
            Field field;
            try {
                field = TextView.class.getDeclaredField(string);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            try {
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            field = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve ");
            stringBuilder.append(string);
            stringBuilder.append(" field.");
            Log.e((String)LOG_TAG, (String)stringBuilder.toString());
            return field;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static int retrieveIntFromField(Field field, TextView textView) {
            try {
                return field.getInt((Object)textView);
            }
            catch (IllegalAccessException illegalAccessException) {}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve value of ");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field.");
            Log.d((String)LOG_TAG, (String)stringBuilder.toString());
            return -1;
        }

        public int getAutoSizeMaxTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView)textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        public int getAutoSizeMinTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView)textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        public int getAutoSizeStepGranularity(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView)textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView)textView).getAutoSizeTextAvailableSizes();
            }
            return new int[0];
        }

        public int getAutoSizeTextType(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView)textView).getAutoSizeTextType();
            }
            return 0;
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        public int getMaxLines(TextView textView) {
            if (!sMaxModeFieldFetched) {
                sMaxModeField = TextViewCompatBaseImpl.retrieveField("mMaxMode");
                sMaxModeFieldFetched = true;
            }
            if (sMaxModeField != null && TextViewCompatBaseImpl.retrieveIntFromField(sMaxModeField, textView) == 1) {
                if (!sMaximumFieldFetched) {
                    sMaximumField = TextViewCompatBaseImpl.retrieveField("mMaximum");
                    sMaximumFieldFetched = true;
                }
                if (sMaximumField != null) {
                    return TextViewCompatBaseImpl.retrieveIntFromField(sMaximumField, textView);
                }
            }
            return -1;
        }

        public int getMinLines(TextView textView) {
            if (!sMinModeFieldFetched) {
                sMinModeField = TextViewCompatBaseImpl.retrieveField("mMinMode");
                sMinModeFieldFetched = true;
            }
            if (sMinModeField != null && TextViewCompatBaseImpl.retrieveIntFromField(sMinModeField, textView) == 1) {
                if (!sMinimumFieldFetched) {
                    sMinimumField = TextViewCompatBaseImpl.retrieveField("mMinimum");
                    sMinimumFieldFetched = true;
                }
                if (sMinimumField != null) {
                    return TextViewCompatBaseImpl.retrieveIntFromField(sMinimumField, textView);
                }
            }
            return -1;
        }

        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int n, int n2, int n3, int n4) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView)textView).setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
            }
        }

        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] arrn, int n) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView)textView).setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
            }
        }

        public void setAutoSizeTextTypeWithDefaults(TextView textView, int n) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView)textView).setAutoSizeTextTypeWithDefaults(n);
            }
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int n, @DrawableRes int n2, @DrawableRes int n3, @DrawableRes int n4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(n, n2, n3, n4);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        public void setCustomSelectionActionModeCallback(TextView textView, ActionMode.Callback callback) {
            textView.setCustomSelectionActionModeCallback(callback);
        }

        public void setTextAppearance(TextView textView, @StyleRes int n) {
            textView.setTextAppearance(textView.getContext(), n);
        }
    }

}
