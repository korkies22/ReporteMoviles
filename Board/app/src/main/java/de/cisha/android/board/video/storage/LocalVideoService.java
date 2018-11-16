// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import de.cisha.chess.model.CishaUUID;
import android.content.Intent;
import java.util.concurrent.Executors;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesParser;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONVideoParser;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.database.Cursor;
import de.cisha.chess.util.Logger;
import java.io.File;
import android.app.DownloadManager.Query;
import android.app.DownloadManager;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoFilter;
import android.os.Handler;
import android.os.Looper;
import java.util.Iterator;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoId;
import java.util.LinkedList;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.model.VideoSeries;
import android.net.Uri;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.HashMap;
import de.cisha.android.board.service.IVideoService;
import java.util.HashSet;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.Set;
import android.content.Context;

public class LocalVideoService implements ILocalVideoService
{
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
    
    public LocalVideoService(final IVideoService videoService, final ILocalVideoStorage localVideoStorage, final Context context) {
        this._videoService = videoService;
        this._localVideoStorage = localVideoStorage;
        this._context = context;
        this._mapLocalSeries = new HashMap<VideoSeriesId, LocalVideoSeriesInfo>();
        this._mapLocalVideo = new HashMap<UniqueVideoId, LocalVideoInfo>();
        this._setSeriesUpdating = new HashSet<VideoSeriesId>();
        this._setVideosUpdating = new HashSet<UniqueVideoId>();
        this._currentDownloads = Collections.synchronizedSet((Set<MyDownloadReceiver>)Collections.newSetFromMap((Map<T, Boolean>)new WeakHashMap<Object, Boolean>()));
        this.removeNotAccessibleSeries();
        this.startUpdateDownloadStateProcess();
        this.scheduleUpdateMissingDataProcess();
    }
    
    private VideoSeriesInformation createSeriesInfo(final VideoSeries videoSeries, final VideoSeriesId videoSeriesId) {
        return new VideoSeriesInformation(videoSeriesId, videoSeries.getTitle(), videoSeries.getPriceCategoryId(), videoSeries.getDescription(), videoSeries.getGoals(), videoSeries.getLanguage(), videoSeries.getAuthor(), videoSeries.getAuthorTitleCode(), videoSeries.getVideoInformationList().size(), videoSeries.getDurationMillis(), videoSeries.getEloRange(), videoSeries.getTeaserCouchId(), videoSeries.isPurchased(), videoSeries.isAccessible());
    }
    
