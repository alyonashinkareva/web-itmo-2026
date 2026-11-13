package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class TicTacToePage {
    private static final String STATE_ATTRIBUTE = "ticTacToeState";

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("state", getState(request));
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);
        if (state.getPhase() != Phase.RUNNING) {
            view.put("state", state);
            return;
        }

        for (int row = 0; row < state.getSize(); row++) {
            for (int col = 0; col < state.getSize(); col++) {
                if (request.getParameter("cell_" + row + col) != null) {
                    state.makeMove(row, col);
                    view.put("state", state);
                    return;
                }
            }
        }

        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        request.getSession().setAttribute(STATE_ATTRIBUTE, state);
        view.put("state", state);
    }

    private State getState(HttpServletRequest request) {
        HttpSession session = request.getSession();
        State state = (State) session.getAttribute(STATE_ATTRIBUTE);
        if (state == null) {
            state = new State();
            session.setAttribute(STATE_ATTRIBUTE, state);
        }
        return state;
    }

    public static class State {
        private static final int SIZE = 3;

        private final String[][] cells = new String[SIZE][SIZE];
        private boolean crossesMove = true;
        private Phase phase = Phase.RUNNING;

        public int getSize() {
            return SIZE;
        }

        public String[][] getCells() {
            return cells;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public Phase getPhase() {
            return phase;
        }

        private void makeMove(int row, int col) {
            if (cells[row][col] != null || phase != Phase.RUNNING) {
                return;
            }

            cells[row][col] = crossesMove ? "X" : "O";
            updatePhase(row, col);
            if (phase == Phase.RUNNING) {
                crossesMove = !crossesMove;
            }
        }

        private void updatePhase(int row, int col) {
            String symbol = cells[row][col];
            if (isWinningLine(symbol, cells[row][0], cells[row][1], cells[row][2])
                    || isWinningLine(symbol, cells[0][col], cells[1][col], cells[2][col])
                    || row == col && isWinningLine(symbol, cells[0][0], cells[1][1], cells[2][2])
                    || row + col == SIZE - 1 && isWinningLine(symbol, cells[0][2], cells[1][1], cells[2][0])) {
                phase = "X".equals(symbol) ? Phase.WON_X : Phase.WON_O;
            } else if (isBoardFull()) {
                phase = Phase.DRAW;
            }
        }

        private static boolean isWinningLine(String symbol, String first, String second, String third) {
            return symbol.equals(first) && symbol.equals(second) && symbol.equals(third);
        }

        private boolean isBoardFull() {
            for (String[] row : cells) {
                for (String cell : row) {
                    if (cell == null) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }
}
