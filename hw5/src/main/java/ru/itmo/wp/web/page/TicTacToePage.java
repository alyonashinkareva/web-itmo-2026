package ru.itmo.wp.web.page;

import ru.itmo.wp.web.model.TicTacToe.State;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        view.put("state", state);
        view.put("phase", state.getPhase());
        saveState(request.getSession(), state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getInstanceState(request.getSession());
        outer:
        for (int row = 0; row < state.getSize(); row++) {
            for (int col = 0; col < state.getSize(); col++) {
                if (request.getParameter("cell_" + row + col) != null) {
                    state.makeMove(row, col);
                    break outer;
                }
            }
        }

        view.put("state", state);
        view.put("phase", state.getPhase());
        saveState(request.getSession(), state);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = getInstanceState(request.getSession());
        view.put("state", state);
        view.put("phase", state.getPhase());
        saveState(request.getSession(), state);
    }

    private State getInstanceState(HttpSession session){
        State state = (State) session.getAttribute("stateTicTacToe");
        if (state == null){
            state = new State();
        }
        return state;
    }

    private void saveState(HttpSession session, State state){
        session.setAttribute("stateTicTacToe", state);
    }
}
