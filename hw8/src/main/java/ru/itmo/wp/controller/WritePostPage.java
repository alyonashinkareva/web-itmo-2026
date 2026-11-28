package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.WritePostForm;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class WritePostPage extends Page {
    private final PostService postService;

    public WritePostPage(PostService postService) {
        this.postService = postService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("postForm", new WritePostForm());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("postForm") WritePostForm form,
                                BindingResult result, HttpSession session) {
        Set<String> names = new TreeSet<>();
        if (form.getTags() != null) {
            for (String name : form.getTags().split("(?U)\\s+")) {
                if (name.isEmpty()) {
                    continue;
                }
                if (!name.matches("[A-Za-z]+") || name.length() > 255) {
                    result.rejectValue("tags", "tags.invalid",
                            "Each tag must contain only Latin letters and be at most 255 characters long");
                    break;
                }
                names.add(name.toLowerCase(Locale.ROOT));
            }
        }
        if (result.hasErrors()) {
            return "WritePostPage";
        }

        postService.writePost(getUser(session), form.getTitle(), form.getText(), names);
        putMessage(session, "You published new post");
        return "redirect:/myPosts";
    }
}
