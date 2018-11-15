/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.os;

import android.os.Build;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.Size;
import android.support.v4.os.LocaleHelper;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

@RequiresApi(value=14)
@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
final class LocaleListHelper {
    private static final Locale EN_LATN;
    private static final Locale LOCALE_AR_XB;
    private static final Locale LOCALE_EN_XA;
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final String STRING_AR_XB = "ar-XB";
    private static final String STRING_EN_XA = "en-XA";
    @GuardedBy(value="sLock")
    private static LocaleListHelper sDefaultAdjustedLocaleList;
    @GuardedBy(value="sLock")
    private static LocaleListHelper sDefaultLocaleList;
    private static final Locale[] sEmptyList;
    private static final LocaleListHelper sEmptyLocaleList;
    @GuardedBy(value="sLock")
    private static Locale sLastDefaultLocale;
    @GuardedBy(value="sLock")
    private static LocaleListHelper sLastExplicitlySetLocaleList;
    private static final Object sLock;
    private final Locale[] mList;
    @NonNull
    private final String mStringRepresentation;

    static {
        sEmptyList = new Locale[0];
        sEmptyLocaleList = new LocaleListHelper(new Locale[0]);
        LOCALE_EN_XA = new Locale("en", "XA");
        LOCALE_AR_XB = new Locale("ar", "XB");
        EN_LATN = LocaleHelper.forLanguageTag("en-Latn");
        sLock = new Object();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    LocaleListHelper(@NonNull Locale serializable, LocaleListHelper localeListHelper) {
        int n;
        int n2;
        block9 : {
            if (serializable == null) {
                throw new NullPointerException("topLocale is null");
            }
            int n3 = 0;
            n = localeListHelper == null ? 0 : localeListHelper.mList.length;
            for (n2 = 0; n2 < n; ++n2) {
                if (!serializable.equals(localeListHelper.mList[n2])) {
                    continue;
                }
                break block9;
            }
            n2 = -1;
        }
        int n4 = n2 == -1 ? 1 : 0;
        int n5 = n4 + n;
        Locale[] arrlocale = new Locale[n5];
        arrlocale[0] = (Locale)serializable.clone();
        if (n2 == -1) {
            n2 = 0;
            while (n2 < n) {
                n4 = n2 + 1;
                arrlocale[n4] = (Locale)localeListHelper.mList[n2].clone();
                n2 = n4;
            }
        } else {
            n4 = 0;
            while (n4 < n2) {
                int n6 = n4 + 1;
                arrlocale[n6] = (Locale)localeListHelper.mList[n4].clone();
                n4 = n6;
            }
            ++n2;
            while (n2 < n) {
                arrlocale[n2] = (Locale)localeListHelper.mList[n2].clone();
                ++n2;
            }
        }
        serializable = new StringBuilder();
        for (n2 = n3; n2 < n5; ++n2) {
            serializable.append(LocaleHelper.toLanguageTag(arrlocale[n2]));
            if (n2 >= n5 - 1) continue;
            serializable.append(',');
        }
        this.mList = arrlocale;
        this.mStringRepresentation = serializable.toString();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    /* varargs */ LocaleListHelper(@NonNull Locale ... object) {
        if (((Locale[])object).length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        Locale[] arrlocale = new Locale[((Locale[])object).length];
        HashSet<Locale> hashSet = new HashSet<Locale>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ((Locale[])object).length; ++i) {
            Locale locale = object[i];
            if (locale == null) {
                object = new StringBuilder();
                object.append("list[");
                object.append(i);
                object.append("] is null");
                throw new NullPointerException(object.toString());
            }
            if (hashSet.contains(locale)) {
                object = new StringBuilder();
                object.append("list[");
                object.append(i);
                object.append("] is a repetition");
                throw new IllegalArgumentException(object.toString());
            }
            arrlocale[i] = locale = (Locale)locale.clone();
            stringBuilder.append(LocaleHelper.toLanguageTag(locale));
            if (i < ((Object)object).length - 1) {
                stringBuilder.append(',');
            }
            hashSet.add(locale);
        }
        this.mList = arrlocale;
        this.mStringRepresentation = stringBuilder.toString();
    }

    private Locale computeFirstMatch(Collection<String> collection, boolean bl) {
        int n = this.computeFirstMatchIndex(collection, bl);
        if (n == -1) {
            return null;
        }
        return this.mList[n];
    }

    private int computeFirstMatchIndex(Collection<String> object, boolean bl) {
        int n;
        block10 : {
            block9 : {
                if (this.mList.length == 1) {
                    return 0;
                }
                if (this.mList.length == 0) {
                    return -1;
                }
                if (!bl) break block9;
                n = this.findFirstMatchIndex(EN_LATN);
                if (n == 0) {
                    return 0;
                }
                if (n < Integer.MAX_VALUE) break block10;
            }
            n = Integer.MAX_VALUE;
        }
        object = object.iterator();
        while (object.hasNext()) {
            int n2 = this.findFirstMatchIndex(LocaleHelper.forLanguageTag((String)object.next()));
            if (n2 == 0) {
                return 0;
            }
            if (n2 >= n) continue;
            n = n2;
        }
        if (n == Integer.MAX_VALUE) {
            return 0;
        }
        return n;
    }

    private int findFirstMatchIndex(Locale locale) {
        for (int i = 0; i < this.mList.length; ++i) {
            if (LocaleListHelper.matchScore(locale, this.mList[i]) <= 0) continue;
            return i;
        }
        return Integer.MAX_VALUE;
    }

    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static LocaleListHelper forLanguageTags(@Nullable String arrstring) {
        if (arrstring != null && !arrstring.isEmpty()) {
            arrstring = arrstring.split(",");
            Locale[] arrlocale = new Locale[arrstring.length];
            for (int i = 0; i < arrlocale.length; ++i) {
                arrlocale[i] = LocaleHelper.forLanguageTag(arrstring[i]);
            }
            return new LocaleListHelper(arrlocale);
        }
        return LocaleListHelper.getEmptyLocaleList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    @Size(min=1L)
    static LocaleListHelper getAdjustedDefault() {
        LocaleListHelper.getDefault();
        Object object = sLock;
        synchronized (object) {
            return sDefaultAdjustedLocaleList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Size(min=1L)
    static LocaleListHelper getDefault() {
        Locale locale = Locale.getDefault();
        Object object = sLock;
        synchronized (object) {
            if (locale.equals(sLastDefaultLocale)) return sDefaultLocaleList;
            sLastDefaultLocale = locale;
            if (sDefaultLocaleList != null && locale.equals(sDefaultLocaleList.get(0))) {
                return sDefaultLocaleList;
            }
            sDefaultAdjustedLocaleList = sDefaultLocaleList = new LocaleListHelper(locale, sLastExplicitlySetLocaleList);
            return sDefaultLocaleList;
        }
    }

    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static LocaleListHelper getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    private static String getLikelyScript(Locale object) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (!(object = object.getScript()).isEmpty()) {
                return object;
            }
            return "";
        }
        return "";
    }

    private static boolean isPseudoLocale(String string) {
        if (!STRING_EN_XA.equals(string) && !STRING_AR_XB.equals(string)) {
            return false;
        }
        return true;
    }

    private static boolean isPseudoLocale(Locale locale) {
        if (!LOCALE_EN_XA.equals(locale) && !LOCALE_AR_XB.equals(locale)) {
            return false;
        }
        return true;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static boolean isPseudoLocalesOnly(@Nullable String[] arrstring) {
        if (arrstring == null) {
            return true;
        }
        if (arrstring.length > 3) {
            return false;
        }
        for (String string : arrstring) {
            if (string.isEmpty() || LocaleListHelper.isPseudoLocale(string)) continue;
            return false;
        }
        return true;
    }

    @IntRange(from=0L, to=1L)
    private static int matchScore(Locale locale, Locale locale2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static void setDefault(@NonNull @Size(min=1L) LocaleListHelper localeListHelper) {
        LocaleListHelper.setDefault(localeListHelper, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static void setDefault(@NonNull @Size(min=1L) LocaleListHelper localeListHelper, int n) {
        if (localeListHelper == null) {
            throw new NullPointerException("locales is null");
        }
        if (localeListHelper.isEmpty()) {
            throw new IllegalArgumentException("locales is empty");
        }
        Object object = sLock;
        synchronized (object) {
            sLastDefaultLocale = localeListHelper.get(n);
            Locale.setDefault(sLastDefaultLocale);
            sLastExplicitlySetLocaleList = localeListHelper;
            sDefaultLocaleList = localeListHelper;
            sDefaultAdjustedLocaleList = n == 0 ? sDefaultLocaleList : new LocaleListHelper(sLastDefaultLocale, sDefaultLocaleList);
            return;
        }
    }

    public boolean equals(Object arrlocale) {
        if (arrlocale == this) {
            return true;
        }
        if (!(arrlocale instanceof LocaleListHelper)) {
            return false;
        }
        arrlocale = ((LocaleListHelper)arrlocale).mList;
        if (this.mList.length != arrlocale.length) {
            return false;
        }
        for (int i = 0; i < this.mList.length; ++i) {
            if (this.mList[i].equals(arrlocale[i])) continue;
            return false;
        }
        return true;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    Locale get(int n) {
        if (n >= 0 && n < this.mList.length) {
            return this.mList[n];
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    Locale getFirstMatch(String[] arrstring) {
        return this.computeFirstMatch(Arrays.asList(arrstring), false);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    int getFirstMatchIndex(String[] arrstring) {
        return this.computeFirstMatchIndex(Arrays.asList(arrstring), false);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    int getFirstMatchIndexWithEnglishSupported(Collection<String> collection) {
        return this.computeFirstMatchIndex(collection, true);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    int getFirstMatchIndexWithEnglishSupported(String[] arrstring) {
        return this.getFirstMatchIndexWithEnglishSupported(Arrays.asList(arrstring));
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    Locale getFirstMatchWithEnglishSupported(String[] arrstring) {
        return this.computeFirstMatch(Arrays.asList(arrstring), true);
    }

    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.mList.length; ++i) {
            n = this.mList[i].hashCode() + 31 * n;
        }
        return n;
    }

    @IntRange(from=-1L)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    int indexOf(Locale locale) {
        for (int i = 0; i < this.mList.length; ++i) {
            if (!this.mList[i].equals(locale)) continue;
            return i;
        }
        return -1;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    boolean isEmpty() {
        if (this.mList.length == 0) {
            return true;
        }
        return false;
    }

    @IntRange(from=0L)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    int size() {
        return this.mList.length;
    }

    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < this.mList.length; ++i) {
            stringBuilder.append(this.mList[i]);
            if (i >= this.mList.length - 1) continue;
            stringBuilder.append(',');
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
