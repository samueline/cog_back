package com.example.cognito.mercado.Blog.mapper;


import com.example.cognito.mercado.Blog.dto.PostRequest;
import com.example.cognito.mercado.Blog.dto.PostResponse;
import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.model.Subreddit;
import com.example.cognito.mercado.Blog.model.Vote;
import com.example.cognito.mercado.Blog.model.VoteType;
import com.example.cognito.mercado.Blog.repository.CommentRepository;
import com.example.cognito.mercado.Blog.repository.VoteRepository;
import com.example.cognito.mercado.security.entity.User;
import com.example.cognito.mercado.security.service.UserService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.cognito.mercado.Blog.model.VoteType.DOWNVOTE;
import static com.example.cognito.mercado.Blog.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserService userService;
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount" , constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (userService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            userService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
