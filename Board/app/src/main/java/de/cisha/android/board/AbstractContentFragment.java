// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Iterator;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import java.util.LinkedList;
import android.view.View;
import java.util.List;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import de.cisha.android.board.modalfragments.RookieDialogLoading;

public abstract class AbstractContentFragment extends BaseFragment implements IContentView
{
    private IContentPresenter _contentPresenter;
    private IErrorPresenter _errorPresenter;
    private boolean _flagOnSaveInstanceStateCalled;
    private boolean _flagRemoveLoadingDialogOnResume;
    private ILoadingPresenter _loadingPresenter;
    private RookieDialogLoading _networkLoadingDialog;
    private Runnable _showLoadingViewAction;
    private Set<StateHolder> _stateHolders;
    
    public AbstractContentFragment() {
        this._stateHolders = Collections.newSetFromMap(new WeakHashMap<StateHolder, Boolean>());
        this._flagOnSaveInstanceStateCalled = false;
    }
    
    private void setContentPresenter(final IContentPresenter contentPresenter) {
        this._contentPresenter = contentPresenter;
    }
    
    private void setErrorHandler(final IErrorPresenter errorPresenter) {
        this._errorPresenter = errorPresenter;
    }
    
    private void setLoadingPresenter(final ILoadingPresenter loadingPresenter) {
        this._loadingPresenter = loadingPresenter;
    }
    
    private RookieDialogLoading showDialog(final boolean cancelable, final String s, final String s2, final RookieDialogLoading.OnCancelListener onCancelListener, final boolean b) {
        final RookieDialogLoading rookieDialogLoading = new RookieDialogLoading();
        rookieDialogLoading.setCancelable(cancelable);
        final FragmentManager childFragmentManager = this.getChildFragmentManager();
        if (!this.isRemoving() && childFragmentManager != null) {
            rookieDialogLoading.setOnCancelListener((RookieDialogLoading.OnCancelListener)new RookieDialogLoading.OnCancelListener() {
                @Override
                public void onCancel() {
                    if (cancelable) {
                        AbstractContentFragment.this.hideDialogWaiting();
                        if (b) {
                            final IContentPresenter contentPresenter = AbstractContentFragment.this.getContentPresenter();
                            if (contentPresenter != null) {
                                contentPresenter.popCurrentFragment();
                            }
                        }
                        AbstractContentFragment.this.onLoadingDialogCancelled();
                        if (onCancelListener != null) {
                            onCancelListener.onCancel();
                        }
                    }
                }
            });
            rookieDialogLoading.show(childFragmentManager, "waitingdialog");
            childFragmentManager.executePendingTransactions();
        }
        return rookieDialogLoading;
    }
    
    protected void addStateHolder(final StateHolder stateHolder) {
        this._stateHolders.add(stateHolder);
    }
    
