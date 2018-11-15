/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.service;

import android.content.Context;
import de.cisha.android.board.broadcast.model.IBroadcastService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.ICountryService;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.IEngineService;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.IInternetAvailabilityService;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.INewsService;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;

public interface IServiceProvider {
    public IBroadcastService getBroadCastService();

    public IConfigService getConfigService();

    public ICouchImageService getCouchImageService();

    public ICountryService getCountryService();

    public IDUUIDService getDUUIDService();

    public IEngineService getEngineService();

    public IGameService getGameService();

    public IInternetAvailabilityService getInternetAvailabilityService();

    public ILocalGameService getLocalGameService();

    public ILocalVideoService getLocalVideoService();

    public ILoginService getLoginService();

    public IMembershipService getMembershipService();

    public INewsService getNewsService();

    public IOpeningTreeService getOpeningTreeService();

    public PlayzoneService getPlayzoneService();

    public IProfileDataService getProfileDataService();

    public IPurchaseService getPurchaseService();

    public SettingsService getSettingsService();

    public ITacticsExerciseService getTacticsExerciseService();

    public ITournamentListService getTournamentListService();

    public ITournamentService getTournamentService();

    public ITrackingService getTrackingService();

    public IVideoService getVideoService();

    public IWebSettingsService getWebSettingsService();

    public void initialize(Context var1);

    public boolean isInitialized();
}
