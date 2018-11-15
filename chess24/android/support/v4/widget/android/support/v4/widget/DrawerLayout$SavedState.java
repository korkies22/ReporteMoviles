/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AbsSavedState;
import android.support.v4.widget.DrawerLayout;

protected static class DrawerLayout.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<DrawerLayout.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<DrawerLayout.SavedState>(){

        public DrawerLayout.SavedState createFromParcel(Parcel parcel) {
            return new DrawerLayout.SavedState(parcel, null);
        }

        public DrawerLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new DrawerLayout.SavedState(parcel, classLoader);
        }

        public DrawerLayout.SavedState[] newArray(int n) {
            return new DrawerLayout.SavedState[n];
        }
    };
    int lockModeEnd;
    int lockModeLeft;
    int lockModeRight;
    int lockModeStart;
    int openDrawerGravity = 0;

    public DrawerLayout.SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
        super(parcel, classLoader);
        this.openDrawerGravity = parcel.readInt();
        this.lockModeLeft = parcel.readInt();
        this.lockModeRight = parcel.readInt();
        this.lockModeStart = parcel.readInt();
        this.lockModeEnd = parcel.readInt();
    }

    public DrawerLayout.SavedState(@NonNull Parcelable parcelable) {
        super(parcelable);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.openDrawerGravity);
        parcel.writeInt(this.lockModeLeft);
        parcel.writeInt(this.lockModeRight);
        parcel.writeInt(this.lockModeStart);
        parcel.writeInt(this.lockModeEnd);
    }

}
