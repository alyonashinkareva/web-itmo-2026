package ru.itmo.wp.web.model.TicTacToe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cells {
    private final int width;
    private final List<Character> table;

    public Cells(int width) {
        this.width = width;
        table = new ArrayList<>(Collections.nCopies(width * width, null));
    }

    private int index(int row, int col){
        return row * width + col;
    }

    public Character get(int row, int col){
        return table.get(index(row, col));
    }

    public Character set(int row, int col, Character value){
        return table.set(index(row, col), value);
    }

    public int getWidth(){
        return width;
    }

    public List<List<Character>> getList() {
        List<List<Character>> result = new ArrayList<>();

        for (int row = 0; row < width; row++) {
            result.add(table.subList(row * width, (row + 1) * width));
        }

        return result;
    }

}
