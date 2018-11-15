/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.account;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.account.view.PremiumFlagDrawable;
import java.util.List;

private class StoreFragment.StoreListAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private StoreFragment.StoreListAdapter() {
    }

    @Override
    public int getItemCount() {
        return StoreFragment.this._featureList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        StoreFragment.Feature feature = (StoreFragment.Feature)StoreFragment.this._featureList.get(n);
        viewHolder._titleTextView.setText(feature._titleRes);
        viewHolder._descriptionTextView.setText(feature._descrRes);
        viewHolder._premiumWebTextView.setText(feature._webPremiumRes);
        viewHolder._premiumMobileTextView.setText(feature._mobilePremiumRes);
        viewHolder._registeredTextView.setText(feature._registeredRes);
        viewHolder._imageView.setImageResource(feature._imageRes);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        viewGroup = LayoutInflater.from((Context)StoreFragment.this.getActivity()).inflate(2131427552, viewGroup, false);
        viewGroup.findViewById(2131297054).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099704));
        viewGroup.findViewById(2131297055).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099707));
        viewGroup.findViewById(2131297056).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099710));
        return new ViewHolder((View)viewGroup);
    }

    public class ViewHolder
    extends RecyclerView.ViewHolder {
        private TextView _descriptionTextView;
        private ImageView _imageView;
        private TextView _premiumMobileTextView;
        private TextView _premiumWebTextView;
        private TextView _registeredTextView;
        private TextView _titleTextView;

        public ViewHolder(View view) {
            super(view);
            this._titleTextView = (TextView)view.findViewById(2131297060);
            this._descriptionTextView = (TextView)view.findViewById(2131297059);
            this._premiumWebTextView = (TextView)view.findViewById(2131297055);
            this._premiumMobileTextView = (TextView)view.findViewById(2131297054);
            this._registeredTextView = (TextView)view.findViewById(2131297056);
            this._imageView = (ImageView)view.findViewById(2131297057);
        }
    }

}
