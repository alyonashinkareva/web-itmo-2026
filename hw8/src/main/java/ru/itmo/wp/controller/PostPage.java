package ru.itmo.wp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", findPost(id));
        model.addAttribute("commentForm", new CommentForm());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String comment(@PathVariable("id") long id,
                          @Valid @ModelAttribute("commentForm") CommentForm form,
                          BindingResult result,
                          Model model, HttpSession session) {
        Post post = findPost(id);
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        postService.addComment(post, getUser(session), form.getText());
        return "redirect:/post/" + id;
    }

    private Post findPost(long id) {
        Post post = postService.findById(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such post");
        }
        return post;
    }
}
