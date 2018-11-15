/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.media.session.PlaybackStateCompatApi21;
import android.text.TextUtils;

public static final class PlaybackStateCompat.CustomAction
implements Parcelable {
    public static final Parcelable.Creator<PlaybackStateCompat.CustomAction> CREATOR = new Parcelable.Creator<PlaybackStateCompat.CustomAction>(){

        public PlaybackStateCompat.CustomAction createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat.CustomAction(parcel);
        }

        public PlaybackStateCompat.CustomAction[] newArray(int n) {
            return new PlaybackStateCompat.CustomAction[n];
        }
    };
    private final String mAction;
    private Object mCustomActionObj;
    private final Bundle mExtras;
    private final int mIcon;
    private final CharSequence mName;

    PlaybackStateCompat.CustomAction(Parcel parcel) {
        this.mAction = parcel.readString();
        this.mName = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = parcel.readInt();
        this.mExtras = parcel.readBundle();
    }

    PlaybackStateCompat.CustomAction(String string, CharSequence charSequence, int n, Bundle bundle) {
        this.mAction = string;
        this.mName = charSequence;
        this.mIcon = n;
        this.mExtras = bundle;
    }

    public static PlaybackStateCompat.CustomAction fromCustomAction(Object object) {
        if (object != null && Build.VERSION.SDK_INT >= 21) {
            PlaybackStateCompat.CustomAction customAction = new PlaybackStateCompat.CustomAction(PlaybackStateCompatApi21.CustomAction.getAction(object), PlaybackStateCompatApi21.CustomAction.getName(object), PlaybackStateCompatApi21.CustomAction.getIcon(object), PlaybackStateCompatApi21.CustomAction.getExtras(object));
            customAction.mCustomActionObj = object;
            return customAction;
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public String getAction() {
        return this.mAction;
    }

    public Object getCustomAction() {
        if (this.mCustomActionObj == null && Build.VERSION.SDK_INT >= 21) {
            this.mCustomActionObj = PlaybackStateCompatApi21.CustomAction.newInstance(this.mAction, this.mName, this.mIcon, this.mExtras);
            return this.mCustomActionObj;
        }
        return this.mCustomActionObj;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Action:mName='");
        stringBuilder.append((Object)this.mName);
        stringBuilder.append(", mIcon=");
        stringBuilder.append(this.mIcon);
        stringBuilder.append(", mExtras=");
        stringBuilder.append((Object)this.mExtras);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mAction);
        TextUtils.writeToParcel((CharSequence)this.mName, (Parcel)parcel, (int)n);
        parcel.writeInt(this.mIcon);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private final String mAction;
        private Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        public Builder(String string, CharSequence charSequence, int n) {
            if (TextUtils.isEmpty((CharSequence)string)) {
                throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
            }
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
            }
            if (n == 0) {
                throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
            }
            this.mAction = string;
            this.mName = charSequence;
            this.mIcon = n;
        }

        public PlaybackStateCompat.CustomAction build() {
            return new PlaybackStateCompat.CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }
    }

}
