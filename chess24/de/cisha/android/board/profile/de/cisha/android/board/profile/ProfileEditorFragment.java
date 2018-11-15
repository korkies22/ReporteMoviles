/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.format.DateFormat
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.Toast
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.board.profile.GenderOption;
import de.cisha.android.board.profile.GoogleAnalyticsSettingController;
import de.cisha.android.board.profile.PrivacySettingsController;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.profile.view.PrivacySettingChooserView;
import de.cisha.android.board.profile.view.PrivacySettingView;
import de.cisha.android.board.profile.view.ProfileSavedToastView;
import de.cisha.android.board.profile.view.RookieFormErrorDialogFragment;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SetUserDataParamsBuilder;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.CustomDatePickerDialog;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.chess.model.Country;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class ProfileEditorFragment
extends AbstractContentFragment
implements PrivacySettingChooserView.PrivacySettingChangedListener {
    public static final String EDIT_PROFILE_TRACKING_NAME = "EditProfile";
    private GoogleAnalyticsSettingController _analyticsController;
    private CustomCountryChoser _countryField;
    private CustomEditDate _dateField;
    private EditEmailDialogFragment _dialogEditEmail;
    private EditPasswordDialogFragment _dialogEditPassword;
    private CustomEditText _emailField;
    private CustomEditText _firstnameField;
    private CustomTextView _mobilePremiumButton;
    private CustomTextView _mobilePremiumDateText;
    private CustomTextView _mobilePremiumText;
    private CustomEditPassword _passwordField;
    private Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> _privacyMap;
    private PrivacySettingsController _privacySettingsController;
    private User.Gender _selectedGender;
    private CustomEditText _sexField;
    private CustomEditText _surnameField;
    private User _user;
    private CustomEditText _usernameField;
    private boolean _viewCreated;
    private CustomTextView _webPremiumButton;

    private String formatDate(Date date) {
        String string = "";
        if (date != null) {
            string = DateFormat.getDateFormat((Context)this.getActivity()).format(date);
        }
        return string;
    }

    private void loadPrivacySettings() {
        ServiceProvider.getInstance().getWebSettingsService().getPrivacySettings((LoadCommandCallback<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>>)new LoadCommandCallbackWithTimeout<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            }

            @Override
            protected void succeded(Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> map) {
                ProfileEditorFragment.this._privacyMap = map;
                ProfileEditorFragment.this.setPrivacyValues();
            }
        });
    }

    private void loadProfileData() {
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getProfileDataService().getUserData((LoadCommandCallback<User>)new LoadCommandCallbackWithTimeout<User>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        ProfileEditorFragment.this.loadProfileData();
                    }
                }, true);
            }

            @Override
            protected void succeded(User user) {
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this._user = user;
                ProfileEditorFragment.this.updateEditFields();
                ProfileEditorFragment.this.updatePremium();
            }

        });
    }

    private void setEmail(final String string, String string2) {
        LoadCommandCallbackWithTimeout<Map<String, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<String, String>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string2, final List<LoadFieldError> list, JSONObject jSONObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        ProfileEditorFragment.this.getView().post(new Runnable(){

                            @Override
                            public void run() {
                                ProfileEditorFragment.this.hideDialogWaiting();
                                if (ProfileEditorFragment.this._dialogEditEmail != null) {
                                    ProfileEditorFragment.this._dialogEditEmail.setErrors(list);
                                }
                            }
                        });
                    }

                });
            }

            @Override
            protected void succeded(Map<String, String> map) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        if (ProfileEditorFragment.this._dialogEditEmail != null && ProfileEditorFragment.this._dialogEditEmail.isResumed()) {
                            ProfileEditorFragment.this._dialogEditEmail.dismiss();
                        }
                        ProfileEditorFragment.this.hideDialogWaiting();
                        ProfileEditorFragment.this._emailField.setText(string);
                    }
                });
            }

        };
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getProfileDataService().setEmail(string, string2, (LoadCommandCallback<Map<String, String>>)loadCommandCallbackWithTimeout);
    }

    private void setPassword(String string, String string2, String string3) {
        LoadCommandCallbackWithTimeout<Map<String, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<String, String>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, final List<LoadFieldError> list, JSONObject jSONObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        ProfileEditorFragment.this.getView().post(new Runnable(){

                            @Override
                            public void run() {
                                if (ProfileEditorFragment.this._dialogEditPassword != null && list != null) {
                                    ProfileEditorFragment.this._dialogEditPassword.setErrors(list);
                                }
                                ProfileEditorFragment.this.hideDialogWaiting();
                            }
                        });
                    }

                });
            }

            @Override
            protected void succeded(Map<String, String> map) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        ProfileEditorFragment.this.hideDialogWaiting();
                        if (ProfileEditorFragment.this._dialogEditPassword != null && ProfileEditorFragment.this._dialogEditPassword.isResumed()) {
                            ProfileEditorFragment.this._dialogEditPassword.dismiss();
                        }
                    }
                });
            }

        };
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getProfileDataService().setPassword(string, string2, string3, (LoadCommandCallback<Map<String, String>>)loadCommandCallbackWithTimeout);
    }

    private void setPrivacyValues() {
        if (this._viewCreated) {
            this.getView().post(new Runnable(){

                @Override
                public void run() {
                    if (ProfileEditorFragment.this._privacySettingsController != null && ProfileEditorFragment.this._privacyMap != null) {
                        ProfileEditorFragment.this._privacySettingsController.setPrivacySettingValues(ProfileEditorFragment.this._privacyMap);
                    }
                }
            });
        }
    }

    private void updateEditFields() {
        if (this.getView() != null) {
            this.getView().post(new Runnable(){

                @Override
                public void run() {
                    if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
                        ProfileEditorFragment.this._passwordField.setText("dummylongpassword");
                        CustomEditText customEditText = ProfileEditorFragment.this._emailField;
                        Object object = ProfileEditorFragment.this._user.getEmail() != null ? ProfileEditorFragment.this._user.getEmail() : "";
                        customEditText.setText((CharSequence)object);
                        customEditText = ProfileEditorFragment.this._surnameField;
                        object = ProfileEditorFragment.this._user.getSurname() != null ? ProfileEditorFragment.this._user.getSurname() : "";
                        customEditText.setText((CharSequence)object);
                        customEditText = ProfileEditorFragment.this._firstnameField;
                        object = ProfileEditorFragment.this._user.getFirstname() != null ? ProfileEditorFragment.this._user.getFirstname() : "";
                        customEditText.setText((CharSequence)object);
                        customEditText = ProfileEditorFragment.this._usernameField;
                        object = ProfileEditorFragment.this._user.getNickname() != null ? ProfileEditorFragment.this._user.getNickname() : "";
                        customEditText.setText((CharSequence)object);
                        ProfileEditorFragment.this._countryField.setCountry(ProfileEditorFragment.this._user.getCountry());
                        object = ProfileEditorFragment.this._user.getBirthday();
                        if (object != null) {
                            ProfileEditorFragment.this._dateField.setDate((Date)object);
                        }
                        object = ProfileEditorFragment.this._user.getGender() != null ? ProfileEditorFragment.this._user.getGender() : User.Gender.MALE;
                        ProfileEditorFragment.this._selectedGender = (User.Gender)((Object)object);
                        ProfileEditorFragment.this._sexField.setText(ProfileEditorFragment.this.getString(object.getNameResId()));
                    }
                }
            });
        }
    }

    private void updatePremium() {
        if (this.getView() != null) {
            this.getView().post(new Runnable(){

                @Override
                public void run() {
                    if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
                        Object object = ProfileEditorFragment.this._user;
                        Date date = new Date();
                        Subscription subscription = object.getSubscription(SubscriptionType.PREMIUM);
                        boolean bl = true;
                        boolean bl2 = subscription != null && subscription.getExpirationDate().after(date);
                        if ((object = object.getSubscription(SubscriptionType.PREMIUM_MOBILE)) == null || !object.getExpirationDate().after(date)) {
                            bl = false;
                        }
                        ProfileEditorFragment.this._mobilePremiumButton.setVisibility(8);
                        ProfileEditorFragment.this._webPremiumButton.setVisibility(8);
                        ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(8);
                        ProfileEditorFragment.this._mobilePremiumText.setVisibility(8);
                        if (bl2) {
                            ProfileEditorFragment.this._webPremiumButton.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(subscription.getExpirationDate()));
                            ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                            return;
                        }
                        if (bl) {
                            ProfileEditorFragment.this._mobilePremiumButton.setText(2131689958);
                            ProfileEditorFragment.this._mobilePremiumButton.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(object.getExpirationDate()));
                            ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumText.setText(2131689960);
                            ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                            return;
                        }
                        ProfileEditorFragment.this._mobilePremiumButton.setText(2131689957);
                        ProfileEditorFragment.this._mobilePremiumButton.setVisibility(0);
                        ProfileEditorFragment.this._mobilePremiumText.setText(2131689956);
                        ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                    }
                }
            });
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690224);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }

    @Override
    public List<View> getRightButtons(Context context) {
        context = this.createNavbarButtonForRessource(context, 2131231653);
        context.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (ProfileEditorFragment.this._user != null) {
                    object = new SetUserDataParamsBuilder().setFirstname(ProfileEditorFragment.this._firstnameField.getEditText().getText().toString()).setSurname(ProfileEditorFragment.this._surnameField.getEditText().getText().toString()).setCountry(ProfileEditorFragment.this._countryField.getCountry()).setBirthdate(ProfileEditorFragment.this._user.getBirthday()).setGender(ProfileEditorFragment.this._selectedGender).createParams();
                    ProfileEditorFragment.this.showDialogWaiting(false, false, "", null);
                    ServiceProvider.getInstance().getProfileDataService().setUserData((Map<String, String>)object, new SetDataCallback((Map<String, String>)object));
                    return;
                }
                ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
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
        return EDIT_PROFILE_TRACKING_NAME;
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileEditorFragment.this.loadProfileData();
                ProfileEditorFragment.this.loadPrivacySettings();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return (ViewGroup)layoutInflater.inflate(2131427515, viewGroup, false);
    }

    @Override
    public void onDestroyView() {
        this._viewCreated = false;
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        this.hideKeyboard();
        super.onPause();
    }

    @Override
    public void onPrivacySettingChanged(final IWebSettingsService.PrivacySetting privacySetting, final IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        LoadCommandCallbackWithTimeout<Void> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Void>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        if (ProfileEditorFragment.this.isResumed()) {
                            Toast.makeText((Context)ProfileEditorFragment.this.getActivity(), (CharSequence)"set setting failed", (int)0).show();
                        }
                        ProfileEditorFragment.this.hideDialogWaiting();
                        ProfileEditorFragment.this._privacySettingsController.resetPrivacySetting(privacySetting);
                    }
                });
            }

            @Override
            protected void succeded(Void void_) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        ProfileEditorFragment.this._privacySettingsController.updatePrivacySetting(privacySetting, privacyValue);
                        ProfileEditorFragment.this.hideDialogWaiting();
                    }
                });
            }

        };
        this.showDialogWaiting(false, false, "", null);
        ServiceProvider.getInstance().getWebSettingsService().setPrivacySetting(privacySetting, privacyValue, (LoadCommandCallback<Void>)loadCommandCallbackWithTimeout);
    }

    @Override
    public void onViewCreated(View view, Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        this._passwordField = (CustomEditPassword)view.findViewById(2131296831);
        this._passwordField.getEditText().setEnabled(false);
        object = new View.OnClickListener(){

            public void onClick(View view) {
                ProfileEditorFragment.this._passwordField.getEditText().requestFocus();
                ProfileEditorFragment.this._dialogEditPassword = new EditPasswordDialogFragment();
                ProfileEditorFragment.this._dialogEditPassword.setOnPasswordChangeListener(new EditPasswordDialogFragment.OnPasswordChangedListener(){

                    @Override
                    public void onPasswordChanged(String string, String string2, String string3) {
                        ProfileEditorFragment.this.setPassword(string, string2, string3);
                    }
                });
                ProfileEditorFragment.this._dialogEditPassword.show(ProfileEditorFragment.this.getFragmentManager(), null);
            }

        };
        view.findViewById(2131296832).setOnClickListener((View.OnClickListener)object);
        this._emailField = (CustomEditText)view.findViewById(2131296827);
        this._emailField.getEditText().setEnabled(false);
        new View.OnClickListener(){

            public void onClick(View view) {
                ProfileEditorFragment.this._emailField.getEditText().requestFocus();
                ProfileEditorFragment.this._dialogEditEmail = new EditEmailDialogFragment();
                ProfileEditorFragment.this._dialogEditEmail.setOnEmailChangedListener(new EditEmailDialogFragment.OnEmailChangedListener(){

                    @Override
                    public void onEmailChanged(String string, String string2) {
                        ProfileEditorFragment.this.setEmail(string, string2);
                    }
                });
                ProfileEditorFragment.this._dialogEditEmail.show(ProfileEditorFragment.this.getFragmentManager(), null);
            }

        };
        this._surnameField = (CustomEditText)view.findViewById(2131296842);
        this._surnameField.getEditText().setInputType(524289);
        this._firstnameField = (CustomEditText)view.findViewById(2131296829);
        this._firstnameField.getEditText().setInputType(524289);
        this._usernameField = (CustomEditText)view.findViewById(2131296843);
        this._usernameField.getEditText().setEnabled(false);
        this._usernameField.getEditText().setFocusable(false);
        this._countryField = (CustomCountryChoser)view.findViewById(2131296825);
        this._dateField = (CustomEditDate)view.findViewById(2131296823);
        object = new View.OnClickListener(){

            public void onClick(View object) {
                if (ProfileEditorFragment.this._user != null) {
                    ProfileEditorFragment.this._dateField.getEditText().requestFocus();
                    object = new CustomDatePickerDialog();
                    object.setOnDateChangedListener(new DatePicker.OnDateChangedListener(){

                        public void onDateChanged(DatePicker object, int n, int n2, int n3) {
                            object = new GregorianCalendar(n, n2, n3);
                            ProfileEditorFragment.this._user.setBirthday(object.getTime());
                            ProfileEditorFragment.this._dateField.setDate(object.getTime());
                        }
                    });
                    object.setTime(ProfileEditorFragment.this._user.getBirthday());
                    object.show(ProfileEditorFragment.this.getFragmentManager(), null);
                }
            }

        };
        view.findViewById(2131296824).setOnClickListener((View.OnClickListener)object);
        this._sexField = (CustomEditText)view.findViewById(2131296840);
        this._sexField.getEditText().setInputType(0);
        object = new View.OnClickListener(){

            public void onClick(View object) {
                ProfileEditorFragment.this._sexField.getEditText().requestFocus();
                OptionDialogFragment<View> optionDialogFragment = new OptionDialogFragment<View>();
                optionDialogFragment.setTitle((CharSequence)ProfileEditorFragment.this.getString(2131690200));
                LinkedList<Object> linkedList = new LinkedList<Object>();
                object = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.MALE.getNameResId()), User.Gender.MALE);
                linkedList.add(object);
                GenderOption genderOption = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.FEMALE.getNameResId()), User.Gender.FEMALE);
                linkedList.add(genderOption);
                if (ProfileEditorFragment.this._selectedGender != User.Gender.MALE) {
                    object = genderOption;
                }
                optionDialogFragment.setOptions((List<View>)linkedList, (View)object);
                optionDialogFragment.setOptionSelectionListener(new OptionDialogFragment.OptionSelectionListener<GenderOption>(){

                    @Override
                    public void onOptionSelected(GenderOption genderOption) {
                        ProfileEditorFragment.this._selectedGender = genderOption.getSex();
                        ProfileEditorFragment.this._sexField.setText(genderOption.getName());
                    }
                });
                optionDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), null);
            }

        };
        view.findViewById(2131296841).setOnClickListener((View.OnClickListener)object);
        this._privacySettingsController = new PrivacySettingsController((PrivacySettingView)view.findViewById(2131296839));
        this._privacySettingsController.addSettingChangedListener(this);
        this._firstnameField.getEditText().requestFocus();
        object = new View.OnClickListener(){

            public void onClick(View view) {
                ProfileEditorFragment.this.getContentPresenter().showFragment(new StoreFragment(), true, true);
            }
        };
        this._mobilePremiumButton = (CustomTextView)view.findViewById(2131296508);
        this._mobilePremiumButton.setOnClickListener((View.OnClickListener)object);
        this._webPremiumButton = (CustomTextView)view.findViewById(2131296510);
        this._webPremiumButton.setOnClickListener((View.OnClickListener)object);
        this._mobilePremiumText = (CustomTextView)view.findViewById(2131296509);
        this._mobilePremiumDateText = (CustomTextView)view.findViewById(2131296507);
        this._analyticsController = new GoogleAnalyticsSettingController((ViewGroup)view.findViewById(2131296849), ServiceProvider.getInstance().getTrackingService());
        this.updateEditFields();
        this.setPrivacyValues();
        this.updatePremium();
        this._viewCreated = true;
    }

    @Override
    public boolean showMenu() {
        return false;
    }

    private class SetDataCallback
    extends LoadCommandCallbackWithTimeout<Map<String, String>> {
        private Map<String, String> _params;

        public SetDataCallback(Map<String, String> map) {
            this._params = map;
        }

        @Override
        protected void failed(final APIStatusCode aPIStatusCode, String string, final List<LoadFieldError> list, JSONObject jSONObject) {
            ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    if (list != null && list.size() > 0) {
                        RookieFormErrorDialogFragment rookieFormErrorDialogFragment = new RookieFormErrorDialogFragment();
                        rookieFormErrorDialogFragment.setErrors(list);
                        rookieFormErrorDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), "ProfileFormErrors");
                    } else {
                        ProfileEditorFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                ServiceProvider.getInstance().getProfileDataService().setUserData(SetDataCallback.this._params, new SetDataCallback(SetDataCallback.this._params));
                            }
                        }, false);
                    }
                    ProfileEditorFragment.this.hideDialogWaiting();
                }

            });
        }

        @Override
        protected void succeded(Map<String, String> map) {
            ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    ProfileSavedToastView profileSavedToastView = new ProfileSavedToastView(ProfileEditorFragment.this.getActivity().getApplicationContext());
                    Toast toast = new Toast(ProfileEditorFragment.this.getActivity().getApplicationContext());
                    toast.setGravity(17, 0, -100);
                    toast.setDuration(1);
                    toast.setView((View)profileSavedToastView);
                    toast.show();
                    ProfileEditorFragment.this.hideDialogWaiting();
                    ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
        }

    }

}
