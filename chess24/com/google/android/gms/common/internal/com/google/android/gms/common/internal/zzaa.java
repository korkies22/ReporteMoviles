/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class zzaa {
    public static boolean equal(@Nullable Object object, @Nullable Object object2) {
        if (!(object == object2 || object != null && object.equals(object2))) {
            return false;
        }
        return true;
    }

    public static /* varargs */ int hashCode(Object ... arrobject) {
        return Arrays.hashCode(arrobject);
    }

    public static zza zzv(Object object) {
        return new zza(object);
    }

    public static final class zza {
        private final Object zzXN;
        private final List<String> zzaEY;

        private zza(Object object) {
            this.zzXN = zzac.zzw(object);
            this.zzaEY = new ArrayList<String>();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(100);
            stringBuilder.append(this.zzXN.getClass().getSimpleName());
            stringBuilder.append('{');
            int n = this.zzaEY.size();
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(this.zzaEY.get(i));
                if (i >= n - 1) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public zza zzg(String string, Object object) {
            List<String> list = this.zzaEY;
            string = zzac.zzw(string);
            object = String.valueOf(String.valueOf(object));
            StringBuilder stringBuilder = new StringBuilder(1 + String.valueOf(string).length() + String.valueOf(object).length());
            stringBuilder.append(string);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            list.add(stringBuilder.toString());
            return this;
        }
    }

}
