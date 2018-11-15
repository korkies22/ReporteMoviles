/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.AudioAttributes
 *  android.media.AudioAttributes$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.support.v4.media.AudioAttributesCompat;
import android.support.v4.media.AudioAttributesCompatApi21;

public static class AudioAttributesCompat.Builder {
    private Object mAAObject;
    private int mContentType = 0;
    private int mFlags = 0;
    private Integer mLegacyStream;
    private int mUsage = 0;

    public AudioAttributesCompat.Builder() {
    }

    public AudioAttributesCompat.Builder(AudioAttributesCompat audioAttributesCompat) {
        this.mUsage = audioAttributesCompat.mUsage;
        this.mContentType = audioAttributesCompat.mContentType;
        this.mFlags = audioAttributesCompat.mFlags;
        this.mLegacyStream = audioAttributesCompat.mLegacyStream;
        this.mAAObject = audioAttributesCompat.unwrap();
    }

    public AudioAttributesCompat build() {
        if (!sForceLegacyBehavior && Build.VERSION.SDK_INT >= 21) {
            if (this.mAAObject != null) {
                return AudioAttributesCompat.wrap(this.mAAObject);
            }
            AudioAttributes.Builder builder = new AudioAttributes.Builder().setContentType(this.mContentType).setFlags(this.mFlags).setUsage(this.mUsage);
            if (this.mLegacyStream != null) {
                builder.setLegacyStreamType(this.mLegacyStream.intValue());
            }
            return AudioAttributesCompat.wrap((Object)builder.build());
        }
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat(null);
        audioAttributesCompat.mContentType = this.mContentType;
        audioAttributesCompat.mFlags = this.mFlags;
        audioAttributesCompat.mUsage = this.mUsage;
        audioAttributesCompat.mLegacyStream = this.mLegacyStream;
        audioAttributesCompat.mAudioAttributesWrapper = null;
        return audioAttributesCompat;
    }

    public AudioAttributesCompat.Builder setContentType(int n) {
        switch (n) {
            default: {
                this.mUsage = 0;
                return this;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
        }
        this.mContentType = n;
        return this;
    }

    public AudioAttributesCompat.Builder setFlags(int n) {
        this.mFlags = n & 1023 | this.mFlags;
        return this;
    }

    public AudioAttributesCompat.Builder setLegacyStreamType(int n) {
        if (n == 10) {
            throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
        }
        this.mLegacyStream = n;
        this.mUsage = AudioAttributesCompat.usageForStreamType(n);
        return this;
    }

    public AudioAttributesCompat.Builder setUsage(int n) {
        switch (n) {
            default: {
                this.mUsage = 0;
                return this;
            }
            case 16: {
                if (!sForceLegacyBehavior && Build.VERSION.SDK_INT > 25) {
                    this.mUsage = n;
                    return this;
                }
                this.mUsage = 12;
                return this;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
        }
        this.mUsage = n;
        return this;
    }
}
