package com.deepakm.puzzles.sudoku.board;

import java.io.PrintStream;

/**
 * Created by dmarathe on 1/21/16.
 */
public class ArrayBackedBoard implements Board {
    private final int rows;
    private final int columns;
    private final int unassignedMarkerValue;
    private final int container[][];

    private static int DEFAULT_INITIAL_VALUE = 0;
    private static int DEFAULT_ROWS = 9;
    private static int DEFAULT_COLUMNS = 9;
    private static int DEFAULT_SUBGRID_SIZE = 3;


    public ArrayBackedBoard() {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS, DEFAULT_INITIAL_VALUE);
    }

    public ArrayBackedBoard(int rows, int columns, int unassignedMarkerValue) {
        this.rows = rows;
        this.columns = columns;
        this.unassignedMarkerValue = unassignedMarkerValue;
        this.container = new int[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.container[i][j] = this.unassignedMarkerValue;
            }
        }
    }


    @Override
    public void initialise(int[][] puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException("input array is null.");
        }
        if (puzzle.length != this.rows) {
            throw new IllegalArgumentException("container size mismatch.");
        }
        if (puzzle[0] == null) {
            throw new IllegalArgumentException("input array cannot be null.");
        }
        if (puzzle[0].length != this.columns) {
            throw new IllegalArgumentException("container size mismatch.");
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.container[i][j] = puzzle[i][j];
            }
        }
    }


    @Override
    public int getSubGridSize() {
        return DEFAULT_SUBGRID_SIZE;
    }

    @Override
    public int getColumns() {
        return this.columns;
    }


    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public void set(int row, int column, int number) {
        this.container[row][column] = number;
    }

    @Override
    public int get(int row, int column) {
        return this.container[row][column];
    }

    @Override
    public void unset(int row, int column) {
        this.container[row][column] = unassignedMarkerValue;
    }

    @Override
    public boolean isSet(int row, int column) {
        return this.container[row][column] != unassignedMarkerValue;
    }

    @Override
    public void printBoard(PrintStream out) {
        final char space = ' ';
        final char vertical = '|';
        final char horizontal = '-';

        out.println();
        for (int i = 0; i < rows; i++) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("" + vertical + "" + space);
            for (int j = 0; j < columns; j++) {
                buffer.append("" + this.container[i][j] + space);
                if ((j + 1) % 3 == 0) {
                    buffer.append("" + vertical + space);
                }
            }
            String line = buffer.toString();

            if (i != 0) {
                out.print(line);
                if ((i + 1) % 3 == 0) {
                    out.println();
                    for (int k = 0; k < line.length() / 2; k++) {
                        out.print(horizontal + "" + space);
                    }
                }
            } else {
                for (int k = 0; k < line.length() / 2; k++) {
                    out.print(horizontal + "" + space);
                }
                out.println();
                out.print(line);
            }
            out.println();
        }
        out.println();
    }
}
