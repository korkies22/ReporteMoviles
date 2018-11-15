/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.ResultReceiver
 */
package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.media.session.MediaSessionCompat;

static final class MediaSessionCompat.ResultReceiverWrapper
implements Parcelable {
    public static final Parcelable.Creator<MediaSessionCompat.ResultReceiverWrapper> CREATOR = new Parcelable.Creator<MediaSessionCompat.ResultReceiverWrapper>(){

        public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel parcel) {
            return new MediaSessionCompat.ResultReceiverWrapper(parcel);
        }

        public MediaSessionCompat.ResultReceiverWrapper[] newArray(int n) {
            return new MediaSessionCompat.ResultReceiverWrapper[n];
        }
    };
    private ResultReceiver mResultReceiver;

    MediaSessionCompat.ResultReceiverWrapper(Parcel parcel) {
        this.mResultReceiver = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(parcel);
    }

    public MediaSessionCompat.ResultReceiverWrapper(ResultReceiver resultReceiver) {
        this.mResultReceiver = resultReceiver;
    }

    static /* synthetic */ ResultReceiver access$400(MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) {
        return resultReceiverWrapper.mResultReceiver;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        this.mResultReceiver.writeToParcel(parcel, n);
    }

}
