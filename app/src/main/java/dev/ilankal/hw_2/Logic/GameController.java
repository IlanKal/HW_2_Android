package dev.ilankal.hw_2.Logic;


import android.util.Log;

import java.util.Random;


public class GameController {
    private final int matrixRows = 6;
    private final int matrixCols = 5;
    private final String FLAMING_METEOR = "flaming_meteor";
    private final String COIN = "coin";
    private final String BLANK = "blank";
    private int rocketPos = 2; // 2 is the middle
    private int life;
    private int amountOfDisqualifications = 0;
    private int ScoreBoard = 0;
    private String[][] matrix;
    private int[] lastTwoMeteorPositions = {-1, -1};

    public GameController(int life) {
        this.life = life;
        setUpMatrix();
    }

    public GameController() {
        this(3);
    }

    public int getMatrixRows() {
        return matrixRows;
    }

    public int getMatrixCols() {
        return matrixCols;
    }

    public String getFLAMING_METEOR() {
        return FLAMING_METEOR;
    }

    public String getBLANK() {
        return BLANK;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public int getRocketPos() {
        return rocketPos;
    }

    public void setRocketPos(int rocketPos) {
        this.rocketPos = rocketPos;
    }

    public int getLife() {
        return life;
    }
    public String getCOIN() {
        return COIN;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAmountOfDisqualifications() {
        return amountOfDisqualifications;
    }
    public int getScoreBoard() {
        return ScoreBoard;
    }

    public void setScoreBoard(int scoreBoard) {
        ScoreBoard = scoreBoard;
    }

    public void setAmountOfDisqualifications(int amountOfDisqualifications) {
        this.amountOfDisqualifications = amountOfDisqualifications;
    }

    private void setUpMatrix() {
        this.matrix = new String[getMatrixRows()][getMatrixCols()];
        for (int i = 0; i < getMatrixRows(); i++) {
            for (int j = 0; j < getMatrixCols(); j++) {
                this.matrix[i][j] = getBLANK();
            }
        }
    }

    public boolean lostGame() {
        return getLife() <= 0;
    }

    public void moveRocket(String dir) {
        if (dir.equals("right")) {
            if (getRocketPos() < 4) {
                setRocketPos(getRocketPos() + 1);
            }
        } else if (dir.equals("left")) {
            if (getRocketPos() > 0) {
                setRocketPos(getRocketPos() - 1);
            }
        }
    }


    public void moveMatrixSpot() {
        for (int i = getMatrixRows() - 1; i >= 0; i--) {
            for (int j = getMatrixCols() - 1; j >= 0; j--) {
                if (i == 0) {
                    getMatrix()[i][j] = getBLANK();
                } else {
                    getMatrix()[i][j] = getMatrix()[i - 1][j];
                }
            }
        }
        putRandomNum(); // set the flaming meteor randomly on row
    }

    private void putRandomNum() {
        int [] objectsOnMatrix = {0,1,2,3}; // 3 will be for coin
        int pickRandomObject =   (int) (Math.random() * objectsOnMatrix.length);
        int randomNumberCol = (int) (Math.random() * getMatrixCols()); // Generates a random number between 0 (inclusive) and matrixCols (exclusive)
        if(pickRandomObject != 3){
            getMatrix()[0][randomNumberCol] = getFLAMING_METEOR();
        }
        else {
            getMatrix()[0][randomNumberCol] = getCOIN();
        }
    }

    public boolean checkCollision() {
        if (getMatrix()[getMatrixRows() - 1][getRocketPos()].equals(getFLAMING_METEOR())) {
            setAmountOfDisqualifications(getAmountOfDisqualifications() + 1); // increase the amount of disqualifications by one
            setLife(getLife() - 1); // decrease the amount of life left
            return true;
        }
        return false;
    }
    public void updateCoinsByTimer(){
        ScoreBoard += 1;
    }

    public boolean checkTookCoin(){
        if (getMatrix()[getMatrixRows() - 1][getRocketPos()].equals(getCOIN())) {
            setScoreBoard(getScoreBoard() + 10);
            return true;
        }
        return false;
    }
}
