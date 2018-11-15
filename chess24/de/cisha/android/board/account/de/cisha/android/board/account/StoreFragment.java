/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.format.DateFormat
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.account;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.account.PurchaseResultReceiver;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.account.view.PremiumFlagDrawable;
import de.cisha.android.board.account.view.PurchaseDialog;
import de.cisha.android.board.account.view.TwoColorDiagonalDividerDrawable;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class StoreFragment
extends AbstractContentFragment
implements PurchaseDialog.IPurchaseDialogListener,
PurchaseResultReceiver {
    private List<Feature> _featureList;
    protected boolean _flagLoadingProductsFailed;
    private boolean _flagUserIsGuest;
    private ModelHolder<List<Product>> _mobileSubscriptionHolder;
    private View _subscriptionCancelView;
    private ViewGroup _subscriptionViewGroup;
    private ModelHolder<List<Product>> _webSubscriptionHolder;

    private void addSubscriptionView(SubscriptionType subscriptionType, Date object) {
        View view = LayoutInflater.from((Context)this.getActivity()).inflate(2131427554, this._subscriptionViewGroup, false);
        int n = subscriptionType == SubscriptionType.PREMIUM ? 2131690301 : 2131690302;
        ((TextView)view.findViewById(2131297075)).setText(n);
        object = DateFormat.getDateFormat((Context)this.getActivity()).format((Date)object);
        ((TextView)view.findViewById(2131297074)).setText((CharSequence)this.getResources().getString(2131690342, new Object[]{object}));
        object = view.findViewById(2131297073);
        n = subscriptionType == SubscriptionType.PREMIUM ? 2131099707 : 2131099704;
        object.setBackgroundResource(n);
        this._subscriptionViewGroup.addView(view);
    }

    private List<Feature> createFeatureList() {
        ArrayList<Feature> arrayList = new ArrayList<Feature>(7);
        arrayList.add(new Feature(2131690340, 2131690336, 2131690338, 2131690337, 2131690339, 2131231022));
        arrayList.add(new Feature(2131690330, 2131690326, 2131690328, 2131690327, 2131690329, 2131231021));
        arrayList.add(new Feature(2131690310, 2131690306, 2131690308, 2131690307, 2131690309, 2131231015));
        arrayList.add(new Feature(2131690320, 2131690316, 2131690318, 2131690317, 2131690319, 2131231016));
        arrayList.add(new Feature(2131690335, 2131690331, 2131690333, 2131690332, 2131690334, 2131231019));
        arrayList.add(new Feature(2131690325, 2131690321, 2131690323, 2131690322, 2131690324, 2131231020));
        arrayList.add(new Feature(2131690315, 2131690311, 2131690313, 2131690312, 2131690314, 2131231017));
        return arrayList;
    }

    private void loadProducts() {
        ServiceProvider.getInstance().getPurchaseService().getAllPremiumSubscriptionProducts((Context)this.getActivity(), (LoadCommandCallback<List<Product>>)new LoadCommandCallbackWithTimeout<List<Product>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                StoreFragment.this._flagLoadingProductsFailed = true;
            }

            @Override
            protected void succeded(List<Product> object) {
                LinkedList<Product> linkedList = new LinkedList<Product>();
                LinkedList<Product> linkedList2 = new LinkedList<Product>();
                object = object.iterator();
                while (object.hasNext()) {
                    Product product = (Product)object.next();
                    if (product.getProductInformation().isWebPremium()) {
                        linkedList2.add(product);
                        continue;
                    }
                    linkedList.add(product);
                }
                StoreFragment.this._mobileSubscriptionHolder.setModel(linkedList);
                StoreFragment.this._webSubscriptionHolder.setModel(linkedList2);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void openPlayStore() {
        String string = this.getActivity().getPackageName();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://details?id=");
            stringBuilder.append(string);
            this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString())));
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://play.google.com/store/apps/details?id=");
        stringBuilder.append(string);
        this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString())));
    }

    private void purchaseProduct(final Product product) {
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getPurchaseService().purchaseProduct(this.getActivity(), product, new LoadCommandCallback<AfterPurchaseInformation>(){

            @Override
            public void loadingCancelled() {
                StoreFragment.this.hideDialogWaiting();
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                StoreFragment.this.hideDialogWaiting();
                StoreFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        StoreFragment.this.purchaseProduct(product);
                    }
                });
            }

            @Override
            public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
                StoreFragment.this.hideDialogWaiting();
                User user = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
                if (user != null) {
                    SubscriptionType subscriptionType = product.getProductInformation().isWebPremium() ? SubscriptionType.PREMIUM : SubscriptionType.PREMIUM_MOBILE;
                    List<Subscription> list = user.getSubscriptions();
                    list.add(new Subscription(subscriptionType, afterPurchaseInformation.getValidUntil(), true, Subscription.SubscriptionOrigin.MOBILE, Subscription.SubscriptionProvider.GOOGLE));
                    user.setSubscriptions(list);
                }
                if (afterPurchaseInformation != null && StoreFragment.this.getView() != null) {
                    StoreFragment.this.getView().post(new Runnable(){

                        @Override
                        public void run() {
                            StoreFragment.this.updateViewsToPremiumState();
                        }
                    });
                }
                ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>(){

                    @Override
                    public void loadingCancelled() {
                        StoreFragment.this.updateViewsToPremiumState();
                    }

                    @Override
                    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                        StoreFragment.this.updateViewsToPremiumState();
                    }

                    @Override
                    public void loadingSucceded(User user) {
                        StoreFragment.this.updateViewsToPremiumState();
                    }
                });
            }

        });
    }

    private void restoreItems() {
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getPurchaseService().restorePurchases((Context)this.getActivity(), new LoadCommandCallback<AfterPurchaseInformation>(){

            @Override
            public void loadingCancelled() {
                StoreFragment.this.hideDialogWaiting();
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                StoreFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        StoreFragment.this.restoreItems();
                    }
                });
                StoreFragment.this.hideDialogWaiting();
            }

            @Override
            public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
                StoreFragment.this.updateViewsToPremiumState();
                StoreFragment.this.hideDialogWaiting();
            }

        });
    }

    private void showPurchaseDialog(ModelHolder<List<Product>> object, boolean bl, int n, int n2) {
        object = PurchaseDialog.createInstance(n, n2, bl, object, this);
        object.show(this.getChildFragmentManager(), PurchaseDialog.class.getName());
        object.setCancelable(true);
        if (this._flagLoadingProductsFailed) {
            this._flagLoadingProductsFailed = false;
            this.loadProducts();
        }
    }

    private void updateViewsToPremiumState() {
        this._subscriptionViewGroup.removeAllViews();
        Object object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (object != null) {
            Date date = new Date();
            Subscription subscription = object.getSubscription(SubscriptionType.PREMIUM);
            boolean bl = true;
            boolean bl2 = subscription != null && subscription.getExpirationDate().after(date);
            if ((object = object.getSubscription(SubscriptionType.PREMIUM_MOBILE)) == null || !object.getExpirationDate().after(date)) {
                bl = false;
            }
            if (bl2) {
                this.addSubscriptionView(SubscriptionType.PREMIUM, subscription.getExpirationDate());
            }
            if (bl) {
                this.addSubscriptionView(SubscriptionType.PREMIUM_MOBILE, object.getExpirationDate());
            }
            if (bl2 && subscription.getProvider() == Subscription.SubscriptionProvider.GOOGLE || bl && object.getProvider() == Subscription.SubscriptionProvider.GOOGLE) {
                this._subscriptionCancelView.setVisibility(0);
                return;
            }
            this._subscriptionCancelView.setVisibility(8);
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
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
    public void onActivityResult(int n, int n2, Intent intent) {
        if (!ServiceProvider.getInstance().getPurchaseService().handleActivityResult(n, n2, intent)) {
            super.onActivityResult(n, n2, intent);
        }
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        boolean bl = object != null && object.isGuest();
        this._flagUserIsGuest = bl;
        this._featureList = this.createFeatureList();
        this._mobileSubscriptionHolder = new ModelHolder(new LinkedList());
        this._webSubscriptionHolder = new ModelHolder(new LinkedList());
        this.loadProducts();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427551, viewGroup, false);
        layoutInflater.findViewById(2131297053).setBackgroundDrawable((Drawable)new TwoColorDiagonalDividerDrawable((Context)this.getActivity(), 2131099707, 2131099704));
        return layoutInflater;
    }

    @Override
    public void onRestorePressed() {
        this.restoreItems();
    }

    @Override
    public void onSelectProduct(Product product) {
        if (this._flagUserIsGuest) {
            this.getContentPresenter().showConversionDialog(null, ConversionContext.ACCOUNT_TYPE);
            return;
        }
        this.purchaseProduct(product);
    }

    @Override
    public void onViewCreated(View view, Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        object = (RecyclerView)view.findViewById(2131297070);
        int n = this.getActivity().getResources().getInteger(2131361809);
        object.setLayoutManager(new GridLayoutManager((Context)this.getActivity(), n));
        object.setAdapter(new StoreListAdapter());
        view.findViewById(2131297052).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                boolean bl = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData().getSubscription(SubscriptionType.PREMIUM) == null;
                StoreFragment.this.showPurchaseDialog(StoreFragment.this._webSubscriptionHolder, bl, 2131690301, 2131690304);
            }
        });
        view.findViewById(2131297051).setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
                boolean bl = object.getSubscription(SubscriptionType.PREMIUM_MOBILE) == null && object.getSubscription(SubscriptionType.PREMIUM) == null;
                StoreFragment.this.showPurchaseDialog(StoreFragment.this._mobileSubscriptionHolder, bl, 2131690302, 2131690305);
            }
        });
        this._subscriptionViewGroup = (ViewGroup)view.findViewById(2131297072);
        this._subscriptionCancelView = view.findViewById(2131297071);
        this._subscriptionCancelView.setVisibility(8);
        this._subscriptionCancelView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                StoreFragment.this.openPlayStore();
            }
        });
        this.updateViewsToPremiumState();
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    private static class Feature {
        private int _descrRes;
        private int _imageRes;
        private int _mobilePremiumRes;
        private int _registeredRes;
        private int _titleRes;
        private int _webPremiumRes;

        public Feature(int n, int n2, int n3, int n4, int n5, int n6) {
            this._titleRes = n;
            this._descrRes = n2;
            this._webPremiumRes = n3;
            this._mobilePremiumRes = n4;
            this._registeredRes = n5;
            this._imageRes = n6;
        }
    }

    private class StoreListAdapter
    extends RecyclerView.Adapter<StoreListAdapter$ViewHolder> {
        private StoreListAdapter() {
        }

        @Override
        public int getItemCount() {
            return StoreFragment.this._featureList.size();
        }

        @Override
        public void onBindViewHolder(StoreListAdapter$ViewHolder storeListAdapter$ViewHolder, int n) {
            Feature feature = (Feature)StoreFragment.this._featureList.get(n);
            storeListAdapter$ViewHolder._titleTextView.setText(feature._titleRes);
            storeListAdapter$ViewHolder._descriptionTextView.setText(feature._descrRes);
            storeListAdapter$ViewHolder._premiumWebTextView.setText(feature._webPremiumRes);
            storeListAdapter$ViewHolder._premiumMobileTextView.setText(feature._mobilePremiumRes);
            storeListAdapter$ViewHolder._registeredTextView.setText(feature._registeredRes);
            storeListAdapter$ViewHolder._imageView.setImageResource(feature._imageRes);
        }

        @Override
        public StoreListAdapter$ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            viewGroup = LayoutInflater.from((Context)StoreFragment.this.getActivity()).inflate(2131427552, viewGroup, false);
            viewGroup.findViewById(2131297054).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099704));
            viewGroup.findViewById(2131297055).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099707));
            viewGroup.findViewById(2131297056).setBackgroundDrawable((Drawable)new PremiumFlagDrawable((Context)StoreFragment.this.getActivity(), 2131099710));
            return new StoreListAdapter$ViewHolder((View)viewGroup);
        }
    }

    public class StoreListAdapter$ViewHolder
    extends RecyclerView.ViewHolder {
        private TextView _descriptionTextView;
        private ImageView _imageView;
        private TextView _premiumMobileTextView;
        private TextView _premiumWebTextView;
        private TextView _registeredTextView;
        private TextView _titleTextView;

        public StoreListAdapter$ViewHolder(View view) {
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
