/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.app;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;

public static final class NotificationCompat.MessagingStyle.Message {
    static final String KEY_DATA_MIME_TYPE = "type";
    static final String KEY_DATA_URI = "uri";
    static final String KEY_EXTRAS_BUNDLE = "extras";
    static final String KEY_SENDER = "sender";
    static final String KEY_TEXT = "text";
    static final String KEY_TIMESTAMP = "time";
    private String mDataMimeType;
    private Uri mDataUri;
    private Bundle mExtras = new Bundle();
    private final CharSequence mSender;
    private final CharSequence mText;
    private final long mTimestamp;

    public NotificationCompat.MessagingStyle.Message(CharSequence charSequence, long l, CharSequence charSequence2) {
        this.mText = charSequence;
        this.mTimestamp = l;
        this.mSender = charSequence2;
    }

    static Bundle[] getBundleArrayForMessages(List<NotificationCompat.MessagingStyle.Message> list) {
        Bundle[] arrbundle = new Bundle[list.size()];
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            arrbundle[i] = list.get(i).toBundle();
        }
        return arrbundle;
    }

    static NotificationCompat.MessagingStyle.Message getMessageFromBundle(Bundle bundle) {
        block5 : {
            block6 : {
                try {
                    if (!bundle.containsKey(KEY_TEXT)) break block5;
                    if (bundle.containsKey(KEY_TIMESTAMP)) break block6;
                    return null;
                }
                catch (ClassCastException classCastException) {
                    return null;
                }
            }
            NotificationCompat.MessagingStyle.Message message = new NotificationCompat.MessagingStyle.Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
            if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                message.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri)bundle.getParcelable(KEY_DATA_URI));
            }
            if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
            }
            return message;
        }
        return null;
    }

    static List<NotificationCompat.MessagingStyle.Message> getMessagesFromBundleArray(Parcelable[] arrparcelable) {
        ArrayList<NotificationCompat.MessagingStyle.Message> arrayList = new ArrayList<NotificationCompat.MessagingStyle.Message>(arrparcelable.length);
        for (int i = 0; i < arrparcelable.length; ++i) {
            NotificationCompat.MessagingStyle.Message message;
            if (!(arrparcelable[i] instanceof Bundle) || (message = NotificationCompat.MessagingStyle.Message.getMessageFromBundle((Bundle)arrparcelable[i])) == null) continue;
            arrayList.add(message);
        }
        return arrayList;
    }

    private Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.mText != null) {
            bundle.putCharSequence(KEY_TEXT, this.mText);
        }
        bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
        if (this.mSender != null) {
            bundle.putCharSequence(KEY_SENDER, this.mSender);
        }
        if (this.mDataMimeType != null) {
            bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
        }
        if (this.mDataUri != null) {
            bundle.putParcelable(KEY_DATA_URI, (Parcelable)this.mDataUri);
        }
        if (this.mExtras != null) {
            bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
        }
        return bundle;
    }

    public String getDataMimeType() {
        return this.mDataMimeType;
    }

    public Uri getDataUri() {
        return this.mDataUri;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getSender() {
        return this.mSender;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public NotificationCompat.MessagingStyle.Message setData(String string, Uri uri) {
        this.mDataMimeType = string;
        this.mDataUri = uri;
        return this;
    }
}
