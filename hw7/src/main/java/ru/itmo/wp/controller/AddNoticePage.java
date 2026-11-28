package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.service.NoticeService;

import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page {
    private final NoticeService noticeService;

    public AddNoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/addNotice")
    public String showForm(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "AddNoticePage";
    }

    @PostMapping("/addNotice")
    public String add(@Valid @ModelAttribute("noticeForm") NoticeForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "AddNoticePage";
        }
        noticeService.add(form.getContent());
        return "redirect:/";
    }
}
