/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.Language;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.news.model.LanguageOption;
import de.cisha.android.board.news.model.NewsItem;
import de.cisha.android.board.service.INewsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import de.cisha.android.view.WebImageView;
import de.cisha.chess.util.Logger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONObject;

public class NewsFragment
extends AbstractContentFragment
implements SwipeRefreshLayout.OnRefreshListener {
    private NewsListAdapter _newsListAdapter;
    private RecyclerView _newsListView;
    private SwipeRefreshLayout _refreshLayout;
    private Language _selectedLanguage;

    private void loadLatestNews() {
        this._refreshLayout.setRefreshing(true);
        ServiceProvider.getInstance().getNewsService().getNewsItems(this._selectedLanguage, (LoadCommandCallback<List<NewsItem>>)new LoadCommandCallbackWithTimeout<List<NewsItem>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        NewsFragment.this._refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            protected void succeded(final List<NewsItem> list) {
                NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        NewsFragment.this._refreshLayout.setRefreshing(false);
                        NewsFragment.this._newsListAdapter = new NewsListAdapter(list);
                        NewsFragment.this._newsListView.setAdapter(NewsFragment.this._newsListAdapter);
                    }
                });
            }

        });
    }

    private void showLanguageChooserDialog() {
        OptionDialogFragment<LanguageOption> optionDialogFragment = new OptionDialogFragment<LanguageOption>();
        optionDialogFragment.setTitle((CharSequence)this.getString(2131690092));
        LinkedList<LanguageOption> linkedList = new LinkedList<LanguageOption>();
        LanguageOption languageOption = new LanguageOption(this.getString(2131690024), Language.EN);
        LanguageOption languageOption2 = new LanguageOption(this.getString(2131690023), Language.DE);
        LanguageOption languageOption3 = new LanguageOption(this.getString(2131690025), Language.ES);
        linkedList.add(languageOption);
        linkedList.add(languageOption2);
        linkedList.add(languageOption3);
        if (this._selectedLanguage == Language.DE) {
            languageOption = languageOption2;
        } else if (this._selectedLanguage == Language.ES) {
            languageOption = languageOption3;
        }
        optionDialogFragment.setOptions(linkedList, languageOption);
        optionDialogFragment.setOptionSelectionListener(new OptionDialogFragment.OptionSelectionListener<LanguageOption>(){

            @Override
            public void onOptionSelected(LanguageOption languageOption) {
                NewsFragment.this._selectedLanguage = languageOption.getLanguage();
                NewsFragment.this.loadLatestNews();
            }
        });
        optionDialogFragment.show(this.getFragmentManager(), null);
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690093);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.NEWS;
    }

    @Override
    public List<View> getRightButtons(Context context) {
        List<View> list = super.getRightButtons(context);
        context = new ImageView(context);
        context.setImageResource(2131231530);
        list.add((View)context);
        context.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                NewsFragment.this.showLanguageChooserDialog();
            }
        });
        return list;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }

    @Override
    public String getTrackingName() {
        return null;
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this._selectedLanguage = Language.valueOf(this.getString(2131690021));
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this._selectedLanguage = Language.EN;
            Logger.getInstance().error(NewsFragment.class.getName(), illegalArgumentException.getClass().getName(), illegalArgumentException);
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2131427463, viewGroup, false);
    }

    @Override
    public void onRefresh() {
        this.loadLatestNews();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._refreshLayout = (SwipeRefreshLayout)view.findViewById(2131296630);
        this._refreshLayout.setOnRefreshListener(this);
        if (bundle == null || this._newsListAdapter == null) {
            this.loadLatestNews();
        }
        this._newsListView = (RecyclerView)view.findViewById(2131296636);
        this._newsListView.setLayoutManager(new GridLayoutManager((Context)this.getActivity(), 1));
        if (this._newsListAdapter != null) {
            this._newsListView.setAdapter(this._newsListAdapter);
        }
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    private class NewsListAdapter
    extends RecyclerView.Adapter<NewsListAdapter$ViewHolder> {
        private SimpleDateFormat _localizedDateFormat;
        private List<NewsItem> _newsItems;

        NewsListAdapter(List<NewsItem> list) {
            this._newsItems = list;
            this._localizedDateFormat = new SimpleDateFormat(NewsFragment.this.getActivity().getString(2131690343), NewsFragment.this.getActivity().getResources().getConfiguration().locale);
        }

        @Override
        public int getItemCount() {
            return this._newsItems.size();
        }

        @Override
        public void onBindViewHolder(NewsListAdapter$ViewHolder newsListAdapter$ViewHolder, int n) {
            NewsItem newsItem = this._newsItems.get(n);
            newsListAdapter$ViewHolder._textViewAuthor.setText((CharSequence)newsItem.getAuthor());
            newsListAdapter$ViewHolder._textViewDescription.setText((CharSequence)Html.fromHtml((String)newsItem.getDescription()));
            newsListAdapter$ViewHolder._textViewTitle.setText((CharSequence)newsItem.getTitle());
            newsListAdapter$ViewHolder._webImageView.setImageResource(0);
            if (newsItem.getTeaserImageUrl() != null) {
                newsListAdapter$ViewHolder._webImageView.setWebImageFrom(newsItem.getTeaserImageUrl().toExternalForm());
            }
            if (newsItem.getPubDate() != null) {
                String string = this._localizedDateFormat.format(newsItem.getPubDate());
                newsListAdapter$ViewHolder._textViewDate.setText((CharSequence)string);
            }
            if (newsItem.getLinkUrl() != null) {
                newsItem = Uri.parse((String)newsItem.getLinkUrl().toExternalForm());
                newsListAdapter$ViewHolder._view.setOnClickListener(new View.OnClickListener((Uri)newsItem){
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
        public NewsListAdapter$ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            return new NewsListAdapter$ViewHolder(LayoutInflater.from((Context)NewsFragment.this.getActivity()).inflate(2131427464, viewGroup, false));
        }

    }

    private class NewsListAdapter$ViewHolder
    extends RecyclerView.ViewHolder {
        private TextView _textViewAuthor;
        private TextView _textViewDate;
        private TextView _textViewDescription;
        private TextView _textViewTitle;
        private View _view;
        private WebImageView _webImageView;

        public NewsListAdapter$ViewHolder(View view) {
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
