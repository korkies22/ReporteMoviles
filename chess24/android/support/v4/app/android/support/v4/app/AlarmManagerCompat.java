/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.AlarmManager$AlarmClockInfo
 *  android.app.PendingIntent
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.NonNull;

public final class AlarmManagerCompat {
    private AlarmManagerCompat() {
    }

    public static void setAlarmClock(@NonNull AlarmManager alarmManager, long l, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
        if (Build.VERSION.SDK_INT >= 21) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(l, pendingIntent), pendingIntent2);
            return;
        }
        AlarmManagerCompat.setExact(alarmManager, 0, l, pendingIntent2);
    }

    public static void setAndAllowWhileIdle(@NonNull AlarmManager alarmManager, int n, long l, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setAndAllowWhileIdle(n, l, pendingIntent);
            return;
        }
        alarmManager.set(n, l, pendingIntent);
    }

    public static void setExact(@NonNull AlarmManager alarmManager, int n, long l, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(n, l, pendingIntent);
            return;
        }
        alarmManager.set(n, l, pendingIntent);
    }

    public static void setExactAndAllowWhileIdle(@NonNull AlarmManager alarmManager, int n, long l, @NonNull PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(n, l, pendingIntent);
            return;
        }
        AlarmManagerCompat.setExact(alarmManager, n, l, pendingIntent);
    }
}
