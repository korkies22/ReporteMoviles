/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.text.Html
 *  android.text.Spanned
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import java.util.ArrayList;

public static class ShareCompat.IntentReader {
    private static final String TAG = "IntentReader";
    private Activity mActivity;
    private ComponentName mCallingActivity;
    private String mCallingPackage;
    private Intent mIntent;
    private ArrayList<Uri> mStreams;

    private ShareCompat.IntentReader(Activity activity) {
        this.mActivity = activity;
        this.mIntent = activity.getIntent();
        this.mCallingPackage = ShareCompat.getCallingPackage(activity);
        this.mCallingActivity = ShareCompat.getCallingActivity(activity);
    }

    public static ShareCompat.IntentReader from(Activity activity) {
        return new ShareCompat.IntentReader(activity);
    }

    private static void withinStyle(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
        while (n < n2) {
            char c = charSequence.charAt(n);
            if (c == '<') {
                stringBuilder.append("&lt;");
            } else if (c == '>') {
                stringBuilder.append("&gt;");
            } else if (c == '&') {
                stringBuilder.append("&amp;");
            } else if (c <= '~' && c >= ' ') {
                if (c == ' ') {
                    int n3;
                    while ((n3 = n + 1) < n2 && charSequence.charAt(n3) == ' ') {
                        stringBuilder.append("&nbsp;");
                        n = n3;
                    }
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append(c);
                }
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("&#");
                stringBuilder2.append((int)c);
                stringBuilder2.append(";");
                stringBuilder.append(stringBuilder2.toString());
            }
            ++n;
        }
    }

    public ComponentName getCallingActivity() {
        return this.mCallingActivity;
    }

    public Drawable getCallingActivityIcon() {
        if (this.mCallingActivity == null) {
            return null;
        }
        PackageManager packageManager = this.mActivity.getPackageManager();
        try {
            packageManager = packageManager.getActivityIcon(this.mCallingActivity);
            return packageManager;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)"Could not retrieve icon for calling activity", (Throwable)nameNotFoundException);
            return null;
        }
    }

    public Drawable getCallingApplicationIcon() {
        if (this.mCallingPackage == null) {
            return null;
        }
        PackageManager packageManager = this.mActivity.getPackageManager();
        try {
            packageManager = packageManager.getApplicationIcon(this.mCallingPackage);
            return packageManager;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)"Could not retrieve icon for calling application", (Throwable)nameNotFoundException);
            return null;
        }
    }

    public CharSequence getCallingApplicationLabel() {
        if (this.mCallingPackage == null) {
            return null;
        }
        Object object = this.mActivity.getPackageManager();
        try {
            object = object.getApplicationLabel(object.getApplicationInfo(this.mCallingPackage, 0));
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)"Could not retrieve label for calling application", (Throwable)nameNotFoundException);
            return null;
        }
    }

    public String getCallingPackage() {
        return this.mCallingPackage;
    }

    public String[] getEmailBcc() {
        return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
    }

    public String[] getEmailCc() {
        return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
    }

    public String[] getEmailTo() {
        return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
    }

    public String getHtmlText() {
        String string = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
        CharSequence charSequence = string;
        if (string == null) {
            CharSequence charSequence2 = this.getText();
            if (charSequence2 instanceof Spanned) {
                return Html.toHtml((Spanned)((Spanned)charSequence2));
            }
            charSequence = string;
            if (charSequence2 != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    return Html.escapeHtml((CharSequence)charSequence2);
                }
                charSequence = new StringBuilder();
                ShareCompat.IntentReader.withinStyle((StringBuilder)charSequence, charSequence2, 0, charSequence2.length());
                charSequence = charSequence.toString();
            }
        }
        return charSequence;
    }

    public Uri getStream() {
        return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
    }

    public Uri getStream(int n) {
        if (this.mStreams == null && this.isMultipleShare()) {
            this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
        }
        if (this.mStreams != null) {
            return this.mStreams.get(n);
        }
        if (n == 0) {
            return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stream items available: ");
        stringBuilder.append(this.getStreamCount());
        stringBuilder.append(" index requested: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getStreamCount() {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public String getSubject() {
        return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
    }

    public CharSequence getText() {
        return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
    }

    public String getType() {
        return this.mIntent.getType();
    }

    public boolean isMultipleShare() {
        return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
    }

    public boolean isShareIntent() {
        String string = this.mIntent.getAction();
        if (!"android.intent.action.SEND".equals(string) && !"android.intent.action.SEND_MULTIPLE".equals(string)) {
            return false;
        }
        return true;
    }

    public boolean isSingleShare() {
        return "android.intent.action.SEND".equals(this.mIntent.getAction());
    }
}