    protected ImageView createNavbarButtonForRessource(final Context context, final int imageResource) {
        final ImageView imageView = (ImageView)((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427462, (ViewGroup)new LinearLayout(context), false);
        imageView.setImageResource(imageResource);
        return imageView;
    }
    
    protected IContentPresenter getContentPresenter() {
        return this._contentPresenter;
    }
    
    protected IErrorPresenter getErrorHandler() {
        return this._errorPresenter;
    }
    
    @Override
    public List<View> getLeftButtons(final Context context) {
        return new LinkedList<View>();
    }
    
    protected ILoadingPresenter getLoadingPresenter() {
        return this._loadingPresenter;
    }
    
    public int getOrientation() {
        return 2;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        return new LinkedList<View>();
    }
    
    public abstract String getTrackingName();
    
    public void hideDialogWaiting() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final FragmentManager childFragmentManager = AbstractContentFragment.this.getChildFragmentManager();
                if (AbstractContentFragment.this._networkLoadingDialog != null && childFragmentManager != null) {
                    if (AbstractContentFragment.this.isAdded() && !AbstractContentFragment.this.isRemoving() && AbstractContentFragment.this._networkLoadingDialog.isAdded() && !AbstractContentFragment.this._networkLoadingDialog.isRemoving()) {
                        AbstractContentFragment.this._networkLoadingDialog.dismissAllowingStateLoss();
                        AbstractContentFragment.this._networkLoadingDialog = null;
                    }
                    else {
                        AbstractContentFragment.this._flagRemoveLoadingDialogOnResume = true;
                    }
                }
                AbstractContentFragment.this._showLoadingViewAction = null;
            }
        });
    }
    
    protected void hideKeyboard() {
        if (this.getActivity() != null) {
            final View currentFocus = this.getActivity().getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
            }
        }
    }
    
    public boolean isLoading() {
        return !this._flagRemoveLoadingDialogOnResume && this._networkLoadingDialog != null;
    }
    
    @Override
    public void onActivityCreated(@Nullable final Bundle bundle) {
        super.onActivityCreated(bundle);
        final ILoadingPresenter loadingPresenter = this._loadingPresenter;
        final View view = this.getView();
        if (loadingPresenter != null && view != null) {
            view.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    loadingPresenter.hideLoadingView();
                }
            });
        }
    }
    
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IContentPresenter) {
            this.setContentPresenter((IContentPresenter)activity);
        }
        if (activity instanceof IErrorPresenter) {
            this.setErrorHandler((IErrorPresenter)activity);
        }
        if (activity instanceof ILoadingPresenter) {
            this.setLoadingPresenter((ILoadingPresenter)activity);
        }
    }
    
    @Override
    public boolean onBackPressed() {
        return false;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setRetainInstance(true);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onDestroyView() {
        if (this._networkLoadingDialog != null && this._networkLoadingDialog.isAdded() && !this._networkLoadingDialog.isRemoving()) {
            this.getChildFragmentManager().beginTransaction().remove(this._networkLoadingDialog).commitAllowingStateLoss();
            this._networkLoadingDialog = null;
        }
        super.onDestroyView();
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        this._errorPresenter = null;
        this._contentPresenter = null;
        this._loadingPresenter = null;
    }
    
    protected void onLoadingDialogCancelled() {
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (this._flagRemoveLoadingDialogOnResume) {
            if (this._networkLoadingDialog != null) {
                this._networkLoadingDialog.dismissAllowingStateLoss();
            }
            this._networkLoadingDialog = null;
            this._flagRemoveLoadingDialogOnResume = false;
        }
    }
    
    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        final Iterator<StateHolder> iterator = this._stateHolders.iterator();
        while (iterator.hasNext()) {
            iterator.next().saveState(bundle);
        }
        this._flagOnSaveInstanceStateCalled = true;
    }
    
    public boolean onSavenInstanceStateCalled() {
        return this._flagOnSaveInstanceStateCalled;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this._flagOnSaveInstanceStateCalled = false;
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen(this);
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            final Iterator<StateHolder> iterator = this._stateHolders.iterator();
            while (iterator.hasNext()) {
                iterator.next().restoreState(bundle);
            }
        }
        if (this._showLoadingViewAction != null) {
            this._showLoadingViewAction.run();
        }
    }
    
    protected void showDialogWaiting(final boolean b, final boolean b2, final String s, final RookieDialogLoading.OnCancelListener onCancelListener) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (!AbstractContentFragment.this._flagOnSaveInstanceStateCalled) {
                    AbstractContentFragment.this._networkLoadingDialog = AbstractContentFragment.this.showDialog(b, "", s, onCancelListener, b2);
                }
                AbstractContentFragment.this._showLoadingViewAction = this;
            }
        });
    }
    
    protected void showViewForErrorCode(final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction) {
        this.showViewForErrorCode(apiStatusCode, reloadAction, false);
    }
    
    protected void showViewForErrorCode(final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction, final IErrorPresenter.ICancelAction cancelAction) {
        if (this._errorPresenter != null) {
            this._errorPresenter.showViewForErrorCode(apiStatusCode, reloadAction, cancelAction);
        }
    }
    
    protected void showViewForErrorCode(final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction, final boolean b) {
        if (this._errorPresenter != null) {
            this._errorPresenter.showViewForErrorCode(apiStatusCode, reloadAction, b);
        }
    }
}
