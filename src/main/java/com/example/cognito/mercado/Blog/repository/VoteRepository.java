package com.example.cognito.mercado.Blog.repository;

import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.model.Vote;

import com.example.cognito.mercado.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
