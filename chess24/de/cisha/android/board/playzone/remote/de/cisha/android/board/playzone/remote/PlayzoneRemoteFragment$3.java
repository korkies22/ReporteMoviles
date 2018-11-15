/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PlayzoneRemoteFragment
extends LoadCommandCallbackWithTimeout<NodeServerAddress> {
    final /* synthetic */ Runnable val$success;

    PlayzoneRemoteFragment(Runnable runnable) {
        this.val$success = runnable;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        PlayzoneRemoteFragment.this.hideDialogWaiting();
        PlayzoneRemoteFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                PlayzoneRemoteFragment.this.loadServerAddressesWithReload(3.this.val$success);
            }
        }, false);
    }

    @Override
    protected void succeded(NodeServerAddress nodeServerAddress) {
        PlayzoneRemoteFragment.this.hideDialogWaiting();
        PlayzoneRemoteFragment.this._pairingServerAddress = nodeServerAddress;
        PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(this.val$success);
    }

}
