package com.example.tictactoe.models.tic;

import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.enums.StatusTicTacToe;
import com.example.tictactoe.enums.WinnerTicTacToe;

import java.io.Serializable;

public class TicTacToe implements Serializable {

    private Button[][] mButtons;
    private TextView mTextViewTurn;
    private StatusTicTacToe[][] mTableStatus = new StatusTicTacToe[3][3];
    private WinnerTicTacToe mWinner = null;
    private StatusTicTacToe mStatusTurn = StatusTicTacToe.O;

    public TicTacToe(Button[][] buttons, TextView textViewTurn) {
        mButtons = buttons;
        mTextViewTurn = textViewTurn;
        for (int i = 0; i < mTableStatus.length; i++) {
            for (int j = 0; j < mTableStatus.length; j++) {
                mTableStatus[i][j] = StatusTicTacToe.E;
            }
        }

        updateUi();

    }

    private void updateUi() {
        mTextViewTurn.setText("Turn : " + mStatusTurn.toString());
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons.length; j++) {
                if (mTableStatus[i][j].equals(StatusTicTacToe.E))
                    mButtons[i][j].setText("");
                else
                    mButtons[i][j].setText(mTableStatus[i][j].toString());
            }
        }
    }

    private void changeTurn() {
        if (mStatusTurn.equals(StatusTicTacToe.O))
            mStatusTurn = StatusTicTacToe.X;
        else
            mStatusTurn = StatusTicTacToe.O;
    }

    private void checkRows() {
        for (int i = 0; i < mTableStatus.length; i++) {
            if (mTableStatus[i][0] == StatusTicTacToe.O && mTableStatus[i][1] == StatusTicTacToe.O && mTableStatus[i][2] == StatusTicTacToe.O) {
                mWinner = WinnerTicTacToe.O_WINS;
            } else if (mTableStatus[i][0] == StatusTicTacToe.X && mTableStatus[i][1] == StatusTicTacToe.X && mTableStatus[i][2] == StatusTicTacToe.X)
                mWinner = WinnerTicTacToe.X_WINS;
        }
    }

    private void checkColumns() {
        for (int i = 0; i < mTableStatus.length; i++) {
            if (mTableStatus[0][i] == StatusTicTacToe.O && mTableStatus[1][i] == StatusTicTacToe.O && mTableStatus[2][i] == StatusTicTacToe.O) {
                mWinner = WinnerTicTacToe.O_WINS;
            } else if (mTableStatus[0][i] == StatusTicTacToe.X && mTableStatus[1][i] == StatusTicTacToe.X && mTableStatus[2][i] == StatusTicTacToe.X)
                mWinner = WinnerTicTacToe.X_WINS;
        }
    }

    private void checkDiameter() {
        if ((mTableStatus[0][0] == StatusTicTacToe.O && mTableStatus[1][1] == StatusTicTacToe.O && mTableStatus[2][2] == StatusTicTacToe.O)
                || (mTableStatus[0][2] == StatusTicTacToe.O && mTableStatus[1][1] == StatusTicTacToe.O && mTableStatus[2][0] == StatusTicTacToe.O)) {
            mWinner = WinnerTicTacToe.O_WINS;
        } else if ((mTableStatus[0][0] == StatusTicTacToe.X && mTableStatus[1][1] == StatusTicTacToe.X && mTableStatus[2][2] == StatusTicTacToe.X)
                || ((mTableStatus[0][2] == StatusTicTacToe.X && mTableStatus[1][1] == StatusTicTacToe.X && mTableStatus[2][0] == StatusTicTacToe.X)))
            mWinner = WinnerTicTacToe.X_WINS;
    }


    private void checkDraw() {
        for (int i = 0; i < mTableStatus.length; i++) {
            for (int j = 0; j < mTableStatus.length; j++) {
                if (mTableStatus[i][j] == StatusTicTacToe.E)
                    return;
            }
        }
        mWinner = WinnerTicTacToe.DRAW;
    }

    private void checkWinner() {
        checkDraw();
        checkRows();
        checkColumns();
        checkDiameter();
    }

    public WinnerTicTacToe getWinner() {
        return mWinner;
    }

    public void play(int i, int j) {
        if (mTableStatus[i][j] == StatusTicTacToe.E) {
            mTableStatus[i][j] = mStatusTurn;
            checkWinner();
            changeTurn();
            updateUi();
        }
    }

    public void savedInstance(Button[][] buttons, TextView textViewTurn) {
        mButtons = buttons;
        mTextViewTurn = textViewTurn;
        updateUi();
    }

    public boolean checkGameFinished() {
        if (mWinner == null)
            return false;
        else
            return true;
    }


}
