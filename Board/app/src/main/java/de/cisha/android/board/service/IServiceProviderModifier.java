// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;

public interface IServiceProviderModifier
{
    void reset();
    
    void setConfigService(final IConfigService p0);
    
    void setCouchImageService(final ICouchImageService p0);
    
    void setDUUIDService(final IDUUIDService p0);
    
    void setEngineService(final IEngineService p0);
    
    void setInternetAvailabilityService(final IInternetAvailabilityService p0);
    
    void setLocalVideoService(final ILocalVideoService p0);
    
    void setLoginService(final ILoginService p0);
    
    void setMembershipService(final IMembershipService p0);
    
    void setPlayzoneService(final PlayzoneService p0);
    
    void setProfileDataService(final IProfileDataService p0);
    
    void setPurchaseService(final IPurchaseService p0);
    
    void setTacticsExerciseService(final ITacticsExerciseService p0);
    
    void setTournamentListService(final ITournamentListService p0);
    
    void setTournamentService(final ITournamentService p0);
    
    void setVideoService(final IVideoService p0);
    
    void setWebSettingsService(final IWebSettingsService p0);
}
