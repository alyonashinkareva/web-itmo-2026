package ru.itmo.wp.web.model.TicTacToe;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class State {
    private final int size = 3;
    private final Cells cells = new Cells(size);

    private boolean crossesMove = true;
    private String phase = "RUNNING";

    public List<List<Character>> getCells() {
        return cells.getList();
    }

    public int getSize() {
        return size;
    }

    public boolean isCrossesMove() {
        return crossesMove;
    }

    public String getPhase() {
        return phase;
    }

    public void makeMove(int row, int col) {
        if (!phase.equals("RUNNING")) {
            return;
        }

        if (cells.get(row, col) != null) {
            return;
        }

        cells.set(row, col, crossesMove ? 'X' : 'O');

        updatePhase(row, col);

        if (phase.equals("RUNNING")) {
            crossesMove = !crossesMove;
        }
    }

    private void updatePhase(int row, int col) {
        if (hasWon(row, col)) {
            phase = crossesMove ? "WON_X" : "WON_O";
        } else if (isFull()) {
            phase = "DRAW";
        }
    }

    private boolean isSameTarget(int row, int col, char target) {
        return row >= 0 && row < size
                && col >= 0 && col < size
                && cells.get(row, col) != null
                && cells.get(row, col) == target;
    }

    private boolean hasWon(int row, int col){
        return hasWon(row, col, 0, 1) || hasWon(row, col, 1, 0) || hasWon(row, col, 1, 1);
    }

    private boolean hasWon(
            int row,
            int col,
            int rowDiff,
            int colDiff
    ) {
        char target = cells.get(row, col);

        int count = 0;

        for (int r = getBeginIndex(row - rowDiff * size); r <= getEndIndex(row + rowDiff * size); r+=rowDiff) {
            for (int c = getBeginIndex(col - colDiff * size); c <= getEndIndex(col + colDiff * size); c+=colDiff) {
                if (isSameTarget(r, c, target)){
                    count++;
                    if (count == size){
                        return true;
                    }
                }else {
                    count = 0;
                }
                if (colDiff == 0){
                    break;
                }
            }
            if (rowDiff == 0){
                break;
            }
        }
        return false;
    }

    private int getBeginIndex(int init){
        return max(0, init);
    }

    private int getEndIndex(int init){
        return min(size, init);
    }

    private boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cells.get(row, col) == null) {
                    return false;
                }
            }
        }

        return true;
    }
}