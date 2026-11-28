package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void addComment(Post post, User user, String text) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(text);
        commentRepository.save(comment);
    }

    @Transactional
    public void writePost(User user, String title, String text, Set<String> tagNames) {
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setText(text);
        for (String name : tagNames) {
            tagRepository.insertIfMissing(name);
            post.getTags().add(tagRepository.findByName(name));
        }
        postRepository.save(post);
    }
}
