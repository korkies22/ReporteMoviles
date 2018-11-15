/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class PlayzoneRemoteFragment
implements Runnable {
    final /* synthetic */ int val$reason;

    PlayzoneRemoteFragment(int n) {
        this.val$reason = n;
    }

    @Override
    public void run() {
        PlayzoneRemoteFragment.this.stopButtonPressed();
        PlayzoneRemoteFragment.this.showWaitingScreenView(PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
        switch (this.val$reason) {
            default: {
                return;
            }
            case 12: {
                PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        PlayzoneRemoteFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.PLAYZONE);
                    }
                });
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "informNotAllowed");
                return;
            }
            case 11: 
        }
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "no opponent");
    }

}
