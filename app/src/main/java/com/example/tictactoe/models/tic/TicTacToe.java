package com.example.tictactoe.models.tic;

import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.enums.Status;
import com.example.tictactoe.enums.Winner;

import java.io.Serializable;

public class TicTacToe implements Serializable {

    private Button[][] mButtons;
    private TextView mTextViewTurn;
    private Status[][] mTableStatus = new Status[3][3];
    private Winner mWinner = null;
    private Status mStatusTurn = Status.O;


    public TicTacToe(Button[][] buttons, TextView textViewTurn) {
        mButtons = buttons;
        mTextViewTurn = textViewTurn;
        for (int i = 0; i < mTableStatus.length; i++) {
            for (int j = 0; j < mTableStatus.length; j++) {
                mTableStatus[i][j] = Status.E;
            }
        }

        updateUi();

    }

    private void updateUi() {
        mTextViewTurn.setText("Turn : " + mStatusTurn.toString());
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons.length; j++) {
                if (mTableStatus[i][j].equals(Status.E))
                    mButtons[i][j].setText("");
                else
                    mButtons[i][j].setText(mTableStatus[i][j].toString());
            }
        }
    }

    private void changeTurn() {
        if (mStatusTurn.equals(Status.O))
            mStatusTurn = Status.X;
        else
            mStatusTurn = Status.O;
    }

    private void checkRows() {
        for (int i = 0; i < mTableStatus.length; i++) {
            if (mTableStatus[i][0] == Status.O && mTableStatus[i][1] == Status.O && mTableStatus[i][2] == Status.O) {
                mWinner = Winner.O_WINS;
            } else if (mTableStatus[i][0] == Status.X && mTableStatus[i][1] == Status.X && mTableStatus[i][2] == Status.X)
                mWinner = Winner.X_WINS;
        }
    }

    private void checkColumns() {
        for (int i = 0; i < mTableStatus.length; i++) {
            if (mTableStatus[0][i] == Status.O && mTableStatus[1][i] == Status.O && mTableStatus[2][i] == Status.O) {
                mWinner = Winner.O_WINS;
            } else if (mTableStatus[0][i] == Status.X && mTableStatus[1][i] == Status.X && mTableStatus[2][i] == Status.X)
                mWinner = Winner.X_WINS;
        }
    }

    private void checkDiameter() {
        if ((mTableStatus[0][0] == Status.O && mTableStatus[1][1] == Status.O && mTableStatus[2][2] == Status.O)
                || (mTableStatus[0][2] == Status.O && mTableStatus[1][1] == Status.O && mTableStatus[2][0] == Status.O)) {
            mWinner = Winner.O_WINS;
        } else if ((mTableStatus[0][0] == Status.X && mTableStatus[1][1] == Status.X && mTableStatus[2][2] == Status.X)
                || ((mTableStatus[0][2] == Status.X && mTableStatus[1][1] == Status.X && mTableStatus[2][0] == Status.X)))
            mWinner = Winner.X_WINS;
    }


    private void checkDraw() {
        for (int i = 0; i < mTableStatus.length; i++) {
            for (int j = 0; j < mTableStatus.length; j++) {
                if (mTableStatus[i][j] == Status.E)
                    return;
            }
        }
        mWinner = Winner.DRAW;
    }

    private void checkWinner() {
        checkDraw();
        checkRows();
        checkColumns();
        checkDiameter();
    }

    public Winner getWinner() {
        return mWinner;
    }

    public void play(int i, int j) {
        if (mTableStatus[i][j] == Status.E) {
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
