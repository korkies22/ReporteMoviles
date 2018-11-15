/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.IEngineService;
import de.cisha.android.board.service.IInternetAvailabilityService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;

public interface IServiceProviderModifier {
    public void reset();

    public void setConfigService(IConfigService var1);

    public void setCouchImageService(ICouchImageService var1);

    public void setDUUIDService(IDUUIDService var1);

    public void setEngineService(IEngineService var1);

    public void setInternetAvailabilityService(IInternetAvailabilityService var1);

    public void setLocalVideoService(ILocalVideoService var1);

    public void setLoginService(ILoginService var1);

    public void setMembershipService(IMembershipService var1);

    public void setPlayzoneService(PlayzoneService var1);

    public void setProfileDataService(IProfileDataService var1);

    public void setPurchaseService(IPurchaseService var1);

    public void setTacticsExerciseService(ITacticsExerciseService var1);

    public void setTournamentListService(ITournamentListService var1);

    public void setTournamentService(ITournamentService var1);

    public void setVideoService(IVideoService var1);

    public void setWebSettingsService(IWebSettingsService var1);
}