    private void loadVideo(final UniqueVideoId uniqueVideoId) {
        if (this._setVideosUpdating.add(uniqueVideoId)) {
            this._videoService.getVideoWithSerializedRepresentation(uniqueVideoId.getVideoId(), uniqueVideoId.getSeriesId(), new LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<Video>>(100000) {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    LocalVideoService.this._setVideosUpdating.remove(uniqueVideoId);
                }
                
                @Override
                protected void succeded(final ISerializedRepresentationHolder<Video> serializedRepresentationHolder) {
                    LocalVideoService.this.runOnBackgroundThread(new Runnable() {
                        @Override
                        public void run() {
                            if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(uniqueVideoId.getSeriesId())) {
                                LocalVideoService.this._localVideoStorage.putVideoJSON(uniqueVideoId.getSeriesId(), uniqueVideoId.getVideoId(), serializedRepresentationHolder.getJSONData());
                                LocalVideoService.this.getLocalVideo(uniqueVideoId.getVideoId(), uniqueVideoId.getSeriesId()).setVideoObjectAvailable(true);
                                LocalVideoService.this.loadVideoData(uniqueVideoId, serializedRepresentationHolder.getObject().getVideoUrlMp4());
                            }
                            LocalVideoService.this._setVideosUpdating.remove(uniqueVideoId);
                        }
                    });
                }
            });
        }
    }
    
    private void loadVideoData(final UniqueVideoId p0, final Uri p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_2        
        //     1: ifnull          681
        //     4: aload_0        
        //     5: getfield        de/cisha/android/board/video/storage/LocalVideoService._localVideoStorage:Lde/cisha/android/board/video/storage/ILocalVideoStorage;
        //     8: aload_1        
        //     9: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getSeriesId:()Lde/cisha/android/board/video/model/VideoSeriesId;
        //    12: aload_1        
        //    13: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getVideoId:()Lde/cisha/android/board/video/model/VideoId;
        //    16: invokeinterface de/cisha/android/board/video/storage/ILocalVideoStorage.getDownloadIdForVideo:(Lde/cisha/android/board/video/model/VideoSeriesId;Lde/cisha/android/board/video/model/VideoId;)J
        //    21: lstore          7
        //    23: aload_0        
        //    24: getfield        de/cisha/android/board/video/storage/LocalVideoService._context:Landroid/content/Context;
        //    27: ldc             "download"
        //    29: invokevirtual   android/content/Context.getSystemService:(Ljava/lang/String;)Ljava/lang/Object;
        //    32: checkcast       Landroid/app/DownloadManager;
        //    35: astore          18
        //    37: lconst_0       
        //    38: lstore          5
        //    40: lload           7
        //    42: lconst_0       
        //    43: lcmp           
        //    44: ifne            681
        //    47: new             Ljava/net/URL;
        //    50: dup            
        //    51: aload_2        
        //    52: invokevirtual   android/net/Uri.toString:()Ljava/lang/String;
        //    55: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //    58: invokevirtual   java/net/URL.openConnection:()Ljava/net/URLConnection;
        //    61: checkcast       Ljava/net/HttpURLConnection;
        //    64: astore          16
        //    66: aload           16
        //    68: astore          15
        //    70: aload           16
        //    72: invokevirtual   java/net/HttpURLConnection.connect:()V
        //    75: aload           16
        //    77: astore          15
        //    79: aload           16
        //    81: invokevirtual   java/net/HttpURLConnection.getContentLength:()I
        //    84: istore          4
        //    86: iload           4
        //    88: istore_3       
        //    89: aload           16
        //    91: ifnull          191
        //    94: aload           16
        //    96: invokevirtual   java/net/HttpURLConnection.disconnect:()V
        //    99: iload           4
        //   101: istore_3       
        //   102: goto            191
        //   105: astore          17
        //   107: goto            127
        //   110: astore          17
        //   112: goto            157
        //   115: astore_1       
        //   116: aconst_null    
        //   117: astore          15
        //   119: goto            669
        //   122: astore          17
        //   124: aconst_null    
        //   125: astore          16
        //   127: aload           16
        //   129: astore          15
        //   131: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   134: ldc             Lde/cisha/android/board/video/storage/LocalVideoService;.class
        //   136: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   139: ldc             Ljava/io/IOException;.class
        //   141: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   144: aload           17
        //   146: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   149: aload           16
        //   151: ifnull          189
        //   154: goto            184
        //   157: aload           16
        //   159: astore          15
        //   161: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   164: ldc             Lde/cisha/android/board/video/storage/LocalVideoService;.class
        //   166: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   169: ldc             Ljava/net/MalformedURLException;.class
        //   171: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   174: aload           17
        //   176: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   179: aload           16
        //   181: ifnull          189
        //   184: aload           16
        //   186: invokevirtual   java/net/HttpURLConnection.disconnect:()V
        //   189: iconst_0       
        //   190: istore_3       
        //   191: aload_0        
        //   192: aload_1        
        //   193: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getVideoId:()Lde/cisha/android/board/video/model/VideoId;
        //   196: aload_1        
        //   197: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getSeriesId:()Lde/cisha/android/board/video/model/VideoSeriesId;
        //   200: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.getLocalVideo:(Lde/cisha/android/board/video/model/VideoId;Lde/cisha/android/board/video/model/VideoSeriesId;)Lde/cisha/android/board/video/storage/LocalVideoInfo;
        //   203: astore          16
        //   205: iload_3        
        //   206: i2l            
        //   207: lstore          7
        //   209: aload           16
        //   211: lconst_0       
        //   212: lload           7
        //   214: invokevirtual   de/cisha/android/board/video/storage/LocalVideoInfo.setDownloadProgress:(JJ)V
        //   217: new             Landroid/os/StatFs;
        //   220: dup            
        //   221: aload_0        
        //   222: getfield        de/cisha/android/board/video/storage/LocalVideoService._context:Landroid/content/Context;
        //   225: getstatic       android/os/Environment.DIRECTORY_MOVIES:Ljava/lang/String;
        //   228: invokevirtual   android/content/Context.getExternalFilesDir:(Ljava/lang/String;)Ljava/io/File;
        //   231: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   234: invokespecial   android/os/StatFs.<init>:(Ljava/lang/String;)V
        //   237: astore          15
        //   239: aload           15
        //   241: invokevirtual   android/os/StatFs.getBlockSize:()I
        //   244: i2l            
        //   245: lstore          9
        //   247: aload           15
        //   249: invokevirtual   android/os/StatFs.getAvailableBlocks:()I
        //   252: i2l            
        //   253: lstore          11
        //   255: new             Ljava/util/LinkedList;
        //   258: dup            
        //   259: aload_0        
        //   260: getfield        de/cisha/android/board/video/storage/LocalVideoService._currentDownloads:Ljava/util/Set;
        //   263: invokespecial   java/util/LinkedList.<init>:(Ljava/util/Collection;)V
        //   266: invokevirtual   java/util/LinkedList.iterator:()Ljava/util/Iterator;
        //   269: astore          17
        //   271: aload           18
        //   273: astore          15
        //   275: aload           17
        //   277: invokeinterface java/util/Iterator.hasNext:()Z
        //   282: ifeq            389
        //   285: aload           17
        //   287: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   292: checkcast       Lde/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver;
        //   295: astore          18
        //   297: aload           15
        //   299: new             Landroid/app/DownloadManager.Query;
        //   302: dup            
        //   303: invokespecial   android/app/DownloadManager.Query.<init>:()V
        //   306: iconst_1       
        //   307: newarray        J
        //   309: dup            
        //   310: iconst_0       
        //   311: aload           18
        //   313: invokestatic    de/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver.access.1100:(Lde/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver;)J
        //   316: lastore        
        //   317: invokevirtual   android/app/DownloadManager.Query.setFilterById:([J)Landroid/app/DownloadManager.Query;
        //   320: invokevirtual   android/app/DownloadManager.query:(Landroid/app/DownloadManager.Query;)Landroid/database/Cursor;
        //   323: astore          19
        //   325: aload           19
        //   327: invokeinterface android/database/Cursor.moveToFirst:()Z
        //   332: ifeq            379
        //   335: aload           19
        //   337: aload           19
        //   339: ldc_w           "bytes_so_far"
        //   342: invokeinterface android/database/Cursor.getColumnIndex:(Ljava/lang/String;)I
        //   347: invokeinterface android/database/Cursor.getLong:(I)J
        //   352: lstore          13
        //   354: lload           5
        //   356: aload_0        
        //   357: getfield        de/cisha/android/board/video/storage/LocalVideoService._localVideoStorage:Lde/cisha/android/board/video/storage/ILocalVideoStorage;
        //   360: aload           18
        //   362: invokestatic    de/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver.access.1100:(Lde/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver;)J
        //   365: invokeinterface de/cisha/android/board/video/storage/ILocalVideoStorage.getFilesizeForVideodownload:(J)J
        //   370: lload           13
        //   372: lsub           
        //   373: ladd           
        //   374: lstore          5
        //   376: goto            379
        //   379: aload           19
        //   381: invokeinterface android/database/Cursor.close:()V
        //   386: goto            275
        //   389: lload           7
        //   391: lload           9
        //   393: lload           11
        //   395: lmul           
        //   396: lload           5
        //   398: lsub           
        //   399: lcmp           
        //   400: ifle            414
        //   403: aload           16
        //   405: getstatic       de/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE:Lde/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState;
        //   408: invokevirtual   de/cisha/android/board/video/storage/LocalVideoInfo.setState:(Lde/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState;)V
        //   411: goto            681
        //   414: aload           16
        //   416: getstatic       de/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING:Lde/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState;
        //   419: invokevirtual   de/cisha/android/board/video/storage/LocalVideoInfo.setState:(Lde/cisha/android/board/video/storage/LocalVideoInfo.LocalVideoServiceDownloadState;)V
        //   422: new             Landroid/app/DownloadManager.Request;
        //   425: dup            
        //   426: aload_2        
        //   427: invokespecial   android/app/DownloadManager.Request.<init>:(Landroid/net/Uri;)V
        //   430: astore          17
        //   432: aload           17
        //   434: iconst_0       
        //   435: invokevirtual   android/app/DownloadManager.Request.setVisibleInDownloadsUi:(Z)Landroid/app/DownloadManager.Request;
        //   438: pop            
        //   439: new             Ljava/lang/StringBuilder;
        //   442: dup            
        //   443: invokespecial   java/lang/StringBuilder.<init>:()V
        //   446: astore          18
        //   448: aload           18
        //   450: ldc_w           "Downloading Video Series "
        //   453: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   456: pop            
        //   457: aload           18
        //   459: aload_1        
        //   460: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getUuid:()Ljava/lang/String;
        //   463: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   466: pop            
        //   467: aload           17
        //   469: aload           18
        //   471: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   474: invokevirtual   android/app/DownloadManager.Request.setDescription:(Ljava/lang/CharSequence;)Landroid/app/DownloadManager.Request;
        //   477: pop            
        //   478: new             Ljava/lang/StringBuilder;
        //   481: dup            
        //   482: invokespecial   java/lang/StringBuilder.<init>:()V
        //   485: astore          18
        //   487: aload           18
        //   489: ldc_w           "chess24Video"
        //   492: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   495: pop            
        //   496: aload           18
        //   498: aload_1        
        //   499: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getUuid:()Ljava/lang/String;
        //   502: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   505: pop            
        //   506: aload           18
        //   508: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   511: astore          18
        //   513: aload           17
        //   515: aload_0        
        //   516: getfield        de/cisha/android/board/video/storage/LocalVideoService._context:Landroid/content/Context;
        //   519: getstatic       android/os/Environment.DIRECTORY_MOVIES:Ljava/lang/String;
        //   522: aload           18
        //   524: invokevirtual   android/app/DownloadManager.Request.setDestinationInExternalFilesDir:(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/app/DownloadManager.Request;
        //   527: pop            
        //   528: new             Ljava/lang/StringBuilder;
        //   531: dup            
        //   532: invokespecial   java/lang/StringBuilder.<init>:()V
        //   535: astore          19
        //   537: aload           19
        //   539: aload_0        
        //   540: getfield        de/cisha/android/board/video/storage/LocalVideoService._context:Landroid/content/Context;
        //   543: getstatic       android/os/Environment.DIRECTORY_MOVIES:Ljava/lang/String;
        //   546: invokevirtual   android/content/Context.getExternalFilesDir:(Ljava/lang/String;)Ljava/io/File;
        //   549: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   552: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   555: pop            
        //   556: aload           19
        //   558: ldc_w           "/"
        //   561: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   564: pop            
        //   565: aload           19
        //   567: aload           18
        //   569: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   572: pop            
        //   573: new             Ljava/io/File;
        //   576: dup            
        //   577: aload           19
        //   579: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   582: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   585: astore          18
        //   587: aload           18
        //   589: invokevirtual   java/io/File.exists:()Z
        //   592: ifeq            601
        //   595: aload           18
        //   597: invokevirtual   java/io/File.delete:()Z
        //   600: pop            
        //   601: aload           15
        //   603: aload           17
        //   605: invokevirtual   android/app/DownloadManager.enqueue:(Landroid/app/DownloadManager.Request;)J
        //   608: lstore          5
        //   610: aload_0        
        //   611: getfield        de/cisha/android/board/video/storage/LocalVideoService._localVideoStorage:Lde/cisha/android/board/video/storage/ILocalVideoStorage;
        //   614: aload_1        
        //   615: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getSeriesId:()Lde/cisha/android/board/video/model/VideoSeriesId;
        //   618: aload_1        
        //   619: invokevirtual   de/cisha/android/board/video/storage/LocalVideoService.UniqueVideoId.getVideoId:()Lde/cisha/android/board/video/model/VideoId;
        //   622: lload           5
        //   624: lload           7
        //   626: invokeinterface de/cisha/android/board/video/storage/ILocalVideoStorage.putVideoDownloadIdWithFilesize:(Lde/cisha/android/board/video/model/VideoSeriesId;Lde/cisha/android/board/video/model/VideoId;JJ)V
        //   631: aload_0        
        //   632: getfield        de/cisha/android/board/video/storage/LocalVideoService._context:Landroid/content/Context;
        //   635: new             Lde/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver;
        //   638: dup            
        //   639: aload_0        
        //   640: lload           5
        //   642: aload           16
        //   644: aload_2        
        //   645: invokespecial   de/cisha/android/board/video/storage/LocalVideoService.MyDownloadReceiver.<init>:(Lde/cisha/android/board/video/storage/LocalVideoService;JLde/cisha/android/board/video/storage/LocalVideoInfo;Landroid/net/Uri;)V
        //   648: new             Landroid/content/IntentFilter;
        //   651: dup            
        //   652: ldc_w           "android.intent.action.DOWNLOAD_COMPLETE"
        //   655: invokespecial   android/content/IntentFilter.<init>:(Ljava/lang/String;)V
        //   658: invokevirtual   android/content/Context.registerReceiver:(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
        //   661: pop            
        //   662: goto            681
        //   665: astore_1       
        //   666: goto            119
        //   669: aload           15
        //   671: ifnull          679
        //   674: aload           15
        //   676: invokevirtual   java/net/HttpURLConnection.disconnect:()V
        //   679: aload_1        
        //   680: athrow         
        //   681: aload_0        
        //   682: getfield        de/cisha/android/board/video/storage/LocalVideoService._updateStateOn:Z
        //   685: ifne            692
        //   688: aload_0        
        //   689: invokespecial   de/cisha/android/board/video/storage/LocalVideoService.startUpdateDownloadStateProcess:()V
        //   692: return         
        //   693: astore          17
        //   695: aconst_null    
        //   696: astore          16
        //   698: goto            157
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  47     66     693    701    Ljava/net/MalformedURLException;
        //  47     66     122    127    Ljava/io/IOException;
        //  47     66     115    119    Any
        //  70     75     110    115    Ljava/net/MalformedURLException;
        //  70     75     105    110    Ljava/io/IOException;
        //  70     75     665    669    Any
        //  79     86     110    115    Ljava/net/MalformedURLException;
        //  79     86     105    110    Ljava/io/IOException;
        //  79     86     665    669    Any
        //  131    149    665    669    Any
        //  161    179    665    669    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0127:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void loadVideoSeries(final VideoSeriesId videoSeriesId) {
        if (this._setSeriesUpdating.add(videoSeriesId)) {
            this._videoService.getVideoSeriesWithSerializeRepresentation(videoSeriesId, new LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<VideoSeries>>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    LocalVideoService.this._setSeriesUpdating.remove(videoSeriesId);
                }
                
                @Override
                protected void succeded(final ISerializedRepresentationHolder<VideoSeries> serializedRepresentationHolder) {
                    LocalVideoService.this.runOnBackgroundThread(new Runnable() {
                        @Override
                        public void run() {
                            if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(videoSeriesId)) {
                                final LinkedList<VideoId> list = new LinkedList<VideoId>();
                                final List<VideoInformation> videoInformationList = serializedRepresentationHolder.getObject().getVideoInformationList();
                                LocalVideoService.this._localVideoStorage.putVideoSeriesJSON(videoSeriesId, serializedRepresentationHolder.getJSONData());
                                final Iterator<VideoInformation> iterator = videoInformationList.iterator();
                                while (iterator.hasNext()) {
                                    final VideoId id = iterator.next().getId();
                                    list.add(id);
                                    LocalVideoService.this._localVideoStorage.putVideoJSON(videoSeriesId, id, null);
                                }
                                final LocalVideoSeriesInfo localVideoSeries = LocalVideoService.this.getLocalVideoSeries(videoSeriesId);
                                localVideoSeries.setSeriesObjectAvailable(true);
                                final LinkedList<LocalVideoInfo> localVideoList = new LinkedList<LocalVideoInfo>();
                                for (final VideoId videoId : list) {
                                    localVideoList.add(LocalVideoService.this.getLocalVideo(videoId, videoSeriesId));
                                    LocalVideoService.this.loadVideo(new UniqueVideoId(videoSeriesId, videoId));
                                }
                                localVideoSeries.setLocalVideoList(localVideoList);
                            }
                            LocalVideoService.this._setSeriesUpdating.remove(videoSeriesId);
                        }
                    });
                }
            });
        }
    }
    
    private void postDelayed(final int n, final boolean b, final Runnable runnable) {
        final Handler handler = new Handler(Looper.getMainLooper());
        if (b) {
            handler.postDelayed((Runnable)new Runnable() {
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
        this.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                final List<VideoSeriesId> allLocalVideoSeries = LocalVideoService.this._localVideoStorage.getAllLocalVideoSeries();
                if (allLocalVideoSeries.size() > 0) {
                    LocalVideoService.this._videoService.getVideoSeriesList(new VideoFilter.Builder().build(), new LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>(100000) {
                        @Override
                        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        }
                        
                        @Override
                        protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList list) {
                            LocalVideoService.this.runOnBackgroundThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (final VideoSeriesInformation videoSeriesInformation : list.getList()) {
                                        if (!videoSeriesInformation.isAccessible() && allLocalVideoSeries.contains(videoSeriesInformation.getVideoSeriesId())) {
                                            LocalVideoService.this.disableOfflineModeForSeries(videoSeriesInformation.getVideoSeriesId());
                                        }
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
        this.postDelayed(60000, true, new Runnable() {
            @Override
            public void run() {
                final Iterator<VideoSeriesId> iterator = LocalVideoService.this._localVideoStorage.getAllLocalVideoSeries().iterator();
                boolean b = false;
                while (iterator.hasNext()) {
                    final VideoSeriesId videoSeriesId = iterator.next();
                    final LocalVideoSeriesInfo localVideoSeries = LocalVideoService.this.getLocalVideoSeries(videoSeriesId);
                    if (localVideoSeries.isSeriesObjectAvailable() && localVideoSeries.getVideoList() != null) {
                        final List<LocalVideoInfo> videoList = localVideoSeries.getVideoList();
                        if (videoList == null) {
                            continue;
                        }
                        final DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                        final Iterator<LocalVideoInfo> iterator2 = videoList.iterator();
                        boolean b2 = b;
                        while (true) {
                            b = b2;
                            if (!iterator2.hasNext()) {
                                break;
                            }
                            final LocalVideoInfo localVideoInfo = iterator2.next();
                            final UniqueVideoId uniqueVideoId = new UniqueVideoId(localVideoInfo.getSeriesId(), localVideoInfo.getVideoId());
                            if (!localVideoInfo.isVideoObjectAvailable()) {
                                LocalVideoService.this.loadVideo(uniqueVideoId);
                            }
                            else {
                                final long downloadIdForVideo = LocalVideoService.this._localVideoStorage.getDownloadIdForVideo(localVideoInfo.getSeriesId(), localVideoInfo.getVideoId());
                                if (downloadIdForVideo > 0L) {
                                    final Cursor query = downloadManager.query(new DownloadManager.Query().setFilterById(new long[] { downloadIdForVideo }));
                                    if (query.moveToFirst()) {
                                        boolean b3 = b2;
                                        if (query.getInt(query.getColumnIndex("status")) == 8) {
                                            b3 = b2;
                                            if (!new File(Uri.parse(query.getString(query.getColumnIndex("local_uri"))).getPath()).exists()) {
                                                downloadManager.remove(new long[] { downloadIdForVideo });
                                                localVideoInfo.setVideoFile(null);
                                                LocalVideoService.this._localVideoStorage.putVideoDownloadIdWithFilesize(localVideoInfo.getSeriesId(), localVideoInfo.getVideoId(), 0L, 0L);
                                                LocalVideoService.this.loadVideo(uniqueVideoId);
                                                b3 = true;
                                            }
                                        }
                                        query.close();
                                        b2 = b3;
                                        continue;
                                    }
                                    LocalVideoService.this.loadVideo(uniqueVideoId);
                                }
                                else {
                                    LocalVideoService.this.loadVideo(uniqueVideoId);
                                }
                            }
                            b2 = true;
                        }
                    }
                    else {
                        LocalVideoService.this.loadVideoSeries(videoSeriesId);
                        b = true;
                    }
                }
                if (b) {
                    LocalVideoService.this.scheduleUpdateMissingDataProcess();
                }
                final Logger instance = Logger.getInstance();
                final String name = LocalVideoStorageMock.class.getName();
                final StringBuilder sb = new StringBuilder();
                sb.append("updated missing data ");
                sb.append(b);
                instance.debug(name, sb.toString());
            }
        });
    }
    
    private void startUpdateDownloadStateProcess() {
        this._updateStateOn = true;
        this.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                final DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                final List<ILocalVideoStorage.VideoDownloadRequestTupel> allVideoDownloadRequestIds = LocalVideoService.this._localVideoStorage.getAllVideoDownloadRequestIds();
                final HashMap<Object, ILocalVideoStorage.VideoDownloadRequestTupel> hashMap = new HashMap<Object, ILocalVideoStorage.VideoDownloadRequestTupel>();
                final long[] filterById = new long[allVideoDownloadRequestIds.size()];
                final Iterator<ILocalVideoStorage.VideoDownloadRequestTupel> iterator = allVideoDownloadRequestIds.iterator();
                int n = 0;
                while (iterator.hasNext()) {
                    final ILocalVideoStorage.VideoDownloadRequestTupel videoDownloadRequestTupel = iterator.next();
                    filterById[n] = videoDownloadRequestTupel.getDownloadId();
                    hashMap.put(videoDownloadRequestTupel.getDownloadId(), videoDownloadRequestTupel);
                    ++n;
                }
                final DownloadManager.Query downloadManager.Query = new DownloadManager.Query();
                if (filterById.length > 0) {
                    downloadManager.Query.setFilterById(filterById);
                }
                final Cursor query = downloadManager.query(downloadManager.Query);
                boolean b;
                if (query.moveToFirst()) {
                    final int columnIndex = query.getColumnIndex("_id");
                    final int columnIndex2 = query.getColumnIndex("status");
                    final int columnIndex3 = query.getColumnIndex("bytes_so_far");
                    final int columnIndex4 = query.getColumnIndex("local_uri");
                    b = false;
                    do {
                        final long long1 = query.getLong(columnIndex);
                        final int int1 = query.getInt(columnIndex2);
                        final long long2 = query.getLong(columnIndex3);
                        final long filesizeForVideodownload = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(long1);
                        final ILocalVideoStorage.VideoDownloadRequestTupel videoDownloadRequestTupel2 = hashMap.get(long1);
                        if (videoDownloadRequestTupel2 != null) {
                            final LocalVideoInfo localVideo = LocalVideoService.this.getLocalVideo(videoDownloadRequestTupel2.getVideoId(), videoDownloadRequestTupel2.getSeriesId());
                            localVideo.setDownloadProgress(long2, filesizeForVideodownload);
                            final Logger instance = Logger.getInstance();
                            final String name = LocalVideoStorageMock.class.getName();
                            final StringBuilder sb = new StringBuilder();
                            sb.append("download progress ");
                            sb.append(long2);
                            sb.append(" / ");
                            sb.append(filesizeForVideodownload);
                            instance.debug(name, sb.toString());
                            if (int1 == 8 && !new File(Uri.parse(query.getString(columnIndex4)).getPath()).exists()) {
                                localVideo.setDownloadProgress(0L, 0L);
                                localVideo.setVideoFile(null);
                                localVideo.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                            }
                        }
                        if (int1 != 8) {
                            b = true;
                        }
                    } while (query.moveToNext());
                    query.close();
                }
                else {
                    b = false;
                }
                if (b) {
                    new Handler(Looper.getMainLooper()).postDelayed((Runnable)new Runnable() {
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
        this.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                if (LocalVideoService.this._localVideoStorage.setVideoSeriesAsLocal(videoSeriesId, false)) {
                    LocalVideoService.this.getLocalVideoSeries(videoSeriesId).setSeriesObjectAvailable(false);
                    final List<LocalVideoInfo> videoList = LocalVideoService.this.getLocalVideoSeries(videoSeriesId).getVideoList();
                    LocalVideoService.this.getLocalVideoSeries(videoSeriesId).setLocalVideoList(null);
                    if (videoList != null) {
                        final DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                        for (final LocalVideoInfo localVideoInfo : videoList) {
                            downloadManager.remove(new long[] { LocalVideoService.this._localVideoStorage.getDownloadIdForVideo(videoSeriesId, localVideoInfo.getVideoId()) });
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
        this.runOnBackgroundThread(new Runnable() {
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
        final LinkedList<VideoSeriesInformation> list = new LinkedList<VideoSeriesInformation>();
        for (final VideoSeriesId videoSeriesId : this._localVideoStorage.getAllLocalVideoSeries()) {
            if (this.getLocalVideoSeries(videoSeriesId).isSeriesObjectAvailable()) {
                list.add(this.createSeriesInfo(this.getVideoSeries(videoSeriesId), videoSeriesId));
            }
        }
        return list;
    }
    
    @Override
    public LocalVideoInfo getLocalVideo(final VideoId videoId, final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            final UniqueVideoId uniqueVideoId = new UniqueVideoId(videoSeriesId, videoId);
            LocalVideoInfo localVideoInfo;
            if ((localVideoInfo = this._mapLocalVideo.get(uniqueVideoId)) == null) {
                final LocalVideoInfo localVideoInfo2 = new LocalVideoInfo(videoId, videoSeriesId);
                this._mapLocalVideo.put(uniqueVideoId, localVideoInfo2);
                final long downloadIdForVideo = this._localVideoStorage.getDownloadIdForVideo(videoSeriesId, videoId);
                final Cursor query = ((DownloadManager)this._context.getSystemService("download")).query(new DownloadManager.Query().setFilterById(new long[] { downloadIdForVideo }));
                if (query.moveToFirst()) {
                    Uri parse = null;
                    final String string = query.getString(query.getColumnIndex("local_uri"));
                    if (string != null) {
                        parse = Uri.parse(string);
                    }
                    if (parse != null && new File(parse.getPath()).exists()) {
                        localVideoInfo2.setVideoFile(parse);
                    }
                    final long long1 = query.getLong(query.getColumnIndex("total_size"));
                    if (parse != null && query.getInt(query.getColumnIndex("status")) == 8) {
                        localVideoInfo2.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
                        localVideoInfo2.setDownloadProgress(long1, long1);
                    }
                    else {
                        localVideoInfo2.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                        localVideoInfo2.setDownloadProgress(query.getLong(query.getColumnIndex("bytes_so_far")), long1);
                        this._context.registerReceiver((BroadcastReceiver)new MyDownloadReceiver(downloadIdForVideo, localVideoInfo2, Uri.parse(query.getString(query.getColumnIndex("uri")))), new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                    }
                }
                else {
                    localVideoInfo2.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                    localVideoInfo2.setDownloadProgress(0L, 0L);
                }
                query.close();
                localVideoInfo2.setVideoObjectAvailable(this._localVideoStorage.isVideoJSONNotNull(videoSeriesId, videoId));
                localVideoInfo = localVideoInfo2;
            }
            return localVideoInfo;
        }
    }
    
    @Override
    public LocalVideoSeriesInfo getLocalVideoSeries(final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            LocalVideoSeriesInfo localVideoSeriesInfo;
            if ((localVideoSeriesInfo = this._mapLocalSeries.get(videoSeriesId)) == null) {
                localVideoSeriesInfo = new LocalVideoSeriesInfo(videoSeriesId);
                this._mapLocalSeries.put(videoSeriesId, localVideoSeriesInfo);
                localVideoSeriesInfo.setSeriesObjectAvailable(this._localVideoStorage.isVideoSeriesJSONNotNull(videoSeriesId));
                final LinkedList<LocalVideoInfo> localVideoList = new LinkedList<LocalVideoInfo>();
                final Iterator<VideoId> iterator = this._localVideoStorage.getAllLocalVideosForSeries(videoSeriesId).iterator();
                while (iterator.hasNext()) {
                    localVideoList.add(this.getLocalVideo(iterator.next(), videoSeriesId));
                }
                localVideoSeriesInfo.setLocalVideoList(localVideoList);
            }
            if (!this._updateStateOn) {
                this.startUpdateDownloadStateProcess();
            }
            return localVideoSeriesInfo;
        }
    }
    
    @Override
    public Video getVideo(final VideoId videoId, final VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.getVideo(videoSeriesId, videoId, new JSONVideoParser());
    }
    
    @Override
    public VideoSeries getVideoSeries(final VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.getVideoSeries(videoSeriesId, new JSONVideoSeriesParser());
    }
    
    @Override
    public boolean isVideoSeriesOfflineModeEnabled(final VideoSeriesId videoSeriesId) {
        return this._localVideoStorage.isVideoSeriesLocalAvailable(videoSeriesId);
    }
    
    public void runOnBackgroundThread(final Runnable runnable) {
        if (this._executor == null) {
            this._executor = Executors.newSingleThreadExecutor();
        }
        this._executor.execute(runnable);
    }
    
    private class MyDownloadReceiver extends BroadcastReceiver
    {
        private long _id;
        private Uri _remoteUri;
        private LocalVideoInfo _video;
        
        public MyDownloadReceiver(final long id, final LocalVideoInfo video, final Uri remoteUri) {
            this._id = id;
            this._video = video;
            this._remoteUri = remoteUri;
            LocalVideoService.this._currentDownloads.add(this);
        }
        
        public void onReceive(final Context context, final Intent intent) {
            final long longExtra = intent.getLongExtra("extra_download_id", 0L);
            if (this._id == longExtra) {
                context.unregisterReceiver((BroadcastReceiver)this);
                LocalVideoService.this.runOnBackgroundThread(new Runnable() {
                    @Override
                    public void run() {
                        LocalVideoService.this._currentDownloads.remove(MyDownloadReceiver.this);
                        final DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                        final DownloadManager.Query downloadManager.Query = new DownloadManager.Query();
                        downloadManager.Query.setFilterById(new long[] { longExtra });
                        final Cursor query = downloadManager.query(downloadManager.Query);
                        if (query.moveToFirst()) {
                            final int columnIndex = query.getColumnIndex("status");
                            final int columnIndex2 = query.getColumnIndex("reason");
                            final int columnIndex3 = query.getColumnIndex("local_uri");
                            final int columnIndex4 = query.getColumnIndex("bytes_so_far");
                            final int int1 = query.getInt(columnIndex);
                            final long filesizeForVideodownload = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(longExtra);
                            if (int1 == 8) {
                                MyDownloadReceiver.this._video.setVideoFile(Uri.parse(query.getString(columnIndex3)));
                                MyDownloadReceiver.this._video.setDownloadProgress(query.getLong(columnIndex4), filesizeForVideodownload);
                                MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
                            }
                            else if (int1 == 16) {
                                if (query.getInt(columnIndex2) == 1006) {
                                    MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE);
                                }
                                downloadManager.remove(new long[] { longExtra });
                                MyDownloadReceiver.this._video.setDownloadProgress(0L, LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(longExtra));
                                LocalVideoService.this._localVideoStorage.putVideoDownloadIdWithFilesize(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId(), 0L, filesizeForVideodownload);
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
    
    private static class UniqueVideoId extends CishaUUID
    {
        private final VideoSeriesId _seriesId;
        private final VideoId _videoId;
        
        public UniqueVideoId(final VideoSeriesId seriesId, final VideoId videoId) {
            final StringBuilder sb = new StringBuilder();
            sb.append("S");
            sb.append(seriesId.getUuid());
            sb.append("V");
            sb.append(videoId.getUuid());
            super(sb.toString(), true);
            this._seriesId = seriesId;
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
