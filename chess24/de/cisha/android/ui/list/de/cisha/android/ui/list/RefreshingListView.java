/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.ListView$FixedViewInfo
 *  android.widget.ProgressBar
 */
package de.cisha.android.ui.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.cisha.android.ui.list.MutableHeaderViewListAdapter;
import de.cisha.android.ui.list.RefreshingListHeaderView;
import de.cisha.android.ui.list.RefreshingListViewFooterView;
import de.cisha.android.ui.list.UpdatingList;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public abstract class RefreshingListView
extends ListView
implements UpdatingList,
AbsListView.OnScrollListener,
View.OnClickListener {
    private static final int LIST_START_INDEX = 2;
    private static final int STATUS_IDLE = -1;
    private static final int STATUS_REFRESHING = 1;
    private static final int STATUS_SCROLLING = 0;
    private ProgressBar _footerLoadingSpinner;
    private RefreshingListViewFooterView _footerView;
    private RefreshingListHeaderView _headerView;
    private boolean _headersAdded = false;
    private boolean _isShowingButton = false;
    private MutableHeaderViewListAdapter _listadapter;
    private AbsListView.OnScrollListener _onScrollListener;
    private View _overHeaderView;
    private int _scrollState;
    private boolean _scrollingOverscrollBack = false;
    private boolean _showFooter = true;
    private boolean _showHeader = true;
    private int _status = -1;

    public RefreshingListView(Context context) {
        super(context);
        this.init();
    }

    public RefreshingListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public RefreshingListView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
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
        this._listadapter = new MutableHeaderViewListAdapter();
        super.setAdapter((ListAdapter)this._listadapter);
        this._footerView = new RefreshingListViewFooterView(this.getContext());
        this._headerView = new RefreshingListHeaderView(this.getContext());
        this._overHeaderView = View.inflate((Context)this.getContext(), (int)R.layout.emptyrow, null);
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
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter != null) {
            if (this.getLastVisiblePosition() - this.getFirstVisiblePosition() >= listAdapter.getCount() - 1) {
                return true;
            }
            return false;
        }
        return true;
    }

    private void measureHeader() {
        int n;
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = layoutParams = this._headerView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new ViewGroup.LayoutParams(-1, -2);
        }
        int n2 = ViewGroup.getChildMeasureSpec((int)0, (int)0, (int)layoutParams2.width);
        if (this._headerView.getVisibility() != 8) {
            n = layoutParams2.height > 0 ? View.MeasureSpec.makeMeasureSpec((int)layoutParams2.height, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        } else {
            n = View.MeasureSpec.makeMeasureSpec((int)0, (int)1073741824);
            n2 = View.MeasureSpec.makeMeasureSpec((int)0, (int)1073741824);
        }
        this._headerView.measure(n2, n);
    }

    private void removeHeaders() {
        if (this._headersAdded) {
            this._headersAdded = false;
            this.removeHeaderView(this._overHeaderView);
            this.removeHeaderView((View)this._headerView);
        }
    }

    private void tryToScrollBack() {
        this.post(new Runnable(){

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
                this.post(new Runnable(){

                    @Override
                    public void run() {
                        int n = RefreshingListView.this._headerView.getBottom();
                        RefreshingListView.this.smoothScrollBy(n, 1000);
                    }
                });
            }
        }
    }

    private void updateHeader() {
        if (this._showHeader) {
            boolean bl = this.isEverythingShown();
            if (this._isShowingButton != bl) {
                this._isShowingButton = bl;
                this._footerView.showButton(bl);
                if (bl) {
                    this.removeHeaders();
                } else {
                    this.addHeaders();
                }
                this.tryToScrollBack();
                return;
            }
        } else {
            this.removeHeaders();
        }
    }

    public void addFooterView(View view) {
        ListView.FixedViewInfo fixedViewInfo = new ListView.FixedViewInfo((ListView)this);
        fixedViewInfo.data = null;
        fixedViewInfo.isSelectable = false;
        fixedViewInfo.view = view;
        this._listadapter.addFooter(fixedViewInfo);
    }

    public void addHeaderView(View view) {
        ListView.FixedViewInfo fixedViewInfo = new ListView.FixedViewInfo((ListView)this);
        fixedViewInfo.data = null;
        fixedViewInfo.isSelectable = false;
        fixedViewInfo.view = view;
        this._listadapter.addHeader(fixedViewInfo);
    }

    @Override
    public void disableFooter() {
        synchronized (this) {
            if (this._showFooter) {
                this._showFooter = false;
                this._footerView.post(new Runnable(){

                    @Override
                    public void run() {
                        RefreshingListView.this.removeFooterView((View)RefreshingListView.this._footerView);
                    }
                });
            }
            return;
        }
    }

    @Override
    public void disableHeader() {
        final int n = this.getFirstVisiblePosition();
        this._showHeader = false;
        this._headerView.post(new Runnable(){

            @Override
            public void run() {
                RefreshingListView.this.removeHeaders();
                if (n <= 1) {
                    RefreshingListView.this.setSelection(RefreshingListView.this.getContentStartIndex());
                }
            }
        });
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int n = this.getFirstVisiblePosition();
        if (this._showHeader && !this.isEverythingShown() && n <= 1 && this._status != 1) {
            n = this._headerView.getHeight() < this._headerView.getBottom() ? 1 : 0;
            RefreshingListHeaderView refreshingListHeaderView = this._headerView;
            RefreshingListHeaderView.State state = n != 0 ? RefreshingListHeaderView.State.FINGER_UP : RefreshingListHeaderView.State.PULLDOWN;
            refreshingListHeaderView.setState(state);
            if (motionEvent.getAction() == 1) {
                if (n != 0 && this._status != 1) {
                    this.headerReached();
                } else {
                    this.tryToScrollBack();
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public void enableFooter() {
        synchronized (this) {
            if (!this._showFooter) {
                this.addFooterView((View)this._footerView);
            }
            this._showFooter = true;
            return;
        }
    }

    @Override
    public void enableHeader() {
        this._showHeader = true;
        this._headerView.post(new Runnable(){

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

    public void onClick(View view) {
        if (this._status != 1) {
            this.headerReached();
        }
    }

    public void onScroll(AbsListView absListView, int n, int n2, int n3) {
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

    public void onScrollStateChanged(AbsListView absListView, int n) {
        this._scrollState = n;
        if (this._scrollState != 0) {
            this._scrollingOverscrollBack = false;
        }
        if (this._onScrollListener != null) {
            this._onScrollListener.onScrollStateChanged(absListView, n);
        }
    }

    public boolean removeFooterView(View view) {
        return this._listadapter.removeFooter(view);
    }

    public boolean removeHeaderView(View view) {
        return this._listadapter.removeHeader(view);
    }

    public void setAdapter(ListAdapter listAdapter) {
        this._listadapter.setAdapter(listAdapter);
        this.setSelection(this.getContentStartIndex());
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            super.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    if (n >= RefreshingListView.this.getContentStartIndex()) {
                        onItemClickListener.onItemClick(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
                    }
                }
            });
            return;
        }
        super.setOnItemClickListener(null);
    }

    public void setOnItemLongClickListener(final AdapterView.OnItemLongClickListener onItemLongClickListener) {
        if (onItemLongClickListener != null) {
            super.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                    if (n >= RefreshingListView.this.getContentStartIndex()) {
                        return onItemLongClickListener.onItemLongClick(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
                    }
                    return false;
                }
            });
            return;
        }
        super.setOnItemLongClickListener(null);
    }

    public void setOnItemSelectedListener(final AdapterView.OnItemSelectedListener onItemSelectedListener) {
        if (onItemSelectedListener != null) {
            super.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                    if (n >= RefreshingListView.this.getContentStartIndex()) {
                        onItemSelectedListener.onItemSelected(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
                    }
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    onItemSelectedListener.onNothingSelected(adapterView);
                }
            });
            return;
        }
        super.setOnItemSelectedListener(null);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this._onScrollListener = onScrollListener;
    }

    @Override
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

    @Override
    public void updateStarted() {
        this._status = 1;
        this._headerView.setState(RefreshingListHeaderView.State.REFRESHING);
        this._footerLoadingSpinner.setVisibility(0);
    }

}
