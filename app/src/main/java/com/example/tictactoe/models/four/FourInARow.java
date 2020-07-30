package com.example.tictactoe.models.four;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class FourInARow implements Serializable {
    private Button[][] mButtons;
    private TextView mTextViewTurn;
    private Player mPlayerOne;
    private Player mPlayerTwo;
    private Player mPlayerDefault = new Player("Default", Color.WHITE);
    private Player mTurn;
    private Player[][] mTableState;
    private Player mWinnerFourInARow = null;
    private int mLevelTable;
    private int[] mVerticalLayouts;
    private int mNumberOfRed = 0;
    private int mNumberOfBlue = 0;


    public FourInARow(Button[][] buttons, TextView textViewTurn, String playerOneName, String playerTwoName) {
        mTextViewTurn = textViewTurn;
        mPlayerOne = new Player(playerOneName, Color.RED);
        mPlayerTwo = new Player(playerTwoName, Color.BLUE);
        mTurn = mPlayerOne;
        mButtons = buttons;
        mTextViewTurn.setText(mTurn.getName());

        mTableState = new Player[buttons.length][buttons.length];
        mVerticalLayouts = new int[mTableState.length];
        mLevelTable = mTableState.length;


        for (int i = 0; i < mTableState.length; i++) {
            mVerticalLayouts[i] = mTableState.length - 1;
            for (int j = 0; j < mTableState.length; j++) {
                mTableState[i][j] = mPlayerDefault;
            }
        }
        changeColor();
    }

    public void changeColor() {
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons[i].length; j++) {
                mButtons[i][j].setBackgroundColor(mTableState[i][j].getColor());
            }
        }
        mTextViewTurn.setText("Turn : " + mTurn.getName());

    }

    public void changeTurn() {
        if (mTurn == mPlayerOne)
            mTurn = mPlayerTwo;
        else
            mTurn = mPlayerOne;
    }

    public void checkWinner() {
        checkVertical();
        checkHorizontal();
        checkDiagonal();
    }

    private void checkDiagonal() {

        for (int i = 0; i < mLevelTable; i++) {
            mNumberOfRed = 0;
            mNumberOfBlue = 0;
            for (int j = 0; j + i < mLevelTable; j++) {
                checkCount(i + j, j);
            }
        }

        for (int i = 0; i < mLevelTable; i++) {
            mNumberOfRed = 0;
            mNumberOfBlue = 0;
            for (int j = 0; j + i < mLevelTable; j++) {
                checkCount(j, i + j);
            }
        }


        for (int i = mLevelTable - 1; i >= 0; i--) {
            mNumberOfRed = 0;
            mNumberOfBlue = 0;
            for (int j = 0; i - j >= 0; j++) {
                checkCount(j, i - j);
            }
        }


        for (int i = 0; i < mLevelTable; i++) {
            mNumberOfRed = 0;
            mNumberOfBlue = 0;
            for (int j = i; j < mLevelTable; j++) {
                checkCount(j, mLevelTable - j + i - 1);
            }
        }
    }

    private void checkVertical() {
        for (int i = 0; i < mTableState.length; i++) {
            mNumberOfBlue = 0;
            mNumberOfRed = 0;
            for (int j = 0; j < mTableState[i].length; j++) {
                checkCount(j, i);
            }
        }
    }

    private void checkHorizontal() {

        for (int i = 0; i < mTableState.length; i++) {
            mNumberOfBlue = 0;
            mNumberOfRed = 0;
            for (int j = 0; j < mTableState[i].length; j++) {
                checkCount(i, j);
            }
        }
    }


    public boolean isGameFinished() {
        if (mWinnerFourInARow != null) {
            return true;
        }
        return false;
    }

    public Player getWinner() {
        return mWinnerFourInARow;
    }

    public void savedInstance(Button[][] buttons, TextView textViewTurn) {
        this.mButtons = buttons;
        this.mTextViewTurn = textViewTurn;
        changeColor();
    }

    public void play(int i) {
        if (mTableState[mVerticalLayouts[i]][i] == mPlayerDefault) {
            mTableState[mVerticalLayouts[i]][i] = mTurn;
            if (mVerticalLayouts[i] != 0)
                mVerticalLayouts[i]--;

            changeTurn();
            changeColor();
            checkEqual();
            checkWinner();
        }
    }


    private void checkEqual() {
        if (mWinnerFourInARow == null) {
            for (int i = 0; i < mTableState.length; i++) {
                for (int j = 0; j < mTableState.length; j++)
                    if (mTableState[i][j] == mPlayerDefault)
                        return;
            }
            mWinnerFourInARow = mPlayerDefault;
        }
    }

    private void checkCount(int i, int j) {
        if (mTableState[i][j] == mPlayerTwo)
            mNumberOfBlue++;
        else if (mTableState[i][j] == mPlayerOne)
            mNumberOfRed++;
        if (mTableState[i][j] != mPlayerOne)
            mNumberOfRed = 0;
        if (mTableState[i][j] != mPlayerTwo)
            mNumberOfBlue = 0;
        if (mNumberOfRed == 4) {
            mWinnerFourInARow = mPlayerOne;
            return;
        }
        if (mNumberOfBlue == 4) {
            mWinnerFourInARow = mPlayerTwo;
        }
    }


}
