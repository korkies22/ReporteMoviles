/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.account;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.account.StoreFragment;

public class StoreFragment.StoreListAdapter.ViewHolder
extends RecyclerView.ViewHolder {
    private TextView _descriptionTextView;
    private ImageView _imageView;
    private TextView _premiumMobileTextView;
    private TextView _premiumWebTextView;
    private TextView _registeredTextView;
    private TextView _titleTextView;

    public StoreFragment.StoreListAdapter.ViewHolder(View view) {
        super(view);
        this._titleTextView = (TextView)view.findViewById(2131297060);
        this._descriptionTextView = (TextView)view.findViewById(2131297059);
        this._premiumWebTextView = (TextView)view.findViewById(2131297055);
        this._premiumMobileTextView = (TextView)view.findViewById(2131297054);
        this._registeredTextView = (TextView)view.findViewById(2131297056);
        this._imageView = (ImageView)view.findViewById(2131297057);
    }

    static /* synthetic */ TextView access$1100(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._premiumWebTextView;
    }

    static /* synthetic */ TextView access$1300(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._premiumMobileTextView;
    }

    static /* synthetic */ TextView access$1500(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._registeredTextView;
    }

    static /* synthetic */ ImageView access$1700(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._imageView;
    }

    static /* synthetic */ TextView access$700(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._titleTextView;
    }

    static /* synthetic */ TextView access$900(StoreFragment.StoreListAdapter.ViewHolder viewHolder) {
        return viewHolder._descriptionTextView;
    }
}
