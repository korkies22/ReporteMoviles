/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.FlushStatistics;
import com.facebook.appevents.SessionEventsState;

static final class AppEventQueue
implements GraphRequest.Callback {
    final /* synthetic */ AccessTokenAppIdPair val$accessTokenAppId;
    final /* synthetic */ SessionEventsState val$appEvents;
    final /* synthetic */ FlushStatistics val$flushState;
    final /* synthetic */ GraphRequest val$postRequest;

    AppEventQueue(AccessTokenAppIdPair accessTokenAppIdPair, GraphRequest graphRequest, SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
        this.val$accessTokenAppId = accessTokenAppIdPair;
        this.val$postRequest = graphRequest;
        this.val$appEvents = sessionEventsState;
        this.val$flushState = flushStatistics;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        com.facebook.appevents.AppEventQueue.handleResponse(this.val$accessTokenAppId, this.val$postRequest, graphResponse, this.val$appEvents, this.val$flushState);
    }
}
