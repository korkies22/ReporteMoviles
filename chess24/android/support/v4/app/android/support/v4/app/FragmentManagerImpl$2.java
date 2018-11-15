/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.v4.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManagerImpl;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

class FragmentManagerImpl
extends FragmentManagerImpl.AnimationListenerWrapper {
    final /* synthetic */ ViewGroup val$container;
    final /* synthetic */ Fragment val$fragment;

    FragmentManagerImpl(Animation.AnimationListener animationListener, ViewGroup viewGroup, Fragment fragment) {
        this.val$container = viewGroup;
        this.val$fragment = fragment;
        super(animationListener, null);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.val$container.post(new Runnable(){

            @Override
            public void run() {
                if (2.this.val$fragment.getAnimatingAway() != null) {
                    2.this.val$fragment.setAnimatingAway(null);
                    FragmentManagerImpl.this.moveToState(2.this.val$fragment, 2.this.val$fragment.getStateAfterAnimating(), 0, 0, false);
                }
            }
        });
    }

}
