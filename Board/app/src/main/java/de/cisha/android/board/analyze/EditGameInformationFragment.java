// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.view.BoardView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.GregorianCalendar;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import de.cisha.android.ui.patterns.dialogs.CustomDatePickerDialog;
import de.cisha.android.board.view.BoardViewFactory;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import java.util.LinkedList;
import android.view.View.OnClickListener;
import android.view.View;
import java.util.List;
import android.content.Context;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import android.widget.EditText;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Opponent;
import android.view.inputmethod.InputMethodManager;
import de.cisha.android.ui.patterns.input.CustomSpinner;
import de.cisha.chess.model.Game;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import android.widget.ImageView;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.AbstractContentFragment;

public class EditGameInformationFragment extends AbstractContentFragment
{
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
    
    private void readElo(final CustomEditText customEditText, final Opponent opponent) {
        final String string = customEditText.getEditText().getText().toString();
        opponent.setRating(Rating.NO_RATING);
        if (string == null || "".equals(string.trim())) {
            return;
        }
        try {
            opponent.setRating(new Rating(Integer.parseInt(string)));
        }
        catch (NumberFormatException ex) {}
    }
    
    private void setValuesFromGame() {
        if (this._game != null) {
            this._titleText.getEditText().setText((CharSequence)this._game.getTitle());
            this._eventText.getEditText().setText((CharSequence)this._game.getEvent());
            this._siteText.getEditText().setText((CharSequence)this._game.getSite());
            this._date.setDate(this._game.getStartDate());
            final Opponent whitePlayer = this._game.getWhitePlayer();
            if (whitePlayer != null) {
                this._whiteName.getEditText().setText((CharSequence)whitePlayer.getName());
                final EditText editText = this._whiteElo.getEditText();
                String ratingString;
                if (whitePlayer.hasRating()) {
                    ratingString = whitePlayer.getRating().getRatingString();
                }
                else {
                    ratingString = "";
                }
                editText.setText((CharSequence)ratingString);
                this._whiteCountry.setCountry(whitePlayer.getCountry());
            }
            final Opponent blackPlayer = this._game.getBlackPlayer();
            if (blackPlayer != null) {
                this._blackName.getEditText().setText((CharSequence)blackPlayer.getName());
                final EditText editText2 = this._blackElo.getEditText();
                String ratingString2;
                if (blackPlayer.hasRating()) {
                    ratingString2 = blackPlayer.getRating().getRatingString();
                }
                else {
                    ratingString2 = "";
                }
                editText2.setText((CharSequence)ratingString2);
                this._blackCountry.setCountry(blackPlayer.getCountry());
            }
            if (this._game.hasChildren()) {
                this._fieldView.getBoardView().setPosition(this._game.getLastMoveinMainLine().getResultingPosition());
            }
            else {
                this._fieldView.getBoardView().setPosition(this._game.getStartingPosition());
            }
            final GameResult[] values = GameResult.values();
            GameResult gameResult;
            if (this._game.getResult() != null) {
                gameResult = this._game.getResult().getResult();
            }
            else {
                gameResult = GameResult.NO_RESULT;
            }
            int i = 0;
            int selection = 0;
            while (i < values.length) {
                if (values[i] == gameResult) {
                    selection = i;
                }
                ++i;
            }
            this._resultSpinner.getSpinner().setSelection(selection);
        }
    }
    
