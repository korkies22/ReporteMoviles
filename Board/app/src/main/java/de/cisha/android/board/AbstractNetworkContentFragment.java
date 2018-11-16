// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.app.AlertDialog.Builder;
import de.cisha.android.board.service.ServiceProvider;
import android.app.AlertDialog;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;
import de.cisha.android.board.service.InternetAvailabiltyService;

public abstract class AbstractNetworkContentFragment extends AbstractContentFragment implements NetworkListener
{
    private RookieDialogFragmentNoInternet _netWorkDialog;
    private AlertDialog _networkLostInfo;
    
    private void showNoNetworkDialog() {
        (this._netWorkDialog = new RookieDialogFragmentNoInternet()).show(this.getFragmentManager(), "No Internet");
    }
    
    protected abstract boolean needAuthToken();
    
    protected abstract boolean needNetwork();
    
    protected abstract boolean needRegisteredUser();
    
    protected boolean networkConnectionAvailable() {
        return ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable();
    }
    
    @Override
    public void networkStateChanged(final boolean b) {
        if (!b) {
            if (this.needNetwork()) {
                this.getActivity().runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (AbstractNetworkContentFragment.this._networkLostInfo == null) {
                            final AlertDialog.Builder alertDialog.Builder = new AlertDialog.Builder((Context)AbstractNetworkContentFragment.this.getActivity());
                            alertDialog.Builder.setCancelable(false);
                            alertDialog.Builder.setNeutralButton((CharSequence)"ok", (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialogInterface, final int n) {
                                    dialogInterface.cancel();
                                    AbstractNetworkContentFragment.this._networkLostInfo = null;
                                }
                            });
                            alertDialog.Builder.setMessage((CharSequence)"networkconnection lost");
                            AbstractNetworkContentFragment.this._networkLostInfo = alertDialog.Builder.create();
                            AbstractNetworkContentFragment.this._networkLostInfo.show();
                        }
                    }
                });
            }
        }
        else {
            this.getActivity().runOnUiThread((Runnable)new Runnable() {
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
