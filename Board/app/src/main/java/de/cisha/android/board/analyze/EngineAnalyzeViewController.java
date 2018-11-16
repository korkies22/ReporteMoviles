// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import de.cisha.android.board.analyze.view.EngineVariantView;
import android.widget.BaseAdapter;
import java.util.Iterator;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.Position;
import java.util.Collection;
import de.cisha.chess.model.Move;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.view.ViewGroup;
import java.util.LinkedList;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import android.widget.ListView;
import android.view.View;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.view.LayoutInflater;
import de.cisha.android.board.engine.EngineController;
import android.widget.TextView;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.List;
import android.content.Context;
import de.cisha.android.board.IContentPresenter;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.board.engine.EngineObserver;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;

public class EngineAnalyzeViewController extends AbstractNavigationBarItem implements EngineObserver, PositionObserver
{
    private static final String ENGINE_GO_PREMIUM_FRAGMENT_TAG = "Engine Go Premium Fragment";
    private static final int MAX_VARIATIONS = 4;
    private IContentPresenter _contentPresenter;
    private Context _context;
    private List<EvaluationInfo> _currentEvaluations;
    private TextView _depth;
    private EngineController _engineController;
    private List<EngineObserver> _engineObservers;
    private boolean _engineRunning;
    private TextView _eval;
    private LayoutInflater _inflater;
    private CustomTextView _infoText;
    private View _lessButton;
    private MyListAdapter _listAdapter;
    private ListView _listView;
    private View _mainView;
    private boolean _mate;
    private boolean _matingColor;
    private int _maxDepthAllowedForThisUser;
    private int _maxVariationsAllowedForThisUser;
    private MenuBarItem _menuItem;
    private View _moreButton;
    private MoveExecutor _moveExecutor;
    private boolean _restartEngine;
    private boolean _staleMate;
    private TextView _startStopButton;
    
