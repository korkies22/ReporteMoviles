// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import com.example.android.trivialdrivesample.util.SkuDetails;
import android.util.SparseArray;
import android.app.Activity;
import de.cisha.android.board.service.jsonparser.JSONSerializedReprHolderParser;
import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesParser;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.service.jsonparser.JSONVideoParser;
import de.cisha.android.board.video.model.Video;
import java.util.Map;
import java.util.HashMap;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoId;
import android.content.Context;

public class ViedeoService implements IVideoService
{
    private static ViedeoService _instance;
    private IPurchaseService _purchaseService;
    
    private ViedeoService(final IPurchaseService purchaseService) {
        this._purchaseService = purchaseService;
    }
    
    public static IVideoService getInstance(final IPurchaseService purchaseService, final Context context) {
        if (ViedeoService._instance == null) {
            ViedeoService._instance = new ViedeoService(purchaseService);
        }
        return ViedeoService._instance;
    }
    
    private <Type> void getVideo(final VideoId videoId, final VideoSeriesId videoSeriesId, final LoadCommandCallback<Type> loadCommandCallback, final JSONAPIResultParser<Type> jsonapiResultParser) {
        if (videoId != null && videoId.getUuid() != null && videoSeriesId != null && videoSeriesId.getUuid() != null) {
            final GeneralJSONAPICommandLoader<Type> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Type>();
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("videoId", videoId.getUuid());
            hashMap.put("seriesId", videoSeriesId.getUuid());
            generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoInfo", hashMap, jsonapiResultParser, true);
        }
    }
    
    private <Type> void getVideoSeries(final VideoSeriesId videoSeriesId, final LoadCommandCallback<Type> loadCommandCallback, final JSONAPIResultParser<Type> jsonapiResultParser) {
        final GeneralJSONAPICommandLoader<Type> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Type>();
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("seriesId", videoSeriesId.getUuid());
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoSeries", hashMap, jsonapiResultParser, true);
    }
    
    @Override
    public void getVideo(final VideoId videoId, final VideoSeriesId videoSeriesId, final LoadCommandCallback<Video> loadCommandCallback) {
        this.getVideo(videoId, videoSeriesId, (LoadCommandCallback<Object>)loadCommandCallback, (JSONAPIResultParser<Object>)new JSONVideoParser());
    }
    
    @Override
    public void getVideoSeries(final VideoSeriesId videoSeriesId, final LoadCommandCallback<VideoSeries> loadCommandCallback) {
        this.getVideoSeries(videoSeriesId, (LoadCommandCallback<Object>)loadCommandCallback, (JSONAPIResultParser<Object>)new JSONVideoSeriesParser());
    }
    
    @Override
    public void getVideoSeriesList(final VideoFilter videoFilter, final LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>();
        final TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
        if (videoFilter != null) {
            treeMap.putAll(videoFilter.createParams());
        }
        treeMap.put("count", "1");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoSeriesList", (Map<String, String>)treeMap, new JSONVideoSeriesInfoListParser(), true);
    }
    
    @Override
    public void getVideoSeriesWithSerializeRepresentation(final VideoSeriesId videoSeriesId, final LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> loadCommandCallback) {
        this.getVideoSeries(videoSeriesId, loadCommandCallback, new JSONSerializedReprHolderParser((JSONAPIResultParser<Object>)new JSONVideoSeriesParser()));
    }
    
    @Override
    public void getVideoWithSerializedRepresentation(final VideoId videoId, final VideoSeriesId videoSeriesId, final LoadCommandCallback<ISerializedRepresentationHolder<Video>> loadCommandCallback) {
        this.getVideo(videoId, videoSeriesId, loadCommandCallback, new JSONSerializedReprHolderParser((JSONAPIResultParser<Object>)new JSONVideoParser()));
    }
    
    @Override
    public void purchaseVideoSeries(final VideoSeriesId videoSeriesId, final int n, final Activity activity, final LoadCommandCallback<Void> loadCommandCallback) {
        this._purchaseService.getSkuDetailsMap(new LoadCommandCallback<SparseArray<SkuDetails>>() {
            @Override
            public void loadingCancelled() {
                loadCommandCallback.loadingCancelled();
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
            }
            
            @Override
            public void loadingSucceded(final SparseArray<SkuDetails> sparseArray) {
                final SkuDetails skuDetails = (SkuDetails)sparseArray.get(n);
                if (skuDetails != null) {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("purchase intent for id: ");
                    sb.append(videoSeriesId.getUuid());
                    sb.append(" and price: ");
                    sb.append(n);
                    instance.debug("Purchase Service", sb.toString());
                    ViedeoService.this._purchaseService.purchaseProduct(activity, new Product(skuDetails, new ProductInformation(skuDetails.getSku(), videoSeriesId.getUuid(), "series")), new LoadCommandCallback<AfterPurchaseInformation>() {
                        @Override
                        public void loadingCancelled() {
                            loadCommandCallback.loadingCancelled();
                        }
                        
                        @Override
                        public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                            loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                        }
                        
                        @Override
                        public void loadingSucceded(final AfterPurchaseInformation afterPurchaseInformation) {
                            loadCommandCallback.loadingSucceded(null);
                        }
                    });
                    return;
                }
                final LoadCommandCallback val.callback = loadCommandCallback;
                final APIStatusCode internal_PURCHASE_ERROR = APIStatusCode.INTERNAL_PURCHASE_ERROR;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("There are no product info from google for the given price category ");
                sb2.append(n);
                val.callback.loadingFailed(internal_PURCHASE_ERROR, sb2.toString(), null, null);
            }
        });
    }
}
