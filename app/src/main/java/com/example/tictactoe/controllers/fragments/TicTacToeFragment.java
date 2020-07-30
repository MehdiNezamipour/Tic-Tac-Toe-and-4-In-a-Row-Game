package com.example.tictactoe.controllers.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.example.tictactoe.models.tic.TicTacToe;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class TicTacToeFragment extends Fragment {

    public static final String BUNDLE_KEY_TIC_TAC_TOW = "com.example.tictactoe.mTicTacTow";
    TicTacToe mTicTacToe;
    private TextView mTextViewTurn;
    private Button[][] mButtons;
    private Button mButtonReset;


    public static TicTacToeFragment newInstance() {
        Bundle args = new Bundle();

        TicTacToeFragment fragment = new TicTacToeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TicTacToeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mButtons = new Button[3][3];
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_KEY_TIC_TAC_TOW, mTicTacToe);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
        findViews(view);
        createTicTacToe(savedInstanceState);
        playGame();
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonReset.setVisibility(View.GONE);
                createTicTacToe(null);
                setEnableStateButtons(true);
            }
        });
        return view;
    }

    private void createTicTacToe(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mTicTacToe = new TicTacToe(mButtons, mTextViewTurn);
            mButtonReset.setVisibility(View.GONE);
        } else {
            mTicTacToe = (TicTacToe) savedInstanceState.getSerializable(BUNDLE_KEY_TIC_TAC_TOW);
            mTicTacToe.savedInstance(mButtons, mTextViewTurn);
            if (mTicTacToe.checkGameFinished()) {
                setEnableStateButtons(false);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }


    }

    private void playGame() {
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons[i].length; j++) {
                final int buttonI = i;
                final int buttonJ = j;
                mButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTicTacToe.play(buttonI, buttonJ);
                        if (mTicTacToe.checkGameFinished())
                            onGameFinish();
                    }
                });
            }
        }
    }

    private void onGameFinish() {
        setEnableStateButtons(false);
        mTextViewTurn.setText("");
        Snackbar.make(getView(), mTicTacToe.getWinner().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
        mButtonReset.setVisibility(View.VISIBLE);

    }

    private void setEnableStateButtons(boolean b) {
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons.length; j++) {
                mButtons[i][j].setEnabled(b);
            }
        }
    }

    private void findViews(View view) {
        mTextViewTurn = view.findViewById(R.id.textView_player_turn_tic);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtons = new Button[][]{
                {view.findViewById(R.id.button_1_1), view.findViewById(R.id.button_1_2), view.findViewById(R.id.button_1_3)
                }, {view.findViewById(R.id.button_2_1), view.findViewById(R.id.button_2_2), view.findViewById(R.id.button_2_3)},
                {view.findViewById(R.id.button_3_1), view.findViewById(R.id.button_3_2), view.findViewById(R.id.button_3_3)}};


    }
}