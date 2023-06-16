package com.example.cognito.mercado.Blog.service;

import com.example.cognito.mercado.Blog.dto.PostRequest;
import com.example.cognito.mercado.Blog.dto.PostResponse;
import com.example.cognito.mercado.Blog.exception.BlogException;
import com.example.cognito.mercado.Blog.mapper.PostMapper;
import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.model.Subreddit;
import com.example.cognito.mercado.Blog.repository.PostRepository;
import com.example.cognito.mercado.Blog.repository.SubredditRepository;
import com.example.cognito.mercado.security.entity.User;
import com.example.cognito.mercado.security.repository.UserRepository;
import com.example.cognito.mercado.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public PostResponse getPost(Long id){
        Post post =  postRepository.findById(id)
                .orElseThrow(()-> new BlogException("can not found " + id.toString())) ;
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(()-> new BlogException("can not found")) ;
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    public List<PostResponse> getPostByUser(String username){
        User user = userRepository.findByName(username)
                .orElseThrow((()-> new BlogException("can not found ")));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Post save(PostRequest postRequest){
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new BlogException("can not create sureddit"));
        User currentUser = userService.getCurrentUser();
        return postMapper.map(postRequest, subreddit, currentUser);
    }

}
