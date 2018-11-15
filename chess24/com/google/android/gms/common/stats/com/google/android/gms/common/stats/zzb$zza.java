/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.stats;

import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.internal.zzabs;

public static final class zzb.zza {
    public static zzabs<Integer> zzaGd = zzabs.zza("gms:common:stats:connections:level", zzc.LOG_LEVEL_OFF);
    public static zzabs<String> zzaGe = zzabs.zzA("gms:common:stats:connections:ignored_calling_processes", "");
    public static zzabs<String> zzaGf = zzabs.zzA("gms:common:stats:connections:ignored_calling_services", "");
    public static zzabs<String> zzaGg = zzabs.zzA("gms:common:stats:connections:ignored_target_processes", "");
    public static zzabs<String> zzaGh = zzabs.zzA("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
    public static zzabs<Long> zzaGi = zzabs.zza("gms:common:stats:connections:time_out_duration", 600000L);
}
