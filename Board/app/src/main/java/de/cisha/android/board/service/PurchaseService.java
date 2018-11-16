// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.content.SharedPreferences;
import de.cisha.android.board.user.User;
import android.content.Intent;
import java.util.Collection;
import de.cisha.android.board.service.jsonparser.JSONVideoGetPriceCategoriesParser;
import java.util.HashMap;
import de.cisha.android.board.account.model.JSONAfterPurchaseInformationParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.account.model.JSONAfterPurchaseInformationPriceTierParser;
import android.os.Looper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import de.cisha.chess.util.Logger;
import com.example.android.trivialdrivesample.util.Inventory;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.JSONParserProductInformation;
import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import org.json.JSONObject;
import android.content.SharedPreferences.Editor;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import com.example.android.trivialdrivesample.util.Purchase;
import android.app.Activity;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import com.example.android.trivialdrivesample.util.IabResult;
import java.util.LinkedList;
import android.os.Handler;
import com.example.android.trivialdrivesample.util.SkuDetails;
import android.util.SparseArray;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;
import com.example.android.trivialdrivesample.util.IabHelper;
import android.content.Context;

public class PurchaseService implements IPurchaseService
{
    private static final String PURCHASE_SERVICE_PREF = "PurchaseService_Pref";
    private static final int RC_PURCHASE = 46806;
    private static PurchaseService _instance;
    private String PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX;
    private String PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX;
    private Context _context;
    private IabHelper _currentPurchaseIabHelper;
    private List<LoadCommandCallback<SparseArray<SkuDetails>>> _skuDetailsCallbackList;
    private boolean _skusRequested;
    private SparseArray<SkuDetails> _skusVideoSeries;
    private Handler _uiThreadHandler;
    
    private PurchaseService(final Context context) {
        this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX = "contentId";
        this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX = "contentType";
        this._context = context;
        this._skuDetailsCallbackList = new LinkedList<LoadCommandCallback<SparseArray<SkuDetails>>>();
    }
    
