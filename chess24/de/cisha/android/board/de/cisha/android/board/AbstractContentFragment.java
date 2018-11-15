/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IContentView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.ILoadingPresenter;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class AbstractContentFragment
extends BaseFragment
implements IContentView {
    private IContentPresenter _contentPresenter;
    private IErrorPresenter _errorPresenter;
    private boolean _flagOnSaveInstanceStateCalled = false;
    private boolean _flagRemoveLoadingDialogOnResume;
    private ILoadingPresenter _loadingPresenter;
    private RookieDialogLoading _networkLoadingDialog;
    private Runnable _showLoadingViewAction;
    private Set<StateHolder> _stateHolders = Collections.newSetFromMap(new WeakHashMap());

    private void setContentPresenter(IContentPresenter iContentPresenter) {
        this._contentPresenter = iContentPresenter;
    }

    private void setErrorHandler(IErrorPresenter iErrorPresenter) {
        this._errorPresenter = iErrorPresenter;
    }

    private void setLoadingPresenter(ILoadingPresenter iLoadingPresenter) {
        this._loadingPresenter = iLoadingPresenter;
    }

    private RookieDialogLoading showDialog(final boolean bl, String object, String object2, final RookieDialogLoading.OnCancelListener onCancelListener, final boolean bl2) {
        object = new RookieDialogLoading();
        object.setCancelable(bl);
        object2 = this.getChildFragmentManager();
        if (!this.isRemoving() && object2 != null) {
            object.setOnCancelListener(new RookieDialogLoading.OnCancelListener(){

                @Override
                public void onCancel() {
                    if (bl) {
                        IContentPresenter iContentPresenter;
                        AbstractContentFragment.this.hideDialogWaiting();
                        if (bl2 && (iContentPresenter = AbstractContentFragment.this.getContentPresenter()) != null) {
                            iContentPresenter.popCurrentFragment();
                        }
                        AbstractContentFragment.this.onLoadingDialogCancelled();
                        if (onCancelListener != null) {
                            onCancelListener.onCancel();
                        }
                    }
                }
            });
            object.show((FragmentManager)object2, "waitingdialog");
            object2.executePendingTransactions();
        }
        return object;
    }

    protected void addStateHolder(StateHolder stateHolder) {
        this._stateHolders.add(stateHolder);
    }

    protected ImageView createNavbarButtonForRessource(Context context, int n) {
        context = (ImageView)((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427462, (ViewGroup)new LinearLayout(context), false);
        context.setImageResource(n);
        return context;
    }

    protected IContentPresenter getContentPresenter() {
        return this._contentPresenter;
    }

    protected IErrorPresenter getErrorHandler() {
        return this._errorPresenter;
    }

    @Override
    public List<View> getLeftButtons(Context context) {
        return new LinkedList<View>();
    }

    protected ILoadingPresenter getLoadingPresenter() {
        return this._loadingPresenter;
    }

    public int getOrientation() {
        return 2;
    }

    @Override
    public List<View> getRightButtons(Context context) {
        return new LinkedList<View>();
    }

    public abstract String getTrackingName();

    public void hideDialogWaiting() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                FragmentManager fragmentManager = AbstractContentFragment.this.getChildFragmentManager();
                if (AbstractContentFragment.this._networkLoadingDialog != null && fragmentManager != null) {
                    if (AbstractContentFragment.this.isAdded() && !AbstractContentFragment.this.isRemoving() && AbstractContentFragment.this._networkLoadingDialog.isAdded() && !AbstractContentFragment.this._networkLoadingDialog.isRemoving()) {
                        AbstractContentFragment.this._networkLoadingDialog.dismissAllowingStateLoss();
                        AbstractContentFragment.this._networkLoadingDialog = null;
                    } else {
                        AbstractContentFragment.this._flagRemoveLoadingDialogOnResume = true;
                    }
                }
                AbstractContentFragment.this._showLoadingViewAction = null;
            }
        });
    }

    protected void hideKeyboard() {
        View view;
        if (this.getActivity() != null && (view = this.getActivity().getCurrentFocus()) != null) {
            ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 2);
        }
    }

    public boolean isLoading() {
        if (!this._flagRemoveLoadingDialogOnResume && this._networkLoadingDialog != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle object) {
        super.onActivityCreated((Bundle)object);
        object = this._loadingPresenter;
        View view = this.getView();
        if (object != null && view != null) {
            view.post(new Runnable((ILoadingPresenter)object){
                final /* synthetic */ ILoadingPresenter val$loadingPresenter;
                {
                    this.val$loadingPresenter = iLoadingPresenter;
                }

                @Override
                public void run() {
                    this.val$loadingPresenter.hideLoadingView();
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
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
    public void onCreate(Bundle bundle) {
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
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Iterator<StateHolder> iterator = this._stateHolders.iterator();
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
    public void onViewCreated(View object, @Nullable Bundle bundle) {
        super.onViewCreated((View)object, bundle);
        if (bundle != null) {
            object = this._stateHolders.iterator();
            while (object.hasNext()) {
                ((StateHolder)object.next()).restoreState(bundle);
            }
        }
        if (this._showLoadingViewAction != null) {
            this._showLoadingViewAction.run();
        }
    }

    protected void showDialogWaiting(final boolean bl, final boolean bl2, final String string, final RookieDialogLoading.OnCancelListener onCancelListener) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (!AbstractContentFragment.this._flagOnSaveInstanceStateCalled) {
                    AbstractContentFragment.this._networkLoadingDialog = AbstractContentFragment.this.showDialog(bl, "", string, onCancelListener, bl2);
                }
                AbstractContentFragment.this._showLoadingViewAction = this;
            }
        });
    }

    protected void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction) {
        this.showViewForErrorCode(aPIStatusCode, iReloadAction, false);
    }

    protected void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction, IErrorPresenter.ICancelAction iCancelAction) {
        if (this._errorPresenter != null) {
            this._errorPresenter.showViewForErrorCode(aPIStatusCode, iReloadAction, iCancelAction);
        }
    }

    protected void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction, boolean bl) {
        if (this._errorPresenter != null) {
            this._errorPresenter.showViewForErrorCode(aPIStatusCode, iReloadAction, bl);
        }
    }

}
