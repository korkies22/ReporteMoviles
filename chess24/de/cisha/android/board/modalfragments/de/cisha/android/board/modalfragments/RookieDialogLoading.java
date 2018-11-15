/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnKeyListener
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.modalfragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.view.RookieLoadingView;

public class RookieDialogLoading
extends DialogFragment {
    private OnCancelListener _cancelListener;
    private RookieLoadingView _rookieLoadingView;

    public RookieDialogLoading() {
        this.setStyle(2, 0);
    }

    private void onLoadingDialogCancelled() {
        if (this._cancelListener != null) {
            this._cancelListener.onCancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._rookieLoadingView = new RookieLoadingView((Context)this.getActivity());
        return this._rookieLoadingView;
    }

    @Override
    public void onResume() {
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener(){

            public boolean onKey(DialogInterface dialogInterface, int n, KeyEvent keyEvent) {
                if (n == 4) {
                    RookieDialogLoading.this.onLoadingDialogCancelled();
                    return false;
                }
                return false;
            }
        });
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        this._rookieLoadingView.enableLoadingAnimation(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        this._rookieLoadingView.enableLoadingAnimation(false);
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this._cancelListener = onCancelListener;
    }

    public static interface OnCancelListener {
        public void onCancel();
    }

}
