/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzbo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static final Pattern zzbDA;
    static final String[] zzbDz;
    private final ConcurrentHashMap<zzb, Integer> zzbDB;
    private final Map<String, Object> zzbDC;
    private final ReentrantLock zzbDD;
    private final LinkedList<Map<String, Object>> zzbDE;
    private final zzc zzbDF;
    private final CountDownLatch zzbDG;

    static {
        zzbDz = "gtm.lifetime".toString().split("\\.");
        zzbDA = Pattern.compile("(\\d+)\\s*([smhd]?)");
    }

    DataLayer() {
        this(new zzc(){

            @Override
            public void zza(zzc$zza zzc$zza2) {
                zzc$zza2.zzJ(new ArrayList<zza>());
            }

            @Override
            public void zza(List<zza> list, long l) {
            }

            @Override
            public void zzhf(String string) {
            }
        });
    }

    DataLayer(zzc zzc2) {
        this.zzbDF = zzc2;
        this.zzbDB = new ConcurrentHashMap();
        this.zzbDC = new HashMap<String, Object>();
        this.zzbDD = new ReentrantLock();
        this.zzbDE = new LinkedList();
        this.zzbDG = new CountDownLatch(1);
        this.zzOR();
    }

    public static /* varargs */ List<Object> listOf(Object ... arrobject) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i = 0; i < arrobject.length; ++i) {
            arrayList.add(arrobject[i]);
        }
        return arrayList;
    }

    public static /* varargs */ Map<String, Object> mapOf(Object ... object) {
        if (((Object[])object).length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        Serializable serializable = new HashMap<String, Object>();
        for (int i = 0; i < ((Object[])object).length; i += 2) {
            if (!(object[i] instanceof String)) {
                object = String.valueOf(object[i]);
                serializable = new StringBuilder(21 + String.valueOf(object).length());
                serializable.append("key is not a string: ");
                serializable.append((String)object);
                throw new IllegalArgumentException(serializable.toString());
            }
            serializable.put((String)((String)object[i]), (Object)object[i + 1]);
        }
        return serializable;
    }

    private void zzOR() {
        this.zzbDF.zza(new zzc$zza(){

            @Override
            public void zzJ(List<zza> object) {
                object = object.iterator();
                while (object.hasNext()) {
                    zza zza2 = (zza)object.next();
                    DataLayer.this.zzab(DataLayer.this.zzo(zza2.zzAH, zza2.zzYe));
                }
                DataLayer.this.zzbDG.countDown();
            }
        });
    }

    private void zzOS() {
        Map<String, Object> map;
        int n = 0;
        while ((map = this.zzbDE.poll()) != null) {
            int n2;
            this.zzag(map);
            n = n2 = n + 1;
            if (n2 <= 500) continue;
            this.zzbDE.clear();
            throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
        }
    }

    private void zza(Map<String, Object> object, String string, Collection<zza> collection) {
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            object = string.length() == 0 ? "" : ".";
            String string2 = entry.getKey();
            StringBuilder stringBuilder = new StringBuilder(0 + String.valueOf(string).length() + String.valueOf(object).length() + String.valueOf(string2).length());
            stringBuilder.append(string);
            stringBuilder.append((String)object);
            stringBuilder.append(string2);
            object = stringBuilder.toString();
            if (entry.getValue() instanceof Map) {
                this.zza((Map)entry.getValue(), (String)object, collection);
                continue;
            }
            if (object.equals("gtm.lifetime")) continue;
            collection.add(new zza((String)object, entry.getValue()));
        }
    }

    private void zzab(Map<String, Object> map) {
        this.zzbDD.lock();
        try {
            this.zzbDE.offer(map);
            if (this.zzbDD.getHoldCount() == 1) {
                this.zzOS();
            }
            this.zzac(map);
            return;
        }
        finally {
            this.zzbDD.unlock();
        }
    }

    private void zzac(Map<String, Object> object) {
        Long l = this.zzad((Map<String, Object>)object);
        if (l == null) {
            return;
        }
        object = this.zzaf((Map<String, Object>)object);
        object.remove("gtm.lifetime");
        this.zzbDF.zza((List<zza>)object, l);
    }

    private Long zzad(Map<String, Object> object) {
        if ((object = this.zzae((Map<String, Object>)object)) == null) {
            return null;
        }
        return DataLayer.zzhe(object.toString());
    }

    private Object zzae(Map<String, Object> object) {
        for (String string : zzbDz) {
            if (!(object instanceof Map)) {
                return null;
            }
            object = object.get(string);
        }
        return object;
    }

    private List<zza> zzaf(Map<String, Object> map) {
        ArrayList<zza> arrayList = new ArrayList<zza>();
        this.zza(map, "", arrayList);
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzag(Map<String, Object> map) {
        Map<String, Object> map2 = this.zzbDC;
        synchronized (map2) {
            Iterator<String> iterator = map.keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [2, 3, 4] lbl6 : MonitorExitStatement: MONITOREXIT : var2_2
                    this.zzah(map);
                    return;
                }
                String string = iterator.next();
                this.zzd(this.zzo(string, map.get(string)), this.zzbDC);
            } while (true);
        }
    }

    private void zzah(Map<String, Object> map) {
        Iterator iterator = this.zzbDB.keySet().iterator();
        while (iterator.hasNext()) {
            ((zzb)iterator.next()).zzZ(map);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Long zzhe(String string) {
        Matcher matcher;
        String string2;
        long l;
        block10 : {
            matcher = zzbDA.matcher(string);
            if (!matcher.matches()) {
                string = (string = String.valueOf(string)).length() != 0 ? "unknown _lifetime: ".concat(string) : new String("unknown _lifetime: ");
                zzbo.zzbd(string);
                return null;
            }
            try {
                l = Long.parseLong(matcher.group(1));
                break block10;
            }
            catch (NumberFormatException numberFormatException) {}
            string2 = String.valueOf(string);
            string2 = string2.length() != 0 ? "illegal number in _lifetime value: ".concat(string2) : new String("illegal number in _lifetime value: ");
            zzbo.zzbe(string2);
            l = 0L;
        }
        if (l <= 0L) {
            string = (string = String.valueOf(string)).length() != 0 ? "non-positive _lifetime: ".concat(string) : new String("non-positive _lifetime: ");
            zzbo.zzbd(string);
            return null;
        }
        string2 = matcher.group(2);
        if (string2.length() == 0) {
            return l;
        }
        char c = string2.charAt(0);
        if (c == 'd') {
            l = l * 1000L * 60L * 60L * 24L;
            return l;
        }
        if (c != 'h') {
            if (c != 'm') {
                if (c == 's') {
                    l *= 1000L;
                    return l;
                }
                string = (string = String.valueOf(string)).length() != 0 ? "unknown units in _lifetime: ".concat(string) : new String("unknown units in _lifetime: ");
                zzbo.zzbe(string);
                return null;
            }
            l *= 1000L;
            return l *= 60L;
        } else {
            l = l * 1000L * 60L;
        }
        return l *= 60L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object get(String object) {
        Map<String, Object> map = this.zzbDC;
        synchronized (map) {
            Object object2 = this.zzbDC;
            String[] arrstring = object.split("\\.");
            int n = arrstring.length;
            int n2 = 0;
            object = object2;
            while (n2 < n) {
                object2 = arrstring[n2];
                if (!(object instanceof Map)) {
                    return null;
                }
                if ((object = ((Map)object).get(object2)) == null) {
                    return null;
                }
                ++n2;
            }
            return object;
        }
    }

    public void push(String string, Object object) {
        this.push(this.zzo(string, object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void push(Map<String, Object> map) {
        block2 : {
            try {
                this.zzbDG.await();
                break block2;
            }
            catch (InterruptedException interruptedException) {}
            zzbo.zzbe("DataLayer.push: unexpected InterruptedException");
        }
        this.zzab(map);
    }

    public void pushEvent(String string, Map<String, Object> map) {
        map = new HashMap<String, Object>(map);
        map.put(EVENT_KEY, string);
        this.push(map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        Map<String, Object> map = this.zzbDC;
        synchronized (map) {
            CharSequence charSequence = new StringBuilder();
            Iterator<Map.Entry<String, Object>> iterator = this.zzbDC.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                charSequence.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            return ((StringBuilder)charSequence).toString();
        }
    }

    void zza(zzb zzb2) {
        this.zzbDB.put(zzb2, 0);
    }

    void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); ++i) {
            Object object = list.get(i);
            if (object instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                this.zzb((List)object, (List)list2.get(i));
                continue;
            }
            if (object instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                this.zzd((Map)object, (Map)list2.get(i));
                continue;
            }
            if (object == OBJECT_NOT_PRESENT) continue;
            list2.set(i, object);
        }
    }

    void zzd(Map<String, Object> map, Map<String, Object> map2) {
        for (String string : map.keySet()) {
            Object object = map.get(string);
            if (object instanceof List) {
                if (!(map2.get(string) instanceof List)) {
                    map2.put(string, new ArrayList());
                }
                this.zzb((List)object, (List)map2.get(string));
                continue;
            }
            if (object instanceof Map) {
                if (!(map2.get(string) instanceof Map)) {
                    map2.put(string, new HashMap());
                }
                this.zzd((Map)object, (Map)map2.get(string));
                continue;
            }
            map2.put(string, object);
        }
    }

    void zzhd(String string) {
        this.push(string, null);
        this.zzbDF.zzhf(string);
    }

    Map<String, Object> zzo(String hashMap, Object object) {
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        String[] arrstring = ((String)((Object)hashMap)).toString().split("\\.");
        hashMap = hashMap2;
        for (int i = 0; i < arrstring.length - 1; ++i) {
            HashMap<String, HashMap<String, Object>> hashMap3 = new HashMap<String, HashMap<String, Object>>();
            hashMap.put(arrstring[i], hashMap3);
            hashMap = hashMap3;
        }
        hashMap.put(arrstring[arrstring.length - 1], (HashMap<String, Object>)object);
        return hashMap2;
    }

    static final class zza {
        public final String zzAH;
        public final Object zzYe;

        zza(String string, Object object) {
            this.zzAH = string;
            this.zzYe = object;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof zza;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (zza)object;
            bl = bl2;
            if (this.zzAH.equals(object.zzAH)) {
                bl = bl2;
                if (this.zzYe.equals(object.zzYe)) {
                    bl = true;
                }
            }
            return bl;
        }

        public int hashCode() {
            return Arrays.hashCode((Object[])new Integer[]{this.zzAH.hashCode(), this.zzYe.hashCode()});
        }

        public String toString() {
            String string = this.zzAH;
            String string2 = String.valueOf(this.zzYe.toString());
            StringBuilder stringBuilder = new StringBuilder(13 + String.valueOf(string).length() + String.valueOf(string2).length());
            stringBuilder.append("Key: ");
            stringBuilder.append(string);
            stringBuilder.append(" value: ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
    }

    static interface zzb {
        public void zzZ(Map<String, Object> var1);
    }

    static interface zzc {
        public void zza(zzc$zza var1);

        public void zza(List<zza> var1, long var2);

        public void zzhf(String var1);
    }

    public static interface zzc$zza {
        public void zzJ(List<zza> var1);
    }

}
