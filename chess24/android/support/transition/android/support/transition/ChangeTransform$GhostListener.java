/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.ChangeTransform;
import android.support.transition.GhostViewImpl;
import android.support.transition.GhostViewUtils;
import android.support.transition.R;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.view.View;

private static class ChangeTransform.GhostListener
extends TransitionListenerAdapter {
    private GhostViewImpl mGhostView;
    private View mView;

    ChangeTransform.GhostListener(View view, GhostViewImpl ghostViewImpl) {
        this.mView = view;
        this.mGhostView = ghostViewImpl;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        transition.removeListener(this);
        GhostViewUtils.removeGhost(this.mView);
        this.mView.setTag(R.id.transition_transform, null);
        this.mView.setTag(R.id.parent_matrix, null);
    }

    @Override
    public void onTransitionPause(@NonNull Transition transition) {
        this.mGhostView.setVisibility(4);
    }

    @Override
    public void onTransitionResume(@NonNull Transition transition) {
        this.mGhostView.setVisibility(0);
    }
}
