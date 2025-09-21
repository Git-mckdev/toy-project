package com.overflow.toy_project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Comment;
import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.model.Post;
import com.overflow.toy_project.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 기능
    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();

        model.addAttribute("posts", posts);

        return "post-list";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        model.addAttribute("newComment", new Comment());

        return "post-detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());

        return "post-form-write";
    }

    @PostMapping("/create")
    public String createPost(Post post, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        post.setMember(loginMember);

        postService.createPost(post);

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("post", postService.getPost(id));

        return "post-form-update";
    }

    @PostMapping("/update")
    public String updatePost(Post post) {
        postService.updatePost(post);

        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String updatePost(@PathVariable long id) {
        postService.deleteCommentAllByPostId(id);
        postService.deletePost(id);

        return "redirect:/posts";
    }

    // 댓글 기능
    @PostMapping("/{postId}/add")
    public String addComment(@PathVariable long postId, Comment comment, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        Post post = postService.getPost(postId);

        comment.setMember(loginMember);
        comment.setPost(post);
        
        postService.addComment(comment);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        postService.deleteComment(commentId);

        return "redirect:/posts/" + postId;
    }
}