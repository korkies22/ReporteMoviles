/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.AudioAttributes
 */
package android.support.v4.media;

import android.media.AudioAttributes;
import android.support.annotation.NonNull;
import android.support.v4.media.AudioAttributesCompatApi21;

static final class AudioAttributesCompatApi21.Wrapper {
    private AudioAttributes mWrapped;

    private AudioAttributesCompatApi21.Wrapper(AudioAttributes audioAttributes) {
        this.mWrapped = audioAttributes;
    }

    public static AudioAttributesCompatApi21.Wrapper wrap(@NonNull AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
        }
        return new AudioAttributesCompatApi21.Wrapper(audioAttributes);
    }

    public AudioAttributes unwrap() {
        return this.mWrapped;
    }
}
