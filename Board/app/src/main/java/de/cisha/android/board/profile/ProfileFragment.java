// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import android.database.Cursor;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.chess.util.BitmapHelper;
import android.os.AsyncTask;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.profile.view.TournamentsWidgetView;
import de.cisha.android.board.profile.view.YourTacticsStatsView;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.profile.view.YourGamesView;
import de.cisha.android.board.profile.view.RegisterWidgetView;
import de.cisha.android.board.profile.view.ProfileDataView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.user.User;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import android.os.Parcelable;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import android.graphics.Bitmap;
import java.io.FileNotFoundException;
import android.provider.MediaStore.Images.Media;
import android.content.Intent;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import android.widget.ImageView;
import java.util.LinkedList;
import de.cisha.android.board.service.IMembershipService;
import android.view.View.OnClickListener;
import android.view.View;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.content.ContentResolver;
import java.io.IOException;
import android.media.ExifInterface;
import android.net.Uri;
import android.content.Context;
import android.util.DisplayMetrics;
import de.cisha.chess.util.Logger;
import android.os.Environment;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import java.io.File;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LoadingHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.AbstractContentFragment;

public class ProfileFragment extends AbstractContentFragment implements ProfileImageClickListener, OnRefreshListener
{
    private static final int GET_IMAGE_FROM_GALLERY_REQUEST_CODE = 5738;
    private static final int PROFILEIMAGE_SERVERSIZE = 300;
    private static final int ROTATION_NOT_FOUND = -1;
    private static final int TAKE_PICTURE_REQUEST_CODE = 55555;
    private LoadingHelper _loadingHelperProfileData;
    private ProfileDataController _profileDataController;
    private SwipeRefreshLayout _refreshView;
    private RegisterWidgetController _registerWidgetController;
    private TournamentsWidgetController _tournamentsWidgetController;
    private YourGamesViewController _yourGamesViewController;
    private YourTacticsStatsController _yourTacticsStatsController;
    
    private int assumeCurrentRotationForImage() {
        switch (this.getCurrentOrientation()) {
            default: {
                return -1;
            }
            case 9: {
                return 90;
            }
            case 8: {
                return 180;
            }
            case 1: {
                return 270;
            }
            case 0: {
                return 0;
            }
        }
    }
    
