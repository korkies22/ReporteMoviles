/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;
import android.support.v4.graphics.drawable.WrappedDrawableApi19;
import android.support.v4.graphics.drawable.WrappedDrawableApi21;
import android.util.AttributeSet;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableCompat {
    private static final String TAG = "DrawableCompat";
    private static Method sGetLayoutDirectionMethod;
    private static boolean sGetLayoutDirectionMethodFetched;
    private static Method sSetLayoutDirectionMethod;
    private static boolean sSetLayoutDirectionMethodFetched;

    private DrawableCompat() {
    }

    public static void applyTheme(@NonNull Drawable drawable, @NonNull Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.applyTheme(theme);
        }
    }

    public static boolean canApplyTheme(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            return drawable.canApplyTheme();
        }
        return false;
    }

    public static void clearColorFilter(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 23) {
            drawable.clearColorFilter();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.clearColorFilter();
            if (drawable instanceof InsetDrawable) {
                DrawableCompat.clearColorFilter(((InsetDrawable)drawable).getDrawable());
                return;
            }
            if (drawable instanceof WrappedDrawable) {
                DrawableCompat.clearColorFilter(((WrappedDrawable)drawable).getWrappedDrawable());
                return;
            }
            if (drawable instanceof DrawableContainer && (drawable = (DrawableContainer.DrawableContainerState)((DrawableContainer)drawable).getConstantState()) != null) {
                int n = drawable.getChildCount();
                for (int i = 0; i < n; ++i) {
                    Drawable drawable2 = drawable.getChild(i);
                    if (drawable2 == null) continue;
                    DrawableCompat.clearColorFilter(drawable2);
                }
            }
        } else {
            drawable.clearColorFilter();
        }
    }

    public static int getAlpha(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            return drawable.getAlpha();
        }
        return 0;
    }

    public static ColorFilter getColorFilter(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            return drawable.getColorFilter();
        }
        return null;
    }

    public static int getLayoutDirection(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 23) {
            return drawable.getLayoutDirection();
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (!sGetLayoutDirectionMethodFetched) {
                try {
                    sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                    sGetLayoutDirectionMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve getLayoutDirection() method", (Throwable)noSuchMethodException);
                }
                sGetLayoutDirectionMethodFetched = true;
            }
            if (sGetLayoutDirectionMethod != null) {
                try {
                    int n = (Integer)sGetLayoutDirectionMethod.invoke((Object)drawable, new Object[0]);
                    return n;
                }
                catch (Exception exception) {
                    Log.i((String)TAG, (String)"Failed to invoke getLayoutDirection() via reflection", (Throwable)exception);
                    sGetLayoutDirectionMethod = null;
                }
            }
            return 0;
        }
        return 0;
    }

    public static void inflate(@NonNull Drawable drawable, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme);
            return;
        }
        drawable.inflate(resources, xmlPullParser, attributeSet);
    }

    public static boolean isAutoMirrored(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            return drawable.isAutoMirrored();
        }
        return false;
    }

    @Deprecated
    public static void jumpToCurrentState(@NonNull Drawable drawable) {
        drawable.jumpToCurrentState();
    }

    public static void setAutoMirrored(@NonNull Drawable drawable, boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            drawable.setAutoMirrored(bl);
        }
    }

    public static void setHotspot(@NonNull Drawable drawable, float f, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspot(f, f2);
        }
    }

    public static void setHotspotBounds(@NonNull Drawable drawable, int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspotBounds(n, n2, n3, n4);
        }
    }

    public static boolean setLayoutDirection(@NonNull Drawable drawable, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return drawable.setLayoutDirection(n);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (!sSetLayoutDirectionMethodFetched) {
                try {
                    sSetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", Integer.TYPE);
                    sSetLayoutDirectionMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve setLayoutDirection(int) method", (Throwable)noSuchMethodException);
                }
                sSetLayoutDirectionMethodFetched = true;
            }
            if (sSetLayoutDirectionMethod != null) {
                try {
                    sSetLayoutDirectionMethod.invoke((Object)drawable, n);
                    return true;
                }
                catch (Exception exception) {
                    Log.i((String)TAG, (String)"Failed to invoke setLayoutDirection(int) via reflection", (Throwable)exception);
                    sSetLayoutDirectionMethod = null;
                }
            }
            return false;
        }
        return false;
    }

    public static void setTint(@NonNull Drawable drawable, @ColorInt int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTint(n);
            return;
        }
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable)drawable).setTint(n);
        }
    }

    public static void setTintList(@NonNull Drawable drawable, @Nullable ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintList(colorStateList);
            return;
        }
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable)drawable).setTintList(colorStateList);
        }
    }

    public static void setTintMode(@NonNull Drawable drawable, @NonNull PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintMode(mode);
            return;
        }
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable)drawable).setTintMode(mode);
        }
    }

    public static <T extends Drawable> T unwrap(@NonNull Drawable drawable) {
        if (drawable instanceof WrappedDrawable) {
            return (T)((WrappedDrawable)drawable).getWrappedDrawable();
        }
        return (T)drawable;
    }

    public static Drawable wrap(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 23) {
            return drawable;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (!(drawable instanceof TintAwareDrawable)) {
                return new WrappedDrawableApi21(drawable);
            }
            return drawable;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (!(drawable instanceof TintAwareDrawable)) {
                return new WrappedDrawableApi19(drawable);
            }
            return drawable;
        }
        if (!(drawable instanceof TintAwareDrawable)) {
            return new WrappedDrawableApi14(drawable);
        }
        return drawable;
    }
}
