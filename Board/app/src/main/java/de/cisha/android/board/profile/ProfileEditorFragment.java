// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.android.board.profile.view.ProfileSavedToastView;
import de.cisha.android.board.profile.view.RookieFormErrorDialogFragment;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.profile.view.PrivacySettingView;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import java.util.GregorianCalendar;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import de.cisha.android.ui.patterns.dialogs.CustomDatePickerDialog;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import android.widget.ImageView;
import java.util.LinkedList;
import de.cisha.android.board.service.SetUserDataParamsBuilder;
import android.view.View.OnClickListener;
import android.view.View;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Context;
import android.text.format.DateFormat;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import java.util.Date;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Map;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.profile.view.PrivacySettingChooserView;
import de.cisha.android.board.AbstractContentFragment;

public class ProfileEditorFragment extends AbstractContentFragment implements PrivacySettingChangedListener
{
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
    
    private String formatDate(final Date date) {
        String format = "";
        if (date != null) {
            format = DateFormat.getDateFormat((Context)this.getActivity()).format(date);
        }
        return format;
    }
    
    private void loadPrivacySettings() {
        ServiceProvider.getInstance().getWebSettingsService().getPrivacySettings(new LoadCommandCallbackWithTimeout<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            }
            
            @Override
            protected void succeded(final Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> map) {
                ProfileEditorFragment.this._privacyMap = map;
                ProfileEditorFragment.this.setPrivacyValues();
            }
        });
    }
    
    private void loadProfileData() {
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallbackWithTimeout<User>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        ProfileEditorFragment.this.loadProfileData();
                    }
                }, true);
            }
            
            @Override
            protected void succeded(final User user) {
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this._user = user;
                ProfileEditorFragment.this.updateEditFields();
                ProfileEditorFragment.this.updatePremium();
            }
        });
    }
    
    private void setEmail(final String s, final String s2) {
        final LoadCommandCallbackWithTimeout<Map<String, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<String, String>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        ProfileEditorFragment.this.getView().post((Runnable)new Runnable() {
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
            protected void succeded(final Map<String, String> map) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        if (ProfileEditorFragment.this._dialogEditEmail != null && ProfileEditorFragment.this._dialogEditEmail.isResumed()) {
                            ProfileEditorFragment.this._dialogEditEmail.dismiss();
                        }
                        ProfileEditorFragment.this.hideDialogWaiting();
                        ProfileEditorFragment.this._emailField.setText(s);
                    }
                });
            }
        };
        this.showDialogWaiting(true, false, "", null);
        ServiceProvider.getInstance().getProfileDataService().setEmail(s, s2, loadCommandCallbackWithTimeout);
    }
    
    private void setPassword(final String s, final String s2, final String s3) {
        final LoadCommandCallbackWithTimeout<Map<String, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<String, String>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        ProfileEditorFragment.this.getView().post((Runnable)new Runnable() {
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
            protected void succeded(final Map<String, String> map) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
        ServiceProvider.getInstance().getProfileDataService().setPassword(s, s2, s3, loadCommandCallbackWithTimeout);
    }
    
    private void setPrivacyValues() {
        if (this._viewCreated) {
            this.getView().post((Runnable)new Runnable() {
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
            this.getView().post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
                        ProfileEditorFragment.this._passwordField.setText("dummylongpassword");
                        final CustomEditText access.1700 = ProfileEditorFragment.this._emailField;
                        String email;
                        if (ProfileEditorFragment.this._user.getEmail() != null) {
                            email = ProfileEditorFragment.this._user.getEmail();
                        }
                        else {
                            email = "";
                        }
                        access.1700.setText(email);
                        final CustomEditText access.1701 = ProfileEditorFragment.this._surnameField;
                        String surname;
                        if (ProfileEditorFragment.this._user.getSurname() != null) {
                            surname = ProfileEditorFragment.this._user.getSurname();
                        }
                        else {
                            surname = "";
                        }
                        access.1701.setText(surname);
                        final CustomEditText access.1702 = ProfileEditorFragment.this._firstnameField;
                        String firstname;
                        if (ProfileEditorFragment.this._user.getFirstname() != null) {
                            firstname = ProfileEditorFragment.this._user.getFirstname();
                        }
                        else {
                            firstname = "";
                        }
                        access.1702.setText(firstname);
                        final CustomEditText access.1703 = ProfileEditorFragment.this._usernameField;
                        String nickname;
                        if (ProfileEditorFragment.this._user.getNickname() != null) {
                            nickname = ProfileEditorFragment.this._user.getNickname();
                        }
                        else {
                            nickname = "";
                        }
                        access.1703.setText(nickname);
                        ProfileEditorFragment.this._countryField.setCountry(ProfileEditorFragment.this._user.getCountry());
                        final Date birthday = ProfileEditorFragment.this._user.getBirthday();
                        if (birthday != null) {
                            ProfileEditorFragment.this._dateField.setDate(birthday);
                        }
                        User.Gender gender;
                        if (ProfileEditorFragment.this._user.getGender() != null) {
                            gender = ProfileEditorFragment.this._user.getGender();
                        }
                        else {
                            gender = User.Gender.MALE;
                        }
                        ProfileEditorFragment.this._selectedGender = gender;
                        ProfileEditorFragment.this._sexField.setText(ProfileEditorFragment.this.getString(gender.getNameResId()));
                    }
                }
            });
        }
    }
    
    private void updatePremium() {
        if (this.getView() != null) {
            this.getView().post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
                        final User access.1000 = ProfileEditorFragment.this._user;
                        final Date date = new Date();
                        final Subscription subscription = access.1000.getSubscription(SubscriptionType.PREMIUM);
                        boolean b = true;
                        final boolean b2 = subscription != null && subscription.getExpirationDate().after(date);
                        final Subscription subscription2 = access.1000.getSubscription(SubscriptionType.PREMIUM_MOBILE);
                        if (subscription2 == null || !subscription2.getExpirationDate().after(date)) {
                            b = false;
                        }
                        ProfileEditorFragment.this._mobilePremiumButton.setVisibility(8);
                        ProfileEditorFragment.this._webPremiumButton.setVisibility(8);
                        ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(8);
                        ProfileEditorFragment.this._mobilePremiumText.setVisibility(8);
                        if (b2) {
                            ProfileEditorFragment.this._webPremiumButton.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(subscription.getExpirationDate()));
                            ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                            return;
                        }
                        if (b) {
                            ProfileEditorFragment.this._mobilePremiumButton.setText(2131689958);
                            ProfileEditorFragment.this._mobilePremiumButton.setVisibility(0);
                            ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(subscription2.getExpirationDate()));
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
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690224);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        final ImageView navbarButtonForRessource = this.createNavbarButtonForRessource(context, 2131231653);
        navbarButtonForRessource.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (ProfileEditorFragment.this._user != null) {
                    final Map<String, String> params = new SetUserDataParamsBuilder().setFirstname(ProfileEditorFragment.this._firstnameField.getEditText().getText().toString()).setSurname(ProfileEditorFragment.this._surnameField.getEditText().getText().toString()).setCountry(ProfileEditorFragment.this._countryField.getCountry()).setBirthdate(ProfileEditorFragment.this._user.getBirthday()).setGender(ProfileEditorFragment.this._selectedGender).createParams();
                    ProfileEditorFragment.this.showDialogWaiting(false, false, "", null);
                    ServiceProvider.getInstance().getProfileDataService().setUserData(params, new SetDataCallback(params));
                    return;
                }
                ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
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
        return "EditProfile";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                ProfileEditorFragment.this.loadProfileData();
                ProfileEditorFragment.this.loadPrivacySettings();
            }
        });
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return layoutInflater.inflate(2131427515, viewGroup, false);
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
        final LoadCommandCallbackWithTimeout<Void> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Void>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        if (ProfileEditorFragment.this.isResumed()) {
                            Toast.makeText((Context)ProfileEditorFragment.this.getActivity(), (CharSequence)"set setting failed", 0).show();
                        }
                        ProfileEditorFragment.this.hideDialogWaiting();
                        ProfileEditorFragment.this._privacySettingsController.resetPrivacySetting(privacySetting);
                    }
                });
            }
            
            @Override
            protected void succeded(final Void void1) {
                ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        ProfileEditorFragment.this._privacySettingsController.updatePrivacySetting(privacySetting, privacyValue);
                        ProfileEditorFragment.this.hideDialogWaiting();
                    }
                });
            }
        };
        this.showDialogWaiting(false, false, "", null);
        ServiceProvider.getInstance().getWebSettingsService().setPrivacySetting(privacySetting, privacyValue, loadCommandCallbackWithTimeout);
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._passwordField = (CustomEditPassword)view.findViewById(2131296831);
        this._passwordField.getEditText().setEnabled(false);
        view.findViewById(2131296832).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ProfileEditorFragment.this._passwordField.getEditText().requestFocus();
                ProfileEditorFragment.this._dialogEditPassword = new EditPasswordDialogFragment();
                ProfileEditorFragment.this._dialogEditPassword.setOnPasswordChangeListener((EditPasswordDialogFragment.OnPasswordChangedListener)new EditPasswordDialogFragment.OnPasswordChangedListener() {
                    @Override
                    public void onPasswordChanged(final String s, final String s2, final String s3) {
                        ProfileEditorFragment.this.setPassword(s, s2, s3);
                    }
                });
                ProfileEditorFragment.this._dialogEditPassword.show(ProfileEditorFragment.this.getFragmentManager(), null);
            }
        });
        this._emailField = (CustomEditText)view.findViewById(2131296827);
        this._emailField.getEditText().setEnabled(false);
        new View.OnClickListener() {
            public void onClick(final View view) {
                ProfileEditorFragment.this._emailField.getEditText().requestFocus();
                ProfileEditorFragment.this._dialogEditEmail = new EditEmailDialogFragment();
                ProfileEditorFragment.this._dialogEditEmail.setOnEmailChangedListener((EditEmailDialogFragment.OnEmailChangedListener)new EditEmailDialogFragment.OnEmailChangedListener() {
                    @Override
                    public void onEmailChanged(final String s, final String s2) {
                        ProfileEditorFragment.this.setEmail(s, s2);
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
        view.findViewById(2131296824).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (ProfileEditorFragment.this._user != null) {
                    ProfileEditorFragment.this._dateField.getEditText().requestFocus();
                    final CustomDatePickerDialog customDatePickerDialog = new CustomDatePickerDialog();
                    customDatePickerDialog.setOnDateChangedListener((DatePicker.OnDateChangedListener)new DatePicker.OnDateChangedListener() {
                        public void onDateChanged(final DatePicker datePicker, final int n, final int n2, final int n3) {
                            final GregorianCalendar gregorianCalendar = new GregorianCalendar(n, n2, n3);
                            ProfileEditorFragment.this._user.setBirthday(gregorianCalendar.getTime());
                            ProfileEditorFragment.this._dateField.setDate(gregorianCalendar.getTime());
                        }
                    });
                    customDatePickerDialog.setTime(ProfileEditorFragment.this._user.getBirthday());
                    customDatePickerDialog.show(ProfileEditorFragment.this.getFragmentManager(), null);
                }
            }
        });
        this._sexField = (CustomEditText)view.findViewById(2131296840);
        this._sexField.getEditText().setInputType(0);
        view.findViewById(2131296841).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ProfileEditorFragment.this._sexField.getEditText().requestFocus();
                final OptionDialogFragment<OptionDialogFragment.Option> optionDialogFragment = new OptionDialogFragment<OptionDialogFragment.Option>();
                optionDialogFragment.setTitle(ProfileEditorFragment.this.getString(2131690200));
                final LinkedList<GenderOption> list = new LinkedList<GenderOption>();
                GenderOption genderOption = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.MALE.getNameResId()), User.Gender.MALE);
                list.add(genderOption);
                final GenderOption genderOption2 = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.FEMALE.getNameResId()), User.Gender.FEMALE);
                list.add(genderOption2);
                if (ProfileEditorFragment.this._selectedGender != User.Gender.MALE) {
                    genderOption = genderOption2;
                }
                optionDialogFragment.setOptions((List<OptionDialogFragment.Option>)list, (OptionDialogFragment.Option)genderOption);
                optionDialogFragment.setOptionSelectionListener((OptionDialogFragment.OptionSelectionListener<OptionDialogFragment.Option>)new OptionDialogFragment.OptionSelectionListener<GenderOption>() {
                    public void onOptionSelected(final GenderOption genderOption) {
                        ProfileEditorFragment.this._selectedGender = genderOption.getSex();
                        ProfileEditorFragment.this._sexField.setText(((OptionDialogFragment.Option)genderOption).getName());
                    }
                });
                optionDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), null);
            }
        });
        (this._privacySettingsController = new PrivacySettingsController((PrivacySettingView)view.findViewById(2131296839))).addSettingChangedListener(this);
        this._firstnameField.getEditText().requestFocus();
        final View.OnClickListener view.OnClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ProfileEditorFragment.this.getContentPresenter().showFragment(new StoreFragment(), true, true);
            }
        };
        (this._mobilePremiumButton = (CustomTextView)view.findViewById(2131296508)).setOnClickListener((View.OnClickListener)view.OnClickListener);
        (this._webPremiumButton = (CustomTextView)view.findViewById(2131296510)).setOnClickListener((View.OnClickListener)view.OnClickListener);
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
    
    private class SetDataCallback extends LoadCommandCallbackWithTimeout<Map<String, String>>
    {
        private Map<String, String> _params;
        
        public SetDataCallback(final Map<String, String> params) {
            this._params = params;
        }
        
        @Override
        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                @Override
                public void run() {
                    if (list != null && list.size() > 0) {
                        final RookieFormErrorDialogFragment rookieFormErrorDialogFragment = new RookieFormErrorDialogFragment();
                        rookieFormErrorDialogFragment.setErrors(list);
                        rookieFormErrorDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), "ProfileFormErrors");
                    }
                    else {
                        ProfileEditorFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
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
        protected void succeded(final Map<String, String> map) {
            ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                @Override
                public void run() {
                    final ProfileSavedToastView view = new ProfileSavedToastView(ProfileEditorFragment.this.getActivity().getApplicationContext());
                    final Toast toast = new Toast(ProfileEditorFragment.this.getActivity().getApplicationContext());
                    toast.setGravity(17, 0, -100);
                    toast.setDuration(1);
                    toast.setView((View)view);
                    toast.show();
                    ProfileEditorFragment.this.hideDialogWaiting();
                    ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
        }
    }
}
