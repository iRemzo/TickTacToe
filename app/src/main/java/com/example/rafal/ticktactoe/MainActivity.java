package com.example.rafal.ticktactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mButtons[];
    private TickTacToeGame mGame;

    private TextView tvInfo;
    private TextView tvHumanCount;
    private TextView tvCompCount;
    private TextView tvTiesCount;
    private Button btnNewGame;

    private int mHumanCounter = 0;
    private int mCompCounter = 0;
    private int mTiesCounter = 0;

    private boolean mGameOver = false;
    private boolean mHumanFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtons = new Button[TickTacToeGame.getBoardSize()];

        TableLayout gameBoard = (TableLayout) findViewById(R.id.gameBoard);
        int counter = 0;
        for(int i = 0; i < 3; i++) {
            TableRow row = new TableRow(this);

            for(int j = 0; j < 3; j++) {
                Button b = new Button(this);
                b.setText("" + counter);
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 75);

                TableRow.LayoutParams params = new TableRow.LayoutParams(200, 200);
                b.setLayoutParams(params);

                mButtons[counter] = b;
                row.addView(mButtons[counter]);
                counter++;
            }
            gameBoard.addView(row);
        }

        tvInfo = (TextView) findViewById(R.id.information);
        tvHumanCount = (TextView) findViewById(R.id.humanCount);
        tvCompCount = (TextView) findViewById(R.id.compCount);
        tvTiesCount = (TextView) findViewById(R.id.tiesCount);
        btnNewGame = (Button) findViewById(R.id.startGame);

        tvHumanCount.setText(Integer.toString(mHumanCounter));
        tvCompCount.setText(Integer.toString(mCompCounter));
        tvTiesCount.setText(Integer.toString(mTiesCounter));

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameOver) {
                    startNewGame();
                    btnNewGame.setVisibility(Button.INVISIBLE);
                    btnNewGame.setEnabled(false);
                }
            }
        });



        mGame = new TickTacToeGame();
        startNewGame();
    }

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;

        for(int i = 0; i < mButtons.length; i++) {
            mButtons[i].setText("");
            mButtons[i].setEnabled(true);
            mButtons[i].setOnClickListener(new ButtonClickListener(i));
            mButtons[i].setBackgroundResource(android.R.drawable.btn_default_small);
        }

        if(mHumanFirst) {
            tvInfo.setText(R.string.first_human);
            mHumanFirst = false;
        }
        else {
            tvInfo.setText(R.string.turn_human);
            int move = mGame.getComputerMove();
            setMove(mGame.COMP_PLAYER, move);
            mHumanFirst = true;
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);

        mButtons[location].setEnabled(false);
        mButtons[location].setText(""+player);

        if(player == mGame.COMP_PLAYER) {
            mButtons[location].setTextColor(Color.RED);
        }
        else {
            mButtons[location].setTextColor(Color.GREEN);
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        private int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            if(!mGameOver) {
                if(mButtons[location].isEnabled()) {
                    setMove(mGame.HUMAN_PLAYER, location);

                    int winner = mGame.checkForWinner();

                    if(winner == 0) {
                        tvInfo.setText(R.string.turn_comp);
                        int move = mGame.getComputerMove();
                        setMove(mGame.COMP_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }

                    if(winner == 0) {
                        tvInfo.setText(R.string.turn_human);
                    }
                    else {
                        if(winner == 1) {
                            tvInfo.setText(R.string.result_tie);
                            mTiesCounter++;
                            tvTiesCount.setText(Integer.toString(mTiesCounter));
                        }
                        else if(winner == 2) {
                            tvInfo.setText(R.string.result_human_wins);
                            mHumanCounter++;
                            tvHumanCount.setText(Integer.toString(mHumanCounter));
                        }
                        else if(winner == 3) {
                            tvInfo.setText(R.string.result_comp_wins);
                            mCompCounter++;
                            tvCompCount.setText(Integer.toString(mCompCounter));
                        }
                        //Create winner lane
                        if(winner >= 2) {
                            int[] winnerLane = mGame.getmWinnerLane();
                            for (int i = 0; i < winnerLane.length; i++) {
                                mButtons[winnerLane[i]].setBackgroundColor(Color.GRAY);
                            }
                        }
                        mGameOver = true;
                        btnNewGame.setVisibility(Button.VISIBLE);
                        btnNewGame.setEnabled(true);
                    }
                }
            }
        }
    }
}
