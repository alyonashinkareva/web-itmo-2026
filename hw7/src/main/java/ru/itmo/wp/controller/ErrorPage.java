package ru.itmo.wp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorPage extends Page implements ErrorController {
    @RequestMapping("/error")
    public String error(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = code == null ? HttpStatus.NOT_FOUND : HttpStatus.valueOf((Integer) code);

        response.setStatus(status.value());
        model.addAttribute("status", status.value());
        model.addAttribute("reason", status.getReasonPhrase());

        return "ErrorPage";
    }

    public String getErrorPath() {
        return "/error";
    }
}
