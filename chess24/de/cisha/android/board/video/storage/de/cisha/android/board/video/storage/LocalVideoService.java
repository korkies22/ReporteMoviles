/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.app.DownloadManager$Request
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Environment
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.StatFs
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONVideoParser;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import de.cisha.android.board.video.storage.LocalVideoStorageMock;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class LocalVideoService
implements ILocalVideoService {
    private Context _context;
    private Set<MyDownloadReceiver> _currentDownloads;
    private ExecutorService _executor;
    private ILocalVideoStorage _localVideoStorage;
    private Map<VideoSeriesId, LocalVideoSeriesInfo> _mapLocalSeries;
    private Map<UniqueVideoId, LocalVideoInfo> _mapLocalVideo;
    private Set<VideoSeriesId> _setSeriesUpdating;
    private HashSet<UniqueVideoId> _setVideosUpdating;
    private boolean _updateStateOn;
    private IVideoService _videoService;

    public LocalVideoService(IVideoService iVideoService, ILocalVideoStorage iLocalVideoStorage, Context context) {
        this._videoService = iVideoService;
        this._localVideoStorage = iLocalVideoStorage;
        this._context = context;
        this._mapLocalSeries = new HashMap<VideoSeriesId, LocalVideoSeriesInfo>();
        this._mapLocalVideo = new HashMap<UniqueVideoId, LocalVideoInfo>();
        this._setSeriesUpdating = new HashSet<VideoSeriesId>();
        this._setVideosUpdating = new HashSet();
        this._currentDownloads = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.removeNotAccessibleSeries();
        this.startUpdateDownloadStateProcess();
        this.scheduleUpdateMissingDataProcess();
    }

    private VideoSeriesInformation createSeriesInfo(VideoSeries videoSeries, VideoSeriesId videoSeriesId) {
        return new VideoSeriesInformation(videoSeriesId, videoSeries.getTitle(), videoSeries.getPriceCategoryId(), videoSeries.getDescription(), videoSeries.getGoals(), videoSeries.getLanguage(), videoSeries.getAuthor(), videoSeries.getAuthorTitleCode(), videoSeries.getVideoInformationList().size(), videoSeries.getDurationMillis(), videoSeries.getEloRange(), videoSeries.getTeaserCouchId(), videoSeries.isPurchased(), videoSeries.isAccessible());
    }

    private void loadVideo(final UniqueVideoId uniqueVideoId) {
        if (this._setVideosUpdating.add(uniqueVideoId)) {
            this._videoService.getVideoWithSerializedRepresentation(uniqueVideoId.getVideoId(), uniqueVideoId.getSeriesId(), (LoadCommandCallback<ISerializedRepresentationHolder<Video>>)new LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<Video>>(100000){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    LocalVideoService.this._setVideosUpdating.remove(uniqueVideoId);
                }

                @Override
                protected void succeded(final ISerializedRepresentationHolder<Video> iSerializedRepresentationHolder) {
                    LocalVideoService.this.runOnBackgroundThread(new Runnable(){

                        @Override
                        public void run() {
                            if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(uniqueVideoId.getSeriesId())) {
                                LocalVideoService.this._localVideoStorage.putVideoJSON(uniqueVideoId.getSeriesId(), uniqueVideoId.getVideoId(), iSerializedRepresentationHolder.getJSONData());
                                LocalVideoService.this.getLocalVideo(uniqueVideoId.getVideoId(), uniqueVideoId.getSeriesId()).setVideoObjectAvailable(true);
                                LocalVideoService.this.loadVideoData(uniqueVideoId, ((Video)iSerializedRepresentationHolder.getObject()).getVideoUrlMp4());
                            }
                            LocalVideoService.this._setVideosUpdating.remove(uniqueVideoId);
                        }
                    });
                }

            });
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadVideoData(UniqueVideoId var1_1, Uri var2_5) {
        block20 : {
            block22 : {
                if (var2_5 != null) {
                    var7_6 = this._localVideoStorage.getDownloadIdForVideo(var1_1.getSeriesId(), var1_1.getVideoId());
                    var18_7 = (DownloadManager)this._context.getSystemService("download");
                    var5_14 = 0L;
                    if (var7_6 == 0L) {
                        block21 : {
                            block18 : {
                                block19 : {
                                    var16_15 = (HttpURLConnection)new URL(var2_5.toString()).openConnection();
                                    var15_16 = var16_15;
                                    try {
                                        var16_15.connect();
                                        var15_16 = var16_15;
                                        var3_18 = var4_17 = var16_15.getContentLength();
                                        if (var16_15 != null) {
                                            var16_15.disconnect();
                                            var3_18 = var4_17;
                                        }
                                        break block18;
                                    }
                                    catch (IOException var17_19) {
                                        break block19;
                                    }
                                    catch (MalformedURLException var17_20) {
                                        break block20;
                                    }
                                    catch (Throwable var1_2) {
                                        var15_16 = null;
                                        break block21;
                                    }
                                    catch (IOException var17_21) {
                                        var16_15 = null;
                                    }
                                }
                                var15_16 = var16_15;
                                try {
                                    Logger.getInstance().debug(LocalVideoService.class.getName(), IOException.class.getName(), (Throwable)var17_22);
                                    if (var16_15 != null) {
                                        var16_15.disconnect();
                                    }
lbl32: // 4 sources:
                                    var3_18 = 0;
                                }
                                catch (Throwable var1_4) {}
                            }
                            var16_15 = this.getLocalVideo(var1_1.getVideoId(), var1_1.getSeriesId());
                            var7_6 = var3_18;
                            var16_15.setDownloadProgress(0L, var7_6);
                            var15_16 = new StatFs(this._context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath());
                            var9_24 = var15_16.getBlockSize();
                            var11_25 = var15_16.getAvailableBlocks();
                            var17_22 = new LinkedList<MyDownloadReceiver>(this._currentDownloads).iterator();
                            var15_16 = var18_7;
                            while (var17_22.hasNext()) {
                                var18_9 = (MyDownloadReceiver)var17_22.next();
                                var19_27 = var15_16.query(new DownloadManager.Query().setFilterById(new long[]{MyDownloadReceiver.access$1100(var18_9)}));
                                if (var19_27.moveToFirst()) {
                                    var13_26 = var19_27.getLong(var19_27.getColumnIndex("bytes_so_far"));
                                    var5_14 += this._localVideoStorage.getFilesizeForVideodownload(MyDownloadReceiver.access$1100(var18_9)) - var13_26;
                                }
                                var19_27.close();
                            }
                            if (var7_6 > var9_24 * var11_25 - var5_14) {
                                var16_15.setState(LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE);
                            } else {
                                var16_15.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                                var17_22 = new DownloadManager.Request(var2_5);
                                var17_22.setVisibleInDownloadsUi(false);
                                var18_10 = new StringBuilder();
                                var18_10.append("Downloading Video Series ");
                                var18_10.append(var1_1.getUuid());
                                var17_22.setDescription((CharSequence)var18_10.toString());
                                var18_11 = new StringBuilder();
                                var18_11.append("chess24Video");
                                var18_11.append(var1_1.getUuid());
                                var18_12 = var18_11.toString();
                                var17_22.setDestinationInExternalFilesDir(this._context, Environment.DIRECTORY_MOVIES, var18_12);
                                var19_27 = new StringBuilder();
                                var19_27.append(this._context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath());
                                var19_27.append("/");
                                var19_27.append(var18_12);
                                var18_13 = new File(var19_27.toString());
                                if (var18_13.exists()) {
                                    var18_13.delete();
                                }
                                var5_14 = var15_16.enqueue(var17_22);
                                this._localVideoStorage.putVideoDownloadIdWithFilesize(var1_1.getSeriesId(), var1_1.getVideoId(), var5_14, var7_6);
                                this._context.registerReceiver((BroadcastReceiver)new MyDownloadReceiver(var5_14, (LocalVideoInfo)var16_15, var2_5), new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                            }
                            break block22;
                        }
                        if (var15_16 == null) throw var1_3;
                        var15_16.disconnect();
                        throw var1_3;
                    }
                }
            }
            if (this._updateStateOn != false) return;
            this.startUpdateDownloadStateProcess();
            return;
            catch (MalformedURLException var17_23) {
                var16_15 = null;
            }
        }
        var15_16 = var16_15;
        Logger.getInstance().debug(LocalVideoService.class.getName(), MalformedURLException.class.getName(), (Throwable)var17_22);
        if (var16_15 == null) ** GOTO lbl32
    }

    private void loadVideoSeries(final VideoSeriesId videoSeriesId) {
        if (this._setSeriesUpdating.add(videoSeriesId)) {
            this._videoService.getVideoSeriesWithSerializeRepresentation(videoSeriesId, (LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>>)new LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<VideoSeries>>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    LocalVideoService.this._setSeriesUpdating.remove(videoSeriesId);
                }

                @Override
                protected void succeded(final ISerializedRepresentationHolder<VideoSeries> iSerializedRepresentationHolder) {
                    LocalVideoService.this.runOnBackgroundThread(new Runnable(){

                        @Override
                        public void run() {
                            if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(videoSeriesId)) {
                                Object object;
                                Object object2 = new LinkedList<VideoId>();
                                Object object3 = ((VideoSeries)iSerializedRepresentationHolder.getObject()).getVideoInformationList();
                                LocalVideoService.this._localVideoStorage.putVideoSeriesJSON(videoSeriesId, iSerializedRepresentationHolder.getJSONData());
                                object3 = object3.iterator();
                                while (object3.hasNext()) {
                                    object = ((VideoInformation)object3.next()).getId();
                                    object2.add(object);
                                    LocalVideoService.this._localVideoStorage.putVideoJSON(videoSeriesId, (VideoId)object, null);
                                }
                                object3 = LocalVideoService.this.getLocalVideoSeries(videoSeriesId);
                                object3.setSeriesObjectAvailable(true);
                                object = new LinkedList();
                                object2 = object2.iterator();
                                while (object2.hasNext()) {
                                    VideoId videoId = (VideoId)object2.next();
                                    object.add(LocalVideoService.this.getLocalVideo(videoId, videoSeriesId));
                                    LocalVideoService.this.loadVideo(new UniqueVideoId(videoSeriesId, videoId));
                                }
                                object3.setLocalVideoList((List<LocalVideoInfo>)object);
                            }
                            LocalVideoService.this._setSeriesUpdating.remove(videoSeriesId);
                        }
                    });
                }

            });
        }
    }

    private void postDelayed(int n, boolean bl, final Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        if (bl) {
            handler.postDelayed(new Runnable(){

                @Override
                public void run() {
                    LocalVideoService.this.runOnBackgroundThread(runnable);
                }
            }, (long)n);
            return;
        }
        handler.postDelayed(runnable, (long)n);
    }

    private void removeNotAccessibleSeries() {
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                final List<VideoSeriesId> list = LocalVideoService.this._localVideoStorage.getAllLocalVideoSeries();
                if (list.size() > 0) {
                    LocalVideoService.this._videoService.getVideoSeriesList(new VideoFilter.Builder().build(), (LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>)new LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>(100000){

                        @Override
                        protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list2, JSONObject jSONObject) {
                        }

                        @Override
                        protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
                            LocalVideoService.this.runOnBackgroundThread(new Runnable(){

                                @Override
                                public void run() {
                                    for (VideoSeriesInformation videoSeriesInformation : videoSeriesInfoList.getList()) {
                                        if (videoSeriesInformation.isAccessible() || !list.contains(videoSeriesInformation.getVideoSeriesId())) continue;
                                        LocalVideoService.this.disableOfflineModeForSeries(videoSeriesInformation.getVideoSeriesId());
                                    }
                                }
                            });
                        }

                    });
                }
            }

        });
    }

    private void scheduleUpdateMissingDataProcess() {
        this.postDelayed(60000, true, new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                var5_1 = LocalVideoService.access$000(LocalVideoService.this).getAllLocalVideoSeries().iterator();
                var2_2 = false;
                do {
                    if (!var5_1.hasNext()) ** GOTO lbl13
                    var6_5 = var5_1.next();
                    var7_6 = LocalVideoService.this.getLocalVideoSeries((VideoSeriesId)var6_5);
                    if (var7_6.isSeriesObjectAvailable() && var7_6.getVideoList() != null) {
                        if ((var7_6 = var7_6.getVideoList()) == null) continue;
                    } else {
                        LocalVideoService.access$200(LocalVideoService.this, (VideoSeriesId)var6_5);
                        var2_2 = true;
                        continue;
lbl13: // 1 sources:
                        if (var2_2) {
                            LocalVideoService.access$500(LocalVideoService.this);
                        }
                        var5_1 = Logger.getInstance();
                        var6_5 = LocalVideoStorageMock.class.getName();
                        var7_6 = new StringBuilder();
                        var7_6.append("updated missing data ");
                        var7_6.append(var2_2);
                        var5_1.debug((String)var6_5, var7_6.toString());
                        return;
                    }
                    var6_5 = (DownloadManager)LocalVideoService.access$300(LocalVideoService.this).getSystemService("download");
                    var7_6 = var7_6.iterator();
                    var1_3 = var2_2;
                    do {
                        block10 : {
                            block9 : {
                                var2_2 = var1_3;
                                if (!var7_6.hasNext()) ** break;
                                var8_7 = (LocalVideoInfo)var7_6.next();
                                var9_8 = new UniqueVideoId(var8_7.getSeriesId(), var8_7.getVideoId());
                                if (var8_7.isVideoObjectAvailable()) break block9;
                                LocalVideoService.access$400(LocalVideoService.this, var9_8);
                                break block10;
                            }
                            var3_4 = LocalVideoService.access$000(LocalVideoService.this).getDownloadIdForVideo(var8_7.getSeriesId(), var8_7.getVideoId());
                            if (var3_4 <= 0L) ** GOTO lbl52
                            var10_9 = var6_5.query(new DownloadManager.Query().setFilterById(new long[]{var3_4}));
                            if (!var10_9.moveToFirst()) {
                                LocalVideoService.access$400(LocalVideoService.this, var9_8);
                            } else {
                                var2_2 = var1_3;
                                if (var10_9.getInt(var10_9.getColumnIndex("status")) == 8) {
                                    var2_2 = var1_3;
                                    if (!new File(Uri.parse((String)var10_9.getString(var10_9.getColumnIndex("local_uri"))).getPath()).exists()) {
                                        var6_5.remove(new long[]{var3_4});
                                        var8_7.setVideoFile(null);
                                        LocalVideoService.access$000(LocalVideoService.this).putVideoDownloadIdWithFilesize(var8_7.getSeriesId(), var8_7.getVideoId(), 0L, 0L);
                                        LocalVideoService.access$400(LocalVideoService.this, var9_8);
                                        var2_2 = true;
                                    }
                                }
                                var10_9.close();
                                var1_3 = var2_2;
                                continue;
lbl52: // 1 sources:
                                LocalVideoService.access$400(LocalVideoService.this, var9_8);
                            }
                        }
                        var1_3 = true;
                    } while (true);
                    break;
                } while (true);
            }
        });
    }

    private void startUpdateDownloadStateProcess() {
        this._updateStateOn = true;
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                Object object;
                DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                Object object2 = LocalVideoService.this._localVideoStorage.getAllVideoDownloadRequestIds();
                HashMap<Long, ILocalVideoStorage.VideoDownloadRequestTupel> hashMap = new HashMap<Long, ILocalVideoStorage.VideoDownloadRequestTupel>();
                Object object3 = new long[object2.size()];
                object2 = object2.iterator();
                int n = 0;
                while (object2.hasNext()) {
                    object = (ILocalVideoStorage.VideoDownloadRequestTupel)object2.next();
                    object3[n] = object.getDownloadId();
                    hashMap.put(object.getDownloadId(), (ILocalVideoStorage.VideoDownloadRequestTupel)object);
                    ++n;
                }
                object2 = new DownloadManager.Query();
                if (((long[])object3).length > 0) {
                    object2.setFilterById((long[])object3);
                }
                if ((downloadManager = downloadManager.query((DownloadManager.Query)object2)).moveToFirst()) {
                    int n2 = downloadManager.getColumnIndex("_id");
                    int n3 = downloadManager.getColumnIndex("status");
                    int n4 = downloadManager.getColumnIndex("bytes_so_far");
                    int n5 = downloadManager.getColumnIndex("local_uri");
                    n = 0;
                    do {
                        long l = downloadManager.getLong(n2);
                        int n6 = downloadManager.getInt(n3);
                        long l2 = downloadManager.getLong(n4);
                        long l3 = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l);
                        object3 = (ILocalVideoStorage.VideoDownloadRequestTupel)hashMap.get(l);
                        if (object3 != null) {
                            object3 = LocalVideoService.this.getLocalVideo(object3.getVideoId(), object3.getSeriesId());
                            object3.setDownloadProgress(l2, l3);
                            object2 = Logger.getInstance();
                            object = LocalVideoStorageMock.class.getName();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("download progress ");
                            stringBuilder.append(l2);
                            stringBuilder.append(" / ");
                            stringBuilder.append(l3);
                            object2.debug((String)object, stringBuilder.toString());
                            if (n6 == 8 && !new File(Uri.parse((String)downloadManager.getString(n5)).getPath()).exists()) {
                                object3.setDownloadProgress(0L, 0L);
                                object3.setVideoFile(null);
                                object3.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                            }
                        }
                        if (n6 == 8) continue;
                        n = 1;
                    } while (downloadManager.moveToNext());
                    downloadManager.close();
                } else {
                    n = 0;
                }
                if (n != 0) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            LocalVideoService.this.startUpdateDownloadStateProcess();
                        }
                    }, 5000L);
                    return;
                }
                LocalVideoService.this._updateStateOn = false;
            }

        });
    }

    @Override
    public void disableOfflineModeForSeries(final VideoSeriesId videoSeriesId) {
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                if (LocalVideoService.this._localVideoStorage.setVideoSeriesAsLocal(videoSeriesId, false)) {
                    LocalVideoService.this.getLocalVideoSeries(videoSeriesId).setSeriesObjectAvailable(false);
                    Object object = LocalVideoService.this.getLocalVideoSeries(videoSeriesId).getVideoList();
                    LocalVideoService.this.getLocalVideoSeries(videoSeriesId).setLocalVideoList(null);
                    if (object != null) {
                        DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                        object = object.iterator();
                        while (object.hasNext()) {
                            LocalVideoInfo localVideoInfo = (LocalVideoInfo)object.next();
                            downloadManager.remove(new long[]{LocalVideoService.this._localVideoStorage.getDownloadIdForVideo(videoSeriesId, localVideoInfo.getVideoId())});
                            localVideoInfo.setDownloadProgress(0L, 0L);
                            if (localVideoInfo.getVideoFile() != null) {
                                new File(localVideoInfo.getVideoFile().getPath()).delete();
                            }
                            localVideoInfo.setVideoFile(null);
                            localVideoInfo.setVideoObjectAvailable(false);
                        }
                    }
                    LocalVideoService.this._localVideoStorage.removeAllDataForVideoSeries(videoSeriesId);
                }
            }
        });
    }

    @Override
    public void enableOfflineModeForSeries(final VideoSeriesId videoSeriesId) {
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                if (LocalVideoService.this._localVideoStorage.setVideoSeriesAsLocal(videoSeriesId, true)) {
                    LocalVideoService.this.loadVideoSeries(videoSeriesId);
                    LocalVideoService.this.scheduleUpdateMissingDataProcess();
                }
            }
        });
    }

    @Override
    public List<VideoSeriesId> getAllVideoSeriesIdsInOfflineMode() {
        return this._localVideoStorage.getAllLocalVideoSeries();
    }

    @Override
    public List<VideoSeriesInformation> getAllVideoSeriesInfoAvailable() {
        LinkedList<VideoSeriesInformation> linkedList = new LinkedList<VideoSeriesInformation>();
        for (VideoSeriesId videoSeriesId : this._localVideoStorage.getAllLocalVideoSeries()) {
            if (!this.getLocalVideoSeries(videoSeriesId).isSeriesObjectAvailable()) continue;
            linkedList.add(this.createSeriesInfo(this.getVideoSeries(videoSeriesId), videoSeriesId));
        }
        return linkedList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public LocalVideoInfo getLocalVideo(VideoId videoId, VideoSeriesId videoSeriesId) {
        synchronized (this) {
            void var2_2;
            LocalVideoInfo localVideoInfo;
            UniqueVideoId uniqueVideoId = new UniqueVideoId((VideoSeriesId)var2_2, videoId);
            LocalVideoInfo localVideoInfo2 = localVideoInfo = this._mapLocalVideo.get(uniqueVideoId);
            if (localVideoInfo != null) return localVideoInfo2;
            localVideoInfo = new LocalVideoInfo(videoId, (VideoSeriesId)var2_2);
            this._mapLocalVideo.put(uniqueVideoId, localVideoInfo);
            long l = this._localVideoStorage.getDownloadIdForVideo((VideoSeriesId)var2_2, videoId);
            uniqueVideoId = ((DownloadManager)this._context.getSystemService("download")).query(new DownloadManager.Query().setFilterById(new long[]{l}));
            if (uniqueVideoId.moveToFirst()) {
                localVideoInfo2 = null;
                String string = uniqueVideoId.getString(uniqueVideoId.getColumnIndex("local_uri"));
                if (string != null) {
                    localVideoInfo2 = Uri.parse((String)string);
                }
                if (localVideoInfo2 != null && new File(localVideoInfo2.getPath()).exists()) {
                    localVideoInfo.setVideoFile((Uri)localVideoInfo2);
                }
                long l2 = uniqueVideoId.getLong(uniqueVideoId.getColumnIndex("total_size"));
                if (localVideoInfo2 != null && uniqueVideoId.getInt(uniqueVideoId.getColumnIndex("status")) == 8) {
                    localVideoInfo.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
                    localVideoInfo.setDownloadProgress(l2, l2);
                } else {
                    localVideoInfo.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                    localVideoInfo.setDownloadProgress(uniqueVideoId.getLong(uniqueVideoId.getColumnIndex("bytes_so_far")), l2);
                    localVideoInfo2 = Uri.parse((String)uniqueVideoId.getString(uniqueVideoId.getColumnIndex("uri")));
                    this._context.registerReceiver((BroadcastReceiver)new MyDownloadReceiver(l, localVideoInfo, (Uri)localVideoInfo2), new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                }
            } else {
                localVideoInfo.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                localVideoInfo.setDownloadProgress(0L, 0L);
            }
            uniqueVideoId.close();
            localVideoInfo.setVideoObjectAvailable(this._localVideoStorage.isVideoJSONNotNull((VideoSeriesId)var2_2, videoId));
            return localVideoInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public LocalVideoSeriesInfo getLocalVideoSeries(VideoSeriesId videoSeriesId) {
        synchronized (this) {
            Object object = this._mapLocalSeries.get(videoSeriesId);
            LocalVideoSeriesInfo localVideoSeriesInfo = object;
            if (object == null) {
                localVideoSeriesInfo = new LocalVideoSeriesInfo(videoSeriesId);
                this._mapLocalSeries.put(videoSeriesId, localVideoSeriesInfo);
                localVideoSeriesInfo.setSeriesObjectAvailable(this._localVideoStorage.isVideoSeriesJSONNotNull(videoSeriesId));
                object = new LinkedList();
                Iterator<VideoId> iterator = this._localVideoStorage.getAllLocalVideosForSeries(videoSeriesId).iterator();
                while (iterator.hasNext()) {
                    object.add(this.getLocalVideo(iterator.next(), videoSeriesId));
                }
                localVideoSeriesInfo.setLocalVideoList((List<LocalVideoInfo>)object);
            }
            if (!this._updateStateOn) {
                this.startUpdateDownloadStateProcess();
            }
            return localVideoSeriesInfo;
        }
    }

    @Override
    public Video getVideo(VideoId videoId, VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.getVideo(videoSeriesId, videoId, new JSONVideoParser());
    }

    @Override
    public VideoSeries getVideoSeries(VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.getVideoSeries(videoSeriesId, new JSONVideoSeriesParser());
    }

    @Override
    public boolean isVideoSeriesOfflineModeEnabled(VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.isVideoSeriesLocalAvailable(videoSeriesId);
    }

    public void runOnBackgroundThread(Runnable runnable) {
        if (this._executor == null) {
            this._executor = Executors.newSingleThreadExecutor();
        }
        this._executor.execute(runnable);
    }

    private class MyDownloadReceiver
    extends BroadcastReceiver {
        private long _id;
        private Uri _remoteUri;
        private LocalVideoInfo _video;

        public MyDownloadReceiver(long l, LocalVideoInfo localVideoInfo, Uri uri) {
            this._id = l;
            this._video = localVideoInfo;
            this._remoteUri = uri;
            LocalVideoService.this._currentDownloads.add(this);
        }

        static /* synthetic */ long access$1100(MyDownloadReceiver myDownloadReceiver) {
            return myDownloadReceiver._id;
        }

        public void onReceive(Context context, Intent intent) {
            final long l = intent.getLongExtra("extra_download_id", 0L);
            if (this._id == l) {
                context.unregisterReceiver((BroadcastReceiver)this);
                LocalVideoService.this.runOnBackgroundThread(new Runnable(){

                    @Override
                    public void run() {
                        LocalVideoService.this._currentDownloads.remove((Object)MyDownloadReceiver.this);
                        DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(new long[]{l});
                        query = downloadManager.query(query);
                        if (query.moveToFirst()) {
                            int n = query.getColumnIndex("status");
                            int n2 = query.getColumnIndex("reason");
                            int n3 = query.getColumnIndex("local_uri");
                            int n4 = query.getColumnIndex("bytes_so_far");
                            n = query.getInt(n);
                            long l2 = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l);
                            if (n == 8) {
                                MyDownloadReceiver.this._video.setVideoFile(Uri.parse((String)query.getString(n3)));
                                MyDownloadReceiver.this._video.setDownloadProgress(query.getLong(n4), l2);
                                MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
                            } else if (n == 16) {
                                if (query.getInt(n2) == 1006) {
                                    MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE);
                                }
                                downloadManager.remove(new long[]{l});
                                MyDownloadReceiver.this._video.setDownloadProgress(0L, LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l));
                                LocalVideoService.this._localVideoStorage.putVideoDownloadIdWithFilesize(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId(), 0L, l2);
                                LocalVideoService.this.loadVideoData(new UniqueVideoId(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId()), MyDownloadReceiver.this._remoteUri);
                            }
                        }
                        query.close();
                        if (LocalVideoService.this._currentDownloads.isEmpty()) {
                            LocalVideoService.this.scheduleUpdateMissingDataProcess();
                        }
                    }
                });
            }
        }

    }

    private static class UniqueVideoId
    extends CishaUUID {
        private final VideoSeriesId _seriesId;
        private final VideoId _videoId;

        public UniqueVideoId(VideoSeriesId videoSeriesId, VideoId videoId) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("S");
            stringBuilder.append(videoSeriesId.getUuid());
            stringBuilder.append("V");
            stringBuilder.append(videoId.getUuid());
            super(stringBuilder.toString(), true);
            this._seriesId = videoSeriesId;
            this._videoId = videoId;
        }

        public VideoSeriesId getSeriesId() {
            return this._seriesId;
        }

        public VideoId getVideoId() {
            return this._videoId;
        }
    }

}
