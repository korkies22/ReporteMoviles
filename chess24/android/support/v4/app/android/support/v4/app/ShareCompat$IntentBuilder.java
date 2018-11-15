/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Parcelable
 *  android.text.Html
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import java.util.ArrayList;

public static class ShareCompat.IntentBuilder {
    private Activity mActivity;
    private ArrayList<String> mBccAddresses;
    private ArrayList<String> mCcAddresses;
    private CharSequence mChooserTitle;
    private Intent mIntent;
    private ArrayList<Uri> mStreams;
    private ArrayList<String> mToAddresses;

    private ShareCompat.IntentBuilder(Activity activity) {
        this.mActivity = activity;
        this.mIntent = new Intent().setAction("android.intent.action.SEND");
        this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, activity.getPackageName());
        this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, (Parcelable)activity.getComponentName());
        this.mIntent.addFlags(524288);
    }

    private void combineArrayExtra(String string, ArrayList<String> arrayList) {
        String[] arrstring = this.mIntent.getStringArrayExtra(string);
        int n = arrstring != null ? arrstring.length : 0;
        String[] arrstring2 = new String[arrayList.size() + n];
        arrayList.toArray(arrstring2);
        if (arrstring != null) {
            System.arraycopy(arrstring, 0, arrstring2, arrayList.size(), n);
        }
        this.mIntent.putExtra(string, arrstring2);
    }

    private void combineArrayExtra(String string, String[] arrstring) {
        Intent intent = this.getIntent();
        String[] arrstring2 = intent.getStringArrayExtra(string);
        int n = arrstring2 != null ? arrstring2.length : 0;
        String[] arrstring3 = new String[arrstring.length + n];
        if (arrstring2 != null) {
            System.arraycopy(arrstring2, 0, arrstring3, 0, n);
        }
        System.arraycopy(arrstring, 0, arrstring3, n, arrstring.length);
        intent.putExtra(string, arrstring3);
    }

    public static ShareCompat.IntentBuilder from(Activity activity) {
        return new ShareCompat.IntentBuilder(activity);
    }

    public ShareCompat.IntentBuilder addEmailBcc(String string) {
        if (this.mBccAddresses == null) {
            this.mBccAddresses = new ArrayList();
        }
        this.mBccAddresses.add(string);
        return this;
    }

    public ShareCompat.IntentBuilder addEmailBcc(String[] arrstring) {
        this.combineArrayExtra("android.intent.extra.BCC", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder addEmailCc(String string) {
        if (this.mCcAddresses == null) {
            this.mCcAddresses = new ArrayList();
        }
        this.mCcAddresses.add(string);
        return this;
    }

    public ShareCompat.IntentBuilder addEmailCc(String[] arrstring) {
        this.combineArrayExtra("android.intent.extra.CC", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder addEmailTo(String string) {
        if (this.mToAddresses == null) {
            this.mToAddresses = new ArrayList();
        }
        this.mToAddresses.add(string);
        return this;
    }

    public ShareCompat.IntentBuilder addEmailTo(String[] arrstring) {
        this.combineArrayExtra("android.intent.extra.EMAIL", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder addStream(Uri uri) {
        Uri uri2 = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        if (this.mStreams == null && uri2 == null) {
            return this.setStream(uri);
        }
        if (this.mStreams == null) {
            this.mStreams = new ArrayList();
        }
        if (uri2 != null) {
            this.mIntent.removeExtra("android.intent.extra.STREAM");
            this.mStreams.add(uri2);
        }
        this.mStreams.add(uri);
        return this;
    }

    public Intent createChooserIntent() {
        return Intent.createChooser((Intent)this.getIntent(), (CharSequence)this.mChooserTitle);
    }

    Activity getActivity() {
        return this.mActivity;
    }

    public Intent getIntent() {
        if (this.mToAddresses != null) {
            this.combineArrayExtra("android.intent.extra.EMAIL", this.mToAddresses);
            this.mToAddresses = null;
        }
        if (this.mCcAddresses != null) {
            this.combineArrayExtra("android.intent.extra.CC", this.mCcAddresses);
            this.mCcAddresses = null;
        }
        if (this.mBccAddresses != null) {
            this.combineArrayExtra("android.intent.extra.BCC", this.mBccAddresses);
            this.mBccAddresses = null;
        }
        ArrayList<Uri> arrayList = this.mStreams;
        boolean bl = true;
        if (arrayList == null || this.mStreams.size() <= 1) {
            bl = false;
        }
        boolean bl2 = this.mIntent.getAction().equals("android.intent.action.SEND_MULTIPLE");
        if (!bl && bl2) {
            this.mIntent.setAction("android.intent.action.SEND");
            if (this.mStreams != null && !this.mStreams.isEmpty()) {
                this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
            } else {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
            }
            this.mStreams = null;
        }
        if (bl && !bl2) {
            this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
            if (this.mStreams != null && !this.mStreams.isEmpty()) {
                this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
            } else {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
            }
        }
        return this.mIntent;
    }

    public ShareCompat.IntentBuilder setChooserTitle(@StringRes int n) {
        return this.setChooserTitle(this.mActivity.getText(n));
    }

    public ShareCompat.IntentBuilder setChooserTitle(CharSequence charSequence) {
        this.mChooserTitle = charSequence;
        return this;
    }

    public ShareCompat.IntentBuilder setEmailBcc(String[] arrstring) {
        this.mIntent.putExtra("android.intent.extra.BCC", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder setEmailCc(String[] arrstring) {
        this.mIntent.putExtra("android.intent.extra.CC", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder setEmailTo(String[] arrstring) {
        if (this.mToAddresses != null) {
            this.mToAddresses = null;
        }
        this.mIntent.putExtra("android.intent.extra.EMAIL", arrstring);
        return this;
    }

    public ShareCompat.IntentBuilder setHtmlText(String string) {
        this.mIntent.putExtra("android.intent.extra.HTML_TEXT", string);
        if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
            this.setText((CharSequence)Html.fromHtml((String)string));
        }
        return this;
    }

    public ShareCompat.IntentBuilder setStream(Uri uri) {
        if (!this.mIntent.getAction().equals("android.intent.action.SEND")) {
            this.mIntent.setAction("android.intent.action.SEND");
        }
        this.mStreams = null;
        this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)uri);
        return this;
    }

    public ShareCompat.IntentBuilder setSubject(String string) {
        this.mIntent.putExtra("android.intent.extra.SUBJECT", string);
        return this;
    }

    public ShareCompat.IntentBuilder setText(CharSequence charSequence) {
        this.mIntent.putExtra("android.intent.extra.TEXT", charSequence);
        return this;
    }

    public ShareCompat.IntentBuilder setType(String string) {
        this.mIntent.setType(string);
        return this;
    }

    public void startChooser() {
        this.mActivity.startActivity(this.createChooserIntent());
    }
}
