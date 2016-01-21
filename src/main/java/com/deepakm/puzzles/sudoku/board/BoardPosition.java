package com.deepakm.puzzles.sudoku.board;

/**
 * Created by dmarathe on 1/21/16.
 */
public class BoardPosition {
    public static final BoardPosition NONE = new BoardPosition(-1, -1);

    private int x;
    private int y;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
