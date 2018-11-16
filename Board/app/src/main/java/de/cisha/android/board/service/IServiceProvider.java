// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.content.Context;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.broadcast.model.IBroadcastService;

public interface IServiceProvider
{
    IBroadcastService getBroadCastService();
    
    IConfigService getConfigService();
    
    ICouchImageService getCouchImageService();
    
    ICountryService getCountryService();
    
    IDUUIDService getDUUIDService();
    
    IEngineService getEngineService();
    
    IGameService getGameService();
    
    IInternetAvailabilityService getInternetAvailabilityService();
    
    ILocalGameService getLocalGameService();
    
    ILocalVideoService getLocalVideoService();
    
    ILoginService getLoginService();
    
    IMembershipService getMembershipService();
    
    INewsService getNewsService();
    
    IOpeningTreeService getOpeningTreeService();
    
    PlayzoneService getPlayzoneService();
    
    IProfileDataService getProfileDataService();
    
    IPurchaseService getPurchaseService();
    
    SettingsService getSettingsService();
    
    ITacticsExerciseService getTacticsExerciseService();
    
    ITournamentListService getTournamentListService();
    
    ITournamentService getTournamentService();
    
    ITrackingService getTrackingService();
    
    IVideoService getVideoService();
    
    IWebSettingsService getWebSettingsService();
    
    void initialize(final Context p0);
    
    boolean isInitialized();
}