    public EngineAnalyzeViewController(final Context context, final IContentPresenter contentPresenter, final MoveExecutor moveExecutor, final List<EvaluationInfo> currentEvaluations) {
        this._maxVariationsAllowedForThisUser = 1;
        this._maxDepthAllowedForThisUser = 12;
        this._engineRunning = false;
        this._restartEngine = false;
        this._staleMate = false;
        this._mate = false;
        this._matingColor = true;
        this._context = context;
        this._contentPresenter = contentPresenter;
        this._moveExecutor = moveExecutor;
        this._engineObservers = new LinkedList<EngineObserver>();
        this._inflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this._mainView = this._inflater.inflate(2131427373, (ViewGroup)null);
        this._currentEvaluations = currentEvaluations;
        this._listAdapter = new MyListAdapter();
        (this._listView = (ListView)this._mainView.findViewById(2131296521)).setAdapter((ListAdapter)this._listAdapter);
        this._infoText = (CustomTextView)this._mainView.findViewById(2131296304);
        this._moreButton = this._mainView.findViewById(2131296319);
        this._lessButton = this._mainView.findViewById(2131296318);
        (this._startStopButton = (TextView)this._mainView.findViewById(2131296320)).setText((CharSequence)this._context.getString(2131689546));
        this._eval = (TextView)this._mainView.findViewById(2131296317);
        this._depth = (TextView)this._mainView.findViewById(2131296316);
        this._moreButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final int variationCount = EngineAnalyzeViewController.this._engineController.getVariationCount();
                if (variationCount < 4) {
                    if (variationCount < EngineAnalyzeViewController.this._maxVariationsAllowedForThisUser) {
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
        this._lessButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (EngineAnalyzeViewController.this._engineController.getVariationCount() > 1) {
                    EngineAnalyzeViewController.this._engineController.removeEngineVariation();
                    if (EngineAnalyzeViewController.this._engineRunning) {
                        EngineAnalyzeViewController.this._engineController.startCalculation();
                    }
                    EngineAnalyzeViewController.this.updateMoreAndLessVariationsButtons();
                }
            }
        });
        this._startStopButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
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
        return this._mate || this._staleMate;
    }
    
    private void resetPosition() {
        final boolean hasSpecialPosition = this.hasSpecialPosition();
        this._mate = false;
        this._staleMate = false;
        if (hasSpecialPosition != this.hasSpecialPosition()) {
            this.updateMateInfo();
        }
    }
    
    private void runOnUIThread(final Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
    
    private void setEval(final float n) {
        float n2;
        if (n > 6.0f) {
            n2 = 6.0f;
        }
        else {
            n2 = n;
            if (n < -6.0f) {
                n2 = -6.0f;
            }
        }
        this._eval.getBackground().setLevel(10000 - (int)((n2 + 6.0f) / 12.0f * 10000.0f));
    }
    
    private void setPositionIsMateForColor(final boolean matingColor) {
        this._mate = true;
        this._staleMate = false;
        this._matingColor = matingColor;
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
            String text = "";
            if (this._mate) {
                if (this._matingColor) {
                    this._infoText.setText((CharSequence)"mate 1-0");
                    this.setEval(999.0f);
                }
                else {
                    this._infoText.setText((CharSequence)"mate 0-1");
                    this.setEval(-999.0f);
                }
                text = "#";
            }
            else if (this._staleMate) {
                this._infoText.setText((CharSequence)"stalemate 1/2");
                text = "=";
                this.setEval(0.0f);
            }
            this._eval.setText((CharSequence)text);
            return;
        }
        this._listView.setVisibility(0);
        this._infoText.setVisibility(8);
    }
    
    private void updateMoreAndLessVariationsButtons() {
        final int variationCount = this._engineController.getVariationCount();
        final View moreButton = this._moreButton;
        final boolean b = false;
        moreButton.setEnabled(variationCount < 4);
        final View lessButton = this._lessButton;
        boolean enabled = b;
        if (variationCount > 1) {
            enabled = true;
        }
        lessButton.setEnabled(enabled);
    }
    
    public void addEngineObserver(final EngineObserver engineObserver) {
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
    public View getContentView(final Context context) {
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
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuItem == null) {
            (this._menuItem = new MenuBarItem(context, context.getResources().getString(2131689524), 2131230825, 2131230826, -1)).setSelectionGroup(1);
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
        this.runOnUIThread(new Runnable() {
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
    public void onEngineStopped(final Move move) {
        this._engineRunning = false;
        this.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (EngineAnalyzeViewController.this._restartEngine) {
                    EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689545));
                }
                else {
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
        this.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                EngineAnalyzeViewController.this._currentEvaluations.clear();
                EngineAnalyzeViewController.this._currentEvaluations.addAll(list);
                EngineAnalyzeViewController.this._listAdapter.notifyDataSetChanged();
                final float eval = EngineAnalyzeViewController.this._engineController.getEval();
                EngineAnalyzeViewController.this._eval.setText((CharSequence)EngineAnalyzeViewController.this._engineController.getEvalString());
                EngineAnalyzeViewController.this.setEval(eval);
                final TextView access.1800 = EngineAnalyzeViewController.this._depth;
                final StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(EngineAnalyzeViewController.this._engineController.getDepth());
                access.1800.setText((CharSequence)sb.toString());
                if (EngineAnalyzeViewController.this.hasSpecialPosition()) {
                    EngineAnalyzeViewController.this.updateMateInfo();
                }
                EngineAnalyzeViewController.this.checkForEngineDepthPermissions();
            }
        });
    }
    
    @Override
    public void positionChanged(final Position position, final Move move) {
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
                }
                else {
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
        this.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                final int size = EngineAnalyzeViewController.this._currentEvaluations.size();
                final UCIEngine defaultSingleEngine = ServiceProvider.getInstance().getEngineService().getDefaultSingleEngine();
                EngineAnalyzeViewController.this._engineController = new EngineController(defaultSingleEngine);
                final EngineController access.000 = EngineAnalyzeViewController.this._engineController;
                int variationsNumber = size;
                if (size == 0) {
                    variationsNumber = 1;
                }
                access.000.setVariationsNumber(variationsNumber);
                defaultSingleEngine.setPosition(positionObservable.getFEN());
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
                final Iterator<EngineObserver> iterator = (Iterator<EngineObserver>)EngineAnalyzeViewController.this._engineObservers.iterator();
                while (iterator.hasNext()) {
                    EngineAnalyzeViewController.this._engineController.addEngineObserver(iterator.next());
                }
                if (EngineAnalyzeViewController.this._engineRunning) {
                    EngineAnalyzeViewController.this._startStopButton.postDelayed((Runnable)new Runnable() {
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
    
    private class MyListAdapter extends BaseAdapter
    {
        public int getCount() {
            return EngineAnalyzeViewController.this._currentEvaluations.size();
        }
        
        public Object getItem(final int n) {
            return EngineAnalyzeViewController.this._currentEvaluations.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            View inflate = view;
            if (view == null) {
                inflate = EngineAnalyzeViewController.this._inflater.inflate(2131427437, (ViewGroup)null);
            }
            final EvaluationInfo evalInfo = EngineAnalyzeViewController.this._currentEvaluations.get(n);
            ((EngineVariantView)inflate).setEvalInfo(evalInfo);
            inflate.findViewById(2131296518).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    final Move move = evalInfo.getMove();
                    if (move != null) {
                        EngineAnalyzeViewController.this._moveExecutor.doMoveInCurrentPosition(move.getSEP());
                    }
                }
            });
            return inflate;
        }
    }
}
