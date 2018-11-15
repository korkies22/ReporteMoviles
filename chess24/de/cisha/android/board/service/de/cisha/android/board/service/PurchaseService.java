/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.JSONAfterPurchaseInformationParser;
import de.cisha.android.board.account.model.JSONAfterPurchaseInformationPriceTierParser;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONParserProductInformation;
import de.cisha.android.board.service.jsonparser.JSONVideoGetPriceCategoriesParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.chess.util.Logger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONObject;

public class PurchaseService
implements IPurchaseService {
    private static final String PURCHASE_SERVICE_PREF = "PurchaseService_Pref";
    private static final int RC_PURCHASE = 46806;
    private static PurchaseService _instance;
    private String PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX = "contentId";
    private String PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX = "contentType";
    private Context _context;
    private IabHelper _currentPurchaseIabHelper;
    private List<LoadCommandCallback<SparseArray<SkuDetails>>> _skuDetailsCallbackList;
    private boolean _skusRequested;
    private SparseArray<SkuDetails> _skusVideoSeries;
    private Handler _uiThreadHandler;

    private PurchaseService(Context context) {
        this._context = context;
        this._skuDetailsCallbackList = new LinkedList<LoadCommandCallback<SparseArray<SkuDetails>>>();
    }

    static /* synthetic */ void access$900(PurchaseService purchaseService, String string, ProductInformation productInformation) {
        purchaseService.storePurchaseInfoToPreferences(string, productInformation);
    }

    private void clearStoredPurchaseInfo(String string) {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(PURCHASE_SERVICE_PREF, 0).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
        editor = editor.remove(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
        editor.remove(stringBuilder.toString()).commit();
    }

    private void consumePurchase(Context object, Purchase purchase, LoadCommandCallback<Void> loadCommandCallback) {
        object = this.createIabHelper((Context)object);
        object.startSetup(new IabHelper.OnIabSetupFinishedListener((IabHelper)object, purchase, loadCommandCallback){
            final /* synthetic */ LoadCommandCallback val$callback;
            final /* synthetic */ IabHelper val$iabHelper;
            final /* synthetic */ Purchase val$purchase;
            {
                this.val$iabHelper = iabHelper;
                this.val$purchase = purchase;
                this.val$callback = loadCommandCallback;
            }

            @Override
            public void onIabSetupFinished(IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    this.val$iabHelper.consumeAsync(this.val$purchase, new IabHelper.OnConsumeFinishedListener(){

                        @Override
                        public void onConsumeFinished(Purchase object, IabResult iabResult) {
                            if (iabResult.isSuccess()) {
                                6.this.val$callback.loadingSucceded(null);
                            } else {
                                object = 6.this.val$callback;
                                APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Error consuming purchase:");
                                stringBuilder.append(iabResult.getMessage());
                                object.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                            }
                            6.this.val$iabHelper.dispose();
                        }
                    });
                    return;
                }
                this.val$iabHelper.dispose();
                LoadCommandCallback loadCommandCallback = this.val$callback;
                APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error consuming purchase - no connection to iab service:");
                stringBuilder.append(iabResult.getMessage());
                loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
            }

        });
    }

    private IabHelper createIabHelper(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArSkoYWLLoZPNX+uL2nXTo5+wJl/m");
        stringBuilder.append("UW+R6m38Q3bZucs2G7JN+QEHC0K0BvwMv1a0S5HpjDLcqQw+hKctA");
        stringBuilder.append("hqAQx/4kkKpE1FnsfKgd9zVgnwHeP0UtsQhLPy3o/Y9HyTr8OCS7YF5z+rLzX20trFCg3p63eKFm2llm4/RmMwFJZRuGnoqvwfYIQHIxuQmG+9ZwZntCaopOu0UdWQS1S+gVSBj8ENjFGFYsQoAkMzuTgtd6XrOgV5Ihe4gKAkXO2Z9a6mjg3leiOOUkHmEp14uFakMgvuypUWQNmHrldDm6CtcTTnbSW1bTbuv1kEu16W1aBdS7Z4ooRKgknBLm9CPnQIDAQAB");
        return new IabHelper(context, stringBuilder.toString());
    }

    private void getAllPremiumProductIdentifiers(LoadCommandCallback<List<ProductInformation>> loadCommandCallback) {
        GeneralJSONAPICommandLoader<List<ProductInformation>> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<List<ProductInformation>>();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("provider", "google");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "shopAPI/GetProductIdentifiers", treeMap, new JSONParserProductInformation(), true);
    }

    private void getAllPriceTierSkus(Context object, List<String> list, LoadCommandCallback<List<SkuDetails>> loadCommandCallback) {
        object = this.createIabHelper((Context)object);
        object.startSetup(new IabHelper.OnIabSetupFinishedListener((IabHelper)object, list, loadCommandCallback){
            final /* synthetic */ IabHelper val$iabHelper;
            final /* synthetic */ LoadCommandCallback val$skuCallback;
            final /* synthetic */ List val$skus;
            {
                this.val$iabHelper = iabHelper;
                this.val$skus = list;
                this.val$skuCallback = loadCommandCallback;
            }

            @Override
            public void onIabSetupFinished(IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    this.val$iabHelper.queryInventoryAsync(true, this.val$skus, new IabHelper.QueryInventoryFinishedListener(){

                        @Override
                        public void onQueryInventoryFinished(IabResult object, Inventory object2) {
                            7.this.val$iabHelper.dispose();
                            if (object.isSuccess()) {
                                object = new LinkedList();
                                for (String string : 7.this.val$skus) {
                                    Object object3 = object2.getSkuDetails(string);
                                    if (object3 != null) {
                                        object.add(object3);
                                        continue;
                                    }
                                    object3 = Logger.getInstance();
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Error: product ");
                                    stringBuilder.append(string);
                                    stringBuilder.append(" not found in store");
                                    object3.error("Purchase Service", stringBuilder.toString());
                                }
                                7.this.val$skuCallback.loadingSucceded(object);
                                return;
                            }
                            object2 = 7.this.val$skuCallback;
                            APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("error loading products from playstore api:");
                            stringBuilder.append(object.getMessage());
                            object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                        }
                    });
                    return;
                }
                LoadCommandCallback loadCommandCallback = this.val$skuCallback;
                APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error loading products from playstore api: Error connecting to iabservice ");
                stringBuilder.append(iabResult.getMessage());
                loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
            }

        });
    }

    private void getAllProductIdentifiers(final LoadCommandCallback<List<ProductInformation>> loadCommandCallback) {
        this.getAllPremiumProductIdentifiers((LoadCommandCallback<List<ProductInformation>>)new LoadCommandCallbackWithTimeout<List<ProductInformation>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
            }

            @Override
            protected void succeded(final List<ProductInformation> list) {
                PurchaseService.this.getPriceCategories((LoadCommandCallback<Map<Integer, String>>)new LoadCommandCallbackWithTimeout<Map<Integer, String>>(){

                    @Override
                    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list2, JSONObject jSONObject) {
                        loadCommandCallback.loadingFailed(aPIStatusCode, string, list2, jSONObject);
                    }

                    @Override
                    protected void succeded(Map<Integer, String> object) {
                        for (String string : object.values()) {
                            list.add(new ProductInformation(string, "noId", "noType"));
                        }
                        loadCommandCallback.loadingSucceded(list);
                    }
                });
            }

        });
    }

    private LoadCommandCallbackWithTimeout<Void> getConsumeCallbackForPurchase(final Purchase purchase) {
        return new LoadCommandCallbackWithTimeout<Void>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                Logger.getInstance().error("Purchase Service", string);
            }

            @Override
            protected void succeded(Void object) {
                object = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("consumed Purchase: ");
                stringBuilder.append(purchase.getSku());
                stringBuilder.append("Token: ");
                stringBuilder.append(purchase.getToken());
                object.debug("Purchase Service", stringBuilder.toString());
                PurchaseService.this.clearStoredPurchaseInfo(purchase.getOrderId());
            }
        };
    }

    public static PurchaseService getInstance(Context context) {
        if (_instance == null) {
            _instance = new PurchaseService(context);
        }
        return _instance;
    }

    private Handler getUiThreadHandler() {
        if (this._uiThreadHandler == null) {
            this._uiThreadHandler = new Handler(Looper.getMainLooper());
        }
        return this._uiThreadHandler;
    }

    private APIStatusCode iabResponseToStatusCode(IabResult iabResult) {
        if (iabResult.isSuccess()) {
            return APIStatusCode.API_OK;
        }
        if (iabResult.getResponse() != -1010) {
            return APIStatusCode.INTERNAL_PURCHASE_ERROR;
        }
        return APIStatusCode.INTERNAL_PURCHASE_CONSUMPTION_ERROR;
    }

    private void launchPurchaseFlow(IabHelper iabHelper, Product object, Activity activity, String string, int n, IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        object = object.getProductInformation().getType();
        switch (.$SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType[object.ordinal()]) {
            default: {
                return;
            }
            case 3: {
                iabHelper.launchSubscriptionPurchaseFlow(activity, string, n, onIabPurchaseFinishedListener);
                return;
            }
            case 1: 
            case 2: 
        }
        iabHelper.launchPurchaseFlow(activity, string, n, onIabPurchaseFinishedListener);
    }

    private void storePurchaseInfoToPreferences(String string, ProductInformation productInformation) {
        Object object = this._context.getSharedPreferences(PURCHASE_SERVICE_PREF, 0).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
        object = object.putString(stringBuilder.toString(), productInformation.getContentId());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
        object.putString(stringBuilder.toString(), productInformation.getContentTypeString()).commit();
        object = Logger.getInstance();
        stringBuilder = new StringBuilder();
        stringBuilder.append("stored purchase information to preferences with orderid: ");
        stringBuilder.append(string);
        stringBuilder.append(" contentId:");
        stringBuilder.append(productInformation.getContentId());
        stringBuilder.append(" type:");
        stringBuilder.append(productInformation.getContentTypeString());
        object.debug("Purchase Service", stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void verifyPriceTierPurchaseToken(Purchase object, String string, String string2, LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        synchronized (this) {
            void var4_4;
            void var2_2;
            void var3_3;
            GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("token", object.getToken());
            treeMap.put("productIdentifier", object.getSku());
            object = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("try to send purchase info to shopApi with contentId:");
            stringBuilder.append((String)var2_2);
            stringBuilder.append(" contentType:");
            stringBuilder.append((String)var3_3);
            object.debug("Purchase Service", stringBuilder.toString());
            if (var2_2 != null && var3_3 != null) {
                treeMap.put("contentId", (String)var2_2);
                treeMap.put("contentType", (String)var3_3);
                generalJSONAPICommandLoader.loadApiCommandPost(var4_4, "shopAPI/buyContentGoogle", (Map<String, String>)treeMap, new JSONAfterPurchaseInformationPriceTierParser(), true);
            } else {
                var4_4.loadingFailed(APIStatusCode.INTERNAL_PURCHASE_ERROR, "There is a valid purchase token but content is unkwown", null, null);
            }
            return;
        }
    }

    private void verifySubscriptionToken(Purchase purchase, LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        synchronized (this) {
            loadCommandCallback = new LoadCommandCallbackWrapper<AfterPurchaseInformation>(loadCommandCallback);
            GeneralJSONAPICommandLoader<AfterPurchaseInformation> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<AfterPurchaseInformation>();
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("token", purchase.getToken());
            treeMap.put("productIdentifier", purchase.getSku());
            generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "shopAPI/ValidateAndroidSubscriptionToken", treeMap, new JSONAfterPurchaseInformationParser(), true);
            return;
        }
    }

    @Override
    public void getAllPremiumSubscriptionProducts(final Context context, final LoadCommandCallback<List<Product>> loadCommandCallback) {
        this.getAllPremiumProductIdentifiers((LoadCommandCallback<List<ProductInformation>>)new LoadCommandCallbackWithTimeout<List<ProductInformation>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, null);
            }

            @Override
            protected void succeded(final List<ProductInformation> list) {
                final IabHelper iabHelper = PurchaseService.this.createIabHelper(context);
                iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener(){

                    @Override
                    public void onIabSetupFinished(IabResult object) {
                        if (object.isSuccess()) {
                            object = new LinkedList();
                            Iterator iterator = list.iterator();
                            while (iterator.hasNext()) {
                                object.add(((ProductInformation)iterator.next()).getSku());
                            }
                            iabHelper.queryInventoryAsync(true, (List<String>)object, new IabHelper.QueryInventoryFinishedListener(){

                                @Override
                                public void onQueryInventoryFinished(IabResult object, Inventory object2) {
                                    iabHelper.dispose();
                                    if (object.isSuccess()) {
                                        object = new LinkedList();
                                        for (Object object3 : list) {
                                            String string = object3.getSku();
                                            Object object4 = object2.getSkuDetails(string);
                                            if (object4 != null) {
                                                object.add(new Product((SkuDetails)object4, (ProductInformation)object3));
                                                continue;
                                            }
                                            object3 = Logger.getInstance();
                                            object4 = new StringBuilder();
                                            object4.append("Error: product ");
                                            object4.append(string);
                                            object4.append(" not found in store");
                                            object3.error("Purchase Service", object4.toString());
                                        }
                                        loadCommandCallback.loadingSucceded(object);
                                        return;
                                    }
                                    object2 = loadCommandCallback;
                                    APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("error loading products from playstore api:");
                                    stringBuilder.append(object.getMessage());
                                    object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                                }
                            });
                            return;
                        }
                        LoadCommandCallback loadCommandCallback = loadCommandCallback;
                        APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error loading products from playstore api: Error connecting to iabservice ");
                        stringBuilder.append(object.getMessage());
                        loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                    }

                });
            }

        });
    }

    void getPriceCategories(LoadCommandCallback<Map<Integer, String>> loadCommandCallback) {
        GeneralJSONAPICommandLoader<Map<Integer, String>> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Map<Integer, String>>();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("provider", "google");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetPriceCategories", hashMap, new JSONVideoGetPriceCategoriesParser(), true);
    }

    @Override
    public void getSkuDetailsMap(final LoadCommandCallback<SparseArray<SkuDetails>> loadCommandCallback) {
        this.getUiThreadHandler().post(new Runnable(){

            @Override
            public void run() {
                if (PurchaseService.this._skusVideoSeries != null) {
                    loadCommandCallback.loadingSucceded(PurchaseService.this._skusVideoSeries);
                    return;
                }
                if (!PurchaseService.this._skusRequested) {
                    PurchaseService.this._skusRequested = true;
                    PurchaseService.this._skuDetailsCallbackList.add(loadCommandCallback);
                    LoadCommandCallbackWithTimeout<Map<Integer, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<Integer, String>>(){

                        @Override
                        protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                            loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                        }

                        @Override
                        protected void succeded(final Map<Integer, String> map) {
                            PurchaseService.this.getAllPriceTierSkus(PurchaseService.this._context, new LinkedList<String>(map.values()), new LoadCommandCallback<List<SkuDetails>>(){

                                @Override
                                public void loadingCancelled() {
                                    PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                        @Override
                                        public void run() {
                                            Iterator iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (iterator.hasNext()) {
                                                ((LoadCommandCallback)iterator.next()).loadingCancelled();
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }

                                @Override
                                public void loadingFailed(final APIStatusCode aPIStatusCode, final String string, final List<LoadFieldError> list, final JSONObject jSONObject) {
                                    PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                        @Override
                                        public void run() {
                                            Iterator iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (iterator.hasNext()) {
                                                ((LoadCommandCallback)iterator.next()).loadingFailed(aPIStatusCode, string, list, jSONObject);
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }

                                @Override
                                public void loadingSucceded(final List<SkuDetails> list) {
                                    PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                        @Override
                                        public void run() {
                                            SparseArray sparseArray = new SparseArray();
                                            Object object = new TreeMap<String, SkuDetails>();
                                            for (SkuDetails object2 : list) {
                                                object.put(object2.getSku(), object2);
                                            }
                                            for (Map.Entry entry : map.entrySet()) {
                                                sparseArray.put(((Integer)entry.getKey()).intValue(), object.get(entry.getValue()));
                                            }
                                            PurchaseService.this._skusVideoSeries = sparseArray;
                                            object = PurchaseService.this._skuDetailsCallbackList.iterator();
                                            while (object.hasNext()) {
                                                ((LoadCommandCallback)object.next()).loadingSucceded(sparseArray);
                                            }
                                            PurchaseService.this._skuDetailsCallbackList.clear();
                                            PurchaseService.this._skusRequested = false;
                                        }
                                    });
                                }

                            });
                        }

                    };
                    PurchaseService.this.getPriceCategories((LoadCommandCallback<Map<Integer, String>>)loadCommandCallbackWithTimeout);
                    return;
                }
                PurchaseService.this._skuDetailsCallbackList.add(loadCommandCallback);
            }

        });
    }

    @Override
    public boolean handleActivityResult(int n, int n2, Intent intent) {
        if (this._currentPurchaseIabHelper != null) {
            return this._currentPurchaseIabHelper.handleActivityResult(n, n2, intent);
        }
        return false;
    }

    @Override
    public void purchaseProduct(final Activity activity, final Product product, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        IabHelper iabHelper;
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "InApp purchase started", null);
        this._currentPurchaseIabHelper = iabHelper = this.createIabHelper((Context)activity);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener(){

            @Override
            public void onIabSetupFinished(IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    PurchaseService.this.launchPurchaseFlow(iabHelper, product, activity, product.getApstoreProduct().getSku(), 46806, new IabHelper.OnIabPurchaseFinishedListener(){

                        /*
                         * Exception decompiling
                         */
                        @Override
                        public void onIabPurchaseFinished(IabResult var1_1, Purchase var2_2) {
                            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                            // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
                            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
                            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
                            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
                            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
                            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
                            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                            // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
                            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
                            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:767)
                            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:864)
                            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:767)
                            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:864)
                            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
                            // org.benf.cfr.reader.Main.doClass(Main.java:54)
                            // org.benf.cfr.reader.Main.main(Main.java:247)
                            throw new IllegalStateException("Decompilation failed");
                        }

                    });
                    return;
                }
                iabHelper.dispose();
                LoadCommandCallback loadCommandCallback2 = loadCommandCallback;
                APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setup iab failed:");
                stringBuilder.append(iabResult.getMessage());
                loadCommandCallback2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
            }

        });
    }

    @Override
    public void restorePurchases(final Context context, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        this.getAllProductIdentifiers((LoadCommandCallback<List<ProductInformation>>)new LoadCommandCallbackWithTimeout<List<ProductInformation>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, null);
            }

            @Override
            protected void succeded(final List<ProductInformation> list) {
                final IabHelper iabHelper = PurchaseService.this.createIabHelper(context);
                iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener(){

                    @Override
                    public void onIabSetupFinished(IabResult object) {
                        if (object.isSuccess()) {
                            object = new LinkedList();
                            Iterator iterator = list.iterator();
                            while (iterator.hasNext()) {
                                object.add(((ProductInformation)iterator.next()).getSku());
                            }
                            iabHelper.queryInventoryAsync(true, (List<String>)object, new IabHelper.QueryInventoryFinishedListener(){

                                @Override
                                public void onQueryInventoryFinished(IabResult object, Inventory object2) {
                                    iabHelper.dispose();
                                    if (object.isSuccess()) {
                                        Logger.getInstance().debug("Purchase Service", "trying to restore purchases");
                                        for (Object object3 : list) {
                                            Object object4 = object3.getSku();
                                            if (!object2.hasPurchase((String)object4)) continue;
                                            Purchase purchase = object2.getPurchase((String)object4);
                                            object4 = new LoadCommandCallbackWithTimeout<AfterPurchaseInformation>((String)object4, purchase){
                                                final /* synthetic */ Purchase val$purchase;
                                                final /* synthetic */ String val$sku;
                                                {
                                                    this.val$sku = string;
                                                    this.val$purchase = purchase;
                                                }

                                                @Override
                                                protected void failed(APIStatusCode object, String charSequence, List<LoadFieldError> list, JSONObject jSONObject) {
                                                    object = Logger.getInstance();
                                                    charSequence = new StringBuilder();
                                                    charSequence.append("restoring ");
                                                    charSequence.append(this.val$sku);
                                                    charSequence.append(" failed");
                                                    object.debug("Purchase Service", charSequence.toString());
                                                }

                                                @Override
                                                protected void succeded(AfterPurchaseInformation object) {
                                                    object = Logger.getInstance();
                                                    StringBuilder stringBuilder = new StringBuilder();
                                                    stringBuilder.append("restoring ");
                                                    stringBuilder.append(this.val$sku);
                                                    stringBuilder.append(" succesful - consuming it");
                                                    object.debug("Purchase Service", stringBuilder.toString());
                                                    PurchaseService.this.consumePurchase(context, this.val$purchase, PurchaseService.this.getConsumeCallbackForPurchase(this.val$purchase));
                                                }
                                            };
                                            if (object3.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING) {
                                                PurchaseService.this.verifySubscriptionToken(purchase, loadCommandCallback);
                                                continue;
                                            }
                                            if (object3.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION) {
                                                PurchaseService.this.verifySubscriptionToken(purchase, (LoadCommandCallback)object4);
                                                continue;
                                            }
                                            Logger.getInstance().debug("purchasetoken", purchase.getToken());
                                            Object object5 = PurchaseService.this._context.getSharedPreferences(PurchaseService.PURCHASE_SERVICE_PREF, 0);
                                            object3 = new StringBuilder();
                                            object3.append(purchase.getOrderId());
                                            object3.append(PurchaseService.this.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
                                            object3 = object5.getString(object3.toString(), "");
                                            Object object6 = new StringBuilder();
                                            object6.append(purchase.getOrderId());
                                            object6.append(PurchaseService.this.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
                                            object5 = object5.getString(object6.toString(), "");
                                            if (!object3.isEmpty() && !object5.isEmpty()) {
                                                object6 = Logger.getInstance();
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("verify pricetier purchase with id ");
                                                stringBuilder.append((String)object3);
                                                stringBuilder.append(" type ");
                                                stringBuilder.append((String)object5);
                                                object6.debug("Purchase Service", stringBuilder.toString());
                                                PurchaseService.this.verifyPriceTierPurchaseToken(purchase, (String)object3, (String)object5, (LoadCommandCallback)object4);
                                                continue;
                                            }
                                            object4 = Logger.getInstance();
                                            object3 = new StringBuilder();
                                            object3.append("consuming without charging - error. OrderId:");
                                            object3.append(purchase.getOrderId());
                                            object4.error("Purchase Service", object3.toString());
                                            PurchaseService.this.consumePurchase(context, purchase, PurchaseService.this.getConsumeCallbackForPurchase(purchase));
                                        }
                                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "not updated");
                                        loadCommandCallback.loadingSucceded(null);
                                        return;
                                    }
                                    object2 = loadCommandCallback;
                                    APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("error loading products from playstore api:");
                                    stringBuilder.append(object.getMessage());
                                    object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "error");
                                }

                            });
                            return;
                        }
                        LoadCommandCallback loadCommandCallback = loadCommandCallback;
                        APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error loading products from playstore api: Error connecting to iabservice ");
                        stringBuilder.append(object.getMessage());
                        loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "error");
                    }

                });
            }

        });
    }

}
