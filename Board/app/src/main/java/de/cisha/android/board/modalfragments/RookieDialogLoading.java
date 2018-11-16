// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.view.KeyEvent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.view.RookieLoadingView;
import android.support.v4.app.DialogFragment;

public class RookieDialogLoading extends DialogFragment
{
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
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return (View)(this._rookieLoadingView = new RookieLoadingView((Context)this.getActivity()));
    }
    
    @Override
    public void onResume() {
        this.getDialog().setOnKeyListener((DialogInterface.OnKeyListener)new DialogInterface.OnKeyListener() {
            public boolean onKey(final DialogInterface dialogInterface, final int n, final KeyEvent keyEvent) {
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
    
    public void setOnCancelListener(final OnCancelListener cancelListener) {
        this._cancelListener = cancelListener;
    }
    
    public interface OnCancelListener
    {
        void onCancel();
    }
}
