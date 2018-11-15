/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.v4.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class TypedArrayUtils {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    public static int getAttr(@NonNull Context context, int n, int n2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(n, typedValue, true);
        if (typedValue.resourceId != 0) {
            return n;
        }
        return n2;
    }

    public static boolean getBoolean(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2, boolean bl) {
        return typedArray.getBoolean(n, typedArray.getBoolean(n2, bl));
    }

    @Nullable
    public static Drawable getDrawable(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2) {
        Drawable drawable;
        Drawable drawable2 = drawable = typedArray.getDrawable(n);
        if (drawable == null) {
            drawable2 = typedArray.getDrawable(n2);
        }
        return drawable2;
    }

    public static int getInt(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2, int n3) {
        return typedArray.getInt(n, typedArray.getInt(n2, n3));
    }

    public static boolean getNamedBoolean(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n, boolean bl) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return bl;
        }
        return typedArray.getBoolean(n, bl);
    }

    @ColorInt
    public static int getNamedColor(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n, @ColorInt int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return n2;
        }
        return typedArray.getColor(n, n2);
    }

    public static float getNamedFloat(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n, float f) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return f;
        }
        return typedArray.getFloat(n, f);
    }

    public static int getNamedInt(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n, int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return n2;
        }
        return typedArray.getInt(n, n2);
    }

    @AnyRes
    public static int getNamedResourceId(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n, @AnyRes int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return n2;
        }
        return typedArray.getResourceId(n, n2);
    }

    @Nullable
    public static String getNamedString(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, @StyleableRes int n) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return null;
        }
        return typedArray.getString(n);
    }

    @AnyRes
    public static int getResourceId(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2, @AnyRes int n3) {
        return typedArray.getResourceId(n, typedArray.getResourceId(n2, n3));
    }

    @Nullable
    public static String getString(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2) {
        String string;
        String string2 = string = typedArray.getString(n);
        if (string == null) {
            string2 = typedArray.getString(n2);
        }
        return string2;
    }

    @Nullable
    public static CharSequence getText(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2) {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = typedArray.getText(n);
        if (charSequence == null) {
            charSequence2 = typedArray.getText(n2);
        }
        return charSequence2;
    }

    @Nullable
    public static CharSequence[] getTextArray(@NonNull TypedArray typedArray, @StyleableRes int n, @StyleableRes int n2) {
        CharSequence[] arrcharSequence;
        CharSequence[] arrcharSequence2 = arrcharSequence = typedArray.getTextArray(n);
        if (arrcharSequence == null) {
            arrcharSequence2 = typedArray.getTextArray(n2);
        }
        return arrcharSequence2;
    }

    public static boolean hasAttribute(@NonNull XmlPullParser xmlPullParser, @NonNull String string) {
        if (xmlPullParser.getAttributeValue(NAMESPACE, string) != null) {
            return true;
        }
        return false;
    }

    @NonNull
    public static TypedArray obtainAttributes(@NonNull Resources resources, @Nullable Resources.Theme theme, @NonNull AttributeSet attributeSet, @NonNull int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }

    @Nullable
    public static TypedValue peekNamedValue(@NonNull TypedArray typedArray, @NonNull XmlPullParser xmlPullParser, @NonNull String string, int n) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string)) {
            return null;
        }
        return typedArray.peekValue(n);
    }
}
