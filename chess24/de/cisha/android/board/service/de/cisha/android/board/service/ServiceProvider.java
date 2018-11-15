/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.android.board.broadcast.model.IBroadcastService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.android.board.broadcast.model.SocketIOTournamentService;
import de.cisha.android.board.database.service.LocalSqlGameService;
import de.cisha.android.board.service.ConfigServiceImpl;
import de.cisha.android.board.service.CouchImageService;
import de.cisha.android.board.service.CountryService;
import de.cisha.android.board.service.DUUIDService;
import de.cisha.android.board.service.EngineService;
import de.cisha.android.board.service.GoogleAnalyticsTrackingService;
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
import de.cisha.android.board.service.IServiceProvider;
import de.cisha.android.board.service.IServiceProviderModifier;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.InternetAvailabiltyService;
import de.cisha.android.board.service.LocalGameService;
import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.NewsService;
import de.cisha.android.board.service.NewsServiceURLProvider;
import de.cisha.android.board.service.OpeningTreeServicePolyglotMemory;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ProfileDataService;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.TournamentListServiceWithCache;
import de.cisha.android.board.service.UserMembershipService;
import de.cisha.android.board.service.ViedeoService;
import de.cisha.android.board.service.WebSettingsService;
import de.cisha.android.board.service.integration.NewsFeedReader;
import de.cisha.android.board.tactics.APITacticsExerciseService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoStorageSqlite;