    private void clearStoredPurchaseInfo(final String s) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("PurchaseService_Pref", 0).edit();
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append(this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
        final SharedPreferences.Editor remove = edit.remove(sb.toString());
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(s);
        sb2.append(this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
        remove.remove(sb2.toString()).commit();
    }
    
    private void consumePurchase(final Context context, final Purchase purchase, final LoadCommandCallback<Void> loadCommandCallback) {
        final IabHelper iabHelper = this.createIabHelper(context);
        iabHelper.startSetup((IabHelper.OnIabSetupFinishedListener)new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(final IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    iabHelper.consumeAsync(purchase, (IabHelper.OnConsumeFinishedListener)new OnConsumeFinishedListener() {
                        @Override
                        public void onConsumeFinished(final Purchase purchase, final IabResult iabResult) {
                            if (iabResult.isSuccess()) {
                                loadCommandCallback.loadingSucceded(null);
                            }
                            else {
                                final LoadCommandCallback val.callback = loadCommandCallback;
                                final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                                final StringBuilder sb = new StringBuilder();
                                sb.append("Error consuming purchase:");
                                sb.append(iabResult.getMessage());
                                val.callback.loadingFailed(access.100, sb.toString(), null, null);
                            }
                            iabHelper.dispose();
                        }
                    });
                    return;
                }
                iabHelper.dispose();
                final LoadCommandCallback val.callback = loadCommandCallback;
                final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                final StringBuilder sb = new StringBuilder();
                sb.append("Error consuming purchase - no connection to iab service:");
                sb.append(iabResult.getMessage());
                val.callback.loadingFailed(access.100, sb.toString(), null, null);
            }
        });
    }
    
    private IabHelper createIabHelper(final Context context) {
        final StringBuilder sb = new StringBuilder();
        sb.append("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArSkoYWLLoZPNX+uL2nXTo5+wJl/m");
        sb.append("UW+R6m38Q3bZucs2G7JN+QEHC0K0BvwMv1a0S5HpjDLcqQw+hKctA");
        sb.append("hqAQx/4kkKpE1FnsfKgd9zVgnwHeP0UtsQhLPy3o/Y9HyTr8OCS7YF5z+rLzX20trFCg3p63eKFm2llm4/RmMwFJZRuGnoqvwfYIQHIxuQmG+9ZwZntCaopOu0UdWQS1S+gVSBj8ENjFGFYsQoAkMzuTgtd6XrOgV5Ihe4gKAkXO2Z9a6mjg3leiOOUkHmEp14uFakMgvuypUWQNmHrldDm6CtcTTnbSW1bTbuv1kEu16W1aBdS7Z4ooRKgknBLm9CPnQIDAQAB");
        return new IabHelper(context, sb.toString());
    }
    
    private void getAllPremiumProductIdentifiers(final LoadCommandCallback<List<ProductInformation>> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<List<ProductInformation>> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<List<ProductInformation>>();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("provider", "google");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "shopAPI/GetProductIdentifiers", treeMap, new JSONParserProductInformation(), true);
    }
    
    private void getAllPriceTierSkus(final Context context, final List<String> list, final LoadCommandCallback<List<SkuDetails>> loadCommandCallback) {
        final IabHelper iabHelper = this.createIabHelper(context);
        iabHelper.startSetup((IabHelper.OnIabSetupFinishedListener)new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(final IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    iabHelper.queryInventoryAsync(true, list, (IabHelper.QueryInventoryFinishedListener)new QueryInventoryFinishedListener() {
                        @Override
                        public void onQueryInventoryFinished(final IabResult iabResult, final Inventory inventory) {
                            iabHelper.dispose();
                            if (iabResult.isSuccess()) {
                                final LinkedList<SkuDetails> list = new LinkedList<SkuDetails>();
                                for (final String s : list) {
                                    final SkuDetails skuDetails = inventory.getSkuDetails(s);
                                    if (skuDetails != null) {
                                        list.add(skuDetails);
                                    }
                                    else {
                                        final Logger instance = Logger.getInstance();
                                        final StringBuilder sb = new StringBuilder();
                                        sb.append("Error: product ");
                                        sb.append(s);
                                        sb.append(" not found in store");
                                        instance.error("Purchase Service", sb.toString());
                                    }
                                }
                                loadCommandCallback.loadingSucceded(list);
                                return;
                            }
                            final LoadCommandCallback val.skuCallback = loadCommandCallback;
                            final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append("error loading products from playstore api:");
                            sb2.append(iabResult.getMessage());
                            val.skuCallback.loadingFailed(access.100, sb2.toString(), null, null);
                        }
                    });
                    return;
                }
                final LoadCommandCallback val.skuCallback = loadCommandCallback;
                final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                final StringBuilder sb = new StringBuilder();
                sb.append("error loading products from playstore api: Error connecting to iabservice ");
                sb.append(iabResult.getMessage());
                val.skuCallback.loadingFailed(access.100, sb.toString(), null, null);
            }
        });
    }
    
    private void getAllProductIdentifiers(final LoadCommandCallback<List<ProductInformation>> loadCommandCallback) {
        this.getAllPremiumProductIdentifiers(new LoadCommandCallbackWithTimeout<List<ProductInformation>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
            }
            
            @Override
            protected void succeded(final List<ProductInformation> list) {
                PurchaseService.this.getPriceCategories(new LoadCommandCallbackWithTimeout<Map<Integer, String>>() {
                    @Override
                    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                    }
                    
                    @Override
                    protected void succeded(final Map<Integer, String> map) {
                        final Iterator<String> iterator = map.values().iterator();
                        while (iterator.hasNext()) {
                            list.add(new ProductInformation(iterator.next(), "noId", "noType"));
                        }
                        loadCommandCallback.loadingSucceded(list);
                    }
                });
            }
        });
    }
    
    private LoadCommandCallbackWithTimeout<Void> getConsumeCallbackForPurchase(final Purchase purchase) {
        return new LoadCommandCallbackWithTimeout<Void>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                Logger.getInstance().error("Purchase Service", s);
            }
            
            @Override
            protected void succeded(final Void void1) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("consumed Purchase: ");
                sb.append(purchase.getSku());
                sb.append("Token: ");
                sb.append(purchase.getToken());
                instance.debug("Purchase Service", sb.toString());
                PurchaseService.this.clearStoredPurchaseInfo(purchase.getOrderId());
            }
        };
    }
    
    public static PurchaseService getInstance(final Context context) {
        if (PurchaseService._instance == null) {
            PurchaseService._instance = new PurchaseService(context);
        }
        return PurchaseService._instance;
    }
    
    private Handler getUiThreadHandler() {
        if (this._uiThreadHandler == null) {
            this._uiThreadHandler = new Handler(Looper.getMainLooper());
        }
        return this._uiThreadHandler;
    }
    
    private APIStatusCode iabResponseToStatusCode(final IabResult iabResult) {
        if (iabResult.isSuccess()) {
            return APIStatusCode.API_OK;
        }
        if (iabResult.getResponse() != -1010) {
            return APIStatusCode.INTERNAL_PURCHASE_ERROR;
        }
        return APIStatusCode.INTERNAL_PURCHASE_CONSUMPTION_ERROR;
    }
    
    private void launchPurchaseFlow(final IabHelper iabHelper, final Product product, final Activity activity, final String s, final int n, final IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        switch (PurchaseService.9..SwitchMap.de.cisha.android.board.account.model.ProductInformation.ProductType[product.getProductInformation().getType().ordinal()]) {
            default: {}
            case 3: {
                iabHelper.launchSubscriptionPurchaseFlow(activity, s, n, onIabPurchaseFinishedListener);
            }
            case 1:
            case 2: {
                iabHelper.launchPurchaseFlow(activity, s, n, onIabPurchaseFinishedListener);
            }
        }
    }
    
    private void storePurchaseInfoToPreferences(final String s, final ProductInformation productInformation) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("PurchaseService_Pref", 0).edit();
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append(this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
        final SharedPreferences.Editor putString = edit.putString(sb.toString(), productInformation.getContentId());
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(s);
        sb2.append(this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
        putString.putString(sb2.toString(), productInformation.getContentTypeString()).commit();
        final Logger instance = Logger.getInstance();
        final StringBuilder sb3 = new StringBuilder();
        sb3.append("stored purchase information to preferences with orderid: ");
        sb3.append(s);
        sb3.append(" contentId:");
        sb3.append(productInformation.getContentId());
        sb3.append(" type:");
        sb3.append(productInformation.getContentTypeString());
        instance.debug("Purchase Service", sb3.toString());
    }
    
    private void verifyPriceTierPurchaseToken(final Purchase purchase, final String s, final String s2, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        synchronized (this) {
            final GeneralJSONAPICommandLoader<AfterPurchaseInformation> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<AfterPurchaseInformation>();
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("token", purchase.getToken());
            treeMap.put("productIdentifier", purchase.getSku());
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("try to send purchase info to shopApi with contentId:");
            sb.append(s);
            sb.append(" contentType:");
            sb.append(s2);
            instance.debug("Purchase Service", sb.toString());
            if (s != null && s2 != null) {
                treeMap.put("contentId", s);
                treeMap.put("contentType", s2);
                generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "shopAPI/buyContentGoogle", treeMap, new JSONAfterPurchaseInformationPriceTierParser(), true);
            }
            else {
                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_PURCHASE_ERROR, "There is a valid purchase token but content is unkwown", null, null);
            }
        }
    }
    
    private void verifySubscriptionToken(final Purchase purchase, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        synchronized (this) {
            final LoadCommandCallbackWrapper<AfterPurchaseInformation> loadCommandCallbackWrapper = new LoadCommandCallbackWrapper<AfterPurchaseInformation>(loadCommandCallback);
            final GeneralJSONAPICommandLoader<AfterPurchaseInformation> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<AfterPurchaseInformation>();
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("token", purchase.getToken());
            treeMap.put("productIdentifier", purchase.getSku());
            generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallbackWrapper, "shopAPI/ValidateAndroidSubscriptionToken", treeMap, new JSONAfterPurchaseInformationParser(), true);
        }
    }
    
    @Override
    public void getAllPremiumSubscriptionProducts(final Context context, final LoadCommandCallback<List<Product>> loadCommandCallback) {
        this.getAllPremiumProductIdentifiers(new LoadCommandCallbackWithTimeout<List<ProductInformation>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final List<ProductInformation> list) {
                final IabHelper access.000 = PurchaseService.this.createIabHelper(context);
                access.000.startSetup((IabHelper.OnIabSetupFinishedListener)new IabHelper.OnIabSetupFinishedListener() {
                    @Override
                    public void onIabSetupFinished(final IabResult iabResult) {
                        if (iabResult.isSuccess()) {
                            final LinkedList<String> list = new LinkedList<String>();
                            final Iterator<ProductInformation> iterator = (Iterator<ProductInformation>)list.iterator();
                            while (iterator.hasNext()) {
                                list.add(iterator.next().getSku());
                            }
                            access.000.queryInventoryAsync(true, list, (IabHelper.QueryInventoryFinishedListener)new QueryInventoryFinishedListener() {
                                @Override
                                public void onQueryInventoryFinished(final IabResult iabResult, final Inventory inventory) {
                                    access.000.dispose();
                                    if (iabResult.isSuccess()) {
                                        final LinkedList<Product> list = new LinkedList<Product>();
                                        for (final ProductInformation productInformation : list) {
                                            final String sku = productInformation.getSku();
                                            final SkuDetails skuDetails = inventory.getSkuDetails(sku);
                                            if (skuDetails != null) {
                                                list.add(new Product(skuDetails, productInformation));
                                            }
                                            else {
                                                final Logger instance = Logger.getInstance();
                                                final StringBuilder sb = new StringBuilder();
                                                sb.append("Error: product ");
                                                sb.append(sku);
                                                sb.append(" not found in store");
                                                instance.error("Purchase Service", sb.toString());
                                            }
                                        }
                                        loadCommandCallback.loadingSucceded(list);
                                        return;
                                    }
                                    final LoadCommandCallback val.productCallback = loadCommandCallback;
                                    final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                                    final StringBuilder sb2 = new StringBuilder();
                                    sb2.append("error loading products from playstore api:");
                                    sb2.append(iabResult.getMessage());
                                    val.productCallback.loadingFailed(access.100, sb2.toString(), null, null);
                                }
                            });
                            return;
                        }
                        final LoadCommandCallback val.productCallback = loadCommandCallback;
                        final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                        final StringBuilder sb = new StringBuilder();
                        sb.append("error loading products from playstore api: Error connecting to iabservice ");
                        sb.append(iabResult.getMessage());
                        val.productCallback.loadingFailed(access.100, sb.toString(), null, null);
                    }
                });
            }
        });
    }
    
    void getPriceCategories(final LoadCommandCallback<Map<Integer, String>> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<Map<Integer, String>> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Map<Integer, String>>();
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("provider", "google");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetPriceCategories", hashMap, new JSONVideoGetPriceCategoriesParser(), true);
    }
    
    @Override
    public void getSkuDetailsMap(final LoadCommandCallback<SparseArray<SkuDetails>> loadCommandCallback) {
        this.getUiThreadHandler().post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (PurchaseService.this._skusVideoSeries != null) {
                    loadCommandCallback.loadingSucceded(PurchaseService.this._skusVideoSeries);
                    return;
                }
                if (!PurchaseService.this._skusRequested) {
                    PurchaseService.this._skusRequested = true;
                    PurchaseService.this._skuDetailsCallbackList.add(loadCommandCallback);
                    PurchaseService.this.getPriceCategories(new LoadCommandCallbackWithTimeout<Map<Integer, String>>() {
                        @Override
                        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                            loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                        }
                        
                        @Override
                        protected void succeded(final Map<Integer, String> map) {
                            PurchaseService.this.getAllPriceTierSkus(PurchaseService.this._context, new LinkedList(map.values()), new LoadCommandCallback<List<SkuDetails>>() {
                                @Override
                                public void loadingCancelled() {
                                    PurchaseService.this.getUiThreadHandler().post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            final Iterator<LoadCommandCallback> iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (iterator.hasNext()) {
                                                iterator.next().loadingCancelled();
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }
                                
                                @Override
                                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                                    PurchaseService.this.getUiThreadHandler().post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            final Iterator<LoadCommandCallback> iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (iterator.hasNext()) {
                                                iterator.next().loadingFailed(apiStatusCode, s, list, jsonObject);
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }
                                
                                @Override
                                public void loadingSucceded(final List<SkuDetails> list) {
                                    PurchaseService.this.getUiThreadHandler().post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            final SparseArray sparseArray = new SparseArray();
                                            final TreeMap<String, SkuDetails> treeMap = (TreeMap<String, SkuDetails>)new TreeMap<String, Object>();
                                            for (final SkuDetails skuDetails : list) {
                                                treeMap.put(skuDetails.getSku(), skuDetails);
                                            }
                                            for (final Map.Entry<Integer, V> entry : map.entrySet()) {
                                                sparseArray.put((int)entry.getKey(), treeMap.get(entry.getValue()));
                                            }
                                            PurchaseService.this._skusVideoSeries = (SparseArray<SkuDetails>)sparseArray;
                                            final Iterator<LoadCommandCallback<SparseArray>> iterator3 = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (iterator3.hasNext()) {
                                                iterator3.next().loadingSucceded(sparseArray);
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }
                            });
                        }
                    });
                    return;
                }
                PurchaseService.this._skuDetailsCallbackList.add(loadCommandCallback);
            }
        });
    }
    
    @Override
    public boolean handleActivityResult(final int n, final int n2, final Intent intent) {
        return this._currentPurchaseIabHelper != null && this._currentPurchaseIabHelper.handleActivityResult(n, n2, intent);
    }
    
    @Override
    public void purchaseProduct(final Activity activity, final Product product, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "InApp purchase started", null);
        final IabHelper iabHelper = this.createIabHelper((Context)activity);
        (this._currentPurchaseIabHelper = iabHelper).startSetup((IabHelper.OnIabSetupFinishedListener)new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(final IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    PurchaseService.this.launchPurchaseFlow(iabHelper, product, activity, product.getApstoreProduct().getSku(), 46806, new OnIabPurchaseFinishedListener() {
                        @Override
                        public void onIabPurchaseFinished(final IabResult iabResult, final Purchase purchase) {
                            iabHelper.dispose();
                            final Logger instance = Logger.getInstance();
                            final StringBuilder sb = new StringBuilder();
                            sb.append("purchase success:");
                            sb.append(iabResult.isSuccess());
                            instance.debug("Purchase Service", sb.toString());
                            if (iabResult.isSuccess()) {
                                final ProductInformation productInformation = product.getProductInformation();
                                final Logger instance2 = Logger.getInstance();
                                final StringBuilder sb2 = new StringBuilder();
                                sb2.append("product type: ");
                                sb2.append(productInformation.getType().name());
                                instance2.debug("Purchase Service", sb2.toString());
                                if (productInformation.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING) {
                                    PurchaseService.this.verifySubscriptionToken(purchase, loadCommandCallback);
                                    return;
                                }
                                if (productInformation.getType() == ProductInformation.ProductType.PRICE_TIER) {
                                    PurchaseService.this.storePurchaseInfoToPreferences(purchase.getOrderId(), productInformation);
                                }
                                final LoadCommandCallbackWithTimeout<AfterPurchaseInformation> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<AfterPurchaseInformation>() {
                                    @Override
                                    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                                        loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
                                        if (apiStatusCode == APIStatusCode.API_ERROR_PAYMENT_FAILED) {
                                            PurchaseService.this.consumePurchase((Context)activity, purchase, PurchaseService.this.getConsumeCallbackForPurchase(purchase));
                                        }
                                        final ITrackingService trackingService = ServiceProvider.getInstance().getTrackingService();
                                        final ITrackingService.TrackingCategory shop = ITrackingService.TrackingCategory.SHOP;
                                        final StringBuilder sb = new StringBuilder();
                                        sb.append("API error: ");
                                        sb.append(apiStatusCode);
                                        trackingService.trackEvent(shop, "InApp purchase failed", sb.toString());
                                    }
                                    
                                    @Override
                                    protected void succeded(final AfterPurchaseInformation afterPurchaseInformation) {
                                        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallbackWithTimeout<User>() {
                                            @Override
                                            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                                            }
                                            
                                            @Override
                                            protected void succeded(final User user) {
                                            }
                                        });
                                        loadCommandCallback.loadingSucceded(afterPurchaseInformation);
                                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "InApp purchase completed", null);
                                        PurchaseService.this.consumePurchase((Context)activity, purchase, PurchaseService.this.getConsumeCallbackForPurchase(purchase));
                                    }
                                };
                                if (productInformation.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION) {
                                    PurchaseService.this.verifySubscriptionToken(purchase, loadCommandCallbackWithTimeout);
                                    return;
                                }
                                if (productInformation.getType() == ProductInformation.ProductType.PRICE_TIER) {
                                    PurchaseService.this.verifyPriceTierPurchaseToken(purchase, productInformation.getContentId(), productInformation.getContentTypeString(), loadCommandCallbackWithTimeout);
                                }
                            }
                            else {
                                final int response = iabResult.getResponse();
                                if (response != -1005) {
                                    if (response == 7) {
                                        PurchaseService.this.restorePurchases((Context)activity, loadCommandCallback);
                                        return;
                                    }
                                    switch (response) {
                                        default: {
                                            final LoadCommandCallback val.callback = loadCommandCallback;
                                            final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                                            final StringBuilder sb3 = new StringBuilder();
                                            sb3.append("purchasing failed:");
                                            sb3.append(iabResult.getMessage());
                                            val.callback.loadingFailed(access.100, sb3.toString(), null, null);
                                            final ITrackingService trackingService = ServiceProvider.getInstance().getTrackingService();
                                            final ITrackingService.TrackingCategory shop = ITrackingService.TrackingCategory.SHOP;
                                            final StringBuilder sb4 = new StringBuilder();
                                            sb4.append("PlayStore error: ");
                                            sb4.append(response);
                                            trackingService.trackEvent(shop, "InApp purchase failed", sb4.toString());
                                            return;
                                        }
                                        case 0:
                                        case 1: {
                                            break;
                                        }
                                    }
                                }
                                loadCommandCallback.loadingCancelled();
                                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "InApp purchase cancelled", null);
                            }
                        }
                    });
                    return;
                }
                iabHelper.dispose();
                final LoadCommandCallback val.callback = loadCommandCallback;
                final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                final StringBuilder sb = new StringBuilder();
                sb.append("setup iab failed:");
                sb.append(iabResult.getMessage());
                val.callback.loadingFailed(access.100, sb.toString(), null, null);
            }
        });
    }
    
    @Override
    public void restorePurchases(final Context context, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        this.getAllProductIdentifiers(new LoadCommandCallbackWithTimeout<List<ProductInformation>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final List<ProductInformation> list) {
                final IabHelper access.000 = PurchaseService.this.createIabHelper(context);
                access.000.startSetup((IabHelper.OnIabSetupFinishedListener)new IabHelper.OnIabSetupFinishedListener() {
                    @Override
                    public void onIabSetupFinished(final IabResult iabResult) {
                        if (iabResult.isSuccess()) {
                            final LinkedList<String> list = new LinkedList<String>();
                            final Iterator<ProductInformation> iterator = (Iterator<ProductInformation>)list.iterator();
                            while (iterator.hasNext()) {
                                list.add(iterator.next().getSku());
                            }
                            access.000.queryInventoryAsync(true, list, (IabHelper.QueryInventoryFinishedListener)new QueryInventoryFinishedListener() {
                                @Override
                                public void onQueryInventoryFinished(final IabResult iabResult, final Inventory inventory) {
                                    access.000.dispose();
                                    if (iabResult.isSuccess()) {
                                        Logger.getInstance().debug("Purchase Service", "trying to restore purchases");
                                        for (final ProductInformation productInformation : list) {
                                            final String sku = productInformation.getSku();
                                            if (inventory.hasPurchase(sku)) {
                                                final Purchase purchase = inventory.getPurchase(sku);
                                                final LoadCommandCallbackWithTimeout<AfterPurchaseInformation> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<AfterPurchaseInformation>() {
                                                    @Override
                                                    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                                                        final Logger instance = Logger.getInstance();
                                                        final StringBuilder sb = new StringBuilder();
                                                        sb.append("restoring ");
                                                        sb.append(sku);
                                                        sb.append(" failed");
                                                        instance.debug("Purchase Service", sb.toString());
                                                    }
                                                    
                                                    @Override
                                                    protected void succeded(final AfterPurchaseInformation afterPurchaseInformation) {
                                                        final Logger instance = Logger.getInstance();
                                                        final StringBuilder sb = new StringBuilder();
                                                        sb.append("restoring ");
                                                        sb.append(sku);
                                                        sb.append(" succesful - consuming it");
                                                        instance.debug("Purchase Service", sb.toString());
                                                        PurchaseService.this.consumePurchase(context, purchase, PurchaseService.this.getConsumeCallbackForPurchase(purchase));
                                                    }
                                                };
                                                if (productInformation.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING) {
                                                    PurchaseService.this.verifySubscriptionToken(purchase, loadCommandCallback);
                                                }
                                                else if (productInformation.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION) {
                                                    PurchaseService.this.verifySubscriptionToken(purchase, loadCommandCallbackWithTimeout);
                                                }
                                                else {
                                                    Logger.getInstance().debug("purchasetoken", purchase.getToken());
                                                    final SharedPreferences sharedPreferences = PurchaseService.this._context.getSharedPreferences("PurchaseService_Pref", 0);
                                                    final StringBuilder sb = new StringBuilder();
                                                    sb.append(purchase.getOrderId());
                                                    sb.append(PurchaseService.this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
                                                    final String string = sharedPreferences.getString(sb.toString(), "");
                                                    final StringBuilder sb2 = new StringBuilder();
                                                    sb2.append(purchase.getOrderId());
                                                    sb2.append(PurchaseService.this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
                                                    final String string2 = sharedPreferences.getString(sb2.toString(), "");
                                                    if (!string.isEmpty() && !string2.isEmpty()) {
                                                        final Logger instance = Logger.getInstance();
                                                        final StringBuilder sb3 = new StringBuilder();
                                                        sb3.append("verify pricetier purchase with id ");
                                                        sb3.append(string);
                                                        sb3.append(" type ");
                                                        sb3.append(string2);
                                                        instance.debug("Purchase Service", sb3.toString());
                                                        PurchaseService.this.verifyPriceTierPurchaseToken(purchase, string, string2, loadCommandCallbackWithTimeout);
                                                    }
                                                    else {
                                                        final Logger instance2 = Logger.getInstance();
                                                        final StringBuilder sb4 = new StringBuilder();
                                                        sb4.append("consuming without charging - error. OrderId:");
                                                        sb4.append(purchase.getOrderId());
                                                        instance2.error("Purchase Service", sb4.toString());
                                                        PurchaseService.this.consumePurchase(context, purchase, PurchaseService.this.getConsumeCallbackForPurchase(purchase));
                                                    }
                                                }
                                            }
                                        }
                                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "not updated");
                                        loadCommandCallback.loadingSucceded(null);
                                        return;
                                    }
                                    final LoadCommandCallback val.restoreCallback = loadCommandCallback;
                                    final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                                    final StringBuilder sb5 = new StringBuilder();
                                    sb5.append("error loading products from playstore api:");
                                    sb5.append(iabResult.getMessage());
                                    val.restoreCallback.loadingFailed(access.100, sb5.toString(), null, null);
                                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "error");
                                }
                            });
                            return;
                        }
                        final LoadCommandCallback val.restoreCallback = loadCommandCallback;
                        final APIStatusCode access.100 = PurchaseService.this.iabResponseToStatusCode(iabResult);
                        final StringBuilder sb = new StringBuilder();
                        sb.append("error loading products from playstore api: Error connecting to iabservice ");
                        sb.append(iabResult.getMessage());
                        val.restoreCallback.loadingFailed(access.100, sb.toString(), null, null);
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "error");
                    }
                });
            }
        });
    }
}
