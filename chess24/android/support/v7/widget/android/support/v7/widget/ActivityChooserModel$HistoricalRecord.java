/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package android.support.v7.widget;

import android.content.ComponentName;
import android.support.v7.widget.ActivityChooserModel;
import java.math.BigDecimal;

public static final class ActivityChooserModel.HistoricalRecord {
    public final ComponentName activity;
    public final long time;
    public final float weight;

    public ActivityChooserModel.HistoricalRecord(ComponentName componentName, long l, float f) {
        this.activity = componentName;
        this.time = l;
        this.weight = f;
    }

    public ActivityChooserModel.HistoricalRecord(String string, long l, float f) {
        this(ComponentName.unflattenFromString((String)string), l, f);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (ActivityChooserModel.HistoricalRecord)object;
        if (this.activity == null ? object.activity != null : !this.activity.equals((Object)object.activity)) {
            return false;
        }
        if (this.time != object.time) {
            return false;
        }
        if (Float.floatToIntBits(this.weight) != Float.floatToIntBits(object.weight)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n = this.activity == null ? 0 : this.activity.hashCode();
        return 31 * ((n + 31) * 31 + (int)(this.time ^ this.time >>> 32)) + Float.floatToIntBits(this.weight);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append("; activity:");
        stringBuilder.append((Object)this.activity);
        stringBuilder.append("; time:");
        stringBuilder.append(this.time);
        stringBuilder.append("; weight:");
        stringBuilder.append(new BigDecimal(this.weight));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
