package com.example.cognito.mercado.Blog.repository;

import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.model.Subreddit;
import com.example.cognito.mercado.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
