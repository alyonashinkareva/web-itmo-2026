package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public static class State {
        private final int size;
        private final String[][] cells;
        private boolean crossesMove;
        private String phase;

        public State() {
            size = 3;
            cells = new String[size][size];
            crossesMove = true;
            phase = "RUNNING";
        }

        public int getSize() {
            return size;
        }
        public String[][] getCells() {
            return cells;
        }
        public boolean isCrossesMove() {
            return crossesMove;
        }
        public String getPhase() {
            return phase;
        }
    }

    private State getState(HttpServletRequest request) {
        HttpSession session = request.getSession();
        State state = (State) session.getAttribute("state");
        if (state == null) {
            state = new State();
            session.setAttribute("state", state);
        }
        return state;
    }

    private void action(Map<String, Object> view, HttpServletRequest request) {
        State state = getState(request);
        view.put("state", state);
    }

    private String getWinner(State state) {
        for(int row = 0; row < state.size; row++) {
            String cell = state.cells[row][0];

            if(cell != null) {
                boolean isWin = true;
                for(int col = 1; col < state.size; col++) {
                    if(!(state.cells[row][col] != null && state.cells[row][col].equals(cell))) {
                        isWin = false;
                        break;
                    }
                }
                if(isWin) {
                    return cell;
                }
            }
        }

        for(int col = 0; col < state.size; col++) {
            String cell = state.cells[0][col];

            if(cell != null) {
                boolean isWin = true;
                for(int row = 1; row < state.size; row++) {
                    if(!(state.cells[row][col] != null && state.cells[row][col].equals(cell))) {
                        isWin = false;
                        break;
                    }
                }
                if(isWin) {
                    return cell;
                }
            }
        }

        String cell = state.cells[0][0];

        if(cell != null) {
            boolean isWin = true;
            for(int row = 1; row < state.size; row++) {
                if(!(state.cells[row][row] != null && state.cells[row][row].equals(cell))) {
                    isWin = false;
                    break;
                }
            }
            if(isWin) {
                return cell;
            }
        }

        cell = state.cells[0][state.size - 1];

        if(cell != null) {
            boolean isWin = true;
            for(int row = 1; row < state.size; row++) {
                if(!(state.cells[row][state.size - 1 - row] != null && state.cells[row][state.size - 1 - row].equals(cell))) {
                    isWin = false;
                    break;
                }
            }
            if(isWin) {
                return cell;
            }
        }



        return null;
    }

    private void onMove(Map<String, Object> view, HttpServletRequest request) {
        State state = getState(request);
        if(state.phase.equals("RUNNING")) {
            for(int row = 0; row < state.size; row++) {
                for(int col = 0; col < state.size; col++) {
                    String cell = "cell_" + row + col;
                    if(request.getParameter(cell) != null && state.cells[row][col] == null) {
                        state.cells[row][col] = state.crossesMove ? "X" : "O";

                        String winner = getWinner(state);
                        if(winner != null) {
                            state.phase = winner.equals("X") ? "WON_X" : "WON_O";
                        } else {
                            boolean isNull = false;
                            for(int i = 0; i < state.size; i++) {
                                for(int j = 0; j < state.size; j++) {
                                    if(state.cells[i][j] == null) {
                                        isNull = true;
                                        break;
                                    }
                                }
                            }
                            if(!isNull) {
                                state.phase = "DRAW";
                            }
                        }

                        state.crossesMove = !state.crossesMove;
                    }
                }
            }
        }
        view.put("state", state);
    }

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        State state = new State();
        HttpSession session = request.getSession();
        session.setAttribute("state", state);
        view.put("state", state);
    }
}
