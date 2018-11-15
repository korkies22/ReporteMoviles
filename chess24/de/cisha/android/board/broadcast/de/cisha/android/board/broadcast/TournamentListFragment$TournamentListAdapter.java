/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import de.cisha.android.board.broadcast.view.TournamentListHeaderView;
import de.cisha.android.ui.list.SectionedListAdapter;
import java.util.List;
import java.util.Map;

private class TournamentListFragment.TournamentListAdapter
extends SectionedListAdapter<TournamentState, TournamentInfo> {
    public TournamentListFragment.TournamentListAdapter(Map<TournamentState, List<TournamentInfo>> map, List<TournamentState> list) {
        super(map, list);
    }

    @Override
    protected View getViewForSectionHeader(TournamentState tournamentState, View object, ViewGroup object2) {
        object2 = object;
        if (object == null) {
            object2 = new TournamentListHeaderView((Context)TournamentListFragment.this.getActivity());
        }
        object = (TournamentListHeaderView)((Object)object2);
        object.setTitle(TournamentListFragment.this.getText(tournamentState.getNameResourceId()));
        return object;
    }

    @Override
    protected View getViewForValue(TournamentInfo tournamentInfo, View object, ViewGroup object2) {
        object2 = object2 != null ? object2.getContext() : TournamentListFragment.this.getActivity();
        Object object3 = object;
        if (object == null) {
            object3 = new TournamentInfoView((Context)object2);
        }
        object = (TournamentInfoView)((Object)object3);
        object.setTournamentInfo(tournamentInfo);
        return object;
    }
}
