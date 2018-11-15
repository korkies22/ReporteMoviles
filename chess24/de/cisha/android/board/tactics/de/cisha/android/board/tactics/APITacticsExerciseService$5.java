/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.model.TacticsExercisesBundle;

static class APITacticsExerciseService {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason = new int[TacticsExercisesBundle.NoMoreExercisesReason.values().length];
        try {
            APITacticsExerciseService.$SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason[TacticsExercisesBundle.NoMoreExercisesReason.NO_MORE_EXERCISES_LEFT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            APITacticsExerciseService.$SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason[TacticsExercisesBundle.NoMoreExercisesReason.DAILY_LIMIT_REACHED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            APITacticsExerciseService.$SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason[TacticsExercisesBundle.NoMoreExercisesReason.GUEST_LIMIT_REACHED.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
