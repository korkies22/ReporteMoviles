// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.broadcast.model.SocketIOTournamentService;
import de.cisha.android.board.tactics.APITacticsExerciseService;
import de.cisha.android.board.service.integration.NewsFeedReader;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoStorageSqlite;
import de.cisha.android.board.database.service.LocalSqlGameService;
import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.android.board.broadcast.model.IBroadcastService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.video.storage.ILocalVideoService;
import android.content.Context;

public class ServiceProvider implements IServiceProvider, IServiceProviderModifier, LoginObserver
{
    private static ServiceProvider _instance;
    private IConfigService _configService;
    private Context _context;
    private ICouchImageService _couchImageService;
    private IDUUIDService _duuidService;
    private IEngineService _engineService;
    private IGameService _gameService;
    private boolean _initialized;
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
        this._initialized = false;
    }
    
    private void checkInitialization() {
        if (!this.isInitialized()) {
            throw new RuntimeException("ServiceProvider is not initialized properly- please make sure the initialize() Method was called prior to usage");
        }
    }
    
    public static IServiceProvider getInstance() {
        synchronized (ServiceProvider.class) {
            if (ServiceProvider._instance == null) {
                ServiceProvider._instance = new ServiceProvider();
            }
            return ServiceProvider._instance;
        }
    }
    
    @Deprecated
    public static IServiceProviderModifier getMutableTestSetterInstance() {
        synchronized (ServiceProvider.class) {
            if (ServiceProvider._instance == null) {
                ServiceProvider._instance = new ServiceProvider();
            }
            return ServiceProvider._instance;
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
            this._localVideoService = new LocalVideoService(this.getVideoService(), new LocalVideoStorageSqlite(this._context, this.getLoginService().getUserPrefix()), this._context);
        }
        return this._localVideoService;
    }
    
    @Override
    public ILoginService getLoginService() {
        if (this._loginService == null) {
            this.checkInitialization();
            (this._loginService = LoginService.getInstance(this._context)).addLoginListener((ILoginService.LoginObserver)this);
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
            this._newsService = new NewsService((NewsService.IRSSReader)new NewsFeedReader(), (NewsService.ILocalizedURLProvider)new NewsServiceURLProvider(this._context));
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
            return this._trackingService;
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
    public void initialize(Context applicationContext) {
        synchronized (this) {
            if (!this.isInitialized()) {
                this._context = applicationContext;
                applicationContext = applicationContext.getApplicationContext();
                if (applicationContext != null) {
                    this._context = applicationContext;
                }
                this._membershipService = UserMembershipService.getInstance(this._context);
                this._profileDataService = ProfileDataService.getInstance(this._context);
                (this._trackingService = GoogleAnalyticsTrackingService.getInstance(applicationContext, this._membershipService)).setDebugMode(this._context.getResources().getBoolean(2131034116));
                this._initialized = true;
            }
        }
    }
    
    @Override
    public boolean isInitialized() {
        return this._initialized;
    }
    
    @Override
    public void loginStateChanged(final boolean b) {
        if (!b) {
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
        }
    }
    
    @Override
    public void setConfigService(final IConfigService configService) {
        this._configService = configService;
    }
    
    @Override
    public void setCouchImageService(final ICouchImageService couchImageService) {
        this._couchImageService = couchImageService;
    }
    
    @Override
    public void setDUUIDService(final IDUUIDService duuidService) {
        this._duuidService = duuidService;
    }
    
    @Override
    public void setEngineService(final IEngineService engineService) {
        this._engineService = engineService;
    }
    
    @Override
    public void setInternetAvailabilityService(final IInternetAvailabilityService internetAvailabilityService) {
        this._internetAvailabilityService = internetAvailabilityService;
    }
    
    @Override
    public void setLocalVideoService(final ILocalVideoService localVideoService) {
        this._localVideoService = localVideoService;
    }
    
    @Override
    public void setLoginService(final ILoginService loginService) {
        this._loginService = loginService;
    }
    
    @Override
    public void setMembershipService(final IMembershipService membershipService) {
        this._membershipService = membershipService;
    }
    
    @Override
    public void setPlayzoneService(final PlayzoneService playzoneService) {
        this._playzoneService = playzoneService;
    }
    
    @Override
    public void setProfileDataService(final IProfileDataService profileDataService) {
        this._profileDataService = profileDataService;
    }
    
    @Override
    public void setPurchaseService(final IPurchaseService purchaseService) {
        this._purchaseService = purchaseService;
    }
    
    @Override
    public void setTacticsExerciseService(final ITacticsExerciseService tacticService) {
        this._tacticService = tacticService;
    }
    
    @Override
    public void setTournamentListService(final ITournamentListService tournamentListService) {
        this._tournamentListService = tournamentListService;
    }
    
    @Override
    public void setTournamentService(final ITournamentService tournamentGameService) {
        this._tournamentGameService = tournamentGameService;
    }
    
    @Override
    public void setVideoService(final IVideoService videoService) {
        this._videoService = videoService;
    }
    
    @Override
    public void setWebSettingsService(final IWebSettingsService webSettingsService) {
        this._webSettingsService = webSettingsService;
    }
}
