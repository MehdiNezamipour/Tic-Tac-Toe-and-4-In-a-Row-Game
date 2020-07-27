package com.example.tictactoe.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.R;
import com.example.tictactoe.controllers.fragments.FourInARowFragment;
import com.example.tictactoe.controllers.fragments.TicTacToeFragment;

public class GameActivity extends AppCompatActivity {

    private Button mButtonTicTacToe;
    private Button mButtonFourInARow;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        mButtonTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, new TicTacToeFragment())
                        .commit();
            }
        });

        mButtonFourInARow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, new FourInARowFragment())
                        .commit();
            }
        });
    }


    private void findViews() {
        mButtonTicTacToe = findViewById(R.id.button_tic_tac_toe);
        mButtonFourInARow = findViewById(R.id.button_four_in_a_row);
    }


}