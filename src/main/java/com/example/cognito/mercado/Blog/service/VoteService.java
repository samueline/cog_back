package com.example.cognito.mercado.Blog.service;

import com.example.cognito.mercado.Blog.dto.VoteDto;
import com.example.cognito.mercado.Blog.exception.BlogException;
import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.model.Vote;
import com.example.cognito.mercado.Blog.repository.PostRepository;
import com.example.cognito.mercado.Blog.repository.VoteRepository;
import com.example.cognito.mercado.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

import static com.example.cognito.mercado.Blog.model.VoteType.UPVOTE;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                        .orElseThrow(() -> new BlogException("post not found whit id"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, userService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new BlogException("You have already vote");
        }
        if (UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(userService.getCurrentUser())
                .build();
    }

}
