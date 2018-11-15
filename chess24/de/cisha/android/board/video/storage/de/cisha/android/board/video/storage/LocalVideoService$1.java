/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import java.util.List;
import org.json.JSONObject;

class LocalVideoService
implements Runnable {
    LocalVideoService() {
    }

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

}
