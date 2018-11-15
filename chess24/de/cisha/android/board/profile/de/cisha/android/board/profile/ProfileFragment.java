/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.media.ExifInterface
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Parcelable
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.profile.ProfileDataController;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.ProfileEditorGuestFragment;
import de.cisha.android.board.profile.RegisterWidgetController;
import de.cisha.android.board.profile.TournamentsWidgetController;
import de.cisha.android.board.profile.YourGamesViewController;
import de.cisha.android.board.profile.YourTacticsStatsController;
import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.profile.view.ProfileDataView;
import de.cisha.android.board.profile.view.RegisterWidgetView;
import de.cisha.android.board.profile.view.TournamentsWidgetView;
import de.cisha.android.board.profile.view.YourGamesView;
import de.cisha.android.board.profile.view.YourTacticsStatsView;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class ProfileFragment
extends AbstractContentFragment
implements ProfileDataController.ProfileImageClickListener,
SwipeRefreshLayout.OnRefreshListener {
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
            case 0: 
        }
        return 0;
    }

    private File getCameraImageFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_PICTURES), "Chess24");
        if (!file.exists() && !file.mkdirs()) {
            Logger logger = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Creating directories for file:");
            stringBuilder.append(file);
            stringBuilder.append(" failed");
            logger.debug("ProfileFragment", stringBuilder.toString());
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append("cameraImage.jpg");
        return new File(stringBuilder.toString());
    }

    /*
     * Exception decompiling
     */
    private int getCurrentOrientation() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CASE]], but top level block is 4[CASE]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getRotation(Context context, Uri uri) {
        int n;
        int n2;
        block5 : {
            block4 : {
                context = context.getContentResolver();
                n = 0;
                if ((context = context.query(uri, new String[]{"orientation"}, null, null, null)) == null) break block4;
                try {
                    n2 = context.moveToFirst() ? context.getInt(0) : -1;
                }
                catch (Throwable throwable) {
                    context.close();
                    throw throwable;
                }
                context.close();
                break block5;
            }
            n2 = -1;
        }
        if (n2 != -1) return n2;
        try {
            int n3 = new ExifInterface(uri.getEncodedPath()).getAttributeInt("Orientation", 1);
            if (n3 == 3) return 180;
            if (n3 == 6) return 90;
            if (n3 == 8) return 270;
            return n;
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(ProfileFragment.class.getName(), IOException.class.getName(), iOException);
        }
        return n2;
    }

    private void loadDashboardData() {
        this._loadingHelperProfileData.addLoadable(this);
        ServiceProvider.getInstance().getProfileDataService().getDashboardData((LoadCommandCallback<DashboardData>)new LoadCommandCallbackWithTimeout<DashboardData>(){

            private void notifyLoadingCompleted() {
                ProfileFragment.this._loadingHelperProfileData.loadingCompleted(ProfileFragment.this);
            }

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                this.notifyLoadingCompleted();
            }

            @Override
            protected void succeded(DashboardData dashboardData) {
                ProfileFragment.this._yourGamesViewController.setPlayzoneData(dashboardData.getPlayzoneData());
                ProfileFragment.this._yourTacticsStatsController.setTacticsData(dashboardData.getTacticsData());
                this.notifyLoadingCompleted();
            }
        });
        this._tournamentsWidgetController.reloadTournaments();
        this._profileDataController.reloadUserData();
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690228);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }

    @Override
    public List<View> getRightButtons(Context context) {
        context = this.createNavbarButtonForRessource(context, 2131231623);
        context.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelGuest ? new ProfileEditorGuestFragment() : new ProfileEditorFragment();
                ProfileFragment.this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
            }
        });
        LinkedList<View> linkedList = new LinkedList<View>();
        linkedList.add((View)context);
        return linkedList;
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

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        block23 : {
            File file;
            int n3;
            block20 : {
                int n4;
                Serializable serializable;
                Object object;
                File file3;
                File file2;
                block24 : {
                    block17 : {
                        if (n != 5738 && n != 55555) break block23;
                        n4 = -1;
                        if (n2 != -1) break block23;
                        Object var9_5 = null;
                        file = null;
                        serializable = null;
                        file3 = null;
                        file2 = null;
                        object = null;
                        if (n != 55555) break block24;
                        file3 = this.getCameraImageFile();
                        n3 = n4;
                        serializable = file;
                        if (file3 != null) {
                            n3 = n4;
                            serializable = file;
                            if (file3.exists()) {
                                void var8_19;
                                block19 : {
                                    block18 : {
                                        serializable = Uri.fromFile((File)file3);
                                        file = MediaStore.Images.Media.getBitmap((ContentResolver)this.getActivity().getContentResolver(), (Uri)serializable);
                                        try {
                                            n3 = this.getRotation((Context)this.getActivity(), (Uri)serializable);
                                            serializable = file;
                                            break block17;
                                        }
                                        catch (IOException iOException) {
                                            serializable = file;
                                            break block18;
                                        }
                                        catch (FileNotFoundException fileNotFoundException) {
                                            serializable = file;
                                            break block19;
                                        }
                                        catch (IOException iOException) {
                                            serializable = object;
                                            object = iOException;
                                        }
                                    }
                                    Logger.getInstance().debug(ProfileFragment.class.getName(), IOException.class.getName(), (Throwable)object);
                                    n3 = n4;
                                    break block17;
                                    catch (FileNotFoundException fileNotFoundException) {
                                        serializable = var9_5;
                                    }
                                }
                                Logger.getInstance().debug(ProfileFragment.class.getName(), FileNotFoundException.class.getName(), (Throwable)var8_19);
                                n3 = n4;
                            }
                        }
                    }
                    file = serializable;
                    if (serializable == null) {
                        file = (Bitmap)intent.getExtras().get("data");
                        n3 = this.assumeCurrentRotationForImage();
                    }
                    break block20;
                }
                n3 = n4;
                file = file2;
                if (n == 5738) {
                    void var7_12;
                    block22 : {
                        block21 : {
                            object = intent.getData();
                            file = MediaStore.Images.Media.getBitmap((ContentResolver)this.getActivity().getContentResolver(), (Uri)object);
                            try {
                                n3 = this.getRotation((Context)this.getActivity(), (Uri)object);
                                break block20;
                            }
                            catch (IOException iOException) {
                                break block21;
                            }
                            catch (FileNotFoundException fileNotFoundException) {
                                break block22;
                            }
                            catch (IOException iOException) {
                                file = serializable;
                                serializable = iOException;
                            }
                        }
                        Logger.getInstance().debug(ProfileFragment.class.getName(), IOException.class.getName(), (Throwable)serializable);
                        n3 = n4;
                        break block20;
                        catch (FileNotFoundException fileNotFoundException) {
                            file = file3;
                        }
                    }
                    Logger.getInstance().debug(ProfileFragment.class.getName(), FileNotFoundException.class.getName(), (Throwable)var7_12);
                    n3 = n4;
                }
            }
            if (file != null) {
                this._profileDataController.showImageLoading();
                new UploadResultTask(n3).execute((Object[])new Bitmap[]{file});
            }
        }
        super.onActivityResult(n, n2, intent);
    }

    public void onClick(View object) {
        object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (object != null && !object.isGuest()) {
            object = new RookieMoreDialogFragment();
            LinkedList<RookieMoreDialogFragment.ListOption> linkedList = new LinkedList<RookieMoreDialogFragment.ListOption>();
            if (this.getActivity().getPackageManager().hasSystemFeature("android.hardware.camera")) {
                linkedList.add(new RookieMoreDialogFragment.ListOption(){

                    @Override
                    public void executeAction() {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        if (intent.resolveActivity(ProfileFragment.this.getActivity().getPackageManager()) != null) {
                            File file = ProfileFragment.this.getCameraImageFile();
                            if (file != null) {
                                if (file.exists()) {
                                    file.delete();
                                }
                                intent.putExtra("output", (Parcelable)Uri.fromFile((File)file));
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
            linkedList.add(new RookieMoreDialogFragment.ListOption(){

                @Override
                public void executeAction() {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    ProfileFragment.this.startActivityForResult(intent, 5738);
                }

                @Override
                public String getString() {
                    return ProfileFragment.this.getString(2131690233);
                }
            });
            object.setListOptions(linkedList);
            this.getContentPresenter().showDialog((AbstractDialogFragment)object);
            return;
        }
        this.getContentPresenter().showConversionDialog(null, ConversionContext.PROFILE_PICTURE);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427519, viewGroup, false);
        this._refreshView = (SwipeRefreshLayout)layoutInflater.findViewById(2131296851);
        this._refreshView.setOnRefreshListener(this);
        this._refreshView.setColorScheme(2131099835, 2131099836, 2131099837, 2131099838);
        return layoutInflater;
    }

    @Override
    public void onRefresh() {
        this.loadDashboardData();
    }

    @Override
    public void onViewCreated(View object, Bundle object2) {
        super.onViewCreated((View)object, (Bundle)object2);
        this._loadingHelperProfileData = new LoadingHelper(new LoadingHelper.LoadingHelperListener(){

            @Override
            public void loadingStart() {
                if (!ProfileFragment.this._refreshView.isRefreshing()) {
                    ProfileFragment.this._refreshView.setRefreshing(true);
                }
            }

            @Override
            public void loadingStop(boolean bl) {
                ProfileFragment.this._refreshView.setRefreshing(false);
            }
        });
        this._profileDataController = new ProfileDataController((ProfileDataView)object.findViewById(2131296844), this, ServiceProvider.getInstance().getProfileDataService(), this._loadingHelperProfileData);
        this._registerWidgetController = new RegisterWidgetController((RegisterWidgetView)object.findViewById(2131296845), ServiceProvider.getInstance().getProfileDataService(), ServiceProvider.getInstance().getMembershipService());
        this._yourGamesViewController = new YourGamesViewController((YourGamesView)object.findViewById(2131296847), this.getFragmentManager()){

            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        this._yourTacticsStatsController = new YourTacticsStatsController((YourTacticsStatsView)object.findViewById(2131296848), this.getFragmentManager()){

            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        object2 = ServiceProvider.getInstance().getTournamentListService();
        this._tournamentsWidgetController = new TournamentsWidgetController((TournamentsWidgetView)object.findViewById(2131296846), (ITournamentListService)object2, this._loadingHelperProfileData){

            @Override
            public IContentPresenter getContentPresenter() {
                return ProfileFragment.this.getContentPresenter();
            }
        };
        object = ServiceProvider.getInstance().getProfileDataService().getCurrentDashboardData();
        if (object != null) {
            this._yourGamesViewController.setPlayzoneData(object.getPlayzoneData());
            this._yourTacticsStatsController.setTacticsData(object.getTacticsData());
        }
        this.loadDashboardData();
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    private class UploadResultTask
    extends AsyncTask<Bitmap, Void, Bitmap> {
        private int _rotation;

        public UploadResultTask(int n) {
            this._rotation = n;
        }

        private void resetImage() {
            ProfileFragment.this._profileDataController.resetProfileImage();
        }

        protected /* varargs */ Bitmap doInBackground(Bitmap ... bitmap) {
            Bitmap bitmap2 = ((Bitmap[])bitmap).length > 0 ? bitmap[0] : null;
            bitmap = bitmap2;
            if (bitmap2 != null) {
                bitmap = bitmap2 = BitmapHelper.scaleBitmapToSize(bitmap2, 300);
                if (this._rotation != -1) {
                    bitmap = bitmap2;
                    if (this._rotation != 0) {
                        bitmap = BitmapHelper.rotateBitmap(bitmap2, this._rotation);
                    }
                }
            }
            return bitmap;
        }

        protected void onPostExecute(final Bitmap bitmap) {
            ProfileFragment.this._profileDataController.setProfileImage(bitmap);
            ServiceProvider.getInstance().getProfileDataService().setProfileImage(bitmap, new LoadCommandCallback<IProfileDataService.SetProfileImageResponse>(){

                @Override
                public void loadingCancelled() {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                    UploadResultTask.this.resetImage();
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                    ProfileFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                        @Override
                        public void performReload() {
                            ProfileFragment.this._profileDataController.showImageLoading();
                            new UploadResultTask(0).execute((Object[])new Bitmap[]{bitmap});
                        }
                    });
                    UploadResultTask.this.resetImage();
                }

                @Override
                public void loadingSucceded(IProfileDataService.SetProfileImageResponse setProfileImageResponse) {
                    ProfileFragment.this._profileDataController.stopShowingImageLoading();
                }

            });
            super.onPostExecute((Object)bitmap);
        }

    }

}
