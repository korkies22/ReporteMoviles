/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package android.support.v4.util;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class Preconditions {
    public static void checkArgument(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static float checkArgumentFinite(float f, String string) {
        if (Float.isNaN(f)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" must not be NaN");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (Float.isInfinite(f)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" must not be infinite");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return f;
    }

    public static float checkArgumentInRange(float f, float f2, float f3, String string) {
        if (Float.isNaN(f)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" must not be NaN");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (f < f2) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", string, Float.valueOf(f2), Float.valueOf(f3)));
        }
        if (f > f3) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", string, Float.valueOf(f2), Float.valueOf(f3)));
        }
        return f;
    }

    public static int checkArgumentInRange(int n, int n2, int n3, String string) {
        if (n < n2) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", string, n2, n3));
        }
        if (n > n3) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", string, n2, n3));
        }
        return n;
    }

    public static long checkArgumentInRange(long l, long l2, long l3, String string) {
        if (l < l2) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", string, l2, l3));
        }
        if (l > l3) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", string, l2, l3));
        }
        return l;
    }

    @IntRange(from=0L)
    public static int checkArgumentNonnegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return n;
    }

    @IntRange(from=0L)
    public static int checkArgumentNonnegative(int n, String string) {
        if (n < 0) {
            throw new IllegalArgumentException(string);
        }
        return n;
    }

    public static long checkArgumentNonnegative(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException();
        }
        return l;
    }

    public static long checkArgumentNonnegative(long l, String string) {
        if (l < 0L) {
            throw new IllegalArgumentException(string);
        }
        return l;
    }

    public static int checkArgumentPositive(int n, String string) {
        if (n <= 0) {
            throw new IllegalArgumentException(string);
        }
        return n;
    }

    public static float[] checkArrayElementsInRange(float[] object, float f, float f2, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" must not be null");
        Preconditions.checkNotNull(object, stringBuilder.toString());
        for (int i = 0; i < ((float[])object).length; ++i) {
            float f3 = object[i];
            if (Float.isNaN(f3)) {
                object = new StringBuilder();
                object.append(string);
                object.append("[");
                object.append(i);
                object.append("] must not be NaN");
                throw new IllegalArgumentException(object.toString());
            }
            if (f3 < f) {
                throw new IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too low)", string, i, Float.valueOf(f), Float.valueOf(f2)));
            }
            if (f3 <= f2) continue;
            throw new IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too high)", string, i, Float.valueOf(f), Float.valueOf(f2)));
        }
        return object;
    }

    public static <T> T[] checkArrayElementsNotNull(T[] object, String string) {
        if (object == null) {
            object = new StringBuilder();
            object.append(string);
            object.append(" must not be null");
            throw new NullPointerException(object.toString());
        }
        for (int i = 0; i < ((T[])object).length; ++i) {
            if (object[i] != null) continue;
            throw new NullPointerException(String.format(Locale.US, "%s[%d] must not be null", string, i));
        }
        return object;
    }

    @NonNull
    public static <C extends Collection<T>, T> C checkCollectionElementsNotNull(C object, String string) {
        if (object == null) {
            object = new StringBuilder();
            object.append(string);
            object.append(" must not be null");
            throw new NullPointerException(object.toString());
        }
        long l = 0L;
        Iterator<T> iterator = object.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                throw new NullPointerException(String.format(Locale.US, "%s[%d] must not be null", string, l));
            }
            ++l;
        }
        return (C)object;
    }

    public static <T> Collection<T> checkCollectionNotEmpty(Collection<T> object, String string) {
        if (object == null) {
            object = new StringBuilder();
            object.append(string);
            object.append(" must not be null");
            throw new NullPointerException(object.toString());
        }
        if (object.isEmpty()) {
            object = new StringBuilder();
            object.append(string);
            object.append(" is empty");
            throw new IllegalArgumentException(object.toString());
        }
        return object;
    }

    public static int checkFlagsArgument(int n, int n2) {
        if ((n & n2) != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested flags 0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(", but only 0x");
            stringBuilder.append(Integer.toHexString(n2));
            stringBuilder.append(" are allowed");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return n;
    }

    @NonNull
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @NonNull
    public static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    public static void checkState(boolean bl) {
        Preconditions.checkState(bl, null);
    }

    public static void checkState(boolean bl, String string) {
        if (!bl) {
            throw new IllegalStateException(string);
        }
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(T t) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException();
        }
        return t;
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(T t, Object object) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return t;
    }
}
