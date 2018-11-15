/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.tactics;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.tactics.TacticsStopFragment;
import de.cisha.android.board.view.KeyValueInfoDialogFragment;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

public static class TacticsStopFragment.TacticsInfoFragment
extends KeyValueInfoDialogFragment {
    private TacticsExerciseUserSolution _currentExercise;
    private TacticsExerciseSession _currentSession;

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle object2) {
        viewGroup = super.onCreateView((LayoutInflater)object, viewGroup, (Bundle)object2);
        if (this._currentExercise != null) {
            object = this.getActivity().getString(2131690351);
            object2 = new StringBuilder();
            object2.append("#");
            object2.append(this._currentExercise.getExercise().getExerciseId());
            this.addRowView((String)object, object2.toString());
            object = this.getActivity().getString(2131690352);
            object2 = new StringBuilder();
            object2.append("");
            object2.append(this._currentExercise.getExercise().getExerciseRating().getRating());
            this.addRowView((String)object, object2.toString());
            object = this.getActivity().getString(2131690350);
            object2 = new StringBuilder();
            object2.append("");
            object2.append((float)this._currentExercise.getExercise().getAverageTimeInMillis() / 1000.0f);
            this.addRowView((String)object, object2.toString());
            object = this.getActivity().getString(2131690356);
            object2 = new StringBuilder();
            object2.append("");
            object2.append((float)this._currentExercise.getTimeUsed() / 1000.0f);
            this.addRowView((String)object, object2.toString());
        }
        if (this._currentSession != null) {
            this.addRowView(this.getActivity().getString(2131690353), "");
            object = this.getActivity().getString(2131690346);
            object2 = new StringBuilder();
            object2.append("");
            object2.append(this._currentSession.getNumberOfExercises());
            this.addRowView((String)object, object2.toString());
            object = this.getActivity().getString(2131690347);
            object2 = new StringBuilder();
            object2.append("");
            object2.append(this._currentSession.getNumberOfCorrectExercises());
            this.addRowView((String)object, object2.toString());
            object = this.getActivity().getString(2131690344);
            object2 = new StringBuilder();
            object2.append("");
            object2.append(this._currentSession.getNumberOfWrongExercises());
            this.addRowView((String)object, object2.toString());
            object2 = this.getActivity().getString(2131690354);
            if (this._currentSession.getUserStartRating() == null) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append("");
                object.append(this._currentSession.getUserStartRating().getRating());
                object = object.toString();
            }
            this.addRowView((String)object2, (String)object);
            object2 = this.getActivity().getString(2131690345);
            if (this._currentSession.getUserEndRating() == null) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append("");
                object.append(this._currentSession.getUserEndRating().getRating());
                object = object.toString();
            }
            this.addRowView((String)object2, (String)object);
            object2 = this.getActivity().getString(2131690349);
            if (this._currentSession.getPerformance() == null) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append("");
                object.append(this._currentSession.getPerformance().getRating());
                object = object.toString();
            }
            this.addRowView((String)object2, (String)object);
            object = this.getActivity().getString(2131690355);
            object2 = new StringBuilder();
            object2.append("");
            object2.append((float)this._currentSession.getAverageTimeMillis() / 1000.0f);
            object2.append(this.getActivity().getString(2131690348));
            this.addRowView((String)object, object2.toString());
        }
        return viewGroup;
    }

    public void setCurrentExercise(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this._currentExercise = tacticsExerciseUserSolution;
    }

    public void setSession(TacticsExerciseSession tacticsExerciseSession) {
        this._currentSession = tacticsExerciseSession;
    }
}
