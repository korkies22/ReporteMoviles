/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.util.Preconditions;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat {
    private static final String TAG = "ResourcesCompat";

    private ResourcesCompat() {
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int n, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(n, theme);
        }
        return resources.getColor(n);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int n, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColorStateList(n, theme);
        }
        return resources.getColorStateList(n);
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int n, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(n, theme);
        }
        return resources.getDrawable(n);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int n, int n2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawableForDensity(n, n2, theme);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            return resources.getDrawableForDensity(n, n2);
        }
        return resources.getDrawable(n);
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int n) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return ResourcesCompat.loadFont(context, n, new TypedValue(), 0, null, null, false);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static Typeface getFont(@NonNull Context context, @FontRes int n, TypedValue typedValue, int n2, @Nullable FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return ResourcesCompat.loadFont(context, n, typedValue, n2, fontCallback, null, true);
    }

    public static void getFont(@NonNull Context context, @FontRes int n, @NonNull FontCallback fontCallback, @Nullable Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
            return;
        }
        ResourcesCompat.loadFont(context, n, new TypedValue(), 0, fontCallback, handler, false);
    }

    private static Typeface loadFont(@NonNull Context object, int n, TypedValue typedValue, int n2, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean bl) {
        Resources resources = object.getResources();
        resources.getValue(n, typedValue, true);
        object = ResourcesCompat.loadFont((Context)object, resources, typedValue, n, n2, fontCallback, handler, bl);
        if (object == null && fontCallback == null) {
            object = new StringBuilder();
            object.append("Font resource ID #0x");
            object.append(Integer.toHexString(n));
            object.append(" could not be retrieved.");
            throw new Resources.NotFoundException(object.toString());
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Typeface loadFont(@NonNull Context object, Resources object2, TypedValue object3, int n, int n2, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean bl) {
        if (object3.string == null) {
            object = new StringBuilder();
            object.append("Resource \"");
            object.append(object2.getResourceName(n));
            object.append("\" (");
            object.append(Integer.toHexString(n));
            object.append(") is not a Font: ");
            object.append(object3);
            throw new Resources.NotFoundException(object.toString());
        }
        object3 = object3.string.toString();
        if (!object3.startsWith("res/")) {
            if (fontCallback == null) return null;
            {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        }
        Typeface typeface = TypefaceCompat.findFromCache((Resources)object2, n, n2);
        if (typeface != null) {
            if (fontCallback == null) return typeface;
            {
                fontCallback.callbackSuccessAsync(typeface, handler);
            }
            return typeface;
        }
        try {
            if (object3.toLowerCase().endsWith(".xml")) {
                FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = FontResourcesParserCompat.parse((XmlPullParser)object2.getXml(n), (Resources)object2);
                if (familyResourceEntry != null) {
                    return TypefaceCompat.createFromResourcesFamilyXml((Context)object, familyResourceEntry, (Resources)object2, n, n2, fontCallback, handler, bl);
                }
                Log.e((String)TAG, (String)"Failed to find font-family tag");
                if (fontCallback == null) return null;
                {
                    fontCallback.callbackFailAsync(-3, handler);
                    return null;
                }
            }
            object = TypefaceCompat.createFromResourcesFontFile((Context)object, (Resources)object2, n, (String)object3, n2);
            if (fontCallback == null) return object;
            {
                if (object != null) {
                    fontCallback.callbackSuccessAsync((Typeface)object, handler);
                    return object;
                }
                fontCallback.callbackFailAsync(-3, handler);
            }
            return object;
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            object2.append("Failed to read xml resource ");
            object2.append((String)object3);
            Log.e((String)TAG, (String)object2.toString(), (Throwable)iOException);
        }
        catch (XmlPullParserException xmlPullParserException) {
            object2 = new StringBuilder();
            object2.append("Failed to parse xml resource ");
            object2.append((String)object3);
            Log.e((String)TAG, (String)object2.toString(), (Throwable)xmlPullParserException);
        }
        if (fontCallback == null) return null;
        {
            fontCallback.callbackFailAsync(-3, handler);
        }
        return null;
    }

    public static abstract class FontCallback {
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackFailAsync(final int n, @Nullable Handler handler) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler(Looper.getMainLooper());
            }
            handler2.post(new Runnable(){

                @Override
                public void run() {
                    FontCallback.this.onFontRetrievalFailed(n);
                }
            });
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler(Looper.getMainLooper());
            }
            handler2.post(new Runnable(){

                @Override
                public void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int var1);

        public abstract void onFontRetrieved(@NonNull Typeface var1);

    }

}
