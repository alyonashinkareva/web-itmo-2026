package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final String STATE_ATTRIBUTE = "ticTacToeState";

    public void action(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);
        view.put("state", state);
    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);

        String cell = null;
        for (String parameter : request.getParameterMap().keySet()) {
            if (parameter.matches("cell_[0-2][0-2]")) {
                cell = parameter;
                break;
            }
        }

        if (cell != null && state.getPhase().equals("RUNNING")) {
            int row = cell.charAt(5) - '0';
            int col = cell.charAt(6) - '0';

            if (state.getCells()[row][col] == null) {
                String mark = state.isCrossesMove() ? "X" : "O";
                state.getCells()[row][col] = mark;

                if (state.hasWon(mark)) {
                    state.phase = state.isCrossesMove() ? "WON_X" : "WON_O";
                } else if (state.isFull()) {
                    state.phase = "DRAW";
                } else {
                    state.crossesMove = !state.isCrossesMove();
                }
            }
        }

        view.put("state", state);
    }

    public void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        request.getSession().setAttribute(STATE_ATTRIBUTE, state);
        view.put("state", state);
    }

    private State getState(HttpServletRequest request) {
        Object value = request.getSession().getAttribute(STATE_ATTRIBUTE);

        if (value instanceof State) {
            return (State) value;
        }

        State state = new State();
        request.getSession().setAttribute(STATE_ATTRIBUTE, state);
        return state;
    }

    public static class State {
        private final int size = 3;
        private final String[][] cells = new String[size][size];
        private boolean crossesMove = true;
        private String phase = "RUNNING";

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

        private boolean hasWon(String mark) {
            for (int i = 0; i < size; i++) {
                if (mark.equals(cells[i][0])
                        && mark.equals(cells[i][1])
                        && mark.equals(cells[i][2])) {
                    return true;
                }

                if (mark.equals(cells[0][i])
                        && mark.equals(cells[1][i])
                        && mark.equals(cells[2][i])) {
                    return true;
                }
            }

            return mark.equals(cells[0][0]) && mark.equals(cells[1][1]) && mark.equals(cells[2][2]) ||
                    mark.equals(cells[0][2]) && mark.equals(cells[1][1]) && mark.equals(cells[2][0]);
        }

        private boolean isFull() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (cells[row][col] == null) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
