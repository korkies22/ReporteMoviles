/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.profile.TournamentsWidgetController;
import de.cisha.android.board.profile.view.TournamentsWidgetView;

class ProfileFragment
extends TournamentsWidgetController {
    ProfileFragment(TournamentsWidgetView tournamentsWidgetView, ITournamentListService iTournamentListService, LoadingHelper loadingHelper) {
        super(tournamentsWidgetView, iTournamentListService, loadingHelper);
    }

    @Override
    public IContentPresenter getContentPresenter() {
        return ProfileFragment.this.getContentPresenter();
    }
}
