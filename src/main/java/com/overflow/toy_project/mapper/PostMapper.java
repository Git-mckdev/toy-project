package com.overflow.toy_project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.overflow.toy_project.model.Comment;
import com.overflow.toy_project.model.Post;

@Mapper
public interface PostMapper {
    // 게시글 기능
    List<Post> selectAllPosts();
    Post selectPostById(@Param("id") long id);
    void insertPost(Post post);
    void updatePost(Post post);
    void deletePost(@Param("id") long id);

    // 댓글 기능
    List<Comment> selectCommentsByPostId(@Param("postId") long postId);
    void insertComment(Comment comment);
    void deleteComment(@Param("id") long id);
    void deleteCommentAllByPostId(@Param("postId") long postId);
}