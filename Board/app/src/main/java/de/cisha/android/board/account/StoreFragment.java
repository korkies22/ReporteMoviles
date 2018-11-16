// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account;

import android.widget.ImageView;
import de.cisha.android.board.account.view.PremiumFlagDrawable;
import android.view.View.OnClickListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import android.graphics.drawable.Drawable;
import de.cisha.android.board.account.view.TwoColorDiagonalDividerDrawable;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.app.Activity;
import de.cisha.android.board.user.User;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import java.util.ArrayList;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import java.util.Date;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.view.ViewGroup;
import android.view.View;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.ModelHolder;
import java.util.List;
import de.cisha.android.board.account.view.PurchaseDialog;
import de.cisha.android.board.AbstractContentFragment;

public class StoreFragment extends AbstractContentFragment implements IPurchaseDialogListener, PurchaseResultReceiver
{
    private List<Feature> _featureList;
    protected boolean _flagLoadingProductsFailed;
    private boolean _flagUserIsGuest;
    private ModelHolder<List<Product>> _mobileSubscriptionHolder;
    private View _subscriptionCancelView;
    private ViewGroup _subscriptionViewGroup;
    private ModelHolder<List<Product>> _webSubscriptionHolder;
    
    private void addSubscriptionView(final SubscriptionType subscriptionType, final Date date) {
        final View inflate = LayoutInflater.from((Context)this.getActivity()).inflate(2131427554, this._subscriptionViewGroup, false);
        int text;
        if (subscriptionType == SubscriptionType.PREMIUM) {
            text = 2131690301;
        }
        else {
            text = 2131690302;
        }
        ((TextView)inflate.findViewById(2131297075)).setText(text);
        ((TextView)inflate.findViewById(2131297074)).setText((CharSequence)this.getResources().getString(2131690342, new Object[] { DateFormat.getDateFormat((Context)this.getActivity()).format(date) }));
        final View viewById = inflate.findViewById(2131297073);
        int backgroundResource;
        if (subscriptionType == SubscriptionType.PREMIUM) {
            backgroundResource = 2131099707;
        }
        else {
            backgroundResource = 2131099704;
        }
        viewById.setBackgroundResource(backgroundResource);
        this._subscriptionViewGroup.addView(inflate);
    }
    
    private List<Feature> createFeatureList() {
        final ArrayList<Feature> list = new ArrayList<Feature>(7);
        list.add(new Feature(2131690340, 2131690336, 2131690338, 2131690337, 2131690339, 2131231022));
        list.add(new Feature(2131690330, 2131690326, 2131690328, 2131690327, 2131690329, 2131231021));
        list.add(new Feature(2131690310, 2131690306, 2131690308, 2131690307, 2131690309, 2131231015));
        list.add(new Feature(2131690320, 2131690316, 2131690318, 2131690317, 2131690319, 2131231016));
        list.add(new Feature(2131690335, 2131690331, 2131690333, 2131690332, 2131690334, 2131231019));
        list.add(new Feature(2131690325, 2131690321, 2131690323, 2131690322, 2131690324, 2131231020));
        list.add(new Feature(2131690315, 2131690311, 2131690313, 2131690312, 2131690314, 2131231017));
        return list;
    }
    
