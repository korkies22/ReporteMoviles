/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.board.news.model.NewsItem;
import de.cisha.android.view.WebImageView;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

private class NewsFragment.NewsListAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private SimpleDateFormat _localizedDateFormat;
    private List<NewsItem> _newsItems;

    NewsFragment.NewsListAdapter(List<NewsItem> list) {
        this._newsItems = list;
        this._localizedDateFormat = new SimpleDateFormat(NewsFragment.this.getActivity().getString(2131690343), NewsFragment.this.getActivity().getResources().getConfiguration().locale);
    }

    @Override
    public int getItemCount() {
        return this._newsItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        NewsItem newsItem = this._newsItems.get(n);
        viewHolder._textViewAuthor.setText((CharSequence)newsItem.getAuthor());
        viewHolder._textViewDescription.setText((CharSequence)Html.fromHtml((String)newsItem.getDescription()));
        viewHolder._textViewTitle.setText((CharSequence)newsItem.getTitle());
        viewHolder._webImageView.setImageResource(0);
        if (newsItem.getTeaserImageUrl() != null) {
            viewHolder._webImageView.setWebImageFrom(newsItem.getTeaserImageUrl().toExternalForm());
        }
        if (newsItem.getPubDate() != null) {
            String string = this._localizedDateFormat.format(newsItem.getPubDate());
            viewHolder._textViewDate.setText((CharSequence)string);
        }
        if (newsItem.getLinkUrl() != null) {
            newsItem = Uri.parse((String)newsItem.getLinkUrl().toExternalForm());
            viewHolder._view.setOnClickListener(new View.OnClickListener((Uri)newsItem){
                final /* synthetic */ Uri val$linkUri;
                {
                    this.val$linkUri = uri;
                }

                public void onClick(View view) {
                    view = new Intent("android.intent.action.VIEW", this.val$linkUri);
                    NewsFragment.this.startActivity((Intent)view);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder(LayoutInflater.from((Context)NewsFragment.this.getActivity()).inflate(2131427464, viewGroup, false));
    }

    private class ViewHolder
    extends RecyclerView.ViewHolder {
        private TextView _textViewAuthor;
        private TextView _textViewDate;
        private TextView _textViewDescription;
        private TextView _textViewTitle;
        private View _view;
        private WebImageView _webImageView;

        public ViewHolder(View view) {
            super(view);
            this._view = view;
            this._textViewAuthor = (TextView)view.findViewById(2131296631);
            this._textViewDate = (TextView)view.findViewById(2131296632);
            this._textViewDescription = (TextView)view.findViewById(2131296633);
            this._textViewTitle = (TextView)view.findViewById(2131296635);
            this._webImageView = (WebImageView)view.findViewById(2131296634);
        }
    }

}
