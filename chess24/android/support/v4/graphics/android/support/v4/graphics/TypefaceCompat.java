/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.os.Handler
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.TypefaceCompatApi21Impl;
import android.support.v4.graphics.TypefaceCompatApi24Impl;
import android.support.v4.graphics.TypefaceCompatApi26Impl;
import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.LruCache;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompat {
    private static final String TAG = "TypefaceCompat";
    private static final LruCache<String, Typeface> sTypefaceCache;
    private static final TypefaceCompatImpl sTypefaceCompatImpl;

    static {
        sTypefaceCompatImpl = Build.VERSION.SDK_INT >= 26 ? new TypefaceCompatApi26Impl() : (Build.VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable() ? new TypefaceCompatApi24Impl() : (Build.VERSION.SDK_INT >= 21 ? new TypefaceCompatApi21Impl() : new TypefaceCompatBaseImpl()));
        sTypefaceCache = new LruCache(16);
    }

    private TypefaceCompat() {
    }

    @Nullable
    public static Typeface createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, arrfontInfo, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Nullable
    public static Typeface createFromResourcesFamilyXml(@NonNull Context object, @NonNull FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, @NonNull Resources resources, int n, int n2, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean bl) {
        void var0_5;
        void var6_11;
        FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry;
        void var4_9;
        void var5_10;
        void var2_7;
        if (providerResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            void var7_12;
            providerResourceEntry = providerResourceEntry;
            boolean bl2 = false;
            if (var7_12 != false ? providerResourceEntry.getFetchStrategy() == 0 : var5_10 == null) {
                bl2 = true;
            }
            int n3 = var7_12 != false ? providerResourceEntry.getTimeout() : -1;
            Typeface typeface = FontsContractCompat.getFontSync(object, providerResourceEntry.getRequest(), (ResourcesCompat.FontCallback)var5_10, (Handler)var6_11, bl2, n3, (int)var4_9);
        } else {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry2 = providerResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry((Context)object, (FontResourcesParserCompat.FontFamilyFilesResourceEntry)((Object)providerResourceEntry), (Resources)var2_7, (int)var4_9);
            if (var5_10 != null) {
                if (providerResourceEntry != null) {
                    var5_10.callbackSuccessAsync((Typeface)providerResourceEntry, (Handler)var6_11);
                    FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry3 = providerResourceEntry;
                } else {
                    var5_10.callbackFailAsync(-3, (Handler)var6_11);
                    FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry4 = providerResourceEntry;
                }
            }
        }
        if (var0_5 != null) {
            void var3_8;
            sTypefaceCache.put(TypefaceCompat.createResourceUid((Resources)var2_7, (int)var3_8, (int)var4_9), (Typeface)var0_5);
        }
        return var0_5;
    }

    @Nullable
    public static Typeface createFromResourcesFontFile(@NonNull Context context, @NonNull Resources object, int n, String string, int n2) {
        if ((context = sTypefaceCompatImpl.createFromResourcesFontFile(context, (Resources)object, n, string, n2)) != null) {
            object = TypefaceCompat.createResourceUid(object, n, n2);
            sTypefaceCache.put((String)object, (Typeface)context);
        }
        return context;
    }

    private static String createResourceUid(Resources resources, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resources.getResourcePackageName(n));
        stringBuilder.append("-");
        stringBuilder.append(n);
        stringBuilder.append("-");
        stringBuilder.append(n2);
        return stringBuilder.toString();
    }

    @Nullable
    public static Typeface findFromCache(@NonNull Resources resources, int n, int n2) {
        return sTypefaceCache.get(TypefaceCompat.createResourceUid(resources, n, n2));
    }

    static interface TypefaceCompatImpl {
        public Typeface createFromFontFamilyFilesResourceEntry(Context var1, FontResourcesParserCompat.FontFamilyFilesResourceEntry var2, Resources var3, int var4);

        public Typeface createFromFontInfo(Context var1, @Nullable CancellationSignal var2, @NonNull FontsContractCompat.FontInfo[] var3, int var4);

        public Typeface createFromResourcesFontFile(Context var1, Resources var2, int var3, String var4, int var5);
    }

}
