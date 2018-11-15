/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.fonts.FontVariationAxis
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatApi21Impl;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

@RequiresApi(value=26)
@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatApi26Impl
extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Executable executable;
        Executable executable2;
        Executable executable3;
        Executable executable4;
        Executable executable5;
        Constructor<?> constructor;
        Constructor<?> constructor2;
        Object var8 = null;
        try {
            constructor = Class.forName(FONT_FAMILY_CLASS);
            constructor2 = constructor.getConstructor(new Class[0]);
            executable2 = constructor.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
            executable4 = constructor.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
            executable5 = constructor.getMethod(FREEZE_METHOD, new Class[0]);
            executable = constructor.getMethod(ABORT_CREATION_METHOD, new Class[0]);
            executable3 = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(constructor, 1).getClass(), Integer.TYPE, Integer.TYPE);
            executable3.setAccessible(true);
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            constructor = new StringBuilder();
            constructor.append("Unable to collect necessary methods for class ");
            constructor.append(reflectiveOperationException.getClass().getName());
            Log.e((String)TAG, (String)((StringBuilder)((Object)constructor)).toString(), (Throwable)reflectiveOperationException);
            Object var7_9 = null;
            constructor = constructor2 = var7_9;
            executable3 = constructor;
            executable = executable2 = executable3;
            executable5 = executable2;
            executable4 = executable3;
            executable2 = constructor;
            executable3 = constructor2;
            constructor = var7_9;
            constructor2 = var8;
        }
        sFontFamilyCtor = constructor2;
        sFontFamily = constructor;
        sAddFontFromAssetManager = executable2;
        sAddFontFromBuffer = executable4;
        sFreeze = executable5;
        sAbortCreation = executable;
        sCreateFromFamiliesWithDefault = executable3;
    }

    private static void abortCreation(Object object) {
        try {
            sAbortCreation.invoke(object, new Object[0]);
            return;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object object, String string, int n, int n2, int n3) {
        try {
            boolean bl = (Boolean)sAddFontFromAssetManager.invoke(object, new Object[]{context.getAssets(), string, 0, false, n, n2, n3, null});
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static boolean addFontFromBuffer(Object object, ByteBuffer byteBuffer, int n, int n2, int n3) {
        try {
            boolean bl = (Boolean)sAddFontFromBuffer.invoke(object, byteBuffer, n, null, n2, n3);
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
            object = (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2, -1, -1);
            return object;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static boolean freeze(Object object) {
        try {
            boolean bl = (Boolean)sFreeze.invoke(object, new Object[0]);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w((String)TAG, (String)"Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        if (sAddFontFromAssetManager != null) {
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
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int n) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Nullable
    @Override
    public Typeface createFromResourcesFontFile(Context context, Resources object, int n, String string, int n2) {
        if (!TypefaceCompatApi26Impl.isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, (Resources)object, n, string, n2);
        }
        object = TypefaceCompatApi26Impl.newFamily();
        if (!TypefaceCompatApi26Impl.addFontFromAssetManager(context, object, string, 0, -1, -1)) {
            TypefaceCompatApi26Impl.abortCreation(object);
            return null;
        }
        if (!TypefaceCompatApi26Impl.freeze(object)) {
            return null;
        }
        return TypefaceCompatApi26Impl.createFromFamiliesWithDefault(object);
    }
}
