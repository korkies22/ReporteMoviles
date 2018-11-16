// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.AbsListView;
import android.view.MotionEvent;
import android.widget.ListView.FixedViewInfo;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.widget.ListAdapter;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public abstract class RefreshingListView extends ListView implements UpdatingList, AbsListView.OnScrollListener, View.OnClickListener
{
    private static final int LIST_START_INDEX = 2;
    private static final int STATUS_IDLE = -1;
    private static final int STATUS_REFRESHING = 1;
    private static final int STATUS_SCROLLING = 0;
    private ProgressBar _footerLoadingSpinner;
    private RefreshingListViewFooterView _footerView;
    private RefreshingListHeaderView _headerView;
    private boolean _headersAdded;
    private boolean _isShowingButton;
    private MutableHeaderViewListAdapter _listadapter;
    private AbsListView.OnScrollListener _onScrollListener;
    private View _overHeaderView;
    private int _scrollState;
    private boolean _scrollingOverscrollBack;
    private boolean _showFooter;
    private boolean _showHeader;
    private int _status;
    
    public RefreshingListView(final Context context) {
        super(context);
        this._scrollingOverscrollBack = false;
        this._showFooter = true;
        this._showHeader = true;
        this._isShowingButton = false;
        this._status = -1;
        this._headersAdded = false;
        this.init();
    }
    
    public RefreshingListView(final Context context, final AttributeSet set) {
        super(context, set);
        this._scrollingOverscrollBack = false;
        this._showFooter = true;
        this._showHeader = true;
        this._isShowingButton = false;
        this._status = -1;
        this._headersAdded = false;
        this.init();
    }
    
    public RefreshingListView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this._scrollingOverscrollBack = false;
        this._showFooter = true;
        this._showHeader = true;
        this._isShowingButton = false;
        this._status = -1;
        this._headersAdded = false;
        this.init();
    }
    
    private void addHeaders() {
        if (!this._headersAdded) {
            this._headersAdded = true;
            this.addHeaderView(this._overHeaderView);
            this.addHeaderView((View)this._headerView);
        }
    }
    
    private int getContentStartIndex() {
        if (this._headersAdded) {
            return 2;
        }
        return 0;
    }
    
    private void init() {
        super.setAdapter((ListAdapter)(this._listadapter = new MutableHeaderViewListAdapter()));
        this._footerView = new RefreshingListViewFooterView(this.getContext());
        this._headerView = new RefreshingListHeaderView(this.getContext());
        this._overHeaderView = View.inflate(this.getContext(), R.layout.emptyrow, (ViewGroup)null);
        this.addHeaders();
        this.setHeaderDividersEnabled(false);
        this._footerLoadingSpinner = (ProgressBar)this._footerView.findViewById(R.id.refreshing_list_footer_progressbar);
        this._footerView.showButton(false);
        this.addFooterView((View)this._footerView);
        this.setSelection(this.getContentStartIndex());
        this.setOverScrollMode(2);
        ((CustomButton)this._footerView.findViewById(R.id.refreshing_list_footer_button)).setOnClickListener((View.OnClickListener)this);
        super.setOnScrollListener((AbsListView.OnScrollListener)this);
        this.enableHeader();
    }
    
    private boolean isEverythingShown() {
        final ListAdapter adapter = this.getAdapter();
        return adapter == null || this.getLastVisiblePosition() - this.getFirstVisiblePosition() >= adapter.getCount() - 1;
    }
    
    private void measureHeader() {
        ViewGroup.LayoutParams layoutParams;
        if ((layoutParams = this._headerView.getLayoutParams()) == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int n = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int n2;
        if (this._headerView.getVisibility() != 8) {
            if (layoutParams.height > 0) {
                n2 = View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            }
            else {
                n2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
        }
        else {
            n2 = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
            n = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
        }
        this._headerView.measure(n, n2);
    }
    
    private void removeHeaders() {
        if (this._headersAdded) {
            this._headersAdded = false;
            this.removeHeaderView(this._overHeaderView);
            this.removeHeaderView((View)this._headerView);
        }
    }
    
    private void tryToScrollBack() {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                RefreshingListView.this.updateHeader();
                RefreshingListView.this.invalidate();
            }
        });
        if (this._showHeader && !this.isEverythingShown()) {
            this._scrollingOverscrollBack = true;
            this.clearAnimation();
            if (this._headerView.getBottom() >= 0) {
                this.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        RefreshingListView.this.smoothScrollBy(RefreshingListView.this._headerView.getBottom(), 1000);
                    }
                });
            }
        }
    }
    
    private void updateHeader() {
        if (this._showHeader) {
            final boolean everythingShown = this.isEverythingShown();
            if (this._isShowingButton != everythingShown) {
                this._isShowingButton = everythingShown;
                this._footerView.showButton(everythingShown);
                if (everythingShown) {
                    this.removeHeaders();
                }
                else {
                    this.addHeaders();
                }
                this.tryToScrollBack();
            }
        }
        else {
            this.removeHeaders();
        }
    }
    
    public void addFooterView(final View view) {
        final ListView.FixedViewInfo listView.FixedViewInfo = new ListView.FixedViewInfo((ListView)this);
        listView.FixedViewInfo.data = null;
        listView.FixedViewInfo.isSelectable = false;
        listView.FixedViewInfo.view = view;
        this._listadapter.addFooter(listView.FixedViewInfo);
    }
    
    public void addHeaderView(final View view) {
        final ListView.FixedViewInfo listView.FixedViewInfo = new ListView.FixedViewInfo((ListView)this);
        listView.FixedViewInfo.data = null;
        listView.FixedViewInfo.isSelectable = false;
        listView.FixedViewInfo.view = view;
        this._listadapter.addHeader(listView.FixedViewInfo);
    }
    
    public void disableFooter() {
        synchronized (this) {
            if (this._showFooter) {
                this._showFooter = false;
                this._footerView.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        RefreshingListView.this.removeFooterView((View)RefreshingListView.this._footerView);
                    }
                });
            }
        }
    }
    
    public void disableHeader() {
        final int firstVisiblePosition = this.getFirstVisiblePosition();
        this._showHeader = false;
        this._headerView.post((Runnable)new Runnable() {
            @Override
            public void run() {
                RefreshingListView.this.removeHeaders();
                if (firstVisiblePosition <= 1) {
                    RefreshingListView.this.setSelection(RefreshingListView.this.getContentStartIndex());
                }
            }
        });
    }
    
    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        final int firstVisiblePosition = this.getFirstVisiblePosition();
        if (this._showHeader && !this.isEverythingShown() && firstVisiblePosition <= 1 && this._status != 1) {
            final boolean b = this._headerView.getHeight() < this._headerView.getBottom();
            final RefreshingListHeaderView headerView = this._headerView;
            RefreshingListHeaderView.State state;
            if (b) {
                state = RefreshingListHeaderView.State.FINGER_UP;
            }
            else {
                state = RefreshingListHeaderView.State.PULLDOWN;
            }
            headerView.setState(state);
            if (motionEvent.getAction() == 1) {
                if (b && this._status != 1) {
                    this.headerReached();
                }
                else {
                    this.tryToScrollBack();
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
    
    public void enableFooter() {
        synchronized (this) {
            if (!this._showFooter) {
                this.addFooterView((View)this._footerView);
            }
            this._showFooter = true;
        }
    }
    
    public void enableHeader() {
        this._showHeader = true;
        this._headerView.post((Runnable)new Runnable() {
            @Override
            public void run() {
                RefreshingListView.this.updateHeader();
                RefreshingListView.this.measureHeader();
                RefreshingListView.this.tryToScrollBack();
            }
        });
    }
    
    protected abstract void footerReached();
    
    public int getFooterViewsCount() {
        return this._listadapter.getFootersCount();
    }
    
    public int getHeaderViewsCount() {
        return this._listadapter.getHeadersCount();
    }
    
    protected abstract void headerReached();
    
    protected void onAttachedToWindow() {
        this.setSelection(this.getContentStartIndex());
        super.onAttachedToWindow();
    }
    
    public void onClick(final View view) {
        if (this._status != 1) {
            this.headerReached();
        }
    }
    
    public void onScroll(final AbsListView absListView, final int n, final int n2, final int n3) {
        if (this._showHeader && !this.isEverythingShown() && this._scrollState == 2 && n <= 1 && !this._scrollingOverscrollBack) {
            this.tryToScrollBack();
        }
        if (n > 0 && n + n2 >= n3 && this._showFooter && this._status != 1) {
            this._status = 1;
            this.footerReached();
        }
        if (this._onScrollListener != null) {
            this._onScrollListener.onScroll(absListView, n, n2, n3);
        }
    }
    
    public void onScrollStateChanged(final AbsListView absListView, final int scrollState) {
        this._scrollState = scrollState;
        if (this._scrollState != 0) {
            this._scrollingOverscrollBack = false;
        }
        if (this._onScrollListener != null) {
            this._onScrollListener.onScrollStateChanged(absListView, scrollState);
        }
    }
    
    public boolean removeFooterView(final View view) {
        return this._listadapter.removeFooter(view);
    }
    
    public boolean removeHeaderView(final View view) {
        return this._listadapter.removeHeader(view);
    }
    
    public void setAdapter(final ListAdapter adapter) {
        this._listadapter.setAdapter(adapter);
        this.setSelection(this.getContentStartIndex());
    }
    
    public void setOnItemClickListener(final AdapterView.OnItemClickListener adapterView.OnItemClickListener) {
        if (adapterView.OnItemClickListener != null) {
            super.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                    if (n >= RefreshingListView.this.getContentStartIndex()) {
                        adapterView.OnItemClickListener.onItemClick((AdapterView)adapterView, view, n - RefreshingListView.this.getContentStartIndex(), n2);
                    }
                }
            });
            return;
        }
        super.setOnItemClickListener((AdapterView.OnItemClickListener)null);
    }
    
    public void setOnItemLongClickListener(final AdapterView.OnItemLongClickListener adapterView.OnItemLongClickListener) {
        if (adapterView.OnItemLongClickListener != null) {
            super.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                    return n >= RefreshingListView.this.getContentStartIndex() && adapterView.OnItemLongClickListener.onItemLongClick((AdapterView)adapterView, view, n - RefreshingListView.this.getContentStartIndex(), n2);
                }
            });
            return;
        }
        super.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)null);
    }
    
    public void setOnItemSelectedListener(final AdapterView.OnItemSelectedListener adapterView.OnItemSelectedListener) {
        if (adapterView.OnItemSelectedListener != null) {
            super.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                    if (n >= RefreshingListView.this.getContentStartIndex()) {
                        adapterView.OnItemSelectedListener.onItemSelected((AdapterView)adapterView, view, n - RefreshingListView.this.getContentStartIndex(), n2);
                    }
                }
                
                public void onNothingSelected(final AdapterView<?> adapterView) {
                    adapterView.OnItemSelectedListener.onNothingSelected((AdapterView)adapterView);
                }
            });
            return;
        }
        super.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)null);
    }
    
    public void setOnScrollListener(final AbsListView.OnScrollListener onScrollListener) {
        this._onScrollListener = onScrollListener;
    }
    
    public void updateFinished() {
        this._footerLoadingSpinner.clearAnimation();
        this._headerView.setState(RefreshingListHeaderView.State.PULLDOWN);
        if (this.getFirstVisiblePosition() <= 1) {
            this.tryToScrollBack();
        }
        this._status = -1;
        this._footerLoadingSpinner.setVisibility(8);
        this.updateHeader();
    }
    
    public void updateStarted() {
        this._status = 1;
        this._headerView.setState(RefreshingListHeaderView.State.REFRESHING);
        this._footerLoadingSpinner.setVisibility(0);
    }
}
