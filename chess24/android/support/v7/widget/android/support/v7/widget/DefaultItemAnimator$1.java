/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

class DefaultItemAnimator
implements Runnable {
    final /* synthetic */ ArrayList val$moves;

    DefaultItemAnimator(ArrayList arrayList) {
        this.val$moves = arrayList;
    }

    @Override
    public void run() {
        for (DefaultItemAnimator.MoveInfo moveInfo : this.val$moves) {
            DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
        }
        this.val$moves.clear();
        DefaultItemAnimator.this.mMovesList.remove(this.val$moves);
    }
}
