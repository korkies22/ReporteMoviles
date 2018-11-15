/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$MessagingStyle
 *  android.app.Notification$MessagingStyle$Message
 *  android.content.res.ColorStateList
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 */
package android.support.v4.app;

import android.app.Notification;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import java.util.ArrayList;
import java.util.List;

public static class NotificationCompat.MessagingStyle
extends NotificationCompat.Style {
    public static final int MAXIMUM_RETAINED_MESSAGES = 25;
    CharSequence mConversationTitle;
    List<Message> mMessages = new ArrayList<Message>();
    CharSequence mUserDisplayName;

    NotificationCompat.MessagingStyle() {
    }

    public NotificationCompat.MessagingStyle(@NonNull CharSequence charSequence) {
        this.mUserDisplayName = charSequence;
    }

    public static NotificationCompat.MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
        if ((notification = NotificationCompat.getExtras(notification)) != null && !notification.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
            return null;
        }
        try {
            NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle();
            messagingStyle.restoreFromCompatExtras((Bundle)notification);
            return messagingStyle;
        }
        catch (ClassCastException classCastException) {
            return null;
        }
    }

    @Nullable
    private Message findLatestIncomingMessage() {
        for (int i = this.mMessages.size() - 1; i >= 0; --i) {
            Message message = this.mMessages.get(i);
            if (TextUtils.isEmpty((CharSequence)message.getSender())) continue;
            return message;
        }
        if (!this.mMessages.isEmpty()) {
            return this.mMessages.get(this.mMessages.size() - 1);
        }
        return null;
    }

    private boolean hasMessagesWithoutSender() {
        for (int i = this.mMessages.size() - 1; i >= 0; --i) {
            if (this.mMessages.get(i).getSender() != null) continue;
            return true;
        }
        return false;
    }

    @NonNull
    private TextAppearanceSpan makeFontColorSpan(int n) {
        return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf((int)n), null);
    }

    private CharSequence makeMessageLine(Message object) {
        CharSequence charSequence;
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        boolean bl = Build.VERSION.SDK_INT >= 21;
        int n = bl ? -16777216 : -1;
        CharSequence charSequence2 = object.getSender();
        int n2 = n;
        if (TextUtils.isEmpty((CharSequence)object.getSender())) {
            charSequence = this.mUserDisplayName == null ? "" : this.mUserDisplayName;
            n2 = n;
            charSequence2 = charSequence;
            if (bl) {
                n2 = n;
                charSequence2 = charSequence;
                if (this.mBuilder.getColor() != 0) {
                    n2 = this.mBuilder.getColor();
                    charSequence2 = charSequence;
                }
            }
        }
        charSequence = bidiFormatter.unicodeWrap(charSequence2);
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan((Object)this.makeFontColorSpan(n2), spannableStringBuilder.length() - charSequence.length(), spannableStringBuilder.length(), 33);
        object = object.getText() == null ? "" : object.getText();
        spannableStringBuilder.append((CharSequence)"  ").append(bidiFormatter.unicodeWrap((CharSequence)object));
        return spannableStringBuilder;
    }

    @Override
    public void addCompatExtras(Bundle bundle) {
        super.addCompatExtras(bundle);
        if (this.mUserDisplayName != null) {
            bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
        }
        if (this.mConversationTitle != null) {
            bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
        }
        if (!this.mMessages.isEmpty()) {
            bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, (Parcelable[])Message.getBundleArrayForMessages(this.mMessages));
        }
    }

    public NotificationCompat.MessagingStyle addMessage(Message message) {
        this.mMessages.add(message);
        if (this.mMessages.size() > 25) {
            this.mMessages.remove(0);
        }
        return this;
    }

    public NotificationCompat.MessagingStyle addMessage(CharSequence charSequence, long l, CharSequence charSequence2) {
        this.mMessages.add(new Message(charSequence, l, charSequence2));
        if (this.mMessages.size() > 25) {
            this.mMessages.remove(0);
        }
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 24) {
            Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle(this.mUserDisplayName).setConversationTitle(this.mConversationTitle);
            for (Message message : this.mMessages) {
                Notification.MessagingStyle.Message message2 = new Notification.MessagingStyle.Message(message.getText(), message.getTimestamp(), message.getSender());
                if (message.getDataMimeType() != null) {
                    message2.setData(message.getDataMimeType(), message.getDataUri());
                }
                messagingStyle.addMessage(message2);
            }
            messagingStyle.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
            return;
        }
        Object object = this.findLatestIncomingMessage();
        if (this.mConversationTitle != null) {
            notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
        } else if (object != null) {
            notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(object.getSender());
        }
        if (object != null) {
            builder = notificationBuilderWithBuilderAccessor.getBuilder();
            object = this.mConversationTitle != null ? this.makeMessageLine((Message)object) : object.getText();
            builder.setContentText((CharSequence)object);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            builder = new SpannableStringBuilder();
            boolean bl = this.mConversationTitle != null || this.hasMessagesWithoutSender();
            for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                object = this.mMessages.get(i);
                object = bl ? this.makeMessageLine((Message)object) : object.getText();
                if (i != this.mMessages.size() - 1) {
                    builder.insert(0, (CharSequence)"\n");
                }
                builder.insert(0, (CharSequence)object);
            }
            new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText((CharSequence)builder);
        }
    }

    public CharSequence getConversationTitle() {
        return this.mConversationTitle;
    }

    public List<Message> getMessages() {
        return this.mMessages;
    }

    public CharSequence getUserDisplayName() {
        return this.mUserDisplayName;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    protected void restoreFromCompatExtras(Bundle arrparcelable) {
        this.mMessages.clear();
        this.mUserDisplayName = arrparcelable.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
        this.mConversationTitle = arrparcelable.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
        arrparcelable = arrparcelable.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
        if (arrparcelable != null) {
            this.mMessages = Message.getMessagesFromBundleArray(arrparcelable);
        }
    }

    public NotificationCompat.MessagingStyle setConversationTitle(CharSequence charSequence) {
        this.mConversationTitle = charSequence;
        return this;
    }

    public static final class Message {
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

        public Message(CharSequence charSequence, long l, CharSequence charSequence2) {
            this.mText = charSequence;
            this.mTimestamp = l;
            this.mSender = charSequence2;
        }

        static Bundle[] getBundleArrayForMessages(List<Message> list) {
            Bundle[] arrbundle = new Bundle[list.size()];
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                arrbundle[i] = list.get(i).toBundle();
            }
            return arrbundle;
        }

        static Message getMessageFromBundle(Bundle bundle) {
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
                Message message = new Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
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

        static List<Message> getMessagesFromBundleArray(Parcelable[] arrparcelable) {
            ArrayList<Message> arrayList = new ArrayList<Message>(arrparcelable.length);
            for (int i = 0; i < arrparcelable.length; ++i) {
                Message message;
                if (!(arrparcelable[i] instanceof Bundle) || (message = Message.getMessageFromBundle((Bundle)arrparcelable[i])) == null) continue;
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

        public Message setData(String string, Uri uri) {
            this.mDataMimeType = string;
            this.mDataUri = uri;
            return this;
        }
    }

}
