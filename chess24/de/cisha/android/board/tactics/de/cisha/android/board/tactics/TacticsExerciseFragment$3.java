/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this.loadCurrentSession();
    }
}
