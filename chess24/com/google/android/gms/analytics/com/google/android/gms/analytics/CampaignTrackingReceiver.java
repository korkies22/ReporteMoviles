/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzayd;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztg;

public class CampaignTrackingReceiver
extends BroadcastReceiver {
    static zzayd zzaav;
    static Boolean zzaaw;
    static Object zztU;

    static {
        zztU = new Object();
    }

    public static boolean zzT(Context context) {
        zzac.zzw(context);
        if (zzaaw != null) {
            return zzaaw;
        }
        boolean bl = zztg.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zzaaw = bl;
        return bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context object, Intent object2) {
        zzsx zzsx2 = zzrw.zzW((Context)object).zznr();
        if (object2 == null) {
            object = "CampaignTrackingReceiver received null intent";
        } else {
            String string = object2.getStringExtra("referrer");
            object2 = object2.getAction();
            zzsx2.zza("CampaignTrackingReceiver received", object2);
            if ("com.android.vending.INSTALL_REFERRER".equals(object2) && !TextUtils.isEmpty((CharSequence)string)) {
                boolean bl = CampaignTrackingService.zzU((Context)object);
                if (!bl) {
                    zzsx2.zzbR("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
                }
                this.zzp((Context)object, string);
                object2 = this.zzlR();
                zzac.zzw(object2);
                Intent intent = new Intent((Context)object, (Class)object2);
                intent.putExtra("referrer", string);
                object2 = zztU;
                // MONITORENTER : object2
                object.startService(intent);
                if (!bl) {
                    // MONITOREXIT : object2
                    return;
                }
                if (zzaav == null) {
                    zzaav = new zzayd((Context)object, 1, "Analytics campaign WakeLock");
                    zzaav.setReferenceCounted(false);
                }
                zzaav.acquire(1000L);
                return;
            }
            object = "CampaignTrackingReceiver received unexpected intent without referrer extra";
        }
        zzsx2.zzbR((String)object);
        return;
        catch (SecurityException securityException) {}
        zzsx2.zzbR("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
        // MONITOREXIT : object2
    }

    protected Class<? extends CampaignTrackingService> zzlR() {
        return CampaignTrackingService.class;
    }

    protected void zzp(Context context, String string) {
    }
}
