/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.AnimatorUtilsImpl;
import java.util.ArrayList;

@RequiresApi(value=14)
class AnimatorUtilsApi14
implements AnimatorUtilsImpl {
    AnimatorUtilsApi14() {
    }

    @Override
    public void addPauseListener(@NonNull Animator animator, @NonNull AnimatorListenerAdapter animatorListenerAdapter) {
    }

    @Override
    public void pause(@NonNull Animator animator) {
        ArrayList arrayList = animator.getListeners();
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(i);
                if (!(animatorListener instanceof AnimatorPauseListenerCompat)) continue;
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationPause(animator);
            }
        }
    }

    @Override
    public void resume(@NonNull Animator animator) {
        ArrayList arrayList = animator.getListeners();
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(i);
                if (!(animatorListener instanceof AnimatorPauseListenerCompat)) continue;
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationResume(animator);
            }
        }
    }

    static interface AnimatorPauseListenerCompat {
        public void onAnimationPause(Animator var1);

        public void onAnimationResume(Animator var1);
    }

}
