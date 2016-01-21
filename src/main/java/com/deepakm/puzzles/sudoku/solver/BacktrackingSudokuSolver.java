package com.deepakm.puzzles.sudoku.solver;

import com.deepakm.puzzles.sudoku.board.Board;
import com.deepakm.puzzles.sudoku.board.BoardPosition;

/**
 * Created by dmarathe on 1/21/16.
 */
public class BacktrackingSudokuSolver implements SudokuSolver {

    private final Board board;

    public BacktrackingSudokuSolver(Board board) {
        this.board = board;
    }

    private boolean isSafe(int row, int column, int number) {
        return isSafeHorizontal(row, number) &&
                isSafeVertical(column, number) &&
                isSafeGrid(row - row % 3, column - column % 3, number);
    }

    private boolean isSafeHorizontal(int row, int number) {
        for (int i = 0; i < board.getColumns(); i++) {
            if (board.get(row, i) == number) return false;
        }
        return true;
    }

    private boolean isSafeVertical(int column, int number) {
        for (int i = 0; i < board.getRows(); i++) {
            if (board.get(i, column) == number) return false;
        }
        return true;
    }

    private boolean isSafeGrid(int row, int colum, int number) {
        for (int i = 0; i < board.getSubGridSize(); i++) {
            for (int j = 0; j < board.getSubGridSize(); j++) {
                if (board.get(row + i, colum + j) == number) return false;
            }
        }
        return true;
    }

    private BoardPosition findUnassignedLocation() {
        BoardPosition position = null;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if (!board.isSet(i, j)) {
                    position = new BoardPosition(i, j);
                    return position;
                }
            }
        }
        return BoardPosition.NONE;
    }

    @Override
    public boolean solve() {
        BoardPosition position = findUnassignedLocation();
        if (position == BoardPosition.NONE) {
            return true;
        }
        for (int num = 1; num <= 9; num++) {
            if (isSafe(position.getX(), position.getY(), num)) {
                board.set(position.getX(), position.getY(), num);
                if (solve()) {
                    return true;
                } else {
                    board.unset(position.getX(), position.getY());
                }
            }
        }
        return false;
    }
}
