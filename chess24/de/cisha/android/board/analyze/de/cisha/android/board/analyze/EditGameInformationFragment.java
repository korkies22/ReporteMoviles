/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.ArrayAdapter
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.dialogs.CustomDatePickerDialog;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.input.CustomSpinner;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.position.Position;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EditGameInformationFragment
extends AbstractContentFragment {
    private CustomCountryChoser _blackCountry;
    private CustomEditText _blackElo;
    private CustomEditText _blackName;
    private ImageView _cancelButton;
    private CustomEditDate _date;
    private CustomEditText _eventText;
    private FieldView _fieldView;
    private Game _game;
    private CustomSpinner _resultSpinner;
    private ImageView _saveButton;
    private CustomEditText _siteText;
    private CustomEditText _titleText;
    private CustomCountryChoser _whiteCountry;
    private CustomEditText _whiteElo;
    private CustomEditText _whiteName;

    private void cancelClicked() {
        this.closeSoftKeyBoard();
        this.getContentPresenter().popCurrentFragment();
    }

    private void closeSoftKeyBoard() {
        ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void readElo(CustomEditText object, Opponent opponent) {
        object = object.getEditText().getText().toString();
        opponent.setRating(Rating.NO_RATING);
        if (object == null || "".equals(object.trim())) return;
        try {
            opponent.setRating(new Rating(Integer.parseInt((String)object)));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    private void setValuesFromGame() {
        if (this._game != null) {
            EditText editText;
            Object object;
            this._titleText.getEditText().setText((CharSequence)this._game.getTitle());
            this._eventText.getEditText().setText((CharSequence)this._game.getEvent());
            this._siteText.getEditText().setText((CharSequence)this._game.getSite());
            this._date.setDate(this._game.getStartDate());
            GameResult[] arrgameResult = this._game.getWhitePlayer();
            if (arrgameResult != null) {
                this._whiteName.getEditText().setText((CharSequence)arrgameResult.getName());
                editText = this._whiteElo.getEditText();
                object = arrgameResult.hasRating() ? arrgameResult.getRating().getRatingString() : "";
                editText.setText((CharSequence)object);
                this._whiteCountry.setCountry(arrgameResult.getCountry());
            }
            if ((arrgameResult = this._game.getBlackPlayer()) != null) {
                this._blackName.getEditText().setText((CharSequence)arrgameResult.getName());
                editText = this._blackElo.getEditText();
                object = arrgameResult.hasRating() ? arrgameResult.getRating().getRatingString() : "";
                editText.setText((CharSequence)object);
                this._blackCountry.setCountry(arrgameResult.getCountry());
            }
            if (this._game.hasChildren()) {
                this._fieldView.getBoardView().setPosition(this._game.getLastMoveinMainLine().getResultingPosition());
            } else {
                this._fieldView.getBoardView().setPosition(this._game.getStartingPosition());
            }
            arrgameResult = GameResult.values();
            object = this._game.getResult() != null ? this._game.getResult().getResult() : GameResult.NO_RESULT;
            int n = 0;
            for (int i = 0; i < arrgameResult.length; ++i) {
                if (arrgameResult[i] != object) continue;
                n = i;
            }
            this._resultSpinner.getSpinner().setSelection(n);
        }
    }

    private void setValuesToGame() {
        if (this._game != null) {
            Object object;
            this._game.setTitle(this._titleText.getEditText().getText().toString());
            this._game.setEvent(this._eventText.getEditText().getText().toString());
            this._game.setSite(this._siteText.getEditText().getText().toString());
            Object object2 = object = this._game.getWhitePlayer();
            if (object == null) {
                object2 = new Opponent();
                this._game.setWhitePlayer((Opponent)object2);
            }
            object2.setName(this._whiteName.getEditText().getText().toString());
            object2.setCountry(this._whiteCountry.getCountry());
            this.readElo(this._whiteElo, (Opponent)object2);
            object2 = object = this._game.getBlackPlayer();
            if (object == null) {
                object2 = new Opponent();
                this._game.setBlackPlayer((Opponent)object2);
            }
            object2.setName(this._blackName.getEditText().getText().toString());
            object2.setCountry(this._blackCountry.getCountry());
            this.readElo(this._blackElo, (Opponent)object2);
            this._game.setStartDate(this._date.getDate());
            object = (GameResult)((Object)this._resultSpinner.getSpinner().getSelectedItem());
            Game game = this._game;
            object2 = this._game.getResult() != null ? this._game.getResult().getReason() : GameEndReason.NO_REASON;
            game.setResult(new GameStatus((GameResult)((Object)object), (GameEndReason)((Object)object2)));
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131689553);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }

    @Override
    public List<View> getLeftButtons(Context object) {
        this._cancelButton = this.createNavbarButtonForRessource((Context)object, 2131231037);
        this._cancelButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditGameInformationFragment.this.cancelClicked();
            }
        });
        object = new LinkedList();
        object.add(this._cancelButton);
        return object;
    }

    @Override
    public List<View> getRightButtons(Context object) {
        this._saveButton = this.createNavbarButtonForRessource((Context)object, 2131231653);
        this._saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditGameInformationFragment.this.saveGame();
            }
        });
        object = new LinkedList();
        object.add(this._saveButton);
        return object;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }

    @Override
    public String getTrackingName() {
        return "EditGameInformation";
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427434, (ViewGroup)object, false);
        this._fieldView = (FieldView)layoutInflater.findViewById(2131296500);
        object = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, true, true);
        object.setWhiteMoveable(false);
        object.setWhitePreMoveable(false);
        object.setBlackMoveable(false);
        object.setBlackPreMoveable(false);
        this._fieldView.setBoardView((BoardView)object);
        if (this._game != null) {
            if (this._game.hasChildren()) {
                object.setPosition(this._game.getLastMoveinMainLine().getResultingPosition());
            } else {
                object.setPosition(this._game.getStartingPosition());
            }
        }
        this._titleText = (CustomEditText)layoutInflater.findViewById(2131296503);
        this._eventText = (CustomEditText)layoutInflater.findViewById(2131296499);
        this._siteText = (CustomEditText)layoutInflater.findViewById(2131296502);
        this._whiteName = (CustomEditText)layoutInflater.findViewById(2131296504);
        this._whiteElo = (CustomEditText)layoutInflater.findViewById(2131296506);
        this._whiteElo.getEditText().setInputType(2);
        this._whiteCountry = (CustomCountryChoser)layoutInflater.findViewById(2131296505);
        this._blackName = (CustomEditText)layoutInflater.findViewById(2131296495);
        this._blackElo = (CustomEditText)layoutInflater.findViewById(2131296497);
        this._blackElo.getEditText().setInputType(2);
        this._blackCountry = (CustomCountryChoser)layoutInflater.findViewById(2131296496);
        this._date = (CustomEditDate)layoutInflater.findViewById(2131296498);
        object = new View.OnClickListener(){

            public void onClick(View object) {
                EditGameInformationFragment.this._date.getEditText().requestFocus();
                object = new CustomDatePickerDialog();
                object.setOnDateChangedListener(new DatePicker.OnDateChangedListener(){

                    public void onDateChanged(DatePicker object, int n, int n2, int n3) {
                        object = new GregorianCalendar(n, n2, n3);
                        EditGameInformationFragment.this._date.setDate(object.getTime());
                    }
                });
                object.setTime(EditGameInformationFragment.this._date.getDate());
                object.show(EditGameInformationFragment.this.getFragmentManager(), null);
            }

        };
        this._date.setOnClickListener((View.OnClickListener)object);
        this._resultSpinner = (CustomSpinner)layoutInflater.findViewById(2131296501);
        this._resultSpinner.getSpinner().setAdapter((SpinnerAdapter)new ArrayAdapter<GameResult>((Context)this.getActivity(), 2131427549, GameResult.values()){

            public View getDropDownView(int n, View view, ViewGroup viewGroup) {
                view = (TextView)super.getDropDownView(n, view, viewGroup);
                view.setText((CharSequence)((GameResult)((Object)this.getItem(n))).getShortDescription());
                return view;
            }

            public View getView(int n, View view, ViewGroup viewGroup) {
                view = (TextView)super.getView(n, view, viewGroup);
                view.setText((CharSequence)((GameResult)((Object)this.getItem(n))).getShortDescription());
                return view;
            }
        });
        this.setValuesFromGame();
        return layoutInflater;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void saveGame() {
        this.setValuesToGame();
        this.closeSoftKeyBoard();
        ServiceProvider.getInstance().getGameService().saveAnalysis(this._game);
        this.getContentPresenter().popCurrentFragment();
    }

    public void setGame(Game game) {
        this._game = game;
    }

    @Override
    public boolean showMenu() {
        return false;
    }

}
