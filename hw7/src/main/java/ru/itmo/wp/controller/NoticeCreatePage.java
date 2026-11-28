package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticeCreatePage extends Page{
    private final NoticeService noticeService;

    public NoticeCreatePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/noticeCreate")
    public String noticeCreateGet(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "NoticeCreatePage";
    }

    @PostMapping("/noticeCreate")
    public String noticeCreatePost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticeCreatePage";
        }
        noticeService.create(noticeForm);
        setMessage(httpSession, "Congrats, your notice has been saved!");

        return "redirect:/";
    }

}
