package com.overflow.toy_project.controller.main.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Comment;
import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.model.Post;
import com.overflow.toy_project.security.CustomUserDetails;
import com.overflow.toy_project.service.PostService;

@Controller
@RequestMapping("/main/post")
public class PostController {
    
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 기능
    @GetMapping("/list")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();

        model.addAttribute("posts", posts);

        return "main/post/list";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable long id, Model model, Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        model.addAttribute("member", member);
        model.addAttribute("post", postService.getPost(id));
        model.addAttribute("newComment", new Comment());

        return "main/post/detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());

        return "main/post/write";
    }

    @PostMapping("/create")
    public String createPost(Post post, Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        post.setMember(member);

        postService.createPost(post);

        return "redirect:/main/post/" + post.getId();
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("post", postService.getPost(id));

        return "main/post/update";
    }

    @PostMapping("/update")
    public String updatePost(Post post) {
        postService.updatePost(post);

        return "redirect:/main/post/" + post.getId();
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postService.deleteCommentAllByPostId(id);
        postService.deletePost(id);

        return "redirect:/main/post/list";
    }

    // 댓글 기능
    @PostMapping("/{postId}/add")
    public String addComment(@PathVariable long postId, Comment comment, Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        Post post = postService.getPost(postId);

        comment.setMember(member);
        comment.setPost(post);
        
        postService.addComment(comment);

        return "redirect:/main/post/" + postId;
    }

    @PostMapping("/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        postService.deleteComment(commentId);

        return "redirect:/main/post/" + postId;
    }
}