    private void setValuesToGame() {
        if (this._game != null) {
            this._game.setTitle(this._titleText.getEditText().getText().toString());
            this._game.setEvent(this._eventText.getEditText().getText().toString());
            this._game.setSite(this._siteText.getEditText().getText().toString());
            Opponent whitePlayer;
            if ((whitePlayer = this._game.getWhitePlayer()) == null) {
                whitePlayer = new Opponent();
                this._game.setWhitePlayer(whitePlayer);
            }
            whitePlayer.setName(this._whiteName.getEditText().getText().toString());
            whitePlayer.setCountry(this._whiteCountry.getCountry());
            this.readElo(this._whiteElo, whitePlayer);
            Opponent blackPlayer;
            if ((blackPlayer = this._game.getBlackPlayer()) == null) {
                blackPlayer = new Opponent();
                this._game.setBlackPlayer(blackPlayer);
            }
            blackPlayer.setName(this._blackName.getEditText().getText().toString());
            blackPlayer.setCountry(this._blackCountry.getCountry());
            this.readElo(this._blackElo, blackPlayer);
            this._game.setStartDate(this._date.getDate());
            final GameResult gameResult = (GameResult)this._resultSpinner.getSpinner().getSelectedItem();
            final Game game = this._game;
            GameEndReason gameEndReason;
            if (this._game.getResult() != null) {
                gameEndReason = this._game.getResult().getReason();
            }
            else {
                gameEndReason = GameEndReason.NO_REASON;
            }
            game.setResult(new GameStatus(gameResult, gameEndReason));
        }
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131689553);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    public List<View> getLeftButtons(final Context context) {
        (this._cancelButton = this.createNavbarButtonForRessource(context, 2131231037)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EditGameInformationFragment.this.cancelClicked();
            }
        });
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        list.add((View)this._cancelButton);
        return (List<View>)list;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        (this._saveButton = this.createNavbarButtonForRessource(context, 2131231653)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EditGameInformationFragment.this.saveGame();
            }
        });
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        list.add((View)this._saveButton);
        return (List<View>)list;
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
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427434, viewGroup, false);
        this._fieldView = (FieldView)inflate.findViewById(2131296500);
        final BoardView boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, true, true);
        boardView.setWhiteMoveable(false);
        boardView.setWhitePreMoveable(false);
        boardView.setBlackMoveable(false);
        boardView.setBlackPreMoveable(false);
        this._fieldView.setBoardView(boardView);
        if (this._game != null) {
            if (this._game.hasChildren()) {
                boardView.setPosition(this._game.getLastMoveinMainLine().getResultingPosition());
            }
            else {
                boardView.setPosition(this._game.getStartingPosition());
            }
        }
        this._titleText = (CustomEditText)inflate.findViewById(2131296503);
        this._eventText = (CustomEditText)inflate.findViewById(2131296499);
        this._siteText = (CustomEditText)inflate.findViewById(2131296502);
        this._whiteName = (CustomEditText)inflate.findViewById(2131296504);
        this._whiteElo = (CustomEditText)inflate.findViewById(2131296506);
        this._whiteElo.getEditText().setInputType(2);
        this._whiteCountry = (CustomCountryChoser)inflate.findViewById(2131296505);
        this._blackName = (CustomEditText)inflate.findViewById(2131296495);
        this._blackElo = (CustomEditText)inflate.findViewById(2131296497);
        this._blackElo.getEditText().setInputType(2);
        this._blackCountry = (CustomCountryChoser)inflate.findViewById(2131296496);
        (this._date = (CustomEditDate)inflate.findViewById(2131296498)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EditGameInformationFragment.this._date.getEditText().requestFocus();
                final CustomDatePickerDialog customDatePickerDialog = new CustomDatePickerDialog();
                customDatePickerDialog.setOnDateChangedListener((DatePicker.OnDateChangedListener)new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(final DatePicker datePicker, final int n, final int n2, final int n3) {
                        EditGameInformationFragment.this._date.setDate(new GregorianCalendar(n, n2, n3).getTime());
                    }
                });
                customDatePickerDialog.setTime(EditGameInformationFragment.this._date.getDate());
                customDatePickerDialog.show(EditGameInformationFragment.this.getFragmentManager(), null);
            }
        });
        this._resultSpinner = (CustomSpinner)inflate.findViewById(2131296501);
        this._resultSpinner.getSpinner().setAdapter((SpinnerAdapter)new ArrayAdapter<GameResult>(this.getActivity(), 2131427549, GameResult.values()) {
            public View getDropDownView(final int n, final View view, final ViewGroup viewGroup) {
                final TextView textView = (TextView)super.getDropDownView(n, view, viewGroup);
                textView.setText((CharSequence)((GameResult)this.getItem(n)).getShortDescription());
                return (View)textView;
            }
            
            public View getView(final int n, final View view, final ViewGroup viewGroup) {
                final TextView textView = (TextView)super.getView(n, view, viewGroup);
                textView.setText((CharSequence)((GameResult)this.getItem(n)).getShortDescription());
                return (View)textView;
            }
        });
        this.setValuesFromGame();
        return inflate;
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
    
    public void setGame(final Game game) {
        this._game = game;
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
}
