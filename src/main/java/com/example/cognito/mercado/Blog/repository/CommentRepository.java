package com.example.cognito.mercado.Blog.repository;

import com.example.cognito.mercado.Blog.model.Comment;

import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
