/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONSerializedReprHolderParser;
import de.cisha.android.board.service.jsonparser.JSONVideoParser;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

public class ViedeoService
implements IVideoService {
    private static ViedeoService _instance;
    private IPurchaseService _purchaseService;

    private ViedeoService(IPurchaseService iPurchaseService) {
        this._purchaseService = iPurchaseService;
    }

    public static IVideoService getInstance(IPurchaseService iPurchaseService, Context context) {
        if (_instance == null) {
            _instance = new ViedeoService(iPurchaseService);
        }
        return _instance;
    }

    private <Type> void getVideo(VideoId videoId, VideoSeriesId videoSeriesId, LoadCommandCallback<Type> loadCommandCallback, JSONAPIResultParser<Type> jSONAPIResultParser) {
        if (videoId != null && videoId.getUuid() != null && videoSeriesId != null && videoSeriesId.getUuid() != null) {
            GeneralJSONAPICommandLoader<Type> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Type>();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("videoId", videoId.getUuid());
            hashMap.put("seriesId", videoSeriesId.getUuid());
            generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoInfo", hashMap, jSONAPIResultParser, true);
        }
    }

    private <Type> void getVideoSeries(VideoSeriesId videoSeriesId, LoadCommandCallback<Type> loadCommandCallback, JSONAPIResultParser<Type> jSONAPIResultParser) {
        GeneralJSONAPICommandLoader<Type> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Type>();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("seriesId", videoSeriesId.getUuid());
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoSeries", hashMap, jSONAPIResultParser, true);
    }

    @Override
    public void getVideo(VideoId videoId, VideoSeriesId videoSeriesId, LoadCommandCallback<Video> loadCommandCallback) {
        this.getVideo(videoId, videoSeriesId, loadCommandCallback, new JSONVideoParser());
    }

    @Override
    public void getVideoSeries(VideoSeriesId videoSeriesId, LoadCommandCallback<VideoSeries> loadCommandCallback) {
        this.getVideoSeries(videoSeriesId, loadCommandCallback, new JSONVideoSeriesParser());
    }

    @Override
    public void getVideoSeriesList(VideoFilter videoFilter, LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> loadCommandCallback) {
        GeneralJSONAPICommandLoader<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        if (videoFilter != null) {
            treeMap.putAll(videoFilter.createParams());
        }
        treeMap.put("count", "1");
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "videoAPI/GetVideoSeriesList", treeMap, new JSONVideoSeriesInfoListParser(), true);
    }

    @Override
    public void getVideoSeriesWithSerializeRepresentation(VideoSeriesId videoSeriesId, LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> loadCommandCallback) {
        this.getVideoSeries(videoSeriesId, loadCommandCallback, new JSONSerializedReprHolderParser(new JSONVideoSeriesParser()));
    }

    @Override
    public void getVideoWithSerializedRepresentation(VideoId videoId, VideoSeriesId videoSeriesId, LoadCommandCallback<ISerializedRepresentationHolder<Video>> loadCommandCallback) {
        this.getVideo(videoId, videoSeriesId, loadCommandCallback, new JSONSerializedReprHolderParser(new JSONVideoParser()));
    }

    @Override
    public void purchaseVideoSeries(final VideoSeriesId videoSeriesId, final int n, final Activity activity, final LoadCommandCallback<Void> loadCommandCallback) {
        this._purchaseService.getSkuDetailsMap(new LoadCommandCallback<SparseArray<SkuDetails>>(){

            @Override
            public void loadingCancelled() {
                loadCommandCallback.loadingCancelled();
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
            }

            @Override
            public void loadingSucceded(SparseArray<SkuDetails> object) {
                if ((object = (SkuDetails)object.get(n)) != null) {
                    Logger logger = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("purchase intent for id: ");
                    stringBuilder.append(videoSeriesId.getUuid());
                    stringBuilder.append(" and price: ");
                    stringBuilder.append(n);
                    logger.debug("Purchase Service", stringBuilder.toString());
                    ViedeoService.this._purchaseService.purchaseProduct(activity, new Product((SkuDetails)object, new ProductInformation(object.getSku(), videoSeriesId.getUuid(), "series")), new LoadCommandCallback<AfterPurchaseInformation>(){

                        @Override
                        public void loadingCancelled() {
                            loadCommandCallback.loadingCancelled();
                        }

                        @Override
                        public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                            loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                        }

                        @Override
                        public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
                            loadCommandCallback.loadingSucceded(null);
                        }
                    });
                    return;
                }
                object = loadCommandCallback;
                APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_PURCHASE_ERROR;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("There are no product info from google for the given price category ");
                stringBuilder.append(n);
                object.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
            }

        });
    }

}