    private void loadProducts() {
        ServiceProvider.getInstance().getPurchaseService().getAllPremiumSubscriptionProducts((Context)this.getActivity(), new LoadCommandCallbackWithTimeout<List<Product>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                StoreFragment.this._flagLoadingProductsFailed = true;
            }
            
            @Override
            protected void succeded(final List<Product> list) {
                final LinkedList<Product> model = new LinkedList<Product>();
                final LinkedList<Product> model2 = new LinkedList<Product>();
                for (final Product product : list) {
                    if (product.getProductInformation().isWebPremium()) {
                        model2.add(product);
                    }
                    else {
                        model.add(product);
                    }
                }
                StoreFragment.this._mobileSubscriptionHolder.setModel(model);
                StoreFragment.this._webSubscriptionHolder.setModel(model2);
            }
        });
    }
    
    private void openPlayStore() {
        final String packageName = this.getActivity().getPackageName();
        while (true) {
            try {
                final StringBuilder sb = new StringBuilder();
                sb.append("market://details?id=");
                sb.append(packageName);
                this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                return;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("https://play.google.com/store/apps/details?id=");
                sb2.append(packageName);
                this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
            }
            catch (ActivityNotFoundException ex) {
                continue;
            }
            break;
        }
    }
    
    private void purchaseProduct(final Product product) {
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getPurchaseService().purchaseProduct(this.getActivity(), product, new LoadCommandCallback<AfterPurchaseInformation>() {
            @Override
            public void loadingCancelled() {
                StoreFragment.this.hideDialogWaiting();
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                StoreFragment.this.hideDialogWaiting();
                StoreFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        StoreFragment.this.purchaseProduct(product);
                    }
                });
            }
            
            @Override
            public void loadingSucceded(final AfterPurchaseInformation afterPurchaseInformation) {
                StoreFragment.this.hideDialogWaiting();
                final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
                if (currentUserData != null) {
                    SubscriptionType subscriptionType;
                    if (product.getProductInformation().isWebPremium()) {
                        subscriptionType = SubscriptionType.PREMIUM;
                    }
                    else {
                        subscriptionType = SubscriptionType.PREMIUM_MOBILE;
                    }
                    final List<Subscription> subscriptions = currentUserData.getSubscriptions();
                    subscriptions.add(new Subscription(subscriptionType, afterPurchaseInformation.getValidUntil(), true, Subscription.SubscriptionOrigin.MOBILE, Subscription.SubscriptionProvider.GOOGLE));
                    currentUserData.setSubscriptions(subscriptions);
                }
                if (afterPurchaseInformation != null && StoreFragment.this.getView() != null) {
                    StoreFragment.this.getView().post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            StoreFragment.this.updateViewsToPremiumState();
                        }
                    });
                }
                ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>() {
                    @Override
                    public void loadingCancelled() {
                        StoreFragment.this.updateViewsToPremiumState();
                    }
                    
                    @Override
                    public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        StoreFragment.this.updateViewsToPremiumState();
                    }
                    
                    @Override
                    public void loadingSucceded(final User user) {
                        StoreFragment.this.updateViewsToPremiumState();
                    }
                });
            }
        });
    }
    
    private void restoreItems() {
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getPurchaseService().restorePurchases((Context)this.getActivity(), new LoadCommandCallback<AfterPurchaseInformation>() {
            @Override
            public void loadingCancelled() {
                StoreFragment.this.hideDialogWaiting();
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                StoreFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        StoreFragment.this.restoreItems();
                    }
                });
                StoreFragment.this.hideDialogWaiting();
            }
            
            @Override
            public void loadingSucceded(final AfterPurchaseInformation afterPurchaseInformation) {
                StoreFragment.this.updateViewsToPremiumState();
                StoreFragment.this.hideDialogWaiting();
            }
        });
    }
    
    private void showPurchaseDialog(final ModelHolder<List<Product>> modelHolder, final boolean b, final int n, final int n2) {
        final PurchaseDialog instance = PurchaseDialog.createInstance(n, n2, b, modelHolder, (PurchaseDialog.IPurchaseDialogListener)this);
        instance.show(this.getChildFragmentManager(), PurchaseDialog.class.getName());
        instance.setCancelable(true);
        if (this._flagLoadingProductsFailed) {
            this._flagLoadingProductsFailed = false;
            this.loadProducts();
        }
    }
    
    private void updateViewsToPremiumState() {
        this._subscriptionViewGroup.removeAllViews();
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (currentUserData != null) {
            final Date date = new Date();
            final Subscription subscription = currentUserData.getSubscription(SubscriptionType.PREMIUM);
            boolean b = true;
            final boolean b2 = subscription != null && subscription.getExpirationDate().after(date);
            final Subscription subscription2 = currentUserData.getSubscription(SubscriptionType.PREMIUM_MOBILE);
            if (subscription2 == null || !subscription2.getExpirationDate().after(date)) {
                b = false;
            }
            if (b2) {
                this.addSubscriptionView(SubscriptionType.PREMIUM, subscription.getExpirationDate());
            }
            if (b) {
                this.addSubscriptionView(SubscriptionType.PREMIUM_MOBILE, subscription2.getExpirationDate());
            }
            if ((b2 && subscription.getProvider() == Subscription.SubscriptionProvider.GOOGLE) || (b && subscription2.getProvider() == Subscription.SubscriptionProvider.GOOGLE)) {
                this._subscriptionCancelView.setVisibility(0);
                return;
            }
            this._subscriptionCancelView.setVisibility(8);
        }
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131689507);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.SUBSCRIPTIONS;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    @Override
    public String getTrackingName() {
        return "AccountType";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        if (!ServiceProvider.getInstance().getPurchaseService().handleActivityResult(n, n2, intent)) {
            super.onActivityResult(n, n2, intent);
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        this._flagUserIsGuest = (currentUserData != null && currentUserData.isGuest());
        this._featureList = this.createFeatureList();
        this._mobileSubscriptionHolder = new ModelHolder<List<Product>>(new LinkedList<Product>());
        this._webSubscriptionHolder = new ModelHolder<List<Product>>(new LinkedList<Product>());
        this.loadProducts();
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427551, viewGroup, false);
        inflate.findViewById(2131297053).setBackgroundDrawable((Drawable)new TwoColorDiagonalDividerDrawable((Context)this.getActivity(), 2131099707, 2131099704));
        return inflate;
    }
    
    @Override
    public void onRestorePressed() {
        this.restoreItems();
    }
    
    @Override
    public void onSelectProduct(final Product product) {
        if (this._flagUserIsGuest) {
            this.getContentPresenter().showConversionDialog(null, ConversionContext.ACCOUNT_TYPE);
            return;
        }
        this.purchaseProduct(product);
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(2131297070);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this.getActivity(), this.getActivity().getResources().getInteger(2131361809)));
        recyclerView.setAdapter((RecyclerView.Adapter)new StoreListAdapter());
        view.findViewById(2131297052).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                StoreFragment.this.showPurchaseDialog(StoreFragment.this._webSubscriptionHolder, ServiceProvider.getInstance().getProfileDataService().getCurrentUserData().getSubscription(SubscriptionType.PREMIUM) == null, 2131690301, 2131690304);
            }
        });
        view.findViewById(2131297051).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
                StoreFragment.this.showPurchaseDialog(StoreFragment.this._mobileSubscriptionHolder, currentUserData.getSubscription(SubscriptionType.PREMIUM_MOBILE) == null && currentUserData.getSubscription(SubscriptionType.PREMIUM) == null, 2131690302, 2131690305);
            }
        });
        this._subscriptionViewGroup = (ViewGroup)view.findViewById(2131297072);
        (this._subscriptionCancelView = view.findViewById(2131297071)).setVisibility(8);
        this._subscriptionCancelView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                StoreFragment.this.openPlayStore();
            }
        });
        this.updateViewsToPremiumState();
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    private static class Feature
    {
        private int _descrRes;
        private int _imageRes;
        private int _mobilePremiumRes;
        private int _registeredRes;
        private int _titleRes;
        private int _webPremiumRes;
        
        public Feature(final int titleRes, final int descrRes, final int webPremiumRes, final int mobilePremiumRes, final int registeredRes, final int imageRes) {
            this._titleRes = titleRes;
            this._descrRes = descrRes;
            this._webPremiumRes = webPremiumRes;
            this._mobilePremiumRes = mobilePremiumRes;
            this._registeredRes = registeredRes;
            this._imageRes = imageRes;
        }
    }
    
    private class StoreListAdapter extends Adapter<ViewHolder>
    {
        @Override
        public int getItemCount() {
            return StoreFragment.this._featureList.size();
        }
        
        public void onBindViewHolder(final ViewHolder viewHolder, final int n) {
            final Feature feature = StoreFragment.this._featureList.get(n);
            viewHolder._titleTextView.setText(feature._titleRes);
            viewHolder._descriptionTextView.setText(feature._descrRes);
            viewHolder._premiumWebTextView.setText(feature._webPremiumRes);
            viewHolder._premiumMobileTextView.setText(feature._mobilePremiumRes);
            viewHolder._registeredTextView.setText(feature._registeredRes);
            viewHolder._imageView.setImageResource(feature._imageRes);
        }
        
        public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            final View inflate = LayoutInflater.from((Context)StoreFragment.this.getActivity()).inflate(2131427552, viewGroup, false);
            inflate.findViewById(2131297054).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099704));
            inflate.findViewById(2131297055).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099707));
            inflate.findViewById(2131297056).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099710));
            return new ViewHolder(inflate);
        }
        
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView _descriptionTextView;
            private ImageView _imageView;
            private TextView _premiumMobileTextView;
            private TextView _premiumWebTextView;
            private TextView _registeredTextView;
            private TextView _titleTextView;
            
            public ViewHolder(final View view) {
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
}
