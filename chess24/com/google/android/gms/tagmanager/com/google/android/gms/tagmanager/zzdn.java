/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzdk;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzdn
extends zzdk {
    private static final String ID = zzag.zzeM.toString();
    private static final String zzbGP = zzah.zzfv.toString();
    private static final String zzbGQ = zzah.zzfG.toString();
    private static final String zzbGR = zzah.zzhf.toString();
    private static final String zzbGS = zzah.zzgY.toString();
    private static final String zzbGT = zzah.zzgX.toString();
    private static final String zzbGU = zzah.zzfF.toString();
    private static final String zzbGV = zzah.zzjN.toString();
    private static final String zzbGW = zzah.zzjQ.toString();
    private static final String zzbGX = zzah.zzjS.toString();
    private static final List<String> zzbGY = Arrays.asList("detail", "checkout", "checkout_option", "click", "add", "remove", "purchase", "refund");
    private static final Pattern zzbGZ = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzbHa = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzbHb;
    private static Map<String, String> zzbHc;
    private final DataLayer zzbCT;
    private final Set<String> zzbHd;
    private final zzdj zzbHe;

    public zzdn(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzdj(context));
    }

    zzdn(Context context, DataLayer dataLayer, zzdj zzdj2) {
        super(ID, new String[0]);
        this.zzbCT = dataLayer;
        this.zzbHe = zzdj2;
        this.zzbHd = new HashSet<String>();
        this.zzbHd.add("");
        this.zzbHd.add("0");
        this.zzbHd.add("false");
    }

    private Double zzV(Object object) {
        if (object instanceof String) {
            try {
                object = Double.valueOf((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                String string = String.valueOf(numberFormatException.getMessage());
                string = string.length() != 0 ? "Cannot convert the object to Double: ".concat(string) : new String("Cannot convert the object to Double: ");
                throw new RuntimeException(string);
            }
        }
        if (object instanceof Integer) {
            return ((Integer)object).doubleValue();
        }
        if (object instanceof Double) {
            return (Double)object;
        }
        object = (object = String.valueOf(object.toString())).length() != 0 ? "Cannot convert the object to Double: ".concat((String)object) : new String("Cannot convert the object to Double: ");
        throw new RuntimeException((String)object);
    }

    private Integer zzW(Object object) {
        if (object instanceof String) {
            try {
                object = Integer.valueOf((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                String string = String.valueOf(numberFormatException.getMessage());
                string = string.length() != 0 ? "Cannot convert the object to Integer: ".concat(string) : new String("Cannot convert the object to Integer: ");
                throw new RuntimeException(string);
            }
        }
        if (object instanceof Double) {
            return ((Double)object).intValue();
        }
        if (object instanceof Integer) {
            return (Integer)object;
        }
        object = (object = String.valueOf(object.toString())).length() != 0 ? "Cannot convert the object to Integer: ".concat((String)object) : new String("Cannot convert the object to Integer: ");
        throw new RuntimeException((String)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zza(Tracker tracker, Map<String, zzaj.zza> iterator) {
        String string = this.zzhE("transactionId");
        if (string == null) {
            zzbo.e("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList<Map<String, String>> linkedList = new LinkedList<Map<String, String>>();
        try {
            Iterator iterator2 = this.zzk((zzaj.zza)iterator.get(zzbGU));
            iterator2.put((String)"&t", (String)"transaction");
            for (Map.Entry<String, String> entry : this.zzak((Map<String, zzaj.zza>)((Object)iterator)).entrySet()) {
                this.zze((Map<String, String>)((Object)iterator2), entry.getValue(), this.zzhE(entry.getKey()));
            }
            linkedList.add((Map<String, String>)((Object)iterator2));
            iterator2 = this.zzhF("transactionProducts");
            if (iterator2 != null) {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    Map map = (Map)iterator2.next();
                    if (map.get("name") == null) {
                        zzbo.e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map<String, String> map2 = this.zzk((zzaj.zza)iterator.get(zzbGU));
                    map2.put("&t", "item");
                    map2.put("&ti", string);
                    for (Map.Entry<String, String> entry : this.zzal((Map<String, zzaj.zza>)((Object)iterator)).entrySet()) {
                        this.zze(map2, entry.getValue(), (String)map.get(entry.getKey()));
                    }
                    linkedList.add(map2);
                }
            }
            iterator = linkedList.iterator();
            do {
                if (!iterator.hasNext()) {
                    return;
                }
                tracker.send((Map)iterator.next());
            } while (true);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            zzbo.zzb("Unable to send transaction", illegalArgumentException);
            return;
        }
    }

    private Promotion zzai(Map<String, String> object) {
        Promotion promotion = new Promotion();
        String string = object.get("id");
        if (string != null) {
            promotion.setId(String.valueOf(string));
        }
        if ((string = object.get("name")) != null) {
            promotion.setName(String.valueOf(string));
        }
        if ((string = object.get("creative")) != null) {
            promotion.setCreative(String.valueOf(string));
        }
        if ((object = object.get("position")) != null) {
            promotion.setPosition(String.valueOf(object));
        }
        return promotion;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Product zzaj(Map<String, Object> var1_1) {
        var5_2 = new Product();
        var3_3 = var1_1.get("id");
        if (var3_3 != null) {
            var5_2.setId(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("name")) != null) {
            var5_2.setName(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("brand")) != null) {
            var5_2.setBrand(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("category")) != null) {
            var5_2.setCategory(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("variant")) != null) {
            var5_2.setVariant(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("coupon")) != null) {
            var5_2.setCouponCode(String.valueOf(var3_3));
        }
        if ((var3_3 = var1_1.get("position")) != null) {
            var5_2.setPosition(this.zzW(var3_3));
        }
        if ((var3_3 = var1_1.get("price")) != null) {
            var5_2.setPrice(this.zzV(var3_3));
        }
        if ((var3_3 = var1_1.get("quantity")) != null) {
            var5_2.setQuantity(this.zzW(var3_3));
        }
        var6_4 = var1_1.keySet().iterator();
        while (var6_4.hasNext() != false) {
            block17 : {
                var3_3 = var6_4.next();
                var4_6 = zzdn.zzbGZ.matcher((CharSequence)var3_3);
                if (var4_6.matches()) {
                    var2_5 = Integer.parseInt(var4_6.group(1));
                    var5_2.setCustomDimension(var2_5, String.valueOf(var1_1.get(var3_3)));
                    continue;
                }
                var4_6 = zzdn.zzbHa.matcher((CharSequence)var3_3);
                if (!var4_6.matches()) continue;
                try {
                    var2_5 = Integer.parseInt(var4_6.group(1));
                }
                catch (NumberFormatException var4_8) {}
                var5_2.setCustomMetric(var2_5, this.zzW(var1_1.get(var3_3)));
                continue;
                catch (NumberFormatException var4_7) {}
                var4_6 = "illegal number in custom dimension value: ";
                var3_3 = String.valueOf(var3_3);
                if (var3_3.length() != 0) ** GOTO lbl-1000
                var3_3 = new String("illegal number in custom dimension value: ");
                break block17;
                var4_6 = "illegal number in custom metric value: ";
                var3_3 = String.valueOf(var3_3);
                if (var3_3.length() != 0) lbl-1000: // 2 sources:
                {
                    var3_3 = var4_6.concat((String)var3_3);
                } else {
                    var3_3 = new String("illegal number in custom metric value: ");
                }
            }
            zzbo.zzbe((String)var3_3);
        }
        return var5_2;
    }

    private Map<String, String> zzak(Map<String, zzaj.zza> hashMap) {
        if ((hashMap = hashMap.get(zzbGW)) != null) {
            return this.zzc((zzaj.zza)((Object)hashMap));
        }
        if (zzbHb == null) {
            hashMap = new HashMap<String, String>();
            hashMap.put("transactionId", "&ti");
            hashMap.put("transactionAffiliation", "&ta");
            hashMap.put("transactionTax", "&tt");
            hashMap.put("transactionShipping", "&ts");
            hashMap.put("transactionTotal", "&tr");
            hashMap.put("transactionCurrency", "&cu");
            zzbHb = hashMap;
        }
        return zzbHb;
    }

    private Map<String, String> zzal(Map<String, zzaj.zza> hashMap) {
        if ((hashMap = hashMap.get(zzbGX)) != null) {
            return this.zzc((zzaj.zza)((Object)hashMap));
        }
        if (zzbHc == null) {
            hashMap = new HashMap<String, String>();
            hashMap.put("name", "&in");
            hashMap.put("sku", "&ic");
            hashMap.put("category", "&iv");
            hashMap.put("price", "&ip");
            hashMap.put("quantity", "&iq");
            hashMap.put("currency", "&cu");
            zzbHc = hashMap;
        }
        return zzbHc;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzb(Tracker tracker, Map<String, zzaj.zza> object) {
        HitBuilders.ScreenViewBuilder screenViewBuilder;
        block23 : {
            Iterator iterator;
            Object object22;
            Object object3;
            boolean bl;
            block26 : {
                block25 : {
                    block24 : {
                        screenViewBuilder = new HitBuilders.ScreenViewBuilder();
                        object22 = this.zzk((zzaj.zza)object.get(zzbGU));
                        screenViewBuilder.setAll((Map<String, String>)object22);
                        boolean bl2 = this.zzi((Map<String, zzaj.zza>)object, zzbGS);
                        object3 = null;
                        object = (bl2 ? (object = this.zzbCT.get("ecommerce")) instanceof Map : (object = zzdm.zzj((zzaj.zza)object.get(zzbGT))) instanceof Map) ? (Map)object : null;
                        if (object == null) break block23;
                        iterator = object22.get("&cu");
                        object22 = iterator;
                        if (iterator == null) {
                            object22 = (String)object.get("currencyCode");
                        }
                        if (object22 != null) {
                            screenViewBuilder.set("&cu", (String)object22);
                        }
                        if ((object22 = object.get("impressions")) instanceof List) {
                            for (Object object22 : (List)object22) {
                                try {
                                    screenViewBuilder.addImpression(this.zzaj((Map<String, Object>)object22), (String)object22.get("list"));
                                }
                                catch (RuntimeException runtimeException) {
                                    object22 = String.valueOf(runtimeException.getMessage());
                                    object22 = object22.length() != 0 ? "Failed to extract a product from DataLayer. ".concat((String)object22) : new String("Failed to extract a product from DataLayer. ");
                                    zzbo.e((String)object22);
                                }
                            }
                        }
                        if (!object.containsKey("promoClick")) break block24;
                        object22 = "promoClick";
                        break block25;
                    }
                    object22 = object3;
                    if (!object.containsKey("promoView")) break block26;
                    object22 = "promoView";
                }
                object22 = (List)((Map)object.get(object22)).get("promotions");
            }
            boolean bl3 = bl = true;
            if (object22 != null) {
                object3 = object22.iterator();
                while (object3.hasNext()) {
                    object22 = (Map)object3.next();
                    try {
                        screenViewBuilder.addPromotion(this.zzai((Map<String, String>)object22));
                    }
                    catch (RuntimeException runtimeException) {
                        object22 = String.valueOf(runtimeException.getMessage());
                        object22 = object22.length() != 0 ? "Failed to extract a promotion from DataLayer. ".concat((String)object22) : new String("Failed to extract a promotion from DataLayer. ");
                        zzbo.e((String)object22);
                    }
                }
                if (object.containsKey("promoClick")) {
                    screenViewBuilder.set("&promoa", "click");
                    bl3 = false;
                } else {
                    screenViewBuilder.set("&promoa", "view");
                    bl3 = bl;
                }
            }
            if (bl3) {
                object3 = zzbGY.iterator();
                while (object3.hasNext()) {
                    object22 = (String)object3.next();
                    if (!object.containsKey(object22)) continue;
                    object3 = (Map)object.get(object22);
                    object = (List)object3.get("products");
                    if (object != null) {
                        iterator = object.iterator();
                        while (iterator.hasNext()) {
                            object = (Map)iterator.next();
                            try {
                                screenViewBuilder.addProduct(this.zzaj((Map<String, Object>)object));
                            }
                            catch (RuntimeException runtimeException) {
                                object = String.valueOf(runtimeException.getMessage());
                                object = object.length() != 0 ? "Failed to extract a product from DataLayer. ".concat((String)object) : new String("Failed to extract a product from DataLayer. ");
                                zzbo.e((String)object);
                            }
                        }
                    }
                    try {
                        object = object3.containsKey("actionField") ? this.zzh((String)object22, (Map)object3.get("actionField")) : new ProductAction((String)object22);
                        screenViewBuilder.setProductAction((ProductAction)object);
                        break;
                    }
                    catch (RuntimeException runtimeException) {
                        String string = String.valueOf(runtimeException.getMessage());
                        string = string.length() != 0 ? "Failed to extract a product action from DataLayer. ".concat(string) : new String("Failed to extract a product action from DataLayer. ");
                        zzbo.e(string);
                        break;
                    }
                }
            }
        }
        tracker.send(screenViewBuilder.build());
    }

    private Map<String, String> zzc(zzaj.zza linkedHashMap) {
        if (!((linkedHashMap = zzdm.zzj(linkedHashMap)) instanceof Map)) {
            return null;
        }
        Map map = linkedHashMap;
        linkedHashMap = new LinkedHashMap<String, String>();
        for (Map.Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private void zze(Map<String, String> map, String string, String string2) {
        if (string2 != null) {
            map.put(string, string2);
        }
    }

    private ProductAction zzh(String object, Map<String, Object> object2) {
        object = new ProductAction((String)object);
        Object object3 = object2.get("id");
        if (object3 != null) {
            object.setTransactionId(String.valueOf(object3));
        }
        if ((object3 = object2.get("affiliation")) != null) {
            object.setTransactionAffiliation(String.valueOf(object3));
        }
        if ((object3 = object2.get("coupon")) != null) {
            object.setTransactionCouponCode(String.valueOf(object3));
        }
        if ((object3 = object2.get("list")) != null) {
            object.setProductActionList(String.valueOf(object3));
        }
        if ((object3 = object2.get("option")) != null) {
            object.setCheckoutOptions(String.valueOf(object3));
        }
        if ((object3 = object2.get("revenue")) != null) {
            object.setTransactionRevenue(this.zzV(object3));
        }
        if ((object3 = object2.get("tax")) != null) {
            object.setTransactionTax(this.zzV(object3));
        }
        if ((object3 = object2.get("shipping")) != null) {
            object.setTransactionShipping(this.zzV(object3));
        }
        if ((object2 = object2.get("step")) != null) {
            object.setCheckoutStep(this.zzW(object2));
        }
        return object;
    }

    private String zzhE(String object) {
        if ((object = this.zzbCT.get((String)object)) == null) {
            return null;
        }
        return object.toString();
    }

    private List<Map<String, String>> zzhF(String object) {
        if ((object = this.zzbCT.get((String)object)) == null) {
            return null;
        }
        if (!(object instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        object = (List)object;
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof Map) continue;
            throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
        }
        return object;
    }

    private boolean zzi(Map<String, zzaj.zza> object, String string) {
        if ((object = object.get(string)) == null) {
            return false;
        }
        return zzdm.zzi((zzaj.zza)object);
    }

    private Map<String, String> zzk(zzaj.zza object) {
        if (object == null) {
            return new HashMap<String, String>();
        }
        if ((object = this.zzc((zzaj.zza)object)) == null) {
            return new HashMap<String, String>();
        }
        String string = (String)object.get("&aip");
        if (string != null && this.zzbHd.contains(string.toLowerCase())) {
            object.remove("&aip");
        }
        return object;
    }

    @Override
    public void zzaa(Map<String, zzaj.zza> map) {
        Tracker tracker = this.zzbHe.zzhw("_GTM_DEFAULT_TRACKER_");
        tracker.enableAdvertisingIdCollection(this.zzi(map, "collect_adid"));
        if (this.zzi(map, zzbGR)) {
            this.zzb(tracker, map);
            return;
        }
        if (this.zzi(map, zzbGQ)) {
            tracker.send(this.zzk(map.get(zzbGU)));
            return;
        }
        if (this.zzi(map, zzbGV)) {
            this.zza(tracker, map);
            return;
        }
        zzbo.zzbe("Ignoring unknown tag.");
    }
}
