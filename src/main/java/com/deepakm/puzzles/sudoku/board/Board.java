package com.deepakm.puzzles.sudoku.board;

import java.io.PrintStream;

/**
 * Created by dmarathe on 1/21/16.
 */
public interface Board {

    public void initialise(int[][] puzzle);

    public int getRows();
    public int getColumns();
    public int getSubGridSize();

    public int get(int row, int column);
    public void set(int row, int column, int number);
    public void unset(int row, int column);
    public boolean isSet(int row, int column);

    public void printBoard(PrintStream out);
}
