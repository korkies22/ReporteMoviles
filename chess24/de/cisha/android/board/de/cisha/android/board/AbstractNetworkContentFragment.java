/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package de.cisha.android.board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;
import de.cisha.android.board.service.IInternetAvailabilityService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.InternetAvailabiltyService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

public abstract class AbstractNetworkContentFragment
extends AbstractContentFragment
implements InternetAvailabiltyService.NetworkListener {
    private RookieDialogFragmentNoInternet _netWorkDialog;
    private AlertDialog _networkLostInfo;

    private void showNoNetworkDialog() {
        this._netWorkDialog = new RookieDialogFragmentNoInternet();
        this._netWorkDialog.show(this.getFragmentManager(), "No Internet");
    }

    protected abstract boolean needAuthToken();

    protected abstract boolean needNetwork();

    protected abstract boolean needRegisteredUser();

    protected boolean networkConnectionAvailable() {
        return ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable();
    }

    @Override
    public void networkStateChanged(boolean bl) {
        if (!bl) {
            if (this.needNetwork()) {
                this.getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        if (AbstractNetworkContentFragment.this._networkLostInfo == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder((Context)AbstractNetworkContentFragment.this.getActivity());
                            builder.setCancelable(false);
                            builder.setNeutralButton((CharSequence)"ok", new DialogInterface.OnClickListener(){

                                public void onClick(DialogInterface dialogInterface, int n) {
                                    dialogInterface.cancel();
                                    AbstractNetworkContentFragment.this._networkLostInfo = null;
                                }
                            });
                            builder.setMessage((CharSequence)"networkconnection lost");
                            AbstractNetworkContentFragment.this._networkLostInfo = builder.create();
                            AbstractNetworkContentFragment.this._networkLostInfo.show();
                        }
                    }

                });
                return;
            }
        } else {
            this.getActivity().runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    if (AbstractNetworkContentFragment.this._networkLostInfo != null) {
                        AbstractNetworkContentFragment.this._networkLostInfo.cancel();
                        AbstractNetworkContentFragment.this._networkLostInfo = null;
                    }
                    if (AbstractNetworkContentFragment.this._netWorkDialog != null) {
                        AbstractNetworkContentFragment.this._netWorkDialog.dismiss();
                        AbstractNetworkContentFragment.this._netWorkDialog = null;
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.needAuthToken() && !ServiceProvider.getInstance().getLoginService().isLoggedIn()) {
            this.showViewForErrorCode(APIStatusCode.INTERNAL_NOT_LOGGED_IN, null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
