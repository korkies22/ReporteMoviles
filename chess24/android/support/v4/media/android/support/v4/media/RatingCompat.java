/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.Rating
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package android.support.v4.media;

import android.media.Rating;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat
implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>(){

        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        public RatingCompat[] newArray(int n) {
            return new RatingCompat[n];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    RatingCompat(int n, float f) {
        this.mRatingStyle = n;
        this.mRatingValue = f;
    }

    public static RatingCompat fromRating(Object object) {
        if (object != null && Build.VERSION.SDK_INT >= 19) {
            Object object2 = (Rating)object;
            int n = object2.getRatingStyle();
            if (object2.isRated()) {
                switch (n) {
                    default: {
                        return null;
                    }
                    case 6: {
                        object2 = RatingCompat.newPercentageRating(object2.getPercentRating());
                        break;
                    }
                    case 3: 
                    case 4: 
                    case 5: {
                        object2 = RatingCompat.newStarRating(n, object2.getStarRating());
                        break;
                    }
                    case 2: {
                        object2 = RatingCompat.newThumbRating(object2.isThumbUp());
                        break;
                    }
                    case 1: {
                        object2 = RatingCompat.newHeartRating(object2.hasHeart());
                        break;
                    }
                }
            } else {
                object2 = RatingCompat.newUnratedRating(n);
            }
            object2.mRatingObj = object;
            return object2;
        }
        return null;
    }

    public static RatingCompat newHeartRating(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        return new RatingCompat(1, f);
    }

    public static RatingCompat newPercentageRating(float f) {
        if (f >= 0.0f && f <= 100.0f) {
            return new RatingCompat(6, f);
        }
        Log.e((String)TAG, (String)"Invalid percentage-based rating value");
        return null;
    }

    public static RatingCompat newStarRating(int n, float f) {
        float f2;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid rating style (");
                stringBuilder.append(n);
                stringBuilder.append(") for a star rating");
                Log.e((String)TAG, (String)stringBuilder.toString());
                return null;
            }
            case 5: {
                f2 = 5.0f;
                break;
            }
            case 4: {
                f2 = 4.0f;
                break;
            }
            case 3: {
                f2 = 3.0f;
            }
        }
        if (f >= 0.0f && f <= f2) {
            return new RatingCompat(n, f);
        }
        Log.e((String)TAG, (String)"Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newThumbRating(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        return new RatingCompat(2, f);
    }

    public static RatingCompat newUnratedRating(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        return new RatingCompat(n, -1.0f);
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    public float getPercentRating() {
        if (this.mRatingStyle == 6 && this.isRated()) {
            return this.mRatingValue;
        }
        return -1.0f;
    }

    public Object getRating() {
        if (this.mRatingObj == null && Build.VERSION.SDK_INT >= 19) {
            if (this.isRated()) {
                switch (this.mRatingStyle) {
                    default: {
                        return null;
                    }
                    case 6: {
                        this.mRatingObj = Rating.newPercentageRating((float)this.getPercentRating());
                        break;
                    }
                    case 3: 
                    case 4: 
                    case 5: {
                        this.mRatingObj = Rating.newStarRating((int)this.mRatingStyle, (float)this.getStarRating());
                        break;
                    }
                    case 2: {
                        this.mRatingObj = Rating.newThumbRating((boolean)this.isThumbUp());
                        break;
                    }
                    case 1: {
                        this.mRatingObj = Rating.newHeartRating((boolean)this.hasHeart());
                        break;
                    }
                }
            } else {
                this.mRatingObj = Rating.newUnratedRating((int)this.mRatingStyle);
            }
        }
        return this.mRatingObj;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public float getStarRating() {
        switch (this.mRatingStyle) {
            default: {
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                if (!this.isRated()) break;
                return this.mRatingValue;
            }
        }
        return -1.0f;
    }

    public boolean hasHeart() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 1) {
            return false;
        }
        if (this.mRatingValue == 1.0f) {
            bl = true;
        }
        return bl;
    }

    public boolean isRated() {
        if (this.mRatingValue >= 0.0f) {
            return true;
        }
        return false;
    }

    public boolean isThumbUp() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 2) {
            return false;
        }
        if (this.mRatingValue == 1.0f) {
            bl = true;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rating:style=");
        stringBuilder.append(this.mRatingStyle);
        stringBuilder.append(" rating=");
        String string = this.mRatingValue < 0.0f ? "unrated" : String.valueOf(this.mRatingValue);
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface StarStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface Style {
    }

}
