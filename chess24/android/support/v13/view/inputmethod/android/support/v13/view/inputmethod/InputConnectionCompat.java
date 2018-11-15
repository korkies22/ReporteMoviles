/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ClipDescription
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 *  android.view.inputmethod.InputContentInfo
 */
package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    public static int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo bundle, @NonNull InputContentInfoCompat inputContentInfoCompat, int n, @Nullable Bundle bundle2) {
        int n2;
        block3 : {
            ClipDescription clipDescription = inputContentInfoCompat.getDescription();
            bundle = EditorInfoCompat.getContentMimeTypes((EditorInfo)bundle);
            int n3 = ((String[])bundle).length;
            for (n2 = 0; n2 < n3; ++n2) {
                if (!clipDescription.hasMimeType(bundle[n2])) continue;
                n2 = 1;
                break block3;
            }
            n2 = 0;
        }
        if (n2 == 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo)inputContentInfoCompat.unwrap(), n, bundle2);
        }
        bundle = new Bundle();
        bundle.putParcelable(COMMIT_CONTENT_CONTENT_URI_KEY, (Parcelable)inputContentInfoCompat.getContentUri());
        bundle.putParcelable(COMMIT_CONTENT_DESCRIPTION_KEY, (Parcelable)inputContentInfoCompat.getDescription());
        bundle.putParcelable(COMMIT_CONTENT_LINK_URI_KEY, (Parcelable)inputContentInfoCompat.getLinkUri());
        bundle.putInt(COMMIT_CONTENT_FLAGS_KEY, n);
        bundle.putParcelable(COMMIT_CONTENT_OPTS_KEY, (Parcelable)bundle2);
        return inputConnection.performPrivateCommand(COMMIT_CONTENT_ACTION, bundle);
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, final @NonNull OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        }
        if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        }
        if (onCommitContentListener == null) {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection, false){

                public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
                    if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap((Object)inputContentInfo), n, bundle)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, n, bundle);
                }
            };
        }
        if (EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0) {
            return inputConnection;
        }
        return new InputConnectionWrapper(inputConnection, false){

            public boolean performPrivateCommand(String string, Bundle bundle) {
                if (InputConnectionCompat.handlePerformPrivateCommand(string, bundle, onCommitContentListener)) {
                    return true;
                }
                return super.performPrivateCommand(string, bundle);
            }
        };
    }

    static boolean handlePerformPrivateCommand(@Nullable String string, @NonNull Bundle bundle, @NonNull OnCommitContentListener onCommitContentListener) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:806)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public static interface OnCommitContentListener {
        public boolean onCommitContent(InputContentInfoCompat var1, int var2, Bundle var3);
    }

}
