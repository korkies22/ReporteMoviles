/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package android.support.v4.graphics;

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
import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(value=24)
@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi24Impl
extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi24Impl";
    private static final Method sAddFontWeightStyle;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;

    static {
        GenericDeclaration genericDeclaration;
        GenericDeclaration genericDeclaration2;
        Class<?> class_;
        GenericDeclaration genericDeclaration3;
        Object var4 = null;
        try {
            class_ = Class.forName(FONT_FAMILY_CLASS);
            genericDeclaration2 = class_.getConstructor(new Class[0]);
            genericDeclaration = class_.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE);
            genericDeclaration3 = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(class_, 1).getClass());
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            Log.e((String)TAG, (String)reflectiveOperationException.getClass().getName(), (Throwable)reflectiveOperationException);
            class_ = null;
            genericDeclaration = genericDeclaration2 = class_;
            genericDeclaration3 = genericDeclaration2;
            genericDeclaration2 = var4;
        }
        sFontFamilyCtor = genericDeclaration2;
        sFontFamily = class_;
        sAddFontWeightStyle = genericDeclaration;
        sCreateFromFamiliesWithDefault = genericDeclaration3;
    }

    TypefaceCompatApi24Impl() {
    }

    private static boolean addFontWeightStyle(Object object, ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        try {
            bl = (Boolean)sAddFontWeightStyle.invoke(object, byteBuffer, n, null, n2, bl);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object object) {
        try {
            Object object2 = Array.newInstance(sFontFamily, 1);
            Array.set(object2, 0, object);
            object = (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2);
            return object;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static boolean isUsable() {
        if (sAddFontWeightStyle == null) {
            Log.w((String)TAG, (String)"Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        if (sAddFontWeightStyle != null) {
            return true;
        }
        return false;
    }

    private static Object newFamily() {
        try {
            Object t = sFontFamilyCtor.newInstance(new Object[0]);
            return t;
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry arrfontFileResourceEntry, Resources resources, int n) {
        Object object = TypefaceCompatApi24Impl.newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : arrfontFileResourceEntry.getEntries()) {
            ByteBuffer byteBuffer = TypefaceCompatUtil.copyToDirectBuffer(context, resources, fontFileResourceEntry.getResourceId());
            if (byteBuffer == null) {
                return null;
            }
            if (TypefaceCompatApi24Impl.addFontWeightStyle(object, byteBuffer, 0, fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) continue;
            return null;
        }
        return TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object);
    }

    @Override
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        Object object = TypefaceCompatApi24Impl.newFamily();
        SimpleArrayMap<Uri, ByteBuffer> simpleArrayMap = new SimpleArrayMap<Uri, ByteBuffer>();
        for (FontsContractCompat.FontInfo fontInfo : arrfontInfo) {
            ByteBuffer byteBuffer;
            Uri uri = fontInfo.getUri();
            ByteBuffer byteBuffer2 = byteBuffer = (ByteBuffer)simpleArrayMap.get((Object)uri);
            if (byteBuffer == null) {
                byteBuffer2 = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                simpleArrayMap.put(uri, byteBuffer2);
            }
            if (TypefaceCompatApi24Impl.addFontWeightStyle(object, byteBuffer2, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic())) continue;
            return null;
        }
        return Typeface.create((Typeface)TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object), (int)n);
    }
}
