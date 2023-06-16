package com.example.cognito.mercado.Blog.controller;

import com.example.cognito.mercado.Blog.dto.CommentsDto;
import com.example.cognito.mercado.Blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
         commentService.save(commentsDto);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsPosts(@PathVariable Long postId){
         return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsPosts(postId));
    }

    @GetMapping("/users/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsUsers(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsUsers(userName));
    }


}