public class ServiceProvider
implements IServiceProvider,
IServiceProviderModifier,
ILoginService.LoginObserver {
    private static ServiceProvider _instance;
    private IConfigService _configService;
    private Context _context;
    private ICouchImageService _couchImageService;
    private IDUUIDService _duuidService;
    private IEngineService _engineService;
    private IGameService _gameService;
    private boolean _initialized = false;
    private IInternetAvailabilityService _internetAvailabilityService;
    private ILocalGameService _localGameService;
    private ILocalVideoService _localVideoService;
    private ILoginService _loginService;
    private IMembershipService _membershipService;
    private INewsService _newsService;
    private PlayzoneService _playzoneService;
    private IProfileDataService _profileDataService;
    private IPurchaseService _purchaseService;
    private ITacticsExerciseService _tacticService;
    private ITournamentService _tournamentGameService;
    private ITournamentListService _tournamentListService;
    private ITrackingService _trackingService;
    private IVideoService _videoService;
    private IWebSettingsService _webSettingsService;

    private ServiceProvider() {
    }

    private void checkInitialization() {
        if (!this.isInitialized()) {
            throw new RuntimeException("ServiceProvider is not initialized properly- please make sure the initialize() Method was called prior to usage");
        }
    }

    public static IServiceProvider getInstance() {
        synchronized (ServiceProvider.class) {
            if (_instance == null) {
                _instance = new ServiceProvider();
            }
            ServiceProvider serviceProvider = _instance;
            return serviceProvider;
        }
    }

    @Deprecated
    public static IServiceProviderModifier getMutableTestSetterInstance() {
        synchronized (ServiceProvider.class) {
            if (_instance == null) {
                _instance = new ServiceProvider();
            }
            ServiceProvider serviceProvider = _instance;
            return serviceProvider;
        }
    }

    @Override
    public IBroadcastService getBroadCastService() {
        return SocketIOTournamentListService.getInstance(this._context);
    }

    @Override
    public IConfigService getConfigService() {
        if (this._configService == null) {
            this.checkInitialization();
            this._configService = ConfigServiceImpl.getInstance(this._context);
        }
        return this._configService;
    }

    @Override
    public ICouchImageService getCouchImageService() {
        if (this._couchImageService == null) {
            this.checkInitialization();
            this._couchImageService = CouchImageService.getInstance(this._context);
        }
        return this._couchImageService;
    }

    @Override
    public ICountryService getCountryService() {
        return CountryService.getInstance();
    }

    @Override
    public IDUUIDService getDUUIDService() {
        this.checkInitialization();
        if (this._duuidService == null) {
            this._duuidService = DUUIDService.getInstance(this._context);
        }
        return this._duuidService;
    }

    @Override
    public IEngineService getEngineService() {
        if (this._engineService == null) {
            this.checkInitialization();
            this._engineService = EngineService.getInstance(this._context);
        }
        return this._engineService;
    }

    @Override
    public IGameService getGameService() {
        if (this._gameService == null) {
            this.checkInitialization();
            this._gameService = LocalSqlGameService.getInstance(this._context);
        }
        return this._gameService;
    }

    @Override
    public IInternetAvailabilityService getInternetAvailabilityService() {
        if (this._internetAvailabilityService == null) {
            this.checkInitialization();
            this._internetAvailabilityService = InternetAvailabiltyService.getInstance(this._context);
        }
        return this._internetAvailabilityService;
    }

    @Override
    public ILocalGameService getLocalGameService() {
        if (this._localGameService == null) {
            this.checkInitialization();
            this._localGameService = LocalGameService.getInstance(this._context);
        }
        return this._localGameService;
    }

    @Override
    public ILocalVideoService getLocalVideoService() {
        if (this._localVideoService == null) {
            String string = this.getLoginService().getUserPrefix();
            this._localVideoService = new LocalVideoService(this.getVideoService(), new LocalVideoStorageSqlite(this._context, string), this._context);
        }
        return this._localVideoService;
    }

    @Override
    public ILoginService getLoginService() {
        if (this._loginService == null) {
            this.checkInitialization();
            this._loginService = LoginService.getInstance(this._context);
            this._loginService.addLoginListener(this);
        }
        return this._loginService;
    }

    @Override
    public IMembershipService getMembershipService() {
        this.checkInitialization();
        return this._membershipService;
    }

    @Override
    public INewsService getNewsService() {
        if (this._newsService == null) {
            this._newsService = new NewsService(new NewsFeedReader(), new NewsServiceURLProvider(this._context));
        }
        return this._newsService;
    }

    @Override
    public IOpeningTreeService getOpeningTreeService() {
        this.checkInitialization();
        return OpeningTreeServicePolyglotMemory.getInstance(this._context);
    }

    @Override
    public PlayzoneService getPlayzoneService() {
        this.checkInitialization();
        if (this._playzoneService == null) {
            this._playzoneService = PlayzoneService.getInstance(this._context);
        }
        return this._playzoneService;
    }

    @Override
    public IProfileDataService getProfileDataService() {
        if (this._profileDataService == null) {
            this.checkInitialization();
            this._profileDataService = ProfileDataService.getInstance(this._context);
        }
        return this._profileDataService;
    }

    @Override
    public IPurchaseService getPurchaseService() {
        if (this._purchaseService == null) {
            this.checkInitialization();
            this._purchaseService = PurchaseService.getInstance(this._context);
        }
        return this._purchaseService;
    }

    @Override
    public SettingsService getSettingsService() {
        this.checkInitialization();
        return SettingsService.getInstance(this._context);
    }

    @Override
    public ITacticsExerciseService getTacticsExerciseService() {
        if (this._tacticService == null) {
            this.checkInitialization();
            this._tacticService = APITacticsExerciseService.getInstance();
        }
        return this._tacticService;
    }

    @Override
    public ITournamentListService getTournamentListService() {
        if (this._tournamentListService == null) {
            this._tournamentListService = new TournamentListServiceWithCache(SocketIOTournamentListService.getInstance(this._context));
        }
        return this._tournamentListService;
    }

    @Override
    public ITournamentService getTournamentService() {
        if (this._tournamentGameService == null) {
            this.checkInitialization();
            this._tournamentGameService = new SocketIOTournamentService(this._context);
        }
        return this._tournamentGameService;
    }

    @Override
    public ITrackingService getTrackingService() {
        synchronized (this) {
            this.checkInitialization();
            ITrackingService iTrackingService = this._trackingService;
            return iTrackingService;
        }
    }

    @Override
    public IVideoService getVideoService() {
        if (this._videoService == null) {
            this.checkInitialization();
            this._videoService = ViedeoService.getInstance(this.getPurchaseService(), this._context);
        }
        return this._videoService;
    }

    @Override
    public IWebSettingsService getWebSettingsService() {
        if (this._webSettingsService == null) {
            this.checkInitialization();
            this._webSettingsService = WebSettingsService.getInstance();
        }
        return this._webSettingsService;
    }

    @Override
    public void initialize(Context context) {
        synchronized (this) {
            if (!this.isInitialized()) {
                this._context = context;
                if ((context = context.getApplicationContext()) != null) {
                    this._context = context;
                }
                this._membershipService = UserMembershipService.getInstance(this._context);
                this._profileDataService = ProfileDataService.getInstance(this._context);
                this._trackingService = GoogleAnalyticsTrackingService.getInstance(context, this._membershipService);
                this._trackingService.setDebugMode(this._context.getResources().getBoolean(2131034116));
                this._initialized = true;
            }
            return;
        }
    }

    @Override
    public boolean isInitialized() {
        return this._initialized;
    }

    @Override
    public void loginStateChanged(boolean bl) {
        if (!bl) {
            this._localVideoService = null;
        }
    }

    @Override
    public void reset() {
        synchronized (this) {
            this._engineService = null;
            this._loginService = null;
            this._internetAvailabilityService = null;
            this._tacticService = null;
            this._playzoneService = null;
            this._membershipService = null;
            return;
        }
    }

    @Override
    public void setConfigService(IConfigService iConfigService) {
        this._configService = iConfigService;
    }

    @Override
    public void setCouchImageService(ICouchImageService iCouchImageService) {
        this._couchImageService = iCouchImageService;
    }

    @Override
    public void setDUUIDService(IDUUIDService iDUUIDService) {
        this._duuidService = iDUUIDService;
    }

    @Override
    public void setEngineService(IEngineService iEngineService) {
        this._engineService = iEngineService;
    }

    @Override
    public void setInternetAvailabilityService(IInternetAvailabilityService iInternetAvailabilityService) {
        this._internetAvailabilityService = iInternetAvailabilityService;
    }

    @Override
    public void setLocalVideoService(ILocalVideoService iLocalVideoService) {
        this._localVideoService = iLocalVideoService;
    }

    @Override
    public void setLoginService(ILoginService iLoginService) {
        this._loginService = iLoginService;
    }

    @Override
    public void setMembershipService(IMembershipService iMembershipService) {
        this._membershipService = iMembershipService;
    }

    @Override
    public void setPlayzoneService(PlayzoneService playzoneService) {
        this._playzoneService = playzoneService;
    }

    @Override
    public void setProfileDataService(IProfileDataService iProfileDataService) {
        this._profileDataService = iProfileDataService;
    }

    @Override
    public void setPurchaseService(IPurchaseService iPurchaseService) {
        this._purchaseService = iPurchaseService;
    }

    @Override
    public void setTacticsExerciseService(ITacticsExerciseService iTacticsExerciseService) {
        this._tacticService = iTacticsExerciseService;
    }

    @Override
    public void setTournamentListService(ITournamentListService iTournamentListService) {
        this._tournamentListService = iTournamentListService;
    }

    @Override
    public void setTournamentService(ITournamentService iTournamentService) {
        this._tournamentGameService = iTournamentService;
    }

    @Override
    public void setVideoService(IVideoService iVideoService) {
        this._videoService = iVideoService;
    }

    @Override
    public void setWebSettingsService(IWebSettingsService iWebSettingsService) {
        this._webSettingsService = iWebSettingsService;
    }
}
