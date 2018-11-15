/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 */
package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RequiresApi(value=14)
@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatBaseImpl
implements TypefaceCompat.TypefaceCompatImpl {
    private static final String CACHE_FILE_PREFIX = "cached_font_";
    private static final String TAG = "TypefaceCompatBaseImpl";

    TypefaceCompatBaseImpl() {
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, int n) {
        return TypefaceCompatBaseImpl.findBestFont(fontFamilyFilesResourceEntry.getEntries(), n, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>(){

            @Override
            public int getWeight(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.getWeight();
            }

            @Override
            public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.isItalic();
            }
        });
    }

    private static <T> T findBestFont(T[] arrT, int n, StyleExtractor<T> styleExtractor) {
        int n2 = (n & 1) == 0 ? 400 : 700;
        boolean bl = (n & 2) != 0;
        int n3 = arrT.length;
        int n4 = Integer.MAX_VALUE;
        T t = null;
        for (n = 0; n < n3; ++n) {
            int n5;
            block4 : {
                int n6;
                T t2;
                block3 : {
                    t2 = arrT[n];
                    n6 = Math.abs(styleExtractor.getWeight(t2) - n2);
                    n5 = styleExtractor.isItalic(t2) == bl ? 0 : 1;
                    n6 = n6 * 2 + n5;
                    if (t == null) break block3;
                    n5 = n4;
                    if (n4 <= n6) break block4;
                }
                t = t2;
                n5 = n6;
            }
            n4 = n5;
        }
        return t;
    }

    @Nullable
    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry object, Resources resources, int n) {
        if ((object = this.findBestEntry((FontResourcesParserCompat.FontFamilyFilesResourceEntry)object, n)) == null) {
            return null;
        }
        return TypefaceCompat.createFromResourcesFontFile(context, resources, object.getResourceId(), object.getFileName(), n);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal object, @NonNull FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        block8 : {
            void var1_4;
            block7 : {
                Object var5_10 = null;
                if (arrfontInfo.length < 1) {
                    return null;
                }
                object = this.findBestInfo(arrfontInfo, n);
                object = context.getContentResolver().openInputStream(object.getUri());
                try {
                    context = this.createFromInputStream(context, (InputStream)object);
                }
                catch (Throwable throwable) {
                    break block7;
                }
                TypefaceCompatUtil.closeQuietly((Closeable)object);
                return context;
                catch (Throwable throwable) {
                    object = var5_10;
                }
            }
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            throw var1_4;
            catch (IOException iOException) {}
            object = null;
            break block8;
            catch (IOException iOException) {}
        }
        TypefaceCompatUtil.closeQuietly((Closeable)object);
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    protected Typeface createFromInputStream(Context var1_1, InputStream var2_2) {
        block6 : {
            if ((var1_1 = TypefaceCompatUtil.getTempFile((Context)var1_1)) == null) {
                return null;
            }
            var3_5 = TypefaceCompatUtil.copyToFile((File)var1_1, var2_2);
            if (var3_5) break block6;
            var1_1.delete();
            return null;
        }
        try {
            var2_2 = Typeface.createFromFile((String)var1_1.getPath());
        }
        catch (Throwable var2_3) {
            var1_1.delete();
            throw var2_3;
        }
        var1_1.delete();
        return var2_2;
lbl16: // 1 sources:
        do {
            var1_1.delete();
            return null;
            break;
        } while (true);
        catch (RuntimeException var2_4) {
            ** continue;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Nullable
    @Override
    public Typeface createFromResourcesFontFile(Context var1_1, Resources var2_2, int var3_5, String var4_6, int var5_7) {
        block6 : {
            if ((var1_1 = TypefaceCompatUtil.getTempFile((Context)var1_1)) == null) {
                return null;
            }
            var6_8 = TypefaceCompatUtil.copyToFile((File)var1_1, var2_2, var3_5);
            if (var6_8) break block6;
            var1_1.delete();
            return null;
        }
        try {
            var2_2 = Typeface.createFromFile((String)var1_1.getPath());
        }
        catch (Throwable var2_3) {
            var1_1.delete();
            throw var2_3;
        }
        var1_1.delete();
        return var2_2;
lbl16: // 1 sources:
        do {
            var1_1.delete();
            return null;
            break;
        } while (true);
        catch (RuntimeException var2_4) {
            ** continue;
        }
    }

    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        return TypefaceCompatBaseImpl.findBestFont(arrfontInfo, n, new StyleExtractor<FontsContractCompat.FontInfo>(){

            @Override
            public int getWeight(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.getWeight();
            }

            @Override
            public boolean isItalic(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.isItalic();
            }
        });
    }

    private static interface StyleExtractor<T> {
        public int getWeight(T var1);

        public boolean isItalic(T var1);
    }

}
