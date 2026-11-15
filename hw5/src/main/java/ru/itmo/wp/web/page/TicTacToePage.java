package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final int SIZE = 3;
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("cell_[0-9]{2}");

    private State getState(HttpServletRequest request) {
        HttpSession session = request.getSession();
        State state = (State) session.getAttribute("state");
        if (state == null) {
            state = new State();
            session.setAttribute("state", state);
        }
        return state;
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("state", getState(request));
    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);

        Enumeration<String> iter = request.getParameterNames();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            if (PARAMETER_PATTERN.matcher(key).matches()) {
                String digits = key.replaceAll("[^0-9]", "");

                int i = Character.digit(digits.charAt(0), 10);
                int j = Character.digit(digits.charAt(1), 10);

                if (0 <= i && i < SIZE && 0 <= j && j < SIZE) {
                    state.makeMove(i, j);
                }
                break;
            }
        }

        view.put("state", state);
    }

    public void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);

        state.reloadGame();
        view.put("state", state);
    }

    public static class State {
        private final int size;
        private final Character[][] cells;
        private String phase;
        private boolean crossesMove;

        private int moveCount;

        public State() {
            this.size = SIZE;
            this.cells = new Character[size][size];

            this.reloadGame();
        }

        public int getSize() {
            return size;
        }

        public Character[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        private void reloadGame() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = null;
                }
            }

            this.phase = "RUNNING";
            this.crossesMove = true;
            this.moveCount = 0;
        }

        private void makeMove(int i, int j) {
             if (!"RUNNING".equals(phase) || cells[i][j] != null) {
                 return;
             }

            Character moveSymbol = getMoveSymbol();
            cells[i][j] = moveSymbol;

            moveCount++;

            if (checkForWinner(i, j, moveSymbol)) {
                phase = "WON_" + moveSymbol;
                return;
            }

            if (checkForDraw()) {
                phase = "DRAW";
                return;
            }

            crossesMove = !crossesMove;

        }

        private Character getMoveSymbol() {
            return isCrossesMove() ? 'X' : 'O';
        }

        private boolean checkForWinner(int i, int j, Character moveSymbol) {
            int row = 0;
            int column = 0;
            int diag1 = 0;
            int diag2 = 0;

            for (int k = 0; k < size; k++) {
                row += moveSymbol.equals(cells[i][k]) ? 1 : 0;
                column += moveSymbol.equals(cells[k][j]) ? 1 : 0;
                diag1 += moveSymbol.equals(cells[k][k]) ? 1 : 0;
                diag2 += moveSymbol.equals(cells[k][size - k - 1]) ? 1 : 0;
            }

            return row == size || column == size || (i == j && diag1 == size) || (i + j == size - 1 && diag2 == size);
        }

        private boolean checkForDraw() {
            return moveCount == size * size;
        }
    }
}
