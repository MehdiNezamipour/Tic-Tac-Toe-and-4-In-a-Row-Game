package com.example.tictactoe.controllers.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.example.tictactoe.models.four.FourInARow;
import com.google.android.material.snackbar.Snackbar;

public class FourInARowFragment extends Fragment {

    public static final String BUNDLE_KEY_M_FOUR_IN_A_ROW = "mFourInARow";
    public static final String BUNDLE_KEY_M_TABLE_LEVEL = "mTableLevel";
    public static final String BUNDLE_KEY_VISIBILITY_OF_SETTING_LAYOUT = "visibilityOfSettingLayout";
    private FourInARow mFourInARow;
    private EditText mEditTextPlayerOne;
    private EditText mEditTextPlayerTwo;
    private EditText mEditTextLevel;
    private TextView mTextViewTurn;
    private Button mButtonStartGame;
    private int mTableLevel;
    private Button[][] mButtons;
    private LinearLayout mContainerLayout;
    private LinearLayout[] mVerticalLayouts;
    private LinearLayout mLinearLayoutSettingLayout;
    private int mVisibilityOfSettingLayout = View.VISIBLE;


    public FourInARowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_KEY_M_FOUR_IN_A_ROW, mFourInARow);
        outState.putInt(BUNDLE_KEY_M_TABLE_LEVEL, mTableLevel);
        outState.putInt(BUNDLE_KEY_VISIBILITY_OF_SETTING_LAYOUT, mLinearLayoutSettingLayout.getVisibility());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_in_a_row, container, false);
        findViews(view);
        if (savedInstanceState != null && savedInstanceState.getInt(BUNDLE_KEY_VISIBILITY_OF_SETTING_LAYOUT) == View.GONE) {
            mVisibilityOfSettingLayout = savedInstanceState.getInt(BUNDLE_KEY_VISIBILITY_OF_SETTING_LAYOUT);
            mLinearLayoutSettingLayout.setVisibility(mVisibilityOfSettingLayout);
            mTableLevel = savedInstanceState.getInt(BUNDLE_KEY_M_TABLE_LEVEL);
            createBoard();
            mFourInARow = (FourInARow) savedInstanceState.getSerializable(BUNDLE_KEY_M_FOUR_IN_A_ROW);
            mFourInARow.savedInstance(mButtons, mTextViewTurn);
            playGame();
        }

        setListeners();
        return view;
    }


    private void setListeners() {
        mButtonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTableLevel = Integer.parseInt(mEditTextLevel.getText().toString());
                createBoard();
                mFourInARow = new FourInARow(mButtons, mTextViewTurn,
                        mEditTextPlayerOne.getText().toString(), mEditTextPlayerTwo.getText().toString());
                mLinearLayoutSettingLayout.setVisibility(View.GONE);
                playGame();
            }
        });
    }

    private void findViews(View view) {
        mLinearLayoutSettingLayout = view.findViewById(R.id.setting_layout);
        mLinearLayoutSettingLayout.setVisibility(mVisibilityOfSettingLayout);
        mContainerLayout = view.findViewById(R.id.containerLayout);
        mEditTextPlayerOne = view.findViewById(R.id.player1_EditText);
        mEditTextPlayerTwo = view.findViewById(R.id.player2_EditText);
        mEditTextLevel = view.findViewById(R.id.level_EditText);
        mTextViewTurn = view.findViewById(R.id.turn_TextView);
        mButtonStartGame = view.findViewById(R.id.start_Button);


    }

    public void createBoard() {
        mTextViewTurn.setVisibility(View.VISIBLE);
        mContainerLayout.setWeightSum(mTableLevel);
        mButtons = new Button[mTableLevel][mTableLevel];
        mVerticalLayouts = new LinearLayout[mTableLevel];

        for (int i = 0; i < mTableLevel; i++) {
            mVerticalLayouts[i] = new LinearLayout(getActivity());
            mVerticalLayouts[i].setOrientation(LinearLayout.VERTICAL);
            mVerticalLayouts[i].setWeightSum(mTableLevel);
            LinearLayout.LayoutParams size = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            size.weight = 1;

            mVerticalLayouts[i].setLayoutParams(size);
            for (int j = 0; j < mTableLevel; j++) {
                size = new LinearLayout.LayoutParams
                        (100, 100);
                size.setMargins(8, 8, 8, 8);
                size.weight = 1;
                mButtons[j][i] = new Button(getActivity());
                mButtons[j][i].setClickable(false);
                mButtons[j][i].setText("");
                mButtons[j][i].setBackgroundColor(Color.WHITE);
                mButtons[j][i].setLayoutParams(size);
                mVerticalLayouts[i].addView(mButtons[j][i]);
            }
            mContainerLayout.addView(mVerticalLayouts[i]);
        }
    }

    public void playGame() {
        for (int i = 0; i < mVerticalLayouts.length; i++) {
            final int finalI = i;
            mVerticalLayouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFourInARow.play(finalI);
                    if (mFourInARow.isGameFinished()) {
                        mContainerLayout.removeAllViews();
                        mTextViewTurn.setVisibility(View.INVISIBLE);
                        resetSettingLayout();
                        mLinearLayoutSettingLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(getView(), mFourInARow.getWinner().getName() + " Win", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void resetSettingLayout() {
        mEditTextLevel.setText("");
        mEditTextPlayerOne.setText("");
        mEditTextPlayerTwo.setText("");
    }
}