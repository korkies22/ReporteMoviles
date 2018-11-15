/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.ActionMode;
import android.widget.TextView;
import java.lang.reflect.Field;

static class TextViewCompat.TextViewCompatBaseImpl {
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

    TextViewCompat.TextViewCompatBaseImpl() {
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
            sMaxModeField = TextViewCompat.TextViewCompatBaseImpl.retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        if (sMaxModeField != null && TextViewCompat.TextViewCompatBaseImpl.retrieveIntFromField(sMaxModeField, textView) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = TextViewCompat.TextViewCompatBaseImpl.retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            if (sMaximumField != null) {
                return TextViewCompat.TextViewCompatBaseImpl.retrieveIntFromField(sMaximumField, textView);
            }
        }
        return -1;
    }

    public int getMinLines(TextView textView) {
        if (!sMinModeFieldFetched) {
            sMinModeField = TextViewCompat.TextViewCompatBaseImpl.retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        if (sMinModeField != null && TextViewCompat.TextViewCompatBaseImpl.retrieveIntFromField(sMinModeField, textView) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = TextViewCompat.TextViewCompatBaseImpl.retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            if (sMinimumField != null) {
                return TextViewCompat.TextViewCompatBaseImpl.retrieveIntFromField(sMinimumField, textView);
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
