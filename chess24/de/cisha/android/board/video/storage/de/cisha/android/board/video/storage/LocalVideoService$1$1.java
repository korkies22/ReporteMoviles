/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.List;
import org.json.JSONObject;

class LocalVideoService
extends LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> {
    final /* synthetic */ List val$localVideoSeries;

    LocalVideoService(int n, List list) {
        this.val$localVideoSeries = list;
        super(n);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
        1.this.this$0.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                for (VideoSeriesInformation videoSeriesInformation : videoSeriesInfoList.getList()) {
                    if (videoSeriesInformation.isAccessible() || !1.this.val$localVideoSeries.contains(videoSeriesInformation.getVideoSeriesId())) continue;
                    1.this.this$0.disableOfflineModeForSeries(videoSeriesInformation.getVideoSeriesId());
                }
            }
        });
    }

}
