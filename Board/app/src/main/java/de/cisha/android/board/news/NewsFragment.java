// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.news;

import de.cisha.android.view.WebImageView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import java.text.SimpleDateFormat;
import android.support.v7.widget.GridLayoutManager;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.chess.util.Logger;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.view.View;
import android.content.Context;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.news.model.LanguageOption;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.news.model.NewsItem;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.Language;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.AbstractContentFragment;

public class NewsFragment extends AbstractContentFragment implements OnRefreshListener
{
    private NewsListAdapter _newsListAdapter;
    private RecyclerView _newsListView;
    private SwipeRefreshLayout _refreshLayout;
    private Language _selectedLanguage;
    
    private void loadLatestNews() {
        this._refreshLayout.setRefreshing(true);
        ServiceProvider.getInstance().getNewsService().getNewsItems(this._selectedLanguage, new LoadCommandCallbackWithTimeout<List<NewsItem>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        NewsFragment.this._refreshLayout.setRefreshing(false);
                    }
                });
            }
            
            @Override
            protected void succeded(final List<NewsItem> list) {
                NewsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        NewsFragment.this._refreshLayout.setRefreshing(false);
                        NewsFragment.this._newsListAdapter = new NewsListAdapter(list);
                        NewsFragment.this._newsListView.setAdapter((RecyclerView.Adapter)NewsFragment.this._newsListAdapter);
                    }
                });
            }
        });
    }
    
    private void showLanguageChooserDialog() {
        final OptionDialogFragment<OptionDialogFragment.Option> optionDialogFragment = new OptionDialogFragment<OptionDialogFragment.Option>();
        optionDialogFragment.setTitle(this.getString(2131690092));
        final LinkedList<LanguageOption> list = new LinkedList<LanguageOption>();
        LanguageOption languageOption = new LanguageOption(this.getString(2131690024), Language.EN);
        final LanguageOption languageOption2 = new LanguageOption(this.getString(2131690023), Language.DE);
        final LanguageOption languageOption3 = new LanguageOption(this.getString(2131690025), Language.ES);
        list.add(languageOption);
        list.add(languageOption2);
        list.add(languageOption3);
        if (this._selectedLanguage == Language.DE) {
            languageOption = languageOption2;
        }
        else if (this._selectedLanguage == Language.ES) {
            languageOption = languageOption3;
        }
        optionDialogFragment.setOptions((List<OptionDialogFragment.Option>)list, (OptionDialogFragment.Option)languageOption);
        optionDialogFragment.setOptionSelectionListener((OptionDialogFragment.OptionSelectionListener<OptionDialogFragment.Option>)new OptionDialogFragment.OptionSelectionListener<LanguageOption>() {
            public void onOptionSelected(final LanguageOption languageOption) {
                NewsFragment.this._selectedLanguage = languageOption.getLanguage();
                NewsFragment.this.loadLatestNews();
            }
        });
        optionDialogFragment.show(this.getFragmentManager(), null);
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690093);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.NEWS;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        final List<View> rightButtons = super.getRightButtons(context);
        final ImageView imageView = new ImageView(context);
        imageView.setImageResource(2131231530);
        rightButtons.add((View)imageView);
        imageView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                NewsFragment.this.showLanguageChooserDialog();
            }
        });
        return rightButtons;
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
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        try {
            this._selectedLanguage = Language.valueOf(this.getString(2131690021));
        }
        catch (IllegalArgumentException ex) {
            this._selectedLanguage = Language.EN;
            Logger.getInstance().error(NewsFragment.class.getName(), ex.getClass().getName(), ex);
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        return layoutInflater.inflate(2131427463, viewGroup, false);
    }
    
    @Override
    public void onRefresh() {
        this.loadLatestNews();
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        (this._refreshLayout = (SwipeRefreshLayout)view.findViewById(2131296630)).setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)this);
        if (bundle == null || this._newsListAdapter == null) {
            this.loadLatestNews();
        }
        (this._newsListView = (RecyclerView)view.findViewById(2131296636)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this.getActivity(), 1));
        if (this._newsListAdapter != null) {
            this._newsListView.setAdapter((RecyclerView.Adapter)this._newsListAdapter);
        }
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    private class NewsListAdapter extends Adapter<ViewHolder>
    {
        private SimpleDateFormat _localizedDateFormat;
        private List<NewsItem> _newsItems;
        
        NewsListAdapter(final List<NewsItem> newsItems) {
            this._newsItems = newsItems;
            this._localizedDateFormat = new SimpleDateFormat(NewsFragment.this.getActivity().getString(2131690343), NewsFragment.this.getActivity().getResources().getConfiguration().locale);
        }
        
        @Override
        public int getItemCount() {
            return this._newsItems.size();
        }
        
        public void onBindViewHolder(final ViewHolder viewHolder, final int n) {
            final NewsItem newsItem = this._newsItems.get(n);
            viewHolder._textViewAuthor.setText((CharSequence)newsItem.getAuthor());
            viewHolder._textViewDescription.setText((CharSequence)Html.fromHtml(newsItem.getDescription()));
            viewHolder._textViewTitle.setText((CharSequence)newsItem.getTitle());
            viewHolder._webImageView.setImageResource(0);
            if (newsItem.getTeaserImageUrl() != null) {
                viewHolder._webImageView.setWebImageFrom(newsItem.getTeaserImageUrl().toExternalForm());
            }
            if (newsItem.getPubDate() != null) {
                viewHolder._textViewDate.setText((CharSequence)this._localizedDateFormat.format(newsItem.getPubDate()));
            }
            if (newsItem.getLinkUrl() != null) {
                viewHolder._view.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    final /* synthetic */ Uri val.linkUri = Uri.parse(newsItem.getLinkUrl().toExternalForm());
                    
                    public void onClick(final View view) {
                        NewsFragment.this.startActivity(new Intent("android.intent.action.VIEW", this.val.linkUri));
                    }
                });
            }
        }
        
        public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new ViewHolder(LayoutInflater.from((Context)NewsFragment.this.getActivity()).inflate(2131427464, viewGroup, false));
        }
        
        private class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView _textViewAuthor;
            private TextView _textViewDate;
            private TextView _textViewDescription;
            private TextView _textViewTitle;
            private View _view;
            private WebImageView _webImageView;
            
            public ViewHolder(final View view) {
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
}