    private File getCameraImageFile() {
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Chess24");
        if (!file.exists() && !file.mkdirs()) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("Creating directories for file:");
            sb.append(file);
            sb.append(" failed");
            instance.debug("ProfileFragment", sb.toString());
            return null;
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getPath());
        sb2.append(File.separator);
        sb2.append("cameraImage.jpg");
        return new File(sb2.toString());
    }
    
    private int getCurrentOrientation() {
        final int rotation = this.getActivity().getWindowManager().getDefaultDisplay().getRotation();
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int widthPixels = displayMetrics.widthPixels;
        final int heightPixels = displayMetrics.heightPixels;
        int n = 0;
        if (((rotation != 0 && rotation != 2) || heightPixels <= widthPixels) && ((rotation != 1 && rotation != 3) || widthPixels <= heightPixels)) {
            switch (rotation) {
                default: {
                    return 0;
                }
                case 1: {
                    break;
                }
                case 2: {
                    return 8;
                }
                case 3: {
                    return 9;
                }
                case 0: {
                    return n;
                }
            }
        }
        else {
            switch (rotation) {
                case 3: {
                    return 8;
                }
                case 2: {
                    return 9;
                }
                case 1: {
                    return n;
                }
            }
        }
        return 1;
        n = 9;
        return n;
    }
    
    private int getRotation(Context query, final Uri uri) {
        final ContentResolver contentResolver = query.getContentResolver();
        final boolean b = false;
        query = (Context)contentResolver.query(uri, new String[] { "orientation" }, (String)null, (String[])null, (String)null);
        int int1 = 0;
        Label_0072: {
            if (query != null) {
                try {
                    if (((Cursor)query).moveToFirst()) {
                        int1 = ((Cursor)query).getInt(0);
                    }
                    else {
                        int1 = -1;
                    }
                    break Label_0072;
                }
                finally {
                    ((Cursor)query).close();
                }
            }
            int1 = -1;
        }
        if (int1 == -1) {
            try {
                final int attributeInt = new ExifInterface(uri.getEncodedPath()).getAttributeInt("Orientation", 1);
                if (attributeInt != 3) {
                    if (attributeInt != 6) {
                        if (attributeInt != 8) {
                            int1 = (b ? 1 : 0);
                        }
                        else {
                            int1 = 270;
                        }
                    }
                    else {
                        int1 = 90;
                    }
                }
                else {
                    int1 = 180;
                }
                return int1;
            }
            catch (IOException ex) {
                Logger.getInstance().debug(ProfileFragment.class.getName(), IOException.class.getName(), ex);
            }
        }
        return int1;
    }
    
    private void loadDashboardData() {
        this._loadingHelperProfileData.addLoadable(this);
        ServiceProvider.getInstance().getProfileDataService().getDashboardData(new LoadCommandCallbackWithTimeout<DashboardData>() {
            private void notifyLoadingCompleted() {
                ProfileFragment.this._loadingHelperProfileData.loadingCompleted(ProfileFragment.this);
            }
            
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                this.notifyLoadingCompleted();
            }
            
            @Override
            protected void succeded(final DashboardData dashboardData) {
                ProfileFragment.this._yourGamesViewController.setPlayzoneData(dashboardData.getPlayzoneData());
                ProfileFragment.this._yourTacticsStatsController.setTacticsData(dashboardData.getTacticsData());
                this.notifyLoadingCompleted();
            }
        });
        this._tournamentsWidgetController.reloadTournaments();
        this._profileDataController.reloadUserData();
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690228);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        final ImageView navbarButtonForRessource = this.createNavbarButtonForRessource(context, 2131231623);
        navbarButtonForRessource.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractContentFragment abstractContentFragment;
                if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelGuest) {
                    abstractContentFragment = new ProfileEditorGuestFragment();
                }
                else {
                    abstractContentFragment = new ProfileEditorFragment();
                }
                ProfileFragment.this.getContentPresenter().showFragment(abstractContentFragment, true, true);
            }
        });
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        list.add((View)navbarButtonForRessource);
        return (List<View>)list;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    @Override
    public String getTrackingName() {
        return "ViewProfile";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n == 5738 || n == 55555) {
            final int n3 = -1;
            if (n2 == -1) {
                final Bitmap bitmap = null;
                final Bitmap bitmap2 = null;
                IOException ex = null;
                final Bitmap bitmap3 = null;
                final Bitmap bitmap4 = null;
                IOException ex2 = null;
                int n4;
                Bitmap bitmap7;
                if (n == 55555) {
                    final File cameraImageFile = this.getCameraImageFile();
                    n4 = n3;
                    Bitmap bitmap5 = bitmap2;
                    if (cameraImageFile != null) {
                        n4 = n3;
                        bitmap5 = bitmap2;
                        if (cameraImageFile.exists()) {
                            final Uri fromFile = Uri.fromFile(cameraImageFile);
                            try {
                                final Bitmap bitmap6 = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), fromFile);
                                try {
                                    n4 = this.getRotation((Context)this.getActivity(), fromFile);
                                    bitmap5 = bitmap6;
                                }
                                catch (IOException ex2) {
                                    bitmap5 = bitmap6;
                                }
                                catch (FileNotFoundException ex2) {
                                    bitmap5 = bitmap6;
                                }
                            }
                            catch (IOException ex3) {
                                bitmap5 = (Bitmap)ex2;
                                ex2 = ex3;
                            }
                            catch (FileNotFoundException ex2) {
                                bitmap5 = bitmap;
                            }
                            Logger.getInstance().debug(ProfileFragment.class.getName(), FileNotFoundException.class.getName(), ex2);
                            n4 = n3;
                        }
                    }
                    if ((bitmap7 = bitmap5) == null) {
                        bitmap7 = (Bitmap)intent.getExtras().get("data");
                        n4 = this.assumeCurrentRotationForImage();
                    }
                }
                else {
                    n4 = n3;
                    bitmap7 = bitmap4;
                    if (n == 5738) {
                        try {
                            final Uri data = intent.getData();
                            bitmap7 = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data);
                            try {
                                n4 = this.getRotation((Context)this.getActivity(), data);
                            }
                            catch (IOException ex) {}
                            catch (FileNotFoundException ex) {}
                        }
                        catch (IOException ex4) {
                            bitmap7 = (Bitmap)ex;
                            ex = ex4;
                        }
                        catch (FileNotFoundException ex) {
                            bitmap7 = bitmap3;
                        }
                        Logger.getInstance().debug(ProfileFragment.class.getName(), FileNotFoundException.class.getName(), ex);
                        n4 = n3;
                    }
                }
                if (bitmap7 != null) {
                    this._profileDataController.showImageLoading();
                    new UploadResultTask(n4).execute((Object[])new Bitmap[] { bitmap7 });
                }
            }
        }
        super.onActivityResult(n, n2, intent);
    }
    
    public void onClick(final View view) {
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (currentUserData != null && !currentUserData.isGuest()) {
            final RookieMoreDialogFragment rookieMoreDialogFragment = new RookieMoreDialogFragment();
            final LinkedList<ProfileFragment.6> listOptions = new LinkedList<ProfileFragment.6>();
            if (this.getActivity().getPackageManager().hasSystemFeature("android.hardware.camera")) {
                listOptions.add(new RookieMoreDialogFragment.ListOption() {
                    @Override
                    public void executeAction() {
                        final Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        if (intent.resolveActivity(ProfileFragment.this.getActivity().getPackageManager()) != null) {
                            final File access.500 = ProfileFragment.this.getCameraImageFile();
                            if (access.500 != null) {
                                if (access.500.exists()) {
                                    access.500.delete();
                                }
                                intent.putExtra("output", (Parcelable)Uri.fromFile(access.500));
                            }
                            ProfileFragment.this.startActivityForResult(intent, 55555);
                        }
                    }
                    
                    @Override
                    public String getString() {
                        return ProfileFragment.this.getResources().getString(2131690215);
                    }
                });
            }
            listOptions.add((ProfileFragment.6)new RookieMoreDialogFragment.ListOption() {
                @Override
                public void executeAction() {
                    final Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    ProfileFragment.this.startActivityForResult(intent, 5738);
                }
                
                @Override
                public String getString() {
                    return ProfileFragment.this.getString(2131690233);
                }
            });
            rookieMoreDialogFragment.setListOptions((List<RookieMoreDialogFragment.ListOption>)listOptions);
            this.getContentPresenter().showDialog(rookieMoreDialogFragment);
            return;
        }
        this.getContentPresenter().showConversionDialog(null, ConversionContext.PROFILE_PICTURE);
    }
    
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427519, viewGroup, false);
        (this._refreshView = (SwipeRefreshLayout)inflate.findViewById(2131296851)).setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)this);
        this._refreshView.setColorScheme(2131099835, 2131099836, 2131099837, 2131099838);
        return inflate;
    }
    
    @Override
    public void onRefresh() {
        this.loadDashboardData();
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._loadingHelperProfileData = new LoadingHelper((LoadingHelper.LoadingHelperListener)new LoadingHelper.LoadingHelperListener() {
            @Override
            public void loadingStart() {
                if (!ProfileFragment.this._refreshView.isRefreshing()) {
                    ProfileFragment.this._refreshView.setRefreshing(true);
                }
            }
            
            @Override
            public void loadingStop(final boolean b) {
                ProfileFragment.this._refreshView.setRefreshing(false);
            }
        });
        this._profileDataController = new ProfileDataController((ProfileDataView)view.findViewById(2131296844), (ProfileDataController.ProfileImageClickListener)this, ServiceProvider.getInstance().getProfileDataService(), this._loadingHelperProfileData);
        this._registerWidgetController = new RegisterWidgetController((RegisterWidgetView)view.findViewById(2131296845), ServiceProvider.getInstance().getProfileDataService(), ServiceProvider.getInstance().getMembershipService());
        this._yourGamesViewController = new YourGamesViewController((YourGamesView)view.findViewById(2131296847), this.getFragmentManager()) {
            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        this._yourTacticsStatsController = new YourTacticsStatsController((YourTacticsStatsView)view.findViewById(2131296848), this.getFragmentManager()) {
            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        this._tournamentsWidgetController = new TournamentsWidgetController((TournamentsWidgetView)view.findViewById(2131296846), ServiceProvider.getInstance().getTournamentListService(), this._loadingHelperProfileData) {
            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        final DashboardData currentDashboardData = ServiceProvider.getInstance().getProfileDataService().getCurrentDashboardData();
        if (currentDashboardData != null) {
            this._yourGamesViewController.setPlayzoneData(currentDashboardData.getPlayzoneData());
            this._yourTacticsStatsController.setTacticsData(currentDashboardData.getTacticsData());
        }
        this.loadDashboardData();
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    private class UploadResultTask extends AsyncTask<Bitmap, Void, Bitmap>
    {
        private int _rotation;
        
        public UploadResultTask(final int rotation) {
            this._rotation = rotation;
        }
        
        private void resetImage() {
            ProfileFragment.this._profileDataController.resetProfileImage();
        }
        
        protected Bitmap doInBackground(final Bitmap... array) {
            Bitmap bitmap;
            if (array.length > 0) {
                bitmap = array[0];
            }
            else {
                bitmap = null;
            }
            Bitmap bitmap2 = bitmap;
            if (bitmap != null) {
                final Bitmap bitmap3 = bitmap2 = BitmapHelper.scaleBitmapToSize(bitmap, 300);
                if (this._rotation != -1) {
                    bitmap2 = bitmap3;
                    if (this._rotation != 0) {
                        bitmap2 = BitmapHelper.rotateBitmap(bitmap3, this._rotation);
                    }
                }
            }
            return bitmap2;
        }
        
        protected void onPostExecute(final Bitmap profileImage) {
            ProfileFragment.this._profileDataController.setProfileImage(profileImage);
            ServiceProvider.getInstance().getProfileDataService().setProfileImage(profileImage, new LoadCommandCallback<SetProfileImageResponse>() {
                @Override
                public void loadingCancelled() {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                    UploadResultTask.this.resetImage();
                }
                
                @Override
                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                    ProfileFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                        @Override
                        public void performReload() {
                            ProfileFragment.this._profileDataController.showImageLoading();
                            new UploadResultTask(0).execute((Object[])new Bitmap[] { profileImage });
                        }
                    });
                    UploadResultTask.this.resetImage();
                }
                
                @Override
                public void loadingSucceded(final SetProfileImageResponse setProfileImageResponse) {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                }
            });
            super.onPostExecute((Object)profileImage);
        }
    }
}
