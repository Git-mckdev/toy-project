package com.overflow.toy_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overflow.toy_project.mapper.PostMapper;
import com.overflow.toy_project.model.Comment;
import com.overflow.toy_project.model.Post;

@Service
public class PostService {
    
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    // 게시글 기능
    public List<Post> getAllPosts() {
        return postMapper.selectAllPosts();
    }

    public Post getPost(long id) {
        Post post = postMapper.selectPostById(id);
        post.setComments(postMapper.selectCommentsByPostId(id));

        return post;
    }

    public void createPost(Post post) {
        postMapper.insertPost(post);
    }

    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    public void deletePost(long id) {
        postMapper.deletePost(id);
    }

    public void addComment(Comment comment) {
        postMapper.insertComment(comment);
    }

    public void deleteComment(long id) {
        postMapper.deleteComment(id);
    }

    public void deleteCommentAllByPostId(long postId) {
        postMapper.deleteCommentAllByPostId(postId);
    }
}
