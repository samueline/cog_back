package com.example.cognito.mercado.Blog.controller;

import com.example.cognito.mercado.Blog.dto.PostRequest;
import com.example.cognito.mercado.Blog.dto.PostResponse;
import com.example.cognito.mercado.Blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts(){

        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String name){

        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUser(name));
    }


}
