/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzabs;
import com.google.android.gms.internal.zzsd;
import com.google.android.gms.internal.zzsg;

public final class zzsq {
    public static zza<String> zzaeA;
    public static zza<String> zzaeB;
    public static zza<Integer> zzaeC;
    public static zza<String> zzaeD;
    public static zza<String> zzaeE;
    public static zza<Integer> zzaeF;
    public static zza<Integer> zzaeG;
    public static zza<Integer> zzaeH;
    public static zza<Integer> zzaeI;
    public static zza<String> zzaeJ;
    public static zza<Integer> zzaeK;
    public static zza<Long> zzaeL;
    public static zza<Integer> zzaeM;
    public static zza<Integer> zzaeN;
    public static zza<Long> zzaeO;
    public static zza<String> zzaeP;
    public static zza<Integer> zzaeQ;
    public static zza<Boolean> zzaeR;
    public static zza<Long> zzaeS;
    public static zza<Long> zzaeT;
    public static zza<Long> zzaeU;
    public static zza<Long> zzaeV;
    public static zza<Long> zzaeW;
    public static zza<Long> zzaeX;
    public static zza<Long> zzaeY;
    public static zza<Boolean> zzaei;
    public static zza<Boolean> zzaej;
    public static zza<String> zzaek;
    public static zza<Long> zzael;
    public static zza<Float> zzaem;
    public static zza<Integer> zzaen;
    public static zza<Integer> zzaeo;
    public static zza<Integer> zzaep;
    public static zza<Long> zzaeq;
    public static zza<Long> zzaer;
    public static zza<Long> zzaes;
    public static zza<Long> zzaet;
    public static zza<Long> zzaeu;
    public static zza<Long> zzaev;
    public static zza<Integer> zzaew;
    public static zza<Integer> zzaex;
    public static zza<String> zzaey;
    public static zza<String> zzaez;

    static {
        zzaei = zza.zzf("analytics.service_enabled", false);
        zzaej = zza.zzf("analytics.service_client_enabled", true);
        zzaek = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
        zzael = zza.zzb("analytics.max_tokens", 60L);
        zzaem = zza.zza("analytics.tokens_per_sec", 0.5f);
        zzaen = zza.zza("analytics.max_stored_hits", 2000, 20000);
        zzaeo = zza.zze("analytics.max_stored_hits_per_app", 2000);
        zzaep = zza.zze("analytics.max_stored_properties_per_app", 100);
        zzaeq = zza.zza("analytics.local_dispatch_millis", 1800000L, 120000L);
        zzaer = zza.zza("analytics.initial_local_dispatch_millis", 5000L, 5000L);
        zzaes = zza.zzb("analytics.min_local_dispatch_millis", 120000L);
        zzaet = zza.zzb("analytics.max_local_dispatch_millis", 7200000L);
        zzaeu = zza.zzb("analytics.dispatch_alarm_millis", 7200000L);
        zzaev = zza.zzb("analytics.max_dispatch_alarm_millis", 32400000L);
        zzaew = zza.zze("analytics.max_hits_per_dispatch", 20);
        zzaex = zza.zze("analytics.max_hits_per_batch", 20);
        zzaey = zza.zzq("analytics.insecure_host", "http://www.google-analytics.com");
        zzaez = zza.zzq("analytics.secure_host", "https://ssl.google-analytics.com");
        zzaeA = zza.zzq("analytics.simple_endpoint", "/collect");
        zzaeB = zza.zzq("analytics.batching_endpoint", "/batch");
        zzaeC = zza.zze("analytics.max_get_length", 2036);
        zzaeD = zza.zzd("analytics.batching_strategy.k", zzsd.zzadO.name(), zzsd.zzadO.name());
        zzaeE = zza.zzq("analytics.compression_strategy.k", zzsg.zzadV.name());
        zzaeF = zza.zze("analytics.max_hits_per_request.k", 20);
        zzaeG = zza.zze("analytics.max_hit_length.k", 8192);
        zzaeH = zza.zze("analytics.max_post_length.k", 8192);
        zzaeI = zza.zze("analytics.max_batch_post_length", 8192);
        zzaeJ = zza.zzq("analytics.fallback_responses.k", "404,502");
        zzaeK = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
        zzaeL = zza.zzb("analytics.service_monitor_interval", 86400000L);
        zzaeM = zza.zze("analytics.http_connection.connect_timeout_millis", 60000);
        zzaeN = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
        zzaeO = zza.zzb("analytics.campaigns.time_limit", 86400000L);
        zzaeP = zza.zzq("analytics.first_party_experiment_id", "");
        zzaeQ = zza.zze("analytics.first_party_experiment_variant", 0);
        zzaeR = zza.zzf("analytics.test.disable_receiver", false);
        zzaeS = zza.zza("analytics.service_client.idle_disconnect_millis", 10000L, 10000L);
        zzaeT = zza.zzb("analytics.service_client.connect_timeout_millis", 5000L);
        zzaeU = zza.zzb("analytics.service_client.second_connect_delay_millis", 5000L);
        zzaeV = zza.zzb("analytics.service_client.unexpected_reconnect_millis", 60000L);
        zzaeW = zza.zzb("analytics.service_client.reconnect_throttle_millis", 1800000L);
        zzaeX = zza.zzb("analytics.monitoring.sample_period_millis", 86400000L);
        zzaeY = zza.zzb("analytics.initialization_warning_threshold", 5000L);
    }

    public static final class zza<V> {
        private final V zzaeZ;
        private final zzabs<V> zzafa;

        private zza(zzabs<V> zzabs2, V v) {
            zzac.zzw(zzabs2);
            this.zzafa = zzabs2;
            this.zzaeZ = v;
        }

        static zza<Float> zza(String string, float f) {
            return zza.zza(string, f, f);
        }

        static zza<Float> zza(String string, float f, float f2) {
            return new zza<Float>(zzabs.zza(string, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> zza(String string, int n, int n2) {
            return new zza<Integer>(zzabs.zza(string, n2), n);
        }

        static zza<Long> zza(String string, long l, long l2) {
            return new zza<Long>(zzabs.zza(string, l2), l);
        }

        static zza<Boolean> zza(String string, boolean bl, boolean bl2) {
            return new zza<Boolean>(zzabs.zzj(string, bl2), bl);
        }

        static zza<Long> zzb(String string, long l) {
            return zza.zza(string, l, l);
        }

        static zza<String> zzd(String string, String string2, String string3) {
            return new zza<String>(zzabs.zzA(string, string3), string2);
        }

        static zza<Integer> zze(String string, int n) {
            return zza.zza(string, n, n);
        }

        static zza<Boolean> zzf(String string, boolean bl) {
            return zza.zza(string, bl, bl);
        }

        static zza<String> zzq(String string, String string2) {
            return zza.zzd(string, string2, string2);
        }

        public V get() {
            return this.zzaeZ;
        }
    }

}
