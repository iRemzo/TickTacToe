package com.example.rafal.ticktactoe;

import java.util.Random;

/**
 * Created by Rafal on 2016-02-07.
 */
public class TickTacToeGame {

    private char mBoard[];
    private final static int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMP_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;
    private int mWinLane[];


    public TickTacToeGame()
    {
        mBoard = new char[BOARD_SIZE];
        mRand = new Random();
        mWinLane = new int[3];

        clearBoard();
    }

    public static int getBoardSize()
    {
        return BOARD_SIZE;
    }

    public void clearBoard()
    {
        for(int i = 0; i < getBoardSize(); i++) {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public int[] getmWinnerLane() {
        return mWinLane;
    }

    public void setMove(char player, int location)
    {
        mBoard[location] = player;
    }

    public int getComputerMove()
    {
        for(int i = 0; i < getBoardSize(); i++) {
            if(mBoard[i] == EMPTY_SPACE) {
                char curr = mBoard[i];
                mBoard[i] = COMP_PLAYER;

                // if pc can win
                if(checkForWinner() == 3) {
                    setMove(COMP_PLAYER, i);
                    return i;
                }
                else {
                    mBoard[i] = curr;
                }
            }
        }

        for(int i = 0; i < getBoardSize(); i++) {
            if(mBoard[i] == EMPTY_SPACE) {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;

                // block player if can win
                if(checkForWinner() == 2) {
                    setMove(COMP_PLAYER, i);
                    return i;
                }
                else {
                    mBoard[i] = curr;
                }
            }
        }

        //lets rand the move
        int move;
        do {
            move = mRand.nextInt(getBoardSize());
        } while(mBoard[move] != EMPTY_SPACE);

        setMove(COMP_PLAYER, move);
        return move;
    }

    public int checkForWinner()
    {
        /*
            0 - no winner
            1 - tie, no spaces
            2 - player wins
            3 - computer wins
        */

        // horizontal for player
        for(int i = 0; i <= 6; i+=3) {
            if(mBoard[i] == HUMAN_PLAYER && mBoard[i+1] == HUMAN_PLAYER && mBoard[i+2] == HUMAN_PLAYER) {
                mWinLane[0] = i;
                mWinLane[1] = i+1;
                mWinLane[2] = i+2;
                return 2;
            }
        }

        // horizontal for pc
        for(int i = 0; i <= 6; i+=3) {
            if(mBoard[i] == COMP_PLAYER && mBoard[i+1] == COMP_PLAYER && mBoard[i+2] == COMP_PLAYER) {
                mWinLane[0] = i;
                mWinLane[1] = i+1;
                mWinLane[2] = i+2;
                return 3;
            }
        }

        // diagonal for player
        for(int i = 0; i <= 2; i++) {
            if(mBoard[i] == HUMAN_PLAYER && mBoard[i+3] == HUMAN_PLAYER && mBoard[i+6] == HUMAN_PLAYER) {
                mWinLane[0] = i;
                mWinLane[1] = i+3;
                mWinLane[2] = i+6;
                return 2;
            }
        }

        // diagonal for pc
        for(int i = 0; i <= 2; i++) {
            if(mBoard[i] == COMP_PLAYER && mBoard[i+3] == COMP_PLAYER && mBoard[i+6] == COMP_PLAYER) {
                mWinLane[0] = i;
                mWinLane[1] = i+3;
                mWinLane[2] = i+6;
                return 3;
            }
        }

        // cross
        if(mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER) {
            mWinLane[0] = 0;
            mWinLane[1] = 4;
            mWinLane[2] = 8;
            return 2;
        }
        if(mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER) {
            mWinLane[0] = 2;
            mWinLane[1] = 4;
            mWinLane[2] = 6;
            return 2;
        }

        // cross
        if(mBoard[0] == COMP_PLAYER && mBoard[4] == COMP_PLAYER && mBoard[8] == COMP_PLAYER) {
            mWinLane[0] = 0;
            mWinLane[1] = 4;
            mWinLane[2] = 8;
            return 3;
        }
        if(mBoard[2] == COMP_PLAYER && mBoard[4] == COMP_PLAYER && mBoard[6] == COMP_PLAYER) {
            mWinLane[0] = 2;
            mWinLane[1] = 4;
            mWinLane[2] = 6;
            return 3;
        }

        for(int i = 0; i < getBoardSize(); i++) {
            if(mBoard[i] == EMPTY_SPACE) {
                return 0;
            }
        }
        return 1;
    }
}
