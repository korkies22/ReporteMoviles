/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.view.WebImageView;

private class NewsFragment.NewsListAdapter.ViewHolder
extends RecyclerView.ViewHolder {
    private TextView _textViewAuthor;
    private TextView _textViewDate;
    private TextView _textViewDescription;
    private TextView _textViewTitle;
    private View _view;
    private WebImageView _webImageView;

    public NewsFragment.NewsListAdapter.ViewHolder(View view) {
        super(view);
        this._view = view;
        this._textViewAuthor = (TextView)view.findViewById(2131296631);
        this._textViewDate = (TextView)view.findViewById(2131296632);
        this._textViewDescription = (TextView)view.findViewById(2131296633);
        this._textViewTitle = (TextView)view.findViewById(2131296635);
        this._webImageView = (WebImageView)view.findViewById(2131296634);
    }

    static /* synthetic */ TextView access$300(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._textViewAuthor;
    }

    static /* synthetic */ TextView access$400(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._textViewDescription;
    }

    static /* synthetic */ TextView access$500(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._textViewTitle;
    }

    static /* synthetic */ WebImageView access$600(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._webImageView;
    }

    static /* synthetic */ TextView access$700(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._textViewDate;
    }

    static /* synthetic */ View access$800(NewsFragment.NewsListAdapter.ViewHolder viewHolder) {
        return viewHolder._view;
    }
}
