package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import ru.itmo.wp.web.exception.RedirectException;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");

        if (state == null) {
            state = new State();
            request.getSession().setAttribute("state", state);
        }

        view.put("state", state);
    }

    private void onMove(HttpServletRequest request) {
        State state = (State) request.getSession().getAttribute("state");

        if (state != null && request.getMethod().equals("POST")) {
            for (String name : request.getParameterMap().keySet()) {
                if (name.matches("cell_[0-2][0-2]")) {
                    int row = name.charAt(5) - '0';
                    int col = name.charAt(6) - '0';

                    state.move(row, col);
                    break;
                }
            }
        }

        throw new RedirectException(request.getRequestURI());
    }

    private void newGame(HttpServletRequest request) {
        if (request.getMethod().equals("POST")) {
            request.getSession().setAttribute("state", new State());
        }

        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final String[][] cells = new String[3][3];
        private boolean crossesMove = true;
        private String phase = "RUNNING";

        public int getSize() {
            return cells.length;
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

        public void move(int row, int col) {
            if (!phase.equals("RUNNING") || cells[row][col] != null) {
                return;
            }

            String mark = crossesMove ? "X" : "O";
            cells[row][col] = mark;

            for (int i = 0; i < 3; i++) {
                if ((mark.equals(cells[i][0])
                        && mark.equals(cells[i][1])
                        && mark.equals(cells[i][2]))
                        || (mark.equals(cells[0][i])
                        && mark.equals(cells[1][i])
                        && mark.equals(cells[2][i]))) {

                    phase = "WON_" + mark;
                    return;
                }
            }

            if ((mark.equals(cells[0][0])
                    && mark.equals(cells[1][1])
                    && mark.equals(cells[2][2]))
                    || (mark.equals(cells[0][2])
                    && mark.equals(cells[1][1])
                    && mark.equals(cells[2][0]))) {

                phase = "WON_" + mark;
                return;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[i][j] == null) {
                        crossesMove = !crossesMove;
                        return;
                    }
                }
            }

            phase = "DRAW";
        }
    }
}