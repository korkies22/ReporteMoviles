/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Pair<F, S> {
    @Nullable
    public final F first;
    @Nullable
    public final S second;

    public Pair(@Nullable F f, @Nullable S s) {
        this.first = f;
        this.second = s;
    }

    @NonNull
    public static <A, B> Pair<A, B> create(@Nullable A a, @Nullable B b) {
        return new Pair<A, B>(a, b);
    }

    private static boolean objectsEqual(Object object, Object object2) {
        if (!(object == object2 || object != null && object.equals(object2))) {
            return false;
        }
        return true;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Pair;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (Pair)object;
        bl = bl2;
        if (Pair.objectsEqual(object.first, this.first)) {
            bl = bl2;
            if (Pair.objectsEqual(object.second, this.second)) {
                bl = true;
            }
        }
        return bl;
    }

    public int hashCode() {
        F f = this.first;
        int n = 0;
        int n2 = f == null ? 0 : this.first.hashCode();
        if (this.second != null) {
            n = this.second.hashCode();
        }
        return n2 ^ n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pair{");
        stringBuilder.append(String.valueOf(this.first));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(this.second));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
