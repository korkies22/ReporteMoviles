/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzc;
import com.google.android.gms.internal.zzsw;
import com.google.android.gms.internal.zztg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HitBuilders {

    @Deprecated
    public static class AppViewBuilder
    extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            this.set("&t", "screenview");
        }
    }

    public static class EventBuilder
    extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            this.set("&t", "event");
        }

        public EventBuilder(String string, String string2) {
            this();
            this.setCategory(string);
            this.setAction(string2);
        }

        public EventBuilder setAction(String string) {
            this.set("&ea", string);
            return this;
        }

        public EventBuilder setCategory(String string) {
            this.set("&ec", string);
            return this;
        }

        public EventBuilder setLabel(String string) {
            this.set("&el", string);
            return this;
        }

        public EventBuilder setValue(long l) {
            this.set("&ev", Long.toString(l));
            return this;
        }
    }

    public static class ExceptionBuilder
    extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            this.set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String string) {
            this.set("&exd", string);
            return this;
        }

        public ExceptionBuilder setFatal(boolean bl) {
            this.set("&exf", zztg.zzW(bl));
            return this;
        }
    }

    protected static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> zzaaM = new HashMap<String, String>();
        ProductAction zzaaN;
        Map<String, List<Product>> zzaaO = new HashMap<String, List<Product>>();
        List<Promotion> zzaaP = new ArrayList<Promotion>();
        List<Product> zzaaQ = new ArrayList<Product>();

        protected HitBuilder() {
        }

        private T zzn(String string, String string2) {
            if (string != null) {
                if (string2 != null) {
                    this.zzaaM.put(string, string2);
                    return (T)this;
                }
            } else {
                zzsw.zzbe("HitBuilder.setIfNonNull() called with a null paramName.");
            }
            return (T)this;
        }

        public T addImpression(Product product, String string) {
            if (product == null) {
                zzsw.zzbe("product should be non-null");
                return (T)this;
            }
            String string2 = string;
            if (string == null) {
                string2 = "";
            }
            if (!this.zzaaO.containsKey(string2)) {
                this.zzaaO.put(string2, new ArrayList());
            }
            this.zzaaO.get(string2).add(product);
            return (T)this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzsw.zzbe("product should be non-null");
                return (T)this;
            }
            this.zzaaQ.add(product);
            return (T)this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzsw.zzbe("promotion should be non-null");
                return (T)this;
            }
            this.zzaaP.add(promotion);
            return (T)this;
        }

        public Map<String, String> build() {
            HashMap<String, String> hashMap = new HashMap<String, String>(this.zzaaM);
            if (this.zzaaN != null) {
                hashMap.putAll(this.zzaaN.build());
            }
            Object object = this.zzaaP.iterator();
            int n = 1;
            while (object.hasNext()) {
                hashMap.putAll(object.next().zzbL(zzc.zzar(n)));
                ++n;
            }
            object = this.zzaaQ.iterator();
            n = 1;
            while (object.hasNext()) {
                hashMap.putAll(((Product)object.next()).zzbL(zzc.zzap(n)));
                ++n;
            }
            Iterator<Map.Entry<String, List<Product>>> iterator = this.zzaaO.entrySet().iterator();
            n = 1;
            while (iterator.hasNext()) {
                Map.Entry<String, List<Product>> entry = iterator.next();
                object = entry.getValue();
                String string = zzc.zzau(n);
                Iterator iterator2 = object.iterator();
                int n2 = 1;
                while (iterator2.hasNext()) {
                    Product product = (Product)iterator2.next();
                    object = String.valueOf(string);
                    String string2 = String.valueOf(zzc.zzat(n2));
                    object = string2.length() != 0 ? object.concat(string2) : new String((String)object);
                    hashMap.putAll(product.zzbL((String)object));
                    ++n2;
                }
                if (!TextUtils.isEmpty((CharSequence)entry.getKey())) {
                    object = String.valueOf(string);
                    string = String.valueOf("nm");
                    object = string.length() != 0 ? object.concat(string) : new String((String)object);
                    hashMap.put((String)object, entry.getKey());
                }
                ++n;
            }
            return hashMap;
        }

        protected String get(String string) {
            return this.zzaaM.get(string);
        }

        public final T set(String string, String string2) {
            if (string != null) {
                this.zzaaM.put(string, string2);
                return (T)this;
            }
            zzsw.zzbe("HitBuilder.set() called with a null paramName.");
            return (T)this;
        }

        public final T setAll(Map<String, String> map) {
            if (map == null) {
                return (T)this;
            }
            this.zzaaM.putAll(new HashMap<String, String>(map));
            return (T)this;
        }

        public T setCampaignParamsFromUrl(String object) {
            if (TextUtils.isEmpty((CharSequence)(object = zztg.zzcf((String)object)))) {
                return (T)this;
            }
            object = zztg.zzcd((String)object);
            this.zzn("&cc", (String)object.get("utm_content"));
            this.zzn("&cm", (String)object.get("utm_medium"));
            this.zzn("&cn", (String)object.get("utm_campaign"));
            this.zzn("&cs", (String)object.get("utm_source"));
            this.zzn("&ck", (String)object.get("utm_term"));
            this.zzn("&ci", (String)object.get("utm_id"));
            this.zzn("&anid", (String)object.get("anid"));
            this.zzn("&gclid", (String)object.get("gclid"));
            this.zzn("&dclid", (String)object.get("dclid"));
            this.zzn("&aclid", (String)object.get("aclid"));
            this.zzn("&gmob_t", (String)object.get("gmob_t"));
            return (T)this;
        }

        public T setCustomDimension(int n, String string) {
            this.set(zzc.zzal(n), string);
            return (T)this;
        }

        public T setCustomMetric(int n, float f) {
            this.set(zzc.zzan(n), Float.toString(f));
            return (T)this;
        }

        protected T setHitType(String string) {
            this.set("&t", string);
            return (T)this;
        }

        public T setNewSession() {
            this.set("&sc", "start");
            return (T)this;
        }

        public T setNonInteraction(boolean bl) {
            this.set("&ni", zztg.zzW(bl));
            return (T)this;
        }

        public T setProductAction(ProductAction productAction) {
            this.zzaaN = productAction;
            return (T)this;
        }

        public T setPromotionAction(String string) {
            this.zzaaM.put("&promoa", string);
            return (T)this;
        }
    }

    @Deprecated
    public static class ItemBuilder
    extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            this.set("&t", "item");
        }

        public ItemBuilder setCategory(String string) {
            this.set("&iv", string);
            return this;
        }

        public ItemBuilder setCurrencyCode(String string) {
            this.set("&cu", string);
            return this;
        }

        public ItemBuilder setName(String string) {
            this.set("&in", string);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            this.set("&ip", Double.toString(d));
            return this;
        }

        public ItemBuilder setQuantity(long l) {
            this.set("&iq", Long.toString(l));
            return this;
        }

        public ItemBuilder setSku(String string) {
            this.set("&ic", string);
            return this;
        }

        public ItemBuilder setTransactionId(String string) {
            this.set("&ti", string);
            return this;
        }
    }

    public static class ScreenViewBuilder
    extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            this.set("&t", "screenview");
        }
    }

    public static class SocialBuilder
    extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            this.set("&t", "social");
        }

        public SocialBuilder setAction(String string) {
            this.set("&sa", string);
            return this;
        }

        public SocialBuilder setNetwork(String string) {
            this.set("&sn", string);
            return this;
        }

        public SocialBuilder setTarget(String string) {
            this.set("&st", string);
            return this;
        }
    }

    public static class TimingBuilder
    extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            this.set("&t", "timing");
        }

        public TimingBuilder(String string, String string2, long l) {
            this();
            this.setVariable(string2);
            this.setValue(l);
            this.setCategory(string);
        }

        public TimingBuilder setCategory(String string) {
            this.set("&utc", string);
            return this;
        }

        public TimingBuilder setLabel(String string) {
            this.set("&utl", string);
            return this;
        }

        public TimingBuilder setValue(long l) {
            this.set("&utt", Long.toString(l));
            return this;
        }

        public TimingBuilder setVariable(String string) {
            this.set("&utv", string);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder
    extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            this.set("&t", "transaction");
        }

        public TransactionBuilder setAffiliation(String string) {
            this.set("&ta", string);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String string) {
            this.set("&cu", string);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            this.set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            this.set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            this.set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTransactionId(String string) {
            this.set("&ti", string);
            return this;
        }
    }

}
