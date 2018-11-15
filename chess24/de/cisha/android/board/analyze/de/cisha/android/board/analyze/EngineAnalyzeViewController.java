/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.board.analyze.view.EngineVariantView;
import de.cisha.android.board.engine.EngineController;
import de.cisha.android.board.engine.EngineObserver;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.IEngineService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EngineAnalyzeViewController
extends AbstractNavigationBarItem
implements EngineObserver,
PositionObserver {
    private static final String ENGINE_GO_PREMIUM_FRAGMENT_TAG = "Engine Go Premium Fragment";
    private static final int MAX_VARIATIONS = 4;
    private IContentPresenter _contentPresenter;
    private Context _context;
    private List<EvaluationInfo> _currentEvaluations;
    private TextView _depth;
    private EngineController _engineController;
    private List<EngineObserver> _engineObservers;
    private boolean _engineRunning = false;
    private TextView _eval;
    private LayoutInflater _inflater;
    private CustomTextView _infoText;
    private View _lessButton;
    private MyListAdapter _listAdapter;
    private ListView _listView;
    private View _mainView;
    private boolean _mate = false;
    private boolean _matingColor = true;
    private int _maxDepthAllowedForThisUser = 12;
    private int _maxVariationsAllowedForThisUser = 1;
    private MenuBarItem _menuItem;
    private View _moreButton;
    private MoveExecutor _moveExecutor;
    private boolean _restartEngine = false;
    private boolean _staleMate = false;
    private TextView _startStopButton;

    public EngineAnalyzeViewController(Context context, IContentPresenter iContentPresenter, MoveExecutor moveExecutor, List<EvaluationInfo> list) {
        this._context = context;
        this._contentPresenter = iContentPresenter;
        this._moveExecutor = moveExecutor;
        this._engineObservers = new LinkedList<EngineObserver>();
        this._inflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this._mainView = this._inflater.inflate(2131427373, null);
        this._currentEvaluations = list;
        this._listAdapter = new MyListAdapter();
        this._listView = (ListView)this._mainView.findViewById(2131296521);
        this._listView.setAdapter((ListAdapter)this._listAdapter);
        this._infoText = (CustomTextView)this._mainView.findViewById(2131296304);
        this._moreButton = this._mainView.findViewById(2131296319);
        this._lessButton = this._mainView.findViewById(2131296318);
        this._startStopButton = (TextView)this._mainView.findViewById(2131296320);
        this._startStopButton.setText((CharSequence)this._context.getString(2131689546));
        this._eval = (TextView)this._mainView.findViewById(2131296317);
        this._depth = (TextView)this._mainView.findViewById(2131296316);
        this._moreButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n = EngineAnalyzeViewController.this._engineController.getVariationCount();
                if (n < 4) {
                    if (n < EngineAnalyzeViewController.this._maxVariationsAllowedForThisUser) {
                        EngineAnalyzeViewController.this._engineController.addEngineVariation();
                        if (EngineAnalyzeViewController.this._engineRunning) {
                            EngineAnalyzeViewController.this._engineController.startCalculation();
                        }
                        EngineAnalyzeViewController.this.updateMoreAndLessVariationsButtons();
                        return;
                    }
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.ANALYSIS, "premium screen shown", "engine");
                    EngineAnalyzeViewController.this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_ENGINE_VARIATION);
                }
            }
        });
        this._lessButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (EngineAnalyzeViewController.this._engineController.getVariationCount() > 1) {
                    EngineAnalyzeViewController.this._engineController.removeEngineVariation();
                    if (EngineAnalyzeViewController.this._engineRunning) {
                        EngineAnalyzeViewController.this._engineController.startCalculation();
                    }
                    EngineAnalyzeViewController.this.updateMoreAndLessVariationsButtons();
                }
            }
        });
        this._startStopButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EngineAnalyzeViewController.this.startStopClicked();
            }
        });
        this.setupRights();
    }

    private void activateButtons() {
        this._startStopButton.setEnabled(true);
        this._moreButton.setEnabled(true);
        this._lessButton.setEnabled(true);
        this.updateMoreAndLessVariationsButtons();
    }

    private void checkForEngineDepthPermissions() {
        if (this._engineController.getDepth() >= this._maxDepthAllowedForThisUser) {
            this.stopEngine();
            this._restartEngine = true;
        }
    }

    private void deactivateButtons() {
        this._startStopButton.setEnabled(false);
        this._moreButton.setEnabled(false);
        this._lessButton.setEnabled(false);
    }

    private boolean hasSpecialPosition() {
        if (!this._mate && !this._staleMate) {
            return false;
        }
        return true;
    }

    private void resetPosition() {
        boolean bl = this.hasSpecialPosition();
        this._mate = false;
        this._staleMate = false;
        if (bl != this.hasSpecialPosition()) {
            this.updateMateInfo();
        }
    }

    private void runOnUIThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private void setEval(float f) {
        float f2;
        if (f > 6.0f) {
            f2 = 6.0f;
        } else {
            f2 = f;
            if (f < -6.0f) {
                f2 = -6.0f;
            }
        }
        int n = (int)((f2 + 6.0f) / 12.0f * 10000.0f);
        this._eval.getBackground().setLevel(10000 - n);
    }

    private void setPositionIsMateForColor(boolean bl) {
        this._mate = true;
        this._staleMate = false;
        this._matingColor = bl;
        this.updateMateInfo();
    }

    private void setPositionIsStaleMate() {
        this._mate = false;
        this._staleMate = true;
        this.updateMateInfo();
    }

    private void setupRights() {
        this._maxDepthAllowedForThisUser = ServiceProvider.getInstance().getMembershipService().getMaximumEngineEvaluationDepth();
        this._maxVariationsAllowedForThisUser = ServiceProvider.getInstance().getMembershipService().getMaximumEngineVariations();
    }

    private void startStopClicked() {
        if (this._restartEngine) {
            this._restartEngine = false;
            this._startStopButton.setText((CharSequence)this._context.getString(2131689546));
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.ANALYSIS, "premium screen shown", "engine");
            this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_ENGINE);
            return;
        }
        if (this._engineRunning) {
            this._engineController.stopEngine();
            return;
        }
        this._engineController.startEngine();
    }

    private void updateMateInfo() {
        if (this.hasSpecialPosition()) {
            this._listView.setVisibility(8);
            this._infoText.setVisibility(0);
            String string = "";
            if (this._mate) {
                if (this._matingColor) {
                    this._infoText.setText((CharSequence)"mate 1-0");
                    this.setEval(999.0f);
                } else {
                    this._infoText.setText((CharSequence)"mate 0-1");
                    this.setEval(-999.0f);
                }
                string = "#";
            } else if (this._staleMate) {
                this._infoText.setText((CharSequence)"stalemate 1/2");
                string = "=";
                this.setEval(0.0f);
            }
            this._eval.setText((CharSequence)string);
            return;
        }
        this._listView.setVisibility(0);
        this._infoText.setVisibility(8);
    }

    private void updateMoreAndLessVariationsButtons() {
        int n = this._engineController.getVariationCount();
        View view = this._moreButton;
        boolean bl = false;
        boolean bl2 = n < 4;
        view.setEnabled(bl2);
        view = this._lessButton;
        bl2 = bl;
        if (n > 1) {
            bl2 = true;
        }
        view.setEnabled(bl2);
    }

    public void addEngineObserver(EngineObserver engineObserver) {
        if (this._engineController != null) {
            this._engineController.addEngineObserver(engineObserver);
            return;
        }
        this._engineObservers.add(engineObserver);
    }

    public void destroy() {
        if (this._engineController != null) {
            this._engineController.destroyEngine();
        }
    }

    @Override
    public View getContentView(Context context) {
        return this.getView();
    }

    public List<EvaluationInfo> getCurrentEvaluations() {
        return this._currentEvaluations;
    }

    @Override
    public String getEventTrackingName() {
        return "Show Engine Evaluation";
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuItem == null) {
            this._menuItem = new MenuBarItem(context, context.getResources().getString(2131689524), 2131230825, 2131230826, -1);
            this._menuItem.setSelectionGroup(1);
        }
        return this._menuItem;
    }

    public View getView() {
        return this._mainView;
    }

    @Override
    public void handleClick() {
        this.startEngine();
    }

    @Override
    public void handleDeselection() {
        this.stopEngine();
    }

    @Override
    public void onEngineStarted() {
        this._engineRunning = true;
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689547));
                if (EngineAnalyzeViewController.this._menuItem != null) {
                    EngineAnalyzeViewController.this._menuItem.setGlowing(true);
                }
            }
        });
    }

    @Override
    public void onEngineStopped(Move move) {
        this._engineRunning = false;
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                if (EngineAnalyzeViewController.this._restartEngine) {
                    EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689545));
                } else {
                    EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689546));
                }
                if (EngineAnalyzeViewController.this._menuItem != null) {
                    EngineAnalyzeViewController.this._menuItem.setGlowing(false);
                }
            }
        });
    }

    @Override
    public void onVariationsChanged(final List<EvaluationInfo> list) {
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                EngineAnalyzeViewController.this._currentEvaluations.clear();
                EngineAnalyzeViewController.this._currentEvaluations.addAll(list);
                EngineAnalyzeViewController.this._listAdapter.notifyDataSetChanged();
                float f = EngineAnalyzeViewController.this._engineController.getEval();
                EngineAnalyzeViewController.this._eval.setText((CharSequence)EngineAnalyzeViewController.this._engineController.getEvalString());
                EngineAnalyzeViewController.this.setEval(f);
                TextView textView = EngineAnalyzeViewController.this._depth;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(EngineAnalyzeViewController.this._engineController.getDepth());
                textView.setText((CharSequence)stringBuilder.toString());
                if (EngineAnalyzeViewController.this.hasSpecialPosition()) {
                    EngineAnalyzeViewController.this.updateMateInfo();
                }
                EngineAnalyzeViewController.this.checkForEngineDepthPermissions();
            }
        });
    }

    @Override
    public void positionChanged(Position position, Move move) {
        if (this._engineController != null) {
            this._engineController.clear();
            this._depth.setText((CharSequence)"0");
            this._eval.setText((CharSequence)"\u2026");
            this._currentEvaluations.clear();
            this._currentEvaluations.addAll(this._engineController.getCurrentEvaluations());
            this.resetPosition();
            if (position != null && position.isCheckMate()) {
                if (position.getActiveColor()) {
                    this._eval.setText((CharSequence)"-#");
                } else {
                    this._eval.setText((CharSequence)"#");
                }
                this.setPositionIsMateForColor(position.getActiveColor() ^ true);
            }
            if (position != null && position.isStaleMate()) {
                this.setPositionIsStaleMate();
            }
            this._listAdapter.notifyDataSetChanged();
            if (!this._engineRunning && this._restartEngine) {
                this._engineController.startEngine();
            }
            this._engineController.positionChanged(position);
            if (this._engineRunning) {
                this._engineController.startCalculation();
            }
        }
    }

    public void setupEngine(final PositionObservable positionObservable) {
        this.deactivateButtons();
        this._listView.setVisibility(8);
        this._infoText.setVisibility(0);
        this._infoText.setText(2131690026);
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                int n = EngineAnalyzeViewController.this._currentEvaluations.size();
                UCIEngine uCIEngine = ServiceProvider.getInstance().getEngineService().getDefaultSingleEngine();
                EngineAnalyzeViewController.this._engineController = new EngineController(uCIEngine);
                Object object2 = EngineAnalyzeViewController.this._engineController;
                int n2 = n;
                if (n == 0) {
                    n2 = 1;
                }
                object2.setVariationsNumber(n2);
                uCIEngine.setPosition(positionObservable.getFEN());
                EngineAnalyzeViewController.this._engineController.addEngineObserver(EngineAnalyzeViewController.this);
                EngineAnalyzeViewController.this.activateButtons();
                EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689547));
                EngineAnalyzeViewController.this._infoText.setVisibility(8);
                EngineAnalyzeViewController.this._infoText.setText((CharSequence)"");
                EngineAnalyzeViewController.this._currentEvaluations.clear();
                EngineAnalyzeViewController.this._currentEvaluations.addAll(EngineAnalyzeViewController.this._engineController.getCurrentEvaluations());
                EngineAnalyzeViewController.this._listView.setVisibility(0);
                EngineAnalyzeViewController.this._listAdapter.notifyDataSetChanged();
                EngineAnalyzeViewController.this.positionChanged(positionObservable.getPosition(), null);
                for (Object object2 : EngineAnalyzeViewController.this._engineObservers) {
                    EngineAnalyzeViewController.this._engineController.addEngineObserver((EngineObserver)object2);
                }
                if (EngineAnalyzeViewController.this._engineRunning) {
                    EngineAnalyzeViewController.this._startStopButton.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            EngineAnalyzeViewController.this._engineController.startEngine();
                        }
                    }, 100L);
                }
                EngineAnalyzeViewController.this._engineObservers.clear();
            }

        });
    }

    public void startEngine() {
        this._engineRunning = true;
        if (this._engineController != null) {
            this._engineController.startEngine();
        }
    }

    public void stopEngine() {
        this._engineRunning = false;
        if (this._engineController != null) {
            this._engineController.stopEngine();
        }
    }

    private class MyListAdapter
    extends BaseAdapter {
        public int getCount() {
            return EngineAnalyzeViewController.this._currentEvaluations.size();
        }

        public Object getItem(int n) {
            return EngineAnalyzeViewController.this._currentEvaluations.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View object, ViewGroup viewGroup) {
            viewGroup = object;
            if (object == null) {
                viewGroup = EngineAnalyzeViewController.this._inflater.inflate(2131427437, null);
            }
            object = (EvaluationInfo)EngineAnalyzeViewController.this._currentEvaluations.get(n);
            ((EngineVariantView)viewGroup).setEvalInfo((EvaluationInfo)object);
            viewGroup.findViewById(2131296518).setOnClickListener(new View.OnClickListener((EvaluationInfo)object){
                final /* synthetic */ EvaluationInfo val$info;
                {
                    this.val$info = evaluationInfo;
                }

                public void onClick(View object) {
                    object = this.val$info.getMove();
                    if (object != null) {
                        EngineAnalyzeViewController.this._moveExecutor.doMoveInCurrentPosition(object.getSEP());
                    }
                }
            });
            return viewGroup;
        }

    }

}
