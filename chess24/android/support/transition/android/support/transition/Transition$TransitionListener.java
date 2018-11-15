/*
 * Decompiled with CFR 0_134.
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;

public static interface Transition.TransitionListener {
    public void onTransitionCancel(@NonNull Transition var1);

    public void onTransitionEnd(@NonNull Transition var1);

    public void onTransitionPause(@NonNull Transition var1);

    public void onTransitionResume(@NonNull Transition var1);

    public void onTransitionStart(@NonNull Transition var1);
}
